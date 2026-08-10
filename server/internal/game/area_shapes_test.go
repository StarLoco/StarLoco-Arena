package game

import "testing"

// TestCrossArities pins the port of the client's qv, which accepts ONE, TWO or
// FOUR arm lengths and rejects any other count. The arm-to-axis mapping is read
// off the cell list qv builds and confirmed by its own debug name
// "cross-h<aeD>b<aeF>-g<aeG>d<aeE>": haut=+x, bas=-x, gauche=-y, droite=+y.
func TestCrossArities(t *testing.T) {
	in := func(size []int32, dx, dy int32) bool { return crossContains(size, dx, dy) }

	// 1 param: all four arms alike (the only form the shipped data uses).
	one := []int32{2}
	for _, c := range [][2]int32{{0, 0}, {1, 0}, {2, 0}, {-1, 0}, {-2, 0}, {0, 1}, {0, 2}, {0, -1}, {0, -2}} {
		if !in(one, c[0], c[1]) {
			t.Errorf("1-param cross r=2: (%d,%d) should be inside", c[0], c[1])
		}
	}
	for _, c := range [][2]int32{{3, 0}, {-3, 0}, {0, 3}, {0, -3}, {1, 1}, {2, 2}, {-1, -1}} {
		if in(one, c[0], c[1]) {
			t.Errorf("1-param cross r=2: (%d,%d) should be outside", c[0], c[1])
		}
	}

	// 2 params: (face-à-soi, côté) -> ±x = p0, ±y = p1.
	two := []int32{1, 3}
	for _, c := range [][2]int32{{1, 0}, {-1, 0}, {0, 3}, {0, -3}} {
		if !in(two, c[0], c[1]) {
			t.Errorf("2-param cross [1 3]: (%d,%d) should be inside", c[0], c[1])
		}
	}
	for _, c := range [][2]int32{{2, 0}, {-2, 0}, {0, 4}, {0, -4}} {
		if in(two, c[0], c[1]) {
			t.Errorf("2-param cross [1 3]: (%d,%d) should be outside", c[0], c[1])
		}
	}

	// 4 params: haut(+x), bas(-x), gauche(-y), droite(+y) — each independent.
	four := []int32{1, 2, 3, 4}
	for _, c := range [][2]int32{{1, 0}, {-2, 0}, {0, -3}, {0, 4}} {
		if !in(four, c[0], c[1]) {
			t.Errorf("4-param cross [1 2 3 4]: (%d,%d) should be inside", c[0], c[1])
		}
	}
	for _, c := range [][2]int32{{2, 0}, {-3, 0}, {0, -4}, {0, 5}} {
		if in(four, c[0], c[1]) {
			t.Errorf("4-param cross [1 2 3 4]: (%d,%d) should be outside", c[0], c[1])
		}
	}

	// A sizeless cross degrades to its centre cell rather than matching nothing.
	if !in(nil, 0, 0) || in(nil, 1, 0) {
		t.Error("sizeless cross should be the centre cell only")
	}
}

// TestCrossOneParamUnchanged guards the refactor: the 1-param form is the only
// one any shipped record uses, and it must behave exactly as the old symmetric
// implementation did.
func TestCrossOneParamUnchanged(t *testing.T) {
	for r := int32(0); r <= 3; r++ {
		size := []int32{r}
		for dx := int32(-4); dx <= 4; dx++ {
			for dy := int32(-4); dy <= 4; dy++ {
				old := (dx == 0 && abs32(dy) <= r) || (dy == 0 && abs32(dx) <= r)
				if got := crossContains(size, dx, dy); got != old {
					t.Fatalf("r=%d (%d,%d): new=%v old=%v", r, dx, dy, got, old)
				}
			}
		}
	}
}

// TestPointListShape covers AoE shape 8 (client acg_0, "forme à base de
// points"): explicit (dx,dy) offsets from the centre, DIRECTIONAL because the
// client's symmetry flag fi() is false for it (as it is for the T shapes), with
// the offsets authored on a fixed reference axis.
func TestPointListShape(t *testing.T) {
	// The one shipped row: spell 469's action-125 effect, size [0 0 -1 0] —
	// the centre plus one cell back along the reference axis.
	size := []int32{0, 0, -1, 0}

	// Cast running along +x (source west of the centre): identity rotation, so
	// the offsets land verbatim.
	src := Pos{X: 5, Y: 10}
	ctr := Pos{X: 7, Y: 10}
	if !pointInArea(areaShapePointList, size, src, ctr, Pos{X: 7, Y: 10}) {
		t.Error("centre cell (offset 0,0) should be inside")
	}
	if !pointInArea(areaShapePointList, size, src, ctr, Pos{X: 6, Y: 10}) {
		t.Error("offset (-1,0) should be inside for a +x cast")
	}
	if pointInArea(areaShapePointList, size, src, ctr, Pos{X: 8, Y: 10}) {
		t.Error("offset (+1,0) is not in the list")
	}

	// Rotated: cast running along +y (source north of the centre). The (-1,0)
	// offset must rotate with it rather than staying on the x axis.
	src2 := Pos{X: 7, Y: 8}
	if !pointInArea(areaShapePointList, size, src2, ctr, Pos{X: 7, Y: 9}) {
		t.Error("offset (-1,0) should rotate to (0,-1) for a +y cast")
	}
	if pointInArea(areaShapePointList, size, src2, ctr, Pos{X: 6, Y: 10}) {
		t.Error("the offset must NOT stay on the x axis when the cast runs along y")
	}

	// A caster standing on the centre has no direction: degrade to the centre.
	if !pointInArea(areaShapePointList, size, ctr, ctr, ctr) {
		t.Error("zero-direction should still include the centre")
	}
	if pointInArea(areaShapePointList, size, ctr, ctr, Pos{X: 6, Y: 10}) {
		t.Error("zero-direction should degrade to the centre cell only")
	}

	// Odd-length / malformed lists degrade rather than panic (acg_0 throws; we
	// must not, since this is attacker-reachable data).
	if pointInArea(areaShapePointList, []int32{3}, src, ctr, Pos{X: 6, Y: 10}) {
		t.Error("a malformed 1-element point list should degrade to the centre")
	}
}
