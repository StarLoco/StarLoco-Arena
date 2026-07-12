package main

import (
	"archive/zip"
	"bytes"
	"encoding/base64"
	"fmt"
	"image/png"
	"path"
	"sort"
	"strconv"
	"strings"
)

// This file implements Phase 3: a sprite gallery over gfx.jar's .tga tiles.
// Every .tga is decoded (tga.go) and re-encoded as PNG so the webview can
// render it. gfx entries are named gfx/<id>.tga, so we surface the numeric
// id for lookup/linking from map elements later (Phase 4).

// SpriteInfo is one gfx sprite's catalog entry (no pixels; the gallery
// requests thumbnails/full images separately so listing stays cheap).
type SpriteInfo struct {
	ID    int    `json:"id"`    // numeric id parsed from "gfx/<id>.tga" (-1 if non-numeric)
	Path  string `json:"path"`  // entry path within gfx.jar
	Name  string `json:"name"`  // base name, e.g. "95.tga"
	Bytes int64  `json:"bytes"` // uncompressed size
}

// SpriteImage is a decoded sprite ready to render.
type SpriteImage struct {
	Path    string `json:"path"`
	Width   int    `json:"width"`
	Height  int    `json:"height"`
	DataURL string `json:"dataUrl"` // PNG base64 data URL
}

// ListSprites returns the gfx.jar sprite catalog, sorted by numeric id
// (non-numeric names sort last by path).
func (a *App) ListSprites() ([]SpriteInfo, error) {
	r, err := a.openNamedJar("gfx.jar")
	if err != nil {
		return nil, err
	}
	var out []SpriteInfo
	for _, f := range r.File {
		if f.FileInfo().IsDir() {
			continue
		}
		name := path.Base(f.Name)
		if !strings.HasSuffix(strings.ToLower(name), ".tga") {
			continue
		}
		id := -1
		if n, convErr := strconv.Atoi(strings.TrimSuffix(name, path.Ext(name))); convErr == nil {
			id = n
		}
		out = append(out, SpriteInfo{
			ID:    id,
			Path:  f.Name,
			Name:  name,
			Bytes: int64(f.UncompressedSize64),
		})
	}
	sort.Slice(out, func(i, j int) bool {
		if out[i].ID != out[j].ID {
			// numeric ids first (ascending); -1 (non-numeric) sinks to the end
			if out[i].ID == -1 {
				return false
			}
			if out[j].ID == -1 {
				return true
			}
			return out[i].ID < out[j].ID
		}
		return out[i].Path < out[j].Path
	})
	return out, nil
}

// GetSprite decodes one gfx.jar .tga entry and returns it as a PNG data URL
// plus its dimensions.
func (a *App) GetSprite(entryPath string) (SpriteImage, error) {
	r, err := a.openNamedJar("gfx.jar")
	if err != nil {
		return SpriteImage{}, err
	}
	f := findEntry(r, entryPath)
	if f == nil {
		return SpriteImage{}, fmt.Errorf("sprite %q not found in gfx.jar", entryPath)
	}
	url, w, h, err := a.decodeSpriteToPNG(f)
	if err != nil {
		return SpriteImage{}, err
	}
	return SpriteImage{Path: entryPath, Width: w, Height: h, DataURL: url}, nil
}

// GetSpriteByID is a convenience lookup: gfx/<id>.tga.
func (a *App) GetSpriteByID(id int) (SpriteImage, error) {
	return a.GetSprite(fmt.Sprintf("gfx/%d.tga", id))
}

// tgaEntryToPNGDataURL decodes a .tga zip entry to a PNG data URL (used by
// the generic asset previewer in assets.go).
func (a *App) tgaEntryToPNGDataURL(f *zip.File) (string, error) {
	url, _, _, err := a.decodeSpriteToPNG(f)
	return url, err
}

// decodeSpriteToPNG reads a .tga entry, decodes it, and PNG-encodes it,
// returning a data URL and the source dimensions.
func (a *App) decodeSpriteToPNG(f *zip.File) (dataURL string, width, height int, err error) {
	// Sprites are modest (tens of KB); read the whole entry.
	raw, err := readZipEntry(f, 32<<20)
	if err != nil {
		return "", 0, 0, err
	}
	img, err := decodeTGA(raw)
	if err != nil {
		return "", 0, 0, err
	}
	var buf bytes.Buffer
	if err := png.Encode(&buf, img); err != nil {
		return "", 0, 0, fmt.Errorf("sprite: png encode: %w", err)
	}
	b := img.Bounds()
	return "data:image/png;base64," + base64.StdEncoding.EncodeToString(buf.Bytes()), b.Dx(), b.Dy(), nil
}
