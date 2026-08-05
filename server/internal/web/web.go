// Package web serves the small browser portal bundled with the server, so
// players can create their own accounts without the operator running a command
// for each of them.
//
// It is intentionally tiny: one page, one form, no JavaScript, no external
// assets. Everything is embedded in the binary, so it works on a machine with
// no internet access.
package web

import (
	"context"
	"embed"
	"errors"
	"fmt"
	"html/template"
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
	"github.com/StarLoco/arena-2.70/internal/version"
)

//go:embed assets/page.html
var assetsFS embed.FS

var pageTmpl = template.Must(template.ParseFS(assetsFS, "assets/page.html"))

// maxLoginLen caps account names. The column holds 64, but a shorter, stricter
// bound keeps names typeable in the game client's login box.
const maxLoginLen = 24

// maxPasswordLen is bcrypt's hard limit: it refuses anything longer, so reject
// it here with a readable message rather than surfacing a library error.
const maxPasswordLen = 72

// loginRe restricts account names to characters that survive the client's
// windows-1252 wire encoding unchanged.
var loginRe = regexp.MustCompile(`^[A-Za-z0-9_-]+$`)

// Server is the portal. Construct it with New and hand Handler() to net/http.
type Server struct {
	store *store.Store
	cfg   config.WebConfig
	log   *slog.Logger

	// gameAddr is the configured game listen address, used to tell players what
	// to put in their client config.
	gameAddr string
	// online reports the number of connected players; may be nil.
	online func() int

	limiter *limiter
}

// New builds the portal. online may be nil.
func New(st *store.Store, cfg config.WebConfig, gameAddr string, online func() int, log *slog.Logger) *Server {
	if log == nil {
		log = slog.Default()
	}
	if cfg.MinLoginLength <= 0 {
		cfg.MinLoginLength = 3
	}
	if cfg.MinPasswordLength <= 0 {
		cfg.MinPasswordLength = 6
	}
	return &Server{
		store:    st,
		cfg:      cfg,
		log:      log,
		gameAddr: gameAddr,
		online:   online,
		// A generous allowance for a household or guild sharing one address,
		// but low enough that the form cannot be used to hammer the database.
		limiter: newLimiter(10, time.Hour),
	}
}

// Handler returns the portal's routes.
func (s *Server) Handler() http.Handler {
	mux := http.NewServeMux()
	mux.HandleFunc("GET /{$}", s.handleIndex) // {$} = exactly "/", not a catch-all
	mux.HandleFunc("POST /register", s.handleRegister)
	mux.HandleFunc("GET /health", func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "text/plain; charset=utf-8")
		_, _ = w.Write([]byte("ok"))
	})
	return securityHeaders(mux)
}

// pageData is the template model.
type pageData struct {
	ServerName          string
	Version             string
	GameAddress         string
	PlayersOnline       int
	RegistrationEnabled bool
	ClientDownloadURL   string
	MinLogin            int
	MinPassword         int

	Error        string
	CreatedLogin string
	CreatedAdmin bool
	FormLogin    string

	// FirstAccount marks a server with no accounts yet, where whoever registers
	// next becomes the owner.
	FirstAccount bool
}

func (s *Server) newPageData(r *http.Request) pageData {
	n := 0
	if s.online != nil {
		n = s.online()
	}
	first := false
	if count, err := s.store.Accounts.Count(); err == nil {
		first = count == 0
	}
	return pageData{
		ServerName:          "DofusArena Arena Server",
		Version:             version.Short(),
		GameAddress:         s.gameAddress(r),
		PlayersOnline:       n,
		RegistrationEnabled: s.cfg.RegistrationEnabled,
		ClientDownloadURL:   s.cfg.ClientDownloadURL,
		MinLogin:            s.cfg.MinLoginLength,
		MinPassword:         s.cfg.MinPasswordLength,
		FirstAccount:        first,
	}
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

func (s *Server) handleIndex(w http.ResponseWriter, r *http.Request) {
	s.render(w, r, http.StatusOK, s.newPageData(r))
}

func (s *Server) handleRegister(w http.ResponseWriter, r *http.Request) {
	data := s.newPageData(r)

	if !s.cfg.RegistrationEnabled {
		data.Error = "Registration is closed on this server."
		s.render(w, r, http.StatusForbidden, data)
		return
	}
	// Reject cross-site form posts. The portal's own form sends either no
	// Origin or its own; anything else is a third-party page driving the
	// visitor's browser.
	if !sameOrigin(r) {
		data.Error = "That request did not come from this page. Please try again."
		s.render(w, r, http.StatusForbidden, data)
		return
	}
	if err := r.ParseForm(); err != nil {
		data.Error = "The form could not be read. Please try again."
		s.render(w, r, http.StatusBadRequest, data)
		return
	}

	login := strings.TrimSpace(r.PostFormValue("login"))
	password := r.PostFormValue("password")
	data.FormLogin = login

	if err := s.validate(login, password); err != nil {
		data.Error = err.Error()
		s.render(w, r, http.StatusBadRequest, data)
		return
	}
	// Rate-limit only once the input is well-formed, so a player fumbling the
	// form does not burn their allowance.
	if !s.limiter.allow(clientIP(r)) {
		data.Error = "Too many accounts created from your address recently. Please try again later."
		s.render(w, r, http.StatusTooManyRequests, data)
		return
	}

	if _, err := s.store.Accounts.FindByName(login); err == nil {
		data.Error = "That account name is already taken."
		s.render(w, r, http.StatusConflict, data)
		return
	} else if !errors.Is(err, store.ErrNotFound) {
		s.log.Error("web: account lookup failed", "err", err)
		data.Error = "The server could not reach its database. Please try again."
		s.render(w, r, http.StatusInternalServerError, data)
		return
	}

	// A brand new server has no owner yet, so the first account registered
	// becomes the administrator — otherwise nobody could ever run a GM command
	// on a machine where only the release binary is installed. Every later
	// account is an ordinary player.
	first := false
	if n, err := s.store.Accounts.Count(); err != nil {
		s.log.Error("web: account count failed", "err", err)
	} else {
		first = n == 0
	}

	if _, err := s.store.Accounts.CreateAccount(login, password, first); err != nil {
		// The unique index is the real guard; a duplicate here means someone
		// took the name between the check above and now.
		if isDuplicate(err) {
			data.Error = "That account name is already taken."
			s.render(w, r, http.StatusConflict, data)
			return
		}
		s.log.Error("web: account creation failed", "err", err)
		data.Error = "The account could not be created. Please try again."
		s.render(w, r, http.StatusInternalServerError, data)
		return
	}

	s.log.Info("account registered via web portal", "login", login, "admin", first)
	data.CreatedLogin = login
	data.CreatedAdmin = first
	data.FormLogin = ""
	s.render(w, r, http.StatusOK, data)
}

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

func (s *Server) render(w http.ResponseWriter, _ *http.Request, status int, data pageData) {
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	w.WriteHeader(status)
	if err := pageTmpl.Execute(w, data); err != nil {
		// The response is already committed; all we can do is record it.
		s.log.Error("web: render failed", "err", err)
	}
}

// securityHeaders applies a conservative baseline. The portal serves only its
// own inline CSS, so the policy can be strict.
func securityHeaders(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		h := w.Header()
		h.Set("Content-Security-Policy",
			"default-src 'none'; style-src 'unsafe-inline'; form-action 'self'; base-uri 'none'; frame-ancestors 'none'")
		h.Set("X-Content-Type-Options", "nosniff")
		h.Set("Referrer-Policy", "same-origin")
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
