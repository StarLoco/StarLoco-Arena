package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// firstUnequippedCard returns a coach's first inventory card (unequipped).
func firstUnequippedCard(t *testing.T, s *store.Store, coachID uint) domain.CoachCard {
	t.Helper()
	c, err := s.Coaches.Get(coachID)
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	for _, card := range c.Inventory {
		if card.Pos == 0 && card.Quantity > 0 {
			return card
		}
	}
	t.Fatalf("coach %d has no unequipped card", coachID)
	return domain.CoachCard{}
}

// TestExchangeTransfersCard drives a full trade over the wire: A invites B, B
// accepts, A stakes a card, both set ready, and the card ownership actually
// transfers (verified in the DB).
func TestExchangeTransfersCard(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)

	a, aID := dialLogin(t, addr, "trader_a", "TraderA")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "trader_b", "TraderB")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// Seed A with a tradeable card (the E2E harness has no game data, so no
	// starter cards are granted).
	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 4242, Quantity: 1})

	// A's card to give away.
	giveCard := firstUnequippedCard(t, st, uint(aID))

	// 1. A invites B.
	_ = a.Send(3, testclient.OpExchangeInvite, testclient.NewW().I64(bID).Bytes())
	inv, _, err := b.WaitFor(testclient.OpExchangeInvitationS, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("B never got invite: %v", err)
	}
	exID := testclient.NewR(inv.Payload).I64()

	// 2. B accepts.
	_ = b.Send(3, testclient.OpExchangeAnswer, testclient.NewW().I64(exID).U8(1).Bytes())
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// 3. A stakes the card: [i64 exId][i32 refCardId][i16 qty]. The client has
	_ = a.Send(3, testclient.OpExchangeAddCard,
		testclient.NewW().I64(exID).I32(giveCard.TemplateID).U16(uint16(giveCard.Quantity)).Bytes())
	// Both should see 5109 CardAdded.
	if _, _, err := b.WaitFor(testclient.OpExchangeCardAdded, testclient.DefaultTimeout); err != nil {
		t.Fatalf("B never saw card added: %v", err)
	}
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// 4. Both set ready -> trade commits.
	_ = a.Send(3, testclient.OpExchangeSetReady, testclient.NewW().I64(exID).Bytes())
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)
	_ = b.Send(3, testclient.OpExchangeSetReady, testclient.NewW().I64(exID).Bytes())

	// Both should receive 5111 End with reason 0 (success).
	end, _, err := a.WaitFor(testclient.OpExchangeEnd, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("A never got exchange end: %v", err)
	}
	if reason := testclient.NewR(end.Payload).U8(); reason != 0 {
		t.Fatalf("exchange end reason = %d, want 0 (success)", reason)
	}

	// Give the async commit a moment, then verify the DB: A lost the card's
	// template, B gained it.
	time.Sleep(200 * time.Millisecond)
	if hasTemplate(t, st, uint(aID), giveCard.TemplateID, giveCard.Quantity) {
		t.Errorf("A should have given away template %d", giveCard.TemplateID)
	}
	if !hasTemplate(t, st, uint(bID), giveCard.TemplateID, 1) {
		t.Errorf("B should have received template %d", giveCard.TemplateID)
	}
}

// openExchange invites B from A and has B accept, returning the exchange id.
func openExchange(t *testing.T, a, b *testclient.Client, bID int64) int64 {
	t.Helper()
	_ = a.Send(3, testclient.OpExchangeInvite, testclient.NewW().I64(bID).Bytes())
	inv, _, err := b.WaitFor(testclient.OpExchangeInvitationS, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("B never got invite: %v", err)
	}
	exID := testclient.NewR(inv.Payload).I64()
	_ = b.Send(3, testclient.OpExchangeAnswer, testclient.NewW().I64(exID).U8(1).Bytes())
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)
	return exID
}

// TestExchangeRemoveCard: a staked card can be un-staked; both parties see a
// 5110 CardRemoved and the ownership is untouched (no swap happened).
func TestExchangeRemoveCard(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "rmcard_a", "RmA")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "rmcard_b", "RmB")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	st.DB().Create(&domain.CoachCard{CoachID: uint(aID), TemplateID: 4243, Quantity: 1})
	giveCard := firstUnequippedCard(t, st, uint(aID))

	exID := openExchange(t, a, b, bID)

	// A stakes the card, both see 5109.
	_ = a.Send(3, testclient.OpExchangeAddCard,
		testclient.NewW().I64(exID).I32(giveCard.TemplateID).U16(uint16(giveCard.Quantity)).Bytes())
	if _, _, err := b.WaitFor(testclient.OpExchangeCardAdded, testclient.DefaultTimeout); err != nil {
		t.Fatalf("B never saw card added: %v", err)
	}
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// A un-stakes it: [i64 exId][i32 refCardId][u16 qty]; both see 5112.
	_ = a.Send(3, testclient.OpExchangeRemoveCard,
		testclient.NewW().I64(exID).I32(giveCard.TemplateID).U16(uint16(giveCard.Quantity)).Bytes())
	if _, _, err := b.WaitFor(testclient.OpExchangeCardRemoved, testclient.DefaultTimeout); err != nil {
		t.Fatalf("B never saw card removed: %v", err)
	}

	// Ownership unchanged: A still holds the template.
	if !hasTemplate(t, st, uint(aID), giveCard.TemplateID, 1) {
		t.Error("A should still own the un-staked card")
	}
}

// TestExchangeCancel: cancelling (5108) ends the trade for both with reason 1.
func TestExchangeCancel(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "cancel_a", "CancA")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "cancel_b", "CancB")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)
	_ = st

	exID := openExchange(t, a, b, bID)

	// A cancels: [i64 exId] (server ignores the body, resolves by coach).
	_ = a.Send(3, testclient.OpExchangeCancel, testclient.NewW().I64(exID).Bytes())

	// Both should get 5111 End with reason 1 (cancel), read reason FIRST.
	end, _, err := b.WaitFor(testclient.OpExchangeEnd, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("B never got exchange end: %v", err)
	}
	if reason := testclient.NewR(end.Payload).U8(); reason != 1 {
		t.Fatalf("exchange end reason = %d, want 1 (cancel)", reason)
	}
}

// hasTemplate reports whether a coach still owns at least minQty of a template.
func hasTemplate(t *testing.T, s *store.Store, coachID uint, tmpl int32, minQty int16) bool {
	t.Helper()
	c, err := s.Coaches.Get(coachID)
	if err != nil {
		return false
	}
	var total int16
	for _, card := range c.Inventory {
		if card.TemplateID == tmpl {
			total += card.Quantity
		}
	}
	return total >= minQty
}
