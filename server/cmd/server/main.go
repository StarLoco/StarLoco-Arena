// Command server is the DofusArena game server entry point. It wires
// config, logging, the database, lazily-loaded game data, the world
// registry, the combat fight manager, and the TCP listener together (via
// internal/app), then serves until an interrupt/terminate signal triggers
// graceful shutdown.
//
// See go-server/docs/01-architecture.md §1.4 for the startup sequence and
// the reasoning behind each ordering decision.
package main

import (
	"context"
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/db"
	golog "github.com/dofusarena/go-server/internal/log"
	"github.com/dofusarena/go-server/migrations"
)

// shutdownGracePeriod bounds how long graceful shutdown waits for connected
// clients to receive the WORLD_SERVER_UNAVAILABLE notification and tear down
// their connections before the process forcibly exits.
const shutdownGracePeriod = 5 * time.Second

func main() {
	if err := run(); err != nil {
		fmt.Fprintln(os.Stderr, "fatal:", err)
		os.Exit(1)
	}
}

func run() error {
	start := time.Now()

	configPath := flag.String("config", "", "path to config YAML file (optional, defaults + env vars used otherwise)")
	noMigrate := flag.Bool("no-migrate", false, "skip running database migrations on startup")
	migrateOnly := flag.Bool("migrate-only", false, "run pending migrations then exit")
	warmCache := flag.Bool("warm-cache", false, "eagerly load all game-data repositories at startup instead of lazily")
	tracePackets := flag.Bool("trace-packets", false, "log every inbound/outbound wire packet (opcode + hex); very verbose, for debugging")
	flag.Parse()

	cfg, err := config.Load(*configPath)
	if err != nil {
		return fmt.Errorf("load config: %w", err)
	}
	if *tracePackets {
		cfg.Logging.TracePackets = true
	}

	logger := golog.New(golog.Options{Level: cfg.Logging.Level, Format: cfg.Logging.Format, Dir: cfg.Logging.Dir})
	logger.Info().Str("server_name", cfg.Server.Name).Msg("starting")

	if *migrateOnly {
		gdb, err := db.Open(cfg.Database, logger)
		if err != nil {
			return fmt.Errorf("open database: %w", err)
		}
		db.SetMigrationsFS(migrations.FS)
		if err := db.Migrate(gdb, cfg.Database); err != nil {
			return fmt.Errorf("run migrations: %w", err)
		}
		logger.Info().Msg("migrations applied, migrate-only requested, exiting")
		return nil
	}

	a, err := app.New(cfg, logger, app.Options{SkipMigrate: *noMigrate, WarmCache: *warmCache})
	if err != nil {
		return err
	}
	if !*noMigrate {
		logger.Info().Msg("migrations applied")
	}

	if err := a.Listen(); err != nil {
		return err
	}

	logger.Info().
		Str("addr", a.Addr()).
		Dur("startup_duration", time.Since(start)).
		Msg("server ready, accepting connections")

	serveCtx, stop := signal.NotifyContext(context.Background(), os.Interrupt, syscall.SIGTERM)
	defer stop()

	serveErrCh := make(chan error, 1)
	go func() {
		serveErrCh <- a.Serve(serveCtx)
	}()

	select {
	case <-serveCtx.Done():
		logger.Info().Msg("shutdown signal received, notifying connected clients")
	case err := <-serveErrCh:
		if err != nil {
			logger.Error().Err(err).Msg("listener stopped unexpectedly")
		}
	}

	// Graceful shutdown: broadcast WORLD_SERVER_UNAVAILABLE to every
	// connected coach and drain their connections, bounded by a timeout so a
	// stuck client can't hold the process open indefinitely.
	shutdownCtx, cancel := context.WithTimeout(context.Background(), shutdownGracePeriod)
	defer cancel()
	if err := a.Shutdown(shutdownCtx); err != nil {
		logger.Warn().Err(err).Msg("graceful shutdown did not complete cleanly")
	}
	logger.Info().Int("active_fights", a.ActiveFights()).Msg("shutdown complete")
	return nil
}
