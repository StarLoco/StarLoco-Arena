package main

import (
	"os"
	"path/filepath"
	"testing"
)

// newAppWithDataAndClient builds an App pointed at both the real data dir and
// the real client dir, skipping if either is unavailable. Needed for push-diff
// tests which compare local data/*.dat against the client's data.jar entries.
func newAppWithDataAndClient(t *testing.T) *App {
	t.Helper()
	a := newAppWithData(t) // sets + validates the data dir (skips if absent)
	wd, _ := os.Getwd()
	dir := filepath.Join(wd, "..", "..", "..", "client", "compiled")
	if !looksLikeClientDir(dir) {
		dir = filepath.Join(wd, "..", "..", "..", "client-compiled")
	}
	if !looksLikeClientDir(dir) {
		t.Skipf("real client dir not found; skipping push-diff test")
	}
	if p := a.SetClientDir(dir); !p.ClientDirValid {
		t.Fatalf("SetClientDir did not validate: %+v", p)
	}
	return a
}

// TestDiffPushFile_RealClient runs the push diff for spells.dat against the real
// client jar and asserts the report is well-formed. Whether it's identical or
// differs depends on whether the local data has been edited vs the shipped
// client, so we only assert structural invariants here.
func TestDiffPushFile_RealClient(t *testing.T) {
	a := newAppWithDataAndClient(t)
	d, err := a.DiffPushFile("spells.dat")
	if err != nil {
		t.Fatalf("DiffPushFile: %v", err)
	}
	if d.Name != "spells.dat" {
		t.Errorf("name = %q, want spells.dat", d.Name)
	}
	if d.LocalBytes <= 0 {
		t.Errorf("local bytes should be positive, got %d", d.LocalBytes)
	}
	if d.Note == "" {
		t.Error("note should not be empty")
	}
	// If present in the client and identical, deltas must all be equal.
	if d.InClient && d.Identical {
		for _, r := range d.Deltas {
			if r.Current != r.Backup {
				t.Errorf("identical push but kind %q differs: client %d vs local %d", r.Kind, r.Current, r.Backup)
			}
		}
	}
	t.Logf("spells.dat push diff: inClient=%v identical=%v note=%q", d.InClient, d.Identical, d.Note)
}

// TestDiffPushFile_UnknownName rejects a non-pushable file.
func TestDiffPushFile_UnknownName(t *testing.T) {
	a := newAppWithData(t)
	if _, err := a.DiffPushFile("breeds.dat"); err == nil {
		t.Error("expected error for non-pushable file")
	}
}
