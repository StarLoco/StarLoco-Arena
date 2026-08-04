package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// twoFighters builds a caster (team 0) and a living enemy victim (team 1) on the
// walkable practice row y=15, with generous resources for the drain/heal tests.
func twoFighters() (*Fight, *FightFighter, *FightFighter) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 6, MaxMP: 6}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 70, MaxHP: 100, AP: 6, MaxAP: 6, MP: 6, MaxMP: 6}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	return f, caster, victim
}

// TestAPMPLossResist covers the flat AP/MP-loss resistance (ResAPLoss/ResMPLoss,
// actions 86/87): a plain loss resists the full rolled value; a steal caps at the
// current resource then resists and grants the caster the resisted amount; 100%
// resist is immunity; a negative resist amplifies; and the 86 buff applies +
// reverts through combatStats.
func TestAPMPLossResist(t *testing.T) {
	loss := func(a int32, v float32) gamedata.Effect { return gamedata.Effect{ActionID: a, Params: []float32{v}} }

	// 50% AP-loss resist halves a 4-AP drain to 2 (6 -> 4).
	f, caster, victim := twoFighters()
	victim.Stats.resAPLoss = 50
	f.resolveEffect(caster, loss(16, 4), victim.Pos)
	if victim.AP != 4 {
		t.Errorf("AP loss w/ 50%% resist: AP=%d want 4", victim.AP)
	}

	// 100% MP-loss resist = immune (MP untouched).
	f, caster, victim = twoFighters()
	victim.Stats.resMPLoss = 100
	f.resolveEffect(caster, loss(20, 3), victim.Pos)
	if victim.MP != 6 {
		t.Errorf("MP loss w/ 100%% resist: MP=%d want 6 (immune)", victim.MP)
	}

	// AP steal w/ 50% resist: cap at current (6) then resist (4 -> 2); the caster
	// gains exactly the resisted amount.
	f, caster, victim = twoFighters()
	victim.Stats.resAPLoss = 50
	f.resolveEffect(caster, loss(85, 4), victim.Pos)
	if victim.AP != 4 || caster.AP != 8 {
		t.Errorf("AP steal w/ 50%% resist: victim=%d caster=%d want 4/8", victim.AP, caster.AP)
	}

	// A negative resist amplifies the loss (no upper clamp): 2 -> 3.
	f, caster, victim = twoFighters()
	victim.Stats.resAPLoss = -50
	f.resolveEffect(caster, loss(16, 2), victim.Pos)
	if victim.AP != 3 {
		t.Errorf("AP loss w/ -50%% resist: AP=%d want 3", victim.AP)
	}

	// Action 86 buff grants ResAPLoss (tracked as a stat buff), reverted on expiry.
	f, caster, victim = twoFighters()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 86, Params: []float32{40}, Duration: []int32{2, 0}}, victim.Pos)
	if victim.Stats.resAPLoss != 40 || len(victim.Buffs) != 1 || !victim.Buffs[0].statBuff {
		t.Fatalf("action 86 buff: resAPLoss=%d buffs=%d", victim.Stats.resAPLoss, len(victim.Buffs))
	}
	f.tickBuffs()
	f.tickBuffs()
	if victim.Stats.resAPLoss != 0 || len(victim.Buffs) != 0 {
		t.Errorf("action 86 revert: resAPLoss=%d buffs=%d want 0/0", victim.Stats.resAPLoss, len(victim.Buffs))
	}
}

// TestApplyLossResistFormula pins the exact integer rounding of the v2.04b
// applyResistance port: removed = v - trunc(v*resist/100), floored at 0.
func TestApplyLossResistFormula(t *testing.T) {
	cases := []struct{ v, resist, want int32 }{
		{5, 30, 4},    // 5 - trunc(1.5)=1 -> 4 (the reference's worked example)
		{4, 50, 2},    // exact half
		{3, 100, 0},   // immune
		{2, -50, 3},   // negative amplifies
		{10, 33, 7},   // 10 - trunc(3.3)=3 -> 7
		{7, 150, 0},   // resist clamped to 100 -> immune
		{7, -150, 14}, // resist clamped to -100 -> doubles
	}
	for _, c := range cases {
		if got := applyLossResist(c.v, c.resist); got != c.want {
			t.Errorf("applyLossResist(%d,%d)=%d want %d", c.v, c.resist, got, c.want)
		}
	}
}

