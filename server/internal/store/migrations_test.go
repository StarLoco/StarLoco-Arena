package store

import (
	"errors"
	"path/filepath"
	"testing"

	"gorm.io/gorm"
)

// withMigrations swaps the migration list for the duration of a test and puts
// the real one back afterwards.
func withMigrations(t *testing.T, list []migration) {
	t.Helper()
	saved := migrations
	migrations = list
	t.Cleanup(func() { migrations = saved })
}

// TestMigrationsRecordAndDoNotRerun is the property the whole mechanism exists
// for: a step runs exactly once per database, ever. Without it a "migration" is
// just startup code that happens to run every boot.
func TestMigrationsRecordAndDoNotRerun(t *testing.T) {
	path := filepath.Join(t.TempDir(), "m.db")

	runs := 0
	withMigrations(t, []migration{
		{Version: 1, Name: "baseline", Up: func(tx *gorm.DB) error {
			return tx.AutoMigrate(baselineModels()...)
		}},
		{Version: 2, Name: "counter", Up: func(tx *gorm.DB) error {
			runs++
			return nil
		}},
	})

	s, err := Open(path)
	if err != nil {
		t.Fatalf("first open: %v", err)
	}
	if runs != 1 {
		t.Fatalf("step ran %d times on a fresh database, want 1", runs)
	}
	if v, err := s.SchemaVersion(); err != nil || v != 2 {
		t.Errorf("SchemaVersion = %d (%v), want 2", v, err)
	}
	_ = s.Close()

	// Reopening the SAME database must apply nothing.
	s2, err := Open(path)
	if err != nil {
		t.Fatalf("second open: %v", err)
	}
	defer func() { _ = s2.Close() }()
	if runs != 1 {
		t.Errorf("step ran %d times across two opens, want 1", runs)
	}
}

// TestMigrationsApplyInOrder: steps are ordered, and a database that already has
// some of them applies only the rest.
func TestMigrationsApplyInOrder(t *testing.T) {
	path := filepath.Join(t.TempDir(), "m.db")
	var order []int

	withMigrations(t, []migration{
		{Version: 1, Name: "baseline", Up: func(tx *gorm.DB) error {
			return tx.AutoMigrate(baselineModels()...)
		}},
		{Version: 2, Name: "two", Up: func(tx *gorm.DB) error { order = append(order, 2); return nil }},
	})
	s, err := Open(path)
	if err != nil {
		t.Fatalf("open: %v", err)
	}
	_ = s.Close()

	// A later release adds step 3; only it may run.
	withMigrations(t, []migration{
		{Version: 1, Name: "baseline", Up: func(tx *gorm.DB) error {
			return tx.AutoMigrate(baselineModels()...)
		}},
		{Version: 2, Name: "two", Up: func(tx *gorm.DB) error { order = append(order, 2); return nil }},
		{Version: 3, Name: "three", Up: func(tx *gorm.DB) error { order = append(order, 3); return nil }},
	})
	s2, err := Open(path)
	if err != nil {
		t.Fatalf("reopen: %v", err)
	}
	defer func() { _ = s2.Close() }()

	if len(order) != 2 || order[0] != 2 || order[1] != 3 {
		t.Errorf("execution order = %v, want [2 3] (2 once on the first open, 3 once on the second)", order)
	}
	if v, _ := s2.SchemaVersion(); v != 3 {
		t.Errorf("SchemaVersion = %d, want 3", v)
	}
}

// TestFailedMigrationIsNotRecorded pins the transaction: a step that fails must
// leave the version unrecorded so the next start retries it. Recording it anyway
// would silently skip a schema change forever - the worst outcome available here.
func TestFailedMigrationIsNotRecorded(t *testing.T) {
	path := filepath.Join(t.TempDir(), "m.db")
	boom := errors.New("boom")

	fail := true
	withMigrations(t, []migration{
		{Version: 1, Name: "baseline", Up: func(tx *gorm.DB) error {
			return tx.AutoMigrate(baselineModels()...)
		}},
		{Version: 2, Name: "explodes", Up: func(tx *gorm.DB) error {
			if fail {
				return boom
			}
			return nil
		}},
	})

	if _, err := Open(path); err == nil {
		t.Fatal("Open succeeded despite a failing migration")
	}

	// Same database, step now succeeds: it must still be pending.
	fail = false
	s, err := Open(path)
	if err != nil {
		t.Fatalf("reopen after fixing the step: %v", err)
	}
	defer func() { _ = s.Close() }()
	if v, _ := s.SchemaVersion(); v != 2 {
		t.Errorf("SchemaVersion = %d, want 2 - the failed step was not retried", v)
	}
}

// TestMigrationsAdoptAnExistingDatabase is the upgrade path every operator will
// take: a database built by the OLD code (bare AutoMigrate, no bookkeeping table)
// must be adopted in place, keeping its rows, not rebuilt or refused.
func TestMigrationsAdoptAnExistingDatabase(t *testing.T) {
	path := filepath.Join(t.TempDir(), "legacy.db")

	// Build a database the way the pre-migration server did.
	legacy, err := gorm.Open(sqliteDialector(path), &gorm.Config{})
	if err != nil {
		t.Fatalf("legacy open: %v", err)
	}
	if err := legacy.AutoMigrate(baselineModels()...); err != nil {
		t.Fatalf("legacy migrate: %v", err)
	}
	acc := map[string]any{"name": "OldTimer", "password_hash": "x"}
	if err := legacy.Table("accounts").Create(acc).Error; err != nil {
		t.Fatalf("legacy insert: %v", err)
	}
	// Release the legacy handle before reopening through the real path.
	if sqlDB, err := legacy.DB(); err == nil {
		_ = sqlDB.Close()
	}

	s, err := Open(path)
	if err != nil {
		t.Fatalf("open legacy db: %v", err)
	}
	defer func() { _ = s.Close() }()

	if v, _ := s.SchemaVersion(); v != len(migrations) {
		t.Errorf("SchemaVersion = %d, want %d", v, len(migrations))
	}
	var n int64
	if err := s.DB().Table("accounts").Where("name = ?", "OldTimer").Count(&n).Error; err != nil {
		t.Fatalf("count: %v", err)
	}
	if n != 1 {
		t.Errorf("the pre-existing account did not survive adoption (found %d)", n)
	}
}
