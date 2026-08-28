// Package config loads server configuration from a YAML file with environment
// variable overrides (12-factor friendly for containerized deployments).
//
// On first run the server writes a fully commented config file (see
// EnsureFile), so an operator who downloaded a release binary has a documented
// starting point without reading any docs.
package config

import (
	_ "embed"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"strings"

	"gopkg.in/yaml.v3"
)

// Template is the annotated config file written on first run. It documents
// every setting; keep it in sync with Config (config_template_test.go fails if
// a field is added here without a matching key in the template).
//
//go:embed config.template.yaml
var Template string

// defaultClientDownloadURL is StarLoco's own mirror of the retail client,
// shown on the web portal so a player who found this project on GitHub has
// somewhere to get the game itself. It is never committed to this repository
// (client/compiled/ stays git-ignored — see AGENTS.md constraint 4); only
// linked. An operator running their own fork can point web.client_download_url
// at their own mirror, or blank it to hide the link entirely.
//
// If this link ever needs to move (Mega links do get taken down), update it
// here, in .goreleaser.yaml's release.footer, and in the root README.md.
const defaultClientDownloadURL = "https://mega.nz/file/tqwUTaJS#-WhMChKA60e2FBXVSsCtgKOh91x4gA4sRI7wdFDHEk4"

// Config is the full server configuration.
type Config struct {
	// Addr is the TCP listen address for the game client (default 0.0.0.0:5555).
	Addr string `yaml:"addr"`
	// DataDir holds the client game data (data.bdat + indexes.bdat).
	DataDir string `yaml:"data_dir"`
	// LogLevel: debug | info | warn | error.
	LogLevel string `yaml:"log_level"`
	// DebugAddr, when non-empty, starts a DEV-ONLY loopback HTTP endpoint for
	// live packet injection (see internal/game/debug.go). Leave empty in prod.
	DebugAddr string `yaml:"debug_addr"`

	Web         WebConfig         `yaml:"web"`
	UpdateCheck UpdateCheckConfig `yaml:"update_check"`
	DB          DBConfig          `yaml:"db"`
	World       WorldConfig       `yaml:"world"`
}

// WebConfig configures the browser-facing portal where players register their
// own accounts.
type WebConfig struct {
	// Enabled serves the portal at all.
	Enabled bool `yaml:"enabled"`
	// Addr is the portal listen address. A port of 0 means "pick one": the
	// portal tries 80 first, then a short ladder of common alternatives, then
	// any free port (see internal/web.Listen).
	Addr string `yaml:"addr"`
	// RegistrationEnabled allows visitors to create accounts themselves.
	RegistrationEnabled bool `yaml:"registration_enabled"`
	// PublicHost overrides the host shown to players as the game-server address
	// (e.g. "arena.example.com"). Empty = use the host the visitor requested.
	PublicHost string `yaml:"public_host"`
	// MinLoginLength / MinPasswordLength gate new sign-ups.
	MinLoginLength    int `yaml:"min_login_length"`
	MinPasswordLength int `yaml:"min_password_length"`
	// ClientDownloadURL, when set, is shown on the portal as a link to get the
	// DofusArena 2.70 client itself — this server never bundles or commits
	// that (see AGENTS.md constraint 4), only links to it. Empty hides the
	// link entirely.
	ClientDownloadURL string `yaml:"client_download_url"`
	// ServerName brands the portal: it is the name in the header, the page
	// titles and the landing page's headline. Empty falls back to
	// "DofusArena".
	ServerName string `yaml:"server_name"`
	// SessionSecret keys the HMAC that signs login-session cookies and CSRF
	// tokens. Leave it empty and the server invents a random one at startup,
	// which is fine for a local game but logs everybody out on every restart.
	// Set it to a long random string to keep sessions across restarts.
	SessionSecret string `yaml:"session_secret"`
	// SecureCookies marks session cookies "Secure", so browsers only ever send
	// them over HTTPS. Turn it on once the portal is behind TLS; leaving it on
	// for a plain-HTTP server makes logging in silently impossible, because the
	// browser accepts the cookie and then refuses to send it back.
	SecureCookies bool `yaml:"secure_cookies"`
	// DiscordURL is where /discord sends visitors. The game client's login
	// screen has a "Rejoindre le Discord" plaque pointing at /<lang>/discord,
	// so this can be filled in (or changed) without touching the client the
	// players already downloaded.
	DiscordURL string `yaml:"discord_url"`
	// BugReportsEnabled accepts submissions from the retail client's own bug
	// dialog at POST /<lang>/bug-report, and shows them in the admin console.
	// The endpoint is necessarily UNAUTHENTICATED - the client sends no
	// credentials with it - so it is rate limited, size capped, and can be
	// turned off entirely here.
	BugReportsEnabled bool `yaml:"bug_reports_enabled"`
	// BugReportAddr, when set, starts a SECOND listener that serves only the
	// bug-report endpoint over plain http (e.g. "0.0.0.0:80").
	//
	// This exists for one reason: the retail client bundles Java 1.6.0_07
	// (2008), which speaks no TLS newer than 1.0, so its bug dialog cannot
	// reach an https endpoint at all - it fails with "handshake_failure", and
	// the client claims success to the player before it even connects, so the
	// failure is invisible in game.
	//
	// Only that one route is served here; the portal, its login form and the
	// admin console are NOT reachable on this port. Leave it empty unless the
	// main site is https-only and you want in-game reports to work anyway.
	BugReportAddr string `yaml:"bug_report_addr"`
	// BugReportDir is where submitted screenshots are written. Relative paths
	// resolve against the working directory, like the database. Screenshots are
	// full-size JPEGs and are deliberately kept OUT of the database: MySQL's
	// default BLOB caps at 64 KB, and anything in the database lands in every
	// backup. Empty disables screenshot storage (reports are still recorded).
	BugReportDir string `yaml:"bug_report_dir"`
	// TrustedProxies lists the peer addresses whose X-Forwarded-For header may
	// be believed, as IPs or CIDRs ("127.0.0.1", "10.0.0.0/8"). It is EMPTY by
	// default, and while it is empty no proxy header is trusted at all — the
	// rate limiter keys on the real peer address, which is the only safe
	// reading when anyone can dial the port directly and forge the header.
	//
	// Set it only when a reverse proxy you control terminates every request and
	// rewrites X-Forwarded-For itself (nginx's proxy_set_header). Without it,
	// a proxied portal sees every visitor as the proxy, so the per-IP sign-up
	// and sign-in limits silently become one shared, server-wide bucket.
	TrustedProxies []string `yaml:"trusted_proxies"`
}

