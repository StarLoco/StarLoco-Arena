package game

// pathfind.go provides the server-side movement search the built-in AI needs.
// A human fighter's moves are validated against the client's own path
// (validateFightMove); an AI fighter (sparring opponent / summon) has no client,
// so the server must compute its walk itself. reachableCells is a breadth-first
// flood over the arena grid, 4-directional (the cardinal grid steps the client's
// walk animator renders), bounded by movement points, blocked by living
// fighters. It returns, for every reachable cell, the shortest step path to it
// (EXCLUDING the origin — the same origin-excluded form the move wire uses).

// cardinalDirs are the four grid steps a fighter may walk (no diagonals).
var cardinalDirs = [4][2]int32{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}

// reachableCells floods outward from start up to maxMP steps, returning a map
// keyed by (x,y) to the shortest origin-excluded path reaching that cell. The
// start cell itself is not included. Cells occupied by a living fighter other
// than `mover` are impassable (a fighter cannot walk through another), and only
// real walkable arena cells are traversed. Altitude is not gated here — the
// client animates whatever contiguous walkable path the server sends.
func (f *Fight) reachableCells(mover *FightFighter, start Pos, maxMP int32) map[[2]int32][]Pos {
	out := make(map[[2]int32][]Pos)
	if maxMP <= 0 {
		return out
	}
	type node struct {
		cell Pos
		dist int32
	}
	startKey := [2]int32{start.X, start.Y}
	visited := map[[2]int32]bool{startKey: true}
	queue := []node{{cell: start, dist: 0}}
	paths := map[[2]int32][]Pos{startKey: nil}

	for len(queue) > 0 {
		cur := queue[0]
		queue = queue[1:]
		if cur.dist >= maxMP {
			continue
		}
		curKey := [2]int32{cur.cell.X, cur.cell.Y}
		for _, d := range cardinalDirs {
			nx, ny := cur.cell.X+d[0], cur.cell.Y+d[1]
			key := [2]int32{nx, ny}
			if visited[key] {
				continue
			}
			if !f.Arena().walkable(nx, ny) {
				continue
			}
			cell := Pos{X: nx, Y: ny, Z: f.Arena().altitudeAt(nx, ny)}
			if f.cellHeldByOther(cell, mover) {
				continue
			}
			visited[key] = true
			path := make([]Pos, 0, len(paths[curKey])+1)
			path = append(path, paths[curKey]...)
			path = append(path, cell)
			paths[key] = path
			out[key] = path
			queue = append(queue, node{cell: cell, dist: cur.dist + 1})
		}
	}
	return out
}
