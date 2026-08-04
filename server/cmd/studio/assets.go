package main

import (
	"archive/zip"
	"encoding/base64"
	"fmt"
	"io"
	"os"
	"path"
	"path/filepath"
	"sort"
	"strings"
	"sync"
)

// This file implements the client asset browser: the 2.70 client's content
// archives (client/compiled/game/contents/*.jar) are ordinary ZIPs, so we open
// them read-only, present a per-jar entry list, and preview images (PNG
// passthrough; standard .tga and the client's custom .tgam/MAGT decoded to PNG
// server-side) and text. Everything here is read-only.

// maxPreviewBytes bounds how much of a text entry we return for preview.
const maxPreviewBytes = 2 << 20 // 2 MiB

// JarInfo summarizes one asset jar for the jar picker.
type JarInfo struct {
	Name       string `json:"name"`
	EntryCount int    `json:"entryCount"`
	SizeBytes  int64  `json:"sizeBytes"`
	Error      string `json:"error"`
}

// AssetEntry is one file inside a jar.
type AssetEntry struct {
	Path string `json:"path"`
	Name string `json:"name"`
	Ext  string `json:"ext"`
	Size int64  `json:"size"`
	Kind string `json:"kind"` // "image" | "text" | "binary"
}

// AssetPreview is the content returned for a single entry.
type AssetPreview struct {
	Path      string `json:"path"`
	Kind      string `json:"kind"`
	Mime      string `json:"mime"`
	DataURL   string `json:"dataUrl"`
	Text      string `json:"text"`
	Truncated bool   `json:"truncated"`
	Size      int64  `json:"size"`
	Width     int    `json:"width"`
	Height    int    `json:"height"`
}

// assetJarNames is the fixed set of 2.70 content jars we expose, in UI order.
// (Unlike 2.04 there is no equipments.jar; maps.jar is new.)
var assetJarNames = []string{
	"gui.jar",
	"gfx.jar",
	"animations.jar",
	"maps.jar",
	"data.jar",
	"sfx.jar",
	"sounds.jar",
	"musics.jar",
	"i18n.jar",
}

// jarCache memoizes opened zip readers per absolute jar path for the life of
// the process (the client jars don't change under us during a session).
type jarCache struct {
	mu     sync.Mutex
	byPath map[string]*zip.ReadCloser
}

func (c *jarCache) open(p string) (*zip.ReadCloser, error) {
	c.mu.Lock()
	defer c.mu.Unlock()
	if c.byPath == nil {
		c.byPath = map[string]*zip.ReadCloser{}
	}
	if r, ok := c.byPath[p]; ok {
		return r, nil
	}
	r, err := zip.OpenReader(p)
	if err != nil {
		return nil, err
	}
	c.byPath[p] = r
	return r, nil
}

// reset closes and drops every cached handle (called when the client dir
// changes so subsequent opens re-read from the new location).
func (c *jarCache) reset() {
	c.mu.Lock()
	defer c.mu.Unlock()
	for _, r := range c.byPath {
		r.Close()
	}
	c.byPath = nil
}

// contentsDir returns the client's game/contents directory, or an error if no
// valid client dir is selected.
func (a *App) contentsDir() (string, error) {
	a.mu.Lock()
	valid := a.paths.ClientDirValid
	dir := a.paths.ClientDir
	a.mu.Unlock()
	if !valid {
		return "", fmt.Errorf("no valid client directory selected (current: %q)", dir)
	}
	return filepath.Join(dir, "game", "contents"), nil
}

// ListJars returns metadata for every known content jar that exists.
func (a *App) ListJars() ([]JarInfo, error) {
	dir, err := a.contentsDir()
	if err != nil {
		return nil, err
	}
	var out []JarInfo
	for _, name := range assetJarNames {
		p := filepath.Join(dir, name)
		info, statErr := os.Stat(p)
		if statErr != nil {
			continue // jar not present in this build; skip silently
		}
		ji := JarInfo{Name: name, SizeBytes: info.Size()}
		r, openErr := a.jars.open(p)
		if openErr != nil {
			ji.Error = openErr.Error()
			out = append(out, ji)
			continue
		}
		for _, f := range r.File {
			if !f.FileInfo().IsDir() {
				ji.EntryCount++
			}
		}
		out = append(out, ji)
	}
	if len(out) == 0 {
		return nil, fmt.Errorf("no content jars found under %s", dir)
	}
	return out, nil
}

