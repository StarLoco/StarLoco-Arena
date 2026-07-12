package combat

import "testing"

// TestSummonAltitudeStepClamp verifies summons are limited to altitude changes
// of at most 1 per step (wiki: "Summons do not jump down cliffs with altitude
// of 2 or higher, nor can they climb them"), while normal fighters keep the
// larger maxAscend/maxDescend of 4.
func TestSummonAltitudeStepClamp(t *testing.T) {
	human := NewFighterFromBreed(1, 1, BreedIop, "Human", 0, 0)
	summon := NewFighterFromBreed(2, 1, BreedIop, "Summon", 0, 0)
	summon.Father = human // marks it as an AI summon

	if got := moverMaxAscend(summon); got != summonMaxAltitudeStep {
		t.Errorf("summon moverMaxAscend = %d, want %d", got, summonMaxAltitudeStep)
	}
	if got := moverMaxDescend(summon); got != summonMaxAltitudeStep {
		t.Errorf("summon moverMaxDescend = %d, want %d", got, summonMaxAltitudeStep)
	}
	if got := moverMaxAscend(human); got != maxAscend {
		t.Errorf("human moverMaxAscend = %d, want %d", got, maxAscend)
	}
	if got := moverMaxDescend(human); got != maxDescend {
		t.Errorf("human moverMaxDescend = %d, want %d", got, maxDescend)
	}
}

// openField is a CellInfoProvider with no obstacles: every cell is walkable,
// nothing is occupied, and altitude never changes or blocks. It isolates the
// pathfinder's direction logic from map/occupancy concerns.
type openField struct{}

func (openField) IsWalkable(Point3) bool           { return true }
func (openField) IsOccupied(Point3, *Fighter) bool { return false }
func (openField) ArrivalAltitude(_ *Fighter, fromZ int16, _ Point3) (int16, bool) {
	return fromZ, false
}

// blockedField marks a set of cells (by X/Y) as non-walkable; everything
// else behaves like openField.
type blockedField struct {
	blocked map[[2]int32]bool
}

func (b blockedField) IsWalkable(p Point3) bool       { return !b.blocked[[2]int32{p.X, p.Y}] }
func (blockedField) IsOccupied(Point3, *Fighter) bool { return false }
func (blockedField) ArrivalAltitude(_ *Fighter, fromZ int16, _ Point3) (int16, bool) {
	return fromZ, false
}

// assertLegalFightSteps fails the test if any consecutive pair of cells in
// the path (starting from start) is not a legal fight move: a SINGLE-AXIS
// grid step (exactly one of dx/dy is ±1, the other 0). Those four steps are
// the client's SOUTH_EAST/SOUTH_WEST/NORTH_WEST/NORTH_EAST directions (the
// only ones with sprite art). A two-axis step (both dx and dy nonzero) would
// be resolved by the client to a cardinal facing, which has no sprite and
// renders the fighter invisible -- exactly the bug fightMoveDirections /
// Point3.Step guard against.
func assertLegalFightSteps(t *testing.T, start Point3, path []Point3) {
	t.Helper()
	if path == nil {
		t.Fatal("FindPath returned nil (no path found)")
	}
	prev := start
	for i, cur := range path {
		dx := cur.X - prev.X
		dy := cur.Y - prev.Y
		if dx < 0 {
			dx = -dx
		}
		if dy < 0 {
			dy = -dy
		}
		if dx+dy != 1 {
			t.Errorf("step %d from (%d,%d) to (%d,%d) is not a single-axis grid move (|dx|=%d, |dy|=%d); fight movement must only emit SOUTH_EAST/SOUTH_WEST/NORTH_WEST/NORTH_EAST steps",
				i, prev.X, prev.Y, cur.X, cur.Y, dx, dy)
		}
		prev = cur
	}
}

