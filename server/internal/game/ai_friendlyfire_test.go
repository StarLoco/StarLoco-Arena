package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// aoeSpell builds a flat-damage spell with a circular area of the given radius.
func aoeSpell(id int32, ap int8, rMax int8, amount float32, radius int32) *gamedata.Spell {
	return &gamedata.Spell{
		ID: id, AP: ap, RangeMin: 1, RangeMax: rMax,
		Effects: []gamedata.Effect{{
			ActionID: 3, EffectID: id * 10, Params: []float32{amount},
			AreaShape: areaShapeCircle, AreaSize: []int32{radius},
		}},
	}
}

// TestAIAvoidsFriendlyFire guards a regression the spell repertoire introduced.
// The AI now picks the HARDEST-HITTING affordable spell, and 15 of the damaging
// breed spells carry an area shape — several of them the strongest their breed
// has. Area effects hit allies, enemies and the caster alike (see areaFighters),
// so without this the AI would nuke its own team to reach one enemy.
func TestAIAvoidsFriendlyFire(t *testing.T) {
	bigAoE := aoeSpell(600, 4, 6, 40, 2) // strongest, but a radius-2 blast
	smallSingle := dmgSpell(601, 2, 1, 6, 10)

	f, caster, enemy := summonTestFight() // caster (7,15), enemy (12,15)
	f.deps.Spells = gamedata.NewSpells(bigAoE, smallSingle)
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 600}, {SpellID: 601}}}
	caster.AP = 6

	// No ally near the enemy: the big AoE is the right pick.
	if got := f.chooseAISpell(caster, enemy); got != 600 {
		t.Fatalf("chooseAISpell with a clear field = %d, want 600 (strongest)", got)
	}

	// Put an ally right next to the enemy, inside the radius-2 blast. The AoE is
	// now disqualified and the weaker single-target spell must win.
	ally := &FightFighter{WireID: 30, TeamID: 0, Pos: Pos{X: 11, Y: 15}, HP: 50, MaxHP: 50}
	f.Teams[0].Fighters = append(f.Teams[0].Fighters, ally)
	if got := f.chooseAISpell(caster, enemy); got != 601 {
		t.Errorf("chooseAISpell with an ally in the blast = %d, want 601 (no friendly fire)", got)
	}

	// Move the ally clear and the AoE is preferred again.
	ally.Pos = Pos{X: 5, Y: 15}
	if got := f.chooseAISpell(caster, enemy); got != 600 {
		t.Errorf("chooseAISpell with the ally clear = %d, want 600 again", got)
	}

	// A dead ally is not friendly fire.
	ally.Pos = Pos{X: 11, Y: 15}
	ally.HP = 0
	if got := f.chooseAISpell(caster, enemy); got != 600 {
		t.Errorf("chooseAISpell with a DEAD ally in the blast = %d, want 600", got)
	}
}

// TestAIWillNotNukeItself covers the self-splash case: an "all fighters" area
// (shape 32767) catches the caster too, and one shipped Iop spell is exactly
// that.
func TestAIWillNotNukeItself(t *testing.T) {
	all := &gamedata.Spell{
		ID: 610, AP: 1, RangeMin: 0, RangeMax: 8,
		Effects: []gamedata.Effect{{
			ActionID: 3, EffectID: 6100, Params: []float32{15},
			AreaShape: areaShapeEmpty, AreaSize: []int32{1},
		}},
	}
	f, caster, enemy := summonTestFight()
	f.deps.Spells = gamedata.NewSpells(all)
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 610}}}
	caster.AP = 6

	if got := f.chooseAISpell(caster, enemy); got != 0 {
		t.Errorf("chooseAISpell = %d, want 0 (a 32767 area hits the caster's own team)", got)
	}
	// And it must not spend AP trying.
	f.castAISpellRepeatedly(caster)
	if caster.AP != 6 {
		t.Errorf("AP = %d, want 6 (nothing safe to cast)", caster.AP)
	}
}
