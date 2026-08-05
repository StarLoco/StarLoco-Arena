package gamedata

import (
	"os"
	"path/filepath"
	"testing"
)

// mkBdat creates a fake record store in dir.
func mkBdat(t *testing.T, dir string) {
	t.Helper()
	if err := os.MkdirAll(dir, 0o755); err != nil {
		t.Fatal(err)
	}
	for _, f := range []string{"data.bdat", "indexes.bdat"} {
		if err := os.WriteFile(filepath.Join(dir, f), []byte("x"), 0o644); err != nil {
			t.Fatal(err)
		}
	}
}

// mkMaps creates a fake arena tree under dir (so dir is the MapsRoot).
func mkMaps(t *testing.T, dir string) {
	t.Helper()
	for _, sub := range []string{"fight", "tplg"} {
		if err := os.MkdirAll(filepath.Join(dir, "maps", sub), 0o755); err != nil {
			t.Fatal(err)
		}
	}
}

// The four layouts an operator can plausibly point at must all resolve, so
// nobody has to hand-merge the client's two data directories into one.
func TestResolveAcceptsEveryRealLayout(t *testing.T) {
	tests := []struct {
		name     string
		build    func(t *testing.T, root string)
		wantBdat string // relative to root
		wantMaps string // relative to root ("." = root itself)
	}{
		{
			name: "merged server data dir",
			build: func(t *testing.T, root string) {
				mkBdat(t, root)
				mkMaps(t, root)
			},
			wantBdat: ".", wantMaps: ".",
		},
		{
			name: "client contents dir",
			build: func(t *testing.T, root string) {
				mkBdat(t, filepath.Join(root, "bdata"))
				mkMaps(t, root)
			},
			wantBdat: "bdata", wantMaps: ".",
		},
		{
			name: "client game dir",
			build: func(t *testing.T, root string) {
				mkBdat(t, filepath.Join(root, "contents", "bdata"))
				mkMaps(t, filepath.Join(root, "contents"))
			},
			wantBdat: filepath.Join("contents", "bdata"), wantMaps: "contents",
		},
		{
			name: "client install root",
			build: func(t *testing.T, root string) {
				mkBdat(t, filepath.Join(root, "game", "contents", "bdata"))
				mkMaps(t, filepath.Join(root, "game", "contents"))
			},
			wantBdat: filepath.Join("game", "contents", "bdata"),
			wantMaps: filepath.Join("game", "contents"),
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			root := t.TempDir()
			tt.build(t, root)

			loc := Resolve(root)
			if !loc.Complete() {
				t.Fatalf("did not resolve both halves: %+v", loc)
			}
			if want := filepath.Join(root, tt.wantBdat); filepath.Clean(loc.BdatDir) != filepath.Clean(want) {
				t.Errorf("BdatDir = %q, want %q", loc.BdatDir, want)
			}
			if want := filepath.Join(root, tt.wantMaps); filepath.Clean(loc.MapsRoot) != filepath.Clean(want) {
				t.Errorf("MapsRoot = %q, want %q", loc.MapsRoot, want)
			}
		})
	}
}

// Copying only the bdata folder is what the old instructions told people to do,
// and it yields records but no arenas. That must be reported as incomplete
// rather than silently passing for "no game data".
func TestResolvePartialDataIsReportedHonestly(t *testing.T) {
	root := t.TempDir()
	mkBdat(t, root)

	loc := Resolve(root)
	if !loc.Found() {
		t.Fatal("records present but Found() is false")
	}
	if loc.Complete() {
		t.Error("Complete() must be false when the arenas are missing")
	}
	if loc.BdatDir == "" || loc.MapsRoot != "" {
		t.Errorf("unexpected split: %+v", loc)
	}
}

func TestResolveEmptyAndMissing(t *testing.T) {
	if loc := Resolve(""); loc.Found() {
		t.Error("empty root must not resolve")
	}
	if loc := Resolve(filepath.Join(t.TempDir(), "nope")); loc.Found() {
		t.Error("missing dir must not resolve")
	}
}