// UpdateCheckConfig configures the startup "a newer release exists" notice.
type UpdateCheckConfig struct {
	// Enabled performs one HTTPS GET against the public GitHub releases API at
	// startup. Nothing is downloaded or installed, and no data about this
	// server is transmitted.
	Enabled bool `yaml:"enabled"`
	// TimeoutSeconds bounds that request. The check is asynchronous and never
	// delays startup.
	TimeoutSeconds int `yaml:"timeout_seconds"`
}

// WorldConfig tunes overworld behavior.
type WorldConfig struct {
	// AoIRadius is the area-of-interest radius in cells: overworld events (chat,
	// movement, spawn) only reach coaches within this distance of the source, so
	// a message never fans out to the whole map. 0 disables scoping (broadcast
	// to everyone — not recommended at scale).
	AoIRadius int `yaml:"aoi_radius"`
	// MatchBand is the strength gap two coaches may have and still be paired by
	// the matchmaker when they FIRST queue. It widens by MatchBandGrowth for
	// every second a searcher has waited, so nobody is stuck for lack of a
	// same-rated opponent. 0 disables the check entirely, which is the sensible
	// setting for a small private server where any opponent beats no opponent.
	MatchBand int `yaml:"match_band"`
	// MatchBandGrowth is how much MatchBand widens per second of waiting. 0
	// makes the band fixed, which on a quiet server can mean never matching.
	MatchBandGrowth int `yaml:"match_band_growth"`
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

// Default returns the configuration a fresh install runs with. It must stay in
// agreement with the values documented in config.template.yaml.
func Default() Config {
	return Config{
		Addr:     "0.0.0.0:5555",
		DataDir:  "data",
		LogLevel: "info",
		Web: WebConfig{
			Enabled:             true,
			Addr:                "0.0.0.0:0", // 0 = auto-select, see internal/web
			RegistrationEnabled: true,
			MinLoginLength:      3,
			MinPasswordLength:   6,
			ClientDownloadURL:   defaultClientDownloadURL,
			BugReportsEnabled:   true,
			BugReportDir:        "bugreports",
		},
		UpdateCheck: UpdateCheckConfig{
			Enabled:        true,
			TimeoutSeconds: 5,
		},
		DB: DBConfig{
			Driver:       "sqlite",
			DSN:          "arena.db",
			MaxOpenConns: 1, // sqlite is single-writer
			MaxIdleConns: 1,
		},
		World: WorldConfig{
			AoIRadius: 75, // cells; overworld events reach only nearby coaches
			// A 300-point opening band widening by 150/s means two coaches 1500
			// apart still meet after 8 seconds, so fairness never becomes a
			// deadlock on a server with few players online.
			MatchBand:       300,
			MatchBandGrowth: 150,
		},
	}
}

// EnsureFile writes the commented default config to path when no file is there
// yet, and reports whether it created one. A path pointing at an existing file
// is left untouched — the operator's edits are never overwritten.
//
// Failing to write is not fatal to the caller: the server runs fine on
// defaults, it just cannot offer the file to edit.
func EnsureFile(path string) (created bool, err error) {
	if path == "" {
		return false, nil
	}
	if _, err := os.Stat(path); err == nil {
		return false, nil // already there
	} else if !os.IsNotExist(err) {
		return false, fmt.Errorf("config: stat %q: %w", path, err)
	}

	if dir := filepath.Dir(path); dir != "" && dir != "." {
		if err := os.MkdirAll(dir, 0o755); err != nil {
			return false, fmt.Errorf("config: create %q: %w", dir, err)
		}
	}
	// O_EXCL so two servers starting at once cannot clobber each other.
	f, err := os.OpenFile(path, os.O_WRONLY|os.O_CREATE|os.O_EXCL, 0o644)
	if err != nil {
		if os.IsExist(err) {
			return false, nil // lost the race; the other file is just as good
		}
		return false, fmt.Errorf("config: write %q: %w", path, err)
	}
	defer func() { _ = f.Close() }()
	if _, err := f.WriteString(Template); err != nil {
		return false, fmt.Errorf("config: write %q: %w", path, err)
	}
	return true, nil
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
	if v, ok := envBool("ARENA_WEB_ENABLED"); ok {
		c.Web.Enabled = v
	}
	if v := os.Getenv("ARENA_WEB_ADDR"); v != "" {
		c.Web.Addr = v
	}
	if v, ok := envBool("ARENA_WEB_REGISTRATION_ENABLED"); ok {
		c.Web.RegistrationEnabled = v
	}
	if v := os.Getenv("ARENA_WEB_PUBLIC_HOST"); v != "" {
		c.Web.PublicHost = v
	}
	if v := os.Getenv("ARENA_WEB_CLIENT_DOWNLOAD_URL"); v != "" {
		c.Web.ClientDownloadURL = v
	}
	// Deliberately env-overridable: a session secret is the one web setting
	// that is a real credential, and operators should be able to inject it
	// without writing it into a config file that ends up in a backup.
	if v := os.Getenv("ARENA_WEB_SESSION_SECRET"); v != "" {
		c.Web.SessionSecret = v
	}
	if v, ok := envBool("ARENA_WEB_SECURE_COOKIES"); ok {
		c.Web.SecureCookies = v
	}
	if v := os.Getenv("ARENA_WEB_DISCORD_URL"); v != "" {
		c.Web.DiscordURL = v
	}
	if v, ok := envBool("ARENA_WEB_BUG_REPORTS_ENABLED"); ok {
		c.Web.BugReportsEnabled = v
	}
	if v := os.Getenv("ARENA_WEB_BUG_REPORT_DIR"); v != "" {
		c.Web.BugReportDir = v
	}
	if v := os.Getenv("ARENA_WEB_BUG_REPORT_ADDR"); v != "" {
		c.Web.BugReportAddr = v
	}
	// Comma-separated, so a container can set it without a config file.
	if v := os.Getenv("ARENA_WEB_TRUSTED_PROXIES"); v != "" {
		parts := strings.Split(v, ",")
		list := make([]string, 0, len(parts))
		for _, p := range parts {
			if p = strings.TrimSpace(p); p != "" {
				list = append(list, p)
			}
		}
		c.Web.TrustedProxies = list
	}
	if v, ok := envBool("ARENA_UPDATE_CHECK_ENABLED"); ok {
		c.UpdateCheck.Enabled = v
	}
	if v := os.Getenv("ARENA_DB_DRIVER"); v != "" {
		c.DB.Driver = v
	}
	if v := os.Getenv("ARENA_DB_DSN"); v != "" {
		c.DB.DSN = v
	}
	if v, ok := envInt("ARENA_DB_MAX_OPEN_CONNS"); ok {
		c.DB.MaxOpenConns = v
	}
	if v, ok := envInt("ARENA_DB_MAX_IDLE_CONNS"); ok {
		c.DB.MaxIdleConns = v
	}
	if v, ok := envInt("ARENA_AOI_RADIUS"); ok {
		c.World.AoIRadius = v
	}
}

func envInt(key string) (int, bool) {
	v := os.Getenv(key)
	if v == "" {
		return 0, false
	}
	n, err := strconv.Atoi(v)
	if err != nil {
		return 0, false
	}
	return n, true
}

func envBool(key string) (bool, bool) {
	v := os.Getenv(key)
	if v == "" {
		return false, false
	}
	b, err := strconv.ParseBool(v)
	if err != nil {
		return false, false
	}
	return b, true
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
	if c.Web.Enabled && c.Web.Addr == "" {
		return fmt.Errorf("config: web.addr is required when web.enabled is true")
	}
	return nil
}
