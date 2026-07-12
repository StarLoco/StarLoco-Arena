package combat

import (
	"container/heap"
)

// CellInfoProvider abstracts the fight's spatial state for pathfinding and
// movement resolution, mirroring how BasicFight itself implements the
// LOS/obstacle provider interfaces directly (no separate grid object) --
// see docs/05-combat-engine.md §5.8. A *Fight implements this interface.
type CellInfoProvider interface {
	// IsWalkable reports whether a cell exists on the map and is not a
	// permanent map obstacle (walls, holes). Does not check fighter
	// occupancy -- see IsOccupied.
	IsWalkable(p Point3) bool
	// IsOccupied reports whether a living fighter (other than exclude)
	// currently stands on p's X/Y (ignoring Z), mirroring
	// BasicFight.getMovementObstacle's linear scan.
	IsOccupied(p Point3, exclude *Fighter) bool
	// ArrivalAltitude returns the Z a mover would land at moving from
	// fromZ onto the cell at (toX,toY), and whether that step is height-
	// blocked (delta exceeds the mover's ascend/descend limits). Backed by
	// real .amw/elements.ade map data when available (see turns.go's
	// Fight.ArrivalAltitude, docs/08-java-parity-roadmap.md Phase K);
	// falls back to the target cell's unchanged Z with no blocking if no
	// map data is attached to the fight.
	ArrivalAltitude(mover *Fighter, fromZ int16, to Point3) (altitude int16, blocked bool)
}

// fightMoveDirections lists the ONLY directions a fighter may move in
// during a fight: the four "diagonal-named" directions SOUTH_EAST(1),
// SOUTH_WEST(3), NORTH_WEST(5), NORTH_EAST(7). These are the only
// directions with fighter sprite art in the isometric client; the four
// cardinal-named directions EAST(0)/SOUTH(2)/WEST(4)/NORTH(6) render blank,
// so a path step in one of them makes the mover invisible client-side (see
// defaultTeamFacing in fighter.go, docs/08-java-parity-roadmap.md §8.17).
// This mirrors the reference client's fight movement, which sets
// PathFindParameters.m_useDiagonals = false and pathfinds over
// Direction8.DIRECTION_4_VALUES only (see
// UIFightMoveWorldSceneInteractionFrame).
//
// IMPORTANT: despite their "diagonal" names, these four directions are
// SINGLE-AXIS grid moves in the client's Direction8 vectors -- SE=(+1,0),
// SW=(0,+1), NW=(-1,0), NE=(0,-1) (they only look diagonal on the 2:1
// isometric projection). So they cover all four grid-orthogonal neighbors:
// every cell is reachable (no parity/checkerboard dead zones), and each
// step changes exactly one grid axis. Because the server re-derives and
// broadcasts the authoritative path (turns.go handleFighterMove) as raw
// X/Y cells, and the client derives facing from each consecutive grid
// delta, the pathfinder must only ever emit these single-axis steps.
var fightMoveDirections = [4]Direction8{
	DirSouthEast, DirSouthWest, DirNorthWest, DirNorthEast,
}

const (
	// stepCost is a flat 1 per cell: every legal fight step is a
	// single-axis grid move costing exactly 1 MP (see PathMPCost), matching
	// the Manhattan heuristic below so A* stays admissible.
	stepCost   = 1.0
	maxAscend  = 4
	maxDescend = 4
	// summonMaxAltitudeStep caps how many altitude levels a SUMMON may climb
	// or descend in a single step. The wiki ("Summons do not jump down cliffs
	// with altitude of 2 or higher, nor can they climb them") restricts
	// summons to altitude changes of at most 1; a normal fighter uses the
	// larger maxAscend/maxDescend. See moverMaxAscend/moverMaxDescend.
	summonMaxAltitudeStep = 1
)

// moverMaxAscend/moverMaxDescend return the per-step altitude climb/descent
// limit for a given mover: summons are clamped to summonMaxAltitudeStep (they
// can't traverse cliffs of altitude >= 2), everyone else uses the normal
// maxAscend/maxDescend.
func moverMaxAscend(mover *Fighter) int {
	if isAISummon(mover) {
		return summonMaxAltitudeStep
	}
	return maxAscend
}

func moverMaxDescend(mover *Fighter) int {
	if isAISummon(mover) {
		return summonMaxAltitudeStep
	}
	return maxDescend
}

// pathNode is one entry in the A* open/closed sets.
type pathNode struct {
	pos    Point3
	g      float64 // cost from start
	f      float64 // g + heuristic
	parent *pathNode
	index  int // heap index, maintained by container/heap
}

type nodeHeap []*pathNode

