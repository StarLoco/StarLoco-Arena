package combat

import "testing"

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase L's
// required tests: casting the same spell more than CastMaxPerTurn times
// in one turn is rejected; re-casting inside MinCastInterval table-turns
// is rejected. Plus additional coverage for CastMaxPerTarget and
// OnNewTurn's reset behavior, mirroring SpellCastHistory.java exactly.

func TestSpellCastHistory_CastMaxPerTurn_RejectsExcessCasts(t *testing.T) {
	var h SpellCastHistory
	const spellID = 100
	const maxPerTurn = 2

	// First 2 casts should be allowed (checked before storing, matching
	// the real validate-then-store call order in handleSpellCast).
	if v := h.CanCastSpell(spellID, 0, maxPerTurn, 0, 1, 0, false); v != SpellCastValidityOK {
		t.Fatalf("cast 1 = %v, want OK", v)
	}
	h.StoreSpellCast(spellID, 0, maxPerTurn, 0, 1, 0, false)

	if v := h.CanCastSpell(spellID, 0, maxPerTurn, 0, 1, 0, false); v != SpellCastValidityOK {
		t.Fatalf("cast 2 = %v, want OK", v)
	}
	h.StoreSpellCast(spellID, 0, maxPerTurn, 0, 1, 0, false)

	// Third cast this turn must be rejected.
	if v := h.CanCastSpell(spellID, 0, maxPerTurn, 0, 1, 0, false); v != SpellCastValidityTooManyCastsThisTurn {
		t.Errorf("cast 3 = %v, want TooManyCastsThisTurn", v)
	}
}

func TestSpellCastHistory_OnNewTurn_ResetsPerTurnCounter(t *testing.T) {
	var h SpellCastHistory
	const spellID = 100
	const maxPerTurn = 1

	h.StoreSpellCast(spellID, 0, maxPerTurn, 0, 1, 0, false)
	if v := h.CanCastSpell(spellID, 0, maxPerTurn, 0, 1, 0, false); v != SpellCastValidityTooManyCastsThisTurn {
		t.Fatalf("cast within same turn after limit = %v, want rejected", v)
	}

	h.OnNewTurn()
	if v := h.CanCastSpell(spellID, 0, maxPerTurn, 0, 1, 0, false); v != SpellCastValidityOK {
		t.Errorf("cast after OnNewTurn reset = %v, want OK", v)
	}
}

func TestSpellCastHistory_MinCastInterval_RejectsTooSoonRecast(t *testing.T) {
	var h SpellCastHistory
	const spellID = 200
	const minInterval = 3

	h.StoreSpellCast(spellID, minInterval, 0, 0, 1, 0, false) // cast at table-turn 1

	// Table-turn 2, 3: interval not yet elapsed (need >= 3 turns gap).
	if v := h.CanCastSpell(spellID, minInterval, 0, 0, 2, 0, false); v != SpellCastValidityLastCastTooRecent {
		t.Errorf("recast at turn 2 (gap=1) = %v, want LastCastTooRecent", v)
	}
	if v := h.CanCastSpell(spellID, minInterval, 0, 0, 3, 0, false); v != SpellCastValidityLastCastTooRecent {
		t.Errorf("recast at turn 3 (gap=2) = %v, want LastCastTooRecent", v)
	}
	// Table-turn 4: gap=3, meets the interval.
	if v := h.CanCastSpell(spellID, minInterval, 0, 0, 4, 0, false); v != SpellCastValidityOK {
		t.Errorf("recast at turn 4 (gap=3) = %v, want OK", v)
	}
}

func TestSpellCastHistory_MinCastInterval63MeansNeverAgain(t *testing.T) {
	var h SpellCastHistory
	const spellID = 300

	h.StoreSpellCast(spellID, 63, 0, 0, 1, 0, false)

	// Even a huge table-turn gap must never allow a recast when
	// minCastInterval==63 (mirrors SpellCastHistory.canCastSpell's
	// explicit `== 63` special case, checked BEFORE the generic interval
	// comparison).
	if v := h.CanCastSpell(spellID, 63, 0, 0, 100000, 0, false); v != SpellCastValidityLastCastTooRecent {
		t.Errorf("recast with MinCastInterval=63 after huge gap = %v, want still rejected (never again)", v)
	}
}

func TestSpellCastHistory_MinCastInterval_NotAppliedOnFirstCast(t *testing.T) {
	var h SpellCastHistory
	const spellID = 400
	const minInterval = 5

	// No prior cast recorded -- must be allowed regardless of interval.
	if v := h.CanCastSpell(spellID, minInterval, 0, 0, 1, 0, false); v != SpellCastValidityOK {
		t.Errorf("first-ever cast with MinCastInterval set = %v, want OK", v)
	}
}

func TestSpellCastHistory_CastMaxPerTarget_TracksPerTargetIndependently(t *testing.T) {
	var h SpellCastHistory
	const spellID = 500
	const maxPerTarget = 1
	const targetA, targetB int64 = 1, 2

	h.StoreSpellCast(spellID, 0, 0, maxPerTarget, 1, targetA, true)

	// Target A is now at the limit, but target B is untouched.
	if v := h.CanCastSpell(spellID, 0, 0, maxPerTarget, 1, targetA, true); v != SpellCastValidityTooManyCastsOnThisTarget {
		t.Errorf("recast on targetA = %v, want TooManyCastsOnThisTarget", v)
	}
	if v := h.CanCastSpell(spellID, 0, 0, maxPerTarget, 1, targetB, true); v != SpellCastValidityOK {
		t.Errorf("first cast on targetB = %v, want OK (independent from targetA)", v)
	}
}

func TestSpellCastHistory_CastMaxPerTarget_SkippedWhenNoTarget(t *testing.T) {
	var h SpellCastHistory
	const spellID = 600
	const maxPerTarget = 1

	h.StoreSpellCast(spellID, 0, 0, maxPerTarget, 1, 0, false) // no target recorded (hasTarget=false)

	// A subsequent no-target cast must not be blocked by the per-target
	// check (mirrors the reference's `target != null` guard).
	if v := h.CanCastSpell(spellID, 0, 0, maxPerTarget, 1, 0, false); v != SpellCastValidityOK {
		t.Errorf("no-target cast after a no-target cast = %v, want OK", v)
	}
}

func TestSpellCastHistory_ZeroLimitsAreUnconstrained(t *testing.T) {
	var h SpellCastHistory
	const spellID = 700

	for i := 0; i < 100; i++ {
		if v := h.CanCastSpell(spellID, 0, 0, 0, int32(i), 0, false); v != SpellCastValidityOK {
			t.Fatalf("cast %d with all-zero limits = %v, want always OK", i, v)
		}
		h.StoreSpellCast(spellID, 0, 0, 0, int32(i), 0, false)
	}
}
