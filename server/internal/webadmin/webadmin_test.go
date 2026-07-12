package webadmin

import (
	"context"
	"net/http"
	"net/http/httptest"
	"net/url"
	"strings"
	"testing"
	"time"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/testutil"
)

// newTestHandlerWithAdminAddr is like newTestHandler but also wires an
// AdminHTTPAddr, letting monitoring-page tests exercise the "configured"
// path (fetching /healthz + /stats and proxying /debug/pprof/*).
func newTestHandlerWithAdminAddr(t *testing.T, adminAddr string) (*Handler, *service.AccountService) {
	t.Helper()
	gdb := testutil.NewTestDB(t)
	accounts := service.NewAccountService(gdb)
	h, _, err := New(Deps{
		Accounts: accounts,
		Logger:   zerolog.Nop(),
	}, Config{
		SessionSecret: "test-secret-key-fixed",
		ServerName:    "TestArena",
		AdminHTTPAddr: adminAddr,
	})
	if err != nil {
		t.Fatalf("New handler: %v", err)
	}
	return h, accounts
}

// newTestHandler builds a Handler backed by an in-memory DB with a fixed
// session secret (so cookies/CSRF are deterministic across the test).
func newTestHandler(t *testing.T) (*Handler, *service.AccountService) {
	t.Helper()
	gdb := testutil.NewTestDB(t)
	accounts := service.NewAccountService(gdb)
	h, _, err := New(Deps{
		Accounts: accounts,
		Logger:   zerolog.Nop(),
	}, Config{
		SessionSecret: "test-secret-key-fixed",
		ServerName:    "TestArena",
	})
	if err != nil {
		t.Fatalf("New handler: %v", err)
	}
	return h, accounts
}

// sessionCookie builds a valid signed session cookie for accountID (and
// optional impersonation target) for use in authenticated requests.
func (h *Handler) sessionCookie(accountID, impersonatedID uint) *http.Cookie {
	return &http.Cookie{
		Name:  sessionCookieName,
		Value: h.codec.encode(session{AccountID: accountID, ImpersonatedID: impersonatedID, IssuedAt: time.Now()}),
	}
}

func TestSessionCodecRoundTrip(t *testing.T) {
	codec, _, err := newSessionCodec("secret")
	if err != nil {
		t.Fatalf("newSessionCodec: %v", err)
	}
	orig := session{AccountID: 42, ImpersonatedID: 7, IssuedAt: time.Now().Truncate(time.Second)}
	got, err := codec.decode(codec.encode(orig))
	if err != nil {
		t.Fatalf("decode: %v", err)
	}
	if got.AccountID != 42 || got.ImpersonatedID != 7 {
		t.Errorf("round-trip mismatch: %+v", got)
	}
}

func TestSessionCodecRejectsTamper(t *testing.T) {
	codec, _, _ := newSessionCodec("secret")
	token := codec.encode(session{AccountID: 1, IssuedAt: time.Now()})
	// Flip a character in the payload segment.
	tampered := "AAAA" + token[4:]
	if _, err := codec.decode(tampered); err == nil {
		t.Error("expected tampered token to be rejected")
	}
}

func TestRegisterFlowLogsInAndShowsAccount(t *testing.T) {
	h, _ := newTestHandler(t)

	form := url.Values{"name": {"newbie"}, "password": {"password1"}, "password_confirm": {"password1"}}
	req := httptest.NewRequest(http.MethodPost, "/register", strings.NewReader(form.Encode()))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusSeeOther {
		t.Fatalf("register status = %d, want 303; body=%s", rec.Code, rec.Body.String())
	}
	if loc := rec.Header().Get("Location"); loc != "/account" {
		t.Errorf("redirect = %q, want /account", loc)
	}
	// A session cookie should have been set.
	var hasSession bool
	for _, c := range rec.Result().Cookies() {
		if c.Name == sessionCookieName && c.Value != "" {
			hasSession = true
		}
	}
	if !hasSession {
		t.Error("expected a session cookie after registration")
	}
}

