package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const opEquipmentUpdate = 5201

// TestEquippingSplitsTheStack covers the inventory desync: Pos lives on the STACK
// row, so equipping one of five copies used to set Pos on the whole row - all
// five vanished from the inventory push, became untradeable and unfusable, and
// counted as ONE for set bonuses.
func TestEquippingSplitsTheStack(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "stacksplit", "StackSplit")
	c.DrainReceived(200 * time.Millisecond)

	// Give the coach a stack of 5 of one card.
	const tmpl = int32(4242)
	if err := st.DB().Create(&domain.CoachCard{
		CoachID: uint(coachID), TemplateID: tmpl, Quantity: 5, Pos: 0,
	}).Error; err != nil {
		t.Fatalf("seed stack: %v", err)
	}

	// Equip that template into slot 1.
	w := testclient.NewW()
	for i := 0; i < 14; i++ {
		if i == 0 {
			w = w.I32(tmpl)
		} else {
			w = w.I32(0)
		}
	}
	_ = c.Send(3, opEquipmentUpdate, w.Bytes())
	c.DrainReceived(500 * time.Millisecond)

	var rows []domain.CoachCard
	if err := st.DB().Where("coach_id = ? AND template_id = ?", coachID, tmpl).
		Find(&rows).Error; err != nil {
		t.Fatalf("reload inventory: %v", err)
	}

	var equipped, unequipped int16
	for _, r := range rows {
		if r.Pos == 0 {
			unequipped += r.Quantity
		} else {
			equipped += r.Quantity
		}
	}
	if equipped != 1 {
		t.Errorf("equipped quantity = %d, want exactly 1 - equipping must split "+
			"one unit off the stack, not move the whole row", equipped)
	}
	if unequipped != 4 {
		t.Errorf("unequipped quantity = %d, want 4 - the other copies must stay "+
			"visible and tradeable", unequipped)
	}
	if total := equipped + unequipped; total != 5 {
		t.Errorf("total quantity %d, want 5 - cards were created or destroyed", total)
	}
}

// TestUnequippingMergesTheStackBack pins the other half: the split unit must
// return to the stack rather than leaving a permanent second pos=0 row, which is
// how duplicate rows accumulated (BuyCards stacks only onto pos = 0).
func TestUnequippingMergesTheStackBack(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "stackmerge", "StackMerge")
	c.DrainReceived(200 * time.Millisecond)

	const tmpl = int32(4243)
	if err := st.DB().Create(&domain.CoachCard{
		CoachID: uint(coachID), TemplateID: tmpl, Quantity: 3, Pos: 0,
	}).Error; err != nil {
		t.Fatalf("seed stack: %v", err)
	}

	equip := testclient.NewW()
	for i := 0; i < 14; i++ {
		if i == 0 {
			equip = equip.I32(tmpl)
		} else {
			equip = equip.I32(0)
		}
	}
	_ = c.Send(3, opEquipmentUpdate, equip.Bytes())
	c.DrainReceived(400 * time.Millisecond)

	// Now unequip everything.
	empty := testclient.NewW()
	for i := 0; i < 14; i++ {
		empty = empty.I32(0)
	}
	_ = c.Send(3, opEquipmentUpdate, empty.Bytes())
	c.DrainReceived(400 * time.Millisecond)

	var rows []domain.CoachCard
	if err := st.DB().Where("coach_id = ? AND template_id = ?", coachID, tmpl).
		Find(&rows).Error; err != nil {
		t.Fatalf("reload inventory: %v", err)
	}
	if len(rows) != 1 {
		t.Errorf("template has %d rows after unequip, want 1 - fragmented stacks "+
			"are how duplicate pos=0 rows accumulated", len(rows))
	}
	var total int16
	for _, r := range rows {
		total += r.Quantity
		if r.Pos != 0 {
			t.Errorf("row still equipped at pos %d after unequip-all", r.Pos)
		}
	}
	if total != 3 {
		t.Errorf("total quantity %d, want 3", total)
	}
}
