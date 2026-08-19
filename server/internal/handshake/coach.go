package handshake

import (
	"fmt"
	"math"
	"sort"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// EncodeCoachCreationRequest builds an opcode-2048 S2C frame (empty payload).
//
// After a successful auth the client passively waits for this push. On receipt
// it opens the coach-creation screen, leaving the black/login screen. It never
// requests it — the server must send it unprompted.
func EncodeCoachCreationRequest() ([]byte, error) {
	return protocol.EncodeS2C(protocol.OpCoachCreationRequest, nil)
}

// EncodeCoachCreationResult builds an opcode-2050 S2C frame.
// resultCode 0 = success; the client then advances (server should follow with
// CoachInformations/2052 to enter the lobby).
func EncodeCoachCreationResult(resultCode uint8) ([]byte, error) {
	payload := protocol.NewWriter().U8(resultCode).Bytes()
	return protocol.EncodeS2C(protocol.OpCoachCreationResult, payload)
}

// CoachCreation is the opcode-2049 client request submitting a new coach.
// Payload: [u8 nameLen][name][u8 hairColor][u8 skinColor][u8 sex].
type CoachCreation struct {
	Name      string
	HairColor uint8
	SkinColor uint8
	Sex       uint8
}

// DecodeCoachCreation parses an opcode-2049 payload.
//
// Wire order (alq_0.encode): [u8 nameLen][name][u8 SKIN][u8 HAIR][u8 sex]. The
// first color byte is SKIN (aQe -> apH flesh palette), the second is HAIR
// (aQd -> agl_0 hair palette). Reading them in the wrong slots swaps the coach's
// colors, so the order here is skin-then-hair (verified against the client).
func DecodeCoachCreation(payload []byte) (*CoachCreation, error) {
	r := protocol.NewReader(payload)
	name, err := r.StringU8()
	if err != nil {
		return nil, fmt.Errorf("coach name: %w", err)
	}
	skin, err := r.U8()
	if err != nil {
		return nil, fmt.Errorf("coach skin: %w", err)
	}
	hair, err := r.U8()
	if err != nil {
		return nil, fmt.Errorf("coach hair: %w", err)
	}
	sex, err := r.U8()
	if err != nil {
		return nil, fmt.Errorf("coach sex: %w", err)
	}
	return &CoachCreation{Name: name, HairColor: hair, SkinColor: skin, Sex: sex}, nil
}

// Coach is a minimal local coach the server serializes into CoachInformations.
type Coach struct {
	ID        int64
	Name      string
	HairColor uint8
	SkinColor uint8
	Sex       uint8
	// Standing is the coach's EVOLUTION experience. The client derives the
	// evolution level from it (aet_0.nJ, mirrored by game.StandingToLevel) and
	// pops its level-up dialog when it changes, so sending 0 pinned every coach
	// at level 1 no matter how much it had earned.
	Standing int32
	// Criteria are the coach's persisted achievement criteria, emitted in the
	// 0x200 stat-pairs blob. criterionZaapUnlock is always added on top, so this
	// may be nil. See buildCriteriaBlob for the ordering/dedup rules.
	Criteria []Criterion
}

// Criterion is one achievement-criterion key/value pair (the client's or_0 enum
// id and its counter). Values are booleans-as-1 for completion flags.
type Criterion struct {
	ID    uint16
	Value uint16
}

// criterionZaapUnlock is client criterion 219 ("the coach met Baan / the Help
// demon"). Achievement 448 — the gate that lets the island Zaap dialog open —
// requires this criterion >= 1. We seed it in the coach descriptor (below) so the
// Zaap unlocks WITHOUT sending opcode 22002, whose client handler (asA)
// unconditionally pops the tutorial-guide dialog on receipt.
const criterionZaapUnlock uint16 = 219

// MaxCriterionID is the highest id the client's or_0 criterion enum defines.
// Ids above this are not criteria at all (the server uses a private range above
// it for its own bookkeeping) and MUST NOT be sent: they would be inert, but
// they inflate the blob for no reason. See buildCriteriaBlob.
const MaxCriterionID uint16 = 1007

// buildCriteriaBlob serializes the 0x200 stat-pairs blob: [i16 byteLen] then
// byteLen/4 × {[i16 criterionId][i16 value]}.
//
// DANGEROUS FIELD. The client's aez_0.O() loops `while (n2*4 < byteLen)` reading
// four bytes per iteration with NO bounds check against the buffer. If byteLen
// overstates the pairs that follow, it reads on into the NEXT descriptor
// sections — corrupting every later field, or underflowing, which aez_0.b()
// swallows by returning false, and the coach then fails to materialise at all
// (silent "stuck at loading"). byteLen must therefore be EXACTLY 4×pairs, which
// is why this function owns both and no caller can write them separately.
//
// It is also read SIGNED (getShort), so a blob over 32767 bytes would go
// negative and the client would silently read zero criteria; hence the cap.
func buildCriteriaBlob(criteria []Criterion) []byte {
	pairs := normalizeCriteria(criteria)

	w := protocol.NewWriter()
	w.U16(uint16(len(pairs) * 4)) // byteLen — EXACTLY 4 per pair, see above
	for _, c := range pairs {
		w.U16(c.ID)
		w.U16(c.Value)
	}
	return w.Bytes()
}

// normalizeCriteria filters, de-duplicates and orders a criteria set into the
// exact pairs that go on the wire. Both encodings below are built from it, so
// the login descriptor and the achievement-tab snapshot can never disagree about
// what a coach has achieved.
//
// The cap keeps byteLen a positive i16 for the descriptor blob. It is applied
// here rather than in that encoder so the two encodings stay identical even in
// the absurd case.
func normalizeCriteria(criteria []Criterion) []Criterion {
	// criterionZaapUnlock is always present; an explicit entry for it wins so a
	// coach can never end up with two pairs for the same key.
	pairs := make([]Criterion, 0, len(criteria)+1)
	seen := make(map[uint16]bool, len(criteria)+1)
	for _, c := range criteria {
		if c.ID == 0 || c.ID > MaxCriterionID || seen[c.ID] {
			continue
		}
		// Value 0 means "not achieved"; absent keys already read as 0 client-side
		// (aGz.cp), so emitting them is pure waste.
		if c.Value == 0 {
			continue
		}
		seen[c.ID] = true
		pairs = append(pairs, c)
	}
	if !seen[criterionZaapUnlock] {
		pairs = append(pairs, Criterion{ID: criterionZaapUnlock, Value: 1})
	}
	// Deterministic order so the frame is byte-stable across runs (and testable).
	sort.Slice(pairs, func(i, j int) bool { return pairs[i].ID < pairs[j].ID })

	// Cap so byteLen stays a positive i16 even in the absurd case.
	if max := int(math.MaxInt16) / 4; len(pairs) > max {
		pairs = pairs[:max]
	}
	return pairs
}

// EncodeStatisticData builds the opcode-22002 payload: [i32 byteLen] then
// byteLen/4 × {[i16 criterionId][i16 value]}.
//
// Note the framing differs from the 2052 descriptor's 0x200 blob, which prefixes
// the SAME pairs with an i16 (client ls_0.a uses getInt, aez_0.O uses getShort).
// The pairs themselves come from normalizeCriteria, so the two always agree.
//
// This must carry the coach's COMPLETE criteria set, not a delta. The client's
// handler A does `Ln().b(ls_0.qI())`, and aez_0.b REPLACES the map wholesale
// rather than merging it — so any criterion missing here is ERASED from the
// running client. Dropping criterionZaapUnlock, for instance, silently re-locks
// the island Zaap until the next login.
func EncodeStatisticData(criteria []Criterion) []byte {
	pairs := normalizeCriteria(criteria)

	w := protocol.NewWriter()
	w.I32(int32(len(pairs) * 4)) // byteLen — EXACTLY 4 per pair
	for _, c := range pairs {
		w.U16(c.ID)
		w.U16(c.Value)
	}
	return w.Bytes()
}

// EncodeCoachInformations builds an opcode-2052 S2C frame carrying a LocalCoach
// serialized at flags=4014 (the value the client hard-codes when decoding).
//
// Field order is the client's SOURCE order (not bit order), big-endian. All
// gated blobs are emitted with zero length/count so the coach is "empty but
// valid", which is enough to render the lobby. See
// client/analysis + memory for the full flag breakdown.
func EncodeCoachInformations(c Coach) ([]byte, error) {
	w := protocol.NewWriter()

	// V (always): id + name
	w.I64(c.ID)
	w.StringU8(c.Name)

	// T (always): look. The client's T reader consumes SKIN then HAIR then sex
	// (by->bh->skin palette, by2->bg->hair palette), so emit skin before hair
	// or the colors render swapped.
	w.U8(c.SkinColor)
	w.U8(c.HairColor)
	w.U8(c.Sex)
	w.U16(0) // UF look id (0 is safe; client derives look from sex)

	// 0x100: tournament points + ladder-levels blob (empty)
	w.I32(0) // anV tournamentPoints
	w.U16(0) // Q blob length = 0

	// 0x400: standing
	w.I32(c.Standing) // bMU

	// 0x20: guild blob (empty -> no guild)
	w.U16(0)

	// 0x80: int-set / betCards blob (empty)
	w.U16(0)

	// 0x2: card inventory blob (empty)
	w.U16(0)

	// 0x4: equipment / sets blob (empty)
	w.U16(0)

	// 0x200: criteria ("stat-pairs") blob. Always carries criterion 219 (see
	// criterionZaapUnlock) plus the coach's persisted criteria — this blob is the
	// ONLY safe way criteria reach the client, so anything not in here is
	// invisible to it. buildCriteriaBlob owns the length prefix; see its comment.
	w.Raw(buildCriteriaBlob(c.Criteria))

	// 0x8: ladder-strength count (u8, not a length) = 0
	w.U8(0)

	// 0x800: adminRight
	w.I32(0) // dBi

	return protocol.EncodeS2C(protocol.OpCoachInformations, w.Bytes())
}

// EncodeEnterInstance builds an opcode-4600 S2C frame. This is the message that
// actually enters the world scene and renders the lobby after CoachInformations
// (which only loads the coach into memory). mapID 0 is the island/lobby world.
//
//	Payload: [f32 worldX][f32 worldY][i16 altitude][i16 mapID][u8 dynamic]
func EncodeEnterInstance(x, y float32, altitude, mapID int16, dynamic bool) ([]byte, error) {
	w := protocol.NewWriter().
		F32(x).
		F32(y).
		U16(uint16(altitude)).
		U16(uint16(mapID))
	if dynamic {
		w.U8(1)
	} else {
		w.U8(0)
	}
	return protocol.EncodeS2C(protocol.OpEnterInstance, w.Bytes())
}
