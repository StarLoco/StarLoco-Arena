package gamedata

import (
	"encoding/json"
	"fmt"
	"os"
	"path/filepath"
	"strconv"
	"sync"
)

// This file resolves per-map special-battlefield-cell layouts (the
// buff/damage tiles from the game manual §5.0.4, implemented in
// internal/combat/specialcells.go).
//
// SOURCE OF TRUTH: the special tiles are painted into every map's OWN art as
// negative-gfx "Bonus" elements (gfx -1002..-1009 -- see
// parser.ResolveCellGfx + Map.deriveSpecialCells), so their authentic
// positions AND types are recovered directly from the .amw map data. The
// negative gfx value's magnitude is exactly the matching staticEffects.dat
// SPECIAL area id (1002-1009) the client's StaticEffectAreaManager looks up,
// which cross-references the effect table 1:1 (verified against the real
// staticEffects.dat: 1002=instakill/killer, 1003=10dmg/trap, 1004=+range/
// eagle_eye, 1005=+resist/shield, 1006=+heal-received/panacea, 1007=+dmg/
// enthusiasm, 1008=+AP/motivation, 1009=+5HP/healing_heart).
//
// An optional per-map JSON side-file (data/maps/<id>/specialcells.json)
// OVERRIDES the derived layout when present, for maps whose art carries no
// markers or where a curator wants a bespoke layout. Each JSON entry carries:
//   - Type: the gameplay effect the server applies at turn start (mapped to
//     combat.SpecialCellType by the dispatch layer).
//   - CellBaseID: the client-side StaticEffectAreaManager template id the
//     CREATE_FIGHT packet references so the client renders the correct tile
//     art (the SPECIAL ids 1002-1009 / TRAP ids 1-2 from staticEffects.dat).

// SpecialCellTypeName is the gameplay type of a special cell, matching (by
// name) the manual's cell list and combat.SpecialCellType. Kept as a string
// in the data file for readability; the dispatch layer maps it to the combat
// enum.
type SpecialCellTypeName string

const (
	SpecialCellTrap         SpecialCellTypeName = "trap"
	SpecialCellEnthusiasm   SpecialCellTypeName = "enthusiasm"
	SpecialCellShield       SpecialCellTypeName = "shield"
	SpecialCellEagleEye     SpecialCellTypeName = "eagle_eye"
	SpecialCellPanacea      SpecialCellTypeName = "panacea"
	SpecialCellMotivation   SpecialCellTypeName = "motivation"
	SpecialCellHealingHeart SpecialCellTypeName = "healing_heart"
	SpecialCellKiller       SpecialCellTypeName = "killer"
)

// specialCellBaseIDToType maps a special tile's client render-template id
// (the staticEffects.dat SPECIAL area id == the magnitude of the map art's
// negative Bonus gfx) to its gameplay type. Confirmed field-by-field against
// the real staticEffects.dat area->effect table (see this file's header).
var specialCellBaseIDToType = map[int64]SpecialCellTypeName{
	1002: SpecialCellKiller,
	1003: SpecialCellTrap,
	1004: SpecialCellEagleEye,
	1005: SpecialCellShield,
	1006: SpecialCellPanacea,
	1007: SpecialCellEnthusiasm,
	1008: SpecialCellMotivation,
	1009: SpecialCellHealingHeart,
}

// specialCellTypeByBaseID resolves a special tile's gameplay type from its
// client render-template id (1002-1009). ok=false for any id outside the
// known SPECIAL range, so an unrecognised negative-gfx marker is ignored
// rather than mis-classified.
func specialCellTypeByBaseID(baseID int64) (SpecialCellTypeName, bool) {
	t, ok := specialCellBaseIDToType[baseID]
	return t, ok
}

// SpecialCellPlacement is one authored special cell on a map: its grid
// coordinate, gameplay effect type, and the client render template id.
type SpecialCellPlacement struct {
	X          int32               `json:"x"`
	Y          int32               `json:"y"`
	Type       SpecialCellTypeName `json:"type"`
	CellBaseID int64               `json:"cellBaseId"`
}

// SpecialCellLayout is the parsed content of one map's specialcells.json.
type SpecialCellLayout struct {
	Cells []SpecialCellPlacement `json:"cells"`
}

// SpecialCellStore lazily loads and caches per-map special-cell layouts. By
// default a map's layout is DERIVED from its own baked art (Map.SpecialCells,
// via the injected MapStore); an optional data/maps/<id>/specialcells.json
// side-file OVERRIDES that derivation when present. A map with neither simply
// has no special cells (an empty layout, not an error).
type SpecialCellStore struct {
	dataDir string
	maps    *MapStore // source of the derived (map-art) layout; may be nil

	mu      sync.Mutex
	layouts map[int]*SpecialCellLayout
}

// NewSpecialCellStore builds a SpecialCellStore rooted at dataDir (the
// directory containing maps/<id>/specialcells.json). maps supplies the
// derived-from-art layout used when a map has no JSON override; it may be nil
// (e.g. in a test that only exercises the JSON path), in which case only
// explicit JSON layouts are returned.
func NewSpecialCellStore(dataDir string, maps *MapStore) *SpecialCellStore {
	return &SpecialCellStore{
		dataDir: dataDir,
		maps:    maps,
		layouts: make(map[int]*SpecialCellLayout),
	}
}

// Get returns mapID's special-cell layout, loading (and caching) it on first
// use. Resolution order:
//  1. data/maps/<id>/specialcells.json, if present (an explicit override); a
//     malformed file returns an error so authoring mistakes are caught.
//  2. otherwise the layout DERIVED from the map's own baked negative-gfx
//     Bonus tiles (Map.SpecialCells), the authentic default.
//  3. otherwise an empty layout (no error) -- the map has no special tiles.
func (s *SpecialCellStore) Get(mapID int) (*SpecialCellLayout, error) {
	s.mu.Lock()
	defer s.mu.Unlock()
	if l, ok := s.layouts[mapID]; ok {
		return l, nil
	}

	path := filepath.Join(s.dataDir, "maps", strconv.Itoa(mapID), "specialcells.json")
	raw, err := os.ReadFile(path)
	if err != nil {
		if !os.IsNotExist(err) {
			return nil, fmt.Errorf("gamedata: read %s: %w", path, err)
		}
		// No override file -> derive from the map's own art.
		layout := s.deriveLayout(mapID)
		s.layouts[mapID] = layout
		return layout, nil
	}

	var layout SpecialCellLayout
	if err := json.Unmarshal(raw, &layout); err != nil {
		return nil, fmt.Errorf("gamedata: parse %s: %w", path, err)
	}
	s.layouts[mapID] = &layout
	return &layout, nil
}

// deriveLayout builds mapID's special-cell layout from the map's own baked
// negative-gfx Bonus tiles. Returns an empty layout if the map can't be
// loaded or carries no markers -- deriving special cells is best-effort and
// must never fail map/fight setup.
func (s *SpecialCellStore) deriveLayout(mapID int) *SpecialCellLayout {
	if s.maps == nil {
		return &SpecialCellLayout{}
	}
	m, err := s.maps.Get(mapID)
	if err != nil {
		return &SpecialCellLayout{}
	}
	derived := m.SpecialCells()
	if len(derived) == 0 {
		return &SpecialCellLayout{}
	}
	return &SpecialCellLayout{Cells: append([]SpecialCellPlacement(nil), derived...)}
}
