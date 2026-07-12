package service_test

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

// Tests for the card-wagering service layer: LockCard's max-10 cap,
// SelectStakeCard's eligibility rules, and TransferCard's transactional
// ownership move + cursed handling.

func newWageringFixture(t *testing.T) (*service.CoachService, context.Context, *domain.Coach, *domain.Coach) {
	t.Helper()
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	accA := createTestAccount(t, gdb, "alice", "pw")
	accB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accB.ID, "Bob", 0, 0, 0)
	return coachSvc, ctx, coachA, coachB
}

func TestLockCard_EnforcesMaxTen(t *testing.T) {
	svc, ctx, coach, _ := newWageringFixture(t)

	// Add 12 distinct cards and lock them all; only 10 should end up locked.
	var ids []uint
	for i := int32(0); i < 12; i++ {
		c, err := svc.AddCard(ctx, coach.ID, 100+i, 1, 0)
		if err != nil {
			t.Fatalf("AddCard: %v", err)
		}
		ids = append(ids, c.ID)
	}
	locked := 0
	for _, id := range ids {
		card, err := svc.LockCard(ctx, coach.ID, id)
		if err != nil {
			t.Fatalf("LockCard: %v", err)
		}
		if card.Flag&domain.CoachCardFlagLocked != 0 {
			locked++
		}
	}
	if locked != service.MaxLockedCards {
		t.Errorf("locked %d cards, want exactly %d (the cap)", locked, service.MaxLockedCards)
	}
}

func TestLockCard_IdempotentDoesNotConsumeCap(t *testing.T) {
	svc, ctx, coach, _ := newWageringFixture(t)
	c, _ := svc.AddCard(ctx, coach.ID, 100, 1, 0)
	// Lock the same card 5 times -- must stay lockable and not eat the cap.
	for i := 0; i < 5; i++ {
		if _, err := svc.LockCard(ctx, coach.ID, c.ID); err != nil {
			t.Fatalf("LockCard: %v", err)
		}
	}
	// Now 9 more distinct cards should still all lock (10 total).
	for i := int32(1); i <= 9; i++ {
		c2, _ := svc.AddCard(ctx, coach.ID, 100+i, 1, 0)
		card, _ := svc.LockCard(ctx, coach.ID, c2.ID)
		if card.Flag&domain.CoachCardFlagLocked == 0 {
			t.Fatalf("card %d failed to lock but cap should allow 10", c2.ID)
		}
	}
}

func TestLockCard_PreservesCursedBit(t *testing.T) {
	svc, ctx, coach, _ := newWageringFixture(t)
	c, _ := svc.AddCard(ctx, coach.ID, 100, 1, domain.CoachCardFlagCursed)
	card, _ := svc.LockCard(ctx, coach.ID, c.ID)
	if card.Flag&domain.CoachCardFlagCursed == 0 {
		t.Error("LockCard cleared the cursed bit; must preserve it")
	}
	if card.Flag&domain.CoachCardFlagLocked == 0 {
		t.Error("LockCard did not set the locked bit")
	}
}

func TestSelectStakeCard_SkipsLockedEquippedAndEmpty(t *testing.T) {
	svc, ctx, coach, _ := newWageringFixture(t)

	// No cards yet -> no stake.
	if card, err := svc.SelectStakeCard(ctx, coach.ID); err != nil || card != nil {
		t.Fatalf("empty inventory: got (%v, %v), want (nil, nil)", card, err)
	}

	// A locked card is not eligible.
	locked, _ := svc.AddCard(ctx, coach.ID, 100, 1, 0)
	svc.LockCard(ctx, coach.ID, locked.ID)
	// An equipped card is not eligible.
	equipped, _ := svc.AddCard(ctx, coach.ID, 200, 1, 0)
	svc.SetCardPosition(ctx, coach.ID, equipped.ID, 1)

	if card, _ := svc.SelectStakeCard(ctx, coach.ID); card != nil {
		t.Fatalf("only locked+equipped cards present, want no stakeable card, got card id %d", card.ID)
	}

	// Add one eligible (unlocked, pos=0) card -> that one must be selected.
	elig, _ := svc.AddCard(ctx, coach.ID, 300, 1, 0)
	card, err := svc.SelectStakeCard(ctx, coach.ID)
	if err != nil {
		t.Fatalf("SelectStakeCard: %v", err)
	}
	if card == nil || card.ID != elig.ID {
		t.Fatalf("stake = %v, want the only eligible card id %d", card, elig.ID)
	}
}

