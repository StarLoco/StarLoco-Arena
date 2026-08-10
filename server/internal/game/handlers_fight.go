package game

import (
	"sort"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/handshake"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerFightHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpReadyForPlacement, handleReadyForPlacement)
	r.Register(protocol.OpMoveToPlacementReq, handleMoveToPlacement)
	r.Register(protocol.OpReadyForObservation, handleReadyForObservation)
	r.Register(protocol.OpReadyForAction, handleReadyForAction)
	r.Register(protocol.OpFighterEndTurnReq, handleFighterEndTurn)
	r.Register(protocol.OpFighterMoveInFightReq, handleFighterMoveInFight)
	r.Register(protocol.OpFighterDirChangeReq, handleFighterDirectionChange)
	r.Register(protocol.OpSpellCastRequest, handleSpellCast)
	r.Register(protocol.OpFighterCardUseRequest, handleFighterCardUse)
	r.Register(protocol.OpCloseCombatRequest, handleCloseCombat)
	r.Register(protocol.OpGiveUpFight, handleGiveUp)
	r.Register(protocol.OpEndFightDone, handleEndFightDone)
}

// startFight builds a Fight from a mutually-accepted match and drives the
// clients into the presentation phase.
func (d *Deps) startFight(pm *pendingMatch) error {
	a := pickArena()
	teamA, err := d.buildFightTeam(a, pm.a, 0)
	if err != nil {
		return err
	}
	teamB, err := d.buildFightTeam(a, pm.b, 1)
	if err != nil {
		return err
	}
	return d.startFightWithTeams(a, teamA, teamB, false, 0, false)
}

// startFightWithTeams creates the Fight from two prepared teams and drives both
// clients into the presentation phase. It is opponent-agnostic: a team with a
// nil Session (e.g. the TESTER sparring partner) is excluded from world/session
// I/O and pre-marked ready in every phase gate, so a single real coach's ready
// still advances the fight. When practice is true the fight is unranked.
func (d *Deps) startFightWithTeams(a *arena, teamA, teamB *FightTeam, practice bool, challengeID int32, evolution bool) error {
	f := &Fight{
		FightType:    1,
		arena:        a,
		Practice:     practice,
		ChallengeID:  challengeID,
		Rules:        d.rulesForChallenge(challengeID),
		Evolution:    evolution,
		Teams:        [2]*FightTeam{teamA, teamB},
		deps:         d,
		readyPresent: make(map[uint]bool),
		readyObserve: make(map[uint]bool),
		readyAction:  make(map[uint]bool),
	}
	// A session-less team can never signal ready, so pre-mark it in every gate.
	for _, t := range f.Teams {
		if t != nil && t.Session == nil && t.Coach != nil {
			f.readyPresent[t.Coach.ID] = true
			f.readyObserve[t.Coach.ID] = true
			f.readyAction[t.Coach.ID] = true
		}
	}
	f.Timeline = buildTimeline(f)
	f.startActor()
	d.Fights.Create(f)

	// Entering a fight removes each real coach from the overworld: despawn them
	// from anyone who currently sees them. Session-less (synthetic) teams are
	// not in the world, so skip them.
	for _, t := range f.Teams {
		if t == nil || t.Session == nil || t.Coach == nil {
			continue
		}
		viewers := d.World.SetInFight(t.Coach.ID, true)
		if len(viewers) > 0 {
			if frame, err := buildActorDespawn([]uint{t.Coach.ID}); err == nil {
				for _, sess := range viewers {
					_ = sess.Send(frame)
				}
			}
		}
	}

	// Drive the fight-start sequence on the fight goroutine so all state access
	// (phase, clock) is serialized there.
	f.Post(func(f *Fight) {
		// Stream the arena world FIRST (EnterInstance = the arena world id) so the
		// client loads its topology before CREATE_FIGHT. The x,y is the CAMERA
		// FOCUS — the battlefield centre — so the view isn't off to one side.
		enter, _ := handshake.EncodeEnterInstance(
			float32(f.Arena().centerX), float32(f.Arena().centerY), 0,
			int16(f.Arena().worldID), true)
		for _, t := range f.Teams {
			if t == nil || t.Session == nil {
				continue
			}
			_ = t.Session.Send(enter)
		}
		// CREATE_FIGHT is built PER RECIPIENT: each coach's own equipped action
		// deck goes in the coach-card blob (the client copies that blob onto both
		// coaches, so it must carry the receiver's deck, not a shared one).
		for _, t := range f.Teams {
			if t == nil || t.Session == nil {
				continue
			}
			if createFrame, err := buildCreateFight(f, t.Coach, false); err == nil {
				_ = t.Session.Send(createFrame)
			}
		}
		// ACTOR_APPEAR (4102): insert coach + fighter avatars into the client's
		// iso render list + show them. The avatars are created HIDDEN during the
		// 8000 parse; only 4102 makes them appear (proven in the 2.04 server).
		if appear, err := buildActorAppearForFight(f); err == nil {
			f.broadcast(appear)
		}
		present, _ := buildEmpty(protocol.OpStartPresentation)
		f.broadcast(present)
		f.setPhase(PhasePresentation)
		f.armClock(presentationClock, (*Fight).advanceToPlacement)
	})
	d.Log.Info("fight started", "id", f.ID, "practice", practice,
		"arena", a.worldID, "evolution", evolution, "challenge", challengeID)
	return nil
}

