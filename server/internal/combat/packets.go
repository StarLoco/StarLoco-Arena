package combat

import "github.com/dofusarena/go-server/internal/protocol"

// This file builds every combat-engine OutboundFrame, byte-exact per
// docs/opcodes/07-fight-lifecycle.md (phase transitions) and
// docs/opcodes/08-fight-combat-engine.md (turn/action opcodes). Kept in
// the combat package (rather than dispatch) since Fight builds and sends
// these directly via Broadcaster -- there is no dispatch-side packet
// construction for anything past CREATE_FIGHT.

// fightActionHeader writes the 8-byte header every FightActionMessage
// subclass carries: int32 uniqueId, int32 triggeringActionUniqueId (-1 if
// not triggered by another action).
func writeFightActionHeader(w *protocol.Writer, uniqueID int32, triggeringID int32) {
	w.PutInt32(uniqueID)
	w.PutInt32(triggeringID)
}

func buildTeamMateSetReadyForPlacement(coachID uint) protocol.OutboundFrame {
	w := protocol.NewWriter(8)
	w.PutInt64(int64(coachID))
	return protocol.OutboundFrame{Opcode: protocol.SendTeamMateSetReadyForPlacementMessage, Payload: w.Bytes()}
}

func buildTeamMateSetReadyForObservation(coachID uint) protocol.OutboundFrame {
	w := protocol.NewWriter(8)
	w.PutInt64(int64(coachID))
	return protocol.OutboundFrame{Opcode: protocol.SendTeamMateSetReadyForObservationMessage, Payload: w.Bytes()}
}

func buildTeamMateSetReadyForAction(coachID uint) protocol.OutboundFrame {
	w := protocol.NewWriter(8)
	w.PutInt64(int64(coachID))
	return protocol.OutboundFrame{Opcode: protocol.SendTeamMateSetReadyForActionMessage, Payload: w.Bytes()}
}

// NOTE: a mid-fight summon does NOT get its own ACTOR_APPEAR (4102). The
// client instantiates the summon entirely from the SUMMON
// RUNNING_EFFECT_ACTION -- Summon.execute() calls
// caster.summonCreature(...) -> summonFighter() -> addMobile(). Sending an
// ACTOR_APPEAR too would addMobile() a duplicate at the same id and freeze
// the client. See applySummon (effects.go) for the full explanation.

func buildMoveToFreePlacement(fighterID int64, pos Point3) protocol.OutboundFrame {
	w := protocol.NewWriter(18)
	w.PutInt64(fighterID).PutInt32(pos.X).PutInt32(pos.Y).PutInt16(pos.Z)
	return protocol.OutboundFrame{Opcode: protocol.SendMoveToFreePlacement, Payload: w.Bytes()}
}

func buildNewTableTurnBegin(uniqueID int32, numTurns byte, eventID int32) protocol.OutboundFrame {
	w := protocol.NewWriter(13)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutByte(numTurns)
	// eventId: the per-round event card the server drew (see events.go /
	// startNextTurn's drawEvent). 0 = no event (no event data loaded). The
	// client resolves this id to display the card; the effects are applied
	// server-side.
	w.PutInt32(eventID)
	return protocol.OutboundFrame{Opcode: protocol.SendNewTableTurnBegin, Payload: w.Bytes()}
}

func buildFighterTurnBegin(uniqueID int32, fighterID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(16)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutInt64(fighterID)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterTurnBegin, Payload: w.Bytes()}
}

func buildFighterTurnEnd(uniqueID int32, fighterID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(16)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutInt64(fighterID)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterTurnEnd, Payload: w.Bytes()}
}

func buildFighterCardUse(uniqueID, triggeringID int32, userID int64, cardID int32, criticalMiss bool, criticalHit bool, target Point3) protocol.OutboundFrame {
	w := protocol.NewWriter(32)
	writeFightActionHeader(w, uniqueID, triggeringID)
	w.PutInt64(userID)
	w.PutInt32(cardID)
	w.PutBool(criticalMiss)
	if !criticalMiss {
		w.PutBool(criticalHit)
		w.PutInt32(target.X).PutInt32(target.Y).PutInt16(target.Z)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendFighterCardUse, Payload: w.Bytes()}
}

func buildSpellCast(uniqueID, triggeringID int32, casterID int64, spellID int32, criticalMiss, criticalHit bool, target Point3) protocol.OutboundFrame {
	w := protocol.NewWriter(32)
	writeFightActionHeader(w, uniqueID, triggeringID)
	w.PutInt64(casterID)
	w.PutInt32(spellID)
	w.PutBool(criticalMiss)
	if !criticalMiss {
		w.PutBool(criticalHit)
		w.PutInt32(target.X).PutInt32(target.Y).PutInt16(target.Z)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendSpellCast, Payload: w.Bytes()}
}

func buildCloseCombat(uniqueID, triggeringID int32, userID int64, criticalMiss, criticalHit bool, target Point3) protocol.OutboundFrame {
	w := protocol.NewWriter(28)
	writeFightActionHeader(w, uniqueID, triggeringID)
	w.PutInt64(userID)
	w.PutBool(criticalMiss)
	if !criticalMiss {
		w.PutBool(criticalHit)
		w.PutInt32(target.X).PutInt32(target.Y).PutInt16(target.Z)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendCloseCombat, Payload: w.Bytes()}
}

// runningEffectPayload bundles the 34-byte serializedRunningEffect blob
// fields, per docs/opcodes/08-fight-combat-engine.md Part 2.
type runningEffectPayload struct {
	GenericEffectID int32
	CasterID        int64
	TargetID        int64
	TargetCell      Point3
	Value           int32
}

