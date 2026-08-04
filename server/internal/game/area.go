package game

import "github.com/StarLoco/arena-2.70/internal/gamedata"

// area.go resolves an effect's area-of-effect: which fighters a cell-targeting
// effect lands on. AoE is server-authoritative — the server expands the aimed
// cell to the zone, finds every fighter inside, and applies the effect to each
// (each broadcasting its own RUNNING_EFFECT, which the client renders per
// fighter), so no new wire format is needed. Ported from the v2.04b combat
// area.go shape hit-tests.
//
// Shapes (mh_2 AreaOfEffectEnum / client zg_1). The radius/size ALWAYS comes
// from the effect's areaSize params — it is NEVER baked into the ordinal — and a
// Dofus "circle" is a Manhattan DIAMOND, not a Euclidean disk (client nw_0).
// Ordinals seen in real 2.70 spell data: 1 point, 2 circle, 3 cross, 4 T, 5 ring,
// 6 square, 8 point-list, 9 inverted-T, 32767 "all". Directional shapes (T /
// inverted-T) orient their stem along the caster→target cardinal step.
const (
	areaShapePoint  int32 = 1
	areaShapeCircle int32 = 2 // filled diamond, radius size[0]
	areaShapeCross  int32 = 3 // row + column arms, length size[0]
	areaShapeT      int32 = 4 // directional: stem size[1] toward target + bar size[0] at the tip
	areaShapeRing   int32 = 5 // diamond annulus [size[0]..size[1]]
	areaShapeSquare int32 = 6 // filled square/rect, half-extents size[0](,size[1])
	areaShapeTInv   int32 = 9 // directional: bar size[0] through center + stem size[1] toward target
	areaShapeEmpty  int32 = 32767
)

// areaFighters returns every living fighter an effect aimed at `center` (cast
// from the caster) affects: just the fighter on the target cell for a point /
// no-size effect, every living fighter for an "empty" (all) area, otherwise every
// living fighter standing inside the zone shape. An area damage/heal/buff thus
// lands on allies AND enemies AND the caster if they fall in the zone (authentic
// friendly-fire — you position to spare your team).
func (f *Fight) areaFighters(caster *FightFighter, ef gamedata.Effect, center Pos) []*FightFighter {
	shape := ef.AreaShape
	// Point, unknown, or a zone shape with no radius/size -> only the fighter on
	// the target cell. This aim was already validated by the client, so it is NOT
	// re-filtered by the target conditions (unlike the expanded area targets).
	if shape != areaShapeEmpty && (shape == areaShapePoint || shape == 0 || len(ef.AreaSize) == 0) {
		if v := f.fighterAtCell(center); v != nil {
			return []*FightFighter{v}
		}
		return nil
	}
	// Multi-target: gather every candidate in the shape (or every living fighter
	// for an "empty"/all area), then keep only those the effect's target
	// conditions allow — the client validated only the centre cell, so the server
	// decides who each EXPANDED target legitimately affects. Without this a
	// "Target: All" self-buff (32767 + IS_CASTER) would wrongly buff everyone.
	var candidates []*FightFighter
	if shape == areaShapeEmpty {
		candidates = f.livingFighters()
	} else {
		src := caster.Pos
		for _, ff := range f.allFighters() {
			if ff.HP > 0 && pointInArea(shape, ef.AreaSize, src, center, ff.Pos) {
				candidates = append(candidates, ff)
			}
		}
	}
	var out []*FightFighter
	for _, ff := range candidates {
		if effectTargetAllowed(caster, ff, ef.Targets) {
			out = append(out, ff)
		}
	}
	return out
}

// livingFighters returns every living fighter across both teams.
func (f *Fight) livingFighters() []*FightFighter {
	var out []*FightFighter
	for _, ff := range f.allFighters() {
		if ff.HP > 0 {
			out = append(out, ff)
		}
	}
	return out
}

// pointInArea reports whether `point` lies inside a zone of `shape`/`size`
// centered on `center` and cast from `source` (source sets the direction for the
// directional T shapes). Non-directional shapes ignore source. A missing size
// element reads as 0, degrading a mis-sized zone to its center cell.
func pointInArea(shape int32, size []int32, source, center, point Pos) bool {
	dx, dy := point.X-center.X, point.Y-center.Y
	switch shape {
	case areaShapeCircle:
		return abs32(dx)+abs32(dy) <= areaSz(size, 0) // Manhattan diamond (client nw_0)
	case areaShapeCross:
		// Symmetric row/column arms of length size[0] (the common 1-param form;
		// the rare 2-/4-param asymmetric variants approximate with size[0]).
		r := areaSz(size, 0)
		return (dx == 0 && abs32(dy) <= r) || (dy == 0 && abs32(dx) <= r)
	case areaShapeRing:
		lo, hi := areaSz(size, 0), areaSz(size, 1)
		if hi < lo {
			lo, hi = hi, lo
		}
		d := abs32(dx) + abs32(dy)
		return d >= lo && d <= hi
	case areaShapeSquare:
		hw := areaSz(size, 0)
		hh := hw
		if len(size) >= 2 {
			hh = size[1]
		}
		return abs32(dx) <= hw && abs32(dy) <= hh
	case areaShapeT:
		return tZoneContains(source, center, point, areaSz(size, 1), areaSz(size, 0), false)
	case areaShapeTInv:
		return tZoneContains(source, center, point, areaSz(size, 1), areaSz(size, 0), true)
	default: // point (1), unknown; "all" (32767) is handled by areaFighters
		return dx == 0 && dy == 0
	}
}

// tZoneContains tests membership of a directional T (or inverted-T): a stem of
// `stemLen` cells running from the center toward the target, and a perpendicular
// bar of half-width `barHalf` — at the stem TIP for a T, or THROUGH the center
// for an inverted-T (client sp_2 / arG). Direction is the caster→target cardinal
// step (the client quantises to the same 4 iso-diagonals); a zero direction
// (caster on the target cell) degenerates to the center cell only.
func tZoneContains(source, center, point Pos, stemLen, barHalf int32, inverted bool) bool {
	dirX, dirY := cardinalStep(center.X-source.X, center.Y-source.Y)
	if dirX == 0 && dirY == 0 {
		return point.X == center.X && point.Y == center.Y
	}
	perpX, perpY := -dirY, dirX
	dx, dy := point.X-center.X, point.Y-center.Y
	along := dx*dirX + dy*dirY // steps toward the target (the stem axis)
	across := dx*perpX + dy*perpY
	if along == 0 && across == 0 {
		return true // center
	}
	if across == 0 && along >= 1 && along <= stemLen {
		return true // stem
	}
	barAt := stemLen // T: bar at the tip
	if inverted {
		barAt = 0 // inverted-T: bar through the center
	}
	return along == barAt && abs32(across) >= 1 && abs32(across) <= barHalf
}

// areaSz returns size[i] or 0 if absent.
func areaSz(size []int32, i int) int32 {
	if i >= 0 && i < len(size) {
		return size[i]
	}
	return 0
}

func abs32(v int32) int32 {
	if v < 0 {
		return -v
	}
	return v
}
