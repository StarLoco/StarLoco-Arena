package main

import (
	"os"
	"path/filepath"
	"testing"
)

func TestListClientMaps_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	maps, err := a.ListClientMaps()
	if err != nil {
		t.Fatalf("ListClientMaps: %v", err)
	}
	byID := map[int]ClientMapInfo{}
	for _, m := range maps {
		byID[m.ID] = m
	}
	// The overworld (map 0) must be present and have many chunks.
	world, ok := byID[0]
	if !ok {
		t.Fatal("expected map 0 (overworld) in client data.jar")
	}
	if world.ChunkCount < 100 {
		t.Errorf("map 0 chunk count = %d, expected the large overworld (>100)", world.ChunkCount)
	}
	if _, ok := byID[1]; !ok {
		t.Error("expected map 1 (secondary world) in client data.jar")
	}
	if _, ok := byID[2]; !ok {
		t.Error("expected fight map 2 in client data.jar")
	}
	t.Logf("client maps: %d; map0 chunks=%d", len(maps), world.ChunkCount)
}

func TestImportMapFromClient_Overworld(t *testing.T) {
	// Need both a writable data dir and the real client jar.
	a, dir := appWithTempMapsCopy(t, 2) // gives temp data dir + client not set
	ac := newAppWithClient(t)
	a.paths.ClientDir = ac.paths.ClientDir
	a.paths.ClientDirValid = ac.paths.ClientDirValid

	n, err := a.ImportMapFromClient(0)
	if err != nil {
		t.Fatalf("ImportMapFromClient(0): %v", err)
	}
	if n < 100 {
		t.Errorf("imported %d chunks, expected the large overworld", n)
	}
	// Files landed locally.
	entries, err := os.ReadDir(filepath.Join(dir, "maps", "0"))
	if err != nil {
		t.Fatalf("read imported map dir: %v", err)
	}
	if len(entries) != n {
		t.Errorf("imported %d chunks but dir has %d files", n, len(entries))
	}
	// It now shows up in ListMaps as a non-fight map.
	maps, err := a.ListMaps()
	if err != nil {
		t.Fatalf("ListMaps: %v", err)
	}
	found := false
	for _, m := range maps {
		if m.ID == 0 {
			found = true
			if m.IsFight {
				t.Error("map 0 should NOT be flagged as a fight map")
			}
			if m.CellCount == 0 {
				t.Error("map 0 should have cells")
			}
		}
	}
	if !found {
		t.Error("map 0 not listed after import")
	}
	// Re-import refuses to clobber.
	if _, err := a.ImportMapFromClient(0); err == nil {
		t.Error("expected re-import to refuse clobbering existing local map")
	}
	t.Logf("imported overworld map 0: %d chunks", n)
}
