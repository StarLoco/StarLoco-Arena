package main

import (
	"archive/zip"
	"bytes"
	"encoding/base64"
	"fmt"
	"image"
	"image/png"
	"path"
	"sort"
	"strconv"
	"strings"
)

// This file implements the sprite gallery over gfx.jar's .tgam tiles. Every
// .tgam is decoded (tgam.go, the MAGT format) and re-encoded as PNG so the
// webview can render it. gfx entries are named gfx/<id>.tgam, so we surface the
// numeric id for lookup/linking.

// SpriteInfo is one gfx sprite's catalog entry (no pixels; the gallery requests
// images separately so listing stays cheap).
type SpriteInfo struct {
	ID    int    `json:"id"`
	Path  string `json:"path"`
	Name  string `json:"name"`
	Bytes int64  `json:"bytes"`
}

// SpriteImage is a decoded sprite ready to render.
type SpriteImage struct {
	Path    string `json:"path"`
	Width   int    `json:"width"`
	Height  int    `json:"height"`
	DataURL string `json:"dataUrl"`
}

// ListSprites returns the gfx.jar .tgam catalog, sorted by numeric id
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
		if !strings.HasSuffix(strings.ToLower(name), ".tgam") {
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

// GetSprite decodes one gfx.jar .tgam entry to a PNG data URL plus dimensions.
func (a *App) GetSprite(entryPath string) (SpriteImage, error) {
	r, err := a.openNamedJar("gfx.jar")
	if err != nil {
		return SpriteImage{}, err
	}
	f := findEntry(r, entryPath)
	if f == nil {
		return SpriteImage{}, fmt.Errorf("sprite %q not found in gfx.jar", entryPath)
	}
	ext := strings.ToLower(strings.TrimPrefix(path.Ext(entryPath), "."))
	url, w, h, err := a.decodeImageEntryToPNG(f, ext)
	if err != nil {
		return SpriteImage{}, err
	}
	return SpriteImage{Path: entryPath, Width: w, Height: h, DataURL: url}, nil
}

// decodeImageEntryToPNG reads a .tga or .tgam zip entry, decodes it, and
// PNG-encodes it, returning a data URL and the source dimensions.
func (a *App) decodeImageEntryToPNG(f *zip.File, ext string) (dataURL string, width, height int, err error) {
	raw, err := readZipEntry(f, 64<<20)
	if err != nil {
		return "", 0, 0, err
	}
	var img image.Image
	switch ext {
	case "tgam":
		img, err = decodeTGAM(raw)
	default: // "tga"
		img, err = decodeTGA(raw)
	}
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
