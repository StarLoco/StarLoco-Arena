package dispatch

import (
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/protocol"
)

// inventoryDelta accumulates the four sections of a
// COACH_INVENTORY_UPDATE_MESSAGE payload, mirroring the legacy
// CoachInventoryUpdateMessage.java builder class.
type inventoryDelta struct {
	AddEquipment    []domain.CoachCard
	RemoveEquipment []int16
	AddInventory    []domain.CoachCard
	RemoveInventory []uint
}

func (d *inventoryDelta) isEmpty() bool {
	return len(d.AddEquipment) == 0 && len(d.RemoveEquipment) == 0 && len(d.AddInventory) == 0 && len(d.RemoveInventory) == 0
}

// build serializes the four-section COACH_INVENTORY_UPDATE_MESSAGE payload,
// see CoachInventoryUpdateMessage.java:42-98.
func (d *inventoryDelta) build() (protocol.OutboundFrame, bool) {
	if d.isEmpty() {
		return protocol.OutboundFrame{}, false
	}

	size := 8 + len(d.AddInventory)*15 + len(d.RemoveInventory)*8 + len(d.AddEquipment)*15 + len(d.RemoveEquipment)*2
	w := protocol.NewWriter(size)

	w.PutUint16(uint16(len(d.AddEquipment)))
	for _, c := range d.AddEquipment {
		// c.Pos is the 1-based stored position; the wire expects the
		// 0-based equipment slot (see equipment_slots.go).
		w.PutInt16(wireSlotForStoredPos(c.Pos)).PutInt32(c.TemplateID).PutInt64(int64(c.ID)).PutByte(c.Flag)
	}

	w.PutUint16(uint16(len(d.RemoveEquipment)))
	for _, wireSlot := range d.RemoveEquipment {
		// RemoveEquipment already holds 0-based wire slots (converted by
		// the handler), so no translation here.
		w.PutInt16(wireSlot)
	}

	w.PutUint16(uint16(len(d.AddInventory)))
	for _, c := range d.AddInventory {
		w.PutInt32(c.TemplateID).PutInt64(int64(c.ID)).PutByte(c.Flag).PutInt16(c.Quantity)
	}

	w.PutUint16(uint16(len(d.RemoveInventory)))
	for _, id := range d.RemoveInventory {
		w.PutInt64(int64(id))
	}

	return protocol.OutboundFrame{Opcode: protocol.SendCoachInventoryUpdateMessage, Payload: w.Bytes()}, true
}

// buildCoachEquipmentUpdateMessage serializes the world-broadcast
// COACH_EQUIPMENT_UPDATE_MESSAGE, see CoachEquipmentUpdateMessage.java.
func buildCoachEquipmentUpdateMessage(coachID uint, equipped []domain.CoachCard) protocol.OutboundFrame {
	w := protocol.NewWriter(10 + len(equipped)*15)
	w.PutInt64(int64(coachID)).PutUint16(uint16(len(equipped) * 15))
	for _, c := range equipped {
		w.PutInt16(wireSlotForStoredPos(c.Pos)).PutInt32(c.TemplateID).PutInt64(int64(c.ID)).PutByte(c.Flag)
	}
	return protocol.OutboundFrame{Opcode: protocol.SendCoachEquipmentUpdateMessage, Payload: w.Bytes()}
}
