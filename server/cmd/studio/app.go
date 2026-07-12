package main

import (
	"context"
	"fmt"
	"os"
	"path/filepath"
	"sync"

	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// App is the Wails-bound backend. Every exported method becomes callable
// from the frontend via the generated JS bindings. It owns the resolved
// data/asset directories and a lazily-loaded gamedata.Store built over the
// existing, byte-exact parsers in internal/gamedata.
type App struct {
	ctx context.Context

	// paths holds the currently-selected data & client directories.
	paths Paths

	// store is the gamedata repository set rooted at paths.DataDir. It is
	// rebuilt whenever the data directory changes (SetDataDir).
	store *gamedata.Store

	// jars memoizes opened client asset .jar (zip) readers, see assets.go
	// (Phase 2).
	jars jarCache

	// lang is the active UI/name language ("en" or "fr"); i18n is the
	// lazily-loaded name table for that language (see i18n.go). Persisted
	// via studio-config.json (config.go).
	lang   string
	i18nMu sync.Mutex
	i18n   *i18nStore

	// icons memoizes decoded gui.jar icon PNGs by "kind/id" (see icons.go).
	icons   map[string]string
	iconsMu sync.Mutex

	// gameIcons memoizes the atlas-cropped UI icons (elements/AP/MP/...) as a
	// name->dataURL map (see gameicons.go).
	gameIcons   map[string]string
	gameIconsMu sync.Mutex

	// elements memoizes the parsed elements.ade catalog for the map tile
	// renderer (see maprender.go).
	elements   map[int32]parser.ElementDef
	elementsMu sync.Mutex
}

// Paths records where the studio reads game data and client assets from.
type Paths struct {
	// DataDir is the directory containing cards.dat, spells.dat,
	// elements.ade, maps/, etc. (the repo's top-level data/ folder).
	DataDir string `json:"dataDir"`
	// ClientDir is the client-compiled/ directory whose
	// game/contents/*.jar archives hold the sprites/animations/etc.
	ClientDir string `json:"clientDir"`
	// DataDirValid / ClientDirValid report whether the directory exists
	// and looks like the thing we expect (a sniff, not a guarantee).
	DataDirValid   bool `json:"dataDirValid"`
	ClientDirValid bool `json:"clientDirValid"`
}

// NewApp constructs the backend with auto-detected paths (best-effort;
// the user can override them from the UI), then applies any persisted
// preferences (language + saved directories) from studio-config.json.
func NewApp() *App {
	a := &App{}
	a.paths = detectPaths()

	cfg := loadConfig()
	a.lang = cfg.Language
	if a.lang == "" {
		a.lang = "en"
	}
	// Prefer a saved data/client dir if it's still valid (lets the user pin
	// a non-adjacent location); otherwise keep auto-detection.
	if cfg.DataDir != "" && looksLikeDataDir(cfg.DataDir) {
		a.paths.DataDir = cfg.DataDir
		a.paths.DataDirValid = true
	}
	if cfg.ClientDir != "" && looksLikeClientDir(cfg.ClientDir) {
		a.paths.ClientDir = cfg.ClientDir
		a.paths.ClientDirValid = true
	}

	if a.paths.DataDirValid {
		a.store = gamedata.NewStore(a.paths.DataDir)
	}
	return a
}

// startup is the Wails OnStartup hook; it captures the runtime context
// used by dialog/event APIs in later phases.
func (a *App) startup(ctx context.Context) {
	a.ctx = ctx
}

// GetPaths returns the currently-resolved data & client directories and
// their validity, for the UI's status bar / settings panel.
func (a *App) GetPaths() Paths {
	return a.paths
}

// SetDataDir points the studio at a new data directory, revalidates it, and
// (if valid) rebuilds the gamedata store. Returns the updated Paths.
func (a *App) SetDataDir(dir string) Paths {
	a.paths.DataDir = dir
	a.paths.DataDirValid = looksLikeDataDir(dir)
	if a.paths.DataDirValid {
		a.store = gamedata.NewStore(dir)
	} else {
		a.store = nil
	}
	// Drop the elements.ade cache tied to the old data dir.
	a.elementsMu.Lock()
	a.elements = nil
	a.elementsMu.Unlock()
	a.saveConfig()
	return a.paths
}

// SetClientDir points the studio at a new client-compiled directory and
// revalidates it. Changing it also drops cached client-asset state (i18n,
// icons, jars) so they reload from the new location. Returns the updated
// Paths.
func (a *App) SetClientDir(dir string) Paths {
	a.paths.ClientDir = dir
	a.paths.ClientDirValid = looksLikeClientDir(dir)
	// Invalidate client-asset caches tied to the old dir.
	a.i18nMu.Lock()
	a.i18n = nil
	a.i18nMu.Unlock()
	a.iconsMu.Lock()
	a.icons = nil
	a.iconsMu.Unlock()
	a.jars = jarCache{}
	a.saveConfig()
	return a.paths
}

// Env is a small snapshot of runtime facts the UI shows in its footer.
type Env struct {
	Version string `json:"version"`
	OS      string `json:"os"`
}

// GetEnv returns build/runtime info for the status bar.
func (a *App) GetEnv() Env {
	return Env{
		Version: studioVersion,
		OS:      runtimeOS(),
	}
}

const studioVersion = "0.1.0-phase0"

// newStoreForData builds a fresh gamedata.Store over the current data dir
// (used after an export to make subsequent reads reflect the written file).
func (a *App) newStoreForData() *gamedata.Store {
	if !a.paths.DataDirValid {
		return nil
	}
	return gamedata.NewStore(a.paths.DataDir)
}

// requireStore returns the loaded store or an error phrased for surfacing
// in the UI. Used by every data-reading method added in later phases.
func (a *App) requireStore() (*gamedata.Store, error) {
	if a.store == nil {
		return nil, fmt.Errorf("no valid data directory selected (current: %q)", a.paths.DataDir)
	}
	return a.store, nil
}

// --- path detection helpers -------------------------------------------------

// detectPaths walks up from the executable and the working directory looking
// for the repo's server/data and client/compiled directories, so a
// freshly-launched studio usually "just works" without the user picking folders.
func detectPaths() Paths {
	var p Paths

	roots := candidateRoots()
	// The game data lives at server/data and the compiled client at
	// client/compiled; older flat layouts (data/, client-compiled/) are kept
	// as fallbacks so a relocated checkout still auto-detects.
	dataCandidates := []string{filepath.Join("server", "data"), "data"}
	clientCandidates := []string{filepath.Join("client", "compiled"), "client-compiled"}
	for _, root := range roots {
		if !p.DataDirValid {
			for _, rel := range dataCandidates {
				dataDir := filepath.Join(root, rel)
				if looksLikeDataDir(dataDir) {
					p.DataDir = dataDir
					p.DataDirValid = true
					break
				}
			}
		}
		if !p.ClientDirValid {
			for _, rel := range clientCandidates {
				clientDir := filepath.Join(root, rel)
				if looksLikeClientDir(clientDir) {
					p.ClientDir = clientDir
					p.ClientDirValid = true
					break
				}
			}
		}
		if p.DataDirValid && p.ClientDirValid {
			break
		}
	}
	return p
}

// candidateRoots returns directories that might be (or contain) the repo
// root: the working dir and the executable's dir, plus several parent
// levels of each (to tolerate being run from go-server/, cmd/studio/, a
// build output dir, etc.).
func candidateRoots() []string {
	var seeds []string
	if wd, err := os.Getwd(); err == nil {
		seeds = append(seeds, wd)
	}
	if exe, err := os.Executable(); err == nil {
		seeds = append(seeds, filepath.Dir(exe))
	}

	seen := map[string]bool{}
	var out []string
	for _, seed := range seeds {
		dir := seed
		for i := 0; i < 6; i++ { // walk up a few levels
			if !seen[dir] {
				seen[dir] = true
				out = append(out, dir)
			}
			parent := filepath.Dir(dir)
			if parent == dir {
				break
			}
			dir = parent
		}
	}
	return out
}

// looksLikeDataDir sniffs for the signature files of the repo's data/ dir.
func looksLikeDataDir(dir string) bool {
	if dir == "" || !isDir(dir) {
		return false
	}
	// spells.dat + elements.ade + a maps/ dir is a strong signature.
	return fileExists(filepath.Join(dir, "spells.dat")) &&
		fileExists(filepath.Join(dir, "elements.ade")) &&
		isDir(filepath.Join(dir, "maps"))
}

// looksLikeClientDir sniffs for the client-compiled/ jar bundle.
func looksLikeClientDir(dir string) bool {
	if dir == "" || !isDir(dir) {
		return false
	}
	return fileExists(filepath.Join(dir, "game", "contents", "gui.jar"))
}

func isDir(p string) bool {
	info, err := os.Stat(p)
	return err == nil && info.IsDir()
}

func fileExists(p string) bool {
	info, err := os.Stat(p)
	return err == nil && !info.IsDir()
}
