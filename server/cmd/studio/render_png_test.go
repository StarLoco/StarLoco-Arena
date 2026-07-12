package main

import (
	"archive/zip"
	"fmt"
	"image"
	"image/color"
	"image/draw"
	"image/png"
	"math"
	"os"
	"path/filepath"
	"sort"
	"testing"
)

// sortDrawablesZ orders drawables by the client's exact single continuous
// z-value (DisplayedCell.updateDisplayedElements): zValue = (x+y)*cellHeight/2
// + altitudeOrder, ascending, with the file `Order` as the exact-tie breaker.
// Mirrors the frontend's sort so the headless render matches the viewer.
func sortDrawablesZ(d []MapDrawable) {
	zv := func(m MapDrawable) float64 {
		return float64(m.X+m.Y)*tileHY + float64(m.AltitudeOrder)
	}
	sort.SliceStable(d, func(i, j int) bool {
		zi, zj := zv(d[i]), zv(d[j])
		if zi != zj {
			return zi < zj
		}
		return d[i].Order < d[j].Order
	})
}

// This test is a HEADLESS PNG renderer of a map, reproducing the frontend's
// drawTiles() logic exactly, so map rendering can be inspected/iterated as an
// actual image (written to tools/render-out/) instead of guessing. Run with:
//
//	go test ./cmd/studio/ -run RenderMapPNG -v
//
// It is a dev/diagnostic tool, not a CI assertion (it only writes a file).

const (
	tileHX        = 43.0 // cellWidth/2
	tileHY        = 21.5 // cellHeight/2
	elevationUnit = 10.0
)

func TestRenderMapPNG(t *testing.T) {
	a := newAppWithData(t)    // skips if data absent
	ac := newAppWithClient(t) // skips if client absent
	a.paths.ClientDir = ac.paths.ClientDir
	a.paths.ClientDirValid = ac.paths.ClientDirValid

	mapID := 2
	if v := os.Getenv("RENDER_MAP"); v != "" {
		if n, err := strconvAtoi(v); err == nil {
			mapID = n
		}
	}
	mr, err := a.GetMapRender(mapID)
	if err != nil {
		t.Fatalf("GetMapRender: %v", err)
	}

	renderMapRenderToPNG(t, a, mr, "map"+itoa(mapID)+".png")
}

// TestRenderMapOverlayPNG renders a map's TILES plus the walkable /
// non-walkable overlay diamonds (reproducing the frontend maps.ts overlay +
// drawDiamond logic incl. the renderAlt lift), so overlay alignment can be
// eyeballed as a real image instead of asking the user for screenshots. Set
// OVERLAY_MAP to change the map (default 8). Writes tools/render-out/mapN-overlay.png.
func TestRenderMapOverlayPNG(t *testing.T) {
	a := newAppWithData(t)
	ac := newAppWithClient(t)
	a.paths.ClientDir = ac.paths.ClientDir
	a.paths.ClientDirValid = ac.paths.ClientDirValid

	mapID := 8
	if v := os.Getenv("OVERLAY_MAP"); v != "" {
		if n, err := strconvAtoi(v); err == nil {
			mapID = n
		}
	}
	mr, err := a.GetMapRender(mapID)
	if err != nil {
		t.Fatalf("GetMapRender(%d): %v", mapID, err)
	}
	md, err := a.GetMap(mapID)
	if err != nil {
		t.Fatalf("GetMap(%d): %v", mapID, err)
	}
	special, _ := a.GetSpecialCells(mapID)
	t.Logf("map %d special cells: %d", mapID, len(special))
	for _, sc := range special {
		t.Logf("   special %q at (%d,%d)", sc.Type, sc.X, sc.Y)
	}
	renderMapWithOverlayToPNG(t, a, mr, md, special, "map"+itoa(mapID)+"-overlay.png")
}

