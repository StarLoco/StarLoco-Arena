package db

import (
	"database/sql"
	"fmt"
	"io/fs"
	"sort"
	"strings"
)

// sqliteMigrationsTable mirrors golang-migrate's DefaultMigrationsTable
// naming convention closely enough for operator familiarity, though this is
// an independent, much simpler implementation (see the note in migrate.go
// on why SQLite can't share golang-migrate's own sqlite driver package).
const sqliteMigrationsTable = "schema_migrations"

// sqliteMigrate applies every "*.up.sql" file in dir, in filename order,
// exactly once, tracked via a simple version table. It intentionally does
// not support "down" migrations (rollback) -- for local dev/test SQLite
// usage, recreating the file is simpler and safer than reverse migrations.
func sqliteMigrate(sqlDB *sql.DB, dir fs.FS) error {
	if _, err := sqlDB.Exec(fmt.Sprintf(
		`CREATE TABLE IF NOT EXISTS %s (version TEXT PRIMARY KEY, applied_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP)`,
		sqliteMigrationsTable,
	)); err != nil {
		return fmt.Errorf("db: create sqlite migrations table: %w", err)
	}

	entries, err := fs.ReadDir(dir, ".")
	if err != nil {
		return fmt.Errorf("db: read sqlite migrations dir: %w", err)
	}

	var upFiles []string
	for _, e := range entries {
		if !e.IsDir() && strings.HasSuffix(e.Name(), ".up.sql") {
			upFiles = append(upFiles, e.Name())
		}
	}
	sort.Strings(upFiles)

	for _, name := range upFiles {
		version := strings.TrimSuffix(name, ".up.sql")

		var exists int
		err := sqlDB.QueryRow(fmt.Sprintf(`SELECT COUNT(1) FROM %s WHERE version = ?`, sqliteMigrationsTable), version).Scan(&exists)
		if err != nil {
			return fmt.Errorf("db: check applied migration %s: %w", version, err)
		}
		if exists > 0 {
			continue
		}

		content, err := fs.ReadFile(dir, name)
		if err != nil {
			return fmt.Errorf("db: read migration %s: %w", name, err)
		}

		tx, err := sqlDB.Begin()
		if err != nil {
			return fmt.Errorf("db: begin tx for migration %s: %w", name, err)
		}
		if _, err := tx.Exec(string(content)); err != nil {
			_ = tx.Rollback()
			return fmt.Errorf("db: apply migration %s: %w", name, err)
		}
		if _, err := tx.Exec(fmt.Sprintf(`INSERT INTO %s (version) VALUES (?)`, sqliteMigrationsTable), version); err != nil {
			_ = tx.Rollback()
			return fmt.Errorf("db: record migration %s: %w", name, err)
		}
		if err := tx.Commit(); err != nil {
			return fmt.Errorf("db: commit migration %s: %w", name, err)
		}
	}

	return nil
}
