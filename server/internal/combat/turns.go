package combat

import (
	"time"

	"github.com/dofusarena/go-server/internal/protocol"
)

// This file implements Phase E: the turn cycle (NEW_TABLE_TURN_BEGIN,
// FIGHTER_TURN_BEGIN, FIGHTER_TURN_END, the 30s per-fighter turn clock,
// and forfeit routing), per docs/opcodes/08-fight-combat-engine.md Part 2.

// startNextTurn asks Timeline for the next living fighter (wrapping into a
// new table-turn if needed), broadcasts NEW_TABLE_TURN_BEGIN (if this
// wrapped) then FIGHTER_TURN_BEGIN, refills AP/MP, and arms the 30s turn
// clock. If no living fighters remain, this is a no-op (checkFightEnd
// should already have ended the fight via killFighter's caller before this
// path is ever reached with zero survivors on one side -- see
// docs/opcodes/08-fight-combat-engine.md §1.2).
func (f *Fight) startNextTurn() {
	// Once the fight has ENDED, all turn progression must halt. A fighter's
	// action can kill the last enemy mid-turn -> killFighter -> checkFightEnd
	// -> endFight(setPhase(Ended)) -- but control then returns to the caller
	// (e.g. the summon AI's runSummonTurn, or handleFighterEndTurn), which
	// would otherwise call askForFighterEndTurn -> startNextTurn here and
	// spuriously open a NEW round (NEW_TABLE_TURN_BEGIN + event effects)
	// AFTER END_FIGHT was already sent. That trailing turn traffic desyncs
	// the client's fight teardown (the reported "results popup shows but the
	// client stays on the fight map"). Guarding at this single choke point
	// covers every turn-advance path.
	if f.CurrentPhase() == PhaseEnded {
		return
	}

	// Mark that we're inside turn selection/start so any death triggered
	// from here (a new-table-turn poison tick, a turn-start killer/trap
	// cell) is NOT auto-advanced by killFighter -- the explicit re-calls
	// below already handle those. Restore (rather than blindly clear) on
	// exit because startNextTurn recurses into itself for those cases.
	prevAdvancing := f.advancingTurn
	f.advancingTurn = true
	defer func() { f.advancingTurn = prevAdvancing }()

	fighter, isNewTableTurn := f.Timeline.StartNextTurn()
	if fighter == nil {
		f.currentFighterID.Store(0)
		return
	}
	f.currentFighterID.Store(fighter.ID)

	if isNewTableTurn {
		tableTurnActionID := f.nextActionID()
		// EVENT CARD: select the round's event id (if event data is loaded)
		// and embed it in NEW_TABLE_TURN_BEGIN so the client instantiates the
		// card, THEN apply its effects to all fighters -- the message must go
		// out before the effect actions that reference the round (see
		// events.go's selectEventID/applyDrawnEvent). The duration tick below
		// then lets a round-scoped event effect (all events are
		// duration=[1,0]) expire at the next table-turn.
		eventID := f.selectEventID()
		f.broadcastAll(buildNewTableTurnBegin(tableTurnActionID, byte(f.Timeline.TableTurn()), eventID))

		// Phase J (docs/08-java-parity-roadmap.md): duration-tracked
		// effects (poison re-ticks, timed buff/debuff expiry) resolve
		// exactly at the new-table-turn boundary, mirroring
		// EffectDef.Duration's [tableTurns, turns] table-turn component.
		// This can kill a fighter (a poison tick reducing HP to 0), so
		// re-check fight-end status immediately after -- and if it
		// happened to kill the very fighter Timeline just selected to go
		// next, re-select (Timeline.RemoveFighter, called from
		// killFighter, already dropped them from the turn order).
		//
		// ORDER MATTERS: the previous round's duration-tracked effects
		// (including the PREVIOUS event card's round-scoped buff, all events
		// are duration=[1,0]) must expire HERE, BEFORE this round's event is
		// applied. Applying the new event first (the old order) and then
		// ticking on the SAME boundary immediately decremented the fresh
		// buff's 1-table-turn counter to 0 and reverted it -- so an event
		// like "+1 AP/MP this round" raised the client's displayed value but
		// the server silently removed the Max increase, and the player could
		// SEE the bonus but not spend it (the reported bug). Ticking first,
		// then applying the new event, gives the new buff a full round before
		// its own expiry next table-turn.
		f.tickActiveEffects(tableTurnActionID)
		if f.CurrentPhase() == PhaseEnded {
			return // a poison tick just ended the fight
		}
		if fighter.IsDead {
			f.startNextTurn()
			return
		}

		f.applyDrawnEvent(eventID, tableTurnActionID)
		if f.CurrentPhase() == PhaseEnded {
			return // an offensive event (e.g. "10 dmg to all") ended the fight
		}
		if fighter.IsDead {
			f.startNextTurn()
			return
		}
	}

	OnFighterStartTurn(fighter)
	// New turn: clear the last-action timestamp so the end-turn settle guard
	// only ever delays for an action taken DURING this turn (see
	// handleFighterEndTurn / markActionTaken).
	f.lastActionAt = time.Time{}
	turnBeginActionID := f.nextActionID()
	f.broadcastAll(buildFighterTurnBegin(turnBeginActionID, fighter.ID))

	f.logger.Info().
		Str("event", "turn_begin").
		Int64("fighter_id", fighter.ID).
		Int("table_turn", f.Timeline.TableTurn()).
		Bool("summon", isAISummon(fighter)).
		Int32("ap", fighter.Characteristic(AP)).
		Int32("mp", fighter.Characteristic(MP)).
		Int32("hp", fighter.Characteristic(HP)).
		Msg("fight: turn begin")

	// Special-cell check happens at the very start of the turn (game
	// manual §5.0.4): a killer cell ends the fight-participation of this
	// fighter before any clock is even armed for them.
	if f.applyTurnStartSpecialCell(fighter, turnBeginActionID) {
		// The killer cell queued a FIGHTER_DIES + its cell animation; flush
		// so the client plays them at turn start rather than holding them in
		// the pending group (there is no follow-up player action to flush
		// them, since this fighter is now dead). See flushActionSequence and
		// the note below on why a turn-start effect MUST be explicitly
		// flushed.
		f.flushActionSequence()
		if f.CurrentPhase() == PhaseEnded {
			return // killFighter's checkFightEnd already ended the fight
		}
		f.startNextTurn()
		return
	}
	// A non-killer special cell (trap damage, a heal, a turn buff) broadcast
	// its effect as a QUEUED action (mustBeExecutedNow=false) tied to
	// FIGHTER_TURN_BEGIN. The client's turn-begin handler already ran its own
	// executePendingGroup() BEFORE these frames arrived, so without an
	// explicit flush here the queued cell effect would sit in the pending
	// group and only play (and only then update the client-side HP bar) when
	// the fighter takes their FIRST action -- the reported "effect applies on
	// the first action, not at turn start, and HP doesn't drop". Flushing
	// commits the cell's animation + effect for immediate playback at the
	// start of the turn. Only needed when the fighter actually stood on a
	// special cell (avoids an 8200 barrier every single turn).
	if f.specialCellAt(fighter.Position) != SpecialCellNone {
		f.flushActionSequence()
	}

	// A summon has no owning coach, so no player input will ever end its
	// turn. Instead of arming the (pointless) turn clock and idling, play
	// its simple built-in AI turn immediately, which ends the turn itself
	// and advances play. See summon_ai.go.
	if isAISummon(fighter) {
		f.runSummonTurn(fighter)
		return
	}

	f.armTurnClock()
}

