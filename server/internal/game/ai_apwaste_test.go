package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestAINeverEndsTurnWithUsableAP is the general form of "the AI does not waste
// its turn".
//
// The existing repertoire test pins one arithmetic case (4 + 2 = 6). This asserts
// the PROPERTY instead: after the AI has finished casting, there must be no spell
// left in its repertoire that is both affordable and castable at its position.
// Whatever the costs happen to be, leftover AP that could still have bought a
// cast is wasted damage.
//
// Written to be able to fail: several odd-cost combinations, including ones where
// the greedy "biggest damage first" choice leaves an awkward remainder.
func TestAINeverEndsTurnWithUsableAP(t *testing.T) {
	for _, tc := range []struct {
		name  string
		ap    int32
		costs []int32 // (cost, damage) pairs are derived below
	}{
		{"exact fit 4+2", 6, []int32{4, 2}},
		{"remainder after the big one", 7, []int32{4, 3, 2}},
		{"only cheap spells", 5, []int32{1, 2}},
		{"awkward remainder", 5, []int32{3, 2}},
		{"greedy leaves a usable gap", 6, []int32{5, 1}},
	} {
		t.Run(tc.name, func(t *testing.T) {
			var spells []*gamedata.Spell
			var known []domain.FighterSpell
			for i, c := range tc.costs {
				id := int32(400 + i)
				// Bigger cost => bigger damage, so the greedy chooser prefers the
				// expensive one first and has to cope with what is left.
				spells = append(spells, dmgSpell(id, int8(c), 1, 8, float32(c)*10))
				known = append(known, domain.FighterSpell{SpellID: id})
			}
			f, caster, enemy := summonTestFight()
			f.deps.Spells = gamedata.NewSpells(spells...)
			f.deps.Fights = NewFightManager()
			f.deps.Log = testLogger()
			caster.Fighter = &domain.Fighter{Spells: known}
			caster.AP, caster.MaxAP = tc.ap, tc.ap
			enemy.HP, enemy.MaxHP = 9000, 9000 // never dies, so the turn is not cut short

			// Fixture check: at least one spell must be castable to begin with, or
			// the assertion below is vacuously satisfied.
			if f.chooseAISpell(caster, enemy) == 0 {
				t.Fatalf("fixture: nothing castable at AP %d with costs %v", tc.ap, tc.costs)
			}

			f.castAISpellRepeatedly(caster)

			// The property: nothing affordable AND castable may remain.
			if leftover := f.chooseAISpell(caster, enemy); leftover != 0 {
				t.Errorf("AI stopped with %d AP left but spell %d is still castable - "+
					"that is a wasted cast every turn", caster.AP, leftover)
			}
		})
	}
}
