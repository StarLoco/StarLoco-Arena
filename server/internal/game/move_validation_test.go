package game

import "testing"

func TestArenaWalkable(t *testing.T) {
	// From practiceArena.topo: (7,15)/(7,14) are floor (0); (4,15) and (0,0) are
	// void (32767); out-of-bounds is not walkable.
	cases := []struct {
		x, y int32
		want bool
	}{
		{7, 15, true},
		{7, 14, true},
		{8, 15, true},
		{4, 15, false}, // void
		{0, 0, false},  // void
		{-1, 5, false}, // out of bounds
		{18, 5, false}, // out of bounds
		{7, 18, false}, // out of bounds (y)
	}
	for _, c := range cases {
		if got := practiceArena.walkable(c.x, c.y); got != c.want {
			t.Errorf("walkable(%d,%d) = %v, want %v", c.x, c.y, got, c.want)
		}
	}
}

func TestCellsAdjacent(t *testing.T) {
	p := Pos{X: 7, Y: 15}
	adj := []Pos{{X: 8, Y: 15}, {X: 6, Y: 15}, {X: 7, Y: 14}, {X: 7, Y: 16}, {X: 8, Y: 16}}
	for _, q := range adj {
		if !cellsAdjacent(p, q) {
			t.Errorf("cellsAdjacent(%v,%v) = false, want true", p, q)
		}
	}
	notAdj := []Pos{{X: 7, Y: 15}, {X: 9, Y: 15}, {X: 7, Y: 13}, {X: 9, Y: 17}}
	for _, q := range notAdj {
		if cellsAdjacent(p, q) {
			t.Errorf("cellsAdjacent(%v,%v) = true, want false", p, q)
		}
	}
}

func TestValidateFightMove(t *testing.T) {
	mover := &FightFighter{WireID: 1, Pos: Pos{X: 7, Y: 15, Z: 0}, MP: 3}
	blocker := &FightFighter{WireID: 2, Pos: Pos{X: 8, Y: 15, Z: 0}, HP: 50}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{mover}},
		{ID: 1, Fighters: []*FightFighter{blocker}},
	}}
	p := func(cells ...[2]int32) []Pos {
		out := make([]Pos, len(cells))
		for i, c := range cells {
			out[i] = Pos{X: c[0], Y: c[1]}
		}
		return out
	}
	// Paths are STEP cells only (origin (7,15) EXCLUDED), matching the retail
	// client's 4503; path[0] must be adjacent to the fighter's cell.
	cases := []struct {
		name string
		path []Pos
		want bool
	}{
		{"valid 1 step", p([2]int32{7, 14}), true},
		// Routed east along row 14, which is clear floor. (Deliberately NOT north
		// up column 7: (7,13) is a scenery obstacle — see the case below.)
		{"valid 3 steps = MP", p([2]int32{7, 14}, [2]int32{8, 14}, [2]int32{9, 14}), true},
		{"empty path", p(), false},
		{"step onto own cell", p([2]int32{7, 15}), false},
		{"first step not adjacent (teleport)", p([2]int32{9, 15}), false},
		{"mid-path teleport", p([2]int32{7, 14}, [2]int32{9, 14}), false},
		{"into void", p([2]int32{6, 15}, [2]int32{5, 15}, [2]int32{4, 15}), false},
		// Scenery obstacles are real cells with an altitude but no walkable
		// ground; a path may not pass through one (B-048). (7,13) is the tree/
		// spike north of the start row.
		{"through a scenery obstacle", p([2]int32{7, 14}, [2]int32{7, 13}), false},
		{"exceeds MP (4 steps)", p([2]int32{7, 14}, [2]int32{8, 14}, [2]int32{9, 14}, [2]int32{10, 14}), false},
		{"onto another fighter", p([2]int32{8, 15}), false},
	}
	for _, c := range cases {
		if got := f.validateFightMove(mover, c.path); got != c.want {
			t.Errorf("%s: validateFightMove = %v, want %v", c.name, got, c.want)
		}
	}

	// A dead fighter no longer blocks its cell.
	blocker.HP = 0
	if !f.validateFightMove(mover, p([2]int32{8, 15})) {
		t.Error("move onto a DEAD fighter's cell should be allowed")
	}
}
