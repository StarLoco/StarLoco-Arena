package game

import "testing"

// TestArenaObstaclesAreNotWalkable locks the scenery model: the 22 cells decoded
// from world 5's topology tile carry a real altitude but no walkable ground, so
// they must be unwalkable AND advertised to the client as 0xFFFF (bit 7 = not a
// valid arena position, bit 8 = blocks line of sight). Emitting 0xFC00 for them
// is what let a displacement spell land a fighter on an ice spike (B-048).
func TestArenaObstaclesAreNotWalkable(t *testing.T) {
	if n := len(practiceArenaSceneryCells()); n != 22 {
		t.Errorf("obstacle count = %d, want 22", n)
	}
	for _, cell := range practiceArenaSceneryCells() {
		x, y := cell[0], cell[1]
		if practiceArena.walkable(x, y) {
			t.Errorf("obstacle (%d,%d) reports walkable", x, y)
		}
		if got := practiceArena.cellFlag(x, y); got != 0xFFFF {
			t.Errorf("obstacle (%d,%d) cellFlag = 0x%04X, want 0xFFFF", x, y, got)
		}
		if !practiceArena.blocksLineOfSight(x, y) {
			t.Errorf("obstacle (%d,%d) should block line of sight", x, y)
		}
		// An obstacle is NOT void: it has a real altitude in the topology.
		if practiceArena.at(x, y).void {
			t.Errorf("obstacle (%d,%d) is void; it should carry a real altitude", x, y)
		}
	}
	// Ordinary floor is untouched.
	if !practiceArena.walkable(7, 15) || practiceArena.cellFlag(7, 15) != 0xFC00 {
		t.Error("(7,15) should still be plain walkable floor")
	}
	// The interior scenery the player can see (tree / ice spikes).
	for _, c := range [][2]int32{{14, 2}, {14, 5}, {6, 13}, {14, 13}, {5, 15}} {
		if practiceArena.walkable(c[0], c[1]) {
			t.Errorf("interior scenery (%d,%d) must not be walkable", c[0], c[1])
		}
	}
}

// TestSpecialCellData checks world 5's 9 map-authored tiles: every one sits on a
// real walkable floor cell whose altitude matches the stored z, and every
// template id is a known SPECIAL behaviour.
func TestSpecialCellData(t *testing.T) {
	if n := len(practiceArena.specials); n != 9 {
		t.Fatalf("special cell count = %d, want 9", n)
	}
	for i, sc := range practiceArena.specials {
		if !practiceArena.walkable(sc.Pos.X, sc.Pos.Y) {
			t.Errorf("special %d at (%d,%d) is not on walkable floor", i, sc.Pos.X, sc.Pos.Y)
		}
		if alt := practiceArena.altitudeAt(sc.Pos.X, sc.Pos.Y); alt != sc.Pos.Z {
			t.Errorf("special %d z = %d, topology altitude = %d (client rejects a mismatch)", i, sc.Pos.Z, alt)
		}
		if specialCellByTemplate[sc.Template] == specialCellNone {
			t.Errorf("special %d has unknown template %d", i, sc.Template)
		}
		// The instance id the client keys its EffectArea by is 1-based.
		_, id, ok := practiceArena.specialAt(sc.Pos.X, sc.Pos.Y)
		if !ok || id != int64(i+1) {
			t.Errorf("specialAt(%d,%d) = id %d, ok %v; want id %d", sc.Pos.X, sc.Pos.Y, id, ok, i+1)
		}
	}
	// World 5 is a practice arena: no killer and no trap tiles.
	for _, sc := range practiceArena.specials {
		if k := specialCellByTemplate[sc.Template]; k == specialCellKiller || k == specialCellTrap {
			t.Errorf("world 5 should carry no killer/trap tile, found template %d", sc.Template)
		}
	}
}

// TestSpecialCellTemplateMapping pins the template -> behaviour table (v2.04b parity).
func TestSpecialCellTemplateMapping(t *testing.T) {
	want := map[int64]specialCellType{
		1002: specialCellKiller, 1003: specialCellTrap,
		1004: specialCellEagleEye, 1005: specialCellShield,
		1006: specialCellPanacea, 1007: specialCellEnthusiasm,
		1008: specialCellMotivation, 1009: specialCellHealingHeart,
	}
	for id, exp := range want {
		if got := specialCellByTemplate[id]; got != exp {
			t.Errorf("template %d = %d, want %d", id, got, exp)
		}
	}
}

