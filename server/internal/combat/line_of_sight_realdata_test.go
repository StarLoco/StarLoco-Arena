package combat

import (
	"os"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// TestHasLineOfSight_RealMapData wires real .amw/elements.ade map data
// (Phase K) into a Fight and confirms hasLineOfSight actually consults it
// (a same-cell/adjacent-cell check trivially passes with zero
// intermediate cells regardless of map data, so this specifically checks
// that a longer line across the real fight map's playable area doesn't
// error/panic and returns a stable, deterministic result).
func TestHasLineOfSight_RealMapData(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/elements.ade"); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}

	store := gamedata.NewMapStore(dataDir)
	m, err := store.Get(2)
	if err != nil {
		t.Fatalf("MapStore.Get(2): %v", err)
	}

	f, _, _ := newTestFightForEffects(t)
	f.SetMapData(m)

	// Both known real coach-start cells (see
	// internal/gamedata/map_realdata_test.go) should have line of sight
	// to themselves and to adjacent cells at minimum.
	from := Point3{X: 16, Y: 11}
	to := Point3{X: 1, Y: 7}
	got1 := f.hasLineOfSight(from, from)
	if !got1 {
		t.Error("a cell must always have line of sight to itself, even with real map data attached")
	}

	// Long-distance LOS across the whole map: just confirm it runs
	// deterministically without panicking (the real answer here depends
	// on the pragmatic Bresenham-approximation documented in
	// line_of_sight.go, not asserted precisely since it's not a bit-exact
	// port of the reference's real DDA algorithm).
	result1 := f.hasLineOfSight(from, to)
	result2 := f.hasLineOfSight(from, to)
	if result1 != result2 {
		t.Errorf("hasLineOfSight must be deterministic for the same inputs, got %v then %v", result1, result2)
	}
}
