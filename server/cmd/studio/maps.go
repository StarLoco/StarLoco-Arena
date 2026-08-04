package main

import (
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strings"
)

// This file exposes the world/arena maps to the frontend: a list of every world
// that ships a topology (contents/maps/tplg/<id>.jar) and, per world, its
// decoded floor grid (altitude per cell) plus — for fight arenas — the team
// start cells and coach pedestals from the .fmd. The isometric projection
// constants match the client (aba_2): cellWidth 86, cellHeight 43, elevation 10.

const (
	mapCellWidth     = 86
	mapCellHeight    = 43
	mapElevationUnit = 10
	mapCellCap       = 40000 // guard huge overworlds in the UI
)

// MapInfo is one world's summary for the map picker.
type MapInfo struct {
	ID      int  `json:"id"`
	Tiles   int  `json:"tiles"`
	IsArena bool `json:"isArena"`
}

// MapCellDTO is one decoded floor cell (W = standable floor vs obstacle block).
type MapCellDTO struct {
	X   int  `json:"x"`
	Y   int  `json:"y"`
	Alt int  `json:"alt"`
	W   bool `json:"w"`
}

// MapSpawnDTO is a placement cell (start or coach).
type MapSpawnDTO struct {
	X int `json:"x"`
	Y int `json:"y"`
	Z int `json:"z"`
}

// MapDTO is a fully-decoded world for the isometric renderer.
type MapDTO struct {
	WorldID       int           `json:"worldId"`
	IsArena       bool          `json:"isArena"`
	MinX          int           `json:"minX"`
	MinY          int           `json:"minY"`
	MaxX          int           `json:"maxX"`
	MaxY          int           `json:"maxY"`
	CellWidth     int           `json:"cellWidth"`
	CellHeight    int           `json:"cellHeight"`
	ElevationUnit int           `json:"elevationUnit"`
	Cells         []MapCellDTO  `json:"cells"`
	Team0         []MapSpawnDTO `json:"team0"`
	Team1         []MapSpawnDTO `json:"team1"`
	Coach         []MapSpawnDTO `json:"coach"`
	Truncated     bool          `json:"truncated"`
	Error         string        `json:"error"`
}

// mapsDir returns the client's contents/maps directory, or an error if no valid
// client dir is selected.
func (a *App) mapsDir() (string, error) {
	a.mu.Lock()
	valid := a.paths.ClientDirValid
	dir := a.paths.ClientDir
	a.mu.Unlock()
	if !valid {
		return "", fmt.Errorf("no valid client directory selected (current: %q)", dir)
	}
	return filepath.Join(dir, "game", "contents", "maps"), nil
}

