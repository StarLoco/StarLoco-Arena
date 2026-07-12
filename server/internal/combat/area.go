package combat

// AreaShape mirrors AreaOfEffectEnum's shape discriminant, see
// docs/05-combat-engine.md §5.7.
type AreaShape int16

const (
	AreaPoint  AreaShape = 1
	AreaCircle AreaShape = 2
	AreaCross  AreaShape = 3
	AreaT      AreaShape = 4
	AreaEmpty  AreaShape = 32767
)

// AreaOfEffect is a resolved effect-shape instance: which cells around a
// cast's target center are affected.
type AreaOfEffect struct {
	Shape AreaShape
	Size  []int32 // shape-specific: Circle=[radius], Cross=[size], T=[height,width]
}

// IsPointInside reports whether point is affected by this area, centered
// at center and cast from source (source matters for T-shape direction).
// See docs/05-combat-engine.md §5.7 for the documented Circle
// preview-vs-hit-test quirk -- this hit-test function intentionally uses
// true Euclidean distance for Circle, not the taxicab-diamond preview
// pattern (which is a client-only rendering concern out of scope here).
func (a AreaOfEffect) IsPointInside(source, center, point Point3) bool {
	switch a.Shape {
	case AreaPoint:
		return point.X == center.X && point.Y == center.Y
	case AreaCircle:
		if len(a.Size) == 0 {
			return point.X == center.X && point.Y == center.Y
		}
		r := a.Size[0]
		dx, dy := point.X-center.X, point.Y-center.Y
		return dx*dx+dy*dy <= r*r
	case AreaCross:
		if len(a.Size) == 0 {
			return point.X == center.X && point.Y == center.Y
		}
		size := a.Size[0]
		return (point.X == center.X && abs32(point.Y-center.Y) <= size) ||
			(point.Y == center.Y && abs32(point.X-center.X) <= size)
	case AreaT:
		if len(a.Size) < 2 {
			return point.X == center.X && point.Y == center.Y
		}
		return tShapeContains(source, center, point, a.Size[0], a.Size[1])
	case AreaEmpty:
		return false
	default:
		return point.X == center.X && point.Y == center.Y
	}
}

// tShapeContains resolves a directional "T" beam: a line of `height` cells
// from source toward center, with a perpendicular bar of `width` (forced
// odd) cells at the far end. Direction is derived from source->center.
func tShapeContains(source, center, point Point3, height, width int32) bool {
	dx := center.X - source.X
	dy := center.Y - source.Y
	if dx == 0 && dy == 0 {
		return point == center
	}
	// Normalize direction to one of the 4 cardinal steps (T-shapes are
	// orthogonal-only in the reference).
	var stepX, stepY int32
	switch {
	case abs32(dx) >= abs32(dy) && dx > 0:
		stepX = 1
	case abs32(dx) >= abs32(dy) && dx < 0:
		stepX = -1
	case dy > 0:
		stepY = 1
	default:
		stepY = -1
	}

	// The beam runs from source (exclusive) to source+height*step.
	for i := int32(1); i <= height; i++ {
		beamPoint := Point3{X: source.X + stepX*i, Y: source.Y + stepY*i, Z: source.Z}
		if beamPoint.X == point.X && beamPoint.Y == point.Y {
			return true
		}
	}
	// Perpendicular bar at the far end (i == height).
	farX, farY := source.X+stepX*height, source.Y+stepY*height
	half := width / 2
	if stepX != 0 {
		// beam runs horizontally; bar runs vertically at far end
		if point.X == farX && abs32(point.Y-farY) <= half {
			return true
		}
	} else {
		if point.Y == farY && abs32(point.X-farX) <= half {
			return true
		}
	}
	return false
}

// ResolveTargets returns every living fighter in the fight standing on a
// cell inside area (centered at center, cast from source).
func (f *Fight) ResolveTargets(area AreaOfEffect, source, center Point3) []*Fighter {
	var out []*Fighter
	for _, t := range f.Timeline.Order() {
		if t.IsDead {
			continue
		}
		if area.IsPointInside(source, center, t.Position) {
			out = append(out, t)
		}
	}
	return out
}
