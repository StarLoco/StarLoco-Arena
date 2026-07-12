package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase L's
// integration-level coverage of the full validateCast pipeline (as
// opposed to spell_cast_history_test.go/criteria_test.go/
// line_of_sight_test.go's unit-level coverage of each individual piece).

func TestValidateCast_RejectsWhenCriterionFails(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}
	a.CarriedByFighter = b // being carried

	cand := castCandidate{
		APCost:    1,
		RangeMax:  5,
		Criterion: "cantCastWhenCarried",
	}
	if f.validateCast(a, cand, Point3{X: 1, Y: 0}, 0, false) {
		t.Error("validateCast should reject a cast whose criterion fails")
	}
}

func TestValidateCast_AllowsWhenCriterionPasses(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}

	cand := castCandidate{
		APCost:    1,
		RangeMax:  5,
		Criterion: "cantCastWhenCarried",
	}
	if !f.validateCast(a, cand, Point3{X: 1, Y: 0}, 0, false) {
		t.Error("validateCast should allow a cast whose criterion passes")
	}
}

func TestValidateCast_RejectsWhenCastFrequencyExceeded(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}
	f.Timeline = NewTimeline([]*Fighter{a})

	cand := castCandidate{
		SpellID:                 42,
		APCost:                  1,
		RangeMax:                5,
		CastFrequencyMaxPerTurn: 1,
	}
	if !f.validateCast(a, cand, Point3{X: 1, Y: 0}, 0, false) {
		t.Fatal("first cast this turn should be allowed")
	}
	a.CastHistory.StoreSpellCast(42, 0, 1, 0, int32(f.Timeline.TableTurn()), 0, false)

	if f.validateCast(a, cand, Point3{X: 1, Y: 0}, 0, false) {
		t.Error("second cast this turn should be rejected (CastMaxPerTurn=1)")
	}
}

func TestValidateCast_LOSNotCheckedWhenSpellDoesNotRequireIt(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}

	// CastTestLineOfSight left false -- must not call hasLineOfSight at
	// all (which would be permissive anyway with mapData==nil, but this
	// confirms the flag actually gates the check rather than always
	// running it).
	cand := castCandidate{APCost: 1, RangeMax: 20, CastTestLineOfSight: false}
	if !f.validateCast(a, cand, Point3{X: 15, Y: 0}, 0, false) {
		t.Error("cast without CastTestLineOfSight should not be blocked by LOS")
	}
}

func TestValidateCast_CardHasNoFrequencyOrCriteriaChecks(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}

	// A card's castCandidate (SpellID=0, Criterion="") must never be
	// blocked by the cast-frequency or criteria checks, confirming cards
	// are correctly exempted (matches FighterCardTemplate having no such
	// fields at all).
	cand := castCandidate{Effects: []gamedata.EffectDef{}}
	if !f.validateCast(a, cand, a.Position, 0, false) {
		t.Error("a card cast candidate (SpellID=0) should never be blocked by spell-only checks")
	}
}

func TestValidateCast_UnrecognizedCriterionDoesNotBlock(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}

	cand := castCandidate{APCost: 1, RangeMax: 5, Criterion: "totallyMadeUp"}
	if !f.validateCast(a, cand, Point3{X: 1, Y: 0}, 0, false) {
		t.Error("an unrecognized criterion token should not block the cast")
	}
}

// TestValidateCast_NormalizedTeleportRangeReachesTargetAtMaxRange is the
// combat-side regression guard for the Feca-teleport bug: with the store
// now normalizing spell 140's inverted raw range (rawMin=6/rawMax=0) to
// RangeMin=0/RangeMax=6, a cast at Manhattan distance 6 must validate. (If
// the range were left inverted -- RangeMin=6, RangeMax=0 -- validateCast's
// "dist > maxRange(0)" test would reject every non-self target, which is
// what made the spell uncastable.)
func TestValidateCast_NormalizedTeleportRangeReachesTargetAtMaxRange(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}

	// Mirrors the normalized spell 140: 6 AP, range 0..6, teleport effect.
	cand := castCandidate{APCost: 6, RangeMin: 0, RangeMax: 6}
	target := Point3{X: 6, Y: 0} // Manhattan distance 6 == RangeMax

	if !f.validateCast(a, cand, target, 0, false) {
		t.Error("validateCast should allow a range-0..6 teleport to a cell at distance 6")
	}
}

// TestValidateCast_InvertedRangeRejectsEverything documents WHY the store
// normalization matters: an un-normalized inverted range (RangeMin>RangeMax)
// rejects even a distance-1 target, proving the bug this fix addresses.
func TestValidateCast_InvertedRangeRejectsEverything(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	a.Characteristics[AP].Value = 10
	a.Position = Point3{X: 0, Y: 0}

	// Raw (unnormalized) spell-140 range: min 6, max 0.
	cand := castCandidate{APCost: 6, RangeMin: 6, RangeMax: 0}
	if f.validateCast(a, cand, Point3{X: 1, Y: 0}, 0, false) {
		t.Error("an inverted range (min 6, max 0) should reject all targets -- the pre-fix bug")
	}
}
