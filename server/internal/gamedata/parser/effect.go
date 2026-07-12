package parser

// EffectRaw is the intermediate representation of one parsed Effect record,
// shared by cards.dat/spells.dat/events.dat/staticEffects.dat, see
// docs/04-game-data-format.md §4.2 section 3.
type EffectRaw struct {
	ID         int32
	ParentType string
	ParentID   int32
	// Reserved is the int16 the reference loader reads immediately after
	// ParentID and then ignores (it plays no role in combat, hence the
	// server never uses it). It is preserved here purely so byte-exact
	// re-encoders (cmd/studio/encode) can reproduce it -- the real data
	// stores non-zero values here (e.g. -1 / 1), so it is NOT safely
	// assumable to be 0. See docs/04-game-data-format.md §4.2 section 3.
	Reserved               int16
	Duration               []int32
	ActionID               int32
	IsCritical             bool
	Params                 []float32
	AreaShape              int16
	AreaSize               []int32
	Targets                []int32
	TriggersAfter          []int32
	TriggersBefore         []int32
	AffectedByLocalisation bool
}

// ReadEffect reads one Effect record in the shared layout used across
// cards.dat/spells.dat/events.dat/staticEffects.dat.
func ReadEffect(r *Reader) EffectRaw {
	e := EffectRaw{}
	e.ID = r.Int32()
	e.ParentType = r.String()
	e.ParentID = r.Int32()
	e.Reserved = r.Int16() // ignored by the engine, preserved for byte-exact re-encode
	e.Duration = r.Int32Slice()
	e.ActionID = r.Int32()
	e.IsCritical = r.Bool()
	e.Params = r.Float32Slice()
	e.AreaShape = r.Int16()
	e.AreaSize = r.Int32Slice()
	e.Targets = r.Int32Slice()
	e.TriggersAfter = r.Int32Slice()
	e.TriggersBefore = r.Int32Slice()
	e.AffectedByLocalisation = r.Bool()
	return e
}
