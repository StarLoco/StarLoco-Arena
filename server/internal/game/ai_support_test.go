package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// healSpell builds a pure support heal: one KindHeal effect (action 69), no
// harmful effect.
func healSpell(id int32, ap int8, rangeMax int8, amount float32) *gamedata.Spell {
	return &gamedata.Spell{
		ID: id, AP: ap, RangeMin: 0, RangeMax: rangeMax,
		Effects: []gamedata.Effect{{ActionID: 69, EffectID: 100, Params: []float32{amount}}},
	}
}

// aiHealFight: an AI caster with a heal, plus a wounded ally and an enemy.
func aiHealFight(t *testing.T, heal *gamedata.Spell) (*Fight, *FightFighter, *FightFighter, *FightFighter) {
	t.Helper()
	f, caster, enemy := summonTestFight()
	f.deps.Spells = gamedata.NewSpells(heal)
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: heal.ID}}}
	caster.AP, caster.MaxAP = 6, 6

	ally := &FightFighter{
		WireID: 55, TeamID: caster.TeamID,
		Pos: Pos{X: caster.Pos.X + 1, Y: caster.Pos.Y},
		HP:  20, MaxHP: 100, // 20% - well under the threshold
	}
	f.Teams[caster.TeamID].Fighters = append(f.Teams[caster.TeamID].Fighters, ally)
	return f, caster, ally, enemy
}

// TestAIHealsAWoundedAlly is the capability that was missing: the attack path
// gates on "harms an enemy", which correctly stopped the AI healing opponents but
// also meant support spells were never cast at all.
func TestAIHealsAWoundedAlly(t *testing.T) {
	f, caster, ally, _ := aiHealFight(t, healSpell(600, 3, 6, 30))
	before := ally.HP

	if !f.aiSupportCast(caster) {
		t.Fatal("the AI did not heal an ally at 20% HP")
	}
	if ally.HP <= before {
		t.Errorf("ally HP %d -> %d, expected a heal", before, ally.HP)
	}
}

// TestAINeverHealsAnEnemy is the mirror of the friendly-fire guard. The same code
// that finds an ally is one sign flip away from finding an opponent.
func TestAINeverHealsAnEnemy(t *testing.T) {
	f, caster, ally, enemy := aiHealFight(t, healSpell(601, 3, 12, 30))
	// Make the ENEMY the most attractive heal target by every measure except
	// which side it is on.
	enemy.HP, enemy.MaxHP = 1, 100
	ally.HP, ally.MaxHP = 99, 100 // healthy: not worth healing
	enemyBefore := enemy.HP

	f.aiSupportCast(caster)

	if enemy.HP > enemyBefore {
		t.Errorf("the AI HEALED an enemy (HP %d -> %d)", enemyBefore, enemy.HP)
	}
}

// TestAIDoesNotHealScratches: a naive "heal anyone damaged" rule burns the turn
// topping up trivial damage and looks robotic.
func TestAIDoesNotHealScratches(t *testing.T) {
	f, caster, ally, _ := aiHealFight(t, healSpell(602, 3, 6, 30))
	ally.HP, ally.MaxHP = 95, 100 // 95% - a scratch

	if f.aiSupportCast(caster) {
		t.Errorf("the AI spent an action healing an ally at %d%% HP",
			ally.HP*100/ally.MaxHP)
	}
}

// TestAIHealEstimateIgnoresOverheal: a 200-point heal on someone 10 HP down is
// worth 10. Valuing it at 200 would make the AI prefer a pointless big heal over
// a useful small one.
func TestAIHealEstimateIgnoresOverheal(t *testing.T) {
	f, caster, ally, _ := aiHealFight(t, healSpell(603, 3, 6, 200))
	ally.HP, ally.MaxHP = 90, 100 // missing 10

	got := f.aiEstimatedHeal(caster, ally, f.deps.Spells.Get(603))
	if got != 10 {
		t.Errorf("estimated heal = %d, want 10 (capped by missing HP)", got)
	}
}

// TestAISpellHealsAllyRejectsMixedSpells: a spell that both damages and heals is
// an attack and belongs to the attack path, or the AI would "support" an ally by
// hitting it.
func TestAISpellHealsAllyRejectsMixedSpells(t *testing.T) {
	mixed := &gamedata.Spell{
		ID: 604, AP: 3, RangeMax: 6,
		Effects: []gamedata.Effect{
			{ActionID: 69, EffectID: 100, Params: []float32{30}}, // heal
			{ActionID: 2, EffectID: 101, Params: []float32{20}},  // fire damage
		},
	}
	if aiSpellHealsAlly(mixed) {
		t.Error("a damage+heal spell was classified as a pure ally heal")
	}
	if !aiSpellHealsAlly(healSpell(605, 3, 6, 30)) {
		t.Error("a pure heal was not classified as an ally heal")
	}
}
