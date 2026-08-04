package main

import (
	"archive/zip"
	"fmt"
	"path"
	"path/filepath"
	"sort"
)

// This file decodes the 2.70 client's per-tile MAP GRAPHICS scene so the viewer
// can composite the real map art. Reverse-engineered from the decompiled client
// (loader kC.load + ScreenElement.b/a, sprite table UF/zl_1). LITTLE-ENDIAN.
//
// Two inputs combine:
//   - contents/maps/data.jar!/elements.lib : the GLOBAL sprite table (UF),
//     id -> {gfxId, originX, originY, width, height, mirror}. gfxId indexes the
//     bitmap gfx.jar!/gfx/<gfxId>.tgam (MAGT, see tgam.go).
//   - contents/maps/gfx/<world>.jar!/<i>_<j> : per-cell element lists giving,
//     for each drawn sprite, its spriteId (into elements.lib), cell, altitude
//     and draw order. Cell x/y are implicit from the block/cell loop.

// spriteMeta is one elements.lib (zl_1) record — the sprite's placement metadata.
type spriteMeta struct {
	GfxID   int32
	OriginX int16
	OriginY int16
	Width   int16
	Height  int16
	Mirror  bool
}

// skip advances the cursor by n bytes (bounds-checked).
func (c *leCur) skip(n int) {
	if n < 0 {
		c.err = true
		return
	}
	if c.need(n) {
		c.p += n
	}
}

// parseElementsLib decodes the global sprite table (UF.load / zl_1):
//
//	i32 count, then count × { i32 id, i16 originX, i16 originY, i16 w, i16 h,
//	i32 gfxId, i8 flags(0x10=mirror), i8 visualHeight, i8 layerMask, i8 aor,
//	atlas(u8 frameCount; if>0: i32,4×i16, frameCount×i16, 2*frameCount×i16), i8 }
func parseElementsLib(data []byte) (map[int32]spriteMeta, error) {
	c := &leCur{b: data}
	n := int(int32(c.u32()))
	if n < 0 || n > 1<<22 {
		return nil, fmt.Errorf("elements.lib: implausible count %d", n)
	}
	out := make(map[int32]spriteMeta, n)
	for i := 0; i < n && c.ok(); i++ {
		id := int32(c.u32())
		m := spriteMeta{
			OriginX: c.i16(),
			OriginY: c.i16(),
			Width:   c.i16(),
			Height:  c.i16(),
			GfxID:   int32(c.u32()),
		}
		flags := c.u8()
		m.Mirror = flags&0x10 != 0
		c.skip(3) // visualHeight, layerMask, aor
		fc := int(c.u8())
		if fc > 0 {
			// animation atlas: i32 gfxId, 4×i16, frameCount×i16 durations,
			// 2*frameCount×i16 uv offsets.
			c.skip(4 + 8 + fc*2 + fc*4)
		}
		c.skip(1) // aon
		out[id] = m
	}
	if !c.ok() {
		return nil, fmt.Errorf("elements.lib: truncated at %d bytes", len(data))
	}
	return out, nil
}

// gfxElement is one raw element read from a gfx tile (cell x/y come from the
// enclosing block/cell loop, not the body).
type gfxElement struct {
	SpriteID int32
	CellX    int32
	CellY    int32
	Alt      int16 // cto (elevation)
	AbaH     int8  // aba (height offset; effective elev = Alt-AbaH)
	Order    int8  // cts (draw-order sub-key)
}

// colorBytes returns the length of an element's variable color/tint tail for a
// given typeTag (ScreenElement.kw / .a).
func colorBytes(tag byte) int {
	group := 0
	if tag&0x02 != 0 {
		group += 3
	}
	if tag&0x08 != 0 {
		group++
	}
	mult := 1
	if tag&0x10 != 0 {
		mult = 2
	}
	total := group * mult
	if tag&0x01 != 0 {
		total += 3
	}
	if tag&0x04 != 0 {
		total += 3
	}
	return total
}

