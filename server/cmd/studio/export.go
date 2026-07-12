package main

import (
	"fmt"
	"os"
	"path/filepath"
	"time"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file implements the Phase 5 write/export side: byte-exact re-encoding
// of edited .dat data plus a safe export pipeline (validate -> back up ->
// atomic write -> verify). It NEVER overwrites a file in place without first
// (a) confirming the encoder round-trips the CURRENT on-disk file exactly
// [so we know the encoder is faithful for this file], and (b) copying the
// original to a timestamped .bak. See tools/README.md section 5.

// ExportResult reports what an export did, for the UI to show/confirm.
type ExportResult struct {
	Target     string `json:"target"`     // absolute path written
	BackupPath string `json:"backupPath"` // absolute path of the backup copy
	Bytes      int    `json:"bytes"`      // bytes written
}

// SaveSpells re-encodes the current spells (as edited via UpdateSpell) and
// exports them to data/spells.dat, backing up the original first. It edits
// only the fields the UI exposes; every other spell field and all effects
// are preserved from the freshly-reparsed on-disk file, so unrelated data
// can't be corrupted.
func (a *App) SaveSpells(edits []SpellEdit) (ExportResult, error) {
	if !a.paths.DataDirValid {
		return ExportResult{}, fmt.Errorf("no valid data directory selected")
	}
	target := filepath.Join(a.paths.DataDir, "spells.dat")

	orig, err := os.ReadFile(target)
	if err != nil {
		return ExportResult{}, fmt.Errorf("read %s: %w", target, err)
	}
	f, err := parser.ParseSpellsFile(orig)
	if err != nil {
		return ExportResult{}, fmt.Errorf("parse current spells.dat: %w", err)
	}

	// Safety gate: the encoder MUST reproduce the current file byte-for-byte
	// before we trust it to write a modified version. If this ever fails,
	// something about the on-disk file is outside our round-trip guarantee
	// and we refuse to export rather than risk corruption.
	if err := verifyRoundTrip(orig, encode.EncodeSpellsFile(f)); err != nil {
		return ExportResult{}, fmt.Errorf("refusing to export: %w", err)
	}

	// Apply edits by ID.
	byID := map[int32]*parser.SpellRaw{}
	for i := range f.Spells {
		byID[f.Spells[i].ID] = &f.Spells[i]
	}
	for _, e := range edits {
		s, ok := byID[e.ID]
		if !ok {
			return ExportResult{}, fmt.Errorf("edit for unknown spell id %d", e.ID)
		}
		applySpellEdit(s, e)
	}

	out := encode.EncodeSpellsFile(f)
	res, err := exportBytes(target, out)
	if err != nil {
		return res, err
	}
	// Rebuild the store so subsequent GetSpells reflect the export.
	a.store = a.newStoreForData()
	return res, nil
}

// SpellEdit is the subset of a spell the UI can modify. Only these fields
// are written back; everything else is preserved from disk.
type SpellEdit struct {
	ID                        int32  `json:"id"`
	ActionPointsCost          byte   `json:"actionPointsCost"`
	RangeMin                  byte   `json:"rangeMin"`
	RangeMax                  byte   `json:"rangeMax"`
	CastTestLineOfSight       bool   `json:"castTestLineOfSight"`
	CastOnlyLine              bool   `json:"castOnlyLine"`
	NeedFreeCell              bool   `json:"needFreeCell"`
	CastFrequencyMaxPerTurn   byte   `json:"castFrequencyMaxPerTurn"`
	CastFrequencyMaxPerPlayer byte   `json:"castFrequencyMaxPerPlayer"`
	CastFrequencyMinInterval  byte   `json:"castFrequencyMinInterval"`
	Price                     int32  `json:"price"`
	Criterion                 string `json:"criterion"`
}

func applySpellEdit(s *parser.SpellRaw, e SpellEdit) {
	s.ActionPointsCost = e.ActionPointsCost
	s.RangeMin = e.RangeMin
	s.RangeMax = e.RangeMax
	s.CastTestLineOfSight = e.CastTestLineOfSight
	s.CastOnlyLine = e.CastOnlyLine
	s.NeedFreeCell = e.NeedFreeCell
	s.CastFrequencyMaxPerTurn = e.CastFrequencyMaxPerTurn
	s.CastFrequencyMaxPerPlayer = e.CastFrequencyMaxPerPlayer
	s.CastFrequencyMinInterval = e.CastFrequencyMinInterval
	s.Price = e.Price
	s.Criterion = e.Criterion
}

// verifyRoundTrip returns an error if got != orig, with a short diagnostic.
func verifyRoundTrip(orig, got []byte) error {
	if len(orig) != len(got) {
		return fmt.Errorf("encoder round-trip length mismatch (orig=%d got=%d); encoder not faithful for this file", len(orig), len(got))
	}
	for i := range orig {
		if orig[i] != got[i] {
			return fmt.Errorf("encoder round-trip byte mismatch at offset %d; encoder not faithful for this file", i)
		}
	}
	return nil
}

// exportBytes backs up target (if it exists) to a timestamped .bak alongside
// it, then writes data atomically (temp file + rename) and reloads the
// gamedata store so the UI reflects the change immediately.
func exportBytes(target string, data []byte) (ExportResult, error) {
	backup := ""
	if _, err := os.Stat(target); err == nil {
		backup = fmt.Sprintf("%s.%s.bak", target, time.Now().Format("20060102-150405"))
		orig, err := os.ReadFile(target)
		if err != nil {
			return ExportResult{}, fmt.Errorf("read original for backup: %w", err)
		}
		if err := os.WriteFile(backup, orig, 0o644); err != nil {
			return ExportResult{}, fmt.Errorf("write backup: %w", err)
		}
	}

	// Atomic write: temp file in the same dir + rename over the target.
	tmp, err := os.CreateTemp(filepath.Dir(target), ".studio-export-*")
	if err != nil {
		return ExportResult{}, fmt.Errorf("create temp: %w", err)
	}
	tmpName := tmp.Name()
	if _, err := tmp.Write(data); err != nil {
		tmp.Close()
		os.Remove(tmpName)
		return ExportResult{}, fmt.Errorf("write temp: %w", err)
	}
	if err := tmp.Close(); err != nil {
		os.Remove(tmpName)
		return ExportResult{}, fmt.Errorf("close temp: %w", err)
	}
	if err := os.Rename(tmpName, target); err != nil {
		os.Remove(tmpName)
		return ExportResult{}, fmt.Errorf("rename temp over target: %w", err)
	}

	return ExportResult{Target: target, BackupPath: backup, Bytes: len(data)}, nil
}
