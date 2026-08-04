package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestSelectEffectsForCrit verifies the crit/normal effect partition + fallback.
func TestSelectEffectsForCrit(t *testing.T) {
	effs := []gamedata.Effect{{ActionID: 1, IsCritical: false}, {ActionID: 2, IsCritical: true}}
	if got := selectEffectsForCrit(effs, true); len(got) != 1 || got[0].ActionID != 2 {
		t.Errorf("crit subset = %v, want just the isCritical effect (action 2)", got)
	}
	if got := selectEffectsForCrit(effs, false); len(got) != 1 || got[0].ActionID != 1 {
		t.Errorf("normal subset = %v, want just the non-critical effect (action 1)", got)
	}
	// A spell with no crit-authored effects, cast as a crit, falls back to normal.
	only := []gamedata.Effect{{ActionID: 1}, {ActionID: 2}}
	if got := selectEffectsForCrit(only, true); len(got) != 2 {
		t.Errorf("crit fallback should run all %d normal effects, got %d", 2, len(got))
	}
}

// TestCritHitAndFumble verifies castSpellByFighter picks the crit/normal effect
// subset and that a fumble spends AP but applies nothing. Outcomes are forced by
// setting the rate to 100 (always) or 0 (never) — no rng seeding needed.
func TestCritHitAndFumble(t *testing.T) {
	sp := &gamedata.Spell{ID: 300, AP: 4, RangeMax: 6, Effects: []gamedata.Effect{
		{ActionID: 1, Params: []float32{10}, IsCritical: false}, // normal 10
		{ActionID: 1, Params: []float32{15}, IsCritical: true},  // crit 15
	}}
	mk := func() (*Fight, *FightFighter, *FightFighter) {
		caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
		victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 100, MaxHP: 100}
		f := castFreqFight(caster, []*FightFighter{victim}, sp)
		return f, caster, victim
	}

	// Normal hit (crit 0, fumble 0) -> the non-critical effect (10 dmg).
	f, caster, victim := mk()
	caster.CritRate, caster.FumbleRate = 0, 0
	f.castSpellByFighter(caster, 300, victim.Pos)
	if victim.HP != 90 {
		t.Errorf("normal hit: HP=%d, want 90 (10 dmg)", victim.HP)
	}

	// Critical hit (crit 100) -> the isCritical effect (15 dmg).
	f, caster, victim = mk()
	caster.CritRate, caster.FumbleRate = 100, 0
	f.castSpellByFighter(caster, 300, victim.Pos)
	if victim.HP != 85 {
		t.Errorf("crit hit: HP=%d, want 85 (15 dmg)", victim.HP)
	}

	// Fumble (fumble 100) -> AP spent, NO effects applied.
	f, caster, victim = mk()
	caster.FumbleRate = 100
	f.castSpellByFighter(caster, 300, victim.Pos)
	if victim.HP != 100 {
		t.Errorf("fumble: HP=%d, want 100 (no damage)", victim.HP)
	}
	if caster.AP != 2 { // 6 - 4 AP, still spent
		t.Errorf("fumble: AP=%d, want 2 (AP still spent on a fumble)", caster.AP)
	}
}

// TestCritRateBuff verifies an action-70 buff raises CritRate mechanically and
// reverts on expiry.
func TestCritRateBuff(t *testing.T) {
	ff := &FightFighter{WireID: 1, Pos: Pos{X: 5, Y: 5}, HP: 50, MaxHP: 50, CritRate: 5, FumbleRate: 1} // explicit gear-granted crit
	f := &Fight{Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{ff}}}}
	f.applyBuff(ff, gamedata.Effect{ActionID: 70, Params: []float32{20}, Duration: []int32{2, 0}}, ff.Pos)
	if ff.CritRate != 25 {
		t.Fatalf("CritRate after +20 buff = %d, want 25", ff.CritRate)
	}
	f.tickBuffs()
	f.tickBuffs() // expires and reverts
	if ff.CritRate != 5 {
		t.Errorf("CritRate after buff expiry = %d, want 5", ff.CritRate)
	}
}
