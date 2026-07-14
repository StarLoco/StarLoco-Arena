package main

import (
	"fmt"
	"os"
	"path/filepath"
	"regexp"
	"sort"
	"strings"
	"time"
)

// This file exposes the safety net every write in the studio creates: the
// timestamped .bak copies left by exportBytes (data/*.dat) and repackJar
// (client jars, i18n/data pushes). It lets the UI list every backup and restore
// any of them over its original -- and taking a fresh backup of the CURRENT
// file first, so a restore is itself undoable.
//
// Backup naming convention (see exportBytes / repackJar):
//   <original path>.<YYYYMMDD-HHMMSS>.bak
// e.g. spells.dat.20260714-153001.bak  or  data.jar.20260714-153044.bak

var backupNameRe = regexp.MustCompile(`^(.*)\.(\d{8}-\d{6})\.bak$`)

// BackupEntry is one restorable backup file.
type BackupEntry struct {
	Path       string `json:"path"`       // absolute path of the .bak file
	Original   string `json:"original"`   // absolute path it restores to
	OrigName   string `json:"origName"`   // base name of the original (e.g. spells.dat)
	Stamp      string `json:"stamp"`      // human timestamp
	Bytes      int64  `json:"bytes"`      // size of the backup
	Area       string `json:"area"`       // "data" | "client"
	Restorable bool   `json:"restorable"` // the original path still exists / is writable dir
}

// BackupsResult is the payload for the backups panel.
type BackupsResult struct {
	Entries []BackupEntry `json:"entries"`
	Error   string        `json:"error"`
}

// ListBackups scans the data dir and the client contents dir (recursively, but
// shallowly bounded) for *.bak files matching the studio's naming convention,
// newest first.
func (a *App) ListBackups() BackupsResult {
	var entries []BackupEntry

	if a.paths.DataDirValid {
		entries = append(entries, scanBackups(a.paths.DataDir, "data")...)
	}
	if dir, err := a.contentsDir(); err == nil {
		entries = append(entries, scanBackups(dir, "client")...)
	}

	// Newest first (by the timestamp encoded in the name).
	sort.Slice(entries, func(i, j int) bool { return entries[i].Stamp > entries[j].Stamp })

	if len(entries) == 0 {
		return BackupsResult{Entries: []BackupEntry{}}
	}
	return BackupsResult{Entries: entries}
}

// scanBackups walks root up to a bounded depth looking for *.bak files.
func scanBackups(root, area string) []BackupEntry {
	var out []BackupEntry
	rootDepth := strings.Count(filepath.Clean(root), string(os.PathSeparator))
	_ = filepath.WalkDir(root, func(p string, d os.DirEntry, err error) error {
		if err != nil {
			return nil
		}
		if d.IsDir() {
			// Bound the walk to a few levels so we don't traverse huge asset trees.
			if strings.Count(filepath.Clean(p), string(os.PathSeparator))-rootDepth > 4 {
				return filepath.SkipDir
			}
			return nil
		}
		m := backupNameRe.FindStringSubmatch(d.Name())
		if m == nil {
			return nil
		}
		original := filepath.Join(filepath.Dir(p), m[1])
		info, _ := d.Info()
		var size int64
		if info != nil {
			size = info.Size()
		}
		restorable := true
		if _, statErr := os.Stat(filepath.Dir(original)); statErr != nil {
			restorable = false
		}
		out = append(out, BackupEntry{
			Path:       p,
			Original:   original,
			OrigName:   m[1],
			Stamp:      humanStamp(m[2]),
			Bytes:      size,
			Area:       area,
			Restorable: restorable,
		})
		return nil
	})
	return out
}

// humanStamp turns "20260714-153001" into "2026-07-14 15:30:01".
func humanStamp(s string) string {
	t, err := time.Parse("20060102-150405", s)
	if err != nil {
		return s
	}
	return t.Format("2006-01-02 15:04:05")
}

// RestoreBackup copies a .bak file over its original, after first taking a
// fresh timestamped backup of the CURRENT original (so a restore is itself
// undoable). The backup path must be one ListBackups would return (guards
// against arbitrary path writes). Returns the ExportResult of the restore.
func (a *App) RestoreBackup(backupPath string) (ExportResult, error) {
	base := filepath.Base(backupPath)
	m := backupNameRe.FindStringSubmatch(base)
	if m == nil {
		return ExportResult{}, fmt.Errorf("not a studio backup file: %s", base)
	}
	// Confine restores to the data dir or the client contents dir.
	if !a.pathUnderManagedRoot(backupPath) {
		return ExportResult{}, fmt.Errorf("backup is outside the managed data/client directories")
	}
	if _, err := os.Stat(backupPath); err != nil {
		return ExportResult{}, fmt.Errorf("backup not found: %w", err)
	}
	original := filepath.Join(filepath.Dir(backupPath), m[1])

	data, err := os.ReadFile(backupPath)
	if err != nil {
		return ExportResult{}, fmt.Errorf("read backup: %w", err)
	}

	// If the original is a jar we may hold a cached handle; drop it first.
	a.jars.invalidate(original)

	// exportBytes backs up the CURRENT original before overwriting, then writes
	// atomically -- exactly the guarantees we want for a restore.
	res, err := exportBytes(original, data)
	if err != nil {
		return res, err
	}

	// Reload data store if we restored a data file; drop i18n cache for i18n.jar.
	if a.paths.DataDirValid && strings.HasPrefix(filepath.Clean(original), filepath.Clean(a.paths.DataDir)) {
		a.store = a.newStoreForData()
	}
	if strings.EqualFold(filepath.Base(original), "i18n.jar") {
		a.i18nMu.Lock()
		a.i18n = nil
		a.i18nMu.Unlock()
	}
	return res, nil
}

// pathUnderManagedRoot reports whether p is inside the data dir or the client
// contents dir (so restores can't touch arbitrary files).
func (a *App) pathUnderManagedRoot(p string) bool {
	clean := filepath.Clean(p)
	if a.paths.DataDirValid {
		if strings.HasPrefix(clean, filepath.Clean(a.paths.DataDir)) {
			return true
		}
	}
	if dir, err := a.contentsDir(); err == nil {
		if strings.HasPrefix(clean, filepath.Clean(dir)) {
			return true
		}
	}
	return false
}

// DeleteBackup removes a single backup file (must be a managed .bak). Used by
// the UI's "delete" action to prune old backups.
func (a *App) DeleteBackup(backupPath string) error {
	if backupNameRe.FindStringSubmatch(filepath.Base(backupPath)) == nil {
		return fmt.Errorf("not a studio backup file")
	}
	if !a.pathUnderManagedRoot(backupPath) {
		return fmt.Errorf("backup is outside the managed directories")
	}
	return os.Remove(backupPath)
}
