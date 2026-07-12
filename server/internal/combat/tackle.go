package combat

// This file implements the Tackle/Evasion zone-of-control mechanic
// described in the game manual (Game_Guide_DofusArena_v2.pdf §5.0.4):
// when two opposing fighters are on adjacent cells, they're in a "tackle
// area." A fighter who tries to move while in a tackle area must "evade"
// each adjacent enemy independently (67% base success chance each,
// shifted by the per-side Evasion difference via evasionChanceAgainst --
// see Fighter.EvasionBonus for why this is a non-wire modifier rather than
// a reference characteristic); failing any of them
// means the fighter is "tackled" -- the move is rejected and the
// fighter's turn ends immediately, exactly as if they'd chosen to end
// their own turn (§5.0.4: "he is considered as tackled and misses his
// turn"). Being adjacent to 4 or more living enemies makes movement
// impossible outright (no roll, always tackled) -- also per the manual.
//
// This mechanic has no equivalent in the decompiled reference source (the
// client's own TackleAction is purely a cosmetic "play an animation"
// no-op -- see docs/08-java-parity-roadmap.md's cross-check), so the
// exact probability model (independent-per-enemy vs. compound) is taken
// directly from the manual's wording ("he has to manage to evade each of
// them") -- implemented as an independent per-enemy roll, ALL of which
// must succeed.
const (
	tackleBaseEvasionPercent = 67
	tackleMaxSimultaneous    = 3 // manual: "three at most" -- a 4th adjacent enemy makes movement impossible
)

// adjacentOpponents returns every living opponent fighter adjacent
// (Manhattan distance 1, matching the same adjacency definition used for
// close combat range validation) to fighter's current position.
func (f *Fight) adjacentOpponents(fighter *Fighter) []*Fighter {
	var out []*Fighter
	for _, other := range f.Timeline.Order() {
		if other == fighter || other.IsDead {
			continue
		}
		if !f.AreOpponents(fighter, other) {
			continue
		}
		if manhattanDistance(fighter.Position, other.Position) == 1 {
			out = append(out, other)
		}
	}
	return out
}

// cellAdjacentToOpponent reports whether `cell` is Manhattan-distance 1 from
// any living opponent of `mover` (the mover itself is skipped). Used by the
// stop-on-contact movement rule.
func (f *Fight) cellAdjacentToOpponent(mover *Fighter, cell Point3) bool {
	for _, other := range f.Timeline.Order() {
		if other == mover || other.IsDead {
			continue
		}
		if !f.AreOpponents(mover, other) {
			continue
		}
		if manhattanDistance(cell, other.Position) == 1 {
			return true
		}
	}
	return false
}

// truncatePathOnEnemyContact cuts a resolved movement path short at the first
// step that lands adjacent to a living opponent, so a fighter stops the
// moment it makes contact with an enemy's zone-of-control rather than walking
// on past it. The contact step itself is KEPT (the fighter ends its move on
// that cell). If no step touches an enemy the path is returned unchanged; an
// empty path in stays empty. Note this only inspects the steps AFTER the
// start cell -- leaving a cell that is already adjacent to an enemy is the
// separate start-of-move tackle roll (attemptEvadeTackle).
func (f *Fight) truncatePathOnEnemyContact(mover *Fighter, path []Point3) []Point3 {
	for i, step := range path {
		if f.cellAdjacentToOpponent(mover, step) {
			return path[:i+1]
		}
	}
	return path
}

// evasionChanceAgainst returns fighter's percentage chance (clamped to
// [0,100]) to evade a single adjacent opponent. It is the flat base
// (tackleBaseEvasionPercent) shifted by the per-side Evasion difference:
// the evader's own EvasionBonus raises it, the tackler's EvasionBonus (its
// "grip") lowers it -- honoring the manual's "modified by the Evasion
// characteristic per-side" wording. With both bonuses at their default
// (0), this is exactly the flat 67% base, so no existing behavior changes
// unless an effect/cell actually sets an EvasionBonus.
func evasionChanceAgainst(evader, tackler *Fighter) int {
	chance := tackleBaseEvasionPercent + int(evader.EvasionBonus) - int(tackler.EvasionBonus)
	if chance < 0 {
		chance = 0
	}
	if chance > 100 {
		chance = 100
	}
	return chance
}

// attemptEvadeTackle checks whether fighter, currently standing adjacent
// to one or more living opponents, may leave (mirrors the manual's tackle
// rule). Returns true if the fighter successfully evades every adjacent
// opponent (or has none) and may proceed to move; false if tackled (the
// caller must reject the move and end the fighter's turn). Each adjacent
// opponent is rolled independently at a chance derived from the per-side
// Evasion difference (see evasionChanceAgainst); ALL must be evaded.
func (f *Fight) attemptEvadeTackle(fighter *Fighter) bool {
	opponents := f.adjacentOpponents(fighter)
	if len(opponents) == 0 {
		return true
	}
	if len(opponents) > tackleMaxSimultaneous {
		// Surrounded by 4+ enemies: movement is impossible, no roll.
		return false
	}
	for _, opp := range opponents {
		if f.rng.Intn(100) >= evasionChanceAgainst(fighter, opp) {
			return false
		}
	}
	return true
}

// handleTackled ends fighter's turn immediately as a consequence of a
// failed evasion attempt, mirroring "he is considered as tackled and
// misses his turn." Broadcasts FIGHTER_TACKLED(4506) for each opponent
// holding the fighter in place before ending the turn.
func (f *Fight) handleTackled(fighter *Fighter) {
	actionID := f.nextActionID()
	for _, opp := range f.adjacentOpponents(fighter) {
		f.broadcastAll(buildFighterTackled(actionID, fighter.ID, opp.ID))
	}
	f.flushActionSequence()
	if f.Timeline.CurrentFighter() == fighter {
		f.askForFighterEndTurn(fighter.ID)
	}
}