// TestFindPathOnlySingleAxisSteps verifies the fix for the fight-movement
// bug: A* must restrict movement to the four diagonal-NAMED directions,
// which are single-axis grid moves, so the broadcast path never contains a
// two-axis step that the client would resolve to a cardinal (invisible)
// facing or animate toward the wrong cell. See fightMoveDirections and
// Point3.Step.
func TestFindPathOnlySingleAxisSteps(t *testing.T) {
	start := Point3{X: 5, Y: 5, Z: 0}

	cases := []struct {
		name string
		goal Point3
	}{
		// Straight line along one grid axis (SOUTH_EAST repeated).
		{"straight +X", Point3{X: 9, Y: 5, Z: 0}},
		// Straight line along the other grid axis (SOUTH_WEST repeated).
		{"straight +Y", Point3{X: 5, Y: 9, Z: 0}},
		// Negative axes (NORTH_WEST / NORTH_EAST).
		{"straight -X", Point3{X: 2, Y: 5, Z: 0}},
		{"straight -Y", Point3{X: 5, Y: 2, Z: 0}},
		// Screen-diagonal goal (both grid axes differ): reachable now with
		// no parity restriction, via a mix of single-axis steps.
		{"screen diagonal", Point3{X: 8, Y: 8, Z: 0}},
		// Odd-Manhattan goal that the old diagonal-only pathfinder could
		// NOT reach (parity dead zone) -- must now be reachable.
		{"odd manhattan", Point3{X: 7, Y: 8, Z: 0}},
	}

	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			path := FindPath(nil, start, tc.goal, openField{})
			assertLegalFightSteps(t, start, path)
			if len(path) == 0 {
				t.Fatalf("expected a non-empty path to (%d,%d)", tc.goal.X, tc.goal.Y)
			}
			last := path[len(path)-1]
			if last.X != tc.goal.X || last.Y != tc.goal.Y {
				t.Errorf("path ends at (%d,%d), want goal (%d,%d)", last.X, last.Y, tc.goal.X, tc.goal.Y)
			}
		})
	}
}

// TestFindPathAroundObstacleStaysLegal verifies that even when routing
// around blocked cells, every emitted step remains a legal single-axis
// fight move and no blocked cell is entered.
func TestFindPathAroundObstacleStaysLegal(t *testing.T) {
	start := Point3{X: 4, Y: 4, Z: 0}
	goal := Point3{X: 8, Y: 4, Z: 0}

	// Wall the direct +X corridor at X=6 (both the goal row and the rows
	// immediately around it) to force a detour, still resolvable with
	// single-axis steps.
	blocked := blockedField{blocked: map[[2]int32]bool{
		{6, 4}: true,
	}}

	path := FindPath(nil, start, goal, blocked)
	assertLegalFightSteps(t, start, path)
	if len(path) == 0 {
		t.Fatal("expected a non-empty detour path")
	}
	last := path[len(path)-1]
	if last.X != goal.X || last.Y != goal.Y {
		t.Errorf("detour path ends at (%d,%d), want goal (%d,%d)", last.X, last.Y, goal.X, goal.Y)
	}
	for _, p := range path {
		if blocked.blocked[[2]int32{p.X, p.Y}] {
			t.Errorf("path passes through blocked cell (%d,%d)", p.X, p.Y)
		}
	}
}

// TestValidateClientPath_FollowsClientRoute reproduces the reported bug: the
// client computes and displays a specific shortest path (the "green" route),
// but the server used to re-derive its own equally-short A* route (the "red"
// route) and broadcast that instead. ValidateClientPath must accept and
// return the client's exact route. Scenario taken from the real packet
// capture: fighter at (3,6) requests (3,5)->(3,4)->(4,4); A* would instead
// return (4,6)->(4,5)->(4,4).
func TestValidateClientPath_FollowsClientRoute(t *testing.T) {
	start := Point3{X: 3, Y: 6, Z: -4}
	clientSteps := []Point3{
		{X: 3, Y: 5, Z: -4},
		{X: 3, Y: 4, Z: -4},
		{X: 4, Y: 4, Z: -4},
	}

	got := ValidateClientPath(nil, start, clientSteps, openField{})
	if got == nil {
		t.Fatal("ValidateClientPath rejected a legal client route")
	}
	assertLegalFightSteps(t, start, got)

	if len(got) != len(clientSteps) {
		t.Fatalf("resolved path length = %d, want %d", len(got), len(clientSteps))
	}
	for i, want := range clientSteps {
		if got[i].X != want.X || got[i].Y != want.Y {
			t.Errorf("step %d = (%d,%d), want the client's cell (%d,%d) -- server must follow the client's route, not re-route",
				i, got[i].X, got[i].Y, want.X, want.Y)
		}
	}

	// Sanity: A* on the same open field produces a DIFFERENT (but equally
	// short) route, which is exactly why we must prefer the client's.
	astar := FindPath(nil, start, clientSteps[len(clientSteps)-1], openField{})
	if len(astar) != len(got) {
		t.Fatalf("expected A* route to be the same length (%d) as client route (%d)", len(astar), len(got))
	}
}

