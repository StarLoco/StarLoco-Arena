package combat

// Timeline manages turn order within the ACTION phase: a fixed initiative
// order established once at fight start, walked round-by-round ("table
// turns"). See docs/05-combat-engine.md §5.2 and, importantly,
// docs/opcodes/08-fight-combat-engine.md §1.4 for the reference engine's
// actual validate-gated turn-advance mechanic: the timeline does NOT
// auto-advance from turn-start to turn-end on its own -- something
// external (a player's FighterEndTurnRequestMessage, or the 30s turn
// clock) must call EndCurrentTurn() before the next fighter's turn can
// begin via StartNextTurn(). This mirrors askForFighterEndTurn/
// askForFighterStartTurn's "stall until validated, then drain" behavior.
// Fight (fight.go) owns the actual clock/readiness bookkeeping and calls
// into this type at the right moments; Timeline itself is a pure turn-order
// + validate-gate state machine with no goroutines/timers of its own.
type Timeline struct {
	order            []*Fighter // fixed at fight start (+ summons inserted after father)
	tableTurn        int
	turnIndexInTable int
	currentFighter   *Fighter

	// roundRemaining counts living fighters that still owe a turn in the
	// CURRENT table-turn (round). It is (re)seeded to the living-fighter
	// count at each new round boundary and decremented each time a fighter
	// begins their turn. This is what actually decides isNewTableTurn --
	// NOT the fragile "cursor wrapped to index 0" heuristic, which
	// mis-fires when a summon is spliced into the order mid-round (its
	// insertion shifts the fixed cursor's meaning). A mid-round summon adds
	// a slot to the current round's remaining schedule (see InsertAfter),
	// mirroring the reference engine's addFighterTimeEventAt shift-cascade
	// (docs/opcodes/08-fight-combat-engine.md §1.4): the summon plays THIS
	// round right after its father, and its insertion does not start a new
	// round.
	roundRemaining int
}

// BuildTurnOrder sorts fighters by descending INIT characteristic,
// preserving insertion order for ties (stable insertion-sort), mirroring
// AbstractFightTimeline.addFighter's insertion-sort-by-INIT behavior. See
// docs/05-combat-engine.md §5.2.1.
func BuildTurnOrder(fighters []*Fighter) []*Fighter {
	order := make([]*Fighter, 0, len(fighters))
	for _, f := range fighters {
		inserted := false
		for i, existing := range order {
			if f.Characteristic(Init) > existing.Characteristic(Init) {
				order = append(order, nil)
				copy(order[i+1:], order[i:])
				order[i] = f
				inserted = true
				break
			}
		}
		if !inserted {
			order = append(order, f)
		}
	}
	return order
}

// NewTimeline builds a Timeline with turn order fixed from fighters.
func NewTimeline(fighters []*Fighter) *Timeline {
	return &Timeline{order: BuildTurnOrder(fighters)}
}

// InsertAfter inserts a newly-summoned fighter into the turn order,
// mirroring AbstractFightTimeline.addFighter's summon-insertion algorithm
// (docs/opcodes/08-fight-combat-engine.md §1.4): the new summon is placed
// after its father AND after any of the father's other already-inserted
// summons, so multiple summons from the same father queue up together in
// insertion order rather than always landing immediately after father
// (which would reverse their relative order for a second/third summon).
func (tl *Timeline) InsertAfter(summoner, summon *Fighter) {
	summon.Father = summoner
	idx := -1
	for i, f := range tl.order {
		if f == summoner {
			idx = i
			break
		}
	}
	if idx == -1 {
		tl.order = append(tl.order, summon)
		return
	}
	insertAt := idx + 1
	for insertAt < len(tl.order) && tl.order[insertAt].Father == summoner {
		insertAt++
	}
	tl.order = append(tl.order, nil)
	copy(tl.order[insertAt+1:], tl.order[insertAt:])
	tl.order[insertAt] = summon

	// Keep the turn cursor pointing at the same "next" fighter after the
	// slice shift (symmetric to RemoveFighter's turnIndexInTable-- fix):
	// only a summon spliced in STRICTLY BEFORE the cursor shifts the
	// not-yet-served region rightward, so the cursor must follow. A summon
	// inserted exactly AT the cursor (insertAt == cursor) must NOT bump it
	// -- the summon then becomes the very next fighter selected this round
	// (e.g. the summoner was the last original fighter, so the cursor sits
	// on the freshly-appended summon's index and it plays next).
	if insertAt < tl.turnIndexInTable {
		tl.turnIndexInTable++
	}

	// The summon joins the CURRENT round's remaining schedule -- it is
	// inserted right after its (already-or-soon-to-act) father and plays
	// this same round, so it owes one more turn before the round ends.
	// This is what prevents a spurious NEW_TABLE_TURN_BEGIN: without it,
	// summoning at the tail of a round left roundRemaining at 0 and the
	// next StartNextTurn wrongly opened a new round (the reported bug).
	if !summon.IsDead {
		tl.roundRemaining++
	}
}

// RemoveFighter removes a dead/fled fighter from the turn order entirely,
// mirroring TurnBasedTimeline.removeFighter's roster-collapse (see
// docs/opcodes/08-fight-combat-engine.md §1.4). Safe to call for a fighter
// not present (no-op). If the removed fighter was mid-turn, also clears
// currentFighter/EndCurrentTurn state so the caller (Fight) knows to move
// on.
func (tl *Timeline) RemoveFighter(f *Fighter) {
	for i, existing := range tl.order {
		if existing == f {
			tl.order = append(tl.order[:i], tl.order[i+1:]...)
			if tl.turnIndexInTable > i {
				tl.turnIndexInTable--
			}
			// If the removed fighter still owed a turn this round (its
			// slot sat at or after the cursor and it wasn't the fighter
			// currently acting), it no longer does -- shrink the round's
			// remaining schedule so the round still ends at the right
			// moment. The currently-acting fighter is already excluded
			// from roundRemaining (StartNextTurn decremented it when their
			// turn began), so only pending (i >= cursor) removals adjust
			// it.
			if tl.currentFighter != f && i >= tl.turnIndexInTable && tl.roundRemaining > 0 {
				tl.roundRemaining--
			}
			if tl.currentFighter == f {
				tl.currentFighter = nil
			}
			return
		}
	}
}

