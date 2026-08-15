package web

import (
	"context"
	"io"
	"log/slog"
	"net"
	"net/http"
	"net/http/httptest"
	"net/url"
	"path/filepath"
	"strconv"
	"strings"
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/config"
	"github.com/StarLoco/arena-2.70/internal/store"
)

func newTestServer(t *testing.T, tweak func(*config.WebConfig)) (*Server, *store.Store) {
	t.Helper()
	st, err := store.Open(filepath.Join(t.TempDir(), "test.db"))
	if err != nil {
		t.Fatalf("store.Open: %v", err)
	}
	t.Cleanup(func() { _ = st.Close() })

	cfg := config.Default().Web
	// A fixed secret so sessions minted in a test stay valid across a
	// hand-rolled second Server in the same test.
	cfg.SessionSecret = "test-secret-not-used-anywhere-real"
	if tweak != nil {
		tweak(&cfg)
	}
	quiet := slog.New(slog.NewTextHandler(io.Discard, nil))

	s, err := New(st, cfg, "0.0.0.0:5555", Live{
		PlayersOnline: func() int { return 3 },
		ActiveFights:  func() int { return 2 },
	}, quiet)
	if err != nil {
		t.Fatalf("New: %v", err)
	}
	return s, st
}

// get fetches a page with no session.
func get(t *testing.T, s *Server, path string) *httptest.ResponseRecorder {
	t.Helper()
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, httptest.NewRequest(http.MethodGet, path, nil))
	return rec
}

// postRegister submits the registration form the way the portal's page does.
func postRegister(t *testing.T, s *Server, login, password string, mutate func(*http.Request)) *httptest.ResponseRecorder {
	t.Helper()
	form := url.Values{"login": {login}, "password": {password}}
	req := httptest.NewRequest(http.MethodPost, "/register", strings.NewReader(form.Encode()))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
	req.RemoteAddr = "192.0.2.10:1234"
	if mutate != nil {
		mutate(req)
	}
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, req)
	return rec
}

// ---------------------------------------------------------------------------
// Public pages
// ---------------------------------------------------------------------------

func TestPublicPagesRender(t *testing.T) {
	s, _ := newTestServer(t, nil)

	for _, tc := range []struct {
		path string
		want []string
	}{
		{"/", []string{"DofusArena", "Create your account", "Registered players"}},
		{"/status", []string{"Server status", "Players online", "Fights in progress"}},
		{"/ladder", []string{"Leaderboard"}},
		{"/login", []string{"Sign in", "Account name"}},
		{"/register", []string{"Create an account", "Repeat the password"}},
	} {
		t.Run(tc.path, func(t *testing.T) {
			rec := get(t, s, tc.path)
			if rec.Code != http.StatusOK {
				t.Fatalf("status = %d", rec.Code)
			}
			body := rec.Body.String()
			for _, want := range tc.want {
				if !strings.Contains(body, want) {
					t.Errorf("page %s is missing %q", tc.path, want)
				}
			}
			if got := rec.Header().Get("Content-Security-Policy"); got == "" {
				t.Error("CSP header missing")
			}
		})
	}
}

// The status page is public, so it must never leak who is playing — only
// aggregate counts.
func TestStatusShowsLiveCountersAndNoNames(t *testing.T) {
	s, st := newTestServer(t, nil)
	acc, _ := st.Accounts.CreateAccount("SecretPlayer", "password1", false)
	coach, _ := st.Coaches.Create(acc.ID, "SecretCoach", 1, 1, 0)
	_ = st.Accounts.LinkCoach(acc.ID, coach.ID)
	_ = st.Accounts.SetConnected(acc.ID, true)

	body := get(t, s, "/status").Body.String()
	if !strings.Contains(body, ">3<") {
		t.Error("players-online counter (3) not rendered")
	}
	if !strings.Contains(body, ">2<") {
		t.Error("active-fights counter (2) not rendered")
	}
	for _, secret := range []string{"SecretPlayer", "SecretCoach"} {
		if strings.Contains(body, secret) {
			t.Errorf("the public status page leaked %q", secret)
		}
	}
}

