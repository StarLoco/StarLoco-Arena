// Package app wires every server dependency together (config, DB, game
// data, world state, dispatch, and the TCP listener) into a single
// runnable App. This is the composition root shared by cmd/server/main.go
// (production entry point) and integration/e2e tests, so both exercise
// the exact same wiring code -- no drift between "what tests boot" and
// "what production boots".
package app

import (
	"context"
	"encoding/hex"
	"fmt"
	"net"
	"time"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/adminhttp"
	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/db"
	"github.com/dofusarena/go-server/internal/dispatch"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/webadmin"
	"github.com/dofusarena/go-server/internal/world"
	"github.com/dofusarena/go-server/migrations"
	"gorm.io/gorm"
)

// App bundles every long-lived component of a running server instance.
type App struct {
	Config config.Config
	Logger zerolog.Logger
	DB     *gorm.DB
	Deps   *dispatch.Deps

	listener *netio.Listener

	// web is the account portal handler, non-nil only when web.enabled so
	// the game process can host the site for single-command local dev. The
	// standalone cmd/web binary builds its own handler instead.
	web *webadmin.Handler

	// adminListener is the admin/observability HTTP server's bound
	// listener, non-nil only when cfg.Server.AdminAddr is non-empty. Bound
	// synchronously in Listen (like listener above) so Addr()-style
	// callers (notably tests using an OS-assigned ":0" port) can discover
	// the actual bound address before Serve's accept loop starts.
	adminListener net.Listener
}

// Options configures New's behavior.
type Options struct {
	// SkipMigrate skips running migrations during New (equivalent to the
	// server binary's --no-migrate flag). Tests that construct their own
	// pre-migrated DB should set this.
	SkipMigrate bool
	// WarmCache forces eager game-data loading during New (equivalent to
	// --warm-cache).
	WarmCache bool
}

