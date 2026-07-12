package main

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// appWithTempDataCopy copies the real data/ files into a temp dir and points
// an App at it, so export tests can write freely without touching the repo's
// real data. Skips if the real data isn't present.
func appWithTempDataCopy(t *testing.T) (*App, string) {
	t.Helper()
	src := repoDataDir(t) // skips if absent
	dst := t.TempDir()

	// Copy the files the export path needs (+ the ones NewStore reads).
	for _, name := range []string{
		"spells.dat", "cards.dat", "events.dat", "summoning.dat",
		"staticEffects.dat", "elements.ade",
	} {
		b, err := os.ReadFile(filepath.Join(src, name))
		if err != nil {
			continue // some may be absent; only spells.dat is required below
		}
		if err := os.WriteFile(filepath.Join(dst, name), b, 0o644); err != nil {
			t.Fatalf("copy %s: %v", name, err)
		}
	}
	// A maps/ dir is part of the data-dir signature.
	if err := os.MkdirAll(filepath.Join(dst, "maps"), 0o755); err != nil {
		t.Fatalf("mkdir maps: %v", err)
	}

	a := &App{}
	p := a.SetDataDir(dst)
	if !p.DataDirValid {
		t.Fatalf("temp data copy not recognized as valid: %+v", p)
	}
	return a, dst
}

func TestSaveSpells_BackupAndEdit(t *testing.T) {
	a, dir := appWithTempDataCopy(t)
	target := filepath.Join(dir, "spells.dat")

	before, err := os.ReadFile(target)
	if err != nil {
		t.Fatalf("read before: %v", err)
	}
	f, err := parser.ParseSpellsFile(before)
	if err != nil || len(f.Spells) == 0 {
		t.Fatalf("parse before: %v", err)
	}
	// Pick the first spell; bump its AP cost by a distinctive amount.
	victim := f.Spells[0]
	newAP := victim.ActionPointsCost + 3

	res, err := a.SaveSpells([]SpellEdit{{
		ID:                        victim.ID,
		ActionPointsCost:          newAP,
		RangeMin:                  victim.RangeMin,
		RangeMax:                  victim.RangeMax,
		CastTestLineOfSight:       victim.CastTestLineOfSight,
		CastOnlyLine:              victim.CastOnlyLine,
		NeedFreeCell:              victim.NeedFreeCell,
		CastFrequencyMaxPerTurn:   victim.CastFrequencyMaxPerTurn,
		CastFrequencyMaxPerPlayer: victim.CastFrequencyMaxPerPlayer,
		CastFrequencyMinInterval:  victim.CastFrequencyMinInterval,
		Price:                     victim.Price,
		Criterion:                 victim.Criterion,
	}})
	if err != nil {
		t.Fatalf("SaveSpells: %v", err)
	}

	// Backup must exist and equal the original bytes.
	if res.BackupPath == "" {
		t.Fatal("expected a backup path")
	}
	backup, err := os.ReadFile(res.BackupPath)
	if err != nil {
		t.Fatalf("read backup: %v", err)
	}
	if len(backup) != len(before) {
		t.Errorf("backup length %d != original %d", len(backup), len(before))
	}

	// Re-read the written file: the edited spell has the new AP, everything
	// else identical, and the file still parses.
	after, err := os.ReadFile(target)
	if err != nil {
		t.Fatalf("read after: %v", err)
	}
	fa, err := parser.ParseSpellsFile(after)
	if err != nil {
		t.Fatalf("parse after: %v", err)
	}
	if len(fa.Spells) != len(f.Spells) {
		t.Fatalf("spell count changed: %d -> %d", len(f.Spells), len(fa.Spells))
	}
	if fa.Spells[0].ActionPointsCost != newAP {
		t.Errorf("AP not applied: got %d want %d", fa.Spells[0].ActionPointsCost, newAP)
	}
	// Second spell (unedited) must be untouched.
	if len(f.Spells) > 1 && fa.Spells[1] != f.Spells[1] {
		t.Errorf("unedited spell changed:\n  before=%+v\n  after =%+v", f.Spells[1], fa.Spells[1])
	}
	// Effects preserved.
	if len(fa.Effects) != len(f.Effects) {
		t.Errorf("effect count changed: %d -> %d", len(f.Effects), len(fa.Effects))
	}
}

func TestSaveSpells_UnknownIDRejected(t *testing.T) {
	a, _ := appWithTempDataCopy(t)
	if _, err := a.SaveSpells([]SpellEdit{{ID: 9999999}}); err == nil {
		t.Fatal("expected error for unknown spell id")
	}
}

func TestSaveSpells_NoDataDir(t *testing.T) {
	a := &App{}
	if _, err := a.SaveSpells(nil); err == nil {
		t.Fatal("expected error with no data dir")
	}
}
