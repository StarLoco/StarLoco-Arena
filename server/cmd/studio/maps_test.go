package main

import (
	"image"
	"image/color"
	"image/draw"
	"sort"
	"testing"
)

// TestDebugMapGfxFreq lists the NEGATIVE-gfx marker tiles (Bonus/special-cell
// art baked into the map: skulls/spikes/paws/hearts/eyes) per map, so the
// gfx-id -> gameplay-type mapping can be built from ground truth.
func TestDebugMapGfxFreq(t *testing.T) {
	a := newAppWithData(t)
	ac := newAppWithClient(t)
	a.paths.ClientDir = ac.paths.ClientDir
	a.paths.ClientDirValid = ac.paths.ClientDirValid

	maps, err := a.ListMaps()
	if err != nil {
		t.Fatalf("ListMaps: %v", err)
	}
	for _, m := range maps {
		mr, err := a.GetMapRender(m.ID)
		if err != nil {
			continue
		}
		freq := map[int32]int{}
		cells := map[int32][][2]int32{}
		for _, d := range mr.Drawables {
			if d.GfxID < 0 {
				freq[d.GfxID]++
				cells[d.GfxID] = append(cells[d.GfxID], [2]int32{d.X, d.Y})
			}
		}
		if len(freq) == 0 {
			continue
		}
		var ids []int32
		for g := range freq {
			ids = append(ids, g)
		}
		sort.Slice(ids, func(i, j int) bool { return ids[i] < ids[j] })
		t.Logf("=== map %d ===", m.ID)
		for _, g := range ids {
			t.Logf("   gfx=%d x%d cells=%v", g, freq[g], cells[g])
		}
	}
}

// TestDumpBonusElementSprites decodes each Bonus element's (1002-1009) tile
// sprite into its own PNG under tools/render-out/bonus-<id>.png, to identify
// which special-cell icon each cellBaseId is (skull/spike/heart/eye/...).
func TestDumpBonusElementSprites(t *testing.T) {
	a := newAppWithData(t)
	ac := newAppWithClient(t)
	a.paths.ClientDir = ac.paths.ClientDir
	a.paths.ClientDirValid = ac.paths.ClientDirValid

	elements, err := a.loadElements()
	if err != nil {
		t.Fatalf("loadElements: %v", err)
	}
	// collect the positive gfx id each Bonus element's state 0 references.
	var gfxIDs []int32
	gfxForBase := map[int32]int32{}
	for id := int32(1002); id <= 1009; id++ {
		def, ok := elements[id]
		if !ok {
			t.Logf("element %d not found", id)
			continue
		}
		for _, s := range def.States {
			if s.Properties.GfxID != 0 {
				t.Logf("Bonus element %d state %d -> gfx %d", id, s.State, s.Properties.GfxID)
				gfxForBase[id] = s.Properties.GfxID
				gfxIDs = append(gfxIDs, s.Properties.GfxID)
				break
			}
		}
	}
	sprites := decodeMapSprites(t, a, gfxIDs)
	for base, gid := range gfxForBase {
		img, ok := sprites[gid]
		if !ok {
			t.Logf("no sprite decoded for gfx %d (base %d)", gid, base)
			continue
		}
		rgba := image.NewRGBA(img.Bounds())
		draw.Draw(rgba, rgba.Bounds(), image.NewUniform(color.RGBA{30, 30, 40, 255}), image.Point{}, draw.Src)
		draw.Draw(rgba, rgba.Bounds(), img, img.Bounds().Min, draw.Over)
		writePNG(t, rgba, "bonus-"+itoa(int(base))+".png", rgba.Bounds().Dx(), rgba.Bounds().Dy())
	}
}

func TestListMaps_RealData(t *testing.T) {
	a := newAppWithData(t)
	maps, err := a.ListMaps()
	if err != nil {
		t.Fatalf("ListMaps: %v", err)
	}
	if len(maps) == 0 {
		t.Fatal("expected fight maps, got none")
	}
	foundFight := false
	for _, m := range maps {
		if m.IsFight {
			foundFight = true
		}
		if m.CellCount == 0 {
			t.Errorf("map %d reported 0 cells", m.ID)
		}
	}
	if !foundFight {
		t.Error("expected at least one fight map")
	}
	t.Logf("maps: %d", len(maps))
}

