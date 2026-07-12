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

// This file implements Phase 2: browsing the client's asset .jar archives
// (client-compiled/game/contents/*.jar). A .jar is an ordinary ZIP, so we
// open them read-only with archive/zip, present a merged virtual tree
// (jar -> entries), and let the UI preview PNGs/text and extract any entry.
// TGA/SBA decoding are later phases; here we only serve raw bytes + PNGs.

// maxPreviewBytes bounds how much of a text/binary entry we return for
// preview, so opening a huge file can't blow up the webview.
const maxPreviewBytes = 2 << 20 // 2 MiB

// JarInfo summarizes one asset jar for the jar picker.
type JarInfo struct {
	Name       string `json:"name"`       // file name, e.g. "gui.jar"
	EntryCount int    `json:"entryCount"` // number of file entries (dirs excluded)
	SizeBytes  int64  `json:"sizeBytes"`  // jar file size on disk
	Error      string `json:"error"`      // non-empty if the jar failed to open
}

// AssetEntry is one file inside a jar (directories are not emitted as
// standalone entries; the UI derives folders from the path).
type AssetEntry struct {
	Path string `json:"path"` // full path within the jar, e.g. "gui/components/x.xml"
	Name string `json:"name"` // base name
	Ext  string `json:"ext"`  // lowercased extension without dot, e.g. "png"
	Size int64  `json:"size"` // uncompressed size
	Kind string `json:"kind"` // preview kind: "image" | "text" | "binary"
}

// AssetPreview is the content returned for a single entry.
type AssetPreview struct {
	Path      string `json:"path"`
	Kind      string `json:"kind"`      // "image" | "text" | "binary"
	Mime      string `json:"mime"`      // for images, e.g. "image/png"
	DataURL   string `json:"dataUrl"`   // for images: base64 data URL
	Text      string `json:"text"`      // for text: decoded UTF-8 (possibly truncated)
	Truncated bool   `json:"truncated"` // text was cut at maxPreviewBytes
	Size      int64  `json:"size"`
}

// assetJarNames is the fixed set of content jars we expose, in a sensible
// UI order. (There are also sounds*/musics jars; included for completeness.)
var assetJarNames = []string{
	"gui.jar",
	"gfx.jar",
	"animations.jar",
	"equipments.jar",
	"sfx.jar",
	"data.jar",
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

// invalidate closes and drops any cached handle for p, so a subsequent open()
// re-reads the file from disk. Call this after repacking a jar in place (the
// old handle points at the pre-swap file, and on Windows a stale open handle
// also blocks the rename until released).
func (c *jarCache) invalidate(p string) {
	c.mu.Lock()
	defer c.mu.Unlock()
	if r, ok := c.byPath[p]; ok {
		r.Close()
		delete(c.byPath, p)
	}
}

// contentsDir returns the client's game/contents directory, or an error if
// no valid client dir is selected.
func (a *App) contentsDir() (string, error) {
	if !a.paths.ClientDirValid {
		return "", fmt.Errorf("no valid client directory selected (current: %q)", a.paths.ClientDir)
	}
	return filepath.Join(a.paths.ClientDir, "game", "contents"), nil
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

// PreviewEntry returns previewable content for one entry: a data URL for
// images (PNG passthrough; other image kinds handled in later phases), or
// decoded text for text kinds, or just metadata for opaque binary.
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
		if ext == "tga" {
			// Decode the Targa to PNG server-side so the webview can
			// render it (browsers don't support TGA natively). See tga.go.
			dataURL, decErr := a.tgaEntryToPNGDataURL(f)
			if decErr != nil {
				// Fall back to metadata-only rather than failing the panel.
				prev.Kind = "binary"
				prev.Text = decErr.Error()
				return prev, nil
			}
			prev.Mime = "image/png"
			prev.DataURL = dataURL
			return prev, nil
		}
		data, readErr := readZipEntry(f, maxPreviewBytes+1)
		if readErr != nil {
			return AssetPreview{}, readErr
		}
		prev.Mime = mimeForExt(ext)
		prev.DataURL = "data:" + prev.Mime + ";base64," + base64.StdEncoding.EncodeToString(data)
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

// ExtractEntry writes one jar entry to destPath on disk (used by the UI's
// "extract" action after the user picks a location via a native dialog in a
// later wiring; for now destPath is provided by the caller).
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
	case "png", "jpg", "jpeg", "gif", "webp", "bmp", "tga":
		return "image"
	case "xml", "lua", "txt", "json", "properties", "mf", "cg", "csv", "md", "ade":
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
