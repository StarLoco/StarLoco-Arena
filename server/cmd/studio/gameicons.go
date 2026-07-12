package main

import (
	"bytes"
	"encoding/base64"
	"image"
	"image/png"
)

// This file extracts the client's real UI icons (elements, AP/MP/HP/init) from
// the shared theme sprite atlases in gui.jar, so the studio can render effects
// and stats with authentic game art instead of emoji. The sprite coordinates
// are read straight from gui/theme/default.xml's <imageXxxIcon> styles:
//
//   themeSimple  : HP(2,2,15,14) AP(2,17) MP(2,32) Init(2,47)
//   themeSimple3 : wind(528,99,19,19) water(550) fire(572) earth(595)
//                  allElements(527,121,19,19)
//
// Verified against the decompiled client theme (see the fighterStatisticsCard
// XML which references windIcon/fireIcon/... and the default.xml texture map).

type atlasSprite struct {
	sheet      string // gui.jar entry
	x, y, w, h int
}

// gameIconSprites maps a logical icon name to its atlas crop.
var gameIconSprites = map[string]atlasSprite{
	"hp":   {"gui/theme/images/themeSimple.png", 2, 2, 15, 14},
	"ap":   {"gui/theme/images/themeSimple.png", 2, 17, 15, 14},
	"mp":   {"gui/theme/images/themeSimple.png", 2, 32, 15, 14},
	"init": {"gui/theme/images/themeSimple.png", 2, 47, 15, 14},

	"wind":  {"gui/theme/images/themeSimple3.png", 528, 99, 19, 19},
	"water": {"gui/theme/images/themeSimple3.png", 550, 99, 19, 19},
	"fire":  {"gui/theme/images/themeSimple3.png", 572, 99, 19, 19},
	"earth": {"gui/theme/images/themeSimple3.png", 595, 99, 19, 19},
	"all":   {"gui/theme/images/themeSimple3.png", 527, 121, 19, 19},
}

// GetGameIcons returns every logical UI icon (element/AP/MP/HP/init) as a
// name -> PNG data URL map, cropped from the theme atlases. Missing atlases
// yield an empty map (the UI falls back to text/emoji). Cached per process.
func (a *App) GetGameIcons() map[string]string {
	a.gameIconsMu.Lock()
	defer a.gameIconsMu.Unlock()
	if a.gameIcons != nil {
		return a.gameIcons
	}
	out := map[string]string{}
	sheets := map[string]image.Image{}
	for name, sp := range gameIconSprites {
		img, ok := sheets[sp.sheet]
		if !ok {
			img = a.loadGuiPNG(sp.sheet)
			sheets[sp.sheet] = img // cache nil too
		}
		if img == nil {
			continue
		}
		crop := cropImage(img, sp.x, sp.y, sp.w, sp.h)
		if crop == nil {
			continue
		}
		var buf bytes.Buffer
		if err := png.Encode(&buf, crop); err != nil {
			continue
		}
		out[name] = "data:image/png;base64," + base64.StdEncoding.EncodeToString(buf.Bytes())
	}
	a.gameIcons = out
	return out
}

// loadGuiPNG decodes a PNG entry from gui.jar, or nil on any miss.
func (a *App) loadGuiPNG(entry string) image.Image {
	r, err := a.openNamedJar("gui.jar")
	if err != nil {
		return nil
	}
	f := findEntry(r, entry)
	if f == nil {
		return nil
	}
	data, err := readZipEntry(f, 16<<20)
	if err != nil {
		return nil
	}
	img, err := png.Decode(bytes.NewReader(data))
	if err != nil {
		return nil
	}
	return img
}

// cropImage returns the (x,y,w,h) sub-rectangle of src as a standalone RGBA,
// clamped to the source bounds. Returns nil if the rect is empty/out of range.
func cropImage(src image.Image, x, y, w, h int) *image.RGBA {
	b := src.Bounds()
	x0 := b.Min.X + x
	y0 := b.Min.Y + y
	x1 := x0 + w
	y1 := y0 + h
	if x0 < b.Min.X {
		x0 = b.Min.X
	}
	if y0 < b.Min.Y {
		y0 = b.Min.Y
	}
	if x1 > b.Max.X {
		x1 = b.Max.X
	}
	if y1 > b.Max.Y {
		y1 = b.Max.Y
	}
	if x1 <= x0 || y1 <= y0 {
		return nil
	}
	dst := image.NewRGBA(image.Rect(0, 0, x1-x0, y1-y0))
	for yy := y0; yy < y1; yy++ {
		for xx := x0; xx < x1; xx++ {
			dst.Set(xx-x0, yy-y0, src.At(xx, yy))
		}
	}
	return dst
}