func (h nodeHeap) Len() int           { return len(h) }
func (h nodeHeap) Less(i, j int) bool { return h[i].f < h[j].f }
func (h nodeHeap) Swap(i, j int)      { h[i], h[j] = h[j], h[i]; h[i].index = i; h[j].index = j }
func (h *nodeHeap) Push(x interface{}) {
	n := x.(*pathNode)
	n.index = len(*h)
	*h = append(*h, n)
}
func (h *nodeHeap) Pop() interface{} {
	old := *h
	n := len(old)
	item := old[n-1]
	old[n-1] = nil
	item.index = -1
	*h = old[:n-1]
	return item
}

func cellKey(p Point3) [2]int32 { return [2]int32{p.X, p.Y} }

func abs32(v int32) int32 {
	if v < 0 {
		return -v
	}
	return v
}

func heuristic(from, to Point3) float64 {
	// Manhattan distance: fight movement uses only single-axis grid steps
	// (see fightMoveDirections), each costing 1, so |dx|+|dy| is the exact
	// minimum step count on an open field and A* stays admissible.
	dx := abs32(from.X - to.X)
	dy := abs32(from.Y - to.Y)
	return float64(dx) + float64(dy)
}

// FindPath runs A* from start to goal over provider's cell state for
// mover, returning the ordered list of steps (excluding start, including
// goal), or nil if no path exists. Movement is restricted to the four
// diagonal-named directions (fightMoveDirections), which are single-axis
// grid moves, so the emitted path never contains a step that the client
// would resolve to a cardinal (invisible) facing.
func FindPath(mover *Fighter, start, goal Point3, provider CellInfoProvider) []Point3 {
	if start.X == goal.X && start.Y == goal.Y {
		return nil
	}

	open := &nodeHeap{}
	heap.Init(open)
	startNode := &pathNode{pos: start, g: 0, f: heuristic(start, goal)}
	heap.Push(open, startNode)

	openIndex := map[[2]int32]*pathNode{cellKey(start): startNode}
	closed := map[[2]int32]bool{}

	const maxIterations = 4096 // defensive bound against pathological maps
	iterations := 0

	for open.Len() > 0 {
		iterations++
		if iterations > maxIterations {
			return nil
		}
		current := heap.Pop(open).(*pathNode)
		delete(openIndex, cellKey(current.pos))
		if current.pos.X == goal.X && current.pos.Y == goal.Y {
			return reconstructPath(current)
		}
		closed[cellKey(current.pos)] = true

		for _, dir := range fightMoveDirections {
			next := current.pos.Step(dir)
			nk := cellKey(next)
			if closed[nk] {
				continue
			}
			if !provider.IsWalkable(next) {
				continue
			}
			if provider.IsOccupied(next, mover) {
				continue
			}
			// No corner-cutting check needed: every fight step is a
			// single-axis grid move (only X or only Y changes), so it can
			// never cut across a blocked corner the way a two-axis move
			// would.

			altitude, blocked := provider.ArrivalAltitude(mover, current.pos.Z, next)
			if blocked {
				continue
			}
			delta := int(altitude) - int(current.pos.Z)
			if delta > moverMaxAscend(mover) || -delta > moverMaxDescend(mover) {
				continue
			}
			next.Z = altitude

			g := current.g + stepCost

			if existing, ok := openIndex[nk]; ok {
				if g < existing.g {
					existing.g = g
					existing.f = g + heuristic(next, goal)
					existing.parent = current
					heap.Fix(open, existing.index)
				}
				continue
			}

			n := &pathNode{pos: next, g: g, f: g + heuristic(next, goal), parent: current}
			heap.Push(open, n)
			openIndex[nk] = n
		}
	}
	return nil
}

func reconstructPath(n *pathNode) []Point3 {
	var out []Point3
	for cur := n; cur.parent != nil; cur = cur.parent {
		out = append([]Point3{cur.pos}, out...)
	}
	return out
}

