package service_test

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

func TestSaveTeamCreatesNewSlot(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	teamSvc := service.NewTeamService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)
	fighter, _ := fighterSvc.CreateFighter(ctx, coach.ID, "Bob", 1, 0, 0, 0, nil, nil)

	team, err := teamSvc.SaveTeam(ctx, coach.ID, 1, "MyTeam", []uint{fighter.ID})
	if err != nil {
		t.Fatalf("SaveTeam: %v", err)
	}
	if team.Slot != 1 || team.Name != "MyTeam" {
		t.Errorf("team = %+v", team)
	}

	teams, err := teamSvc.ListTeams(ctx, coach.ID)
	if err != nil {
		t.Fatalf("ListTeams: %v", err)
	}
	if len(teams) != 1 || len(teams[0].Fighters) != 1 {
		t.Fatalf("teams = %+v", teams)
	}
	if teams[0].Fighters[0].ID != fighter.ID {
		t.Errorf("team fighter = %+v, want %d", teams[0].Fighters[0], fighter.ID)
	}
}

func TestSaveTeamUpdatesExistingSlot(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	teamSvc := service.NewTeamService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)
	f1, _ := fighterSvc.CreateFighter(ctx, coach.ID, "F1", 1, 0, 0, 0, nil, nil)
	f2, _ := fighterSvc.CreateFighter(ctx, coach.ID, "F2", 1, 0, 0, 0, nil, nil)

	teamV1, err := teamSvc.SaveTeam(ctx, coach.ID, 1, "TeamV1", []uint{f1.ID})
	if err != nil {
		t.Fatalf("SaveTeam v1: %v", err)
	}

	teamV2, err := teamSvc.SaveTeam(ctx, coach.ID, 1, "TeamV2", []uint{f2.ID})
	if err != nil {
		t.Fatalf("SaveTeam v2: %v", err)
	}

	// Same DB row (same ID), updated in place -- not a second team.
	if teamV1.ID != teamV2.ID {
		t.Errorf("expected same team row (slot reuse), got IDs %d and %d", teamV1.ID, teamV2.ID)
	}

	teams, err := teamSvc.ListTeams(ctx, coach.ID)
	if err != nil {
		t.Fatalf("ListTeams: %v", err)
	}
	if len(teams) != 1 {
		t.Fatalf("teams = %+v, want exactly 1 (slot reused, not duplicated)", teams)
	}
	if teams[0].Name != "TeamV2" || len(teams[0].Fighters) != 1 || teams[0].Fighters[0].ID != f2.ID {
		t.Errorf("team after update = %+v", teams[0])
	}
}

func TestDeleteTeam(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	teamSvc := service.NewTeamService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	teamSvc.SaveTeam(ctx, coach.ID, 1, "ToDelete", nil)
	if err := teamSvc.DeleteTeam(ctx, coach.ID, 1); err != nil {
		t.Fatalf("DeleteTeam: %v", err)
	}

	teams, _ := teamSvc.ListTeams(ctx, coach.ID)
	if len(teams) != 0 {
		t.Errorf("teams after delete = %+v, want empty", teams)
	}
}

func TestSaveTeamWithSlotMinusOneAllocatesSequentialSlots(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	teamSvc := service.NewTeamService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	// The client sends slot == -1 for every brand-new preset. The server
	// must allocate distinct sequential slots so they don't collide.
	teamA, err := teamSvc.SaveTeam(ctx, coach.ID, -1, "First", nil)
	if err != nil {
		t.Fatalf("SaveTeam first: %v", err)
	}
	teamB, err := teamSvc.SaveTeam(ctx, coach.ID, -1, "Second", nil)
	if err != nil {
		t.Fatalf("SaveTeam second: %v", err)
	}

	if teamA.Slot < 0 || teamB.Slot < 0 {
		t.Errorf("allocated slots must be non-negative, got %d and %d", teamA.Slot, teamB.Slot)
	}
	if teamA.Slot == teamB.Slot {
		t.Errorf("two new presets collided on slot %d", teamA.Slot)
	}

	teams, err := teamSvc.ListTeams(ctx, coach.ID)
	if err != nil {
		t.Fatalf("ListTeams: %v", err)
	}
	if len(teams) != 2 {
		t.Fatalf("expected 2 distinct presets, got %d", len(teams))
	}
}

func TestSaveTeamEditExistingSlotAfterAllocation(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	teamSvc := service.NewTeamService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	created, _ := teamSvc.SaveTeam(ctx, coach.ID, -1, "Original", nil)
	edited, err := teamSvc.SaveTeam(ctx, coach.ID, created.Slot, "Renamed", nil)
	if err != nil {
		t.Fatalf("SaveTeam edit: %v", err)
	}
	if edited.ID != created.ID {
		t.Errorf("editing an existing slot should reuse the same team row (ids %d vs %d)", created.ID, edited.ID)
	}

	teams, _ := teamSvc.ListTeams(ctx, coach.ID)
	if len(teams) != 1 || teams[0].Name != "Renamed" {
		t.Errorf("expected 1 renamed team, got %+v", teams)
	}
}

func TestDifferentCoachesCanUseSameSlotNumber(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	teamSvc := service.NewTeamService(gdb)
	accountA := createTestAccount(t, gdb, "alice", "pw")
	accountB := createTestAccount(t, gdb, "bob", "pw")
	ctx := context.Background()
	coachA, _, _ := coachSvc.CreateCoach(ctx, accountA.ID, "Alice", 0, 0, 0)
	coachB, _, _ := coachSvc.CreateCoach(ctx, accountB.ID, "Bob", 0, 0, 0)

	teamSvc.SaveTeam(ctx, coachA.ID, 1, "AliceTeam", nil)
	teamSvc.SaveTeam(ctx, coachB.ID, 1, "BobTeam", nil)

	teamsA, _ := teamSvc.ListTeams(ctx, coachA.ID)
	teamsB, _ := teamSvc.ListTeams(ctx, coachB.ID)
	if len(teamsA) != 1 || teamsA[0].Name != "AliceTeam" {
		t.Errorf("teamsA = %+v", teamsA)
	}
	if len(teamsB) != 1 || teamsB[0].Name != "BobTeam" {
		t.Errorf("teamsB = %+v", teamsB)
	}
}
