package combat

import "sort"

// summon_ai.go drives the AI that plays a SUMMON's turn. A summon is not
// owned by any coach, so no client will ever send its
// FighterEndTurnRequestMessage / movement / spell-cast -- without this the
// summon's turn would just idle until the 30s turn clock force-ended it.
//
// There is NO behavior data in the game files: a SummoningTemplate is only
// {id, hp, ap, mp, gfx, spellId} and the original server-side turn AI
// (org.ankarton) is absent from the decompiled reference (confirmed via a
// full search of client/source, server/data/stc, and tools). So a summon's
// behavior is DERIVED here entirely from its single spell + stats, which is
// exactly how the real game's summons feel:
//
//   - Blocker    (no spell): walk adjacent to the nearest enemy and body-
//                block it. e.g. Blocker, Tree.
//   - Aggressive (spell damages enemies): close into spell range and cast
//                until out of AP/range. e.g. Gobball, Royal Gobball,
//                Crackler, Victim (kamikaze -- its spell also kills itself).
//   - Kite/fear  (spell only debuffs enemies, or the summon has lots of MP):
//                cast from range, then retreat away from enemies with the
//                leftover MP. e.g. Prespic, Madoll, Tofu.
//   - Self-buff  (spell targets self, e.g. a rebound/armor buff): cast on
//                itself, then block the nearest enemy. e.g. Dial.
//
// The classification reads the summon's spell effects' target side
// (EffectDef.Targets: 1 = enemies, 2 = self/allies) and effect kinds
// (damage vs debuff vs buff), so it stays correct if the underlying spell
// data changes -- no hardcoded per-monster table.

// summonBehavior is the archetype the AI plays for a given summon,
// classified from its spell (see classifySummon).
type summonBehavior int

const (
	behaviorBlocker    summonBehavior = iota // no spell: close in and body-block
	behaviorSelfBuff                         // spell buffs self: cast on self, then block
	behaviorKite                             // spell debuffs enemies / high MP: cast then retreat
	behaviorAggressive                       // spell damages enemies: close in and cast
)

// kiteMPThreshold: a summon with at least this much MP is treated as a
// hit-and-run kiter even if its spell deals damage (it has the mobility to
// strike then flee -- e.g. the Tofu with MP 6). Below it, a damage summon
// simply presses the attack.
const kiteMPThreshold int32 = 4

// isAISummon reports whether a fighter should be played by the built-in
// summon AI: it is a summon (has a Father) that no human turn-input will
// ever arrive for.
func isAISummon(f *Fighter) bool {
	return f != nil && f.Father != nil
}

// runSummonTurn plays a full AI turn for a summon and then ends its turn.
// Called from startNextTurn when the fighter whose turn just began is an AI
// summon. It is safe to call askForFighterEndTurn at the end because
// runSummonTurn always runs inside the Fight actor goroutine (same as every
// other turn-progression call).
func (f *Fight) runSummonTurn(summon *Fighter) {
	switch f.classifySummon(summon) {
	case behaviorSelfBuff:
		f.playSelfBuffSummon(summon)
	case behaviorAggressive:
		f.playAggressiveSummon(summon)
	case behaviorKite:
		f.playKiteSummon(summon)
	default: // behaviorBlocker
		f.moveSummonTowardNearestOpponent(summon)
	}

	// If the summon died mid-turn (walked onto a trap, or a kamikaze spell
	// killed itself), killFighter's central recovery already advanced the
	// turn. Calling askForFighterEndTurn now would just hit its
	// non-current-fighter guard and log a spurious warning, so only end the
	// turn while the summon is still the live current fighter.
	if summon.IsDead || f.Timeline.CurrentFighter() != summon {
		return
	}
	// End the summon's turn so play advances without waiting on the
	// (nonexistent) player input or the 30s clock.
	f.askForFighterEndTurn(summon.ID)
}

