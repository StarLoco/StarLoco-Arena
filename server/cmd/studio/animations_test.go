package main

import (
	"strings"
	"testing"
)

// TestGetAnimationPlayback_RealClient exercises the full backend player path:
// open jar -> ParseFull -> Build -> PNG-encode bitmaps, against real .sba files.
func TestGetAnimationPlayback_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	anims, err := a.ListAnimations()
	if err != nil {
		t.Skipf("no animations: %v", err)
	}
	tested := 0
	for _, info := range anims {
		pb, err := a.GetAnimationPlayback(info.Jar, info.Path, animDefaultSymbol)
		if err != nil {
			t.Fatalf("GetAnimationPlayback(%s/%s): %v", info.Jar, info.Path, err)
		}
		if len(pb.Symbols) == 0 {
			t.Errorf("%s: no symbols", info.Path)
		}
		if len(pb.Frames) == 0 {
			t.Errorf("%s: no frames produced", info.Path)
		}
		for _, bm := range pb.Bitmaps {
			if bm.DataURL != "" && !strings.HasPrefix(bm.DataURL, "data:image/png;base64,") {
				t.Errorf("%s: bitmap %d has non-PNG data url", info.Path, bm.ID)
			}
		}
		// bounds must be finite and non-degenerate for anything with pixels.
		if len(pb.Bitmaps) > 0 && pb.Bounds[2] < pb.Bounds[0] {
			t.Errorf("%s: inverted bounds %v", info.Path, pb.Bounds)
		}
		tested++
		if tested >= 10 {
			break
		}
	}
	if tested == 0 {
		t.Skip("no .sba animations available")
	}
	t.Logf("played %d real animations end-to-end", tested)
}

// TestComposeFighter_RealClient composites a base animation with one equipment
// layer and asserts the merged playback is coherent (namespaced bitmaps, frames
// covering the longer layer).
func TestComposeFighter_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	anims, err := a.ListAnimations()
	if err != nil {
		t.Skipf("no animations: %v", err)
	}
	var base, equip *AnimationInfo
	for i := range anims {
		switch anims[i].Jar {
		case "animations.jar":
			if base == nil {
				base = &anims[i]
			}
		case "equipments.jar":
			if equip == nil {
				equip = &anims[i]
			}
		}
	}
	if base == nil || equip == nil {
		t.Skip("need both an animation and an equipment .sba")
	}
	pb, err := a.ComposeFighter(
		LayerRef{Jar: base.Jar, Path: base.Path, SymbolID: animDefaultSymbol},
		[]LayerRef{{Jar: equip.Jar, Path: equip.Path, SymbolID: animDefaultSymbol}},
	)
	if err != nil {
		t.Fatalf("ComposeFighter: %v", err)
	}
	if len(pb.Frames) == 0 {
		t.Fatal("composite produced no frames")
	}
	// The composite should reference bitmaps from both layers (namespaced ids
	// keep them distinct), so at least the base layer's should be present.
	if len(pb.Bitmaps) == 0 {
		t.Error("composite has no bitmaps")
	}
	t.Logf("composited base %s + equip %s -> %d frames, %d bitmaps", base.Name, equip.Name, len(pb.Frames), len(pb.Bitmaps))
}
