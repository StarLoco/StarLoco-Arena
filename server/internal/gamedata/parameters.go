package gamedata

// parameters.go decodes `np_1` — the client's "gameplay parameter" element.
//
// It is a small polymorphic record carried by coach cards (field 19), and by
// parts of the challenge and tournament tables. Its element layout was the last
// unknown blocking those three records, which is why the coach-card decoder used
// to stop at field 18.
//
// Layout, from `np_1.k(ByteBuffer)` (and confirmed against its writer `cd()` and
// its size function `nj()`):
//
//	[i32 type][i32 id][i32 parentId][u8 n][i32 × n params][i16 effectVersion]
//	  if effectVersion != 0: [i32 effectId][Ht blob]
//
// `nj()` returns `13 + 4*len(params) + (effect != nil ? effect.nj()+2+4 : 2)`,
// which is exactly 4+4+4+1 for the header plus the trailing short — a useful
// cross-check that no field is missing.
//
// TWO TRAPS, both different from the effect lists elsewhere in this format:
//
//  1. The trailing effect is written **version first, then id** — the reverse of
//     the `[i32 id][i16 ver][i32 len]` framing `decodeEffectList` uses.
//  2. It has **no length prefix**: `Ht.a(buffer, id, ver)` consumes it inline. So
//     an `np_1` carrying an effect can only be skipped by fully parsing that
//     effect, and a decoder that guesses will silently desynchronise the rest of
//     the record. We therefore refuse to guess — see ParameterEffectUnsupported.

// ParamTypeVictoryCondition (14, "Condition de victoire") is the ONE np_1 type
// that overrides the element layout. Its class `wi_0` reads
//
//	[i32 id][i32 parentId][mp_2 blob]
//
// with no param array and no trailing effect. Decoding it with the generic
// layout reads the mp_2's leading bytes as a param count and desynchronises the
// rest of the record — which is exactly what happened to challenges 14 and
// 37..44 before this case existed.
const ParamTypeVictoryCondition int32 = 14

// VictoryCondition is the `mp_2` payload of a type-14 parameter:
//
//	[i16 type][i32 id][u8 n][i32 × n params][u8 flag][i32 value][u8 grade]
//
// (`mp_2.i` / `mp_2.cd`; its `nj()` = 7 + 4*len + 1 + 4 + 1 confirms the shape.)
type VictoryCondition struct {
	Type   int16
	ID     int32
	Params []int32
	Flag   bool
	Value  int32
	Grade  uint8
}

// Parameter is one decoded `np_1` element.
type Parameter struct {
	// Type selects the concrete subclass (the client's `ajr_2` enum: Rx, wb_1,
	// qt_2 …). An unknown type still decodes — the client falls back to a generic
	// `aIE(type)` holder rather than failing — so this is data, not a tag we must
	// recognise.
	Type int32
	// ID and ParentID are the element's own id and the id it refines.
	ID       int32
	ParentID int32
	// Params are the element's operands (`JI`).
	Params []int32
	// EffectVersion is 0 when the element carries no trailing effect.
	EffectVersion int16
	// EffectID is meaningful only when EffectVersion != 0.
	EffectID int32
	// Victory is set only for ParamTypeVictoryCondition, whose element layout is
	// different (see that constant).
	Victory *VictoryCondition
	// Effect is the trailing inline `Ht` effect, present iff EffectVersion != 0.
	// Rule type 12 ("Lance un effet sur tous les combattants a la creation du
	// combat") is the shipped user.
	Effect *Effect
}

// HasEffect reports whether this element carries a trailing inline `Ht` effect.
func (p *Parameter) HasEffect() bool { return p.EffectVersion != 0 }

// decodeParameters reads `[u8 count]` followed by that many `np_1` elements.
//
// Returns ok=false only on a malformed count or a truncated read. A trailing
// inline effect is handled (decodeEffectCursor consumes it exactly), so an
// element carrying one no longer stops the decode.
func decodeParameters(c *cur) (out []Parameter, ok bool) {
	n := int(c.u8())
	if n < 0 || n > 64 {
		c.err = true
		return nil, false
	}
	for i := 0; i < n && c.ok(); i++ {
		var p Parameter
		p.Type = c.i32()
		p.ID = c.i32()
		p.ParentID = c.i32()

		// Type 14 has its own layout (wi_0): no param array, no effect.
		if p.Type == ParamTypeVictoryCondition {
			p.Victory = decodeVictoryCondition(c)
			out = append(out, p)
			continue
		}

		np := int(c.u8())
		if np < 0 || np > 64 {
			c.err = true
			return out, false
		}
		for j := 0; j < np && c.ok(); j++ {
			p.Params = append(p.Params, c.i32())
		}
		p.EffectVersion = c.i16()
		if p.EffectVersion != 0 {
			p.EffectID = c.i32()
			// The effect is INLINE with no length prefix, so it can only be
			// passed by parsing it in full — decodeEffectCursor consumes it
			// exactly. Reading a byte too few or too many here desynchronises
			// everything after this array.
			ef := decodeEffectCursor(c)
			ef.EffectID = p.EffectID // the wrapper id is authoritative
			p.Effect = &ef
		}
		out = append(out, p)
	}
	return out, c.ok()
}

// decodeVictoryCondition reads the `mp_2` payload carried by a type-14 element.
func decodeVictoryCondition(c *cur) *VictoryCondition {
	v := &VictoryCondition{}
	v.Type = c.i16()
	v.ID = c.i32()
	n := int(c.u8())
	if n < 0 || n > 64 {
		c.err = true
		return v
	}
	for i := 0; i < n && c.ok(); i++ {
		v.Params = append(v.Params, c.i32())
	}
	v.Flag = c.u8() == 1
	v.Value = c.i32()
	v.Grade = c.u8()
	return v
}

// Rule types from the client's `ajr_2` enum. Only the ones the server acts on
// are named; the full table is in docs/DATA-COVERAGE.md §5b.
const (
	ParamTypeBudget          int32 = 1  // "Modifie le budget"
	ParamTypeMinFighters     int32 = 2  // "Modifie le nombre minimum de combattant"
	ParamTypeMaxFighters     int32 = 3  // "Modifie le nombre maximum de combattant"
	ParamTypeTurnDurationMS  int32 = 10 // "Modifie la durée en milliseconde du tour de chaque combattant"
	ParamTypeSuddenDeathTurn int32 = 11 // "Modifie le tour du début de la mort subite"
	ParamTypeBonusCellMult   int32 = 13 // "Multiplie les effets des cases bonus"
	ParamTypeArena           int32 = 29 // "Choisir une arène"
	// ParamTypeNoBudgetLimit carries no parameters; its i18n label is the whole
	// rule ("content.54.1000 = Pas de limite de budget"). Challenge 12 uses it.
	ParamTypeNoBudgetLimit int32 = 1000
)

// FirstParam returns params[0] of the first parameter of the given type, and
// whether one was present. Rules are single-valued in the shipped data, so
// "first wins" is the whole rule; a caller that needs to sum should iterate.
func FirstParam(ps []Parameter, typ int32) (int32, bool) {
	for _, p := range ps {
		if p.Type == typ && len(p.Params) > 0 {
			return p.Params[0], true
		}
	}
	return 0, false
}
