package main

import (
	"os"
	"path/filepath"
	"testing"
)

// repoDataDir locates the repository's real data/ directory relative to this
// package (server/cmd/studio -> ../../data), skipping the test if it
// isn't present (e.g. a checkout without the copyrighted data files).
func repoDataDir(t *testing.T) string {
	t.Helper()
	wd, err := os.Getwd()
	if err != nil {
		t.Fatalf("getwd: %v", err)
	}
	dir := filepath.Join(wd, "..", "..", "data")
	if !looksLikeDataDir(dir) {
		t.Skipf("real data dir not found/valid at %s; skipping", dir)
	}
	return dir
}

// newAppWithData builds an App pointed at the real data dir for read tests.
func newAppWithData(t *testing.T) *App {
	t.Helper()
	a := &App{}
	p := a.SetDataDir(repoDataDir(t))
	if !p.DataDirValid {
		t.Fatalf("SetDataDir did not validate real data dir: %+v", p)
	}
	return a
}

func TestGetDataCounts_RealData(t *testing.T) {
	a := newAppWithData(t)
	c := a.GetDataCounts()
	if c.Error != "" {
		t.Fatalf("GetDataCounts error: %s", c.Error)
	}
	// The real data has hundreds of spells and a non-trivial number of the
	// other record types; assert each repository actually parsed something,
	// which exercises the full parser -> App -> JSON-shape path.
	if c.Spells == 0 {
		t.Errorf("expected >0 spells, got 0")
	}
	if c.FighterCards == 0 {
		t.Errorf("expected >0 fighter cards, got 0")
	}
	if c.StaticEffects == 0 {
		t.Errorf("expected >0 static effects, got 0")
	}
	t.Logf("counts: spells=%d coachCards=%d fighterCards=%d summonings=%d staticEffects=%d events=%d",
		c.Spells, c.CoachCards, c.FighterCards, c.Summonings, c.StaticEffects, c.Events)
}

func TestGetSpells_RealData(t *testing.T) {
	a := newAppWithData(t)
	spells, err := a.GetSpells()
	if err != nil {
		t.Fatalf("GetSpells: %v", err)
	}
	if len(spells) == 0 {
		t.Fatal("expected spells, got none")
	}
	// Sorted ascending by ID (the App contract the UI relies on).
	for i := 1; i < len(spells); i++ {
		if spells[i-1].ID > spells[i].ID {
			t.Fatalf("spells not sorted by ID at index %d: %d > %d", i, spells[i-1].ID, spells[i].ID)
		}
	}
	// Ranges must already be normalized (min <= max) by the store layer.
	for _, s := range spells {
		if s.RangeMin > s.RangeMax {
			t.Errorf("spell %d has unnormalized range %d..%d", s.ID, s.RangeMin, s.RangeMax)
		}
	}
}

func TestGetStaticEffects_RealData(t *testing.T) {
	a := newAppWithData(t)
	areas, err := a.GetStaticEffects()
	if err != nil {
		t.Fatalf("GetStaticEffects: %v", err)
	}
	if len(areas) == 0 {
		t.Fatal("expected static-effect areas, got none")
	}
	// EffectAreaType is documented to be TRAP or SPECIAL (possibly space-
	// padded); verify the field is populated for every record.
	for _, ar := range areas {
		if ar.EffectAreaType == "" {
			t.Errorf("area %d has empty EffectAreaType", ar.ID)
		}
	}
}

func TestRequireStore_NoDataDir(t *testing.T) {
	a := &App{}
	if _, err := a.requireStore(); err == nil {
		t.Fatal("expected error when no data dir is set")
	}
	c := a.GetDataCounts()
	if c.Error == "" {
		t.Fatal("expected GetDataCounts to report an error with no data dir")
	}
}
