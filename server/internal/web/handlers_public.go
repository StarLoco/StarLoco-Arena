package web

import (
	"encoding/xml"
	"io"
	"net/http"
	"strconv"
	"strings"

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

// handleIndex serves the landing page to EVERYONE, signed in or not.
//
// It used to bounce a signed-in visitor to /account, on the reasoning that they
// want their account rather than the marketing page. That reasoning only holds
// immediately after signing in - which the login handler already does itself by
// redirecting to /account. As a rule for "/" it made the site's own "Home" link
// and the header logo unusable for anybody logged in: both point at "/", so
// both silently landed back on /account and the landing page became unreachable
// without signing out.
// publicPages are the crawlable pages, listed per language in the sitemap.
// Anything requiring a session is deliberately absent: a crawler can never see
// it, so listing it would only advertise URLs that answer with a redirect.
var publicPages = []string{"/", "/ladder", "/status", "/login", "/register"}

// handleRobots points crawlers at the sitemap and keeps them out of the parts
// of the site that are operator-only or per-visitor.
func (s *Server) handleRobots(w http.ResponseWriter, r *http.Request) {
	var b strings.Builder
	b.WriteString("User-agent: *\n")
	b.WriteString("Disallow: /admin\n")
	b.WriteString("Disallow: /account\n")
	b.WriteString("Disallow: /lang\n")
	b.WriteString("Allow: /\n")
	if base := s.baseURL(r); base != "" {
		b.WriteString("\nSitemap: " + base + "/sitemap.xml\n")
	}
	w.Header().Set("Content-Type", "text/plain; charset=utf-8")
	_, _ = io.WriteString(w, b.String())
}

// handleSitemap lists every public page in every language, each entry carrying
// xhtml:link alternates. That is what tells a search engine the four language
// URLs are translations of one page rather than four competing duplicates.
func (s *Server) handleSitemap(w http.ResponseWriter, r *http.Request) {
	base := s.baseURL(r)
	if base == "" {
		http.NotFound(w, r)
		return
	}
	var b strings.Builder
	b.WriteString(`<?xml version="1.0" encoding="UTF-8"?>` + "\n")
	b.WriteString(`<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" ` +
		`xmlns:xhtml="http://www.w3.org/1999/xhtml">` + "\n")
	for _, page := range publicPages {
		p := page
		if p == "/" {
			p = "/"
		}
		for _, lang := range Languages {
			b.WriteString("  <url>\n")
			b.WriteString("    <loc>" + xmlEscape(base+"/"+lang+p) + "</loc>\n")
			for _, alt := range Languages {
				b.WriteString(`    <xhtml:link rel="alternate" hreflang="` + alt +
					`" href="` + xmlEscape(base+"/"+alt+p) + `"/>` + "\n")
			}
			b.WriteString(`    <xhtml:link rel="alternate" hreflang="x-default" href="` +
				xmlEscape(base+"/en"+p) + `"/>` + "\n")
			b.WriteString("  </url>\n")
		}
	}
	b.WriteString("</urlset>\n")

	w.Header().Set("Content-Type", "application/xml; charset=utf-8")
	_, _ = io.WriteString(w, b.String())
}

func xmlEscape(s string) string {
	var b strings.Builder
	_ = xml.EscapeText(&b, []byte(s))
	return b.String()
}

// handleDiscord sends visitors to the community Discord.
//
// It exists as a redirect rather than a raw invite link because the game
// client's login screen points here: those URLs are compiled into the client
// the players have already downloaded, so the invite behind it has to be
// changeable server-side. An unset invite falls back to the home page rather
// than a dead end.
func (s *Server) handleDiscord(w http.ResponseWriter, r *http.Request) {
	target := strings.TrimSpace(s.cfg.DiscordURL)
	if target == "" {
		s.log.Debug("web: /discord hit but web.discord_url is not set")
		redirect(w, r, "/")
		return
	}
	http.Redirect(w, r, target, http.StatusFound)
}

func (s *Server) handleIndex(w http.ResponseWriter, r *http.Request) {
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
