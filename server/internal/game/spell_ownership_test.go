package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestFighterKnowsSpell covers both legitimate sources of a castable spell and,
// just as importantly, the case that must NOT regress: a challenge demon, which
// carries a domain.Fighter for its breed and stats but an EMPTY spell list, its
// one spell living in SummonSpellID. Checking only Fighter.Spells would mute
// every demon in the game.
func TestFighterKnowsSpell(t *testing.T) {
	coachFighter := &FightFighter{Fighter: &domain.Fighter{Spells: []domain.FighterSpell{
		{SpellID: 12}, {SpellID: 34},
	}}}
	summon := &FightFighter{SummonSpellID: 77, Father: &FightFighter{}}
	demon := &FightFighter{ // AI opponent: real Fighter, no spell rows
		Fighter: &domain.Fighter{BreedID: 8}, SummonSpellID: 55,
	}
	bare := &FightFighter{Fighter: &domain.Fighter{}}

	for _, tc := range []struct {
		name  string
		ff    *FightFighter
		spell int32
		want  bool
	}{
		{"coach fighter knows an equipped spell", coachFighter, 12, true},
		{"coach fighter knows its second spell", coachFighter, 34, true},
		{"coach fighter does NOT know an unequipped spell", coachFighter, 99, false},
		{"summon knows its template spell", summon, 77, true},
		{"summon knows nothing else", summon, 12, false},
		{"challenge demon knows its breed spell", demon, 55, true},
		{"challenge demon knows nothing else", demon, 12, false},
		{"a fighter with no spells knows none", bare, 1, false},
		{"spell 0 is never 'known' via an unset SummonSpellID", bare, 0, false},
		{"nil fighter", nil, 1, false},
	} {
		if got := fighterKnowsSpell(tc.ff, tc.spell); got != tc.want {
			t.Errorf("%s: knows(%d) = %v, want %v", tc.name, tc.spell, got, tc.want)
		}
	}
}

// TestCastRefusedForUnknownSpell is the anti-cheat case end to end through
// castSpellByFighter: a forged 8109 naming any id in the table must not fire.
// The equipment path (8107) has always checked ownership; this closes the same
// hole on the spell path.
func TestCastRefusedForUnknownSpell(t *testing.T) {
	known := &gamedata.Spell{ID: 500, AP: 1, RangeMax: 6,
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	forged := &gamedata.Spell{ID: 501, AP: 1, RangeMax: 6,
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{500}}}}

	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70}
	// castFreqFight teaches the caster every template it is handed, so hand it
	// only the one it is supposed to own.
	f := castFreqFight(caster, []*FightFighter{victim}, known)
	f.deps.Spells = gamedata.NewSpells(known, forged) // both resolvable in the table

	if f.castSpellByFighter(caster, 501, victim.Pos) {
		t.Error("cast a spell the fighter does not know")
	}
	if victim.HP != 70 {
		t.Errorf("the forged cast still dealt damage: victim HP %d", victim.HP)
	}
	if caster.AP != 6 {
		t.Errorf("the forged cast still spent AP: caster AP %d", caster.AP)
	}
	// The spell it DOES own still works, so the guard is not simply blocking
	// everything.
	if !f.castSpellByFighter(caster, 500, victim.Pos) {
		t.Fatal("the fighter's own spell was refused")
	}
	if victim.HP == 70 {
		t.Error("the legitimate cast dealt no damage")
	}
}
