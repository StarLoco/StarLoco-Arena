package service_test

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

func TestCreateCoachSuccess(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	auth := service.NewAuthService(gdb)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	_ = auth

	ctx := context.Background()
	coach, result, err := coachSvc.CreateCoach(ctx, account.ID, "myCoach", 1, 2, 0)
	if err != nil {
		t.Fatalf("CreateCoach: %v", err)
	}
	if result != service.CoachCreationOK {
		t.Fatalf("result = %v, want OK", result)
	}
	// Name should be Title-cased, matching the legacy server's behavior.
	if coach.Name != "Mycoach" {
		t.Errorf("Name = %q, want %q (title-cased)", coach.Name, "Mycoach")
	}

	// Account should now be linked to the coach.
	var updated domain.Account
	gdb.First(&updated, account.ID)
	if updated.CoachID == nil || *updated.CoachID != coach.ID {
		t.Error("account.CoachID should be linked after coach creation")
	}
}

func TestCreateCoachRejectsForbiddenFragments(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()

	forbidden := []string{"admin123", "modoperson", "has space", "weird&name"}
	for _, name := range forbidden {
		_, result, err := coachSvc.CreateCoach(ctx, account.ID, name, 0, 0, 0)
		if err != nil {
			t.Fatalf("CreateCoach(%q): %v", name, err)
		}
		if result != service.CoachCreationInvalidName {
			t.Errorf("CreateCoach(%q) result = %v, want InvalidName", name, result)
		}
	}
}

func TestCreateCoachRejectsDuplicateName(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()

	_, result, err := coachSvc.CreateCoach(ctx, accountA.ID, "SameName", 0, 0, 0)
	if err != nil || result != service.CoachCreationOK {
		t.Fatalf("first creation: result=%v err=%v", result, err)
	}

	_, result, err = coachSvc.CreateCoach(ctx, accountB.ID, "sameName", 0, 0, 0)
	if err != nil {
		t.Fatalf("CreateCoach: %v", err)
	}
	if result != service.CoachCreationNameTaken {
		t.Errorf("result = %v, want NameTaken (names compared case-insensitively via normalization)", result)
	}
}

func TestGetCoachByAccountIDPreloadsAssociations(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	social := service.NewSocialService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()

	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	social.AddFriend(ctx, coachA.ID, coachB.ID)
	social.AddIgnored(ctx, coachA.ID, coachB.ID)
	coachSvc.AddCard(ctx, coachA.ID, 42, 1, 0)

	loaded, err := coachSvc.GetCoachByAccountID(ctx, accountA.ID)
	if err != nil {
		t.Fatalf("GetCoachByAccountID: %v", err)
	}
	if len(loaded.Inventory) != 1 {
		t.Errorf("Inventory len = %d, want 1", len(loaded.Inventory))
	}
	if len(loaded.Friends) != 1 || loaded.Friends[0].Friend == nil || loaded.Friends[0].Friend.Name != "Bob" {
		t.Errorf("Friends = %+v", loaded.Friends)
	}
	if len(loaded.Ignored) != 1 || loaded.Ignored[0].Ignored == nil || loaded.Ignored[0].Ignored.Name != "Bob" {
		t.Errorf("Ignored = %+v", loaded.Ignored)
	}
}

func TestUpdatePosition(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	if err := coachSvc.UpdatePosition(ctx, coach.ID, 10, 20, 3); err != nil {
		t.Fatalf("UpdatePosition: %v", err)
	}

	reloaded, err := coachSvc.GetByID(ctx, coach.ID)
	if err != nil {
		t.Fatalf("GetByID: %v", err)
	}
	if reloaded.PosX != 10 || reloaded.PosY != 20 || reloaded.PosZ != 3 {
		t.Errorf("position = (%d,%d,%d), want (10,20,3)", reloaded.PosX, reloaded.PosY, reloaded.PosZ)
	}
}

func TestAddCardStacksOnExistingUnequippedCard(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	card1, err := coachSvc.AddCard(ctx, coach.ID, 42, 3, 0)
	if err != nil {
		t.Fatalf("AddCard: %v", err)
	}
	card2, err := coachSvc.AddCard(ctx, coach.ID, 42, 2, 0)
	if err != nil {
		t.Fatalf("AddCard: %v", err)
	}
	if card1.ID != card2.ID {
		t.Errorf("expected stacking onto same card row, got IDs %d and %d", card1.ID, card2.ID)
	}
	if card2.Quantity != 5 {
		t.Errorf("Quantity = %d, want 5 (3+2)", card2.Quantity)
	}
}

func TestAddCardDoesNotStackOnEquippedCard(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	card1, _ := coachSvc.AddCard(ctx, coach.ID, 42, 1, 0)
	coachSvc.SetCardPosition(ctx, coach.ID, card1.ID, 1) // equip it

	card2, err := coachSvc.AddCard(ctx, coach.ID, 42, 1, 0)
	if err != nil {
		t.Fatalf("AddCard: %v", err)
	}
	if card1.ID == card2.ID {
		t.Error("should not stack a new inventory card onto an equipped one")
	}
}