// renderMapRenderToPNG decodes a MapRender's gfx and paints it to a PNG under
// tools/render-out/, using the exact frontend drawTiles projection/sort/anchor.
// Reused by the builder edit test.
func renderMapRenderToPNG(t *testing.T, a *App, mr MapRender, name string) {
	t.Helper()
	sprites := decodeMapSprites(t, a, mr.GfxIDs)
	t.Logf("%s: %d drawables, %d/%d gfx decoded", name, len(mr.Drawables), len(sprites), len(mr.GfxIDs))

	drawables := append([]MapDrawable(nil), mr.Drawables...)
	sortDrawablesZ(drawables)

	minX, minY := math.Inf(1), math.Inf(1)
	maxX, maxY := math.Inf(-1), math.Inf(-1)
	project := func(x, y int32) (float64, float64) {
		return float64(x-y) * tileHX, float64(x+y) * tileHY
	}
	for _, d := range drawables {
		img, ok := sprites[d.GfxID]
		if !ok {
			continue
		}
		px, py := project(d.X, d.Y)
		sy := py - float64(d.Altitude)*elevationUnit
		left := px - float64(d.OriginX)
		top := sy - float64(d.OriginY)
		minX = math.Min(minX, left)
		minY = math.Min(minY, top)
		maxX = math.Max(maxX, left+float64(img.Bounds().Dx()))
		maxY = math.Max(maxY, top+float64(img.Bounds().Dy()))
	}
	if math.IsInf(minX, 1) {
		t.Skip("no drawables to render")
	}

	const pad = 20
	W := int(maxX-minX) + pad*2
	H := int(maxY-minY) + pad*2
	if W <= 0 || H <= 0 || W > 12000 || H > 12000 {
		t.Fatalf("implausible canvas %dx%d", W, H)
	}
	offX := -minX + pad
	offY := -minY + pad

	canvas := image.NewRGBA(image.Rect(0, 0, W, H))
	draw.Draw(canvas, canvas.Bounds(), image.NewUniform(color.RGBA{15, 17, 23, 255}), image.Point{}, draw.Src)

	for _, d := range drawables {
		img, ok := sprites[d.GfxID]
		if !ok {
			continue
		}
		px, py := project(d.X, d.Y)
		sy := py - float64(d.Altitude)*elevationUnit
		left := int(math.Round(px - float64(d.OriginX) + offX))
		top := int(math.Round(sy - float64(d.OriginY) + offY))
		src := img
		if d.Flip {
			src = flipH(img)
		}
		r := image.Rect(left, top, left+src.Bounds().Dx(), top+src.Bounds().Dy())
		draw.Draw(canvas, r, src, src.Bounds().Min, draw.Over)
	}

	// Optional 2x crop around a cell (CROP="x,y") to inspect a region closely.
	if cc := os.Getenv("CROP"); cc != "" {
		var cxg, cyg int
		if _, err := fmt.Sscanf(cc, "%d,%d", &cxg, &cyg); err == nil {
			px, py := project(int32(cxg), int32(cyg))
			ccx := int(px + offX)
			ccy := int(py + offY)
			const cw, ch = 220, 180
			crop := image.NewRGBA(image.Rect(0, 0, cw*2, ch*2))
			for yy := 0; yy < ch*2; yy++ {
				for xx := 0; xx < cw*2; xx++ {
					sxp := ccx - cw + xx/2
					syp := ccy - ch + yy/2
					if sxp >= 0 && sxp < W && syp >= 0 && syp < H {
						crop.Set(xx, yy, canvas.At(sxp, syp))
					}
				}
			}
			writePNG(t, crop, name[:len(name)-4]+"-crop.png", cw*2, ch*2)
		}
	}

	writePNG(t, canvas, name, W, H)
}

func strconvAtoi(s string) (int, error) {
	n := 0
	neg := false
	for i, c := range s {
		if i == 0 && c == '-' {
			neg = true
			continue
		}
		if c < '0' || c > '9' {
			return 0, os.ErrInvalid
		}
		n = n*10 + int(c-'0')
	}
	if neg {
		n = -n
	}
	return n, nil
}