// buildFightTeam creates a FightTeam from a searcher's roster, placing fighters
// on the arena's start cells for its side.
func (d *Deps) buildFightTeam(a *arena, sr *searcher, side uint8) (*FightTeam, error) {
	return d.buildFightTeamFor(sr.session, side, a.startCells(side), sr.teamIDs)
}

// buildFightTeamFor creates a FightTeam for a session from an explicit list of
// selected fighter ids, placing each fighter on one of the given arena start
// cells (which carry the cell's real x,y,z so the client accepts the position).
// If none of the ids resolve to an owned fighter, it falls back to the coach's
// first owned fighter, then to a synthesized placeholder, so a fight can always
// start (dev convenience).
func (d *Deps) buildFightTeamFor(sess *Session, side uint8, cells []Pos, rosterIDs []int64) (*FightTeam, error) {
	coach := sess.Coach
	team := &FightTeam{ID: side, Coach: coach, Session: sess}

	fighters, _ := d.Store.Fighters.ListByCoach(coach.ID)
	byID := make(map[uint]*domain.Fighter)
	for i := range fighters {
		byID[fighters[i].ID] = &fighters[i]
	}

	var chosen []*domain.Fighter
	for _, id := range rosterIDs {
		if fr := byID[uint(id)]; fr != nil {
			chosen = append(chosen, fr)
		}
	}
	if len(chosen) == 0 && len(fighters) > 0 {
		chosen = append(chosen, &fighters[0]) // fall back to first owned fighter
	}
	if len(chosen) == 0 {
		// Synthesize a placeholder so the fight can proceed.
		chosen = append(chosen, &domain.Fighter{Name: "Champion", BreedID: 1})
	}

	for i, fr := range chosen {
		pos := Pos{} // (0,0,0) fallback if the arena has no start cells
		if len(cells) > 0 {
			pos = cells[i%len(cells)]
		}
		st := computeFighterStatsWithConditions(fr, d.FighterCards, d.Conditions)
		ff := &FightFighter{
			WireID:  FighterWireIDBase + int64(fr.ID)*16 + int64(side)*8 + int64(i),
			CoachID: coach.ID,
			TeamID:  side,
			Fighter: fr,
			Pos:     pos,
			MaxHP:   st.MaxHP, HP: st.MaxHP,
			MaxAP: st.MaxAP, AP: st.MaxAP,
			MaxMP: st.MaxMP, MP: st.MaxMP,
			Init: st.Init, Range: st.Range,
			CritRate: st.CritRate, FumbleRate: st.FumbleRate,
			Block: st.Block, Dodge: st.Dodge,
			Stats: st.Stats,
		}
		team.Fighters = append(team.Fighters, ff)
	}
	return team, nil
}

// buildTimeline orders all fighters into the turn order: descending initiative
// (breed base init), stable so ties keep the team-A-before-team-B insertion
// order. The client plays exactly this order (it does no re-sort of its own), so
// this is what decides who acts first each round.
func buildTimeline(f *Fight) []*FightFighter {
	var tl []*FightFighter
	for _, t := range f.Teams {
		if t != nil {
			tl = append(tl, t.Fighters...)
		}
	}
	sort.SliceStable(tl, func(i, j int) bool {
		return fighterInit(tl[i]) > fighterInit(tl[j])
	})
	return tl
}

