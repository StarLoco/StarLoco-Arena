// Package config loads and validates the server configuration from a YAML
// file with environment-variable overrides (12-factor style).
//
// See go-server/docs/06-config-and-ops.md for the full schema reference.
package config

import (
	"fmt"
	"strings"
	"time"

	"github.com/knadh/koanf/parsers/yaml"
	"github.com/knadh/koanf/providers/env"
	"github.com/knadh/koanf/providers/file"
	"github.com/knadh/koanf/providers/structs"
	"github.com/knadh/koanf/v2"
)

// envPrefix is the prefix used for environment-variable overrides, e.g.
// ARENA_DATABASE_DSN overrides database.dsn.
const envPrefix = "ARENA_"

// Config is the root configuration structure.
type Config struct {
	Server   ServerConfig   `koanf:"server"`
	Database DatabaseConfig `koanf:"database"`
	GameData GameDataConfig `koanf:"gamedata"`
	Logging  LoggingConfig  `koanf:"logging"`
	Combat   CombatConfig   `koanf:"combat"`
	Web      WebConfig      `koanf:"web"`
}

// WebConfig configures the account web portal (register/login, per-user
// data view, admin console + impersonation). See docs/10-web-portal.md.
//
// The portal shares internal/service and the database with the game server.
// Enabled controls whether app.New starts it inside the game process (handy
// for single-command local dev); the standalone cmd/web binary starts it
// regardless of this flag so production can run the two as separate
// processes.
type WebConfig struct {
	// Enabled starts the portal alongside the game listener when the game
	// server boots (cmd/server / app.Serve). Default false so the game
	// server stays lean unless explicitly opted in.
	Enabled bool `koanf:"enabled"`
	// ListenAddr is the HTTP bind address for the portal, e.g. ":8080".
	ListenAddr string `koanf:"listen_addr"`
	// SessionSecret is the HMAC key used to sign session cookies. MUST be
	// set to a strong random value in production; an empty value causes a
	// random ephemeral key to be generated at startup (sessions won't
	// survive a restart, and won't be shared across multiple web
	// instances). Validate() warns via error only when Enabled and the
	// value is present-but-weak; empty is tolerated for dev convenience.
	SessionSecret string `koanf:"session_secret"`
	// SecureCookies forces the Secure attribute on session cookies (set
	// true when the portal is served over HTTPS / behind a TLS-terminating
	// proxy). Default false so http://localhost dev works.
	SecureCookies bool `koanf:"secure_cookies"`
	// BaseURL is the public origin of the portal (e.g.
	// "https://arena.example.com"), used for absolute links; optional.
	BaseURL string `koanf:"base_url"`
}

// ServerConfig holds network/identity settings for the game server.
type ServerConfig struct {
	Name       string `koanf:"name"`
	ListenAddr string `koanf:"listen_addr"`
	// AdminAddr is the bind address for the admin/observability HTTP
	// server (see internal/adminhttp): /healthz, /debug/pprof/*, /stats.
	// Independently toggleable: set to "" to disable it entirely (e.g. a
	// production deployment that prefers to not expose it at all).
	AdminAddr string        `koanf:"admin_addr"`
	Beta      bool          `koanf:"beta"`
	Version   VersionConfig `koanf:"version"`
}

// VersionConfig identifies the protocol/client version this server expects,
// mirroring the constants formerly hardcoded in network/Network.java.
type VersionConfig struct {
	Major    byte   `koanf:"major"`
	Revision uint16 `koanf:"revision"`
	Build    string `koanf:"build"`
}

// DatabaseConfig configures the GORM connection. Driver selects the dialect;
// see internal/db for the supported set (sqlite, postgres, mysql).
type DatabaseConfig struct {
	Driver          string        `koanf:"driver"`
	DSN             string        `koanf:"dsn"`
	MaxOpenConns    int           `koanf:"max_open_conns"`
	MaxIdleConns    int           `koanf:"max_idle_conns"`
	ConnMaxLifetime time.Duration `koanf:"conn_max_lifetime"`
	ConnMaxIdleTime time.Duration `koanf:"conn_max_idle_time"`
}

// GameDataConfig points at the directory containing the legacy .dat files
// (cards.dat, spells.dat, etc.) and controls lazy vs eager loading.
type GameDataConfig struct {
	Dir       string `koanf:"dir"`
	WarmCache bool   `koanf:"warm_cache"`
}

// LoggingConfig configures the zerolog logger.
type LoggingConfig struct {
	Level  string `koanf:"level"`
	Format string `koanf:"format"`
	// TracePackets, when true, logs every inbound and outbound wire packet
	// (opcode + hex payload) at info level. Extremely verbose -- intended
	// for debugging protocol/format issues against a real client, not for
	// production. Can also be toggled via the --trace-packets CLI flag.
	TracePackets bool `koanf:"trace_packets"`
	// Dir, when non-empty, additionally writes every log line to a fresh
	// timestamped file in that directory each run (the newest file = the
	// latest run's full log), so packet traces can be read from disk
	// instead of copy-pasting the console. Relative to the working dir.
	Dir string `koanf:"dir"`
}

