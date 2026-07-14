package dispatch

import (
	"context"

	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// RegisterCoachManagementHandlers wires coach inventory/equipment update
// opcodes, see docs/02-protocol.md COACH_INVENTORY_UPDATE_REQUEST/
// COACH_EQUIPMENT_UPDATE_REQUEST and
// CoachInventoryUpdateRequest.java/CoachEquipmentUpdateRequest.java.
func RegisterCoachManagementHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvCoachInventoryUpdateRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleCoachInventoryUpdate(session, payload, deps)
	})
	r.Register(protocol.RecvCoachEquipmentUpdateRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleCoachEquipmentUpdate(session, payload, deps)
	})
}

func handleCoachInventoryUpdate(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	// Reject inventory edits (remove/lock/unlock) while the coach is in a
	// duel/fight. A card staked in a bet fight must not be removed, locked,
	// or re-staked mid-fight: otherwise a losing player could make their
	// stake "disappear" before settlement to dodge losing it while still
	// collecting the opponent's stake on a win (stake evasion). The stake
	// is chosen server-side and settled at fight end; the inventory is
	// frozen for the duration.
	if _, inDuel := deps.Duels.GetByCoach(coach.ID); inDuel {
		return
	}

	ctx := context.Background()
	delta := &inventoryDelta{}

	removeCount := int(payload.Uint16())
	for i := 0; i < removeCount; i++ {
		id := uint(payload.Int64())
		removed, err := deps.Coach.RemoveCard(ctx, coach.ID, id)
		if err != nil {
			deps.Logger.Error().Err(err).Msg("dispatch: remove card failed")
			continue
		}
		if removed {
			delta.RemoveInventory = append(delta.RemoveInventory, id)
		}
	}

	lockCount := int(payload.Uint16())
	for i := 0; i < lockCount; i++ {
		id := uint(payload.Int64())
		card, err := deps.Coach.LockCard(ctx, coach.ID, id)
		if err != nil {
			deps.Logger.Error().Err(err).Msg("dispatch: lock card failed")
			continue
		}
		delta.AddInventory = append(delta.AddInventory, *card)
	}

	unlockCount := int(payload.Uint16())
	for i := 0; i < unlockCount; i++ {
		id := uint(payload.Int64())
		card, err := deps.Coach.UnlockCard(ctx, coach.ID, id)
		if err != nil {
			deps.Logger.Error().Err(err).Msg("dispatch: unlock card failed")
			continue
		}
		delta.AddInventory = append(delta.AddInventory, *card)
	}

	if payload.Err() != nil {
		return
	}

	// A remove can change the cached inventory (lock/unlock don't change
	// equipment, but refreshing keeps the in-memory copy authoritative).
	refreshOnlineInventory(deps, coach.ID)

	if frame, ok := delta.build(); ok {
		session.Send(frame)
	}
}

func handleCoachEquipmentUpdate(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	// Reject equipment changes while in a duel/fight, for the same reason
	// as inventory edits: equipping a staked card (pos > 0) mid-fight would
	// let a losing player dodge the stake transfer (TransferCard skips a
	// card whose pos != 0). The inventory/equipment is frozen for the
	// fight's duration.
	if _, inDuel := deps.Duels.GetByCoach(coach.ID); inDuel {
		return
	}

	ctx := context.Background()

	// Read all 14 equipment slots up front (fixed-size request, one long
	// UID per slot; 0 = empty slot), mirroring
	// CoachEquipmentUpdateRequest.java:36-53. We must read the whole
	// payload before touching the DB so a mid-loop read error can't leave
	// equipment half-applied.
	const equipmentSlotCount = 14
	slotUIDs := make([]uint, equipmentSlotCount)
	for i := 0; i < equipmentSlotCount; i++ {
		slotUIDs[i] = uint(payload.Int64())
	}
	if payload.Err() != nil {
		return
	}

	// Snapshot what's currently equipped BEFORE any change, so we can
	// compute the add/remove delta for the response.
	currentlyEquipped, err := deps.Coach.GetEquippedCards(ctx, coach.ID)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: load equipped cards failed")
		return
	}
	// stillRemoved tracks slots that were occupied before and haven't been
	// re-filled by the incoming request -- start with every previously
	// occupied slot, then remove any slot that gets re-equipped below.
	stillRemoved := make(map[int16]bool, len(currentlyEquipped))
	for _, c := range currentlyEquipped {
		stillRemoved[c.Pos] = true
	}

	// Step 1: unequip EVERYTHING first (pos -> 0). This is the critical
	// step the previous implementation was missing: without it, a card
	// moved out of a slot (or a slot emptied to uid=0) kept its old
	// position, so equipment changes appeared not to save. Mirrors
	// CoachEquipmentUpdateRequest.java:24-34.
	if err := deps.Coach.UnequipAll(ctx, coach.ID); err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: unequip-all failed")
		return
	}

	delta := &inventoryDelta{}

	// Step 2: re-equip according to the requested slot layout. Cards are
	// stored with Pos == wireSlot+1 (see equipment_slots.go) so slot 0 is
	// distinguishable from "unequipped" (Pos == 0).
	for wireSlot := int16(0); wireSlot < equipmentSlotCount; wireSlot++ {
		uid := slotUIDs[wireSlot]
		if uid == 0 {
			continue
		}

		storedPos := storedPosForWireSlot(wireSlot)
		card, err := deps.Coach.SetCardPosition(ctx, coach.ID, uid, storedPos)
		if err != nil {
			// Card not found / not owned -- skip it, matching the legacy
			// null-check behavior.
			continue
		}

		if stillRemoved[storedPos] {
			delete(stillRemoved, storedPos)
		} else {
			delta.AddEquipment = append(delta.AddEquipment, *card)
		}
	}

	for storedPos := range stillRemoved {
		delta.RemoveEquipment = append(delta.RemoveEquipment, wireSlotForStoredPos(storedPos))
	}

	equipped, err := deps.Coach.GetEquippedCards(ctx, coach.ID)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: reload equipped cards failed")
		return
	}
	broadcastFrame := buildCoachEquipmentUpdateMessage(coach.ID, equipped)
	for _, oc := range deps.World.SnapshotWorld() {
		oc.Session.Send(broadcastFrame)
	}

	// Refresh the cached in-memory inventory so a later ACTOR_SPAWN (sent to
	// a newly-joining player) shows this coach's CURRENT equipment, not the
	// stale login-time snapshot.
	refreshOnlineInventory(deps, coach.ID)

	if frame, ok := delta.build(); ok {
		session.Send(frame)
	}
}

// refreshOnlineInventory reloads a coach's full card inventory from the DB
// and replaces the in-memory registry copy (under the registry lock), so
// broadcast serializers that read cached equipment (ACTOR_SPAWN's equipped
// list) reflect the change instead of the stale login-time snapshot. No-op
// if the coach is offline. Best-effort: a load error is logged, not fatal.
func refreshOnlineInventory(deps *Deps, coachID uint) {
	if !deps.World.IsOnline(coachID) {
		return
	}
	inv, err := deps.Coach.GetInventory(context.Background(), coachID)
	if err != nil {
		deps.Logger.Error().Err(err).Uint("coach_id", coachID).Msg("dispatch: failed to refresh cached inventory")
		return
	}
	deps.World.UpdateInventory(coachID, inv)
}
