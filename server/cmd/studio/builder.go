package main

import (
	"encoding/base64"
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strconv"
	"strings"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file implements the map BUILDER: creating and editing fight maps.
// It builds on the byte-exact .amw encoder (encode/amw.go), the
// elements.ade catalog (loadElements), and the tile renderer (maprender.go).
//
// Editing model: a map is a set of .amw chunk files, each a Size*Size square
// of cells at chunk-offset (CoordX*Size, CoordY*Size). The editor loads all
// chunks, presents a flat per-cell view keyed by world (x,y) with each cell's
// stacked levels/elements, lets the UI mutate those, then re-encodes each
// chunk (byte-exact for untouched cells, freshly-built for edited ones) and
// writes it back to data/maps/<id>/ with a backup. The cell GRID (which
// coords exist, and their chunk assignment) is fixed at create/duplicate
// time; editing only changes each cell's element contents.

// PaletteElement is one placeable element definition for the builder palette.
type PaletteElement struct {
	ID           int32 `json:"id"`
	Kind         int   `json:"kind"`
	GfxID        int32 `json:"gfxId"`        // default state's gfx (0 if none)
	DefaultState byte  `json:"defaultState"` // first state byte
	StateCount   int   `json:"stateCount"`
	Walkable     bool  `json:"walkable"`
	Height       int   `json:"height"`
	Piled        bool  `json:"piled"`
}

// ListPaletteElements returns every elements.ade element that carries a gfx
// (Graphical/Bonus kinds), sorted by id, for the builder's tile palette. The
// UI lazily loads each element's thumbnail via GetSprite("gfx/<gfxId>.tga").
func (a *App) ListPaletteElements() ([]PaletteElement, error) {
	if !a.paths.DataDirValid {
		return nil, fmt.Errorf("no valid data directory selected")
	}
	elements, err := a.loadElements()
	if err != nil {
		return nil, err
	}
	out := make([]PaletteElement, 0, len(elements))
	for _, def := range elements {
		pe := PaletteElement{
			ID:         def.ID,
			Kind:       int(def.Kind),
			StateCount: len(def.States),
		}
		if len(def.States) > 0 {
			s := def.States[0]
			pe.DefaultState = s.State
			pe.GfxID = s.Properties.GfxID
			pe.Walkable = s.Properties.Walkable
			pe.Height = int(s.Properties.Height())
			pe.Piled = s.Properties.Piled
		}
		// Only expose elements that draw a real, positive gfx id (graphical
		// ground/wall/decoration tiles). Negative/zero gfx ids are sentinels
		// on non-tile elements (e.g. bonus markers) and aren't paintable.
		if pe.GfxID > 0 {
			out = append(out, pe)
		}
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// MarkerSpec is a ready-to-place non-graphical marker element (fight-start /
// coach-start) with its params already built, so the builder can drop
// playable spawn cells that carry no gfx (and thus aren't in the tile
// palette). Color is a UI hint for the overlay.
type MarkerSpec struct {
	Label   string         `json:"label"`
	Color   string         `json:"color"`
	Element MapEditElement `json:"element"`
}

// GetMarkerElements returns the placeable spawn markers (team A/B start,
// coach A/B) for the builder, using the elements.ade element ids whose Kind
// is FightStartPoint(1000)/FightStartCoachPoint(1001). Each is built with its
// single 1-byte team-side param (type tag 3): 1 = side A, 0 = side B.
func (a *App) GetMarkerElements() ([]MarkerSpec, error) {
	if !a.paths.DataDirValid {
		return nil, fmt.Errorf("no valid data directory selected")
	}
	elements, err := a.loadElements()
	if err != nil {
		return nil, err
	}
	// Pick the smallest element id for each marker kind (deterministic).
	startID, coachID := int32(-1), int32(-1)
	for id, def := range elements {
		switch def.Kind {
		case parser.ElementKindFightStartPoint:
			if startID < 0 || id < startID {
				startID = id
			}
		case parser.ElementKindFightStartCoachPoint:
			if coachID < 0 || id < coachID {
				coachID = id
			}
		}
	}

	build := func(elementID int32, side byte) MapEditElement {
		return MapEditElement{
			ElementID: elementID,
			State:     0,
			GroupID:   0,
			// param: [type-tag=3, value=side]
			ParamsB64:  base64.StdEncoding.EncodeToString([]byte{3, side}),
			ParamTypes: []int{3},
		}
	}

	var out []MarkerSpec
	if startID >= 0 {
		out = append(out,
			MarkerSpec{Label: "Team A start", Color: "#4f9dff", Element: build(startID, 1)},
			MarkerSpec{Label: "Team B start", Color: "#7c5cff", Element: build(startID, 0)},
		)
	}
	if coachID >= 0 {
		out = append(out,
			MarkerSpec{Label: "Coach A", Color: "#3ecf8e", Element: build(coachID, 1)},
			MarkerSpec{Label: "Coach B", Color: "#ffb454", Element: build(coachID, 0)},
		)
	}
	return out, nil
}

// --- editable per-cell model ---

// MapEditElement is one element in a cell's level stack, in editable form.
// ParamsB64/ParamTypes preserve the raw params for byte-exact round-trip of
// untouched elements; newly-placed elements from the palette have empty
// params (valid for graphical tiles, which take no params).
type MapEditElement struct {
	ElementID  int32  `json:"elementId"`
	State      byte   `json:"state"`
	GroupID    int32  `json:"groupId"`
	ParamsB64  string `json:"paramsB64"`  // base64 of raw ParamBytes
	ParamTypes []int  `json:"paramTypes"` // param type tags
	// derived (read-only display helpers)
	GfxID int32 `json:"gfxId"`
	Kind  int   `json:"kind"`
}

// MapEditCell is one cell's editable element stack (levels of elements).
type MapEditCell struct {
	X      int32              `json:"x"`
	Y      int32              `json:"y"`
	Levels [][]MapEditElement `json:"levels"`
}

// MapEditData is the full editable structure for a map.
type MapEditData struct {
	ID    int           `json:"id"`
	MinX  int32         `json:"minX"`
	MinY  int32         `json:"minY"`
	MaxX  int32         `json:"maxX"`
	MaxY  int32         `json:"maxY"`
	Cells []MapEditCell `json:"cells"`
}

// GetMapEditData loads a map's chunks and returns its per-cell editable
// element stacks, with each element's derived gfx for display.
func (a *App) GetMapEditData(id int) (MapEditData, error) {
	if !a.paths.DataDirValid {
		return MapEditData{}, fmt.Errorf("no valid data directory selected")
	}
	elements, err := a.loadElements()
	if err != nil {
		return MapEditData{}, err
	}
	chunks, err := a.loadMapChunks(id)
	if err != nil {
		return MapEditData{}, err
	}

	out := MapEditData{ID: id}
	first := true
	for _, ch := range chunks {
		for _, cell := range ch.chunk.Cells {
			if first {
				out.MinX, out.MaxX, out.MinY, out.MaxY = cell.X, cell.X, cell.Y, cell.Y
				first = false
			} else {
				out.MinX = min32(out.MinX, cell.X)
				out.MaxX = max32(out.MaxX, cell.X)
				out.MinY = min32(out.MinY, cell.Y)
				out.MaxY = max32(out.MaxY, cell.Y)
			}
			ec := MapEditCell{X: cell.X, Y: cell.Y}
			for _, lvl := range cell.Levels {
				els := make([]MapEditElement, 0, len(lvl.Elements))
				for _, el := range lvl.Elements {
					me := MapEditElement{
						ElementID:  el.ElementID,
						State:      el.State,
						GroupID:    el.GroupID,
						ParamsB64:  base64.StdEncoding.EncodeToString(el.ParamBytes),
						ParamTypes: bytesToInts(el.ParamTypes),
					}
					if def, ok := elements[el.ElementID]; ok {
						me.Kind = int(def.Kind)
						if props, ok := def.StateProperties(el.State); ok {
							me.GfxID = props.GfxID
						}
					}
					els = append(els, me)
				}
				ec.Levels = append(ec.Levels, els)
			}
			out.Cells = append(out.Cells, ec)
		}
	}
	sort.Slice(out.Cells, func(i, j int) bool {
		if out.Cells[i].Y != out.Cells[j].Y {
			return out.Cells[i].Y < out.Cells[j].Y
		}
		return out.Cells[i].X < out.Cells[j].X
	})
	return out, nil
}

// SaveMapEditData writes edited cells back into their chunks, re-encodes each
// chunk byte-exactly, and writes them to data/maps/<id>/ with backups. The
// gamedata store is reloaded so the viewer reflects the change.
func (a *App) SaveMapEditData(id int, cells []MapEditCell) ([]ExportResult, error) {
	if !a.paths.DataDirValid {
		return nil, fmt.Errorf("no valid data directory selected")
	}
	chunks, err := a.loadMapChunks(id)
	if err != nil {
		return nil, err
	}

	// Index edited cells by world (x,y).
	edited := make(map[[2]int32]MapEditCell, len(cells))
	for _, c := range cells {
		edited[[2]int32{c.X, c.Y}] = c
	}

	// Apply edits into each chunk's Cells (row-major).
	for ci := range chunks {
		ch := &chunks[ci]
		for idx := range ch.chunk.Cells {
			cell := &ch.chunk.Cells[idx]
			ec, ok := edited[[2]int32{cell.X, cell.Y}]
			if !ok {
				continue
			}
			rebuilt, rebErr := rebuildCell(cell.X, cell.Y, ec)
			if rebErr != nil {
				return nil, fmt.Errorf("cell (%d,%d): %w", cell.X, cell.Y, rebErr)
			}
			*cell = rebuilt
		}
	}

	// Re-encode and export each chunk.
	var results []ExportResult
	for _, ch := range chunks {
		data := encode.EncodeAMWFile(ch.header, ch.chunk)
		res, expErr := exportBytes(ch.path, data)
		if expErr != nil {
			return results, expErr
		}
		results = append(results, res)
	}

	a.store = a.newStoreForData()
	// Drop cached elements? elements.ade unchanged, keep it.
	return results, nil
}

// rebuildCell constructs an AMWCell from its editable form, materializing raw
// AMWCellElement records (decoding preserved params, or empty for new ones).
func rebuildCell(x, y int32, ec MapEditCell) (parser.AMWCell, error) {
	cell := parser.AMWCell{X: x, Y: y}
	for _, lvl := range ec.Levels {
		var out parser.AMWLevel
		for _, me := range lvl {
			paramBytes, err := base64.StdEncoding.DecodeString(me.ParamsB64)
			if err != nil {
				return parser.AMWCell{}, fmt.Errorf("bad params b64: %w", err)
			}
			out.Elements = append(out.Elements, parser.AMWCellElement{
				ElementID:  me.ElementID,
				State:      me.State,
				GroupID:    me.GroupID,
				ParamBytes: paramBytes,
				ParamTypes: intsToBytes(me.ParamTypes),
			})
		}
		cell.Levels = append(cell.Levels, out)
	}
	return cell, nil
}

// PreviewMapRender resolves a set of edited cells to renderable drawables
// (via the same parser.ResolveCellGfx altitude/stacking logic the real
// renderer uses) WITHOUT saving, so the builder can live-preview edits. It
// returns the same MapRender shape as GetMapRender.
func (a *App) PreviewMapRender(cells []MapEditCell) (MapRender, error) {
	if !a.paths.DataDirValid {
		return MapRender{}, fmt.Errorf("no valid data directory selected")
	}
	elements, err := a.loadElements()
	if err != nil {
		return MapRender{}, err
	}

	out := MapRender{ID: -1}
	gfxSet := map[int32]bool{}
	first := true
	for _, ec := range cells {
		if first {
			out.MinX, out.MaxX, out.MinY, out.MaxY = ec.X, ec.X, ec.Y, ec.Y
			first = false
		} else {
			out.MinX = min32(out.MinX, ec.X)
			out.MaxX = max32(out.MaxX, ec.X)
			out.MinY = min32(out.MinY, ec.Y)
			out.MaxY = max32(out.MaxY, ec.Y)
		}
		cell, err := rebuildCell(ec.X, ec.Y, ec)
		if err != nil {
			return MapRender{}, err
		}
		for _, g := range parser.ResolveCellGfx(cell, elements) {
			out.Drawables = append(out.Drawables, MapDrawable{
				X:             ec.X,
				Y:             ec.Y,
				GfxID:         g.GfxID,
				OriginX:       g.OriginX,
				OriginY:       g.OriginY,
				Flip:          g.Flip,
				Altitude:      g.Altitude,
				Level:         g.LevelIndex,
				Order:         g.Order,
				AltitudeOrder: g.AltitudeOrder,
			})
			gfxSet[g.GfxID] = true
		}
	}
	for gid := range gfxSet {
		out.GfxIDs = append(out.GfxIDs, gid)
	}
	sort.Slice(out.GfxIDs, func(i, j int) bool { return out.GfxIDs[i] < out.GfxIDs[j] })
	return out, nil
}

// --- create / duplicate ---

// DuplicateMap copies every file in data/maps/<srcID>/ to data/maps/<newID>/.
// Fails if the destination already exists (won't clobber).
func (a *App) DuplicateMap(srcID, newID int) error {
	if !a.paths.DataDirValid {
		return fmt.Errorf("no valid data directory selected")
	}
	srcDir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(srcID))
	dstDir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(newID))
	if _, err := os.Stat(srcDir); err != nil {
		return fmt.Errorf("source map %d not found", srcID)
	}
	if _, err := os.Stat(dstDir); err == nil {
		return fmt.Errorf("map %d already exists", newID)
	}
	entries, err := os.ReadDir(srcDir)
	if err != nil {
		return err
	}
	if err := os.MkdirAll(dstDir, 0o755); err != nil {
		return err
	}
	for _, e := range entries {
		if e.IsDir() {
			continue
		}
		b, err := os.ReadFile(filepath.Join(srcDir, e.Name()))
		if err != nil {
			return err
		}
		if err := os.WriteFile(filepath.Join(dstDir, e.Name()), b, 0o644); err != nil {
			return err
		}
	}
	a.store = a.newStoreForData()
	return nil
}

// CreateBlankMap creates a new single-chunk map of size*size empty cells at
// data/maps/<newID>/map_0_0.amw. Cells start with zero levels (empty); the
// user paints tiles from the palette. size must be 1..40.
func (a *App) CreateBlankMap(newID, size int) error {
	if !a.paths.DataDirValid {
		return fmt.Errorf("no valid data directory selected")
	}
	if size < 1 || size > 40 {
		return fmt.Errorf("size %d out of range (1..40)", size)
	}
	dstDir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(newID))
	if _, err := os.Stat(dstDir); err == nil {
		return fmt.Errorf("map %d already exists", newID)
	}
	if err := os.MkdirAll(dstDir, 0o755); err != nil {
		return err
	}

	chunk := parser.AMWMapChunk{CoordX: 0, CoordY: 0, Size: int32(size)}
	for ly := int32(0); ly < int32(size); ly++ {
		for lx := int32(0); lx < int32(size); lx++ {
			chunk.Cells = append(chunk.Cells, parser.AMWCell{X: lx, Y: ly})
		}
	}
	header := parser.AleaFileHeader{TypeCode: parser.AleaTypeCodeWorldMap, Version: parser.AleaDocumentVersion1}
	data := encode.EncodeAMWFile(header, chunk)
	if err := os.WriteFile(filepath.Join(dstDir, "map_0_0.amw"), data, 0o644); err != nil {
		return err
	}
	a.store = a.newStoreForData()
	return nil
}

