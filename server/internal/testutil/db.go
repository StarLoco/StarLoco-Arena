// Package testutil provides shared test helpers: an in-memory SQLite
// database with the full schema applied (for service-layer tests) and
// small domain-object factories.
package testutil

import (
	"testing"

	"github.com/rs/zerolog"
	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/db"
	"github.com/dofusarena/go-server/migrations"
)

// NewTestDB opens a fresh in-memory SQLite database, applies every
// migration, and returns the *gorm.DB. Each call gets an isolated
// database (via a unique in-memory DSN), so tests never interfere with
// each other even when run in parallel.
func NewTestDB(t *testing.T) *gorm.DB {
	t.Helper()

	db.SetMigrationsFS(migrations.FS)

	cfg := config.DatabaseConfig{
		Driver: "sqlite",
		// A named in-memory database (rather than ":memory:") combined
		// with cache=shared keeps the same DB alive across the pool's
		// connections for the lifetime of this *testing.T, while the
		// unique name (test name) isolates it from other tests' DBs
		// running in the same process.
		//
		// NOTE: the glebarez/go-sqlite driver (unlike mattn/go-sqlite3)
		// does NOT support a bare "_foreign_keys=on" query param -- it
		// only recognizes "_pragma", "_time_format", and "_txlock". Using
		// the mattn-style param here silently no-ops, which would leave
		// foreign key enforcement (and therefore ON DELETE CASCADE)
		// disabled without any error. Always use "_pragma=foreign_keys(1)".
		DSN:          "file:" + t.Name() + "?mode=memory&cache=shared&_pragma=foreign_keys(1)",
		MaxOpenConns: 1,
	}

	gdb, err := db.Open(cfg, zerolog.Nop())
	if err != nil {
		t.Fatalf("testutil: open in-memory sqlite: %v", err)
	}

	if err := db.Migrate(gdb, cfg); err != nil {
		t.Fatalf("testutil: apply migrations: %v", err)
	}

	t.Cleanup(func() {
		sqlDB, err := gdb.DB()
		if err == nil {
			_ = sqlDB.Close()
		}
	})

	return gdb
}