// classifySummon derives the summon's behavior archetype from its spell.
// No spell -> blocker. Otherwise inspect the spell's effects: a self-only
// buff -> self-buff; an enemy-targeting spell -> aggressive if it deals
// damage (and isn't a high-MP kiter), else kite (debuff-only or mobile).
func (f *Fight) classifySummon(summon *Fighter) summonBehavior {
	if summon.SummonSpellID == 0 || f.data == nil {
		return behaviorBlocker
	}
	spell, ok := f.data.Spells.Get(summon.SummonSpellID)
	if !ok {
		return behaviorBlocker
	}

	// The primary signal is the effect KIND (a spell's Targets bitmask is
	// often the ambiguous bare CONDITION_IN_AOE=1 for area offensive
	// spells, so kind is more reliable): damage/debuff = hostile ->
	// aggressive/kite; a buff whose target side is the caster = self-buff.
	var damagesEnemy, debuffsEnemy, buffsSelf bool
	for _, e := range spell.Effects {
		def, known := LookupRunningEffect(e.ActionID)
		if !known {
			continue
		}
		switch {
		case isDamageKind(def.Kind):
			// A self-Death (kamikaze) still counts as an offensive spell:
			// the summon rushes in, its damage-side hits the enemy and its
			// death-side kills itself. So any damage kind = aggressive.
			damagesEnemy = true
		case isDebuffKind(def.Kind):
			debuffsEnemy = true
		case isBuffKind(def.Kind) && effectTargetSide(e) != targetEnemy:
			buffsSelf = true
		}
	}

	switch {
	case damagesEnemy:
		// Nimble damage summons (lots of MP, e.g. the Tofu) hit and run;
		// slower ones just press the attack.
		if summon.Characteristic(MP) >= kiteMPThreshold {
			return behaviorKite
		}
		return behaviorAggressive
	case debuffsEnemy:
		// Pure harassers (Prespic mockery, Madoll irritation): cast then
		// keep their distance.
		return behaviorKite
	case buffsSelf:
		return behaviorSelfBuff
	default:
		return behaviorBlocker
	}
}

// playAggressiveSummon closes into the summon's spell range of the nearest
// enemy and casts until it runs out of AP or the target leaves range.
func (f *Fight) playAggressiveSummon(summon *Fighter) {
	target := f.nearestOpponent(summon)
	if target == nil {
		return
	}
	f.moveSummonIntoSpellRange(summon, target)
	f.castSummonSpellRepeatedly(summon, func() *Fighter { return f.nearestOpponent(summon) })
}

// playKiteSummon casts the summon's spell at the nearest enemy from range,
// then retreats away from enemies with any remaining MP (hit-and-run).
func (f *Fight) playKiteSummon(summon *Fighter) {
	target := f.nearestOpponent(summon)
	if target == nil {
		return
	}
	f.moveSummonIntoSpellRange(summon, target)
	f.castSummonSpellRepeatedly(summon, func() *Fighter { return f.nearestOpponent(summon) })
	// Retreat only while still alive and holding the turn (a kamikaze-ish
	// self-hit or trap could have killed us during the cast phase).
	if !summon.IsDead && f.Timeline.CurrentFighter() == summon {
		f.retreatSummonFromOpponents(summon)
	}
}

// playSelfBuffSummon casts the summon's self-targeting buff on itself, then
// closes in to block the nearest enemy like a wall.
func (f *Fight) playSelfBuffSummon(summon *Fighter) {
	if summon.SummonSpellID != 0 {
		f.castSpell(summon, summon.SummonSpellID, summon.Position)
	}
	if !summon.IsDead && f.Timeline.CurrentFighter() == summon {
		f.moveSummonTowardNearestOpponent(summon)
	}
}

// castSummonSpellRepeatedly casts the summon's spell at the (re-evaluated)
// target as many times as its AP allows, stopping as soon as a cast fails
// validation (out of AP/range/LOS, or no target). pickTarget is called
// before each attempt so the AI re-targets if the current one died.
func (f *Fight) castSummonSpellRepeatedly(summon *Fighter, pickTarget func() *Fighter) {
	if summon.SummonSpellID == 0 || f.data == nil {
		return
	}
	spell, ok := f.data.Spells.Get(summon.SummonSpellID)
	if !ok {
		return
	}
	// Bound the loop by the max number of casts the summon's AP could ever
	// afford (each cast costs at least 1 AP; a 0-AP spell would loop
	// forever otherwise). castSpell also self-limits via validateCast +
	// per-turn cast-frequency history, so this is just a hard safety cap.
	maxCasts := int(summon.Characteristic(AP)) + 1
	for i := 0; i < maxCasts; i++ {
		if summon.IsDead || f.Timeline.CurrentFighter() != summon {
			return
		}
		if summon.Characteristic(AP) < int32(spell.ActionPointsCost) {
			return
		}
		target := pickTarget()
		if target == nil {
			return
		}
		if !f.castSpell(summon, summon.SummonSpellID, target.Position) {
			return // out of range / LOS / cast-frequency: stop trying
		}
	}
}

