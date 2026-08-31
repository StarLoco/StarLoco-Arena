package web

import (
	"net/http"
	"strings"
)

// The legal pages exist because this project runs a public service built on
// somebody else's intellectual property, and the honest way to do that is to
// say so on the site rather than only in a file on GitHub that no player reads.
//
// Three pages, all public and all crawlable:
//
//   - /legal   — non-affiliation, whose IP this is, and how a rights holder
//     gets material removed without needing a lawyer.
//   - /privacy — the GDPR notice. The portal is operated from the EU and holds
//     account records, so this is not optional.
//   - /terms   — what a player is agreeing to, and what gets them banned.
//
// They carry no state beyond the contact address, so they share one model.

// legalData backs all three static legal pages.
type legalData struct {
	*baseData
	// ContactEmail is the address a rights holder or a data subject writes to.
	// Empty renders a fallback line rather than a broken mailto:, because a
	// takedown page that cannot be contacted is worse than no page at all.
	ContactEmail string
}

// contactEmail is the address shown on the legal and privacy pages.
func (s *Server) contactEmail() string {
	return strings.TrimSpace(s.cfg.ContactEmail)
}

func (s *Server) handleLegal(w http.ResponseWriter, r *http.Request) {
	s.render(w, http.StatusOK, "legal.html", &legalData{
		baseData:     s.newBase(w, r, s.tr(r, "legal.title"), "legal"),
		ContactEmail: s.contactEmail(),
	})
}

func (s *Server) handlePrivacy(w http.ResponseWriter, r *http.Request) {
	s.render(w, http.StatusOK, "privacy.html", &legalData{
		baseData:     s.newBase(w, r, s.tr(r, "privacy.title"), "privacy"),
		ContactEmail: s.contactEmail(),
	})
}

func (s *Server) handleTerms(w http.ResponseWriter, r *http.Request) {
	s.render(w, http.StatusOK, "terms.html", &legalData{
		baseData:     s.newBase(w, r, s.tr(r, "terms.title"), "terms"),
		ContactEmail: s.contactEmail(),
	})
}
