package game

import "testing"

// suddenDeathFight builds a fight with one fighter per side at the given cells.
func suddenDeathFight(a, b Pos) (*Fight, *FightFighter, *FightFighter) {
	fa := &FightFighter{WireID: 1, TeamID: 0, Pos: a, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	fb := &FightFighter{WireID: 2, TeamID: 1, Pos: b, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{fa}},
		{ID: 1, Fighters: []*FightFighter{fb}},
	}}
	f.Timeline = []*FightFighter{fa, fb}
	f.deps = &Deps{Log: testLogger(), Fights: NewFightManager()}
	return f, fa, fb
}

// TestSpiralWalkerMatchesClient pins the generator against a hand-trace of the
// client's in_0: (0,0), then 1×right, 1×down, 2×left, 2×up, 3×right, 3×down,
// 4×left, 4×up. Both sides derive the destroyed cells from this independently, so
// any drift silently desyncs which cells are gone.
func TestSpiralWalkerMatchesClient(t *testing.T) {
	want := [][2]int32{
		{0, 0},
		{1, 0},
		{0, 1},
		{-1, 0}, {-1, 0},
		{0, -1}, {0, -1},
		{1, 0}, {1, 0}, {1, 0},
		{0, 1}, {0, 1}, {0, 1},
		{-1, 0}, {-1, 0}, {-1, 0}, {-1, 0},
		{0, -1}, {0, -1}, {0, -1}, {0, -1},
	}
	var g spiralWalker
	for i, w := range want {
		dx, dy := g.next()
		if dx != w[0] || dy != w[1] {
			t.Fatalf("step %d = (%d,%d), want (%d,%d)", i, dx, dy, w[0], w[1])
		}
	}
}

// TestMapDestructionOrderIsOutermostFirst: the client prepends each spiral cell, so
// its list runs outermost → centre and it destroys the FIRST r entries. Ours must
// match, or `r` would remove the wrong cells.
func TestMapDestructionOrderIsOutermostFirst(t *testing.T) {
	centre := Pos{X: 10, Y: 9}
	order := mapDestructionOrder(centre, mapDestructionNy)
	if got := int32(len(order)); got != mapDestructionNy*mapDestructionNy {
		t.Fatalf("order length = %d, want %d", got, mapDestructionNy*mapDestructionNy)
	}
	if last := order[len(order)-1]; last != centre {
		t.Errorf("last entry = %v, want the centre %v (destroyed last)", last, centre)
	}
	// Each cell appears once.
	seen := map[[2]int32]bool{}
	for _, c := range order {
		k := [2]int32{c.X, c.Y}
		if seen[k] {
			t.Fatalf("cell %v appears twice", c)
		}
		seen[k] = true
	}
	// The head is far from the centre, the tail near it.
	headDist := absI32(order[0].X-centre.X) + absI32(order[0].Y-centre.Y)
	tailDist := absI32(order[len(order)-1].X-centre.X) + absI32(order[len(order)-1].Y-centre.Y)
	if headDist <= tailDist {
		t.Errorf("order is not outermost-first: head dist %d, tail dist %d", headDist, tailDist)
	}
}

// TestSuddenDeathScheduleConvergesOnTheCore: the steps rise monotonically and the
// last one leaves exactly the client's default 5×5 core (Ny²−Nz² = 299).
func TestSuddenDeathScheduleConvergesOnTheCore(t *testing.T) {
	sched := suddenDeathSchedule(&practiceArena, mapDestructionNy, mapDestructionNz)
	if len(sched) < 2 {
		t.Fatalf("schedule has %d steps; the collapse must be progressive", len(sched))
	}
	for i := 1; i < len(sched); i++ {
		if sched[i] <= sched[i-1] {
			t.Errorf("step %d (%d) does not advance past step %d (%d)", i, sched[i], i-1, sched[i-1])
		}
	}
	want := mapDestructionNy*mapDestructionNy - mapDestructionNz*mapDestructionNz
	if last := sched[len(sched)-1]; last != want {
		t.Errorf("final r = %d, want %d (everything outside the %dx%d core)",
			last, want, mapDestructionNz, mapDestructionNz)
	}
}

