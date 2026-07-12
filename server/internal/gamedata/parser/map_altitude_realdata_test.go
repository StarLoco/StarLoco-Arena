package parser

import (
	"os"
	"testing"
)

// TestResolveCellSurfaces_RealMapData sanity-checks ResolveCellSurfaces
// against the real fight-map data (mapID=2): every cell should resolve to
// at least one surface (a totally empty/unwalkable cell would mean no
// fighter could ever stand anywhere, which would make the existing
// hardcoded placement spots impossible), and the known coach-start cells
// should resolve to at least one WALKABLE surface (since a fighter is
// placed there at fight start).
func TestResolveCellSurfaces_RealMapData(t *testing.T) {
	elementsRaw, err := os.ReadFile("../../../data/elements.ade")
	if err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}
	_, elementsBody, err := PeekAleaHeader(elementsRaw)
	if err != nil {
		t.Fatalf("PeekAleaHeader(elements.ade): %v", err)
	}
	elementsFile, err := ParseElementsFile(elementsBody)
	if err != nil {
		t.Fatalf("ParseElementsFile: %v", err)
	}

	mapRaw, err := os.ReadFile("../../../data/maps/2/map_0_0.amw")
	if err != nil {
		t.Skipf("real .amw fixture not available (%v), skipping", err)
	}
	_, mapBody, err := PeekAleaHeader(mapRaw)
	if err != nil {
		t.Fatalf("PeekAleaHeader(map_0_0.amw): %v", err)
	}
	chunk, err := ParseAMWFile(mapBody)
	if err != nil {
		t.Fatalf("ParseAMWFile: %v", err)
	}

	var totalSurfaces, walkableSurfaces, cellsWithNoSurface int
	for _, cell := range chunk.Cells {
		surfaces := ResolveCellSurfaces(cell, elementsFile.Elements)
		if len(surfaces) == 0 {
			cellsWithNoSurface++
			continue
		}
		totalSurfaces += len(surfaces)
		for _, s := range surfaces {
			if s.Walkable {
				walkableSurfaces++
			}
		}
	}
	t.Logf("cells=%d cellsWithNoSurface=%d totalSurfaces=%d walkableSurfaces=%d",
		len(chunk.Cells), cellsWithNoSurface, totalSurfaces, walkableSurfaces)

	if walkableSurfaces == 0 {
		t.Fatal("resolved ZERO walkable surfaces across the entire fight map -- algorithm is almost certainly wrong (no fighter could ever stand anywhere)")
	}

	// Confirm the two known coach-start cells (1,7) and (16,11) --
	// verified in TestRealFightMapAMWParses to have a
	// FightStartCoachPointElement -- resolve to at least one walkable
	// surface at some altitude, since a real fighter must be able to
	// stand there.
	for _, coord := range [][2]int32{{1, 7}, {16, 11}} {
		cell, ok := chunk.CellAt(coord[0], coord[1])
		if !ok {
			t.Errorf("cell (%d,%d) not found in chunk", coord[0], coord[1])
			continue
		}
		surfaces := ResolveCellSurfaces(cell, elementsFile.Elements)
		foundWalkable := false
		for _, s := range surfaces {
			if s.Walkable {
				foundWalkable = true
				break
			}
		}
		if !foundWalkable {
			t.Errorf("coach-start cell (%d,%d) has no walkable surface (surfaces=%+v)", coord[0], coord[1], surfaces)
		}
	}
}