func TestGetMap_RealData(t *testing.T) {
	a := newAppWithData(t)
	// Map 2 is this project's canonical fight map.
	md, err := a.GetMap(2)
	if err != nil {
		t.Fatalf("GetMap(2): %v", err)
	}
	if len(md.Cells) == 0 {
		t.Fatal("map 2 has no cells")
	}
	if md.MaxX < md.MinX || md.MaxY < md.MinY {
		t.Fatalf("bad bounds: x[%d,%d] y[%d,%d]", md.MinX, md.MaxX, md.MinY, md.MaxY)
	}
	// Fight map => must carry fight-start markers for both sides.
	sides := map[byte]int{}
	for _, mk := range md.FightStart {
		sides[mk.Side]++
	}
	if sides[0] == 0 || sides[1] == 0 {
		t.Errorf("expected fight-start cells for both sides, got %v", sides)
	}
	// Coach-start markers should match this project's known cells (16,11)
	// and (1,7) from the roadmap/memory; just assert they exist.
	if len(md.CoachStart) == 0 {
		t.Error("expected coach-start markers")
	}
	// At least some cells must be walkable (a fight needs standable ground).
	walk := 0
	for _, c := range md.Cells {
		if c.Walkable {
			walk++
		}
	}
	if walk == 0 {
		t.Error("map 2 has no walkable cells")
	}
	t.Logf("map 2: %d cells, %d walkable, bounds x[%d,%d] y[%d,%d], fightStart=%d coachStart=%d",
		len(md.Cells), walk, md.MinX, md.MaxX, md.MinY, md.MaxY, len(md.FightStart), len(md.CoachStart))
}

// TestGetMap_EmptyCellsInheritNeighbourAltitude verifies the studio gives
// EMPTY (0-surface) border cells a ground altitude borrowed from surrounding
// real tiles instead of a hard 0, so their overlays don't float above the
// terrain (the reported "cells stick together while far apart" gap).
func TestGetMap_EmptyCellsInheritNeighbourAltitude(t *testing.T) {
	a := newAppWithData(t)
	md, err := a.GetMap(8)
	if err != nil {
		t.Skipf("GetMap(8): %v (map 8 may be absent)", err)
	}

	// Build a lookup of surfaced-cell altitudes to compare against.
	realAlt := map[[2]int32]int16{}
	var emptyChecked int
	for _, c := range md.Cells {
		if c.SurfaceCount > 0 {
			realAlt[[2]int32{c.X, c.Y}] = c.RenderAlt
		}
	}
	for _, c := range md.Cells {
		if c.SurfaceCount != 0 {
			continue
		}
		// If this empty cell has ANY surfaced 4-neighbour, its RenderAlt must
		// be within the neighbourhood's altitude range (not a stray 0 far off).
		lo, hi, found := int16(0), int16(0), false
		for _, d := range [][2]int32{{1, 0}, {-1, 0}, {0, 1}, {0, -1}} {
			if a2, ok := realAlt[[2]int32{c.X + d[0], c.Y + d[1]}]; ok {
				if !found {
					lo, hi, found = a2, a2, true
				} else {
					if a2 < lo {
						lo = a2
					}
					if a2 > hi {
						hi = a2
					}
				}
			}
		}
		if !found {
			continue
		}
		emptyChecked++
		// Allow a small margin since we average a wider ring, but it must be
		// nowhere near a hard 0 when neighbours sit at e.g. -11.
		if c.RenderAlt < lo-4 || c.RenderAlt > hi+4 {
			t.Errorf("empty cell (%d,%d) renderAlt=%d not within neighbour range [%d,%d]", c.X, c.Y, c.RenderAlt, lo, hi)
		}
	}
	if emptyChecked == 0 {
		t.Skip("map 8 exposed no empty cells adjacent to surfaced tiles")
	}
	t.Logf("map 8: checked %d empty border cells inherit neighbour altitude", emptyChecked)
}
