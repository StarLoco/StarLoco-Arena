package service

import (
	"context"
	"errors"
	"fmt"

	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
)

// TeamService handles team-preset CRUD (named rosters of fighters). See
// docs/03-data-model.md §3.3's note on splitting the legacy Team.id's dual
// role (DB PK vs. per-coach preset slot) into ID + Slot.
type TeamService struct {
	db *gorm.DB
}

// NewTeamService constructs a TeamService bound to db.
func NewTeamService(db *gorm.DB) *TeamService {
	return &TeamService{db: db}
}

// SaveTeam creates or updates a team preset for coachID with the given
// fighter roster.
//
// The client sends slot == -1 for a brand-new preset (see the client's
// TeamPreset constructor, which initializes m_id = -1) and the actual slot
// number of an existing preset when editing one. For a new preset we
// allocate the next free per-coach slot (mirroring the legacy
// Team.getNextId), so multiple new presets don't collide on slot -1. The
// returned team carries the real assigned Slot, which the caller must echo
// back to the client so it can reference the preset later.
func (s *TeamService) SaveTeam(ctx context.Context, coachID uint, slot int16, name string, fighterIDs []uint) (*domain.Team, error) {
	var team domain.Team
	isNew := slot < 0
	if !isNew {
		err := s.db.WithContext(ctx).Where("coach_id = ? AND slot = ?", coachID, slot).First(&team).Error
		if errors.Is(err, gorm.ErrRecordNotFound) {
			isNew = true
		} else if err != nil {
			return nil, fmt.Errorf("service: lookup team slot %d: %w", slot, err)
		}
	}

	err := s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		if isNew {
			nextSlot, err := nextTeamSlot(tx, coachID)
			if err != nil {
				return err
			}
			team = domain.Team{CoachID: coachID, Slot: nextSlot, Name: name}
			if err := tx.Create(&team).Error; err != nil {
				return err
			}
		} else {
			team.Name = name
			if err := tx.Save(&team).Error; err != nil {
				return err
			}
		}

		var fighters []domain.Fighter
		if len(fighterIDs) > 0 {
			if err := tx.Where("coach_id = ? AND id IN ?", coachID, fighterIDs).Find(&fighters).Error; err != nil {
				return err
			}
		}
		return tx.Model(&team).Association("Fighters").Replace(fighters)
	})
	if err != nil {
		return nil, fmt.Errorf("service: save team: %w", err)
	}

	return &team, nil
}

// nextTeamSlot computes the next free per-coach team slot (1-based),
// mirroring Team.getNextId (Team.java:71-77): one greater than the current
// maximum slot, or 1 if the coach has no teams yet.
func nextTeamSlot(tx *gorm.DB, coachID uint) (int16, error) {
	var maxSlot *int16
	if err := tx.Model(&domain.Team{}).
		Where("coach_id = ?", coachID).
		Select("MAX(slot)").Scan(&maxSlot).Error; err != nil {
		return 0, fmt.Errorf("service: compute next team slot: %w", err)
	}
	if maxSlot == nil {
		return 1, nil
	}
	return *maxSlot + 1, nil
}

// DeleteTeam removes a team preset by slot.
func (s *TeamService) DeleteTeam(ctx context.Context, coachID uint, slot int16) error {
	if err := s.db.WithContext(ctx).Where("coach_id = ? AND slot = ?", coachID, slot).Delete(&domain.Team{}).Error; err != nil {
		return fmt.Errorf("service: delete team slot %d: %w", slot, err)
	}
	return nil
}

// ListTeams returns every team preset for coachID, with fighters preloaded.
func (s *TeamService) ListTeams(ctx context.Context, coachID uint) ([]domain.Team, error) {
	var teams []domain.Team
	if err := s.db.WithContext(ctx).Preload("Fighters").Where("coach_id = ?", coachID).Find(&teams).Error; err != nil {
		return nil, fmt.Errorf("service: list teams for coach %d: %w", coachID, err)
	}
	return teams, nil
}