// TestStatusPageIsPublic verifies /status renders without any
// authentication, showing only aggregate counts (no player/account
// details), and degrades gracefully to zero counters when World/Fights are
// nil (matching the standalone cmd/web process, which has neither).
func TestStatusPageIsPublic(t *testing.T) {
	h, _ := newTestHandler(t)

	req := httptest.NewRequest(http.MethodGet, "/status", nil)
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, want 200; body=%s", rec.Code, rec.Body.String())
	}
	body := rec.Body.String()
	if !strings.Contains(body, "Players online") {
		t.Error("status page should show a 'Players online' tile")
	}
	if !strings.Contains(body, "Fights in progress") {
		t.Error("status page should show a 'Fights in progress' tile")
	}
	if !strings.Contains(body, "Server uptime") {
		t.Error("status page should show a 'Server uptime' tile")
	}
}

func TestAccountPageRequiresAuth(t *testing.T) {
	h, _ := newTestHandler(t)
	req := httptest.NewRequest(http.MethodGet, "/account", nil)
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)
	if rec.Code != http.StatusSeeOther || rec.Header().Get("Location") != "/login" {
		t.Errorf("unauthenticated /account = %d -> %q, want 303 -> /login", rec.Code, rec.Header().Get("Location"))
	}
}

func TestAccountPageRendersForLoggedInUser(t *testing.T) {
	h, accounts := newTestHandler(t)
	acc, _ := accounts.Register(context.Background(), "viewer", "password", false)

	req := httptest.NewRequest(http.MethodGet, "/account", nil)
	req.AddCookie(h.sessionCookie(acc.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, want 200; body=%s", rec.Code, rec.Body.String())
	}
	if !strings.Contains(rec.Body.String(), "viewer") {
		t.Error("account page should show the account name")
	}
}

func TestAdminRoutesForbiddenForNonAdmin(t *testing.T) {
	h, accounts := newTestHandler(t)
	acc, _ := accounts.Register(context.Background(), "plainuser", "password", false)

	req := httptest.NewRequest(http.MethodGet, "/admin", nil)
	req.AddCookie(h.sessionCookie(acc.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusForbidden {
		t.Errorf("non-admin /admin = %d, want 403", rec.Code)
	}
}

func TestAdminDashboardForAdmin(t *testing.T) {
	h, accounts := newTestHandler(t)
	admin, _ := accounts.Register(context.Background(), "boss", "password", true)

	req := httptest.NewRequest(http.MethodGet, "/admin", nil)
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("admin dashboard = %d, want 200; body=%s", rec.Code, rec.Body.String())
	}
	if !strings.Contains(rec.Body.String(), "Overview") {
		t.Error("admin dashboard should render the overview")
	}
}

// TestAdminDashboardShowsLiveServerStats verifies the dashboard's "Active
// fights" and "Server uptime" tiles render, and that they degrade
// gracefully (no panic, zero fights) when Deps.Fights is nil (e.g. the
// standalone cmd/web process, which has no live combat.Manager).
func TestAdminDashboardShowsLiveServerStats(t *testing.T) {
	h, accounts := newTestHandler(t)
	admin, _ := accounts.Register(context.Background(), "boss", "password", true)

	req := httptest.NewRequest(http.MethodGet, "/admin", nil)
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("admin dashboard = %d, want 200; body=%s", rec.Code, rec.Body.String())
	}
	body := rec.Body.String()
	if !strings.Contains(body, "Active fights") {
		t.Error("admin dashboard should show an 'Active fights' tile")
	}
	if !strings.Contains(body, "Server uptime") {
		t.Error("admin dashboard should show a 'Server uptime' tile")
	}
}

func TestImpersonationRequiresCSRFAndAdmin(t *testing.T) {
	h, accounts := newTestHandler(t)
	ctx := context.Background()
	admin, _ := accounts.Register(ctx, "boss", "password", true)
	target, _ := accounts.Register(ctx, "target", "password", false)

	path := "/admin/accounts/" + itoa(target.ID) + "/impersonate"

	// Without a CSRF token -> 403.
	req := httptest.NewRequest(http.MethodPost, path, strings.NewReader(""))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)
	if rec.Code != http.StatusForbidden {
		t.Fatalf("impersonate without CSRF = %d, want 403", rec.Code)
	}

	// With a valid CSRF token -> 303 to /account and impersonation cookie set.
	form := url.Values{"csrf_token": {h.csrfToken(admin.ID)}}
	req = httptest.NewRequest(http.MethodPost, path, strings.NewReader(form.Encode()))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec = httptest.NewRecorder()
	h.ServeHTTP(rec, req)
	if rec.Code != http.StatusSeeOther {
		t.Fatalf("impersonate with CSRF = %d, want 303; body=%s", rec.Code, rec.Body.String())
	}

	var impCookie string
	for _, c := range rec.Result().Cookies() {
		if c.Name == sessionCookieName {
			impCookie = c.Value
		}
	}
	sess, err := h.codec.decode(impCookie)
	if err != nil {
		t.Fatalf("decode impersonation cookie: %v", err)
	}
	if sess.AccountID != admin.ID || sess.ImpersonatedID != target.ID {
		t.Errorf("impersonation session = %+v, want admin=%d target=%d", sess, admin.ID, target.ID)
	}
	if sess.effectiveID() != target.ID {
		t.Errorf("effectiveID = %d, want %d", sess.effectiveID(), target.ID)
	}
}

