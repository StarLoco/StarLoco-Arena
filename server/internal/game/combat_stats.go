package game

import (
	"fmt"
	"strings"
)

// combat_stats.go models the elemental damage/resistance characteristics that
// turn "flat damage" into the real Dofus damage formula, ported from the proven
// v2.04b resolver (internal/combat/damage.go ⇐ HPLoss.computeValue). Every
// resistance/damage buff (mh_2 ids 21-55, 80-83) that used to render-but-do-
// nothing now feeds these stats, and every elemental hit is boosted by the
// caster's damage stats and reduced by the target's resistance stats.

// Elements. The order matches the 2.70 HP-loss action ids (direct 1-5, "par
// sort" 130-134, leech 6-10): neutral, fire, earth, water, air. Neutral
// ("physical") bypasses ALL damage/resist stats — its hit is the raw rolled
// value (verified in the v2.04b HPLoss PHYSICAL short-circuit).
const (
	elemNeutral = 0
	elemFire    = 1
	elemEarth   = 2
	elemWater   = 3
	elemAir     = 4
	numElements = 5
)

// combatStats is a fighter's elemental damage/resistance profile. Values are
// stored UNCLAMPED (so a timed buff reverts by exact subtraction); the Dofus
// bounds — flat resist ≥ 0, percents in [-100,100] — are applied at read time in
// the damage formula. A breed contributes zero here; everything comes from
// equipped cards (computeFighterStats) and in-fight buffs (applyBuff).
type combatStats struct {
	dmgFlat   [numElements]int32 // caster: flat elemental damage bonus
	dmgPct    [numElements]int32 // caster: elemental damage %
	resFlat   [numElements]int32 // target: flat elemental resistance
	resPct    [numElements]int32 // target: elemental resistance %
	dmgPctAll int32              // caster: all-element damage % (DmgInPercent)
	resPctAll int32              // target: all-element resistance % (ResInPercent)

	// Scalar (non-elemental) combat characteristics, ported from the v2.04b
	// charac table. Stored UNCLAMPED like the elemental stats; the Dofus bounds
	// are applied at read time where each is consumed:
	resAPLoss  int32 // target: flat % resistance to AP loss (action 86, "esquive PA"), read clamped [-100,100]
	resMPLoss  int32 // target: flat % resistance to MP loss (action 87, "esquive PM"), read clamped [-100,100]
	dmgRebound int32 // target: % of a taken elemental hit reflected to the attacker (action 89), read clamped [0,99]
	healPct    int32 // caster: heal-power % scaling every heal it casts (actions 78 up / 79 down), read clamped [-100,100]
}

// damageElement returns the element an HP-loss / leech effect deals, from its
// mh_2 action id. Anything not an elemental HP-loss (poison, %HP, scaled, …)
// reads as neutral, i.e. it bypasses the resist/damage formula (raw value) —
// matching the reference (poison ignores resistance).
func damageElement(actionID int32) int {
	switch actionID {
	// 165-168 are the ZONE-triggered elemental HP losses (mh_2 "Perte de points
	// de vie <élément> triggerée en zone", one aez_1 class per element).
	case 2, 131, 7, 178, 156, 157, 165: // direct/"par sort"/leech/line fire + AP/MP-scaled fire (156/157) + zone fire
		return elemFire
	case 3, 132, 8, 181, 162, 163, 168: // .earth + AP/MP-scaled earth (162/163) + zone earth
		return elemEarth
	case 4, 133, 9, 179, 160, 161, 166: // .water + AP/MP-scaled water (160/161) + zone water
		return elemWater
	case 5, 134, 10, 180, 158, 159, 167: // .air + AP/MP-scaled air (158/159) + zone air
		return elemAir
	default: // 1/130/6 neutral, AP/MP-scaled neutral (151/152), and every non-elemental effect
		return elemNeutral
	}
}

// stat-op kinds for elementalStatOps (elemental) and scalarStatOps (scalar).
const (
	opResFlat = iota
	opResPct
	opDmgFlat
	opDmgPct
	opResPctAll
	opDmgPctAll
	opResAPLoss  // scalar: resAPLoss
	opResMPLoss  // scalar: resMPLoss
	opDmgRebound // scalar: dmgRebound
	opHealPct    // scalar: healPct
)

