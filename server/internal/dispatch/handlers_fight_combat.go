package dispatch

import (
	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/world"
)

// This file wires the in-fight opcode handlers (Phase D readiness gates,
// Phase E turn cycle + close combat, Phase F spell/card casting) onto
// combat.Fight commands. Every handler here just decodes the wire payload,
// resolves the session's active combat.Fight (see sessionFight in
// handlers_fight.go), and forwards a combat.Command -- all validation and
// state mutation happens inside the Fight actor itself (internal/combat),
// never in dispatch, keeping the actor's serial-access guarantee intact.

// handleMoveToFreePlacementRequest processes
// MoveToFreePlacementRequestMessage (Recv 8021).
func handleMoveToFreePlacementRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()
	x := payload.Int32()
	y := payload.Int32()
	z := payload.Int16()
	if payload.Err() != nil {
		return
	}
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewMoveToFreePlacement(coach.ID, fighterID, x, y, z))
}

// handleCoachReadyForObservation processes
// TeamMateSetReadyForObservationRequestMessage (Recv 8023).
func handleCoachReadyForObservation(session *netio.Session, deps *Deps) {
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewCoachReadyObservation(coach.ID))
}

// handleCoachReadyForAction processes
// TeamMateSetReadyForActionRequestMessage (Recv 8031).
func handleCoachReadyForAction(session *netio.Session, deps *Deps) {
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewCoachReadyAction(coach.ID))
}

// handleFighterEndTurnRequest processes FighterEndTurnRequestMessage
// (Recv 8105): `long fighterId`, no header.
func handleFighterEndTurnRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()
	if payload.Err() != nil {
		return
	}
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewFighterEndTurn(coach.ID, fighterID))
}

// handleCloseCombatRequest processes CloseCombatRequestMessage
// (Recv 8111, 18 bytes): long fighterId; int32 x,y; short z.
func handleCloseCombatRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()
	x := payload.Int32()
	y := payload.Int32()
	z := payload.Int16()
	if payload.Err() != nil {
		return
	}
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewCloseCombat(coach.ID, fighterID, x, y, z))
}

// handleSpellCastRequest processes SpellCastRequestMessage (Recv 8109,
// 22 bytes): long fighterId; int32 spellId; int32 x,y; short z.
func handleSpellCastRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()
	spellID := payload.Int32()
	x := payload.Int32()
	y := payload.Int32()
	z := payload.Int16()
	if payload.Err() != nil {
		return
	}
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewSpellCast(coach.ID, fighterID, spellID, x, y, z))
}

// handleFighterCardUseRequest processes FighterCardUseRequestMessage
// (Recv 8107, 22 bytes): long fighterId; int32 cardId; int32 x,y; short z.
func handleFighterCardUseRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()
	cardID := payload.Int32()
	x := payload.Int32()
	y := payload.Int32()
	z := payload.Int16()
	if payload.Err() != nil {
		return
	}
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewCardUse(coach.ID, fighterID, cardID, x, y, z))
}

// handleFighterActorMovementRequest processes
// FighterActorMovementRequestMessage (Recv 4503, in-fight movement,
// distinct from world-map movement 4501): long fighterId + repeated
// (int32 x,y; short z).
func handleFighterActorMovementRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()
	var path []combat.Point3
	for payload.Remaining() >= 10 {
		x := payload.Int32()
		y := payload.Int32()
		z := payload.Int16()
		path = append(path, combat.Point3{X: x, Y: y, Z: z})
	}
	if payload.Err() != nil || len(path) == 0 {
		return
	}
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewFighterMove(coach.ID, fighterID, path))
}

// handleFighterActorDirectionChangeRequest processes
// FighterActorDirectionChangeRequestMessage (Recv 4521, 9 bytes):
// long fighterId; byte direction8.
func handleFighterActorDirectionChangeRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	fighterID := payload.Int64()
	dir := payload.Byte()
	if payload.Err() != nil {
		return
	}
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewFighterDirectionChange(coach.ID, fighterID, combat.Direction8(dir)))
}

// handleGiveUpFightRequest processes GiveUpFightRequestMessage
// (Recv 8151, forfeit): empty payload.
func handleGiveUpFightRequest(session *netio.Session, deps *Deps) {
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewGiveUp(coach.ID))
}

