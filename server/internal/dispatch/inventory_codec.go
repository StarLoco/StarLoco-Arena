package dispatch

import (
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// Fighter spell/equipment inventory wire format.
//
// The client serializes a fighter's spell inventory and equipment
// inventory using the shared ArrayInventory format (client
// baseImpl/.../inventory/ArrayInventory.serialize): a flat concatenation,
// one entry per item, of:
//
//	short pos      // slot index within the inventory
//	int32 id       // the spell / fighter-card reference (template) id
//	               // (AbstractSpell.serialize / AbstractFighterCard.serialize
//	               //  each write exactly putInt(id))
//
// i.e. 6 bytes per item, with NO leading count -- the reader consumes
// pairs until the buffer is exhausted. The previous implementation wrongly
// treated these blobs as a flat array of int32 ids (4 bytes each, no pos),
// which corrupted both spell and equipment round-trips -- equipment most
// visibly, because it silently produced ids that failed the
// known-fighter-card filter and got dropped.
//
// These helpers parse and build that exact format.

// parseInventoryIDs reads an ArrayInventory blob ([short pos][int32 id]
// pairs) and returns just the ids, in wire order. Trailing/odd bytes are
// ignored defensively.
//
// NOTE: this format is only correct for the equipment/card inventory blob.
// The spell inventory blob is a flat int32[] (no leading pos) -- see
// parseSpellIDs/buildSpellBlob below. Using this function for spells
// misaligns the reader and silently corrupts every spell id.
func parseInventoryIDs(blob []byte) []int32 {
	r := protocol.NewReader(blob)
	ids := make([]int32, 0, len(blob)/6)
	for r.Remaining() >= 6 {
		_ = r.Uint16() // pos, not persisted server-side (we re-assign on write)
		ids = append(ids, r.Int32())
	}
	return ids
}

// buildInventoryBlob serializes a list of equipment/card ids into the
// ArrayInventory format ([short pos][int32 id] pairs).
//
// The position is NOT cosmetic/sequential: the real client's
// ArrayInventory<FighterCard> uses a per-type content checker
// (AbstractFighterCardInventoryChecker.canAddItem, see
// gamedata.FighterCardInventoryPosition's doc comment) that rejects any
// entry whose position doesn't equal that item's fixed equipment-category
// slot (weapon=0, pet=1, cloak=2, hat=3, dofus=4). store looks up each id's
// real category; ids the store doesn't recognize (already should have been
// filtered out by filterKnownObjects before reaching here) are defensively
// skipped rather than assigned a guessed/incorrect position.
func buildInventoryBlob(store *gamedata.Store, ids []int32) []byte {
	w := protocol.NewWriter(len(ids) * 6)
	for _, id := range ids {
		card, ok := store.FighterCards.Get(id)
		if !ok {
			continue
		}
		pos, ok := gamedata.FighterCardInventoryPosition(card.Type)
		if !ok {
			continue
		}
		w.PutInt16(pos).PutInt32(id)
	}
	return w.Bytes()
}

// parseSpellIDs reads a fighter's spell inventory blob, which the real
// client serializes via StackInventory<Spell>(serializeQuantity=false) as a
// flat concatenation of `int32 spellId` (4 bytes/entry, NO leading short
// pos, NO trailing quantity -- see docs/opcodes/06-fighter-team.md). This is
// a DIFFERENT format from the equipment/card blob handled by
// parseInventoryIDs. Trailing bytes that don't form a full 4-byte entry are
// ignored defensively.
func parseSpellIDs(blob []byte) []int32 {
	r := protocol.NewReader(blob)
	ids := make([]int32, 0, len(blob)/4)
	for r.Remaining() >= 4 {
		ids = append(ids, r.Int32())
	}
	return ids
}

// buildSpellBlob serializes a list of spell ids into the flat int32[]
// format the real client expects for the spell inventory (see
// parseSpellIDs). Do not use for equipment/cards -- see buildInventoryBlob.
func buildSpellBlob(ids []int32) []byte {
	w := protocol.NewWriter(len(ids) * 4)
	for _, id := range ids {
		w.PutInt32(id)
	}
	return w.Bytes()
}
