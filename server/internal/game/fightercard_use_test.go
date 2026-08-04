package game

import (
	"math/rand"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// weaponCard builds a fighter card with a use-time (active) damage ability.
func weaponCard(id, ap, rangeMin, rangeMax int32) *gamedata.FighterCard {
	return &gamedata.FighterCard{
		ID: id, Type: 1, APCost: ap, RangeMin: rangeMin, RangeMax: rangeMax,
		UseEffects: []gamedata.Effect{
			// action 5 = an elemental damage effect (KindDamage), point area.
			{ActionID: 5, AreaShape: 1, Params: []float32{10}, IsCritical: false},
			{ActionID: 5, AreaShape: 1, Params: []float32{30}, IsCritical: true},
		},
	}
}

// cardUseFight builds a fight where `attacker` (team 0) holds the given cards and
// an enemy stands two cells away. Crit/fumble are forced off by default.
func cardUseFight(cards ...*gamedata.FighterCard) (*Fight, *FightFighter, *FightFighter) {
	equipped := make([]domain.FighterObject, 0, len(cards))
	for _, c := range cards {
		equipped = append(equipped, domain.FighterObject{TemplateID: c.ID})
	}
	attacker := &FightFighter{
		WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70,
		AP: 6, MaxAP: 6, MP: 3, MaxMP: 3, CritRate: 0, FumbleRate: 0,
		Fighter: &domain.Fighter{Name: "Attacker", BreedID: 8, Objects: equipped},
	}
	enemy := &FightFighter{
		WireID: 2, TeamID: 1, Pos: Pos{X: 9, Y: 15}, HP: 70, MaxHP: 70,
		AP: 6, MaxAP: 6, MP: 3, MaxMP: 3,
		Fighter: &domain.Fighter{Name: "Enemy", BreedID: 1},
	}
	f := &Fight{
		Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{attacker}},
			{ID: 1, Fighters: []*FightFighter{enemy}},
		},
		deps: &Deps{FighterCards: gamedata.NewFighterCards(cards...)},
		rng:  rand.New(rand.NewSource(1)),
	}
	f.setPhase(PhaseAction)
	f.Timeline = []*FightFighter{attacker, enemy}
	f.turnIndex = 0
	return f, attacker, enemy
}

// TestFighterCardUseDealsDamageAndCostsAP is the core regression: playing an
// equipped weapon must actually hurt the target and spend its AP. Before this,
// 8107 was acknowledged and animated but applied nothing at all.
func TestFighterCardUseDealsDamageAndCostsAP(t *testing.T) {
	card := weaponCard(85, 4, 1, 3)
	f, attacker, enemy := cardUseFight(card)

	if !f.useFighterCard(attacker, 85, enemy.Pos) {
		t.Fatal("weapon use was refused")
	}
	if enemy.HP >= 70 {
		t.Errorf("weapon dealt no damage: enemy HP %d", enemy.HP)
	}
	if attacker.AP != 2 {
		t.Errorf("attacker AP = %d, want 2 (6 - the card's 4)", attacker.AP)
	}
}

// TestFighterCardUseRejectsUnowned: a client must not be able to play equipment
// the fighter does not have.
func TestFighterCardUseRejectsUnowned(t *testing.T) {
	owned := weaponCard(85, 4, 1, 3)
	other := weaponCard(99, 4, 1, 3)
	// Only card 85 is equipped; the catalog still knows both.
	f, attacker, enemy := cardUseFight(owned)
	f.deps.FighterCards = gamedata.NewFighterCards(owned, other)

	if f.useFighterCard(attacker, 99, enemy.Pos) {
		t.Error("played a card the fighter does not have equipped")
	}
	if enemy.HP != 70 || attacker.AP != 6 {
		t.Errorf("refused play still had effects: enemyHP=%d ap=%d", enemy.HP, attacker.AP)
	}
}

// TestFighterCardUseRejectsPassiveOnly: gear with no FIGHTER_CARD_USE effects is
// not an action (client jb_2.isUsable), so playing it must be refused.
func TestFighterCardUseRejectsPassiveOnly(t *testing.T) {
	passive := &gamedata.FighterCard{ID: 60, APCost: 3, RangeMin: 1, RangeMax: 3,
		EquipEffects: []gamedata.Effect{{ActionID: 11, Params: []float32{20}}}}
	f, attacker, enemy := cardUseFight(passive)
	if f.useFighterCard(attacker, 60, enemy.Pos) {
		t.Error("played a passive-only equipment card")
	}
	if attacker.AP != 6 {
		t.Errorf("a refused play spent AP: %d", attacker.AP)
	}
}

