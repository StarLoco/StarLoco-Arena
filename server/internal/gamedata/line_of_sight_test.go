package gamedata

import "testing"

// TestLineOfSightValidAt_ChecksSpecificDirectionFlag proves the key
// behavioral improvement over the old "any solid surface blocks every
// direction" approximation: a wall whose LineOfSight flags are only false
// for ONE edge must block sight crossing that specific edge while still
// allowing sight to pass through the other three edges (and top/bottom).
func TestLineOfSightValidAt_ChecksSpecificDirectionFlag(t *testing.T) {
	m := newSyntheticMapWithSurfaces(map[[2]int32][]MapCellFact{
		// A solid wall (height 3, base 0 -> straddles z=0..2) that only
		// blocks sight crossing its SOUTH_EAST edge; every other
		// direction is open.
		{5, 5}: {{
			Altitude: 0, Walkable: false, Height: 3,
			LineOfSight1: false, LineOfSight3: true, LineOfSight5: true,
			LineOfSight7: true, LineOfSightTop: true, LineOfSightBottom: true,
		}},
	})

	cases := []struct {
		dir       LOSDirection
		wantValid bool
	}{
		{LOSDirSouthEast, false}, // the one blocked edge
		{LOSDirSouthWest, true},
		{LOSDirNorthWest, true},
		{LOSDirNorthEast, true},
		{LOSDirTop, true},
		{LOSDirBottom, true},
	}
	for _, tc := range cases {
		if got := m.LineOfSightValidAt(5, 5, 1, tc.dir); got != tc.wantValid {
			t.Errorf("LineOfSightValidAt(5,5,z=1,%v) = %v, want %v", tc.dir, got, tc.wantValid)
		}
	}
}

// TestLineOfSightValidAt_OutsideVerticalExtentIsNeverBlocked confirms the
// straddle test: a surface only blocks sight for z values within its
// [Altitude, Altitude+Height) vertical extent, never above or below it.
func TestLineOfSightValidAt_OutsideVerticalExtentIsNeverBlocked(t *testing.T) {
	m := newSyntheticMapWithSurfaces(map[[2]int32][]MapCellFact{
		{5, 5}: {{Altitude: 2, Walkable: false, Height: 3, LineOfSight1: false}}, // straddles z in [2,5)
	})
	if !m.LineOfSightValidAt(5, 5, 1, LOSDirSouthEast) {
		t.Error("z below the surface's vertical extent should not be blocked")
	}
	if !m.LineOfSightValidAt(5, 5, 5, LOSDirSouthEast) {
		t.Error("z at/above the surface's top should not be blocked")
	}
	if m.LineOfSightValidAt(5, 5, 2, LOSDirSouthEast) {
		t.Error("z at the surface's base altitude should be blocked (base is inclusive)")
	}
}

// TestLineOfSightValidAt_ZeroHeightSurfaceNeverBlocks confirms flat
// ground (Height<=0, e.g. walkable floor tiles) never blocks LOS
// regardless of its flags -- only genuinely solid (Height>0) surfaces do,
// matching the reference's `element.getHeight() > 0.0D` guard.
func TestLineOfSightValidAt_ZeroHeightSurfaceNeverBlocks(t *testing.T) {
	m := newSyntheticMapWithSurfaces(map[[2]int32][]MapCellFact{
		{5, 5}: {{Altitude: 0, Walkable: true, Height: 0, LineOfSight1: false, LineOfSight3: false, LineOfSight5: false, LineOfSight7: false}},
	})
	for _, dir := range []LOSDirection{LOSDirSouthEast, LOSDirSouthWest, LOSDirNorthWest, LOSDirNorthEast} {
		if !m.LineOfSightValidAt(5, 5, 0, dir) {
			t.Errorf("flat (height 0) ground should never block LOS, direction %v", dir)
		}
	}
}

// TestLineOfSightValidAt_NoSurfaceIsNeverBlocked confirms a cell with no
// straddling surface at all (or no map data whatsoever) is never blocked
// by this check -- matching the reference, where an empty/no-match loop
// simply falls through to `return true`. (The separate "cell must exist"
// requirement is enforced by LineOfSightEndValidAt for the destination,
// not by this per-crossing check.)
func TestLineOfSightValidAt_NoSurfaceIsNeverBlocked(t *testing.T) {
	m := newSyntheticMap() // completely empty map
	if !m.LineOfSightValidAt(99, 99, 0, LOSDirSouthEast) {
		t.Error("a cell with no map data at all should not be blocked by LineOfSightValidAt")
	}
}

// TestLineOfSightEndValidAt matches WorldCell.isLineOfSightEndValid's
// exact semantics: a surface must match z EXACTLY (by standing altitude,
// base+height) to have any effect; if one does and it's not walkable, the
// destination is invalid; if none matches, the destination defaults to
// VALID (not the same rule as IsWalkable); a cell with no map data at all
// is always invalid.
func TestLineOfSightEndValidAt(t *testing.T) {
	m := newSyntheticMapWithSurfaces(map[[2]int32][]MapCellFact{
		{1, 1}: {{Altitude: 0, Walkable: true, Height: 0}},   // standing z=0, walkable
		{2, 2}: {{Altitude: 0, Walkable: false, Height: 0}},  // standing z=0, NOT walkable
		{3, 3}: {{Altitude: -11, Walkable: true, Height: 7}}, // standing z=-4
	})

	cases := []struct {
		name string
		x, y int32
		z    int16
		want bool
	}{
		{"exact match, walkable -> valid", 1, 1, 0, true},
		{"exact match, not walkable -> invalid", 2, 2, 0, false},
		{"no exact match at this z -> defaults valid", 1, 1, 5, true},
		{"raised platform exact standing altitude", 3, 3, -4, true},
		{"no map data at all -> invalid", 9, 9, 0, false},
	}
	for _, tc := range cases {
		if got := m.LineOfSightEndValidAt(tc.x, tc.y, tc.z); got != tc.want {
			t.Errorf("%s: LineOfSightEndValidAt(%d,%d,%d) = %v, want %v", tc.name, tc.x, tc.y, tc.z, got, tc.want)
		}
	}
}

// TestLineOfSightEndValidAt_TopmostMatchWins confirms the reverse-
// iteration (topmost surface checked first) when multiple surfaces could
// match different z values -- each is only checked at its own exact
// standing altitude, so this mainly guards against an accidental
// forward-iteration regression silently changing which surface is
// consulted when two surfaces coincidentally share a standing altitude.
func TestLineOfSightEndValidAt_TopmostMatchWins(t *testing.T) {
	m := newSyntheticMapWithSurfaces(map[[2]int32][]MapCellFact{
		{4, 4}: {
			{Altitude: 0, Walkable: false, Height: 0}, // bottom, standing 0, not walkable
			{Altitude: 0, Walkable: true, Height: 0},  // top (later in stack), standing 0, walkable
		},
	})
	if !m.LineOfSightEndValidAt(4, 4, 0) {
		t.Error("topmost surface at the matching standing altitude should win (walkable), want valid")
	}
}
