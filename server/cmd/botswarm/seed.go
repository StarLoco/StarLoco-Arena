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

	// posX/posY track the bot's current overworld cell so the walk behavior
	// steps continuously from where it is (rather than jumping to a fresh
	// random origin each time, which looked like teleporting). Seeded from
	// the coach's stored start position at login.
	posX, posY int32
	// wpX/wpY is the bot's current roaming waypoint; hasWaypoint is false
	// until the first one is chosen. Roaming toward dispersed waypoints is
	// what spreads bots across the map instead of clustering at spawn.
	wpX, wpY    int32
	hasWaypoint bool
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
		return s.reuseBot(ctx, id, existing, rng)
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
	id.posX, id.posY = coach.PosX, coach.PosY

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
func (s *seeder) reuseBot(ctx context.Context, id *botIdentity, account domain.Account, rng *rand.Rand) (*botIdentity, error) {
	if account.CoachID == nil {
		// Account exists but has no coach yet (partial prior seed). Let the
		// login flow create the coach over the wire; fighters/cards will be
		// missing, so this bot won't fight, but it can still walk/chat.
		return id, nil
	}
	id.CoachID = *account.CoachID

	// Load the coach's stored overworld position so the bot's walk tracking
	// starts where the SERVER thinks the coach is. Otherwise the first move
	// would broadcast from the server's real position to a path the bot
	// computed from a wrong origin -- a visible teleport.
	var coach domain.Coach
	if err := s.db.WithContext(ctx).First(&coach, id.CoachID).Error; err == nil {
		id.posX, id.posY = coach.PosX, coach.PosY
	}

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

	// Re-dress reused coaches so they show a proper outfit even if they were
	// seeded by an older build that equipped cards into the wrong slots
	// (nothing rendered). Idempotent: it re-slots cards the coach already
	// owns and only grants a fresh outfit if it owns no equippable cards.
	if err := s.redressReusedCoach(ctx, id.CoachID, rng); err != nil {
		return nil, err
	}
	return id, nil
}

// grantCardSets dresses the coach in a random, RENDERABLE outfit (one card
// per body slot, each equipped in the WIRE slot matching its type so the
// client actually draws it) and leaves a second batch of cards unequipped in
// inventory (unlocked -- so they're stakeable in bet fights and tradeable in
// exchanges). This is the "2 card sets, equip 1" rule with the crucial fix
// that equipped cards must land in their type's correct slot, else nothing
// shows on the coach sprite.
func (s *seeder) grantCardSets(ctx context.Context, coachID uint, rng *rand.Rand) error {
	// Outfit A: worn on the sprite. Guarantee at least 4 visible pieces.
	outfit := s.idx.generateOutfit(rng, 4)
	for _, e := range outfit {
		card, err := s.coach.AddCard(ctx, coachID, e.TemplateID, 1, 0)
		if err != nil {
			return fmt.Errorf("botswarm: grant equipped card %d: %w", e.TemplateID, err)
		}
		// Stored Pos = wire slot + 1 (equipment_slots.go's convention, so
		// Pos != 0 marks "equipped" and slot 0 isn't confused with
		// inventory). ACTOR_SPAWN/COACH_INFORMATION translate it back.
		storedPos := e.WireSlot + 1
		if _, err := s.coach.SetCardPosition(ctx, coachID, card.ID, storedPos); err != nil {
			return fmt.Errorf("botswarm: equip card %d in slot %d: %w", e.TemplateID, e.WireSlot, err)
		}
	}

	// Batch B: a few unequipped cards left in inventory (Pos 0), unlocked --
	// stakeable + tradeable.
	setB := s.idx.pickCoachCardSet(rng, 3)
	for _, tmpl := range setB {
		if _, err := s.coach.AddCard(ctx, coachID, tmpl, 1, 0); err != nil {
			return fmt.Errorf("botswarm: grant inventory card %d: %w", tmpl, err)
		}
	}
	return nil
}

// redressReusedCoach ensures an already-seeded coach shows a proper outfit.
// It unequips everything (so any wrongly-slotted equip from an older build
// goes back to inventory), then equips one owned card per body slot into its
// CORRECT wire slot. If the coach owns no body-equippable cards at all (e.g.
// an old seed granted only non-wearable card types), it grants a fresh
// outfit. Idempotent across runs: re-slotting owned cards is stable, and the
// fresh-outfit grant only fires when there's nothing to re-slot.
func (s *seeder) redressReusedCoach(ctx context.Context, coachID uint, rng *rand.Rand) error {
	if err := s.coach.UnequipAll(ctx, coachID); err != nil {
		return fmt.Errorf("botswarm: unequip reused coach %d: %w", coachID, err)
	}

	inv, err := s.coach.GetInventory(ctx, coachID)
	if err != nil {
		return fmt.Errorf("botswarm: load reused inventory %d: %w", coachID, err)
	}

	// Pick one owned card per wire slot it can fill (first-come wins).
	usedSlot := make(map[int16]bool)
	equippedAny := false
	for _, card := range inv {
		slot, ok := s.idx.slotByCoachCardTemplate[card.TemplateID]
		if !ok || usedSlot[slot] {
			continue
		}
		if _, err := s.coach.SetCardPosition(ctx, coachID, card.ID, slot+1); err != nil {
			return fmt.Errorf("botswarm: re-slot card %d: %w", card.ID, err)
		}
		usedSlot[slot] = true
		equippedAny = true
	}

	// If the coach owned nothing wearable, grant a fresh renderable outfit.
	if !equippedAny {
		outfit := s.idx.generateOutfit(rng, 4)
		for _, e := range outfit {
			card, err := s.coach.AddCard(ctx, coachID, e.TemplateID, 1, 0)
			if err != nil {
				return fmt.Errorf("botswarm: grant redress card %d: %w", e.TemplateID, err)
			}
			if _, err := s.coach.SetCardPosition(ctx, coachID, card.ID, e.WireSlot+1); err != nil {
				return fmt.Errorf("botswarm: equip redress card %d: %w", e.TemplateID, err)
			}
		}
	}
	return nil
}
