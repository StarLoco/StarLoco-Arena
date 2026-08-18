package gamedata

// Interactive-element VIEWS — data.bdat record type 360 (client `rb_0`, loaded by
// `nh_2`, whose surviving trace string is "Loading N interactive elements
// views..." / "vues d'éléments interactifs").
//
// READ THIS BEFORE REACHING FOR IT: despite the name, this is **not** the
// interactive-element table. It is a sprite descriptor keyed by view id — gfx,
// colour, height — and it carries no instanceId, no world, no cell, no descriptor
// and no behaviour. The client attaches these to elements it already knows about
// from its own env layer (`un_2.a`: "element interactif N n'a pas de vue", then
// `vx_1.iw` → `aL(gfx)` / `B(height)` / `setColor(colour)`).
//
// The server renders nothing, so **it has no consumer here and is not expected to
// get one.** It is decoded so the record type is accounted for, and so the claim
// above is backed by a test rather than by a comment: the shipped table is 42
// records in which Type, Colour and Height never vary and Unused is dead.
//
// Where the element table actually lives: `maps/env/<world>.jar` (client `ru_2` /
// `aEG`) plus `maps/tplg` for ground altitude. See ROADMAP item 24 and
// docs/OVERWORLD-MAP.md.

// TypeElementView is the data.bdat record type holding element sprite views.
const TypeElementView = 360

// ElementView is one `rb_0` record: a fixed 19-byte sprite descriptor.
type ElementView struct {
	// ID is the view id env elements reference (aEG.dBG holds a list of these).
	ID int32
	// Type is `rb_0.Gp`, the view type. 0 in every shipped record.
	Type int16
	// GFX is the sprite id (`adz()`), and the only field that varies in retail
	// data.
	GFX int32
	// Colour is `adA()`, fed to setColor. -1 (none) in every shipped record.
	Colour int32
	// Height is `PD()`. 0 in every shipped record.
	Height int8
	// Unused is the record's trailing i32 (`eA()`). It has **no reader anywhere in
	// the client** and is 0 in every shipped record; kept so the 19-byte layout is
	// documented in full rather than silently truncated.
	Unused int32
}

// ElementViews is the decoded type-360 table, keyed by view id.
type ElementViews struct {
	byID map[int32]*ElementView
}

// NewElementViews builds a table from explicit records (tests).
func NewElementViews(views ...*ElementView) *ElementViews {
	t := &ElementViews{byID: make(map[int32]*ElementView, len(views))}
	for _, v := range views {
		if v != nil {
			t.byID[v.ID] = v
		}
	}
	return t
}

// Get returns one view, or nil.
func (t *ElementViews) Get(id int32) *ElementView {
	if t == nil {
		return nil
	}
	return t.byID[id]
}

// Len reports how many views are loaded.
func (t *ElementViews) Len() int {
	if t == nil {
		return 0
	}
	return len(t.byID)
}

// All exposes the table for iteration.
func (t *ElementViews) All() map[int32]*ElementView {
	if t == nil {
		return nil
	}
	return t.byID
}

// LoadElementViews decodes every type-360 record.
func (s *Store) LoadElementViews() (*ElementViews, error) {
	t := &ElementViews{byID: make(map[int32]*ElementView)}
	for _, e := range s.EntriesOf(TypeElementView) {
		rec, err := s.ReadRecord(e.Position)
		if err != nil {
			return nil, err
		}
		if v := decodeElementView(rec.Data); v != nil {
			t.byID[v.ID] = v
		}
	}
	return t, nil
}

// decodeElementView reads one `rb_0` record, or nil if it is short.
//
// Field order is `rb_0.a(ByteBuffer, int, short)` under version 1: getInt,
// getShort, getInt, getInt, get, getInt = 19 bytes, which is exactly the
// `dataLen` of every shipped record.
func decodeElementView(data []byte) *ElementView {
	c := &cur{b: data}
	v := &ElementView{}
	v.ID = c.i32()
	v.Type = c.i16()
	v.GFX = c.i32()
	v.Colour = c.i32()
	v.Height = int8(c.u8())
	v.Unused = c.i32()
	if !c.ok() {
		return nil
	}
	return v
}
