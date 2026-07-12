package service

import (
	"context"
	"fmt"

	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
)

// FighterService handles fighter (in-combat deck-built unit) CRUD. See
// docs/02-protocol.md §2.4.8 for the wire serialization format used in
// FIGHTER_CREATE_RESULT responses.
type FighterService struct {
	db *gorm.DB
}

// NewFighterService constructs a FighterService bound to db.
func NewFighterService(db *gorm.DB) *FighterService {
	return &FighterService{db: db}
}

// CreateFighter persists a new fighter for coachID along with its spell and
// object loadout, replacing the legacy CSV-string columns with proper join
// rows (see docs/03-data-model.md §3.3).
func (s *FighterService) CreateFighter(ctx context.Context, coachID uint, name string, breed, sex, skin uint8, budget int16, spellIDs, objectIDs []int32) (*domain.Fighter, error) {
	fighter := &domain.Fighter{
		CoachID: coachID,
		Name:    name,
		Breed:   breed,
		Sex:     sex,
		Skin:    skin,
		Budget:  budget,
	}

	err := s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		if err := tx.Create(fighter).Error; err != nil {
			return err
		}
		for _, spellID := range spellIDs {
			if err := tx.Create(&domain.FighterSpell{FighterID: fighter.ID, SpellID: spellID}).Error; err != nil {
				return err
			}
		}
		for _, objID := range objectIDs {
			if err := tx.Create(&domain.FighterObject{FighterID: fighter.ID, TemplateID: objID}).Error; err != nil {
				return err
			}
		}
		return nil
	})
	if err != nil {
		return nil, fmt.Errorf("service: create fighter: %w", err)
	}

	return fighter, nil
}

// DeleteFighter removes a fighter (and, via ON DELETE CASCADE, its
// spell/object join rows and team memberships), scoped to coachID for
// ownership verification. The coach_id predicate is a security boundary:
// the client supplies the fighter id in FIGHTER_DELETE_REQUEST, so without
// scoping to the authenticated coach a crafted packet could delete ANY
// coach's fighter (IDOR). Returns (false, nil) when no owned fighter
// matched (already gone, or not the caller's -- treated as a silent no-op
// by the handler).
func (s *FighterService) DeleteFighter(ctx context.Context, coachID, fighterID uint) (bool, error) {
	res := s.db.WithContext(ctx).
		Where("id = ? AND coach_id = ?", fighterID, coachID).
		Delete(&domain.Fighter{})
	if res.Error != nil {
		return false, fmt.Errorf("service: delete fighter %d: %w", fighterID, res.Error)
	}
	return res.RowsAffected > 0, nil
}

// ListFighters returns every fighter owned by coachID.
func (s *FighterService) ListFighters(ctx context.Context, coachID uint) ([]domain.Fighter, error) {
	var fighters []domain.Fighter
	if err := s.db.WithContext(ctx).Where("coach_id = ?", coachID).Find(&fighters).Error; err != nil {
		return nil, fmt.Errorf("service: list fighters for coach %d: %w", coachID, err)
	}
	return fighters, nil
}

// GetFightersByIDs returns the subset of coachID's fighters matching
// fighterIDs, in no particular order, scoped to coachID for ownership
// verification -- mirrors TempCoachFighter's constructor filtering
// (TempCoachFighter.java:21-33).
func (s *FighterService) GetFightersByIDs(ctx context.Context, coachID uint, fighterIDs []uint) ([]domain.Fighter, error) {
	if len(fighterIDs) == 0 {
		return nil, nil
	}
	var fighters []domain.Fighter
	if err := s.db.WithContext(ctx).Where("coach_id = ? AND id IN ?", coachID, fighterIDs).Find(&fighters).Error; err != nil {
		return nil, fmt.Errorf("service: get fighters by ids for coach %d: %w", coachID, err)
	}
	return fighters, nil
}

