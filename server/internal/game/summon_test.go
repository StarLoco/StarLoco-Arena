package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// summonTestFight builds a caster (team 0, current turn) vs an enemy (team 1) on
// the real practice arena, with a summon-template catalog on deps.
func summonTestFight(templates ...*gamedata.Summoning) (*Fight, *FightFighter, *FightFighter) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 75, MaxHP: 75, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3, Init: 40}
	enemy := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 12, Y: 15}, HP: 70, MaxHP: 70, Init: 50}
	f := &Fight{
		Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{caster}},
			{ID: 1, Fighters: []*FightFighter{enemy}},
		},
		deps: &Deps{Summonings: gamedata.NewSummonings(templates...)},
	}
	f.Timeline = []*FightFighter{enemy, caster} // init-descending (enemy 50 > caster 40)
	f.turnIndex = 1                             // caster's turn
	f.setPhase(PhaseAction)
	return f, caster, enemy
}

func TestApplySummon(t *testing.T) {
	f, caster, _ := summonTestFight(&gamedata.Summoning{ID: 1, HP: 20, AP: 6, MP: 3, SpellIDs: []int32{105}})

	// Summon (action 67) template 1 onto the adjacent empty cell (8,15).
	ef := gamedata.Effect{ActionID: 67, EffectID: 500, Params: []float32{1}}
	f.resolveEffect(caster, ef, Pos{X: 8, Y: 15})

	if len(f.Teams[0].Fighters) != 2 {
		t.Fatalf("team0 fighters = %d, want 2 (caster + summon)", len(f.Teams[0].Fighters))
	}
	summon := f.Teams[0].Fighters[1]
	if summon.Father != caster {
		t.Errorf("summon.Father not set to caster")
	}
	if !summon.isSummon() {
		t.Errorf("summon.isSummon() = false")
	}
	if summon.SummonSpellID != 105 {
		t.Errorf("summon spell = %d, want 105", summon.SummonSpellID)
	}
	if summon.MaxHP != 20 || summon.HP != 20 || summon.MaxMP != 3 || summon.MaxAP != 6 {
		t.Errorf("summon stats hp=%d/%d ap=%d mp=%d, want 20/20 ap6 mp3", summon.HP, summon.MaxHP, summon.MaxAP, summon.MaxMP)
	}
	if summon.Pos.X != 8 || summon.Pos.Y != 15 {
		t.Errorf("summon pos = (%d,%d), want (8,15)", summon.Pos.X, summon.Pos.Y)
	}
	if summon.TeamID != 0 {
		t.Errorf("summon team = %d, want 0 (caster's team)", summon.TeamID)
	}
	if summon.WireID <= FighterWireIDBase {
		t.Errorf("summon wire id %d not in the summon namespace", summon.WireID)
	}
	// Timeline: inserted right AFTER the caster; the current turn index is unchanged.
	if len(f.Timeline) != 3 {
		t.Fatalf("timeline len = %d, want 3", len(f.Timeline))
	}
	casterIdx := -1
	for i, x := range f.Timeline {
		if x == caster {
			casterIdx = i
		}
	}
	if f.Timeline[casterIdx+1] != summon {
		t.Errorf("summon not inserted immediately after caster in timeline")
	}
	if f.Timeline[f.turnIndex] != caster {
		t.Errorf("current turn index shifted off the caster after summoning")
	}
}

func TestApplySummonFallbackStatsAndBlockedCell(t *testing.T) {
	// No template for id 99 -> fallback blocker stats.
	f, caster, _ := summonTestFight()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 67, Params: []float32{99}}, Pos{X: 8, Y: 15})
	if len(f.Teams[0].Fighters) != 2 {
		t.Fatalf("fallback summon not spawned: team0=%d", len(f.Teams[0].Fighters))
	}
	s := f.Teams[0].Fighters[1]
	if s.MaxHP != defaultSummonHP || s.SummonSpellID != 0 {
		t.Errorf("fallback summon hp=%d spell=%d, want %d/0", s.MaxHP, s.SummonSpellID, defaultSummonHP)
	}

	// Summoning onto an occupied/void cell is a no-op.
	f2, caster2, enemy2 := summonTestFight()
	f2.resolveEffect(caster2, gamedata.Effect{ActionID: 67, Params: []float32{1}}, enemy2.Pos)      // occupied
	f2.resolveEffect(caster2, gamedata.Effect{ActionID: 67, Params: []float32{1}}, Pos{X: 0, Y: 0}) // void
	if len(f2.Teams[0].Fighters) != 1 {
		t.Errorf("summon spawned on an illegal cell: team0=%d, want 1", len(f2.Teams[0].Fighters))
	}
}
