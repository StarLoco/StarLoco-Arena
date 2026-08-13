package e2e

import "testing"

// The game server used to hand out administrator rights to anybody who logged
// in: unknown logins were auto-created with admin=true, and every existing
// non-admin account was promoted on each successful login. That was a
// deliberate convenience while is_admin only gated chat GM commands, but the
// web portal now gates account deletion, admin granting and impersonation on
// the same flag (see BUGS.md B-090).
//
// These tests hold the boundary: exactly one account — the first on a fresh
// server — is created as an admin, and logging in never changes the flag.

func TestFirstAccountBecomesAdmin(t *testing.T) {
	st, addr := testServerWithStore(t)

	dialLogin(t, addr, "owner", "Owner")

	acc, err := st.Accounts.FindByName("owner")
	if err != nil {
		t.Fatalf("FindByName: %v", err)
	}
	if !acc.IsAdmin {
		t.Error("the first account on an empty server should be the administrator, " +
			"otherwise a fresh install has nobody who can run GM commands or open the console")
	}
}

func TestLaterAccountsAreNotAdmin(t *testing.T) {
	st, addr := testServerWithStore(t)

	dialLogin(t, addr, "owner", "Owner")
	dialLogin(t, addr, "player", "Player")

	acc, err := st.Accounts.FindByName("player")
	if err != nil {
		t.Fatalf("FindByName: %v", err)
	}
	if acc.IsAdmin {
		t.Error("an ordinary player auto-created by logging in must NOT be an administrator")
	}
}

// TestLoginDoesNotGrantAdmin is the important one: the removed code promoted on
// *every* login, so a second connection by an already-existing ordinary account
// was what actually leaked the flag.
func TestLoginDoesNotGrantAdmin(t *testing.T) {
	st, addr := testServerWithStore(t)

	dialLogin(t, addr, "owner", "Owner")

	// Create an ordinary account directly, then let it log in twice.
	if _, err := st.Accounts.CreateAccount("plain", "pw", false); err != nil {
		t.Fatalf("CreateAccount: %v", err)
	}
	c, _ := dialLogin(t, addr, "plain", "Plain")
	_ = c.Close()
	dialLogin(t, addr, "plain", "")

	acc, err := st.Accounts.FindByName("plain")
	if err != nil {
		t.Fatalf("FindByName: %v", err)
	}
	if acc.IsAdmin {
		t.Error("logging in promoted an ordinary account to administrator")
	}
}