// elementalStatOp says which combatStats field an mh_2 buff action id mutates.
type elementalStatOp struct {
	kind int
	elem int
	sign int32
}

// elementalStatOps maps every elemental/all-element damage-or-resist buff id to
// its stat mutation. Ranges + element order (fire, earth, water, air; each a
// consecutive +/- pair) are the Ankama scheme confirmed against the v2.04b
// RunningEffectConstants port: 21-28 flat res, 29-36 res %, 40-47 flat dmg,
// 48-55 dmg %, 80/81 all-res %, 82/83 all-dmg %.
var elementalStatOps = func() map[int32]elementalStatOp {
	m := map[int32]elementalStatOp{}
	elems := []int{elemFire, elemEarth, elemWater, elemAir}
	addRange := func(base int32, kind int) {
		for i, e := range elems {
			m[base+int32(i*2)] = elementalStatOp{kind, e, +1}
			m[base+int32(i*2)+1] = elementalStatOp{kind, e, -1}
		}
	}
	addRange(21, opResFlat) // 21-28
	addRange(29, opResPct)  // 29-36
	addRange(40, opDmgFlat) // 40-47
	addRange(48, opDmgPct)  // 48-55
	m[80] = elementalStatOp{opResPctAll, 0, +1}
	m[81] = elementalStatOp{opResPctAll, 0, -1}
	m[82] = elementalStatOp{opDmgPctAll, 0, +1}
	m[83] = elementalStatOp{opDmgPctAll, 0, -1}
	return m
}()

// scalarStatOps maps the non-elemental combat-stat buff action ids to the scalar
// combatStats field they mutate and their sign, ported from the v2.04b
// CharacGain/Loss table: 86/87 grant AP/MP-loss resistance, 89 grants damage
// rebound, 78/79 raise/lower heal power.
var scalarStatOps = map[int32]struct {
	kind int
	sign int32
}{
	86: {opResAPLoss, +1},  // Résistance à la perte de PA
	87: {opResMPLoss, +1},  // Résistance à la perte de PM
	89: {opDmgRebound, +1}, // Renvoie les dégâts (dommages renvoyés %)
	78: {opHealPct, +1},    // Augmente les soins (heal power)
	79: {opHealPct, -1},    // Diminue les soins
}

// isElementalStatBuff reports whether an action id buffs an elemental
// damage/resist stat (so applyBuff resolves it mechanically, not render-only).
func isElementalStatBuff(actionID int32) bool {
	_, ok := elementalStatOps[actionID]
	return ok
}

// isScalarStatBuff reports whether an action id buffs a scalar combat stat
// (AP/MP-loss resist, damage rebound, heal power).
func isScalarStatBuff(actionID int32) bool {
	_, ok := scalarStatOps[actionID]
	return ok
}

// isStatBuff reports whether an action id feeds a modelled combat stat (elemental
// or scalar) — i.e. applyBuff/computeFighterStats resolve it mechanically via
// combatStats.apply rather than rendering it icon-only.
func isStatBuff(actionID int32) bool {
	return isElementalStatBuff(actionID) || isScalarStatBuff(actionID)
}

// apply mutates the stat named by a combat-stat buff action id by sign*v (pure
// add, no clamp — bounds are applied at read time). A no-op for an unmapped id.
func (s *combatStats) apply(actionID, v int32) {
	if op, ok := elementalStatOps[actionID]; ok {
		d := op.sign * v
		switch op.kind {
		case opResFlat:
			s.resFlat[op.elem] += d
		case opResPct:
			s.resPct[op.elem] += d
		case opDmgFlat:
			s.dmgFlat[op.elem] += d
		case opDmgPct:
			s.dmgPct[op.elem] += d
		case opResPctAll:
			s.resPctAll += d
		case opDmgPctAll:
			s.dmgPctAll += d
		}
		return
	}
	if op, ok := scalarStatOps[actionID]; ok {
		d := op.sign * v
		switch op.kind {
		case opResAPLoss:
			s.resAPLoss += d
		case opResMPLoss:
			s.resMPLoss += d
		case opDmgRebound:
			s.dmgRebound += d
		case opHealPct:
			s.healPct += d
		}
	}
}