// ReachableCells does a breadth-first flood from `start` over the same
// movement rules FindPath uses (single-axis fight steps to walkable,
// unoccupied, altitude-reachable cells), bounded to at most `maxSteps` MP,
// and returns the shortest path from start to every reachable cell keyed by
// "x,y" (the start cell itself is NOT included). Used by the summon AI to
// evaluate all cells it could stand on this turn and pick the best one in a
// single pass -- far more stable than re-scanning candidate rings each turn
// (which produced the "walk forward then back" oscillation when equally-good
// cells were picked inconsistently). BFS is exact here because every step
// costs 1 MP.
func ReachableCells(mover *Fighter, start Point3, maxSteps int, provider CellInfoProvider) map[[2]int32][]Point3 {
	out := make(map[[2]int32][]Point3)
	if maxSteps <= 0 {
		return out
	}
	type qentry struct {
		pos   Point3
		steps int
	}
	dist := map[[2]int32]int{cellKey(start): 0}
	queue := []qentry{{pos: start, steps: 0}}
	// parentCell[nk] = the cell (with resolved Z) stepped FROM to reach nk;
	// resolved[nk] = nk's own cell with its resolved arrival Z.
	parentCell := map[[2]int32]Point3{}
	resolved := map[[2]int32]Point3{cellKey(start): start}
	const maxVisited = 8192 // defensive bound
	visited := 0
	for len(queue) > 0 {
		cur := queue[0]
		queue = queue[1:]
		if cur.steps >= maxSteps {
			continue
		}
		visited++
		if visited > maxVisited {
			break
		}
		for _, dir := range fightMoveDirections {
			next := cur.pos.Step(dir)
			nk := cellKey(next)
			if _, seen := dist[nk]; seen {
				continue
			}
			if !provider.IsWalkable(next) || provider.IsOccupied(next, mover) {
				continue
			}
			altitude, blocked := provider.ArrivalAltitude(mover, cur.pos.Z, next)
			if blocked {
				continue
			}
			delta := int(altitude) - int(cur.pos.Z)
			if delta > moverMaxAscend(mover) || -delta > moverMaxDescend(mover) {
				continue
			}
			next.Z = altitude
			dist[nk] = cur.steps + 1
			parentCell[nk] = cur.pos
			resolved[nk] = next
			out[nk] = buildReachablePath(start, nk, parentCell, resolved)
			queue = append(queue, qentry{pos: next, steps: cur.steps + 1})
		}
	}
	return out
}

// buildReachablePath reconstructs the ordered step list (excluding start,
// including goal) from ReachableCells' parent/resolved maps.
func buildReachablePath(start Point3, goalKey [2]int32, parentCell, resolved map[[2]int32]Point3) []Point3 {
	var rev []Point3
	k := goalKey
	for k != cellKey(start) {
		rev = append(rev, resolved[k])
		p, ok := parentCell[k]
		if !ok {
			break
		}
		k = cellKey(p)
	}
	for i, j := 0, len(rev)-1; i < j; i, j = i+1, j-1 {
		rev[i], rev[j] = rev[j], rev[i]
	}
	return rev
}

// isFightStepDir reports whether stepping from `from` to `to` is exactly one
// of the four legal single-axis fight-movement directions (SE/SW/NW/NE).
// Rejects staying in place, two-axis (cardinal-facing / invisible) moves,
// and jumps of more than one cell.
func isFightStepDir(from, to Point3) bool {
	dx := abs32(to.X - from.X)
	dy := abs32(to.Y - from.Y)
	return dx+dy == 1
}

// ValidateClientPath checks the client's requested step list (each entry the
// next cell to enter, EXCLUDING the fighter's start cell) against the same
// rules FindPath enforces: every step must be a single-axis fight move to a
// walkable, unoccupied, altitude-reachable cell. It returns the resolved
// path with each cell's arrival altitude filled in (same shape as FindPath's
// output), or nil if any step is illegal.
//
// Preferring the client's own path -- rather than re-deriving a fresh A*
// route -- makes the server FOLLOW the exact green path the client computed
// and displayed, instead of an equally-short but visually different route.
// Server authority is preserved because every step is still validated here.
func ValidateClientPath(mover *Fighter, start Point3, steps []Point3, provider CellInfoProvider) []Point3 {
	if len(steps) == 0 {
		return nil
	}
	out := make([]Point3, 0, len(steps))
	cur := start
	seen := map[[2]int32]bool{cellKey(start): true}
	for _, raw := range steps {
		next := Point3{X: raw.X, Y: raw.Y, Z: cur.Z}
		if !isFightStepDir(cur, next) {
			return nil
		}
		if seen[cellKey(next)] {
			return nil // no revisiting a cell (would loop / miscount MP)
		}
		if !provider.IsWalkable(next) || provider.IsOccupied(next, mover) {
			return nil
		}
		altitude, blocked := provider.ArrivalAltitude(mover, cur.Z, next)
		if blocked {
			return nil
		}
		delta := int(altitude) - int(cur.Z)
		if delta > moverMaxAscend(mover) || -delta > moverMaxDescend(mover) {
			return nil
		}
		next.Z = altitude
		out = append(out, next)
		seen[cellKey(next)] = true
		cur = next
	}
	return out
}

// PathMPCost returns the MP cost of a resolved path: 1 per step, per
// docs/05-combat-engine.md §5.8.
func PathMPCost(path []Point3) int {
	return len(path)
}
