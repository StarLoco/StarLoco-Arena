package service_test

import (
	"context"
	"testing"

	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

func newStatsTestCoach(t *testing.T, gdb *gorm.DB, name string) *domain.Coach {
	t.Helper()
	coach := &domain.Coach{Name: name, Skin: 1, Hair: 1, Sex: 0}
	if err := gdb.Create(coach).Error; err != nil {
		t.Fatalf("create coach: %v", err)
	}
	return coach
}

func TestApplyFightResult_WinThenLoss(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewCoachService(gdb)
	ctx := context.Background()

	coach := newStatsTestCoach(t, gdb, "winner")

	// First win: unranked (0) seeds to StrengthUnranked (1000) then +delta.
	got, err := svc.ApplyFightResult(ctx, coach.ID, true, 90)
	if err != nil {
		t.Fatalf("ApplyFightResult win: %v", err)
	}
	if got.StatFights != 1 || got.StatWins != 1 || got.StatLosses != 0 {
		t.Errorf("after win: fights=%d wins=%d losses=%d, want 1/1/0", got.StatFights, got.StatWins, got.StatLosses)
	}
	if got.ConsecutiveWins != 1 {
		t.Errorf("after win: consecutiveWins=%d, want 1", got.ConsecutiveWins)
	}
	if got.TimeInFightSecs != 90 {
		t.Errorf("after win: timeInFight=%d, want 90", got.TimeInFightSecs)
	}
	wantStrength := domain.StrengthUnranked + domain.StrengthDelta
	if got.Strength != wantStrength {
		t.Errorf("after win: strength=%d, want %d", got.Strength, wantStrength)
	}

	// Second win: consecutive streak grows.
	got, err = svc.ApplyFightResult(ctx, coach.ID, true, 30)
	if err != nil {
		t.Fatalf("ApplyFightResult win2: %v", err)
	}
	if got.ConsecutiveWins != 2 {
		t.Errorf("after 2nd win: consecutiveWins=%d, want 2", got.ConsecutiveWins)
	}
	if got.TimeInFightSecs != 120 {
		t.Errorf("after 2nd win: timeInFight=%d, want 120", got.TimeInFightSecs)
	}

	// A loss resets the consecutive-win streak and drops strength.
	got, err = svc.ApplyFightResult(ctx, coach.ID, false, 10)
	if err != nil {
		t.Fatalf("ApplyFightResult loss: %v", err)
	}
	if got.StatFights != 3 || got.StatWins != 2 || got.StatLosses != 1 {
		t.Errorf("after loss: fights=%d wins=%d losses=%d, want 3/2/1", got.StatFights, got.StatWins, got.StatLosses)
	}
	if got.ConsecutiveWins != 0 {
		t.Errorf("after loss: consecutiveWins=%d, want 0", got.ConsecutiveWins)
	}
	if got.Strength != wantStrength+domain.StrengthDelta-domain.StrengthDelta {
		t.Errorf("after loss: strength=%d, want %d", got.Strength, wantStrength+domain.StrengthDelta-domain.StrengthDelta)
	}

	// Values must be persisted, not just returned.
	var reloaded domain.Coach
	if err := gdb.First(&reloaded, coach.ID).Error; err != nil {
		t.Fatalf("reload coach: %v", err)
	}
	if reloaded.StatFights != 3 || reloaded.ConsecutiveWins != 0 {
		t.Errorf("persisted: fights=%d consecutiveWins=%d, want 3/0", reloaded.StatFights, reloaded.ConsecutiveWins)
	}
}

func TestAddPlayTime(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewCoachService(gdb)
	ctx := context.Background()

	coach := newStatsTestCoach(t, gdb, "player")

	if err := svc.AddPlayTime(ctx, coach.ID, 100); err != nil {
		t.Fatalf("AddPlayTime: %v", err)
	}
	if err := svc.AddPlayTime(ctx, coach.ID, 50); err != nil {
		t.Fatalf("AddPlayTime 2: %v", err)
	}
	// Non-positive is a no-op.
	if err := svc.AddPlayTime(ctx, coach.ID, 0); err != nil {
		t.Fatalf("AddPlayTime 0: %v", err)
	}

	var reloaded domain.Coach
	if err := gdb.First(&reloaded, coach.ID).Error; err != nil {
		t.Fatalf("reload coach: %v", err)
	}
	if reloaded.TotalPlayTimeSecs != 150 {
		t.Errorf("totalPlayTime=%d, want 150", reloaded.TotalPlayTimeSecs)
	}
}
