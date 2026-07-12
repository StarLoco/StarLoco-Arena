package combat

import (
	"math"
	"math/rand"
)

// randomRound rounds v to the nearest integer, resolving the fractional
// remainder probabilistically (weighted by the fractional part) rather
// than always rounding the same direction -- this avoids systematic
// rounding bias on repeated small-damage ticks. The reference engine's
// exact ValueRounder.randomRound() implementation was not recoverable
// during the opcode-documentation research pass (see
// docs/opcodes/08-fight-combat-engine.md's "Remaining genuine unknowns");
// this is the standard, most likely-correct implementation of that
// pattern and should be revisited if a byte-exact reference is found
// later.
func randomRound(v float64, rng *rand.Rand) int {
	floor := int(v)
	frac := v - float64(floor)
	if frac <= 0 {
		return floor
	}
	if rng.Float64() < frac {
		return floor + 1
	}
	return floor
}

// DamageParams bundles the inputs to ComputeHPLoss.
type DamageParams struct {
	Caster    *Fighter
	Target    *Fighter
	BaseValue float64 // resolved dice/flat value, before any modifiers
	Element   Element
	// AffectedByLocation mirrors EffectDef.AffectedByLocalisation: whether
	// the front/side/back hit-location bonus applies to this cast. Per
	// the decompiled HPLoss.computeValue(), this only ever has an effect
	// for non-physical elements (see the source's
	// "if (m_staticElement != PHYSICAL && mustBeLocalised)" guard).
	AffectedByLocation bool
}

// ComputeHPLoss ports HPLoss.computeValue() exactly, confirmed against the
// decompiled client source
// (dofusarena/common/game/effect/runningEffect/HPLoss.java): base value,
// then a hit-location percent bonus (non-physical + AffectedByLocation
// only), then per-element flat dmg/res and percent dmg/res modifiers
// (PHYSICAL gets NEITHER -- see the correction note below), percent
// modifiers applied last, then damage-rebound redirection. rng must be
// non-nil (each Fight owns one RNG instance for deterministic-per-fight
// testability).
//
// Correction vs. an earlier version of this file: physical/close-combat
// damage does NOT go through the plain Dmg/Res characteristics. The
// decompiled HPLoss.computeValue()'s element switch has cases for
// FIRE/EARTH("null" -- a decompiler artifact for EARTH)/WATER/WIND only;
// there is no PHYSICAL case, so a physical HPLoss's value/modificator are
// never touched by that switch at all -- physical damage is exactly the
// rolled base value (dice or flat), full stop, before the hit-location
// bonus and rebound. This matches the game manual's own description:
// "some very rare effects are not linked with any element... damage due
// to a collision is damage which doesn't take elements into account."
func ComputeHPLoss(p DamageParams, rng *rand.Rand) int {
	caster, target := p.Caster, p.Target

	value := p.BaseValue
	modPercent := 0

	if p.Element != ElementPhysical {
		if p.AffectedByLocation {
			modPercent += hitLocationBonus(caster, target)
		}
		modPercent += int(caster.Characteristic(DmgInPercent)) + int(caster.Characteristic(p.Element.DmgPercentCharacteristic()))
		modPercent -= int(target.Characteristic(ResInPercent)) + int(target.Characteristic(p.Element.ResPercentCharacteristic()))
		value += float64(caster.Characteristic(Dmg)) + float64(caster.Characteristic(p.Element.DmgCharacteristic()))
		value -= float64(target.Characteristic(p.Element.ResCharacteristic())) + float64(target.Characteristic(Res))
	}

	value = value * float64(100+modPercent) / 100

	finalDamage := randomRound(value, rng)
	if finalDamage < 0 {
		finalDamage = 0
	}

	if reboundPct := target.Characteristic(DmgRebound); reboundPct > 0 && caster != target && finalDamage > 0 {
		rebound := randomRound(float64(finalDamage)*float64(reboundPct)/100, rng)
		if rebound > 0 {
			// Rebound damage is a flat, un-modified HP loss (the reference
			// forces the value and disables re-computation for the rebound
			// hit -- see HPLoss.computeValue()'s rebound block), not a
			// second full damage-formula pass.
			caster.AddCharacteristic(HP, -int32(rebound))
			finalDamage -= rebound
			if finalDamage < 0 {
				finalDamage = 0
			}
		}
	}

	return finalDamage
}

// hitLocationBonus returns the front/side/back percent bonus (+0/+15/+30)
// for an attack from caster against target, based on target's facing
// direction relative to the attacker -- ported from
// FourSidedPartLocalisator.getMainPartInSightFromPosition()'s dot-product
// threshold (>=0.5 back, >=-0.5 side, else front). The exact
// isometric-grid vector semantics of the reference Direction8 enum
// (whose decompiled getVector() values look like a decompiler artifact --
// see docs/08-java-parity-roadmap.md §8.11) aren't reproduced bit-exact;
// this uses this port's own consistent Point3.Step() direction vectors
// instead, which preserves the same gameplay-visible front/side/back
// behavior without depending on real map coordinate data this port
// doesn't have anyway.
func hitLocationBonus(caster, target *Fighter) int {
	if caster.Position == target.Position {
		return 0 // FRONT
	}
	facing := target.Position.Step(target.Direction)
	fx, fy := float64(facing.X-target.Position.X), float64(facing.Y-target.Position.Y)
	tx, ty := float64(target.Position.X-caster.Position.X), float64(target.Position.Y-caster.Position.Y)

	fLen := math.Hypot(fx, fy)
	tLen := math.Hypot(tx, ty)
	if fLen == 0 || tLen == 0 {
		return 0
	}
	dot := (fx*tx + fy*ty) / (fLen * tLen)

	switch {
	case dot >= 0.5:
		return 30 // BACK
	case dot >= -0.5:
		return 15 // SIDE (left or right, same bonus)
	default:
		return 0 // FRONT
	}
}

// ComputeHeal is the HPGain formula mirror: same flat/percent-modifier
// shape as damage but using the Heal characteristic instead of Dmg/Res,
// and no rebound/hit-location concept.
func ComputeHeal(caster *Fighter, baseValue float64, rng *rand.Rand) int {
	modPercent := caster.Characteristic(Heal)
	value := baseValue * float64(100+modPercent) / 100
	v := randomRound(value, rng)
	if v < 0 {
		v = 0
	}
	return v
}

// RollCriticalHit reports whether a critical hit occurs, mirroring
// AbstractFighter.rollCriticalHitTest(): DiceRoll.roll(100) <= CriticalRate.
func RollCriticalHit(f *Fighter, rng *rand.Rand) bool {
	limit := f.Characteristic(CriticalRate)
	if limit <= 0 {
		return false
	}
	return rng.Intn(100)+1 <= int(limit)
}

// RollFumble reports whether a fumble (critical miss) occurs, mirroring
// AbstractFighter.rollCriticalMissTest(): short-circuits false if
// FumbleRate <= 0.
func RollFumble(f *Fighter, rng *rand.Rand) bool {
	limit := f.Characteristic(FumbleRate)
	if limit <= 0 {
		return false
	}
	return rng.Intn(100)+1 <= int(limit)
}