// ClientMapInfo describes a map found inside the client's data.jar that can
// be imported into the local data/ dir for viewing/editing.
type ClientMapInfo struct {
	ID           int  `json:"id"`
	ChunkCount   int  `json:"chunkCount"`
	AlreadyLocal bool `json:"alreadyLocal"` // true if data/maps/<id>/ already exists
}

// ListClientMaps returns every map directory present in the client's data.jar
// (data/maps/<id>/), including the non-fight world/island maps (0 = the
// overworld coaches spawn on outside a fight, 1 = a secondary world) that
// aren't shipped in the repo's local data/maps by default.
func (a *App) ListClientMaps() ([]ClientMapInfo, error) {
	if !a.paths.ClientDirValid {
		return nil, fmt.Errorf("no valid client directory selected")
	}
	r, err := a.openNamedJar("data.jar")
	if err != nil {
		return nil, err
	}
	counts := map[int]int{}
	for _, f := range r.File {
		// entries look like data/maps/<id>/map_x_y.amw
		name := f.Name
		if !strings.HasPrefix(name, "data/maps/") || !strings.HasSuffix(name, ".amw") {
			continue
		}
		rest := strings.TrimPrefix(name, "data/maps/")
		slash := strings.IndexByte(rest, '/')
		if slash <= 0 {
			continue
		}
		id, convErr := strconv.Atoi(rest[:slash])
		if convErr != nil {
			continue
		}
		counts[id]++
	}
	out := make([]ClientMapInfo, 0, len(counts))
	for id, n := range counts {
		local := false
		if a.paths.DataDirValid {
			if st, statErr := os.Stat(filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(id))); statErr == nil && st.IsDir() {
				local = true
			}
		}
		out = append(out, ClientMapInfo{ID: id, ChunkCount: n, AlreadyLocal: local})
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// ImportMapFromClient extracts every .amw chunk of map <id> from the client's
// data.jar into the local data/maps/<id>/ so it can be viewed/edited in the
// studio. Refuses to clobber an existing local map. Returns the number of
// chunks written.
func (a *App) ImportMapFromClient(id int) (int, error) {
	if !a.paths.DataDirValid {
		return 0, fmt.Errorf("no valid data directory selected")
	}
	if !a.paths.ClientDirValid {
		return 0, fmt.Errorf("no valid client directory selected")
	}
	dstDir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(id))
	if _, err := os.Stat(dstDir); err == nil {
		return 0, fmt.Errorf("map %d already exists locally", id)
	}
	r, err := a.openNamedJar("data.jar")
	if err != nil {
		return 0, err
	}
	prefix := fmt.Sprintf("data/maps/%d/", id)
	if err := os.MkdirAll(dstDir, 0o755); err != nil {
		return 0, err
	}
	written := 0
	for _, f := range r.File {
		if !strings.HasPrefix(f.Name, prefix) || !strings.HasSuffix(f.Name, ".amw") {
			continue
		}
		base := f.Name[len(prefix):]
		if base == "" || strings.ContainsAny(base, "/\\") {
			continue // only flat chunk files
		}
		data, readErr := readZipEntry(f, 4<<20)
		if readErr != nil {
			return written, readErr
		}
		if writeErr := os.WriteFile(filepath.Join(dstDir, base), data, 0o644); writeErr != nil {
			return written, writeErr
		}
		written++
	}
	if written == 0 {
		os.Remove(dstDir)
		return 0, fmt.Errorf("map %d not found in client data.jar", id)
	}
	a.store = a.newStoreForData()
	return written, nil
}

