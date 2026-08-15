package web

import (
	"errors"
	"net/http"
	"strconv"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// tournamentRow decorates a stored tournament with what the admin list shows
// alongside it: how the client will read its definition, and how many coaches
// have signed up.
type tournamentRow struct {
	domain.Tournament
	TeamType      string
	KnownDef      bool
	Registrations int
}

type tournamentsData struct {
	*baseData
	AdminTab string
	Rows     []tournamentRow
	// CatalogueLoaded reports whether the decoded definitions are available.
	// Without them the editor still works, but nothing can be validated.
	CatalogueLoaded bool
}

func (s *Server) decorate(t domain.Tournament) tournamentRow {
	row := tournamentRow{Tournament: t, TeamType: "unknown"}
	if s.tournamentDefs != nil {
		if d := s.tournamentDefs.Get(int16(t.DefID)); d != nil {
			row.KnownDef = true
			row.TeamType = teamTypeLabel(d.TeamType)
		}
	} else {
		// Nothing to check against, so do not cry wolf.
		row.KnownDef = true
	}
	if s.live.TournamentRegistrations != nil {
		row.Registrations = s.live.TournamentRegistrations(t.WireID())
	}
	return row
}

func (s *Server) handleAdminTournaments(w http.ResponseWriter, r *http.Request, sess session) {
	list, err := s.store.Tournaments.List()
	if err != nil {
		s.log.Error("web: list tournaments failed", "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "The tournaments could not be loaded.")
		return
	}
	d := &tournamentsData{
		baseData:        s.newBase(w, r, "Tournaments", "admin"),
		AdminTab:        "tournaments",
		CatalogueLoaded: len(s.tournamentChoices()) > 0,
	}
	for _, t := range list {
		d.Rows = append(d.Rows, s.decorate(t))
	}
	s.render(w, http.StatusOK, "admin_tournaments.html", d)
}

// ---------------------------------------------------------------------------
// Create / edit
// ---------------------------------------------------------------------------

type tournamentFormData struct {
	*baseData
	AdminTab string
	Error    string
	// IsNew switches the form between create and edit.
	IsNew   bool
	Form    domain.Tournament
	Choices []tournamentChoice
	// Registrations is shown when editing, so an admin knows whether anyone
	// would be affected by a change.
	Registrations int
}

func (s *Server) newTournamentForm(w http.ResponseWriter, r *http.Request, title string, isNew bool) *tournamentFormData {
	return &tournamentFormData{
		baseData: s.newBase(w, r, title, "admin"),
		AdminTab: "tournaments",
		IsNew:    isNew,
		Choices:  s.tournamentChoices(),
	}
}

func (s *Server) handleAdminTournamentNew(w http.ResponseWriter, r *http.Request, sess session) {
	d := s.newTournamentForm(w, r, "New tournament", true)
	d.Form = domain.Tournament{
		Enabled:          true,
		RegistrationOpen: true,
		Organizer:        "StarLoco",
	}
	// Default to the first offered definition so the form is valid as-is.
	if len(d.Choices) > 0 {
		d.Form.DefID = d.Choices[0].DefID
	}
	s.render(w, http.StatusOK, "admin_tournament_form.html", d)
}

// readTournamentForm pulls a tournament out of a posted form.
func readTournamentForm(r *http.Request) domain.Tournament {
	defID, _ := strconv.ParseUint(r.PostFormValue("def_id"), 10, 16)
	pos, _ := strconv.Atoi(r.PostFormValue("position"))
	return domain.Tournament{
		DefID:            uint16(defID),
		Name:             r.PostFormValue("name"),
		Short:            r.PostFormValue("short"),
		Description:      r.PostFormValue("description"),
		Organizer:        r.PostFormValue("organizer"),
		Enabled:          r.PostFormValue("enabled") != "",
		RegistrationOpen: r.PostFormValue("registration_open") != "",
		Position:         pos,
	}
}

func (s *Server) handleAdminTournamentCreate(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	d := s.newTournamentForm(w, r, "New tournament", true)
	d.Form = readTournamentForm(r)

	if err := s.validateTournament(&d.Form); err != nil {
		d.Error = err.Error()
		s.render(w, http.StatusBadRequest, "admin_tournament_form.html", d)
		return
	}
	t := d.Form
	if err := s.store.Tournaments.Create(&t); err != nil {
		s.log.Error("web: create tournament failed", "err", err)
		d.Error = "The tournament could not be saved."
		s.render(w, http.StatusInternalServerError, "admin_tournament_form.html", d)
		return
	}
	s.log.Info("web: tournament created", "name", t.Name, "defID", t.DefID, "by", sess.AccountID)
	setFlash(w, "success", "Tournament “"+t.Name+"” created.")
	redirect(w, r, "/admin/tournaments")
}

func (s *Server) handleAdminTournamentEdit(w http.ResponseWriter, r *http.Request, sess session) {
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}
	t, err := s.store.Tournaments.Get(id)
	if err != nil {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}
	d := s.newTournamentForm(w, r, t.Name, false)
	d.Form = *t
	if s.live.TournamentRegistrations != nil {
		d.Registrations = s.live.TournamentRegistrations(t.WireID())
	}
	s.render(w, http.StatusOK, "admin_tournament_form.html", d)
}

func (s *Server) handleAdminTournamentSave(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}
	existing, err := s.store.Tournaments.Get(id)
	if err != nil {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}

	d := s.newTournamentForm(w, r, existing.Name, false)
	d.Form = readTournamentForm(r)
	d.Form.ID = existing.ID

	if err := s.validateTournament(&d.Form); err != nil {
		d.Error = err.Error()
		s.render(w, http.StatusBadRequest, "admin_tournament_form.html", d)
		return
	}
	if err := s.store.Tournaments.Update(&d.Form); err != nil {
		s.log.Error("web: update tournament failed", "err", err)
		d.Error = "The tournament could not be saved."
		s.render(w, http.StatusInternalServerError, "admin_tournament_form.html", d)
		return
	}
	s.log.Info("web: tournament updated", "name", d.Form.Name, "id", id, "by", sess.AccountID)
	setFlash(w, "success", "Tournament “"+d.Form.Name+"” saved.")
	redirect(w, r, "/admin/tournaments")
}

