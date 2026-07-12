package combat

// This file implements the CastTestLineOfSight piece of
// docs/08-java-parity-roadmap.md §8.12 Phase L, using the real map data
// wired in Phase K (Fight.mapData). See gamedata.Map.BlocksLineOfSight's
// doc comment for the explicit, deliberate simplification vs. the
// reference's full per-direction-flag 3D DDA algorithm
// (LineOfSightUtils.java) -- this walks a simple Bresenham-style line
// between caster and target, checking each intermediate cell (excluding
// both endpoints, mirroring the reference's own intent that the caster's
// and target's own cells never block their own LOS to each other) for a
// blocking obstacle via the real map data.

// hasLineOfSight reports whether nothing blocks a straight line between
// from and to. Falls back to true (permissive) if no real map data is
// attached to this fight, matching the pattern used throughout Phase K's
// wiring (IsWalkable/ArrivalAltitude) of "never block a fight just
// because optional real map data isn't available."
func (f *Fight) hasLineOfSight(from, to Point3) bool {
	if f.mapData == nil {
		return true
	}
	for _, cell := range bresenhamLine(from, to) {
		if f.mapData.BlocksLineOfSight(cell.X, cell.Y) {
			return false
		}
	}
	return true
}

// bresenhamLine returns every intermediate grid cell strictly between
// from and to (excluding both endpoints), using the standard integer
// Bresenham line algorithm -- a reasonable, well-understood stand-in for
// the reference's own sub-cell-boundary DDA traversal (see this file's
// doc comment on why bit-exact reproduction wasn't attempted).
func bresenhamLine(from, to Point3) []Point3 {
	x0, y0 := from.X, from.Y
	x1, y1 := to.X, to.Y

	dx := abs32(x1 - x0)
	dy := -abs32(y1 - y0)
	sx := int32(1)
	if x0 >= x1 {
		sx = -1
	}
	sy := int32(1)
	if y0 >= y1 {
		sy = -1
	}
	err := dx + dy

	var out []Point3
	x, y := x0, y0
	for {
		if x == x1 && y == y1 {
			break
		}
		e2 := 2 * err
		if e2 >= dy {
			err += dy
			x += sx
		}
		if e2 <= dx {
			err += dx
			y += sy
		}
		if x == x1 && y == y1 {
			break
		}
		out = append(out, Point3{X: x, Y: y, Z: from.Z})
	}
	return out
}
