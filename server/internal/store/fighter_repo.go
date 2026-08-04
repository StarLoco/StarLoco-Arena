package store

import (
	"errors"

	"gorm.io/gorm"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// FighterRepo persists a coach's fighters.
type FighterRepo struct{ db *gorm.DB }

// Create inserts a fighter (with its spells + objects) in one transaction.
func (r *FighterRepo) Create(f *domain.Fighter) error {
	return r.db.Create(f).Error // associations cascade via GORM
}

// SetState persists a fighter's evolution state byte (0 titular … 3 graveyard).
// Shared by the graveyard/evolution handlers and by evolution-fight death
// persistence.
func (r *FighterRepo) SetState(id uint, state uint8) error {
	return r.db.Model(&domain.Fighter{}).Where("id = ?", id).Update("state", state).Error
}

// SaveProgress persists ONLY the post-fight META columns (see
// game/postfight.go). It is a column-scoped update rather than a full Save on
// purpose: the fighter struct a fight holds does not carry its Spells/Objects
// associations, so saving the whole record would let GORM wipe the loadout.
func (r *FighterRepo) SaveProgress(f *domain.Fighter) error {
	return r.db.Model(&domain.Fighter{}).Where("id = ?", f.ID).
		Updates(map[string]any{
			"xp":            f.XP,
			"total_xp":      f.TotalXP,
			"tiredness":     f.Tiredness,
			"morale":        f.Morale,
			"last_fight_at": f.LastFightAt,
			"state":         f.State,
		}).Error
}

// SaveConditions replaces a fighter's persistent condition list. Delete-then-
// insert rather than a diff: the list is tiny (one per exclusion class) and this
// keeps "what the fighter holds" as one atomic statement pair, so a wound that
// was upgraded in place can never leave both halves behind.
func (r *FighterRepo) SaveConditions(fighterID uint, conds []domain.FighterCondition) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		if err := tx.Where("fighter_id = ?", fighterID).
			Delete(&domain.FighterCondition{}).Error; err != nil {
			return err
		}
		if len(conds) == 0 {
			return nil
		}
		rows := make([]domain.FighterCondition, 0, len(conds))
		for _, c := range conds {
			rows = append(rows, domain.FighterCondition{
				FighterID:   fighterID,
				ConditionID: c.ConditionID,
				Remaining:   c.Remaining,
			})
		}
		return tx.Create(&rows).Error
	})
}

// ListByCoach returns all fighters of a coach with spells + objects preloaded.
func (r *FighterRepo) ListByCoach(coachID uint) ([]domain.Fighter, error) {
	var fighters []domain.Fighter
	err := r.db.Preload("Spells").Preload("Objects").Preload("Conditions").
		Where("coach_id = ?", coachID).Find(&fighters).Error
	return fighters, err
}

// Get loads one fighter (spells + objects) by id.
func (r *FighterRepo) Get(id uint) (*domain.Fighter, error) {
	var f domain.Fighter
	err := r.db.Preload("Spells").Preload("Objects").Preload("Conditions").First(&f, id).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, ErrNotFound
	}
	return &f, err
}

// Delete removes a fighter, scoped to its owner (IDOR guard): a mismatched
// coachID deletes nothing. Returns whether a row was deleted.
func (r *FighterRepo) Delete(id, coachID uint) (bool, error) {
	res := r.db.Where("id = ? AND coach_id = ?", id, coachID).
		Delete(&domain.Fighter{})
	return res.RowsAffected > 0, res.Error
}

// SaveLoadout replaces a fighter's equipped cards + spells (and its recomputed
// budget) in one transaction, scoped to its owner. Returns ErrNotFound if the
// fighter doesn't exist or isn't owned by coachID. cards/spells already carry
// the target FighterID via the caller.
func (r *FighterRepo) SaveLoadout(fighterID, coachID uint, cards []domain.FighterObject, spells []domain.FighterSpell, budget int16) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Ownership check (IDOR guard).
		var count int64
		if err := tx.Model(&domain.Fighter{}).
			Where("id = ? AND coach_id = ?", fighterID, coachID).
			Count(&count).Error; err != nil {
			return err
		}
		if count == 0 {
			return ErrNotFound
		}
		// Replace objects + spells.
		if err := tx.Where("fighter_id = ?", fighterID).
			Delete(&domain.FighterObject{}).Error; err != nil {
			return err
		}
		if err := tx.Where("fighter_id = ?", fighterID).
			Delete(&domain.FighterSpell{}).Error; err != nil {
			return err
		}
		for i := range cards {
			cards[i].ID = 0
			cards[i].FighterID = fighterID
			if err := tx.Create(&cards[i]).Error; err != nil {
				return err
			}
		}
		for i := range spells {
			spells[i].ID = 0
			spells[i].FighterID = fighterID
			if err := tx.Create(&spells[i]).Error; err != nil {
				return err
			}
		}
		return tx.Model(&domain.Fighter{}).Where("id = ?", fighterID).
			Update("budget", budget).Error
	})
}
