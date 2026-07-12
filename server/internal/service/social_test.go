package service_test

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

func TestAddFriendAndDuplicateIsNoop(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	social := service.NewSocialService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	added, err := social.AddFriend(ctx, coachA.ID, coachB.ID)
	if err != nil {
		t.Fatalf("AddFriend: %v", err)
	}
	if !added {
		t.Error("first AddFriend should return added=true")
	}

	added, err = social.AddFriend(ctx, coachA.ID, coachB.ID)
	if err != nil {
		t.Fatalf("AddFriend (dup): %v", err)
	}
	if added {
		t.Error("duplicate AddFriend should return added=false")
	}
}

func TestRemoveFriend(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	social := service.NewSocialService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	social.AddFriend(ctx, coachA.ID, coachB.ID)
	if err := social.RemoveFriend(ctx, coachA.ID, coachB.ID); err != nil {
		t.Fatalf("RemoveFriend: %v", err)
	}

	loaded, err := coachSvc.GetCoachByAccountID(ctx, accountA.ID)
	if err != nil {
		t.Fatalf("GetCoachByAccountID: %v", err)
	}
	if len(loaded.Friends) != 0 {
		t.Errorf("Friends after remove = %+v, want empty", loaded.Friends)
	}
}

func TestFriendshipIsDirectionalNotMutual(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	social := service.NewSocialService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	social.AddFriend(ctx, coachA.ID, coachB.ID)

	// Alice added Bob as a friend; Bob did NOT reciprocally add Alice.
	// This mirrors the legacy Coach.addFriend's one-directional edge.
	loadedB, err := coachSvc.GetCoachByAccountID(ctx, accountB.ID)
	if err != nil {
		t.Fatalf("GetCoachByAccountID: %v", err)
	}
	if len(loadedB.Friends) != 0 {
		t.Errorf("Bob's friend list should be empty (directional edge), got %+v", loadedB.Friends)
	}
}

func TestAddIgnoredAndRemove(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	social := service.NewSocialService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	added, err := social.AddIgnored(ctx, coachA.ID, coachB.ID)
	if err != nil || !added {
		t.Fatalf("AddIgnored: added=%v err=%v", added, err)
	}

	if err := social.RemoveIgnored(ctx, coachA.ID, coachB.ID); err != nil {
		t.Fatalf("RemoveIgnored: %v", err)
	}

	loaded, err := coachSvc.GetCoachByAccountID(ctx, accountA.ID)
	if err != nil {
		t.Fatalf("GetCoachByAccountID: %v", err)
	}
	if len(loaded.Ignored) != 0 {
		t.Errorf("Ignored after remove = %+v, want empty", loaded.Ignored)
	}
}