// parseGfxTile decodes one gfx scene tile (kC.load): a 30-byte header, then a
// list of rectangle blocks, each enumerating cells (column-major) that hold a
// count-prefixed list of elements.
func parseGfxTile(data []byte) ([]gfxElement, error) {
	c := &leCur{b: data}
	// Header (30 bytes): minX,minY,minAlt, maxX,maxY,maxAlt, baseX,baseY, blocks.
	c.skip(4 + 4 + 2 + 4 + 4 + 2) // bounds (unused)
	baseX := int32(c.u32())
	baseY := int32(c.u32())
	blocks := int(uint16(c.i16()))

	var out []gfxElement
	for b := 0; b < blocks && c.ok(); b++ {
		xS := baseX + int32(c.u8())
		xE := baseX + int32(c.u8())
		yS := baseY + int32(c.u8())
		yE := baseY + int32(c.u8())
		for cx := xS; cx < xE && c.ok(); cx++ {
			for cy := yS; cy < yE && c.ok(); cy++ {
				count := int(c.u8())
				for k := 0; k < count && c.ok(); k++ {
					tag := c.u8()
					el := gfxElement{CellX: cx, CellY: cy}
					el.Alt = c.i16()             // cto
					el.AbaH = int8(c.u8())       // aba
					el.Order = int8(c.u8())      // cts
					c.skip(4)                    // bPM
					c.skip(1)                    // coF
					c.skip(4)                    // aoq
					c.skip(1)                    // ctt flag byte
					el.SpriteID = int32(c.u32()) // n2 -> elements.lib id
					c.skip(colorBytes(tag))
					out = append(out, el)
				}
			}
		}
	}
	if !c.ok() {
		return nil, fmt.Errorf("gfx tile: truncated")
	}
	return out, nil
}

// mapDrawable is a resolved, placeable map sprite (element + its sprite meta).
type mapDrawable struct {
	GfxID   int32
	CellX   int32
	CellY   int32
	Alt     int16
	AbaH    int8
	Order   int8
	OriginX int16
	OriginY int16
	W       int16
	H       int16
	Flip    bool
}

// parseGfxJar decodes every "<i>_<j>" scene tile in a world's gfx jar and
// resolves each element against the global sprite table into placeable
// drawables, sorted back-to-front by (cellY, cellX, order). cap bounds the count.
func parseGfxJar(zpath string, meta map[int32]spriteMeta, cap int) ([]mapDrawable, bool, error) {
	r, err := zip.OpenReader(zpath)
	if err != nil {
		return nil, false, err
	}
	defer r.Close()

	var out []mapDrawable
	truncated := false
	for _, f := range r.File {
		if !tileNameRe.MatchString(path.Base(f.Name)) {
			continue
		}
		data, rerr := readAll(f)
		if rerr != nil {
			return nil, false, rerr
		}
		els, derr := parseGfxTile(data)
		if derr != nil {
			continue // skip a malformed tile rather than failing the world
		}
		for _, el := range els {
			m, ok := meta[el.SpriteID]
			if !ok {
				continue
			}
			if cap > 0 && len(out) >= cap {
				truncated = true
				break
			}
			out = append(out, mapDrawable{
				GfxID: m.GfxID, CellX: el.CellX, CellY: el.CellY,
				Alt: el.Alt, AbaH: el.AbaH, Order: el.Order,
				OriginX: m.OriginX, OriginY: m.OriginY, W: m.Width, H: m.Height,
				Flip: m.Mirror,
			})
		}
		if truncated {
			break
		}
	}
	sort.SliceStable(out, func(i, j int) bool {
		if out[i].CellY != out[j].CellY {
			return out[i].CellY < out[j].CellY
		}
		if out[i].CellX != out[j].CellX {
			return out[i].CellX < out[j].CellX
		}
		return out[i].Order < out[j].Order
	})
	return out, truncated, nil
}

// loadElementsLib opens contents/maps/data.jar and parses its elements.lib.
func loadElementsLib(mapsDir string) (map[int32]spriteMeta, error) {
	zpath := filepath.Join(mapsDir, "data.jar")
	r, err := zip.OpenReader(zpath)
	if err != nil {
		return nil, err
	}
	defer r.Close()
	for _, f := range r.File {
		if path.Base(f.Name) == "elements.lib" {
			data, rerr := readAll(f)
			if rerr != nil {
				return nil, rerr
			}
			return parseElementsLib(data)
		}
	}
	return nil, fmt.Errorf("elements.lib not found in %s", zpath)
}
