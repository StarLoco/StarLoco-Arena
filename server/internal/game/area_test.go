package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func TestPointInArea(t *testing.T) {
	src := Pos{X: 5, Y: 5}
	center := Pos{X: 8, Y: 5}

	// Circle radius 2 = Manhattan diamond (|dx|+|dy| ≤ 2).
	circ := func(p Pos) bool { return pointInArea(areaShapeCircle, []int32{2}, src, center, p) }
	if !circ(center) || !circ(Pos{X: 8, Y: 7}) || !circ(Pos{X: 10, Y: 5}) {
		t.Error("circle should include center, (8,7) dist2, (10,5) dist2")
	}
	if circ(Pos{X: 8, Y: 8}) || circ(Pos{X: 10, Y: 6}) {
		t.Error("circle r2 should exclude (8,8) dist3 and (10,6) dist3")
	}
	// Diamond, not Euclidean disk: at r3 the corner (2,2) (Manhattan 4) is OUT,
	// while a Euclidean disk (√8 < 3) would include it. This is the divergence.
	c3 := func(p Pos) bool { return pointInArea(areaShapeCircle, []int32{3}, src, center, p) }
	if c3(Pos{X: 10, Y: 7}) {
		t.Error("diamond r3 must EXCLUDE the (2,2) corner (Euclidean would include it)")
	}
	if !c3(Pos{X: 11, Y: 5}) {
		t.Error("diamond r3 should include the (3,0) tip")
	}

	// Cross size 1 (same row/col arms).
	cross := func(p Pos) bool { return pointInArea(areaShapeCross, []int32{1}, src, center, p) }
	if !cross(Pos{X: 9, Y: 5}) || !cross(Pos{X: 8, Y: 6}) {
		t.Error("cross should include the row/col arm cells")
	}
	if cross(Pos{X: 9, Y: 6}) {
		t.Error("cross should exclude the diagonal (9,6)")
	}

	// Ring [1,2]: excludes the center, includes dist 1 and 2, excludes dist 3.
	ring := func(p Pos) bool { return pointInArea(areaShapeRing, []int32{1, 2}, src, center, p) }
	if ring(center) || !ring(Pos{X: 9, Y: 5}) || !ring(Pos{X: 10, Y: 5}) || ring(Pos{X: 11, Y: 5}) {
		t.Error("ring[1,2] should be the annulus dist 1..2, excluding center and dist3")
	}

	// Inverted-T (9), cast from (5,5) toward (8,5): stem +X len 2, bar (half 1)
	// THROUGH the center. size = [barHalf=1, stemLen=2].
	tinv := func(p Pos) bool { return pointInArea(areaShapeTInv, []int32{1, 2}, src, center, p) }
	if !tinv(center) || !tinv(Pos{X: 8, Y: 6}) || !tinv(Pos{X: 8, Y: 4}) {
		t.Error("inverted-T bar (through center, ±1 perpendicular) missing cells")
	}
	if !tinv(Pos{X: 9, Y: 5}) || !tinv(Pos{X: 10, Y: 5}) || tinv(Pos{X: 11, Y: 5}) {
		t.Error("inverted-T stem should reach 2 cells toward the target, not 3")
	}
	if tinv(Pos{X: 7, Y: 5}) || tinv(Pos{X: 8, Y: 7}) {
		t.Error("inverted-T should exclude behind-center and beyond-bar cells")
	}

	// Point: only the center.
	if !pointInArea(areaShapePoint, nil, src, center, center) || pointInArea(areaShapePoint, nil, src, center, Pos{X: 9, Y: 5}) {
		t.Error("point should be center-only")
	}
}

