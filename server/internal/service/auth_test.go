package service_test

import (
	"context"
	"testing"

	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

func createTestAccount(t *testing.T, gdb *gorm.DB, login, password string) *domain.Account {
	t.Helper()
	hash, err := service.HashPassword(password)
	if err != nil {
		t.Fatalf("HashPassword: %v", err)
	}
	account := &domain.Account{Name: login, PasswordHash: hash}
	if err := gdb.Create(account).Error; err != nil {
		t.Fatalf("create account: %v", err)
	}
	return account
}

func TestAuthenticateSuccess(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	auth := service.NewAuthService(gdb)
	createTestAccount(t, gdb, "alice", "correct-password")

	account, result, err := auth.Authenticate(context.Background(), "alice", "correct-password")
	if err != nil {
		t.Fatalf("Authenticate: %v", err)
	}
	if result != service.AuthResultOK {
		t.Fatalf("result = %v, want AuthResultOK", result)
	}
	if !account.Connected {
		t.Error("account should be marked connected after successful auth")
	}
}

func TestAuthenticateWrongPassword(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	auth := service.NewAuthService(gdb)
	createTestAccount(t, gdb, "alice", "correct-password")

	_, result, err := auth.Authenticate(context.Background(), "alice", "wrong-password")
	if err != nil {
		t.Fatalf("Authenticate: %v", err)
	}
	if result != service.AuthResultInvalidLogin {
		t.Errorf("result = %v, want AuthResultInvalidLogin", result)
	}
}

func TestAuthenticateUnknownLogin(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	auth := service.NewAuthService(gdb)

	_, result, err := auth.Authenticate(context.Background(), "nobody", "whatever")
	if err != nil {
		t.Fatalf("Authenticate: %v", err)
	}
	if result != service.AuthResultInvalidLogin {
		t.Errorf("result = %v, want AuthResultInvalidLogin", result)
	}
}

func TestAuthenticateAlreadyConnectedRejected(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	auth := service.NewAuthService(gdb)
	createTestAccount(t, gdb, "alice", "pw")

	ctx := context.Background()
	_, result, err := auth.Authenticate(ctx, "alice", "pw")
	if err != nil || result != service.AuthResultOK {
		t.Fatalf("first login: result=%v err=%v", result, err)
	}

	// Second concurrent login attempt with correct credentials must be
	// rejected while the account is still marked connected.
	_, result, err = auth.Authenticate(ctx, "alice", "pw")
	if err != nil {
		t.Fatalf("Authenticate: %v", err)
	}
	if result != service.AuthResultAlreadyConnected {
		t.Errorf("result = %v, want AuthResultAlreadyConnected", result)
	}
}

func TestSetDisconnectedAllowsReLogin(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	auth := service.NewAuthService(gdb)
	account := createTestAccount(t, gdb, "alice", "pw")

	ctx := context.Background()
	auth.Authenticate(ctx, "alice", "pw") // first login, marks connected

	if err := auth.SetDisconnected(ctx, account.ID); err != nil {
		t.Fatalf("SetDisconnected: %v", err)
	}

	_, result, err := auth.Authenticate(ctx, "alice", "pw")
	if err != nil {
		t.Fatalf("Authenticate after disconnect: %v", err)
	}
	if result != service.AuthResultOK {
		t.Errorf("result after disconnect+relogin = %v, want AuthResultOK", result)
	}
}

func TestResetAllConnectedFlags(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	auth := service.NewAuthService(gdb)
	ctx := context.Background()

	createTestAccount(t, gdb, "alice", "pw")
	createTestAccount(t, gdb, "bob", "pw")
	auth.Authenticate(ctx, "alice", "pw")
	auth.Authenticate(ctx, "bob", "pw")

	if err := auth.ResetAllConnectedFlags(ctx); err != nil {
		t.Fatalf("ResetAllConnectedFlags: %v", err)
	}

	// Both should now be able to log in again.
	_, result, _ := auth.Authenticate(ctx, "alice", "pw")
	if result != service.AuthResultOK {
		t.Errorf("alice result after reset = %v, want OK", result)
	}
}

func TestPasswordNeverStoredInCleartext(t *testing.T) {
	gdb := testutil.NewTestDB(t)
	account := createTestAccount(t, gdb, "alice", "super-secret-password")

	if account.PasswordHash == "super-secret-password" {
		t.Fatal("password hash equals the plaintext password -- not hashed!")
	}
	if len(account.PasswordHash) < 20 {
		t.Errorf("password hash looks too short to be a real bcrypt hash: %q", account.PasswordHash)
	}
}
