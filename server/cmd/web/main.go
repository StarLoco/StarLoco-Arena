// Command web runs the DofusArena account web portal as a standalone
// process, independent of the game TCP server. It shares the same config
// file, database, and internal/service layer as the game server, but binds
// only the HTTP portal.
//
// Use this in production to run the public-facing site and the game listener
// as separate processes (e.g. behind different reverse-proxy routes / on
// different hosts). For local dev you can instead set web.enabled: true in
// the config and run cmd/server alone, which hosts both in one process.
//
// Usage:
//
//	go run ./cmd/web --config configs/config.dev.yaml
//
// The portal binds config web.listen_addr (default :8080) regardless of the
// web.enabled flag -- that flag only governs the in-process launch from
// cmd/server.
package main

import (
	"context"
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/db"
	"github.com/dofusarena/go-server/internal/gamedata"
	golog "github.com/dofusarena/go-server/internal/log"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/webadmin"
	"github.com/dofusarena/go-server/migrations"
)

func main() {
	if err := run(); err != nil {
		fmt.Fprintln(os.Stderr, "fatal:", err)
		os.Exit(1)
	}
}

func run() error {
	configPath := flag.String("config", "", "path to config YAML file (optional, defaults + env vars used otherwise)")
	noMigrate := flag.Bool("no-migrate", false, "skip running database migrations on startup")
	addr := flag.String("addr", "", "override web.listen_addr (e.g. :8080)")
	flag.Parse()

	cfg, err := config.Load(*configPath)
	if err != nil {
		return fmt.Errorf("load config: %w", err)
	}
	listenAddr := cfg.Web.ListenAddr
	if *addr != "" {
		listenAddr = *addr
	}
	if listenAddr == "" {
		listenAddr = ":8080"
	}

	logger := golog.New(golog.Options{Level: cfg.Logging.Level, Format: cfg.Logging.Format})
	logger.Info().Str("server_name", cfg.Server.Name).Msg("starting web portal")

	gdb, err := db.Open(cfg.Database, logger)
	if err != nil {
		return fmt.Errorf("open database: %w", err)
	}
	if !*noMigrate {
		db.SetMigrationsFS(migrations.FS)
		if err := db.Migrate(gdb, cfg.Database); err != nil {
			return fmt.Errorf("run migrations: %w", err)
		}
		logger.Info().Msg("migrations applied")
	}

	accountService := service.NewAccountService(gdb)

	// The standalone portal has no live game registry (that lives in the
	// game process), so World is nil -- handlers degrade to omitting
	// online-status badges. Game data is loaded lazily for any name lookups.
	store := gamedata.NewStore(cfg.GameData.Dir)

	handler, ephemeral, err := webadmin.New(webadmin.Deps{
		Accounts: accountService,
		Data:     store,
		World:    nil,
		Logger:   logger,
	}, webadmin.Config{
		SecureCookies: cfg.Web.SecureCookies,
		SessionSecret: cfg.Web.SessionSecret,
		BaseURL:       cfg.Web.BaseURL,
		ServerName:    cfg.Server.Name,
		// Only reachable if this standalone process shares a host/network
		// with the game process's admin HTTP server (loopback-bound by
		// default) -- see docs/10-web-portal.md §10.8's monitoring-page
		// note. If unreachable, the monitoring page just shows a fetch
		// error rather than failing to start.
		AdminHTTPAddr: cfg.Server.AdminAddr,
	})
	if err != nil {
		return fmt.Errorf("build web portal: %w", err)
	}
	if ephemeral {
		logger.Warn().Msg("web.session_secret is empty -- using an ephemeral key; sessions won't survive restart. Set web.session_secret in production.")
	}

	ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt, syscall.SIGTERM)
	defer stop()

	return webadmin.Serve(ctx, listenAddr, handler, logger)
}
