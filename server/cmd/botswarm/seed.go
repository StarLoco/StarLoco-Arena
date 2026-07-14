package main

import (
	"context"
	"fmt"
	"math/rand"
	"strings"

	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/db"
	"github.com/dofusarena/go-server/internal/domain"
	golog "github.com/dofusarena/go-server/internal/log"
	"github.com/dofusarena/go-server/internal/service"

	"gorm.io/gorm"
)

// seed.go provisions bot accounts directly in the live server's database
// (the same DB the running server uses, reached via the shared config).
// SQLite in WAL mode supports concurrent readers/writers, so this runs
// safely alongside a running server. Each bot gets:
//
//   - an account (bcrypt-hashed password);
//   - a coach with 2 random "card sets" (batches of coach-card templates),
//     one of which is equipped -- the "get 2 sets, equip 1" rule;
//   - 1..2 procedurally generated, always-legal fighters (never identical),
//     including a summoner when the breed supports it.
//
// Seeding is idempotent: an existing bot account is reused (its coach and
// fighters are left as-is), so re-running the swarm doesn't duplicate rows
// or fail on unique-name conflicts.

// botIdentity is everything a bot goroutine needs to log in and play.
type botIdentity struct {
	Index      int
	Login      string
	Password   string
	CoachName  string
	CoachID    uint
	FighterIDs []int64
	// InventoryCardUIDs are the bot's unequipped card uids (learned at
	// login from COACH_INFORMATION), offered in card exchanges.
	InventoryCardUIDs []int64
	// PrimaryLoadout describes the bot's first fighter, used by the fight
	// AI (spell ids, summon capability). Populated for freshly seeded bots;
	// for reused accounts it is reconstructed from the DB.
	PrimaryLoadout loadout
}

// seeder owns the DB handle and services used to provision bots.
type seeder struct {
	db      *gorm.DB
	coach   *service.CoachService
	fighter *service.FighterService
	idx     *dataIndex
}

// newSeeder opens the configured database and wires the provisioning
// services. Callers must Close the returned DB via the returned *gorm.DB's
// underlying sql.DB when done (the swarm keeps it open for the whole run so
// fight-stake grants and telemetry lookups can reuse it).
func newSeeder(cfg config.Config, idx *dataIndex) (*seeder, error) {
	logger := golog.New(golog.Options{Level: "error", Format: "console"})
	// When seeding against a RUNNING server sharing the same SQLite file,
	// concurrent writes can hit SQLITE_BUSY. A busy_timeout makes our writes
	// wait for the lock instead of failing immediately. Inject it if the
	// operator hasn't already set one.
	dbCfg := cfg.Database
	if dbCfg.Driver == "sqlite" && !strings.Contains(dbCfg.DSN, "busy_timeout") {
		sep := "?"
		if strings.Contains(dbCfg.DSN, "?") {
			sep = "&"
		}
		dbCfg.DSN += sep + "_pragma=busy_timeout(10000)"
	}
	gdb, err := db.Open(dbCfg, logger)
	if err != nil {
		return nil, fmt.Errorf("botswarm: open db: %w", err)
	}
	return &seeder{
		db:      gdb,
		coach:   service.NewCoachService(gdb),
		fighter: service.NewFighterService(gdb),
		idx:     idx,
	}, nil
}

// Close releases the DB connection.
func (s *seeder) Close() {
	if sqlDB, err := s.db.DB(); err == nil {
		_ = sqlDB.Close()
	}
}