// CurrentFighterID returns the ID of the fighter whose turn it currently
// is (0 if none), safe to call from any goroutine.
func (f *Fight) CurrentFighterID() int64 {
	return f.currentFighterID.Load()
}

// askForFighterEndTurn is the validate-and-drain entry point for ending a
// fighter's turn, called identically whether triggered by the player's
// FighterEndTurnRequestMessage(8105) or the 30s turn clock firing --
// mirrors TurnBasedTimeline.askForFighterEndTurn (
// docs/opcodes/08-fight-combat-engine.md §1.4).
func (f *Fight) askForFighterEndTurn(fighterID int64) {
	// No turns to end once the fight is over -- a fighter's action can end
	// the fight mid-turn (killing the last enemy), and the caller (summon
	// AI / player end-turn) must not then broadcast a trailing
	// FIGHTER_TURN_END or open a new round after END_FIGHT. See
	// startNextTurn's PhaseEnded guard.
	if f.CurrentPhase() == PhaseEnded {
		return
	}
	cur := f.Timeline.CurrentFighter()
	if cur == nil || cur.ID != fighterID {
		f.logger.Warn().Int64("fighter_id", fighterID).Msg("combat: askForFighterEndTurn for non-current fighter, ignoring")
		return
	}
	f.cancelTurnClock()
	f.revertTurnEndSpecialCellBuffs(cur)
	f.Timeline.EndCurrentTurn()
	f.broadcastAll(buildFighterTurnEnd(f.nextActionID(), fighterID))
	f.startNextTurn()
}

