package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/game"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/store"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// The tome ("grimoire") is every card template a coach has EVER owned. The
// client's set (aez_0.dBd) only ever grows — every use of aQm() is a read or an
// add, and nothing anywhere removes from it — so the server must not derive it
// from the live inventory.
//
// It matters because 24 of the 332 achievements are card-gated.

// tomeServer runs with a catalogue whose only achievement needs card 500.
func tomeServer(t *testing.T) (*store.Store, string) {
	t.Helper()
	return testServerWithDeps(t, func(d *game.Deps) {
		d.Achievements = gamedata.NewAchievements(
			&gamedata.Achievement{ID: 8001, Points: 5, Cards: []int32{500}},
			// Same card, but also a criterion — so it can be made to complete at a
			// moment we choose, in particular AFTER the card is gone.
			&gamedata.Achievement{ID: 8002, Points: 5, Cards: []int32{500},
				Conditions: []gamedata.AchievementCondition{{StatID: 221, Threshold: 1}}},
		)
	})
}

// TestTomeRemembersSoldCards is the load-bearing one: owning a card then losing
// it must not revoke the achievement it completed. A "distinct templates
// currently held" implementation passes every other test here and fails this one.
func TestTomeRemembersSoldCards(t *testing.T) {
	st, addr := tomeServer(t)
	c, coachID := dialLogin(t, addr, "tome_sold", "TomeSold")
	reachWorld(t, c)

	// Grant the card, then relog so the login-time sync folds it into the tome.
	if err := st.DB().Create(&domain.CoachCard{
		CoachID: uint(coachID), TemplateID: 500, Quantity: 1,
	}).Error; err != nil {
		t.Fatalf("grant card: %v", err)
	}
	_ = c.Close()
	c2, _ := dialLogin(t, addr, "tome_sold", "TomeSold")
	if _, _, err := c2.WaitFor(opAchievementUnlocked, testclient.DefaultTimeout); err != nil {
		t.Fatalf("card-gated achievement did not unlock while the card was held: %v", err)
	}

	// Now take the card away entirely, as a sale or a fusion would.
	if err := st.DB().Where("coach_id = ? AND template_id = ?", coachID, 500).
		Delete(&domain.CoachCard{}).Error; err != nil {
		t.Fatalf("remove card: %v", err)
	}

	// The behavioural assertion: 8002 needs the same (now sold) card plus a
	// criterion. Meeting the criterion now must still unlock it, which is only
	// possible if EVALUATION consults the tome rather than the live inventory.
	setCriterion(c2, 221, 1)
	f, _, err := c2.WaitFor(opAchievementUnlocked, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("achievement 8002 did not unlock after the card was sold: "+
			"evaluation is reading the live inventory instead of the tome, so "+
			"selling a card silently revokes credit for having owned it: %v", err)
	}
	if got := testclient.NewR(f.Payload).U16(); got != 8002 {
		t.Errorf("unlocked %d, want 8002", got)
	}

	// And the record itself must have survived.
	tome, err := st.Coaches.TomeCards(uint(coachID))
	if err != nil {
		t.Fatalf("read tome: %v", err)
	}
	var found bool
	for _, id := range tome {
		if id == 500 {
			found = true
		}
	}
	if !found {
		t.Errorf("card 500 left the tome when it left the inventory (tome=%v)", tome)
	}
}

// TestTomeIsNotGrantedForUnownedCards: the tome must not invent membership, or
// every card-gated achievement would unlock for everyone.
func TestTomeIsNotGrantedForUnownedCards(t *testing.T) {
	_, addr := tomeServer(t)
	c, _ := dialLogin(t, addr, "tome_none", "TomeNone")
	reachWorld(t, c)

	if f, _, err := c.WaitFor(opAchievementUnlocked, 900*time.Millisecond); err == nil {
		t.Errorf("achievement %d unlocked without ever owning the card",
			testclient.NewR(f.Payload).U16())
	}
}

// TestTomeReachesTheClient checks the 0x80 descriptor blob actually carries the
// tome. The client computes card-gated progress from its OWN copy (aea_1 reads
// aQm()), so a server-side tome the client never sees would show 0% on rows the
// server considers complete.
func TestTomeReachesTheClient(t *testing.T) {
	st, addr := tomeServer(t)
	c, coachID := dialLogin(t, addr, "tome_wire", "TomeWire")
	reachWorld(t, c)
	if err := st.DB().Create(&domain.CoachCard{
		CoachID: uint(coachID), TemplateID: 500, Quantity: 1,
	}).Error; err != nil {
		t.Fatalf("grant card: %v", err)
	}
	// Relog by hand: the descriptor is built once, at login, and dialLogin's
	// CreateCoach consumes that very frame to read the coach id out of it.
	_ = c.Close()
	c2, err := testclient.Dial(addr)
	if err != nil {
		t.Fatalf("dial: %v", err)
	}
	t.Cleanup(func() { _ = c2.Close() })
	if err := c2.Login("tome_wire", "pw"); err != nil {
		t.Fatalf("login: %v", err)
	}

	f, _, err := c2.WaitFor(testclient.OpCoachInfo, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no CoachInformations(2052): %v", err)
	}
	// The blob is deep inside the descriptor, so rather than re-implement the
	// whole reader, assert the card id appears as a big-endian i32 in it. That is
	// enough to catch "the blob is still hard-coded empty", which is the failure
	// this guards.
	want := []byte{0x00, 0x00, 0x01, 0xF4} // 500
	if !containsBytes(f.Payload, want) {
		t.Errorf("card 500 does not appear in the 2052 descriptor: the tome blob "+
			"is not being sent, so the client will show card-gated achievements "+
			"as incomplete (payload %d bytes)", len(f.Payload))
	}
}

func containsBytes(hay, needle []byte) bool {
	for i := 0; i+len(needle) <= len(hay); i++ {
		match := true
		for j := range needle {
			if hay[i+j] != needle[j] {
				match = false
				break
			}
		}
		if match {
			return true
		}
	}
	return false
}
