package game

import (
	"sort"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// ai.go drives the built-in AI that plays the turn of any fighter no client
// controls: a summoned creature (has a Father) and the sparring opponent (its
// team has no session). Without it such a fighter would just idle until its turn
// clock force-ended. The behaviour is a port of the v2.04b server's summon AI
// (internal/combat/summon_ai.go), adapted to the 2.70 fight model: a summon's
// archetype is DERIVED from its single spell + stats (there is no behaviour data
// in the game files), so it stays correct as the underlying spell data changes.
//
//   - Blocker    (no spell): walk adjacent to the nearest enemy and body-block.
//   - Aggressive (spell damages enemies): close into range and cast until dry.
//   - Kite       (debuff spell, or lots of MP): cast from range, then retreat.
//   - Self-buff  (self-targeted buff): cast on self, then block the nearest enemy.

type aiBehavior int

const (
	behaviorBlocker    aiBehavior = iota // no spell: close in and body-block
	behaviorSelfBuff                     // buffs self: cast on self, then block
	behaviorKite                         // debuffs enemies / high MP: cast then retreat
	behaviorAggressive                   // damages enemies: close in and cast
)

// kiteMPThreshold: a damage summon with at least this much MP hits and runs
// instead of standing and trading (e.g. a nimble Tofu).
const kiteMPThreshold int32 = 4

// runAITurn plays a full AI turn for `ff` and then ends its turn. Armed from
// beginTurn (on the short aiTurnClock) so the client renders the turn-begin
// first. Runs on the fight actor goroutine like every other turn-progression
// call, so ending the turn at the end is safe.
func (f *Fight) runAITurn(ff *FightFighter) {
	if f.Phase() != PhaseAction || !f.isCurrentTurn(ff.WireID) {
		return
	}
	switch f.classifyAI(ff) {
	case behaviorSelfBuff:
		f.playSelfBuffAI(ff)
	case behaviorAggressive:
		f.playAggressiveAI(ff)
	case behaviorKite:
		f.playKiteAI(ff)
	default: // behaviorBlocker
		f.moveTowardNearestOpponent(ff)
	}

	// If the fighter died mid-turn (kamikaze cast / lethal cell) or its cast
	// ended the fight, the turn already advanced centrally — don't end it again.
	if ff.HP <= 0 || f.Phase() != PhaseAction || !f.isCurrentTurn(ff.WireID) {
		return
	}
	f.endTurn(ff.WireID)
}

// classifyAI derives the fighter's behaviour archetype from its spell. No spell
// (or no gamedata) -> blocker. Otherwise inspect the spell's effect kinds: any
// damage -> aggressive (or kite if very mobile); a debuff -> kite; a self-only
// buff -> self-buff.
func (f *Fight) classifyAI(ff *FightFighter) aiBehavior {
	if ff.SummonSpellID == 0 || f.deps == nil || f.deps.Spells == nil {
		return behaviorBlocker
	}
	sp := f.deps.Spells.Get(ff.SummonSpellID)
	if sp == nil {
		return behaviorBlocker
	}
	var damages, debuffs, buffsSelf bool
	for _, ef := range sp.Effects {
		switch ef.Kind() {
		case gamedata.KindDamage, gamedata.KindLeech, gamedata.KindPercentHP,
			gamedata.KindPoison, gamedata.KindScaledAP, gamedata.KindScaledMP,
			gamedata.KindInstantDeath:
			damages = true
		case gamedata.KindAPLoss, gamedata.KindMPLoss, gamedata.KindAPSteal, gamedata.KindMPSteal:
			debuffs = true
		case gamedata.KindBuff:
			if sp.RangeMax == 0 { // a range-0 spell only targets its own cell
				buffsSelf = true
			}
		}
	}
	switch {
	case damages:
		if ff.MP >= kiteMPThreshold {
			return behaviorKite
		}
		return behaviorAggressive
	case debuffs:
		return behaviorKite
	case buffsSelf:
		return behaviorSelfBuff
	default:
		return behaviorBlocker
	}
}

// playAggressiveAI closes into spell range of the nearest enemy and casts until
// out of AP or range.
func (f *Fight) playAggressiveAI(ff *FightFighter) {
	target := f.nearestOpponent(ff)
	if target == nil {
		return
	}
	f.moveIntoSpellRange(ff, target)
	f.castAISpellRepeatedly(ff)
}

// playKiteAI casts at the nearest enemy from range, then retreats with leftover
// MP (hit-and-run).
func (f *Fight) playKiteAI(ff *FightFighter) {
	target := f.nearestOpponent(ff)
	if target == nil {
		return
	}
	f.moveIntoSpellRange(ff, target)
	f.castAISpellRepeatedly(ff)
	if ff.HP > 0 && f.isCurrentTurn(ff.WireID) {
		f.retreatFromOpponents(ff)
	}
}

// playSelfBuffAI casts the self-buff on its own cell, then blocks the nearest
// enemy.
func (f *Fight) playSelfBuffAI(ff *FightFighter) {
	if ff.SummonSpellID != 0 {
		f.castSpellByFighter(ff, ff.SummonSpellID, ff.Pos)
	}
	if ff.HP > 0 && f.isCurrentTurn(ff.WireID) {
		f.moveTowardNearestOpponent(ff)
	}
}

// castAISpellRepeatedly casts the fighter's spell at the re-evaluated nearest
// enemy as many times as AP allows, stopping when a cast fails validation.
func (f *Fight) castAISpellRepeatedly(ff *FightFighter) {
	if ff.SummonSpellID == 0 || f.deps == nil || f.deps.Spells == nil {
		return
	}
	sp := f.deps.Spells.Get(ff.SummonSpellID)
	if sp == nil {
		return
	}
	apCost := int32(sp.AP)
	if apCost <= 0 {
		apCost = 1
	}
	maxCasts := int(ff.AP) + 1 // hard safety cap; castSpellByFighter self-limits
	for i := 0; i < maxCasts; i++ {
		if ff.HP <= 0 || !f.isCurrentTurn(ff.WireID) || ff.AP < apCost {
			return
		}
		target := f.nearestOpponent(ff)
		if target == nil {
			return
		}
		if !f.castSpellByFighter(ff, ff.SummonSpellID, target.Pos) {
			return // out of range / LoS / AP: stop trying
		}
	}
}

// moveIntoSpellRange walks the fighter (MP-limited) to the nearest reachable cell
// from which it can hit `target` with its spell. For a melee (range-1) spell this
// is the same as getting adjacent. No-op if it can already fire or can't improve.
func (f *Fight) moveIntoSpellRange(ff, target *FightFighter) {
	if f.deps == nil || f.deps.Spells == nil {
		f.moveTowardNearestOpponent(ff)
		return
	}
	sp := f.deps.Spells.Get(ff.SummonSpellID)
	if sp == nil {
		f.moveTowardNearestOpponent(ff)
		return
	}
	rMin, rMax := int32(sp.RangeMin), int32(sp.RangeMax)
	if f.canHitFrom(ff.Pos, target.Pos, rMin, rMax, sp.TestLoS) {
		return // already able to fire
	}
	if ff.MP <= 0 || ff.hasState(stateRooted) {
		return
	}
	var bestPath []Pos
	bestCanFire := false
	bestScore := firingGap(ff.Pos, target.Pos, rMin, rMax)
	bestLen := 0
	for _, path := range f.orderedReachablePaths(ff, ff.MP) {
		cell := path[len(path)-1]
		canFire := f.canHitFrom(cell, target.Pos, rMin, rMax, sp.TestLoS)
		score := firingGap(cell, target.Pos, rMin, rMax)
		better := (canFire && !bestCanFire) ||
			(canFire == bestCanFire && score < bestScore) ||
			(canFire == bestCanFire && score == bestScore && bestPath != nil && len(path) < bestLen)
		if better {
			bestPath, bestCanFire, bestScore, bestLen = path, canFire, score, len(path)
		}
	}
	if len(bestPath) > 0 {
		f.applyFighterMove(ff, bestPath)
	}
}

// moveTowardNearestOpponent walks the fighter toward the cell nearest the closest
// living opponent (blocker / self-buff behaviour). No-op if already adjacent, out
// of MP, or unable to improve.
func (f *Fight) moveTowardNearestOpponent(ff *FightFighter) {
	if ff.MP <= 0 || ff.hasState(stateRooted) {
		return
	}
	target := f.nearestOpponent(ff)
	if target == nil || manhattanDist(ff.Pos, target.Pos) <= 1 {
		return
	}
	bestDist := manhattanDist(ff.Pos, target.Pos)
	var bestPath []Pos
	bestLen := 0
	for _, path := range f.orderedReachablePaths(ff, ff.MP) {
		cell := path[len(path)-1]
		d := manhattanDist(cell, target.Pos)
		if d < bestDist || (d == bestDist && bestPath != nil && len(path) < bestLen) {
			bestPath, bestDist, bestLen = path, d, len(path)
		}
	}
	if len(bestPath) > 0 {
		f.applyFighterMove(ff, bestPath)
	}
}

// retreatFromOpponents walks the fighter to the reachable cell that maximises its
// minimum distance to any living enemy (used after a kite cast). Only moves if it
// strictly improves safety.
func (f *Fight) retreatFromOpponents(ff *FightFighter) {
	if ff.MP <= 0 || ff.hasState(stateRooted) {
		return
	}
	currentSafety := f.minEnemyDistance(ff, ff.Pos)
	if currentSafety < 0 {
		return // no enemies
	}
	bestSafety := currentSafety
	var bestPath []Pos
	bestLen := 0
	for _, path := range f.orderedReachablePaths(ff, ff.MP) {
		cell := path[len(path)-1]
		safety := f.minEnemyDistance(ff, cell)
		if safety > bestSafety || (safety == bestSafety && bestPath != nil && len(path) < bestLen) {
			bestPath, bestSafety, bestLen = path, safety, len(path)
		}
	}
	if len(bestPath) > 0 {
		f.applyFighterMove(ff, bestPath)
	}
}

// orderedReachablePaths returns every reachable-cell path (from reachableCells)
// in a deterministic order (shorter routes first, then by destination coords),
// so the AI's "first strictly-better wins" tie-breaks are stable across the map's
// random Go iteration order.
func (f *Fight) orderedReachablePaths(ff *FightFighter, mp int32) [][]Pos {
	reachable := f.reachableCells(ff, ff.Pos, mp)
	paths := make([][]Pos, 0, len(reachable))
	for _, p := range reachable {
		if len(p) > 0 {
			paths = append(paths, p)
		}
	}
	sort.Slice(paths, func(i, j int) bool {
		a, b := paths[i], paths[j]
		if len(a) != len(b) {
			return len(a) < len(b)
		}
		la, lb := a[len(a)-1], b[len(b)-1]
		if la.X != lb.X {
			return la.X < lb.X
		}
		return la.Y < lb.Y
	})
	return paths
}

// canHitFrom reports whether a caster at `from` could cast a spell of the given
// range at `to` (Manhattan range window + optional line-of-sight).
func (f *Fight) canHitFrom(from, to Pos, rMin, rMax int32, testLOS bool) bool {
	d := manhattanDist(from, to)
	if d < rMin || d > rMax {
		return false
	}
	if testLOS && !f.Arena().hasLineOfSight(from, to) {
		return false
	}
	return true
}

// firingGap is how many cells `from` is outside the spell's [rMin,rMax] range
// window to `to` (0 if in range) — a proxy for "how much closer to get".
func firingGap(from, to Pos, rMin, rMax int32) int32 {
	d := manhattanDist(from, to)
	switch {
	case d > rMax:
		return d - rMax
	case d < rMin:
		return rMin - d
	default:
		return 0
	}
}

// nearestOpponent returns the living opponent the fighter should engage: smallest
// Manhattan distance, tie-broken by highest initiative, then a real fighter over
// an enemy summon (the wiki's "Summon Intelligence" rules).
func (f *Fight) nearestOpponent(self *FightFighter) *FightFighter {
	var best *FightFighter
	var bestDist int32
	for _, fr := range f.allFighters() {
		if fr.HP <= 0 || !f.areOpponents(self, fr) || fr.hasState(stateInvisible) {
			continue // an invisible enemy is unseen by the AI
		}
		d := manhattanDist(self.Pos, fr.Pos)
		if best == nil || d < bestDist || (d == bestDist && aiTargetPreferred(fr, best)) {
			best, bestDist = fr, d
		}
	}
	return best
}

// aiTargetPreferred reports whether cand is a better target than best at equal
// distance: higher initiative first, then a non-summon over a summon.
func aiTargetPreferred(cand, best *FightFighter) bool {
	if cand.Init != best.Init {
		return cand.Init > best.Init
	}
	return !cand.isSummon() && best.isSummon()
}

// minEnemyDistance returns the Manhattan distance from `at` to the nearest living
// enemy of `self`, or -1 if there are none.
func (f *Fight) minEnemyDistance(self *FightFighter, at Pos) int32 {
	best := int32(-1)
	for _, fr := range f.allFighters() {
		if fr.HP <= 0 || !f.areOpponents(self, fr) || fr.hasState(stateInvisible) {
			continue
		}
		d := manhattanDist(at, fr.Pos)
		if best < 0 || d < best {
			best = d
		}
	}
	return best
}

// areOpponents reports whether a and b are on opposing teams.
func (f *Fight) areOpponents(a, b *FightFighter) bool {
	return a != nil && b != nil && a.TeamID != b.TeamID
}