// ---------------------------------------------------------------------------
// Delete / enable
// ---------------------------------------------------------------------------

func (s *Server) handleAdminTournamentDelete(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}
	t, err := s.store.Tournaments.Get(id)
	if err != nil {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}
	name := t.Name

	if err := s.store.Tournaments.Delete(id); err != nil {
		if errors.Is(err, store.ErrNotFound) {
			s.renderError(w, r, http.StatusNotFound, "No such tournament.")
			return
		}
		s.log.Error("web: delete tournament failed", "err", err)
		setFlash(w, "error", "That tournament could not be deleted.")
	} else {
		s.log.Info("web: tournament deleted", "name", name, "id", id, "by", sess.AccountID)
		setFlash(w, "success", "Tournament “"+name+"” deleted.")
	}
	redirect(w, r, "/admin/tournaments")
}

// handleAdminTournamentToggle flips Enabled, the quickest way to take an event
// off the board without losing how it was set up.
func (s *Server) handleAdminTournamentToggle(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}
	t, err := s.store.Tournaments.Get(id)
	if err != nil {
		s.renderError(w, r, http.StatusNotFound, "No such tournament.")
		return
	}
	t.Enabled = !t.Enabled
	if err := s.store.Tournaments.Update(t); err != nil {
		s.log.Error("web: toggle tournament failed", "err", err)
		setFlash(w, "error", "That change could not be saved.")
	} else {
		s.log.Info("web: tournament visibility changed",
			"name", t.Name, "enabled", t.Enabled, "by", sess.AccountID)
		if t.Enabled {
			setFlash(w, "success", "“"+t.Name+"” is now visible to players.")
		} else {
			setFlash(w, "info", "“"+t.Name+"” is hidden from players.")
		}
	}
	redirect(w, r, "/admin/tournaments")
}
