package dispatch

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// newTestFighterCardStore builds a *gamedata.Store whose FighterCards
// repository is seeded with one card per equipment category (weapon, pet,
// cloak, hat, dofus), so tests can exercise buildInventoryBlob's
// type->position lookup without touching real data files.
func newTestFighterCardStore(cards map[int32]gamedata.FighterCardTemplate) *gamedata.Store {
	return &gamedata.Store{
		FighterCards: gamedata.NewRepository(func() (map[int32]gamedata.FighterCardTemplate, error) {
			return cards, nil
		}),
	}
}

func TestBuildInventoryBlobFormat(t *testing.T) {
	store := newTestFighterCardStore(map[int32]gamedata.FighterCardTemplate{
		100: {ID: 100, Type: gamedata.FighterCardTypeWeapon},
		200: {ID: 200, Type: gamedata.FighterCardTypePet},
		300: {ID: 300, Type: gamedata.FighterCardTypeCloak},
	})
	blob := buildInventoryBlob(store, []int32{100, 200, 300})

	// Expect 3 entries * 6 bytes ([short pos][int32 id]) = 18 bytes.
	if len(blob) != 18 {
		t.Fatalf("blob len = %d, want 18", len(blob))
	}

	r := protocol.NewReader(blob)
	// Position must equal each item's equipment-category slot (weapon=0,
	// pet=1, cloak=2), NOT its sequential index in the input slice.
	for _, want := range []struct {
		id  int32
		pos int16
	}{{100, 0}, {200, 1}, {300, 2}} {
		pos := r.Int16()
		id := r.Int32()
		if pos != want.pos {
			t.Errorf("id %d: pos = %d, want %d", id, pos, want.pos)
		}
		if id != want.id {
			t.Errorf("id = %d, want %d", id, want.id)
		}
	}
}

// TestBuildInventoryBlobAssignsPositionByType is the regression guard for
// the "hat/cape/pet/dofus don't persist" bug: the real client's
// ArrayInventory<FighterCard> rejects any entry whose position doesn't
// match that item's fixed FighterCardType.getInventoryPosition() slot.
// Equipping items out of weapon-first order must NOT change their emitted
// wire position.
func TestBuildInventoryBlobAssignsPositionByType(t *testing.T) {
	store := newTestFighterCardStore(map[int32]gamedata.FighterCardTemplate{
		108: {ID: 108, Type: gamedata.FighterCardTypeHat},
		9:   {ID: 9, Type: gamedata.FighterCardTypeWeapon},
		118: {ID: 118, Type: gamedata.FighterCardTypeDofus},
		88:  {ID: 88, Type: gamedata.FighterCardTypePet},
		96:  {ID: 96, Type: gamedata.FighterCardTypeCloak},
	})
	// Deliberately NOT weapon-first, to catch any regression back to
	// sequential-index positions.
	blob := buildInventoryBlob(store, []int32{108, 9, 118, 88, 96})

	r := protocol.NewReader(blob)
	wantByID := map[int32]int16{108: 3, 9: 0, 118: 4, 88: 1, 96: 2}
	seen := map[int32]bool{}
	for r.Remaining() >= 6 {
		pos := r.Int16()
		id := r.Int32()
		if want := wantByID[id]; pos != want {
			t.Errorf("id %d: pos = %d, want %d (its fixed equipment slot)", id, pos, want)
		}
		seen[id] = true
	}
	for id := range wantByID {
		if !seen[id] {
			t.Errorf("id %d missing from blob", id)
		}
	}
}

func TestParseInventoryIDsRoundTrip(t *testing.T) {
	store := newTestFighterCardStore(map[int32]gamedata.FighterCardTemplate{
		9:   {ID: 9, Type: gamedata.FighterCardTypeWeapon},
		23:  {ID: 23, Type: gamedata.FighterCardTypePet},
		35:  {ID: 35, Type: gamedata.FighterCardTypeCloak},
		133: {ID: 133, Type: gamedata.FighterCardTypeHat},
	})
	original := []int32{9, 23, 35, 133}
	blob := buildInventoryBlob(store, original)

	got := parseInventoryIDs(blob)
	if len(got) != len(original) {
		t.Fatalf("parsed %d ids, want %d", len(got), len(original))
	}
	for i := range original {
		if got[i] != original[i] {
			t.Errorf("id[%d] = %d, want %d", i, got[i], original[i])
		}
	}
}

func TestParseInventoryIDsIgnoresPositionValues(t *testing.T) {
	// Build a blob with non-sequential positions (as the client may send
	// when items sit in arbitrary inventory slots) and confirm the ids are
	// still extracted correctly regardless of position.
	w := protocol.NewWriter(0)
	w.PutUint16(5).PutInt32(111)
	w.PutUint16(2).PutInt32(222)
	w.PutUint16(9).PutInt32(333)

	got := parseInventoryIDs(w.Bytes())
	want := []int32{111, 222, 333}
	if len(got) != len(want) {
		t.Fatalf("parsed %d ids, want %d", len(got), len(want))
	}
	for i := range want {
		if got[i] != want[i] {
			t.Errorf("id[%d] = %d, want %d", i, got[i], want[i])
		}
	}
}

