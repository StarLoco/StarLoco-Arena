package combat

import "testing"

func TestEvaluateCastCriteria_EmptyStringAlwaysValid(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	if !f.evaluateCastCriteria("", a) {
		t.Error("empty criterion string should always be valid")
	}
}

func TestEvaluateCastCriteria_UnrecognizedTokenIsPermissive(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	// Mirrors CriteriaCompiler.compile()'s own behavior: an unknown token
	// only logs an error, it never rejects the cast.
	if !f.evaluateCastCriteria("someMadeUpCriterion", a) {
		t.Error("unrecognized criterion token should not reject the cast")
	}
}

func TestEvaluateCastCriteria_CanSummon_AllowsUnderLimit(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Characteristics[NbSummons].Value = 1 // budget = 1+1 = 2 summons allowed

	if !f.evaluateCastCriteria("canSummon", a) {
		t.Error("canSummon should be valid with 0 existing summons and budget 2")
	}
}

func TestEvaluateCastCriteria_CanSummon_RejectsAtLimit(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	summon1 := NewFighterFromBreed(10, a.TeamID, BreedFeca, "S1", 0, 0)
	summon1.Father = a
	f.Timeline = NewTimeline([]*Fighter{a, b, summon1})
	// NbSummons=0 -> budget = 1+0 = 1 summon allowed; already has 1.

	if f.evaluateCastCriteria("canSummon", a) {
		t.Error("canSummon should be rejected once budget (1) is already reached")
	}
}

func TestEvaluateCastCriteria_CanSummon_IgnoresDeadSummons(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	summon1 := NewFighterFromBreed(10, a.TeamID, BreedFeca, "S1", 0, 0)
	summon1.Father = a
	summon1.IsDead = true
	f.Timeline = NewTimeline([]*Fighter{a, b, summon1})

	if !f.evaluateCastCriteria("canSummon", a) {
		t.Error("a dead summon should not count against the living-summon budget")
	}
}

func TestEvaluateCastCriteria_CantCastWhenCarrying_RejectsWhileCarrying(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.CarriedFighter = b

	if f.evaluateCastCriteria("cantCastWhenCarrying", a) {
		t.Error("cantCastWhenCarrying should reject a caster currently carrying someone")
	}
}

func TestEvaluateCastCriteria_CantCastWhenCarrying_AllowsWhenNotCarrying(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	if !f.evaluateCastCriteria("cantCastWhenCarrying", a) {
		t.Error("cantCastWhenCarrying should allow a caster not carrying anyone")
	}
}

func TestEvaluateCastCriteria_CanCastWhenCarrying_RequiresCarrying(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	if f.evaluateCastCriteria("canCastWhenCarrying", a) {
		t.Error("canCastWhenCarrying should reject a caster NOT carrying anyone")
	}
	a.CarriedFighter = b
	if !f.evaluateCastCriteria("canCastWhenCarrying", a) {
		t.Error("canCastWhenCarrying should allow a caster who IS carrying someone")
	}
}

func TestEvaluateCastCriteria_CantCastWhenCarried_RejectsWhileCarried(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.CarriedByFighter = b

	if f.evaluateCastCriteria("cantCastWhenCarried", a) {
		t.Error("cantCastWhenCarried should reject a caster who is currently being carried")
	}
}

func TestEvaluateCastCriteria_MultipleCriteriaAllMustPass(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	// Real spells.dat data has "cantCastWhenCarrying;cantCastWhenCarried"
	// combos -- confirm both must pass.
	if !f.evaluateCastCriteria("cantCastWhenCarrying;cantCastWhenCarried", a) {
		t.Error("combo criterion should pass when neither carrying nor carried")
	}

	a.CarriedByFighter = b
	if f.evaluateCastCriteria("cantCastWhenCarrying;cantCastWhenCarried", a) {
		t.Error("combo criterion should fail when being carried, even if not carrying")
	}
}

func TestEvaluateCastCriteria_CaseInsensitiveAndWhitespaceTolerant(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.CarriedFighter = b
	if f.evaluateCastCriteria(" CANTCASTWHENCARRYING ", a) {
		t.Error("criterion matching should be case-insensitive and whitespace-tolerant")
	}
}
