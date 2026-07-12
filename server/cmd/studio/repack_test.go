package main

import (
	"archive/zip"
	"bytes"
	"os"
	"path/filepath"
	"testing"
)

// makeTestJar writes a small zip with the given entries and returns its path.
func makeTestJar(t *testing.T, entries map[string]string) string {
	t.Helper()
	p := filepath.Join(t.TempDir(), "test.jar")
	f, err := os.Create(p)
	if err != nil {
		t.Fatalf("create jar: %v", err)
	}
	zw := zip.NewWriter(f)
	// Deterministic order: manifest first, then the rest as given.
	order := []string{"META-INF/MANIFEST.MF"}
	for k := range entries {
		if k != "META-INF/MANIFEST.MF" {
			order = append(order, k)
		}
	}
	for _, name := range order {
		content, ok := entries[name]
		if !ok {
			continue
		}
		w, err := zw.Create(name)
		if err != nil {
			t.Fatalf("zip create %s: %v", name, err)
		}
		w.Write([]byte(content))
	}
	zw.Close()
	f.Close()
	return p
}

func TestRepackJar_ReplacesAndPreserves(t *testing.T) {
	jar := makeTestJar(t, map[string]string{
		"META-INF/MANIFEST.MF":    "Manifest-Version: 1.0\n",
		"data/maps/2/map_0_0.amw": "ORIGINAL",
		"gui/keep.txt":            "unchanged",
	})

	res, err := repackJar(jar, []RepackReplacement{
		{EntryPath: "data/maps/2/map_0_0.amw", Data: []byte("EDITED-BYTES")},
	})
	if err != nil {
		t.Fatalf("repackJar: %v", err)
	}
	if len(res.Replaced) != 1 || res.Replaced[0] != "data/maps/2/map_0_0.amw" {
		t.Errorf("Replaced = %v", res.Replaced)
	}
	if len(res.Missing) != 0 {
		t.Errorf("Missing = %v", res.Missing)
	}
	if res.BackupPath == "" {
		t.Error("expected a backup path")
	}
	if _, err := os.Stat(res.BackupPath); err != nil {
		t.Errorf("backup not written: %v", err)
	}

	// The replaced entry has the new bytes...
	got, err := verifyJarEntry(jar, "data/maps/2/map_0_0.amw")
	if err != nil {
		t.Fatalf("verify replaced: %v", err)
	}
	if !bytes.Equal(got, []byte("EDITED-BYTES")) {
		t.Errorf("replaced content = %q", got)
	}
	// ...and every other entry is preserved verbatim.
	keep, err := verifyJarEntry(jar, "gui/keep.txt")
	if err != nil || string(keep) != "unchanged" {
		t.Errorf("preserved entry wrong: %q err=%v", keep, err)
	}
	man, err := verifyJarEntry(jar, "META-INF/MANIFEST.MF")
	if err != nil || string(man) != "Manifest-Version: 1.0\n" {
		t.Errorf("manifest not preserved: %q err=%v", man, err)
	}
}

func TestRepackJar_ReportsMissing(t *testing.T) {
	jar := makeTestJar(t, map[string]string{"a.txt": "x"})
	res, err := repackJar(jar, []RepackReplacement{{EntryPath: "does/not/exist", Data: []byte("y")}})
	if err != nil {
		t.Fatalf("repackJar: %v", err)
	}
	if len(res.Missing) != 1 || res.Missing[0] != "does/not/exist" {
		t.Errorf("Missing = %v, want [does/not/exist]", res.Missing)
	}
	if len(res.Replaced) != 0 {
		t.Errorf("Replaced = %v, want none", res.Replaced)
	}
}

func TestRepackJar_EntryOrderPreserved(t *testing.T) {
	jar := makeTestJar(t, map[string]string{
		"META-INF/MANIFEST.MF": "m",
		"b.txt":                "1",
		"c.txt":                "2",
	})
	_, err := repackJar(jar, []RepackReplacement{{EntryPath: "b.txt", Data: []byte("EDIT")}})
	if err != nil {
		t.Fatalf("repackJar: %v", err)
	}
	r, err := zip.OpenReader(jar)
	if err != nil {
		t.Fatalf("open: %v", err)
	}
	defer r.Close()
	if len(r.File) == 0 || r.File[0].Name != "META-INF/MANIFEST.MF" {
		t.Errorf("manifest should remain first entry, got order: %v", names(r))
	}
}

func names(r *zip.ReadCloser) []string {
	var out []string
	for _, f := range r.File {
		out = append(out, f.Name)
	}
	return out
}

func TestSaveAndGetSpecialCells(t *testing.T) {
	a, dir := appWithTempDataCopy(t)
	cells := []SpecialCellDTO{
		{X: 5, Y: 6, Type: "trap", CellBaseID: 1},
		{X: 7, Y: 8, Type: "shield", CellBaseID: 1003},
	}
	res, err := a.SaveSpecialCells(3, cells)
	if err != nil {
		t.Fatalf("SaveSpecialCells: %v", err)
	}
	if res.Target == "" {
		t.Error("expected a target path")
	}
	if _, err := os.Stat(filepath.Join(dir, "maps", "3", "specialcells.json")); err != nil {
		t.Errorf("specialcells.json not written: %v", err)
	}
	got, err := a.GetSpecialCells(3)
	if err != nil {
		t.Fatalf("GetSpecialCells: %v", err)
	}
	if len(got) != 2 || got[0].Type != "trap" || got[1].CellBaseID != 1003 {
		t.Errorf("round-trip mismatch: %+v", got)
	}
}

func TestGetSpecialCells_MissingReturnsEmpty(t *testing.T) {
	a, _ := appWithTempDataCopy(t)
	got, err := a.GetSpecialCells(999)
	if err != nil {
		t.Fatalf("GetSpecialCells: %v", err)
	}
	if len(got) != 0 {
		t.Errorf("expected empty for missing map, got %+v", got)
	}
}