func buildRunningEffectAction(uniqueID, triggeringID int32, mustBeExecutedNow, triggered bool, runningEffectID int32, payload runningEffectPayload, containerType int32, containerID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(64)
	writeFightActionHeader(w, uniqueID, triggeringID)
	w.PutBool(mustBeExecutedNow)
	w.PutBool(triggered)
	w.PutInt32(runningEffectID)
	w.PutInt32(payload.GenericEffectID)
	w.PutInt64(payload.CasterID)
	w.PutInt64(payload.TargetID)
	w.PutInt32(payload.TargetCell.X)
	w.PutInt32(payload.TargetCell.Y)
	w.PutInt16(payload.TargetCell.Z)
	w.PutInt32(payload.Value)
	w.PutInt32(containerType)
	w.PutInt64(containerID)
	return protocol.OutboundFrame{Opcode: protocol.SendRunningEffectAction, Payload: w.Bytes()}
}

func buildEffectAreaAction(uniqueID, triggeringID int32, apply bool, areaID, targetID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(25)
	writeFightActionHeader(w, uniqueID, triggeringID)
	w.PutBool(apply)
	w.PutInt64(areaID)
	w.PutInt64(targetID)
	return protocol.OutboundFrame{Opcode: protocol.SendEffectAreaAction, Payload: w.Bytes()}
}

func buildFighterMove(uniqueID int32, fighterID int64, path []Point3) protocol.OutboundFrame {
	w := protocol.NewWriter(16 + len(path)*10)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutInt64(fighterID)
	for _, p := range path {
		w.PutInt32(p.X).PutInt32(p.Y).PutInt16(p.Z)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendFighterMove, Payload: w.Bytes()}
}

func buildFighterChangeDirection(uniqueID int32, fighterID int64, dir Direction8) protocol.OutboundFrame {
	w := protocol.NewWriter(13)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutInt64(fighterID)
	w.PutByte(byte(dir))
	return protocol.OutboundFrame{Opcode: protocol.SendFighterChangeDirection, Payload: w.Bytes()}
}

func buildFighterDies(uniqueID int32, fighterID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(16)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutInt64(fighterID)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterDies, Payload: w.Bytes()}
}

func buildFighterTackled(uniqueID int32, tackledFighterID, tacklerID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(24)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutInt64(tackledFighterID)
	w.PutInt64(tacklerID)
	return protocol.OutboundFrame{Opcode: protocol.SendFighterTackled, Payload: w.Bytes()}
}

// cardBlobEntry mirrors one entry of unserializeCards()'s repeated block:
// a player (or -1 for the bonus pool) plus their list of (templateId,
// cursed) card outcomes.
type cardBlobEntry struct {
	PlayerID int64
	Cards    []cardBlobCard
}

type cardBlobCard struct {
	TemplateID int32
	Cursed     bool
}

func writeCardBlob(w *protocol.Writer, entries []cardBlobEntry) {
	w.PutByte(byte(len(entries)))
	for _, e := range entries {
		w.PutInt64(e.PlayerID)
		w.PutByte(byte(len(e.Cards)))
		for _, c := range e.Cards {
			w.PutInt32(c.TemplateID)
			w.PutBool(c.Cursed)
		}
	}
}

// endFightPlayerResult is one winner/loser entry in END_FIGHT.
type endFightPlayerResult struct {
	PlayerID uint
	Strength int16
	// Report is the opaque PlayerStatisticsReport blob; nil/empty means
	// reportSize=0 (this codebase's stats reporting is fake/placeholder in
	// both servers, see docs/08-java-parity-roadmap.md §8.1).
	Report []byte
}

func writeEndFightPlayerList(w *protocol.Writer, results []endFightPlayerResult) {
	w.PutByte(byte(len(results)))
	for _, r := range results {
		w.PutInt64(int64(r.PlayerID))
		w.PutInt16(r.Strength)
		w.PutInt16(int16(len(r.Report)))
		if len(r.Report) > 0 {
			w.PutBytes(r.Report)
		}
	}
}

// buildEndFight serializes END_FIGHT(8300), branching on flee, per
// docs/opcodes/08-fight-combat-engine.md Part 3.
func buildEndFight(uniqueID int32, flee bool, winners, losers []endFightPlayerResult, lostCards, wonCards []cardBlobEntry) protocol.OutboundFrame {
	w := protocol.NewWriter(64)
	writeFightActionHeader(w, uniqueID, -1)
	w.PutBool(flee)
	if flee {
		if len(lostCards) == 0 {
			w.PutInt16(0)
		} else {
			blob := protocol.NewWriter(32)
			writeCardBlob(blob, lostCards)
			w.PutInt16(int16(blob.Len()))
			w.PutBytes(blob.Bytes())
		}
		return protocol.OutboundFrame{Opcode: protocol.SendEndFight, Payload: w.Bytes()}
	}

	writeEndFightPlayerList(w, winners)
	writeEndFightPlayerList(w, losers)

	lostBlob := protocol.NewWriter(16)
	writeCardBlob(lostBlob, lostCards)
	w.PutInt16(int16(lostBlob.Len()))
	w.PutBytes(lostBlob.Bytes())

	wonBlob := protocol.NewWriter(16)
	writeCardBlob(wonBlob, wonCards)
	w.PutInt16(int16(wonBlob.Len()))
	w.PutBytes(wonBlob.Bytes())

	return protocol.OutboundFrame{Opcode: protocol.SendEndFight, Payload: w.Bytes()}
}