// seedBot provisions (or reuses) one bot's account/coach/cards/fighters and
// returns its identity. loginPrefix+index forms the login; password is
// shared across bots (they are throwaway test accounts). fighterCount is how
// many fighters to generate for a freshly-created bot.
func (s *seeder) seedBot(ctx context.Context, loginPrefix, password string, index, fighterCount int, rng *rand.Rand) (*botIdentity, error) {
	login := fmt.Sprintf("%s%05d", loginPrefix, index)
	coachName := fmt.Sprintf("Bot%05d", index)

	id := &botIdentity{Index: index, Login: login, Password: password, CoachName: coachName}

	// Reuse an existing account if present (idempotent re-runs).
	var existing domain.Account
	err := s.db.WithContext(ctx).Where("name = ?", login).First(&existing).Error
	if err == nil {
		return s.reuseBot(ctx, id, existing)
	}
	if err != gorm.ErrRecordNotFound && err.Error() != "record not found" {
		return nil, fmt.Errorf("botswarm: lookup account %s: %w", login, err)
	}

	// Fresh account.
	hash, err := service.HashPassword(password)
	if err != nil {
		return nil, fmt.Errorf("botswarm: hash password: %w", err)
	}
	account := domain.Account{Name: login, PasswordHash: hash, IsAdmin: false}
	if err := s.db.WithContext(ctx).Create(&account).Error; err != nil {
		return nil, fmt.Errorf("botswarm: create account %s: %w", login, err)
	}

	// Coach.
	coach, result, err := s.coach.CreateCoach(ctx, account.ID, coachName, byte(rng.Intn(30)), byte(rng.Intn(30)), byte(rng.Intn(2)))
	if err != nil {
		return nil, fmt.Errorf("botswarm: create coach %s: %w", coachName, err)
	}
	if result != service.CoachCreationOK {
		return nil, fmt.Errorf("botswarm: create coach %s rejected: %v", coachName, result)
	}
	id.CoachID = coach.ID

	// 2 card sets, equip 1.
	if err := s.grantCardSets(ctx, coach.ID, rng); err != nil {
		return nil, err
	}

	// Generated fighters.
	if fighterCount < 1 {
		fighterCount = 1
	}
	for i := 0; i < fighterCount; i++ {
		lo := s.idx.generateLoadout(rng)
		spellIDs := lo.SpellIDs
		objectIDs := lo.ObjectIDs
		fname := fmt.Sprintf("F%05d_%d", index, i)
		f, err := s.fighter.CreateFighter(ctx, coach.ID, fname, lo.Breed, byte(rng.Intn(2)), byte(rng.Intn(30)), lo.Budget, spellIDs, objectIDs)
		if err != nil {
			return nil, fmt.Errorf("botswarm: create fighter %s: %w", fname, err)
		}
		id.FighterIDs = append(id.FighterIDs, int64(f.ID))
		if i == 0 {
			id.PrimaryLoadout = lo
		}
	}

	return id, nil
}

// reuseBot reconstructs a bot identity from an already-seeded account,
// loading its coach and fighters from the DB.
func (s *seeder) reuseBot(ctx context.Context, id *botIdentity, account domain.Account) (*botIdentity, error) {
	if account.CoachID == nil {
		// Account exists but has no coach yet (partial prior seed). Let the
		// login flow create the coach over the wire; fighters/cards will be
		// missing, so this bot won't fight, but it can still walk/chat.
		return id, nil
	}
	id.CoachID = *account.CoachID

	var fighters []domain.Fighter
	if err := s.db.WithContext(ctx).Where("coach_id = ?", id.CoachID).Find(&fighters).Error; err != nil {
		return nil, fmt.Errorf("botswarm: load fighters for reused coach %d: %w", id.CoachID, err)
	}
	for i, f := range fighters {
		id.FighterIDs = append(id.FighterIDs, int64(f.ID))
		if i == 0 {
			// Reconstruct the primary loadout's spell ids for the AI.
			var spells []domain.FighterSpell
			_ = s.db.WithContext(ctx).Where("fighter_id = ?", f.ID).Find(&spells).Error
			lo := loadout{Breed: f.Breed, Budget: f.Budget}
			for _, sp := range spells {
				lo.SpellIDs = append(lo.SpellIDs, sp.SpellID)
				if containsInt32(s.idx.summonSpellsByBreed[f.Breed], sp.SpellID) {
					lo.CanSummon = true
				}
			}
			id.PrimaryLoadout = lo
		}
	}
	return id, nil
}

// grantCardSets grants two random coach-card sets and equips the first set.
// A "set" here is a small batch of distinct coach-card templates (the game's
// Set field is only a cosmetic grouping tag, so we model a set as N random
// templates). Equipped cards use equipment slots 1..14 (stored Pos), the
// rest stay in inventory unlocked so they can be staked in bet fights and
// offered in card exchanges.
func (s *seeder) grantCardSets(ctx context.Context, coachID uint, rng *rand.Rand) error {
	const setSize = 3
	setA := s.idx.pickCoachCardSet(rng, setSize)
	setB := s.idx.pickCoachCardSet(rng, setSize)

	// Equip set A into consecutive equipment slots (stored Pos 1..14).
	slot := int16(1)
	for _, tmpl := range setA {
		card, err := s.coach.AddCard(ctx, coachID, tmpl, 1, 0)
		if err != nil {
			return fmt.Errorf("botswarm: grant equipped card %d: %w", tmpl, err)
		}
		if slot <= 14 {
			if _, err := s.coach.SetCardPosition(ctx, coachID, card.ID, slot); err != nil {
				return fmt.Errorf("botswarm: equip card %d: %w", tmpl, err)
			}
			slot++
		}
	}
	// Leave set B in inventory (Pos 0), unlocked -- stakeable + tradeable.
	for _, tmpl := range setB {
		if _, err := s.coach.AddCard(ctx, coachID, tmpl, 1, 0); err != nil {
			return fmt.Errorf("botswarm: grant inventory card %d: %w", tmpl, err)
		}
	}
	return nil
}
