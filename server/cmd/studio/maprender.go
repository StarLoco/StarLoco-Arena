package main

import (
	"fmt"
	"os"
	"path/filepath"
	"sort"
	"strconv"

	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file implements the tile-rendered map view: it parses a map's .amw
// chunks + the shared elements.ade catalog itself (via the parser package,
// like mapexport.go) and flattens them into a list of drawable sprites, each
// with its gfx id, hot-point origin, flip, and resolved isometric altitude
// (computed by parser.ResolveCellGfx -- the same altitude loop that drives
// walkability, so tiles line up with the analytical overlays). The frontend
// then draws each sprite (decoded from gfx.jar TGA -> PNG, batched by
// GetMapGfxBatch) in back-to-front isometric order.

// MapDrawable is one sprite to paint for a cell.
type MapDrawable struct {
	X             int32   `json:"x"`
	Y             int32   `json:"y"`
	GfxID         int32   `json:"gfxId"`
	OriginX       int16   `json:"originX"`
	OriginY       int16   `json:"originY"`
	Flip          bool    `json:"flip"`
	Altitude      int16   `json:"altitude"`
	Level         int     `json:"level"`
	Order         int     `json:"order"`
	AltitudeOrder float32 `json:"altitudeOrder"`
}

// MapRender is the full drawable payload for one map.
type MapRender struct {
	ID        int           `json:"id"`
	MinX      int32         `json:"minX"`
	MinY      int32         `json:"minY"`
	MaxX      int32         `json:"maxX"`
	MaxY      int32         `json:"maxY"`
	Drawables []MapDrawable `json:"drawables"`
	GfxIDs    []int32       `json:"gfxIds"` // unique gfx ids used (for the batch fetch)
}

// GetMapRender parses a map's chunks and returns every tile sprite to draw,
// plus the set of unique gfx ids so the frontend can batch-decode them.
func (a *App) GetMapRender(id int) (MapRender, error) {
	if !a.paths.DataDirValid {
		return MapRender{}, fmt.Errorf("no valid data directory selected")
	}
	elements, err := a.loadElements()
	if err != nil {
		return MapRender{}, err
	}

	mapDir := filepath.Join(a.paths.DataDir, "maps", strconv.Itoa(id))
	entries, err := os.ReadDir(mapDir)
	if err != nil {
		return MapRender{}, fmt.Errorf("read map dir: %w", err)
	}

	var drawables []MapDrawable
	gfxSet := map[int32]bool{}
	first := true
	var minX, minY, maxX, maxY int32

	for _, fe := range entries {
		if fe.IsDir() || filepath.Ext(fe.Name()) != ".amw" {
			continue
		}
		raw, err := os.ReadFile(filepath.Join(mapDir, fe.Name()))
		if err != nil {
			return MapRender{}, err
		}
		_, body, err := parser.PeekAleaHeader(raw)
		if err != nil {
			return MapRender{}, fmt.Errorf("%s header: %w", fe.Name(), err)
		}
		chunk, err := parser.ParseAMWFile(body)
		if err != nil {
			return MapRender{}, fmt.Errorf("parse %s: %w", fe.Name(), err)
		}
		for _, cell := range chunk.Cells {
			if first {
				minX, maxX, minY, maxY = cell.X, cell.X, cell.Y, cell.Y
				first = false
			} else {
				if cell.X < minX {
					minX = cell.X
				}
				if cell.X > maxX {
					maxX = cell.X
				}
				if cell.Y < minY {
					minY = cell.Y
				}
				if cell.Y > maxY {
					maxY = cell.Y
				}
			}
			for _, g := range parser.ResolveCellGfx(cell, elements) {
				drawables = append(drawables, MapDrawable{
					X:             cell.X,
					Y:             cell.Y,
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
	}
	if first {
		return MapRender{}, fmt.Errorf("map %d has no cells", id)
	}

	gfxIDs := make([]int32, 0, len(gfxSet))
	for gid := range gfxSet {
		gfxIDs = append(gfxIDs, gid)
	}
	sort.Slice(gfxIDs, func(i, j int) bool { return gfxIDs[i] < gfxIDs[j] })

	return MapRender{
		ID:        id,
		MinX:      minX,
		MinY:      minY,
		MaxX:      maxX,
		MaxY:      maxY,
		Drawables: drawables,
		GfxIDs:    gfxIDs,
	}, nil
}

// MapGfx is one decoded gfx sprite for the map renderer.
type MapGfx struct {
	GfxID   int32  `json:"gfxId"`
	Width   int    `json:"width"`
	Height  int    `json:"height"`
	DataURL string `json:"dataUrl"`
}

// GetMapGfxBatch decodes the given gfx ids from gfx.jar (TGA -> PNG) in one
// call, so the frontend fetches all of a map's tile art at once instead of
// per-sprite. Missing/undecodable ids are silently skipped (the renderer
// falls back to a flat cell for those).
func (a *App) GetMapGfxBatch(gfxIDs []int32) ([]MapGfx, error) {
	if !a.paths.ClientDirValid {
		return nil, fmt.Errorf("no valid client directory selected")
	}
	r, err := a.openNamedJar("gfx.jar")
	if err != nil {
		return nil, err
	}
	out := make([]MapGfx, 0, len(gfxIDs))
	for _, gid := range gfxIDs {
		f := findEntry(r, fmt.Sprintf("gfx/%d.tga", gid))
		if f == nil {
			continue
		}
		url, w, h, err := a.decodeSpriteToPNG(f)
		if err != nil {
			continue
		}
		out = append(out, MapGfx{GfxID: gid, Width: w, Height: h, DataURL: url})
	}
	return out, nil
}

// loadElements parses the shared elements.ade catalog from the data dir
// (cached on the App so repeated map renders don't re-read it).
func (a *App) loadElements() (map[int32]parser.ElementDef, error) {
	a.elementsMu.Lock()
	defer a.elementsMu.Unlock()
	if a.elements != nil {
		return a.elements, nil
	}
	p := filepath.Join(a.paths.DataDir, "elements.ade")
	raw, err := os.ReadFile(p)
	if err != nil {
		return nil, fmt.Errorf("read elements.ade: %w", err)
	}
	_, body, err := parser.PeekAleaHeader(raw)
	if err != nil {
		return nil, fmt.Errorf("elements.ade header: %w", err)
	}
	ef, err := parser.ParseElementsFile(body)
	if err != nil {
		return nil, fmt.Errorf("parse elements.ade: %w", err)
	}
	a.elements = ef.Elements
	return a.elements, nil
}
