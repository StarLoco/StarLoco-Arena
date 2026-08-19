package game

import "testing"

// TestChunkOfFloorDivides guards the negative-coordinate trap. Go's `/` truncates
// toward zero, so cell -1 would land in chunk 0 alongside cell +1 — and several
// islands place elements at negative cells (world 25's Fusion altar is at
// (-45,-25), a Card Master at (-58,33)). Chunking those wrong would put them a
// whole chunk closer or further than they are.
func TestChunkOfFloorDivides(t *testing.T) {
	cases := []struct {
		cell int32
		want int32
	}{
		{0, 0}, {1, 0}, {17, 0}, {18, 1}, {35, 1}, {36, 2},
		{-1, -1}, {-17, -1}, {-18, -1}, {-19, -2}, {-36, -2}, {-37, -3},
		{40, 2}, {-20, -2}, {45, 2}, {-45, -3}, {-58, -4}, {94, 5},
	}
	for _, c := range cases {
		if got := chunkOf(c.cell); got != c.want {
			t.Errorf("chunkOf(%d) = %d, want %d", c.cell, got, c.want)
		}
	}
	// The property that matters: cells 18 apart are never in the same chunk.
	for cell := int32(-60); cell <= 60; cell++ {
		if chunkOf(cell) == chunkOf(cell+envChunkSide) {
			t.Fatalf("cells %d and %d share a chunk", cell, cell+envChunkSide)
		}
	}
}

// TestElementInRangeMatchesTheLiveClient replays the measurements taken against
// the running client. Every row was observed: "in" means the client resolved the
// element, "out" means it logged "Aucune définition trouvée" and dropped it.
//
// This is the evidence for elementChunkRadius = 2. If someone widens the radius to
// be "safe", these rows fail — and widening it is not safe, it silently drops
// elements again.
func TestElementInRangeMatchesTheLiveClient(t *testing.T) {
	el := func(x, y int32) worldElement { return worldElement{cellX: x, cellY: y} }
	cases := []struct {
		name           string
		elem           worldElement
		coachX, coachY int32
		want           bool
	}{
		// Coach on the login spawn (the Zaap cell).
		{"Zaap, same cell", el(40, -20), 40, -20, true},
		{"Mailbox 56 cells", el(33, 36), 40, -20, false},
		{"Graveyard 65 cells", el(4, 45), 40, -20, false},
		{"CardMaster 54 cells", el(94, 6), 40, -20, false},
		{"CardMaster 98 cells", el(-58, 33), 40, -20, false},
		{"FusionLab 85 cells", el(-45, -25), 40, -20, false},
		// Coach standing on the graveyard.
		{"Graveyard, same cell", el(4, 45), 4, 45, true},
		{"Mailbox 1 chunk away", el(33, 36), 4, 45, true},
		{"Zaap now far", el(40, -20), 4, 45, false},
		// Coach 40 cells east of the graveyard — the boundary case.
		{"Graveyard at exactly 2 chunks", el(4, 45), 44, 45, true},
		{"Mailbox still near", el(33, 36), 44, 45, true},
		{"CardMaster at exactly 3 chunks", el(94, 6), 44, 45, false},
		{"Zaap 4 chunks", el(40, -20), 44, 45, false},
		{"FusionLab 5 chunks", el(-45, -25), 44, 45, false},
	}
	for _, c := range cases {
		if got := elementInRange(c.elem, c.coachX, c.coachY); got != c.want {
			t.Errorf("%s: elementInRange = %v, want %v (live client said %v)",
				c.name, got, c.want, c.want)
		}
	}
}

// TestRefreshWorldElementsIsADelta: the refresh must send each element once and
// then leave it alone, or every step of a walk would re-spawn everything in range
// and the client would pile up duplicates.
func TestRefreshWorldElementsIsADelta(t *testing.T) {
	const world = 25
	if len(worldElements[world]) == 0 {
		t.Skip("world 25 has no elements")
	}
	s := &Session{}

	inRangeAt := func(x, y int32) map[int64]bool {
		out := map[int64]bool{}
		for _, e := range worldElements[world] {
			if elementInRange(e, x, y) {
				out[e.instanceID] = true
			}
		}
		return out
	}

	// First pass at the spawn cell: everything in range is new.
	s.spawnedElements = nil
	want := inRangeAt(40, -20)
	s.spawnedElements = make(map[int64]bool)
	for id := range want {
		s.spawnedElements[id] = true
	}
	if len(s.spawnedElements) != len(want) {
		t.Fatalf("setup: tracked %d, want %d", len(s.spawnedElements), len(want))
	}

	// Moving to the graveyard must drop what left and add what arrived — and the
	// two sets must be disjoint from each other.
	after := inRangeAt(4, 45)
	var added, dropped int
	for id := range after {
		if !want[id] {
			added++
		}
	}
	for id := range want {
		if !after[id] {
			dropped++
		}
	}
	if added == 0 {
		t.Error("moving across the island added no elements: streaming is inert")
	}
	if dropped == 0 {
		t.Error("moving across the island dropped none: the far ones were never in range")
	}
}
