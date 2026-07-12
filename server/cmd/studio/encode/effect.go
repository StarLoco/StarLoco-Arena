package encode

import "github.com/dofusarena/go-server/internal/gamedata/parser"

// WriteEffect encodes one Effect record in the shared layout used across
// cards.dat/spells.dat/events.dat/staticEffects.dat, the exact inverse of
// parser.ReadEffect. The int16 the engine ignores (EffectRaw.Reserved) is
// re-emitted from its preserved value -- the real data stores non-zero
// values there (e.g. -1/1), so it cannot be assumed 0; the byte-exact
// round-trip tests confirm this reproduces the originals.
func WriteEffect(w *Writer, e parser.EffectRaw) {
	w.Int32(e.ID)
	w.String(e.ParentType)
	w.Int32(e.ParentID)
	w.Int16(e.Reserved)
	w.Int32Slice(e.Duration)
	w.Int32(e.ActionID)
	w.Bool(e.IsCritical)
	w.Float32Slice(e.Params)
	w.Int16(e.AreaShape)
	w.Int32Slice(e.AreaSize)
	w.Int32Slice(e.Targets)
	w.Int32Slice(e.TriggersAfter)
	w.Int32Slice(e.TriggersBefore)
	w.Bool(e.AffectedByLocalisation)
}
