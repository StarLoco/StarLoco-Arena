package service_test

import (
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
)

// Tests for CoachService.CompleteExchange -- the atomic, commit-time-
// revalidating item-exchange transfer that closes the card-duplication and
// stake-evasion vectors reported in the security audit.

func TestCompleteExchange_HappyPathTransfersAndStamps(t *testing.T) {
	svc, ctx, alice, bob := newWageringFixture(t)

	// Alice offers 2 of a stack of 5; Bob offers 1 of a stack of 1.
	aliceCard, _ := svc.AddCard(ctx, alice.ID, 100, 5, 0)
	bobCard, _ := svc.AddCard(ctx, bob.ID, 200, 1, 0)

	res, err := svc.CompleteExchange(ctx, alice.ID, bob.ID,
		[]service.ExchangeOffer{{CoachCardID: aliceCard.ID, Quantity: 2}},
		[]service.ExchangeOffer{{CoachCardID: bobCard.ID, Quantity: 1}},
	)
	if err != nil {
		t.Fatalf("CompleteExchange: %v", err)
	}
	if res == nil {
		t.Fatalf("CompleteExchange aborted unexpectedly")
	}

	// Alice's stack of 100 should now be 3 (5-2); Bob should have 2 of 100.
	aliceCards := loadInventory(t, svc, alice.ID)
	bobCards := loadInventory(t, svc, bob.ID)

	if q := stackQty(aliceCards, 100); q != 3 {
		t.Errorf("alice template 100 qty = %d, want 3 (5-2)", q)
	}
	if q := stackQty(bobCards, 100); q != 2 {
		t.Errorf("bob template 100 qty = %d, want 2 (received)", q)
	}
	// Bob gave away his only 200; Alice received it.
	if q := stackQty(bobCards, 200); q != 0 {
		t.Errorf("bob template 200 qty = %d, want 0 (gave it away)", q)
	}
	if q := stackQty(aliceCards, 100); q < 0 {
		t.Fatal("negative quantity -- impossible")
	}
	// Received cards must be stamped CURSED.
	for _, c := range bobCards {
		if c.TemplateID == 100 && c.Flag&domain.CoachCardFlagCursed == 0 {
			t.Error("received card should be stamped CURSED")
		}
	}

	// No duplication: total units of template 100 across both coaches must
	// equal the original 5.
	total := stackQty(aliceCards, 100) + stackQty(bobCards, 100)
	if total != 5 {
		t.Errorf("template 100 total across both coaches = %d, want 5 (no dup/loss)", total)
	}
}

func TestCompleteExchange_QuantityClampedToOwned(t *testing.T) {
	svc, ctx, alice, bob := newWageringFixture(t)
	// Alice owns 2 but offers 999 -- must clamp to 2, never conjure more.
	aliceCard, _ := svc.AddCard(ctx, alice.ID, 100, 2, 0)

	res, err := svc.CompleteExchange(ctx, alice.ID, bob.ID,
		[]service.ExchangeOffer{{CoachCardID: aliceCard.ID, Quantity: 999}},
		nil,
	)
	if err != nil {
		t.Fatalf("CompleteExchange: %v", err)
	}
	if res == nil {
		t.Fatalf("CompleteExchange aborted unexpectedly")
	}
	bobCards := loadInventory(t, svc, bob.ID)
	if q := stackQty(bobCards, 100); q != 2 {
		t.Errorf("bob received %d of template 100, want 2 (clamped, no dup)", q)
	}
	aliceCards := loadInventory(t, svc, alice.ID)
	if q := stackQty(aliceCards, 100); q != 0 {
		t.Errorf("alice should have 0 left, got %d", q)
	}
}

