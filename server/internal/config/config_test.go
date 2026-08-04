package config

import (
	"os"
	"path/filepath"
	"testing"
)

func TestDefaultIsSQLite(t *testing.T) {
	c := Default()
	if c.DB.Driver != "sqlite" {
		t.Errorf("default driver = %q, want sqlite", c.DB.Driver)
	}
	if err := c.validate(); err != nil {
		t.Errorf("default config invalid: %v", err)
	}
}

func TestLoadYAML(t *testing.T) {
	dir := t.TempDir()
	path := filepath.Join(dir, "c.yaml")
	os.WriteFile(path, []byte(`
addr: "0.0.0.0:9999"
log_level: "warn"
db:
  driver: "postgres"
  dsn: "host=x dbname=y"
  max_open_conns: 25
`), 0o644)

	c, err := Load(path)
	if err != nil {
		t.Fatalf("Load: %v", err)
	}
	if c.Addr != "0.0.0.0:9999" {
		t.Errorf("addr = %q", c.Addr)
	}
	if c.DB.Driver != "postgres" || c.DB.MaxOpenConns != 25 {
		t.Errorf("db = %+v", c.DB)
	}
}

func TestEnvOverride(t *testing.T) {
	t.Setenv("ARENA_ADDR", "1.2.3.4:5000")
	t.Setenv("ARENA_DB_DRIVER", "mysql")
	t.Setenv("ARENA_DB_DSN", "u:p@tcp(h:3306)/d")

	c, err := Load("") // no file, defaults + env
	if err != nil {
		t.Fatalf("Load: %v", err)
	}
	if c.Addr != "1.2.3.4:5000" {
		t.Errorf("env addr override failed: %q", c.Addr)
	}
	if c.DB.Driver != "mysql" {
		t.Errorf("env driver override failed: %q", c.DB.Driver)
	}
}

func TestMissingFileUsesDefaults(t *testing.T) {
	c, err := Load(filepath.Join(t.TempDir(), "nope.yaml"))
	if err != nil {
		t.Fatalf("missing file should not error: %v", err)
	}
	if c.DB.Driver != "sqlite" {
		t.Errorf("expected default sqlite, got %q", c.DB.Driver)
	}
}

func TestBadDriverRejected(t *testing.T) {
	c := Default()
	c.DB.Driver = "oracle"
	if err := c.validate(); err == nil {
		t.Error("expected error for unknown driver")
	}
}
