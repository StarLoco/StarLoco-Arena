package main

import (
	"os"
	"path/filepath"
	"testing"
)

func TestListPaletteElements_RealData(t *testing.T) {
	a := newAppWithData(t)
	pal, err := a.ListPaletteElements()
	if err != nil {
		t.Fatalf("ListPaletteElements: %v", err)
	}
	if len(pal) == 0 {
		t.Fatal("expected palette elements")
	}
	for _, pe := range pal {
		if pe.GfxID == 0 {
			t.Errorf("palette element %d has no gfx (should be filtered)", pe.ID)
		}
	}
	t.Logf("palette: %d placeable elements", len(pal))
}

func TestGetMapEditData_RealData(t *testing.T) {
	a := newAppWithData(t)
	ed, err := a.GetMapEditData(2)
	if err != nil {
		t.Fatalf("GetMapEditData: %v", err)
	}
	if len(ed.Cells) == 0 {
		t.Fatal("map 2 has no cells")
	}
	// Some cell must carry elements.
	total := 0
	for _, c := range ed.Cells {
		for _, lvl := range c.Levels {
			total += len(lvl)
		}
	}
	if total == 0 {
		t.Fatal("map 2 edit data has no elements")
	}
	t.Logf("map 2: %d cells, %d elements", len(ed.Cells), total)
}

// The critical guarantee: loading a map's edit data and saving it back
// UNCHANGED reproduces every .amw chunk byte-for-byte.
func TestSaveMapEditData_RoundTrip(t *testing.T) {
	a, dir := appWithTempMapsCopy(t, 2)

	// original chunk bytes
	before := readMapChunkBytes(t, dir, 2)

	ed, err := a.GetMapEditData(2)
	if err != nil {
		t.Fatalf("GetMapEditData: %v", err)
	}
	if _, err := a.SaveMapEditData(2, ed.Cells); err != nil {
		t.Fatalf("SaveMapEditData: %v", err)
	}

	after := readMapChunkBytes(t, dir, 2)
	if len(before) != len(after) {
		t.Fatalf("chunk file count changed: %d -> %d", len(before), len(after))
	}
	for name, b := range before {
		if a2, ok := after[name]; !ok {
			t.Errorf("chunk %s missing after save", name)
		} else if len(b) != len(a2) {
			t.Errorf("chunk %s length changed %d -> %d", name, len(b), len(a2))
		} else {
			for i := range b {
				if b[i] != a2[i] {
					t.Errorf("chunk %s byte %d differs: %02x -> %02x", name, i, b[i], a2[i])
					break
				}
			}
		}
	}
}

func TestDuplicateAndDeleteMap(t *testing.T) {
	a, dir := appWithTempMapsCopy(t, 2)
	const newID = 900
	if err := a.DuplicateMap(2, newID); err != nil {
		t.Fatalf("DuplicateMap: %v", err)
	}
	if _, err := os.Stat(filepath.Join(dir, "maps", "900")); err != nil {
		t.Fatalf("duplicated map dir missing: %v", err)
	}
	// duplicate again should fail (won't clobber)
	if err := a.DuplicateMap(2, newID); err == nil {
		t.Error("expected duplicate-clobber to fail")
	}
	// editable data loads for the new map
	ed, err := a.GetMapEditData(newID)
	if err != nil || len(ed.Cells) == 0 {
		t.Fatalf("GetMapEditData(new): %v", err)
	}
	// delete works for non-builtin
	if err := a.DeleteMap(newID); err != nil {
		t.Fatalf("DeleteMap: %v", err)
	}
	if _, err := os.Stat(filepath.Join(dir, "maps", "900")); !os.IsNotExist(err) {
		t.Error("map dir should be gone after delete")
	}
	// refuse deleting a built-in
	if err := a.DeleteMap(2); err == nil {
		t.Error("expected refusal to delete built-in map 2")
	}
}

func TestCreateBlankMap(t *testing.T) {
	a, dir := appWithTempMapsCopy(t, 2)
	const newID = 901
	if err := a.CreateBlankMap(newID, 10); err != nil {
		t.Fatalf("CreateBlankMap: %v", err)
	}
	ed, err := a.GetMapEditData(newID)
	if err != nil {
		t.Fatalf("GetMapEditData(blank): %v", err)
	}
	if len(ed.Cells) != 100 {
		t.Errorf("blank 10x10 should have 100 cells, got %d", len(ed.Cells))
	}
	for _, c := range ed.Cells {
		if len(c.Levels) != 0 {
			t.Errorf("blank cell (%d,%d) should be empty", c.X, c.Y)
			break
		}
	}
	_ = dir
	// bad size rejected
	if err := a.CreateBlankMap(902, 0); err == nil {
		t.Error("expected size 0 to be rejected")
	}
}

// --- helpers ---

// appWithTempMapsCopy copies the real data dir's essential files + maps/<id>/
// into a temp dir and points an App at it, so map-write tests don't touch the
// repo's real data.
func appWithTempMapsCopy(t *testing.T, mapID int) (*App, string) {
	t.Helper()
	src := repoDataDir(t)
	dst := t.TempDir()

	for _, name := range []string{"spells.dat", "elements.ade"} {
		if b, err := os.ReadFile(filepath.Join(src, name)); err == nil {
			os.WriteFile(filepath.Join(dst, name), b, 0o644)
		}
	}
	// copy maps/<id>/
	srcMap := filepath.Join(src, "maps", itoa(mapID))
	dstMap := filepath.Join(dst, "maps", itoa(mapID))
	os.MkdirAll(dstMap, 0o755)
	entries, err := os.ReadDir(srcMap)
	if err != nil {
		t.Skipf("map %d not present: %v", mapID, err)
	}
	for _, e := range entries {
		if e.IsDir() {
			continue
		}
		b, _ := os.ReadFile(filepath.Join(srcMap, e.Name()))
		os.WriteFile(filepath.Join(dstMap, e.Name()), b, 0o644)
	}

	a := &App{}
	p := a.SetDataDir(dst)
	if !p.DataDirValid {
		t.Fatalf("temp data dir not valid: %+v", p)
	}
	return a, dst
}

func readMapChunkBytes(t *testing.T, dataDir string, id int) map[string][]byte {
	t.Helper()
	dir := filepath.Join(dataDir, "maps", itoa(id))
	entries, err := os.ReadDir(dir)
	if err != nil {
		t.Fatalf("read map dir: %v", err)
	}
	out := map[string][]byte{}
	for _, e := range entries {
		if e.IsDir() || filepath.Ext(e.Name()) != ".amw" {
			continue
		}
		b, _ := os.ReadFile(filepath.Join(dir, e.Name()))
		out[e.Name()] = b
	}
	return out
}
