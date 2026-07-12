package main

import (
	"encoding/json"
	"fmt"
	"os"
	"path"
	"path/filepath"
	"strconv"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file wires the Phase 7 map write/export path together: byte-exact
// .amw re-encode (encode.EncodeAMWFile) + the safe file/jar export
// primitives (export.go / repack.go). It also handles the simplest,
// highest-value map edit -- authoring per-map special-battlefield-cell
// placements (data/maps/<id>/specialcells.json) -- which needs no binary
// encoder at all.

// SpecialCellDTO is the JSON shape the UI sends/receives for one special
// cell (mirrors gamedata.SpecialCellPlacement).
type SpecialCellDTO struct {
	X          int32  `json:"x"`
	Y          int32  `json:"y"`
	Type       string `json:"type"`
	CellBaseID int64  `json:"cellBaseId"`
}

// GetSpecialCells returns a map's special cells. By default these are DERIVED
// from the map's own baked negative-gfx Bonus tiles (via the gamedata store);
// a data/maps/<id>/specialcells.json override is used automatically when
// present (both handled inside gamedata.SpecialCellStore.Get). Returns an
// empty slice for a map with neither.
func (a *App) GetSpecialCells(mapID int) ([]SpecialCellDTO, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	layout, err := store.SpecialCells.Get(mapID)
	if err != nil {
		return nil, err
	}
	out := make([]SpecialCellDTO, 0, len(layout.Cells))
	for _, c := range layout.Cells {
		out = append(out, SpecialCellDTO{X: c.X, Y: c.Y, Type: string(c.Type), CellBaseID: c.CellBaseID})
	}
	return out, nil
}

// SaveSpecialCells writes a map's special-cell layout to
// data/maps/<id>/specialcells.json, backing up any existing file first.
func (a *App) SaveSpecialCells(mapID int, cells []SpecialCellDTO) (ExportResult, error) {
	if !a.paths.DataDirValid {
		return ExportResult{}, fmt.Errorf("no valid data directory selected")
	}
	layout := gamedata.SpecialCellLayout{Cells: make([]gamedata.SpecialCellPlacement, 0, len(cells))}
	for _, c := range cells {
		layout.Cells = append(layout.Cells, gamedata.SpecialCellPlacement{
			X: c.X, Y: c.Y, Type: gamedata.SpecialCellTypeName(c.Type), CellBaseID: c.CellBaseID,
		})
	}
	data, err := json.MarshalIndent(layout, "", "  ")
	if err != nil {
		return ExportResult{}, err
	}
	data = append(data, '\n')

	dir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(mapID))
	if err := os.MkdirAll(dir, 0o755); err != nil {
		return ExportResult{}, err
	}
	target := filepath.Join(dir, "specialcells.json")
	res, err := exportBytes(target, data)
	if err != nil {
		return res, err
	}
	// Refresh the store so GetSpecialCells / combat see the new layout.
	a.store = a.newStoreForData()
	return res, nil
}

// ExportMapToClientJar re-encodes a map's .amw chunk files and repacks them
// into the client's data.jar under data/maps/<id>/, so a modified client
// loads the edited map. Before writing, it verifies each chunk's encoder
// round-trips the CURRENT on-disk file byte-for-byte (the same safety gate as
// the .dat export), refusing otherwise. This variant re-packs the maps
// UNCHANGED from data/ (the byte-exact bridge from the loose data/ dir into
// the client jar); actual geometry editing rides on the same encoder once a
// chunk-editing UI mutates the parsed AMWMapChunk before re-encode.
func (a *App) ExportMapToClientJar(mapID int) (RepackResult, error) {
	if !a.paths.DataDirValid {
		return RepackResult{}, fmt.Errorf("no valid data directory selected")
	}
	if !a.paths.ClientDirValid {
		return RepackResult{}, fmt.Errorf("no valid client directory selected")
	}

	mapDir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(mapID))
	entries, err := os.ReadDir(mapDir)
	if err != nil {
		return RepackResult{}, fmt.Errorf("read map dir: %w", err)
	}

	var replacements []RepackReplacement
	for _, fe := range entries {
		if fe.IsDir() || filepath.Ext(fe.Name()) != ".amw" {
			continue
		}
		p := filepath.Join(mapDir, fe.Name())
		raw, err := os.ReadFile(p)
		if err != nil {
			return RepackResult{}, err
		}
		header, body, err := parser.PeekAleaHeader(raw)
		if err != nil {
			return RepackResult{}, fmt.Errorf("%s header: %w", fe.Name(), err)
		}
		chunk, err := parser.ParseAMWFile(body)
		if err != nil {
			return RepackResult{}, fmt.Errorf("parse %s: %w", fe.Name(), err)
		}
		reenc := encode.EncodeAMWFile(header, chunk)
		if err := verifyRoundTrip(raw, reenc); err != nil {
			return RepackResult{}, fmt.Errorf("refusing to export %s: %w", fe.Name(), err)
		}
		// The jar path convention is data/maps/<id>/<file>.amw (forward
		// slashes, matching how the client jar stores them).
		jarEntry := path.Join("data", "maps", strconv.Itoa(mapID), fe.Name())
		replacements = append(replacements, RepackReplacement{EntryPath: jarEntry, Data: reenc})
	}
	if len(replacements) == 0 {
		return RepackResult{}, fmt.Errorf("no .amw chunks found for map %d", mapID)
	}

	jarPath := filepath.Join(a.paths.ClientDir, "game", "contents", "data.jar")
	return repackJar(jarPath, replacements)
}
