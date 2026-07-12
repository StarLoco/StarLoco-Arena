package sba

import (
	"archive/zip"
	"fmt"
	"os"
	"sort"
	"testing"
)

// TestDiag_Dump11 dumps the real structure of animations/11.sba so we can see
// exactly what the decoder produces and how Build flattens it. Run with:
//
//	go test -run TestDiag_Dump11 -v ./cmd/studio/sba/...
//
// It skips when the client jar is absent.
func TestDiag_Dump11(t *testing.T) {
	if os.Getenv("DIAG") == "" {
		t.Skip("set DIAG=1 to dump animation structure")
	}
	jar := findClientJar(t, "animations.jar")
	r, err := zip.OpenReader(jar)
	if err != nil {
		t.Fatalf("open: %v", err)
	}
	defer r.Close()
	var f *zip.File
	for _, e := range r.File {
		if e.Name == "animations/11.sba" {
			f = e
			break
		}
	}
	if f == nil {
		t.Skip("animations/11.sba not found")
	}
	data := readZip(t, f)
	m, err := ParseFull(data)
	if err != nil {
		t.Fatalf("ParseFull: %v", err)
	}

	// Symbol inventory.
	ids := make([]int, 0, len(m.Symbols))
	for id := range m.Symbols {
		ids = append(ids, id)
	}
	sort.Ints(ids)
	t.Logf("=== SYMBOLS (root=%d) ===", m.RootID)
	kinds := map[SymbolKind]int{}
	for _, id := range ids {
		s := m.Symbols[id]
		kinds[s.Kind]++
	}
	t.Logf("counts: bitmap=%d seq=%d clip=%d", kinds[KindBitmap], kinds[KindBitmapSequence], kinds[KindMovieClip])

	for _, id := range ids {
		s := m.Symbols[id]
		switch s.Kind {
		case KindBitmap:
			dims := "nil"
			if s.Bitmap != nil {
				dims = fmt.Sprintf("%dx%d", s.Bitmap.Width, s.Bitmap.Height)
			}
			t.Logf("  #%d BITMAP hot=(%d,%d) inv=%.3f %s", id, s.HotX, s.HotY, s.InvertScaling, dims)
		case KindBitmapSequence:
			t.Logf("  #%d SEQUENCE frames=%d inv=%.3f loop=%d", id, len(s.Frames), s.InvertScaling, s.LoopCount)
		case KindMovieClip:
			t.Logf("  #%d CLIP frames=%d loop=%d ops=%d", id, clipFrameCount(s), s.LoopCount, len(s.Ops))
			dumpClipOps(t, s, 6)
		}
	}

	// Root timeline: how many frames, and the ops of the first few frames.
	root := m.Symbols[m.RootID]
	if root != nil && root.Kind == KindMovieClip {
		t.Logf("=== ROOT TIMELINE: %d frames, %d ops ===", clipFrameCount(root), len(root.Ops))
		dumpClipOps(t, root, 40)
	}

	// Final display map for root frame 0 (what should actually be on screen),
	// annotated with each child's kind + frame count.
	if root != nil {
		disp, _ := clipDisplayAt(root, 0)
		ds := make([]int, 0, len(disp))
		for d := range disp {
			ds = append(ds, d)
		}
		sort.Ints(ds)
		t.Logf("=== ROOT FRAME 0 FINAL DISPLAY MAP (%d depths) ===", len(disp))
		for _, d := range ds {
			cid := disp[d].charID
			cs := m.Symbols[cid]
			kind := "?"
			if cs != nil {
				switch cs.Kind {
				case KindBitmap:
					kind = "bitmap"
				case KindBitmapSequence:
					kind = fmt.Sprintf("seq(%d)", len(cs.Frames))
				case KindMovieClip:
					kind = fmt.Sprintf("clip(%d)", clipFrameCount(cs))
				}
			}
			t.Logf("  depth=%d char=%d %s", d, cid, kind)
		}
	}

	// Focused: every op touching depth 3 and 7 (the head/hair) across frames.
	if root != nil {
		t.Logf("=== DEPTH 3 & 7 (head/hair) across frames ===")
		frame := 0
		for _, op := range root.Ops {
			if op.Op == "showframe" {
				frame++
				continue
			}
			if op.Op == "place" && op.Depth == 50 {
				mtx := "(inherit)"
				if op.Matrix != nil {
					a := op.Matrix.Affine()
					mtx = fmt.Sprintf("A[%.3f %.3f %.3f %.3f %.1f %.1f]", a[0], a[1], a[2], a[3], a[4], a[5])
				}
				t.Logf("  [f%d] depth=50 char=%d %s", frame, op.CharID, mtx)
			}
		}
	}

	// Build and dump the composited frames.
	pb := m.Build(m.RootID)
	t.Logf("=== PLAYBACK: %d frames, %d bitmaps ===", len(pb.Frames), len(pb.Bitmaps))
	for fi := 0; fi < len(pb.Frames) && fi < 4; fi++ {
		fr := pb.Frames[fi]
		t.Logf("  frame %d: dur=%d ops=%d", fi, fr.Duration, len(fr.Ops))
		for _, op := range fr.Ops {
			mtx := op.Matrix
			t.Logf("    depth=%d bmp=%d M=[%.2f %.2f %.2f %.2f %.2f %.2f]",
				op.Depth, op.BitmapID, mtx[0], mtx[1], mtx[2], mtx[3], mtx[4], mtx[5])
		}
	}
}

func dumpClipOps(t *testing.T, s *Symbol, maxOps int) {
	t.Helper()
	frame := 0
	shown := 0
	for _, op := range s.Ops {
		if shown >= maxOps {
			t.Logf("      ... (%d more ops)", len(s.Ops)-shown)
			break
		}
		switch op.Op {
		case "place":
			mtx := "-"
			if op.Matrix != nil {
				a := op.Matrix.Affine()
				mtx = fmt.Sprintf("A[%.2f %.2f %.2f %.2f %.1f %.1f]", a[0], a[1], a[2], a[3], a[4], a[5])
			}
			col := ""
			if op.Color != nil {
				col = " +color"
			}
			t.Logf("      [f%d] place char=%d depth=%d %s%s", frame, op.CharID, op.Depth, mtx, col)
		case "remove":
			t.Logf("      [f%d] remove depth=%d", frame, op.Depth)
		case "showframe":
			t.Logf("      [f%d] SHOWFRAME dur=%d", frame, op.Duration)
			frame++
		case "action":
			t.Logf("      [f%d] action", frame)
		}
		shown++
	}
}
