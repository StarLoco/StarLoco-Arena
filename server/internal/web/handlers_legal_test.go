package web

import (
	"net/http"
	"net/http/cookiejar"
	"net/http/httptest"
	"net/url"
	"strings"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/config"
)

// newPortalWith is newPortal with a config tweak, for the handful of cases
// below that need a non-default setting (a contact address, a download link).
func newPortalWith(t *testing.T, tweak func(*config.WebConfig)) *portal {
	t.Helper()
	s, st := newTestServer(t, tweak)
	srv := httptest.NewServer(s.Handler())
	t.Cleanup(srv.Close)

	jar, err := cookiejar.New(nil)
	if err != nil {
		t.Fatalf("cookiejar: %v", err)
	}
	return &portal{t: t, srv: srv, cl: &http.Client{Jar: jar}, st: st, self: s}
}

// ---------------------------------------------------------------------------
// The legal pages
// ---------------------------------------------------------------------------

// The three legal pages are the project's public statement of what it is, what
// it does not distribute, and how a rights holder gets material removed. They
// are worthless if they only exist in English, or if a crawler cannot find
// them, so both properties are pinned here.
func TestLegalPagesRenderInEveryLanguage(t *testing.T) {
	p := newPortalWith(t, func(c *config.WebConfig) {
		c.ContactEmail = "legal@example.com"
	})

	for _, lang := range Languages {
		for _, path := range []string{"/legal", "/privacy", "/terms"} {
			pg := p.get("/" + lang + path)
			if pg.Code != http.StatusOK {
				t.Errorf("%s in %s: status = %d", path, lang, pg.Code)
				continue
			}
			// A raw key in the body means a missing translation: catalog.t
			// falls back to returning the key itself.
			if strings.Contains(pg.Body, "legal.affiliation.body") ||
				strings.Contains(pg.Body, "privacy.rights.body") ||
				strings.Contains(pg.Body, "terms.conduct.body") {
				t.Errorf("%s in %s: an untranslated key leaked into the page", path, lang)
			}
		}
	}
}

// The non-affiliation statement is the single most important sentence on the
// site from a rights holder's point of view, so it must be on EVERY page and
// not only on /legal.
func TestNonAffiliationNoticeIsOnEveryPage(t *testing.T) {
	p := newPortal(t)

	for _, path := range []string{"/", "/status", "/ladder", "/login", "/register", "/legal"} {
		pg := p.get(path)
		if pg.Code != http.StatusOK {
			t.Fatalf("%s: status = %d", path, pg.Code)
		}
		if !strings.Contains(pg.Body, "Ankama") {
			t.Errorf("%s does not name Ankama anywhere - the footer notice is missing", path)
		}
		if !strings.Contains(strings.ToLower(pg.Body), "not affiliated") {
			t.Errorf("%s is missing the non-affiliation notice", path)
		}
		if !strings.Contains(pg.Body, "/legal") {
			t.Errorf("%s does not link to the legal notice", path)
		}
	}
}

// A takedown page with no address on it is not a takedown process. When the
// operator has set one it must be reachable; when they have not, the page must
// say so rather than rendering a broken mailto:.
func TestTakedownContactIsShownOrExplained(t *testing.T) {
	withEmail := newPortalWith(t, func(c *config.WebConfig) {
		c.ContactEmail = "takedown@example.com"
	})
	pg := withEmail.get("/legal")
	if !strings.Contains(pg.Body, "mailto:takedown@example.com") {
		t.Error("configured contact address is not linked on /legal")
	}
	if pg = withEmail.get("/privacy"); !strings.Contains(pg.Body, "mailto:takedown@example.com") {
		t.Error("configured contact address is not linked on /privacy")
	}

	blank := newPortal(t)
	pg = blank.get("/legal")
	if strings.Contains(pg.Body, "mailto:") {
		t.Error("a mailto: was rendered despite no contact address being configured")
	}
	if !strings.Contains(pg.Body, "has not published a contact address") {
		t.Error("no explanation shown in place of the missing contact address")
	}
}

func TestLegalPagesAreCrawlable(t *testing.T) {
	p := newPortal(t)
	sitemap := p.get("/sitemap.xml")
	if sitemap.Code != http.StatusOK {
		t.Fatalf("sitemap status = %d", sitemap.Code)
	}
	for _, path := range []string{"/legal", "/privacy", "/terms"} {
		if !strings.Contains(sitemap.Body, path) {
			t.Errorf("%s is not listed in the sitemap", path)
		}
	}
}

