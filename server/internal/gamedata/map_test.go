package gamedata

import (
	"math/rand"
	"testing"
)

// newSyntheticMap builds a bare Map with an explicit set of walkable
// cells, for unit tests that don't need real .amw/elements.ade fixtures.
// Every cell not listed in walkable has no map data at all (matching
// HasCell's "no entry -- off the playable area" semantics).
func newSyntheticMap(walkable ...[2]int32) *Map {
	m := &Map{
		cells:           make(map[[2]int32][]MapCellFact),
		coachStartCells: make(map[byte][][2]int32),
		fightStartCells: make(map[byte][][2]int32),
	}
	for _, c := range walkable {
		m.cells[c] = []MapCellFact{{Altitude: 0, Walkable: true}}
	}
	return m
}

// newSyntheticMapWithSurfaces builds a bare Map with explicit per-cell
// surface lists, for tests that need to exercise altitude/height details.
func newSyntheticMapWithSurfaces(cells map[[2]int32][]MapCellFact) *Map {
	m := &Map{
		cells:           make(map[[2]int32][]MapCellFact),
		coachStartCells: make(map[byte][][2]int32),
		fightStartCells: make(map[byte][][2]int32),
	}
	for c, surfaces := range cells {
		m.cells[c] = surfaces
	}
	return m
}

// TestStandingAltitudeAtUsesBasePlusHeight verifies the "standing
// altitude = surface base Altitude + Height" convention (the top-of-block
// surface a mobile visibly stands on) that fixes the reported "fighter
// renders sunk under the map" bug -- see StandingAltitudeAt's doc comment
// and docs/08-java-parity-roadmap.md §8.17.
func TestStandingAltitudeAtUsesBasePlusHeight(t *testing.T) {
	m := newSyntheticMapWithSurfaces(map[[2]int32][]MapCellFact{
		// A raised walkable platform: base=-11, height=7 -> standing -4
		// (this is the exact real-data shape of fightMapID=2's cell (14,7),
		// and the -4 value the reference server hardcodes for fighters).
		{5, 5}: {{Altitude: -11, Walkable: true, Height: 7}},
		// Two walkable surfaces, both standing at -4 (-11+7 and -4+0).
		{6, 6}: {{Altitude: -11, Walkable: true, Height: 7}, {Altitude: -4, Walkable: true, Height: 0}},
		// Flat ground (height 0): standing == base.
		{7, 7}: {{Altitude: -8, Walkable: true, Height: 0}},
		// Non-walkable only: no standing surface.
		{8, 8}: {{Altitude: 3, Walkable: false, Height: 2}},
	})

	cases := []struct {
		cell      [2]int32
		wantZ     int16
		wantFound bool
	}{
		{[2]int32{5, 5}, -4, true},
		{[2]int32{6, 6}, -4, true},
		{[2]int32{7, 7}, -8, true},
		{[2]int32{8, 8}, 0, false},
		{[2]int32{9, 9}, 0, false}, // no data at all
	}
	for _, tc := range cases {
		z, found := m.StandingAltitudeAt(tc.cell[0], tc.cell[1])
		if found != tc.wantFound {
			t.Errorf("StandingAltitudeAt%v found = %v, want %v", tc.cell, found, tc.wantFound)
		}
		if found && z != tc.wantZ {
			t.Errorf("StandingAltitudeAt%v = %d, want %d", tc.cell, z, tc.wantZ)
		}
	}
}

func TestNearbyWalkableCellsFindsAnchorItself(t *testing.T) {
	m := newSyntheticMap([2]int32{5, 5})
	got := m.NearbyWalkableCells([2]int32{5, 5}, 1, nil)
	if len(got) != 1 || got[0] != ([2]int32{5, 5}) {
		t.Errorf("NearbyWalkableCells = %v, want [{5 5}]", got)
	}
}

func TestNearbyWalkableCellsExpandsOutwardWhenAnchorNotWalkable(t *testing.T) {
	// Anchor (0,0) itself has no map data; only a ring-1 neighbor is
	// walkable.
	m := newSyntheticMap([2]int32{1, 0})
	got := m.NearbyWalkableCells([2]int32{0, 0}, 1, nil)
	if len(got) != 1 || got[0] != ([2]int32{1, 0}) {
		t.Errorf("NearbyWalkableCells = %v, want [{1 0}]", got)
	}
}

func TestNearbyWalkableCellsReturnsUpToCountDistinctCells(t *testing.T) {
	m := newSyntheticMap(
		[2]int32{0, 0}, [2]int32{1, 0}, [2]int32{0, 1}, [2]int32{-1, 0}, [2]int32{0, -1},
	)
	got := m.NearbyWalkableCells([2]int32{0, 0}, 3, nil)
	if len(got) != 3 {
		t.Fatalf("len(NearbyWalkableCells) = %d, want 3", len(got))
	}
	seen := map[[2]int32]bool{}
	for _, c := range got {
		if seen[c] {
			t.Errorf("duplicate cell %v in result", c)
		}
		seen[c] = true
	}
}

func TestNearbyWalkableCellsReturnsFewerThanCountIfMapExhausted(t *testing.T) {
	m := newSyntheticMap([2]int32{0, 0})
	got := m.NearbyWalkableCells([2]int32{0, 0}, 10, nil)
	if len(got) != 1 {
		t.Errorf("len(NearbyWalkableCells) = %d, want 1 (only one walkable cell exists)", len(got))
	}
}

func TestNearbyWalkableCellsZeroCountReturnsNil(t *testing.T) {
	m := newSyntheticMap([2]int32{0, 0})
	if got := m.NearbyWalkableCells([2]int32{0, 0}, 0, nil); got != nil {
		t.Errorf("NearbyWalkableCells(count=0) = %v, want nil", got)
	}
}

func TestNearbyWalkableCellsIgnoresNonWalkableSurfaces(t *testing.T) {
	m := &Map{
		cells: map[[2]int32][]MapCellFact{
			{0, 0}: {{Altitude: 0, Walkable: false}}, // has map data, but not walkable
			{1, 0}: {{Altitude: 0, Walkable: true}},
		},
		coachStartCells: make(map[byte][][2]int32),
		fightStartCells: make(map[byte][][2]int32),
	}
	got := m.NearbyWalkableCells([2]int32{0, 0}, 1, nil)
	if len(got) != 1 || got[0] != ([2]int32{1, 0}) {
		t.Errorf("NearbyWalkableCells = %v, want [{1 0}] (non-walkable anchor cell must be skipped)", got)
	}
}

func TestNearbyWalkableCellsShufflesWithRNG(t *testing.T) {
	m := newSyntheticMap(
		[2]int32{0, 0}, [2]int32{1, 0}, [2]int32{0, 1}, [2]int32{-1, 0}, [2]int32{0, -1},
		[2]int32{1, 1}, [2]int32{-1, -1}, [2]int32{1, -1}, [2]int32{-1, 1},
	)
	// With a fixed seed, results should be deterministic and a permutation
	// of the same 9 cells regardless of call order.
	rng1 := rand.New(rand.NewSource(42))
	got1 := m.NearbyWalkableCells([2]int32{0, 0}, 9, rng1)
	rng2 := rand.New(rand.NewSource(42))
	got2 := m.NearbyWalkableCells([2]int32{0, 0}, 9, rng2)
	if len(got1) != 9 || len(got2) != 9 {
		t.Fatalf("expected 9 cells both times, got %d and %d", len(got1), len(got2))
	}
	for i := range got1 {
		if got1[i] != got2[i] {
			t.Errorf("same-seed rng produced different order at index %d: %v vs %v", i, got1[i], got2[i])
		}
	}
}
