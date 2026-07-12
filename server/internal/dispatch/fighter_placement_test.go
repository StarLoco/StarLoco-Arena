package dispatch

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/gamedata"
)

// realDataDeps builds a *Deps with only Data populated, pointed at the
// project's real game-data directory, skipping the test if it isn't
// present (mirrors internal/gamedata's own real-data test skip pattern).
func realDataDeps(t *testing.T) *Deps {
	t.Helper()
	// internal/dispatch is two directories under server/, so the real data
	// dir (server/data) is ../../data from here (see AGENTS.md).
	dataDir := filepath.Join("..", "..", "data")
	if _, err := os.Stat(filepath.Join(dataDir, "elements.ade")); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}
	return &Deps{Data: gamedata.NewStore(dataDir)}
}

// TestResolveFighterPlacementCellsUsesRealFightStartCells verifies the fix
// for a reported bug: fighters were previously placed at
// (anchor.X, anchor.Y+i, anchor.Z) -- i.e. stacked on/adjacent to the
// COACH's own pedestal cell -- instead of using the real per-team
// FightStartPointElement placement zone. This test confirms
// resolveFighterPlacementCells now returns distinct cells sourced from
// Map.FightStartCells(), not the coach anchor, for a realistic team size.
func TestResolveFighterPlacementCellsUsesRealFightStartCells(t *testing.T) {
	deps := realDataDeps(t)
	m, err := deps.Data.Maps.Get(defaultFightMapID)
	if err != nil {
		t.Fatalf("Maps.Get(defaultFightMapID): %v", err)
	}

	anchor := combat.Point3{X: 16, Y: 11, Z: -8} // team-side-0 coach anchor
	const teamSideByte = 0
	const count = 6 // a realistic team roster size, well under the ~10 real cells available

	got := resolveFighterPlacementCells(deps, defaultFightMapID, anchor, teamSideByte, count)
	if len(got) != count {
		t.Fatalf("len(resolveFighterPlacementCells) = %d, want %d", len(got), count)
	}

	realCells := make(map[[2]int32]bool)
	for _, c := range m.FightStartCells()[teamSideByte] {
		realCells[c] = true
	}
	if len(realCells) == 0 {
		t.Fatal("expected real FightStartCells() data for team side 0")
	}

	seen := map[[2]int32]bool{}
	for i, pos := range got {
		key := [2]int32{pos.X, pos.Y}
		if pos == anchor {
			t.Errorf("fighter %d placed exactly on the coach's own anchor cell %+v -- expected a distinct fighter placement cell", i, anchor)
		}
		if seen[key] {
			t.Errorf("fighter %d reused cell %v already assigned to another fighter", i, key)
		}
		seen[key] = true
		if !realCells[key] {
			t.Errorf("fighter %d placed at %v, which is not among the real FightStartCells() pool %v", i, key, realCells)
		}
	}
}

// TestResolveFighterPlacementCellsFallsBackWhenRosterExceedsRealCells
// verifies that when a team has more fighters than real placement cells
// exist for that side, the remainder falls back to
// Map.NearbyWalkableCells rather than crashing, erroring, or reusing a
// cell (per the user's explicit request: "if you don't know where, then
// use random walkable cell").
func TestResolveFighterPlacementCellsFallsBackWhenRosterExceedsRealCells(t *testing.T) {
	deps := realDataDeps(t)
	m, err := deps.Data.Maps.Get(defaultFightMapID)
	if err != nil {
		t.Fatalf("Maps.Get(defaultFightMapID): %v", err)
	}
	const teamSideByte = 0
	realCount := len(m.FightStartCells()[teamSideByte])
	if realCount == 0 {
		t.Fatal("expected at least one real FightStartCells() entry for team side 0 to make this test meaningful")
	}

	anchor := combat.Point3{X: 16, Y: 11, Z: -8}
	count := realCount + 4 // deliberately exceed the real pool

	got := resolveFighterPlacementCells(deps, defaultFightMapID, anchor, teamSideByte, count)
	if len(got) != count {
		t.Fatalf("len(resolveFighterPlacementCells) = %d, want %d", len(got), count)
	}

	seen := map[[2]int32]bool{}
	for i, pos := range got {
		key := [2]int32{pos.X, pos.Y}
		if seen[key] {
			t.Errorf("fighter %d reused cell %v -- every fighter must get a distinct starting cell", i, key)
		}
		seen[key] = true
	}
}

