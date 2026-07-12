package db

import (
	"database/sql"
	"errors"
	"fmt"
	"io/fs"

	"github.com/golang-migrate/migrate/v4"
	"github.com/golang-migrate/migrate/v4/database"
	"github.com/golang-migrate/migrate/v4/database/mysql"
	"github.com/golang-migrate/migrate/v4/database/postgres"
	"github.com/golang-migrate/migrate/v4/source/iofs"
	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/config"
)

// Note on SQLite: we deliberately do NOT use golang-migrate's
// database/sqlite driver here, even though it exists. That package imports
// modernc.org/sqlite directly, which self-registers a database/sql driver
// named "sqlite" in its init(). Our GORM sqlite dialect
// (github.com/glebarez/sqlite -> github.com/glebarez/go-sqlite) also
// registers a driver named "sqlite" in its own init(). Since Go's
// database/sql.Register panics on a duplicate name, importing both packages
// in the same binary is fatal at startup. Instead, sqliteMigrate below
// applies the same .up.sql files with a minimal hand-rolled runner that
// works against the already-open *sql.DB (see sqlite_migrate.go).

// migrationsFS holds the embedded migrations filesystem (rooted such that
// it contains top-level sqlite/, postgres/, mysql/ directories), wired in
// from cmd/server via SetMigrationsFS so this package stays free of a
// direct embed directive tied to the top-level source tree layout.
var migrationsFS fs.FS

// SetMigrationsFS registers the embedded migrations filesystem. Must be
// called once during startup, before Migrate.
func SetMigrationsFS(f fs.FS) {
	migrationsFS = f
}

// Migrate applies all pending migrations for the configured dialect.
func Migrate(gdb *gorm.DB, cfg config.DatabaseConfig) error {
	if migrationsFS == nil {
		return errors.New("db: migrations filesystem not registered, call SetMigrationsFS first")
	}

	sqlDB, err := gdb.DB()
	if err != nil {
		return fmt.Errorf("db: get underlying sql.DB: %w", err)
	}

	sub, err := fs.Sub(migrationsFS, cfg.Driver)
	if err != nil {
		return fmt.Errorf("db: locate migrations for %s: %w", cfg.Driver, err)
	}

	// SQLite goes through a minimal hand-rolled runner to avoid a
	// database/sql driver-registration conflict with golang-migrate's own
	// sqlite driver package, see the note above.
	if cfg.Driver == "sqlite" {
		return sqliteMigrate(sqlDB, sub)
	}

	source, err := iofs.New(sub, ".")
	if err != nil {
		return fmt.Errorf("db: init migration source: %w", err)
	}

	driver, err := newMigrateDriver(cfg.Driver, sqlDB)
	if err != nil {
		return err
	}

	m, err := migrate.NewWithInstance("iofs", source, cfg.Driver, driver)
	if err != nil {
		return fmt.Errorf("db: init migrate instance: %w", err)
	}

	if err := m.Up(); err != nil && !errors.Is(err, migrate.ErrNoChange) {
		return fmt.Errorf("db: apply migrations: %w", err)
	}
	return nil
}

func newMigrateDriver(driverName string, sqlDB *sql.DB) (database.Driver, error) {
	switch driverName {
	case "postgres":
		return postgres.WithInstance(sqlDB, &postgres.Config{})
	case "mysql":
		return mysql.WithInstance(sqlDB, &mysql.Config{})
	default:
		return nil, fmt.Errorf("db: unsupported migration driver %q", driverName)
	}
}