// The default build must not publish a link to a copy of the retail client.
// This is the regression guard for the compiled-in mirror that used to ship
// enabled, so every operator republished it without ever choosing to.
func TestNoClientDownloadLinkByDefault(t *testing.T) {
	if got := config.Default().Web.ClientDownloadURL; got != "" {
		t.Fatalf("default client_download_url = %q, want empty: a fresh install "+
			"must not publish a link to the game client", got)
	}

	p := newPortal(t)
	for _, path := range []string{"/", "/status"} {
		body := p.get(path).Body
		if strings.Contains(body, "mega.nz") {
			t.Errorf("%s links a file-locker mirror by default", path)
		}
		if strings.Contains(body, "Download the game") || strings.Contains(body, "Download the client") {
			t.Errorf("%s offers a client download despite no URL being configured", path)
		}
	}
}

// ---------------------------------------------------------------------------
// Self-service erasure (GDPR art. 17)
// ---------------------------------------------------------------------------

// deleteAccount drives the real form: fetch the page, scrape its CSRF token,
// submit. Anything less would not exercise requirePost.
func (p *portal) deleteAccount(password, confirm string) page {
	p.t.Helper()
	form := p.get("/account/delete")
	return p.post("/account/delete", url.Values{
		"csrf_token": {p.csrfFrom(form)},
		"password":   {password},
		"confirm":    {confirm},
	})
}

func TestSelfDeleteRemovesEverythingAndSignsOut(t *testing.T) {
	p := newPortal(t)
	p.register("Leaver", "hunter2xyz")

	if _, err := p.st.Accounts.FindByName("Leaver"); err != nil {
		t.Fatalf("account was not created: %v", err)
	}

	pg := p.deleteAccount("hunter2xyz", "Leaver")
	if pg.Code != http.StatusOK {
		t.Fatalf("delete: status = %d, body: %s", pg.Code, pg.Body)
	}

	if _, err := p.st.Accounts.FindByName("Leaver"); err == nil {
		t.Fatal("the account still exists after the player deleted it")
	}

	// The session must be gone too: it now points at a row that is not there,
	// and leaving it set renders every page as a signed-in visitor whose
	// account cannot be loaded.
	if body := p.get("/").Body; strings.Contains(body, "Leaver") {
		t.Error("still signed in as the deleted account")
	}
	if pg := p.get("/account"); pg.URL != "/login" {
		t.Errorf("after deletion /account went to %q, want the sign-in page", pg.URL)
	}
}

// Deletion is irreversible, so it takes two independent confirmations. Neither
// may be skippable.
func TestSelfDeleteRefusesWithoutBothConfirmations(t *testing.T) {
	for _, tc := range []struct {
		name              string
		password, confirm string
		wantStatus        int
	}{
		{"wrong password", "wrongpassword", "Stayer", http.StatusUnauthorized},
		{"wrong account name", "hunter2xyz", "SomeoneElse", http.StatusBadRequest},
		{"empty confirmation", "hunter2xyz", "", http.StatusBadRequest},
	} {
		t.Run(tc.name, func(t *testing.T) {
			p := newPortal(t)
			p.register("Stayer", "hunter2xyz")

			pg := p.deleteAccount(tc.password, tc.confirm)
			if pg.Code != tc.wantStatus {
				t.Errorf("status = %d, want %d", pg.Code, tc.wantStatus)
			}
			if _, err := p.st.Accounts.FindByName("Stayer"); err != nil {
				t.Fatal("the account was deleted despite a failed confirmation")
			}
		})
	}
}

// The account page is where a player sees everything held about them, so it is
// also where the erasure route has to be: right of access and right to erasure
// in the same place.
func TestAccountPageOffersDeletion(t *testing.T) {
	p := newPortal(t)
	p.register("Curious", "hunter2xyz")

	pg := p.get("/account")
	if pg.Code != http.StatusOK {
		t.Fatalf("account page status = %d", pg.Code)
	}
	if !strings.Contains(pg.Body, "/account/delete") {
		t.Error("the account page does not offer a way to delete the account")
	}
}
