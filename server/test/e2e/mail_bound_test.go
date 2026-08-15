package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/game"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// Attaching a card to mail used to be gated on a per-card "locked" bit that
// nothing ever set, so the gate never fired (B-094). The real rule is the card
// TEMPLATE's Bound flag, which is what the client checks (ay.java reads tp()).
//
// The distinction that matters, and the reason this is an end-to-end test
// rather than another unit test of the predicate: the client gates mail on
// Bound *alone*. An Undestructible card cannot be destroyed or sold but CAN be
// posted, so using the broader "is it tradable" check here would silently
// refuse a card the real client mails happily. Only exercising the handler can
// tell those two apart.
func TestMailRefusesBoundCardsButAllowsUndestructible(t *testing.T) {
	const (
		plainCard          int32 = 6001
		boundCard          int32 = 6002
		undestructibleCard int32 = 6003
	)

	st, addr := testServerWithDeps(t, func(d *game.Deps) {
		d.Cards = gamedata.NewCards(
			&gamedata.CoachCard{ID: plainCard},
			&gamedata.CoachCard{ID: boundCard, Bound: true},
			&gamedata.CoachCard{ID: undestructibleCard, Undestructible: true},
		)
	})

	a, aID := dialLogin(t, addr, "sender", "Sender")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "receiver", "Receiver")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	// Give the sender exactly one of each. The starter-card grant runs at login
	// and draws from whatever catalogue is configured, so these templates may
	// already be present; seeding on top of that would leave two rows per
	// template and the attach would silently consume the other one.
	for _, id := range []int32{plainCard, boundCard, undestructibleCard} {
		if err := st.DB().Where("coach_id = ? AND template_id = ?", uint(aID), id).
			Delete(&domain.CoachCard{}).Error; err != nil {
			t.Fatalf("clear card %d: %v", id, err)
		}
		if err := st.DB().Create(&domain.CoachCard{
			CoachID: uint(aID), TemplateID: id, Quantity: 1,
		}).Error; err != nil {
			t.Fatalf("seed card %d: %v", id, err)
		}
	}

	_ = a.Send(3, testclient.OpMailSend, mailRecord(bID, "Receiver", "Parcel", "Take these",
		[]int32{plainCard, boundCard, undestructibleCard}))
	if _, _, err := a.WaitFor(testclient.OpMailSendResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("send result: %v", err)
	}

	// What actually left the sender's inventory is the ground truth.
	owned := func(tmpl int32) int64 {
		var n int64
		st.DB().Model(&domain.CoachCard{}).
			Where("coach_id = ? AND template_id = ?", uint(aID), tmpl).Count(&n)
		return n
	}

	if owned(plainCard) != 0 {
		t.Error("an ordinary card was not attached")
	}
	if owned(boundCard) != 1 {
		t.Error("a Bound card was attached to mail — the client refuses to send one " +
			"(\"On ne peut pas envoyer de kard liee par mail\")")
	}
	if owned(undestructibleCard) != 0 {
		t.Error("an Undestructible card was refused; the client gates mail on Bound " +
			"alone, so this card should have been posted")
	}
}
