package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/store"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// createFighter creates a fighter over the wire and returns its DB id.
func createFighter(t *testing.T, a *testclient.Client, st *store.Store, coachID uint, name string, breed uint8) uint {
	t.Helper()
	blob := buildFighterBlob(name, breed)
	req := testclient.NewW().U8(0).U16(0).U16(uint16(len(blob))).Raw(blob).Bytes()
	_ = a.Send(3, testclient.OpFighterCreate, req)
	if _, _, err := a.WaitFor(testclient.OpFighterCreateResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no FighterCreateResult: %v", err)
	}
	fighters, err := st.Fighters.ListByCoach(coachID)
	if err != nil || len(fighters) == 0 {
		t.Fatalf("fighter not created: %v", err)
	}
	return fighters[len(fighters)-1].ID
}

// TestFighterEquipLoadout: equipping a fighter's spells + equipment via 6011 is
// acked with 6010 (result 0) echoing the stored loadout, and it persists.
//
// The blob ORDER here is the client's, not the server's former belief:
// `bp_1.encode()` sends `Oh().cd()` (the ajv_2 SPELL inventory, a flat [i32]
// list) and then `Oi().cd()` (the en_1 EQUIPMENT inventory, [i16 slot][i32]
// pairs). This test used to be written the other way round - it built a flat
// blob, called it "cards", and asserted the server stored it as cards. It passed,
// because the server made exactly the same mistake. Both are fixed together.
func TestFighterEquipLoadout(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "load_a", "LoadA")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	fighterID := createFighter(t, a, st, uint(aID), "Champ", 5)

	// 6011: [i64 fighterId][i16 teamId][i16 lenSpells][spells][i16 lenCards][cards]
	spells := testclient.NewW().I32(101).I32(102).I32(103).Bytes()
	cards := testclient.NewW().U16(0).I32(555).U16(3).I32(556).Bytes()
	req := testclient.NewW().
		I64(int64(fighterID)).
		U16(0). // teamId
		U16(uint16(len(spells))).Raw(spells).
		U16(uint16(len(cards))).Raw(cards).
		Bytes()
	_ = a.Send(2, testclient.OpUpdateFighterInventory, req)

	f, _, err := a.WaitFor(testclient.OpUpdatedFighterInventory, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no UpdatedFighterInventory(6010): %v", err)
	}
	r := testclient.NewR(f.Payload)
	if id := r.I64(); id != int64(fighterID) {
		t.Errorf("6010 fighterId = %d, want %d", id, fighterID)
	}
	if res := r.U8(); res != 0 {
		t.Fatalf("6010 result = %d, want 0", res)
	}
	// Spells echo first, flat.
	sl := int(r.U16())
	if sl != 12 { // 3 * 4 bytes
		t.Errorf("spells blob len = %d, want 12", sl)
	}
	sr := testclient.NewR(r.RawN(sl))
	for _, want := range []int32{101, 102, 103} {
		if got := sr.I32(); got != want {
			t.Errorf("spell = %d, want %d", got, want)
		}
	}
	// Equipment echoes second, with its slots.
	cl := int(r.U16())
	if cl != 12 { // 2 * 6 bytes
		t.Errorf("cards blob len = %d, want 12", cl)
	}
	cr := testclient.NewR(r.RawN(cl))
	if slot, id := cr.U16(), cr.I32(); slot != 0 || id != 555 {
		t.Errorf("card 0 = slot %d id %d, want slot 0 id 555", slot, id)
	}
	if slot, id := cr.U16(), cr.I32(); slot != 3 || id != 556 {
		t.Errorf("card 1 = slot %d id %d, want slot 3 id 556", slot, id)
	}

	// Verify persisted in the DB, on the correct side of the fence.
	time.Sleep(150 * time.Millisecond)
	fr, err := st.Fighters.Get(fighterID)
	if err != nil {
		t.Fatalf("get fighter: %v", err)
	}
	if len(fr.Spells) != 3 {
		t.Errorf("persisted spells = %d, want 3 (the flat blob is SPELLS)", len(fr.Spells))
	}
	if len(fr.Objects) != 2 {
		t.Errorf("persisted equipment = %d, want 2 (the slotted blob is EQUIPMENT)", len(fr.Objects))
	}
	ids := map[int32]bool{}
	for _, sp := range fr.Spells {
		ids[sp.SpellID] = true
	}
	if !ids[101] || !ids[103] {
		t.Errorf("persisted spells = %+v, want 101..103", fr.Spells)
	}
	slots := map[int16]int32{}
	for _, o := range fr.Objects {
		slots[o.Slot] = o.TemplateID
	}
	if slots[0] != 555 || slots[3] != 556 {
		t.Errorf("persisted equipment slots = %+v, want {0:555,3:556}", slots)
	}
}

// TestFighterLoadoutCap: each blob is truncated to what the CLIENT's own
// inventory can hold - `ajv_2(6)` spells and `en_1(...,5,...)` equipment. The
// caps used to be applied to the opposite blob, so a 6th piece of equipment was
// accepted that the client would refuse on arrival.
func TestFighterLoadoutCap(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "load_b", "LoadB")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	fighterID := createFighter(t, a, st, uint(aID), "Capped", 5)

	// 8 spells (>6) and 7 equipment rows (>5, and positions 5/6 do not exist).
	sw := testclient.NewW()
	for i := 0; i < 8; i++ {
		sw.I32(int32(600 + i))
	}
	cw := testclient.NewW()
	for i := 0; i < 7; i++ {
		cw.U16(uint16(i)).I32(int32(200 + i))
	}
	spells, cards := sw.Bytes(), cw.Bytes()
	req := testclient.NewW().
		I64(int64(fighterID)).U16(0).
		U16(uint16(len(spells))).Raw(spells).
		U16(uint16(len(cards))).Raw(cards).
		Bytes()
	_ = a.Send(2, testclient.OpUpdateFighterInventory, req)
	if _, _, err := a.WaitFor(testclient.OpUpdatedFighterInventory, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no 6010: %v", err)
	}

	time.Sleep(150 * time.Millisecond)
	fr, _ := st.Fighters.Get(fighterID)
	if len(fr.Spells) != 6 {
		t.Errorf("spells capped to %d, want 6 (ajv_2 holds 6)", len(fr.Spells))
	}
	if len(fr.Objects) != 5 {
		t.Errorf("equipment capped to %d, want 5 (en_1 holds 5)", len(fr.Objects))
	}
	for _, o := range fr.Objects {
		if o.Slot >= 5 {
			t.Errorf("stored equipment at slot %d, which the client cannot hold", o.Slot)
		}
	}
}