// moveSummonIntoSpellRange walks the summon (MP-limited) to the nearest cell
// from which it can hit `target` with its spell -- i.e. within the spell's
// max range (and, when the spell tests line-of-sight, with LOS). For a
// melee spell (range 1) this is the same "get adjacent" behavior as a
// blocker. No-op if the summon already has a valid firing position or can't
// improve it.
func (f *Fight) moveSummonIntoSpellRange(summon, target *Fighter) {
	if f.data == nil {
		return
	}
	spell, ok := f.data.Spells.Get(summon.SummonSpellID)
	if !ok {
		return
	}
	rMin, maxRange := int32(spell.RangeMin), int32(spell.RangeMax)
	// Already able to fire from here?
	if f.canHitFrom(summon.Position, target.Position, rMin, maxRange, spell.CastTestLineOfSight) {
		return
	}
	mp := int(summon.Characteristic(MP))
	if mp <= 0 {
		return
	}

	// Evaluate EVERY cell reachable this turn in a single BFS pass and pick
	// the best one, instead of re-scanning candidate rings each turn (which
	// caused the summon to oscillate forward-and-back when equally-good
	// cells were chosen inconsistently). Priority:
	//   1. a cell we can actually FIRE from (closest such cell wins);
	//   2. otherwise the cell that minimizes remaining distance to a firing
	//      position, i.e. gets us as close to the target as possible.
	// Ties break toward the SHORTER path, and we only move if it strictly
	// improves on standing still (anti-oscillation).
	reachable := ReachableCells(summon, summon.Position, mp, f)

	var bestPath []Point3
	bestCanFire := false
	// "score" = distance from a cell to the nearest in-range firing spot
	// (0 if the cell itself can fire). Lower is better.
	bestScore := f.firingApproachScore(summon.Position, target, rMin, maxRange, spell.CastTestLineOfSight)
	bestLen := 0
	for _, path := range orderedReachablePaths(summon.Position, reachable) {
		if len(path) == 0 {
			continue
		}
		cell := path[len(path)-1]
		if f.cellIsLethalToSummon(summon, cell) {
			continue // never end a move on a lethal special cell
		}
		canFire := f.canHitFrom(cell, target.Position, rMin, maxRange, spell.CastTestLineOfSight)
		score := f.firingApproachScore(cell, target, rMin, maxRange, spell.CastTestLineOfSight)

		better := false
		switch {
		case canFire && !bestCanFire:
			better = true
		case canFire == bestCanFire && score < bestScore:
			better = true
		case canFire == bestCanFire && score == bestScore && bestPath != nil && len(path) < bestLen:
			better = true
		}
		if better {
			bestPath, bestCanFire, bestScore, bestLen = path, canFire, score, len(path)
		}
	}
	if len(bestPath) == 0 {
		return // nothing strictly better than staying put
	}
	f.walkSummon(summon, bestPath)
}

// firingApproachScore returns how far `cell` is from being able to cast a
// range [rMin,rMax] spell at `target`: 0 if `cell` can already fire, else
// the number of cells its Manhattan distance is outside the range window
// (a proxy for "how much closer we still need to get"). LOS is treated as a
// small penalty when required and missing, so an in-range-but-no-LOS cell is
// still preferred over a farther one. Lower is better.
func (f *Fight) firingApproachScore(cell Point3, target *Fighter, rMin, rMax int32, testLOS bool) int {
	d := manhattanDistance(cell, target.Position)
	var rangeGap int32
	switch {
	case d > rMax:
		rangeGap = d - rMax
	case d < rMin:
		rangeGap = rMin - d
	default:
		rangeGap = 0
	}
	score := int(rangeGap)
	if testLOS && rangeGap == 0 && !f.hasLineOfSight(cell, target.Position) {
		score++ // in range but blocked: slightly worse than a clear shot
	}
	return score
}