// renderMapWithOverlayToPNG paints the map tiles, then overlays the analytical
// walkable/non-walkable diamonds exactly as the frontend does (renderAlt lift,
// green=walkable, solid red=blocked terrain, thin red outline=empty border
// cell). This is the headless equivalent of the viewer with Tiles + Walkable +
// Non-walkable enabled.
func renderMapWithOverlayToPNG(t *testing.T, a *App, mr MapRender, md MapData, special []SpecialCellDTO, name string) {
	t.Helper()
	sprites := decodeMapSprites(t, a, mr.GfxIDs)

	drawables := append([]MapDrawable(nil), mr.Drawables...)
	sortDrawablesZ(drawables)

	project := func(x, y int32) (float64, float64) {
		return float64(x-y) * tileHX, float64(x+y) * tileHY
	}

	// Bounds over BOTH tiles and overlay diamonds so nothing is clipped.
	minX, minY := math.Inf(1), math.Inf(1)
	maxX, maxY := math.Inf(-1), math.Inf(-1)
	grow := func(l, tp, r, b float64) {
		minX, minY = math.Min(minX, l), math.Min(minY, tp)
		maxX, maxY = math.Max(maxX, r), math.Max(maxY, b)
	}
	for _, d := range drawables {
		img, ok := sprites[d.GfxID]
		if !ok {
			continue
		}
		px, py := project(d.X, d.Y)
		sy := py - float64(d.Altitude)*elevationUnit
		l := px - float64(d.OriginX)
		tp := sy - float64(d.OriginY)
		grow(l, tp, l+float64(img.Bounds().Dx()), tp+float64(img.Bounds().Dy()))
	}
	for _, c := range md.Cells {
		px, py := project(c.X, c.Y)
		cy := py - float64(c.RenderAlt)*elevationUnit
		grow(px-tileHX, cy-tileHY, px+tileHX, cy+tileHY)
	}
	if math.IsInf(minX, 1) {
		t.Skip("no drawables to render")
	}

	const pad = 20
	W := int(maxX-minX) + pad*2
	H := int(maxY-minY) + pad*2
	if W <= 0 || H <= 0 || W > 12000 || H > 12000 {
		t.Fatalf("implausible canvas %dx%d", W, H)
	}
	offX, offY := -minX+pad, -minY+pad
	canvas := image.NewRGBA(image.Rect(0, 0, W, H))
	draw.Draw(canvas, canvas.Bounds(), image.NewUniform(color.RGBA{15, 17, 23, 255}), image.Point{}, draw.Src)

	// 1) tiles
	for _, d := range drawables {
		img, ok := sprites[d.GfxID]
		if !ok {
			continue
		}
		px, py := project(d.X, d.Y)
		sy := py - float64(d.Altitude)*elevationUnit
		left := int(math.Round(px - float64(d.OriginX) + offX))
		top := int(math.Round(sy - float64(d.OriginY) + offY))
		src := img
		if d.Flip {
			src = flipH(img)
		}
		r := image.Rect(left, top, left+src.Bounds().Dx(), top+src.Bounds().Dy())
		draw.Draw(canvas, r, src, src.Bounds().Min, draw.Over)
	}

	// 2) overlay diamonds (walkable + non-walkable), lifted by renderAlt.
	// EMPTY (0-surface) off-map slots are skipped -- matching the frontend --
	// since they have no tile to sit on and just add floating noise.
	for _, c := range md.Cells {
		if c.SurfaceCount == 0 {
			continue
		}
		px, py := project(c.X, c.Y)
		cx := px + offX
		cy := py - c.TopAlt*elevationUnit + offY
		var fill *color.RGBA
		if c.Walkable {
			fill = &color.RGBA{62, 207, 142, 115}
		} else {
			fill = &color.RGBA{224, 64, 64, 150} // blocked terrain
		}
		fillDiamond(canvas, cx, cy, tileHX, tileHY, fill, nil)
	}

	// 3) special cells (from specialcells.json), lifted by the same renderAlt
	// as the frontend (altAt). Drawn bright magenta so they stand out; this
	// reproduces exactly where the viewer places them.
	altOf := map[[2]int32]int16{}
	for _, c := range md.Cells {
		altOf[[2]int32{c.X, c.Y}] = c.RenderAlt
	}
	for _, sc := range special {
		px, py := project(sc.X, sc.Y)
		cx := px + offX
		cy := py - float64(altOf[[2]int32{sc.X, sc.Y}])*elevationUnit + offY
		fillDiamond(canvas, cx, cy, tileHX, tileHY, &color.RGBA{255, 0, 255, 170}, &color.RGBA{255, 255, 255, 255})
	}

	// 3b) MARKER cells = tiles whose resolved gfx is a Bonus/special marker
	// (negative gfx id, e.g. -1004/-1008/-1009). These are baked into the map
	// art. Drawn as a CYAN outline to distinguish from the json special cells.
	if os.Getenv("MARK_NEG_GFX") != "" {
		markerCells := map[[2]int32]bool{}
		for _, d := range mr.Drawables {
			if d.GfxID < 0 {
				markerCells[[2]int32{d.X, d.Y}] = true
			}
		}
		for cell := range markerCells {
			px, py := project(cell[0], cell[1])
			cx := px + offX
			cy := py - float64(altOf[cell])*elevationUnit + offY
			fillDiamond(canvas, cx, cy, tileHX, tileHY, nil, &color.RGBA{0, 255, 255, 255})
		}
	}

	// Optional 2x zoomed crop around a cell (CROP_CELL="x,y") for close
	// alignment inspection, written as <name>-crop.png.
	if cc := os.Getenv("CROP_CELL"); cc != "" {
		var cxg, cyg int
		if _, err := fmt.Sscanf(cc, "%d,%d", &cxg, &cyg); err == nil {
			px, py := project(int32(cxg), int32(cyg))
			// centre of the crop in canvas space (use ground py; good enough)
			ccx := int(px + offX)
			ccy := int(py + offY)
			const cw, ch = 260, 200
			crop := image.NewRGBA(image.Rect(0, 0, cw*2, ch*2))
			for yy := 0; yy < ch*2; yy++ {
				for xx := 0; xx < cw*2; xx++ {
					srcX := ccx - cw + xx/2
					srcY := ccy - ch + yy/2
					if srcX >= 0 && srcX < W && srcY >= 0 && srcY < H {
						crop.Set(xx, yy, canvas.At(srcX, srcY))
					}
				}
			}
			writePNG(t, crop, name[:len(name)-4]+"-crop.png", cw*2, ch*2)
		}
	}

	writePNG(t, canvas, name, W, H)
}