// The landing page shows the address a player must type into their client.
func TestIndexShowsGameAddress(t *testing.T) {
	s, _ := newTestServer(t, nil)
	req := httptest.NewRequest(http.MethodGet, "/", nil)
	req.Host = "arena.example:8080"
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, req)

	if !strings.Contains(rec.Body.String(), "arena.example:5555") {
		t.Error("landing page does not show the game address to connect to")
	}
}

func TestLadderListsRankedCoaches(t *testing.T) {
	s, st := newTestServer(t, nil)

	acc, _ := st.Accounts.CreateAccount("laddered", "password1", false)
	coach, _ := st.Coaches.Create(acc.ID, "Champion", 1, 1, 0)
	coach.Strength = 1500
	coach.StatWins = 7
	if err := st.Coaches.Save(coach); err != nil {
		t.Fatalf("Save: %v", err)
	}

	body := get(t, s, "/ladder").Body.String()
	if !strings.Contains(body, "Champion") {
		t.Error("a ranked coach is missing from the leaderboard")
	}
	if !strings.Contains(body, "1500") {
		t.Error("the coach's rating is not shown")
	}
}

func TestClientDownloadLinkShownWhenConfigured(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) { c.ClientDownloadURL = "https://example.com/client.zip" })
	if !strings.Contains(get(t, s, "/").Body.String(), "https://example.com/client.zip") {
		t.Error("configured client download URL not shown on the page")
	}
}

func TestClientDownloadLinkHiddenWhenBlank(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) { c.ClientDownloadURL = "" })
	if strings.Contains(get(t, s, "/").Body.String(), "Download the game") {
		t.Error("client download button shown despite a blank URL")
	}
}

func TestServerNameBrandsThePage(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) { c.ServerName = "Hormonde Reborn" })
	if !strings.Contains(get(t, s, "/").Body.String(), "Hormonde Reborn") {
		t.Error("the configured server name is not shown")
	}
}

func TestStaticAssetsAreServed(t *testing.T) {
	s, _ := newTestServer(t, nil)
	for _, tc := range []struct{ path, contains string }{
		{"/static/app.css", "--accent"},
		{"/static/favicon.svg", "<svg"},
	} {
		rec := get(t, s, tc.path)
		if rec.Code != http.StatusOK {
			t.Errorf("%s: status = %d", tc.path, rec.Code)
			continue
		}
		if !strings.Contains(rec.Body.String(), tc.contains) {
			t.Errorf("%s does not look like the right asset", tc.path)
		}
	}

	// The fonts must actually be embedded, or every page falls back to a
	// system font and the design silently degrades.
	for _, f := range []string{"/static/fonts/sora-latin.woff2", "/static/fonts/outfit-latin.woff2"} {
		rec := get(t, s, f)
		if rec.Code != http.StatusOK {
			t.Errorf("%s: status = %d — font not embedded", f, rec.Code)
			continue
		}
		if got := rec.Body.String(); len(got) < 4 || got[:4] != "wOF2" {
			t.Errorf("%s is not a valid woff2 file", f)
		}
	}
}

// ---------------------------------------------------------------------------
// Registration
// ---------------------------------------------------------------------------

