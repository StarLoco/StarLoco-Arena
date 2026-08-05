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
	if tweak != nil {
		tweak(&cfg)
	}
	quiet := slog.New(slog.NewTextHandler(io.Discard, nil))
	return New(st, cfg, "0.0.0.0:5555", func() int { return 3 }, quiet), st
}

// post submits the registration form the way the portal's own page does.
func post(t *testing.T, s *Server, login, password string, mutate func(*http.Request)) *httptest.ResponseRecorder {
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

func TestIndexRenders(t *testing.T) {
	s, _ := newTestServer(t, nil)

	req := httptest.NewRequest(http.MethodGet, "/", nil)
	req.Host = "arena.example:8080"
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d", rec.Code)
	}
	body := rec.Body.String()
	for _, want := range []string{"Create an account", "proxyAddresses_1", "arena.example:5555"} {
		if !strings.Contains(body, want) {
			t.Errorf("page is missing %q", want)
		}
	}
	if got := rec.Header().Get("Content-Security-Policy"); got == "" {
		t.Error("CSP header missing")
	}
}

func TestRegisterCreatesUsableAccount(t *testing.T) {
	s, st := newTestServer(t, nil)

	rec := post(t, s, "Newbie", "hunter2xyz", nil)
	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, body: %s", rec.Code, rec.Body)
	}
	if !strings.Contains(rec.Body.String(), "created") {
		t.Error("no success message shown")
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
// Every subsequent account must be an ordinary player.
func TestFirstAccountBecomesOwnerAndNoOtherDoes(t *testing.T) {
	s, st := newTestServer(t, nil)

	if rec := post(t, s, "Owner", "password1", nil); rec.Code != http.StatusOK {
		t.Fatalf("first registration failed: %d", rec.Code)
	}
	owner, err := st.Accounts.FindByName("Owner")
	if err != nil {
		t.Fatalf("owner not found: %v", err)
	}
	if !owner.IsAdmin {
		t.Error("the first account must be an administrator")
	}

	if rec := post(t, s, "Player", "password1", nil); rec.Code != http.StatusOK {
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
	const hint = "becomes the\n        administrator"

	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/", nil))
	if !strings.Contains(rec.Body.String(), "administrator") {
		t.Error("a fresh server should offer ownership to the first registrant")
	}

	if r := post(t, s, "Owner", "password1", nil); r.Code != http.StatusOK {
		t.Fatalf("registration failed: %d", r.Code)
	}

	rec2 := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec2, httptest.NewRequest(http.MethodGet, "/", nil))
	if strings.Contains(rec2.Body.String(), "no accounts yet") {
		t.Error("ownership offer still shown after an account exists")
	}
	_ = hint
}

// The client-download link only appears when configured, and shows the
// configured URL verbatim — an operator pointing it at their own mirror must
// not see a stale or hardcoded one instead.
func TestClientDownloadLinkShownWhenConfigured(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) { c.ClientDownloadURL = "https://example.com/client.zip" })
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/", nil))
	if !strings.Contains(rec.Body.String(), "https://example.com/client.zip") {
		t.Error("configured client download URL not shown on the page")
	}
}

// A blank URL (e.g. a fork's operator with no mirror to offer) must hide the
// panel entirely rather than render a dead link.
func TestClientDownloadLinkHiddenWhenBlank(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) { c.ClientDownloadURL = "" })
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/", nil))
	if strings.Contains(rec.Body.String(), "Get the client") {
		t.Error("client download panel shown despite a blank URL")
	}
}

func TestDuplicateNameRejected(t *testing.T) {
	s, _ := newTestServer(t, nil)

	if rec := post(t, s, "Twin", "password1", nil); rec.Code != http.StatusOK {
		t.Fatalf("first registration failed: %d", rec.Code)
	}
	rec := post(t, s, "Twin", "password2", nil)
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
			rec := post(t, s, tt.login, tt.password, nil)
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

func TestRegistrationCanBeClosed(t *testing.T) {
	s, st := newTestServer(t, func(c *config.WebConfig) { c.RegistrationEnabled = false })

	rec := post(t, s, "Latecomer", "password1", nil)
	if rec.Code != http.StatusForbidden {
		t.Errorf("status = %d, want 403", rec.Code)
	}
	if _, err := st.Accounts.FindByName("Latecomer"); err == nil {
		t.Error("account created while registration was closed")
	}

	// The form must also disappear from the page.
	req := httptest.NewRequest(http.MethodGet, "/", nil)
	idx := httptest.NewRecorder()
	s.Handler().ServeHTTP(idx, req)
	if strings.Contains(idx.Body.String(), "<form") {
		t.Error("registration form still rendered while closed")
	}
}

// A third-party page must not be able to drive a visitor's browser into
// creating accounts.
func TestCrossOriginPostRejected(t *testing.T) {
	s, st := newTestServer(t, nil)

	rec := post(t, s, "Victim", "password1", func(r *http.Request) {
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
	rec := post(t, s, "Local", "password1", func(r *http.Request) {
		r.Host = "arena.example"
		r.Header.Set("Origin", "http://arena.example")
	})
	if rec.Code != http.StatusOK {
		t.Errorf("status = %d, want 200", rec.Code)
	}
}

func TestRateLimitStopsFloods(t *testing.T) {
	s, _ := newTestServer(t, nil)

	// The limiter allows 10 per hour per address.
	for i := 0; i < 10; i++ {
		rec := post(t, s, "flood"+string(rune('a'+i)), "password1", nil)
		if rec.Code != http.StatusOK {
			t.Fatalf("registration %d rejected early: %d", i, rec.Code)
		}
	}
	rec := post(t, s, "floodlast", "password1", nil)
	if rec.Code != http.StatusTooManyRequests {
		t.Errorf("status = %d, want 429", rec.Code)
	}

	// A different address is unaffected.
	other := post(t, s, "elsewhere", "password1", func(r *http.Request) {
		r.RemoteAddr = "198.51.100.7:5555"
	})
	if other.Code != http.StatusOK {
		t.Errorf("unrelated address was limited: %d", other.Code)
	}
}

// A rejected registration must not consume the caller's allowance, or a player
// mistyping their password would lock themselves out.
func TestInvalidInputDoesNotConsumeAllowance(t *testing.T) {
	s, _ := newTestServer(t, nil)
	for i := 0; i < 30; i++ {
		post(t, s, "x", "short", nil) // invalid every time
	}
	if rec := post(t, s, "goodname", "password1", nil); rec.Code != http.StatusOK {
		t.Errorf("valid registration blocked after failed attempts: %d", rec.Code)
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

func TestHealthEndpoint(t *testing.T) {
	s, _ := newTestServer(t, nil)
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/health", nil))
	if rec.Code != http.StatusOK || rec.Body.String() != "ok" {
		t.Errorf("health = %d %q", rec.Code, rec.Body.String())
	}
}

func TestUnknownPathIs404(t *testing.T) {
	s, _ := newTestServer(t, nil)
	rec := httptest.NewRecorder()
	s.Handler().ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/admin", nil))
	if rec.Code != http.StatusNotFound {
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