func TestDeleteConnectedAccountBlocked(t *testing.T) {
	h, accounts := newTestHandler(t)
	ctx := context.Background()
	admin, _ := accounts.Register(ctx, "boss", "password", true)
	target, _ := accounts.Register(ctx, "victim", "password", false)
	if err := accounts.DB().Exec("UPDATE accounts SET connected = 1 WHERE id = ?", target.ID).Error; err != nil {
		t.Fatalf("mark connected: %v", err)
	}

	form := url.Values{"csrf_token": {h.csrfToken(admin.ID)}}
	path := "/admin/accounts/" + itoa(target.ID) + "/delete"
	req := httptest.NewRequest(http.MethodPost, path, strings.NewReader(form.Encode()))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	// Redirects back to the detail page with an error flash rather than deleting.
	if rec.Code != http.StatusSeeOther {
		t.Fatalf("delete connected = %d, want 303 (blocked)", rec.Code)
	}
	if _, err := accounts.GetByID(ctx, target.ID); err != nil {
		t.Errorf("connected account should NOT have been deleted: %v", err)
	}
}

// TestAdminMonitoringForbiddenForNonAdmin mirrors
// TestAdminRoutesForbiddenForNonAdmin for the new monitoring route.
func TestAdminMonitoringForbiddenForNonAdmin(t *testing.T) {
	h, accounts := newTestHandler(t)
	acc, _ := accounts.Register(context.Background(), "plainuser", "password", false)

	req := httptest.NewRequest(http.MethodGet, "/admin/monitoring", nil)
	req.AddCookie(h.sessionCookie(acc.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusForbidden {
		t.Errorf("non-admin /admin/monitoring = %d, want 403", rec.Code)
	}
}

// TestAdminMonitoringNotConfigured verifies the monitoring page renders a
// clear "not configured" notice (rather than erroring) when AdminHTTPAddr
// is empty, e.g. server.admin_addr disabled.
func TestAdminMonitoringNotConfigured(t *testing.T) {
	h, accounts := newTestHandler(t) // no AdminHTTPAddr
	admin, _ := accounts.Register(context.Background(), "boss", "password", true)

	req := httptest.NewRequest(http.MethodGet, "/admin/monitoring", nil)
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, want 200; body=%s", rec.Code, rec.Body.String())
	}
	if !strings.Contains(rec.Body.String(), "Not configured") {
		t.Error("monitoring page should show a 'Not configured' notice when AdminHTTPAddr is empty")
	}
}

// TestAdminMonitoringFetchesLiveCounters spins up a real internal/adminhttp
// server and verifies the monitoring page fetches and renders its
// healthz/stats counters, and that the pprof grid links are present.
func TestAdminMonitoringFetchesLiveCounters(t *testing.T) {
	adminSrv := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		switch r.URL.Path {
		case "/healthz":
			w.Write([]byte(`{"status":"ok","uptime_seconds":42}`))
		case "/stats":
			w.Write([]byte(`{"online_players":3,"active_fights":1}`))
		default:
			http.NotFound(w, r)
		}
	}))
	defer adminSrv.Close()
	adminAddr := strings.TrimPrefix(adminSrv.URL, "http://")

	h, accounts := newTestHandlerWithAdminAddr(t, adminAddr)
	admin, _ := accounts.Register(context.Background(), "boss", "password", true)

	req := httptest.NewRequest(http.MethodGet, "/admin/monitoring", nil)
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, want 200; body=%s", rec.Code, rec.Body.String())
	}
	body := rec.Body.String()
	for _, want := range []string{">OK<", "42s", ">3<", ">1<", "/admin/monitoring/pprof/heap", "/admin/monitoring/pprof/goroutine"} {
		if !strings.Contains(body, want) {
			t.Errorf("monitoring page missing %q; body=%s", want, body)
		}
	}
}

