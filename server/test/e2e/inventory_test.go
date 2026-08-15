package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// unequippedTemplates parses a CoachInventoryUpdate(5200) payload and returns
// the template ids listed in section 3 (the unequipped inventory).
//
// Layout: [u16 sec1][u16 sec2][u16 count]{[i32 tmpl][u16 qty]}×count [u16 sec4].
func unequippedTemplates(t *testing.T, payload []byte) []int32 {
	t.Helper()
	r := testclient.NewR(payload)
	_ = r.U16() // section 1: added-equip
	_ = r.U16() // section 2: removed shorts
	n := int(r.U16())
	out := make([]int32, 0, n)
	for i := 0; i < n; i++ {
		tmpl := r.I32()
		_ = r.U16() // quantity
		out = append(out, tmpl)
	}
	return out
}

func hasTemplateID(list []int32, tmpl int32) bool {
	for _, v := range list {
		if v == tmpl {
			return true
		}
	}
	return false
}

// TestInventoryRequestReturnsCards: a CoachInventoryUpdateRequest(5203) is
// answered with a CoachInventoryUpdate(5200) listing the coach's unequipped
// cards.
func TestInventoryRequestReturnsCards(t *testing.T) {
	st, addr := testServerWithStore(t)
	// Create the coach, seed a card in the DB, then reconnect so the session
	// loads the inventory fresh (the harness has no gamedata, so no starter
	// cards are granted at creation).
	_, aID := dialLogin(t, addr, "inv_a", "InvA")
	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 7777, Quantity: 3})
	a, _ := dialLogin(t, addr, "inv_a", "InvA")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	// Request inventory: [u16 count][i64 uids...] — server ignores the body.
	_ = a.Send(4, testclient.OpCoachInventoryUpdateRequest, testclient.NewW().U16(0).Bytes())
	f, _, err := a.WaitFor(testclient.OpCoachInventoryUpdate, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no CoachInventoryUpdate: %v", err)
	}
	if !hasTemplateID(unequippedTemplates(t, f.Payload), 7777) {
		t.Error("seeded template 7777 missing from inventory update")
	}
}

// TestEquipMovesCardOutOfInventory: a CoachEquipmentUpdateRequest(5201) that
// slots a template equips it — the card leaves the unequipped list in the
// resulting 5200 and its Pos is persisted.
func TestEquipMovesCardOutOfInventory(t *testing.T) {
	st, addr := testServerWithStore(t)
	_, aID := dialLogin(t, addr, "equip_a", "EquipA")
	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 8888, Quantity: 1})
	a, _ := dialLogin(t, addr, "equip_a", "EquipA")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	// Equip template 8888 into slot 0 (14 i32 slots, rest empty).
	w := testclient.NewW().I32(8888)
	for i := 1; i < 14; i++ {
		w.I32(0)
	}
	_ = a.Send(4, testclient.OpCoachEquipmentUpdateRequest, w.Bytes())

	f, _, err := a.WaitFor(testclient.OpCoachInventoryUpdate, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no CoachInventoryUpdate after equip: %v", err)
	}
	if hasTemplateID(unequippedTemplates(t, f.Payload), 8888) {
		t.Error("equipped template 8888 should no longer be in the unequipped list")
	}

	// Persisted: the card now has a non-zero Pos.
	time.Sleep(100 * time.Millisecond)
	c, err := st.Coaches.Get(uint(aID))
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	var pos int16 = -1
	for _, card := range c.Inventory {
		if card.TemplateID == 8888 {
			pos = card.Pos
		}
	}
	if pos == 0 || pos == -1 {
		t.Errorf("equipped card Pos = %d, want non-zero (persisted equip)", pos)
	}
}