func TestParseInventoryIDsEmpty(t *testing.T) {
	if got := parseInventoryIDs(nil); len(got) != 0 {
		t.Errorf("empty blob should parse to no ids, got %v", got)
	}
	if got := parseInventoryIDs([]byte{}); len(got) != 0 {
		t.Errorf("empty blob should parse to no ids, got %v", got)
	}
}

func TestParseInventoryIDsIgnoresTrailingPartialEntry(t *testing.T) {
	// A well-formed entry (6 bytes) followed by 3 stray bytes: the stray
	// bytes must be ignored, not cause a bad read.
	w := protocol.NewWriter(0)
	w.PutUint16(0).PutInt32(42)
	w.PutBytes([]byte{0x01, 0x02, 0x03})

	got := parseInventoryIDs(w.Bytes())
	if len(got) != 1 || got[0] != 42 {
		t.Errorf("got %v, want [42]", got)
	}
}

// --- Spell blob (4-byte int32[] format) -------------------------------
//
// These pin the SPELL inventory wire format and its distinction from the
// 6-byte equipment/card format -- the "6011 spell-blob entry-size" item
// flagged in FEATURES-STATUS.md §5. Verified against the decompiled
// reference: the client serializes spells via StackInventory<Spell> with
// serializeQuantity=false (StackInventory.serialize writes only
// item.serialize() = putInt(id), NO pos, NO quantity -> 4 bytes/entry),
// whereas equipment uses ArrayInventory (putShort(pos)+putInt(id) ->
// 6 bytes/entry). So parseSpellIDs MUST use a 4-byte stride, not 6.

func TestBuildSpellBlobIsFlatInt32Array(t *testing.T) {
	blob := buildSpellBlob([]int32{11, 22, 33})
	// 3 entries * 4 bytes (int32 id, no pos, no quantity) = 12 bytes.
	if len(blob) != 12 {
		t.Fatalf("spell blob len = %d, want 12 (3 * 4 bytes); a 6-byte stride would be 18 and indicates the wrong (ArrayInventory) format", len(blob))
	}
	r := protocol.NewReader(blob)
	for _, want := range []int32{11, 22, 33} {
		if got := r.Int32(); got != want {
			t.Errorf("spell id = %d, want %d", got, want)
		}
	}
}

func TestParseSpellIDsRoundTrip(t *testing.T) {
	original := []int32{140, 169, 209, 1035}
	got := parseSpellIDs(buildSpellBlob(original))
	if len(got) != len(original) {
		t.Fatalf("parsed %d spell ids, want %d", len(got), len(original))
	}
	for i := range original {
		if got[i] != original[i] {
			t.Errorf("spell id[%d] = %d, want %d", i, got[i], original[i])
		}
	}
}

// TestParseSpellIDsRejectsCardFormatMisread is the regression guard: if a
// spell blob were (wrongly) parsed with the 6-byte card stride, the ids
// would be corrupted. This feeds a real 4-byte spell blob and confirms the
// 4-byte parser recovers the exact ids -- and that the same bytes read with
// the 6-byte card parser would NOT (proving the stride matters).
func TestParseSpellIDsRejectsCardFormatMisread(t *testing.T) {
	blob := buildSpellBlob([]int32{100, 200, 300})

	spellIDs := parseSpellIDs(blob)
	if len(spellIDs) != 3 || spellIDs[0] != 100 || spellIDs[1] != 200 || spellIDs[2] != 300 {
		t.Fatalf("4-byte parse of spell blob = %v, want [100 200 300]", spellIDs)
	}
	// The SAME bytes read as 6-byte card entries misalign: 12 bytes / 6 = 2
	// entries, with ids stitched from the wrong offsets -- demonstrating why
	// the spell path must not use parseInventoryIDs.
	asCards := parseInventoryIDs(blob)
	if len(asCards) == 3 && asCards[0] == 100 && asCards[1] == 200 && asCards[2] == 300 {
		t.Error("6-byte card parse unexpectedly recovered the spell ids; the two formats would be indistinguishable (they must not be)")
	}
}

func TestParseSpellIDsIgnoresTrailingPartialEntry(t *testing.T) {
	// 2 full int32 entries + 2 stray bytes -> the stray bytes are ignored.
	w := protocol.NewWriter(0)
	w.PutInt32(7).PutInt32(8)
	w.PutBytes([]byte{0xAB, 0xCD})
	got := parseSpellIDs(w.Bytes())
	if len(got) != 2 || got[0] != 7 || got[1] != 8 {
		t.Errorf("got %v, want [7 8]", got)
	}
}

func TestParseSpellIDsEmpty(t *testing.T) {
	if got := parseSpellIDs(nil); len(got) != 0 {
		t.Errorf("empty spell blob should parse to no ids, got %v", got)
	}
}
