package store

import (
	"errors"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// seedAccountWithCoach builds an account that owns a coach with one of
// everything hanging off it, so a delete has something to actually cascade
// through.
func seedAccountWithCoach(t *testing.T, s *Store, login, coachName string) (*domain.Account, *domain.Coach) {
	t.Helper()

	acc, err := s.Accounts.CreateAccount(login, "pw", false)
	if err != nil {
		t.Fatalf("CreateAccount(%s): %v", login, err)
	}
	coach, err := s.Coaches.Create(acc.ID, coachName, 1, 1, 0)
	if err != nil {
		t.Fatalf("Coaches.Create(%s): %v", coachName, err)
	}
	if err := s.Accounts.LinkCoach(acc.ID, coach.ID); err != nil {
		t.Fatalf("LinkCoach: %v", err)
	}

	f := &domain.Fighter{CoachID: coach.ID, BreedID: 1, Name: coachName + "F"}
	if err := s.Fighters.Create(f); err != nil {
		t.Fatalf("Fighters.Create: %v", err)
	}
	if err := s.Fighters.SaveConditions(f.ID, []domain.FighterCondition{
		{FighterID: f.ID, ConditionID: 7, Remaining: -1},
	}); err != nil {
		t.Fatalf("SaveConditions: %v", err)
	}
	if err := s.Coaches.GrantCards(coach.ID, []GrantCard{{TemplateID: 900, Quantity: 1}}); err != nil {
		t.Fatalf("GrantCards: %v", err)
	}

	reloaded, err := s.Accounts.FindByID(acc.ID)
	if err != nil {
		t.Fatalf("FindByID: %v", err)
	}
	return reloaded, coach
}

func TestFindByIDLoadsCoach(t *testing.T) {
	s := newTestStore(t)
	acc, coach := seedAccountWithCoach(t, s, "finder", "Finder")

	got, err := s.Accounts.FindByID(acc.ID)
	if err != nil {
		t.Fatalf("FindByID: %v", err)
	}
	if got.Name != "finder" {
		t.Errorf("name = %q, want %q", got.Name, "finder")
	}
	if got.Coach == nil || got.Coach.ID != coach.ID {
		t.Error("FindByID should preload the coach — the portal renders it on every page")
	}

	if _, err := s.Accounts.FindByID(99999); !errors.Is(err, ErrNotFound) {
		t.Errorf("missing account: err = %v, want ErrNotFound", err)
	}
}

func TestDeleteAccountRemovesEverything(t *testing.T) {
	s := newTestStore(t)
	acc, coach := seedAccountWithCoach(t, s, "doomed", "Doomed")

	// A second account survives, proving the delete is scoped.
	keep, keepCoach := seedAccountWithCoach(t, s, "keeper", "Keeper")

	if err := s.Accounts.DeleteAccount(acc.ID); err != nil {
		t.Fatalf("DeleteAccount: %v", err)
	}

	if _, err := s.Accounts.FindByID(acc.ID); !errors.Is(err, ErrNotFound) {
		t.Errorf("account still present: err = %v", err)
	}
	if _, err := s.Coaches.Get(coach.ID); !errors.Is(err, ErrNotFound) {
		t.Errorf("coach survived the account delete: err = %v", err)
	}

	// Every child table must be empty for the deleted coach.
	//
	// The fighter_conditions row is guaranteed by the schema's ON DELETE
	// CASCADE rather than by DeleteCoach's explicit delete, so removing that
	// delete would not fail this test. It is asserted anyway because the
	// invariant — no wound rows outliving their fighter — is what matters, and
	// SQLite reuses rowids, so an orphan really could be inherited by a later
	// fighter if the cascade were ever lost.
	for _, tc := range []struct {
		table string
		where string
		arg   any
	}{
		{"fighters", "coach_id = ?", coach.ID},
		{"coach_cards", "coach_id = ?", coach.ID},
		{"teams", "coach_id = ?", coach.ID},
		{"fighter_conditions", "fighter_id NOT IN (SELECT id FROM fighters)", nil},
	} {
		var n int64
		q := s.DB().Table(tc.table)
		if tc.arg != nil {
			q = q.Where(tc.where, tc.arg)
		} else {
			q = q.Where(tc.where)
		}
		if err := q.Count(&n).Error; err != nil {
			t.Fatalf("count %s: %v", tc.table, err)
		}
		if n != 0 {
			t.Errorf("%s: %d row(s) left behind after the account was deleted", tc.table, n)
		}
	}

	// The bystander is untouched.
	if _, err := s.Accounts.FindByID(keep.ID); err != nil {
		t.Errorf("the other account was collateral damage: %v", err)
	}
	if _, err := s.Coaches.Get(keepCoach.ID); err != nil {
		t.Errorf("the other coach was collateral damage: %v", err)
	}
}

func TestDeleteAccountRefusesWhileConnected(t *testing.T) {
	s := newTestStore(t)
	acc, _ := seedAccountWithCoach(t, s, "online", "Online")

	if err := s.Accounts.SetConnected(acc.ID, true); err != nil {
		t.Fatalf("SetConnected: %v", err)
	}

	err := s.Accounts.DeleteAccount(acc.ID)
	if !errors.Is(err, ErrAccountConnected) {
		t.Fatalf("DeleteAccount while connected: err = %v, want ErrAccountConnected", err)
	}
	if _, err := s.Accounts.FindByID(acc.ID); err != nil {
		t.Errorf("a refused delete must not have removed anything: %v", err)
	}

	// Once they log out it goes through.
	if err := s.Accounts.SetConnected(acc.ID, false); err != nil {
		t.Fatalf("SetConnected: %v", err)
	}
	if err := s.Accounts.DeleteAccount(acc.ID); err != nil {
		t.Fatalf("DeleteAccount after disconnect: %v", err)
	}
}

func TestCountConnected(t *testing.T) {
	s := newTestStore(t)
	a, _ := seedAccountWithCoach(t, s, "one", "One")
	seedAccountWithCoach(t, s, "two", "Two")

	if n, err := s.Accounts.CountConnected(); err != nil || n != 0 {
		t.Fatalf("CountConnected on a quiet server = %d, %v; want 0, nil", n, err)
	}
	if err := s.Accounts.SetConnected(a.ID, true); err != nil {
		t.Fatalf("SetConnected: %v", err)
	}
	if n, err := s.Accounts.CountConnected(); err != nil || n != 1 {
		t.Fatalf("CountConnected = %d, %v; want 1, nil", n, err)
	}
}

func TestListAccountsSearchAndPaging(t *testing.T) {
	s := newTestStore(t)
	seedAccountWithCoach(t, s, "alice", "Alcedon")
	seedAccountWithCoach(t, s, "bob", "Bombard")
	seedAccountWithCoach(t, s, "carol", "Cralove")
	// An account with no coach must still be listed, otherwise someone who
	// just registered on the website is invisible to the admin console.
	if _, err := s.Accounts.CreateAccount("dave", "pw", false); err != nil {
		t.Fatalf("CreateAccount: %v", err)
	}

	rows, total, err := s.Accounts.ListAccounts("", 0, 25)
	if err != nil {
		t.Fatalf("ListAccounts: %v", err)
	}
	if total != 4 || len(rows) != 4 {
		t.Fatalf("unfiltered: total=%d rows=%d, want 4 and 4", total, len(rows))
	}
	var sawCoachless bool
	for _, r := range rows {
		if r.Name == "dave" {
			sawCoachless = true
			if r.CoachName != "" {
				t.Errorf("coachless account reported coach %q", r.CoachName)
			}
		}
		if r.Name == "alice" && r.CoachName != "Alcedon" {
			t.Errorf("alice's coach = %q, want Alcedon", r.CoachName)
		}
	}
	if !sawCoachless {
		t.Error("the account with no coach was dropped by the LEFT JOIN")
	}

	// Search matches the ACCOUNT name...
	rows, total, err = s.Accounts.ListAccounts("ali", 0, 25)
	if err != nil {
		t.Fatalf("ListAccounts: %v", err)
	}
	if total != 1 || len(rows) != 1 || rows[0].Name != "alice" {
		t.Fatalf("search by account name: total=%d rows=%v", total, rows)
	}

	// ...and the COACH name, which is the only name most players know.
	rows, total, err = s.Accounts.ListAccounts("bomb", 0, 25)
	if err != nil {
		t.Fatalf("ListAccounts: %v", err)
	}
	if total != 1 || len(rows) != 1 || rows[0].Name != "bob" {
		t.Fatalf("search by coach name: total=%d rows=%v", total, rows)
	}

	// Case-insensitive.
	if _, total, err = s.Accounts.ListAccounts("ALICE", 0, 25); err != nil || total != 1 {
		t.Fatalf("case-insensitive search: total=%d, %v", total, err)
	}

	// Paging: page size 2 over 4 rows, and the total stays the FULL count so
	// the console can render "page 1 of 2".
	page1, total, err := s.Accounts.ListAccounts("", 0, 2)
	if err != nil {
		t.Fatalf("ListAccounts: %v", err)
	}
	if total != 4 || len(page1) != 2 {
		t.Fatalf("page 1: total=%d len=%d, want 4 and 2", total, len(page1))
	}
	page2, _, err := s.Accounts.ListAccounts("", 2, 2)
	if err != nil {
		t.Fatalf("ListAccounts: %v", err)
	}
	if len(page2) != 2 {
		t.Fatalf("page 2 len=%d, want 2", len(page2))
	}
	if page1[0].ID == page2[0].ID {
		t.Error("page 2 repeated page 1 — offset is not applied")
	}
}
