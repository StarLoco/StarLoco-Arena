package combat

import (
	"math/rand"
	"testing"
)

// TestComputeHPLoss_PhysicalIgnoresDmgResCharacteristics regression-tests
// the confirmed bug found by cross-checking the decompiled
// HPLoss.computeValue(): physical damage's element switch has no PHYSICAL
// case at all, so Dmg/Res/DmgInPercent/ResInPercent characteristics must
// have ZERO effect on physical damage -- only the raw base value (plus
// hit-location, which the source also gates on "element != PHYSICAL").
func TestComputeHPLoss_PhysicalIgnoresDmgResCharacteristics(t *testing.T) {
	rng := rand.New(rand.NewSource(1))
	caster := NewFighter(1, 1, BreedIop)
	target := NewFighter(2, 2, BreedFeca)

	caster.Characteristics[Dmg].Value = 100
	caster.Characteristics[DmgInPercent].Value = 100
	target.Characteristics[Res].Value = 100
	target.Characteristics[ResInPercent].Value = 100

	dmg := ComputeHPLoss(DamageParams{
		Caster:    caster,
		Target:    target,
		BaseValue: 10,
		Element:   ElementPhysical,
	}, rng)

	if dmg != 10 {
		t.Fatalf("physical damage = %d, want exactly the base value 10 (Dmg/Res/percent characteristics must not apply)", dmg)
	}
}

// TestComputeHPLoss_ElementalAppliesFlatAndPercentModifiers is the mirror
// positive case: a non-physical element DOES apply Dmg/Res/percent
// modifiers, per the same source's element switch cases.
func TestComputeHPLoss_ElementalAppliesFlatAndPercentModifiers(t *testing.T) {
	rng := rand.New(rand.NewSource(1))
	caster := NewFighter(1, 1, BreedXelor)
	target := NewFighter(2, 2, BreedFeca)

	caster.Characteristics[Dmg].Value = 5
	caster.Characteristics[DmgFire].Value = 5
	target.Characteristics[Res].Value = 2
	target.Characteristics[ResFire].Value = 3

	// base 10 + (5+5) dmg - (2+3) res = 15; no percent modifiers -> 15.
	dmg := ComputeHPLoss(DamageParams{
		Caster:    caster,
		Target:    target,
		BaseValue: 10,
		Element:   ElementFire,
	}, rng)

	if dmg != 15 {
		t.Fatalf("fire damage = %d, want 15 (10 base + 5 Dmg + 5 DmgFire - 2 Res - 3 ResFire)", dmg)
	}
}

// TestComputeHPLoss_HitLocationOnlyAppliesToNonPhysical verifies the
// hit-location bonus is applied for an elemental hit from behind, and
// confirmed absent for a physical hit in the identical geometry (since
// the source's hit-location branch is nested inside the
// "element != PHYSICAL" guard).
func TestComputeHPLoss_HitLocationOnlyAppliesToNonPhysical(t *testing.T) {
	rng := rand.New(rand.NewSource(1))
	caster := NewFighter(1, 1, BreedXelor)
	target := NewFighter(2, 2, BreedFeca)

	// Target at (1,0) facing East (away from caster at (0,0)): caster is
	// directly behind the target's facing direction -> BACK (+30%).
	caster.Position = Point3{X: 0, Y: 0}
	target.Position = Point3{X: 1, Y: 0}
	target.Direction = DirEast

	elemental := ComputeHPLoss(DamageParams{
		Caster: caster, Target: target, BaseValue: 10, Element: ElementFire, AffectedByLocation: true,
	}, rng)
	if elemental != 13 {
		t.Errorf("elemental back-hit damage = %d, want 13 (10 * 1.30)", elemental)
	}

	physical := ComputeHPLoss(DamageParams{
		Caster: caster, Target: target, BaseValue: 10, Element: ElementPhysical, AffectedByLocation: true,
	}, rng)
	if physical != 10 {
		t.Errorf("physical back-hit damage = %d, want 10 (hit-location bonus must not apply to physical)", physical)
	}
}

func TestHitLocationBonus_FrontSideBack(t *testing.T) {
	caster := NewFighter(1, 1, BreedIop)
	target := NewFighter(2, 2, BreedFeca)
	target.Position = Point3{X: 5, Y: 5}

	// Target facing East, caster directly East of target (in front of
	// target's facing) -> FRONT, no bonus.
	target.Direction = DirEast
	caster.Position = Point3{X: 6, Y: 5}
	if got := hitLocationBonus(caster, target); got != 0 {
		t.Errorf("front hit bonus = %d, want 0", got)
	}

	// Caster directly West of target (opposite target's East facing) ->
	// BACK, +30.
	caster.Position = Point3{X: 4, Y: 5}
	if got := hitLocationBonus(caster, target); got != 30 {
		t.Errorf("back hit bonus = %d, want 30", got)
	}
}

func TestRandomRound_Deterministic(t *testing.T) {
	rng := rand.New(rand.NewSource(42))
	// An integer value should always round to itself regardless of RNG.
	if v := randomRound(5.0, rng); v != 5 {
		t.Errorf("randomRound(5.0) = %d, want 5", v)
	}
}