// applyLossResist reduces an AP/MP-loss magnitude by the target's flat percent
// resistance, a direct port of the v2.04b applyResistance: removed = v -
// trunc(v*resist/100), floored at 0 (100% resist ⇒ immune). The resist is clamped
// to the Dofus [-100,100] characteristic bound first; a negative resist amplifies
// the loss (no upper clamp), matching the reference.
func applyLossResist(v, resist int32) int32 {
	resist = clampPct(resist)
	reduced := v - v*resist/100
	if reduced < 0 {
		return 0
	}
	return reduced
}

// computeElementalDamage applies the Dofus damage formula: flat caster damage +
// and flat target resist − adjust the value; the caster's damage % and the
// target's resist % (element-specific + all-element) sum into a single percent
// modifier applied LAST; the result floors at 0. Neutral/physical bypasses the
// whole thing (raw value). Deterministic integer rounding (truncation) — the
// client renders the server's value verbatim, so the exact integer is what
// matters, not Ankama's probabilistic round.
func (f *Fight) computeElementalDamage(caster, target *FightFighter, base int32, elem int) int32 {
	if base <= 0 {
		return 0
	}
	if elem == elemNeutral || caster == nil || target == nil {
		return base // neutral bypasses all damage/resist stats
	}
	// Flat first: caster's flat elemental damage adds, target's flat elemental
	// resistance (floored at 0 — no flat vulnerability) subtracts.
	value := base + caster.Stats.dmgFlat[elem] - maxI32(0, target.Stats.resFlat[elem])

	// Percent modifier LAST: caster damage % (element + all) minus target resist %
	// (element + all), each stat clamped to the Dofus ±100 bound.
	modPct := clampPct(caster.Stats.dmgPctAll) + clampPct(caster.Stats.dmgPct[elem]) -
		clampPct(target.Stats.resPctAll) - clampPct(target.Stats.resPct[elem])

	v := int64(value) * int64(100+modPct) / 100
	if v < 0 {
		return 0
	}
	return int32(v)
}

// summary renders the non-zero elemental stats compactly for the dev /fight dump
// (empty when the fighter has no damage/resist profile). Elements: f/e/w/a.
func (s combatStats) summary() string {
	elems := [numElements]string{"n", "f", "e", "w", "a"}
	var parts []string
	for i := elemFire; i < numElements; i++ {
		if s.resFlat[i] != 0 || s.resPct[i] != 0 {
			parts = append(parts, fmt.Sprintf("%sRes%d/%d%%", elems[i], s.resFlat[i], s.resPct[i]))
		}
		if s.dmgFlat[i] != 0 || s.dmgPct[i] != 0 {
			parts = append(parts, fmt.Sprintf("%sDmg%d/%d%%", elems[i], s.dmgFlat[i], s.dmgPct[i]))
		}
	}
	if s.resPctAll != 0 {
		parts = append(parts, fmt.Sprintf("allRes%d%%", s.resPctAll))
	}
	if s.dmgPctAll != 0 {
		parts = append(parts, fmt.Sprintf("allDmg%d%%", s.dmgPctAll))
	}
	if s.resAPLoss != 0 || s.resMPLoss != 0 {
		parts = append(parts, fmt.Sprintf("resLoss%d/%d%%", s.resAPLoss, s.resMPLoss))
	}
	if s.dmgRebound != 0 {
		parts = append(parts, fmt.Sprintf("rebound%d%%", s.dmgRebound))
	}
	if s.healPct != 0 {
		parts = append(parts, fmt.Sprintf("heal%d%%", s.healPct))
	}
	if len(parts) == 0 {
		return ""
	}
	return "stats[" + strings.Join(parts, " ") + "]"
}

// clampPct bounds a percentage characteristic to the Dofus [-100, 100] range.
func clampPct(v int32) int32 {
	if v > 100 {
		return 100
	}
	if v < -100 {
		return -100
	}
	return v
}

func maxI32(a, b int32) int32 {
	if a > b {
		return a
	}
	return b
}

func minI32(a, b int32) int32 {
	if a < b {
		return a
	}
	return b
}

// directElementActionID returns the direct-elemental HP-loss action id (1-5) the
// client renders for an element, used to broadcast per-victim damage from a
// server-computed area effect (line/zone) as its primitive, not the meta id.
func directElementActionID(elem int) int32 {
	switch elem {
	case elemFire:
		return 2
	case elemEarth:
		return 3
	case elemWater:
		return 4
	case elemAir:
		return 5
	default:
		return 1 // neutral
	}
}