func TestTransferCard_MovesOwnershipToWinner(t *testing.T) {
	svc, ctx, loser, winner := newWageringFixture(t)
	card, _ := svc.AddCard(ctx, loser.ID, 42, 1, 0)

	out, err := svc.TransferCard(ctx, loser.ID, winner.ID, card.ID)
	if err != nil {
		t.Fatalf("TransferCard: %v", err)
	}
	if out == nil || out.TemplateID != 42 {
		t.Fatalf("transferred = %v, want template 42", out)
	}
	// Loser no longer owns it; winner now has one of template 42.
	if _, err := svc.GetCardByID(ctx, loser.ID, card.ID); err == nil {
		t.Error("loser still owns the transferred card")
	}
	winnerCard, err := svc.SelectStakeCard(ctx, winner.ID)
	if err != nil || winnerCard == nil || winnerCard.TemplateID != 42 {
		t.Errorf("winner did not receive template 42, got %v (err %v)", winnerCard, err)
	}
}

func TestTransferCard_DecrementsMultiQuantityStack(t *testing.T) {
	svc, ctx, loser, winner := newWageringFixture(t)
	card, _ := svc.AddCard(ctx, loser.ID, 42, 3, 0) // stack of 3

	if _, err := svc.TransferCard(ctx, loser.ID, winner.ID, card.ID); err != nil {
		t.Fatalf("TransferCard: %v", err)
	}
	// Loser keeps 2, winner has 1.
	remaining, err := svc.GetCardByID(ctx, loser.ID, card.ID)
	if err != nil {
		t.Fatalf("loser should still own the stack (qty 2): %v", err)
	}
	if remaining.Quantity != 2 {
		t.Errorf("loser stack quantity = %d, want 2", remaining.Quantity)
	}
}

func TestTransferCard_PreservesCursedFlag(t *testing.T) {
	svc, ctx, loser, winner := newWageringFixture(t)
	card, _ := svc.AddCard(ctx, loser.ID, 42, 1, domain.CoachCardFlagCursed)

	out, err := svc.TransferCard(ctx, loser.ID, winner.ID, card.ID)
	if err != nil {
		t.Fatalf("TransferCard: %v", err)
	}
	if out == nil || !out.Cursed {
		t.Fatalf("transferred cursed flag = %v, want cursed=true", out)
	}
	got, _ := svc.SelectStakeCard(ctx, winner.ID)
	if got == nil || got.Flag&domain.CoachCardFlagCursed == 0 {
		t.Error("winner's received card lost the cursed flag")
	}
}

func TestTransferCard_SkipsLockedCard(t *testing.T) {
	svc, ctx, loser, winner := newWageringFixture(t)
	card, _ := svc.AddCard(ctx, loser.ID, 42, 1, 0)
	svc.LockCard(ctx, loser.ID, card.ID) // locked mid-fight

	out, err := svc.TransferCard(ctx, loser.ID, winner.ID, card.ID)
	if err != nil {
		t.Fatalf("TransferCard: %v", err)
	}
	if out != nil {
		t.Error("a locked card must not transfer, want nil result")
	}
	// Loser keeps it.
	if _, err := svc.GetCardByID(ctx, loser.ID, card.ID); err != nil {
		t.Error("loser lost a locked card that should have been protected")
	}
}

func TestTransferCard_GoneCardIsNoOp(t *testing.T) {
	svc, ctx, loser, winner := newWageringFixture(t)
	// card id that doesn't exist
	out, err := svc.TransferCard(ctx, loser.ID, winner.ID, 99999)
	if err != nil {
		t.Fatalf("TransferCard on missing card should not error: %v", err)
	}
	if out != nil {
		t.Error("transferring a nonexistent card should be a no-op (nil result)")
	}
}