func TestRegisterCreatesUsableAccountAndSignsIn(t *testing.T) {
	s, st := newTestServer(t, nil)

	rec := postRegister(t, s, "Newbie", "hunter2xyz", nil)
	if rec.Code != http.StatusSeeOther {
		t.Fatalf("status = %d, want 303; body: %s", rec.Code, rec.Body)
	}
	if got := rec.Header().Get("Location"); got != "/account" {
		t.Errorf("redirected to %q, want /account", got)
	}
	// Registering signs you straight in.
	if !strings.Contains(rec.Header().Get("Set-Cookie"), sessionCookieName) {
		t.Error("registration did not establish a session")
	}

	acc, err := st.Accounts.FindByName("Newbie")
	if err != nil {
		t.Fatalf("account was not persisted: %v", err)
	}
	// The password must be usable by the game login path, and stored hashed.
	if !st.Accounts.VerifyPassword(acc, "hunter2xyz") {
		t.Error("password does not verify — the game client could not log in")
	}
	if acc.PasswordHash == "hunter2xyz" {
		t.Error("password stored in clear")
	}
}

// A release binary ships without the seeding CLI, so the very first account
// registered has to become the owner or nobody could ever run a GM command.
func TestFirstAccountBecomesOwnerAndNoOtherDoes(t *testing.T) {
	s, st := newTestServer(t, nil)

	if rec := postRegister(t, s, "Owner", "password1", nil); rec.Code != http.StatusSeeOther {
		t.Fatalf("first registration failed: %d", rec.Code)
	}
	owner, err := st.Accounts.FindByName("Owner")
	if err != nil {
		t.Fatalf("owner not found: %v", err)
	}
	if !owner.IsAdmin {
		t.Error("the first account must be an administrator")
	}

	if rec := postRegister(t, s, "Player", "password1", nil); rec.Code != http.StatusSeeOther {
		t.Fatalf("second registration failed: %d", rec.Code)
	}
	player, err := st.Accounts.FindByName("Player")
	if err != nil {
		t.Fatalf("player not found: %v", err)
	}
	if player.IsAdmin {
		t.Error("only the first account may be an administrator")
	}
}

// The offer must only be advertised while it is actually true.
func TestOwnerHintDisappearsAfterFirstAccount(t *testing.T) {
	s, _ := newTestServer(t, nil)

	if !strings.Contains(get(t, s, "/register").Body.String(), "no accounts yet") {
		t.Error("a fresh server should offer ownership to the first registrant")
	}
	if r := postRegister(t, s, "Owner", "password1", nil); r.Code != http.StatusSeeOther {
		t.Fatalf("registration failed: %d", r.Code)
	}
	if strings.Contains(get(t, s, "/register").Body.String(), "no accounts yet") {
		t.Error("ownership offer still shown after an account exists")
	}
}

func TestDuplicateNameRejected(t *testing.T) {
	s, _ := newTestServer(t, nil)

	if rec := postRegister(t, s, "Twin", "password1", nil); rec.Code != http.StatusSeeOther {
		t.Fatalf("first registration failed: %d", rec.Code)
	}
	rec := postRegister(t, s, "Twin", "password2", nil)
	if rec.Code != http.StatusConflict {
		t.Errorf("status = %d, want 409", rec.Code)
	}
	if !strings.Contains(rec.Body.String(), "already taken") {
		t.Error("no duplicate-name message")
	}
}

func TestValidationRejectsBadInput(t *testing.T) {
	tests := []struct {
		name, login, password, want string
	}{
		{"short login", "ab", "password1", "at least 3"},
		{"long login", strings.Repeat("a", 25), "password1", "at most 24"},
		{"bad chars", "hé;llo", "password1", "letters, digits"},
		{"spaces", "two words", "password1", "letters, digits"},
		{"short password", "player", "abc", "at least 6"},
		// bcrypt refuses anything past 72 bytes; catch it with a readable message.
		{"long password", "player", strings.Repeat("x", 73), "at most 72"},
		{"empty login", "", "password1", "choose an account name"},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			s, st := newTestServer(t, nil)
			rec := postRegister(t, s, tt.login, tt.password, nil)
			if rec.Code != http.StatusBadRequest {
				t.Fatalf("status = %d, want 400", rec.Code)
			}
			if !strings.Contains(rec.Body.String(), tt.want) {
				t.Errorf("message %q not found in page", tt.want)
			}
			if _, err := st.Accounts.FindByName(tt.login); err == nil {
				t.Error("an invalid registration created an account")
			}
		})
	}
}

