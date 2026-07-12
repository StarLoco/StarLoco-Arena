package dispatch

import (
	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// MovementCell is one waypoint in an actor movement path, mirroring the
// legacy ActorMovementRequest.java's inline (x,y,z) triplets.
type MovementCell struct {
	X, Y int32
	Z    int16
}

// duelTeamInfo bundles what's needed to serialize one side of a CREATE_FIGHT
// packet. SpellsByFighter/ObjectsByFighter carry each fighter's real
// loadout (keyed by fighter ID), fixing a reported bug where fighters
// showed up in-fight with no spells/equipment despite the player having
// equipped them -- see docs/08-java-parity-roadmap.md's write-up on this
// fix. Populated via FighterService.LoadoutMaps, same source
// buildCombatTeam already uses for the real combat.Fight's Fighter
// objects; this struct just threads that same data through to the wire
// format too.
type duelTeamInfo struct {
	TeamID           byte
	TeamName         string
	Coach            *domain.Coach
	CoachEquipment   []domain.CoachCard // equipped (Pos != 0) coach cards, for the CREATE_FIGHT look blob
	Fighters         []domain.Fighter
	SpellsByFighter  map[uint][]int32
	ObjectsByFighter map[uint][]int32

	// BetCard is the single coach card this team's coach staked in a bet
	// fight (selected in prepareCreateFight), announced to the client in
	// CREATE_FIGHT's per-team bet-card list so both sides see what's at
	// stake. nil for a no-bet fight (the list is then empty).
	BetCard *domain.CoachCard
}

// buildCreateFight serializes CREATE_FIGHT (opcode 8000), replicating the
// exact byte layout the current (working) Java server sends -- see
// Fight.java:79-206 and docs/02-protocol.md §2.4.7. This is a byte-for-byte
// port of the legacy hand-built buffer's field layout, EXCEPT for the
// per-fighter spells/equipment blobs, which now carry the fighter's real
// loadout (previously always empty, a known simplification -- see
// docs/08-java-parity-roadmap.md's write-up on this fix) using the same
// wire sub-formats already implemented and tested in inventory_codec.go
// (buildSpellBlob: flat int32[] per StackInventory<Spell>'s
// serializeQuantity=false format; buildInventoryBlob: [short pos][int32
// id] pairs per ArrayInventory<FighterCard>'s format) -- confirmed via
// docs/opcodes/07-fight-lifecycle.md's "Fighter.unserialize(buffer)"
// section to be the exact format the client expects here. store is used
// to resolve each equipped object id's real equipment-category slot
// (buildInventoryBlob); may be nil, in which case every fighter's
// equipment blob is empty (spells still populate normally, since
// buildSpellBlob needs no store lookup) rather than panicking -- a fight
// must never fail to start just because gamedata lookups are unavailable.
func buildCreateFight(fightType byte, bet int32, teamA, teamB duelTeamInfo, store *gamedata.Store, specialCells []combat.SpecialCellRender) protocol.OutboundFrame {
	w := protocol.NewWriter(256)

	w.PutByte(0)   // error code: 0 = ok
	w.PutUint16(0) // coach cards blob length (unused, matches legacy)
	w.PutInt32(int32(fightType))
	w.PutInt32(bet)
	w.PutByte(2) // team count

	writeTeam := func(team duelTeamInfo) {
		w.PutByte(team.TeamID)
		w.PutString(team.TeamName)
		w.PutByte(1) // coach count (1v1 duel only, matches legacy)
		w.PutInt64(int64(team.Coach.ID))
		w.PutString(team.Coach.Name)
		w.PutByte(team.Coach.Skin).PutByte(team.Coach.Hair).PutByte(team.Coach.Sex)

		// Equipped coach-card blob (Coach.unserialize with
		// options=EQUIPMENT): each equipped card composes the coach's
		// on-map sprite (hat/cloak/weapon/etc. via Coach.applyEquipment ->
		// setPartDescriptor). Previously always empty (a reported "coach
		// equipment doesn't show" bug). Same 15-byte-per-card wire format
		// (`[int16 slot][int32 templateId][int64 uniqueId][int8 flags]`)
		// already used by buildActorSpawn/buildCoachInformation -- see
		// §8.20 of docs/08-java-parity-roadmap.md.
		w.PutUint16(uint16(len(team.CoachEquipment) * 15))
		for _, card := range team.CoachEquipment {
			w.PutInt16(wireSlotForStoredPos(card.Pos)).PutInt32(card.TemplateID).PutInt64(int64(card.ID)).PutByte(card.Flag)
		}
		w.PutByte(byte(len(team.Fighters)))
		for _, f := range team.Fighters {
			// Wire id = real DB id + FighterWireIDBase, so fighter ids
			// never collide with coach ids (see combat.FighterWireIDBase);
			// the combat engine assigns the identical offset id to its
			// combat.Fighter (buildCombatTeam), so the two paths agree and
			// the client's getFighterById map is keyed consistently. The
			// REAL id (f.ID) is still used for loadout-map lookups below.
			w.PutInt64(combat.FighterWireIDBase + int64(f.ID))
			w.PutByte(f.Breed)
			w.PutString(f.Name)
			w.PutByte(f.Sex).PutByte(f.Skin)

			spellBlob := buildSpellBlob(team.SpellsByFighter[f.ID])
			w.PutUint16(uint16(len(spellBlob)))
			w.PutBytes(spellBlob)

			var objectBlob []byte
			if store != nil {
				objectBlob = buildInventoryBlob(store, team.ObjectsByFighter[f.ID])
			}
			w.PutUint16(uint16(len(objectBlob)))
			w.PutBytes(objectBlob)
		}
		w.PutUint16(0) // statistics report length (unused, matches legacy)

		// Bet card list: the coach cards this team's coach staked, announced
		// so the client shows what's at stake (FightCreationMessage:
		// `byte count` + `count × int referenceCardId` ->
		// coach.addBetCoachCard). One card per coach in this project's
		// one-random-card wagering rule; empty (count 0) for a no-bet fight.
		if team.BetCard != nil {
			w.PutByte(1)
			w.PutInt32(team.BetCard.TemplateID)
		} else {
			w.PutByte(0)
		}
	}

	writeTeam(teamA)
	writeTeam(teamB)

	// Timeline fighter list -- CRITICAL: must be emitted in the SAME order
	// the combat engine plays turns (initiative-descending), NOT team
	// insertion order. The client adds these to its own timeline with
	// ordered=false (FightCreationMessage.addFighterToTimeline(id,false,false)),
	// i.e. it trusts this wire order verbatim and does NOT re-sort by INIT.
	// It then processes FIGHTER_TURN_BEGIN strictly against the FRONT of
	// that queue (TurnBasedTimeline.askForFighterStartTurn only advances if
	// the named fighter is the very next queued one, else silently no-ops).
	// So if this list's order disagrees with the server's INIT-sorted turn
	// order, every FIGHTER_TURN_BEGIN for an out-of-position fighter is
	// dropped client-side -- the reported "I can only act on ONE of my two
	// fighters, the others' turns are skipped" bug. timelineFighterOrder
	// reproduces combat.BuildTurnOrder's stable INIT-descending sort exactly.
	timeline := timelineFighterOrder(teamA.Fighters, teamB.Fighters)
	w.PutByte(byte(len(timeline))) // timeline fighter count
	for _, f := range timeline {
		w.PutInt64(combat.FighterWireIDBase + int64(f.ID))
	}

	w.PutByte(0) // event count (unused, matches legacy)

	// Special-cell list: the battlefield buff/damage tiles the client
	// renders via StaticEffectAreaManager.getSpecialCell(cellBaseId) ->
	// instanceAnother(cellId, x, y, z) -- see FightCreationMessage.decode()
	// (docs/opcodes/07-fight-lifecycle.md). Wire tuple per cell:
	// [long cellBaseId][long cellId][int x][int y][short z].
	w.PutByte(byte(len(specialCells)))
	for _, c := range specialCells {
		w.PutInt64(c.CellBaseID)
		w.PutInt64(c.CellID)
		w.PutInt32(c.X)
		w.PutInt32(c.Y)
		w.PutInt16(c.Z)
	}

	return protocol.OutboundFrame{Opcode: protocol.SendCreateFight, Payload: w.Bytes()}
}

// timelineFighterOrder returns teamA's then teamB's fighters ordered exactly
// as combat.BuildTurnOrder orders the real combat.Fighter timeline:
// initiative-descending, stable (ties keep the teamA-before-teamB insertion
// order). This MUST stay byte-for-byte consistent with BuildTurnOrder (same
// input order = AllFighters() = teamA then teamB; same strict-`>` insertion
// so equal-INIT fighters preserve insertion order) so the CREATE_FIGHT
// timeline the client trusts matches the FIGHTER_TURN_BEGIN sequence the
// combat engine actually sends. Initiative comes from the breed base stats
// (combat.GetBreedStats), the same source NewFighterFromBreed uses; an
// unknown breed contributes INIT 0 (sorts last), never a panic.
func timelineFighterOrder(teamA, teamB []domain.Fighter) []domain.Fighter {
	all := append(append([]domain.Fighter{}, teamA...), teamB...)
	initOf := func(f domain.Fighter) int32 {
		if st, ok := combat.GetBreedStats(f.Breed); ok {
			return st.BaseInit
		}
		return 0
	}
	order := make([]domain.Fighter, 0, len(all))
	for _, f := range all {
		inserted := false
		for i := range order {
			if initOf(f) > initOf(order[i]) {
				order = append(order, domain.Fighter{})
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

// buildStartPresentation serializes START_PRESENTATION (empty payload).
func buildStartPresentation() protocol.OutboundFrame {
	return protocol.OutboundFrame{Opcode: protocol.SendStartPresentation}
}

// buildReadyForFight serializes READY_FOR_FIGHT, see SetReadyForFight.java:38.
func buildReadyForFight(errorCode byte, coachID uint) protocol.OutboundFrame {
	w := protocol.NewWriter(9)
	w.PutByte(errorCode).PutInt64(int64(coachID))
	return protocol.OutboundFrame{Opcode: protocol.SendReadyForFight, Payload: w.Bytes()}
}

// Fight-creation cancel reasons, mirroring Fight.java's CANCEL_REASON_*
// constants.
const (
	CancelReasonUnableToCreateFight       = 34
	CancelReasonTargetDisconnected        = 35
	CancelReasonNoSelectedTeam            = 36
	CancelReasonNoPendingFight            = 37
	CancelReasonInternalErrorDuringCreate = 38
	CancelReasonNoInstanceServer          = 39
	CancelReasonCanceledByOpponent        = 40
	CancelReasonBadFightParameters        = 41
	CancelReasonNoSelectedFighter         = 42
	CancelReasonNotEnoughFighters         = 43
	CancelReasonNotEnoughCoach            = 44
	CancelReasonInvalidFightersCount      = 45
	CancelReasonInvalidTeamBudget         = 46
	CancelReasonCantHoldTheBet            = 47
)

// buildFightCreationCanceled serializes FIGHT_CREATION_CANCELED_MESSAGE,
// see FightCreationCancel.java.
func buildFightCreationCanceled(fightID int64, reason byte) protocol.OutboundFrame {
	w := protocol.NewWriter(9)
	w.PutInt64(fightID).PutByte(reason)
	return protocol.OutboundFrame{Opcode: protocol.SendFightCreationCanceledMessage, Payload: w.Bytes()}
}

// buildActorMovement serializes ACTOR_MOVEMENT, see
// ActorMovementRequest.java:20-28.
func buildActorMovement(coachID uint, fromX, fromY int32, fromZ int16, cells []MovementCell) protocol.OutboundFrame {
	w := protocol.NewWriter(18 + len(cells)*10)
	w.PutInt64(int64(coachID)).PutInt32(fromX).PutInt32(fromY).PutInt16(fromZ)
	for _, c := range cells {
		w.PutInt32(c.X).PutInt32(c.Y).PutInt16(c.Z)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendActorMovement, Payload: w.Bytes()}
}
