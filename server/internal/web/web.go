// Package web serves the browser portal bundled with the server: the public
// site players land on, the account area where they see everything the server
// stores about them, and the admin console operators run the game from.
//
// Everything ships inside the Go binary — templates, stylesheet, fonts,
// favicon — so the portal works on a machine with no internet access and needs
// no build step, no Node toolchain and no CDN.
package web

import (
	"context"
	"errors"
	"fmt"
	"log/slog"
	"net"
	"net/http"
	"net/url"
	"regexp"
	"strconv"
	"strings"
	"sync"
	"time"

	"github.com/StarLoco/arena-2.70/internal/config"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// maxLoginLen caps account names. The column holds 64, but a shorter, stricter
// bound keeps names typeable in the game client's login box.
const maxLoginLen = 24

// maxPasswordLen is bcrypt's hard limit: it refuses anything longer, so reject
// it here with a readable message rather than surfacing a library error.
const maxPasswordLen = 72

// loginRe restricts account names to characters that survive the client's
// windows-1252 wire encoding unchanged.
var loginRe = regexp.MustCompile(`^[A-Za-z0-9_-]+$`)

// accountsPerPage is the admin console's page size.
const accountsPerPage = 25

// Live exposes the running game server's counters to the portal. Every field is
// optional — a nil func reads zero — so tests and a server started without a
// world can construct the portal without stubbing anything.
type Live struct {
	// PlayersOnline is the number of coaches currently in the world.
	PlayersOnline func() int
	// ActiveFights is the number of fights in progress.
	ActiveFights func() int
}

// Server is the portal. Construct it with New and hand Handler() to net/http.
type Server struct {
	store *store.Store
	cfg   config.WebConfig
	log   *slog.Logger

	// gameAddr is the configured game listen address, used to tell players
	// what to put in their client config.
	gameAddr string
	live     Live

	codec *sessionCodec
	tmpl  *templateSet

	// limiter caps account creation, loginLimiter caps sign-in attempts. They
	// are separate because the right allowance differs by an order of
	// magnitude: creating ten accounts an hour from one address is already
	// suspicious, whereas mistyping a password ten times in an evening is not.
	limiter      *limiter
	loginLimiter *limiter
	started      time.Time
}

// New builds the portal. It fails only on a programming error — a template
// that does not parse — so a caller can treat an error as fatal.
func New(st *store.Store, cfg config.WebConfig, gameAddr string, live Live, log *slog.Logger) (*Server, error) {
	if log == nil {
		log = slog.Default()
	}
	if cfg.MinLoginLength <= 0 {
		cfg.MinLoginLength = 3
	}
	if cfg.MinPasswordLength <= 0 {
		cfg.MinPasswordLength = 6
	}

	codec, ephemeral, err := newSessionCodec(cfg.SessionSecret)
	if err != nil {
		return nil, err
	}
	if ephemeral {
		log.Warn("web: no session secret configured, using a random one — " +
			"everybody will be signed out when the server restarts " +
			"(set web.session_secret to avoid this)")
	}

	tmpl, err := parseTemplates()
	if err != nil {
		return nil, err
	}

	return &Server{
		store:    st,
		cfg:      cfg,
		log:      log,
		gameAddr: gameAddr,
		live:     live,
		codec:    codec,
		tmpl:     tmpl,
		// A generous allowance for a household or guild sharing one address,
		// but low enough that the form cannot be used to hammer the database.
		limiter: newLimiter(10, time.Hour),
		// Brute-force guard. Passwords are bcrypt-hashed, so an online guess
		// already costs ~100ms of CPU; this stops that CPU being the whole
		// server's.
		loginLimiter: newLimiter(20, 15*time.Minute),
		started:      time.Now(),
	}, nil
}

// Handler returns the portal's routes.
//
// Go 1.22 method+path patterns let GET and POST on the same URL map to
// different handlers, so no handler starts with a method switch.
func (s *Server) Handler() http.Handler {
	mux := http.NewServeMux()

	// Embedded assets: stylesheet, fonts, favicon.
	mux.Handle("GET /static/", http.StripPrefix("/static/", cacheStatic(staticFileServer())))
	mux.HandleFunc("GET /favicon.ico", func(w http.ResponseWriter, r *http.Request) {
		http.Redirect(w, r, "/static/favicon.svg", http.StatusMovedPermanently)
	})

	// Public.
	mux.HandleFunc("GET /{$}", s.handleIndex) // {$} = exactly "/", not a catch-all
	mux.HandleFunc("GET /status", s.handleStatus)
	mux.HandleFunc("GET /ladder", s.handleLadder)
	mux.HandleFunc("GET /login", s.handleLoginForm)
	mux.HandleFunc("POST /login", s.handleLoginSubmit)
	mux.HandleFunc("GET /register", s.handleRegisterForm)
	mux.HandleFunc("POST /register", s.handleRegisterSubmit)
	mux.HandleFunc("POST /logout", s.handleLogout)
	mux.HandleFunc("GET /health", func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "text/plain; charset=utf-8")
		_, _ = w.Write([]byte("ok"))
	})

	// Signed in (any account).
	mux.HandleFunc("GET /account", s.requireUser(s.handleAccount))
	mux.HandleFunc("GET /account/password", s.requireUser(s.handlePasswordForm))
	mux.HandleFunc("POST /account/password", s.requireUser(s.handlePasswordSubmit))

	// Admin console. Starting impersonation is admin-gated; stopping it is
	// not, so somebody who is impersonating can always get back to themselves
	// even if their admin rights were revoked while they were away.
	mux.HandleFunc("GET /admin", s.requireAdmin(s.handleAdminDashboard))
	mux.HandleFunc("GET /admin/accounts", s.requireAdmin(s.handleAdminAccounts))
	mux.HandleFunc("GET /admin/accounts/new", s.requireAdmin(s.handleAdminCreateForm))
	mux.HandleFunc("POST /admin/accounts/new", s.requireAdmin(s.handleAdminCreateSubmit))
	mux.HandleFunc("GET /admin/accounts/{id}", s.requireAdmin(s.handleAdminAccountDetail))
	mux.HandleFunc("POST /admin/accounts/{id}/delete", s.requireAdmin(s.handleAdminDelete))
	mux.HandleFunc("POST /admin/accounts/{id}/toggle-admin", s.requireAdmin(s.handleAdminToggleAdmin))
	mux.HandleFunc("POST /admin/accounts/{id}/impersonate", s.requireAdmin(s.handleImpersonateStart))
	mux.HandleFunc("GET /admin/monitoring", s.requireAdmin(s.handleAdminMonitoring))
	mux.HandleFunc("GET /admin/monitoring/pprof/{profile}", s.requireAdmin(s.handleAdminPprof))
	mux.HandleFunc("POST /impersonate/stop", s.handleImpersonateStop)

	return securityHeaders(mux)
}