// DeleteMap removes data/maps/<id>/ entirely (for discarding a draft). It
// refuses to delete the built-in fight maps 2..16 as a safety guard.
func (a *App) DeleteMap(id int) error {
	if !a.paths.DataDirValid {
		return fmt.Errorf("no valid data directory selected")
	}
	if id >= 2 && id <= 16 {
		return fmt.Errorf("refusing to delete built-in fight map %d", id)
	}
	dir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(id))
	if _, err := os.Stat(dir); err != nil {
		return fmt.Errorf("map %d not found", id)
	}
	return os.RemoveAll(dir)
}

// --- chunk loading helper ---

// loadedChunk pairs a parsed chunk with the file/header needed to re-encode it.
type loadedChunk struct {
	path   string
	header parser.AleaFileHeader
	chunk  parser.AMWMapChunk
}

// loadMapChunks reads and parses every .amw chunk of a map, sorted by
// filename for deterministic export order.
func (a *App) loadMapChunks(id int) ([]loadedChunk, error) {
	dir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(id))
	entries, err := os.ReadDir(dir)
	if err != nil {
		return nil, fmt.Errorf("read map dir: %w", err)
	}
	var out []loadedChunk
	for _, e := range entries {
		if e.IsDir() || filepath.Ext(e.Name()) != ".amw" {
			continue
		}
		p := filepath.Join(dir, e.Name())
		raw, err := os.ReadFile(p)
		if err != nil {
			return nil, err
		}
		header, body, err := parser.PeekAleaHeader(raw)
		if err != nil {
			return nil, fmt.Errorf("%s header: %w", e.Name(), err)
		}
		chunk, err := parser.ParseAMWFile(body)
		if err != nil {
			return nil, fmt.Errorf("parse %s: %w", e.Name(), err)
		}
		out = append(out, loadedChunk{path: p, header: header, chunk: chunk})
	}
	if len(out) == 0 {
		return nil, fmt.Errorf("map %d has no .amw chunks", id)
	}
	sort.Slice(out, func(i, j int) bool { return out[i].path < out[j].path })
	return out, nil
}

// --- small helpers ---

func bytesToInts(b []byte) []int {
	out := make([]int, len(b))
	for i, v := range b {
		out[i] = int(v)
	}
	return out
}

func intsToBytes(in []int) []byte {
	if len(in) == 0 {
		return nil
	}
	out := make([]byte, len(in))
	for i, v := range in {
		out[i] = byte(v)
	}
	return out
}

func min32(a, b int32) int32 {
	if a < b {
		return a
	}
	return b
}

func max32(a, b int32) int32 {
	if a > b {
		return a
	}
	return b
}
