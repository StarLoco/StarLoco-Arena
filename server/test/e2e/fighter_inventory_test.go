package e2e

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

// Real IDs present in the production data files (verified via the gamedata
// parser). The spells 31, 32, 33 all belong to breed 1 (Feca) -- the breed
// createFighter uses -- because the server now enforces the client's
// SpellInventoryChecker rule that a fighter may only equip spells of its
// own breed (see validateFighterSpells). Fighter cards include 9 (weapon,
// type 1), 93 (pet, type 2), 96 (cloak, type 3), 133 (hat, type 4), 118
// (dofus, type 5). Deliberately covering all 5 equipment categories (not
// weapon-first) is the regression guard for the "only weapon persists,
// pet/cloak/hat/dofus silently vanish" bug: the real client's
// ArrayInventory<FighterCard> requires each entry's wire position to equal
// that item's fixed FighterCardType.getInventoryPosition() slot
// (weapon=0, pet=1, cloak=2, hat=3, dofus=4), not a sequential array index.
var (
	realSpellIDs  = []int32{31, 32, 33}          // all breed 1 (Feca) spells
	realObjectIDs = []int32{133, 118, 9, 96, 93} // hat, dofus, weapon, cloak, pet -- intentionally out of category order
)

// TestE2E_FighterInventoryUpdateRoundTrip is the regression guard for the
// reported "fighter equipment isn't saved" bug. It creates a fighter, sets
// a spell + equipment loadout via UPDATE_FIGHTER_INVENTORY, then reconnects
// and reads the fighter list back, asserting BOTH spells and equipment
// survive.
func TestE2E_FighterInventoryUpdateRoundTrip(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")

	c := dialTestClient(t, addr)
	c.mustLogin("alice", "pw", "Alice")

	fighterID := createFighter(t, c, "Kit")

	// UPDATE_FIGHTER_INVENTORY_REQUEST (6011):
	// long(fighterId) short(spellBlobLen) spellBlob short(objBlobLen) objBlob
	sBlob := spellBlob(realSpellIDs...)
	objBlob := inventoryBlob(realObjectIDs...)
	payload := putInt64(fighterID)
	payload = append(payload, putInt16(int16(len(sBlob)))...)
	payload = append(payload, sBlob...)
	payload = append(payload, putInt16(int16(len(objBlob)))...)
	payload = append(payload, objBlob...)

	c.send(3, protocol.RecvFighterUpdateInventoryRequest, payload)

	// FIGHTER_UPDATED_INFORMATION_INVENTORY response:
	// long(fighterId) byte(0) short(spellLen) spellBlob short(objLen) objBlob
	resp := c.expectOpcode(protocol.SendFighterUpdatedInformationInventory)
	r := newPayloadReader(resp)
	if got := r.int64(); got != fighterID {
		t.Fatalf("response fighter id = %d, want %d", got, fighterID)
	}
	if got := r.byte_(); got != 0 {
		t.Fatalf("response error code = %d, want 0", got)
	}
	respSpellBlob := readBlob(r)
	respObjBlob := readBlob(r)
	assertIDs(t, "response spells", parseSpellBlob(respSpellBlob), realSpellIDs)
	assertIDs(t, "response objects", parseInventoryBlob(respObjBlob), realObjectIDs)
	assertObjectPositions(t, "response objects", parseInventoryBlobPositions(respObjBlob))

	// Reconnect and read the fighter list -- the loadout must persist.
	c.conn.Close()
	c2 := dialTestClient(t, addr)
	c2.mustLogin("alice", "pw", "Alice")
	c2.send(3, protocol.RecvFighterInformationListRequest, nil)
	listPayload := c2.expectOpcode(protocol.SendFighterInformationList)

	spells, objects, objBlobBack := parseFirstFighterLoadout(t, listPayload)
	assertIDs(t, "reconnect spells", spells, realSpellIDs)
	assertIDs(t, "reconnect objects", objects, realObjectIDs)
	assertObjectPositions(t, "reconnect objects", parseInventoryBlobPositions(objBlobBack))
}

// wantEquipmentPosition maps each real object id used in this test to its
// fixed equipment-category slot, per FighterCardType.getInventoryPosition()
// (weapon=0, pet=1, cloak=2, hat=3, dofus=4): 9=weapon, 93=pet, 96=cloak,
// 133=hat, 118=dofus (see gamedata's TestZZDumpFighterCardsByType-style
// verification against the real data/cards.dat).
var wantEquipmentPosition = map[int32]int16{9: 0, 93: 1, 96: 2, 133: 3, 118: 4}

// assertObjectPositions is the regression guard for the "only weapon
// persists, pet/cloak/hat/dofus silently vanish" bug: it asserts every
// equipped item's wire position equals its fixed equipment-category slot,
// not a sequential array index.
func assertObjectPositions(t *testing.T, label string, gotPositions map[int32]int16) {
	t.Helper()
	for id, wantPos := range wantEquipmentPosition {
		gotPos, ok := gotPositions[id]
		if !ok {
			continue // id-set mismatch already reported by assertIDs
		}
		if gotPos != wantPos {
			t.Errorf("%s: id %d has wire pos %d, want %d (its fixed equipment-category slot -- a real client would reject/drop this item)", label, id, gotPos, wantPos)
		}
	}
}

// readBlob reads a 2-byte-length-prefixed byte blob from a payloadReader.
func readBlob(r *payloadReader) []byte {
	n := int(uint16(r.int16()))
	b := r.buf[r.pos : r.pos+n]
	r.pos += n
	return b
}

// parseFirstFighterLoadout decodes the first fighter entry in a
// FIGHTER_INFORMATION_LIST payload and returns its spell + object IDs.
// Layout per entry: long id, short serializedLen, then the serialized
// FighterInformation blob:
//
//	byte(1) short(budget) byte(breed) byte(nameLen) name byte(sex) byte(skin)
//	short(spellBlobLen) spellBlob short(objBlobLen) objBlob
func parseFirstFighterLoadout(t *testing.T, payload []byte) (spells, objects []int32, objBlob []byte) {
	t.Helper()
	r := newPayloadReader(payload)
	count := r.byte_()
	if count < 1 {
		t.Fatalf("fighter list is empty")
	}
	r.int64() // fighter id
	r.int16() // serialized length (we parse structurally instead)

	r.byte_() // version marker
	r.int16() // budget
	r.byte_() // breed
	nameLen := int(r.byte_())
	r.skip(nameLen) // name
	r.byte_()       // sex
	r.byte_()       // skin

	spells = parseSpellBlob(readBlob(r))
	objBlob = readBlob(r)
	objects = parseInventoryBlob(objBlob)
	return spells, objects, objBlob
}

// assertIDs checks got and want contain the same set of ids, ignoring
// order. Order is not semantically meaningful for spell/equipment
// loadouts: for equipment specifically, each wire entry carries its own
// "pos" field (the item's fixed equipment-category slot -- see
// gamedata.FighterCardInventoryPosition), so array order is not what the
// real client relies on to place an item in the correct slot.
func assertIDs(t *testing.T, label string, got, want []int32) {
	t.Helper()
	if len(got) != len(want) {
		t.Fatalf("%s: got %d ids (%v), want %d (%v)", label, len(got), got, len(want), want)
	}
	gotSet := make(map[int32]int, len(got))
	for _, id := range got {
		gotSet[id]++
	}
	for _, id := range want {
		if gotSet[id] == 0 {
			t.Errorf("%s: missing id %d (full got=%v, want=%v)", label, id, got, want)
			continue
		}
		gotSet[id]--
	}
}
