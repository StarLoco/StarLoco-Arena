package game

// tackle.go implements the Tackle / zone-of-control mechanic (DofusArena manual
// §5.0.4), ported from the v2.04b combat/tackle.go. A fighter standing
// orthogonally adjacent (Manhattan distance 1) to a living opponent is in that
// opponent's "tackle area": trying to MOVE requires evading EACH adjacent enemy
// independently (a flat base chance each; ALL must succeed), and failing any means
// the move is rejected and the fighter's turn ends immediately. Being hemmed in by
// 4+ living enemies makes movement impossible (no roll). Separately, a fighter
// walking PAST an enemy stops the instant it enters a cell adjacent to one
// (stop-on-contact) rather than gliding through the enemy's zone of control.
//
// # The evasion chance
//
// The escape chance is driven by two REAL per-fighter characteristics, not by a
// flat constant: the mover's DODGE (client charac Lr.bre) against each holder's
// BLOCK (Lr.brd). Both come from the breed table (`xq` args 13/14) and are shifted
// by card/spell actions 120-123 and by a summon's own template.
//
// The client documents the semantics but not the curve — its TackleAction is a
// cosmetic animation, so the arithmetic is server-side in 2.70 exactly as it was
// in 2.04b. What the client's help text DOES pin down is the anchor:
//
//	"Ce sont tes chances d'esquive qui donneront ton pourcentage de chance de ne
//	 pas être bloqué. Avoir 100% en esquive te garantis de toujours esquiver les
//	 personnages qui ont 0% de chance de blocage, mais tes chances diminuent contre
//	 un adversaire avec plus en blocage. Tous les personnages ont, de base, 100%
//	 en esquive."
//
// i.e. dodge 100 vs block 0 must ALWAYS succeed, and the chance falls as the
// holder's block rises. `dodge - block` is the simplest curve satisfying that, and
// it is what we use. **This subtraction is a server choice, not a client-verified
// formula** — the anchor is verified, the shape between the endpoints is not.
//
// With the shipped breed values (dodge 100 for everyone; block 60 Feca, 40 Iop and
// Pandawa, 20 Sram and Sacrier, 0 for the rest) that yields: a Feca holds you 60%
// of the time, an Iop 40%, and a Cra never — which is what "100% dodge guarantees
// you always dodge characters with 0% block" requires, and what the old flat 67%
// got wrong in both directions.
//
// There is deliberately NO "4+ enemies means you cannot move" cap any more: it
// came from the 2.04b manual and directly contradicts the guarantee above (four
// adjacent Cras would pin a fighter who is promised free passage). Being surrounded
// is punishing on its own — four Fecas give 0.4^4 ≈ 2.6% escape.

// isTackler reports whether other is a living enemy of ff that can hold ground
// (a carried fighter is held on its carrier's cell and cannot tackle).
func (f *Fight) isTackler(ff, other *FightFighter) bool {
	return other != nil && other != ff && other.HP > 0 &&
		other.CarriedByFighter == nil && other.TeamID != ff.TeamID
}

// posManhattan returns the orthogonal grid distance between a and b (ignoring z).
func posManhattan(a, b Pos) int32 {
	dx := a.X - b.X
	if dx < 0 {
		dx = -dx
	}
	dy := a.Y - b.Y
	if dy < 0 {
		dy = -dy
	}
	return dx + dy
}

// adjacentOpponents returns every living enemy orthogonally adjacent to ff.
func (f *Fight) adjacentOpponents(ff *FightFighter) []*FightFighter {
	var out []*FightFighter
	for _, o := range f.allFighters() {
		if f.isTackler(ff, o) && posManhattan(ff.Pos, o.Pos) == 1 {
			out = append(out, o)
		}
	}
	return out
}

// cellAdjacentToOpponent reports whether cell is orthogonally adjacent to a living
// enemy of mover.
func (f *Fight) cellAdjacentToOpponent(mover *FightFighter, cell Pos) bool {
	for _, o := range f.allFighters() {
		if f.isTackler(mover, o) && posManhattan(cell, o.Pos) == 1 {
			return true
		}
	}
	return false
}

// truncatePathOnEnemyContact cuts path short at the first step that lands adjacent
// to a living enemy (that step is KEPT — the fighter ends its move there). A path
// with no such step is returned unchanged. This is distinct from the start-of-move
// evasion roll (attemptEvadeTackle), which handles LEAVING a cell already adjacent
// to an enemy.
func (f *Fight) truncatePathOnEnemyContact(mover *FightFighter, path []Pos) []Pos {
	for i, step := range path {
		if f.cellAdjacentToOpponent(mover, step) {
			return path[:i+1]
		}
	}
	return path
}

// tackleEvasionChance is ff's percentage chance to slip past ONE holder: the
// mover's dodge minus the holder's block, bounded to 0..100. Both inputs are
// stored unclamped (so timed buffs revert exactly) and bounded here at read time.
func tackleEvasionChance(mover, holder *FightFighter) int32 {
	if mover == nil {
		return 0
	}
	chance := mover.Dodge
	if holder != nil {
		chance -= holder.Block
	}
	if chance < 0 {
		return 0
	}
	if chance > 100 {
		return 100
	}
	return chance
}

// attemptEvadeTackle reports whether ff, standing adjacent to one or more living
// enemies, may leave: each adjacent enemy is rolled independently and ALL must be
// evaded. No adjacent enemy = free to move.
func (f *Fight) attemptEvadeTackle(ff *FightFighter) bool {
	for _, opp := range f.adjacentOpponents(ff) {
		chance := tackleEvasionChance(ff, opp)
		if chance >= 100 {
			continue // guaranteed escape: do not burn a roll
		}
		if int32(f.rngSource().Intn(100))+1 > chance {
			return false
		}
	}
	return true
}

// handleTackled ends ff's turn as a consequence of a failed evasion, broadcasting
// FIGHTER_TACKLED (4506) for each opponent holding it in place first.
func (f *Fight) handleTackled(ff *FightFighter) {
	for _, opp := range f.adjacentOpponents(ff) {
		if tk, err := buildFighterTackled(f.nextActionUID(), ff.WireID, opp.WireID); err == nil {
			f.broadcast(tk)
		}
	}
	if seq, err := buildActionSequenceExecute(); err == nil {
		f.broadcast(seq)
	}
	if f.isCurrentTurn(ff.WireID) {
		f.endTurn(ff.WireID)
	}
}
