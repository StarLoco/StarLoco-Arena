package gamedata

// fusionlabs.go decodes record type 1100, the fusion-laboratory definitions
// (client `ajd_0`, loader `contentLoader.fusionLaboratoryDefinition` via
// `ank_1`). One record per altar.
//
// The record is 12 bytes and its layout comes from `ajd_0`'s own
// `a(ByteBuffer,int,short)`, confirmed against its writer `cr()`:
//
//	[i64 id][i16 power][u8 quality][u8 slotsPlusOne]
//
// WHAT THE ALTAR IS FOR. The client's fusion panel (`ajt_1`) exposes exactly four
// numbers, and they name the whole mechanic:
//
//	"slotCount"  = lab.azi() - 1          how many input cards the altar takes
//	"labPower"   = lab.tz()               the altar's power
//	"kardsPower" = Σ inputs.RequiredLevel - target.FusionPower
//	"quality"    = lab.tA()               the altar's quality
//	"canFusion"  = inputs >= 2
//
// So fusion is NOT a recipe lookup. The player picks a TARGET card ("fusionCard")
// and feeds it input cards; the inputs' summed RequiredLevel must cover the
// target's FusionPower, and the altar's power/quality modulate the outcome. There
// is no recipe table anywhere in the client — `contentLoader.recipe` is a declared
// i18n key with no loader and no record type behind it.
type FusionLab struct {
	ID int64
	// Power is the altar's own strength ("labPower"), the number the client shows
	// next to the player's computed "kardsPower".
	Power int16
	// Quality is the altar's quality grade ("quality").
	Quality uint8
	// Slots is how many input cards the altar accepts. The record stores it
	// PLUS ONE — the client renders `azi() - 1` — because the stored value counts
	// the target card's slot alongside the inputs.
	Slots uint8
}

// TypeFusionLab is the data.bdat record type holding fusion-lab definitions
// (client enum `atr_0.cVf` = 1100).
const TypeFusionLab = 1100

// FusionLabs is the decoded type-1100 table, keyed by id.
type FusionLabs struct {
	byID map[int64]*FusionLab
}

// NewFusionLabs builds a table from explicit templates (tests).
func NewFusionLabs(labs ...*FusionLab) *FusionLabs {
	t := &FusionLabs{byID: make(map[int64]*FusionLab, len(labs))}
	for _, l := range labs {
		if l != nil {
			t.byID[l.ID] = l
		}
	}
	return t
}

// Get returns the lab with this id, or nil.
func (t *FusionLabs) Get(id int64) *FusionLab {
	if t == nil {
		return nil
	}
	return t.byID[id]
}

// Len reports how many labs decoded.
func (t *FusionLabs) Len() int {
	if t == nil {
		return 0
	}
	return len(t.byID)
}

// All returns the id->lab map (read-only use).
func (t *FusionLabs) All() map[int64]*FusionLab {
	if t == nil {
		return nil
	}
	return t.byID
}

// Default returns the lab a fusion should use when the request does not name one.
// The 5490 request carries no altar id — the client opens the panel from whichever
// interactive element the coach walked up to and never tells the server which —
// so the server needs a single deterministic choice. Lowest id wins, so it cannot
// drift with map iteration order.
func (t *FusionLabs) Default() *FusionLab {
	if t == nil || len(t.byID) == 0 {
		return nil
	}
	var best *FusionLab
	for _, l := range t.byID {
		if best == nil || l.ID < best.ID {
			best = l
		}
	}
	return best
}

// LoadFusionLabs decodes every type-1100 record.
func (s *Store) LoadFusionLabs() (*FusionLabs, error) {
	labs := &FusionLabs{byID: make(map[int64]*FusionLab)}
	for _, e := range s.EntriesOf(TypeFusionLab) {
		rec, err := s.ReadRecord(e.Position)
		if err != nil {
			return nil, err
		}
		if l := decodeFusionLab(rec.Data); l != nil {
			labs.byID[l.ID] = l
		}
	}
	return labs, nil
}

// decodeFusionLab reads one `ajd_0` record, or nil if it is short.
func decodeFusionLab(data []byte) *FusionLab {
	c := &cur{b: data}
	l := &FusionLab{}
	l.ID = c.i64()
	l.Power = c.i16()
	l.Quality = c.u8()
	l.Slots = c.u8()
	if !c.ok() {
		return nil
	}
	return l
}
