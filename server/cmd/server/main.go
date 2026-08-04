// Command server runs the DofusArena 2.70 game server.
package main

import (
	"context"
	"flag"
	"log/slog"
	"os"
	"os/signal"
	"strings"
	"syscall"

	"github.com/StarLoco/arena-2.70/internal/config"
	"github.com/StarLoco/arena-2.70/internal/game"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/store"
)

func main() {
	configPath := flag.String("config", "config.yaml", "path to YAML config file (optional)")
	flag.Parse()

	cfg, err := config.Load(*configPath)
	if err != nil {
		slog.Error("config", "err", err)
		os.Exit(1)
	}

	log := slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		Level: parseLevel(cfg.LogLevel),
	}))

	st, err := store.OpenConfig(store.Config{
		Driver:       cfg.DB.Driver,
		DSN:          cfg.DB.DSN,
		MaxOpenConns: cfg.DB.MaxOpenConns,
		MaxIdleConns: cfg.DB.MaxIdleConns,
	})
	if err != nil {
		log.Error("open store", "err", err)
		os.Exit(1)
	}
	defer st.Close()
	if err := st.ResetConnectedFlags(); err != nil {
		log.Warn("reset connected flags", "err", err)
	}

	// Load static game data (cards/spells/fighter-cards). Optional: the server
	// still runs without it, just with no templates.
	var cards *gamedata.Cards
	var spells *gamedata.Spells
	var fighterCards *gamedata.FighterCards
	var summonings *gamedata.Summonings
	var staticEffects *gamedata.StaticEffects
	var challengeDefs *gamedata.Challenges
	var events *gamedata.Events
	var cardSets *gamedata.CardSets
	var conditions *gamedata.Conditions
	// Fight arenas come from the client's own map files rather than the bdat store,
	// so they load independently: a server without them still runs, falling back to
	// the built-in world 5.
	fightMaps, err := gamedata.LoadFightMaps(cfg.DataDir)
	if err != nil {
		log.Warn("fight maps not loaded; falling back to the built-in arena",
			"dir", cfg.DataDir, "err", err)
	} else {
		log.Info("fight maps loaded", "arenas", fightMaps.Len(),
			"skipped", fightMaps.Skipped(), "unreachableSpecials", fightMaps.UnreachableSpecials())
	}
	if gdStore, err := gamedata.Open(cfg.DataDir); err != nil {
		log.Warn("game data not loaded", "dir", cfg.DataDir, "err", err)
	} else {
		if cards, err = gdStore.LoadCards(); err != nil {
			log.Warn("card load failed", "err", err)
		}
		if spells, err = gdStore.LoadSpells(); err != nil {
			log.Warn("spell load failed", "err", err)
		}
		if fighterCards, err = gdStore.LoadFighterCards(); err != nil {
			log.Warn("fighter-card load failed", "err", err)
		}
		if summonings, err = gdStore.LoadSummonings(); err != nil {
			log.Warn("summoning load failed", "err", err)
		}
		if staticEffects, err = gdStore.LoadStaticEffects(); err != nil {
			log.Warn("static-effect load failed", "err", err)
		}
		if challengeDefs, err = gdStore.LoadChallenges(); err != nil {
			log.Warn("challenge load failed", "err", err)
		}
		if events, err = gdStore.LoadEvents(); err != nil {
			log.Warn("event-card load failed", "err", err)
		}
		if cardSets, err = gdStore.LoadCardSets(); err != nil {
			log.Warn("card-set load failed", "err", err)
		}
		if conditions, err = gdStore.LoadConditions(); err != nil {
			log.Warn("fighter-condition load failed", "err", err)
		}
		log.Info("game data loaded", "cards", cardsLen(cards), "spells", spellsLen(spells),
			"fighterCards", fighterCardsLen(fighterCards), "summonings", summoningsLen(summonings),
			"staticEffects", staticEffectsLen(staticEffects), "challenges", challengeDefs.Len(),
			"eventCards", events.Len(), "cardSets", cardSets.Len(),
			"conditions", conditions.Len())
	}

	ctx, stop := signal.NotifyContext(context.Background(),
		syscall.SIGINT, syscall.SIGTERM)
	defer stop()

	deps := &game.Deps{
		Store:         st,
		World:         game.NewRegistry(cfg.World.AoIRadius),
		Cards:         cards,
		Exchanges:     game.NewExchangeManager(),
		Spells:        spells,
		FighterCards:  fighterCards,
		Summonings:    summonings,
		ChallengeDefs: challengeDefs,
		FightMaps:     fightMaps,
		Events:        events,
		CardSets:      cardSets,
		Conditions:    conditions,
		StaticEffects: staticEffects,
		Matchmaker:    game.NewMatchmaker(),
		Challenges:    game.NewChallengeManager(),
		Fights:        game.NewFightManager(),
		Sessions:      game.NewSessionRegistry(),
		Tournaments:   game.NewTournamentManager(),
		Log:           log,
	}
	srv := game.NewServer(cfg.Addr, deps)
	// Dev-only live packet-inject endpoint (no-op unless debug_addr is set).
	srv.StartDebugInject(cfg.DebugAddr)
	log.Info("DofusArena 2.70 server starting", "addr", cfg.Addr, "db", cfg.DB.Driver)

	if err := srv.ListenAndServe(ctx); err != nil {
		log.Error("server stopped", "err", err)
		os.Exit(1)
	}
	log.Info("server stopped cleanly")
}

func parseLevel(s string) slog.Level {
	switch strings.ToLower(s) {
	case "debug":
		return slog.LevelDebug
	case "warn":
		return slog.LevelWarn
	case "error":
		return slog.LevelError
	default:
		return slog.LevelInfo
	}
}

func cardsLen(c *gamedata.Cards) int {
	if c == nil {
		return 0
	}
	return c.Len()
}

func spellsLen(s *gamedata.Spells) int {
	if s == nil {
		return 0
	}
	return s.Len()
}

func fighterCardsLen(c *gamedata.FighterCards) int {
	if c == nil {
		return 0
	}
	return c.Len()
}

func summoningsLen(s *gamedata.Summonings) int {
	if s == nil {
		return 0
	}
	return s.Len()
}

func staticEffectsLen(s *gamedata.StaticEffects) int {
	if s == nil {
		return 0
	}
	return s.Len()
}