// TestSuddenDeathIsProgressive: the first step must NOT flatten the map. Sending
// everything at once was the reported bug — it killed every fighter instantly.
func TestSuddenDeathIsProgressive(t *testing.T) {
	centre := suddenDeathCentre(&practiceArena)
	f, _, _ := suddenDeathFight(centre, centre)
	f.tableTurn = suddenDeathTurn

	f.maybeTriggerSuddenDeath()
	first := len(f.destroyedCells)
	total := int(mapDestructionNy * mapDestructionNy)
	if first == 0 {
		t.Fatal("first step removed nothing")
	}
	if first >= total/2 {
		t.Errorf("first step removed %d of %d cells — that is not a gradual shrink", first, total)
	}

	f.tableTurn++
	f.maybeTriggerSuddenDeath()
	if len(f.destroyedCells) <= first {
		t.Errorf("second step removed nothing (%d -> %d)", first, len(f.destroyedCells))
	}
}

// TestSuddenDeathDoesNotStartEarly.
func TestSuddenDeathDoesNotStartEarly(t *testing.T) {
	centre := suddenDeathCentre(&practiceArena)
	f, _, _ := suddenDeathFight(centre, centre)
	f.tableTurn = suddenDeathTurn - 1
	f.maybeTriggerSuddenDeath()
	if len(f.destroyedCells) != 0 || f.suddenDeathStep != 0 {
		t.Errorf("collapsed at turn %d; sudden death is turn %d", f.tableTurn, suddenDeathTurn)
	}
}

// TestSuddenDeathStopsAtTheCore: after every step the centre survives and nothing
// outside the core is left.
func TestSuddenDeathStopsAtTheCore(t *testing.T) {
	centre := suddenDeathCentre(&practiceArena)
	f, _, _ := suddenDeathFight(centre, centre)
	f.tableTurn = suddenDeathTurn
	for i := 0; i < 40; i++ { // many more steps than the schedule has
		f.maybeTriggerSuddenDeath()
		f.tableTurn++
	}
	if f.cellDestroyed(centre.X, centre.Y) {
		t.Error("the centre was destroyed; a central arena must survive")
	}
	sched := suddenDeathSchedule(&practiceArena, mapDestructionNy, mapDestructionNz)
	order := mapDestructionOrder(centre, mapDestructionNy)
	finalR := sched[len(sched)-1]
	for _, c := range order[:finalR] {
		if !f.cellDestroyed(c.X, c.Y) {
			t.Fatalf("cell %v should be gone after the full collapse", c)
		}
	}
	for _, c := range order[finalR:] {
		if f.cellDestroyed(c.X, c.Y) {
			t.Fatalf("core cell %v was destroyed", c)
		}
	}
}

// TestSuddenDeathKillsOnlyFightersOnRemovedCells is the reported bug: everyone died
// the instant it fired. A fighter dies only when the ground under IT goes.
func TestSuddenDeathKillsOnlyFightersOnRemovedCells(t *testing.T) {
	centre := suddenDeathCentre(&practiceArena)
	order := mapDestructionOrder(centre, mapDestructionNy)
	sched := suddenDeathSchedule(&practiceArena, mapDestructionNy, mapDestructionNz)

	doomed := order[0]          // removed by the very first step
	safe := order[len(order)-1] // the centre: survives everything
	f, dies, lives := suddenDeathFight(doomed, safe)

	f.tableTurn = suddenDeathTurn
	f.maybeTriggerSuddenDeath()

	if dies.HP > 0 {
		t.Errorf("fighter on removed cell %v survived (HP %d)", dies.Pos, dies.HP)
	}
	if lives.HP <= 0 {
		t.Errorf("fighter at %v died, but its cell was not removed by this step", lives.Pos)
	}
	_ = sched
}

