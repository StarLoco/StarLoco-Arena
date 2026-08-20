package store

import (
	"fmt"
	"time"

	"github.com/glebarez/sqlite"

	"gorm.io/gorm"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// Versioned schema migrations.
//
// The server supports three dialects (sqlite, postgres, mysql), which is what
// decides the shape of this file. Hand-frozen `CREATE TABLE` SQL would have to
// exist three times and drift three ways, so the baseline is expressed through
// GORM's own migrator instead: portable by construction, and identical to what
// every existing database was built with.
//
// What this adds over the bare `AutoMigrate` call it replaces:
//
//   - a `schema_migrations` table recording which steps have run, so a database
//     has a KNOWN version instead of "whatever the models happened to say";
//   - ordered, one-shot steps, each in its own transaction, so a step that fails
//     leaves no half-applied schema and is retried on the next start;
//   - somewhere to put the things AutoMigrate cannot express - dropping or
//     renaming a column, backfilling a row - which is the reason this item
//     existed at all.
//
// AutoMigrate remains the BASELINE step (version 1) rather than being deleted.
// It is additive-only and idempotent, so re-running it against a database built
// by the old code adopts that database unchanged, which is what makes this
// upgrade safe for every operator who already has an `arena.db`. The honest cost
// of that choice: step 1 is defined against the LIVE models, so a purely additive
// model change still lands without a numbered migration. The moment a change is
// destructive - or must be ordered against a data fix - it needs its own entry
// here, and from then on the version number is what tells you where a database
// stands.

// schemaMigration is the bookkeeping row. Deliberately store-private: nothing
// outside migration control should read or write it.
type schemaMigration struct {
	Version   int `gorm:"primaryKey"`
	Name      string
	AppliedAt time.Time
}

func (schemaMigration) TableName() string { return "schema_migrations" }

// migration is one ordered, one-shot schema step.
type migration struct {
	Version int
	Name    string
	Up      func(tx *gorm.DB) error
}

// baselineModels is the model set the initial schema is built from. Keep it in
// sync with the domain package when ADDING a model; when changing an existing
// one destructively, add a numbered migration below instead.
func baselineModels() []any {
	return []any{
		&domain.Account{},
		&domain.Coach{},
		&domain.CoachCard{},
		&domain.CoachFriend{},
		&domain.CoachIgnored{},
		&domain.CoachCurrency{},
		&domain.CoachStat{},
		&domain.CoachAchievement{},
		&domain.CoachTomeCard{},
		&domain.Fighter{},
		&domain.FighterSpell{},
		&domain.FighterObject{},
		&domain.FighterCondition{},
		&domain.Team{},
		&domain.TeamFighter{},
		&domain.Mail{},
		&domain.MailCard{},
		&domain.Tournament{},
		&domain.TournamentRegistration{},
	}
}

// migrations is the ordered list. NEVER renumber or edit a shipped entry: a
// database that already recorded a version will not run it again, so changing it
// only makes new installs diverge from old ones. Append instead.
var migrations = []migration{
	{
		Version: 1,
		Name:    "baseline",
		Up: func(tx *gorm.DB) error {
			return tx.AutoMigrate(baselineModels()...)
		},
	},
	{
		// Guilds ("clans"). Additive, so AutoMigrate could have carried it - but
		// it gets its own version anyway: this is the first schema change since
		// the mechanism landed, and a numbered step is what lets a later data
		// migration say "after guilds existed" without guessing.
		Version: 2,
		Name:    "guilds",
		Up: func(tx *gorm.DB) error {
			return tx.AutoMigrate(
				&domain.Guild{},
				&domain.GuildRank{},
				&domain.GuildMember{},
			)
		},
	},
}

// runMigrations applies every step the database has not recorded yet, in order.
//
// Each step runs inside a transaction together with the row that records it, so
// the two cannot disagree: a step either applied and is marked, or did neither.
// (SQLite and both server dialects allow DDL in a transaction; a dialect that
// did not would still be correct here, just not atomic.)
func runMigrations(db *gorm.DB) error {
	if err := db.AutoMigrate(&schemaMigration{}); err != nil {
		return fmt.Errorf("store: migrations table: %w", err)
	}
	var applied []schemaMigration
	if err := db.Find(&applied).Error; err != nil {
		return fmt.Errorf("store: read migrations: %w", err)
	}
	done := make(map[int]bool, len(applied))
	for _, a := range applied {
		done[a.Version] = true
	}
	for _, m := range migrations {
		if done[m.Version] {
			continue
		}
		if err := db.Transaction(func(tx *gorm.DB) error {
			if err := m.Up(tx); err != nil {
				return err
			}
			return tx.Create(&schemaMigration{
				Version:   m.Version,
				Name:      m.Name,
				AppliedAt: time.Now().UTC(),
			}).Error
		}); err != nil {
			return fmt.Errorf("store: migration %d (%s): %w", m.Version, m.Name, err)
		}
	}
	return nil
}

// SchemaVersion reports the highest applied migration version (0 = none).
// Exposed so the admin panel and a future `--version` flag can show it.
func (s *Store) SchemaVersion() (int, error) {
	var m schemaMigration
	err := s.db.Order("version DESC").First(&m).Error
	if err == gorm.ErrRecordNotFound {
		return 0, nil
	}
	if err != nil {
		return 0, err
	}
	return m.Version, nil
}

// sqliteDialector is the sqlite dialector used by Open. Exposed inside the
// package so a test can build a database exactly the way the pre-migration
// server did.
func sqliteDialector(path string) gorm.Dialector { return sqlite.Open(path) }
