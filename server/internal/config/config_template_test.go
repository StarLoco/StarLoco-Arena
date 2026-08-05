package config

import (
	"os"
	"path/filepath"
	"reflect"
	"regexp"
	"strings"
	"testing"
)

// TestTemplateDocumentsEveryField is the anti-drift guard: the shipped config
// file is the only documentation most operators will ever read, so a field
// added to Config without a corresponding key in config.template.yaml is a bug.
// The key may be active or commented out — it just has to be there.
func TestTemplateDocumentsEveryField(t *testing.T) {
	for _, key := range yamlKeys(reflect.TypeOf(Config{})) {
		// Matches "key:", "# key:" and any indentation, but not a bare mention
		// inside prose.
		re := regexp.MustCompile(`(?m)^\s*#?\s*` + regexp.QuoteMeta(key) + `\s*:`)
		if !re.MatchString(Template) {
			t.Errorf("config.template.yaml has no %q key — a new Config field must be "+
				"documented there, since that file is what operators actually read", key)
		}
	}
}

// TestTemplateIsValid: the file we hand people must itself load cleanly and
// pass validation, or first-run is broken for everyone.
func TestTemplateIsValid(t *testing.T) {
	path := filepath.Join(t.TempDir(), "config.yaml")
	if err := os.WriteFile(path, []byte(Template), 0o644); err != nil {
		t.Fatalf("write: %v", err)
	}
	cfg, err := Load(path)
	if err != nil {
		t.Fatalf("shipped template does not load: %v", err)
	}
	// Spot-check that the documented values are the ones actually parsed, i.e.
	// the template's active (uncommented) settings agree with Default().
	def := Default()
	if cfg.Addr != def.Addr {
		t.Errorf("template addr = %q, Default() = %q", cfg.Addr, def.Addr)
	}
	if cfg.DB.Driver != def.DB.Driver || cfg.DB.DSN != def.DB.DSN {
		t.Errorf("template db = %+v, Default() = %+v", cfg.DB, def.DB)
	}
	if cfg.Web.Addr != def.Web.Addr || !cfg.Web.Enabled {
		t.Errorf("template web = %+v, Default() = %+v", cfg.Web, def.Web)
	}
	if !cfg.UpdateCheck.Enabled {
		t.Error("template should ship with update_check.enabled: true")
	}
	if cfg.LogLevel != def.LogLevel {
		t.Errorf("template log_level = %q, Default() = %q", cfg.LogLevel, def.LogLevel)
	}
	// debug_addr must stay commented out: it is an unauthenticated control
	// channel and must never be on by default.
	if cfg.DebugAddr != "" {
		t.Errorf("template must not enable debug_addr, got %q", cfg.DebugAddr)
	}
}

func TestEnsureFileCreatesThenLeavesAlone(t *testing.T) {
	dir := t.TempDir()
	path := filepath.Join(dir, "sub", "config.yaml") // also exercises MkdirAll

	created, err := EnsureFile(path)
	if err != nil {
		t.Fatalf("EnsureFile: %v", err)
	}
	if !created {
		t.Fatal("first call should report created=true")
	}
	got, err := os.ReadFile(path)
	if err != nil {
		t.Fatalf("read back: %v", err)
	}
	if string(got) != Template {
		t.Error("written file does not match the embedded template")
	}

	// An operator's edits must survive a restart.
	edited := string(got) + "\n# my note\n"
	if err := os.WriteFile(path, []byte(edited), 0o644); err != nil {
		t.Fatalf("edit: %v", err)
	}
	created, err = EnsureFile(path)
	if err != nil {
		t.Fatalf("EnsureFile (2nd): %v", err)
	}
	if created {
		t.Error("second call should report created=false")
	}
	again, _ := os.ReadFile(path)
	if string(again) != edited {
		t.Error("EnsureFile overwrote an existing config file")
	}
}

func TestEnsureFileEmptyPathIsNoOp(t *testing.T) {
	created, err := EnsureFile("")
	if err != nil || created {
		t.Errorf("EnsureFile(\"\") = (%v, %v), want (false, nil)", created, err)
	}
}

// yamlKeys collects the yaml tag of every field in t, recursing into nested
// structs so section keys and their children are all covered.
func yamlKeys(t reflect.Type) []string {
	var keys []string
	for i := 0; i < t.NumField(); i++ {
		f := t.Field(i)
		tag := strings.Split(f.Tag.Get("yaml"), ",")[0]
		if tag == "" || tag == "-" {
			continue
		}
		keys = append(keys, tag)
		if f.Type.Kind() == reflect.Struct {
			keys = append(keys, yamlKeys(f.Type)...)
		}
	}
	return keys
}
