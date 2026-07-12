package main

import (
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strconv"
)

// This file implements Phase 4: exposing parsed map data (reusing
// gamedata.MapStore / the .amw+elements.ade parsers) to the frontend for an
// isometric render with gameplay overlays. All the heavy lifting (parsing,
// altitude/walkability resolution) is already done by internal/gamedata; we
// only flatten it into a compact, JSON-friendly per-cell shape here.

// MapSummary is one map's entry in the map picker.
type MapSummary struct {
	ID        int  `json:"id"`
	CellCount int  `json:"cellCount"`
	IsFight   bool `json:"isFight"`
}

// MapCell is one rendered cell: its world coords plus the flattened surface
// facts the viewer needs (top standing altitude, walkability, whether it
// blocks line of sight).
type MapCell struct {
	X           int32 `json:"x"`
	Y           int32 `json:"y"`
	Walkable    bool  `json:"walkable"`
	HasStanding bool  `json:"hasStanding"`
	StandingAlt int16 `json:"standingAlt"`
	// RenderAlt is the topmost surface altitude (walkable OR not) used ONLY
	// to lift this cell's overlay diamond onto its visible tile, so a blocked
	// cell (water/wall) doesn't float at ground level. Falls back to
	// StandingAlt when no surface exists.
	RenderAlt int16 `json:"renderAlt"`
	// TopAlt is the cell's VISUAL TOP: max(surface base altitude + height)
	// over every surface. This is where the client anchors its cell highlight
	// diamond (DisplayedElement.transformHighLightMesh uses getScreenTopY()
	// = screenY + (altitude+height)*elevationUnit for flat tiles), i.e. the
	// top FACE of a block, not its base. Float because sloped tiles have
	// half-unit heights. Falls back to RenderAlt when no surface exists.
	TopAlt       float64 `json:"topAlt"`
	BlocksLOS    bool    `json:"blocksLos"`
	SurfaceCount int     `json:"surfaceCount"`
}

// MarkerCell is a special cell (fight-start / coach-start), keyed by its
// team-side byte, that the viewer overlays on the grid.
type MarkerCell struct {
	X    int32 `json:"x"`
	Y    int32 `json:"y"`
	Side byte  `json:"side"`
}

// MapData is the full payload for rendering one map.
type MapData struct {
	ID         int          `json:"id"`
	MinX       int32        `json:"minX"`
	MinY       int32        `json:"minY"`
	MaxX       int32        `json:"maxX"`
	MaxY       int32        `json:"maxY"`
	Cells      []MapCell    `json:"cells"`
	FightStart []MarkerCell `json:"fightStart"`
	CoachStart []MarkerCell `json:"coachStart"`
}

// ListMaps returns EVERY map directory under data/maps (not just fight maps),
// flagging which are usable fight maps. This includes the non-fight world/
// island maps (e.g. map 0, the overworld coaches spawn on outside a fight)
// once they've been imported from the client jar (ImportMapFromClient).
func (a *App) ListMaps() ([]MapSummary, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	fightIDs, err := store.Maps.FightMapIDs()
	if err != nil {
		return nil, err
	}
	fightSet := map[int]bool{}
	for _, id := range fightIDs {
		fightSet[id] = true
	}

	// Enumerate every numeric map directory under data/maps.
	mapsDir := filepath.Join(a.paths.DataDir, "maps")
	entries, err := os.ReadDir(mapsDir)
	if err != nil {
		return nil, fmt.Errorf("read maps dir: %w", err)
	}
	var ids []int
	for _, e := range entries {
		if !e.IsDir() {
			continue
		}
		if id, convErr := strconv.Atoi(e.Name()); convErr == nil {
			ids = append(ids, id)
		}
	}
	sort.Ints(ids)

	out := make([]MapSummary, 0, len(ids))
	for _, id := range ids {
		m, err := store.Maps.Get(id)
		if err != nil {
			continue // unparseable dir, skip
		}
		out = append(out, MapSummary{
			ID:        id,
			CellCount: len(m.Cells()),
			IsFight:   fightSet[id],
		})
	}
	if len(out) == 0 {
		return nil, fmt.Errorf("no maps found (data/maps empty or unparseable)")
	}
	return out, nil
}

