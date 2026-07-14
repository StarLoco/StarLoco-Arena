package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

func TestHasLineOfSight_NoMapDataIsPermissive(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	// f.mapData is nil by default in this test helper.
	if !f.hasLineOfSight(Point3{X: 0, Y: 0}, Point3{X: 10, Y: 10}) {
		t.Error("hasLineOfSight with no map data attached should always be permissive (true)")
	}
}

func TestHasLineOfSight_SameCellAlwaysVisible(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	if !f.hasLineOfSight(Point3{X: 5, Y: 5}, Point3{X: 5, Y: 5}) {
		t.Error("a cell always has line of sight to itself")
	}
}

// TestGenerateLOSCellInputs_AdjacentCellsCrossOnce confirms two adjacent
// cells produce exactly ONE crossing: entering the destination cell
// itself across the shared edge (the reference's DDA walk is NOT
// "strictly between, excluding both endpoints" like the old Bresenham
// helper it replaces -- it deliberately checks whether sight can enter
// the destination cell too, complementing the separate end-validity
// check, which asks a different question: whether the destination's own
// ground is targetable).
func TestGenerateLOSCellInputs_AdjacentCellsCrossOnce(t *testing.T) {
	in := generateLOSCellInputs(Point3{X: 0, Y: 0}, Point3{X: 1, Y: 0})
	want := []losCellInput{{pos: Point3{X: 1, Y: 0}, dir: gamedata.LOSDirSouthEast}}
	if len(in) != len(want) || in[0] != want[0] {
		t.Errorf("got %v, want %v", in, want)
	}
}

// TestGenerateLOSCellInputs_StraightLineCrossesEveryBoundaryUpToDest
// walks a straight +X line and checks the emitted cell/direction sequence
// matches a single-axis DDA walk: one SouthEast-tagged crossing per
// integer X from origin+1 up to and including the destination.
func TestGenerateLOSCellInputs_StraightLineCrossesEveryBoundaryUpToDest(t *testing.T) {
	in := generateLOSCellInputs(Point3{X: 0, Y: 0, Z: 0}, Point3{X: 3, Y: 0, Z: 0})
	want := []losCellInput{
		{pos: Point3{X: 1, Y: 0, Z: 0}, dir: gamedata.LOSDirSouthEast},
		{pos: Point3{X: 2, Y: 0, Z: 0}, dir: gamedata.LOSDirSouthEast},
		{pos: Point3{X: 3, Y: 0, Z: 0}, dir: gamedata.LOSDirSouthEast},
	}
	if len(in) != len(want) {
		t.Fatalf("got %d crossings %v, want %d %v", len(in), in, len(want), want)
	}
	for i := range want {
		if in[i] != want[i] {
			t.Errorf("crossing[%d] = %+v, want %+v", i, in[i], want[i])
		}
	}
}

// TestGenerateLOSCellInputs_NegativeAxisUsesOppositeDirection confirms
// the direction flips (SouthEast -> NorthWest) when walking the X axis in
// the negative direction, matching Direction8's from/to pairing, and ends
// exactly on the destination cell.
func TestGenerateLOSCellInputs_NegativeAxisUsesOppositeDirection(t *testing.T) {
	in := generateLOSCellInputs(Point3{X: 3, Y: 0, Z: 0}, Point3{X: 0, Y: 0, Z: 0})
	want := []losCellInput{
		{pos: Point3{X: 2, Y: 0, Z: 0}, dir: gamedata.LOSDirNorthWest},
		{pos: Point3{X: 1, Y: 0, Z: 0}, dir: gamedata.LOSDirNorthWest},
		{pos: Point3{X: 0, Y: 0, Z: 0}, dir: gamedata.LOSDirNorthWest},
	}
	if len(in) != len(want) {
		t.Fatalf("got %d crossings %v, want %d %v", len(in), in, len(want), want)
	}
	for i := range want {
		if in[i] != want[i] {
			t.Errorf("crossing[%d] = %+v, want %+v", i, in[i], want[i])
		}
	}
}

// TestGenerateLOSCellInputs_YAxisUsesSouthWestNorthEast confirms the Y
// axis maps to the other direction pair (SouthWest/NorthEast), not the X
// axis's SouthEast/NorthWest.
func TestGenerateLOSCellInputs_YAxisUsesSouthWestNorthEast(t *testing.T) {
	in := generateLOSCellInputs(Point3{X: 0, Y: 0, Z: 0}, Point3{X: 0, Y: 2, Z: 0})
	want := []losCellInput{
		{pos: Point3{X: 0, Y: 1, Z: 0}, dir: gamedata.LOSDirSouthWest},
		{pos: Point3{X: 0, Y: 2, Z: 0}, dir: gamedata.LOSDirSouthWest},
	}
	if len(in) != len(want) {
		t.Fatalf("got %d crossings %v, want %d %v", len(in), in, len(want), want)
	}
	for i := range want {
		if in[i] != want[i] {
			t.Errorf("crossing[%d] = %+v, want %+v", i, in[i], want[i])
		}
	}
}

