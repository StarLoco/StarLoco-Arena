package main

import (
	"image"
	"image/color"
	"strings"
	"testing"
)

// buildTGA constructs a minimal uncompressed 32bpp BGRA TGA (type 2) with
// the given pixels (row-major, top-left origin via descriptor bit5) for
// decoder unit tests independent of the real asset files.
func buildTGA(width, height int, topOrigin bool, pixels []color.NRGBA) []byte {
	desc := byte(0)
	if topOrigin {
		desc = 0x20
	}
	h := []byte{
		0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		byte(width), byte(width >> 8),
		byte(height), byte(height >> 8),
		32, desc,
	}
	buf := make([]byte, 0, len(h)+width*height*4)
	buf = append(buf, h...)
	for _, p := range pixels {
		buf = append(buf, p.B, p.G, p.R, p.A) // BGRA
	}
	return buf
}

func TestDecodeTGA_TopOrigin(t *testing.T) {
	// 2x1: red then green, top-origin so no vertical flip.
	px := []color.NRGBA{
		{R: 255, A: 255}, {G: 255, A: 255},
	}
	img, err := decodeTGA(buildTGA(2, 1, true, px))
	if err != nil {
		t.Fatalf("decodeTGA: %v", err)
	}
	if got := img.Bounds(); got.Dx() != 2 || got.Dy() != 1 {
		t.Fatalf("bounds = %v, want 2x1", got)
	}
	assertPixel(t, img, 0, 0, color.NRGBA{R: 255, A: 255})
	assertPixel(t, img, 1, 0, color.NRGBA{G: 255, A: 255})
}

func TestDecodeTGA_BottomOriginFlips(t *testing.T) {
	// 1x2 bottom-origin: file row0 is the BOTTOM image row, so it should
	// land at y=1 after the flip.
	px := []color.NRGBA{
		{B: 255, A: 255}, // file row0 -> image y=1
		{R: 255, A: 255}, // file row1 -> image y=0
	}
	img, err := decodeTGA(buildTGA(1, 2, false, px))
	if err != nil {
		t.Fatalf("decodeTGA: %v", err)
	}
	assertPixel(t, img, 0, 0, color.NRGBA{R: 255, A: 255})
	assertPixel(t, img, 0, 1, color.NRGBA{B: 255, A: 255})
}

func TestDecodeTGA_RejectsColorMapped(t *testing.T) {
	data := make([]byte, 18)
	data[1] = 1 // colorMapType != 0
	data[2] = 1 // colour-mapped type
	data[12], data[14], data[16] = 1, 1, 32
	if _, err := decodeTGA(data); err == nil {
		t.Fatal("expected error for colour-mapped TGA")
	}
}

func TestDecodeTGA_RLE(t *testing.T) {
	// 4x1 RLE type-10: one run packet of 4x blue (32bpp).
	blue := []byte{255, 0, 0, 255} // BGRA
	data := []byte{0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 1, 0, 32, 0x20}
	data = append(data, 0x83) // run packet, count = 3+1 = 4
	data = append(data, blue...)
	img, err := decodeTGA(data)
	if err != nil {
		t.Fatalf("decodeTGA RLE: %v", err)
	}
	for x := 0; x < 4; x++ {
		assertPixel(t, img, x, 0, color.NRGBA{B: 255, A: 255})
	}
}

// assertPixel compares an image pixel to an expected NRGBA.
func assertPixel(t *testing.T, img image.Image, x, y int, want color.NRGBA) {
	t.Helper()
	r, g, b, a := img.At(x, y).RGBA()
	got := color.NRGBA{R: uint8(r >> 8), G: uint8(g >> 8), B: uint8(b >> 8), A: uint8(a >> 8)}
	if got != want {
		t.Errorf("pixel (%d,%d) = %+v, want %+v", x, y, got, want)
	}
}

// --- real-data integration: decode actual gfx.jar sprites ---

func TestListAndGetSprite_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	sprites, err := a.ListSprites()
	if err != nil {
		t.Fatalf("ListSprites: %v", err)
	}
	if len(sprites) == 0 {
		t.Fatal("expected sprites in gfx.jar")
	}
	// Decode the first several to prove the real BGRA/32bpp files decode.
	n := 0
	for _, s := range sprites {
		img, err := a.GetSprite(s.Path)
		if err != nil {
			t.Fatalf("GetSprite(%s): %v", s.Path, err)
		}
		if img.Width <= 0 || img.Height <= 0 {
			t.Errorf("sprite %s decoded to %dx%d", s.Path, img.Width, img.Height)
		}
		if !strings.HasPrefix(img.DataURL, "data:image/png;base64,") {
			t.Errorf("sprite %s missing png data url", s.Path)
		}
		n++
		if n >= 12 {
			break
		}
	}
	t.Logf("decoded %d/%d gfx sprites OK", n, len(sprites))
}