// --- phase-gate handlers ---
//
// Each phase transition is funneled through an idempotent advanceToX method
// guarded by f.phase, so a manual "both ready" signal and a clock firing can
// never double-advance. The clock is armed on entering each phase.

func handleReadyForPlacement(s *Session, _ *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil {
		return nil
	}
	cid := s.Coach.ID
	f.Post(func(f *Fight) {
		ack, _ := buildReadyAck(protocol.OpReadyForPlacementAck, cid)
		f.broadcast(ack)
		if f.markReady(f.readyPresent, cid) {
			f.advanceToPlacement()
		}
	})
	return nil
}

// advanceToPlacement moves presentation -> placement exactly once.
func (f *Fight) advanceToPlacement() {
	if !f.transition(PhasePresentation, PhasePlacement) {
		return
	}
	end, _ := buildEmpty(protocol.OpEndPresentation)
	f.broadcast(end)
	start, _ := buildEmpty(protocol.OpStartPlacement)
	f.broadcast(start)
	f.armClock(placementClock, (*Fight).advanceToObservation)
}

func handleMoveToPlacement(s *Session, frame *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil {
		return nil
	}
	r := protocol.NewReader(frame.Payload)
	wireID, _ := r.I64()
	x, _ := r.I32()
	y, _ := r.I32()
	z, _ := r.U16()
	cid := s.Coach.ID
	f.Post(func(f *Fight) {
		ff := f.fighterByWireID(wireID)
		if ff == nil || ff.CoachID != cid {
			return // not your fighter
		}
		ff.Pos = Pos{X: x, Y: y, Z: int16(z)}
		bc, _ := buildPlacementBroadcast(wireID, ff.Pos)
		f.broadcast(bc)
	})
	return nil
}

func handleReadyForObservation(s *Session, _ *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil {
		return nil
	}
	cid := s.Coach.ID
	f.Post(func(f *Fight) {
		ack, _ := buildReadyAck(protocol.OpReadyForObservationAck, cid)
		f.broadcast(ack)
		if f.markReady(f.readyObserve, cid) {
			f.advanceToObservation()
		}
	})
	return nil
}

// advanceToObservation moves placement -> observation exactly once.
func (f *Fight) advanceToObservation() {
	if !f.transition(PhasePlacement, PhaseObservation) {
		return
	}
	end, _ := buildEmpty(protocol.OpEndPlacement)
	f.broadcast(end)
	start, _ := buildEmpty(protocol.OpStartObservation)
	f.broadcast(start)
	f.armClock(observationClock, (*Fight).advanceToAction)
}

func handleReadyForAction(s *Session, _ *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil {
		return nil
	}
	cid := s.Coach.ID
	f.Post(func(f *Fight) {
		ack, _ := buildReadyAck(protocol.OpReadyForActionAck, cid)
		f.broadcast(ack)
		if f.markReady(f.readyAction, cid) {
			f.advanceToAction()
		}
	})
	return nil
}

// advanceToAction moves observation -> action exactly once and starts turn 1.
func (f *Fight) advanceToAction() {
	if !f.transition(PhaseObservation, PhaseAction) {
		return
	}
	end, _ := buildEmpty(protocol.OpEndObservation)
	f.broadcast(end)
	start, _ := buildEmpty(protocol.OpStartAction)
	f.broadcast(start)
	f.startFirstTurn()
}

// transition atomically moves the phase from `from` to `to`, returning false if
// the fight wasn't in `from` (already advanced/ended) so callers run once.
func (f *Fight) transition(from, to FightPhase) bool {
	return f.phase.CompareAndSwap(int32(from), int32(to))
}

// startFirstTurn opens the action phase with the first table turn + first
// fighter's turn, and arms the turn clock.
func (f *Fight) startFirstTurn() {
	f.tableTurn = 1
	// np_1 type 12 lands BEFORE the first round card, so a fight-long buff is
	// already in place when round 1 resolves (and, being sent before the first
	// turn begins, the client shows its icon from the very first fighter).
	f.applyFightStartEffects()
	f.beginTableTurn()
	if len(f.Timeline) > 0 {
		f.turnIndex = 0
		f.beginTurn(f.Timeline[0])
	}
}