func TestMismatchedConfirmationRejected(t *testing.T) {
	s, st := newTestServer(t, nil)
	form := url.Values{"login": {"Careful"}, "password": {"password1"}, "confirm": {"password2"}}
	req := httptest.NewRequest(http.MethodPost, "/register", strings.NewReader(form.Encode()))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, req)

	if rec.Code != http.StatusBadRequest {
		t.Errorf("status = %d, want 400", rec.Code)
	}
	if !strings.Contains(rec.Body.String(), "do not match") {
		t.Error("no mismatch message")
	}
	if _, err := st.Accounts.FindByName("Careful"); err == nil {
		t.Error("account created despite mismatched confirmation")
	}
}

func TestRegistrationCanBeClosed(t *testing.T) {
	s, st := newTestServer(t, func(c *config.WebConfig) { c.RegistrationEnabled = false })

	rec := postRegister(t, s, "Latecomer", "password1", nil)
	if rec.Code != http.StatusForbidden {
		t.Errorf("status = %d, want 403", rec.Code)
	}
	if _, err := st.Accounts.FindByName("Latecomer"); err == nil {
		t.Error("account created while registration was closed")
	}
	if strings.Contains(get(t, s, "/register").Body.String(), `name="password"`) {
		t.Error("registration form still rendered while closed")
	}
}

// A third-party page must not be able to drive a visitor's browser into
// creating accounts.
func TestCrossOriginPostRejected(t *testing.T) {
	s, st := newTestServer(t, nil)

	rec := postRegister(t, s, "Victim", "password1", func(r *http.Request) {
		r.Host = "arena.example"
		r.Header.Set("Origin", "https://evil.example")
	})
	if rec.Code != http.StatusForbidden {
		t.Errorf("status = %d, want 403", rec.Code)
	}
	if _, err := st.Accounts.FindByName("Victim"); err == nil {
		t.Error("cross-origin request created an account")
	}
}

func TestSameOriginPostAccepted(t *testing.T) {
	s, _ := newTestServer(t, nil)
	rec := postRegister(t, s, "Local", "password1", func(r *http.Request) {
		r.Host = "arena.example"
		r.Header.Set("Origin", "http://arena.example")
	})
	if rec.Code != http.StatusSeeOther {
		t.Errorf("status = %d, want 303", rec.Code)
	}
}

// ---------------------------------------------------------------------------
// Rate limiting
// ---------------------------------------------------------------------------

func TestRateLimitStopsFloods(t *testing.T) {
	s, _ := newTestServer(t, nil)

	// The limiter allows 10 per hour per address.
	for i := 0; i < 10; i++ {
		rec := postRegister(t, s, "flood"+string(rune('a'+i)), "password1", nil)
		if rec.Code != http.StatusSeeOther {
			t.Fatalf("registration %d rejected early: %d", i, rec.Code)
		}
	}
	rec := postRegister(t, s, "floodlast", "password1", nil)
	if rec.Code != http.StatusTooManyRequests {
		t.Errorf("status = %d, want 429", rec.Code)
	}

	// A different address is unaffected.
	other := postRegister(t, s, "elsewhere", "password1", func(r *http.Request) {
		r.RemoteAddr = "198.51.100.7:5555"
	})
	if other.Code != http.StatusSeeOther {
		t.Errorf("unrelated address was limited: %d", other.Code)
	}
}

// A rejected registration must not consume the caller's allowance, or a player
// mistyping their password would lock themselves out.
func TestInvalidInputDoesNotConsumeAllowance(t *testing.T) {
	s, _ := newTestServer(t, nil)
	for i := 0; i < 30; i++ {
		postRegister(t, s, "x", "short", nil) // invalid every time
	}
	if rec := postRegister(t, s, "goodname", "password1", nil); rec.Code != http.StatusSeeOther {
		t.Errorf("valid registration blocked after failed attempts: %d", rec.Code)
	}
}