// canHitFrom reports whether a caster standing at `from` could cast a spell
// with the given range at `to` (Manhattan range window + optional LOS).
func (f *Fight) canHitFrom(from, to Point3, rMin, rMax int32, testLOS bool) bool {
	d := manhattanDistance(from, to)
	if d < rMin || d > rMax {
		return false
	}
	if testLOS && !f.hasLineOfSight(from, to) {
		return false
	}
	return true
}

// retreatSummonFromOpponents walks the summon (MP-limited) to the reachable
// cell that maximizes its minimum distance to any living enemy -- a simple
// "flee" that the kite behavior uses after firing. No-op if it has no MP,
// no enemies, or can't improve its safest distance.
func (f *Fight) retreatSummonFromOpponents(summon *Fighter) {
	mp := int(summon.Characteristic(MP))
	if mp <= 0 {
		return
	}
	currentSafety := f.minEnemyDistance(summon, summon.Position)
	if currentSafety < 0 {
		return // no enemies
	}
	// BFS every reachable cell; keep the one that maximizes the minimum
	// distance to any living enemy (safest), tie-broken by shorter path.
	// Only move if it STRICTLY improves safety over standing still.
	reachable := ReachableCells(summon, summon.Position, mp, f)
	var bestPath []Point3
	bestSafety := currentSafety
	bestLen := 0
	for _, path := range orderedReachablePaths(summon.Position, reachable) {
		if len(path) == 0 {
			continue
		}
		cell := path[len(path)-1]
		if f.cellIsLethalToSummon(summon, cell) {
			continue // never flee onto a lethal special cell
		}
		safety := f.minEnemyDistance(summon, cell)
		if safety > bestSafety || (safety == bestSafety && bestPath != nil && len(path) < bestLen) {
			// Only accept an equal-safety cell if it's a shorter walk than a
			// previously chosen improvement -- never accept equal safety to
			// the CURRENT cell (that would be pointless movement).
			if safety > currentSafety || (bestPath != nil && safety == bestSafety) {
				bestPath, bestSafety, bestLen = path, safety, len(path)
			}
		}
	}
	if len(bestPath) == 0 {
		return
	}
	f.walkSummon(summon, bestPath)
}

// minEnemyDistance returns the Manhattan distance from `at` to the nearest
// living enemy of `self`, or -1 if there are no living enemies.
func (f *Fight) minEnemyDistance(self *Fighter, at Point3) int32 {
	best := int32(-1)
	for _, fr := range f.Timeline.Order() {
		if fr.IsDead || !f.AreOpponents(self, fr) || summonCannotSee(fr) {
			continue
		}
		d := manhattanDistance(at, fr.Position)
		if best < 0 || d < best {
			best = d
		}
	}
	return best
}

// cellIsLethalToSummon reports whether ENDING a move on `cell` would kill
// the summon via a lethal special cell (wiki: "summons also will not step on
// lethal traps"). A KILLER cell always kills; a TRAP cell kills only if its
// fixed damage would drop the summon to 0 HP. The summon's CURRENT cell is
// never treated as lethal-to-avoid (it's already standing there, and the
// special-cell effect only fires at turn start anyway, so refusing to leave
// would just strand it). Non-lethal cells (or a fight with no special cells)
// return false, so this never restricts movement on ordinary maps.
func (f *Fight) cellIsLethalToSummon(summon *Fighter, cell Point3) bool {
	if cell == summon.Position {
		return false
	}
	switch f.specialCellAt(cell) {
	case SpecialCellKiller:
		return true
	case SpecialCellTrap:
		return summon.Characteristic(HP) <= trapDamage
	default:
		return false
	}
}

// summonCannotSee reports whether the summon AI must ignore `enemy` when
// choosing a target or fleeing: an INVISIBLE fighter is unseen (wiki:
// "Summons do not see invisible characters"). This mirrors the client's own
// invisibility model, where an invisible fighter doesn't even block
// line-of-sight (AbstractFighter.isBlockingLOS). It is an AI-only rule (the
// original org.ankarton turn-AI is absent from the decompiled reference), so
// it lives here in the derived summon AI rather than in shared targeting.
func summonCannotSee(enemy *Fighter) bool {
	return enemy.Properties.Has(PropertyInvisible)
}