// handleEndFightDoneRequest processes EndFightDoneMessage (Recv 4321):
// the client's ack after dismissing the results screen. Once every
// participating coach has acked, the Fight actor's Run loop returns and
// Manager forgets the fight (see combat/fightend.go).
func handleEndFightDoneRequest(session *netio.Session, deps *Deps) {
	fight, coach, ok := sessionFight(session, deps)
	if !ok {
		return
	}
	fight.Send(combat.NewEndFightDone(coach.ID))

	// The duel's setup-flow bookkeeping (readiness maps, fighter
	// selections) is no longer needed once the fight has ACTUALLY
	// concluded -- i.e. every participating coach has acked, not just
	// this one.
	//
	// BUG FIX (docs/08-java-parity-roadmap.md §8.11 item 9): this
	// previously removed the duel immediately after ANY single coach's
	// ack, which breaks a subsequent disconnect for the OTHER coach (who
	// hasn't acked yet): dispatch/disconnect.go's own
	// deps.Duels.GetByCoach(coach.ID) lookup would already fail to find
	// the duel, silently skipping the synthesized EndFightDone ack that
	// fix relies on -- reintroducing the exact Fight-actor leak that fix
	// was meant to close, just via a different code path. Fixed by
	// deferring removal to a small goroutine that waits on Fight.Done()
	// (closed only once every coach has acked, see fightend.go's
	// allEndFightDoneAcked/fight.go's Run loop) before removing the
	// duel. Safe to spawn once per ack (both coaches' acks may each
	// reach this handler): DuelManager.Remove is an idempotent map
	// delete, so a second removal after the duel is already gone is a
	// harmless no-op.
	if duel, ok := deps.Duels.GetByCoach(coach.ID); ok {
		coachAID, coachBID := duel.CoachAID, duel.CoachBID
		go func() {
			<-fight.Done()
			// Every coach has acked the results screen and the fight actor
			// has exited: send both participants back to the overworld.
			// Without this the client dismisses the results popup but is
			// never told to leave the fight map -- it just sits on the
			// arena (the reported "won/lost popup shows but I stay on the
			// fight map" bug). The client's onFightEnded() only tears down
			// the fight net-frames; it relies on the server to reposition
			// it into the world (ENTER_WORLD_INSTANCE map 0).
			returnCoachToWorld(deps, coachAID)
			returnCoachToWorld(deps, coachBID)
			deps.Duels.Remove(duel.ID)
		}()
	}
}

// returnCoachToWorld sends a post-fight coach back onto the overworld (map
// 0) at its stored world position and re-populates its view of the other
// online coaches. The coach never left world.Registry during the fight
// (it's added at login and removed only on disconnect), so this does NOT
// re-Add it -- it only re-sends the world-scene messages the client needs
// after tearing down the fight scene: ENTER_WORLD_INSTANCE (moves the
// client off the fight map) plus an ACTOR_SPAWN of every OTHER online coach
// (so the overworld isn't empty). No-op if the coach is offline (already
// disconnected during/after the fight).
func returnCoachToWorld(deps *Deps, coachID uint) {
	oc, online := deps.World.Get(coachID)
	if !online {
		return
	}
	// Back on the overworld: clear the in-fight flag so this coach resumes
	// receiving world broadcasts (and is included in others' world scenes).
	deps.World.SetInFight(coachID, false)

	// Read this coach's position via a value snapshot rather than off the
	// live shared pointer: returnCoachToWorld runs on a post-fight
	// background goroutine, so a direct oc.Coach.PosX read here would race
	// a concurrent movement/TP write on the coach's own goroutine.
	view, ok := deps.World.ViewOf(coachID)
	if !ok {
		return
	}
	oc.Session.Send(buildEnterWorldInstance(
		float32(view.PosX), float32(view.PosY), view.PosZ, 0, false))

	// Repopulate this coach's world scene with every OTHER overworld coach...
	if others := deps.World.SnapshotWorldViewsWithout(coachID); len(others) > 0 {
		oc.Session.Send(buildActorSpawn(others))
	}
	// ...and re-spawn THIS coach into every other overworld coach's scene, so
	// they see them reappear on the map (they were despawned on fight entry).
	if view, ok := deps.World.ViewOf(coachID); ok {
		spawnMe := buildActorSpawn([]world.CoachView{view})
		for _, other := range deps.World.SnapshotWorldWithout(coachID) {
			other.Session.Send(spawnMe)
		}
	}
}
