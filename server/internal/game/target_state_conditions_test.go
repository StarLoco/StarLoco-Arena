package game

import "testing"

// TestStateTargetConditionsAreEvaluated covers the client's SECOND target
// evaluator (aLc.java:89-131), whose whole bit bank used to be unrepresentable.
//
// SECURITY: an unrepresentable bit makes spellTargetMaskAllows hit its escape
// hatch and skip the ENTIRE mask, so a spell whose mask used any of these was
// cast with no target validation at all. Harmless while no shipped spell relies
// on them, dangerous the moment one does.
func TestStateTargetConditionsAreEvaluated(t *testing.T) {
	newFighter := func() *FightFighter {
		return &FightFighter{HP: 50, MaxHP: 100, AP: 6, MP: 3, TeamID: 1}
	}

	cases := []struct {
		name   string
		cond   int64
		set    func(caster, target *FightFighter)
		reject bool
	}{
		{"intransposable target", condTargetIntransposable,
			func(c, tg *FightFighter) { tg.addState(stateIntransposable, 2) }, true},
		{"intransposable CASTER (bit 49 reads both)", condTargetIntransposable,
			func(c, tg *FightFighter) { c.addState(stateIntransposable, 2) }, true},
		{"stabilized", condTargetStabilized,
			func(c, tg *FightFighter) { tg.addState(stateStabilized, 2) }, true},
		{"anchored", condTargetAnchored,
			func(c, tg *FightFighter) { tg.addState(stateAnchored, 2) }, true},
		{"at full HP", condTargetAtFullHP,
			func(c, tg *FightFighter) { tg.HP = tg.MaxHP }, true},
		{"below full HP is fine", condTargetAtFullHP,
			func(c, tg *FightFighter) { tg.HP = tg.MaxHP - 1 }, false},
		{"no AP", condTargetNoAP,
			func(c, tg *FightFighter) { tg.AP = 0 }, true},
		{"has AP is fine", condTargetNoAP, func(c, tg *FightFighter) {}, false},
		{"no MP", condTargetNoMP,
			func(c, tg *FightFighter) { tg.MP = 0 }, true},
		{"rooted", condTargetRooted,
			func(c, tg *FightFighter) { tg.addState(stateRooted, 2) }, true},
		{"petrified", condTargetPetrified,
			func(c, tg *FightFighter) { tg.addState(statePetrified, 2) }, true},
		{"state absent means no rejection", condTargetPetrified,
			func(c, tg *FightFighter) {}, false},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			caster, target := newFighter(), newFighter()
			tc.set(caster, target)
			if got := stateConditionRejects(caster, target, tc.cond); got != tc.reject {
				t.Errorf("stateConditionRejects = %v, want %v", got, tc.reject)
			}
		})
	}
}

// TestStateBitsAreInsideTheEvaluableSet is the point of the change: these bits
// must no longer trip the escape hatch that skipped the whole mask.
func TestStateBitsAreInsideTheEvaluableSet(t *testing.T) {
	for _, bit := range []int64{
		condTargetIntransposable, condTargetStabilized, condTargetAnchored,
		condTargetAtFullHP, condTargetNoAP, condTargetNoMP,
		condTargetRooted, condTargetPetrified,
	} {
		if bit&^evaluableTargetBits != 0 {
			t.Errorf("bit %#x is still outside evaluableTargetBits, so a mask using "+
				"it skips ALL target validation", bit)
		}
	}
	// And the two banks we still cannot decide must remain OUTSIDE, or we would be
	// silently claiming to enforce something we do not.
	const condDeC int64 = 1 << 55
	const condFireResist int64 = 1 << 58
	for _, bit := range []int64{condDeC, condFireResist} {
		if bit&^evaluableTargetBits == 0 {
			t.Errorf("bit %#x is claimed evaluable but nothing evaluates it", bit)
		}
	}
}

// TestStateConditionIgnoresUnsetBits guards against over-rejection: a condition
// that sets none of these bits must never be refused by this bank.
func TestStateConditionIgnoresUnsetBits(t *testing.T) {
	target := &FightFighter{HP: 100, MaxHP: 100, AP: 0, MP: 0}
	target.addState(statePetrified, 2)
	target.addState(stateRooted, 2)
	if stateConditionRejects(nil, target, condIsEnemy) {
		t.Error("a condition with no state bits rejected a target that happens to " +
			"be petrified, rooted, at full HP and out of AP/MP")
	}
	if stateConditionRejects(nil, nil, condTargetPetrified) {
		t.Error("a nil target must not be rejected by the state bank")
	}
}