func TestAreaFighters(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 5}, HP: 70, MaxHP: 70}
	a := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 5}, HP: 60, MaxHP: 60} // center
	b := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 8, Y: 6}, HP: 60, MaxHP: 60} // dist 1
	c := &FightFighter{WireID: 4, TeamID: 0, Pos: Pos{X: 8, Y: 9}, HP: 60, MaxHP: 60} // dist 4 (outside r2)
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster, c}},
		{ID: 1, Fighters: []*FightFighter{a, b}},
	}}

	// Point / no-area -> only the fighter at the target cell.
	if got := f.areaFighters(caster, gamedata.Effect{AreaShape: 1}, Pos{X: 8, Y: 5}); len(got) != 1 || got[0] != a {
		t.Errorf("point area = %v, want [a]", got)
	}
	// A circle with no size falls back to point.
	if got := f.areaFighters(caster, gamedata.Effect{AreaShape: 2}, Pos{X: 8, Y: 5}); len(got) != 1 {
		t.Errorf("sizeless circle = %d, want 1 (point fallback)", len(got))
	}
	// Circle r2 -> a + b (not c).
	if got := f.areaFighters(caster, gamedata.Effect{AreaShape: 2, AreaSize: []int32{2}}, Pos{X: 8, Y: 5}); len(got) != 2 {
		t.Errorf("circle r2 hit %d fighters, want 2 (a,b)", len(got))
	}
	// Empty (all) -> every living fighter (4).
	if got := f.areaFighters(caster, gamedata.Effect{AreaShape: 32767}, Pos{X: 8, Y: 5}); len(got) != 4 {
		t.Errorf("empty area hit %d, want 4", len(got))
	}
	// A dead fighter drops out of the zone.
	b.HP = 0
	if got := f.areaFighters(caster, gamedata.Effect{AreaShape: 2, AreaSize: []int32{2}}, Pos{X: 8, Y: 5}); len(got) != 1 {
		t.Errorf("circle r2 with b dead = %d, want 1 (a)", len(got))
	}
}

// TestAreaTargetConditions: an "all" (32767) area is filtered by the effect's
// target conditions — an IS_CASTER self-buff lands only on the caster, an
// IS_ENEMY area only on the other team, and a zero/empty mask hits everyone.
func TestAreaTargetConditions(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 5}, HP: 70, MaxHP: 70}
	ally := &FightFighter{WireID: 2, TeamID: 0, Pos: Pos{X: 6, Y: 5}, HP: 70, MaxHP: 70}
	enemy1 := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 8, Y: 5}, HP: 70, MaxHP: 70}
	enemy2 := &FightFighter{WireID: 4, TeamID: 1, Pos: Pos{X: 9, Y: 5}, HP: 70, MaxHP: 70}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster, ally}},
		{ID: 1, Fighters: []*FightFighter{enemy1, enemy2}},
	}}
	all := func(targets []int64) []*FightFighter {
		return f.areaFighters(caster, gamedata.Effect{AreaShape: 32767, Targets: targets}, caster.Pos)
	}

	// IS_CASTER (2) -> only the caster (spell 7's self-buff mask).
	if got := all([]int64{condIsCaster}); len(got) != 1 || got[0] != caster {
		t.Errorf("IS_CASTER area = %v, want [caster]", got)
	}
	// IS_ENEMY (8) -> both enemies, no allies.
	if got := all([]int64{condIsEnemy}); len(got) != 2 {
		t.Errorf("IS_ENEMY area hit %d, want 2 enemies", len(got))
	}
	// IS_ALLY (4) -> caster + ally.
	if got := all([]int64{condIsAlly}); len(got) != 2 {
		t.Errorf("IS_ALLY area hit %d, want 2 (caster+ally)", len(got))
	}
	// Zero condition -> everyone (spell 9's hit-all mask).
	if got := all([]int64{0}); len(got) != 4 {
		t.Errorf("zero-condition area hit %d, want 4 (all)", len(got))
	}
	// Empty conditions -> everyone (no restriction).
	if got := all(nil); len(got) != 4 {
		t.Errorf("empty-condition area hit %d, want 4 (all)", len(got))
	}
}

// TestResolveEffectAreaDamage: a circle-2 damage effect hits every fighter in the
// zone (each takes its own resolved damage) and spares those outside it.
func TestResolveEffectAreaDamage(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 5}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	a := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 5}, HP: 60, MaxHP: 60}
	b := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 8, Y: 6}, HP: 60, MaxHP: 60}
	far := &FightFighter{WireID: 4, TeamID: 1, Pos: Pos{X: 8, Y: 12}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{a, b, far}},
	}}

	// Action 132 (earth "par sort") 15 flat, circle radius 2 at (8,5).
	ef := gamedata.Effect{ActionID: 132, Params: []float32{15}, AreaShape: 2, AreaSize: []int32{2}}
	f.resolveEffect(caster, ef, Pos{X: 8, Y: 5})

	if a.HP != 45 || b.HP != 45 {
		t.Errorf("area damage: a=%d b=%d, want 45/45", a.HP, b.HP)
	}
	if far.HP != 60 {
		t.Errorf("fighter outside the zone took damage: %d, want 60", far.HP)
	}
}