// New constructs a fully-wired App: opens the DB, applies migrations
// (unless skipped), builds every service/world/dispatch component, and
// registers all opcode handlers. It does NOT start listening -- call
// Listen and then Serve separately, so callers (tests especially) can
// control exactly when the socket binds.
func New(cfg config.Config, logger zerolog.Logger, opts Options) (*App, error) {
	gdb, err := db.Open(cfg.Database, logger)
	if err != nil {
		return nil, fmt.Errorf("app: open database: %w", err)
	}
	db.PingAsync(gdb, logger, 5*time.Second)

	db.SetMigrationsFS(migrations.FS)
	if !opts.SkipMigrate {
		if err := db.Migrate(gdb, cfg.Database); err != nil {
			return nil, fmt.Errorf("app: run migrations: %w", err)
		}
	}

	authService := service.NewAuthService(gdb)
	coachService := service.NewCoachService(gdb)
	fighterService := service.NewFighterService(gdb)
	teamService := service.NewTeamService(gdb)
	socialService := service.NewSocialService(gdb)
	accountService := service.NewAccountService(gdb)

	ctx := context.Background()
	if err := authService.ResetAllConnectedFlags(ctx); err != nil {
		logger.Warn().Err(err).Msg("app: failed to reset stale connected flags (non-fatal)")
	}

	store := gamedata.NewStore(cfg.GameData.Dir)
	if cfg.GameData.WarmCache || opts.WarmCache {
		if err := store.WarmUp(); err != nil {
			logger.Warn().Err(err).Msg("app: gamedata warm-cache failed (non-fatal, will retry lazily)")
		}
	}

	registry := world.NewRegistry()
	matchmaker := world.NewMatchmaker()
	duelManager := world.NewDuelManager()
	invitationManager := world.NewInvitationManager()
	exchangeManager := world.NewExchangeManager()
	matchmakingService := service.NewMatchmakingService(matchmaker, duelManager, logger)

	fightClocks := combat.Clocks{
		Presentation: cfg.Combat.PresentationClock,
		Placement:    cfg.Combat.PlacementClock,
		Observation:  cfg.Combat.ObservationClock,
		Turn:         cfg.Combat.TurnClock,
		// Brief settle so a FIGHTER_TURN_END sent immediately after a
		// spell/card/close-combat cast waits for the client to finish
		// playing that action's animation group before the turn ends --
		// preventing the "cast Teleport, end turn, fighter stuck" freeze
		// (see combat.handleFighterEndTurn's settle guard).
		ActionSettle: cfg.Combat.ActionSettle,
	}
	fightManager := combat.NewManager(fightClocks, store, logger)

	deps := &dispatch.Deps{
		Server:      cfg.Server,
		Combat:      cfg.Combat,
		Auth:        authService,
		Coach:       coachService,
		Fighter:     fighterService,
		Team:        teamService,
		Social:      socialService,
		Matchmaking: matchmakingService,
		World:       registry,
		Duels:       duelManager,
		Invitations: invitationManager,
		Exchanges:   exchangeManager,
		Fights:      fightManager,
		Data:        store,
		Logger:      logger,
	}
	router := dispatch.NewRouter(logger)
	dispatch.RegisterAll(router, deps)

	// Optional packet tracing: log every inbound (via the router) and
	// outbound (via a global netio tracer) frame with opcode name + hex.
	// Verbose; intended for debugging protocol issues against a real
	// client, toggled by logging.trace_packets / --trace-packets.
	if cfg.Logging.TracePackets {
		router.SetTracePackets(true)
		netio.SetOutboundTracer(func(sessionID uint64, frame protocol.OutboundFrame) {
			logger.Info().
				Uint64("session", sessionID).
				Str("dir", "OUT").
				Str("opcode", frame.Opcode.Name()).
				Int("len", len(frame.Payload)).
				Str("hex", hex.EncodeToString(frame.Payload)).
				Msg("packet")
		})
	}

	listener := netio.NewListener(
		cfg.Server.ListenAddr,
		logger,
		func(session *netio.Session) {
			logger.Debug().Uint64("session_id", session.ID()).Str("remote", session.RemoteAddr()).Msg("connection accepted")
		},
		router.Handle,
		func(session *netio.Session) {
			logger.Debug().Uint64("session_id", session.ID()).Msg("connection closed")
			dispatch.HandleDisconnect(session, deps)
		},
	)

	a := &App{
		Config:   cfg,
		Logger:   logger,
		DB:       gdb,
		Deps:     deps,
		listener: listener,
	}

	// Build the account web portal only when enabled, so the game process
	// can optionally host it for single-command local dev. Production
	// typically runs it as the separate cmd/web process instead.
	if cfg.Web.Enabled {
		webHandler, ephemeral, err := webadmin.New(webadmin.Deps{
			Accounts: accountService,
			Data:     store,
			World:    registry,
			Fights:   fightManager,
			Logger:   logger,
		}, webadmin.Config{
			SecureCookies: cfg.Web.SecureCookies,
			SessionSecret: cfg.Web.SessionSecret,
			BaseURL:       cfg.Web.BaseURL,
			ServerName:    cfg.Server.Name,
			AdminHTTPAddr: cfg.Server.AdminAddr,
		})
		if err != nil {
			return nil, fmt.Errorf("app: build web portal: %w", err)
		}
		if ephemeral {
			logger.Warn().Msg("app: web.session_secret is empty -- using an ephemeral key; sessions won't survive restart. Set web.session_secret in production.")
		}
		a.web = webHandler
	}

	return a, nil
}

// Listen binds the TCP listener, and -- if cfg.Server.AdminAddr is
// non-empty -- the admin/observability HTTP listener too. Must be called
// before Serve. Both bind synchronously here (rather than inside Serve's
// goroutines) so Addr()/AdminAddr() reliably report the actual bound
// address immediately afterward, including for OS-assigned (":0") ports
// used by tests.
func (a *App) Listen() error {
	if err := a.listener.Listen(); err != nil {
		return fmt.Errorf("app: bind listener on %s: %w", a.Config.Server.ListenAddr, err)
	}
	if a.Config.Server.AdminAddr != "" {
		ln, err := net.Listen("tcp", a.Config.Server.AdminAddr)
		if err != nil {
			return fmt.Errorf("app: bind admin http listener on %s: %w", a.Config.Server.AdminAddr, err)
		}
		a.adminListener = ln
	}
	return nil
}