// GetMap loads one map and flattens it into a render-ready MapData.
func (a *App) GetMap(id int) (MapData, error) {
	store, err := a.requireStore()
	if err != nil {
		return MapData{}, err
	}
	m, err := store.Maps.Get(id)
	if err != nil {
		return MapData{}, err
	}

	minX, minY, maxX, maxY, ok := m.Bounds()
	if !ok {
		return MapData{}, fmt.Errorf("map %d has no cells", id)
	}

	coords := m.Cells()
	cells := make([]MapCell, 0, len(coords))
	// realAlt[x,y] = top-surface altitude of every cell that HAS a surface,
	// used afterwards to give EMPTY (no-tile) border cells a sensible ground
	// altitude from their neighbours instead of a hard 0 (see below).
	realAlt := make(map[[2]int32]int16)
	for _, c := range coords {
		surfaces := m.SurfacesAt(c.X, c.Y)
		alt, hasStanding := m.StandingAltitudeAt(c.X, c.Y)
		// A cell is "walkable" for the overlay if it has any walkable
		// surface at its standing altitude.
		walkable := hasStanding && m.IsWalkable(c.X, c.Y, alt)
		// Rendering altitude: lift the overlay onto the TOP surface (walkable
		// or not) so blocked cells (water/wall) align with their tile instead
		// of sinking to ground level. Fall back to the standing altitude.
		renderAlt, hasTop := m.TopSurfaceAltitudeAt(c.X, c.Y)
		if !hasTop {
			renderAlt = alt
		}
		// Visual top FACE (base+height) of the topmost surface -- where the
		// client anchors cell-highlight / start diamonds. Falls back to the
		// base render altitude when no surface exists.
		topAlt, hasFace := m.TopFaceAltitudeAt(c.X, c.Y)
		if !hasFace {
			topAlt = float64(renderAlt)
		}
		if len(surfaces) > 0 {
			realAlt[[2]int32{c.X, c.Y}] = renderAlt
		}
		cells = append(cells, MapCell{
			X:            c.X,
			Y:            c.Y,
			Walkable:     walkable,
			HasStanding:  hasStanding,
			StandingAlt:  alt,
			RenderAlt:    renderAlt,
			TopAlt:       topAlt,
			BlocksLOS:    m.BlocksLineOfSight(c.X, c.Y),
			SurfaceCount: len(surfaces),
		})
	}

	// Second pass: give EMPTY (0-surface) border cells a ground altitude
	// borrowed from their surrounding real tiles, so their overlay diamonds
	// sit on the same plane as the terrain instead of floating at a hard 0
	// (an empty edge cell next to ground at alt -11 was drawn 11 units too
	// high -- the reported "cells stick together while far apart"). We use the
	// nearest ringed neighbours that actually have a surface and average their
	// top altitudes; cells with no surfaced neighbour at all keep 0.
	for i := range cells {
		if cells[i].SurfaceCount > 0 {
			continue
		}
		if a, ok := neighbourGroundAlt(realAlt, cells[i].X, cells[i].Y); ok {
			cells[i].RenderAlt = a
			cells[i].TopAlt = float64(a)
		}
	}

	return MapData{
		ID:         id,
		MinX:       minX,
		MinY:       minY,
		MaxX:       maxX,
		MaxY:       maxY,
		Cells:      cells,
		FightStart: flattenMarkers(m.FightStartCells()),
		CoachStart: flattenMarkers(m.CoachStartCells()),
	}, nil
}

// neighbourGroundAlt estimates the ground altitude for an empty (no-surface)
// cell from the CLOSEST surfaced neighbour(s) in realAlt. It expands the
// search outward by Manhattan distance (nearest ground wins), and at the
// first distance that yields any surfaced cells returns the average of THOSE
// (usually just the one or two adjacent tiles), rounded to nearest. Using the
// nearest ground rather than a whole Chebyshev ring keeps a border cell glued
// to the edge tile it touches (e.g. -11) instead of being dragged toward 0 by
// farther/higher tiles. Returns (0,false) if nothing is found within radius 4.
func neighbourGroundAlt(realAlt map[[2]int32]int16, x, y int32) (int16, bool) {
	for r := int32(1); r <= 4; r++ {
		var sum, n int
		for dx := -r; dx <= r; dx++ {
			for dy := -r; dy <= r; dy++ {
				// Manhattan ring at exactly distance r (nearest-first).
				if abs32i(dx)+abs32i(dy) != r {
					continue
				}
				if a, ok := realAlt[[2]int32{x + dx, y + dy}]; ok {
					sum += int(a)
					n++
				}
			}
		}
		if n > 0 {
			avg := (sum*2 + sign(sum)*n) / (2 * n) // round-to-nearest
			return int16(avg), true
		}
	}
	return 0, false
}

func abs32i(v int32) int32 {
	if v < 0 {
		return -v
	}
	return v
}

func sign(v int) int {
	if v < 0 {
		return -1
	}
	return 1
}

// flattenMarkers turns a side->cells map into a flat, sorted MarkerCell list.
func flattenMarkers(byside map[byte][][2]int32) []MarkerCell {
	var out []MarkerCell
	for side, cells := range byside {
		for _, c := range cells {
			out = append(out, MarkerCell{X: c[0], Y: c[1], Side: side})
		}
	}
	sort.Slice(out, func(i, j int) bool {
		if out[i].Side != out[j].Side {
			return out[i].Side < out[j].Side
		}
		if out[i].Y != out[j].Y {
			return out[i].Y < out[j].Y
		}
		return out[i].X < out[j].X
	})
	return out
}
