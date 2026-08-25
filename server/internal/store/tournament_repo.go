package store

import (
	"errors"

	"gorm.io/gorm"
	"gorm.io/gorm/clause"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TournamentRepo persists the standing tournaments the server offers. They were
// a compiled-in table until the web console needed to edit them.
type TournamentRepo struct{ db *gorm.DB }

// List returns every tournament, in the order players see them.
func (r *TournamentRepo) List() ([]domain.Tournament, error) {
	var out []domain.Tournament
	err := r.db.Order("position ASC, id ASC").Find(&out).Error
	return out, err
}

// ListEnabled returns only the tournaments currently offered to players.
func (r *TournamentRepo) ListEnabled() ([]domain.Tournament, error) {
	var out []domain.Tournament
	err := r.db.Where("enabled = ?", true).Order("position ASC, id ASC").Find(&out).Error
	return out, err
}

// Get loads one tournament by row id.
func (r *TournamentRepo) Get(id uint) (*domain.Tournament, error) {
	var t domain.Tournament
	err := r.db.First(&t, id).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, ErrNotFound
	}
	if err != nil {
		return nil, err
	}
	return &t, nil
}

// GetByWireID loads the tournament a client is referring to.
func (r *TournamentRepo) GetByWireID(wireID int64) (*domain.Tournament, error) {
	id := wireID - domain.TournamentWireBase
	if id <= 0 {
		return nil, ErrNotFound
	}
	return r.Get(uint(id))
}

// Create inserts a tournament.
func (r *TournamentRepo) Create(t *domain.Tournament) error {
	return r.db.Create(t).Error
}

// Update saves an edited tournament. It writes the editable columns explicitly
// so a zero value (an emptied description, a disabled flag) is persisted —
// GORM's Updates with a struct skips zero values and would silently ignore
// exactly the changes an admin is most likely to make.
func (r *TournamentRepo) Update(t *domain.Tournament) error {
	return r.db.Model(&domain.Tournament{}).Where("id = ?", t.ID).
		Updates(map[string]any{
			"def_id":            t.DefID,
			"name":              t.Name,
			"short":             t.Short,
			"description":       t.Description,
			"organizer":         t.Organizer,
			"enabled":           t.Enabled,
			"registration_open": t.RegistrationOpen,
			"position":          t.Position,
		}).Error
}

// Delete removes a tournament.
func (r *TournamentRepo) Delete(id uint) error {
	res := r.db.Delete(&domain.Tournament{}, id)
	if res.Error != nil {
		return res.Error
	}
	if res.RowsAffected == 0 {
		return ErrNotFound
	}
	// Registrations are keyed by WIRE id and have no FK, so they would otherwise
	// outlive the tournament as orphans — and, because the wire id is derived
	// from the row id, be inherited by whatever row reused that id later.
	return r.DeleteRegistrationsFor(domain.TournamentWireBase + int64(id))
}

// --- registrations ---
//
// Keyed by the tournament's WIRE id: that is what the client sends in 4607 and
// what the manager caches, and it is stable across restarts because it is derived
// from the row id.

// ListRegistrations returns every stored registration, for loading the in-memory
// cache at startup.
func (r *TournamentRepo) ListRegistrations() ([]domain.TournamentRegistration, error) {
	var out []domain.TournamentRegistration
	err := r.db.Find(&out).Error
	return out, err
}

// AddRegistration records one coach's entry, idempotently: the (coach, tid) pair
// carries a unique index, and a duplicate insert is not an error here because the
// caller has usually already decided the registration is new.
func (r *TournamentRepo) AddRegistration(coachID uint, tid int64) error {
	reg := domain.TournamentRegistration{CoachID: coachID, TournamentWireID: tid}
	return r.db.Where("coach_id = ? AND tournament_wire_id = ?", coachID, tid).
		FirstOrCreate(&reg).Error
}

// RemoveRegistration withdraws one coach from one tournament.
func (r *TournamentRepo) RemoveRegistration(coachID uint, tid int64) error {
	return r.db.Where("coach_id = ? AND tournament_wire_id = ?", coachID, tid).
		Delete(&domain.TournamentRegistration{}).Error
}

// DeleteRegistrationsFor drops every registration for one tournament.
func (r *TournamentRepo) DeleteRegistrationsFor(tid int64) error {
	return r.db.Where("tournament_wire_id = ?", tid).
		Delete(&domain.TournamentRegistration{}).Error
}

// Count returns how many tournaments exist.
func (r *TournamentRepo) Count() (int64, error) {
	var n int64
	err := r.db.Model(&domain.Tournament{}).Count(&n).Error
	return n, err
}

// DefaultTournaments is the line-up a fresh server starts with. It is the table
// that used to be compiled into the game package, kept so a new install behaves
// exactly as before and an operator has something to edit rather than a blank
// page.
//
// The def ids are real no-card client definitions: 1 and 4 are classic 1v1
// (team type 1), 17 is the graveyard type (3), which is why the third is named
// for it.
func DefaultTournaments() []domain.Tournament {
	return []domain.Tournament{
		{
			DefID: 1, Position: 1,
			Name:        "Tournoi 1v1 Classique",
			Short:       "Classique",
			Description: "Affrontez les meilleurs coachs dans un tournoi 1 contre 1.",
			Organizer:   "StarLoco",
			Enabled:     true, RegistrationOpen: true,
		},
		{
			DefID: 4, Position: 2,
			Name:        "Tournoi des Champions",
			Short:       "Champions",
			Description: "Un tournoi d'elite reserve aux coachs les plus aguerris.",
			Organizer:   "StarLoco",
			Enabled:     true, RegistrationOpen: true,
		},
		{
			DefID: 17, Position: 3,
			Name:        "Tournoi du Cimetiere",
			Short:       "Cimetiere",
			Description: "Un tournoi hante ou seuls les plus braves osent s'inscrire.",
			Organizer:   "StarLoco",
			Enabled:     true, RegistrationOpen: true,
		},
	}
}

// SeedDefaults inserts DefaultTournaments when the table is empty, and reports
// how many it added.
//
// It seeds only an EMPTY table on purpose: an operator who deletes a tournament
// means it, and having it reappear on the next restart would be maddening.
func (r *TournamentRepo) SeedDefaults() (int, error) {
	n, err := r.Count()
	if err != nil || n > 0 {
		return 0, err
	}
	defaults := DefaultTournaments()
	for i := range defaults {
		if err := r.Create(&defaults[i]); err != nil {
			return i, err
		}
	}
	return len(defaults), nil
}

// ListSlots returns every stored bracket slot (all tournaments), for priming the
// in-memory bracket at boot.
func (r *TournamentRepo) ListSlots() ([]domain.TournamentSlot, error) {
	var out []domain.TournamentSlot
	err := r.db.Find(&out).Error
	return out, err
}

// SetSlot records the occupant of a bracket slot, replacing any previous one.
//
// Upsert rather than insert: a slot is decided once, but a fixture can be
// replayed (a disconnect, a GM ending a fight), and the later result is the one
// that stands.
func (r *TournamentRepo) SetSlot(tid int64, slot int32, coachID uint) error {
	return r.db.Clauses(clause.OnConflict{
		Columns:   []clause.Column{{Name: "tournament_wire_id"}, {Name: "slot"}},
		DoUpdates: clause.AssignmentColumns([]string{"coach_id"}),
	}).Create(&domain.TournamentSlot{
		TournamentWireID: tid, Slot: slot, CoachID: coachID,
	}).Error
}
