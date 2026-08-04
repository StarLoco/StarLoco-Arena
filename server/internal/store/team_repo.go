package store

import (
	"errors"

	"gorm.io/gorm"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TeamRepo persists a coach's team presets.
type TeamRepo struct{ db *gorm.DB }

// Upsert saves (creates or updates) a team preset with its member list, scoped
// to the owning coach, in one transaction.
func (r *TeamRepo) Upsert(t *domain.Team) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		if t.ID != 0 {
			// Replace members: delete existing then re-insert.
			if err := tx.Where("team_id = ?", t.ID).
				Delete(&domain.TeamFighter{}).Error; err != nil {
				return err
			}
		}
		return tx.Save(t).Error
	})
}

// ListByCoach returns a coach's team presets with members preloaded.
func (r *TeamRepo) ListByCoach(coachID uint) ([]domain.Team, error) {
	var teams []domain.Team
	err := r.db.Preload("Members").Where("coach_id = ?", coachID).Find(&teams).Error
	return teams, err
}

// Delete removes a team preset, scoped to its owner. Returns whether a row was
// deleted.
func (r *TeamRepo) Delete(id, coachID uint) (bool, error) {
	res := r.db.Where("id = ? AND coach_id = ?", id, coachID).Delete(&domain.Team{})
	return res.RowsAffected > 0, res.Error
}

// Get loads a team preset (members) by id.
func (r *TeamRepo) Get(id uint) (*domain.Team, error) {
	var t domain.Team
	err := r.db.Preload("Members").First(&t, id).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, ErrNotFound
	}
	return &t, err
}

// AddMember links a fighter into a team's membership. It is idempotent: a fighter
// already on the team is left untouched (no duplicate row).
func (r *TeamRepo) AddMember(teamID, fighterID uint) error {
	var count int64
	if err := r.db.Model(&domain.TeamFighter{}).
		Where("team_id = ? AND fighter_id = ?", teamID, fighterID).
		Count(&count).Error; err != nil {
		return err
	}
	if count > 0 {
		return nil
	}
	return r.db.Create(&domain.TeamFighter{TeamID: teamID, FighterID: fighterID}).Error
}

// RemoveMember unlinks a fighter from a team's membership. Removing a fighter that
// is not on the team is a no-op.
func (r *TeamRepo) RemoveMember(teamID, fighterID uint) error {
	return r.db.Where("team_id = ? AND fighter_id = ?", teamID, fighterID).
		Delete(&domain.TeamFighter{}).Error
}
