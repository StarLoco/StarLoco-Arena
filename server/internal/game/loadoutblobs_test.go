package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestLoadoutBlobOrderMatchesTheClient pins which of 6011's two blobs is which.
//
// `bp_1.encode()` writes `Oh().cd()` then `Oi().cd()`, and on a fighter
// `Oh() -> ajv_2` is the SPELL inventory (flat [i32]) while `Oi() -> en_1` is the
// EQUIPMENT inventory ([i16 slot][i32]). The server read them the other way
// round, and because a flat list and a slotted list happen to sit in that same
// order nothing ever failed to parse — the contents were just transposed, so a
// fighter's spells were stored as its equipment. This test is written against
// the client's byte order rather than the server's previous belief.
func TestLoadoutBlobOrderMatchesTheClient(t *testing.T) {
	// First blob: three spells, flat.
	spells := protocol.NewWriter().I32(101).I32(102).I32(103).Bytes()
	// Second blob: two equipped items at their fixed vi_1 positions (weapon 0,
	// hat 3).
	cards := protocol.NewWriter().U16(0).I32(900).U16(3).I32(901).Bytes()

	gotSpells := decodeLoadoutSpells(spells)
	gotCards := decodeLoadoutCards(cards)

	if len(gotSpells) != 3 {
		t.Fatalf("decoded %d spells from a flat 3-spell blob, want 3", len(gotSpells))
	}
	if gotSpells[0].SpellID != 101 || gotSpells[2].SpellID != 103 {
		t.Errorf("spell ids = %d,%d want 101,103", gotSpells[0].SpellID, gotSpells[2].SpellID)
	}
	if len(gotCards) != 2 {
		t.Fatalf("decoded %d cards from a 2-item blob, want 2", len(gotCards))
	}
	if gotCards[0].Slot != 0 || gotCards[0].TemplateID != 900 {
		t.Errorf("card 0 = slot %d id %d, want slot 0 id 900", gotCards[0].Slot, gotCards[0].TemplateID)
	}
	if gotCards[1].Slot != 3 || gotCards[1].TemplateID != 901 {
		t.Errorf("card 1 = slot %d id %d, want slot 3 id 901", gotCards[1].Slot, gotCards[1].TemplateID)
	}
}

// TestLoadoutBlobsRoundTrip: the 6010 reply echoes the loadout, so encode must be
// the exact inverse of decode. If the two disagree the client silently rebuilds a
// different fighter than the one just saved.
func TestLoadoutBlobsRoundTrip(t *testing.T) {
	spells := []domain.FighterSpell{{SpellID: 7, Slot: 0}, {SpellID: 8, Slot: 1}}
	cards := []domain.FighterObject{{TemplateID: 900, Slot: 0}, {TemplateID: 901, Slot: 4}}

	gotSpells := decodeLoadoutSpells(encodeLoadoutSpells(spells))
	if len(gotSpells) != 2 || gotSpells[0].SpellID != 7 || gotSpells[1].SpellID != 8 {
		t.Errorf("spell round trip = %+v", gotSpells)
	}
	gotCards := decodeLoadoutCards(encodeLoadoutCards(cards))
	if len(gotCards) != 2 || gotCards[0].Slot != 0 || gotCards[1].Slot != 4 ||
		gotCards[1].TemplateID != 901 {
		t.Errorf("card round trip = %+v", gotCards)
	}
}

// TestEquipForWireMatchesTheClientInventory covers the overflow that produced 40
// "position en dehors des limites" rejections per fight: the client's fighter
// inventory is 5 fixed slots, so anything beyond that cannot be stored and must
// not be sent.
func TestEquipForWireMatchesTheClientInventory(t *testing.T) {
	objs := []domain.FighterObject{
		{TemplateID: 122, Slot: 0}, {TemplateID: 95, Slot: 1},
		{TemplateID: 125, Slot: 2}, {TemplateID: 121, Slot: 3},
		{TemplateID: 103, Slot: 4}, {TemplateID: 129, Slot: 5}, // beyond the inventory
		{TemplateID: 102, Slot: 6}, {TemplateID: 156, Slot: 7},
		{TemplateID: 158, Slot: 8}, {TemplateID: 128, Slot: 9},
	}
	got := equipForWire(objs)
	if len(got) != maxFighterEquipSlots {
		t.Fatalf("kept %d items, want %d (the client's en_1 holds no more)", len(got), maxFighterEquipSlots)
	}
	for i, o := range got {
		if o.Slot != int16(i) {
			t.Errorf("item %d has slot %d, want ascending 0..4", i, o.Slot)
		}
		if o.Slot >= maxFighterEquipSlots {
			t.Errorf("slot %d would be refused by the client", o.Slot)
		}
	}
}

// TestEquipForWireKeepsOneItemPerSlot: two items claiming the same position is
// the other way the client's checker refuses a row.
func TestEquipForWireKeepsOneItemPerSlot(t *testing.T) {
	objs := []domain.FighterObject{
		{TemplateID: 1, Slot: 2}, {TemplateID: 2, Slot: 2}, {TemplateID: 3, Slot: 0},
	}
	got := equipForWire(objs)
	if len(got) != 2 {
		t.Fatalf("kept %d items, want 2 (the duplicate slot must be dropped)", len(got))
	}
	if got[0].Slot != 0 || got[1].Slot != 2 || got[1].TemplateID != 1 {
		t.Errorf("got %+v, want slot 0 then the FIRST item in slot 2", got)
	}
}

// TestDecodeLoadoutCardsRejectsUnstorableSlots stops the overflow at ingest too,
// so it is never persisted and re-sent forever.
func TestDecodeLoadoutCardsRejectsUnstorableSlots(t *testing.T) {
	blob := protocol.NewWriter().
		U16(0).I32(900).
		U16(7).I32(901). // no such equipment position
		U16(4).I32(902).
		Bytes()
	got := decodeLoadoutCards(blob)
	if len(got) != 2 {
		t.Fatalf("stored %d items, want 2 (slot 7 is not a real position)", len(got))
	}
	for _, o := range got {
		if o.Slot == 7 {
			t.Error("slot 7 was stored")
		}
	}
}
