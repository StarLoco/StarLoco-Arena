package service_test

import (
	"context"
	"errors"
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

func TestRegisterSuccess(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewAccountService(gdb)

	acc, err := svc.Register(context.Background(), "player_one", "hunter2pw", false)
	if err != nil {
		t.Fatalf("Register: %v", err)
	}
	if acc.ID == 0 {
		t.Error("expected a persisted account with a non-zero ID")
	}
	if acc.IsAdmin {
		t.Error("public registration must not create an admin")
	}
	if acc.PasswordHash == "hunter2pw" {
		t.Error("password stored in cleartext")
	}
}

func TestRegisterRejectsDuplicateNameCaseInsensitive(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewAccountService(gdb)
	ctx := context.Background()

	if _, err := svc.Register(ctx, "Bob", "password", false); err != nil {
		t.Fatalf("first Register: %v", err)
	}
	_, err := svc.Register(ctx, "bob", "password", false)
	if !errors.Is(err, service.ErrAccountNameTaken) {
		t.Errorf("err = %v, want ErrAccountNameTaken", err)
	}
}

func TestRegisterValidation(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewAccountService(gdb)
	ctx := context.Background()

	if _, err := svc.Register(ctx, "ab", "password", false); !errors.Is(err, service.ErrAccountNameInvalid) {
		t.Errorf("short name err = %v, want ErrAccountNameInvalid", err)
	}
	if _, err := svc.Register(ctx, "bad name!", "password", false); !errors.Is(err, service.ErrAccountNameInvalid) {
		t.Errorf("bad-char name err = %v, want ErrAccountNameInvalid", err)
	}
	if _, err := svc.Register(ctx, "gooduser", "short", false); !errors.Is(err, service.ErrPasswordTooShort) {
		t.Errorf("short password err = %v, want ErrPasswordTooShort", err)
	}
}

func TestVerifyPassword(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewAccountService(gdb)
	ctx := context.Background()

	if _, err := svc.Register(ctx, "alice", "correcthorse", false); err != nil {
		t.Fatalf("Register: %v", err)
	}

	if _, err := svc.VerifyPassword(ctx, "alice", "correcthorse"); err != nil {
		t.Errorf("VerifyPassword valid: %v", err)
	}
	if _, err := svc.VerifyPassword(ctx, "alice", "wrongpass"); !errors.Is(err, service.ErrWrongPassword) {
		t.Errorf("wrong password err = %v, want ErrWrongPassword", err)
	}
	if _, err := svc.VerifyPassword(ctx, "ghost", "whatever"); !errors.Is(err, service.ErrAccountNotFound) {
		t.Errorf("unknown account err = %v, want ErrAccountNotFound", err)
	}
}

func TestChangePassword(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewAccountService(gdb)
	ctx := context.Background()

	acc, _ := svc.Register(ctx, "alice", "oldpassword", false)
	if err := svc.ChangePassword(ctx, acc.ID, "newpassword"); err != nil {
		t.Fatalf("ChangePassword: %v", err)
	}
	if _, err := svc.VerifyPassword(ctx, "alice", "newpassword"); err != nil {
		t.Errorf("verify new password: %v", err)
	}
	if _, err := svc.VerifyPassword(ctx, "alice", "oldpassword"); !errors.Is(err, service.ErrWrongPassword) {
		t.Errorf("old password should no longer work, got %v", err)
	}
	if err := svc.ChangePassword(ctx, acc.ID, "short"); !errors.Is(err, service.ErrPasswordTooShort) {
		t.Errorf("short new password err = %v, want ErrPasswordTooShort", err)
	}
}

func TestSetAdmin(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewAccountService(gdb)
	ctx := context.Background()

	acc, _ := svc.Register(ctx, "mod", "password", false)
	if err := svc.SetAdmin(ctx, acc.ID, true); err != nil {
		t.Fatalf("SetAdmin: %v", err)
	}
	got, _ := svc.GetByID(ctx, acc.ID)
	if !got.IsAdmin {
		t.Error("account should be admin after SetAdmin(true)")
	}
}

func TestListAccountsSearchAndPaginate(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	svc := service.NewAccountService(gdb)
	ctx := context.Background()

	for _, name := range []string{"alpha", "alphabet", "beta", "gamma"} {
		if _, err := svc.Register(ctx, name, "password", false); err != nil {
			t.Fatalf("Register %s: %v", name, err)
		}
	}

	items, total, err := svc.ListAccounts(ctx, service.ListAccountsParams{Search: "alpha"})
	if err != nil {
		t.Fatalf("ListAccounts: %v", err)
	}
	if total != 2 {
		t.Errorf("total = %d, want 2 (alpha, alphabet)", total)
	}
	if len(items) != 2 {
		t.Errorf("len(items) = %d, want 2", len(items))
	}

	// Pagination: limit 2, first page returns 2, second returns the rest.
	page1, total, _ := svc.ListAccounts(ctx, service.ListAccountsParams{Limit: 2, Offset: 0})
	if total != 4 || len(page1) != 2 {
		t.Errorf("page1: total=%d len=%d, want total=4 len=2", total, len(page1))
	}
	page2, _, _ := svc.ListAccounts(ctx, service.ListAccountsParams{Limit: 2, Offset: 2})
	if len(page2) != 2 {
		t.Errorf("page2 len = %d, want 2", len(page2))
	}
}

func TestGetAccountDetailDeep(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	accounts := service.NewAccountService(gdb)
	coaches := service.NewCoachService(gdb)
	fighters := service.NewFighterService(gdb)
	teams := service.NewTeamService(gdb)
	ctx := context.Background()

	acc, _ := accounts.Register(ctx, "hero", "password", false)
	coach, res, err := coaches.CreateCoach(ctx, acc.ID, "Heroina", 1, 2, 1)
	if err != nil || res != service.CoachCreationOK {
		t.Fatalf("CreateCoach: res=%v err=%v", res, err)
	}
	if _, err := coaches.AddCard(ctx, coach.ID, 100, 1, 0); err != nil {
		t.Fatalf("AddCard: %v", err)
	}
	f, err := fighters.CreateFighter(ctx, coach.ID, "Blade", 3, 0, 4, 100, []int32{10, 20}, []int32{30})
	if err != nil {
		t.Fatalf("CreateFighter: %v", err)
	}
	if _, err := teams.SaveTeam(ctx, coach.ID, -1, "Main", []uint{f.ID}); err != nil {
		t.Fatalf("SaveTeam: %v", err)
	}

	detail, err := accounts.GetAccountDetail(ctx, acc.ID)
	if err != nil {
		t.Fatalf("GetAccountDetail: %v", err)
	}
	if detail.Coach == nil || detail.Coach.Name != "Heroina" {
		t.Fatalf("coach not hydrated: %+v", detail.Coach)
	}
	if len(detail.Cards) != 1 {
		t.Errorf("cards = %d, want 1", len(detail.Cards))
	}
	if len(detail.Fighters) != 1 {
		t.Fatalf("fighters = %d, want 1", len(detail.Fighters))
	}
	if len(detail.Fighters[0].SpellIDs) != 2 {
		t.Errorf("fighter spells = %v, want 2", detail.Fighters[0].SpellIDs)
	}
	if len(detail.Teams) != 1 {
		t.Errorf("teams = %d, want 1", len(detail.Teams))
	}
}

func TestDeleteAccountCascadesAndBlocksConnected(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	accounts := service.NewAccountService(gdb)
	coaches := service.NewCoachService(gdb)
	ctx := context.Background()

	acc, _ := accounts.Register(ctx, "doomed", "password", false)
	coach, _, err := coaches.CreateCoach(ctx, acc.ID, "Doomedcoach", 1, 1, 0)
	if err != nil {
		t.Fatalf("CreateCoach: %v", err)
	}
	if _, err := coaches.AddCard(ctx, coach.ID, 100, 1, 0); err != nil {
		t.Fatalf("AddCard: %v", err)
	}

	// Connected accounts can't be deleted.
	if err := gdb.Model(&domain.Account{}).Where("id = ?", acc.ID).Update("connected", true).Error; err != nil {
		t.Fatalf("mark connected: %v", err)
	}
	if err := accounts.DeleteAccount(ctx, acc.ID); !errors.Is(err, service.ErrAccountConnected) {
		t.Errorf("delete connected err = %v, want ErrAccountConnected", err)
	}

	// After disconnect, deletion removes the account and cascades to coach + cards.
	if err := gdb.Model(&domain.Account{}).Where("id = ?", acc.ID).Update("connected", false).Error; err != nil {
		t.Fatalf("mark disconnected: %v", err)
	}
	if err := accounts.DeleteAccount(ctx, acc.ID); err != nil {
		t.Fatalf("DeleteAccount: %v", err)
	}

	var accCount, coachCount, cardCount int64
	gdb.Model(&domain.Account{}).Where("id = ?", acc.ID).Count(&accCount)
	gdb.Model(&domain.Coach{}).Where("id = ?", coach.ID).Count(&coachCount)
	gdb.Model(&domain.CoachCard{}).Where("coach_id = ?", coach.ID).Count(&cardCount)
	if accCount != 0 || coachCount != 0 || cardCount != 0 {
		t.Errorf("after delete: accounts=%d coachs=%d cards=%d, want all 0", accCount, coachCount, cardCount)
	}
}
