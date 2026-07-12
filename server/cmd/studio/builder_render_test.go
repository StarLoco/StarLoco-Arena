package main

import "testing"

// TestBuilderEditRenderPNG exercises the full builder pipeline end-to-end
// against real data+client: load map 2's edit data, mutate cells (paint a
// ground tile onto a patch, erase another patch), then PreviewMapRender and
// write a PNG so the edit result can be eyeballed. Also verifies the edited
// cells actually change the drawable output.
func TestBuilderEditRenderPNG(t *testing.T) {
	a := newAppWithData(t)
	ac := newAppWithClient(t)
	a.paths.ClientDir = ac.paths.ClientDir
	a.paths.ClientDirValid = ac.paths.ClientDirValid

	ed, err := a.GetMapEditData(2)
	if err != nil {
		t.Fatalf("GetMapEditData: %v", err)
	}

	// Find a common ground-tile gfx element to paint with (from the palette).
	pal, err := a.ListPaletteElements()
	if err != nil || len(pal) == 0 {
		t.Fatalf("palette: %v", err)
	}
	// pick a walkable, low element (a floor tile) as the paint brush
	var brush *PaletteElement
	for i := range pal {
		if pal[i].Walkable && pal[i].Height <= 0 {
			brush = &pal[i]
			break
		}
	}
	if brush == nil {
		brush = &pal[0]
	}
	t.Logf("brush element #%d gfx %d", brush.ID, brush.GfxID)

	idx := map[[2]int32]int{}
	for i, c := range ed.Cells {
		idx[[2]int32{c.X, c.Y}] = i
	}

	// Paint the brush onto a 5x5 patch (replace), erase a 3x3 patch.
	painted, erased := 0, 0
	for y := int32(6); y <= 10; y++ {
		for x := int32(6); x <= 10; x++ {
			if i, ok := idx[[2]int32{x, y}]; ok {
				ed.Cells[i].Levels = [][]MapEditElement{{{
					ElementID: brush.ID, State: brush.DefaultState, GfxID: brush.GfxID, Kind: brush.Kind,
				}}}
				painted++
			}
		}
	}
	for y := int32(12); y <= 14; y++ {
		for x := int32(12); x <= 14; x++ {
			if i, ok := idx[[2]int32{x, y}]; ok {
				ed.Cells[i].Levels = nil
				erased++
			}
		}
	}
	t.Logf("painted %d cells, erased %d cells", painted, erased)

	mr, err := a.PreviewMapRender(ed.Cells)
	if err != nil {
		t.Fatalf("PreviewMapRender: %v", err)
	}
	if len(mr.Drawables) == 0 {
		t.Fatal("preview produced no drawables")
	}
	renderMapRenderToPNG(t, a, mr, "map2_edited.png")
}