// serverName is the branding shown in the header and the page titles.
func (s *Server) serverName() string {
	if n := strings.TrimSpace(s.cfg.ServerName); n != "" {
		return n
	}
	return "DofusArena"
}

func (s *Server) playersOnline() int {
	if s.live.PlayersOnline == nil {
		return 0
	}
	return s.live.PlayersOnline()
}

func (s *Server) activeFights() int {
	if s.live.ActiveFights == nil {
		return 0
	}
	return s.live.ActiveFights()
}

// uptimeSeconds is how long the portal (and so the server) has been running.
func (s *Server) uptimeSeconds() int64 {
	return int64(time.Since(s.started) / time.Second)
}

// gameAddress works out what players should type into their client. The
// configured listen address is usually a wildcard (0.0.0.0), which is useless
// to a player, so the host the visitor already reached us on is substituted.
func (s *Server) gameAddress(r *http.Request) string {
	_, port, err := net.SplitHostPort(s.gameAddr)
	if err != nil || port == "" {
		port = "5555"
	}

	host := s.cfg.PublicHost
	if host == "" {
		if h, _, err := net.SplitHostPort(s.gameAddr); err == nil && !isWildcard(h) {
			host = h
		}
	}
	if host == "" && r != nil {
		if h, _, err := net.SplitHostPort(r.Host); err == nil {
			host = h
		} else {
			host = r.Host
		}
	}
	if host == "" {
		host = "127.0.0.1"
	}
	return net.JoinHostPort(host, port)
}