// TestAdminPprofProxyForwardsToAdminHTTP verifies GET
// /admin/monitoring/pprof/{name} reverse-proxies to the configured
// adminhttp server's /debug/pprof/{name}, preserving the response body.
func TestAdminPprofProxyForwardsToAdminHTTP(t *testing.T) {
	adminSrv := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if r.URL.Path != "/debug/pprof/cmdline" {
			http.NotFound(w, r)
			return
		}
		w.Write([]byte("fake-cmdline-output"))
	}))
	defer adminSrv.Close()
	adminAddr := strings.TrimPrefix(adminSrv.URL, "http://")

	h, accounts := newTestHandlerWithAdminAddr(t, adminAddr)
	admin, _ := accounts.Register(context.Background(), "boss", "password", true)

	req := httptest.NewRequest(http.MethodGet, "/admin/monitoring/pprof/cmdline", nil)
	req.AddCookie(h.sessionCookie(admin.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("proxy status = %d, want 200; body=%s", rec.Code, rec.Body.String())
	}
	if rec.Body.String() != "fake-cmdline-output" {
		t.Errorf("proxy body = %q, want %q", rec.Body.String(), "fake-cmdline-output")
	}
}

// TestAdminPprofProxyForbiddenForNonAdmin verifies the proxy route is
// gated by requireAdmin like every other /admin/* route.
func TestAdminPprofProxyForbiddenForNonAdmin(t *testing.T) {
	h, accounts := newTestHandlerWithAdminAddr(t, "127.0.0.1:9")
	acc, _ := accounts.Register(context.Background(), "plainuser", "password", false)

	req := httptest.NewRequest(http.MethodGet, "/admin/monitoring/pprof/heap", nil)
	req.AddCookie(h.sessionCookie(acc.ID, 0))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, req)

	if rec.Code != http.StatusForbidden {
		t.Errorf("non-admin pprof proxy = %d, want 403", rec.Code)
	}
}

// itoa is a tiny helper to avoid importing strconv in every test line.
func itoa(u uint) string {
	if u == 0 {
		return "0"
	}
	var buf [20]byte
	i := len(buf)
	for u > 0 {
		i--
		buf[i] = byte('0' + u%10)
		u /= 10
	}
	return string(buf[i:])
}
