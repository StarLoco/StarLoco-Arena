package gamedata

import "sort"

// cardsets.go decodes card SETS ("panoplies", record type 101, client class yp_0
// -> runtime fe_1, registered in ayc_0.aLE()).
//
// A set is just an id plus a list of threshold-gated effects; membership runs the
// other way, from each coach card's own CardSet field. The client's rule
// (sj_1.getFieldValue, the coach's aggregated-bonus builder) is:
//
//	count = how many cards of this set the coach has EQUIPPED
//	for each set effect: if (effect.Threshold > count) skip; else apply it
//
// so an effect with threshold 3 needs three of the set's cards equipped. fe_1's
// "halfSetEffects" / "fullSetEffects" split (threshold < size vs == size) is only
// how the UI groups them for display — the engine rule is the per-effect
// threshold, nothing else.
//
// The effects are `akw_0` entries, the SAME structure coach cards carry, whose
// action ids are the client's AI enum — the coach META layer (XP, wounds, morale,
// fatigue, drops, reputation, resurrection, "apply a condition"). They are NOT
// in-fight combat effects.

// TypeCardSet is the data.bdat record type for card sets.
const TypeCardSet = 101

// CardSetEffect is one threshold-gated set bonus (an akw_0 entry).
type CardSetEffect struct {
	// Action is the akw_0 type: an id in the client's AI enum (e.g. 13 =
	// "x% chance to resurrect a fighter that just died").
	Action int32
	// Params are the effect's operands; params[0] is the magnitude the client
	// sums when several sources grant the same action.
	Params []int32
	// Condition is the akw_0 condition mask (evaluated by aap.a against the
	// target fighter's breed and flags); 0 = unconditional.
	Condition int64
	// Threshold is akw_0.aAm(): the number of the set's cards that must be
	// equipped for this effect to apply.
	Threshold uint8
}

// CardSet is a decoded set definition.
type CardSet struct {
	ID      int32
	Effects []CardSetEffect
}

// CardSets holds all set definitions by id.
type CardSets struct {
	byID map[int32]*CardSet
	ids  []int32
}

// LoadCardSets reads and decodes every card-set definition (type 101).
func (s *Store) LoadCardSets() (*CardSets, error) {
	out := &CardSets{byID: make(map[int32]*CardSet)}
	for _, e := range s.EntriesOf(TypeCardSet) {
		rec, err := s.ReadRecord(e.Position)
		if err != nil {
			return nil, err
		}
		if cs := decodeCardSet(rec.Data); cs != nil {
			out.byID[cs.ID] = cs
		}
	}
	out.reindex()
	return out, nil
}

// NewCardSets builds a catalog from explicit definitions (tests/tooling).
func NewCardSets(defs ...*CardSet) *CardSets {
	out := &CardSets{byID: make(map[int32]*CardSet, len(defs))}
	for _, d := range defs {
		if d != nil {
			out.byID[d.ID] = d
		}
	}
	out.reindex()
	return out
}

func (c *CardSets) reindex() {
	c.ids = make([]int32, 0, len(c.byID))
	for id := range c.byID {
		c.ids = append(c.ids, id)
	}
	sort.Slice(c.ids, func(i, j int) bool { return c.ids[i] < c.ids[j] })
}

// Get returns a set by id, or nil.
func (c *CardSets) Get(id int32) *CardSet {
	if c == nil {
		return nil
	}
	return c.byID[id]
}

// IDs returns every set id, ascending.
func (c *CardSets) IDs() []int32 {
	if c == nil {
		return nil
	}
	return c.ids
}

// Len is the number of decoded sets.
func (c *CardSets) Len() int {
	if c == nil {
		return 0
	}
	return len(c.byID)
}

// ActiveEffects returns the set's effects that are unlocked by having `equipped`
// of its cards on the coach — the client's rule, `threshold <= equipped`.
func (c *CardSet) ActiveEffects(equipped int) []CardSetEffect {
	if c == nil || equipped <= 0 {
		return nil
	}
	var out []CardSetEffect
	for _, ef := range c.Effects {
		if int(ef.Threshold) <= equipped {
			out = append(out, ef)
		}
	}
	return out
}

// decodeCardSet parses a yp_0 record: [i32 setId][u8 count][count x akw_0].
func decodeCardSet(data []byte) *CardSet {
	c := &cur{b: data}
	cs := &CardSet{}
	cs.ID = c.i32()
	n := int(c.u8())
	if n < 0 || n > 64 {
		return nil
	}
	for i := 0; i < n && c.ok(); i++ {
		cs.Effects = append(cs.Effects, decodeAkw(c))
	}
	if !c.ok() || cs.ID <= 0 {
		return nil
	}
	return cs
}

// decodeAkw reads one akw_0 entry, the shared "card effect" structure:
//
//	[i32 type][i8 opCount][i32 x opCount][i64 condition][i8 threshold]
//
// Used by card sets (type 101) and by coach cards' own effect array (type 100
// field 15), so the two stay in step by construction.
func decodeAkw(c *cur) CardSetEffect {
	ef := CardSetEffect{Action: c.i32()}
	n := int(c.u8())
	if n < 0 || n > 32 {
		c.err = true
		return ef
	}
	for i := 0; i < n && c.ok(); i++ {
		ef.Params = append(ef.Params, c.i32())
	}
	ef.Condition = c.i64()
	ef.Threshold = c.u8()
	return ef
}