// Addr returns the bound listener address. Only valid after Listen.
func (a *App) Addr() string {
	if a.listener.Addr() == nil {
		return ""
	}
	return a.listener.Addr().String()
}

// AdminAddr returns the bound admin/observability HTTP listener address.
// Only valid after Listen, and empty if cfg.Server.AdminAddr was "" (admin
// server disabled).
func (a *App) AdminAddr() string {
	if a.adminListener == nil {
		return ""
	}
	return a.adminListener.Addr().String()
}

// Serve runs the accept loop until ctx is canceled. Blocks until the
// listener stops. When the web portal is enabled, or the admin/
// observability HTTP server is configured, each is started in its own
// background goroutine tied to the same context, so all three stop
// together on shutdown.
func (a *App) Serve(ctx context.Context) error {
	if a.web != nil {
		go func() {
			if err := webadmin.Serve(ctx, a.Config.Web.ListenAddr, a.web, a.Logger); err != nil {
				a.Logger.Error().Err(err).Msg("app: web portal stopped unexpectedly")
			}
		}()
	}
	if a.adminListener != nil {
		go func() {
			deps := adminhttp.Deps{World: a.Deps.World, Fights: a.Deps.Fights}
			if err := adminhttp.ServeListener(ctx, a.adminListener, deps, a.Logger); err != nil {
				a.Logger.Error().Err(err).Msg("app: admin http server stopped unexpectedly")
			}
		}()
	}
	return a.listener.Serve(ctx)
}

// Close stops the listener from accepting new connections.
func (a *App) Close() error {
	return a.listener.Close()
}

// Shutdown performs a graceful shutdown: it stops accepting new connections,
// then notifies every currently-online coach that the world is going away by
// broadcasting WORLD_SERVER_UNAVAILABLE (1026) and closing their session (see
// dispatch.BroadcastShutdown). netio's drain-before-close contract guarantees
// the notification frame is flushed to each client before its socket closes,
// so players get a clean "world unavailable" prompt rather than a silently
// dropped connection.
//
// After broadcasting, it waits for the connections to drain and tear down,
// bounded by ctx: if ctx's deadline elapses first, Shutdown returns
// ctx.Err() and the remaining sockets are severed by process exit. The
// notified-coach count is logged for operators.
func (a *App) Shutdown(ctx context.Context) error {
	// Stop accepting new connections first so the online set can't grow
	// while we're notifying it.
	if err := a.listener.Close(); err != nil {
		a.Logger.Warn().Err(err).Msg("app: error closing listener during shutdown")
	}

	notified := dispatch.BroadcastShutdown(a.Deps)
	a.Logger.Info().
		Int("notified_coaches", notified).
		Int("active_fights", a.Deps.Fights.Count()).
		Msg("app: broadcast WORLD_SERVER_UNAVAILABLE, draining connections")

	// Give the connection write loops a moment to flush the 1026 frames and
	// close their sockets. Poll the online count rather than sleeping a
	// fixed duration so shutdown finishes as soon as every session has torn
	// down (each disconnect handler calls World.Remove).
	ticker := time.NewTicker(50 * time.Millisecond)
	defer ticker.Stop()
	for {
		if a.Deps.World.Len() == 0 {
			return nil
		}
		select {
		case <-ctx.Done():
			a.Logger.Warn().
				Int("still_online", a.Deps.World.Len()).
				Msg("app: shutdown grace period elapsed, forcing exit")
			return ctx.Err()
		case <-ticker.C:
		}
	}
}

// ActiveFights returns the number of in-progress fights, used for shutdown
// logging.
func (a *App) ActiveFights() int {
	return a.Deps.Fights.Count()
}