// ListMaps returns every world that ships a topology jar, arenas flagged.
func (a *App) ListMaps() ([]MapInfo, error) {
	dir, err := a.mapsDir()
	if err != nil {
		return nil, err
	}
	tplgDir := filepath.Join(dir, "tplg")
	entries, err := os.ReadDir(tplgDir)
	if err != nil {
		return nil, fmt.Errorf("no topology dir under %s: %w", dir, err)
	}
	var out []MapInfo
	for _, e := range entries {
		if e.IsDir() || !strings.HasSuffix(strings.ToLower(e.Name()), ".jar") {
			continue
		}
		id, ok := worldIDFromJar(e.Name())
		if !ok {
			continue
		}
		tiles, ok := tileCount(filepath.Join(tplgDir, e.Name()))
		if !ok {
			continue
		}
		info := MapInfo{ID: id, Tiles: tiles}
		info.IsArena = fightJarHasFmd(filepath.Join(dir, "fight", fmt.Sprintf("%d.jar", id)))
		out = append(out, info)
	}
	if len(out) == 0 {
		return nil, fmt.Errorf("no topology jars found under %s", tplgDir)
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// GetMap decodes one world's topology (and, for arenas, its placement cells).
func (a *App) GetMap(id int) (MapDTO, error) {
	dir, err := a.mapsDir()
	if err != nil {
		return MapDTO{}, err
	}
	dto := MapDTO{
		WorldID:       id,
		CellWidth:     mapCellWidth,
		CellHeight:    mapCellHeight,
		ElevationUnit: mapElevationUnit,
		// Non-nil so the JSON is [] (not null) for non-arena worlds; the webview
		// iterates these directly.
		Team0: []MapSpawnDTO{},
		Team1: []MapSpawnDTO{},
		Coach: []MapSpawnDTO{},
	}

	wt, err := parseTplgJar(filepath.Join(dir, "tplg", fmt.Sprintf("%d.jar", id)), mapCellCap)
	if err != nil {
		return MapDTO{}, fmt.Errorf("topology: %w", err)
	}
	dto.MinX, dto.MinY, dto.MaxX, dto.MaxY = int(wt.MinX), int(wt.MinY), int(wt.MaxX), int(wt.MaxY)
	dto.Truncated = wt.Truncated
	dto.Cells = make([]MapCellDTO, len(wt.Cells))
	for i, c := range wt.Cells {
		dto.Cells[i] = MapCellDTO{X: int(c.X), Y: int(c.Y), Alt: int(c.Alt), W: c.Walkable}
	}

	// Placement cells (arenas only).
	fightPath := filepath.Join(dir, "fight", fmt.Sprintf("%d.jar", id))
	if fightJarHasFmd(fightPath) {
		dto.IsArena = true
		if fd, ferr := parseFightJar(fightPath); ferr == nil && fd != nil {
			dto.Team0 = spawnDTOs(fd.Team0)
			dto.Team1 = spawnDTOs(fd.Team1)
			dto.Coach = spawnDTOs(coachInBounds(fd.Coach, wt))
		}
	}
	return dto, nil
}

// spawnDTOs converts decoded placement cells to DTOs.
func spawnDTOs(cells []fmdCell) []MapSpawnDTO {
	out := make([]MapSpawnDTO, 0, len(cells))
	for _, c := range cells {
		out = append(out, MapSpawnDTO{X: int(c.X), Y: int(c.Y), Z: int(c.Z)})
	}
	return out
}

// coachInBounds drops unset/sentinel coach slots (packed 0 -> x=-2047) and any
// that fall outside the world, keeping the real pedestals.
func coachInBounds(cells []fmdCell, wt *worldTopo) []fmdCell {
	var out []fmdCell
	for _, c := range cells {
		if c.X < wt.MinX-1 || c.X > wt.MaxX+1 || c.Y < wt.MinY-1 || c.Y > wt.MaxY+1 {
			continue
		}
		out = append(out, c)
	}
	return out
}

// --- Map graphics (decorative art layer) ------------------------------------

const mapDrawableCap = 30000

// MapDrawableDTO is one placeable map sprite (an element resolved against the
// global sprite table).
type MapDrawableDTO struct {
	GfxID   int  `json:"gfxId"`
	CellX   int  `json:"cellX"`
	CellY   int  `json:"cellY"`
	Alt     int  `json:"alt"`
	AbaH    int  `json:"abaH"`
	OriginX int  `json:"originX"`
	OriginY int  `json:"originY"`
	W       int  `json:"w"`
	H       int  `json:"h"`
	Flip    bool `json:"flip"`
}

// MapGfxDTO is a world's decorative sprite scene (already z-sorted back-to-front).
type MapGfxDTO struct {
	Drawables []MapDrawableDTO `json:"drawables"`
	Truncated bool             `json:"truncated"`
	Error     string           `json:"error"`
}

// elementsLib lazily loads + caches the global map sprite table.
func (a *App) elementsLib() (map[int32]spriteMeta, error) {
	a.mu.Lock()
	cached := a.spriteMeta
	a.mu.Unlock()
	if cached != nil {
		return cached, nil
	}
	dir, err := a.mapsDir()
	if err != nil {
		return nil, err
	}
	m, err := loadElementsLib(dir)
	if err != nil {
		return nil, err
	}
	a.mu.Lock()
	a.spriteMeta = m
	a.mu.Unlock()
	return m, nil
}

// GetMapGfx decodes a world's gfx scene tiles into placeable, z-sorted drawables.
func (a *App) GetMapGfx(id int) (MapGfxDTO, error) {
	dir, err := a.mapsDir()
	if err != nil {
		return MapGfxDTO{}, err
	}
	meta, err := a.elementsLib()
	if err != nil {
		return MapGfxDTO{Error: err.Error()}, nil
	}
	zpath := filepath.Join(dir, "gfx", fmt.Sprintf("%d.jar", id))
	if !fileExists(zpath) {
		return MapGfxDTO{Error: fmt.Sprintf("no gfx jar for world %d", id)}, nil
	}
	draws, trunc, err := parseGfxJar(zpath, meta, mapDrawableCap)
	if err != nil {
		return MapGfxDTO{Error: err.Error()}, nil
	}
	dto := MapGfxDTO{Truncated: trunc}
	dto.Drawables = make([]MapDrawableDTO, len(draws))
	for i, d := range draws {
		dto.Drawables[i] = MapDrawableDTO{
			GfxID: int(d.GfxID), CellX: int(d.CellX), CellY: int(d.CellY),
			Alt: int(d.Alt), AbaH: int(d.AbaH),
			OriginX: int(d.OriginX), OriginY: int(d.OriginY),
			W: int(d.W), H: int(d.H), Flip: d.Flip,
		}
	}
	return dto, nil
}

// MapSpriteDTO is a decoded map sprite bitmap (PNG data URL).
type MapSpriteDTO struct {
	GfxID   int    `json:"gfxId"`
	DataURL string `json:"dataUrl"`
	W       int    `json:"w"`
	H       int    `json:"h"`
}

// GetMapSprites decodes the given (unique) gfx ids from global gfx.jar
// (gfx/<id>.tgam) to PNG data URLs. Unknown/failed ids are skipped.
func (a *App) GetMapSprites(ids []int) ([]MapSpriteDTO, error) {
	r, err := a.openNamedJar("gfx.jar")
	if err != nil {
		return nil, err
	}
	seen := make(map[int]bool, len(ids))
	out := make([]MapSpriteDTO, 0, len(ids))
	for _, id := range ids {
		if seen[id] {
			continue
		}
		seen[id] = true
		f := findEntry(r, fmt.Sprintf("gfx/%d.tgam", id))
		if f == nil {
			continue
		}
		url, w, h, derr := a.decodeImageEntryToPNG(f, "tgam")
		if derr != nil {
			continue
		}
		out = append(out, MapSpriteDTO{GfxID: id, DataURL: url, W: w, H: h})
	}
	return out, nil
}