// specialFight builds a minimal fight with one fighter placed on a given cell.
func specialFight(pos Pos) (*Fight, *FightFighter) {
	ff := &FightFighter{
		WireID: 1, TeamID: 0, Pos: pos,
		HP: 50, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3,
	}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{ff}},
		{ID: 1},
	}}
	f.Timeline = []*FightFighter{ff}
	return f, ff
}

// TestSpecialCellBuffsApplyAndRevert: a fighter that STARTS its turn on a buff
// tile gets the bonus, and it is removed when its turn ends.
func TestSpecialCellBuffsApplyAndRevert(t *testing.T) {
	cases := []struct {
		name  string
		cell  Pos
		check func(t *testing.T, ff *FightFighter, phase string)
	}{
		{"motivation +1 AP", Pos{X: 8, Y: 9}, func(t *testing.T, ff *FightFighter, phase string) {
			if phase == "buffed" && (ff.AP != 7 || ff.MaxAP != 7) {
				t.Errorf("motivation: AP %d/%d, want 7/7 (the ceiling must rise too, else the clamp eats it)", ff.AP, ff.MaxAP)
			}
			if phase == "reverted" && (ff.AP != 6 || ff.MaxAP != 6) {
				t.Errorf("motivation revert: AP %d/%d, want 6/6", ff.AP, ff.MaxAP)
			}
		}},
		{"eagle eye +1 range", Pos{X: 15, Y: 7}, func(t *testing.T, ff *FightFighter, phase string) {
			want := int32(0)
			if phase == "buffed" {
				want = 1
			}
			if ff.Range != want {
				t.Errorf("eagle eye (%s): Range = %d, want %d", phase, ff.Range, want)
			}
		}},
		{"shield +10% res", Pos{X: 7, Y: 9}, func(t *testing.T, ff *FightFighter, phase string) {
			want := int32(0)
			if phase == "buffed" {
				want = 10
			}
			if ff.Stats.resPctAll != want {
				t.Errorf("shield (%s): resPctAll = %d, want %d", phase, ff.Stats.resPctAll, want)
			}
		}},
		{"panacea +10% heal", Pos{X: 6, Y: 9}, func(t *testing.T, ff *FightFighter, phase string) {
			want := int32(0)
			if phase == "buffed" {
				want = 10
			}
			if ff.Stats.healPct != want {
				t.Errorf("panacea (%s): healPct = %d, want %d", phase, ff.Stats.healPct, want)
			}
		}},
	}
	for _, c := range cases {
		t.Run(c.name, func(t *testing.T) {
			f, ff := specialFight(c.cell)
			if died := f.applyTurnStartSpecialCell(ff); died {
				t.Fatal("a buff tile must not kill")
			}
			c.check(t, ff, "buffed")
			f.revertSpecialCellBuffs(ff)
			c.check(t, ff, "reverted")
			if len(f.cellBuffs) != 0 {
				t.Errorf("%d cell buffs still tracked after revert", len(f.cellBuffs))
			}
		})
	}
}

// TestSpecialCellOnlyFiresAtTurnStart: the manual is explicit that walking over
// or flying over a special cell does nothing — only STARTING a turn on one
// triggers it. An ordinary cell must therefore never grant anything.
func TestSpecialCellOnlyFiresAtTurnStart(t *testing.T) {
	f, ff := specialFight(Pos{X: 7, Y: 15}) // plain floor, no tile
	if f.applyTurnStartSpecialCell(ff) {
		t.Error("a plain cell reported a death")
	}
	if len(f.cellBuffs) != 0 || ff.AP != 6 || ff.Range != 0 {
		t.Error("a plain cell must not grant anything")
	}
	// Merely standing on / passing through is modelled by the move path, which
	// never calls the turn-start hook: no buff is tracked for the tile at (8,9).
	ff.Pos = Pos{X: 8, Y: 9}
	if len(f.cellBuffs) != 0 {
		t.Error("moving onto a special cell must not buff before the turn starts")
	}
}

// TestSpecialCellBuffRevertKeepsSpentAP: reverting a motivation tile drops the
// ceiling, but a fighter that already SPENT the bonus AP must not be charged
// twice (the current value only follows the ceiling down if it now exceeds it).
func TestSpecialCellBuffRevertKeepsSpentAP(t *testing.T) {
	f, ff := specialFight(Pos{X: 8, Y: 9}) // motivation
	f.applyTurnStartSpecialCell(ff)
	ff.AP -= 4 // spend: 7 -> 3
	f.revertSpecialCellBuffs(ff)
	if ff.MaxAP != 6 {
		t.Errorf("MaxAP = %d, want 6", ff.MaxAP)
	}
	if ff.AP != 3 {
		t.Errorf("AP = %d, want 3 (already spent; must not be double-charged)", ff.AP)
	}
}
