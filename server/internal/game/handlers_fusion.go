package game

import (
	"errors"
	"math/rand"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/protocol"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// Fusion result codes carried by FusionResult(5491).
const (
	fusionResultOK    uint8 = 0 // request processed (outcome in the card ids)
	fusionResultError uint8 = 1 // malformed / invalid request
)

// fusionSuccessPercent is the altar's base success chance. Historically each
// fusion altar had a power level; we model a single altar. On success the
// player obtains a new card; on failure they recover one input as leftovers.
const fusionSuccessPercent = 60

// maxFusionInputs bounds the ids accepted from a single 5490 request.
const maxFusionInputs = 32

// fusionRand is the RNG used for fusion rolls/picks; tests may reseed it via
// SeedFusionRand for deterministic outcomes.
var fusionRand = rand.New(rand.NewSource(1))

// SeedFusionRand reseeds the fusion RNG (test hook for deterministic outcomes).
func SeedFusionRand(seed int64) { fusionRand = rand.New(rand.NewSource(seed)) }

func registerFusionHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpFusionRequest, handleFusionRequest)
}

// handleFusionRequest (5490 C2S: [i32 count]{i32 cardId}) runs the Fusion Lab.
//
// THE LAST ID IS THE TARGET, NOT AN INPUT. The client builds the array as the
// input list with the chosen card inserted at index 0 (`add.java`:
// `jg_02.v(0, ajt_16.azv())`), and `ahg_0.encode()` then writes it REVERSED, so
// the player's chosen card lands last on the wire. `azv()` is `cCr`, which the
// fusion panel exposes as the property "fusionCard" — the card the player is
// trying to MAKE. Reading every id as an input, as this used to, both consumed
// the player's chosen card as fuel and threw away their choice.
//
// The outcome is therefore the CHOSEN card, not a random one. It is still
// constrained to the inputs' CardSet: the target being player-supplied means an
// unconstrained server would let anyone name the best card in the game and fuse
// two commons into it.
//
// Replies with FusionResult(5491) carrying [obtained][notObtained][recovered],
// which the client renders as four distinct outcomes (`cp_0`, case 5491):
// obtained -> "fusionSuccess", notObtained -> "fusionRecipeFailed", recovered ->
// "fusionLeftovers", none -> "fusionFailed". Sending the target back as
// notObtained on a failed roll is what makes the client name the card that was
// missed instead of showing a bare failure.
func handleFusionRequest(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	n, err := r.I32()
	if err != nil {
		return err
	}
	// >= 3: the target plus the client's own minimum of two inputs
	// (`ajt_1`'s "canFusion" is `cCq.size() >= 2`).
	if n < 3 || n > maxFusionInputs || s.deps.Cards == nil {
		return s.sendFusionResult(fusionResultError, 0, 0, 0)
	}
	ids := make([]int32, 0, n)
	for i := int32(0); i < n; i++ {
		id, err := r.I32()
		if err != nil {
			return err
		}
		ids = append(ids, id)
	}
	target := ids[len(ids)-1]
	inputs := ids[:len(ids)-1]

	// The altar bounds how many cards may be fed in ("slotCount" = azi() - 1).
	if lab := s.fusionLab(); lab != nil && len(inputs) > int(lab.Slots) {
		return s.sendFusionResult(fusionResultError, 0, 0, 0)
	}

	// The target must be a real card in the same set as the inputs.
	set, ok := s.commonCardSet(inputs)
	if !ok {
		return s.sendFusionResult(fusionResultOK, 0, 0, 0) // mixed sets -> plain fail
	}
	tc := s.deps.Cards.Get(target)
	if tc == nil || tc.CardSet != set {
		return s.sendFusionResult(fusionResultOK, 0, 0, 0)
	}

	// Roll the altar. The probability curve is the one piece of this mechanic the
	// data does not settle: the panel shows "labPower" beside "kardsPower"
	// (Σ inputs' RequiredLevel − target's FusionPower) and "quality", but the
	// server owns the roll and no client code reveals it. A hard
	// kardsPower >= labPower gate would be wrong: 543 of the 907 cards have
	// RequiredLevel 0, so most fusions would become impossible. Left as a flat
	// chance until the real curve is known — see docs/DATA-COVERAGE.md.
	if fusionRand.Intn(100) < fusionSuccessPercent {
		if err := s.deps.Store.Coaches.ConsumeAndGrant(s.Coach.ID, inputs, target); err != nil {
			if errors.Is(err, store.ErrCardNotOwned) {
				return s.sendFusionResult(fusionResultOK, 0, 0, 0)
			}
			return err
		}
		s.refreshAndPushInventory()
		s.log.Info("fusion success", "coach", s.Coach.Name, "obtained", target)
		return s.sendFusionResult(fusionResultOK, target, 0, 0)
	}

	// Failure: consume the inputs, return one as leftovers, and name the card
	// that was missed so the client can say so.
	recovered := inputs[0]
	if err := s.deps.Store.Coaches.ConsumeAndGrant(s.Coach.ID, inputs, recovered); err != nil {
		if errors.Is(err, store.ErrCardNotOwned) {
			return s.sendFusionResult(fusionResultOK, 0, 0, 0)
		}
		return err
	}
	s.refreshAndPushInventory()
	s.log.Info("fusion failed", "coach", s.Coach.Name, "missed", target, "recovered", recovered)
	return s.sendFusionResult(fusionResultOK, 0, target, recovered)
}

// fusionLab returns the altar a fusion runs on. The 5490 request carries no
// altar id, so the server picks deterministically (lowest id).
func (s *Session) fusionLab() *gamedata.FusionLab {
	if s.deps == nil || s.deps.FusionLabs == nil {
		return nil
	}
	return s.deps.FusionLabs.Default()
}

// commonCardSet returns the shared non-zero CardSet of the inputs, or ok=false
// if they don't all belong to one set (or any id is unknown / ungrouped).
func (s *Session) commonCardSet(inputs []int32) (int32, bool) {
	var set int32
	for _, id := range inputs {
		card := s.deps.Cards.Get(id)
		if card == nil || card.CardSet == 0 {
			return 0, false
		}
		if set == 0 {
			set = card.CardSet
		} else if card.CardSet != set {
			return 0, false
		}
	}
	return set, set != 0
}

// sendFusionResult replies with FusionResult(5491):
// [i8 result][i32 obtained][i32 notObtained][i32 recovered].
func (s *Session) sendFusionResult(result uint8, obtained, notObtained, recovered int32) error {
	w := protocol.NewWriter().U8(result).I32(obtained).I32(notObtained).I32(recovered)
	frame, err := protocol.EncodeS2C(protocol.OpFusionResult, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// refreshAndPushInventory reloads the coach's inventory from the store and
// pushes a fresh CoachInventoryUpdate(5200).
func (s *Session) refreshAndPushInventory() {
	if fresh, err := s.deps.Store.Coaches.Get(s.Coach.ID); err == nil {
		s.Coach.Inventory = fresh.Inventory
	}
	_ = s.pushInventory(s.Coach)
}