// fillDiamond scan-fills (and optionally outlines) a cell diamond centred at
// (cx,cy) with half-extents (hx,hy) using alpha-over compositing.
func fillDiamond(dst *image.RGBA, cx, cy, hx, hy float64, fill, stroke *color.RGBA) {
	x0 := int(math.Floor(cx - hx))
	x1 := int(math.Ceil(cx + hx))
	y0 := int(math.Floor(cy - hy))
	y1 := int(math.Ceil(cy + hy))
	inside := func(px, py float64) bool {
		return math.Abs(px-cx)/hx+math.Abs(py-cy)/hy <= 1.0
	}
	for py := y0; py <= y1; py++ {
		for px := x0; px <= x1; px++ {
			if !dst.Rect.Empty() && (px < dst.Rect.Min.X || px >= dst.Rect.Max.X || py < dst.Rect.Min.Y || py >= dst.Rect.Max.Y) {
				continue
			}
			pf, pyf := float64(px)+0.5, float64(py)+0.5
			if fill != nil && inside(pf, pyf) {
				over(dst, px, py, *fill)
				continue
			}
			if stroke != nil {
				// crude edge test: inside the diamond but within ~1px of an edge
				d := math.Abs(pf-cx)/hx + math.Abs(pyf-cy)/hy
				if d <= 1.0 && d >= 1.0-2.0/hx {
					over(dst, px, py, *stroke)
				}
			}
		}
	}
}

