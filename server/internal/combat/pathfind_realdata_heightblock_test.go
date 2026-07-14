package combat

import (
	"os"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file finally closes the roadmap Phase K item left explicitly
// "not done, deliberately deferred" (docs/08-java-parity-roadmap.md, Phase K):
// "re-validate A*'s against a real map with actual height-blocked steps... no
// dedicated test exercises a genuinely height-blocked... path against the
// real fight map's actual geometry."
//
// The existing pathfind_realdata_test.go integration test only proves the
// Fight -> gamedata.Map wiring works for an ARBITRARY walkable route; it
// does not guarantee the discovered path ever crosses a real height
// obstacle (fightMapID=2, the canonical/default fight map, has ZERO
// adjacent walkable-cell pairs whose standing-altitude delta exceeds the
// ±4 ascend/descend limit -- confirmed by an exhaustive scan of its 972
// populated cells). Duels are NOT always played on map 2 though:
// selectFightMapID (internal/dispatch/handlers_fight.go) picks a RANDOM map
// from MapStore.FightMapIDs() per duel, and that scan found genuine
// height-blocked steps on several other real fight maps in the pool (e.g.
// maps 4, 5, 6, 7, 8, 9, 12, 13). This test pins one such real, concrete
// case (map 4) so a future map-data or parser change that silently
// eliminates or shifts the obstacle fails loudly instead of leaving this
// scenario untested forever.
func TestFindPath_RealMapRejectsGenuineHeightBlockedStep(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/elements.ade"); err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}
	store := gamedata.NewMapStore(dataDir)
	m, err := store.Get(4)
	if err != nil {
		t.Fatalf("MapStore.Get(4): %v", err)
	}
	f, _, _ := newTestFightForEffects(t)
	f.SetMapData(m)

	// Known real cliff on fightMapID=4: (9,0) stands at z=5, its single-axis
	// neighbor (9,1) stands at z=-1 -- a 6-level drop, exceeding
	// moverMaxDescend (4). Both cells are individually walkable; only the
	// STEP between them is illegal.
	from := Point3{X: 9, Y: 0, Z: 5}
	to := Point3{X: 9, Y: 1}

	// Guard the fixture itself: if map data ever changes, fail loudly here
	// rather than silently passing a now-meaningless test.
	if !f.IsWalkable(from) {
		t.Fatalf("fixture stale: (9,0) is no longer walkable on map 4")
	}
	if !f.IsWalkable(to) {
		t.Fatalf("fixture stale: (9,1) is no longer walkable on map 4")
	}
	altitude, blocked := f.ArrivalAltitude(nil, from.Z, to)
	if blocked {
		t.Fatalf("fixture stale: (9,1) no longer resolves to a walkable surface on map 4")
	}
	delta := int(altitude) - int(from.Z)
	if -delta <= maxDescend {
		t.Fatalf("fixture stale: (9,0)->(9,1) delta=%d no longer exceeds moverMaxDescend=%d; pick a new known-blocked real-map pair", delta, maxDescend)
	}

	// 1. Server authority: ValidateClientPath must reject a client
	// submitting this single step directly, even though both endpoint
	// cells are individually walkable -- this is the actual anti-cheat
	// check that stops a modified client from teleporting down the cliff.
	if got := ValidateClientPath(nil, from, []Point3{to}, f); got != nil {
		t.Errorf("ValidateClientPath allowed a genuine real-map height-violating step: %v", got)
	}

	// 2. FindPath must never emit this illegal step as part of any route it
	// finds between the two cells -- whether it finds a legal detour around
	// the cliff or correctly reports no path exists.
	path := FindPath(nil, from, to, f)
	prev := from
	for _, p := range path {
		d := int(p.Z) - int(prev.Z)
		if d > moverMaxAscend(nil) || -d > moverMaxDescend(nil) {
			t.Errorf("FindPath emitted an illegal real-map height step %v -> %v (delta=%d)", prev, p, d)
		}
		if !f.IsWalkable(p) {
			t.Errorf("FindPath emitted a step onto a non-walkable real-map cell %v", p)
		}
		prev = p
	}
	// path == nil (no legal detour exists around the cliff) is also a
	// correct, acceptable outcome -- the invariant under test is "never an
	// illegal height step", not "a detour must exist".
}
