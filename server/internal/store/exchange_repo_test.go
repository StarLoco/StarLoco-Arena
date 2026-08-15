package store

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// seedCoachWithCard creates a coach and gives it one card, returning the card id.
func seedCoachWithCard(t *testing.T, s *Store, name string, tmpl int32, qty int16, pos int16) (coachID, cardID uint) {
	t.Helper()
	acc, _ := s.Accounts.CreateAccount(name+"_acc", "pw", false)
	coach, err := s.Coaches.Create(acc.ID, name, 0, 0, 0)
	if err != nil {
		t.Fatalf("create coach: %v", err)
	}
	card := domain.CoachCard{CoachID: coach.ID, TemplateID: tmpl, Quantity: qty, Pos: pos}
	if err := s.DB().Create(&card).Error; err != nil {
		t.Fatalf("create card: %v", err)
	}
	return coach.ID, card.ID
}

func cardCount(t *testing.T, s *Store, coachID uint, tmpl int32) int16 {
	t.Helper()
	var card domain.CoachCard
	err := s.DB().Where("coach_id = ? AND template_id = ?", coachID, tmpl).First(&card).Error
	if err != nil {
		return 0
	}
	return card.Quantity
}

// TestCompleteExchange_SwapsBothSides: a valid two-sided trade moves both cards.
func TestCompleteExchange_SwapsBothSides(t *testing.T) {
	s := newTestStore(t)
	aID, aCard := seedCoachWithCard(t, s, "Alice", 100, 1, 0)
	bID, bCard := seedCoachWithCard(t, s, "Bob", 200, 1, 0)

	committed, err := s.Coaches.CompleteExchange(
		ExchangeOffer{GiverID: aID, Receiver: bID, Cards: []ExchangeCard{{CardID: aCard, Quantity: 1}}},
		ExchangeOffer{GiverID: bID, Receiver: aID, Cards: []ExchangeCard{{CardID: bCard, Quantity: 1}}},
	)
	if err != nil || !committed {
		t.Fatalf("expected commit, got committed=%v err=%v", committed, err)
	}
	// Alice lost tmpl 100, gained tmpl 200; Bob the reverse.
	if cardCount(t, s, aID, 100) != 0 || cardCount(t, s, aID, 200) != 1 {
		t.Errorf("Alice inventory wrong: 100=%d 200=%d", cardCount(t, s, aID, 100), cardCount(t, s, aID, 200))
	}
	if cardCount(t, s, bID, 200) != 0 || cardCount(t, s, bID, 100) != 1 {
		t.Errorf("Bob inventory wrong: 200=%d 100=%d", cardCount(t, s, bID, 200), cardCount(t, s, bID, 100))
	}
}

// TestCompleteExchange_RollsBackWhenSecondSideInvalid: if B's card cannot be
// traded, the WHOLE trade rolls back — A must NOT lose its card (no one-sided theft).
func TestCompleteExchange_RollsBackWhenSecondSideInvalid(t *testing.T) {
	s := newTestStore(t)
	aID, aCard := seedCoachWithCard(t, s, "Alice", 100, 1, 0)
	bID, bCard := seedCoachWithCard(t, s, "Bob", 200, 1, 1) // EQUIPPED: not tradable

	committed, err := s.Coaches.CompleteExchange(
		ExchangeOffer{GiverID: aID, Receiver: bID, Cards: []ExchangeCard{{CardID: aCard, Quantity: 1}}},
		ExchangeOffer{GiverID: bID, Receiver: aID, Cards: []ExchangeCard{{CardID: bCard, Quantity: 1}}},
	)
	if err != nil {
		t.Fatalf("err: %v", err)
	}
	if committed {
		t.Fatal("trade should have aborted (B's card is equipped)")
	}
	// Nothing moved: Alice still has 100, Bob still has 200.
	if cardCount(t, s, aID, 100) != 1 {
		t.Errorf("Alice lost her card despite rollback: 100=%d", cardCount(t, s, aID, 100))
	}
	if cardCount(t, s, aID, 200) != 0 {
		t.Errorf("Alice received a card despite rollback")
	}
	if cardCount(t, s, bID, 200) != 1 {
		t.Errorf("Bob's card changed despite rollback")
	}
}

// TestCompleteExchange_AbortsOnEquipped: an equipped (pos!=0) card can't trade.
func TestCompleteExchange_AbortsOnEquipped(t *testing.T) {
	s := newTestStore(t)
	aID, aCard := seedCoachWithCard(t, s, "Alice", 100, 1, 1) // equipped
	bID, bCard := seedCoachWithCard(t, s, "Bob", 200, 1, 0)

	committed, _ := s.Coaches.CompleteExchange(
		ExchangeOffer{GiverID: aID, Receiver: bID, Cards: []ExchangeCard{{CardID: aCard, Quantity: 1}}},
		ExchangeOffer{GiverID: bID, Receiver: aID, Cards: []ExchangeCard{{CardID: bCard, Quantity: 1}}},
	)
	if committed {
		t.Fatal("equipped card must not be tradeable")
	}
	if cardCount(t, s, bID, 200) != 1 {
		t.Error("Bob lost his card despite abort")
	}
}

// TestCompleteExchange_QuantityClampAndStack: giving more than owned is clamped;
// receiving stacks onto an existing same-template card.
func TestCompleteExchange_QuantityClampAndStack(t *testing.T) {
	s := newTestStore(t)
	aID, aCard := seedCoachWithCard(t, s, "Alice", 100, 3, 0)
	bID, _ := seedCoachWithCard(t, s, "Bob", 100, 2, 0) // Bob already has tmpl 100

	// Alice offers 10 (only owns 3 -> clamp to 3); Bob offers nothing.
	committed, err := s.Coaches.CompleteExchange(
		ExchangeOffer{GiverID: aID, Receiver: bID, Cards: []ExchangeCard{{CardID: aCard, Quantity: 10}}},
		ExchangeOffer{GiverID: bID, Receiver: aID},
	)
	if err != nil || !committed {
		t.Fatalf("expected commit, got %v %v", committed, err)
	}
	if cardCount(t, s, aID, 100) != 0 {
		t.Errorf("Alice should have given all 3, has %d", cardCount(t, s, aID, 100))
	}
	if got := cardCount(t, s, bID, 100); got != 5 { // 2 + 3
		t.Errorf("Bob should have 5 (2+3 stacked), has %d", got)
	}
}