// TestSuddenDeathFighterSurvivesUntilItsOwnCellGoes: being outside the eventual core
// is not lethal by itself — only the step that removes your cell kills you.
func TestSuddenDeathFighterSurvivesUntilItsOwnCellGoes(t *testing.T) {
	centre := suddenDeathCentre(&practiceArena)
	order := mapDestructionOrder(centre, mapDestructionNy)

	// Discover empirically which cells the first two EFFECTIVE steps take (the
	// schedule skips steps that would only clear void, so this cannot be assumed).
	probe, _, _ := suddenDeathFight(centre, centre)
	probe.tableTurn = suddenDeathTurn
	probe.maybeTriggerSuddenDeath()
	afterFirst := make(map[[2]int32]bool, len(probe.destroyedCells))
	for k := range probe.destroyedCells {
		afterFirst[k] = true
	}
	probe.tableTurn++
	probe.maybeTriggerSuddenDeath()

	// A cell taken by step 2 but still standing after step 1.
	var target Pos
	found := false
	for k := range probe.destroyedCells {
		if !afterFirst[k] {
			target, found = Pos{X: k[0], Y: k[1]}, true
			break
		}
	}
	if !found {
		t.Skip("the second step took no new cell")
	}

	f, ff, _ := suddenDeathFight(target, order[len(order)-1])
	f.tableTurn = suddenDeathTurn
	f.maybeTriggerSuddenDeath() // step 1: its cell still stands
	if ff.HP <= 0 {
		t.Fatalf("fighter at %v died on step 1, but that step does not remove its cell", target)
	}
	f.tableTurn++
	f.maybeTriggerSuddenDeath() // step 2: its cell goes
	if ff.HP > 0 {
		t.Errorf("fighter survived removal of its own cell %v", target)
	}
}

// TestSuddenDeathBlocksMovementOntoRemovedCells.
func TestSuddenDeathBlocksMovementOntoRemovedCells(t *testing.T) {
	centre := suddenDeathCentre(&practiceArena)
	order := mapDestructionOrder(centre, mapDestructionNy)
	sched := suddenDeathSchedule(&practiceArena, mapDestructionNy, mapDestructionNz)

	// A real floor cell removed by the FIRST effective step, with a walkable
	// neighbour to move from. (The outermost spiral cells are all void, so the
	// first effective step is not schedule[0] — advanceSuddenDeath skips those.)
	f, mover, _ := suddenDeathFight(Pos{X: 0, Y: 0}, order[len(order)-1])
	f.tableTurn = suddenDeathTurn
	f.maybeTriggerSuddenDeath()
	if len(f.destroyedCells) == 0 {
		t.Fatal("the first effective step removed nothing")
	}

	var from, to Pos
	found := false
	for cell := range f.destroyedCells {
		c := Pos{X: cell[0], Y: cell[1]}
		if !practiceArena.walkable(c.X, c.Y) {
			continue
		}
		for _, d := range [][2]int32{{1, 0}, {-1, 0}, {0, 1}, {0, -1}} {
			n := Pos{X: c.X + d[0], Y: c.Y + d[1]}
			if practiceArena.walkable(n.X, n.Y) && !f.cellDestroyed(n.X, n.Y) {
				from, to, found = n, c, true
				break
			}
		}
		if found {
			break
		}
	}
	if !found {
		t.Skip("no removed floor cell has a surviving walkable neighbour")
	}

	mover.HP, mover.Pos = 70, from
	if f.validateFightMove(mover, []Pos{to}) {
		t.Errorf("move onto removed cell %v was allowed", to)
	}
	// Sanity: an ordinary neighbour that was NOT removed is still reachable.
	for _, d := range [][2]int32{{1, 0}, {-1, 0}, {0, 1}, {0, -1}} {
		n := Pos{X: from.X + d[0], Y: from.Y + d[1]}
		if practiceArena.walkable(n.X, n.Y) && !f.cellDestroyed(n.X, n.Y) {
			if !f.validateFightMove(mover, []Pos{n}) {
				t.Errorf("move onto surviving cell %v was refused", n)
			}
			break
		}
	}
	_ = sched
}
