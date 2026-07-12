package combat

// This file ports the reference client's FightTargetValidator (see the
// decompiled com/ankamagames/dofusarena/common/game/fight/
// FightTargetValidator.java) so the SERVER filters each effect's resolved
// targets by the effect's own target-condition bitmask (EffectDef.Targets),
// which it previously ignored entirely -- applying every effect to any living
// fighter in its area shape regardless of ally/enemy/self intent. That gap is
// why e.g. a self-only buff could land on an enemy and, conversely, why some
// spells behaved oddly when their authored conditions weren't respected.
//
// Model (matches FightTargetValidator.getTargetValidity): Targets is an int[]
// of conditions. A target is VALID if ANY single condition passes (logical
// OR across the array); a condition passes only if ALL of its set bits hold
// for that target (logical AND within one condition). An empty Targets array
// means "no restriction" (every fighter in the area is a valid target) --
// the permissive default the engine used before this port.

// Target-condition bits from FightTargetValidator.java. Only the fight-role
// bits the server can evaluate are modeled; the breed bits (<<16) are
// deliberately ignored (no shipping spell filters by target breed, and the
// server would just treat an unmatched breed bit as a non-match, wrongly
// rejecting valid targets). See targetConditionPasses for the exact per-bit
// semantics ported from the reference.
const (
	condInAOE         int32 = 1   // CONDITION_IN_AOE: valid only if in the AoE (all our resolved targets are)
	condIsCaster      int32 = 2   // CONDITION_IS_CASTER: target must be the caster
	condIsAlly        int32 = 4   // CONDITION_IS_ALLY: same team as caster (includes caster)
	condIsEnemy       int32 = 8   // CONDITION_IS_ENEMY: different team than caster
	condIsHuman       int32 = 16  // CONDITION_IS_HUMAN: a real (non-summon) fighter
	condIsSummoned    int32 = 32  // CONDITION_IS_SUMMONED: a summoned fighter
	condIsEffectArea  int32 = 64  // CONDITION_IS_EFFECT_AREA: target is a ground area (never a fighter here)
	condIsAllyNotSelf int32 = 128 // CONDITION_IS_ALLY_EXCEPT_CASTER
	condIsNotCaster   int32 = 256 // CONDITION_IS_NOT_CASTER
)

// effectTargetAllowed reports whether `target` satisfies effect `eff`'s
// target-condition bitmask, cast by `caster`. Mirrors
// FightTargetValidator.getTargetValidity's OR-of-conditions / AND-of-bits
// evaluation, restricted to the fight-role bits (breed bits are treated as
// "no breed restriction" -- see the const block). An empty Targets list is
// permissive (always true), preserving pre-port behavior for effects/tests
// that carry no conditions.
func (f *Fight) effectTargetAllowed(caster, target *Fighter, targets []int32) bool {
	if len(targets) == 0 {
		return true
	}
	for _, cond := range targets {
		if f.targetConditionPasses(caster, target, cond) {
			return true
		}
	}
	return false
}

// breedConditionMask covers the 12 breed bits (0x10000..0x8000000), the high
// 16 bits of a target condition. A set breed bit requires the target to BE
// that breed (FightTargetValidator.checkBreed: condition>>16 == 1<<(breedId-1)).
const breedConditionMask int32 = 0x0FFF0000

// targetConditionPasses evaluates ONE condition bitmask against a target,
// mirroring the per-bit `if ((BIT & condition) != 0 && !holds) continue;`
// chain in FightTargetValidator.getTargetValidity (a condition passes iff
// every set role-bit holds). CONDITION_IN_AOE (bit 1) is always satisfied
// here because the server only ever tests targets already resolved to be
// inside the effect's area (ResolveTargets), so it never rejects on its own.
// Breed bits ARE enforced (see breedConditionAllows) so a breed-restricted
// spell like Enutrof's Prime of Life ("All Enutrofs") only lands on that breed.
func (f *Fight) targetConditionPasses(caster, target *Fighter, cond int32) bool {
	isSelf := target == caster
	sameTeam := caster != nil && target != nil && caster.TeamID == target.TeamID

	if cond&condIsCaster != 0 && !isSelf {
		return false
	}
	if cond&condIsNotCaster != 0 && isSelf {
		return false
	}
	if cond&condIsAllyNotSelf != 0 {
		if isSelf || caster == nil || !sameTeam {
			return false
		}
	}
	if cond&condIsAlly != 0 {
		if caster == nil || !sameTeam {
			return false
		}
	}
	if cond&condIsEnemy != 0 {
		if caster == nil || sameTeam {
			return false
		}
	}
	if cond&condIsHuman != 0 && target.Father != nil {
		// CONDITION_IS_HUMAN: reference gates on id > 0; summons carry the
		// summoned marker (a Father) and are excluded.
		return false
	}
	if cond&condIsSummoned != 0 && target.Father == nil {
		return false
	}
	if cond&condIsEffectArea != 0 {
		// A resolved fighter is never a ground EffectArea, so a condition
		// requiring one can't be satisfied by a fighter target.
		return false
	}
	if cond&breedConditionMask != 0 && !breedConditionAllows(cond, target) {
		return false
	}
	_ = condInAOE // always satisfied for already-in-area resolved targets
	return true
}

// breedConditionAllows reports whether target satisfies every breed bit set in
// cond, mirroring FightTargetValidator.checkBreed: for each set breed bit,
// (bit >> 16) must equal 1 << (target.Breed - 1). The server's Fighter.Breed
// uses the same 1-indexed breed ids as the reference (Feca=1 .. Pandawa=12), so
// a spell whose condition carries CONDITION_BREED_ENUTROF (262144) only accepts
// an Enutrof target. Multiple breed bits are ANDed (a target must match all),
// matching the reference's nested checkBreed chain (in practice only one bit is
// ever set). A summon has no breed of its own, so any breed-restricted spell
// rejects it.
func breedConditionAllows(cond int32, target *Fighter) bool {
	breedBits := cond & breedConditionMask
	if breedBits == 0 {
		return true
	}
	// A summon has no breed of its own -> it can never satisfy a breed-
	// restricted condition (checkBreed reads the fighter's own breed).
	if target == nil || target.Breed == 0 || target.Father != nil {
		return false
	}
	want := int32(1) << (int32(target.Breed) - 1) // the (>>16)-space bit for this breed
	// Every set breed bit must correspond to this target's breed.
	return (breedBits >> 16) == want
}
