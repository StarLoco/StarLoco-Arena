package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func TestSpellTargetValid(t *testing.T) {
	caster := &FightFighter{WireID: 1, Pos: Pos{X: 7, Y: 15}}
	enemy := &FightFighter{WireID: 2, Pos: Pos{X: 7, Y: 11}, HP: 50} // 4 cells north (same column)
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{enemy}},
	}}
	at := func(x, y int32) Pos { return Pos{X: x, Y: y} }

	// --- range ---
	rng := &gamedata.Spell{RangeMin: 1, RangeMax: 5}
	if !f.spellTargetValid(caster, rng, at(7, 11)) { // dist 4
		t.Error("dist 4 within 1-5 should be valid")
	}
	if f.spellTargetValid(caster, rng, at(7, 4)) { // dist 11
		t.Error("dist 11 > max 5 should be invalid")
	}
	minRng := &gamedata.Spell{RangeMin: 2, RangeMax: 5}
	if f.spellTargetValid(caster, minRng, at(7, 14)) { // dist 1 < min 2
		t.Error("dist 1 < min 2 should be invalid")
	}
	self := &gamedata.Spell{RangeMin: 0, RangeMax: 0}
	if !f.spellTargetValid(caster, self, at(7, 15)) { // dist 0
		t.Error("self-cast dist 0 within 0-0 should be valid")
	}
	if f.spellTargetValid(caster, self, at(7, 14)) { // dist 1 > max 0
		t.Error("dist 1 > max 0 (self-only) should be invalid")
	}

	// --- boostable range (only for RangeMax > 1) ---
	caster.Range = 2
	if !f.spellTargetValid(caster, rng, at(7, 9)) { // dist 6 <= 5+2
		t.Error("dist 6 within boosted 1-7 should be valid")
	}
	caster.Range = 0
	if f.spellTargetValid(caster, rng, at(7, 9)) { // dist 6 > 5
		t.Error("dist 6 > max 5 without boost should be invalid")
	}
	caster.Range = 5
	melee := &gamedata.Spell{RangeMin: 1, RangeMax: 1}
	if f.spellTargetValid(caster, melee, at(9, 15)) { // dist 2; range-1 never extends
		t.Error("range-1 melee must not extend with Range stat")
	}
	if !f.spellTargetValid(caster, melee, at(7, 14)) { // dist 1
		t.Error("range-1 melee at dist 1 should be valid")
	}
	caster.Range = 0

	// --- only-line ---
	line := &gamedata.Spell{RangeMin: 1, RangeMax: 6, OnlyLine: true}
	if !f.spellTargetValid(caster, line, at(7, 11)) { // same column
		t.Error("only-line same-column should be valid")
	}
	if !f.spellTargetValid(caster, line, at(10, 15)) { // same row, dist 3
		t.Error("only-line same-row should be valid")
	}
	if f.spellTargetValid(caster, line, at(9, 13)) { // diagonal, dist 4
		t.Error("only-line off-axis diagonal should be invalid")
	}

	// --- target cell must be REAL floor (B-048) ---
	// NeedFreeCell only ever tested for FIGHTERS, so nothing stopped a cast from
	// being aimed at a void cell or a scenery obstacle. For a displacement spell
	// (the Iop's Bond) that landed the caster on top of an ice spike.
	anyCell := &gamedata.Spell{RangeMin: 1, RangeMax: 6}
	if f.spellTargetValid(caster, anyCell, at(4, 15)) { // void: row 15 starts at x=5
		t.Error("targeting a VOID cell should be invalid")
	}
	if !practiceArena.scenery(5, 15) {
		t.Fatal("test precondition: (5,15) should be a scenery obstacle")
	}
	if f.spellTargetValid(caster, anyCell, at(5, 15)) { // scenery obstacle, dist 2
		t.Error("targeting a scenery OBSTACLE should be invalid")
	}

	// --- free-cell ---
	free := &gamedata.Spell{RangeMin: 1, RangeMax: 6, NeedFreeCell: true}
	if f.spellTargetValid(caster, free, at(7, 11)) { // enemy occupies it
		t.Error("free-cell on an occupied cell should be invalid")
	}
	if !f.spellTargetValid(caster, free, at(7, 12)) { // empty, dist 3
		t.Error("free-cell on an empty in-range cell should be valid")
	}
	// A dead fighter no longer occupies its cell.
	enemy.HP = 0
	if !f.spellTargetValid(caster, free, at(7, 11)) {
		t.Error("free-cell on a DEAD fighter's cell should be valid")
	}
}