// markActionTaken records the wall-clock time an animated action sequence
// (spell/card/close-combat cast) was just broadcast for the current fighter.
// The FIGHTER_TURN_END settle guard reads it to avoid ending the turn while
// the client is still playing that action's group. See handleFighterEndTurn.
func (f *Fight) markActionTaken() {
	f.lastActionAt = time.Now()
}

// handleFighterEndTurn processes FighterEndTurnRequestMessage(8105).
//
// Settle guard: if the fighter cast an animated action (spell/card/close
// combat) less than Clocks.ActionSettle ago, the client is likely still
// playing that action's queued group. Ending the turn now would stack the
// FIGHTER_TURN_END (and the next fighter's TURN_BEGIN) behind the still-
// running cast group on the client; if that group stalls, the fighter freezes
// (can't move/cast) -- the reported "cast Teleport, ended turn, stuck" bug.
// We instead re-arm the same end-turn command after the remaining settle
// window (via a one-shot timer, so the fight actor stays single-threaded),
// letting the client finish the cast first. The re-sent command carries the
// endTurnDeferred flag so it is processed immediately on the second pass
// (never deferred twice). ActionSettle==0 disables the guard entirely (tests
// and any caller that doesn't want the delay).
func (f *Fight) handleFighterEndTurn(c cmdFighterEndTurn) {
	if f.CurrentPhase() != PhaseAction {
		return
	}
	if _, ok := f.resolveOwnedFighter(c.RequesterCoachID, c.FighterID); !ok {
		return // a coach may only end the turn of a fighter they own
	}
	// Only the CURRENT fighter's turn can be ended; and only defer if this is
	// the first pass (not an already-deferred re-send) and a settle is set.
	if !c.deferred && f.Clocks.ActionSettle > 0 && !f.lastActionAt.IsZero() {
		if cur := f.Timeline.CurrentFighter(); cur != nil && cur.ID == c.FighterID {
			elapsed := time.Since(f.lastActionAt)
			if elapsed < f.Clocks.ActionSettle {
				remaining := f.Clocks.ActionSettle - elapsed
				time.AfterFunc(remaining, func() {
					f.Send(cmdFighterEndTurn{RequesterCoachID: c.RequesterCoachID, FighterID: c.FighterID, deferred: true})
				})
				return
			}
		}
	}
	f.askForFighterEndTurn(c.FighterID)
}

// handleGiveUp processes GiveUpFightRequestMessage(8151): the forfeiting
// coach's fighters are removed from the fight and treated as a loss,
// routing into fight-end with flee=1 (see fightend.go).
func (f *Fight) handleGiveUp(c cmdGiveUp) {
	// Forfeit is allowed in any phase before the fight has already ended
	// (Ended is filtered out one level up in handleCommand); a real
	// player can concede during presentation/placement/observation just
	// as easily as mid-action.
	f.fleeCoach(c.CoachID)
}

