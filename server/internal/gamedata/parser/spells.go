package parser

// SpellRaw mirrors the EXACT field READ ORDER of the reference client's
// SpellLoader.readSpells (SpellLoader.java:74-90), which is NOT the same as
// Spell.java's constructor argument order (the loader reorders args when
// calling the constructor). The wire order is:
//
//	id(int), actionPoints(byte), castFreqMaxPerPlayer(byte),
//	castFreqMaxPerTurn(byte), castFreqMinInterval(byte),
//	castTestLOS(bool), castOnlyLine(bool),
//	rangeMin(byte), rangeMax(byte),
//	value(int), aiTargetId(int),
//	testFreeCell(bool),                <-- AFTER aiTargetId, not before rangeMin
//	scriptId(int), breedId(int), criterion(string), useAutoDescription(bool)
//
// A previous version wrongly placed NeedFreeCell between castOnlyLine and
// rangeMin (and dropped the real testFreeCell bool after aiTargetId), which
// shifted every subsequent field by one byte for EVERY spell -- corrupting
// rangeMin/rangeMax (making spell 140's range look "inverted"), aiTargetId,
// scriptId, breedId, criterion, and NeedFreeCell itself (e.g. spell 169, a
// damage spell, spuriously read NeedFreeCell=true and so rejected casts on
// a targeted fighter). See docs/04-game-data-format.md §4.3.
type SpellRaw struct {
	ID                        int32
	ActionPointsCost          byte // PARequired
	CastFrequencyMaxPerPlayer byte
	CastFrequencyMaxPerTurn   byte
	CastFrequencyMinInterval  byte
	CastTestLineOfSight       bool
	CastOnlyLine              bool
	RangeMin                  byte
	RangeMax                  byte
	Price                     int32
	AiTargetID                int32
	NeedFreeCell              bool
	ScriptID                  int32
	BreedID                   int32
	Criterion                 string
	UseAutoDescription        bool
}

// SpellsFile is the fully-parsed content of spells.dat: spell definitions
// and the effects attached to them by ParentID.
type SpellsFile struct {
	Spells  []SpellRaw
	Effects []EffectRaw
}

// ParseSpellsFile parses the full contents of spells.dat.
func ParseSpellsFile(data []byte) (SpellsFile, error) {
	r := NewReader(data)
	var out SpellsFile

	spellCount := int(r.Int32())
	out.Spells = make([]SpellRaw, 0, spellCount)
	for i := 0; i < spellCount; i++ {
		// Fields MUST be read in the exact wire order (see SpellRaw's doc
		// comment); Go evaluates struct-literal field values in source
		// order, so the read order here matches SpellLoader.java:74-90.
		out.Spells = append(out.Spells, SpellRaw{
			ID:                        r.Int32(),
			ActionPointsCost:          r.Byte(),
			CastFrequencyMaxPerPlayer: r.Byte(),
			CastFrequencyMaxPerTurn:   r.Byte(),
			CastFrequencyMinInterval:  r.Byte(),
			CastTestLineOfSight:       r.Bool(),
			CastOnlyLine:              r.Bool(),
			RangeMin:                  r.Byte(),
			RangeMax:                  r.Byte(),
			Price:                     r.Int32(),
			AiTargetID:                r.Int32(),
			NeedFreeCell:              r.Bool(),
			ScriptID:                  r.Int32(),
			BreedID:                   r.Int32(),
			Criterion:                 r.String(),
			UseAutoDescription:        r.Bool(),
		})
	}

	effectCount := int(r.Int32())
	out.Effects = make([]EffectRaw, 0, effectCount)
	for i := 0; i < effectCount; i++ {
		out.Effects = append(out.Effects, ReadEffect(r))
	}

	if err := r.Err(); err != nil {
		return SpellsFile{}, err
	}
	return out, nil
}
