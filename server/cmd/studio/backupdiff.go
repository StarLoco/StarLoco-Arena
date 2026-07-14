package main

import (
	"crypto/sha1"
	"encoding/hex"
	"fmt"
	"os"
	"path/filepath"
	"strings"

	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file adds a PREVIEW for backup restores: before overwriting the current
// file with a .bak, show what actually changes. Restore was previously a blind
// operation ("Restore spells.dat from 15:30:01?") -- now the UI can say
// "identical (no-op)" or "spells: 170 -> 168 (-2), 3.1 KB smaller" so you know
// exactly what you're rolling back to.
//
// For parseable .dat files it reports a per-kind record-count delta (the
// genuinely useful semantic diff); for jars/other files it reports size + a
// byte-identical check only. It never writes anything -- pure read/compare.

// RecordDelta is one record-kind's count in the current file vs the backup.
type RecordDelta struct {
	Kind    string `json:"kind"`    // e.g. "spells", "effects"
	Current int    `json:"current"` // count in the live file (0 if file missing)
	Backup  int    `json:"backup"`  // count in the backup
}

// BackupDiff summarizes what restoring a backup would change.
type BackupDiff struct {
	OrigName     string        `json:"origName"`     // e.g. spells.dat
	Stamp        string        `json:"stamp"`        // human timestamp of the backup
	Identical    bool          `json:"identical"`    // backup == current bytes (restore is a no-op)
	CurrentBytes int64         `json:"currentBytes"` // size of the live file (-1 if missing)
	BackupBytes  int64         `json:"backupBytes"`  // size of the backup
	Parsed       bool          `json:"parsed"`       // true if we produced record-count deltas
	Deltas       []RecordDelta `json:"deltas"`       // per-kind counts (only when Parsed)
	Note         string        `json:"note"`         // human summary line
}

// DiffBackup compares a studio .bak against its current original and returns a
// human-oriented summary of what a restore would change. Read-only.
func (a *App) DiffBackup(backupPath string) (BackupDiff, error) {
	base := filepath.Base(backupPath)
	m := backupNameRe.FindStringSubmatch(base)
	if m == nil {
		return BackupDiff{}, fmt.Errorf("not a studio backup file: %s", base)
	}
	if !a.pathUnderManagedRoot(backupPath) {
		return BackupDiff{}, fmt.Errorf("backup is outside the managed data/client directories")
	}
	backupData, err := os.ReadFile(backupPath)
	if err != nil {
		return BackupDiff{}, fmt.Errorf("read backup: %w", err)
	}
	origName := m[1]
	original := filepath.Join(filepath.Dir(backupPath), origName)

	out := BackupDiff{
		OrigName:    origName,
		Stamp:       humanStamp(m[2]),
		BackupBytes: int64(len(backupData)),
		CurrentBytes: func() int64 {
			if info, e := os.Stat(original); e == nil {
				return info.Size()
			}
			return -1
		}(),
	}

	curData, curErr := os.ReadFile(original)
	if curErr == nil {
		out.Identical = sha1sum(curData) == sha1sum(backupData)
	}

	// Per-kind record counts for parseable .dat files.
	if deltas, ok := recordCountDeltas(origName, curData, backupData); ok {
		out.Parsed = true
		out.Deltas = deltas
	}

	out.Note = summarizeDiff(out)
	return out, nil
}

// recordCountDeltas parses both versions of a known .dat and returns per-kind
// counts. `cur` may be nil/empty (missing current file) -> current counts 0.
// Returns ok=false for files we don't parse (jars, unknown).
func recordCountDeltas(name string, cur, backup []byte) ([]RecordDelta, bool) {
	counts := func(data []byte) (map[string]int, bool) {
		if len(data) == 0 {
			return map[string]int{}, true // treat missing as empty (all-zero counts)
		}
		switch strings.ToLower(name) {
		case "spells.dat":
			f, err := parser.ParseSpellsFile(data)
			if err != nil {
				return nil, false
			}
			return map[string]int{"spells": len(f.Spells), "effects": len(f.Effects)}, true
		case "cards.dat":
			f, err := parser.ParseCardsFile(data)
			if err != nil {
				return nil, false
			}
			return map[string]int{
				"coach cards":   len(f.CoachCards),
				"fighter cards": len(f.FighterCards),
				"effects":       len(f.Effects),
			}, true
		case "events.dat":
			f, err := parser.ParseEventsFile(data)
			if err != nil {
				return nil, false
			}
			return map[string]int{"events": len(f.Events), "effects": len(f.Effects)}, true
		case "summoning.dat":
			rows, err := parser.ParseSummoningFile(data)
			if err != nil {
				return nil, false
			}
			return map[string]int{"summonings": len(rows)}, true
		case "staticeffects.dat":
			f, err := parser.ParseStaticEffectsFile(data)
			if err != nil {
				return nil, false
			}
			return map[string]int{"static effects": len(f.Areas), "effects": len(f.Effects)}, true
		}
		return nil, false
	}

	curCounts, ok1 := counts(cur)
	bakCounts, ok2 := counts(backup)
	if !ok1 || !ok2 {
		return nil, false
	}
	// Union of kinds, stable order.
	order := []string{"spells", "coach cards", "fighter cards", "events", "summonings", "static effects", "effects"}
	seen := map[string]bool{}
	var out []RecordDelta
	for _, k := range order {
		_, inCur := curCounts[k]
		_, inBak := bakCounts[k]
		if inCur || inBak {
			out = append(out, RecordDelta{Kind: k, Current: curCounts[k], Backup: bakCounts[k]})
			seen[k] = true
		}
	}
	return out, len(out) > 0
}

// summarizeDiff builds a one-line human summary of a BackupDiff.
func summarizeDiff(d BackupDiff) string {
	if d.CurrentBytes < 0 {
		return "The current file is missing; restoring recreates it."
	}
	if d.Identical {
		return "Identical to the current file \u2014 restoring changes nothing."
	}
	parts := []string{}
	if d.Parsed {
		for _, r := range d.Deltas {
			if r.Current != r.Backup {
				parts = append(parts, fmt.Sprintf("%s %d\u2192%d (%+d)", r.Kind, r.Current, r.Backup, r.Backup-r.Current))
			}
		}
	}
	sizeDelta := d.BackupBytes - d.CurrentBytes
	sizeTxt := "same size"
	if sizeDelta != 0 {
		sizeTxt = fmt.Sprintf("%+d bytes", sizeDelta)
	}
	if len(parts) == 0 {
		return fmt.Sprintf("Content differs (%s) but record counts are unchanged.", sizeTxt)
	}
	return fmt.Sprintf("Restoring would set: %s \u00B7 %s.", strings.Join(parts, ", "), sizeTxt)
}

func sha1sum(b []byte) string {
	h := sha1.Sum(b)
	return hex.EncodeToString(h[:])
}
