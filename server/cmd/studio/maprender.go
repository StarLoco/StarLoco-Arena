package main

import (
	"bytes"
	"encoding/base64"
	"fmt"
	"image"
	"image/draw"
	"image/png"
	"math"

	xdraw "golang.org/x/image/draw"
)

// This file composites a world's decorative art (the resolved gfx drawables)
// into a single PNG server-side. Doing it in Go — rather than in the webview
// canvas — guarantees pixel-correct output (verified against the client). Large
// worlds are downscaled so EVERY world renders as one bounded-size image.

const (
	renderHX     = 43.0 // cellWidth/2
	renderHY     = 21.5 // cellHeight/2
	renderEL     = 10.0 // elevation unit
	renderTarget = 2600 // downscale so the larger world-extent fits this many px
	renderMaxPx  = 8192 // hard safety cap on the output image
	renderPad    = 4
)

// MapRenderDTO is a composited map-art image (base64 PNG) plus the world
// rectangle it covers, so the frontend can place it exactly under the same
// isometric projection as the topology/spawn overlays. A world-projected point
// (wx,wy) = ((cx-cy)*43, (cx+cy)*21.5 - alt*10) sits inside [WorldX, WorldX+
// WorldW] x [WorldY, WorldY+WorldH]. Empty=true when the world has no art.
type MapRenderDTO struct {
	DataURL string  `json:"dataUrl"`
	WorldX  float64 `json:"worldX"`
	WorldY  float64 `json:"worldY"`
	WorldW  float64 `json:"worldW"`
	WorldH  float64 `json:"worldH"`
	Empty   bool    `json:"empty"`
	Error   string  `json:"error"`
}

// GetMapRender composites a world's art to a (possibly downscaled) PNG data URL,
// cached by world id.
func (a *App) GetMapRender(id int) (MapRenderDTO, error) {
	a.mu.Lock()
	if a.mapRenderCache != nil {
		if dto, ok := a.mapRenderCache[id]; ok {
			a.mu.Unlock()
			return dto, nil
		}
	}
	a.mu.Unlock()

	gfx, err := a.GetMapGfx(id)
	if err != nil {
		return MapRenderDTO{}, err
	}
	if gfx.Error != "" || len(gfx.Drawables) == 0 {
		return MapRenderDTO{Empty: true, Error: gfx.Error}, nil
	}
	img, wx, wy, ww, wh, rerr := a.renderMapArt(gfx.Drawables)
	if rerr != nil {
		return MapRenderDTO{Empty: true, Error: rerr.Error()}, nil
	}
	var buf bytes.Buffer
	if err := png.Encode(&buf, img); err != nil {
		return MapRenderDTO{}, err
	}
	dto := MapRenderDTO{
		DataURL: "data:image/png;base64," + base64.StdEncoding.EncodeToString(buf.Bytes()),
		WorldX:  wx, WorldY: wy, WorldW: ww, WorldH: wh,
	}
	a.mu.Lock()
	if a.mapRenderCache == nil {
		a.mapRenderCache = map[int]MapRenderDTO{}
	}
	a.mapRenderCache[id] = dto
	a.mu.Unlock()
	return dto, nil
}

