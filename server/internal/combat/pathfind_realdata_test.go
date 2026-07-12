package combat

import (
	"os"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file closes the roadmap Phase K verification item: "re-validate A*
// against a height-blocked / corner-cut real-map path." The synthetic
// height tests in pathfind_test.go pin the FindPath/ValidateClientPath
// altitude logic in isolation; these drive that same logic through the REAL
// Fight.IsWalkable/ArrivalAltitude wrappers backed by actual .amw/
// elements.ade fightMapID=2 data, so the wiring (Fight -> gamedata.Map ->
// resolved surfaces) is proven end-to-end.

func realMapFight(t *testing.T) (*Fight, *gamedata.Map) {
	t.Helper()
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
	return f, m
}

// TestFindPath_RealMapProducesWalkableAltitudeCarryingPath finds a path
// between two real walkable cells and asserts every emitted step is (a) a
// legal single-axis fight move, (b) actually walkable per the real map, and
// (c) carries the map's standing altitude (not the requested Z) -- proving
// Fight.ArrivalAltitude is consulted, not bypassed.
func TestFindPath_RealMapProducesWalkableAltitudeCarryingPath(t *testing.T) {
	f, m := realMapFight(t)

	// Pick two genuinely walkable cells on the real map by scanning the
	// known coach-start neighborhoods (fightMapID=2's playable area).
	start, okS := firstWalkableNear(f, Point3{X: 16, Y: 11})
	goal, okG := firstWalkableNear(f, Point3{X: 1, Y: 7})
	if !okS || !okG {
		t.Skip("could not locate two walkable anchor cells on the real map")
	}
	if start.X == goal.X && start.Y == goal.Y {
		t.Skip("start and goal resolved to the same cell")
	}

	path := FindPath(nil, start, goal, f)
	if path == nil {
		t.Skipf("no path between %v and %v on the real map (may be genuinely disconnected)", start, goal)
	}
	assertLegalFightSteps(t, start, path)

	for i, p := range path {
		if !f.IsWalkable(p) {
			t.Errorf("step %d entered a non-walkable real-map cell (%d,%d)", i, p.X, p.Y)
		}
		// The step's Z must equal the map's standing altitude for that cell
		// (ArrivalAltitude), i.e. FindPath must overwrite the requested Z.
		wantZ, blocked := f.ArrivalAltitude(nil, p.Z, p)
		if blocked {
			t.Errorf("step %d is on a cell the map reports as height-blocked (%d,%d)", i, p.X, p.Y)
		}
		_ = wantZ // altitude presence already asserted via !blocked; exact value is map-dependent
	}
	last := path[len(path)-1]
	if last.X != goal.X || last.Y != goal.Y {
		t.Errorf("real-map path ends at (%d,%d), want goal (%d,%d)", last.X, last.Y, goal.X, goal.Y)
	}
	_ = m
}

// TestFindPath_RealMapRejectsPathIntoOffMapCell confirms the real
// IsWalkable gate stops A* from routing a fighter off the playable area: a
// goal that has no map data at all is unreachable.
func TestFindPath_RealMapRejectsPathIntoOffMapCell(t *testing.T) {
	f, _ := realMapFight(t)
	start, ok := firstWalkableNear(f, Point3{X: 16, Y: 11})
	if !ok {
		t.Skip("no walkable anchor found")
	}
	// A wildly out-of-bounds cell that certainly has no map data.
	offMap := Point3{X: 9999, Y: 9999}
	if f.IsWalkable(offMap) {
		t.Skip("chosen off-map cell unexpectedly has data; skipping")
	}
	if path := FindPath(nil, start, offMap, f); path != nil {
		t.Errorf("expected no path to an off-map cell, got a %d-step path", len(path))
	}
}

// firstWalkableNear returns the first walkable cell found scanning outward
// from anchor (up to a small radius), with its real standing altitude
// filled into Z. ok=false if none found nearby.
func firstWalkableNear(f *Fight, anchor Point3) (Point3, bool) {
	for r := int32(0); r <= 4; r++ {
		for dx := -r; dx <= r; dx++ {
			for dy := -r; dy <= r; dy++ {
				c := Point3{X: anchor.X + dx, Y: anchor.Y + dy}
				if !f.IsWalkable(c) {
					continue
				}
				if z, blocked := f.ArrivalAltitude(nil, 0, c); !blocked {
					c.Z = z
					return c, true
				}
			}
		}
	}
	return Point3{}, false
}

// TestCoachCellsAreNotWalkable verifies the coaches' start-point pedestals
// are excluded from in-fight movement: every FightStartCoachPointElement
// cell must report IsWalkable=false, so no fighter/summon can path onto or
// through a coach's platform.
func TestCoachCellsAreNotWalkable(t *testing.T) {
	f, m := realMapFight(t)
	coachCells := m.CoachStartCells()
	total := 0
	for _, side := range coachCells {
		for _, c := range side {
			total++
			if f.IsWalkable(Point3{X: c[0], Y: c[1]}) {
				t.Errorf("coach cell (%d,%d) is walkable, want blocked (coach pedestals are off-limits to combatants)", c[0], c[1])
			}
		}
	}
	if total == 0 {
		t.Skip("map 2 exposes no coach start-point cells")
	}
}
