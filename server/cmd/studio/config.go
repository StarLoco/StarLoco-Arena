package main

import (
	"encoding/json"
	"os"
	"path/filepath"
)

// studioConfig is the small user-preferences file the studio persists next to
// its executable (so it's per-user and not committed to the repo). It remembers
// the last-used data + client directories so the app reopens where you left off.
type studioConfig struct {
	DataDir   string `json:"dataDir"`
	ClientDir string `json:"clientDir"`
}

// configPath returns the path to studio-config.json beside the executable,
// falling back to the working directory if the executable path is unknown.
func configPath() string {
	if exe, err := os.Executable(); err == nil {
		return filepath.Join(filepath.Dir(exe), "studio-config.json")
	}
	return "studio-config.json"
}

// loadConfig reads studio-config.json, returning a zero-value config if it
// doesn't exist or can't be parsed (never fatal -- preferences are optional).
func loadConfig() studioConfig {
	var c studioConfig
	raw, err := os.ReadFile(configPath())
	if err != nil {
		return c
	}
	_ = json.Unmarshal(raw, &c)
	return c
}

// saveConfig writes the current preferences. Errors are ignored on purpose:
// failing to persist a preference must never break the app. Caller holds a.mu.
func (a *App) saveConfig() {
	c := studioConfig{DataDir: a.paths.DataDir, ClientDir: a.paths.ClientDir}
	if raw, err := json.MarshalIndent(c, "", "  "); err == nil {
		_ = os.WriteFile(configPath(), raw, 0o644)
	}
}