// renderMapArt composites the z-sorted drawables, downscaling if the world is
// large, and returns the image plus the world rectangle it covers.
func (a *App) renderMapArt(draws []MapDrawableDTO) (img *image.RGBA, worldX, worldY, worldW, worldH float64, err error) {
	r, e := a.openNamedJar("gfx.jar")
	if e != nil {
		return nil, 0, 0, 0, 0, e
	}
	imgs := map[int]image.Image{}
	decode := func(gid int) image.Image {
		if im, ok := imgs[gid]; ok {
			return im
		}
		var im image.Image
		if f := findEntry(r, fmt.Sprintf("gfx/%d.tgam", gid)); f != nil {
			if raw, e := readZipEntry(f, 64<<20); e == nil {
				if d, e2 := decodeTGAM(raw); e2 == nil {
					im = d
				}
			}
		}
		imgs[gid] = im
		return im
	}

	// World-projected screen bounds over every drawable.
	minX, minY, maxX, maxY := math.Inf(1), math.Inf(1), math.Inf(-1), math.Inf(-1)
	for _, d := range draws {
		im := decode(d.GfxID)
		if im == nil {
			continue
		}
		w, h := float64(im.Bounds().Dx()), float64(im.Bounds().Dy())
		x := float64(d.CellX-d.CellY)*renderHX - float64(d.OriginX)
		y := float64(d.CellX+d.CellY)*renderHY - float64(d.Alt-d.AbaH)*renderEL - float64(d.OriginY)
		minX, maxX = math.Min(minX, x), math.Max(maxX, x+w)
		minY, maxY = math.Min(minY, y), math.Max(maxY, y+h)
	}
	if minX > maxX {
		return nil, 0, 0, 0, 0, fmt.Errorf("no decodable sprites")
	}
	spanX, spanY := maxX-minX, maxY-minY
	scale := 1.0
	if ext := math.Max(spanX, spanY); ext > renderTarget {
		scale = renderTarget / ext
	}
	W := int(spanX*scale) + 2*renderPad
	H := int(spanY*scale) + 2*renderPad
	if W < 1 || H < 1 || W > renderMaxPx || H > renderMaxPx {
		return nil, 0, 0, 0, 0, fmt.Errorf("render size out of range (%dx%d)", W, H)
	}
	canvas := image.NewRGBA(image.Rect(0, 0, W, H))

	for _, d := range draws {
		im := decode(d.GfxID)
		if im == nil {
			continue
		}
		src := im
		if d.Flip {
			src = flipHImage(im)
		}
		sw, sh := float64(src.Bounds().Dx()), float64(src.Bounds().Dy())
		x := float64(d.CellX-d.CellY)*renderHX - float64(d.OriginX)
		y := float64(d.CellX+d.CellY)*renderHY - float64(d.Alt-d.AbaH)*renderEL - float64(d.OriginY)
		dx0 := (x-minX)*scale + renderPad
		dy0 := (y-minY)*scale + renderPad
		dr := image.Rect(int(dx0), int(dy0), int(math.Ceil(dx0+sw*scale)), int(math.Ceil(dy0+sh*scale)))
		if scale == 1.0 {
			draw.Draw(canvas, dr, src, src.Bounds().Min, draw.Over)
		} else {
			xdraw.ApproxBiLinear.Scale(canvas, dr, src, src.Bounds(), xdraw.Over, nil)
		}
	}

	worldX = minX - renderPad/scale
	worldY = minY - renderPad/scale
	worldW = float64(W) / scale
	worldH = float64(H) / scale
	return canvas, worldX, worldY, worldW, worldH, nil
}

// --- Viewport rendering -----------------------------------------------------
//
// Instead of one downscaled image for the whole map (which blurs when zoomed),
// GetMapView composites ONLY the sprites intersecting the requested world
// rectangle, scaled to the caller's output pixel size — so any zoom level is
// rendered at native canvas resolution (crisp), and huge worlds stay fast
// because off-screen sprites are culled. GetMapBounds gives the full art extent
// for the initial auto-fit.

const mapViewMaxPx = 4096 // clamp the requested output size

// MapBoundsDTO is a world's full art extent (world-projected coords).
type MapBoundsDTO struct {
	WorldX float64 `json:"worldX"`
	WorldY float64 `json:"worldY"`
	WorldW float64 `json:"worldW"`
	WorldH float64 `json:"worldH"`
	Empty  bool    `json:"empty"`
	Error  string  `json:"error"`
}

// MapViewDTO is a composited viewport image plus the world rectangle it covers.
type MapViewDTO struct {
	DataURL string  `json:"dataUrl"`
	WorldX  float64 `json:"worldX"`
	WorldY  float64 `json:"worldY"`
	WorldW  float64 `json:"worldW"`
	WorldH  float64 `json:"worldH"`
	Empty   bool    `json:"empty"`
	Error   string  `json:"error"`
}

// mapSpriteImage returns a decoded gfx.jar tile bitmap, cached across calls
// (including a nil cache entry for a missing/undecodable sprite).
func (a *App) mapSpriteImage(gfxID int) image.Image {
	a.mu.Lock()
	if a.mapSprite != nil {
		if im, ok := a.mapSprite[gfxID]; ok {
			a.mu.Unlock()
			return im
		}
	}
	a.mu.Unlock()

	r, err := a.openNamedJar("gfx.jar")
	var im image.Image
	if err == nil {
		if f := findEntry(r, fmt.Sprintf("gfx/%d.tgam", gfxID)); f != nil {
			if raw, e := readZipEntry(f, 64<<20); e == nil {
				if d, e2 := decodeTGAM(raw); e2 == nil {
					im = d
				}
			}
		}
	}
	a.mu.Lock()
	if a.mapSprite == nil {
		a.mapSprite = map[int]image.Image{}
	}
	a.mapSprite[gfxID] = im
	a.mu.Unlock()
	return im
}