// handleFighterMove processes FighterActorMovementRequestMessage(4503):
// in-fight movement during ACTION, distinct from world-map movement
// (4501). Validates the path via A* pathfinding, deducts MP, and
// broadcasts FIGHTER_MOVE.
func (f *Fight) handleFighterMove(c cmdFighterMove) {
	if f.CurrentPhase() != PhaseAction {
		return
	}
	fighter, ok := f.resolveOwnedFighter(c.RequesterCoachID, c.FighterID)
	if !ok || fighter.IsDead {
		return
	}
	if f.Timeline.CurrentFighter() != fighter {
		return // only the current fighter may move
	}
	if len(c.Path) == 0 {
		return
	}

	// A ROOTED or PETRIFIED fighter cannot move under its own power (Enutrof's
	// Petrifaction "immobilizes" the target; Root likewise). The immobilizing
	// property is cleared when its duration expires. Rejecting the move here
	// (rather than only in push/pull) is what actually enforces the
	// immobilization for a player-initiated walk.
	if fighter.Properties.Has(PropertyRooted) || fighter.Properties.Has(PropertyPetrified) {
		return
	}

	// A carried fighter (on a Pandawa's shoulder) that moves DISMOUNTS first:
	// its Position holds the carrier's raised Z (carrierZ + height), so path
	// validation would reject the move as starting "in the air". Drop it to
	// the carrier's ground cell and break the carry link (mirrors the client's
	// MoveAction, which calls carrier.uncarry(here) when a carried fighter
	// moves) BEFORE resolving the path, so `start` below is a real ground
	// cell. The client performs its own dismount when it plays the resulting
	// FIGHTER_MOVE (MoveAction checks isCarried()), so no extra message is
	// needed. Fixes "the carried fighter can't move to get down".
	f.dismountIfCarried(fighter)

	// Tackle/evasion check (game manual §5.0.4): a fighter adjacent to a
	// living opponent must successfully evade before being allowed to
	// move at all. Failing ends the fighter's turn immediately.
	if !f.attemptEvadeTackle(fighter) {
		f.handleTackled(fighter)
		return
	}

	// Follow the client's OWN requested path (the green route it computed
	// and displayed), validating every step server-side rather than
	// re-deriving a fresh A* route. The client already picked a specific
	// shortest path among several equally-short options; re-running A* here
	// would pick a different equally-short route, so the fighter would walk
	// a path the player never saw (the reported "server sends the red path,
	// not the green one" bug). ValidateClientPath enforces the same
	// walkability/occupancy/single-axis/altitude rules FindPath does, so
	// server authority is preserved. c.Path is the step list EXCLUDING the
	// start cell. Fall back to A* only if the client's path is illegal
	// (e.g. desynced client, tampering) so a valid destination still moves.
	start := fighter.Position
	path := ValidateClientPath(fighter, start, c.Path, f)
	if path == nil {
		dest := c.Path[len(c.Path)-1]
		path = FindPath(fighter, start, dest, f)
	}
	if path == nil {
		return
	}
	// Stop-on-contact: a fighter walking past a living enemy halts the
	// instant it enters a cell adjacent to one, rather than gliding through
	// the enemy's zone-of-control to a farther destination. Truncate the
	// resolved path at the first step adjacent to an opponent (that step is
	// still taken -- the fighter ends its move there). This is distinct from
	// the start-of-move tackle roll (attemptEvadeTackle above), which
	// handles the case of *leaving* a cell already adjacent to an enemy.
	path = f.truncatePathOnEnemyContact(fighter, path)
	if len(path) == 0 {
		return
	}
	cost := PathMPCost(path)
	if int32(cost) > fighter.Characteristic(MP) {
		return
	}

	fighter.AddCharacteristic(MP, -int32(cost))

	// Phase M (docs/08-java-parity-roadmap.md): check every persistent
	// ground-effect area's enter/exit trigger once PER MOVEMENT STEP,
	// mirroring EffectAreaManager.checkInAndOut()'s own per-step call
	// site -- a fighter merely passing through a trap mid-path (not
	// necessarily ending their move there) must still trigger it. Called
	// from the fighter's actual pre-move position through each step of
	// the resolved path, not just start->final-destination.
	from := start
	for _, step := range path {
		f.checkInAndOut(from, step, fighter)
		// A ground effect entered mid-path (a trap) can kill the mover
		// itself. Stop walking further steps the moment that happens: the
		// fighter is dead at this cell and must not keep triggering areas
		// or "arrive" anywhere further along.
		if fighter.IsDead {
			break
		}
		from = step
	}
	// If the mover died mid-path, killFighter has already broadcast
	// FIGHTER_DIES and (via its central recovery) advanced the turn. Do
	// NOT broadcast a FIGHTER_MOVE for a now-dead fighter -- the client
	// already saw it die, and a trailing MOVE for a dead actor corrupts
	// its action pipeline (the reported DIES-then-MOVE desync that jams
	// the fight, blocking the later END_FIGHT ack). The MP debit above is
	// harmless on a dead fighter and needs no client counter update.
	if fighter.IsDead {
		return
	}
	fighter.Position = path[len(path)-1]

	// Broadcast the path WITH the fighter's starting cell prepended. The
	// client's move animator (MoveAction/PathMobile) treats the first wire
	// cell as the fighter's CURRENT position and interpolates from it
	// toward the next -- so a path that omits the start cell makes the
	// fighter visibly "teleport" one cell at the start of every move, and a
	// 1-cell move (wire path length 1) snaps to the destination with no
	// walk animation at all. Prepending `start` gives the animator the
	// origin it needs. See §8.21 of docs/08-java-parity-roadmap.md. (MP
	// cost and per-step trap checks above still use the A* path WITHOUT the
	// start cell, which is correct -- the start cell costs no MP and isn't
	// an entered cell.)
	wirePath := make([]Point3, 0, len(path)+1)
	wirePath = append(wirePath, start)
	wirePath = append(wirePath, path...)
	f.broadcastAll(buildFighterMove(f.nextActionID(), fighter.ID, wirePath))
	// Tell the client to debit the mover's MP counter by the path cost --
	// the client never does this itself (see broadcastCharacUse). Without
	// it the PM counter never decreases and the fighter can move without
	// limit.
	f.broadcastCharacUse(runningEffectMPUse, fighter, int32(cost))
	f.flushActionSequence()
}

