package main

import (
	"math/rand"
	"testing"
)

func abs32t(v int32) int32 {
	if v < 0 {
		return -v
	}
	return v
}

// TestBuildWalkLeg_StrictlyAdjacent proves that every consecutive cell in a
// generated walk leg -- INCLUDING the transition from the server-prepended
// "from" cell (the coach's current position) to the first returned cell --
// differs by exactly one cell in exactly one axis. This is the property that
// makes the client render a real step-by-step walk instead of the "teleport"
// slide that happens when cells are non-adjacent.
func TestBuildWalkLeg_StrictlyAdjacent(t *testing.T) {
	rng := rand.New(rand.NewSource(20260715))
	for iter := 0; iter < 5000; iter++ {
		fromX := int32(rng.Intn(walkRegionHi + 1))
		fromY := int32(rng.Intn(walkRegionHi + 1))
		toX := int32(rng.Intn(walkRegionHi + 1))
		toY := int32(rng.Intn(walkRegionHi + 1))
		path := buildWalkLeg(fromX, fromY, toX, toY, rng)

		px, py := fromX, fromY
		for i, cell := range path {
			dx := abs32t(cell.X - px)
			dy := abs32t(cell.Y - py)
			if dx+dy != 1 {
				t.Fatalf("iter %d step %d: non-adjacent move from (%d,%d) to (%d,%d) (dx=%d dy=%d)",
					iter, i, px, py, cell.X, cell.Y, dx, dy)
			}
			if cell.X < walkRegionLo || cell.X > walkRegionHi || cell.Y < walkRegionLo || cell.Y > walkRegionHi {
				t.Fatalf("iter %d step %d: cell (%d,%d) out of region [%d,%d]",
					iter, i, cell.X, cell.Y, walkRegionLo, walkRegionHi)
			}
			px, py = cell.X, cell.Y
		}
	}
}

// TestBuildWalkLeg_HeadsTowardWaypoint verifies a leg makes net progress
// toward its target (dispersal), not a symmetric random walk that stays put.
func TestBuildWalkLeg_HeadsTowardWaypoint(t *testing.T) {
	rng := rand.New(rand.NewSource(42))
	// From a corner toward the opposite corner, a leg should end strictly
	// closer to the target than it started, on average across many runs.
	closer := 0
	const runs = 400
	for i := 0; i < runs; i++ {
		from := int32(0)
		to := int32(walkRegionHi)
		path := buildWalkLeg(from, from, to, to, rng)
		if len(path) == 0 {
			continue
		}
		last := path[len(path)-1]
		startDist := abs32t(to-from) + abs32t(to-from)
		endDist := abs32t(to-last.X) + abs32t(to-last.Y)
		if endDist < startDist {
			closer++
		}
	}
	if closer < runs*9/10 {
		t.Fatalf("only %d/%d legs moved toward the waypoint; roaming is not dispersing bots", closer, runs)
	}
}