// TestHealPower covers heal scaling by the caster's heal power (actions 78 up /
// 79 down): healed = base*(100+healPct)/100, clamped, buff applied + reverted.
func TestHealPower(t *testing.T) {
	heal := gamedata.Effect{ActionID: 69, Params: []float32{20}}

	// +50% heal power: 20 -> 30.
	f, caster, victim := twoFighters()
	victim.HP = 10
	caster.Stats.healPct = 50
	f.resolveEffect(caster, heal, victim.Pos)
	if victim.HP != 40 {
		t.Errorf("heal +50%%: HP=%d want 40 (10+30)", victim.HP)
	}

	// -50% heal power: 20 -> 10.
	f, caster, victim = twoFighters()
	victim.HP = 10
	caster.Stats.healPct = -50
	f.resolveEffect(caster, heal, victim.Pos)
	if victim.HP != 20 {
		t.Errorf("heal -50%%: HP=%d want 20 (10+10)", victim.HP)
	}

	// No heal power = raw base.
	f, caster, victim = twoFighters()
	victim.HP = 10
	f.resolveEffect(caster, heal, victim.Pos)
	if victim.HP != 30 {
		t.Errorf("heal 0%%: HP=%d want 30", victim.HP)
	}

	// Action 78 buff raises heal power and reverts on expiry; 79 lowers it.
	f, caster, victim = twoFighters()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 78, Params: []float32{30}, Duration: []int32{1, 0}}, caster.Pos)
	if caster.Stats.healPct != 30 {
		t.Fatalf("action 78 buff: healPct=%d want 30", caster.Stats.healPct)
	}
	f.tickBuffs()
	if caster.Stats.healPct != 0 {
		t.Errorf("action 78 revert: healPct=%d want 0", caster.Stats.healPct)
	}
	f.resolveEffect(caster, gamedata.Effect{ActionID: 79, Params: []float32{20}, Duration: []int32{1, 0}}, caster.Pos)
	if caster.Stats.healPct != -20 {
		t.Errorf("action 79 buff: healPct=%d want -20", caster.Stats.healPct)
	}
}

// TestDamageRebound covers reflection (action 89): a share of a mitigated hit is
// dealt to the attacker and subtracted from the victim's damage; self-hits and a
// zero stat are no-ops; a lethal rebound kills the attacker; the 89 buff feeds it.
func TestDamageRebound(t *testing.T) {
	hit := gamedata.Effect{ActionID: 1, Params: []float32{20}} // neutral flat 20

	// 50% rebound: victim takes 10, attacker loses 10.
	f, caster, victim := twoFighters()
	victim.Stats.dmgRebound = 50
	f.resolveEffect(caster, hit, victim.Pos)
	if victim.HP != 60 || caster.HP != 60 {
		t.Errorf("rebound 50%%: victim=%d caster=%d want 60/60", victim.HP, caster.HP)
	}

	// No rebound stat: full damage, attacker untouched.
	f, caster, victim = twoFighters()
	f.resolveEffect(caster, hit, victim.Pos)
	if victim.HP != 50 || caster.HP != 70 {
		t.Errorf("no rebound: victim=%d caster=%d want 50/70", victim.HP, caster.HP)
	}

	// Self-hit never rebounds (caster == victim guard).
	f, caster, _ = twoFighters()
	caster.Stats.dmgRebound = 50
	f.resolveEffect(caster, hit, caster.Pos)
	if caster.HP != 50 {
		t.Errorf("self-hit rebound: HP=%d want 50 (no reflect)", caster.HP)
	}

	// Rebound clamped to 99% and lethal to the attacker: hit 100, rebound 99 (the
	// attacker at 40 HP dies), the victim takes the remaining 1 (70 -> 69).
	f, caster, victim = twoFighters()
	caster.HP = 40
	victim.Stats.dmgRebound = 200 // clamps to 99
	f.resolveEffect(caster, gamedata.Effect{ActionID: 1, Params: []float32{100}}, victim.Pos)
	if caster.HP != 0 {
		t.Errorf("lethal rebound: attacker HP=%d want 0", caster.HP)
	}
	if victim.HP != 69 {
		t.Errorf("lethal rebound: victim HP=%d want 69 (took 100-99=1)", victim.HP)
	}

	// Action 89 buff grants the rebound stat mechanically.
	f, caster, victim = twoFighters()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 89, Params: []float32{25}, Duration: []int32{2, 0}}, victim.Pos)
	if victim.Stats.dmgRebound != 25 || len(victim.Buffs) != 1 || !victim.Buffs[0].statBuff {
		t.Errorf("action 89 buff: dmgRebound=%d buffs=%d", victim.Stats.dmgRebound, len(victim.Buffs))
	}
}

