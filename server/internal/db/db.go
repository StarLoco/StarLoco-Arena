// Package db wires up the GORM connection for whichever dialect is
// configured (sqlite, postgres, mysql) and applies connection-pool tuning.
// See go-server/docs/03-data-model.md for the multi-DB rationale.
package db

import (
	"context"
	"database/sql"
	"fmt"
	"strings"
	"time"

	"github.com/glebarez/sqlite"
	"github.com/rs/zerolog"
	"gorm.io/driver/mysql"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	gormlogger "gorm.io/gorm/logger"

	"github.com/dofusarena/go-server/internal/config"
)

// Open constructs a *gorm.DB for the configured driver and tunes the
// underlying connection pool. It does not block on a network round-trip:
// callers should use PingAsync to verify connectivity without delaying
// server startup (see docs/01-architecture.md §1.4.1).
func Open(cfg config.DatabaseConfig, logger zerolog.Logger) (*gorm.DB, error) {
	dialector, err := dialectorFor(cfg)
	if err != nil {
		return nil, err
	}

	gdb, err := gorm.Open(dialector, &gorm.Config{
		Logger: newGormLogger(logger),
	})
	if err != nil {
		return nil, fmt.Errorf("db: open %s: %w", cfg.Driver, err)
	}

	sqlDB, err := gdb.DB()
	if err != nil {
		return nil, fmt.Errorf("db: get underlying sql.DB: %w", err)
	}
	tunePool(sqlDB, cfg)

	return gdb, nil
}

func dialectorFor(cfg config.DatabaseConfig) (gorm.Dialector, error) {
	switch cfg.Driver {
	case "sqlite":
		return sqlite.Open(applySQLitePragmaDefaults(cfg.DSN)), nil
	case "postgres":
		return postgres.Open(cfg.DSN), nil
	case "mysql":
		return mysql.Open(cfg.DSN), nil
	default:
		return nil, fmt.Errorf("db: unsupported driver %q", cfg.Driver)
	}
}

// sqlitePragmaDefaults are the connection pragmas we ensure are set for
// SQLite unless the operator has already specified them in the DSN. They
// materially affect write throughput under concurrent load (e.g. a bot
// swarm, or many clients disconnecting at once):
//
//   - synchronous(NORMAL): in WAL mode this is the recommended setting --
//     it stops fsync()'ing on every single commit (the FULL default), which
//     is what makes a burst of small auto-committed UPDATEs (connected=false,
//     total_play_time_secs+=, fight-end stat writes) each pay a full disk
//     flush and serialize into tens of milliseconds apiece. NORMAL is still
//     crash-consistent in WAL mode; only a power loss at the wrong instant
//     can lose the very last committed transaction, an acceptable trade for
//     a game server (and irrelevant for dev/load-test databases).
//   - busy_timeout(5000): makes a writer WAIT up to 5s for the lock instead
//     of failing immediately with SQLITE_BUSY, so a write burst degrades to
//     latency rather than errors.
//
// journal_mode(WAL) and foreign_keys(1) are intentionally NOT forced here --
// the shipped configs already set them, and WAL in particular is a
// persistent database property, not a per-connection one.
var sqlitePragmaDefaults = map[string]string{
	"synchronous":  "NORMAL",
	"busy_timeout": "5000",
}

// applySQLitePragmaDefaults appends any missing performance pragmas to a
// SQLite DSN. An operator-supplied pragma of the same name always wins (we
// only add ones that aren't already present, by substring match on the
// pragma name).
func applySQLitePragmaDefaults(dsn string) string {
	sep := "?"
	if strings.Contains(dsn, "?") {
		sep = "&"
	}
	for name, val := range sqlitePragmaDefaults {
		if strings.Contains(dsn, name) {
			continue // operator already set this pragma
		}
		dsn += sep + "_pragma=" + name + "(" + val + ")"
		sep = "&"
	}
	return dsn
}

func tunePool(sqlDB *sql.DB, cfg config.DatabaseConfig) {
	maxOpen := cfg.MaxOpenConns
	if cfg.Driver == "sqlite" {
		// SQLite is single-writer; forcing a single connection avoids
		// SQLITE_BUSY errors under any concurrent write attempt.
		maxOpen = 1
	}
	if maxOpen > 0 {
		sqlDB.SetMaxOpenConns(maxOpen)
	}
	if cfg.MaxIdleConns > 0 {
		sqlDB.SetMaxIdleConns(cfg.MaxIdleConns)
	}
	if cfg.ConnMaxLifetime > 0 {
		sqlDB.SetConnMaxLifetime(cfg.ConnMaxLifetime)
	}
	if cfg.ConnMaxIdleTime > 0 {
		sqlDB.SetConnMaxIdleTime(cfg.ConnMaxIdleTime)
	}
}

// PingAsync verifies DB connectivity in the background with a bounded
// timeout and logs the outcome. It never blocks the caller and never
// terminates the process on failure -- the server should stay up and
// diagnosable even if the database is temporarily unreachable at boot.
func PingAsync(gdb *gorm.DB, logger zerolog.Logger, timeout time.Duration) {
	go func() {
		sqlDB, err := gdb.DB()
		if err != nil {
			logger.Error().Err(err).Msg("db: could not obtain underlying sql.DB for ping")
			return
		}
		ctx, cancel := context.WithTimeout(context.Background(), timeout)
		defer cancel()
		if err := sqlDB.PingContext(ctx); err != nil {
			logger.Warn().Err(err).Msg("db: initial connectivity check failed; server will continue starting and retry lazily")
			return
		}
		logger.Info().Msg("db: connectivity check succeeded")
	}()
}

func newGormLogger(logger zerolog.Logger) gormlogger.Interface {
	return &zerologGormLogger{logger: logger.With().Str("component", "gorm").Logger()}
}
