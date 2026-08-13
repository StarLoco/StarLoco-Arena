package web

import (
	"net/http"
	"strconv"

	"github.com/StarLoco/arena-2.70/internal/store"
)

// indexData is the landing page's model.
type indexData struct {
	*baseData
	// TotalAccounts and RankedCoaches feed the stat band. They are lifetime
	// totals, not live state, which is exactly what a visitor deciding whether
	// to download the client wants to know.
	TotalAccounts int64
	RankedCoaches int64
	TotalFights   int64
}

func (s *Server) handleIndex(w http.ResponseWriter, r *http.Request) {
	// A signed-in visitor wants their account, not the marketing page.
	if _, ok := s.readSession(r); ok {
		redirect(w, r, "/account")
		return
	}

	d := &indexData{baseData: s.newBase(w, r, "", "home")}
	if n, err := s.store.Accounts.Count(); err == nil {
		d.TotalAccounts = n
	}
	if n, err := s.store.Coaches.LadderCount(); err == nil {
		d.RankedCoaches = int64(n)
	}
	if n, err := s.totalFights(); err == nil {
		d.TotalFights = n
	}
	s.render(w, http.StatusOK, "index.html", d)
}

// totalFights sums every coach's lifetime fight counter. It is the closest
// thing the schema has to "battles played" — there is no fight history table,
// fights are only ever counted onto the two coaches that took part.
//
// That means the sum double-counts a 1v1 (both sides record it), so it is
// halved to give the number of *fights* rather than the number of
// participations.
func (s *Server) totalFights() (int64, error) {
	var sum int64
	err := s.store.DB().Table("coaches").
		Select("COALESCE(SUM(stat_fights), 0)").
		Scan(&sum).Error
	return sum / 2, err
}

// ---------------------------------------------------------------------------
// Public server status
// ---------------------------------------------------------------------------

// statusData backs the public status page. It deliberately carries only
// aggregate counts and no player-identifying information: it exists so a
// visitor can answer "is the server up, and is anyone playing" without an
// account, and it is reachable by anyone on the internet.
type statusData struct {
	*baseData
	UptimeSeconds int64
	TotalAccounts int64
}

func (s *Server) handleStatus(w http.ResponseWriter, r *http.Request) {
	d := &statusData{
		baseData:      s.newBase(w, r, "Server status", "status"),
		UptimeSeconds: s.uptimeSeconds(),
	}
	if n, err := s.store.Accounts.Count(); err == nil {
		d.TotalAccounts = n
	}
	s.render(w, http.StatusOK, "status.html", d)
}

// ---------------------------------------------------------------------------
// Public ladder
// ---------------------------------------------------------------------------

// ladderPageSize matches what feels right on one screen; the client's own
// ladder pages in similar chunks.
const ladderPageSize = 25

type ladderData struct {
	*baseData
	Entries  []store.LadderEntry
	Page     int
	LastPage int
	Total    int
	Offset   int
}

// handleLadder shows the 1v1 leaderboard. It is public because a leaderboard
// nobody can see is not a leaderboard, and it exposes only what the in-game
// ladder already shows every player: coach name, rating and win/loss record.
func (s *Server) handleLadder(w http.ResponseWriter, r *http.Request) {
	total, err := s.store.Coaches.LadderCount()
	if err != nil {
		s.log.Error("web: ladder count failed", "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "The ladder could not be loaded.")
		return
	}

	page := 1
	if v := r.URL.Query().Get("page"); v != "" {
		if n, err := strconv.Atoi(v); err == nil && n > 1 {
			page = n
		}
	}
	lastPage := (total + ladderPageSize - 1) / ladderPageSize
	if lastPage < 1 {
		lastPage = 1
	}
	if page > lastPage {
		page = lastPage
	}
	offset := (page - 1) * ladderPageSize

	entries, err := s.store.Coaches.LadderPage(offset, ladderPageSize)
	if err != nil {
		s.log.Error("web: ladder page failed", "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "The ladder could not be loaded.")
		return
	}

	s.render(w, http.StatusOK, "ladder.html", &ladderData{
		baseData: s.newBase(w, r, "Leaderboard", "ladder"),
		Entries:  entries,
		Page:     page,
		LastPage: lastPage,
		Total:    total,
		Offset:   offset,
	})
}