// beginTurn refills the fighter, broadcasts its FIGHTER_TURN_BEGIN and arms the
// turn clock. Session-less (sparring/AI) fighters auto-pass on the short
// aiTurnClock so a practice fight never dead-waits on a dummy; a human fighter
// gets the full turnClock to act.
func (f *Fight) beginTurn(ff *FightFighter) {
	refillFighter(ff)
	ff.CastHistory.onNewTurn() // reset this fighter's per-turn cast counters
	turn, _ := buildFighterTurnBegin(f.nextActionUID(), ff.WireID)
	f.broadcast(turn)
	// A turn-start glyph/special the fighter stands on fires now (before it acts).
	f.checkEffectAreasTurnStart(ff)
	// Map-authored special cells fire on the same trigger: only when a fighter
	// STARTS its turn on one (walking over it does nothing) — see specialcells.go.
	if f.applyTurnStartSpecialCell(ff) {
		return // a killer/trap cell ended this fighter's turn (and maybe the fight)
	}
	if f.Phase() != PhaseAction {
		return // a turn-start glyph ended the fight
	}
	if ff.HP <= 0 {
		f.endTurn(ff.WireID) // died at turn start: pass to the next fighter
		return
	}
	ai := f.isAIControlled(ff)
	clock := f.turnClockFor()
	if ai {
		clock = aiTurnClock
	}
	if f.deps != nil && f.deps.Log != nil {
		name := ""
		if ff.Fighter != nil {
			name = ff.Fighter.Name
		}
		f.deps.Log.Debug("turn begin", "wire", ff.WireID, "name", name,
			"ai", ai, "summon", ff.isSummon(), "clockMs", clock.Milliseconds(), "mp", ff.MP, "ap", ff.AP)
	}
	// A petrified fighter cannot act — pass its turn after a short beat (both a
	// human and an AI skip it). Otherwise an AI fighter (sparring opponent /
	// summon) is played by the built-in AI after a short beat, and a human gets
	// the full turn clock.
	switch {
	case ff.hasState(stateSkipTurn):
		// Skip-turn (56/111): pass this turn and consume one skip.
		ff.consumeSkipTurn()
		f.armClock(aiTurnClock, func(f *Fight) { f.forceEndTurn(ff.WireID) })
	case ff.hasState(statePetrified):
		f.armClock(aiTurnClock, func(f *Fight) { f.forceEndTurn(ff.WireID) })
	case f.teamAbsent(ff):
		// A disconnected coach's fighters auto-pass — they are NOT AI-played (the
		// coach may still reconnect); the grace timer will forfeit if it doesn't.
		f.armClock(aiTurnClock, func(f *Fight) { f.forceEndTurn(ff.WireID) })
	case ai:
		f.armClock(aiTurnClock, func(f *Fight) { f.runAITurn(ff) })
	default:
		f.armClock(f.turnClockFor(), func(f *Fight) { f.forceEndTurn(ff.WireID) })
	}
}

// refillFighter restores a fighter's AP/MP to its per-turn ceiling (breed base +
// equipped-card bonuses). Falls back to the breed base when the ceiling is unset
// (lightweight tests that build a FightFighter without computed maxima).
func refillFighter(ff *FightFighter) {
	ap, mp := ff.MaxAP, ff.MaxMP
	if ap <= 0 {
		ap = baseAP
	}
	if mp <= 0 {
		mp = baseMP
	}
	ff.AP, ff.MP = ap, mp
}

// Base action/movement points per turn (breed base: AP=6, MP=3), used as a
// fallback when a fighter has no computed maxima.
const (
	baseAP = 6
	baseMP = 3
)

func handleFighterEndTurn(s *Session, frame *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil || f.Phase() != PhaseAction {
		return nil
	}
	wireID, _ := protocol.NewReader(frame.Payload).I64()
	cid := s.Coach.ID
	deps := s.deps
	f.Post(func(f *Fight) {
		// Only the coach who owns the current-turn fighter may end it.
		ff := f.fighterByWireID(wireID)
		if deps.Log != nil {
			deps.Log.Debug("client end-turn req (8105)", "wire", wireID,
				"haveFighter", ff != nil, "current", f.isCurrentTurn(wireID))
		}
		if ff == nil || ff.CoachID != cid || !f.isCurrentTurn(wireID) {
			return
		}
		f.endTurn(wireID)
	})
	return nil
}