// CombatConfig configures the turn-based fight clocks.
type CombatConfig struct {
	TurnClock         time.Duration `koanf:"turn_clock"`
	PresentationClock time.Duration `koanf:"presentation_clock"`
	PlacementClock    time.Duration `koanf:"placement_clock"`
	ObservationClock  time.Duration `koanf:"observation_clock"`
	// ActionSettle is the minimum delay enforced between a fighter's last
	// animated cast (spell/card/close-combat) and processing their
	// FIGHTER_TURN_END, so the client finishes playing the cast's action
	// group before the turn ends (avoids the "cast then end-turn -> stuck
	// fighter" freeze). See combat.Clocks.ActionSettle.
	ActionSettle time.Duration `koanf:"action_settle"`
	// MatchReadyClock/PlacementReadyClock configure the two pre-fight
	// forced-progress timers that run *before* any combat.Fight actor
	// exists (see world.Duel / dispatch/handlers_fight.go): matching the
	// client's own cosmetic 20s ready-countdown UI, these force a duel to
	// proceed even if one or both coaches never explicitly acknowledge
	// readiness, rather than stalling forever (see
	// docs/08-java-parity-roadmap.md's write-up on this fix).
	MatchReadyClock     time.Duration `koanf:"match_ready_clock"`
	PlacementReadyClock time.Duration `koanf:"placement_ready_clock"`
}

// Default returns a Config populated with sane defaults for local
// development (SQLite, console logging, permissive beta flag).
func Default() Config {
	return Config{
		Server: ServerConfig{
			Name:       "DofusArena",
			ListenAddr: ":443",
			AdminAddr:  "127.0.0.1:9090",
			Beta:       true,
			Version: VersionConfig{
				Major:    2,
				Revision: 4,
				Build:    "7025",
			},
		},
		Database: DatabaseConfig{
			Driver: "sqlite",
			// NOTE: glebarez/go-sqlite (unlike mattn/go-sqlite3) only
			// recognizes "_pragma"/"_time_format"/"_txlock" DSN query
			// params -- "_journal_mode"/"_foreign_keys" shorthand params
			// are silently ignored, not errors. Always use the explicit
			// "_pragma=name(value)" form. See internal/testutil/db.go for
			// the same note.
			DSN:             "file:arena.db?_pragma=journal_mode(WAL)&_pragma=foreign_keys(1)",
			MaxOpenConns:    1,
			MaxIdleConns:    1,
			ConnMaxLifetime: 0,
			ConnMaxIdleTime: 0,
		},
		GameData: GameDataConfig{
			Dir:       "./data",
			WarmCache: false,
		},
		Logging: LoggingConfig{
			Level:  "info",
			Format: "console",
		},
		Combat: CombatConfig{
			TurnClock:           30 * time.Second,
			PresentationClock:   20 * time.Second,
			PlacementClock:      30 * time.Second,
			ObservationClock:    10 * time.Second,
			MatchReadyClock:     20 * time.Second,
			PlacementReadyClock: 20 * time.Second,
			ActionSettle:        600 * time.Millisecond,
		},
		Web: WebConfig{
			Enabled:       false,
			ListenAddr:    ":8080",
			SessionSecret: "",
			SecureCookies: false,
			BaseURL:       "",
		},
	}
}

// Load reads configuration from defaults, then an optional YAML file at
// path (skipped silently if it doesn't exist), then environment variables
// prefixed with ARENA_ (highest priority). Nested keys use double
// underscores, e.g. ARENA_DATABASE__MAX_OPEN_CONNS.
func Load(path string) (Config, error) {
	k := koanf.New(".")

	def := Default()
	if err := k.Load(structs.Provider(def, "koanf"), nil); err != nil {
		return Config{}, fmt.Errorf("config: load defaults: %w", err)
	}

	if path != "" {
		if err := k.Load(file.Provider(path), yaml.Parser()); err != nil {
			return Config{}, fmt.Errorf("config: load file %q: %w", path, err)
		}
	}

	if err := k.Load(env.Provider(envPrefix, ".", func(s string) string {
		s = strings.TrimPrefix(s, envPrefix)
		s = strings.ToLower(s)
		s = strings.ReplaceAll(s, "__", ".")
		return s
	}), nil); err != nil {
		return Config{}, fmt.Errorf("config: load env: %w", err)
	}

	var cfg Config
	if err := k.Unmarshal("", &cfg); err != nil {
		return Config{}, fmt.Errorf("config: unmarshal: %w", err)
	}

	if err := cfg.Validate(); err != nil {
		return Config{}, fmt.Errorf("config: validate: %w", err)
	}

	return cfg, nil
}

// Validate checks that required fields are present and internally
// consistent. It never mutates the receiver.
func (c Config) Validate() error {
	if c.Server.ListenAddr == "" {
		return fmt.Errorf("server.listen_addr must not be empty")
	}
	switch c.Database.Driver {
	case "sqlite", "postgres", "mysql":
	default:
		return fmt.Errorf("database.driver must be one of sqlite|postgres|mysql, got %q", c.Database.Driver)
	}
	if c.Database.DSN == "" {
		return fmt.Errorf("database.dsn must not be empty")
	}
	if c.GameData.Dir == "" {
		return fmt.Errorf("gamedata.dir must not be empty")
	}
	if c.Web.Enabled && c.Web.ListenAddr == "" {
		return fmt.Errorf("web.listen_addr must not be empty when web.enabled is true")
	}
	return nil
}
