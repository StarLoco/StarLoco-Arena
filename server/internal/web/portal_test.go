package web

import (
	"io"
	"net/http"
	"net/http/cookiejar"
	"net/http/httptest"
	"net/url"
	"regexp"
	"strconv"
	"strings"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// portal drives the site the way a browser does: over a real socket, with a
// cookie jar, following redirects. Handler-level tests cannot catch a mistake
// in cookie attributes or a redirect loop; this can.
type portal struct {
	t    *testing.T
	srv  *httptest.Server
	cl   *http.Client
	st   *store.Store
	self *Server
}

func newPortal(t *testing.T) *portal {
	t.Helper()
	s, st := newTestServer(t, nil)
	srv := httptest.NewServer(s.Handler())
	t.Cleanup(srv.Close)

	jar, err := cookiejar.New(nil)
	if err != nil {
		t.Fatalf("cookiejar: %v", err)
	}
	return &portal{t: t, srv: srv, cl: &http.Client{Jar: jar}, st: st, self: s}
}

type page struct {
	Code int
	Body string
	URL  string
}

func (p *portal) get(path string) page {
	p.t.Helper()
	resp, err := p.cl.Get(p.srv.URL + path)
	if err != nil {
		p.t.Fatalf("GET %s: %v", path, err)
	}
	defer func() { _ = resp.Body.Close() }()
	body, _ := io.ReadAll(resp.Body)
	return page{Code: resp.StatusCode, Body: string(body), URL: resp.Request.URL.Path}
}

func (p *portal) post(path string, form url.Values) page {
	p.t.Helper()
	resp, err := p.cl.PostForm(p.srv.URL+path, form)
	if err != nil {
		p.t.Fatalf("POST %s: %v", path, err)
	}
	defer func() { _ = resp.Body.Close() }()
	body, _ := io.ReadAll(resp.Body)
	return page{Code: resp.StatusCode, Body: string(body), URL: resp.Request.URL.Path}
}

var csrfRe = regexp.MustCompile(`name="csrf_token" value="([^"]+)"`)

// csrfFrom scrapes the token out of a rendered page, exactly as a browser
// would submit it.
func (p *portal) csrfFrom(pg page) string {
	p.t.Helper()
	m := csrfRe.FindStringSubmatch(pg.Body)
	if m == nil {
		p.t.Fatalf("no CSRF token in page %s", pg.URL)
	}
	return m[1]
}

// register creates an account through the site and leaves it signed in.
func (p *portal) register(login, password string) page {
	p.t.Helper()
	return p.post("/register", url.Values{
		"login": {login}, "password": {password}, "confirm": {password},
	})
}

func (p *portal) login(login, password string) page {
	p.t.Helper()
	return p.post("/login", url.Values{"login": {login}, "password": {password}})
}

func (p *portal) logout() {
	p.t.Helper()
	pg := p.get("/account")
	p.post("/logout", url.Values{"csrf_token": {p.csrfFrom(pg)}})
}

// seedPlayer creates an ordinary account with a coach and some data, without
// going through the site.
func (p *portal) seedPlayer(login, coachName string) *domain.Account {
	p.t.Helper()
	acc, err := p.st.Accounts.CreateAccount(login, "password1", false)
	if err != nil {
		p.t.Fatalf("CreateAccount: %v", err)
	}
	coach, err := p.st.Coaches.Create(acc.ID, coachName, 1, 1, 0)
	if err != nil {
		p.t.Fatalf("Coaches.Create: %v", err)
	}
	if err := p.st.Accounts.LinkCoach(acc.ID, coach.ID); err != nil {
		p.t.Fatalf("LinkCoach: %v", err)
	}
	if err := p.st.Fighters.Create(&domain.Fighter{
		CoachID: coach.ID, BreedID: 3, Name: coachName + "Iop",
	}); err != nil {
		p.t.Fatalf("Fighters.Create: %v", err)
	}
	if err := p.st.Coaches.GrantCards(coach.ID, []store.GrantCard{{TemplateID: 900, Quantity: 2}}); err != nil {
		p.t.Fatalf("GrantCards: %v", err)
	}
	return acc
}

// ---------------------------------------------------------------------------
// Sign in / out
// ---------------------------------------------------------------------------

func TestLoginFlow(t *testing.T) {
	p := newPortal(t)
	p.seedPlayer("player", "Rushu")

	if pg := p.login("player", "wrong"); pg.Code != http.StatusUnauthorized {
		t.Fatalf("bad password: status = %d, want 401", pg.Code)
	}
	// The message must not reveal whether the account exists.
	pg := p.login("nosuchaccount", "whatever")
	if !strings.Contains(pg.Body, "Wrong account name or password") {
		t.Error("the login error distinguishes a missing account from a wrong password")
	}

	pg = p.login("player", "password1")
	if pg.Code != http.StatusOK || pg.URL != "/account" {
		t.Fatalf("login landed on %s (%d), want /account", pg.URL, pg.Code)
	}
	if !strings.Contains(pg.Body, "Rushu") {
		t.Error("the account page does not show the coach")
	}
}

func TestLogoutClearsSession(t *testing.T) {
	p := newPortal(t)
	p.seedPlayer("player", "Rushu")
	p.login("player", "password1")

	p.logout()

	if pg := p.get("/account"); pg.URL != "/login" {
		t.Errorf("after signing out, /account landed on %s, want /login", pg.URL)
	}
}

// The landing page must stay reachable once signed in.
//
// This asserted the opposite - that "/" redirected a signed-in visitor to
// /account, "not the sales pitch". That reasoning only holds for the moment
// just after signing in, which the login handler already covers by redirecting
// there itself. As a rule for "/" it made the site's own Home link and header
// logo dead ends for every logged-in player, because both point at "/".
func TestSignedInVisitorCanStillReachLanding(t *testing.T) {
	p := newPortal(t)
	p.register("player", "password1")
	if pg := p.get("/"); pg.URL != "/" {
		t.Errorf("landing page for a signed-in user went to %s, want / "+
			"(redirecting it makes Home and the logo unusable)", pg.URL)
	}
}

// ...but signing IN still lands on the account page, which is what the
// redirect above was really for.
func TestSigningInLandsOnTheAccountPage(t *testing.T) {
	p := newPortal(t)
	p.seedPlayer("player", "Rushu")
	pg := p.login("player", "password1")
	if pg.URL != "/account" {
		t.Errorf("after signing in, landed on %s, want /account", pg.URL)
	}
}

// ---------------------------------------------------------------------------
// The account page shows everything
// ---------------------------------------------------------------------------

func TestAccountPageRequiresLogin(t *testing.T) {
	p := newPortal(t)
	for _, path := range []string{"/account", "/account/password"} {
		if pg := p.get(path); pg.URL != "/login" {
			t.Errorf("%s was reachable without signing in (landed on %s)", path, pg.URL)
		}
	}
}

func TestAccountPageShowsStoredData(t *testing.T) {
	p := newPortal(t)
	p.seedPlayer("player", "Rushu")
	pg := p.login("player", "password1")

	for _, want := range []string{
		"Rushu",                                           // coach name
		"RushuIop",                                        // fighter
		"Cards owned", "Fighters", "Teams", "Ladder rank", // tiles
		"Friends", "Ignored", "Mail",
	} {
		if !strings.Contains(pg.Body, want) {
			t.Errorf("the account page does not show %q", want)
		}
	}
}

// A fresh registration has no coach; the page must still render rather than
// blowing up on a nil pointer.
func TestAccountPageWithoutCoach(t *testing.T) {
	p := newPortal(t)
	pg := p.register("brandnew", "password1")

	if pg.Code != http.StatusOK {
		t.Fatalf("status = %d", pg.Code)
	}
	if !strings.Contains(pg.Body, "No character yet") {
		t.Error("an account with no coach should say so")
	}
}

// ---------------------------------------------------------------------------
// Password change
// ---------------------------------------------------------------------------

func TestPasswordChange(t *testing.T) {
	p := newPortal(t)
	p.seedPlayer("player", "Rushu")
	p.login("player", "password1")

	form := p.get("/account/password")
	token := p.csrfFrom(form)

	// The current password must be proved.
	pg := p.post("/account/password", url.Values{
		"csrf_token": {token}, "current": {"wrong"},
		"password": {"newpassword"}, "confirm": {"newpassword"},
	})
	if pg.Code != http.StatusUnauthorized {
		t.Errorf("wrong current password: status = %d, want 401", pg.Code)
	}

	// Mismatched confirmation is refused.
	pg = p.post("/account/password", url.Values{
		"csrf_token": {token}, "current": {"password1"},
		"password": {"newpassword"}, "confirm": {"different"},
	})
	if pg.Code != http.StatusBadRequest {
		t.Errorf("mismatch: status = %d, want 400", pg.Code)
	}

	// And the real thing works.
	pg = p.post("/account/password", url.Values{
		"csrf_token": {token}, "current": {"password1"},
		"password": {"newpassword"}, "confirm": {"newpassword"},
	})
	if pg.Code != http.StatusOK || pg.URL != "/account" {
		t.Fatalf("password change landed on %s (%d)", pg.URL, pg.Code)
	}
	acc, err := p.st.Accounts.FindByName("player")
	if err != nil {
		t.Fatalf("FindByName: %v", err)
	}
	if !p.st.Accounts.VerifyPassword(acc, "newpassword") {
		t.Error("the new password does not verify")
	}
	if p.st.Accounts.VerifyPassword(acc, "password1") {
		t.Error("the old password still works")
	}
}

// Without a CSRF token the change must be refused, or any page on the internet
// could rewrite a visitor's password.
func TestPasswordChangeNeedsCSRF(t *testing.T) {
	p := newPortal(t)
	p.seedPlayer("player", "Rushu")
	p.login("player", "password1")

	pg := p.post("/account/password", url.Values{
		"current": {"password1"}, "password": {"newpassword"}, "confirm": {"newpassword"},
	})
	if pg.Code != http.StatusForbidden {
		t.Errorf("status = %d, want 403", pg.Code)
	}
	acc, _ := p.st.Accounts.FindByName("player")
	if !p.st.Accounts.VerifyPassword(acc, "password1") {
		t.Error("the password was changed without a CSRF token")
	}
}

// ---------------------------------------------------------------------------
// Admin gating
// ---------------------------------------------------------------------------

var adminPaths = []string{
	"/admin",
	"/admin/accounts",
	"/admin/accounts/new",
	"/admin/monitoring",
	"/admin/monitoring/pprof/goroutine",
}

func TestAdminRoutesRejectAnonymous(t *testing.T) {
	p := newPortal(t)
	for _, path := range adminPaths {
		if pg := p.get(path); pg.URL != "/login" {
			t.Errorf("%s was reachable without signing in (landed on %s)", path, pg.URL)
		}
	}
}

func TestAdminRoutesRejectOrdinaryPlayers(t *testing.T) {
	p := newPortal(t)
	// First registration would become admin, so burn it on somebody else.
	p.register("owner", "password1")
	p.logout()

	p.seedPlayer("player", "Rushu")
	p.login("player", "password1")

	for _, path := range adminPaths {
		pg := p.get(path)
		if pg.Code != http.StatusForbidden {
			t.Errorf("%s: status = %d, want 403 for a non-admin", path, pg.Code)
		}
	}
}

func TestAdminConsoleRenders(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1") // first account => admin
	p.seedPlayer("player", "Rushu")

	for _, tc := range []struct {
		path string
		want []string
	}{
		{"/admin", []string{"Administration", "Accounts", "Active fights", "Uptime"}},
		{"/admin/accounts", []string{"player", "Rushu"}},
		{"/admin/accounts/new", []string{"New account", "administrator"}},
		{"/admin/monitoring", []string{"Monitoring", "Goroutines", "goroutine"}},
	} {
		pg := p.get(tc.path)
		if pg.Code != http.StatusOK {
			t.Errorf("%s: status = %d", tc.path, pg.Code)
			continue
		}
		for _, want := range tc.want {
			if !strings.Contains(pg.Body, want) {
				t.Errorf("%s is missing %q", tc.path, want)
			}
		}
	}
}

func TestAdminAccountSearch(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	p.seedPlayer("alice", "Alcedon")
	p.seedPlayer("bob", "Bombard")

	// By coach name — the only name most players know each other by.
	pg := p.get("/admin/accounts?q=bomb")
	if !strings.Contains(pg.Body, "bob") {
		t.Error("searching by coach name did not find the account")
	}
	if strings.Contains(pg.Body, "Alcedon") {
		t.Error("the search returned an account it should have filtered out")
	}
}

func TestAdminPprofServesAProfile(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")

	pg := p.get("/admin/monitoring/pprof/goroutine")
	if pg.Code != http.StatusOK {
		t.Fatalf("status = %d", pg.Code)
	}
	if !strings.Contains(pg.Body, "goroutine profile") {
		t.Error("the goroutine profile does not look like pprof output")
	}

	// Only the listed profiles are reachable; nothing may fall through to
	// net/http/pprof's own mux (cmdline leaks the command line).
	if pg := p.get("/admin/monitoring/pprof/cmdline"); pg.Code != http.StatusNotFound {
		t.Errorf("cmdline: status = %d, want 404", pg.Code)
	}
}

// ---------------------------------------------------------------------------
// Admin actions
// ---------------------------------------------------------------------------

func TestAdminCreatesAccount(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")

	form := p.get("/admin/accounts/new")
	pg := p.post("/admin/accounts/new", url.Values{
		"csrf_token": {p.csrfFrom(form)},
		"login":      {"created"}, "password": {"password1"},
	})
	if pg.Code != http.StatusOK {
		t.Fatalf("status = %d", pg.Code)
	}
	acc, err := p.st.Accounts.FindByName("created")
	if err != nil {
		t.Fatalf("account was not created: %v", err)
	}
	if acc.IsAdmin {
		t.Error("the account was made an admin without the box being ticked")
	}
}

func TestAdminGrantsAndRevokesAdmin(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	target := p.seedPlayer("player", "Rushu")

	detail := p.get("/admin/accounts/" + itoa(target.ID))
	token := p.csrfFrom(detail)

	p.post("/admin/accounts/"+itoa(target.ID)+"/toggle-admin", url.Values{"csrf_token": {token}})
	acc, _ := p.st.Accounts.FindByID(target.ID)
	if !acc.IsAdmin {
		t.Fatal("granting admin did not take effect")
	}

	p.post("/admin/accounts/"+itoa(target.ID)+"/toggle-admin", url.Values{"csrf_token": {token}})
	acc, _ = p.st.Accounts.FindByID(target.ID)
	if acc.IsAdmin {
		t.Error("revoking admin did not take effect")
	}
}

// An admin must not be able to demote or delete themselves: on a one-admin
// server that locks the console for good.
func TestAdminCannotActOnSelf(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	owner, err := p.st.Accounts.FindByName("owner")
	if err != nil {
		t.Fatalf("FindByName: %v", err)
	}

	detail := p.get("/admin/accounts/" + itoa(owner.ID))
	if strings.Contains(detail.Body, "Revoke administrator") {
		t.Error("the console offers to revoke your own admin rights")
	}

	// Even posting directly must be refused.
	token := p.csrfFrom(p.get("/admin/accounts/new"))
	p.post("/admin/accounts/"+itoa(owner.ID)+"/toggle-admin", url.Values{"csrf_token": {token}})
	if acc, _ := p.st.Accounts.FindByID(owner.ID); !acc.IsAdmin {
		t.Error("an admin demoted themselves")
	}

	p.post("/admin/accounts/"+itoa(owner.ID)+"/delete", url.Values{"csrf_token": {token}})
	if _, err := p.st.Accounts.FindByID(owner.ID); err != nil {
		t.Error("an admin deleted the account they were signed in as")
	}
}

func TestAdminDeletesAccount(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	target := p.seedPlayer("doomed", "Doomed")

	token := p.csrfFrom(p.get("/admin/accounts/" + itoa(target.ID)))
	pg := p.post("/admin/accounts/"+itoa(target.ID)+"/delete", url.Values{"csrf_token": {token}})
	if pg.Code != http.StatusOK {
		t.Fatalf("status = %d", pg.Code)
	}
	if _, err := p.st.Accounts.FindByID(target.ID); err == nil {
		t.Error("the account was not deleted")
	}
}

// A connected player is mid-game; deleting them out from under the game server
// would leave it holding a coach whose account no longer exists.
func TestAdminCannotDeleteConnectedAccount(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	target := p.seedPlayer("busy", "Busy")
	if err := p.st.Accounts.SetConnected(target.ID, true); err != nil {
		t.Fatalf("SetConnected: %v", err)
	}

	token := p.csrfFrom(p.get("/admin/accounts/" + itoa(target.ID)))
	pg := p.post("/admin/accounts/"+itoa(target.ID)+"/delete", url.Values{"csrf_token": {token}})

	if _, err := p.st.Accounts.FindByID(target.ID); err != nil {
		t.Fatal("a connected account was deleted")
	}
	if !strings.Contains(pg.Body, "connected right now") {
		t.Error("no explanation was shown for the refused delete")
	}
}

func TestAdminActionsNeedCSRF(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	target := p.seedPlayer("player", "Rushu")

	for _, action := range []string{"delete", "toggle-admin", "impersonate"} {
		pg := p.post("/admin/accounts/"+itoa(target.ID)+"/"+action, url.Values{})
		if pg.Code != http.StatusForbidden {
			t.Errorf("%s without a CSRF token: status = %d, want 403", action, pg.Code)
		}
	}
	if _, err := p.st.Accounts.FindByID(target.ID); err != nil {
		t.Error("a CSRF-less request deleted the account")
	}
}

// ---------------------------------------------------------------------------
// Impersonation
// ---------------------------------------------------------------------------

func TestImpersonationSwitchesIdentityButNotPrivilege(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	target := p.seedPlayer("player", "Rushu")

	token := p.csrfFrom(p.get("/admin/accounts/" + itoa(target.ID)))
	pg := p.post("/admin/accounts/"+itoa(target.ID)+"/impersonate", url.Values{"csrf_token": {token}})

	if pg.URL != "/account" {
		t.Fatalf("impersonation landed on %s, want /account", pg.URL)
	}
	// The data shown is the target's...
	if !strings.Contains(pg.Body, "Rushu") {
		t.Error("impersonation did not switch to the target's data")
	}
	// ...and there is a permanent, obvious way back.
	if !strings.Contains(pg.Body, "Stop impersonating") {
		t.Error("no way to stop impersonating is shown")
	}
	if !strings.Contains(pg.Body, "viewing this site as") {
		t.Error("no impersonation banner is shown")
	}

	// The console is still reachable, because the REAL account is the admin.
	if pg := p.get("/admin"); pg.Code != http.StatusOK {
		t.Errorf("the admin lost console access while impersonating: %d", pg.Code)
	}

	// Stop, and we are ourselves again.
	stop := p.csrfFrom(p.get("/account"))
	p.post("/impersonate/stop", url.Values{"csrf_token": {stop}})
	if pg := p.get("/account"); !strings.Contains(pg.Body, "owner") {
		t.Error("stopping impersonation did not restore the real account")
	}
}

// Impersonation is a view, never an escalation: an ordinary player cannot
// start one, and the impersonated session must not be able to change the
// target's password.
func TestImpersonationCannotEscalate(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	victim := p.seedPlayer("victim", "Victim")
	p.seedPlayer("sneak", "Sneak")
	p.logout()

	// A non-admin cannot start impersonating anyone.
	p.login("sneak", "password1")
	pg := p.post("/admin/accounts/"+itoa(victim.ID)+"/impersonate", url.Values{})
	if pg.Code != http.StatusForbidden {
		t.Errorf("a non-admin started impersonation: status = %d", pg.Code)
	}
	p.logout()

	// An admin may, but the session is read-only.
	p.login("owner", "password1")
	token := p.csrfFrom(p.get("/admin/accounts/" + itoa(victim.ID)))
	p.post("/admin/accounts/"+itoa(victim.ID)+"/impersonate", url.Values{"csrf_token": {token}})

	pwPage := p.get("/account/password")
	if pwPage.Code != http.StatusForbidden {
		t.Errorf("the password page was editable while impersonating: %d", pwPage.Code)
	}
	post := p.post("/account/password", url.Values{
		"csrf_token": {token}, "current": {"password1"},
		"password": {"hijacked1"}, "confirm": {"hijacked1"},
	})
	if post.Code != http.StatusForbidden {
		t.Errorf("a password change while impersonating: status = %d, want 403", post.Code)
	}
	acc, _ := p.st.Accounts.FindByID(victim.ID)
	if p.st.Accounts.VerifyPassword(acc, "hijacked1") {
		t.Fatal("an admin changed a player's password by impersonating them")
	}
}

// A session whose account has been deleted must be signed out cleanly rather
// than leaving handlers working with a ghost.
func TestSessionForDeletedAccountIsDropped(t *testing.T) {
	p := newPortal(t)
	p.register("owner", "password1")
	acc := p.seedPlayer("ghost", "Ghost")
	p.logout()

	p.login("ghost", "password1")
	if err := p.st.Accounts.DeleteAccount(acc.ID); err != nil {
		t.Fatalf("DeleteAccount: %v", err)
	}
	if pg := p.get("/account"); pg.URL != "/login" {
		t.Errorf("a session pointing at a deleted account landed on %s, want /login", pg.URL)
	}
}

func itoa(u uint) string {
	return strconv.FormatUint(uint64(u), 10)
}
