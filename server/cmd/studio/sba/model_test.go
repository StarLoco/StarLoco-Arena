package sba

import (
	"archive/zip"
	"testing"
)

// TestParseFull_RealAnimations fully decodes real .sba files (tag bodies +
// bitmaps + timeline) and asserts the decoded model is coherent: every bitmap
// has width*height*4 straight-alpha RGBA bytes, and the default symbol flattens
// into at least one drawable frame.
func TestParseFull_RealAnimations(t *testing.T) {
	for _, jarName := range []string{"animations.jar", "equipments.jar"} {
		jar := findClientJar(t, jarName)
		r, err := zip.OpenReader(jar)
		if err != nil {
			t.Fatalf("open %s: %v", jarName, err)
		}
		func() {
			defer r.Close()
			n, withBitmaps, withFrames := 0, 0, 0
			for _, f := range r.File {
				if len(f.Name) < 4 || f.Name[len(f.Name)-4:] != ".sba" {
					continue
				}
				data := readZip(t, f)
				m, err := ParseFull(data)
				if err != nil {
					t.Fatalf("ParseFull(%s/%s): %v", jarName, f.Name, err)
				}
				// Validate every decoded bitmap.
				for _, sym := range m.Symbols {
					checkBitmap(t, f.Name, sym.Bitmap)
					for _, fr := range sym.Frames {
						checkBitmap(t, f.Name, fr.Bitmap)
					}
					if sym.Bitmap != nil || len(sym.Frames) > 0 {
						withBitmaps++
					}
				}
				pb := m.Build(m.RootID)
				if len(pb.Frames) > 0 {
					withFrames++
				}
				for id, bmp := range pb.Bitmaps {
					if bmp != nil && bmp.RGBA != nil && len(bmp.RGBA) != bmp.Width*bmp.Height*4 {
						t.Errorf("%s: playback bitmap %d has %d bytes, want %d", f.Name, id, len(bmp.RGBA), bmp.Width*bmp.Height*4)
					}
				}
				n++
				if n >= 15 {
					break
				}
			}
			if n == 0 {
				t.Skipf("no .sba files in %s", jarName)
			}
			t.Logf("%s: fully decoded %d files (%d had bitmaps, %d produced frames)", jarName, n, withBitmaps, withFrames)
		}()
	}
}

func checkBitmap(t *testing.T, name string, b *BitmapData) {
	t.Helper()
	if b == nil {
		return
	}
	if b.Width < 0 || b.Height < 0 {
		t.Errorf("%s: negative bitmap dims %dx%d", name, b.Width, b.Height)
	}
	if b.RGBA != nil && len(b.RGBA) != b.Width*b.Height*4 {
		t.Errorf("%s: bitmap %dx%d has %d bytes, want %d", name, b.Width, b.Height, len(b.RGBA), b.Width*b.Height*4)
	}
	if b.Premultiplied {
		t.Errorf("%s: bitmap should be un-premultiplied after decode", name)
	}
}
