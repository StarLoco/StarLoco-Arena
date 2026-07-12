package main

import "testing"

// appWithBothDirs points an App at both the real data and client dirs
// (needed because map rendering reads .amw/elements.ade from data/ AND
// decodes gfx from client-compiled/).
func appWithBothDirs(t *testing.T) *App {
	t.Helper()
	a := newAppWithData(t) // skips if data absent
	// Attach the client dir too (skips if absent).
	ac := newAppWithClient(t)
	a.paths.ClientDir = ac.paths.ClientDir
	a.paths.ClientDirValid = ac.paths.ClientDirValid
	return a
}

func TestGetMapRender_RealData(t *testing.T) {
	a := newAppWithData(t)
	mr, err := a.GetMapRender(2)
	if err != nil {
		t.Fatalf("GetMapRender(2): %v", err)
	}
	if len(mr.Drawables) == 0 {
		t.Fatal("map 2 produced no drawables")
	}
	if len(mr.GfxIDs) == 0 {
		t.Fatal("map 2 produced no gfx ids")
	}
	// Every drawable must reference a real gfx id and have a plausible order.
	gfxNonZero := 0
	for _, d := range mr.Drawables {
		if d.GfxID != 0 {
			gfxNonZero++
		}
	}
	if gfxNonZero != len(mr.Drawables) {
		t.Errorf("expected all drawables to have gfx: %d/%d", gfxNonZero, len(mr.Drawables))
	}
	if mr.MaxX < mr.MinX || mr.MaxY < mr.MinY {
		t.Errorf("bad bounds x[%d,%d] y[%d,%d]", mr.MinX, mr.MaxX, mr.MinY, mr.MaxY)
	}
	t.Logf("map 2: %d drawables, %d unique gfx, bounds x[%d,%d] y[%d,%d]",
		len(mr.Drawables), len(mr.GfxIDs), mr.MinX, mr.MaxX, mr.MinY, mr.MaxY)
}

func TestGetMapGfxBatch_RealData(t *testing.T) {
	a := appWithBothDirs(t)
	mr, err := a.GetMapRender(2)
	if err != nil {
		t.Fatalf("GetMapRender: %v", err)
	}
	// Decode the first chunk of gfx ids to keep the test quick.
	ids := mr.GfxIDs
	if len(ids) > 30 {
		ids = ids[:30]
	}
	batch, err := a.GetMapGfxBatch(ids)
	if err != nil {
		t.Fatalf("GetMapGfxBatch: %v", err)
	}
	if len(batch) == 0 {
		t.Fatal("no gfx decoded")
	}
	for _, g := range batch {
		if g.Width <= 0 || g.Height <= 0 {
			t.Errorf("gfx %d decoded to %dx%d", g.GfxID, g.Width, g.Height)
		}
		if len(g.DataURL) < 30 || g.DataURL[:22] != "data:image/png;base64," {
			t.Errorf("gfx %d bad data url", g.GfxID)
		}
	}
	t.Logf("decoded %d/%d map gfx sprites", len(batch), len(ids))
}

func TestGetMapRender_NoDataDir(t *testing.T) {
	a := &App{}
	if _, err := a.GetMapRender(2); err == nil {
		t.Fatal("expected error with no data dir")
	}
}
