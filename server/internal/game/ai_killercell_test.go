package game

import "testing"

// TestAIWillNotWalkOntoAKillerCell covers a suicide watched in the retail client:
// a Xelor closing on my team stepped onto a Killer tile and was dead at the start
// of its next turn. Movement scoring only measured distance, so nothing stopped
// it — and nothing would have stopped it doing the same thing every fight.
//
// The Killer tile (template 1002) kills whoever STARTS a turn on it, with no save
// and no resistance (applyTurnStartSpecialCell).
func TestAIWillNotWalkOntoAKillerCell(t *testing.T) {
	// A copy of the practice arena with a Killer tile planted on the single cell
	// that is directly toward the enemy — the exact cell the AI wants.
	killerAt := Pos{X: 8, Y: 15, Z: practiceArena.altitudeAt(8, 15)}
	withKiller := practiceArena
	withKiller.specials = append(append([]specialCell{}, practiceArena.specials...),
		specialCell{Pos: killerAt, Template: 1002})

	f, caster, enemy := summonTestFight()
	f.arena = &withKiller
	caster.Pos = Pos{X: 7, Y: 15}
	enemy.Pos = Pos{X: 12, Y: 15} // due east, so (8,15) is straight on the path
	caster.MP, caster.MaxMP = 1, 1

	// Sanity: with 1 MP the killer cell is the ONLY strictly-closer option, so a
	// distance-only AI must pick it. Without this the test could pass for the
	// wrong reason.
	if got := f.aiCellIsSuicide(killerAt); !got {
		t.Fatal("aiCellIsSuicide false for a template-1002 tile")
	}

	f.moveTowardNearestOpponent(caster)

	if caster.Pos == killerAt {
		t.Fatalf("AI walked onto the killer cell at %v", killerAt)
	}
	// It may stand still or step aside, but it must be alive-safe.
	if f.aiCellIsSuicide(caster.Pos) {
		t.Errorf("AI ended its move on a lethal cell %v", caster.Pos)
	}

	// Passing OVER a killer tile is fine — it only fires at turn start — so a
	// fighter with the MP to go further must still be able to advance past it.
	f2, c2, e2 := summonTestFight()
	f2.arena = &withKiller
	c2.Pos = Pos{X: 7, Y: 15}
	e2.Pos = Pos{X: 12, Y: 15}
	c2.MP, c2.MaxMP = 3, 3
	f2.moveTowardNearestOpponent(c2)
	if c2.Pos == (Pos{X: 7, Y: 15}) {
		t.Error("AI refused to advance at all rather than pass over a killer cell")
	}
	if f2.aiCellIsSuicide(c2.Pos) {
		t.Errorf("AI ended its move on a lethal cell %v", c2.Pos)
	}
	if manhattanDist(c2.Pos, e2.Pos) >= manhattanDist(Pos{X: 7, Y: 15}, e2.Pos) {
		t.Errorf("moved to %v, no closer to the enemy", c2.Pos)
	}
}
