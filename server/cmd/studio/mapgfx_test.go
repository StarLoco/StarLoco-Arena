package main

import (
	"path/filepath"
	"strings"
	"testing"
)

// TestParseGfxTile_World5 verifies the gfx scene decoder against the client's
// own kC.load: gfx/5.jar!/0_-1 begins with two typeTag=2 elements — sprite
// 12017 at cell (8,1) altitude 10, and sprite 10444 at cell (8,2) altitude 3.
func TestParseGfxTile_World5(t *testing.T) {
	dir := mapsTestDir(t)
	data := readZipEntryByName(t, filepath.Join(dir, "gfx", "5.jar"), "0_-1")
	els, err := parseGfxTile(data)
	if err != nil {
		t.Fatalf("parseGfxTile: %v", err)
	}
	if len(els) < 2 {
		t.Fatalf("expected >=2 elements, got %d", len(els))
	}
	if e := els[0]; e.CellX != 8 || e.CellY != 1 || e.Alt != 10 || e.SpriteID != 12017 {
		t.Errorf("el[0] = %+v, want cell(8,1) alt10 sprite12017", e)
	}
	if e := els[1]; e.CellX != 8 || e.CellY != 2 || e.Alt != 3 || e.SpriteID != 10444 {
		t.Errorf("el[1] = %+v, want cell(8,2) alt3 sprite10444", e)
	}
}

func TestGetMapGfx_World5(t *testing.T) {
	dir := realClientDir(t)
	a := NewApp()
	a.SetClientDir(dir)

	dto, err := a.GetMapGfx(5)
	if err != nil {
		t.Fatalf("GetMapGfx: %v", err)
	}
	if dto.Error != "" {
		t.Fatalf("GetMapGfx error: %s", dto.Error)
	}
	if len(dto.Drawables) == 0 {
		t.Fatal("expected drawables for world 5")
	}
	// Drawables must be z-sorted (cellY, cellX, order) ascending.
	for i := 1; i < len(dto.Drawables); i++ {
		p, c := dto.Drawables[i-1], dto.Drawables[i]
		if p.CellY > c.CellY {
			t.Fatalf("drawables not sorted by cellY at %d", i)
		}
	}

	// Resolve the referenced sprite bitmaps.
	ids := make([]int, 0, len(dto.Drawables))
	for _, d := range dto.Drawables {
		ids = append(ids, d.GfxID)
	}
	sprites, err := a.GetMapSprites(ids)
	if err != nil {
		t.Fatalf("GetMapSprites: %v", err)
	}
	if len(sprites) == 0 {
		t.Fatal("expected some decoded sprite bitmaps")
	}
	s := sprites[0]
	if s.DataURL == "" || s.W <= 0 || s.H <= 0 {
		t.Errorf("bad sprite: %+v", s)
	}
}

func TestGetMapRender_RealClient(t *testing.T) {
	dir := realClientDir(t)
	a := NewApp()
	a.SetClientDir(dir)
	// Includes the huge overworlds 0 & 1 — they must now render (downscaled),
	// not be skipped.
	for _, id := range []int{0, 1, 2, 5} {
		rd, err := a.GetMapRender(id)
		if err != nil {
			t.Fatalf("GetMapRender(%d): %v", id, err)
		}
		if rd.Empty || rd.Error != "" {
			t.Fatalf("world %d: empty=%v err=%q", id, rd.Empty, rd.Error)
		}
		if !strings.HasPrefix(rd.DataURL, "data:image/png;base64,") || len(rd.DataURL) < 1000 {
			t.Errorf("world %d: not a valid PNG data URL (len=%d)", id, len(rd.DataURL))
		}
		if rd.WorldW <= 0 || rd.WorldH <= 0 {
			t.Errorf("world %d: bad world rect %.0fx%.0f", id, rd.WorldW, rd.WorldH)
		}
	}
}
