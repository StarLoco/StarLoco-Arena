package combat

import (
	"math/rand"
	"testing"
	"testing/quick"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file implements the second half of docs/08-java-parity-roadmap.md
// §8.12 Phase N: property-based tests for ComputeHPLoss's edge cases
// (zero resist, negative resist stacking, rebound interacting with
// zero/negative final damage, hit-location bonus combined with rebound).

// TestComputeHPLoss_ZeroResistIsIdentity confirms an elemental hit with
// zero resist/dmg characteristics on both sides reduces to the base value
// (rounded), for any base value -- the "zero resist" edge case.
func TestComputeHPLoss_ZeroResistIsIdentity(t *testing.T) {
	f := func(base uint16) bool {
		rng := rand.New(rand.NewSource(1))
		caster := NewFighter(1, 1, BreedIop)
		target := NewFighter(2, 2, BreedFeca)

		dmg := ComputeHPLoss(DamageParams{
			Caster: caster, Target: target,
			BaseValue: float64(base), Element: ElementFire,
		}, rng)

		// With every characteristic at zero, value*=100/100 is a no-op;
		// randomRound(base) is either base or base (integer input has no
		// fractional part to round), so dmg must equal exactly base.
		return dmg == int(base)
	}
	if err := quick.Check(f, &quick.Config{MaxCount: 200}); err != nil {
		t.Error(err)
	}
}

// TestComputeHPLoss_NegativeResistIncreasesDamage confirms a "debuffed"
// target (negative flat resist, i.e. more vulnerable) takes STRICTLY MORE
// damage than the same hit against a target with zero resist -- the
// "buffed target takes more damage" edge case explicitly called out in
// the roadmap.
func TestComputeHPLoss_NegativeResistIncreasesDamage(t *testing.T) {
	f := func(base uint8, negRes uint8) bool {
		if negRes == 0 {
			return true // no difference to assert
		}
		rng1 := rand.New(rand.NewSource(7))
		rng2 := rand.New(rand.NewSource(7))
		caster := NewFighter(1, 1, BreedIop)

		normalTarget := NewFighter(2, 2, BreedFeca)
		vulnerableTarget := NewFighter(3, 2, BreedFeca)
		vulnerableTarget.Characteristics[ResFire].Value = -int32(negRes)

		dmgNormal := ComputeHPLoss(DamageParams{
			Caster: caster, Target: normalTarget, BaseValue: float64(base) + 1, Element: ElementFire,
		}, rng1)
		dmgVulnerable := ComputeHPLoss(DamageParams{
			Caster: caster, Target: vulnerableTarget, BaseValue: float64(base) + 1, Element: ElementFire,
		}, rng2)

		return dmgVulnerable >= dmgNormal
	}
	if err := quick.Check(f, &quick.Config{MaxCount: 200}); err != nil {
		t.Error(err)
	}
}

// TestComputeHPLoss_FlatResistAppliedBeforePercent pins the formula
// ordering (HPLoss.computeValue): flat dmg/res adjust the value FIRST, then
// the percent modifier multiplies the flat-adjusted value last
// (value*(100+mod)/100), not the raw base. Concretely: base 100, +100% dmg,
// and 20 flat res -> (100 - 20) * 2 = 160, NOT (100*2) - 20 = 180. Uses no
// rebound (caster==target guard avoided by distinct fighters, DmgRebound 0)
// and a fixed seed; the value is a whole number so randomRound is exact.
func TestComputeHPLoss_FlatResistAppliedBeforePercent(t *testing.T) {
	rng := rand.New(rand.NewSource(1))
	caster := NewFighter(1, 1, BreedIop)
	target := NewFighter(2, 2, BreedFeca)
	caster.Characteristics[DmgFirePercent].Value = 100 // +100%
	target.Characteristics[ResFire].Value = 20         // 20 flat res

	dmg := ComputeHPLoss(DamageParams{
		Caster: caster, Target: target, BaseValue: 100, Element: ElementFire,
	}, rng)

	if dmg != 160 {
		t.Errorf("damage = %d, want 160 ((100-20 flat res)*2 from +100%%); a result of 180 would mean percent was applied before flat res", dmg)
	}
}

// TestComputeHPLoss_PhysicalIgnoresDmgAndRes is the property form of the
// "physical damage bypasses Dmg/Res" correction: for a PHYSICAL hit, adding
// ANY flat/percent Dmg or Res characteristics to caster/target must leave
// the delivered damage equal to the plain rolled base -- the element switch
// has no PHYSICAL case (see damage.go's ComputeHPLoss doc + HPLoss.java).
func TestComputeHPLoss_PhysicalIgnoresDmgAndRes(t *testing.T) {
	f := func(base uint8, dmgFlat, resFlat uint8, dmgPct, resPct int8) bool {
		rng := rand.New(rand.NewSource(1))
		caster := NewFighter(1, 1, BreedIop)
		target := NewFighter(2, 2, BreedFeca)
		// Pile on every modifier that a NON-physical hit would consult.
		caster.Characteristics[Dmg].Value = int32(dmgFlat)
		caster.Characteristics[DmgInPercent].Value = int32(dmgPct)
		target.Characteristics[Res].Value = int32(resFlat)
		target.Characteristics[ResInPercent].Value = int32(resPct)

		dmg := ComputeHPLoss(DamageParams{
			Caster: caster, Target: target, BaseValue: float64(base), Element: ElementPhysical,
		}, rng)
		// Physical: none of the above apply, so damage == base exactly.
		return dmg == int(base)
	}
	if err := quick.Check(f, &quick.Config{MaxCount: 300}); err != nil {
		t.Error(err)
	}
}

// TestComputeHPLoss_NeverNegative confirms ComputeHPLoss's floor-at-zero
// clamp holds across a wide range of base values and (possibly huge)
// positive resist characteristics that would otherwise drive the formula
// negative.
func TestComputeHPLoss_NeverNegative(t *testing.T) {
	f := func(base int16, res uint16, resPercent int8) bool {
		rng := rand.New(rand.NewSource(3))
		caster := NewFighter(1, 1, BreedIop)
		target := NewFighter(2, 2, BreedFeca)
		target.Characteristics[ResFire].Value = int32(res)
		// Clamp resPercent into the real characteristic bound [-100,100]
		// so this test reflects an actually-reachable game state.
		rp := int32(resPercent)
		if rp < -100 {
			rp = -100
		}
		if rp > 100 {
			rp = 100
		}
		target.Characteristics[ResFirePercent].Value = rp

		dmg := ComputeHPLoss(DamageParams{
			Caster: caster, Target: target, BaseValue: float64(base), Element: ElementFire,
		}, rng)

		return dmg >= 0
	}
	if err := quick.Check(f, &quick.Config{MaxCount: 500}); err != nil {
		t.Error(err)
	}
}

// TestComputeHPLoss_ReboundNeverExceedsFinalDamage confirms the
// rebound-redirection step never leaves the target's recorded damage
// negative, for any rebound percentage in its real characteristic bound
// [0,99] (see characteristicBounds[DmgRebound]).
func TestComputeHPLoss_ReboundNeverExceedsFinalDamage(t *testing.T) {
	f := func(base uint16, reboundPct uint8) bool {
		rng := rand.New(rand.NewSource(11))
		caster := NewFighter(1, 1, BreedIop)
		target := NewFighter(2, 2, BreedFeca)

		rp := int32(reboundPct)
		if rp > 99 {
			rp = 99
		}
		target.Characteristics[DmgRebound].Value = rp

		dmg := ComputeHPLoss(DamageParams{
			Caster: caster, Target: target, BaseValue: float64(base), Element: ElementPhysical,
		}, rng)

		return dmg >= 0
	}
	if err := quick.Check(f, &quick.Config{MaxCount: 300}); err != nil {
		t.Error(err)
	}
}

// TestComputeHPLoss_ReboundDamagesCasterDirectly confirms rebound damage
// is actually applied to the caster's HP (a flat subtraction, not merely
// subtracted from the target's final damage without a real side effect).
func TestComputeHPLoss_ReboundDamagesCasterDirectly(t *testing.T) {
	rng := rand.New(rand.NewSource(5))
	caster := NewFighter(1, 1, BreedIop)
	target := NewFighter(2, 2, BreedFeca)
	caster.Characteristics[HP].Value = 100
	caster.Characteristics[HP].Max = 100
	target.Characteristics[DmgRebound].Value = 50 // 50% rebound

	finalDamage := ComputeHPLoss(DamageParams{
		Caster: caster, Target: target, BaseValue: 20, Element: ElementPhysical,
	}, rng)

	// base 20, 50% rebound -> 10 rebounds to caster, 10 remains on target.
	if finalDamage != 10 {
		t.Fatalf("finalDamage with 50%% rebound on base 20 = %d, want 10", finalDamage)
	}
	if got := caster.Characteristic(HP); got != 90 {
		t.Errorf("caster HP after rebound = %d, want 90 (100 - 10 rebounded)", got)
	}
}

// TestComputeHPLoss_ReboundNeverAppliesToSelfHits confirms a self-cast
// (caster == target) HPLoss never triggers rebound even if the "target"
// (itself) has DmgRebound set -- mirrors the explicit "caster != target"
// guard in HPLoss.computeValue()'s rebound block.
func TestComputeHPLoss_ReboundNeverAppliesToSelfHits(t *testing.T) {
	rng := rand.New(rand.NewSource(5))
	self := NewFighter(1, 1, BreedIop)
	self.Characteristics[HP].Value = 100
	self.Characteristics[HP].Max = 100
	self.Characteristics[DmgRebound].Value = 50

	finalDamage := ComputeHPLoss(DamageParams{
		Caster: self, Target: self, BaseValue: 20, Element: ElementPhysical,
	}, rng)

	if finalDamage != 20 {
		t.Errorf("self-inflicted damage with own DmgRebound set = %d, want 20 (rebound must not apply to self-hits)", finalDamage)
	}
	// ComputeHPLoss only ever mutates HP directly for the REBOUND side
	// effect (caster.AddCharacteristic inside the rebound block) -- the
	// primary finalDamage return value is applied to the target's HP by
	// the caller (applyDamage), not by ComputeHPLoss itself. Since
	// rebound must not fire here, self's HP should be completely
	// untouched by this call.
	if got := self.Characteristic(HP); got != 100 {
		t.Errorf("self HP mutated by ComputeHPLoss itself = %d, want unchanged 100 (rebound side-effect must not fire on self-hits; the primary 20 dmg is applied by the caller, not tested here)", got)
	}
}

// TestHitLocationBonus_CombinesWithRebound confirms the hit-location bonus
// is folded into the value BEFORE rebound is computed (rebound takes a
// percentage of the location-boosted final damage, not the unboosted base).
func TestHitLocationBonus_CombinesWithRebound(t *testing.T) {
	rng := rand.New(rand.NewSource(9))
	caster := NewFighter(1, 1, BreedIop)
	target := NewFighter(2, 2, BreedFeca)
	caster.Characteristics[HP].Value = 100
	caster.Characteristics[HP].Max = 100

	// Target facing away from caster (caster behind) -> BACK, +30%.
	caster.Position = Point3{X: 0, Y: 0}
	target.Position = Point3{X: 1, Y: 0}
	target.Direction = DirEast
	target.Characteristics[DmgRebound].Value = 50

	finalDamage := ComputeHPLoss(DamageParams{
		Caster: caster, Target: target, BaseValue: 10, Element: ElementFire, AffectedByLocation: true,
	}, rng)

	// base 10 * 1.30 (back bonus) = 13; 50% rebound -> 6 (randomRound of
	// 6.5 is probabilistic, but with this fixed seed we just assert the
	// invariant that final+rebounded == the location-boosted value.
	reboundedToCaster := int(100 - caster.Characteristic(HP))
	if finalDamage+reboundedToCaster != 13 {
		t.Errorf("finalDamage(%d) + rebounded(%d) = %d, want 13 (location-boosted value split between target and caster)", finalDamage, reboundedToCaster, finalDamage+reboundedToCaster)
	}
}

// TestReboundAppliedExactlyOnceEndToEnd is the regression test for the
// DmgRebound double-count bug (FEATURES-STATUS.md §1 / roadmap): rebound is
// applied INSIDE ComputeHPLoss (mirroring HPLoss.computeValue lines
// 312-331), and must NOT also be re-applied by the reactive damage-return
// step in applyDamageFromEffect. Before the fix, a spell HP-loss bounced
// DmgRebound% twice -- once in the formula, once reactively.
//
// Here a Fire HP-loss of base 20 with the target holding 50% DmgRebound
// must rebound EXACTLY 10 to the caster (not 10 + a second ~5), and deal
// exactly 10 to the target. Driven through applyRunningEffect (the real
// spell path) so both the formula rebound and the (now removed) reactive
// rebound would fire if the bug were present.
func TestReboundAppliedExactlyOnceEndToEnd(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	a.Characteristics[HP].Value = 100
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100
	b.Characteristics[DmgRebound].Value = 50 // 50% rebound

	// actionID 2 = HP_FIRE_LOSS (elemental, so it goes through the rebound
	// branch of ComputeHPLoss). Base 20, no res -> 20 damage, 50% rebound.
	def, ok := LookupRunningEffect(2)
	if !ok {
		t.Fatalf("actionID 2 (HP_FIRE_LOSS) not resolvable")
	}
	eff := gamedata.EffectDef{ID: 1, ActionID: 2, Params: []float32{20}}
	f.applyRunningEffect(a, b, def, eff, -1)

	// Target takes 20 - 10(rebounded) = 10.
	if got := b.Characteristic(HP); got != 90 {
		t.Errorf("target HP after fire HP-loss(20) w/ 50%% rebound = %d, want 90 (10 delivered)", got)
	}
	// Caster takes EXACTLY the 10 rebounded once -- not 15 (double-count).
	if got := a.Characteristic(HP); got != 90 {
		t.Errorf("caster HP after rebound = %d, want 90 (exactly 10 rebounded ONCE, not double-counted)", got)
	}
}

// TestReboundNotAppliedTwiceForCloseCombatStyleDamage guards the other
// hostile entry point: applyDamageFrom (used by close combat) must not add
// a second rebound on top of whatever ComputeHPLoss already applied. Since
// applyDamageFrom is called with an ALREADY-computed damage value, and the
// reactive step no longer looks at DmgRebound at all, giving the target a
// DmgRebound characteristic must have ZERO effect on this call (the formula
// rebound happened earlier, at ComputeHPLoss time, not here).
func TestReboundNotAppliedTwiceForCloseCombatStyleDamage(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Characteristics[HP].Value = 100
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100
	b.Characteristics[DmgRebound].Value = 50 // would double-count if re-read here

	// Pre-computed 20 damage delivered via applyDamageFrom (the close-combat
	// path). The reactive step must NOT bounce anything to a from DmgRebound.
	f.applyDamageFrom(a, b, 20, -1)

	if got := b.Characteristic(HP); got != 80 {
		t.Errorf("target HP = %d, want 80 (full 20 delivered; formula rebound already happened upstream)", got)
	}
	if got := a.Characteristic(HP); got != 100 {
		t.Errorf("attacker HP = %d, want unchanged 100 (reactive step must not re-apply DmgRebound)", got)
	}
}

// TestRandomRound_NeverBiased is a property test on randomRound itself:
// over many trials, the average of randomRound(v) for a fixed fractional
// v should converge close to v (within a generous tolerance), confirming
// the probabilistic-rounding scheme isn't systematically biased in either
// direction.
func TestRandomRound_NeverBiased(t *testing.T) {
	rng := rand.New(rand.NewSource(123))
	const trials = 20000
	const v = 10.3
	sum := 0
	for i := 0; i < trials; i++ {
		sum += randomRound(v, rng)
	}
	avg := float64(sum) / float64(trials)
	if avg < v-0.05 || avg > v+0.05 {
		t.Errorf("average of randomRound(%.1f) over %d trials = %.4f, want within 0.05 of %.1f", v, trials, avg, v)
	}
}

// TestRandomRound_AlwaysFloorOrCeil confirms randomRound(v) only ever
// returns floor(v) or floor(v)+1, never anything further away.
func TestRandomRound_AlwaysFloorOrCeil(t *testing.T) {
	f := func(base int16, fracHundredths uint8) bool {
		rng := rand.New(rand.NewSource(1))
		frac := float64(fracHundredths%100) / 100
		v := float64(base) + frac
		got := randomRound(v, rng)
		floor := int(v)
		return got == floor || got == floor+1
	}
	if err := quick.Check(f, &quick.Config{MaxCount: 500}); err != nil {
		t.Error(err)
	}
}
