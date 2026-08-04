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

// TestFighterEquipLoadout: equipping a fighter's cards + spells via 6011 is
// acked with 6010 (result 0) echoing the stored loadout, and the loadout
// persists (survives a relogin).
func TestFighterEquipLoadout(t *testing.T) {
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "load_a", "LoadA")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	fighterID := createFighter(t, a, st, uint(aID), "Champ", 5)

	// Build 6011: [i64 fighterId][i16 teamId][i16 lenCards][cards][i16 lenSpells][spells].
	// cards (Oh) = [i32 cardId]* ; spells (Oi) = [i16 slot][i32 spellId]*.
	cards := testclient.NewW().I32(101).I32(102).I32(103).Bytes()
	spells := testclient.NewW().U16(0).I32(555).U16(1).I32(556).Bytes()
	req := testclient.NewW().
		I64(int64(fighterID)).
		U16(0). // teamId
		U16(uint16(len(cards))).Raw(cards).
		U16(uint16(len(spells))).Raw(spells).
		Bytes()
	_ = a.Send(2, testclient.OpUpdateFighterInventory, req)

	// 6010 reply: [i64 fighterId][i8 result][i16 lenCards][cards][i16 lenSpells][spells].
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
	// Cards echo: [i32]* — expect 3 ids.
	cl := int(r.U16())
	if cl != 12 { // 3 * 4 bytes
		t.Errorf("cards blob len = %d, want 12", cl)
	}
	cr := testclient.NewR(r.RawN(cl))
	for _, want := range []int32{101, 102, 103} {
		if got := cr.I32(); got != want {
			t.Errorf("card = %d, want %d", got, want)
		}
	}
	// Spells echo: [i16 slot][i32 id]* — expect 2.
	sl := int(r.U16())
	if sl != 12 { // 2 * 6 bytes
		t.Errorf("spells blob len = %d, want 12", sl)
	}
	sr := testclient.NewR(r.RawN(sl))
	if slot, id := sr.U16(), sr.I32(); slot != 0 || id != 555 {
		t.Errorf("spell 0 = slot %d id %d, want slot 0 id 555", slot, id)
	}
	if slot, id := sr.U16(), sr.I32(); slot != 1 || id != 556 {
		t.Errorf("spell 1 = slot %d id %d, want slot 1 id 556", slot, id)
	}

	// Verify persisted in the DB.
	time.Sleep(150 * time.Millisecond)
	fr, err := st.Fighters.Get(fighterID)
	if err != nil {
		t.Fatalf("get fighter: %v", err)
	}
	if len(fr.Objects) != 3 {
		t.Errorf("persisted cards = %d, want 3", len(fr.Objects))
	}
	if len(fr.Spells) != 2 {
		t.Errorf("persisted spells = %d, want 2", len(fr.Spells))
	}
	// Spell slots must round-trip.
	slots := map[int16]int32{}
	for _, sp := range fr.Spells {
		slots[sp.Slot] = sp.SpellID
	}
	if slots[0] != 555 || slots[1] != 556 {
		t.Errorf("persisted spell slots = %+v, want {0:555,1:556}", slots)
	}
}

// TestFighterLoadoutCap: card/spell blobs beyond the slot caps (6 cards / 5
// spells) are truncated.
func TestFighterLoadoutCap(t *testing.T) {
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "load_b", "LoadB")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	fighterID := createFighter(t, a, st, uint(aID), "Capped", 5)

	// 8 cards (>6) and 7 spells (>5).
	cw := testclient.NewW()
	for i := 0; i < 8; i++ {
		cw.I32(int32(200 + i))
	}
	sw := testclient.NewW()
	for i := 0; i < 7; i++ {
		sw.U16(uint16(i)).I32(int32(600 + i))
	}
	cards, spells := cw.Bytes(), sw.Bytes()
	req := testclient.NewW().
		I64(int64(fighterID)).U16(0).
		U16(uint16(len(cards))).Raw(cards).
		U16(uint16(len(spells))).Raw(spells).
		Bytes()
	_ = a.Send(2, testclient.OpUpdateFighterInventory, req)
	if _, _, err := a.WaitFor(testclient.OpUpdatedFighterInventory, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no 6010: %v", err)
	}

	time.Sleep(150 * time.Millisecond)
	fr, _ := st.Fighters.Get(fighterID)
	if len(fr.Objects) != 6 {
		t.Errorf("cards capped to %d, want 6", len(fr.Objects))
	}
	if len(fr.Spells) != 5 {
		t.Errorf("spells capped to %d, want 5", len(fr.Spells))
	}
}
