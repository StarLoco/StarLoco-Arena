package game

import "strings"

// criteria.go evaluates a spell's field-20 "criterion" cast precondition — a
// ';'-separated list of case-insensitive named tokens combined with implicit AND
// (an empty string is no gate; an unrecognised token is permissive/skipped).
// Ported from the client's CriteriaCompiler (ahp_1): the grammar has NO
// operators or operands — the polarity and any threshold are baked into the
// token name — and every token reads only the CASTER's state.
//
// The full 15-token set (case-insensitive):
//
//	canSummon                 living summons < 1 + NB_SUMMONS
//	canCastWhenCarrying       is carrying someone
//	cantCastWhenCarrying      is NOT carrying
//	cantCastWhenCarried       is NOT being carried
//	canCastWhenDying          HP <= 25% of max
//	canCastWhenInjured        HP <= 99% of max (not full)
//	canCastWhenDrunk          has the DRUNK state
//	can/cannotCastWhenMaskClass    has / lacks the MASK_CLASS state
//	can/cannotCastWhenMaskBerzerk  has / lacks the MASK_BERZERK state
//	can/cannotCastWhenMaskCoward   has / lacks the MASK_COWARD state
//	canCastWhenCarryAlly      carrying an ALLY
//	canCastWhenCarryEnnemy    carrying an ENEMY  (sic — the data's spelling)

// meetsCastCriteria reports whether the caster satisfies every token of a
// spell's criterion string (implicit AND). Empty = always true.
func (f *Fight) meetsCastCriteria(caster *FightFighter, criterion string) bool {
	if caster == nil || criterion == "" {
		return true
	}
	for _, tok := range strings.Split(criterion, ";") {
		if !f.criterionToken(caster, strings.ToLower(strings.TrimSpace(tok))) {
			return false
		}
	}
	return true
}

// criterionToken evaluates one lowercased token against the caster. An unknown
// token is permissive (true), matching the client (which logs and skips it).
func (f *Fight) criterionToken(caster *FightFighter, tok string) bool {
	switch tok {
	case "":
		return true
	case "cansummon":
		return f.summonCount(caster) < 1+caster.nbSummons()
	case "cancastwhencarrying":
		return caster.CarriedFighter != nil
	case "cantcastwhencarrying":
		return caster.CarriedFighter == nil
	case "cantcastwhencarried":
		return caster.CarriedByFighter == nil
	case "cancastwhendying":
		return caster.hpAtOrBelowPct(25)
	case "cancastwheninjured":
		return caster.hpAtOrBelowPct(99)
	case "cancastwhendrunk":
		return caster.hasState(stateDrunk)
	case "cancastwhenmaskclass":
		return caster.hasState(stateMaskClass)
	case "cannotcastwhenmaskclass":
		return !caster.hasState(stateMaskClass)
	case "cancastwhenmaskberzerk":
		return caster.hasState(stateMaskBerzerk)
	case "cannotcastwhenmaskberzerk":
		return !caster.hasState(stateMaskBerzerk)
	case "cancastwhenmaskcoward":
		return caster.hasState(stateMaskCoward)
	case "cannotcastwhenmaskcoward":
		return !caster.hasState(stateMaskCoward)
	case "cancastwhencarryally":
		return caster.CarriedFighter != nil && caster.CarriedFighter.TeamID == caster.TeamID
	case "cancastwhencarryennemy":
		return caster.CarriedFighter != nil && caster.CarriedFighter.TeamID != caster.TeamID
	default:
		return true // unrecognised token — permissive
	}
}

// summonCount returns how many living creatures the fighter currently has out.
func (f *Fight) summonCount(caster *FightFighter) int32 {
	var n int32
	for _, ff := range f.allFighters() {
		if ff.HP > 0 && ff.Father == caster {
			n++
		}
	}
	return n
}

// nbSummons is the fighter's NB_SUMMONS characteristic (client id 26): extra
// simultaneous summons above the base of one. Base 0; an action-74 buff ("+1
// invocation") raises it, so a buffed Sadida/Osamodas/Rogue reaches a cap of 2.
func (ff *FightFighter) nbSummons() int32 {
	if ff == nil {
		return 0
	}
	return ff.NbSummons
}

// hpAtOrBelowPct reports whether the fighter's HP is at or below pct% of its
// max (rounded, matching the client's Math.round(max*pct/100)).
func (ff *FightFighter) hpAtOrBelowPct(pct int32) bool {
	if ff.MaxHP <= 0 {
		return false
	}
	return ff.HP <= (ff.MaxHP*pct+50)/100
}