// forceEndTurn is the clock-driven turn timeout: end the current fighter's turn
// even though the coach never signalled.
func (f *Fight) forceEndTurn(wireID int64) {
	if f.Phase() != PhaseAction || !f.isCurrentTurn(wireID) {
		return
	}
	if f.deps != nil && f.deps.Log != nil {
		f.deps.Log.Debug("force end-turn (clock timeout)", "wire", wireID)
	}
	f.endTurn(wireID)
}

// endTurn broadcasts FIGHTER_TURN_END for wireID, advances the timeline to the
// next living fighter, refills it, broadcasts its turn-begin and (re)arms the
// turn clock. Safe for both the manual handler and the clock.
func (f *Fight) endTurn(wireID int64) {
	if f.Phase() != PhaseAction {
		return
	}
	end, _ := protocol.EncodeS2C(protocol.OpFighterTurnEnd,
		protocol.NewWriter().I32(f.nextActionUID()).I32(-1).I64(wireID).Bytes())
	f.broadcast(end)

	// A special cell's bonus lasts only the turn it was granted on.
	f.revertSpecialCellBuffs(f.fighterByWireID(wireID))

	// Advance to the next LIVING fighter (wrapping into a new table turn).
	newTable := false
	var next *FightFighter
	for i := 0; i < len(f.Timeline); i++ {
		f.turnIndex++
		if f.turnIndex >= len(f.Timeline) {
			f.turnIndex = 0
			newTable = true
		}
		if f.Timeline[f.turnIndex].HP > 0 {
			next = f.Timeline[f.turnIndex]
			break
		}
	}
	if next == nil {
		return // no living fighter (fight should be ending)
	}

	if newTable {
		f.tableTurn++
		// A new round elapsed: age every fighter's timed buffs (reverting those
		// that expired), status states, damage-transfer links and auras before the
		// next fighter refills.
		f.tickBuffs()
		f.tickStates()
		f.tickTransfers()
		f.tickEffectAreas()
		f.tickPoisons()
		f.beginTableTurn()
		// Sudden death: once the fight reaches the configured turn the arena
		// collapses to a small central area (suddendeath.go). Fires once.
		f.maybeTriggerSuddenDeath()
		if f.Phase() != PhaseAction {
			return // the collapse ended the fight
		}
		// An alternative win condition (np_1 type 14) can end the fight with
		// both sides still standing. Checked AFTER the collapse, because the
		// shipped conditions all outlast the default sudden-death turn: the
		// last rounds of a "Défi du temps" are meant to be fought on a
		// shrinking arena, and a fighter the collapse just killed must not
		// still be credited with surviving the round.
		if f.deps != nil {
			f.deps.checkVictoryConditions(f)
			if f.Phase() != PhaseAction {
				return // a victory condition ended the fight
			}
		}
	}
	f.beginTurn(next)
}

func handleGiveUp(s *Session, _ *protocol.C2SFrame) error {
	f := s.deps.Fights.ByCoach(coachID(s))
	if f == nil {
		return nil
	}
	cid := s.Coach.ID
	deps := s.deps
	f.Post(func(f *Fight) { deps.forfeitCoach(f, cid) })
	return nil
}

// forfeitCoach makes coachID lose the fight: every fighter on its team (including
// summons) dies, then the victory is resolved for the opponent (checkFightEnd
// sends END_FIGHT + stats + reward). Shared by the give-up button, the reconnect
// grace timeout and a returning coach. MUST run on the fight actor.
func (d *Deps) forfeitCoach(f *Fight, coachID uint) {
	if f.Phase() == PhaseEnded {
		return
	}
	t := f.teamOfCoach(coachID)
	if t == nil {
		return
	}
	for _, ff := range t.Fighters {
		if ff.HP > 0 {
			ff.HP = 0
			dies, _ := buildFighterDies(f.nextActionUID(), ff.WireID)
			f.broadcast(dies)
		}
	}
	d.checkFightEnd(f)
}

