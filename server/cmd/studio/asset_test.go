package main

import (
	"encoding/binary"
	"image"
	"path/filepath"
	"strings"
	"testing"
)

// buildMAGT assembles a minimal valid MAGT/.tgam buffer for the given content
// size, laying pixels out in a next-pow2-padded, top-left-aligned RGBA grid.
// content[y][x] = [4]byte{R,G,B,A}; padding is left zero.
func buildMAGT(w, h int, content [][][4]byte) []byte {
	pw, ph := nextPow2(w), nextPow2(h)
	pixelLen := pw * ph * 4
	maskLen := (w*h + 7) / 8

	b := make([]byte, 16+pixelLen+maskLen)
	copy(b, "MAGT")
	binary.LittleEndian.PutUint16(b[4:], uint16(w))
	binary.LittleEndian.PutUint16(b[6:], uint16(h))
	binary.LittleEndian.PutUint32(b[8:], uint32(pixelLen))
	binary.LittleEndian.PutUint32(b[12:], uint32(maskLen))
	for y := 0; y < h; y++ {
		for x := 0; x < w; x++ {
			o := 16 + (y*pw+x)*4
			p := content[y][x]
			b[o+0], b[o+1], b[o+2], b[o+3] = p[0], p[1], p[2], p[3]
		}
	}
	return b
}

func TestDecodeTGAM_Synthetic(t *testing.T) {
	// 3x2 content forces a paddedW of 4, exercising the row-stride skip.
	content := [][][4]byte{
		{{10, 20, 30, 40}, {11, 21, 31, 41}, {12, 22, 32, 42}},
		{{13, 23, 33, 43}, {14, 24, 34, 44}, {15, 25, 35, 45}},
	}
	img, err := decodeTGAM(buildMAGT(3, 2, content))
	if err != nil {
		t.Fatalf("decodeTGAM: %v", err)
	}
	if b := img.Bounds(); b.Dx() != 3 || b.Dy() != 2 {
		t.Fatalf("bounds = %dx%d, want 3x2", b.Dx(), b.Dy())
	}
	nrgba, ok := img.(*image.NRGBA)
	if !ok {
		t.Fatalf("decodeTGAM returned %T, want *image.NRGBA", img)
	}
	// Compare straight (non-premultiplied) channels via NRGBAAt.
	for y := 0; y < 2; y++ {
		for x := 0; x < 3; x++ {
			got := nrgba.NRGBAAt(x, y)
			want := content[y][x]
			if got.R != want[0] || got.G != want[1] || got.B != want[2] || got.A != want[3] {
				t.Errorf("px(%d,%d)= %d,%d,%d,%d want %v", x, y, got.R, got.G, got.B, got.A, want)
			}
		}
	}
}

func TestDecodeTGAM_BadMagic(t *testing.T) {
	if _, err := decodeTGAM([]byte("XXXX\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00")); err == nil {
		t.Error("expected error for non-MAGT magic")
	}
}

// realClientDir resolves the v2.70 client bundle relative to this package.
// Asset tests skip (not fail) when it is absent.
func realClientDir(t *testing.T) string {
	t.Helper()
	dir := filepath.Join("..", "..", "..", "client", "compiled")
	if !looksLikeClientDir(dir) {
		t.Skipf("v2.70 client bundle not found at %s; skipping real-asset test", dir)
	}
	return dir
}

func TestAssets_RealJars(t *testing.T) {
	dir := realClientDir(t)
	a := NewApp()
	if p := a.SetClientDir(dir); !p.ClientDirValid {
		t.Fatalf("SetClientDir(%q) reported invalid", dir)
	}

	jars, err := a.ListJars()
	if err != nil {
		t.Fatalf("ListJars: %v", err)
	}
	names := map[string]int{}
	for _, j := range jars {
		names[j.Name] = j.EntryCount
	}
	if names["gfx.jar"] == 0 {
		t.Errorf("expected gfx.jar with entries, got %+v", names)
	}
	if names["gui.jar"] == 0 {
		t.Errorf("expected gui.jar with entries, got %+v", names)
	}

	// gfx.jar is all .tgam sprites.
	sprites, err := a.ListSprites()
	if err != nil {
		t.Fatalf("ListSprites: %v", err)
	}
	if len(sprites) == 0 {
		t.Fatal("expected some gfx sprites")
	}

	// Decode the reference sprite verified by hand (MAGT 60x62).
	img, err := a.GetSprite("gfx/2525.tgam")
	if err != nil {
		t.Fatalf("GetSprite(2525): %v", err)
	}
	if img.Width != 60 || img.Height != 62 {
		t.Errorf("sprite 2525 = %dx%d, want 60x62", img.Width, img.Height)
	}
	if !strings.HasPrefix(img.DataURL, "data:image/png;base64,") {
		t.Errorf("sprite 2525 dataURL not a PNG data URL: %.32q", img.DataURL)
	}

	// A standard .tga in gui.jar must preview through the Targa decoder.
	entries, err := a.ListJarEntries("gui.jar")
	if err != nil {
		t.Fatalf("ListJarEntries(gui.jar): %v", err)
	}
	var tga string
	for _, e := range entries {
		if e.Ext == "tga" {
			tga = e.Path
			break
		}
	}
	if tga == "" {
		t.Skip("no .tga entry in gui.jar to preview")
	}
	prev, err := a.PreviewEntry("gui.jar", tga)
	if err != nil {
		t.Fatalf("PreviewEntry(%s): %v", tga, err)
	}
	if prev.Kind != "image" || prev.Width <= 0 || prev.Height <= 0 {
		t.Errorf("preview %s = kind=%s %dx%d, want a sized image", tga, prev.Kind, prev.Width, prev.Height)
	}
}
