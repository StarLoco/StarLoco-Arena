package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func poisonFight(caster, victim *FightFighter) *Fight {
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	f.setPhase(PhaseAction)
	return f
}

// TestPoisonDoT verifies a poison (action 61, duration N) ticks once immediately
// then once per new table turn for N turns, then stops — matching the shipped
// data (e.g. Sadida spell 173 = 3-turn poison).
func TestPoisonDoT(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 100, MaxHP: 100}
	f := poisonFight(caster, victim)

	// 5 fixed damage, duration 2 table-turns.
	f.applyPoison(caster, gamedata.Effect{ActionID: 61, Params: []float32{5}, Duration: []int32{2, 0}}, victim.Pos)
	if victim.HP != 95 {
		t.Fatalf("immediate first tick: HP=%d, want 95", victim.HP)
	}
	if len(victim.Poisons) != 1 {
		t.Fatalf("poison should be tracked for recurring ticks, got %d", len(victim.Poisons))
	}
	f.tickPoisons() // boundary tick 1
	if victim.HP != 90 {
		t.Errorf("after boundary tick 1: HP=%d, want 90", victim.HP)
	}
	f.tickPoisons() // boundary tick 2 (duration exhausted)
	if victim.HP != 85 {
		t.Errorf("after boundary tick 2: HP=%d, want 85", victim.HP)
	}
	if len(victim.Poisons) != 0 {
		t.Errorf("poison should have expired after its duration, got %d", len(victim.Poisons))
	}
	f.tickPoisons() // no further ticks
	if victim.HP != 85 {
		t.Errorf("no further ticks after expiry: HP=%d, want 85", victim.HP)
	}
}

// TestPoisonInfiniteAndLethal verifies an infinite poison (duration 63) never
// stops, and a lethal tick kills + clears.
func TestPoisonInfiniteAndLethal(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 100, MaxHP: 100}
	f := poisonFight(caster, victim)

	f.applyPoison(caster, gamedata.Effect{ActionID: 61, Params: []float32{5}, Duration: []int32{63, 0}}, victim.Pos)
	for i := 0; i < 5; i++ {
		f.tickPoisons()
	}
	if len(victim.Poisons) != 1 {
		t.Errorf("infinite poison must persist, got %d", len(victim.Poisons))
	}
	if victim.HP != 70 { // 100 - 5 immediate - 5*5 ticks
		t.Errorf("infinite poison HP=%d, want 70", victim.HP)
	}

	// A lethal boundary tick kills the bearer and clears its poisons.
	victim.HP = 3
	f.tickPoisons()
	if victim.HP > 0 {
		t.Errorf("lethal poison tick should kill: HP=%d", victim.HP)
	}
	if len(victim.Poisons) != 0 {
		t.Errorf("a dead fighter's poisons should be cleared, got %d", len(victim.Poisons))
	}
}