// handleFighterDirectionChange processes
// FighterActorDirectionChangeRequestMessage(4521). Self-flushing (doesn't
// need the 8200 barrier), per
// docs/opcodes/08-fight-combat-engine.md Part 3.
func (f *Fight) handleFighterDirectionChange(c cmdFighterDirectionChange) {
	if f.CurrentPhase() != PhaseAction {
		return
	}
	fighter, ok := f.resolveOwnedFighter(c.RequesterCoachID, c.FighterID)
	if !ok {
		return
	}
	fighter.Direction = c.Direction
	f.broadcastAll(buildFighterChangeDirection(f.nextActionID(), fighter.ID, c.Direction))
}

// flushActionSequence sends the empty FIGHT_ACTION_SEQUENCE_EXECUTE(8200)
// commit barrier, per docs/opcodes/08-fight-combat-engine.md Part 3's
// "flush/commit barrier, not a batch container" design.
func (f *Fight) flushActionSequence() {
	f.broadcastAll(protocol.OutboundFrame{Opcode: protocol.SendFightActionSequenceExecute})
}

// IsOccupied implements CellInfoProvider: reports whether a living fighter
// (other than exclude) occupies p's X/Y.
func (f *Fight) IsOccupied(p Point3, exclude *Fighter) bool {
	for _, fr := range f.Timeline.Order() {
		if fr == exclude || fr.IsDead {
			continue
		}
		if fr.Position.X == p.X && fr.Position.Y == p.Y {
			return true
		}
	}
	return false
}

// IsWalkable implements CellInfoProvider. Backed by real .amw/
// elements.ade-derived map data when available (see
// docs/08-java-parity-roadmap.md Phase K, internal/gamedata's Map type)
// -- checks whether ANY resolved surface at p's X/Y is walkable at some
// altitude (not specifically p.Z, since the caller's own
// ArrivalAltitude/height-blocking check is what actually pins down which
// altitude a mover would land at; IsWalkable here is purely the "does a
// walkable cell exist here at all" gate used by A*'s neighbor-expansion
// and corner-cutting checks, mirroring how the reference's own
// FindPath-equivalent calls isWalkable(z) with an already-resolved
// candidate z rather than blindly checking p.Z). Falls back to always-true
// (deferring entirely to IsOccupied) if no map data was ever attached to
// this fight (SetMapData never called, or the map failed to load) --
// preserves old behavior for tests/dev setups that don't wire real map
// data.
func (f *Fight) IsWalkable(p Point3) bool {
	if f.mapData == nil {
		return true
	}
	// The coaches' raised start-point pedestals are off-limits to combatants
	// (and summons): they're spectator platforms, not battlefield cells.
	// Rejecting them here blocks every movement path (FindPath /
	// ValidateClientPath both gate on IsWalkable) from ever standing on or
	// crossing a coach's cell -- fixing "a fighter/summon walked onto the
	// coach's platform".
	if f.coachCells[[2]int32{p.X, p.Y}] {
		return false
	}
	if !f.mapData.HasCell(p.X, p.Y) {
		return false
	}
	for _, s := range f.mapData.SurfacesAt(p.X, p.Y) {
		if s.Walkable {
			return true
		}
	}
	return false
}

// ArrivalAltitude implements CellInfoProvider. Backed by real map data
// when available: picks the walkable surface at `to`'s cell whose
// STANDING altitude (surface base Altitude + its Height -- the top-of-
// block surface a mobile actually stands on, matching the client's own
// coordinates.z = base+height convention; see topmostWalkableSpot in
// dispatch/handlers_fight.go and docs/08-java-parity-roadmap.md §8.17)
// is closest to fromZ, then reports blocked=true if no walkable surface
// exists there at all. Returning the standing altitude (not the raw base)
// keeps a moving fighter's Position.Z consistent with the standing-Z its
// initial ACTOR_APPEAR placement used, so it never "sinks" mid-move.
// Falls back to the old always-succeeds/never-blocked stub (fromZ
// unchanged) if no map data is attached.
func (f *Fight) ArrivalAltitude(mover *Fighter, fromZ int16, to Point3) (int16, bool) {
	if f.mapData == nil {
		return fromZ, false
	}
	surfaces := f.mapData.SurfacesAt(to.X, to.Y)
	best, found := int16(0), false
	bestDist := 0
	for _, s := range surfaces {
		if !s.Walkable {
			continue
		}
		standing := s.Altitude + int16(s.Height)
		dist := int(standing) - int(fromZ)
		if dist < 0 {
			dist = -dist
		}
		if !found || dist < bestDist {
			best, bestDist, found = standing, dist, true
		}
	}
	if !found {
		return fromZ, true
	}
	return best, false
}