func isWildcard(host string) bool {
	return host == "" || host == "0.0.0.0" || host == "::" || host == "[::]"
}

// validate applies the sign-up policy shared by public registration and the
// admin console's create form.
func (s *Server) validate(login, password string) error {
	switch {
	case login == "":
		return errors.New("Please choose an account name.")
	case len(login) < s.cfg.MinLoginLength:
		return fmt.Errorf("The account name must be at least %d characters.", s.cfg.MinLoginLength)
	case len(login) > maxLoginLen:
		return fmt.Errorf("The account name must be at most %d characters.", maxLoginLen)
	case !loginRe.MatchString(login):
		return errors.New("The account name may only contain letters, digits, - and _.")
	case len(password) < s.cfg.MinPasswordLength:
		return fmt.Errorf("The password must be at least %d characters.", s.cfg.MinPasswordLength)
	case len(password) > maxPasswordLen:
		return fmt.Errorf("The password must be at most %d characters.", maxPasswordLen)
	}
	return nil
}

// redirect sends a see-other, the correct code after a successful POST: it
// makes the browser follow up with a GET, so a refresh cannot re-submit.
func redirect(w http.ResponseWriter, r *http.Request, to string) {
	http.Redirect(w, r, to, http.StatusSeeOther)
}

// securityHeaders applies a conservative baseline.
//
// The portal serves only its own assets and has no inline scripts, so the
// policy can forbid scripts outright. That is a real mitigation rather than a
// formality: it means a stored-XSS bug in, say, a coach name could not execute.
func securityHeaders(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		h := w.Header()
		h.Set("Content-Security-Policy",
			"default-src 'none'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; "+
				"font-src 'self'; form-action 'self'; base-uri 'none'; frame-ancestors 'none'")
		h.Set("X-Content-Type-Options", "nosniff")
		h.Set("Referrer-Policy", "same-origin")
		next.ServeHTTP(w, r)
	})
}

// cacheStatic lets browsers keep the stylesheet and fonts. The URLs are stable
// across builds, so the window is a day rather than a year: long enough to skip
// the requests, short enough that an upgraded server looks right by tomorrow.
func cacheStatic(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Cache-Control", "public, max-age=86400")
		next.ServeHTTP(w, r)
	})
}

// sameOrigin reports whether a state-changing request came from our own page.
// Browsers always send Origin on cross-origin form posts, so an Origin that
// disagrees with Host is a cross-site submission. A missing Origin is accepted:
// same-origin form posts may omit it, and non-browser clients (curl) are not
// the threat model here.
func sameOrigin(r *http.Request) bool {
	origin := r.Header.Get("Origin")
	if origin == "" || origin == "null" {
		return true
	}
	u, err := url.Parse(origin)
	if err != nil {
		return false
	}
	return strings.EqualFold(u.Host, r.Host)
}

// clientIP extracts the peer address. Proxy headers are deliberately ignored:
// they are attacker-controlled unless a trusted proxy is known to rewrite
// them, and trusting them would defeat the rate limit entirely.
func clientIP(r *http.Request) string {
	host, _, err := net.SplitHostPort(r.RemoteAddr)
	if err != nil {
		return r.RemoteAddr
	}
	return host
}

func isDuplicate(err error) bool {
	if err == nil {
		return false
	}
	msg := strings.ToLower(err.Error())
	return strings.Contains(msg, "unique constraint") || // sqlite
		strings.Contains(msg, "duplicate key") || // postgres
		strings.Contains(msg, "duplicate entry") // mysql
}

// idParam reads a {id} path value.
func idParam(r *http.Request) (uint, bool) {
	n, err := strconv.ParseUint(r.PathValue("id"), 10, 64)
	if err != nil || n == 0 {
		return 0, false
	}
	return uint(n), true
}

// ---------------------------------------------------------------------------
// Rate limiting
// ---------------------------------------------------------------------------

// limiter is a fixed-window counter keyed by client address.
type limiter struct {
	mu     sync.Mutex
	hits   map[string][]time.Time
	max    int
	window time.Duration
	// now is swappable for tests.
	now func() time.Time
}

// maxTrackedClients bounds the limiter's memory. Reaching it means either a
// very popular server or a distributed abuse attempt; either way, dropping the
// oldest state is preferable to growing without limit.
const maxTrackedClients = 4096