// ListJarEntries returns every file entry in the named jar, sorted by path.
func (a *App) ListJarEntries(jarName string) ([]AssetEntry, error) {
	r, err := a.openNamedJar(jarName)
	if err != nil {
		return nil, err
	}
	out := make([]AssetEntry, 0, len(r.File))
	for _, f := range r.File {
		if f.FileInfo().IsDir() {
			continue
		}
		name := path.Base(f.Name)
		ext := strings.ToLower(strings.TrimPrefix(filepath.Ext(name), "."))
		out = append(out, AssetEntry{
			Path: f.Name,
			Name: name,
			Ext:  ext,
			Size: int64(f.UncompressedSize64),
			Kind: kindForExt(ext),
		})
	}
	sort.Slice(out, func(i, j int) bool { return out[i].Path < out[j].Path })
	return out, nil
}

// PreviewEntry returns previewable content for one entry.
func (a *App) PreviewEntry(jarName, entryPath string) (AssetPreview, error) {
	r, err := a.openNamedJar(jarName)
	if err != nil {
		return AssetPreview{}, err
	}
	f := findEntry(r, entryPath)
	if f == nil {
		return AssetPreview{}, fmt.Errorf("entry %q not found in %s", entryPath, jarName)
	}
	ext := strings.ToLower(strings.TrimPrefix(filepath.Ext(entryPath), "."))
	kind := kindForExt(ext)
	prev := AssetPreview{Path: entryPath, Kind: kind, Size: int64(f.UncompressedSize64)}

	switch kind {
	case "image":
		switch ext {
		case "tga", "tgam":
			// Decode to PNG server-side (browsers render neither format).
			url, w, h, decErr := a.decodeImageEntryToPNG(f, ext)
			if decErr != nil {
				prev.Kind = "binary"
				prev.Text = decErr.Error()
				return prev, nil
			}
			prev.Mime = "image/png"
			prev.DataURL = url
			prev.Width = w
			prev.Height = h
		default:
			data, readErr := readZipEntry(f, maxPreviewBytes+1)
			if readErr != nil {
				return AssetPreview{}, readErr
			}
			prev.Mime = mimeForExt(ext)
			prev.DataURL = "data:" + prev.Mime + ";base64," + base64.StdEncoding.EncodeToString(data)
		}
	case "text":
		data, readErr := readZipEntry(f, maxPreviewBytes+1)
		if readErr != nil {
			return AssetPreview{}, readErr
		}
		if len(data) > maxPreviewBytes {
			data = data[:maxPreviewBytes]
			prev.Truncated = true
		}
		prev.Text = string(data)
	default:
		// binary: metadata only.
	}
	return prev, nil
}

// ExtractEntry writes one jar entry to destPath on disk.
func (a *App) ExtractEntry(jarName, entryPath, destPath string) error {
	r, err := a.openNamedJar(jarName)
	if err != nil {
		return err
	}
	f := findEntry(r, entryPath)
	if f == nil {
		return fmt.Errorf("entry %q not found in %s", entryPath, jarName)
	}
	rc, err := f.Open()
	if err != nil {
		return err
	}
	defer rc.Close()
	if mkErr := os.MkdirAll(filepath.Dir(destPath), 0o755); mkErr != nil {
		return mkErr
	}
	out, err := os.Create(destPath)
	if err != nil {
		return err
	}
	defer out.Close()
	if _, err := io.Copy(out, rc); err != nil {
		return err
	}
	return nil
}

// --- helpers ----------------------------------------------------------------

func (a *App) openNamedJar(jarName string) (*zip.ReadCloser, error) {
	if !isKnownJar(jarName) {
		return nil, fmt.Errorf("unknown jar %q", jarName)
	}
	dir, err := a.contentsDir()
	if err != nil {
		return nil, err
	}
	return a.jars.open(filepath.Join(dir, jarName))
}

func isKnownJar(name string) bool {
	for _, n := range assetJarNames {
		if n == name {
			return true
		}
	}
	return false
}

func findEntry(r *zip.ReadCloser, entryPath string) *zip.File {
	for _, f := range r.File {
		if f.Name == entryPath {
			return f
		}
	}
	return nil
}

func readZipEntry(f *zip.File, limit int64) ([]byte, error) {
	rc, err := f.Open()
	if err != nil {
		return nil, err
	}
	defer rc.Close()
	return io.ReadAll(io.LimitReader(rc, limit))
}

// kindForExt classifies an extension into a preview kind.
func kindForExt(ext string) string {
	switch ext {
	case "png", "jpg", "jpeg", "gif", "webp", "bmp", "tga", "tgam":
		return "image"
	case "xml", "lua", "txt", "json", "properties", "mf", "cg", "csv", "md", "ade", "tab", "fnt", "anmx":
		return "text"
	default:
		return "binary"
	}
}

func mimeForExt(ext string) string {
	switch ext {
	case "png":
		return "image/png"
	case "jpg", "jpeg":
		return "image/jpeg"
	case "gif":
		return "image/gif"
	case "webp":
		return "image/webp"
	case "bmp":
		return "image/bmp"
	default:
		return "application/octet-stream"
	}
}