func TestCompleteExchange_AbortsOnLockedCard(t *testing.T) {
	svc, ctx, alice, bob := newWageringFixture(t)
	aliceCard, _ := svc.AddCard(ctx, alice.ID, 100, 1, 0)
	bobCard, _ := svc.AddCard(ctx, bob.ID, 200, 1, 0)
	// Alice locks her offered card after "adding" it -- commit must abort.
	if _, err := svc.LockCard(ctx, alice.ID, aliceCard.ID); err != nil {
		t.Fatalf("LockCard: %v", err)
	}

	res, err := svc.CompleteExchange(ctx, alice.ID, bob.ID,
		[]service.ExchangeOffer{{CoachCardID: aliceCard.ID, Quantity: 1}},
		[]service.ExchangeOffer{{CoachCardID: bobCard.ID, Quantity: 1}},
	)
	if err != nil {
		t.Fatalf("CompleteExchange: %v", err)
	}
	if res != nil {
		t.Fatal("CompleteExchange should ABORT when an offered card is locked (no partial transfer)")
	}
	// Nothing moved: Alice still has 100, Bob still has 200, no cross-grant.
	if q := stackQty(loadInventory(t, svc, alice.ID), 100); q != 1 {
		t.Errorf("alice template 100 qty = %d, want 1 (untouched)", q)
	}
	if q := stackQty(loadInventory(t, svc, bob.ID), 200); q != 1 {
		t.Errorf("bob template 200 qty = %d, want 1 (untouched)", q)
	}
	if q := stackQty(loadInventory(t, svc, bob.ID), 100); q != 0 {
		t.Errorf("bob should not have received template 100 on an aborted exchange, got %d", q)
	}
}

func TestCompleteExchange_AbortsOnEquippedCard(t *testing.T) {
	svc, ctx, alice, bob := newWageringFixture(t)
	aliceCard, _ := svc.AddCard(ctx, alice.ID, 100, 1, 0)
	bobCard, _ := svc.AddCard(ctx, bob.ID, 200, 1, 0)
	// Alice equips her offered card (pos>0) -- commit must abort.
	if _, err := svc.SetCardPosition(ctx, alice.ID, aliceCard.ID, 1); err != nil {
		t.Fatalf("SetCardPosition: %v", err)
	}

	res, err := svc.CompleteExchange(ctx, alice.ID, bob.ID,
		[]service.ExchangeOffer{{CoachCardID: aliceCard.ID, Quantity: 1}},
		[]service.ExchangeOffer{{CoachCardID: bobCard.ID, Quantity: 1}},
	)
	if err != nil {
		t.Fatalf("CompleteExchange: %v", err)
	}
	if res != nil {
		t.Fatal("CompleteExchange should ABORT when an offered card is equipped (pos>0)")
	}
}

func TestCompleteExchange_AbortsOnMissingCard(t *testing.T) {
	svc, ctx, alice, bob := newWageringFixture(t)
	bobCard, _ := svc.AddCard(ctx, bob.ID, 200, 1, 0)

	// Alice offers a card id that doesn't exist / she doesn't own.
	res, err := svc.CompleteExchange(ctx, alice.ID, bob.ID,
		[]service.ExchangeOffer{{CoachCardID: 999999, Quantity: 1}},
		[]service.ExchangeOffer{{CoachCardID: bobCard.ID, Quantity: 1}},
	)
	if err != nil {
		t.Fatalf("CompleteExchange: %v", err)
	}
	if res != nil {
		t.Fatal("CompleteExchange should ABORT when an offered card is not owned")
	}
	// Bob's card must be untouched (no one-sided transfer).
	if q := stackQty(loadInventory(t, svc, bob.ID), 200); q != 1 {
		t.Errorf("bob template 200 qty = %d, want 1 (untouched on abort)", q)
	}
}

