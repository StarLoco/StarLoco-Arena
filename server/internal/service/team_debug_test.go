package service_test

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

// TestSaveTeamWhenCoachAlreadyHasWeirdSlots reproduces the reported
// scenario more faithfully: the coach already has teams saved with the
// legacy slot values (-1, 0) from an older server build, then saves a new
// team. Verifies the new team's fighters actually persist.
func TestSaveTeamWhenCoachAlreadyHasWeirdSlots(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	teamSvc := service.NewTeamService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	f1, _ := fighterSvc.CreateFighter(ctx, coach.ID, "F1", 1, 0, 0, 0, nil, nil)
	f2, _ := fighterSvc.CreateFighter(ctx, coach.ID, "F2", 1, 0, 0, 0, nil, nil)

	// Simulate pre-existing legacy teams with slot -1 and 0 (like the
	// reported live DB) inserted directly, bypassing SaveTeam's allocation.
	gdb.Exec("INSERT INTO teams (coach_id, slot, name) VALUES (?, -1, 'legacy1')", coach.ID)
	gdb.Exec("INSERT INTO teams (coach_id, slot, name) VALUES (?, 0, 'legacy2')", coach.ID)

	// Now save a new team the normal way.
	team, err := teamSvc.SaveTeam(ctx, coach.ID, -1, "NewSquad", []uint{f1.ID, f2.ID})
	if err != nil {
		t.Fatalf("SaveTeam: %v", err)
	}

	var count int64
	gdb.Table("team_fighters").Where("team_id = ?", team.ID).Count(&count)
	if count != 2 {
		t.Fatalf("team_fighters rows for new team = %d, want 2", count)
	}
}
