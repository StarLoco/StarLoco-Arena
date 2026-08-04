package game

import "math"

// Line-of-sight for spell targeting, matching the 2.70 client's altitude-based
// visibility (class ahc_2 / ahC: axq() ray-cast + axp() per-column test), reduced
// to the SINGLE-LEVEL tile case the practice arena (world 5) uses.
//
// The client raises both endpoints to "eye" height, casts a ray between them, and
// — for each intermediate cell the ray crosses — blocks sight iff the cell's
// terrain top rises above the ray's LOWEST altitude while crossing that cell
// (grazing equality counts as visible). It tries eye→eye first, then eye→feet
// (mv_1's two-try check) before declaring no line of sight.
//
// We implement it TERRAIN-ONLY: the client also blocks on obstacle/creature
// occluders (its bE() call), which we deliberately omit. That makes our blocking a
// strict SUBSET of the client's, so a genuine cast (which the client only sends
// when its own LoS is clear) is never rejected here — we can only ever miss a
// block (an acceptable anti-cheat gap), never invent one.
const (
	// losEyeOffset is the altitude the client adds to both LoS endpoints:
	// (short)(fighter.PE() * 0.8) with the base fighter height PE()=6 → +4.
	losEyeOffset int16 = 4
	// losLowAltitude is the altitude a hole/void or off-map cell is treated as
	// for LoS — a floor so low the ray always passes over it (never blocks),
	// mirroring the client's visibility accessor, which maps a floorless cell to
	// a very low floor rather than the movement-side void sentinel.
	losLowAltitude int16 = -30000
	// losHighAltitude is the pseudo-altitude of a scenery obstacle: tall enough
	// that no ray ever clears it, so the cell always blocks sight.
	losHighAltitude int16 = 30000
)

// hasLineOfSight reports whether terrain leaves an unobstructed sight line between
// two cells of this arena.
func (a *arena) hasLineOfSight(from, to Pos) bool {
	return lineOfSightClear(from, to, a.losAltitude)
}

// losAltitude returns a cell's terrain altitude for LoS: the topology altitude,
// or a very low floor for a void/off-map cell (so it never blocks).
//
// A scenery OBSTACLE (ice spike, tree) returns an impassably tall column instead,
// so a ray crossing it always blocks. That mirrors the client, whose aoq_0.bE
// tests the cell word's LoS bit — which is set for the 0xFFFF we now emit for
// these cells. Without this the server would be more permissive than the client
// and would accept casts the player was never offered (B-048).
func (a *arena) losAltitude(x, y int32) int16 {
	if x < 0 || x >= a.width || y < 0 || y >= a.height {
		return losLowAltitude
	}
	c := a.at(x, y)
	if c.void {
		return losLowAltitude
	}
	if !c.ground {
		return losHighAltitude // scenery: an impassably tall column
	}
	return c.alt
}

// lineOfSightClear runs the client's two-try check: eye→eye, then eye→feet. altAt
// returns a cell's terrain altitude.
func lineOfSightClear(from, to Pos, altAt func(x, y int32) int16) bool {
	feetFrom := altAt(from.X, from.Y)
	feetTo := altAt(to.X, to.Y)
	if rayClear(from, to, feetFrom+losEyeOffset, feetTo+losEyeOffset, altAt) {
		return true
	}
	return rayClear(from, to, feetFrom+losEyeOffset, feetTo, altAt)
}

// rayClear samples the straight ray from (from, z0) to (to, z1) cell-by-cell and
// reports whether every INTERMEDIATE cell's terrain stays at or below the ray's
// lowest altitude over that cell. z0/z1 are the (eye-raised) endpoint altitudes.
//
// It oversamples the segment (8× the Chebyshev step count) so every cell the ray
// passes through is seen and its minimum ray altitude captured — the client's
// per-column cwG. The endpoints never block (a caster/target cell is exempt).
func rayClear(from, to Pos, z0, z1 int16, altAt func(x, y int32) int16) bool {
	dx := float64(to.X - from.X)
	dy := float64(to.Y - from.Y)
	steps := absI32(to.X - from.X)
	if s := absI32(to.Y - from.Y); s > steps {
		steps = s
	}
	if steps == 0 {
		return true // same cell — nothing between
	}
	n := int(steps) * 8

	type cell struct{ x, y int32 }
	minRay := make(map[cell]int16, int(steps)+2)
	for i := 0; i <= n; i++ {
		t := float64(i) / float64(n)
		x := from.X + int32(math.Round(dx*t))
		y := from.Y + int32(math.Round(dy*t))
		z := int16(math.Round(float64(z0) + (float64(z1)-float64(z0))*t))
		k := cell{x, y}
		if cur, ok := minRay[k]; !ok || z < cur {
			minRay[k] = z
		}
	}
	for k, rayMin := range minRay {
		if (k.x == from.X && k.y == from.Y) || (k.x == to.X && k.y == to.Y) {
			continue // endpoints are exempt
		}
		if altAt(k.x, k.y) > rayMin {
			return false // terrain pokes above the ray → sight blocked
		}
	}
	return true
}

func absI32(v int32) int32 {
	if v < 0 {
		return -v
	}
	return v
}
