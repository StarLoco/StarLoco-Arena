// Package e2e exercises the full server binary's behavior end-to-end: real
// TCP connections, real (in-memory) SQLite persistence, and the full
// dispatch/service/world stack wired exactly as internal/app.New wires
// production. See go-server/docs/02-protocol.md for the wire formats these
// tests assert against.
package e2e

import (
	"context"
	"path/filepath"
	"testing"
	"time"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/service"
)

// startTestServer boots a fully-wired App against a fresh isolated
// in-memory SQLite database and returns it already listening, along with
// its bound address. The server is torn down automatically at test end.
func startTestServer(t *testing.T) (a *app.App, addr string) {
	t.Helper()
	return startTestServerConfigured(t, nil)
}

// startTestServerConfigured is like startTestServer, but lets the caller
// tweak the config (e.g. shortening the pre-fight forced-progress ready
// timers for a test that wants to actually observe a timeout firing)
// before the server boots. mutate may be nil.
func startTestServerConfigured(t *testing.T, mutate func(*config.Config)) (a *app.App, addr string) {
	t.Helper()

	cfg := config.Default()
	cfg.Server.ListenAddr = "127.0.0.1:0" // OS-assigned free port
	cfg.Server.AdminAddr = "127.0.0.1:0"  // OS-assigned free port, avoids clashing with other tests/a real server on the default 9090
	cfg.Database.Driver = "sqlite"
	cfg.Database.DSN = "file:" + t.Name() + "?mode=memory&cache=shared&_pragma=foreign_keys(1)"
	cfg.Database.MaxOpenConns = 1
	// Point at the real game-data files (relative to test/e2e/) so tests
	// that exercise spell/equipment IDs validate against actual card/spell
	// definitions. Repositories load lazily, so this costs nothing for
	// tests that don't touch game data.
	cfg.GameData.Dir = filepath.Join("..", "..", "data")
	// Fast clocks so any future placement/observation-timeout-driven tests
	// don't need to wait on production-length timers.
	cfg.Combat.PresentationClock = 2 * time.Second
	cfg.Combat.PlacementClock = 2 * time.Second
	cfg.Combat.ObservationClock = 2 * time.Second
	cfg.Combat.TurnClock = 2 * time.Second
	// Same rationale for the two pre-fight (pre-combat.Fight) forced-
	// progress ready timers -- see docs/08-java-parity-roadmap.md's
	// write-up on this fix.
	cfg.Combat.MatchReadyClock = 2 * time.Second
	cfg.Combat.PlacementReadyClock = 2 * time.Second

	if mutate != nil {
		mutate(&cfg)
	}

	logger := zerolog.Nop()

	a, err := app.New(cfg, logger, app.Options{})
	if err != nil {
		t.Fatalf("app.New: %v", err)
	}
	if err := a.Listen(); err != nil {
		t.Fatalf("Listen: %v", err)
	}

	ctx, cancel := context.WithCancel(context.Background())
	serveDone := make(chan struct{})
	go func() {
		defer close(serveDone)
		_ = a.Serve(ctx)
	}()

	t.Cleanup(func() {
		cancel()
		_ = a.Close()
		<-serveDone
	})

	return a, a.Addr()
}

// seedAccount creates a login-ready account directly via the app's own
// AuthService (exercising the real bcrypt hashing path), bypassing the
// wire protocol since account provisioning isn't itself a network opcode
// (see cmd/seedaccount's doc comment -- accounts are provisioned
// out-of-band in this design).
func seedAccount(t *testing.T, a *app.App, login, password string) {
	t.Helper()
	seedAccountWithAdmin(t, a, login, password, false)
}

// seedAdminAccount is like seedAccount but flags the account
// Account.IsAdmin, for tests exercising admin-gated behavior (the GM chat
// commands, see handlers_gm_commands.go).
func seedAdminAccount(t *testing.T, a *app.App, login, password string) {
	t.Helper()
	seedAccountWithAdmin(t, a, login, password, true)
}

func seedAccountWithAdmin(t *testing.T, a *app.App, login, password string, isAdmin bool) {
	t.Helper()
	hash, err := service.HashPassword(password)
	if err != nil {
		t.Fatalf("HashPassword: %v", err)
	}
	if err := a.DB.Exec("INSERT INTO accounts (name, password_hash, connected, is_admin) VALUES (?, ?, false, ?)", login, hash, isAdmin).Error; err != nil {
		t.Fatalf("seed account: %v", err)
	}
}
