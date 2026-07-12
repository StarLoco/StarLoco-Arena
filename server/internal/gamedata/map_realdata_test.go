package gamedata

import (
	"os"
	"testing"
)

// TestMapStore_RealFightMap loads the actual fightMapID=2 map data (see
// internal/dispatch/handlers_fight.go's fightMapID constant) and confirms:
//   - it loads without error
//   - both of this project's existing hardcoded coach-placement cells
//     ((16,11) and (1,7), see handlers_fight.go's buildEnterWorldInstance
//     calls) are found among the map's real FightStartCoachPointElement
//     cells, and each has AT LEAST ONE walkable surface at some altitude.
//
// NOTE: this test does NOT assert the walkable altitude equals the
// existing hardcoded Z=-3 from handlers_fight.go/combatPlacementSpotA/B --
// cross-checking against real data reveals -3 does not correspond to any
// walkable surface at either cell (the real walkable altitudes there are
// -12/-8 and -10/-6 respectively), confirming -3 was an arbitrary
// placeholder value invented before real map data existed, not a value
// derived from it. Wiring real spawn altitudes (querying
// Map.SurfacesAt/CoachStartCells instead of hardcoding Z) is a followup
// wiring task once this parser itself is trusted -- see
// docs/08-java-parity-roadmap.md Phase K.
func TestMapStore_RealFightMap(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/elements.ade"); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}

	store := NewMapStore(dataDir)
	m, err := store.Get(2)
	if err != nil {
		t.Fatalf("MapStore.Get(2): %v", err)
	}

	if !m.HasCell(16, 11) {
		t.Error("expected map data at cell (16,11)")
	}
	if !m.HasCell(1, 7) {
		t.Error("expected map data at cell (1,7)")
	}

	hasAnyWalkable := func(x, y int32) bool {
		for _, s := range m.SurfacesAt(x, y) {
			if s.Walkable {
				return true
			}
		}
		return false
	}
	if !hasAnyWalkable(16, 11) {
		t.Errorf("expected (16,11) to have at least one walkable surface, surfaces=%+v", m.SurfacesAt(16, 11))
	}
	if !hasAnyWalkable(1, 7) {
		t.Errorf("expected (1,7) to have at least one walkable surface, surfaces=%+v", m.SurfacesAt(1, 7))
	}

	found16_11, found1_7 := false, false
	for _, cells := range m.CoachStartCells() {
		for _, c := range cells {
			if c == [2]int32{16, 11} {
				found16_11 = true
			}
			if c == [2]int32{1, 7} {
				found1_7 = true
			}
		}
	}
	if !found16_11 || !found1_7 {
		t.Errorf("expected both hardcoded coach cells among CoachStartCells(), got %+v", m.CoachStartCells())
	}

	// A cell far outside any parsed chunk should have no map data at all.
	if m.HasCell(9999, 9999) {
		t.Error("expected no map data for an out-of-range cell")
	}
	if m.IsWalkable(9999, 9999, 0) {
		t.Error("expected IsWalkable=false for a cell with no map data")
	}
}

// TestMapStore_RealFightStartCellsDistinctFromCoachAnchor confirms the
// fix for a reported bug ("fighters all appear stacked on the coach's own
// spot during placement, and become permanently stuck once combat
// starts"): FightStartPointElement (kind 1000, the real per-team fighter
// placement zone) cells are genuinely distinct from -- not merely
// adjacent to -- the single FightStartCoachPointElement (kind 1001) coach
// anchor cell for the same team side, and every one of them resolves to
// at least one walkable surface (so a fighter placed there can actually
// path out once combat begins, unlike the old anchor+Y-offset formula
// which could land a fighter in an unreachable off-map pocket). See
// docs/08-java-parity-roadmap.md's write-up on this fix.
func TestMapStore_RealFightStartCellsDistinctFromCoachAnchor(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/elements.ade"); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}

	store := NewMapStore(dataDir)
	m, err := store.Get(2)
	if err != nil {
		t.Fatalf("MapStore.Get(2): %v", err)
	}

	fightCells := m.FightStartCells()
	coachCells := m.CoachStartCells()

	if len(fightCells) == 0 {
		t.Fatal("expected at least one team side with FightStartCells() data")
	}

	for side, cells := range fightCells {
		if len(cells) == 0 {
			t.Errorf("team side %d has an empty FightStartCells() entry", side)
			continue
		}
		coachAnchors := coachCells[side]
		seen := map[[2]int32]bool{}
		for _, c := range cells {
			if seen[c] {
				t.Errorf("team side %d: duplicate FightStartCells() entry %v", side, c)
			}
			seen[c] = true

			for _, anchor := range coachAnchors {
				if c == anchor {
					t.Errorf("team side %d: fighter placement cell %v is the same cell as the coach's own anchor %v -- expected genuinely distinct cells", side, c, anchor)
				}
			}

			hasWalkable := false
			for _, s := range m.SurfacesAt(c[0], c[1]) {
				if s.Walkable {
					hasWalkable = true
					break
				}
			}
			if !hasWalkable {
				t.Errorf("team side %d: fighter placement cell %v has no walkable surface, surfaces=%+v", side, c, m.SurfacesAt(c[0], c[1]))
			}
		}
	}
}

// TestMapStore_CachesResult confirms a second Get call for the same
// mapID returns the same *Map instance (no re-parse).
func TestMapStore_CachesResult(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/elements.ade"); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}

	store := NewMapStore(dataDir)
	m1, err := store.Get(2)
	if err != nil {
		t.Fatalf("first Get: %v", err)
	}
	m2, err := store.Get(2)
	if err != nil {
		t.Fatalf("second Get: %v", err)
	}
	if m1 != m2 {
		t.Error("expected cached Map instance on second Get call")
	}
}

// TestMapStore_UnknownMapIDReturnsError confirms a nonexistent mapID
// directory produces an error rather than a silently-empty Map.
func TestMapStore_UnknownMapIDReturnsError(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/elements.ade"); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}

	store := NewMapStore(dataDir)
	if _, err := store.Get(999999); err == nil {
		t.Error("expected error for nonexistent mapID directory")
	}
}