// CurrentFighter returns whoever's turn it currently is, or nil if the
// timeline hasn't started yet or is between turns (after EndCurrentTurn,
// before the next StartNextTurn).
func (tl *Timeline) CurrentFighter() *Fighter {
	return tl.currentFighter
}

// TableTurn returns the current round number (1-indexed once started).
func (tl *Timeline) TableTurn() int {
	return tl.tableTurn
}

// Order returns the fixed turn order. The returned slice must not be
// mutated by the caller.
func (tl *Timeline) Order() []*Fighter {
	return tl.order
}

// AnyAlive reports whether any fighter in the turn order is still alive.
func (tl *Timeline) AnyAlive() bool {
	for _, f := range tl.order {
		if !f.IsDead {
			return true
		}
	}
	return false
}

// StartNextTurn advances to the next living fighter in turn order,
// wrapping into a new table-turn (round) when the order is exhausted.
// Dead/fled fighters are skipped. Returns (nil, false) if no living
// fighters remain (the fight should already have ended in that case, see
// docs/05-combat-engine.md §5.9). The second return value is true exactly
// when this call wrapped into a new round, signaling the caller
// (Fight.startNextTurn) to also broadcast NEW_TABLE_TURN_BEGIN before
// FIGHTER_TURN_BEGIN.
//
// Callers must have already ended the previous turn (EndCurrentTurn) --
// Fight enforces this ordering, mirroring the reference's
// askForFighterStartTurn/askForFighterEndTurn validate-gate pairing
// (docs/opcodes/08-fight-combat-engine.md §1.4).
func (tl *Timeline) StartNextTurn() (fighter *Fighter, isNewTableTurn bool) {
	if len(tl.order) == 0 || !tl.AnyAlive() {
		tl.currentFighter = nil
		return nil, false
	}

	// A new table-turn begins whenever the current round has no living
	// fighters left owing a turn (roundRemaining == 0), OR when the very
	// first turn of the fight is starting (roundRemaining seeded lazily
	// below on first call). Crucially this is decided by the remaining-
	// schedule COUNT, not by "did the cursor wrap to index 0" -- a summon
	// spliced into the order mid-round shifts the fixed cursor and would
	// make the old start==0 heuristic mis-fire a spurious NEW_TABLE_TURN,
	// which was the "summoning double freezes the UI for a full round" bug.
	isNewTableTurn = tl.roundRemaining <= 0
	if isNewTableTurn {
		// Open a fresh round: re-seed the remaining schedule with every
		// living fighter and restart the pass at the front of the order.
		tl.tableTurn++
		tl.turnIndexInTable = 0
		tl.roundRemaining = tl.countAlive()
	}

	// Scan FORWARD from the cursor to the end of the order -- never
	// wrapping mid-round. Wrapping is handled solely by the new-round reset
	// above (cursor back to 0). This is what lets a summon appended at the
	// tail (turnIndexInTable already past the last original fighter) still
	// be reached this round: the cursor advances to idx+1 unclamped, so a
	// summon spliced in beyond it is the very next fighter selected rather
	// than being skipped past by a premature modulo wrap to index 0.
	for idx := tl.turnIndexInTable; idx < len(tl.order); idx++ {
		f := tl.order[idx]
		if !f.IsDead {
			tl.turnIndexInTable = idx + 1
			tl.currentFighter = f
			if tl.roundRemaining > 0 {
				tl.roundRemaining--
			}
			return f, isNewTableTurn
		}
	}

	// Every remaining slot this round was dead/skipped. The round is
	// effectively over -- force a fresh round on the next call by draining
	// the remaining schedule, and re-run to select the first living
	// fighter of that new round. (Guarded by AnyAlive above, so this
	// recursion terminates.)
	if tl.roundRemaining > 0 {
		tl.roundRemaining = 0
		return tl.StartNextTurn()
	}

	tl.currentFighter = nil
	return nil, false
}

// countAlive returns the number of living fighters currently in the turn
// order (the size of a fresh round's schedule).
func (tl *Timeline) countAlive() int {
	n := 0
	for _, f := range tl.order {
		if !f.IsDead {
			n++
		}
	}
	return n
}

// EndCurrentTurn clears the current fighter, marking the timeline ready
// for the next StartNextTurn call. Mirrors
// TurnBasedTimeline.endFighterTurn's validate-gate release.
func (tl *Timeline) EndCurrentTurn() {
	tl.currentFighter = nil
}

// OnFighterStartTurn resets AP and MP to their max values, mirroring
// AbstractFight.onFighterStartTurn, see docs/05-combat-engine.md §5.2.2.
// Also resets this fighter's per-table-turn spell cast-frequency counters
// (SpellCastHistory.onNewTurn(), see spell_cast_history.go -- Phase L),
// since CastMaxPerTurn/CastMaxPerTarget are both scoped to a single
// fighter turn, not the whole fight.
func OnFighterStartTurn(f *Fighter) {
	f.Characteristics[AP].ToMax()
	f.Characteristics[MP].ToMax()
	f.CastHistory.OnNewTurn()
}
