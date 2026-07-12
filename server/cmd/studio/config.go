package main

import (
	"encoding/json"
	"os"
	"path/filepath"
)

// studioConfig is the small user-preferences file the studio persists next to
// its executable (so it's per-user and not committed to the repo). It
// remembers the chosen language and the last-used data/client directories so
// the app reopens where you left off.
type studioConfig struct {
	Language  string `json:"language"`
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
// failing to persist a preference must never break the app.
func (a *App) saveConfig() {
	c := studioConfig{
		Language:  a.lang,
		DataDir:   a.paths.DataDir,
		ClientDir: a.paths.ClientDir,
	}
	if raw, err := json.MarshalIndent(c, "", "  "); err == nil {
		_ = os.WriteFile(configPath(), raw, 0o644)
	}
}

// SupportedLanguages is the fixed set the client ships (texts_<lang>.properties).
var SupportedLanguages = []LanguageOption{
	{Code: "en", Label: "English"},
	{Code: "fr", Label: "Français"},
}

// LanguageOption is one selectable UI language.
type LanguageOption struct {
	Code  string `json:"code"`
	Label string `json:"label"`
}

// GetLanguage returns the active language code and the list of options.
func (a *App) GetLanguage() LanguageState {
	if a.lang == "" {
		a.lang = "en"
	}
	return LanguageState{Current: a.lang, Options: SupportedLanguages}
}

// LanguageState is the payload for the language dropdown.
type LanguageState struct {
	Current string           `json:"current"`
	Options []LanguageOption `json:"options"`
}

// SetLanguage switches the active name/description language, drops the cached
// i18n table so the next lookup reloads it, and persists the choice.
func (a *App) SetLanguage(code string) LanguageState {
	valid := false
	for _, o := range SupportedLanguages {
		if o.Code == code {
			valid = true
			break
		}
	}
	if !valid {
		code = "en"
	}
	a.lang = code
	// Invalidate the cached i18n store so ensureI18n reloads for the new lang.
	a.i18nMu.Lock()
	a.i18n = nil
	a.i18nMu.Unlock()
	a.saveConfig()
	return a.GetLanguage()
}