// TestGenerateLOSCellInputs_ZAxisUsesTopBottom confirms an altitude-only
// change (X/Y unchanged) produces Top/Bottom-tagged crossings -- this is
// the axis the OLD 2D Bresenham implementation completely ignored, so
// this pins the concrete behavioral fix: LOS now genuinely depends on
// altitude, not just the flat X/Y projection.
func TestGenerateLOSCellInputs_ZAxisUsesTopBottom(t *testing.T) {
	up := generateLOSCellInputs(Point3{X: 0, Y: 0, Z: 0}, Point3{X: 0, Y: 0, Z: 2})
	wantUp := []losCellInput{
		{pos: Point3{X: 0, Y: 0, Z: 1}, dir: gamedata.LOSDirTop},
		{pos: Point3{X: 0, Y: 0, Z: 2}, dir: gamedata.LOSDirTop},
	}
	if len(up) != len(wantUp) || up[0] != wantUp[0] || up[1] != wantUp[1] {
		t.Errorf("ascending Z crossings = %v, want %v", up, wantUp)
	}
	down := generateLOSCellInputs(Point3{X: 0, Y: 0, Z: 2}, Point3{X: 0, Y: 0, Z: 0})
	wantDown := []losCellInput{
		{pos: Point3{X: 0, Y: 0, Z: 1}, dir: gamedata.LOSDirBottom},
		{pos: Point3{X: 0, Y: 0, Z: 0}, dir: gamedata.LOSDirBottom},
	}
	if len(down) != len(wantDown) || down[0] != wantDown[0] || down[1] != wantDown[1] {
		t.Errorf("descending Z crossings = %v, want %v", down, wantDown)
	}
}

// TestGenerateLOSCellInputs_DiagonalLineCrossesBothAxesIndependently
// walks a diagonal line and confirms crossings are generated for BOTH the
// X and Y axes independently (the reference's real DDA, unlike a single
// merged 2D Bresenham line, walks each axis on its own pass, so a 2-cell
// diagonal produces 2 X-axis + 2 Y-axis crossings, not a single merged
// diagonal sequence).
func TestGenerateLOSCellInputs_DiagonalLineCrossesBothAxesIndependently(t *testing.T) {
	in := generateLOSCellInputs(Point3{X: 0, Y: 0, Z: 0}, Point3{X: 2, Y: 2, Z: 0})
	var seenX, seenY int
	for _, c := range in {
		switch c.dir {
		case gamedata.LOSDirSouthEast, gamedata.LOSDirNorthWest:
			seenX++
		case gamedata.LOSDirSouthWest, gamedata.LOSDirNorthEast:
			seenY++
		}
	}
	if seenX != 2 || seenY != 2 {
		t.Errorf("diagonal (0,0)->(2,2) crossings = %v, want exactly 2 X-axis + 2 Y-axis crossings", in)
	}
}

// TestGenerateLOSCellInputs_ZeroDeltaAxisContributesNoCrossings confirms
// an axis that doesn't change between from/to (e.g. Y here) never emits a
// crossing, regardless of how far the other axes travel.
func TestGenerateLOSCellInputs_ZeroDeltaAxisContributesNoCrossings(t *testing.T) {
	in := generateLOSCellInputs(Point3{X: 0, Y: 5, Z: 0}, Point3{X: 4, Y: 5, Z: 0})
	for _, c := range in {
		if c.pos.Y != 5 {
			t.Errorf("crossing %+v has Y != 5 even though Y never changes between from/to", c)
		}
		if c.dir == gamedata.LOSDirSouthWest || c.dir == gamedata.LOSDirNorthEast {
			t.Errorf("crossing %+v has a Y-axis direction even though Y never changes", c)
		}
	}
}

// TestHasLineOfSight_DirectionSensitiveBlocking is the end-to-end proof
// that hasLineOfSight now respects the SPECIFIC direction a wall blocks,
// not just "is there any solid cell on the line" -- the concrete
// behavioral improvement over the old approximation. A wall between
// (1,0) and the target blocks sight only from its SouthEast-facing edge;
// approaching from the (only) direction that crosses that edge is
// blocked, and the same wall cell with the opposite (open) edge is not.
func TestHasLineOfSight_DirectionSensitiveBlocking(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)

	blockingWall := gamedata.MapCellFact{
		Altitude: 0, Walkable: false, Height: 3,
		LineOfSight1: false, LineOfSight3: true, LineOfSight5: true, LineOfSight7: true,
		LineOfSightTop: true, LineOfSightBottom: true,
	}
	openWall := blockingWall
	openWall.LineOfSight1 = true

	m := gamedata.NewMapForTest(map[[2]int32][]gamedata.MapCellFact{
		{0, 0}: {{Altitude: 0, Walkable: true}},
		{1, 0}: {blockingWall}, // sits directly between (0,0) and (2,0)
		{2, 0}: {{Altitude: 0, Walkable: true}},
	})
	f.SetMapData(m)
	if f.hasLineOfSight(Point3{X: 0, Y: 0}, Point3{X: 2, Y: 0}) {
		t.Error("a wall blocking its SouthEast edge should block a +X line of sight crossing it")
	}

	m2 := gamedata.NewMapForTest(map[[2]int32][]gamedata.MapCellFact{
		{0, 0}: {{Altitude: 0, Walkable: true}},
		{1, 0}: {openWall}, // same wall, SouthEast edge now open
		{2, 0}: {{Altitude: 0, Walkable: true}},
	})
	f.SetMapData(m2)
	if !f.hasLineOfSight(Point3{X: 0, Y: 0}, Point3{X: 2, Y: 0}) {
		t.Error("a wall with an open SouthEast edge should not block a +X line of sight crossing it")
	}
}
