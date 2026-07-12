package main

import (
	"archive/zip"
	"bytes"
	"fmt"
	"io"
	"os"
	"path/filepath"
	"time"
)

// This file implements the Phase 7 jar-repack pipeline: rewriting a client
// content .jar (a ZIP) with one or more entries replaced by edited bytes,
// while preserving every other entry (including META-INF/MANIFEST.MF), the
// original entry ORDER, and each entry's compression method. It always backs
// up the original jar first and writes via a staging temp file + atomic
// rename, so a failed/interrupted repack can't leave a corrupt jar in place.
// See tools/README.md section 5 (guardrails).

// RepackReplacement is one entry to overwrite in a jar, keyed by its exact
// path within the archive.
type RepackReplacement struct {
	EntryPath string `json:"entryPath"`
	Data      []byte `json:"-"` // filled in by the caller, not from JSON
}

// RepackResult reports what a repack did.
type RepackResult struct {
	Jar        string   `json:"jar"`
	BackupPath string   `json:"backupPath"`
	Replaced   []string `json:"replaced"`
	Missing    []string `json:"missing"` // requested entries not found in the jar
}

// repackJar rewrites jarPath, replacing the entries named in replacements
// with their new bytes. Entries not listed are copied through verbatim. The
// original jar is backed up to a timestamped .bak alongside it first.
func repackJar(jarPath string, replacements []RepackReplacement) (RepackResult, error) {
	byPath := map[string][]byte{}
	for _, r := range replacements {
		byPath[r.EntryPath] = r.Data
	}

	src, err := zip.OpenReader(jarPath)
	if err != nil {
		return RepackResult{}, fmt.Errorf("open jar: %w", err)
	}
	// NOTE: src must be CLOSED before we rename the temp file over jarPath --
	// on Windows you cannot replace a file that still has an open handle.
	// We close it explicitly at each exit path below rather than via defer.

	// Track which replacements actually matched an entry.
	seen := map[string]bool{}

	// Stage the new jar in a temp file in the same directory.
	tmp, err := os.CreateTemp(filepath.Dir(jarPath), ".studio-repack-*.jar")
	if err != nil {
		return RepackResult{}, fmt.Errorf("create temp: %w", err)
	}
	tmpName := tmp.Name()
	zw := zip.NewWriter(tmp)

	cleanupTmp := func() {
		zw.Close()
		tmp.Close()
		os.Remove(tmpName)
		src.Close()
	}

	for _, f := range src.File {
		// Preserve the original header (name, method, modtime, etc.) so
		// unchanged entries stay identical and changed entries keep their
		// metadata; only the content differs.
		hdr := f.FileHeader
		w, err := zw.CreateHeader(&hdr)
		if err != nil {
			cleanupTmp()
			return RepackResult{}, fmt.Errorf("create header %s: %w", f.Name, err)
		}

		if newData, ok := byPath[f.Name]; ok {
			seen[f.Name] = true
			if _, err := w.Write(newData); err != nil {
				cleanupTmp()
				return RepackResult{}, fmt.Errorf("write replacement %s: %w", f.Name, err)
			}
			continue
		}

		rc, err := f.Open()
		if err != nil {
			cleanupTmp()
			return RepackResult{}, fmt.Errorf("open entry %s: %w", f.Name, err)
		}
		if _, err := io.Copy(w, rc); err != nil {
			rc.Close()
			cleanupTmp()
			return RepackResult{}, fmt.Errorf("copy entry %s: %w", f.Name, err)
		}
		rc.Close()
	}

	if err := zw.Close(); err != nil {
		tmp.Close()
		os.Remove(tmpName)
		src.Close()
		return RepackResult{}, fmt.Errorf("finalize zip: %w", err)
	}
	if err := tmp.Close(); err != nil {
		os.Remove(tmpName)
		src.Close()
		return RepackResult{}, fmt.Errorf("close temp: %w", err)
	}
	// Release the source jar handle BEFORE backup/rename (Windows can't
	// rename over an open file).
	if err := src.Close(); err != nil {
		os.Remove(tmpName)
		return RepackResult{}, fmt.Errorf("close source jar: %w", err)
	}

	// Back up the original before swapping.
	backup := fmt.Sprintf("%s.%s.bak", jarPath, time.Now().Format("20060102-150405"))
	if err := copyFile(jarPath, backup); err != nil {
		os.Remove(tmpName)
		return RepackResult{}, fmt.Errorf("backup jar: %w", err)
	}

	if err := os.Rename(tmpName, jarPath); err != nil {
		os.Remove(tmpName)
		return RepackResult{}, fmt.Errorf("swap jar: %w", err)
	}

	// Report which requested replacements had no matching entry.
	var replaced, missing []string
	for _, r := range replacements {
		if seen[r.EntryPath] {
			replaced = append(replaced, r.EntryPath)
		} else {
			missing = append(missing, r.EntryPath)
		}
	}

	return RepackResult{
		Jar:        jarPath,
		BackupPath: backup,
		Replaced:   replaced,
		Missing:    missing,
	}, nil
}

// copyFile copies src to dst (used for jar backups).
func copyFile(src, dst string) error {
	in, err := os.ReadFile(src)
	if err != nil {
		return err
	}
	return os.WriteFile(dst, in, 0o644)
}

// verifyJarEntry re-opens a jar and returns the bytes of one entry, used to
// confirm a repack landed the expected content.
func verifyJarEntry(jarPath, entryPath string) ([]byte, error) {
	r, err := zip.OpenReader(jarPath)
	if err != nil {
		return nil, err
	}
	defer r.Close()
	for _, f := range r.File {
		if f.Name == entryPath {
			rc, err := f.Open()
			if err != nil {
				return nil, err
			}
			defer rc.Close()
			var buf bytes.Buffer
			if _, err := io.Copy(&buf, rc); err != nil {
				return nil, err
			}
			return buf.Bytes(), nil
		}
	}
	return nil, fmt.Errorf("entry %q not found after repack", entryPath)
}
