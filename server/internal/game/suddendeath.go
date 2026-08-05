package game

import "github.com/StarLoco/arena-2.70/internal/protocol"

// Sudden death ("Mort Subite"): from a fixed table turn onward the arena shrinks,
// ring by ring, from the outside in. A fighter dies ONLY if it is standing on a
// cell removed by that step — being outside the surviving area is not lethal by
// itself, you simply cannot walk back out. Summons count as fighters here.
//
// The client's i18n puts the default at turn 15 ("Pourquoi attendre 15 tours avant
// que les équipes se rencontrent"); tournament rule cards shift it by ±5/±10 turns
// and presets pin it to turn 2 or 10, so suddenDeathTurn is a var.
//
// --- How the client renders it ------------------------------------------------
//
// mh_2 action id 117 ("Destruction de terrain") is bound to class mw_2, whose own
// error string names it MapDestruction. It is driven by the ORDINARY running-effect
// message, RUNNING_EFFECT (8120) — the same one damage and heals use — with the
// action id in the runningEffectId field:
//
//	of_1 case 8120: el = mh_2.YJ().cr(actionId); mv_0(...el, blob...); if (execNow) run()
//
// mv_0 calls akd() on the effect, which clears the "instant" flag, so mw_2 takes its
// PROGRESSIVE branch: it walks a square spiral (in_0) outward from the effect's
// target cell, builds the cell list by PREPENDING (so the list runs outermost →
// centre) and destroys exactly the first `r` entries, where r is the last field of
// running-effect part 0. Each destroyed cell gets asF.bV — movement-block bit set
// and tile graphics hidden — and entities standing on it are killed locally.
//
// So the server controls the collapse entirely through `r`, and both sides derive
// the same cell order from the same spiral. spiralWalker below reproduces in_0
// exactly; if it ever drifts, the two would silently disagree about which cells are
// gone. (The sibling opcode 8121 only ATTACHES an effect to a fighter as a buff —
// it never executes one, which is why sending MapDestruction there did nothing.)
//
// Because the progressive branch never reads the effect's parameters, mw_2 keeps its
// field defaults Ny=18 (the 18x18 arena) and Nz=5 (a 5x5 core) — no data-backed
// action-117 effect is needed. See BUGS.md B-050.
var (
	// suddenDeathTurn is the table turn on which the arena starts to shrink.
	suddenDeathTurn int32 = 15
	// mapDestructionNy is the spiral bound and mapDestructionNz the core that
	// survives — both mirror the client's field defaults.
	mapDestructionNy int32 = 18
	mapDestructionNz int32 = 5
)

// mapDestructionAction is the mh_2 action id bound to MapDestruction (mw_2).
const mapDestructionAction int32 = 117

// suddenDeathCentre is the point the arena collapses toward.
func suddenDeathCentre(a *arena) Pos {
	return Pos{X: a.centerX, Y: a.centerY}
}

// spiralWalker reproduces the client's in_0 square-spiral generator byte-for-byte.
// It yields movement DELTAS: first (0,0) (the centre itself), then 1×right, 1×down,
// 2×left, 2×up, 3×right, 3×down, 4×left, 4×up, …
type spiralWalker struct {
	leg   int // in_0.bgC — current leg length; also selects the direction pair
	index int // in_0.m_index — steps taken along the current leg
	dir   int // in_0.bgD — which of the two directions in the current pair
}

// next returns the next delta, mirroring in_0.Ug().
func (g *spiralWalker) next() (int32, int32) {
	if g.leg == 0 {
		g.leg = 1
		g.dir = 0
		return 0, 0
	}
	g.index++
	// bgA = {{1,0},{0,1}} on odd legs; bgB = {{-1,0},{0,-1}} on even legs.
	var pair [2][2]int32
	if g.leg%2 == 0 {
		pair = [2][2]int32{{-1, 0}, {0, -1}}
	} else {
		pair = [2][2]int32{{1, 0}, {0, 1}}
	}
	d := pair[g.dir]
	if g.index == g.leg {
		if g.dir == 0 {
			g.dir = 1
			g.index = 0
		} else {
			g.dir = 0
			g.index = 0
			g.leg++
		}
	}
	return d[0], d[1]
}

// mapDestructionOrder returns the cells in the order the client DESTROYS them:
// outermost first, centre last. The client generates the spiral from the centre
// outward but prepends each cell, so its list is the reverse of generation order —
// this returns that same reversed list.
func mapDestructionOrder(centre Pos, ny int32) []Pos {
	var g spiralWalker
	x, y := centre.X, centre.Y
	total := int(ny * ny)
	gen := make([]Pos, 0, total)
	for i := 0; i < total; i++ {
		dx, dy := g.next()
		x += dx
		y += dy
		gen = append(gen, Pos{X: x, Y: y})
	}
	out := make([]Pos, 0, total)
	for i := len(gen) - 1; i >= 0; i-- {
		out = append(out, gen[i])
	}
	return out
}

// suddenDeathFloorCellsPerTurn is how many REAL (walkable) cells each turn of the
// collapse should swallow. Counting floor rather than raw spiral cells keeps the
// pace even: the arena's outer band is mostly void, so a fixed step in `r` would
// eat nothing for several turns and then take a huge bite.
const suddenDeathFloorCellsPerTurn = 12

