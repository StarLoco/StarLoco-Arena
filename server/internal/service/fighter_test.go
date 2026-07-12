package service_test

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

func TestCreateFighterWithLoadout(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	fighter, err := fighterSvc.CreateFighter(ctx, coach.ID, "Bob", 1, 0, 3, 100, []int32{10, 20}, []int32{200})
	if err != nil {
		t.Fatalf("CreateFighter: %v", err)
	}
	if fighter.Name != "Bob" || fighter.CoachID != coach.ID {
		t.Errorf("fighter = %+v", fighter)
	}

	spells, objects, err := fighterSvc.LoadoutMaps(ctx, []uint{fighter.ID})
	if err != nil {
		t.Fatalf("LoadoutMaps: %v", err)
	}
	if len(spells[fighter.ID]) != 2 {
		t.Errorf("spells = %v, want 2 entries", spells[fighter.ID])
	}
	if len(objects[fighter.ID]) != 1 {
		t.Errorf("objects = %v, want 1 entry", objects[fighter.ID])
	}
}

func TestDeleteFighterCascadesLoadout(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)
	fighter, _ := fighterSvc.CreateFighter(ctx, coach.ID, "Bob", 1, 0, 3, 100, []int32{10}, []int32{200})

	deleted, err := fighterSvc.DeleteFighter(ctx, coach.ID, fighter.ID)
	if err != nil {
		t.Fatalf("DeleteFighter: %v", err)
	}
	if !deleted {
		t.Fatalf("DeleteFighter reported no rows deleted")
	}

	fighters, err := fighterSvc.ListFighters(ctx, coach.ID)
	if err != nil {
		t.Fatalf("ListFighters: %v", err)
	}
	if len(fighters) != 0 {
		t.Errorf("ListFighters after delete = %v, want empty", fighters)
	}

	spells, _, err := fighterSvc.LoadoutMaps(ctx, []uint{fighter.ID})
	if err != nil {
		t.Fatalf("LoadoutMaps: %v", err)
	}
	if len(spells[fighter.ID]) != 0 {
		t.Errorf("spells after fighter delete should cascade-delete, got %v", spells[fighter.ID])
	}
}

func TestUpdateInventoryReplacesLoadout(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)
	fighter, _ := fighterSvc.CreateFighter(ctx, coach.ID, "Bob", 1, 0, 3, 100, []int32{10, 20}, []int32{200})

	owned, err := fighterSvc.UpdateInventory(ctx, coach.ID, fighter.ID, 150, []int32{99}, nil)
	if err != nil {
		t.Fatalf("UpdateInventory: %v", err)
	}
	if !owned {
		t.Fatalf("UpdateInventory reported fighter not owned")
	}

	spellIDs, err := fighterSvc.GetSpellIDs(ctx, fighter.ID)
	if err != nil {
		t.Fatalf("GetSpellIDs: %v", err)
	}
	if len(spellIDs) != 1 || spellIDs[0] != 99 {
		t.Errorf("spellIDs = %v, want [99]", spellIDs)
	}

	objectIDs, err := fighterSvc.GetObjectIDs(ctx, fighter.ID)
	if err != nil {
		t.Fatalf("GetObjectIDs: %v", err)
	}
	if len(objectIDs) != 0 {
		t.Errorf("objectIDs = %v, want empty (fully replaced)", objectIDs)
	}
}

func TestDeleteFighterScopedToCoach(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)
	fighterA, _ := fighterSvc.CreateFighter(ctx, coachA.ID, "Fa", 1, 0, 0, 0, nil, nil)

	// Bob attempting to delete Alice's fighter must be a no-op (IDOR guard).
	deleted, err := fighterSvc.DeleteFighter(ctx, coachB.ID, fighterA.ID)
	if err != nil {
		t.Fatalf("DeleteFighter: %v", err)
	}
	if deleted {
		t.Fatalf("coachB deleted coachA's fighter -- IDOR not prevented")
	}
	// Alice's fighter must still exist.
	fighters, _ := fighterSvc.ListFighters(ctx, coachA.ID)
	if len(fighters) != 1 {
		t.Fatalf("coachA's fighter was removed by a foreign delete, got %d", len(fighters))
	}
}

func TestUpdateInventoryScopedToCoach(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)
	fighterA, _ := fighterSvc.CreateFighter(ctx, coachA.ID, "Fa", 1, 0, 0, 0, []int32{10}, nil)

	// Bob attempting to rewrite Alice's loadout must be a no-op (IDOR guard).
	owned, err := fighterSvc.UpdateInventory(ctx, coachB.ID, fighterA.ID, 150, []int32{99}, nil)
	if err != nil {
		t.Fatalf("UpdateInventory: %v", err)
	}
	if owned {
		t.Fatalf("coachB rewrote coachA's loadout -- IDOR not prevented")
	}
	// Alice's original loadout must be intact.
	spellIDs, _ := fighterSvc.GetSpellIDs(ctx, fighterA.ID)
	if len(spellIDs) != 1 || spellIDs[0] != 10 {
		t.Fatalf("coachA's loadout was altered by a foreign update: %v", spellIDs)
	}
}

func TestGetFightersByIDsScopedToCoach(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	fighterA, _ := fighterSvc.CreateFighter(ctx, coachA.ID, "Fa", 1, 0, 0, 0, nil, nil)
	fighterB, _ := fighterSvc.CreateFighter(ctx, coachB.ID, "Fb", 1, 0, 0, 0, nil, nil)

	// Bob should not be able to fetch Alice's fighter via GetFightersByIDs.
	got, err := fighterSvc.GetFightersByIDs(ctx, coachB.ID, []uint{fighterA.ID, fighterB.ID})
	if err != nil {
		t.Fatalf("GetFightersByIDs: %v", err)
	}
	if len(got) != 1 || got[0].ID != fighterB.ID {
		t.Errorf("GetFightersByIDs should only return coachB's own fighter, got %+v", got)
	}
}
