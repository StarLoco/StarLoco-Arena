package gamedata

// This file implements the real per-direction line-of-sight validity
// checks the reference client uses (WorldCell.isLineOfSightValid /
// isLineOfSightEndValid, called from LineOfSightUtils.check() during its
// 3D DDA line traversal -- see combat/line_of_sight.go for the traversal
// itself, which calls these two methods per generated cell-boundary
// crossing). Ported field-for-field from the decompiled reference; see
// docs/08-java-parity-roadmap.md Phase L (LOS bit-exactness).

// LOSDirection identifies which of a ResolvedSurface's six per-edge/top/
// bottom line-of-sight flags to check for one step of a line-of-sight
// traversal. The four "edge" values correspond exactly to the reference's
// Direction8 SOUTH_EAST(1)/SOUTH_WEST(3)/NORTH_WEST(5)/NORTH_EAST(7) --
// the same four single-axis grid directions fight movement uses (see
// combat/fighter.go's Direction8 doc comment) -- crossed when the DDA
// walk steps along the X or Y axis; Top/Bottom correspond to Direction8
// TOP(8)/BOTTOM(9), crossed when it steps along the Z (altitude) axis.
type LOSDirection int

const (
	LOSDirSouthEast LOSDirection = iota // +X boundary (Direction8=1)
	LOSDirSouthWest                     // +Y boundary (Direction8=3)
	LOSDirNorthWest                     // -X boundary (Direction8=5)
	LOSDirNorthEast                     // -Y boundary (Direction8=7)
	LOSDirTop                           // +Z boundary (Direction8=8)
	LOSDirBottom                        // -Z boundary (Direction8=9)
)

// LineOfSightValidAt mirrors WorldCell.isLineOfSightValid(height,
// direction): for every resolved surface at (x,y) that is SOLID (real
// Height > 0) and whose vertical extent straddles z (its base Altitude
// <= z, and its standing top Altitude+Height > z), the surface's flag for
// `dir` must be true or this reports blocked (false). A cell with no such
// straddling surface at all -- including a cell with no map data -- is
// NOT considered blocked here (matches the reference: the loop simply
// finds nothing to check and falls through to `return true`; note this
// differs from IsWalkable/ArrivalAltitude, which treat "no data" as
// blocked -- callers combine this with LineOfSightEndValidAt, which does
// reject missing cells, matching the reference's own two-part check).
func (m *Map) LineOfSightValidAt(x, y int32, z int16, dir LOSDirection) bool {
	for _, s := range m.SurfacesAt(x, y) {
		if s.Height <= 0 {
			continue
		}
		top := s.Altitude + int16(s.Height)
		if s.Altitude > z || top <= z {
			continue
		}
		var ok bool
		switch dir {
		case LOSDirSouthEast:
			ok = s.LineOfSight1
		case LOSDirSouthWest:
			ok = s.LineOfSight3
		case LOSDirNorthWest:
			ok = s.LineOfSight5
		case LOSDirNorthEast:
			ok = s.LineOfSight7
		case LOSDirTop:
			ok = s.LineOfSightTop
		case LOSDirBottom:
			ok = s.LineOfSightBottom
		}
		if !ok {
			return false
		}
	}
	return true
}

// LineOfSightEndValidAt mirrors WorldCell.isLineOfSightEndValid(height):
// scanning this cell's resolved surfaces from topmost to bottommost (they
// are resolved/appended in bottom-to-top stacking order, so iterate in
// reverse to check the topmost first, exactly mirroring the reference's
// reverse iteration over m_visualElements), the FIRST surface whose
// standing altitude (Altitude+Height) exactly equals z decides validity
// via its Walkable flag. If no surface has that exact standing altitude,
// the cell is considered valid -- this is NOT the same check as
// IsWalkable (which requires finding a walkable surface, defaulting to
// false); here, finding nothing at all defaults to true, matching the
// reference precisely. A cell with no map data at all is always invalid
// (mirrors `m_visualElements == null` -> false).
func (m *Map) LineOfSightEndValidAt(x, y int32, z int16) bool {
	facts, ok := m.cells[[2]int32{x, y}]
	if !ok {
		return false
	}
	for i := len(facts) - 1; i >= 0; i-- {
		s := facts[i]
		standing := s.Altitude + int16(s.Height)
		if standing == z {
			return s.Walkable
		}
	}
	return true
}