// TestFighterCardUseRangeBand: the target must fall inside [min,max]. The enemy is
// 2 cells away, so a melee (1-1) weapon cannot reach it and a min-3 weapon is too
// close to use.
func TestFighterCardUseRangeBand(t *testing.T) {
	melee := weaponCard(85, 4, 1, 1)
	f, attacker, enemy := cardUseFight(melee)
	if f.useFighterCard(attacker, 85, enemy.Pos) {
		t.Error("a range 1-1 weapon reached a cell 2 away")
	}

	far := weaponCard(49, 4, 3, 4)
	f, attacker, enemy = cardUseFight(far)
	if f.useFighterCard(attacker, 49, enemy.Pos) {
		t.Error("a min-range-3 weapon hit a cell only 2 away")
	}

	ok := weaponCard(43, 4, 1, 3)
	f, attacker, enemy = cardUseFight(ok)
	if !f.useFighterCard(attacker, 43, enemy.Pos) {
		t.Error("a range 1-3 weapon could not reach a cell 2 away")
	}
	_ = enemy
}

// TestFighterCardUseRangeStatExtendsOnlyRangedWeapons mirrors the spell rule: the
// Range characteristic extends a weapon whose base max is > 1, never a melee one.
func TestFighterCardUseRangeStatExtendsOnlyRangedWeapons(t *testing.T) {
	ranged := weaponCard(43, 4, 1, 1)
	ranged.RangeMax = 1
	f, attacker, enemy := cardUseFight(ranged)
	attacker.Range = 5 // a big range bonus must NOT extend a melee weapon
	if f.useFighterCard(attacker, 43, enemy.Pos) {
		t.Error("the Range stat extended a base-max-1 (melee) weapon")
	}

	bow := weaponCard(37, 4, 1, 2)
	f, attacker, enemy = cardUseFight(bow)
	attacker.Range = 2
	far := Pos{X: attacker.Pos.X + 4, Y: attacker.Pos.Y}
	if !f.Arena().walkable(far.X, far.Y) {
		t.Skip("test arena has no walkable cell 4 away")
	}
	if !f.cardTargetValid(attacker, bow, far) {
		t.Error("the Range stat did not extend a ranged weapon's max")
	}
}

// TestFighterCardUseNotEnoughAP: a weapon costing more AP than the fighter has
// must be refused outright, with no damage and no AP spent.
func TestFighterCardUseNotEnoughAP(t *testing.T) {
	card := weaponCard(85, 4, 1, 3)
	f, attacker, enemy := cardUseFight(card)
	attacker.AP = 3
	if f.useFighterCard(attacker, 85, enemy.Pos) {
		t.Error("played a weapon without enough AP")
	}
	if attacker.AP != 3 || enemy.HP != 70 {
		t.Errorf("refused play had effects: ap=%d enemyHP=%d", attacker.AP, enemy.HP)
	}
}

// TestFighterCardUseCritUsesTheCriticalSubset: a crit must resolve the IsCritical
// effects (30 damage), not the normal ones (10).
func TestFighterCardUseCritUsesTheCriticalSubset(t *testing.T) {
	card := weaponCard(85, 4, 1, 3)

	f, attacker, enemy := cardUseFight(card)
	attacker.CritRate = 100 // rates are percentages: 100 = always crit
	f.useFighterCard(attacker, 85, enemy.Pos)
	critDamage := 70 - enemy.HP

	f, attacker, enemy = cardUseFight(card)
	f.useFighterCard(attacker, 85, enemy.Pos)
	normalDamage := 70 - enemy.HP

	if critDamage <= normalDamage {
		t.Errorf("crit damage %d not greater than normal %d — the IsCritical subset was not selected",
			critDamage, normalDamage)
	}
}

// TestFighterCardUseFumbleSpendsAPButDoesNothing mirrors the spell rule.
func TestFighterCardUseFumbleSpendsAPButDoesNothing(t *testing.T) {
	card := weaponCard(85, 4, 1, 3)
	f, attacker, enemy := cardUseFight(card)
	attacker.FumbleRate = 100 // rates are percentages: 100 = always fumble
	if !f.useFighterCard(attacker, 85, enemy.Pos) {
		t.Fatal("a fumbled play should still fire (and spend AP)")
	}
	if enemy.HP != 70 {
		t.Errorf("a fumble dealt damage: enemy HP %d", enemy.HP)
	}
	if attacker.AP != 2 {
		t.Errorf("a fumble did not spend AP: %d, want 2", attacker.AP)
	}
}

// TestFighterCardUseOutOfTurn: only the fighter whose turn it is may act.
func TestFighterCardUseOutOfTurn(t *testing.T) {
	card := weaponCard(85, 4, 1, 3)
	f, attacker, enemy := cardUseFight(card)
	f.turnIndex = 1 // it is the enemy's turn
	if f.useFighterCard(attacker, 85, enemy.Pos) {
		t.Error("a fighter acted out of turn")
	}
}