// suddenDeathSchedule returns the cumulative destroy counts (`r`) for each shrink
// step, walking the destruction order and cutting a step every time another
// suddenDeathFloorCellsPerTurn walkable cells have been passed. The final entry is
// always ny²−nz², i.e. everything outside the client's default core, so the collapse
// ends exactly where the client's own geometry does.
func suddenDeathSchedule(a *arena, ny, nz int32) []int32 {
	order := mapDestructionOrder(suddenDeathCentre(a), ny)
	finalR := ny*ny - nz*nz
	if int(finalR) > len(order) {
		finalR = int32(len(order))
	}
	var out []int32
	floor := 0
	for i := int32(0); i < finalR; i++ {
		c := order[i]
		if a.walkable(c.X, c.Y) {
			floor++
		}
		if floor >= suddenDeathFloorCellsPerTurn {
			out = append(out, i+1)
			floor = 0
		}
	}
	if len(out) == 0 || out[len(out)-1] != finalR {
		out = append(out, finalR)
	}
	return out
}

// cellDestroyed reports whether the collapse has removed cell (x,y) in THIS fight.
// Per-fight state: the arena value is shared and must never be mutated.
func (f *Fight) cellDestroyed(x, y int32) bool {
	if f.destroyedCells == nil {
		return false
	}
	return f.destroyedCells[[2]int32{x, y}]
}

// maybeTriggerSuddenDeath advances the collapse by one step once the fight reaches
// the sudden-death turn. Called on each new table turn; a no-op before then and
// after the arena has shrunk to its core. Must run on the fight goroutine.
func (f *Fight) maybeTriggerSuddenDeath() {
	if f.tableTurn < f.suddenDeathTurnFor() {
		return
	}
	f.advanceSuddenDeath()
}

// advanceSuddenDeath performs the next shrink step: destroy the newly doomed cells,
// kill only the fighters standing on them, and tell the client to render the same
// collapse. Must run on the fight goroutine.
func (f *Fight) advanceSuddenDeath() {
	schedule := suddenDeathSchedule(f.Arena(), mapDestructionNy, mapDestructionNz)
	centre := suddenDeathCentre(f.Arena())
	order := mapDestructionOrder(centre, mapDestructionNy)

	// The spiral covers the whole 18x18 grid, but the arena's outer band is void:
	// the earliest steps would remove nothing real and burn turns invisibly. Skip
	// ahead to the next step that actually takes a walkable cell. r stays the true
	// cumulative count the client needs.
	var r int32
	for {
		if f.suddenDeathStep >= len(schedule) {
			return // fully shrunk; the central arena stays
		}
		r = schedule[f.suddenDeathStep]
		f.suddenDeathStep++
		if int(r) > len(order) {
			r = int32(len(order))
		}
		if f.stepTakesWalkableCell(order[:r]) {
			break
		}
	}

	if f.destroyedCells == nil {
		f.destroyedCells = make(map[[2]int32]bool)
	}

	// Cells newly removed by THIS step: the prefix up to r that we had not already
	// taken. Only fighters standing on one of these die.
	var fresh []Pos
	for _, c := range order[:r] {
		key := [2]int32{c.X, c.Y}
		if f.destroyedCells[key] {
			continue
		}
		f.destroyedCells[key] = true
		fresh = append(fresh, c)
	}

	// Render it: the client recomputes the identical spiral from the centre and
	// destroys its first r cells. A target fighter is required — the effect is
	// dropped if it does not resolve.
	// mustExecNow=false QUEUES the effect on the client's action sequence (the 8200
	// flush below plays it) instead of running it the instant the frame lands. That
	// is how damage and heals are sent, and it is what lets the client animate the
	// collapse rather than snapping the cells out of existence.
	if anchor := f.anyLivingFighter(); anchor != nil {
		if frame, err := buildRunningEffect(f.nextActionUID(), mapDestructionAction,
			f.scriptEffectID(), anchor.WireID, anchor.WireID, centre, r, 0, false); err == nil {
			f.broadcast(frame)
		}
	}

	killed := 0
	for _, ff := range f.allFighters() {
		if ff.HP <= 0 || !f.cellDestroyed(ff.Pos.X, ff.Pos.Y) {
			continue
		}
		killed++
		f.applyHPDelta(ff, ff, protocol.RunEffectHPLoss, 0, -ff.HP)
	}
	if seq, err := buildActionSequenceExecute(); err == nil {
		f.broadcast(seq)
	}
	if f.deps != nil {
		if f.deps.Log != nil {
			f.deps.Log.Debug("sudden death: arena shrunk", "turn", f.tableTurn,
				"step", f.suddenDeathStep, "r", r, "newCells", len(fresh), "killed", killed)
		}
		if killed > 0 {
			f.deps.checkFightEnd(f)
		}
	}
}

// stepTakesWalkableCell reports whether the given destroy-prefix would remove at
// least one real floor cell this fight has not already lost. Used to skip shrink
// steps that would only clear void.
func (f *Fight) stepTakesWalkableCell(prefix []Pos) bool {
	for _, c := range prefix {
		if f.cellDestroyed(c.X, c.Y) {
			continue
		}
		if f.Arena().walkable(c.X, c.Y) {
			return true
		}
	}
	return false
}

// anyLivingFighter returns any fighter still standing, used to anchor an effect
// that requires a resolvable target. nil when none is left.
func (f *Fight) anyLivingFighter() *FightFighter {
	for _, ff := range f.allFighters() {
		if ff.HP > 0 {
			return ff
		}
	}
	return nil
}

// scriptEffectID returns a generic-effect id the client can resolve. MapDestruction
// never reads it in the progressive branch, but part 0 carries one and an unknown id
// makes the client log a deserialisation error, so prefer a real one.
func (f *Fight) scriptEffectID() int32 {
	if f.deps != nil && f.deps.Spells != nil {
		if id, ok := f.deps.Spells.AnyEffectIDWithParamCountOtherThan(2); ok {
			return id
		}
	}
	return 0
}