// TestResolveFighterPlacementCellsFallsBackToAnchorOffsetWithoutMapData
// verifies the historical anchor+Y-offset behavior is preserved as a last
// resort when no real map data is attached at all (e.g. a dev/test setup
// missing the game's data files) -- a fight must never fail to place
// fighters just because optional real map data is unavailable.
func TestResolveFighterPlacementCellsFallsBackToAnchorOffsetWithoutMapData(t *testing.T) {
	deps := &Deps{Data: nil}
	anchor := combat.Point3{X: 16, Y: 11, Z: -8}

	got := resolveFighterPlacementCells(deps, defaultFightMapID, anchor, 0, 3)
	want := []combat.Point3{
		{X: 16, Y: 11, Z: -8},
		{X: 16, Y: 12, Z: -8},
		{X: 16, Y: 13, Z: -8},
	}
	if len(got) != len(want) {
		t.Fatalf("len = %d, want %d", len(got), len(want))
	}
	for i := range want {
		if got[i] != want[i] {
			t.Errorf("position %d = %+v, want %+v", i, got[i], want[i])
		}
	}
}

// TestResolveFighterPlacementCellsZeroCountReturnsEmpty is a basic
// degenerate-input guard test.
func TestResolveFighterPlacementCellsZeroCountReturnsEmpty(t *testing.T) {
	deps := &Deps{Data: nil}
	got := resolveFighterPlacementCells(deps, defaultFightMapID, combat.Point3{}, 0, 0)
	if len(got) != 0 {
		t.Errorf("resolveFighterPlacementCells(count=0) = %v, want empty", got)
	}
}

// TestTopmostWalkableSpotUsesStandingAltitude verifies the fix for a
// reported bug ("fighter renders sunk under the map during placement, only
// pops up after a client redraw"): the client renders a mobile at
// `altitude * elevationUnit` px and expects that altitude to be the
// TOP-of-block STANDING altitude (surface base Altitude + Height), NOT the
// raw base Altitude. Sending the raw base drops the fighter Height
// altitude-units into the terrain.
//
// Real cell (4,9) on fightMapID=2 (team side 1's FightStartCells() pool)
// has walkable surfaces {base=-11, height=7} and {base=-4, height=0} --
// both with standing altitude -4 (= -11+7 = -4+0). The reference server
// itself hardcodes exactly -4 for fighter placement (Fight.java:274,289).
// topmostWalkableSpot must therefore return Z=-4, NOT the old raw-base -11
// (which was 7 units too low and caused the sinking).
func TestTopmostWalkableSpotUsesStandingAltitude(t *testing.T) {
	deps := realDataDeps(t)
	m, err := deps.Data.Maps.Get(defaultFightMapID)
	if err != nil {
		t.Fatalf("Maps.Get(defaultFightMapID): %v", err)
	}

	cell := [2]int32{4, 9}
	surfaces := m.SurfacesAt(cell[0], cell[1])
	if len(surfaces) == 0 {
		t.Skipf("no real surface data for cell %v, skipping", cell)
	}

	// Compute the expected standing altitude (highest base+height among
	// walkable surfaces) independently.
	var wantZ int16
	found := false
	for _, s := range surfaces {
		if !s.Walkable {
			continue
		}
		standing := s.Altitude + int16(s.Height)
		if !found || standing > wantZ {
			wantZ, found = standing, true
		}
	}
	if !found {
		t.Fatalf("no walkable surface found at %v, surfaces=%+v", cell, surfaces)
	}
	// For this specific real cell, the standing altitude must be -4 (the
	// exact value the reference server hardcodes for fighter placement).
	if wantZ != -4 {
		t.Errorf("computed standing altitude for cell %v = %d, want -4 (matches reference Fight.java); surfaces=%+v", cell, wantZ, surfaces)
	}

	// Deliberately pass a fallbackZ far from the real surface to prove
	// topmostWalkableSpot ignores it entirely when real data exists.
	got := topmostWalkableSpot(m, cell, -20)
	if got.Z != wantZ {
		t.Errorf("topmostWalkableSpot(%v) = %+v, want Z=%d (standing altitude = base+height)", cell, got, wantZ)
	}
	if got.X != cell[0] || got.Y != cell[1] {
		t.Errorf("topmostWalkableSpot(%v) = %+v, want X/Y unchanged", cell, got)
	}
}

