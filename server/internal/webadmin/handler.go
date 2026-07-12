// Package webadmin implements the account web portal: a self-contained,
// dependency-light (Go stdlib + html/template only) HTTP site letting
// visitors register/login and view all of their own stored data, and
// letting admin accounts manage every account (list/create/delete/deep-view)
// and impersonate any account to see the portal as them.
//
// It shares internal/service and the database with the game server. It can
// be started inside the game process (config web.enabled) for single-command
// local dev, or run as the standalone cmd/web binary in production so the
// public-facing site and the game TCP listener are separate processes.
//
// See docs/10-web-portal.md for the full design.
package webadmin

import (
	"context"
	"net/http"
	"time"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/world"
)

// Deps bundles everything the portal needs from the rest of the server.
// Data, World, and Fights are optional (all may be nil when the portal
// runs standalone without a loaded game-data store or live game-process
// state, e.g. cmd/web); handlers degrade gracefully, e.g. showing template
// IDs instead of names when Data is nil, or omitting online/live-fight
// counts when World/Fights are nil.
type Deps struct {
	Accounts *service.AccountService
	Data     *gamedata.Store
	World    *world.Registry
	Fights   *combat.Manager
	Logger   zerolog.Logger
}

// Config tunes portal behavior independent of the shared Deps.
type Config struct {
	SecureCookies bool
	SessionSecret string
	BaseURL       string
	ServerName    string
	// AdminHTTPAddr is the address of the loopback-only internal/adminhttp
	// server (server.admin_addr in config -- e.g. "127.0.0.1:9090"),
	// letting the portal's /admin/monitoring page fetch its live
	// healthz/stats counters and reverse-proxy its pprof endpoints so an
	// authenticated admin can reach them through the public-facing portal
	// without direct network access to the loopback address. Empty
	// disables the feature: the monitoring page renders a "not
	// configured" notice and the proxy route always 404s. See
	// docs/10-web-portal.md §10.8.
	AdminHTTPAddr string
}

// Handler is the portal's root http.Handler: it owns the router, the parsed
// template set, the session codec, and the injected dependencies.
type Handler struct {
	deps          Deps
	mux           *http.ServeMux
	tmpl          *templateSet
	codec         *sessionCodec
	secureCookies bool
	serverName    string
	baseURL       string
	startedAt     time.Time
	adminHTTPAddr string
}

// New constructs the portal Handler: parses the embedded templates, builds
// the session codec, and registers all routes. It returns an ephemeral-key
// warning flag so the caller can log a prominent notice when no session
// secret was configured.
func New(deps Deps, cfg Config) (*Handler, bool, error) {
	tmpl, err := parseTemplates()
	if err != nil {
		return nil, false, err
	}
	codec, ephemeral, err := newSessionCodec(cfg.SessionSecret)
	if err != nil {
		return nil, false, err
	}

	serverName := cfg.ServerName
	if serverName == "" {
		serverName = "DofusArena"
	}

	h := &Handler{
		deps:          deps,
		tmpl:          tmpl,
		codec:         codec,
		secureCookies: cfg.SecureCookies,
		serverName:    serverName,
		baseURL:       cfg.BaseURL,
		startedAt:     time.Now(),
		adminHTTPAddr: cfg.AdminHTTPAddr,
	}
	h.routes()
	return h, ephemeral, nil
}

// ServeHTTP dispatches to the internal router.
func (h *Handler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	h.mux.ServeHTTP(w, r)
}

// ctx returns a request-scoped context with a short timeout for DB work,
// so a slow query can't pin a request open indefinitely.
func reqCtx(r *http.Request) (context.Context, context.CancelFunc) {
	return context.WithTimeout(r.Context(), 10*time.Second)
}
