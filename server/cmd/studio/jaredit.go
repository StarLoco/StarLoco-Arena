package main

import (
	"encoding/base64"
	"fmt"
	"path/filepath"
)

// This file makes the studio a universal client RECOMPILER: it can replace ANY
// entry in ANY content jar -- Lua spell scripts, XML config, GUI text, PNG art,
// fonts, anything -- not just the parsed .dat data. Every write goes through the
// same order/method-preserving repackJar (backup + atomic swap), and the cached
// jar handle is released first (Windows can't rename over an open file).
//
// Two entry points: SaveJarText for text assets edited inline in the browser,
// and ReplaceJarEntry for binary assets uploaded as base64 (images, fonts, etc).

// SaveJarText overwrites a text entry (Lua/XML/properties/...) in a client jar
// with new UTF-8 content, backing up the jar first. Returns the repack result.
func (a *App) SaveJarText(jarName, entryPath, content string) (RepackResult, error) {
	return a.replaceJarEntryBytes(jarName, entryPath, []byte(content))
}

// ReplaceJarEntry overwrites any entry with the given base64-encoded bytes
// (used for images/fonts/other binaries uploaded from disk). Returns the repack
// result.
func (a *App) ReplaceJarEntry(jarName, entryPath, base64Data string) (RepackResult, error) {
	// Accept an optional data-URL prefix ("data:...;base64,") for convenience.
	if i := indexOfBase64Comma(base64Data); i >= 0 {
		base64Data = base64Data[i+1:]
	}
	data, err := base64.StdEncoding.DecodeString(base64Data)
	if err != nil {
		return RepackResult{}, fmt.Errorf("decode replacement bytes: %w", err)
	}
	return a.replaceJarEntryBytes(jarName, entryPath, data)
}

// replaceJarEntryBytes is the shared write path: verify the jar + entry exist,
// release the cached handle, repack with the one replacement (backup + atomic).
func (a *App) replaceJarEntryBytes(jarName, entryPath string, data []byte) (RepackResult, error) {
	dir, err := a.contentsDir()
	if err != nil {
		return RepackResult{}, err
	}
	jarPath := filepath.Join(dir, jarName)

	// Confirm the entry currently exists (we only REPLACE existing assets, to
	// avoid silently adding stray files with a wrong path).
	r, err := a.openNamedJar(jarName)
	if err != nil {
		return RepackResult{}, err
	}
	if findEntry(r, entryPath) == nil {
		return RepackResult{}, fmt.Errorf("entry %q not found in %s", entryPath, jarName)
	}

	// Release the cached handle BEFORE repack (Windows rename-over-open guard).
	a.jars.invalidate(jarPath)

	res, err := repackJar(jarPath, []RepackReplacement{{EntryPath: entryPath, Data: data}})
	if err != nil {
		return res, err
	}
	if len(res.Missing) > 0 {
		return res, fmt.Errorf("entry %q vanished during repack", entryPath)
	}
	return res, nil
}

// indexOfBase64Comma returns the index of the comma that ends a data-URL
// header ("data:mime;base64,"), or -1 if the string has no such prefix.
func indexOfBase64Comma(s string) int {
	if len(s) < 5 || s[:5] != "data:" {
		return -1
	}
	for i := 0; i < len(s); i++ {
		if s[i] == ',' {
			return i
		}
	}
	return -1
}