// TestValidateClientPath_RejectsIllegalSteps verifies the server-authority
// guard: a client path containing a two-axis (cardinal-facing / invisible)
// step, a multi-cell jump, or an occupied/unwalkable cell is rejected
// (returns nil) so handleFighterMove falls back to A*.
func TestValidateClientPath_RejectsIllegalSteps(t *testing.T) {
	start := Point3{X: 5, Y: 5, Z: 0}

	cases := map[string][]Point3{
		"two-axis step":    {{X: 6, Y: 6, Z: 0}}, // diagonal-on-grid = invisible
		"multi-cell jump":  {{X: 8, Y: 5, Z: 0}}, // skips (6,5),(7,5)
		"non-adjacent mid": {{X: 6, Y: 5, Z: 0}, {X: 8, Y: 5, Z: 0}},
		"revisit start":    {{X: 6, Y: 5, Z: 0}, {X: 5, Y: 5, Z: 0}},
	}
	for name, steps := range cases {
		t.Run(name, func(t *testing.T) {
			if got := ValidateClientPath(nil, start, steps, openField{}); got != nil {
				t.Errorf("expected nil (rejected), got %v", got)
			}
		})
	}

	// A step onto a blocked cell is rejected too.
	blocked := blockedField{blocked: map[[2]int32]bool{{6, 5}: true}}
	if got := ValidateClientPath(nil, start, []Point3{{X: 6, Y: 5, Z: 0}}, blocked); got != nil {
		t.Errorf("expected nil for step onto blocked cell, got %v", got)
	}
}

// --- Height / altitude pathfinding verification -----------------------
//
// The previous mocks (openField/blockedField) always returned
// blocked=false from ArrivalAltitude, so the height-blocking and
// ascend/descend-limit branches in FindPath/ValidateClientPath were never
// exercised. heightField models a real map's altitude behavior: each cell
// has a standing altitude, and cells with no entry are height-blocked
// (mirroring Fight.ArrivalAltitude returning blocked=true when no walkable
// surface exists at the target cell). This closes the roadmap Phase K
// verification gap ("A* against a height-blocked / corner-cut real-map
// path").
type heightField struct {
	altitude map[[2]int32]int16 // cell -> standing altitude; absent => height-blocked
}

func (h heightField) IsWalkable(p Point3) bool {
	_, ok := h.altitude[[2]int32{p.X, p.Y}]
	return ok
}
func (heightField) IsOccupied(Point3, *Fighter) bool { return false }
func (h heightField) ArrivalAltitude(_ *Fighter, fromZ int16, to Point3) (int16, bool) {
	alt, ok := h.altitude[[2]int32{to.X, to.Y}]
	if !ok {
		return fromZ, true // no surface -> height-blocked, mirroring Fight.ArrivalAltitude
	}
	return alt, false
}

// TestFindPath_RoutesAroundHeightBlockedCell confirms a cell that is
// walkable in X/Y but height-blocked (ArrivalAltitude blocked=true) is
// treated like an obstacle: the path detours around it and never enters it.
func TestFindPath_RoutesAroundHeightBlockedCell(t *testing.T) {
	// A flat corridor from (0,0) to (3,0), but (2,0) is a hole (no surface).
	field := heightField{altitude: map[[2]int32]int16{
		{0, 0}: 0, {1, 0}: 0 /* (2,0) blocked */, {3, 0}: 0,
		{1, 1}: 0, {2, 1}: 0, {3, 1}: 0, // detour row
	}}
	start := Point3{X: 0, Y: 0, Z: 0}
	goal := Point3{X: 3, Y: 0, Z: 0}

	path := FindPath(nil, start, goal, field)
	assertLegalFightSteps(t, start, path)
	for _, p := range path {
		if p.X == 2 && p.Y == 0 {
			t.Errorf("path entered the height-blocked cell (2,0)")
		}
	}
	last := path[len(path)-1]
	if last.X != goal.X || last.Y != goal.Y {
		t.Errorf("path ends at (%d,%d), want goal (%d,%d)", last.X, last.Y, goal.X, goal.Y)
	}
}

