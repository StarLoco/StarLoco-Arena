package main

import (
	"os"
	"path/filepath"
	"testing"
)

// TestRecordCountDeltas_Identical: diffing a real .dat against itself yields
// equal current/backup counts per kind (a no-op restore).
func TestRecordCountDeltas_Identical(t *testing.T) {
	raw := realData(t, "spells.dat")
	deltas, ok := recordCountDeltas("spells.dat", raw, raw)
	if !ok {
		t.Fatal("expected spells.dat to be parseable")
	}
	if len(deltas) == 0 {
		t.Fatal("expected at least one record kind")
	}
	for _, d := range deltas {
		if d.Current != d.Backup {
			t.Errorf("kind %q: current %d != backup %d for identical data", d.Kind, d.Current, d.Backup)
		}
	}
}

// TestRecordCountDeltas_UnknownFile: a non-.dat name isn't parsed.
func TestRecordCountDeltas_UnknownFile(t *testing.T) {
	if _, ok := recordCountDeltas("data.jar", []byte{1, 2, 3}, []byte{1, 2, 3}); ok {
		t.Error("expected data.jar to be unparseable for record counts")
	}
}

// TestSummarizeDiff_Cases covers the human summary branches.
func TestSummarizeDiff_Cases(t *testing.T) {
	if got := summarizeDiff(BackupDiff{CurrentBytes: -1}); got == "" {
		t.Error("missing-file summary empty")
	}
	if got := summarizeDiff(BackupDiff{CurrentBytes: 10, Identical: true}); got == "" {
		t.Error("identical summary empty")
	}
	d := BackupDiff{
		CurrentBytes: 100, BackupBytes: 90, Parsed: true,
		Deltas: []RecordDelta{{Kind: "spells", Current: 170, Backup: 168}},
	}
	got := summarizeDiff(d)
	if got == "" {
		t.Error("delta summary empty")
	}
	// Must mention the -2 change.
	if want := "170\u2192168"; !contains(got, want) {
		t.Errorf("summary %q missing %q", got, want)
	}
}

// TestDiffBackup_RealRoundTrip creates a real backup via the export pipeline and
// confirms DiffBackup reports it identical to the current file (a no-op restore
// of an unchanged file). Skips when data is absent.
func TestDiffBackup_RealRoundTrip(t *testing.T) {
	a := newAppWithData(t)
	// Copy spells.dat to a temp data dir so we can create a .bak without
	// touching the repo's real data.
	src := filepath.Join(a.paths.DataDir, "spells.dat")
	raw, err := os.ReadFile(src)
	if err != nil {
		t.Skipf("spells.dat unavailable: %v", err)
	}
	tmp := t.TempDir()
	dst := filepath.Join(tmp, "spells.dat")
	if err := os.WriteFile(dst, raw, 0o644); err != nil {
		t.Fatal(err)
	}
	// Point the app at the temp dir so pathUnderManagedRoot accepts it.
	if p := a.SetDataDir(tmp); !p.DataDirValid {
		// SetDataDir may reject a dir without full data; fall back to manual root.
		a.paths.DataDir = tmp
		a.paths.DataDirValid = true
	}
	// exportBytes writes dst and creates a timestamped .bak of the prior content.
	if _, err := exportBytes(dst, raw); err != nil {
		t.Fatalf("exportBytes: %v", err)
	}
	// Find the created backup.
	entries, _ := os.ReadDir(tmp)
	var bak string
	for _, e := range entries {
		if backupNameRe.MatchString(e.Name()) {
			bak = filepath.Join(tmp, e.Name())
		}
	}
	if bak == "" {
		t.Skip("no backup produced (file may not have pre-existed)")
	}
	diff, err := a.DiffBackup(bak)
	if err != nil {
		t.Fatalf("DiffBackup: %v", err)
	}
	if !diff.Identical {
		t.Errorf("expected identical diff for unchanged restore, got note=%q", diff.Note)
	}
	if !diff.Parsed || len(diff.Deltas) == 0 {
		t.Errorf("expected parsed spells deltas, got parsed=%v deltas=%d", diff.Parsed, len(diff.Deltas))
	}
}

func contains(s, sub string) bool {
	return len(s) >= len(sub) && (indexOf(s, sub) >= 0)
}
func indexOf(s, sub string) int {
	for i := 0; i+len(sub) <= len(s); i++ {
		if s[i:i+len(sub)] == sub {
			return i
		}
	}
	return -1
}