// TestTopmostWalkableSpotFallsBackWhenNoWalkableSurface verifies the
// defensive fallback: a cell with no walkable surface at all (or no map
// data) returns fallbackZ unchanged rather than a zero-value/crash.
func TestTopmostWalkableSpotFallsBackWhenNoWalkableSurface(t *testing.T) {
	deps := realDataDeps(t)
	m, err := deps.Data.Maps.Get(defaultFightMapID)
	if err != nil {
		t.Fatalf("Maps.Get(defaultFightMapID): %v", err)
	}

	// A cell far outside any parsed chunk has no map data at all.
	cell := [2]int32{99999, 99999}
	got := topmostWalkableSpot(m, cell, -42)
	if got.Z != -42 {
		t.Errorf("topmostWalkableSpot for an unmapped cell = %+v, want Z=-42 (fallbackZ unchanged)", got)
	}
	if got.X != cell[0] || got.Y != cell[1] {
		t.Errorf("topmostWalkableSpot(%v) = %+v, want X/Y unchanged", cell, got)
	}
}

// TestResolveFightCameraCenterMidpointWithoutMapData verifies that without
// real map data, the initial fight-camera focus falls back to the midpoint
// between the two coach spots (so the camera still lands roughly in the
// middle of the arena rather than on one coach).
func TestResolveFightCameraCenterMidpointWithoutMapData(t *testing.T) {
	deps := &Deps{Data: nil}
	coachA := combat.Point3{X: 16, Y: 11, Z: -3}
	coachB := combat.Point3{X: 2, Y: 7, Z: -3}

	got := resolveFightCameraCenter(deps, defaultFightMapID, coachA, coachB)
	want := combat.Point3{X: 9, Y: 9, Z: -3} // ((16+2)/2, (11+7)/2)
	if got != want {
		t.Errorf("resolveFightCameraCenter (no map data) = %+v, want %+v (coach-spot midpoint)", got, want)
	}
}

// TestResolveFightCameraCenterUsesArenaCentroid verifies that with real map
// data the camera focus is the centroid of ALL fight-start (spawn-area)
// cells across both teams -- i.e. the middle of the battlefield -- and is
// NOT sitting on either coach's own spot. This is the fix for "the camera
// focuses on the coach position instead of the center of the map".
func TestResolveFightCameraCenterUsesArenaCentroid(t *testing.T) {
	deps := realDataDeps(t)
	m, err := deps.Data.Maps.Get(defaultFightMapID)
	if err != nil {
		t.Fatalf("Maps.Get(defaultFightMapID): %v", err)
	}

	// Independently compute the expected centroid of every fight-start cell
	// on both team sides.
	var sumX, sumY, n int64
	for _, cells := range m.FightStartCells() {
		for _, c := range cells {
			sumX += int64(c[0])
			sumY += int64(c[1])
			n++
		}
	}
	if n == 0 {
		t.Skip("no real FightStartCells() data, skipping centroid check")
	}
	wantX, wantY := int32(sumX/n), int32(sumY/n)

	coachA, coachB := resolveCoachStartSpots(deps, defaultFightMapID)
	got := resolveFightCameraCenter(deps, defaultFightMapID, coachA, coachB)

	if got.X != wantX || got.Y != wantY {
		t.Errorf("camera center = (%d,%d), want arena centroid (%d,%d)", got.X, got.Y, wantX, wantY)
	}
	// The whole point of the fix: the camera must NOT be on a coach spot.
	if (got.X == coachA.X && got.Y == coachA.Y) || (got.X == coachB.X && got.Y == coachB.Y) {
		t.Errorf("camera center %+v coincides with a coach spot (A=%+v B=%+v); expected the arena center", got, coachA, coachB)
	}
}
