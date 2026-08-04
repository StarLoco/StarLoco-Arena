package main

import (
	"archive/zip"
	"image"
	"image/png"
	"os"
	"path"
	"path/filepath"
	"strings"
	"time"

	wruntime "github.com/wailsapp/wails/v2/pkg/runtime"
)

// This file implements the "export all sprites to PNG" batch action: decode
// every gfx.jar .tgam (the MAGT format, see tgam.go) and write it as a PNG into
// a user-chosen folder, mirroring the jar's paths (gfx/<id>.tgam ->
// <dir>/gfx/<id>.png). Progress is streamed to the UI on the
// "sprites:export:progress" event.

// SpriteExportResult summarizes a batch sprite->PNG export.
type SpriteExportResult struct {
	Dir       string `json:"dir"`
	Total     int    `json:"total"`
	Written   int    `json:"written"`
	Failed    int    `json:"failed"`
	Cancelled bool   `json:"cancelled"`
	ElapsedMs int64  `json:"elapsedMs"`
}

// exportProgress is the payload of the "sprites:export:progress" event.
type exportProgress struct {
	Done  int `json:"done"`
	Total int `json:"total"`
}

// ExportAllSprites prompts for a destination folder (native dialog) and writes
// every gfx.jar .tgam sprite there as a PNG. A cancelled dialog returns a
// Cancelled result (no error).
func (a *App) ExportAllSprites() (SpriteExportResult, error) {
	dir, err := wruntime.OpenDirectoryDialog(a.ctx, wruntime.OpenDialogOptions{
		Title: "Export all sprites to PNG \u2014 choose a destination folder",
	})
	if err != nil {
		return SpriteExportResult{}, err
	}
	if strings.TrimSpace(dir) == "" {
		return SpriteExportResult{Cancelled: true}, nil
	}
	return a.exportSpritesTo(dir, 0, true)
}

// exportSpritesTo writes gfx.jar's .tgam sprites as PNGs under dir. limit>0 caps
// how many are written (for tests); progress toggles event emission.
func (a *App) exportSpritesTo(dir string, limit int, progress bool) (SpriteExportResult, error) {
	r, err := a.openNamedJar("gfx.jar")
	if err != nil {
		return SpriteExportResult{}, err
	}
	var entries []*zip.File
	for _, f := range r.File {
		if f.FileInfo().IsDir() {
			continue
		}
		if strings.HasSuffix(strings.ToLower(f.Name), ".tgam") {
			entries = append(entries, f)
		}
	}
	if limit > 0 && len(entries) > limit {
		entries = entries[:limit]
	}

	res := SpriteExportResult{Dir: dir, Total: len(entries)}
	start := time.Now()
	for i, f := range entries {
		if err := a.exportOneSprite(f, dir); err != nil {
			res.Failed++
		} else {
			res.Written++
		}
		if progress && a.ctx != nil && ((i+1)%50 == 0 || i+1 == len(entries)) {
			wruntime.EventsEmit(a.ctx, "sprites:export:progress", exportProgress{Done: i + 1, Total: len(entries)})
		}
	}
	res.ElapsedMs = time.Since(start).Milliseconds()
	return res, nil
}

// exportOneSprite decodes a single .tgam entry and writes it as a PNG mirroring
// the entry path (with a .png extension) under dir. The output path is
// sanitized so a hostile entry name can't escape dir.
func (a *App) exportOneSprite(f *zip.File, dir string) error {
	raw, err := readZipEntry(f, 64<<20)
	if err != nil {
		return err
	}
	img, err := decodeTGAM(raw)
	if err != nil {
		return err
	}
	rel := strings.TrimSuffix(f.Name, path.Ext(f.Name)) + ".png"
	// Collapse any ".." by cleaning as an absolute path, then re-root under dir.
	rel = strings.TrimPrefix(path.Clean("/"+rel), "/")
	outPath := filepath.Join(dir, filepath.FromSlash(rel))
	return writePNGFile(outPath, img)
}

// writePNGFile PNG-encodes img to outPath, creating parent directories.
func writePNGFile(outPath string, img image.Image) error {
	if err := os.MkdirAll(filepath.Dir(outPath), 0o755); err != nil {
		return err
	}
	out, err := os.Create(outPath)
	if err != nil {
		return err
	}
	defer out.Close()
	return png.Encode(out, img)
}
