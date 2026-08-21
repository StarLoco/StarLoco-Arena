package store

import (
	"errors"

	"gorm.io/gorm"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// FighterRepo persists a coach's fighters.
type FighterRepo struct {
	db *gorm.DB

	// EquipSlotOf maps a fighter-card template id to the equipment position it
	// must occupy, reporting false for an id that is not equippable. It is
	// injected at startup (the store must not depend on the game-data package),
	// and when nil every fighter is returned exactly as stored.
	//
	// It exists because a fighter's equipment position is NOT a free index: the
	// client derives it from the card's own record type
	// (`vi_1.ap((byte)uh_0.getType())`, eh_2.java:81) and refuses on arrival any
	// item whose position is not its type's, or whose position falls outside the
	// 5-slot inventory. Normalising here, at the two read paths, keeps every
	// consumer - the roster blob, the fight blob, stat computation - agreed with
	// what the client will actually hold.
	EquipSlotOf func(templateID int32) (slot int16, ok bool)
}

// maxEquipSlots is the client's fighter item inventory size (`en_1(..., 5, ...)`,
// ee_2.java:139).
const maxEquipSlots = 5

// normalizeEquipment rewrites a fighter's equipment to the storable subset: each
// item at the position its card type demands, at most one per position, in
// ascending order.
//
// Read-only repair: the rows on disk are left alone, so a server started without
// game data (EquipSlotOf nil) still returns them untouched, and nothing is
// destroyed by a mis-loaded card table.
func (r *FighterRepo) normalizeEquipment(f *domain.Fighter) {
	if r.EquipSlotOf == nil || f == nil || len(f.Objects) == 0 {
		return
	}
	// One pass per position, taking the first card that belongs there: the break
	// is what enforces "at most one item per slot", so no separate seen-set is
	// needed (an earlier version carried one and it was pure decoration - a
	// mutation that deleted it changed nothing).
	out := make([]domain.FighterObject, 0, maxEquipSlots)
	for slot := int16(0); slot < maxEquipSlots; slot++ {
		for _, o := range f.Objects {
			if got, ok := r.EquipSlotOf(o.TemplateID); !ok || got != slot {
				continue
			}
			o.Slot = slot
			out = append(out, o)
			break
		}
	}
	f.Objects = out
}

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
	for i := range fighters {
		r.normalizeEquipment(&fighters[i])
	}
	return fighters, err
}

// Get loads one fighter (spells + objects) by id.
func (r *FighterRepo) Get(id uint) (*domain.Fighter, error) {
	var f domain.Fighter
	err := r.db.Preload("Spells").Preload("Objects").Preload("Conditions").First(&f, id).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, ErrNotFound
	}
	r.normalizeEquipment(&f)
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

// ErrSphereNotAffordable means the fighter no longer had the experience the
// purchase required - the guard is in the UPDATE's WHERE clause, so this is also
// what a lost race reports.
var ErrSphereNotAffordable = errors.New("store: not enough experience")

// BuySphere records a Kanodo purchase atomically: charge the experience, add the
// node if it is new, and walk the cursor onto it.
//
// One transaction because a half-applied purchase is the worst outcome available
// - charged but not credited, or credited without paying - and the client has
// already applied its own copy locally by the time this arrives.
func (r *FighterRepo) BuySphere(fighterID uint, sphereID int32, cost int32, cursorX, cursorY int16) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		res := tx.Model(&domain.Fighter{}).
			// The xp >= cost guard is in the WHERE clause, not a read-then-write,
			// so two purchases racing on one fighter cannot both pass the check.
			Where("id = ? AND xp >= ?", fighterID, cost).
			Updates(map[string]any{
				"xp":       gorm.Expr("xp - ?", cost),
				"sphere_x": cursorX,
				"sphere_y": cursorY,
			})
		if res.Error != nil {
			return res.Error
		}
		if res.RowsAffected == 0 {
			return ErrSphereNotAffordable
		}
		var n int64
		if err := tx.Model(&domain.FighterSphere{}).
			Where("fighter_id = ? AND sphere_id = ?", fighterID, sphereID).
			Count(&n).Error; err != nil {
			return err
		}
		if n > 0 {
			// Re-buying an owned node is legal (it costs a tenth) and must not
			// duplicate the row.
			return nil
		}
		return tx.Create(&domain.FighterSphere{FighterID: fighterID, SphereID: sphereID}).Error
	})
}

// SpheresOf returns the node ids a fighter has bought.
func (r *FighterRepo) SpheresOf(fighterID uint) ([]int32, error) {
	var out []int32
	err := r.db.Model(&domain.FighterSphere{}).
		Where("fighter_id = ?", fighterID).
		Order("sphere_id ASC").
		Pluck("sphere_id", &out).Error
	return out, err
}