// TestFindPath_RejectsStepExceedingAscendLimit confirms a step whose
// altitude delta exceeds maxAscend is not taken -- a too-high ledge is
// impassable even though the cell is "walkable".
func TestFindPath_RejectsStepExceedingAscendLimit(t *testing.T) {
	// (1,0) is a cliff maxAscend+1 above the start; the only route to the
	// goal at (2,0) would go through it, so no path should exist.
	field := heightField{altitude: map[[2]int32]int16{
		{0, 0}: 0,
		{1, 0}: int16(maxAscend + 1), // too high to ascend onto in one step
		{2, 0}: 0,
	}}
	start := Point3{X: 0, Y: 0, Z: 0}
	goal := Point3{X: 2, Y: 0, Z: 0}

	if path := FindPath(nil, start, goal, field); path != nil {
		t.Errorf("expected no path (cliff exceeds maxAscend=%d), got %v", maxAscend, path)
	}
}

// TestFindPath_RejectsStepExceedingDescendLimit is the mirror: a drop
// deeper than maxDescend is impassable.
func TestFindPath_RejectsStepExceedingDescendLimit(t *testing.T) {
	field := heightField{altitude: map[[2]int32]int16{
		{0, 0}: 0,
		{1, 0}: int16(-(maxDescend + 1)), // too deep to descend into in one step
		{2, 0}: 0,
	}}
	start := Point3{X: 0, Y: 0, Z: 0}
	goal := Point3{X: 2, Y: 0, Z: 0}

	if path := FindPath(nil, start, goal, field); path != nil {
		t.Errorf("expected no path (drop exceeds maxDescend=%d), got %v", maxDescend, path)
	}
}

// TestFindPath_AllowsStepWithinAscendLimitAndCarriesAltitude confirms a
// legal climb (delta <= maxAscend) is taken AND the resolved step carries
// the arrival altitude (so the mover's Z tracks the terrain, not the
// requested Z).
func TestFindPath_AllowsStepWithinAscendLimitAndCarriesAltitude(t *testing.T) {
	field := heightField{altitude: map[[2]int32]int16{
		{0, 0}: 0,
		{1, 0}: int16(maxAscend), // exactly the limit -> allowed
	}}
	start := Point3{X: 0, Y: 0, Z: 0}
	goal := Point3{X: 1, Y: 0, Z: 0}

	path := FindPath(nil, start, goal, field)
	if len(path) != 1 {
		t.Fatalf("expected a 1-step climb, got %v", path)
	}
	if path[0].Z != int16(maxAscend) {
		t.Errorf("arrival Z = %d, want %d (step must carry the terrain altitude, not the requested Z)", path[0].Z, maxAscend)
	}
}

// TestValidateClientPath_RejectsHeightBlockedStep confirms the client-path
// validator (server-authority gate) also rejects a step into a
// height-blocked cell and a step exceeding the ascend limit.
func TestValidateClientPath_RejectsHeightBlockedStep(t *testing.T) {
	field := heightField{altitude: map[[2]int32]int16{
		{5, 5}: 0,
		{6, 5}: int16(maxAscend + 1), // reachable in X/Y but too high
		{5, 6}: 0,                    // a walkable neighbor at level ground
	}}
	start := Point3{X: 5, Y: 5, Z: 0}

	// Step onto the too-high cliff -> rejected.
	if got := ValidateClientPath(nil, start, []Point3{{X: 6, Y: 5, Z: 0}}, field); got != nil {
		t.Errorf("expected nil for a step exceeding the ascend limit, got %v", got)
	}
	// Step onto a hole (no surface at (7,5)) -> rejected.
	if got := ValidateClientPath(nil, start, []Point3{{X: 5, Y: 6, Z: 0}, {X: 6, Y: 6, Z: 0}}, field); got != nil {
		t.Errorf("expected nil for a step into a height-blocked hole, got %v", got)
	}
	// A legal level-ground step is still accepted, with its altitude filled.
	got := ValidateClientPath(nil, start, []Point3{{X: 5, Y: 6, Z: 0}}, field)
	if got == nil || len(got) != 1 || got[0].Z != 0 {
		t.Errorf("expected the legal level step accepted with Z=0, got %v", got)
	}
}
