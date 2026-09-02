package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// aiTargetFight builds a fight with one AI caster and two reachable enemies so
// "which one does it pick" is actually a choice.
func aiTargetFight(t *testing.T, spellDmg float32, apCost int8) (*Fight, *FightFighter, *FightFighter, *FightFighter) {
	t.Helper()
	f, caster, near := summonTestFight()
	f.deps.Spells = gamedata.NewSpells(dmgSpell(500, apCost, 1, 12, spellDmg))
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 500}}}
	caster.AP, caster.MaxAP = 12, 12

	far := &FightFighter{
		WireID: 88, TeamID: near.TeamID,
		Pos: Pos{X: near.Pos.X + 1, Y: near.Pos.Y},
		HP:  100, MaxHP: 100, Init: 1,
	}
	f.Teams[near.TeamID].Fighters = append(f.Teams[near.TeamID].Fighters, far)
	return f, caster, near, far
}

// TestAIFinishesAKillableTarget: a player takes the kill in front of them. With a
// healthy enemy adjacent and a nearly-dead one one cell further, the AI must
// choose the one it can finish.
func TestAIFinishesAKillableTarget(t *testing.T) {
	f, caster, near, far := aiTargetFight(t, 30, 3)
	near.HP, near.MaxHP = 100, 100 // healthy, closest
	far.HP = 10                    // killable in one cast

	// Fixture check: both must be castable targets, or the "choice" is fake.
	if _, d := f.bestSpellAgainst(caster, near); d <= 0 {
		t.Fatal("fixture: the near enemy is not castable, so there is no choice to make")
	}
	if _, d := f.bestSpellAgainst(caster, far); d <= 0 {
		t.Fatal("fixture: the far enemy is not castable, so there is no choice to make")
	}
	if f.nearestOpponent(caster) != near {
		t.Fatal("fixture: the 'near' enemy is not the nearest, so this proves nothing")
	}

	if got := f.chooseAITarget(caster); got != far {
		t.Errorf("AI targeted %d, want the killable enemy %d - a player finishes what "+
			"they can reach", got.WireID, far.WireID)
	}
}

// TestAIPressesTheWeakestWhenNothingIsKillable: no finisher available, so the
// sensible play is the one closest to dying rather than merely the closest.
func TestAIPressesTheWeakestWhenNothingIsKillable(t *testing.T) {
	f, caster, near, far := aiTargetFight(t, 5, 3) // 5 damage kills nothing
	near.HP, near.MaxHP = 100, 100
	far.HP = 40

	if got := f.chooseAITarget(caster); got != far {
		t.Errorf("AI targeted %d, want the weakest reachable enemy %d",
			got.WireID, far.WireID)
	}
}

// TestAIFallsBackToNearestWhenNothingIsCastable: with no reachable target the
// movement logic still needs something to walk towards, so the old behaviour must
// survive.
func TestAIFallsBackToNearestWhenNothingIsCastable(t *testing.T) {
	f, caster, near, _ := aiTargetFight(t, 30, 3)
	f.deps.Spells = gamedata.NewSpells() // no spells at all -> nothing castable

	got := f.chooseAITarget(caster)
	if got != near {
		t.Errorf("with nothing castable the AI chose %v, want the nearest enemy %d",
			got, near.WireID)
	}
}

// TestAIKillPreferenceBeatsLowestHP: with per-target resistances the "finish it"
// branch stops being a synonym for "lowest HP", which is the only situation where
// it earns its place.
//
// Setup: the weakest enemy resists the spell's element hard enough to survive;
// a tougher one does not. A player reading the enemy's stats takes the kill.
func TestAIKillPreferenceBeatsLowestHP(t *testing.T) {
	f, caster, weakResistant := summonTestFight()
	f.deps.Spells = gamedata.NewSpells(dmgSpell(510, 3, 1, 12, 40))
	f.deps.Fights = NewFightManager()
	f.deps.Log = testLogger()
	caster.Fighter = &domain.Fighter{Spells: []domain.FighterSpell{{SpellID: 510}}}
	caster.AP, caster.MaxAP = 12, 12

	killable := &FightFighter{
		WireID: 91, TeamID: weakResistant.TeamID,
		Pos: Pos{X: weakResistant.Pos.X + 1, Y: weakResistant.Pos.Y},
		HP:  35, MaxHP: 35, Init: 1,
	}
	f.Teams[weakResistant.TeamID].Fighters = append(f.Teams[weakResistant.TeamID].Fighters, killable)

	// The nearer, LOWER-HP enemy resists this element enough to survive the hit.
	weakResistant.HP, weakResistant.MaxHP = 30, 30
	weakResistant.Stats.resPctAll = 90 // resists everything hard enough to survive

	dmgWeak := f.aiEstimatedDamage(caster, weakResistant, f.deps.Spells.Get(510))
	dmgKill := f.aiEstimatedDamage(caster, killable, f.deps.Spells.Get(510))
	if dmgWeak >= weakResistant.HP {
		t.Skipf("fixture: resistance did not prevent the kill (%d vs %d HP) - this "+
			"arena/stat setup cannot express the case", dmgWeak, weakResistant.HP)
	}
	if dmgKill < killable.HP {
		t.Skipf("fixture: the intended killable target survives (%d vs %d HP)",
			dmgKill, killable.HP)
	}

	if got := f.chooseAITarget(caster); got != killable {
		t.Errorf("AI targeted %d (HP %d), want the one it can actually finish, %d "+
			"(HP %d) - lowest HP is not the same as killable once resistances differ",
			got.WireID, got.HP, killable.WireID, killable.HP)
	}
}