func newLimiter(max int, window time.Duration) *limiter {
	return &limiter{
		hits:   make(map[string][]time.Time),
		max:    max,
		window: window,
		now:    time.Now,
	}
}

func (l *limiter) allow(key string) bool {
	l.mu.Lock()
	defer l.mu.Unlock()

	now := l.now()
	cutoff := now.Add(-l.window)

	if len(l.hits) > maxTrackedClients {
		l.pruneLocked(cutoff)
		if len(l.hits) > maxTrackedClients {
			l.hits = make(map[string][]time.Time)
		}
	}

	kept := l.hits[key][:0]
	for _, t := range l.hits[key] {
		if t.After(cutoff) {
			kept = append(kept, t)
		}
	}
	if len(kept) >= l.max {
		l.hits[key] = kept
		return false
	}
	l.hits[key] = append(kept, now)
	return true
}

func (l *limiter) pruneLocked(cutoff time.Time) {
	for k, times := range l.hits {
		kept := times[:0]
		for _, t := range times {
			if t.After(cutoff) {
				kept = append(kept, t)
			}
		}
		if len(kept) == 0 {
			delete(l.hits, k)
		} else {
			l.hits[k] = kept
		}
	}
}

// ---------------------------------------------------------------------------
// Listening
// ---------------------------------------------------------------------------

// autoPorts is the ladder tried when the configured port is 0. Port 80 first so
// players can reach the portal without typing a port at all; 0 last means "any
// free port the OS will give us", which cannot fail.
var autoPorts = []string{"80", "8080", "8090", "3000", "5000", "0"}

// Listen binds the portal's listener.
//
// A port of 0 means "choose for me" and walks autoPorts. An explicit port is
// tried first and, if it is unavailable, falls back to the same ladder rather
// than refusing to start — a busy port should not cost the operator their
// server. Compare the returned listener's port with the requested one to detect
// that case.
func Listen(addr string) (net.Listener, error) {
	host, port, err := net.SplitHostPort(addr)
	if err != nil {
		// Tolerate a bare host or a bare port.
		if p, convErr := strconv.Atoi(strings.TrimPrefix(addr, ":")); convErr == nil {
			host, port = "", strconv.Itoa(p)
		} else {
			host, port = addr, "0"
		}
	}

	if port != "0" && port != "" {
		if ln, err := net.Listen("tcp", net.JoinHostPort(host, port)); err == nil {
			return ln, nil
		}
	}
	var lastErr error
	for _, p := range autoPorts {
		ln, err := net.Listen("tcp", net.JoinHostPort(host, p))
		if err == nil {
			return ln, nil
		}
		lastErr = err
	}
	return nil, fmt.Errorf("web: no port available for %q: %w", addr, lastErr)
}

// Port returns the numeric port a listener is bound to.
func Port(ln net.Listener) int {
	if tcp, ok := ln.Addr().(*net.TCPAddr); ok {
		return tcp.Port
	}
	return 0
}

// URL renders the address to show an operator in the console.
func URL(ln net.Listener) string { return urlForPort(Port(ln)) }

// urlForPort omits the port when it is the browser default, so the operator
// sees the shortest thing a player can actually type.
func urlForPort(port int) string {
	if port == 80 {
		return "http://localhost"
	}
	return fmt.Sprintf("http://localhost:%d", port)
}

// Serve runs the portal on ln until ctx is cancelled, then shuts it down.
func (s *Server) Serve(ctx context.Context, ln net.Listener) error {
	srv := &http.Server{
		Handler: s.Handler(),
		// The portal is on a hostile-by-default network; bound every phase so a
		// stuck client cannot pin a connection open indefinitely.
		ReadHeaderTimeout: 10 * time.Second,
		ReadTimeout:       30 * time.Second,
		WriteTimeout:      30 * time.Second,
		IdleTimeout:       60 * time.Second,
	}

	done := make(chan struct{})
	go func() {
		defer close(done)
		<-ctx.Done()
		shutdownCtx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
		defer cancel()
		_ = srv.Shutdown(shutdownCtx)
	}()

	err := srv.Serve(ln)
	<-done
	if errors.Is(err, http.ErrServerClosed) {
		return nil
	}
	return err
}
