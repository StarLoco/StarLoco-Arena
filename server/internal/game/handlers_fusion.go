package game

import (
	"errors"
	"math/rand"

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

// handleFusionRequest (5490 C2S: [i32 count]{i32 cardId}, ids reversed by the
// client) runs the Fusion Lab: it consumes same-set input cards and, on a
// success roll, grants a random other card from that set. Replies with
// FusionResult(5491) carrying [obtained][notObtained][recovered] card ids and,
// on any inventory change, pushes the updated inventory (5200).
//
// Fusion rule (a faithful approximation — the original's ~100 recipes are not in
// the decoded gamedata): the inputs must be >=2 cards of a single CardSet the
// coach owns. Success -> obtain a random other card of that set. Failure ->
// recover one input card as leftovers.
func handleFusionRequest(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	n, err := r.I32()
	if err != nil {
		return err
	}
	if n < 2 || n > maxFusionInputs || s.deps.Cards == nil {
		return s.sendFusionResult(fusionResultError, 0, 0, 0)
	}
	inputs := make([]int32, 0, n)
	for i := int32(0); i < n; i++ {
		id, err := r.I32()
		if err != nil {
			return err
		}
		inputs = append(inputs, id)
	}

	// All inputs must belong to a single, non-zero CardSet.
	set, ok := s.commonCardSet(inputs)
	if !ok {
		return s.sendFusionResult(fusionResultOK, 0, 0, 0) // no recipe -> plain fail
	}

	// Roll the altar.
	success := fusionRand.Intn(100) < fusionSuccessPercent
	if success {
		obtained := s.pickFusionOutput(set, inputs)
		if obtained == 0 {
			return s.sendFusionResult(fusionResultOK, 0, 0, 0)
		}
		if err := s.deps.Store.Coaches.ConsumeAndGrant(s.Coach.ID, inputs, obtained); err != nil {
			if errors.Is(err, store.ErrCardNotOwned) {
				return s.sendFusionResult(fusionResultOK, 0, 0, 0)
			}
			return err
		}
		s.refreshAndPushInventory()
		s.log.Info("fusion success", "coach", s.Coach.Name, "obtained", obtained)
		return s.sendFusionResult(fusionResultOK, obtained, 0, 0)
	}

	// Failure: consume the inputs but return one as leftovers (recovered).
	recovered := inputs[0]
	if err := s.deps.Store.Coaches.ConsumeAndGrant(s.Coach.ID, inputs, recovered); err != nil {
		if errors.Is(err, store.ErrCardNotOwned) {
			return s.sendFusionResult(fusionResultOK, 0, 0, 0)
		}
		return err
	}
	s.refreshAndPushInventory()
	s.log.Info("fusion failed (leftovers)", "coach", s.Coach.Name, "recovered", recovered)
	return s.sendFusionResult(fusionResultOK, 0, 0, recovered)
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

// pickFusionOutput chooses a random card from the set that isn't one of the
// inputs (so fusion produces something new). Falls back to any set card.
func (s *Session) pickFusionOutput(set int32, inputs []int32) int32 {
	inSet := s.deps.Cards.CardsInSet(set)
	if len(inSet) == 0 {
		return 0
	}
	isInput := make(map[int32]bool, len(inputs))
	for _, id := range inputs {
		isInput[id] = true
	}
	candidates := make([]int32, 0, len(inSet))
	for _, id := range inSet {
		if !isInput[id] {
			candidates = append(candidates, id)
		}
	}
	if len(candidates) == 0 {
		candidates = inSet // set had only the input cards; allow any
	}
	return candidates[fusionRand.Intn(len(candidates))]
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
