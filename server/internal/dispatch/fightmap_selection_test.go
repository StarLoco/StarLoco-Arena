package dispatch

import (
	"testing"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/combat"
)

// TestSelectFightMapIDReturnsRealPoolMember verifies selectFightMapID only
// ever returns a map from the discovered fight-map pool (MapStore.FightMapIDs),
// exercised many times so the randomness is actually covered.
func TestSelectFightMapIDReturnsRealPoolMember(t *testing.T) {
	deps := realDataDeps(t)
	pool, err := deps.Data.Maps.FightMapIDs()
	if err != nil {
		t.Fatalf("FightMapIDs: %v", err)
	}
	if len(pool) == 0 {
		t.Fatal("no fight maps discovered")
	}
	valid := make(map[int32]bool, len(pool))
	for _, id := range pool {
		valid[int32(id)] = true
	}
	for i := 0; i < 200; i++ {
		got := selectFightMapID(deps)
		if !valid[got] {
			t.Fatalf("selectFightMapID returned %d, not in the fight-map pool %v", got, pool)
		}
	}
}

// TestSelectFightMapIDVaries confirms selection is actually RANDOM (not
// stuck on a single hardcoded map): across many draws over a 15-map pool it
// must yield at least two distinct maps. (Chance of 200 identical draws over
// >=2 maps is astronomically small, so this is not flaky.)
func TestSelectFightMapIDVaries(t *testing.T) {
	deps := realDataDeps(t)
	pool, _ := deps.Data.Maps.FightMapIDs()
	if len(pool) < 2 {
		t.Skipf("only %d fight map(s) available; can't assert variety", len(pool))
	}
	seen := map[int32]bool{}
	for i := 0; i < 200; i++ {
		seen[selectFightMapID(deps)] = true
	}
	if len(seen) < 2 {
		t.Errorf("selectFightMapID only ever returned %v over 200 draws; expected variety across a %d-map pool", seen, len(pool))
	}
}

// TestSelectFightMapIDFallsBackWithoutData verifies the safe fallback: with
// no game data, selection returns the default map rather than failing.
func TestSelectFightMapIDFallsBackWithoutData(t *testing.T) {
	if got := selectFightMapID(&Deps{Data: nil}); got != defaultFightMapID {
		t.Errorf("selectFightMapID with no data = %d, want fallback %d", got, defaultFightMapID)
	}
}

// TestResolveSpecialCellRendersFromRealMap verifies the CREATE_FIGHT special-
// cell render list is populated from a real map's DERIVED special cells (the
// baked negative-gfx Bonus tiles): each carries a real cellBaseId (a SPECIAL
// staticEffects id 1002-1009) and a resolved altitude, and the wire cell ids
// are 1-based sequential.
func TestResolveSpecialCellRendersFromRealMap(t *testing.T) {
	deps := realDataDeps(t)
	pool, _ := deps.Data.Maps.FightMapIDs()
	if len(pool) == 0 {
		t.Fatal("no fight maps")
	}
	mapID := int(pool[0])

	renders := resolveSpecialCellRenders(deps, mapID)
	if len(renders) == 0 {
		t.Fatalf("map %d resolved no special-cell renders, want the baked special tiles", mapID)
	}
	for i, r := range renders {
		if r.CellBaseID == 0 {
			t.Errorf("render %d has cellBaseId 0", i)
		}
		validBase := r.CellBaseID == 1 || (r.CellBaseID >= 1002 && r.CellBaseID <= 1009)
		if !validBase {
			t.Errorf("render %d cellBaseId = %d, want trap(1) or special(1002-1009)", i, r.CellBaseID)
		}
		if r.CellID != int64(i+1) {
			t.Errorf("render %d CellID = %d, want %d (1-based sequential)", i, r.CellID, i+1)
		}
	}
}

// TestApplySpecialCellsRegistersGameplayAndRenderCells verifies applySpecialCells
// populates BOTH the gameplay special-cell map (so tiles trigger at turn start)
// AND the render tuples (so CREATE_FIGHT can draw them) from real map data.
func TestApplySpecialCellsRegistersGameplayAndRenderCells(t *testing.T) {
	deps := realDataDeps(t)
	pool, _ := deps.Data.Maps.FightMapIDs()
	if len(pool) == 0 {
		t.Fatal("no fight maps")
	}
	m, err := deps.Data.Maps.Get(int(pool[0]))
	if err != nil {
		t.Fatalf("Get map: %v", err)
	}

	// A bare fight is enough: applySpecialCells only needs the fight's
	// SetSpecialCell/AddSpecialCellRender methods (no actor goroutine).
	fight := combat.NewFight(1, 1, combat.Clocks{}, nil, nil, deps.Data, zerolog.Nop())
	applySpecialCells(fight, m, deps)

	renders := fight.SpecialCellRenders()
	if len(renders) == 0 {
		t.Errorf("applySpecialCells registered no render cells for map %d", m.ID)
	}
}