// Password guessing must run out of road too.
func TestLoginAttemptsAreRateLimited(t *testing.T) {
	s, st := newTestServer(t, nil)
	if _, err := st.Accounts.CreateAccount("target", "correct-horse", false); err != nil {
		t.Fatalf("CreateAccount: %v", err)
	}

	attempt := func() int {
		form := url.Values{"login": {"target"}, "password": {"wrong"}}
		req := httptest.NewRequest(http.MethodPost, "/login", strings.NewReader(form.Encode()))
		req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
		req.RemoteAddr = "203.0.113.9:4321"
		rec := httptest.NewRecorder()
		s.Handler().ServeHTTP(rec, req)
		return rec.Code
	}
	for i := 0; i < 20; i++ {
		if code := attempt(); code != http.StatusUnauthorized {
			t.Fatalf("attempt %d: status = %d, want 401", i, code)
		}
	}
	if code := attempt(); code != http.StatusTooManyRequests {
		t.Errorf("21st attempt: status = %d, want 429", code)
	}
}

func TestLimiterWindowExpires(t *testing.T) {
	l := newLimiter(2, time.Hour)
	now := time.Now()
	l.now = func() time.Time { return now }

	if !l.allow("a") || !l.allow("a") {
		t.Fatal("first two should be allowed")
	}
	if l.allow("a") {
		t.Fatal("third should be blocked")
	}
	now = now.Add(2 * time.Hour) // window rolls over
	if !l.allow("a") {
		t.Error("allowance should be restored after the window")
	}
}

// ---------------------------------------------------------------------------
// Misc
// ---------------------------------------------------------------------------

func TestHealthEndpoint(t *testing.T) {
	s, _ := newTestServer(t, nil)
	rec := get(t, s, "/health")
	if rec.Code != http.StatusOK || rec.Body.String() != "ok" {
		t.Errorf("health = %d %q", rec.Code, rec.Body.String())
	}
}

func TestUnknownPathIs404(t *testing.T) {
	s, _ := newTestServer(t, nil)
	if rec := get(t, s, "/nope"); rec.Code != http.StatusNotFound {
		t.Errorf("status = %d, want 404", rec.Code)
	}
}

func TestGameAddressPrefersPublicHost(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) { c.PublicHost = "arena.example.com" })
	req := httptest.NewRequest(http.MethodGet, "/", nil)
	req.Host = "10.0.0.5:8080"
	if got := s.gameAddress(req); got != "arena.example.com:5555" {
		t.Errorf("gameAddress = %q", got)
	}
}

func TestGameAddressFallsBackToRequestHost(t *testing.T) {
	s, _ := newTestServer(t, nil) // game addr is the 0.0.0.0 wildcard
	req := httptest.NewRequest(http.MethodGet, "/", nil)
	req.Host = "192.168.1.20:8080"
	if got := s.gameAddress(req); got != "192.168.1.20:5555" {
		t.Errorf("gameAddress = %q", got)
	}
}

// Every page must render, so a template typo cannot ship. parseTemplates is
// what New calls, and a bad {{template}} reference only fails at execute time,
// which is why this walks the whole set.
func TestEveryTemplateParses(t *testing.T) {
	set, err := parseTemplates()
	if err != nil {
		t.Fatalf("parseTemplates: %v", err)
	}
	want := []string{
		"index.html", "status.html", "ladder.html", "login.html", "register.html",
		"account.html", "password.html", "error.html",
		"admin_dashboard.html", "admin_accounts.html", "admin_detail.html",
		"admin_create.html", "admin_monitoring.html",
		"admin_tournaments.html", "admin_tournament_form.html",
	}
	for _, name := range want {
		if _, ok := set.pages[name]; !ok {
			t.Errorf("template %q was not parsed", name)
		}
	}
	if len(set.pages) != len(want) {
		t.Errorf("parsed %d templates, expected %d — a new page needs adding here",
			len(set.pages), len(want))
	}
}

