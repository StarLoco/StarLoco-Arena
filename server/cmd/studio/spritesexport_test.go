package main

import (
	"image/png"
	"io/fs"
	"os"
	"path/filepath"
	"strings"
	"testing"
)

func TestExportSprites_RealJar(t *testing.T) {
	dir := realClientDir(t)
	a := NewApp()
	a.SetClientDir(dir)

	out := t.TempDir()
	res, err := a.exportSpritesTo(out, 25, false)
	if err != nil {
		t.Fatalf("exportSpritesTo: %v", err)
	}
	if res.Total != 25 || res.Written != 25 || res.Failed != 0 {
		t.Fatalf("res = %+v, want total/written 25, failed 0", res)
	}

	var pngs []string
	_ = filepath.WalkDir(out, func(p string, d fs.DirEntry, _ error) error {
		if d != nil && !d.IsDir() && strings.EqualFold(filepath.Ext(p), ".png") {
			pngs = append(pngs, p)
		}
		return nil
	})
	if len(pngs) != 25 {
		t.Fatalf("expected 25 PNGs on disk, found %d", len(pngs))
	}
	// The paths must mirror the jar (under a gfx/ subfolder).
	if !strings.Contains(filepath.ToSlash(pngs[0]), "/gfx/") {
		t.Errorf("expected mirrored gfx/ path, got %s", pngs[0])
	}
	// The produced file must be a valid PNG.
	f, err := os.Open(pngs[0])
	if err != nil {
		t.Fatal(err)
	}
	defer f.Close()
	if _, err := png.Decode(f); err != nil {
		t.Errorf("produced file is not a valid PNG: %v", err)
	}
}
