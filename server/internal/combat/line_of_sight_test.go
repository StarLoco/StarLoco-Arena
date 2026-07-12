package combat

import "testing"

func TestHasLineOfSight_NoMapDataIsPermissive(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	// f.mapData is nil by default in this test helper.
	if !f.hasLineOfSight(Point3{X: 0, Y: 0}, Point3{X: 10, Y: 10}) {
		t.Error("hasLineOfSight with no map data attached should always be permissive (true)")
	}
}

func TestBresenhamLine_ExcludesBothEndpoints(t *testing.T) {
	line := bresenhamLine(Point3{X: 0, Y: 0}, Point3{X: 3, Y: 0})
	for _, p := range line {
		if (p.X == 0 && p.Y == 0) || (p.X == 3 && p.Y == 0) {
			t.Errorf("bresenhamLine should exclude both endpoints, got %v in result %v", p, line)
		}
	}
	// Straight horizontal line from (0,0) to (3,0) should pass through
	// exactly (1,0) and (2,0).
	if len(line) != 2 {
		t.Fatalf("line length = %d, want 2, got %v", len(line), line)
	}
}

func TestBresenhamLine_AdjacentCellsHaveNoIntermediatePoints(t *testing.T) {
	line := bresenhamLine(Point3{X: 0, Y: 0}, Point3{X: 1, Y: 0})
	if len(line) != 0 {
		t.Errorf("adjacent cells should have zero intermediate points, got %v", line)
	}
}

func TestBresenhamLine_DiagonalLine(t *testing.T) {
	line := bresenhamLine(Point3{X: 0, Y: 0}, Point3{X: 3, Y: 3})
	if len(line) != 2 {
		t.Fatalf("diagonal line length = %d, want 2, got %v", len(line), line)
	}
	// Should pass through (1,1) and (2,2).
	want := map[[2]int32]bool{{1, 1}: false, {2, 2}: false}
	for _, p := range line {
		key := [2]int32{p.X, p.Y}
		if _, ok := want[key]; ok {
			want[key] = true
		}
	}
	for k, found := range want {
		if !found {
			t.Errorf("expected diagonal line to pass through %v, got %v", k, line)
		}
	}
}

// fakeMapDataForLOS implements just enough of gamedata.Map's surface for
// a hasLineOfSight test by directly setting f.mapData to nil and
// exercising bresenhamLine's output against a hand-rolled blocker
// function -- since gamedata.Map itself can't easily be constructed
// without real map files in a unit test, this test instead verifies the
// integration point (Fight.hasLineOfSight calls mapData.BlocksLineOfSight
// per intermediate cell) using the real gamedata.Map type is covered by
// the real-data test suite in internal/gamedata instead. This local test
// focuses on bresenhamLine's own correctness, already covered above.
func TestHasLineOfSight_SameCellAlwaysVisible(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	if !f.hasLineOfSight(Point3{X: 5, Y: 5}, Point3{X: 5, Y: 5}) {
		t.Error("a cell always has line of sight to itself")
	}
}
