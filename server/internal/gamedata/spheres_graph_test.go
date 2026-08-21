package gamedata

import "testing"

// board: a 1-D corridor plus a branch, laid out so each rule has a distinct case.
//
//	(1,1) root [payload]  -- (2,1) empty -- (3,1) SPHERE -- (4,1) empty -- (5,1) SPHERE
//	                                          |
//	                                       (3,2) empty
//	                                          |
//	                                       (3,3) DEADEND
//	 (7,1) SPHERE reachable only through (6,1) DEADEND
func corridorBoard() *SphereBoards {
	return NewSphereBoards(
		[]*SphereBoard{{ID: 1, Season: 1, Breed: 3, RootX: 1, RootY: 1}},
		[]*Sphere{
			{ID: 10, BoardID: 1, X: 1, Y: 1, XPCost: 100, Effects: []Effect{{}}},
			{ID: 11, BoardID: 1, X: 2, Y: 1},
			{ID: 12, BoardID: 1, X: 3, Y: 1, XPCost: 200, Effects: []Effect{{}}},
			{ID: 13, BoardID: 1, X: 4, Y: 1},
			{ID: 14, BoardID: 1, X: 5, Y: 1, XPCost: 300, Effects: []Effect{{}}},
			{ID: 15, BoardID: 1, X: 3, Y: 2},
			{ID: 16, BoardID: 1, X: 3, Y: 3, DeadEnd: true},
			{ID: 17, BoardID: 1, X: 6, Y: 1, DeadEnd: true},
			{ID: 18, BoardID: 1, X: 7, Y: 1, XPCost: 400, Effects: []Effect{{}}},
		},
	)
}

func TestReachableOnlyCrossesEmptyCells(t *testing.T) {
	b := corridorBoard()
	at := func(x, y int16) *Sphere { return b.At(1, x, y) }

	// The next sphere along an empty corridor is buyable.
	if !b.Reachable(1, at(1, 1), at(3, 1)) {
		t.Error("the next sphere along an empty path is not reachable")
	}
	// The one BEHIND it is not: a node with a payload blocks the route through it.
	// This is the whole meaning of "un chemin direct" and the easiest rule to get
	// wrong by writing a plain flood fill.
	if b.Reachable(1, at(1, 1), at(5, 1)) {
		t.Error("a sphere two spheres away is reachable - a payload node must block")
	}
	// Standing on the middle sphere, the far one becomes reachable.
	if !b.Reachable(1, at(3, 1), at(5, 1)) {
		t.Error("the next sphere from the new cursor is not reachable")
	}
}

func TestReachableRefusesDeadEnds(t *testing.T) {
	b := corridorBoard()
	at := func(x, y int16) *Sphere { return b.At(1, x, y) }

	// A dead end can never be bought...
	if b.Reachable(1, at(3, 1), at(3, 3)) {
		t.Error("a dead-end node is reachable; the client refuses it before anything else")
	}
	// ...nor walked through to what lies beyond it.
	if b.Reachable(1, at(5, 1), at(7, 1)) {
		t.Error("a route through a dead end is allowed")
	}
}

func TestReachableRefusesTheCellYouStandOn(t *testing.T) {
	b := corridorBoard()
	at := func(x, y int16) *Sphere { return b.At(1, x, y) }
	if b.Reachable(1, at(3, 1), at(3, 1)) {
		t.Error("the node under the cursor is reachable; Ei.b returns null for it")
	}
}

// A portal reaches its arrival cell in one step, however far away and whatever
// lies between - that is the entire point of a portal, and a reachability check
// that only walked the grid would refuse every one of them.
func TestReachableFollowsAPortal(t *testing.T) {
	b := NewSphereBoards(
		[]*SphereBoard{{ID: 1, Season: 1, Breed: 3, RootX: 1, RootY: 1}},
		[]*Sphere{
			{ID: 10, BoardID: 1, X: 1, Y: 1, TeleportX: 9, TeleportY: 9},
			{ID: 11, BoardID: 1, X: 2, Y: 1, DeadEnd: true},
			{ID: 12, BoardID: 1, X: 9, Y: 9, XPCost: 50, Effects: []Effect{{}}},
		},
	)
	if !b.Reachable(1, b.At(1, 1, 1), b.At(1, 9, 9)) {
		t.Error("a portal does not reach its own arrival cell")
	}
	// The portal reaches its target, not everything everywhere.
	if b.Reachable(1, b.At(1, 9, 9), b.At(1, 1, 1)) {
		t.Error("the portal works in reverse, which it should not")
	}
}

func TestNeighboursAreGridOrthogonal(t *testing.T) {
	b := corridorBoard()
	n := b.Neighbours(1, b.At(1, 3, 1))
	got := map[int32]bool{}
	for _, s := range n {
		got[s.ID] = true
	}
	// (2,1), (4,1) and (3,2) exist; nothing diagonal does.
	for _, want := range []int32{11, 13, 15} {
		if !got[want] {
			t.Errorf("node %d is not a neighbour of (3,1)", want)
		}
	}
	if len(n) != 3 {
		t.Errorf("(3,1) has %d neighbours, want 3", len(n))
	}
}

// TestEveryPayloadKindBlocksARoute: HasPayload is a disjunction, and a corridor
// built only from effect nodes cannot tell whether the other terms are there.
// Each kind gets its own corridor, because dropping any one of them silently
// turns that kind into a walk-through path cell and puts the sphere BEHIND it up
// for sale.
func TestEveryPayloadKindBlocksARoute(t *testing.T) {
	cases := []struct {
		name  string
		block *Sphere
	}{
		{"barrier", &Sphere{ID: 21, BoardID: 1, X: 3, Y: 1, BarrierCards: []int32{99}}},
		{"teleport", &Sphere{ID: 21, BoardID: 1, X: 3, Y: 1, TeleportX: 9, TeleportY: 9}},
		{"spell", &Sphere{ID: 21, BoardID: 1, X: 3, Y: 1, SpellID: 42}},
		{"equipment pool", &Sphere{ID: 21, BoardID: 1, X: 3, Y: 1, EquipmentPoolID: 7}},
		{"effects", &Sphere{ID: 21, BoardID: 1, X: 3, Y: 1, Effects: []Effect{{}}}},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			b := NewSphereBoards(
				[]*SphereBoard{{ID: 1, Season: 1, Breed: 3, RootX: 1, RootY: 1}},
				[]*Sphere{
					{ID: 20, BoardID: 1, X: 1, Y: 1, Effects: []Effect{{}}}, // cursor
					{ID: 25, BoardID: 1, X: 2, Y: 1},                        // empty
					tc.block,                                                // the node under test
					{ID: 26, BoardID: 1, X: 4, Y: 1},                        // empty
					{ID: 27, BoardID: 1, X: 5, Y: 1, Effects: []Effect{{}}}, // the prize behind it
				},
			)
			if !tc.block.HasPayload() {
				t.Fatalf("a %s node does not count as a payload", tc.name)
			}
			if !b.Reachable(1, b.At(1, 1, 1), b.At(1, 3, 1)) {
				t.Errorf("the %s node itself is not reachable", tc.name)
			}
			if b.Reachable(1, b.At(1, 1, 1), b.At(1, 5, 1)) {
				t.Errorf("a route runs straight through the %s node to the sphere behind it", tc.name)
			}
		})
	}
}
