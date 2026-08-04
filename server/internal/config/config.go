// Package config loads server configuration from a YAML file with environment
// variable overrides (12-factor friendly for containerized deployments).
package config

import (
	"fmt"
	"os"
	"strconv"
	"strings"

	"gopkg.in/yaml.v3"
)

// Config is the full server configuration.
type Config struct {
	// Addr is the TCP listen address (retail client defaults to 127.0.0.1:5555).
	Addr string `yaml:"addr"`
	// DataDir holds the client game data (data.bdat + indexes.bdat).
	DataDir string `yaml:"data_dir"`
	// LogLevel: debug | info | warn | error.
	LogLevel string `yaml:"log_level"`
	// DebugAddr, when non-empty, starts a DEV-ONLY loopback HTTP endpoint for
	// live packet injection (see internal/game/debug.go). Leave empty in prod.
	DebugAddr string `yaml:"debug_addr"`

	DB    DBConfig    `yaml:"db"`
	World WorldConfig `yaml:"world"`
}

// WorldConfig tunes overworld behavior.
type WorldConfig struct {
	// AoIRadius is the area-of-interest radius in cells: overworld events (chat,
	// movement, spawn) only reach coaches within this distance of the source, so
	// a message never fans out to the whole map. 0 disables scoping (broadcast
	// to everyone — not recommended at scale).
	AoIRadius int `yaml:"aoi_radius"`
}

// DBConfig selects and configures the database backend.
type DBConfig struct {
	// Driver: sqlite | postgres | mysql.
	Driver string `yaml:"driver"`
	// DSN is the driver-specific connection string. For sqlite it's a file path.
	DSN string `yaml:"dsn"`
	// MaxOpenConns / MaxIdleConns tune the connection pool (ignored for sqlite,
	// which is single-writer).
	MaxOpenConns int `yaml:"max_open_conns"`
	MaxIdleConns int `yaml:"max_idle_conns"`
}

// Default returns a config suitable for local development (SQLite).
func Default() Config {
	return Config{
		Addr:     "127.0.0.1:5555",
		DataDir:  `data`,
		LogLevel: "debug",
		DB: DBConfig{
			Driver:       "sqlite",
			DSN:          "arena.db",
			MaxOpenConns: 1, // sqlite is single-writer
			MaxIdleConns: 1,
		},
		World: WorldConfig{
			AoIRadius: 75, // cells; overworld events reach only nearby coaches
		},
	}
}

// Load reads a YAML config file (if path is non-empty and exists), starting
// from Default(), then applies environment overrides. A missing file is not an
// error — defaults + env are used.
func Load(path string) (Config, error) {
	cfg := Default()

	if path != "" {
		data, err := os.ReadFile(path)
		if err == nil {
			if err := yaml.Unmarshal(data, &cfg); err != nil {
				return cfg, fmt.Errorf("config: parse %q: %w", path, err)
			}
		} else if !os.IsNotExist(err) {
			return cfg, fmt.Errorf("config: read %q: %w", path, err)
		}
	}

	cfg.applyEnv()
	return cfg, cfg.validate()
}

// applyEnv overrides fields from ARENA_* environment variables (for Docker).
func (c *Config) applyEnv() {
	if v := os.Getenv("ARENA_ADDR"); v != "" {
		c.Addr = v
	}
	if v := os.Getenv("ARENA_DATA_DIR"); v != "" {
		c.DataDir = v
	}
	if v := os.Getenv("ARENA_LOG_LEVEL"); v != "" {
		c.LogLevel = v
	}
	if v := os.Getenv("ARENA_DEBUG_ADDR"); v != "" {
		c.DebugAddr = v
	}
	if v := os.Getenv("ARENA_DB_DRIVER"); v != "" {
		c.DB.Driver = v
	}
	if v := os.Getenv("ARENA_DB_DSN"); v != "" {
		c.DB.DSN = v
	}
	if v := os.Getenv("ARENA_DB_MAX_OPEN_CONNS"); v != "" {
		if n, err := strconv.Atoi(v); err == nil {
			c.DB.MaxOpenConns = n
		}
	}
	if v := os.Getenv("ARENA_DB_MAX_IDLE_CONNS"); v != "" {
		if n, err := strconv.Atoi(v); err == nil {
			c.DB.MaxIdleConns = n
		}
	}
	if v := os.Getenv("ARENA_AOI_RADIUS"); v != "" {
		if n, err := strconv.Atoi(v); err == nil {
			c.World.AoIRadius = n
		}
	}
}

func (c *Config) validate() error {
	switch strings.ToLower(c.DB.Driver) {
	case "sqlite", "postgres", "mysql":
	default:
		return fmt.Errorf("config: unknown db driver %q (want sqlite|postgres|mysql)", c.DB.Driver)
	}
	if c.DB.DSN == "" {
		return fmt.Errorf("config: db.dsn is required")
	}
	if c.Addr == "" {
		return fmt.Errorf("config: addr is required")
	}
	return nil
}