// over composites src over dst[px,py] using src alpha.
func over(dst *image.RGBA, px, py int, src color.RGBA) {
	i := dst.PixOffset(px, py)
	if i < 0 || i+3 >= len(dst.Pix) {
		return
	}
	a := float64(src.A) / 255
	dst.Pix[i] = uint8(float64(src.R)*a + float64(dst.Pix[i])*(1-a))
	dst.Pix[i+1] = uint8(float64(src.G)*a + float64(dst.Pix[i+1])*(1-a))
	dst.Pix[i+2] = uint8(float64(src.B)*a + float64(dst.Pix[i+2])*(1-a))
	dst.Pix[i+3] = 255
}

func writePNG(t *testing.T, canvas *image.RGBA, name string, w, h int) {
	t.Helper()
	outDir := filepath.Join("..", "..", "..", "tools", "render-out")
	os.MkdirAll(outDir, 0o755)
	outPath := filepath.Join(outDir, name)
	f, err := os.Create(outPath)
	if err != nil {
		t.Fatalf("create png: %v", err)
	}
	defer f.Close()
	if err := png.Encode(f, canvas); err != nil {
		t.Fatalf("encode: %v", err)
	}
	abs, _ := filepath.Abs(outPath)
	t.Logf("wrote %s (%dx%d)", abs, w, h)
}

// decodeMapSprites decodes every gfx id from gfx.jar into an image.Image.
func decodeMapSprites(t *testing.T, a *App, ids []int32) map[int32]image.Image {
	t.Helper()
	dir, err := a.contentsDir()
	if err != nil {
		t.Fatalf("contentsDir: %v", err)
	}
	r, err := zip.OpenReader(filepath.Join(dir, "gfx.jar"))
	if err != nil {
		t.Fatalf("open gfx.jar: %v", err)
	}
	defer r.Close()
	byName := map[string]*zip.File{}
	for _, f := range r.File {
		byName[f.Name] = f
	}
	out := map[int32]image.Image{}
	for _, id := range ids {
		f := byName[sprintfGfx(id)]
		if f == nil {
			continue
		}
		rc, err := f.Open()
		if err != nil {
			continue
		}
		raw := make([]byte, f.UncompressedSize64)
		readFull(rc, raw)
		rc.Close()
		img, err := decodeTGA(raw)
		if err != nil {
			continue
		}
		out[id] = img
	}
	return out
}

func sprintfGfx(id int32) string {
	return "gfx/" + itoa(int(id)) + ".tga"
}

func itoa(n int) string {
	if n == 0 {
		return "0"
	}
	neg := n < 0
	if neg {
		n = -n
	}
	var b [12]byte
	i := len(b)
	for n > 0 {
		i--
		b[i] = byte('0' + n%10)
		n /= 10
	}
	if neg {
		i--
		b[i] = '-'
	}
	return string(b[i:])
}

func readFull(r interface{ Read([]byte) (int, error) }, buf []byte) {
	total := 0
	for total < len(buf) {
		n, err := r.Read(buf[total:])
		total += n
		if err != nil {
			return
		}
	}
}

func flipH(src image.Image) image.Image {
	b := src.Bounds()
	dst := image.NewRGBA(image.Rect(0, 0, b.Dx(), b.Dy()))
	for y := 0; y < b.Dy(); y++ {
		for x := 0; x < b.Dx(); x++ {
			dst.Set(b.Dx()-1-x, y, src.At(b.Min.X+x, b.Min.Y+y))
		}
	}
	return dst
}