// A directory containing only one of the two .bdat files is not a data dir.
func TestResolveRejectsIncompleteBdat(t *testing.T) {
	root := t.TempDir()
	if err := os.WriteFile(filepath.Join(root, "data.bdat"), []byte("x"), 0o644); err != nil {
		t.Fatal(err)
	}
	if loc := Resolve(root); loc.BdatDir != "" {
		t.Errorf("half a record store must not count: %+v", loc)
	}
}

// Discover must combine halves found in DIFFERENT places: the realistic case is
// a server/data folder holding the records while the arenas are still only in
// the client install.
func TestDiscoverCombinesHalvesFromDifferentRoots(t *testing.T) {
	base := t.TempDir()
	recordsOnly := filepath.Join(base, "data")
	clientGame := filepath.Join(base, "client", "compiled", "game")
	mkBdat(t, recordsOnly)
	mkMaps(t, filepath.Join(clientGame, "contents"))
	mkBdat(t, filepath.Join(clientGame, "contents", "bdata"))

	// Run from base so the relative client path candidate applies.
	t.Chdir(base)

	loc := Discover("data")
	if !loc.Complete() {
		t.Fatalf("halves were not combined: %+v", loc)
	}
	if filepath.Clean(loc.BdatDir) != filepath.Clean(recordsOnly) {
		t.Errorf("BdatDir = %q, want the configured dir %q", loc.BdatDir, recordsOnly)
	}
	wantMaps := filepath.Join(clientGame, "contents")
	if filepath.Clean(loc.MapsRoot) != filepath.Clean(wantMaps) {
		t.Errorf("MapsRoot = %q, want %q", loc.MapsRoot, wantMaps)
	}
}

// The configured path must win over any auto-detected location.
func TestDiscoverPrefersTheConfiguredPath(t *testing.T) {
	base := t.TempDir()
	configured := filepath.Join(base, "chosen")
	decoy := filepath.Join(base, "data")
	mkBdat(t, configured)
	mkMaps(t, configured)
	mkBdat(t, decoy)
	mkMaps(t, decoy)

	t.Chdir(base)

	loc := Discover(configured)
	if filepath.Clean(loc.BdatDir) != filepath.Clean(configured) {
		t.Errorf("BdatDir = %q, want the configured %q", loc.BdatDir, configured)
	}
}

func TestDiscoverFindsNothingGracefully(t *testing.T) {
	t.Chdir(t.TempDir())
	if loc := Discover("definitely-not-here"); loc.Found() {
		t.Errorf("expected nothing, got %+v", loc)
	}
}

// The operator has to be told where we looked, or a failed detection is a dead
// end. Paths must be absolute so they can be acted on directly.
func TestSearchedPathsAreAbsoluteAndNonEmpty(t *testing.T) {
	paths := SearchedPaths("data")
	if len(paths) == 0 {
		t.Fatal("no search paths reported")
	}
	for _, p := range paths {
		if !filepath.IsAbs(p) {
			t.Errorf("search path %q is not absolute", p)
		}
	}
}

// Real client data, when present, must be detected by pointing at the client
// install root — the thing an operator would naturally try. Skips when the
// git-ignored client tree is absent.
func TestDiscoverRealClientLayout(t *testing.T) {
	root := filepath.Join("..", "..", "..", "client", "compiled")
	if _, err := os.Stat(filepath.Join(root, "game", "contents", "bdata", "data.bdat")); err != nil {
		t.Skip("retail client not present locally")
	}
	loc := Resolve(root)
	if !loc.Complete() {
		t.Fatalf("real client layout did not fully resolve: %+v", loc)
	}
	if !hasBdat(loc.BdatDir) {
		t.Errorf("resolved BdatDir %q has no record store", loc.BdatDir)
	}
	if !hasMaps(loc.MapsRoot) {
		t.Errorf("resolved MapsRoot %q has no arenas", loc.MapsRoot)
	}
}