// moveSummonTowardNearestOpponent walks the summon along the best MP-limited
// path toward the cell adjacent to the nearest living opponent. Used by the
// blocker/self-buff behaviors (and as the aggressive fallback when no firing
// position is reachable). No-op if the summon has no MP, there is no
// opponent, or it is already adjacent to one.
func (f *Fight) moveSummonTowardNearestOpponent(summon *Fighter) {
	mp := int(summon.Characteristic(MP))
	if mp <= 0 {
		return
	}
	target := f.nearestOpponent(summon)
	if target == nil {
		return
	}
	// Already adjacent (Manhattan distance 1) -> nothing to do.
	if manhattanDistance(summon.Position, target.Position) <= 1 {
		return
	}

	// BFS over every reachable cell; pick the one closest (Manhattan) to the
	// target, tie-broken by shorter path. Only move if it STRICTLY beats
	// standing still, which stops the summon oscillating when it can't
	// actually reach adjacency this turn.
	reachable := ReachableCells(summon, summon.Position, mp, f)
	var bestPath []Point3
	bestDist := manhattanDistance(summon.Position, target.Position)
	bestLen := 0
	for _, path := range orderedReachablePaths(summon.Position, reachable) {
		if len(path) == 0 {
			continue
		}
		cell := path[len(path)-1]
		if f.cellIsLethalToSummon(summon, cell) {
			continue // never end a move on a lethal special cell
		}
		d := manhattanDistance(cell, target.Position)
		if d < bestDist || (d == bestDist && bestPath != nil && len(path) < bestLen) {
			bestPath, bestDist, bestLen = path, d, len(path)
		}
	}
	if len(bestPath) == 0 {
		return
	}
	f.walkSummon(summon, bestPath)
}

// walkSummon advances the summon along `path`, running per-step ground-effect
// triggers, debiting MP, and broadcasting the move + MP use exactly like a
// player move (turns.go handleFighterMove). No-op for an empty path. Safe to
// call from any behavior; it handles a self-kill mid-path (trap) by stopping
// and skipping the trailing FIGHTER_MOVE for a dead actor.
func (f *Fight) walkSummon(summon *Fighter, path []Point3) {
	if len(path) == 0 {
		return
	}
	// Stop-on-contact: like a player fighter (handleFighterMove), a summon
	// halts the instant it steps adjacent to a living enemy rather than
	// gliding through its zone-of-control. The aggressive behavior WANTS to
	// end adjacent anyway, but this also stops a blocker/kiter from walking
	// past an enemy it should have blocked.
	path = f.truncatePathOnEnemyContact(summon, path)
	if len(path) == 0 {
		return
	}
	cost := PathMPCost(path)
	summon.AddCharacteristic(MP, -int32(cost))

	// Capture the pre-move origin FIRST (for the wire path's start cell)
	// before advancing Position.
	origin := summon.Position
	from := origin
	for _, step := range path {
		f.checkInAndOut(from, step, summon)
		// A trap entered mid-path can kill the summon. Stop walking and skip
		// the trailing FIGHTER_MOVE for a dead actor (killFighter already
		// broadcast FIGHTER_DIES and advanced the turn centrally).
		if summon.IsDead {
			break
		}
		from = step
	}
	if summon.IsDead {
		return
	}
	summon.Position = path[len(path)-1]

	// Broadcast the path WITH the origin cell prepended (the client's move
	// animator treats the first wire cell as the current position -- see
	// handleFighterMove's identical wirePath handling).
	wirePath := make([]Point3, 0, len(path)+1)
	wirePath = append(wirePath, origin)
	wirePath = append(wirePath, path...)
	f.broadcastAll(buildFighterMove(f.nextActionID(), summon.ID, wirePath))
	f.broadcastCharacUse(runningEffectMPUse, summon, int32(cost))
	f.flushActionSequence()
}

