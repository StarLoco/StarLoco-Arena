package dispatch

// Equipment slot encoding.
//
// The client's coach-equipment protocol addresses 14 fixed equipment slots
// by a zero-based index (0..13) -- see CoachEquipmentUpdateRequestMessage
// (112 bytes = 14 longs) and CoachEquipmentType's position values, which
// start at 0.
//
// Internally, however, a CoachCard's Pos == 0 is the sentinel for "sitting
// in the inventory, not equipped" (used everywhere as the equipped test,
// e.g. GetEquippedCards' `pos != 0`). If we stored an equipped card at
// wire slot 0 directly as Pos == 0, it would be indistinguishable from an
// unequipped card -- exactly the bug that made equipping the first slot
// silently fail.
//
// To resolve this we store equipped cards with Pos == wireSlot + 1, so
// every equipped card has Pos >= 1 and the `pos != 0` convention stays
// correct for all 14 slots. The +1/-1 translation happens only at the
// protocol boundary, via these two helpers; the DB and all in-memory
// equipped-vs-inventory logic work purely in terms of the 1-based Pos.
//
// (The legacy Java server stored Pos == wireSlot directly and thus shares
// the latent "slot 0 looks unequipped" ambiguity; this port deliberately
// fixes it, which is safe because Pos is never sent to the client raw --
// it's always translated back to the zero-based wire slot on the way out.)

// storedPosForWireSlot converts a zero-based wire equipment slot (0..13)
// to the 1-based Pos stored on a CoachCard.
func storedPosForWireSlot(wireSlot int16) int16 {
	return wireSlot + 1
}

// wireSlotForStoredPos converts a 1-based stored CoachCard.Pos back to the
// zero-based wire equipment slot the client expects.
func wireSlotForStoredPos(pos int16) int16 {
	return pos - 1
}