// TestScaledDamageElement verifies AP/MP-scaled damage (151/152 neutral,
// 156-163 elemental) is dealt as its element through the resist formula (not raw
// neutral) and feeds the rebound step.
func TestScaledDamageElement(t *testing.T) {
	// Element mapping for the AP/MP-scaled action ids.
	for id, want := range map[int32]int{
		151: elemNeutral, 156: elemFire, 158: elemAir, 160: elemWater, 162: elemEarth,
		152: elemNeutral, 157: elemFire, 159: elemAir, 161: elemWater, 163: elemEarth,
	} {
		if got := damageElement(id); got != want {
			t.Errorf("damageElement(%d)=%d want %d", id, got, want)
		}
	}

	scaled := func(a int32, perPoint float32) gamedata.Effect {
		return gamedata.Effect{ActionID: a, Params: []float32{perPoint}}
	}

	// AP-scaled neutral (151): 3/AP × 6 AP = 18 neutral (no resist).
	f, caster, victim := twoFighters()
	f.resolveEffect(caster, scaled(151, 3), victim.Pos)
	if victim.HP != 52 {
		t.Errorf("scaled AP neutral: HP=%d want 52 (70-18)", victim.HP)
	}

	// AP-scaled fire (156): 2×6 = 12 fire, victim 50% fire resist → 6.
	f, caster, victim = twoFighters()
	victim.Stats.resPct[elemFire] = 50
	f.resolveEffect(caster, scaled(156, 2), victim.Pos)
	if victim.HP != 64 {
		t.Errorf("scaled AP fire w/ 50%% res: HP=%d want 64 (70-6)", victim.HP)
	}

	// MP-scaled earth (163): 2×6 MP = 12 earth, victim flat earth res 2 → 10.
	f, caster, victim = twoFighters()
	victim.Stats.resFlat[elemEarth] = 2
	f.resolveEffect(caster, scaled(163, 2), victim.Pos)
	if victim.HP != 60 {
		t.Errorf("scaled MP earth w/ flat res 2: HP=%d want 60 (70-10)", victim.HP)
	}

	// Scaled damage feeds rebound: neutral 151, 2×6 = 12, victim 50% rebound → 6 each.
	f, caster, victim = twoFighters()
	victim.Stats.dmgRebound = 50
	f.resolveEffect(caster, scaled(151, 2), victim.Pos)
	if victim.HP != 64 || caster.HP != 64 {
		t.Errorf("scaled + rebound: victim=%d caster=%d want 64/64", victim.HP, caster.HP)
	}
}

// TestPullCollisionDamagesBlocker verifies a fighter pulled into another fighter
// deals the same collision damage to that blocker (the reference damages the
// obstacle on push AND pull).
func TestPullCollisionDamagesBlocker(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 6, MaxMP: 6}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 10, Y: 15}, HP: 60, MaxHP: 60, AP: 6, MaxAP: 6, MP: 6, MaxMP: 6}
	blocker := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60, AP: 6, MaxAP: 6, MP: 6, MaxMP: 6}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim, blocker}},
	}}
	// Pull the victim (10,15) west toward the caster (5,15), distance 4: it moves
	// 10 -> 9, then hits the blocker at 8. cellsLeft = 4-1 = 3, 3/cell = 9 damage
	// to BOTH the victim and the blocker.
	f.applyPushPull(caster, gamedata.Effect{ActionID: 38, Params: []float32{4}}, victim.Pos, false)
	if victim.Pos.X != 9 {
		t.Fatalf("pull: victim.x=%d want 9 (stopped before blocker)", victim.Pos.X)
	}
	if victim.HP != 51 {
		t.Errorf("pull collision (victim): HP=%d want 51 (60-9)", victim.HP)
	}
	if blocker.HP != 51 {
		t.Errorf("pull collision (blocker): HP=%d want 51 (60-9)", blocker.HP)
	}
}
