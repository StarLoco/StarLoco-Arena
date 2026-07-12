package main

import (
	"os"
	"path/filepath"
	"strings"
	"testing"
)

// newAppWithClient builds an App pointed at the real client-compiled dir,
// skipping if it (or its jars) aren't present.
func newAppWithClient(t *testing.T) *App {
	t.Helper()
	wd, err := os.Getwd()
	if err != nil {
		t.Fatalf("getwd: %v", err)
	}
	// Try the documented repo layout (client/compiled) first, then the older
	// flat client-compiled/ fallback.
	dir := filepath.Join(wd, "..", "..", "..", "client", "compiled")
	if !looksLikeClientDir(dir) {
		dir = filepath.Join(wd, "..", "..", "..", "client-compiled")
	}
	if !looksLikeClientDir(dir) {
		t.Skipf("real client dir not found/valid at %s; skipping", dir)
	}
	a := &App{}
	p := a.SetClientDir(dir)
	if !p.ClientDirValid {
		t.Fatalf("SetClientDir did not validate real client dir: %+v", p)
	}
	return a
}

func TestListJars_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	jars, err := a.ListJars()
	if err != nil {
		t.Fatalf("ListJars: %v", err)
	}
	if len(jars) == 0 {
		t.Fatal("expected content jars, got none")
	}
	gui, ok := findJar(jars, "gui.jar")
	if !ok {
		t.Fatal("expected gui.jar present")
	}
	if gui.EntryCount == 0 {
		t.Errorf("gui.jar reported 0 entries")
	}
	t.Logf("jars: %d; gui.jar entries=%d", len(jars), gui.EntryCount)
}

func TestListJarEntries_And_Preview_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	entries, err := a.ListJarEntries("gui.jar")
	if err != nil {
		t.Fatalf("ListJarEntries: %v", err)
	}
	if len(entries) == 0 {
		t.Fatal("gui.jar has no entries")
	}
	// Sorted ascending by path (UI contract).
	for i := 1; i < len(entries); i++ {
		if entries[i-1].Path > entries[i].Path {
			t.Fatalf("entries not sorted at %d: %q > %q", i, entries[i-1].Path, entries[i].Path)
		}
	}
	// Find a PNG and preview it -> must yield an image data URL.
	var png *AssetEntry
	for i := range entries {
		if entries[i].Ext == "png" {
			png = &entries[i]
			break
		}
	}
	if png == nil {
		t.Skip("no png in gui.jar (unexpected but not fatal)")
	}
	prev, err := a.PreviewEntry("gui.jar", png.Path)
	if err != nil {
		t.Fatalf("PreviewEntry(png): %v", err)
	}
	if prev.Kind != "image" || !strings.HasPrefix(prev.DataURL, "data:image/png;base64,") {
		t.Errorf("png preview wrong: kind=%q dataUrlPrefix=%q", prev.Kind, safePrefix(prev.DataURL, 30))
	}
}

func TestPreviewText_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	entries, err := a.ListJarEntries("gui.jar")
	if err != nil {
		t.Fatalf("ListJarEntries: %v", err)
	}
	var xml *AssetEntry
	for i := range entries {
		if entries[i].Ext == "xml" {
			xml = &entries[i]
			break
		}
	}
	if xml == nil {
		t.Skip("no xml entry to test text preview")
	}
	prev, err := a.PreviewEntry("gui.jar", xml.Path)
	if err != nil {
		t.Fatalf("PreviewEntry(xml): %v", err)
	}
	if prev.Kind != "text" || prev.Text == "" {
		t.Errorf("xml preview should be non-empty text, got kind=%q len=%d", prev.Kind, len(prev.Text))
	}
}

func TestExtractEntry_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	entries, err := a.ListJarEntries("gui.jar")
	if err != nil || len(entries) == 0 {
		t.Skipf("no entries: %v", err)
	}
	dest := filepath.Join(t.TempDir(), "extracted", filepath.Base(entries[0].Path))
	if err := a.ExtractEntry("gui.jar", entries[0].Path, dest); err != nil {
		t.Fatalf("ExtractEntry: %v", err)
	}
	info, err := os.Stat(dest)
	if err != nil {
		t.Fatalf("stat extracted: %v", err)
	}
	if info.Size() != entries[0].Size {
		t.Errorf("extracted size %d != entry size %d", info.Size(), entries[0].Size)
	}
}

func TestAssets_NoClientDir(t *testing.T) {
	a := &App{}
	if _, err := a.ListJars(); err == nil {
		t.Fatal("expected error with no client dir")
	}
}

// helpers

func findJar(jars []JarInfo, name string) (JarInfo, bool) {
	for _, j := range jars {
		if j.Name == name {
			return j, true
		}
	}
	return JarInfo{}, false
}

func safePrefix(s string, n int) string {
	if len(s) < n {
		return s
	}
	return s[:n]
}
