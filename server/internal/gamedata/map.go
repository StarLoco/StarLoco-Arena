package gamedata

import (
	"fmt"
	"math/rand"
	"os"
	"path/filepath"
	"sort"
	"strconv"
	"strings"
	"sync"

	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase K: a
// real .amw/elements.ade-backed Map type exposing per-cell walkability and
// altitude, replacing the combat engine's previous always-walkable stub
// (internal/combat/turns.go's IsWalkable/ArrivalAltitude). See
// docs/04-game-data-format.md §4.9 for the full binary-format reference
// this is built on.

// MapCellFact is one resolved (altitude, walkable) surface fact for a
// cell, re-exported from parser.ResolvedSurface so combat/other packages
// don't need to import internal/gamedata/parser directly.
type MapCellFact = parser.ResolvedSurface

// Map is one fully-loaded fight map: every .amw chunk file under
// maps/<mapID>/ parsed and merged into a single lookup, plus the shared
// elements.ade catalog needed to resolve walkability/height per element.
// Construct via MapStore.Get (lazy, cached) rather than directly.
type Map struct {
	ID    int
	cells map[[2]int32][]MapCellFact // world (X,Y) -> resolved surfaces, precomputed at load time

	// coachStartCells/fightStartCells record every
	// FightStartCoachPointElement(1001)/FightStartPointElement(1000)
	// cell found in this map's chunk files, keyed by the element's
	// single team-side param byte (see docs/04-game-data-format.md
	// §4.9.5) -- 1 typically meaning "team A/first coach", 0 "team B/
	// second coach", though this project treats it as an opaque
	// grouping key rather than hardcoding a specific meaning, since only
	// two real fight-map chunks have ever been cross-checked.
	coachStartCells map[byte][][2]int32
	fightStartCells map[byte][][2]int32

	// specialCells holds the special battlefield cells (killer/trap/
	// eagle_eye/shield/panacea/enthusiasm/motivation/healing_heart)
	// DERIVED from this map's own baked art: the original game paints each
	// special tile as a negative-gfx Bonus element (gfx -1002..-1009, see
	// deriveSpecialCells + specialCellTypeByBaseID), so the authentic
	// positions are recoverable from the .amw data itself rather than
	// hand-authored. Populated once at load time, sorted (Y then X).
	specialCells []SpecialCellPlacement
}

// NewMapForTest builds a bare *Map from an explicit set of per-cell
// surface lists, with no coach/fight-start cell data. Exported strictly
// so OTHER packages' tests (e.g. internal/combat's line-of-sight tests)
// can construct a synthetic Map without needing real .amw/elements.ade
// fixtures -- mirrors the gamedata-internal newSyntheticMapWithSurfaces
// test helper (map_test.go), just exported for cross-package use. Not
// intended for any non-test production use.
func NewMapForTest(cells map[[2]int32][]MapCellFact) *Map {
	m := &Map{
		cells:           make(map[[2]int32][]MapCellFact),
		coachStartCells: make(map[byte][][2]int32),
		fightStartCells: make(map[byte][][2]int32),
	}
	for c, surfaces := range cells {
		m.cells[c] = surfaces
	}
	return m
}

// IsWalkable reports whether any resolved surface at (x,y) is walkable at
// the given altitude z -- an exact match on altitude, mirroring
// WorldCell.isWalkable(short z)'s own exact-altitude lookup (confirmed via
// javap: `if (element.getAltitude() == z) return ...isWalkable()`, no
// tolerance/rounding).
func (m *Map) IsWalkable(x, y int32, z int16) bool {
	facts, ok := m.cells[[2]int32{x, y}]
	if !ok {
		return false // no map data at all for this cell -- off the playable area
	}
	for _, f := range facts {
		if f.Altitude == z {
			return f.Walkable
		}
	}
	return false
}

// SurfacesAt returns every resolved surface at (x,y) (for callers that
// need more than a single altitude's walkability, e.g. picking a landing
// altitude for a jump/push).
func (m *Map) SurfacesAt(x, y int32) []MapCellFact {
	return m.cells[[2]int32{x, y}]
}

// HasCell reports whether (x,y) has any map data at all (distinct from
// "has map data but nothing walkable there").
func (m *Map) HasCell(x, y int32) bool {
	_, ok := m.cells[[2]int32{x, y}]
	return ok
}

// BlocksLineOfSight reports whether (x,y) contains a solid obstacle for
// line-of-sight purposes: a resolved surface with real height that isn't
// itself walkable (e.g. a wall), at any altitude.
//
// NOTE: combat's actual cast-validation LOS check (Fight.hasLineOfSight,
// internal/combat/line_of_sight.go) no longer uses this method -- it now
// calls LineOfSightValidAt/LineOfSightEndValidAt, a bit-exact port of the
// reference's real per-direction algorithm (see those methods' doc
// comments). This coarser method is kept only for the studio map viewer's
// simplified visualization (cmd/studio/maps.go), where a single
// direction-agnostic "is this cell solid" flag is what a 2D top-down
// viewer actually wants to render.
//
// This IS still a deliberate SIMPLIFICATION relative to the reference's
// real line-of-sight algorithm (WorldCell.isLineOfSightValid(), confirmed
// via the decompiled source): the real algorithm checks 6 separate per-
// direction LineOfSight1/3/5/7/Top/Bottom flags against the exact travel
// direction at each sub-cell boundary crossing of a full 3D DDA line
// traversal (LineOfSightUtils.check()/getCellsInputs()) -- reproduced
// bit-exact by LineOfSightValidAt/generateLOSCellInputs; reproducing it
// here too wasn't warranted since doing so without real test vectors (the
// same problem noted for
// map-format reverse-engineering in docs/04-game-data-format.md §4.9.1)
// risks silently-wrong LOS blocking being worse than a conservative,
// clearly-documented approximation. "Solid, non-walkable, has height" is
// a reasonable stand-in for "this cell contains a wall/obstacle" for
// the common case (walls, closed doors) without needing the full
// direction-flag machinery. See docs/08-java-parity-roadmap.md Phase L.
func (m *Map) BlocksLineOfSight(x, y int32) bool {
	for _, s := range m.SurfacesAt(x, y) {
		if !s.Walkable && s.Height > 0 {
			return true
		}
	}
	return false
}

// StandingAltitudeAt returns the highest "standing altitude" among walkable
// surfaces at (x,y) -- i.e. the top-of-block surface a mobile visibly
// stands ON, computed as a surface's base Altitude + its Height. This is
// the altitude convention the client uses everywhere it renders/places/
// moves a mobile (a mobile is drawn at `altitude * elevationUnit` px and
// must be given base+height to sit on the block top, matching
// coordinates.z = base+height in the client's own placement/movement
// messages; see docs/08-java-parity-roadmap.md §8.17). Returns
// (fallbackZ, false) if the cell has no walkable surface at all.
//
// Note: for the flat walkable ground tiles that actually receive a mobile,
// Height == 0, so this collapses to the surface's base Altitude -- the
// same value the reference client's isWalkable(z) matches exactly. It only
// differs (adds Height) for the raised-platform surfaces this map format
// occasionally uses as a standable top (e.g. a walkable base=-11 height=7
// tile whose true standing surface is at -4).
func (m *Map) StandingAltitudeAt(x, y int32) (z int16, found bool) {
	for _, s := range m.SurfacesAt(x, y) {
		if !s.Walkable {
			continue
		}
		standing := s.Altitude + int16(s.Height)
		if !found || standing > z {
			z, found = standing, true
		}
	}
	return z, found
}

// TopSurfaceAltitudeAt returns the BASE altitude of the TOPMOST surface at a
// cell (walkable or not), plus whether any surface exists. This is a
// RENDERING-only helper that matches how the client draws a cell's top tile
// sprite: each gfx element is drawn at its element `Altitude` (base), with
// the block's `Height` baked into the sprite ART itself (a tall block sprite
// already extends upward visually) -- NOT at altitude+height. So to lift an
// overlay diamond onto the visible top-tile it must use the base altitude of
// the topmost surface, matching the sprite's screen lift. (Contrast
// StandingAltitudeAt, which returns altitude+height = where a fighter's feet
// stand, used by combat, and which floats overlays a block's height too high
// when used for rendering.) The topmost surface is the one with the greatest
// base+height (visual top); we return its BASE.
func (m *Map) TopSurfaceAltitudeAt(x, y int32) (z int16, found bool) {
	bestTop := int16(0)
	for _, s := range m.SurfacesAt(x, y) {
		top := s.Altitude + int16(s.Height)
		if !found || top > bestTop {
			bestTop, z, found = top, s.Altitude, true
		}
	}
	return z, found
}

// TopFaceAltitudeAt returns the cell's VISUAL TOP-FACE altitude: the greatest
// surface base+height over all surfaces (as a float, since sloped tiles have
// half-unit heights). This is where the client anchors a cell's highlight /
// start-point diamond (DisplayedElement.getScreenTopY uses
// screenY + (altitude+height)*elevationUnit), so overlay diamonds and markers
// must sit here to line up with the top of a raised/blocked tile rather than
// its base. Returns (0,false) for a cell with no surface.
func (m *Map) TopFaceAltitudeAt(x, y int32) (top float64, found bool) {
	for _, s := range m.SurfacesAt(x, y) {
		t := float64(s.Altitude) + float64(s.Height)
		if !found || t > top {
			top, found = t, true
		}
	}
	return top, found
}

// CellCoords is one populated map cell's world (X,Y). Returned by Cells for
// callers (e.g. the studio map viewer) that need to enumerate the whole map
// rather than probe known coordinates.
type CellCoords struct {
	X int32
	Y int32
}

// Cells returns the world coordinates of every cell that has any resolved
// surface data, in ascending (Y, then X) order. This is a read-only
// enumeration helper for tooling/rendering; the combat engine itself only
// ever probes specific coordinates via SurfacesAt/IsWalkable.
func (m *Map) Cells() []CellCoords {
	out := make([]CellCoords, 0, len(m.cells))
	for k := range m.cells {
		out = append(out, CellCoords{X: k[0], Y: k[1]})
	}
	sort.Slice(out, func(i, j int) bool {
		if out[i].Y != out[j].Y {
			return out[i].Y < out[j].Y
		}
		return out[i].X < out[j].X
	})
	return out
}

// Bounds returns the inclusive min/max X and Y over every populated cell,
// plus whether the map has any cells at all. Useful for sizing a render
// viewport.
func (m *Map) Bounds() (minX, minY, maxX, maxY int32, ok bool) {
	first := true
	for k := range m.cells {
		x, y := k[0], k[1]
		if first {
			minX, maxX, minY, maxY = x, x, y, y
			first = false
			continue
		}
		if x < minX {
			minX = x
		}
		if x > maxX {
			maxX = x
		}
		if y < minY {
			minY = y
		}
		if y > maxY {
			maxY = y
		}
	}
	return minX, minY, maxX, maxY, !first
}

// CoachStartCells returns every FightStartCoachPointElement cell found in
// this map, keyed by the element's raw team-side param byte.
func (m *Map) CoachStartCells() map[byte][][2]int32 {
	return m.coachStartCells
}

// FightStartCells returns every FightStartPointElement cell (the broader
// team spawn-area, as opposed to the specific per-coach placement cell),
// keyed by the element's raw team-side param byte.
func (m *Map) FightStartCells() map[byte][][2]int32 {
	return m.fightStartCells
}

// SpecialCells returns this map's special battlefield cells, derived from
// the map's own baked negative-gfx Bonus tiles at load time (see
// deriveSpecialCells). The slice is sorted (Y then X) and safe to read
// concurrently (never mutated after load). Empty for maps with no special
// tiles painted in their art.
func (m *Map) SpecialCells() []SpecialCellPlacement {
	return m.specialCells
}

// IsFightMap reports whether this map is a usable 1v1/2v2 fight map: it must
// carry FightStartPointElement (spawn-area) cells for BOTH team sides (raw
// team-side param bytes 0 and 1), so both teams have somewhere to stand.
// Empty edge-chunk-only maps and non-combat maps return false. This is the
// discriminator used by MapStore.FightMapIDs to build the random-selection
// pool; see docs/08-java-parity-roadmap.md §8.11 item 13 follow-up.
func (m *Map) IsFightMap() bool {
	return len(m.fightStartCells[0]) > 0 && len(m.fightStartCells[1]) > 0
}

// maxNearbyWalkableSearchRadius bounds NearbyWalkableCells's outward
// ring-scan, matching the same "must never hang or scan a whole map"
// defensiveness as pathfind.go's maxIterations bound.
const maxNearbyWalkableSearchRadius = 32

// NearbyWalkableCells finds up to count DISTINCT walkable cells at or near
// anchor, used as a fallback fighter-placement pool when a map lacks real
// FightStartPointElement data (or has fewer real cells than fighters need)
// -- see docs/08-java-parity-roadmap.md's write-up on the fighter-
// placement bug fix. Scans outward from anchor in expanding
// Chebyshev-distance rings (radius 0, 1, 2, ...), collecting every
// walkable cell found at each ring before moving to the next, until
// either count cells have been collected or maxNearbyWalkableSearchRadius
// is reached. Cells are then shuffled (via rng, so callers get variety
// without needing their own search logic) and returned in that shuffled
// order -- callers wanting a random SUBSET should simply take the first N
// of the result themselves.
//
// This is a pure proximity search (grid Chebyshev distance), not a
// pathfinding/connectivity search -- appropriate for its purpose (finding
// candidate battlefield-adjacent placement cells near a known-good anchor
// like the coach's own pedestal), not for general navigation.
func (m *Map) NearbyWalkableCells(anchor [2]int32, count int, rng *rand.Rand) [][2]int32 {
	if count <= 0 {
		return nil
	}
	var found [][2]int32
	seen := map[[2]int32]bool{}

	collect := func(x, y int32) {
		key := [2]int32{x, y}
		if seen[key] {
			return
		}
		seen[key] = true
		for _, s := range m.SurfacesAt(x, y) {
			if s.Walkable {
				found = append(found, key)
				return
			}
		}
	}

	for radius := 0; radius <= maxNearbyWalkableSearchRadius && len(found) < count; radius++ {
		if radius == 0 {
			collect(anchor[0], anchor[1])
			continue
		}
		// Walk the perimeter of the [-radius, radius] square ring, top
		// and bottom rows plus left/right columns (excluding corners
		// already covered by the rows).
		for dx := -radius; dx <= radius; dx++ {
			collect(anchor[0]+int32(dx), anchor[1]-int32(radius))
			collect(anchor[0]+int32(dx), anchor[1]+int32(radius))
		}
		for dy := -radius + 1; dy <= radius-1; dy++ {
			collect(anchor[0]-int32(radius), anchor[1]+int32(dy))
			collect(anchor[0]+int32(radius), anchor[1]+int32(dy))
		}
	}

	if rng != nil {
		rng.Shuffle(len(found), func(i, j int) { found[i], found[j] = found[j], found[i] })
	}
	if len(found) > count {
		found = found[:count]
	}
	return found
}

// MapStore lazily loads and caches Map instances by mapID, backed by a
// shared elements.ade catalog (loaded once, since every map references the
// same element-definition registry).
type MapStore struct {
	dataDir string

	elementsOnce sync.Once
	elements     map[int32]parser.ElementDef
	elementsErr  error

	mu   sync.Mutex
	maps map[int]*Map
	errs map[int]error

	// fightMapIDs caches the discovered set of usable fight-map IDs
	// (Map.IsFightMap), computed lazily by FightMapIDs. nil until first
	// computed.
	fightMapIDs []int
}

// NewMapStore builds a MapStore rooted at dataDir (the directory
// containing elements.ade and maps/<mapID>/map_<x>_<y>.amw).
func NewMapStore(dataDir string) *MapStore {
	return &MapStore{
		dataDir: dataDir,
		maps:    make(map[int]*Map),
		errs:    make(map[int]error),
	}
}

func (s *MapStore) ensureElementsLoaded() {
	s.elementsOnce.Do(func() {
		raw, err := os.ReadFile(filepath.Join(s.dataDir, "elements.ade"))
		if err != nil {
			s.elementsErr = fmt.Errorf("gamedata: read elements.ade: %w", err)
			return
		}
		_, body, err := parser.PeekAleaHeader(raw)
		if err != nil {
			s.elementsErr = fmt.Errorf("gamedata: elements.ade header: %w", err)
			return
		}
		ef, err := parser.ParseElementsFile(body)
		if err != nil {
			s.elementsErr = fmt.Errorf("gamedata: parse elements.ade: %w", err)
			return
		}
		s.elements = ef.Elements
	})
}

// Get lazily loads (or returns the cached) Map for mapID, parsing every
// maps/<mapID>/map_*.amw chunk file found in that directory.
func (s *MapStore) Get(mapID int) (*Map, error) {
	s.ensureElementsLoaded()
	if s.elementsErr != nil {
		return nil, s.elementsErr
	}

	s.mu.Lock()
	defer s.mu.Unlock()
	if m, ok := s.maps[mapID]; ok {
		return m, nil
	}
	if err, ok := s.errs[mapID]; ok {
		return nil, err
	}

	m, err := s.loadMap(mapID)
	if err != nil {
		s.errs[mapID] = err
		return nil, err
	}
	s.maps[mapID] = m
	return m, nil
}

// FightMapIDs returns the sorted list of map IDs under maps/ that are usable
// fight maps (Map.IsFightMap, i.e. have spawn-area cells for both team
// sides). Result is computed once and cached. A directory that fails to
// load, or that isn't a fight map, is skipped (logged by the caller if it
// cares). Used to build the random fight-map selection pool -- see
// docs/08-java-parity-roadmap.md §8.11 item 13 follow-up and Part A of the
// map-randomization work.
func (s *MapStore) FightMapIDs() ([]int, error) {
	s.ensureElementsLoaded()
	if s.elementsErr != nil {
		return nil, s.elementsErr
	}

	s.mu.Lock()
	defer s.mu.Unlock()
	if s.fightMapIDs != nil {
		return append([]int(nil), s.fightMapIDs...), nil
	}

	mapsDir := filepath.Join(s.dataDir, "maps")
	entries, err := os.ReadDir(mapsDir)
	if err != nil {
		return nil, fmt.Errorf("gamedata: read maps dir %s: %w", mapsDir, err)
	}

	var ids []int
	for _, entry := range entries {
		if !entry.IsDir() {
			continue
		}
		id, convErr := strconv.Atoi(entry.Name())
		if convErr != nil {
			continue // non-numeric dir name, not a map
		}
		// Reuse the per-map cache when already loaded; otherwise load
		// (and cache) it here so a subsequent Get is free.
		m, ok := s.maps[id]
		if !ok {
			if _, seenErr := s.errs[id]; seenErr {
				continue
			}
			loaded, loadErr := s.loadMap(id)
			if loadErr != nil {
				s.errs[id] = loadErr
				continue
			}
			s.maps[id] = loaded
			m = loaded
		}
		if m.IsFightMap() {
			ids = append(ids, id)
		}
	}

	sort.Ints(ids)
	s.fightMapIDs = ids
	return append([]int(nil), ids...), nil
}

func (s *MapStore) loadMap(mapID int) (*Map, error) {
	dir := filepath.Join(s.dataDir, "maps", strconv.Itoa(mapID))
	entries, err := os.ReadDir(dir)
	if err != nil {
		return nil, fmt.Errorf("gamedata: read map dir %s: %w", dir, err)
	}

	m := &Map{
		ID:              mapID,
		cells:           make(map[[2]int32][]MapCellFact),
		coachStartCells: make(map[byte][][2]int32),
		fightStartCells: make(map[byte][][2]int32),
	}

	for _, entry := range entries {
		name := entry.Name()
		if entry.IsDir() || !strings.HasSuffix(name, ".amw") {
			continue
		}
		raw, err := os.ReadFile(filepath.Join(dir, name))
		if err != nil {
			return nil, fmt.Errorf("gamedata: read %s: %w", name, err)
		}
		_, body, err := parser.PeekAleaHeader(raw)
		if err != nil {
			return nil, fmt.Errorf("gamedata: %s header: %w", name, err)
		}
		chunk, err := parser.ParseAMWFile(body)
		if err != nil {
			return nil, fmt.Errorf("gamedata: parse %s: %w", name, err)
		}

		for _, cell := range chunk.Cells {
			key := [2]int32{cell.X, cell.Y}
			m.cells[key] = append(m.cells[key], parser.ResolveCellSurfaces(cell, s.elements)...)

			for _, lvl := range cell.Levels {
				for _, el := range lvl.Elements {
					switch el.ElementID {
					case int32(parser.ElementKindFightStartCoachPoint):
						if b, ok := el.ParamAsByte(0); ok {
							m.coachStartCells[b] = append(m.coachStartCells[b], key)
						}
					case int32(parser.ElementKindFightStartPoint):
						if b, ok := el.ParamAsByte(0); ok {
							m.fightStartCells[b] = append(m.fightStartCells[b], key)
						}
					}
				}
			}

			// Derive special battlefield cells from this cell's baked art:
			// the original game paints each special tile as a negative-gfx
			// Bonus element (gfx -1002..-1009). See deriveSpecialCells.
			m.deriveSpecialCells(cell, s.elements)
		}
	}

	// Stable ordering (Y then X) so the derived layout is deterministic
	// across loads/chunks.
	sort.Slice(m.specialCells, func(i, j int) bool {
		if m.specialCells[i].Y != m.specialCells[j].Y {
			return m.specialCells[i].Y < m.specialCells[j].Y
		}
		return m.specialCells[i].X < m.specialCells[j].X
	})

	return m, nil
}

// deriveSpecialCells scans one cell's resolved gfx sprites for negative-gfx
// Bonus markers (the special-tile art baked into the map: gfx -1002..-1009)
// and records a SpecialCellPlacement for each recognised one. The special
// cell's client render-template id (CellBaseID) is the marker's absolute gfx
// value, which is exactly the matching staticEffects.dat SPECIAL area id
// (1002-1009) the client's StaticEffectAreaManager looks up. A cell can carry
// at most one special tile in the real data; if several markers somehow
// stack, the highest-drawn (last) one wins, matching what a player sees.
func (m *Map) deriveSpecialCells(cell parser.AMWCell, elements map[int32]parser.ElementDef) {
	var (
		found   bool
		best    SpecialCellPlacement
		bestOrd float32
	)
	for _, g := range parser.ResolveCellGfx(cell, elements) {
		if g.GfxID >= 0 {
			continue
		}
		baseID := int64(-g.GfxID)
		typ, ok := specialCellTypeByBaseID(baseID)
		if !ok {
			continue
		}
		if !found || g.AltitudeOrder >= bestOrd {
			found, bestOrd = true, g.AltitudeOrder
			best = SpecialCellPlacement{X: cell.X, Y: cell.Y, Type: typ, CellBaseID: baseID}
		}
	}
	if found {
		m.specialCells = append(m.specialCells, best)
	}
}
