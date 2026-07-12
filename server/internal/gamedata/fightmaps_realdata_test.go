package gamedata

import (
	"os"
	"path/filepath"
	"testing"
)

// realDataDir returns the project's real game-data directory, skipping the
// test if it isn't present (mirrors the other real-data test skip pattern).
func realDataDir(t *testing.T) string {
	t.Helper()
	// internal/gamedata is two directories under server/, so the real data
	// dir (server/data) is ../../data from here (see AGENTS.md).
	dir := filepath.Join("..", "..", "data")
	if _, err := os.Stat(filepath.Join(dir, "elements.ade")); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}
	return dir
}

// TestFightMapIDsDiscoversImportedMaps verifies the fight-map registry
// discovers every imported fight map (maps 2-16 all carry both-side
// fight-start markers) and excludes non-fight maps. It also confirms the
// result is sorted and stable across calls (cache).
func TestFightMapIDsDiscoversImportedMaps(t *testing.T) {
	s := NewMapStore(realDataDir(t))
	ids, err := s.FightMapIDs()
	if err != nil {
		t.Fatalf("FightMapIDs: %v", err)
	}
	if len(ids) == 0 {
		t.Fatal("FightMapIDs returned no maps -- expected the imported fight maps")
	}
	// Sorted + contains the canonical map 2.
	found2 := false
	for i, id := range ids {
		if i > 0 && ids[i-1] > id {
			t.Errorf("FightMapIDs not sorted: %v", ids)
		}
		if id == 2 {
			found2 = true
		}
	}
	if !found2 {
		t.Errorf("FightMapIDs %v does not include canonical fight map 2", ids)
	}
	// Every returned map must actually be a fight map.
	for _, id := range ids {
		m, err := s.Get(id)
		if err != nil {
			t.Fatalf("Get(%d): %v", id, err)
		}
		if !m.IsFightMap() {
			t.Errorf("map %d in FightMapIDs but IsFightMap()=false", id)
		}
	}
	// Cache stability.
	ids2, _ := s.FightMapIDs()
	if len(ids2) != len(ids) {
		t.Errorf("FightMapIDs not stable across calls: %v vs %v", ids, ids2)
	}
}

// TestSpecialCellLayoutDerivedFromMapArt verifies every fight map's special
// cells are DERIVED from its own baked negative-gfx Bonus tiles: each entry
// references a known cell type, its CellBaseID matches its Type (per the
// staticEffects mapping), positions are distinct, and every fight map that
// has special art yields a non-empty layout (with no JSON override present).
func TestSpecialCellLayoutDerivedFromMapArt(t *testing.T) {
	dir := realDataDir(t)
	maps := NewMapStore(dir)
	cells := NewSpecialCellStore(dir, maps)

	ids, err := maps.FightMapIDs()
	if err != nil {
		t.Fatalf("FightMapIDs: %v", err)
	}

	known := map[SpecialCellTypeName]bool{
		SpecialCellTrap: true, SpecialCellEnthusiasm: true, SpecialCellShield: true,
		SpecialCellEagleEye: true, SpecialCellPanacea: true, SpecialCellMotivation: true,
		SpecialCellHealingHeart: true, SpecialCellKiller: true,
	}

	totalCells := 0
	for _, id := range ids {
		layout, err := cells.Get(id)
		if err != nil {
			t.Errorf("map %d: SpecialCells.Get: %v", id, err)
			continue
		}
		seen := map[[2]int32]bool{}
		for _, c := range layout.Cells {
			totalCells++
			if !known[c.Type] {
				t.Errorf("map %d: unknown special-cell type %q", id, c.Type)
			}
			// CellBaseID must be a SPECIAL staticEffects id whose mapping
			// agrees with the entry's own Type (single source of truth).
			wantType, ok := specialCellTypeByBaseID(c.CellBaseID)
			if !ok {
				t.Errorf("map %d cell %v: cellBaseId %d not a known SPECIAL id", id, [2]int32{c.X, c.Y}, c.CellBaseID)
			} else if wantType != c.Type {
				t.Errorf("map %d cell %v: cellBaseId %d => %q but Type is %q", id, [2]int32{c.X, c.Y}, c.CellBaseID, wantType, c.Type)
			}
			key := [2]int32{c.X, c.Y}
			if seen[key] {
				t.Errorf("map %d: duplicate special cell at %v", id, key)
			}
			seen[key] = true
		}
	}
	if totalCells == 0 {
		t.Fatal("no special cells derived from any fight map's art -- derivation is broken")
	}
	t.Logf("derived %d special cells across %d fight maps", totalCells, len(ids))
}

// TestSpecialCellDerivationGroundTruth pins the derived layout for two maps
// against the ground truth recovered by scanning their baked negative-gfx
// tiles (cmd/studio TestDebugMapGfxFreq), so a regression in the derivation
// or the gfx->type mapping is caught immediately.
func TestSpecialCellDerivationGroundTruth(t *testing.T) {
	dir := realDataDir(t)
	maps := NewMapStore(dir)

	type want struct {
		x, y int32
		typ  SpecialCellTypeName
	}
	// Map 2 (the ice arena) -- a representative subset from the scan:
	//   gfx -1008(motivation) @ (6,1)(9,1); -1007(enthusiasm) @ (7,1)(8,1)(8,16);
	//   -1006(panacea) @ (8,15); -1004(eagle_eye) @ (8,5)(8,10);
	//   -1003(trap) @ (6,16)(11,16).
	cases := map[int][]want{
		2: {
			{6, 1, SpecialCellMotivation}, {9, 1, SpecialCellMotivation},
			{7, 1, SpecialCellEnthusiasm}, {8, 5, SpecialCellEagleEye},
			{8, 15, SpecialCellPanacea}, {6, 16, SpecialCellTrap},
		},
		// Map 8: -1009(healing_heart) @ (6,5); -1008(motivation) @ (7,5);
		//        -1004(eagle_eye) @ (8,5).
		8: {
			{6, 5, SpecialCellHealingHeart}, {7, 5, SpecialCellMotivation},
			{8, 5, SpecialCellEagleEye},
		},
	}

	for id, wants := range cases {
		m, err := maps.Get(id)
		if err != nil {
			t.Fatalf("map %d: Get: %v", id, err)
		}
		got := map[[2]int32]SpecialCellTypeName{}
		for _, c := range m.SpecialCells() {
			got[[2]int32{c.X, c.Y}] = c.Type
		}
		for _, w := range wants {
			key := [2]int32{w.x, w.y}
			if got[key] != w.typ {
				t.Errorf("map %d cell %v: derived %q, want %q", id, key, got[key], w.typ)
			}
		}
	}
}

// TestSpecialCellStoreMissingFileIsEmptyNoError verifies a map with no
// specialcells.json yields an empty layout (not an error).
func TestSpecialCellStoreMissingFileIsEmptyNoError(t *testing.T) {
	dir := realDataDir(t)
	cells := NewSpecialCellStore(dir, NewMapStore(dir))
	layout, err := cells.Get(999999) // no such map dir
	if err != nil {
		t.Fatalf("Get(missing) returned error: %v", err)
	}
	if len(layout.Cells) != 0 {
		t.Errorf("Get(missing) = %d cells, want 0", len(layout.Cells))
	}
}
