package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// dmgSpell builds a flat-damage spell (action 3 = water damage) so Damage()
// reports `amount` and the AI can rank it.
func dmgSpell(id int32, ap int8, rMin, rMax int8, amount float32) *gamedata.Spell {
	return &gamedata.Spell{
		ID: id, AP: ap, RangeMin: rMin, RangeMax: rMax,
		Effects: []gamedata.Effect{{ActionID: 3, EffectID: id * 10, Params: []float32{amount}}},
	}
}

// TestAIRepertoire covers the source and ordering of the AI's castable set, and
// the no-regression property that matters most: a SUMMON (one spell, no
// domain.Fighter) still yields exactly that one spell.
func TestAIRepertoire(t *testing.T) {
	f, _, _ := summonTestFight()

	// A summon: SummonSpellID only, empty Fighter.Spells.
	summon := &FightFighter{WireID: 20, TeamID: 0, SummonSpellID: 105}
	if got := f.aiRepertoire(summon); len(got) != 1 || got[0] != 105 {
		t.Errorf("summon repertoire = %v, want exactly [105]", got)
	}

	// A spell-less fighter has nothing to cast.
	if got := f.aiRepertoire(&FightFighter{WireID: 21}); len(got) != 0 {
		t.Errorf("spell-less repertoire = %v, want empty", got)
	}

	// A real fighter: SummonSpellID leads, then its own spells, deduped.
	real := &FightFighter{
		WireID: 22, SummonSpellID: 200,
		Fighter: &domain.Fighter{Spells: []domain.FighterSpell{
			{SpellID: 300}, {SpellID: 200}, {SpellID: 301},
		}},
	}
	got := f.aiRepertoire(real)
	want := []int32{200, 300, 301}
	if len(got) != len(want) {
		t.Fatalf("repertoire = %v, want %v", got, want)
	}
	for i := range want {
		if got[i] != want[i] {
			t.Fatalf("repertoire = %v, want %v (SummonSpellID first, then own, deduped)", got, want)
		}
	}
}

// TestChooseAISpellPicksBestAffordableInRange is the core of the upgrade: the AI
// must pick the hardest-hitting spell it can actually cast right now, and must
// not pick one that is unaffordable, out of range, or on cooldown.
func TestChooseAISpellPicksBestAffordableInRange(t *testing.T) {
	weak := dmgSpell(300, 2, 1, 6, 10)   // cheap, long range, weak
	strong := dmgSpell(301, 4, 1, 6, 40) // pricey, long range, strong
	melee := dmgSpell(302, 2, 1, 1, 99)  // huge but range 1 only

	f, caster, enemy := summonTestFight() // caster (7,15), enemy (12,15): dist 5
	f.deps.Spells = gamedata.NewSpells(weak, strong, melee)
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{
		{SpellID: 300}, {SpellID: 301}, {SpellID: 302},
	}}

	// AP 6: the strong one is affordable and in range; melee is out of range
	// despite the biggest number.
	caster.AP = 6
	if got := f.chooseAISpell(caster, enemy); got != 301 {
		t.Errorf("chooseAISpell at AP6 = %d, want 301 (strongest in range)", got)
	}

	// AP 3: the strong one is no longer affordable, so it falls back to the weak
	// one rather than casting nothing.
	caster.AP = 3
	if got := f.chooseAISpell(caster, enemy); got != 300 {
		t.Errorf("chooseAISpell at AP3 = %d, want 300 (strongest AFFORDABLE)", got)
	}

	// AP 1: nothing is affordable.
	caster.AP = 1
	if got := f.chooseAISpell(caster, enemy); got != 0 {
		t.Errorf("chooseAISpell at AP1 = %d, want 0 (nothing castable)", got)
	}

	// Adjacent: melee becomes legal and is the strongest.
	caster.AP = 6
	caster.Pos = Pos{X: 11, Y: 15}
	if got := f.chooseAISpell(caster, enemy); got != 302 {
		t.Errorf("chooseAISpell adjacent = %d, want 302 (melee now in range, biggest)", got)
	}

	// A spell on cooldown is skipped: burn 302's once-per-fight cooldown.
	melee.Cooldown = 63
	caster.CastHistory.storeCast(melee.LimitKeyID(), melee.Cooldown, melee.CastMaxPerTurn,
		melee.CastMaxPerTarget, f.tableTurn, enemy.WireID, true)
	if got := f.chooseAISpell(caster, enemy); got != 301 {
		t.Errorf("chooseAISpell with 302 on cooldown = %d, want 301", got)
	}
}

// TestCastAISpellRepeatedlyUsesMultipleSpells proves the turn is played from the
// repertoire rather than one spell: with 6 AP and a 4-AP + 2-AP pair, the AI
// should spend everything by casting BOTH, which the old single-spell loop
// could not do.
func TestCastAISpellRepeatedlyUsesMultipleSpells(t *testing.T) {
	weak := dmgSpell(300, 2, 1, 6, 10)
	strong := dmgSpell(301, 4, 1, 6, 40)

	f, caster, enemy := summonTestFight()
	f.deps.Spells = gamedata.NewSpells(weak, strong)
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 300}, {SpellID: 301}}}
	caster.AP, caster.MaxAP = 6, 6
	enemy.HP, enemy.MaxHP = 500, 500 // survive the whole turn

	f.castAISpellRepeatedly(caster)

	if caster.AP != 0 {
		t.Errorf("AP left = %d, want 0 (4-AP strong + 2-AP weak spends exactly 6)", caster.AP)
	}
	if enemy.HP >= 500 {
		t.Errorf("enemy took no damage (HP %d)", enemy.HP)
	}
}
