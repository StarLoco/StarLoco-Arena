package e2e

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

// TestE2E_CoachEquipmentUpdate verifies the equip/unequip flow: the client
// sends a fixed 14-slot layout (long UID per slot, 0 = empty), the server
// re-equips accordingly and reports the result. This exercises the
// "unequip everything first, then re-equip" fix.
func TestE2E_CoachEquipmentUpdate(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	c := dialTestClient(t, addr)
	coachID := c.mustLogin("alice", "pw", "Alice")

	ctx := context.Background()
	// Grant the coach two cards to equip (no wire opcode grants cards).
	card1, err := a.Deps.Coach.AddCard(ctx, uint(coachID), 100, 1, 0)
	if err != nil {
		t.Fatalf("AddCard 1: %v", err)
	}
	card2, err := a.Deps.Coach.AddCard(ctx, uint(coachID), 101, 1, 0)
	if err != nil {
		t.Fatalf("AddCard 2: %v", err)
	}

	// COACH_EQUIPMENT_UPDATE_REQUEST: 14 longs (slot 0..13). Put card1 in
	// slot 0, card2 in slot 1, rest empty.
	payload := make([]byte, 0, 14*8)
	for slot := 0; slot < 14; slot++ {
		switch slot {
		case 0:
			payload = append(payload, putInt64(int64(card1.ID))...)
		case 1:
			payload = append(payload, putInt64(int64(card2.ID))...)
		default:
			payload = append(payload, putInt64(0)...)
		}
	}
	c.send(3, protocol.RecvCoachEquipmentUpdateRequest, payload)

	// Server broadcasts COACH_EQUIPMENT_UPDATE_MESSAGE (to everyone,
	// including self) then sends the COACH_INVENTORY_UPDATE_MESSAGE delta.
	equipMsg := c.drainUntil(protocol.SendCoachEquipmentUpdateMessage, 5)
	if len(equipMsg) == 0 {
		t.Fatal("expected non-empty COACH_EQUIPMENT_UPDATE_MESSAGE")
	}

	// Verify persistence: both cards should now be equipped.
	equipped, err := a.Deps.Coach.GetEquippedCards(ctx, uint(coachID))
	if err != nil {
		t.Fatalf("GetEquippedCards: %v", err)
	}
	if len(equipped) != 2 {
		t.Fatalf("expected 2 equipped cards after update, got %d", len(equipped))
	}

	// Now send an update that unequips everything (all 14 slots = 0).
	empty := make([]byte, 14*8)
	c.send(3, protocol.RecvCoachEquipmentUpdateRequest, empty)
	c.drainUntil(protocol.SendCoachEquipmentUpdateMessage, 5)

	equipped, err = a.Deps.Coach.GetEquippedCards(ctx, uint(coachID))
	if err != nil {
		t.Fatalf("GetEquippedCards after unequip: %v", err)
	}
	if len(equipped) != 0 {
		t.Errorf("expected 0 equipped after clearing all slots, got %d (unequip-all fix regressed)", len(equipped))
	}
}