// ---------------------------------------------------------------------------
// Listener selection
// ---------------------------------------------------------------------------

func TestListenHonoursExplicitPort(t *testing.T) {
	// Grab a free port, release it, then ask for it by name.
	probe, err := net.Listen("tcp", "127.0.0.1:0")
	if err != nil {
		t.Fatalf("probe: %v", err)
	}
	want := Port(probe)
	_ = probe.Close()

	ln, err := Listen("127.0.0.1:" + strconv.Itoa(want))
	if err != nil {
		t.Fatalf("Listen: %v", err)
	}
	defer func() { _ = ln.Close() }()
	if got := Port(ln); got != want {
		t.Errorf("bound port %d, want %d", got, want)
	}
}

// A port already in use must not stop the server from starting.
func TestListenFallsBackWhenPortBusy(t *testing.T) {
	busy, err := net.Listen("tcp", "127.0.0.1:0")
	if err != nil {
		t.Fatalf("busy: %v", err)
	}
	defer func() { _ = busy.Close() }()

	ln, err := Listen("127.0.0.1:" + strconv.Itoa(Port(busy)))
	if err != nil {
		t.Fatalf("Listen should fall back, got error: %v", err)
	}
	defer func() { _ = ln.Close() }()
	if Port(ln) == Port(busy) {
		t.Error("Listen returned the busy port")
	}
}

// Port 0 means "choose for me" and must always find something, even when the
// privileged ports on the ladder are unavailable (the usual case for a
// non-root process).
func TestListenAutoAlwaysSucceeds(t *testing.T) {
	ln, err := Listen("127.0.0.1:0")
	if err != nil {
		t.Fatalf("Listen: %v", err)
	}
	defer func() { _ = ln.Close() }()
	if Port(ln) == 0 {
		t.Error("no port was bound")
	}
}

func TestURLOmitsPort80(t *testing.T) {
	if got := urlForPort(80); got != "http://localhost" {
		t.Errorf("urlForPort(80) = %q", got)
	}
	if got := urlForPort(8080); got != "http://localhost:8080" {
		t.Errorf("urlForPort(8080) = %q", got)
	}
}

// URL must reflect the port actually bound, not the one requested.
func TestURLReflectsBoundListener(t *testing.T) {
	ln, err := Listen("127.0.0.1:0")
	if err != nil {
		t.Fatalf("Listen: %v", err)
	}
	defer func() { _ = ln.Close() }()
	if got, want := URL(ln), urlForPort(Port(ln)); got != want {
		t.Errorf("URL = %q, want %q", got, want)
	}
}

func TestServeShutsDownWithContext(t *testing.T) {
	s, _ := newTestServer(t, nil)
	ln, err := Listen("127.0.0.1:0")
	if err != nil {
		t.Fatalf("Listen: %v", err)
	}
	ctx, cancel := context.WithCancel(context.Background())

	errCh := make(chan error, 1)
	go func() { errCh <- s.Serve(ctx, ln) }()

	// The portal must actually answer before we tear it down.
	deadline := time.Now().Add(3 * time.Second)
	var served bool
	for time.Now().Before(deadline) {
		resp, err := http.Get("http://" + ln.Addr().String() + "/health")
		if err == nil {
			_ = resp.Body.Close()
			served = true
			break
		}
		time.Sleep(20 * time.Millisecond)
	}
	if !served {
		cancel()
		t.Fatal("portal never became reachable")
	}

	cancel()
	select {
	case err := <-errCh:
		if err != nil {
			t.Errorf("Serve returned %v, want nil on clean shutdown", err)
		}
	case <-time.After(10 * time.Second):
		t.Error("Serve did not stop after the context was cancelled")
	}
}