// TestCompleteExchange_RollsBackWhenSecondSideInvalid is the regression
// guard for the gorm "nil return commits" bug: when the FROM side is fully
// valid but the TO side has an invalid offer, the FROM-side writes (already
// staged in the transaction) MUST be rolled back, not committed. Otherwise
// FROM loses its card and TO receives it while giving nothing back
// (one-sided theft).
func TestCompleteExchange_RollsBackWhenSecondSideInvalid(t *testing.T) {
	svc, ctx, alice, bob := newWageringFixture(t)

	// Alice (FROM) offers a fully valid card.
	aliceCard, _ := svc.AddCard(ctx, alice.ID, 100, 1, 0)
	// Bob (TO) offers a card, then locks it so the TO side is invalid.
	bobCard, _ := svc.AddCard(ctx, bob.ID, 200, 1, 0)
	if _, err := svc.LockCard(ctx, bob.ID, bobCard.ID); err != nil {
		t.Fatalf("LockCard: %v", err)
	}

	res, err := svc.CompleteExchange(ctx, alice.ID, bob.ID,
		[]service.ExchangeOffer{{CoachCardID: aliceCard.ID, Quantity: 1}}, // valid FROM
		[]service.ExchangeOffer{{CoachCardID: bobCard.ID, Quantity: 1}},   // invalid TO (locked)
	)
	if err != nil {
		t.Fatalf("CompleteExchange: %v", err)
	}
	if res != nil {
		t.Fatal("CompleteExchange should ABORT when the TO side is invalid")
	}

	// The FROM-side write must have been rolled back: Alice STILL has her
	// card, and Bob did NOT receive it.
	if q := stackQty(loadInventory(t, svc, alice.ID), 100); q != 1 {
		t.Errorf("alice template 100 qty = %d, want 1 (FROM-side write must roll back)", q)
	}
	if q := stackQty(loadInventory(t, svc, bob.ID), 100); q != 0 {
		t.Errorf("bob received template 100 = %d, want 0 (one-sided transfer must not commit)", q)
	}
	// Bob's own (locked) card is untouched too.
	if q := stackQty(loadInventory(t, svc, bob.ID), 200); q != 1 {
		t.Errorf("bob template 200 qty = %d, want 1 (untouched)", q)
	}
}

// TestCompleteExchange_RollsBackOnSecondOfferSameSide guards the intra-side
// case: on ONE side, offer[0] is valid and offer[1] is invalid. offer[0]'s
// staged write must roll back too (all-or-nothing per exchange).
func TestCompleteExchange_RollsBackOnSecondOfferSameSide(t *testing.T) {
	svc, ctx, alice, bob := newWageringFixture(t)

	good, _ := svc.AddCard(ctx, alice.ID, 100, 1, 0)
	bad, _ := svc.AddCard(ctx, alice.ID, 300, 1, 0)
	if _, err := svc.SetCardPosition(ctx, alice.ID, bad.ID, 1); err != nil { // equip -> invalid
		t.Fatalf("SetCardPosition: %v", err)
	}

	res, err := svc.CompleteExchange(ctx, alice.ID, bob.ID,
		[]service.ExchangeOffer{
			{CoachCardID: good.ID, Quantity: 1}, // valid, staged first
			{CoachCardID: bad.ID, Quantity: 1},  // invalid (equipped) -> abort
		},
		nil,
	)
	if err != nil {
		t.Fatalf("CompleteExchange: %v", err)
	}
	if res != nil {
		t.Fatal("CompleteExchange should ABORT when a later same-side offer is invalid")
	}
	// good card must NOT have been transferred.
	if q := stackQty(loadInventory(t, svc, alice.ID), 100); q != 1 {
		t.Errorf("alice template 100 qty = %d, want 1 (first offer's write must roll back)", q)
	}
	if q := stackQty(loadInventory(t, svc, bob.ID), 100); q != 0 {
		t.Errorf("bob received template 100 = %d, want 0", q)
	}
}

func loadInventory(t *testing.T, svc *service.CoachService, coachID uint) []domain.CoachCard {
	t.Helper()
	var cards []domain.CoachCard
	if err := svc.DB().Where("coach_id = ?", coachID).Find(&cards).Error; err != nil {
		t.Fatalf("load inventory: %v", err)
	}
	return cards
}

func stackQty(cards []domain.CoachCard, templateID int32) int16 {
	var total int16
	for _, c := range cards {
		if c.TemplateID == templateID {
			total += c.Quantity
		}
	}
	return total
}
