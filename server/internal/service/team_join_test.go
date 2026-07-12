package service_test

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

// TestSaveTeamActuallyWritesJoinRows directly inspects the team_fighters
// join table after a save, to catch the "association Replace silently
// wrote nothing" bug.
func TestSaveTeamActuallyWritesJoinRows(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	coachSvc := service.NewCoachService(gdb)
	fighterSvc := service.NewFighterService(gdb)
	teamSvc := service.NewTeamService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")
	ctx := context.Background()
	coach, _, _ := coachSvc.CreateCoach(ctx, account.ID, "Alice", 0, 0, 0)

	f1, _ := fighterSvc.CreateFighter(ctx, coach.ID, "F1", 1, 0, 0, 0, nil, nil)
	f2, _ := fighterSvc.CreateFighter(ctx, coach.ID, "F2", 1, 0, 0, 0, nil, nil)

	team, err := teamSvc.SaveTeam(ctx, coach.ID, -1, "Squad", []uint{f1.ID, f2.ID})
	if err != nil {
		t.Fatalf("SaveTeam: %v", err)
	}

	// Direct join-table inspection.
	type joinRow struct {
		TeamID    uint
		FighterID uint
	}
	var rows []joinRow
	if err := gdb.Table("team_fighters").Where("team_id = ?", team.ID).Scan(&rows).Error; err != nil {
		t.Fatalf("query team_fighters: %v", err)
	}
	if len(rows) != 2 {
		t.Fatalf("team_fighters rows = %d, want 2 -- association Replace did not persist", len(rows))
	}

	// Also verify via a fresh reload with preload.
	var reloaded domain.Team
	if err := gdb.Preload("Fighters").First(&reloaded, team.ID).Error; err != nil {
		t.Fatalf("reload team: %v", err)
	}
	if len(reloaded.Fighters) != 2 {
		t.Errorf("reloaded team fighters = %d, want 2", len(reloaded.Fighters))
	}
}
