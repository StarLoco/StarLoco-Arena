package game

import "testing"

// TestPathfindAvoidsDestroyedCells covers a gap between the two movement paths.
// A human's move goes through validateFightMove, which rejects any path touching
// a cell sudden death has removed. The AI calls applyFighterMove directly on a
// path from reachableCells, which did not check at all — so the AI got a move no
// player could make: it could stop on a destroyed cell (shrinkArena kills whoever
// stands on one outright) or walk THROUGH cells the client has flagged
// movement-blocked.
func TestPathfindAvoidsDestroyedCells(t *testing.T) {
	f, caster, _ := summonTestFight()
	caster.Pos = Pos{X: 7, Y: 15}

	// Baseline: (8,15) is reachable with 1 MP.
	if _, ok := f.reachableCells(caster, caster.Pos, 1)[[2]int32{8, 15}]; !ok {
		t.Fatal("(8,15) not reachable before destruction — test setup is wrong")
	}

	// Destroy it: it must vanish from the flood entirely.
	f.destroyedCells = map[[2]int32]bool{{8, 15}: true}
	if _, ok := f.reachableCells(caster, caster.Pos, 1)[[2]int32{8, 15}]; ok {
		t.Error("a destroyed cell is still offered as a destination")
	}

	// And it must not be walked THROUGH: with 2 MP, nothing reachable may route
	// over the destroyed cell.
	for cell, path := range f.reachableCells(caster, caster.Pos, 2) {
		for _, step := range path {
			if step.X == 8 && step.Y == 15 {
				t.Errorf("path to (%d,%d) routes through the destroyed cell (8,15): %v",
					cell[0], cell[1], path)
			}
		}
	}

	// The AI's movement must therefore never land on one either.
	f2, c2, e2 := summonTestFight()
	c2.Pos = Pos{X: 7, Y: 15}
	e2.Pos = Pos{X: 12, Y: 15} // due east: (8,15) is the cell it wants
	c2.MP, c2.MaxMP = 1, 1
	f2.destroyedCells = map[[2]int32]bool{{8, 15}: true}
	f2.moveTowardNearestOpponent(c2)
	if c2.Pos.X == 8 && c2.Pos.Y == 15 {
		t.Error("AI moved onto a destroyed cell")
	}
	if f2.cellDestroyed(c2.Pos.X, c2.Pos.Y) {
		t.Errorf("AI ended its move on destroyed cell %v", c2.Pos)
	}
}