// UpdateInventory replaces a fighter's spell and equipped-object loadout,
// replacing the legacy CSV-string mutation in FighterUpdateInventoryRequest.java
// with proper join-row replacement (see docs/03-data-model.md §3.3).
//
// The transaction first re-verifies the fighter belongs to coachID: the
// client supplies the fighter id in FIGHTER_UPDATE_INVENTORY_REQUEST, so
// without this ownership check a crafted packet could rewrite ANY coach's
// fighter loadout (IDOR). A fighter not owned by coachID yields
// (false, nil) and no mutation.
func (s *FighterService) UpdateInventory(ctx context.Context, coachID, fighterID uint, budget int16, spellIDs, objectIDs []int32) (bool, error) {
	owned := false
	err := s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		// Re-check ownership AND update the server-recomputed budget in one
		// statement: a non-owned fighter matches zero rows, so the update
		// is a no-op and owned stays false.
		res := tx.Model(&domain.Fighter{}).
			Where("id = ? AND coach_id = ?", fighterID, coachID).
			Update("budget", budget)
		if res.Error != nil {
			return res.Error
		}
		if res.RowsAffected == 0 {
			return nil // not the caller's fighter -- leave everything untouched
		}
		owned = true
		if err := tx.Where("fighter_id = ?", fighterID).Delete(&domain.FighterSpell{}).Error; err != nil {
			return err
		}
		if err := tx.Where("fighter_id = ?", fighterID).Delete(&domain.FighterObject{}).Error; err != nil {
			return err
		}
		for _, spellID := range spellIDs {
			if err := tx.Create(&domain.FighterSpell{FighterID: fighterID, SpellID: spellID}).Error; err != nil {
				return err
			}
		}
		for _, objID := range objectIDs {
			if err := tx.Create(&domain.FighterObject{FighterID: fighterID, TemplateID: objID}).Error; err != nil {
				return err
			}
		}
		return nil
	})
	if err != nil {
		return false, fmt.Errorf("service: update fighter %d inventory: %w", fighterID, err)
	}
	return owned, nil
}

// GetSpellIDs returns the spell IDs currently equipped on a fighter.
func (s *FighterService) GetSpellIDs(ctx context.Context, fighterID uint) ([]int32, error) {
	var rows []domain.FighterSpell
	if err := s.db.WithContext(ctx).Where("fighter_id = ?", fighterID).Find(&rows).Error; err != nil {
		return nil, fmt.Errorf("service: get fighter %d spells: %w", fighterID, err)
	}
	out := make([]int32, len(rows))
	for i, r := range rows {
		out[i] = r.SpellID
	}
	return out, nil
}

// GetObjectIDs returns the fighter-card template IDs currently equipped on
// a fighter.
func (s *FighterService) GetObjectIDs(ctx context.Context, fighterID uint) ([]int32, error) {
	var rows []domain.FighterObject
	if err := s.db.WithContext(ctx).Where("fighter_id = ?", fighterID).Find(&rows).Error; err != nil {
		return nil, fmt.Errorf("service: get fighter %d objects: %w", fighterID, err)
	}
	out := make([]int32, len(rows))
	for i, r := range rows {
		out[i] = r.TemplateID
	}
	return out, nil
}

// LoadoutMaps batch-loads every FighterSpell/FighterObject row for the
// given fighter IDs, grouped by fighter, to avoid an N+1 query pattern when
// serializing a whole fighter list (see dispatch.buildFighterInformationList).
func (s *FighterService) LoadoutMaps(ctx context.Context, fighterIDs []uint) (spells map[uint][]int32, objects map[uint][]int32, err error) {
	spells = make(map[uint][]int32)
	objects = make(map[uint][]int32)
	if len(fighterIDs) == 0 {
		return spells, objects, nil
	}

	var spellRows []domain.FighterSpell
	if err := s.db.WithContext(ctx).Where("fighter_id IN ?", fighterIDs).Find(&spellRows).Error; err != nil {
		return nil, nil, fmt.Errorf("service: batch load fighter spells: %w", err)
	}
	for _, r := range spellRows {
		spells[r.FighterID] = append(spells[r.FighterID], r.SpellID)
	}

	var objectRows []domain.FighterObject
	if err := s.db.WithContext(ctx).Where("fighter_id IN ?", fighterIDs).Find(&objectRows).Error; err != nil {
		return nil, nil, fmt.Errorf("service: batch load fighter objects: %w", err)
	}
	for _, r := range objectRows {
		objects[r.FighterID] = append(objects[r.FighterID], r.TemplateID)
	}

	return spells, objects, nil
}
