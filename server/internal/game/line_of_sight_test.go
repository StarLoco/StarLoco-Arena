package game

import "testing"

func TestLineOfSightClear(t *testing.T) {
	flat := func(x, y int32) int16 { return 0 }
	from := Pos{X: 0, Y: 0}

	// Flat terrain is always visible (eye ray sits above the ground everywhere).
	if !lineOfSightClear(from, Pos{X: 10, Y: 0}, flat) {
		t.Error("flat horizontal line should have LoS")
	}
	if !lineOfSightClear(from, Pos{X: 6, Y: 6}, flat) {
		t.Error("flat diagonal line should have LoS")
	}
	// Adjacent cells: no intermediate cell to block.
	if !lineOfSightClear(Pos{X: 3, Y: 3}, Pos{X: 4, Y: 3}, flat) {
		t.Error("adjacent cells always have LoS")
	}

	bumpAt := func(bx int32, h int16) func(int32, int32) int16 {
		return func(x, y int32) int16 {
			if x == bx {
				return h
			}
			return 0
		}
	}
	// A low bump 3 above the floor does NOT block: the +4 eye offset clears it.
	if !lineOfSightClear(from, Pos{X: 10, Y: 0}, bumpAt(5, 3)) {
		t.Error("bump 3 below eye height should not block")
	}
	// Terrain exactly at eye height (4) grazes the ray → still visible.
	if !lineOfSightClear(from, Pos{X: 10, Y: 0}, bumpAt(5, 4)) {
		t.Error("terrain grazing the ray (==ray min) should be visible")
	}
	// Terrain 1 above eye height blocks.
	if lineOfSightClear(from, Pos{X: 10, Y: 0}, bumpAt(5, 5)) {
		t.Error("terrain above the ray should block LoS")
	}
	// A tall wall blocks.
	if lineOfSightClear(from, Pos{X: 10, Y: 0}, bumpAt(5, 40)) {
		t.Error("a tall wall should block LoS")
	}
	// Targeting the wall cell ITSELF (as the endpoint) is not blocked by it.
	if !lineOfSightClear(from, Pos{X: 5, Y: 0}, bumpAt(5, 40)) {
		t.Error("the target endpoint cell must be exempt from blocking")
	}
	// A pit / void (very low) never blocks.
	if !lineOfSightClear(from, Pos{X: 10, Y: 0}, bumpAt(5, losLowAltitude)) {
		t.Error("a pit should not block LoS")
	}
}

func TestArenaHasLineOfSight(t *testing.T) {
	// Flat south row (all altitude 0): clear.
	if !practiceArena.hasLineOfSight(Pos{X: 7, Y: 15}, Pos{X: 9, Y: 15}) {
		t.Error("flat south-row cells should have LoS")
	}
	// A vertical line passing over the raised interior cell (9,7) is blocked.
	// (9,7) is one of the map's scenery OBSTACLES (topology altitude 10, no
	// walkable ground): since B-048 those report an impassable pseudo-altitude so
	// a ray never clears them, matching the client, which sets the cell word's
	// LoS bit for exactly these cells.
	if !practiceArena.scenery(9, 7) {
		t.Fatal("test precondition: (9,7) should be a scenery obstacle")
	}
	if practiceArena.hasLineOfSight(Pos{X: 9, Y: 11}, Pos{X: 9, Y: 4}) {
		t.Error("line over the (9,7) obstacle should be blocked")
	}
}
