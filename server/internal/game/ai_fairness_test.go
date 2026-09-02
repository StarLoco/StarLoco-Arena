package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestAIDoesNotSeeEnemyTraps is the fairness invariant.
//
// A server-side AI is reasoning over the same structs that hold every hidden
// thing on the battlefield, so being omniscient is the DEFAULT and staying
// honest is the thing that needs enforcing. An AI that steps around a trap
// nobody told it about is the most visible way for a bot to look like it is
// cheating.
//
// The test deliberately asserts the AI treats an enemy trap as SAFE. That reads
// backwards - it is asserting the AI walks into danger - which is exactly the
// point: a human in the same seat would walk into it too.
func TestAIDoesNotSeeEnemyTraps(t *testing.T) {
	f, mover, enemy := summonTestFight()
	cell := Pos{X: mover.Pos.X + 1, Y: mover.Pos.Y}

	// An enemy places a trap on the cell next to our fighter.
	f.effectAreas = append(f.effectAreas, &effectArea{
		id: 1, center: cell, caster: enemy,
		tmpl: &gamedata.StaticEffect{ID: 9001, Type: "TRAP", AreaShape: 2, AreaSize: []int32{0}, MaxExec: 63},
	})

	// Fixture check: the trap must actually cover the cell, or this test would
	// pass regardless of what the knowledge filter does.
	if !f.effectAreas[0].contains(cell) {
		t.Fatal("fixture: the trap does not cover the cell under test")
	}
	if enemy.TeamID == mover.TeamID {
		t.Fatal("fixture: the 'enemy' is on the mover's own team")
	}

	if aiKnowsEffectArea(mover, f.effectAreas[0]) {
		t.Error("the AI can see an ENEMY trap; it must play from the same " +
			"information a human has")
	}
	if _, damaging := f.aiKnownHazardAt(mover, cell); damaging {
		t.Error("an enemy trap made the cell count as a known hazard")
	}
}

// TestAIDoesSeeItsOwnTeamsTraps is the other half. Ignoring your OWN trap is not
// fairness, it is stupidity - you placed it.
func TestAIDoesSeeItsOwnTeamsTraps(t *testing.T) {
	f, mover, _ := summonTestFight()
	cell := Pos{X: mover.Pos.X + 1, Y: mover.Pos.Y}

	ally := &FightFighter{WireID: 77, TeamID: mover.TeamID, Pos: Pos{X: 1, Y: 1}, HP: 50}
	f.Teams[mover.TeamID].Fighters = append(f.Teams[mover.TeamID].Fighters, ally)
	f.effectAreas = append(f.effectAreas, &effectArea{
		id: 2, center: cell, caster: ally,
		tmpl: &gamedata.StaticEffect{ID: 9001, Type: "TRAP", AreaShape: 2, AreaSize: []int32{0}, MaxExec: 63},
	})

	if !aiKnowsEffectArea(mover, f.effectAreas[0]) {
		t.Error("the AI cannot see its OWN team's trap; it placed that")
	}
	if _, damaging := f.aiKnownHazardAt(mover, cell); !damaging {
		t.Error("a friendly trap did not register as a known hazard")
	}
}

// TestAIStillAvoidsArenaKillerCells: map features are drawn for everyone, so
// using them is not cheating - and the AI must not lose that in the name of
// fairness.
func TestAIStillAvoidsArenaKillerCells(t *testing.T) {
	f, mover, _ := summonTestFight()
	killer, ok := findKillerCell(f)
	if !ok {
		// Author one rather than skipping: a fairness test that does not run is
		// indistinguishable from one that passes for the wrong reason.
		killer = Pos{X: mover.Pos.X + 2, Y: mover.Pos.Y}
		a := f.Arena()
		var tmpl int64
		for id, kind := range specialCellByTemplate {
			if kind == specialCellKiller {
				tmpl = id
				break
			}
		}
		if tmpl == 0 {
			t.Fatal("no killer template is registered at all")
		}
		// Arena() hands back SHARED state - appending to it leaks into every other
		// test in the package (it broke TestSpecialCellData the first time). Restore
		// it, and restore the slice header rather than re-slicing, so a re-alloc
		// during append cannot leave the original backing array modified.
		saved := make([]specialCell, len(a.specials))
		copy(saved, a.specials)
		t.Cleanup(func() { a.specials = saved })
		a.specials = append(append([]specialCell{}, a.specials...),
			specialCell{Pos: killer, Template: tmpl})
	}
	if lethal, _ := f.aiKnownHazardAt(mover, killer); !lethal {
		t.Error("an arena killer tile is not treated as lethal; it is a map feature " +
			"every player can see")
	}
	if !f.aiCellIsSuicideFor(mover, killer) {
		t.Error("aiCellIsSuicideFor missed an arena killer tile")
	}
}

// findKillerCell locates a killer tile in the fight's arena, if it has one.
func findKillerCell(f *Fight) (Pos, bool) {
	a := f.Arena()
	for _, sc := range a.specials {
		if specialCellByTemplate[sc.Template] == specialCellKiller {
			return sc.Pos, true
		}
	}
	return Pos{}, false
}
