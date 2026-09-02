package store

import (
	"errors"

	"gorm.io/gorm"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TeamRepo persists a coach's team presets.
type TeamRepo struct{ db *gorm.DB }

// Upsert saves (creates or updates) a team preset with its member list in one
// transaction, scoped to the owning coach.
//
// SECURITY: the "scoped to the owning coach" in the old doc comment was a claim,
// not a fact - the code had no coach_id predicate anywhere, and t.ID comes
// straight off the wire in 6021. gorm.Save with a non-zero primary key issues
// UPDATE teams SET * WHERE id = ?, which rewrote coach_id, name, game_mode and
// the appearance bytes of ANY team id the client named, after first deleting that
// team's members. Team ids are small sequential integers, so enumerating other
// players' presets was trivial: an attacker could wipe a victim's roster and
// reassign the preset to itself, which also reset ally_coach_id and destroyed
// their 2v2 pairing. TeamRepo.Delete already did this correctly
// (WHERE id = ? AND coach_id = ?); Upsert simply did not.
//
// An update now requires the row to already belong to t.CoachID.
func (r *TeamRepo) Upsert(t *domain.Team) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		if t.ID != 0 {
			var owned int64
			if err := tx.Model(&domain.Team{}).
				Where("id = ? AND coach_id = ?", t.ID, t.CoachID).
				Count(&owned).Error; err != nil {
				return err
			}
			if owned == 0 {
				// Either the id does not exist or it belongs to someone else.
				// Both are refusals, and deliberately indistinguishable so the
				// caller cannot use this to enumerate other coaches' team ids.
				return ErrNotFound
			}
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
