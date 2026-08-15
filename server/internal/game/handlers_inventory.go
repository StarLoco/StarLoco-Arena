package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerInventoryHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpCoachInventoryUpdateRequest, handleInventoryRequest)
	r.Register(protocol.OpCoachEquipmentUpdateRequest, handleEquipmentRequest)
}

// grantStarterCards gives a brand-new coach a handful of cards so the inventory
// UI isn't empty. Picks the lowest-id templates from gamedata (deterministic).
func (s *Session) grantStarterCards(coach *domain.Coach) {
	if s.deps.Cards == nil || len(coach.Inventory) > 0 {
		return
	}
	// Take up to 10 lowest card ids.
	ids := make([]int32, 0, s.deps.Cards.Len())
	for id := range s.deps.Cards.All() {
		ids = append(ids, id)
	}
	sortInt32(ids)
	if len(ids) > 10 {
		ids = ids[:10]
	}
	db := s.deps.Store.DB()
	for _, id := range ids {
		card := domain.CoachCard{CoachID: coach.ID, TemplateID: id, Quantity: 1}
		db.Create(&card)
		coach.Inventory = append(coach.Inventory, card)
	}
	s.log.Info("granted starter cards", "count", len(ids), "coach", coach.Name)
}

// pushInventory sends the coach's full inventory as a CoachInventoryUpdate
// (5200): section 3 (updated inventory) lists each unequipped card as
// {i32 referenceCardId, i16 quantity}.
func (s *Session) pushInventory(coach *domain.Coach) error {
	w := protocol.NewWriter()
	w.U16(0) // section 1: added-equip (none)
	w.U16(0) // section 2: removed shorts (none)

	// section 3: updated inventory (unequipped cards)
	inv := make([]domain.CoachCard, 0, len(coach.Inventory))
	for _, c := range coach.Inventory {
		if c.Pos == 0 {
			inv = append(inv, c)
		}
	}
	w.U16(uint16(len(inv)))
	for _, c := range inv {
		w.I32(c.TemplateID) // card wy_2 = [i32 referenceCardId]
		w.U16(uint16(c.Quantity))
	}

	w.U16(0) // section 4: ints (none)

	frame, err := protocol.EncodeS2C(protocol.OpCoachInventoryUpdate, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

// handleInventoryRequest processes CoachInventoryUpdateRequest(5203):
// [u16 count] + count×i64 uid, and answers by re-pushing the inventory.
//
// It does NOT act on the uids, and that is a protocol limit rather than an
// unfinished feature — worth stating, because "implement the destructive ops"
// looks like a small task until you ask what the uids identify.
//
// What the message means: `sj_1.yG` builds it from the cards that were in the
// client's reference set but are no longer in its current inventory view, and
// sends it right after the 5201 equipment layout. So 5203 is "these cards are
// gone from my inventory" — a REMOVAL notice, not a lock. (Our opcode comment
// used to say "remove/lock"; there is no lock here and no action discriminator
// in the payload at all.)
//
// Why the uids are unusable: the client's card object gets its unique id in
// `eb_1.b(ByteBuffer)`, which reads ONLY the i32 reference-card id off the wire
// and then assigns `this.aFL = uq_1.ahR()` — a client-local monotonic counter
// (`bRK + (bRI & 0xFFFFFF)`, incrementing). The server never sends a per-card
// identity and never sees that number, so an incoming uid cannot be resolved to
// a CoachCard row. Deleting "the card the player probably meant" would be
// guessing at destructive removals against bound and undestructible cards.
//
// Making this work means giving inventory cards a server-assigned identity on
// the wire first (section 3 of the 5200 push currently carries only
// {i32 templateId, u16 quantity}), which is a wire-format change, not a handler
// change.
func handleInventoryRequest(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	return s.pushInventory(s.Coach)
}

// handleEquipmentRequest processes CoachEquipmentUpdateRequest(5201): 14×i32
// slot references (0 = empty). Persists the equipped layout and re-pushes.
func handleEquipmentRequest(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	var slots [14]int32
	for i := range slots {
		v, err := r.I32()
		if err != nil {
			break
		}
		slots[i] = v
	}
	s.applyEquipment(s.Coach, slots)
	return s.pushInventory(s.Coach)
}

// applyEquipment sets Pos for each inventory card according to the 14 wire
// slots (slot value = card TemplateID; Pos = slotIndex+1; 0 = unequipped).
func (s *Session) applyEquipment(coach *domain.Coach, slots [14]int32) {
	db := s.deps.Store.DB()
	// Reset all to unequipped first.
	for i := range coach.Inventory {
		coach.Inventory[i].Pos = 0
	}
	for slotIdx, templateID := range slots {
		if templateID == 0 {
			continue
		}
		for i := range coach.Inventory {
			if coach.Inventory[i].TemplateID == templateID && coach.Inventory[i].Pos == 0 {
				coach.Inventory[i].Pos = int16(slotIdx + 1)
				break
			}
		}
	}
	for i := range coach.Inventory {
		db.Model(&domain.CoachCard{}).Where("id = ?", coach.Inventory[i].ID).
			Update("pos", coach.Inventory[i].Pos)
	}
}

// sortInt32 sorts a slice of int32 ascending (small, no import needed).
func sortInt32(a []int32) {
	for i := 1; i < len(a); i++ {
		for j := i; j > 0 && a[j-1] > a[j]; j-- {
			a[j-1], a[j] = a[j], a[j-1]
		}
	}
}
