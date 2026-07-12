package encode

import "github.com/dofusarena/go-server/internal/gamedata/parser"

// This file holds the whole-file encoders for the .dat formats whose parser
// preserves every meaningful byte (spells, events, summoning,
// staticEffects), so Encode(Parse(f)) == f byte-for-byte. cards.dat is
// handled separately (cards.go) because its parser discards several bytes
// per fighter-card that can't be reconstructed from the exposed struct.

// EncodeSpellsFile encodes a parsed spells.dat back to bytes (inverse of
// parser.ParseSpellsFile). Field order matches SpellRaw's documented wire
// order exactly.
func EncodeSpellsFile(f parser.SpellsFile) []byte {
	w := NewWriter()
	w.Int32(int32(len(f.Spells)))
	for _, s := range f.Spells {
		w.Int32(s.ID)
		w.Byte(s.ActionPointsCost)
		w.Byte(s.CastFrequencyMaxPerPlayer)
		w.Byte(s.CastFrequencyMaxPerTurn)
		w.Byte(s.CastFrequencyMinInterval)
		w.Bool(s.CastTestLineOfSight)
		w.Bool(s.CastOnlyLine)
		w.Byte(s.RangeMin)
		w.Byte(s.RangeMax)
		w.Int32(s.Price)
		w.Int32(s.AiTargetID)
		w.Bool(s.NeedFreeCell)
		w.Int32(s.ScriptID)
		w.Int32(s.BreedID)
		w.String(s.Criterion)
		w.Bool(s.UseAutoDescription)
	}
	w.Int32(int32(len(f.Effects)))
	for _, e := range f.Effects {
		WriteEffect(w, e)
	}
	return w.Bytes()
}

// EncodeEventsFile encodes a parsed events.dat back to bytes.
func EncodeEventsFile(f parser.EventsFile) []byte {
	w := NewWriter()
	w.Int32(int32(len(f.Events)))
	for _, e := range f.Events {
		w.Int32(e.ID)
		w.Bool(e.UseAutoDescription)
	}
	w.Int32(int32(len(f.Effects)))
	for _, e := range f.Effects {
		WriteEffect(w, e)
	}
	return w.Bytes()
}

// EncodeSummoningFile encodes a parsed summoning.dat back to bytes.
func EncodeSummoningFile(rows []parser.SummoningRaw) []byte {
	w := NewWriter()
	w.Int32(int32(len(rows)))
	for _, s := range rows {
		w.Int32(s.ID)
		w.Int32(s.HP)
		w.Int32(s.AP)
		w.Int32(s.MP)
		w.Int32(s.Gfx)
		w.Int32(s.SpellID)
	}
	return w.Bytes()
}

// EncodeStaticEffectsFile encodes a parsed staticEffects.dat back to bytes.
func EncodeStaticEffectsFile(f parser.StaticEffectsFile) []byte {
	w := NewWriter()
	w.Int32(int32(len(f.Areas)))
	for _, a := range f.Areas {
		w.Int32(a.ID)
		w.Int32(a.ScriptID)
		w.Int16(a.AreaShapeID)
		w.Int32Slice(a.AreaParams)
		w.Int32Slice(a.ApplicationTriggers)
		w.Int32Slice(a.UnapplicationTriggers)
		w.Int16(a.MaxExecutionCount)
		w.Int32Slice(a.ApplicationTargets)
		w.Int32Slice(a.UnapplicationTargets)
		w.Int32(a.TargetsToShow)
		w.String(a.EffectAreaType)
		w.Int32Slice(a.DeactivationDelay)
		w.Int32(a.ApplicationCondition)
	}
	w.Int32(int32(len(f.Effects)))
	for _, e := range f.Effects {
		WriteEffect(w, e)
	}
	return w.Bytes()
}