// GetMapBounds returns a world's full art extent (cached), for auto-fit.
func (a *App) GetMapBounds(id int) (MapBoundsDTO, error) {
	a.mu.Lock()
	if a.mapBounds != nil {
		if b, ok := a.mapBounds[id]; ok {
			a.mu.Unlock()
			return b, nil
		}
	}
	a.mu.Unlock()

	gfx, err := a.GetMapGfx(id)
	if err != nil {
		return MapBoundsDTO{}, err
	}
	if gfx.Error != "" || len(gfx.Drawables) == 0 {
		return MapBoundsDTO{Empty: true, Error: gfx.Error}, nil
	}
	minX, minY, maxX, maxY := math.Inf(1), math.Inf(1), math.Inf(-1), math.Inf(-1)
	for _, d := range gfx.Drawables {
		im := a.mapSpriteImage(d.GfxID)
		if im == nil {
			continue
		}
		w, h := float64(im.Bounds().Dx()), float64(im.Bounds().Dy())
		x := float64(d.CellX-d.CellY)*renderHX - float64(d.OriginX)
		y := float64(d.CellX+d.CellY)*renderHY - float64(d.Alt-d.AbaH)*renderEL - float64(d.OriginY)
		minX, maxX = math.Min(minX, x), math.Max(maxX, x+w)
		minY, maxY = math.Min(minY, y), math.Max(maxY, y+h)
	}
	if minX > maxX {
		return MapBoundsDTO{Empty: true}, nil
	}
	b := MapBoundsDTO{WorldX: minX, WorldY: minY, WorldW: maxX - minX, WorldH: maxY - minY}
	a.mu.Lock()
	if a.mapBounds == nil {
		a.mapBounds = map[int]MapBoundsDTO{}
	}
	a.mapBounds[id] = b
	a.mu.Unlock()
	return b, nil
}

// GetMapView composites the sprites intersecting the world rectangle
// [wl,wt]-[wr,wb] into an outW x outH PNG (canvas-resolution), culling
// everything off-screen. This is the crisp, performant per-viewport render.
func (a *App) GetMapView(id int, wl, wt, wr, wb float64, outW, outH int) (MapViewDTO, error) {
	if wr <= wl || wb <= wt || outW < 1 || outH < 1 {
		return MapViewDTO{Empty: true}, nil
	}
	if outW > mapViewMaxPx {
		outW = mapViewMaxPx
	}
	if outH > mapViewMaxPx {
		outH = mapViewMaxPx
	}
	gfx, err := a.GetMapGfx(id)
	if err != nil {
		return MapViewDTO{}, err
	}
	if gfx.Error != "" || len(gfx.Drawables) == 0 {
		return MapViewDTO{Empty: true, Error: gfx.Error}, nil
	}
	sx := float64(outW) / (wr - wl)
	sy := float64(outH) / (wb - wt)
	// Zoomed in (upscaling): CatmullRom for crisp detail. Zoomed out: fast bilinear.
	var scaler xdraw.Interpolator = xdraw.ApproxBiLinear
	if sx >= 1 {
		scaler = xdraw.CatmullRom
	}

	canvas := image.NewRGBA(image.Rect(0, 0, outW, outH))
	for _, d := range gfx.Drawables { // pre-sorted back-to-front
		im := a.mapSpriteImage(d.GfxID)
		if im == nil {
			continue
		}
		w, h := float64(im.Bounds().Dx()), float64(im.Bounds().Dy())
		x := float64(d.CellX-d.CellY)*renderHX - float64(d.OriginX)
		y := float64(d.CellX+d.CellY)*renderHY - float64(d.Alt-d.AbaH)*renderEL - float64(d.OriginY)
		if x+w < wl || x > wr || y+h < wt || y > wb {
			continue // off-screen
		}
		src := im
		if d.Flip {
			src = flipHImage(im)
		}
		dx0 := (x - wl) * sx
		dy0 := (y - wt) * sy
		dr := image.Rect(int(math.Floor(dx0)), int(math.Floor(dy0)),
			int(math.Ceil((x+w-wl)*sx)), int(math.Ceil((y+h-wt)*sy)))
		if dr.Empty() {
			continue
		}
		scaler.Scale(canvas, dr, src, src.Bounds(), xdraw.Over, nil)
	}

	var buf bytes.Buffer
	if err := png.Encode(&buf, canvas); err != nil {
		return MapViewDTO{}, err
	}
	return MapViewDTO{
		DataURL: "data:image/png;base64," + base64.StdEncoding.EncodeToString(buf.Bytes()),
		WorldX:  wl, WorldY: wt, WorldW: wr - wl, WorldH: wb - wt,
	}, nil
}

// flipHImage returns a horizontally-mirrored copy (for mirrored sprite entries).
func flipHImage(im image.Image) image.Image {
	b := im.Bounds()
	o := image.NewNRGBA(image.Rect(0, 0, b.Dx(), b.Dy()))
	for y := 0; y < b.Dy(); y++ {
		for x := 0; x < b.Dx(); x++ {
			o.Set(b.Dx()-1-x, y, im.At(b.Min.X+x, b.Min.Y+y))
		}
	}
	return o
}
