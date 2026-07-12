package combat

import "time"

// This file implements Phase H: fight end. Ports BasicFight.checkFightEnd/
// killFighter/endFight exactly, per docs/opcodes/08-fight-combat-engine.md
// §1.2: checkFightEnd is NOT auto-invoked by killFighter -- every call
// site of killFighter in this package (applyDamage, effects.go's
// EffectDeath) explicitly calls checkFightEnd afterward, mirroring that
// contract.

// killFighter marks fighter dead, removes it from the turn order, and
// broadcasts FIGHTER_DIES, then checks whether the fight should end.
// Mirrors BasicFight.killFighter + docs/opcodes/08-fight-combat-engine.md
// §1.2's explicit note that the caller (here, this very function acting
// in that role for its own call sites) must call checkFightEnd after.
func (f *Fight) killFighter(fighter *Fighter, triggeringActionID int32) {
	if fighter.IsDead {
		return
	}
	fighter.IsDead = true
	f.Timeline.RemoveFighter(fighter)
	fighter.clearActiveEffectsOnDeath()
	f.broadcastAll(buildFighterDies(f.nextActionID(), fighter.ID))

	if fighter.CarriedByFighter != nil {
		fighter.CarriedByFighter.CarriedFighter = nil
		fighter.CarriedByFighter = nil
	}
	if fighter.CarriedFighter != nil {
		fighter.CarriedFighter.CarriedByFighter = nil
		fighter.CarriedFighter = nil
	}

	// A summoner's summons disappear the instant it dies (wiki: "Summons
	// disappear immediately when their creator is killed"). Kill every
	// living summon whose Father is the fighter that just died BEFORE the
	// fight-end check, so a summoner + all its summons vanishing together
	// can't leave a phantom "team still alive" via an orphaned summon.
	// killChildSummons recurses (a summon-of-a-summon dies too) via each
	// child's own killFighter call. This is intentionally NOT what the
	// decompiled retail client does (there a summon survives its creator --
	// AbstractFight.onFighterDeath only removes running effects linked to
	// the dead caster, not the summons); it reproduces the beta-era wiki
	// rule at explicit request.
	f.killChildSummons(fighter, triggeringActionID)

	if f.checkFightEnd() {
		return // fight is over; no turn to advance
	}

	// The fight continues. Timeline.RemoveFighter nils currentFighter when
	// the fighter that just died was the one whose turn it is. If a fighter
	// kills ITSELF mid-turn (walks onto a trap during its own move --
	// handleFighterMove / the summon AI's moveSummonTowardNearestOpponent),
	// nothing else will advance the turn: handleFighterMove returns
	// normally and every later FIGHTER_END_TURN_REQUEST then hits
	// askForFighterEndTurn's "current fighter is nil" guard, hanging the
	// fight on nobody's turn (and, downstream, jamming the client so
	// END_FIGHT_DONE never acks a subsequent forfeit). Recover centrally
	// here: cancel the now-stale turn clock and start the next turn.
	// Suppressed while inside startNextTurn (advancingTurn) because the
	// new-table-turn poison tick and turn-start killer/trap-cell paths
	// there already re-advance themselves -- advancing again here would
	// double-skip a turn.
	if !f.advancingTurn &&
		f.CurrentPhase() == PhaseAction &&
		f.Timeline.CurrentFighter() == nil {
		f.cancelTurnClock()
		f.startNextTurn()
	}
}

// killChildSummons kills every living summon whose Father is `parent`,
// used by killFighter so a summoner's summons die with it. Children are
// snapshotted from fightersByID first because each recursive killFighter
// mutates the timeline and the death cascade. It relies on killFighter's
// idempotent early-return (a child already dead is skipped) and on
// fightersByID retaining dead entries, so no child is visited twice.
func (f *Fight) killChildSummons(parent *Fighter, triggeringActionID int32) {
	var children []*Fighter
	for _, fr := range f.fightersByID {
		if fr.Father == parent && !fr.IsDead {
			children = append(children, fr)
		}
	}
	for _, child := range children {
		// killFighter itself recurses into this child's own summons and
		// runs checkFightEnd; guard against a child already removed by a
		// prior cascade step.
		if !child.IsDead {
			f.killFighter(child, triggeringActionID)
		}
	}
}

// checkFightEnd collects the set of distinct teams that still have at
// least one living fighter; if fewer than 2 teams remain, the fight ends.
// Mirrors BasicFight.checkFightEnd's early-return-as-soon-as-enough-teams-
// found shape, though at this codebase's scale (small fighter counts) a
// simple full scan is equally cheap and clearer.
func (f *Fight) checkFightEnd() bool {
	if f.CurrentPhase() == PhaseEnded {
		return true
	}

	teamsAlive := make(map[uint8]bool)
	for _, team := range f.Teams {
		for _, mate := range team.Mates {
			for _, fr := range mate.Fighters {
				if !fr.IsDead {
					teamsAlive[team.ID] = true
				}
			}
		}
	}

	if len(teamsAlive) >= 2 {
		return false
	}

	var winningTeamID uint8
	hasWinner := false
	for id := range teamsAlive {
		winningTeamID = id
		hasWinner = true
		break
	}

	f.endFight(false, winningTeamID, hasWinner)
	return true
}

