package combat

import "strings"

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase L's
// custom cast-criteria evaluator, a direct port of the decompiled
// dofusarena/common/game/ai/{CriteriaCompiler,CanSummonCriterion,
// CanCastWhenCarryCriterion,CantCastWhenCarriedCriterion}.java. Confirmed
// against this project's real spells.dat data (via a one-off inspection
// script): only 3 distinct criterion tokens actually appear across the
// full spell catalog -- "canSummon", "cantCastWhenCarrying",
// "cantCastWhenCarried", "canCastWhenCarrying" (and combinations joined
// by ';', e.g. "cantCastWhenCarrying;cantCastWhenCarried") -- so a small
// named-criterion registry (rather than a full expression-language
// compiler) is sufficient, per docs/05-combat-engine.md §5.5 step 9's own
// suggestion.

// evaluateCastCriteria parses spell.Criterion (a ';'-separated list of
// criterion tokens, e.g. "cantCastWhenCarrying;cantCastWhenCarried") and
// reports whether caster satisfies every one of them, mirroring
// CriteriaCompiler.compile()+each Criterion's getValidity() returning 0
// (valid) for ALL entries -- a single failing criterion rejects the whole
// cast, matching the reference's implicit AND semantics (SpellCastAction
// would only proceed if every compiled Criterion passed; no OR/NOT
// combinators exist in the reference format). An empty/unrecognized
// string is always valid (mirrors CriteriaCompiler.compile()'s own
// `s == null || s.length() == 0` early-return of an empty list, and an
// unrecognized token only logs an error server-side in the reference --
// it does NOT reject the cast, so this port matches that exact
// permissive-on-unknown-token behavior too).
func (f *Fight) evaluateCastCriteria(criterion string, caster *Fighter) bool {
	if criterion == "" {
		return true
	}
	for _, tok := range strings.Split(criterion, ";") {
		switch strings.ToLower(strings.TrimSpace(tok)) {
		case "cansummon":
			if !f.canSummonCriterion(caster) {
				return false
			}
		case "cancastwhencarrying":
			if !canCastWhenCarryCriterion(caster, true) {
				return false
			}
		case "cantcastwhencarrying":
			if !canCastWhenCarryCriterion(caster, false) {
				return false
			}
		case "cantcastwhencarried":
			if !cantCastWhenCarriedCriterion(caster) {
				return false
			}
		default:
			// Mirrors CriteriaCompiler.compile()'s own behavior for an
			// unrecognized token: log and otherwise ignore it (does not
			// reject the cast).
			f.logger.Debug().Str("criterion", tok).Msg("combat: unrecognized cast criterion token, ignoring (matches reference's permissive-on-unknown behavior)")
		}
	}
	return true
}

// canSummonCriterion mirrors CanSummonCriterion.getValidity(): a caster
// may summon if their current living-summon count is strictly less than
// 1 + their NbSummons characteristic (the "+1" bonus-summon-slot baseline
// mirrors the reference's exact `1 + getCharacteristicValue(NB_SUMMONS)`
// formula).
func (f *Fight) canSummonCriterion(caster *Fighter) bool {
	count := 0
	for _, fr := range f.Timeline.Order() {
		if !fr.IsDead && fr.Father == caster {
			count++
		}
	}
	return count < 1+int(caster.Characteristic(NbSummons))
}

// canCastWhenCarryCriterion mirrors CanCastWhenCarryCriterion.getValidity():
// valid exactly when caster.isCarrying() (CarriedFighter != nil) matches
// the criterion's own configured expectation (want=true for
// "canCastWhenCarrying", want=false for "cantCastWhenCarrying").
func canCastWhenCarryCriterion(caster *Fighter, want bool) bool {
	isCarrying := caster.CarriedFighter != nil
	return isCarrying == want
}

// cantCastWhenCarriedCriterion mirrors
// CantCastWhenCarriedCriterion.getValidity(): valid exactly when caster is
// NOT currently being carried by another fighter (piggybacked).
func cantCastWhenCarriedCriterion(caster *Fighter) bool {
	return caster.CarriedByFighter == nil
}