// coachLeftFight handles a coach dropping its connection mid-fight. A practice
// fight (synthetic opponent) is torn down — there is no one to award a win. In a
// real fight the leaver's team is flagged ABSENT and detached, but the fight is
// KEPT ALIVE so the coach can reconnect (the 2.70 client supports resume): the
// absent team's turns auto-pass and a grace timer forfeits it if it never returns.
// If BOTH real teams are absent the fight is torn down.
func (d *Deps) coachLeftFight(f *Fight, coachID uint) {
	if f == nil {
		return
	}
	f.Post(func(f *Fight) { d.coachLeftFightOnActor(f, coachID) })
}

// coachLeftFightOnActor is the fight-actor half of coachLeftFight (also called
// directly by tests). See coachLeftFight.
func (d *Deps) coachLeftFightOnActor(f *Fight, coachID uint) {
	if f.Phase() == PhaseEnded {
		return
	}
	// Practice ("Tester") fights have no real opponent to win — just tear down.
	if f.Practice {
		d.endFight(f)
		return
	}
	t := f.teamOfCoach(coachID)
	if t == nil {
		return
	}
	t.Session = nil
	t.Absent = true
	d.Log.Info("coach left fight (grace period)", "id", f.ID, "coach", coachID)

	// Both sides gone → nobody to play or win.
	if f.allTeamsAbsent() {
		d.endFight(f)
		return
	}
	// If it's the absent coach's turn right now, pass it immediately so the
	// opponent isn't left waiting out the departed coach's full turn clock.
	if f.Phase() == PhaseAction {
		if cur := f.currentFighter(); cur != nil && cur.CoachID == coachID {
			f.endTurn(cur.WireID)
		}
	}
	// Arm the reconnect grace: forfeit the absent coach if it hasn't returned.
	f.armGrace(disconnectGraceClock, func(f *Fight) {
		d.Log.Info("reconnect grace expired -> forfeit", "id", f.ID, "coach", coachID)
		d.forfeitCoach(f, coachID)
	})
}

// markReady records a coach as ready and returns true once both coaches are.
// Called inside the actor (no lock).
func (f *Fight) markReady(m map[uint]bool, coachID uint) bool {
	m[coachID] = true
	return len(m) >= 2
}

// endFight tears a fight down with NO winner declared — used for a practice fight
// or when BOTH coaches have left: it stops the clocks, returns any still-connected
// coach to the overworld and stops the actor. A single mid-fight disconnect goes
// through coachLeftFight (grace period + forfeit) instead, which DOES declare the
// opponent the winner. Safe to call from any goroutine: the teardown runs on the
// fight actor. CAS on the phase makes it run exactly once.
func (d *Deps) endFight(f *Fight) {
	if !f.phase.CompareAndSwap(int32(PhasePresentation), int32(PhaseEnded)) &&
		!f.phase.CompareAndSwap(int32(PhasePlacement), int32(PhaseEnded)) &&
		!f.phase.CompareAndSwap(int32(PhaseObservation), int32(PhaseEnded)) &&
		!f.phase.CompareAndSwap(int32(PhaseAction), int32(PhaseEnded)) {
		return // already ended
	}
	d.Fights.Remove(f)
	// Run the teardown on the actor (so clock access is race-free), then stop it.
	posted := f.Post(func(f *Fight) {
		f.stopClock()
		f.stopGrace()
		for _, t := range f.Teams {
			if t == nil || t.Coach == nil {
				continue
			}
			d.World.SetInFight(t.Coach.ID, false)
			if t.Session != nil {
				enter, _ := handshake.EncodeEnterInstance(
					float32(t.Coach.PosX), float32(t.Coach.PosY), t.Coach.PosZ, 0, false)
				_ = t.Session.Send(enter)
			}
		}
		f.stopActor()
	})
	if !posted {
		// Actor already stopped: do the world cleanup directly.
		for _, t := range f.Teams {
			if t != nil && t.Coach != nil {
				d.World.SetInFight(t.Coach.ID, false)
			}
		}
	}
	d.Log.Info("fight ended (teardown)", "id", f.ID)
}

// coachID returns the session's coach id (0 if none).
func coachID(s *Session) uint {
	if s.Coach != nil {
		return s.Coach.ID
	}
	return 0
}
