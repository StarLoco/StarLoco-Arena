package encode

import "github.com/dofusarena/go-server/internal/gamedata/parser"

// EncodeCardsFile encodes a parsed cards.dat back to bytes (inverse of
// parser.ParseCardsFile). The fighter-card section re-emits the engine-
// ignored "reserved" fields the parser now preserves (see FighterCardRaw),
// so the whole file round-trips byte-for-byte.
//
// Caveat: bytes the parser read via Reader.Bool() ("non-zero == true") are
// re-emitted as 0/1 by Writer.Bool(). This is byte-exact only if the
// original stored those flags as 0/1 (true for every card in this project's
// real data -- the round-trip test confirms it). A hypothetical file storing
// e.g. 2 for a bool would not byte-match, and the round-trip test would flag
// it.
func EncodeCardsFile(f parser.CardsFile) []byte {
	w := NewWriter()

	w.Int32(int32(len(f.CoachCards)))
	for _, c := range f.CoachCards {
		w.Int32(c.ID)
		w.Int32(c.Type)
		w.Int32(c.Value)
		w.Int32(c.Set)
	}

	w.Int32(int32(len(f.FighterCards)))
	for _, c := range f.FighterCards {
		w.Int32(c.ID)
		w.Byte(c.Type)
		w.Byte(c.ReservedByte1)
		w.Bool(c.ReservedBool1)
		w.Int32(c.ReservedI32A)
		w.Int32(c.ReservedI32B)
		w.Bool(c.ReservedBool2)
		w.Bool(c.ReservedBool3)
		w.Int32(c.Value)
		w.Bool(c.ReservedBool4)
		w.Bool(c.ReservedBool5)
		w.Int32(c.ScriptID)
		w.Int32(c.SubType)
	}

	w.Int32(int32(len(f.Effects)))
	for _, e := range f.Effects {
		WriteEffect(w, e)
	}
	return w.Bytes()
}
