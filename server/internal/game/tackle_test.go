package game

import (
	"math/rand"
	"testing"
)

// TestTackleAdjacencyAndTruncate verifies the zone-of-control adjacency test and
// stop-on-contact path truncation (both deterministic, no roll).
func TestTackleAdjacencyAndTruncate(t *testing.T) {
	mover := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	enemy := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{mover}},
		{ID: 1, Fighters: []*FightFighter{enemy}},
	}}

	if len(f.adjacentOpponents(mover)) != 0 {
		t.Error("mover at (5,15) should have no adjacent enemy (enemy at (8,15))")
	}
	// Walking toward the enemy stops at (7,15) — the first cell adjacent to it.
	got := f.truncatePathOnEnemyContact(mover, []Pos{{X: 6, Y: 15}, {X: 7, Y: 15}, {X: 7, Y: 14}})
	if len(got) != 2 || got[len(got)-1] != (Pos{X: 7, Y: 15}) {
		t.Errorf("path should stop on contact at (7,15); got %v", got)
	}
	// A path that never touches the enemy's ZoC is unchanged.
	clear := []Pos{{X: 4, Y: 15}, {X: 3, Y: 15}}
	if len(f.truncatePathOnEnemyContact(mover, clear)) != 2 {
		t.Error("a path clear of enemies should not be truncated")
	}
	// Once adjacent, the mover is in the tackle area.
	mover.Pos = Pos{X: 7, Y: 15}
	if len(f.adjacentOpponents(mover)) != 1 {
		t.Error("mover at (7,15) should be adjacent to the enemy at (8,15)")
	}
}

// TestTackleEvasion verifies the evasion roll against the REAL characteristics:
// a mover's dodge (Lr.bre) minus each holder's block (Lr.brd). Breed defaults are
// dodge 100 for everyone and block 60 Feca / 40 Iop / 20 Sram / 0 for the rest.
func TestTackleEvasion(t *testing.T) {
	// A default fighter: dodge 100, no block (a Cra/Xelor/... profile).
	mover := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15},
		HP: 70, MaxHP: 70, Dodge: 100}
	f := &Fight{Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{mover}}, {ID: 1}}}
	f.rng = rand.New(rand.NewSource(42))

	// No adjacent enemy -> always evades.
	far := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 2, Y: 2}, HP: 70, MaxHP: 70}
	f.Teams[1].Fighters = []*FightFighter{far}
	if !f.attemptEvadeTackle(mover) {
		t.Error("no adjacent enemy should allow free movement")
	}

	// The client's guarantee: 100% dodge ALWAYS escapes a 0%-block holder, however
	// many of them there are. (This is why the old "4+ enemies = impossible" cap
	// was dropped — it contradicted this.)
	f.Teams[1].Fighters = []*FightFighter{
		{WireID: 3, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70},
		{WireID: 4, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 70, MaxHP: 70},
		{WireID: 5, TeamID: 1, Pos: Pos{X: 7, Y: 14}, HP: 70, MaxHP: 70},
		{WireID: 6, TeamID: 1, Pos: Pos{X: 7, Y: 16}, HP: 70, MaxHP: 70},
	}
	for i := 0; i < 200; i++ {
		if !f.attemptEvadeTackle(mover) {
			t.Fatal("100% dodge vs 0% block must always escape, even surrounded")
		}
	}

	// A single Feca (block 60): escape ~40%.
	f.Teams[1].Fighters = []*FightFighter{
		{WireID: 3, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70, Block: 60},
	}
	f.rng = rand.New(rand.NewSource(7))
	evaded := 0
	for i := 0; i < 1000; i++ {
		if f.attemptEvadeTackle(mover) {
			evaded++
		}
	}
	if evaded < 340 || evaded > 460 {
		t.Errorf("escape rate vs block 60 = %d/1000, want ~400", evaded)
	}

	// Two Fecas: independent rolls, so ~0.4 * 0.4 = 16%.
	f.Teams[1].Fighters = append(f.Teams[1].Fighters,
		&FightFighter{WireID: 4, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 70, MaxHP: 70, Block: 60})
	evaded = 0
	for i := 0; i < 1000; i++ {
		if f.attemptEvadeTackle(mover) {
			evaded++
		}
	}
	if evaded < 110 || evaded > 210 {
		t.Errorf("escape rate vs two block-60 holders = %d/1000, want ~160", evaded)
	}
}

// TestTackleEvasionChance pins the formula and its bounds.
func TestTackleEvasionChance(t *testing.T) {
	mk := func(dodge, block int32) *FightFighter {
		return &FightFighter{Dodge: dodge, Block: block}
	}
	cases := []struct {
		dodge, block, want int32
		why                string
	}{
		{100, 0, 100, "the client's guarantee: full dodge vs no block"},
		{100, 60, 40, "vs a Feca"},
		{100, 40, 60, "vs an Iop/Pandawa"},
		{100, 20, 80, "vs a Sram/Sacrier"},
		{100, 100, 0, "block matching dodge pins you"},
		{100, 150, 0, "over-block clamps at 0, never negative"},
		{200, 60, 100, "buffed dodge clamps at 100"},
		{0, 0, 0, "a fighter with no dodge stat cannot slip away"},
	}
	for _, c := range cases {
		if got := tackleEvasionChance(mk(c.dodge, 0), mk(0, c.block)); got != c.want {
			t.Errorf("dodge %d vs block %d = %d, want %d (%s)", c.dodge, c.block, got, c.want, c.why)
		}
	}
	if got := tackleEvasionChance(nil, mk(0, 0)); got != 0 {
		t.Errorf("nil mover = %d, want 0", got)
	}
}
