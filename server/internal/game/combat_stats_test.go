package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func TestDamageElement(t *testing.T) {
	cases := map[int32]int{
		1: elemNeutral, 130: elemNeutral, 6: elemNeutral,
		2: elemFire, 131: elemFire, 7: elemFire,
		3: elemEarth, 132: elemEarth, 8: elemEarth,
		4: elemWater, 133: elemWater, 9: elemWater,
		5: elemAir, 134: elemAir, 10: elemAir,
		61: elemNeutral, // poison bypasses resist (reads as neutral)
	}
	for id, want := range cases {
		if got := damageElement(id); got != want {
			t.Errorf("damageElement(%d)=%d want %d", id, got, want)
		}
	}
}

func TestElementalStatOpsApplyRevert(t *testing.T) {
	var s combatStats
	s.apply(21, 30) // +fire flat res
	s.apply(23, 10) // +earth flat res
	s.apply(48, 25) // +fire dmg %
	s.apply(82, 15) // +all dmg %
	s.apply(81, 20) // -all res %
	if s.resFlat[elemFire] != 30 || s.resFlat[elemEarth] != 10 {
		t.Errorf("flat res fire/earth = %d/%d want 30/10", s.resFlat[elemFire], s.resFlat[elemEarth])
	}
	if s.dmgPct[elemFire] != 25 || s.dmgPctAll != 15 || s.resPctAll != -20 {
		t.Errorf("dmgPct fire=%d dmgPctAll=%d resPctAll=%d want 25/15/-20", s.dmgPct[elemFire], s.dmgPctAll, s.resPctAll)
	}
	// Exact revert (the buff-expiry path).
	s.apply(21, -30)
	s.apply(82, -15)
	if s.resFlat[elemFire] != 0 || s.dmgPctAll != 0 {
		t.Errorf("revert: resFlat fire=%d dmgPctAll=%d want 0/0", s.resFlat[elemFire], s.dmgPctAll)
	}
}

func TestComputeElementalDamage(t *testing.T) {
	f := &Fight{}
	mk := func() (*FightFighter, *FightFighter) { return &FightFighter{}, &FightFighter{} }

	// Neutral bypasses all resist (raw base) even with big resist on the target.
	c, tg := mk()
	tg.Stats.resFlat[elemFire] = 50
	if got := f.computeElementalDamage(c, tg, 30, elemNeutral); got != 30 {
		t.Errorf("neutral bypass = %d want 30", got)
	}
	// Flat elemental resistance subtracts from the value.
	c, tg = mk()
	tg.Stats.resFlat[elemFire] = 10
	if got := f.computeElementalDamage(c, tg, 30, elemFire); got != 20 {
		t.Errorf("flat res = %d want 20", got)
	}
	// Percent elemental resistance (50%).
	c, tg = mk()
	tg.Stats.resPct[elemFire] = 50
	if got := f.computeElementalDamage(c, tg, 30, elemFire); got != 15 {
		t.Errorf("pct res = %d want 15", got)
	}
	// Flat is applied BEFORE percent: (100-20) * 2 = 160 (not 180).
	c, tg = mk()
	c.Stats.dmgPctAll = 100
	tg.Stats.resFlat[elemFire] = 20
	if got := f.computeElementalDamage(c, tg, 100, elemFire); got != 160 {
		t.Errorf("flat-before-pct = %d want 160", got)
	}
	// Caster flat + percent element damage: (20+5) * 1.5 = 37 (truncated).
	c, tg = mk()
	c.Stats.dmgFlat[elemFire] = 5
	c.Stats.dmgPct[elemFire] = 50
	if got := f.computeElementalDamage(c, tg, 20, elemFire); got != 37 {
		t.Errorf("caster dmg = %d want 37", got)
	}
	// Fully resisted → 0 (floor, no negative "healing").
	c, tg = mk()
	tg.Stats.resFlat[elemFire] = 100
	if got := f.computeElementalDamage(c, tg, 30, elemFire); got != 0 {
		t.Errorf("fully resisted = %d want 0", got)
	}
	// Flat resistance can't go negative (a debuffed resFlat floors at 0, no bonus damage).
	c, tg = mk()
	tg.Stats.resFlat[elemFire] = -50
	if got := f.computeElementalDamage(c, tg, 30, elemFire); got != 30 {
		t.Errorf("negative flat res floored = %d want 30", got)
	}
	// Percent resistance clamps at 100 (200% → treated as 100% → 0 damage).
	c, tg = mk()
	tg.Stats.resPct[elemFire] = 200
	if got := f.computeElementalDamage(c, tg, 30, elemFire); got != 0 {
		t.Errorf("pct res clamp = %d want 0", got)
	}
}

// TestDamageWithResistBuff: a fire-resist buff makes a subsequent fire hit deal
// less — the end-to-end path (buff → stat → formula) that used to be inert.
func TestDamageWithResistBuff(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 5}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	target := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 5}, HP: 70, MaxHP: 70}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{target}},
	}}
	f.setPhase(PhaseAction)

	// Baseline: a raw fire hit (action 2, 30) with no resist removes 30.
	f.applyDamageEffect(caster, gamedata.Effect{ActionID: 2, Params: []float32{30}}, target.Pos, false)
	if target.HP != 40 {
		t.Fatalf("baseline fire hit: HP=%d want 40", target.HP)
	}

	// +20 flat fire resist (action 21, infinite) → next identical hit removes 10.
	f.resolveEffect(caster, gamedata.Effect{ActionID: 21, Params: []float32{20}, Duration: []int32{63, 0}}, target.Pos)
	if target.Stats.resFlat[elemFire] != 20 {
		t.Fatalf("resist buff not applied: resFlat fire=%d want 20", target.Stats.resFlat[elemFire])
	}
	f.applyDamageEffect(caster, gamedata.Effect{ActionID: 2, Params: []float32{30}}, target.Pos, false)
	if target.HP != 30 {
		t.Errorf("fire hit after +20 fire resist: HP=%d want 30 (took 10, not 30)", target.HP)
	}

	// An EARTH hit ignores the fire resist (still full 30).
	f.applyDamageEffect(caster, gamedata.Effect{ActionID: 3, Params: []float32{30}}, target.Pos, false)
	if target.HP != 0 {
		t.Errorf("earth hit should ignore fire resist: HP=%d want 0", target.HP)
	}
}