func TestRemoveCardScopedToCoachOwnership(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	card, _ := coachSvc.AddCard(ctx, coachA.ID, 42, 1, 0)

	// Bob must not be able to remove Alice's card.
	removed, err := coachSvc.RemoveCard(ctx, coachB.ID, card.ID)
	if err != nil {
		t.Fatalf("RemoveCard: %v", err)
	}
	if removed {
		t.Error("RemoveCard should fail (return false) for a card not owned by the calling coach")
	}

	// Alice can remove her own card.
	removed, err = coachSvc.RemoveCard(ctx, coachA.ID, card.ID)
	if err != nil {
		t.Fatalf("RemoveCard: %v", err)
	}
	if !removed {
		t.Error("RemoveCard should succeed for the owning coach")
	}
}

func TestUnequipAll(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	c1, _ := coachSvc.AddCard(ctx, coach.ID, 10, 1, 0)
	c2, _ := coachSvc.AddCard(ctx, coach.ID, 20, 1, 0)
	coachSvc.SetCardPosition(ctx, coach.ID, c1.ID, 1)
	coachSvc.SetCardPosition(ctx, coach.ID, c2.ID, 2)

	equipped, _ := coachSvc.GetEquippedCards(ctx, coach.ID)
	if len(equipped) != 2 {
		t.Fatalf("precondition: expected 2 equipped, got %d", len(equipped))
	}

	if err := coachSvc.UnequipAll(ctx, coach.ID); err != nil {
		t.Fatalf("UnequipAll: %v", err)
	}

	equipped, _ = coachSvc.GetEquippedCards(ctx, coach.ID)
	if len(equipped) != 0 {
		t.Errorf("after UnequipAll, expected 0 equipped, got %d", len(equipped))
	}

	// The cards must still exist (back in inventory), not be deleted.
	card1, err := coachSvc.GetCardByID(ctx, coach.ID, c1.ID)
	if err != nil {
		t.Fatalf("card should still exist after unequip: %v", err)
	}
	if card1.Pos != 0 {
		t.Errorf("card1 Pos = %d, want 0 (back in inventory)", card1.Pos)
	}
}

func TestSetCardFlagAndPosition(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)
	card, _ := coachSvc.AddCard(ctx, coach.ID, 42, 1, 0)

	updated, err := coachSvc.SetCardFlag(ctx, coach.ID, card.ID, domain.CoachCardFlagLocked)
	if err != nil {
		t.Fatalf("SetCardFlag: %v", err)
	}
	if updated.Flag != domain.CoachCardFlagLocked {
		t.Errorf("Flag = %d, want CoachCardFlagLocked", updated.Flag)
	}

	updated, err = coachSvc.SetCardPosition(ctx, coach.ID, card.ID, 5)
	if err != nil {
		t.Fatalf("SetCardPosition: %v", err)
	}
	if updated.Pos != 5 {
		t.Errorf("Pos = %d, want 5", updated.Pos)
	}

	equipped, err := coachSvc.GetEquippedCards(ctx, coach.ID)
	if err != nil {
		t.Fatalf("GetEquippedCards: %v", err)
	}
	if len(equipped) != 1 || equipped[0].ID != card.ID {
		t.Errorf("GetEquippedCards = %+v", equipped)
	}
}

// TestLockUnlockCardRoundTrip is the regression test for the bug documented
// in docs/08-java-parity-roadmap.md §8.2: the legacy Java server's unlock
// branch overwrote the whole flag byte with FLAG_CURSED instead of clearing
// the locked bit. LockCard/UnlockCard must round-trip a card's flag back to
// its pre-lock state, and must never set the cursed bit as a side effect of
// unlocking.
func TestLockUnlockCardRoundTrip(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	// Start from a cursed (but not locked) card, mirroring a real
	// trade-received card, to make sure unlocking doesn't disturb the
	// cursed bit either way.
	card, err := coachSvc.AddCard(ctx, coach.ID, 42, 1, domain.CoachCardFlagCursed)
	if err != nil {
		t.Fatalf("AddCard: %v", err)
	}
	preLockFlag := card.Flag

	locked, err := coachSvc.LockCard(ctx, coach.ID, card.ID)
	if err != nil {
		t.Fatalf("LockCard: %v", err)
	}
	if locked.Flag&domain.CoachCardFlagLocked == 0 {
		t.Errorf("Flag = %d, want locked bit set", locked.Flag)
	}
	if locked.Flag&domain.CoachCardFlagCursed != preLockFlag&domain.CoachCardFlagCursed {
		t.Errorf("LockCard must not disturb the cursed bit: Flag = %d", locked.Flag)
	}

	unlocked, err := coachSvc.UnlockCard(ctx, coach.ID, card.ID)
	if err != nil {
		t.Fatalf("UnlockCard: %v", err)
	}
	if unlocked.Flag != preLockFlag {
		t.Errorf("Flag = %d, want %d (back to pre-lock state)", unlocked.Flag, preLockFlag)
	}
	if unlocked.Flag == domain.CoachCardFlagCursed && preLockFlag != domain.CoachCardFlagCursed {
		t.Errorf("unlock must clear only the locked bit, not overwrite with FLAG_CURSED")
	}
}
