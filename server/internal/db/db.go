// Package db wires up the GORM connection for whichever dialect is
// configured (sqlite, postgres, mysql) and applies connection-pool tuning.
// See go-server/docs/03-data-model.md for the multi-DB rationale.
package db

import (
	"context"
	"database/sql"
	"fmt"
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
		return sqlite.Open(cfg.DSN), nil
	case "postgres":
		return postgres.Open(cfg.DSN), nil
	case "mysql":
		return mysql.Open(cfg.DSN), nil
	default:
		return nil, fmt.Errorf("db: unsupported driver %q", cfg.Driver)
	}
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