// nearestOpponent returns the living opponent of `self` a summon should walk
// toward, using the wiki's "Summon Intelligence" tie-break rules exactly:
//  1. smallest Manhattan distance ("closest hostile");
//  2. on a distance tie, the HIGHEST initiative in the turn order (the wiki:
//     "the one which has highest initiative according to the turn order");
//  3. on a further tie, prefer a NON-summon (the summoner) over an enemy
//     summon (the wiki: "it will choose the summoner before the summon").
//
// Invisible enemies are skipped (summons can't see them). Returns nil if no
// visible opponent remains (the fight is about to end anyway).
func (f *Fight) nearestOpponent(self *Fighter) *Fighter {
	var best *Fighter
	var bestDist int32
	for _, fr := range f.Timeline.Order() {
		if fr.IsDead || !f.AreOpponents(self, fr) || summonCannotSee(fr) {
			continue
		}
		d := manhattanDistance(self.Position, fr.Position)
		if best == nil || d < bestDist || (d == bestDist && f.summonTargetPreferred(fr, best)) {
			best, bestDist = fr, d
		}
	}
	return best
}

// summonTargetPreferred reports whether candidate cand is a better summon
// target than the current best at the SAME distance, per the wiki tie-break:
// higher initiative first, then a real fighter (non-summon) over a summon.
func (f *Fight) summonTargetPreferred(cand, best *Fighter) bool {
	ci, bi := cand.Characteristic(Init), best.Characteristic(Init)
	if ci != bi {
		return ci > bi // higher initiative wins
	}
	// Equal initiative: prefer the summoner (a non-summon) over an enemy summon.
	candIsSummon := cand.Father != nil
	bestIsSummon := best.Father != nil
	return !candIsSummon && bestIsSummon
}

// orderedReachablePaths returns the reachable-cell paths from ReachableCells in
// a DETERMINISTIC order that honours the wiki's "Summon Intelligence" path
// priority: among candidate destinations the summon AI treats as equally good,
// the one whose route is preferred by the Southeast > Southwest > Northwest >
// Northeast step ordering must win. ReachableCells returns a map (random Go
// iteration order), so the AI's "first strictly-better wins" tie-breaks were
// non-deterministic; iterating in this stable order fixes that.
//
// Ordering key per path: (1) shorter path first (fewer steps), then (2)
// lexicographically by each step's direction-priority rank (SE=0, SW=1, NW=2,
// NE=3), then (3) by final cell coords as a total-order backstop. So the first
// path encountered for any given length is the SE-first route the wiki
// describes.
func orderedReachablePaths(start Point3, reachable map[[2]int32][]Point3) [][]Point3 {
	paths := make([][]Point3, 0, len(reachable))
	for _, p := range reachable {
		if len(p) > 0 {
			paths = append(paths, p)
		}
	}
	sort.Slice(paths, func(i, j int) bool {
		a, b := paths[i], paths[j]
		if len(a) != len(b) {
			return len(a) < len(b) // shorter route first
		}
		// Compare step-by-step by direction-priority rank (SE>SW>NW>NE). The
		// k-th step's direction is prevCell -> a[k], where prevCell is the
		// summon's start for k==0 and a[k-1] otherwise (all paths share the
		// same start).
		prevA, prevB := start, start
		for k := 0; k < len(a); k++ {
			ra, rb := dirRank(prevA, a[k]), dirRank(prevB, b[k])
			if ra != rb {
				return ra < rb
			}
			prevA, prevB = a[k], b[k]
		}
		// Total-order backstop on the final cell (identical routes only).
		la, lb := a[len(a)-1], b[len(b)-1]
		if la.X != lb.X {
			return la.X < lb.X
		}
		return la.Y < lb.Y
	})
	return paths
}

// dirRank maps the single-axis grid step from a to b to its SE>SW>NW>NE
// priority rank (SE=0, SW=1, NW=2, NE=3); 4 for a non-unit/degenerate step.
func dirRank(a, b Point3) int {
	dx, dy := b.X-a.X, b.Y-a.Y
	switch {
	case dx == 1 && dy == 0: // SouthEast
		return 0
	case dx == 0 && dy == 1: // SouthWest
		return 1
	case dx == -1 && dy == 0: // NorthWest
		return 2
	case dx == 0 && dy == -1: // NorthEast
		return 3
	default:
		return 4
	}
}