// fleeCoach handles GiveUpFightRequestMessage: the forfeiting coach's team
// is treated as the loser regardless of remaining HP, ending the fight
// immediately with flee=1.
func (f *Fight) fleeCoach(coachID uint) {
	var fleeingTeamID uint8
	found := false
	for _, team := range f.Teams {
		for _, mate := range team.Mates {
			if mate.CoachID == coachID {
				fleeingTeamID = team.ID
				found = true
			}
		}
	}
	if !found {
		return
	}

	var winningTeamID uint8
	for _, team := range f.Teams {
		if team.ID != fleeingTeamID {
			winningTeamID = team.ID
			break
		}
	}
	f.endFight(true, winningTeamID, true)
}

// endFight stops all clocks, marks the phase Ended, and broadcasts
// END_FIGHT (8300). Mirrors BasicFight.endFight's teardown order
// (stop timeline -> ... -> destroyFight as the fight's own last act) --
// here, "destroy" is the caller (Manager) removing this Fight once Run()
// returns, which only happens after every coach acks
// EndFightDoneMessage(4321), see fight.go's Run loop.
func (f *Fight) endFight(flee bool, winningTeamID uint8, hasWinner bool) {
	f.cancelPhaseClock()
	f.cancelTurnClock()
	f.setPhase(PhaseEnded)

	// Classify every participant as winner/loser and run the end hook (if
	// any) so the service layer persists ladder & lifetime statistics and
	// hands back each coach's updated strength + PlayerStatisticsReport
	// blob to embed in END_FIGHT (drives the results screen's Level/Rank
	// and the "Statistiques du Coach" numbers).
	var participants []FightEndParticipant
	coachWon := make(map[uint]bool)
	for _, team := range f.Teams {
		won := hasWinner && team.ID == winningTeamID
		for _, mate := range team.Mates {
			coachWon[mate.CoachID] = won
			participants = append(participants, FightEndParticipant{CoachID: mate.CoachID, Won: won})
		}
	}

	var outcomes map[uint]FightEndOutcome
	if f.endHook != nil {
		outcomes = f.endHook(participants, time.Since(f.startedAt))
	}

	var winners, losers []endFightPlayerResult
	// lostCards/wonCards accumulate the card-wagering blobs across all
	// participants (per-coach entries keyed by coach id), populated from each
	// outcome's LostCards/WonCards (a bet fight; empty otherwise). The hook
	// has already done the authoritative inventory transfer -- these are the
	// display lists END_FIGHT carries so each side's results screen shows the
	// cards it won/lost.
	var lostCards, wonCards []cardBlobEntry
	for _, team := range f.Teams {
		for _, mate := range team.Mates {
			result := endFightPlayerResult{PlayerID: mate.CoachID}
			if oc, ok := outcomes[mate.CoachID]; ok {
				result.Strength = oc.Strength
				result.Report = oc.Report
				if entry, ok := cardBlobEntryFor(mate.CoachID, oc.LostCards); ok {
					lostCards = append(lostCards, entry)
				}
				if entry, ok := cardBlobEntryFor(mate.CoachID, oc.WonCards); ok {
					wonCards = append(wonCards, entry)
				}
			}
			if coachWon[mate.CoachID] {
				winners = append(winners, result)
			} else {
				losers = append(losers, result)
			}
		}
	}

	frame := buildEndFight(f.nextActionID(), flee, winners, losers, lostCards, wonCards)
	f.broadcastAll(frame)

	// Arm the force-complete clock: if not every coach acks
	// EndFightDoneMessage(4321) within EndFightAck, fire a PhaseEnded clock
	// that force-acks everyone so Run() can exit and the duel is removed.
	// This prevents a coach that never acks (malicious or crashed-but-still-
	// connected) from stranding both players' inventory/exchange (which are
	// frozen while "in a duel"). See docs on the leaked-duel fix.
	ackTimeout := f.Clocks.EndFightAck
	if ackTimeout <= 0 {
		ackTimeout = defaultEndFightAckClock
	}
	f.armPhaseClock(ackTimeout)

	f.logger.Info().
		Bool("flee", flee).
		Uint8("winning_team", winningTeamID).
		Msg("combat: fight ended")
}

// cardBlobEntryFor converts a coach's []FightEndCard (from its
// FightEndOutcome) into the internal cardBlobEntry the END_FIGHT wire writer
// consumes, keyed by the coach's id. Returns ok=false when the coach won/lost
// no cards, so the caller omits an empty entry from the blob (the client's
// unserializeCards would otherwise create a pointless zero-card entry).
func cardBlobEntryFor(coachID uint, cards []FightEndCard) (cardBlobEntry, bool) {
	if len(cards) == 0 {
		return cardBlobEntry{}, false
	}
	entry := cardBlobEntry{PlayerID: int64(coachID), Cards: make([]cardBlobCard, 0, len(cards))}
	for _, c := range cards {
		entry.Cards = append(entry.Cards, cardBlobCard{TemplateID: c.TemplateID, Cursed: c.Cursed})
	}
	return entry, true
}

// handleEndFightDone processes EndFightDoneMessage(4321): the client's ack
// after dismissing the results screen. Once every participating coach has
// acked, Fight.Run's loop returns, letting Manager forget this fight (see
// manager.go).
func (f *Fight) handleEndFightDone(c cmdEndFightDone) {
	if f.CurrentPhase() != PhaseEnded {
		return
	}
	f.endFightDoneCoaches[c.CoachID] = true
}

// allEndFightDoneAcked reports whether every participating coach has acked
// EndFightDoneMessage.
func (f *Fight) allEndFightDoneAcked() bool {
	for _, coachID := range f.CoachIDs() {
		if !f.endFightDoneCoaches[coachID] {
			return false
		}
	}
	return true
}
