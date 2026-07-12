package combat

import "testing"

// TestDirection8_WireValuesMatchClient regression-tests the confirmed bug
// found by cross-checking framework.kernel.core.maths.Direction8's
// decompiled enum ordinals: FIGHTER_CHANGE_DIRECTION(4522) sends this as
// a raw byte the client resolves via Direction8.getDirectionFromIndex(),
// so these numeric values must match the client exactly.
func TestDirection8_WireValuesMatchClient(t *testing.T) {
	cases := map[Direction8]byte{
		DirEast:      0,
		DirSouthEast: 1,
		DirSouth:     2,
		DirSouthWest: 3,
		DirWest:      4,
		DirNorthWest: 5,
		DirNorth:     6,
		DirNorthEast: 7,
	}
	for dir, want := range cases {
		if byte(dir) != want {
			t.Errorf("Direction8 %v = %d, want %d (client wire value)", dir, byte(dir), want)
		}
	}
}

// TestStep_MatchesClientGridVectors pins Point3.Step to the client's exact
// Direction8.getVector() grid deltas (correcting the decompiler's dropped
// trailing zero on SOUTH_EAST/NORTH_WEST). This mapping is wire-critical:
// movement path cells are broadcast raw and the client derives each step's
// facing from the grid delta between consecutive cells, so a mismatch makes
// the client pick a wrong -- and for the fight-movement directions, a
// cardinal/invisible -- facing. The four "diagonal-named" fight directions
// (SE/SW/NW/NE) MUST be single-axis moves (only one of X/Y changes).
func TestStep_MatchesClientGridVectors(t *testing.T) {
	origin := Point3{X: 10, Y: 10, Z: 0}
	cases := map[Direction8][2]int32{
		DirEast:      {1, -1},
		DirSouthEast: {1, 0},
		DirSouth:     {1, 1},
		DirSouthWest: {0, 1},
		DirWest:      {-1, 1},
		DirNorthWest: {-1, 0},
		DirNorth:     {-1, -1},
		DirNorthEast: {0, -1},
	}
	for dir, want := range cases {
		got := origin.Step(dir)
		gotDelta := [2]int32{got.X - origin.X, got.Y - origin.Y}
		if gotDelta != want {
			t.Errorf("Step(%v) delta = %v, want %v (client grid vector)", dir, gotDelta, want)
		}
	}

	// The four legal fight-movement directions must be single-axis.
	for _, dir := range []Direction8{DirSouthEast, DirSouthWest, DirNorthWest, DirNorthEast} {
		d := cases[dir]
		abs := func(v int32) int32 {
			if v < 0 {
				return -v
			}
			return v
		}
		if abs(d[0])+abs(d[1]) != 1 {
			t.Errorf("fight direction %v grid vector %v is not single-axis; would render invisible on the client", dir, d)
		}
	}
}

// TestDirectionFrom_InvertsStep verifies directionFrom is the exact inverse
// of Point3.Step: for every direction, stepping one cell and asking which
// direction that cell lies in must return the same direction. Push/pull
// facing and any wire-facing derived from directionFrom depend on this
// round-trip holding.
func TestDirectionFrom_InvertsStep(t *testing.T) {
	origin := Point3{X: 10, Y: 10, Z: 0}
	for _, dir := range []Direction8{
		DirEast, DirSouthEast, DirSouth, DirSouthWest,
		DirWest, DirNorthWest, DirNorth, DirNorthEast,
	} {
		neighbor := origin.Step(dir)
		if got := directionFrom(origin, neighbor); got != dir {
			t.Errorf("directionFrom(origin, Step(origin, %v)) = %v, want %v", dir, got, dir)
		}
	}
}
