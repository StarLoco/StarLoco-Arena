package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func TestCarryCastGating(t *testing.T) {
	// Data-driven gating via the spells' real criterion tokens.
	carrySpell := &gamedata.Spell{ID: 100, AP: 4, RangeMax: 1, Criterion: "cantCastWhenCarrying", Effects: []gamedata.Effect{{ActionID: 58}}}
	throwSpell := &gamedata.Spell{ID: 101, AP: 4, RangeMax: 4, Criterion: "canCastWhenCarryEnnemy", Effects: []gamedata.Effect{{ActionID: 59}}}
	carriedGate := &gamedata.Spell{ID: 103, AP: 2, RangeMax: 4, Criterion: "cantCastWhenCarried", Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	freeSpell := &gamedata.Spell{ID: 104, AP: 2, RangeMax: 4, Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	carrier := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	f := &Fight{
		Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{carrier}}, {ID: 1, Fighters: []*FightFighter{victim}}},
		deps:  &Deps{Spells: gamedata.NewSpells(carrySpell, throwSpell, carriedGate, freeSpell), Fights: NewFightManager()},
	}
	f.Timeline = []*FightFighter{carrier, victim}
	f.turnIndex = 0
	f.setPhase(PhaseAction)

	// Throw needs to be carrying an enemy — rejected with an empty grip.
	if f.castSpellByFighter(carrier, 101, Pos{X: 10, Y: 15}) {
		t.Error("throw with empty grip should be rejected (canCastWhenCarryEnnemy)")
	}
	// Carry (cantCastWhenCarrying) succeeds with an empty grip.
	if !f.castSpellByFighter(carrier, 100, victim.Pos) || carrier.CarriedFighter != victim {
		t.Fatalf("carry cast should succeed and link (carrying=%v)", carrier.CarriedFighter == victim)
	}
	carrier.AP = 6
	// Carrying again is rejected (cantCastWhenCarrying now fails).
	if f.castSpellByFighter(carrier, 100, victim.Pos) {
		t.Error("carry while already carrying should be rejected")
	}
	// While carried, the victim is blocked from a cantCastWhenCarried spell but CAN
	// cast an ungated one (only 4 spells forbid it in the real data).
	f.turnIndex = 1
	victim.AP = 6
	if f.castSpellByFighter(victim, 103, carrier.Pos) {
		t.Error("carried fighter should not cast a cantCastWhenCarried spell")
	}
	if !f.castSpellByFighter(victim, 104, carrier.Pos) {
		t.Error("carried fighter SHOULD be able to cast an ungated spell")
	}
	// Back to the carrier: throw the enemy it carries.
	f.turnIndex = 0
	carrier.AP = 6
	if !f.castSpellByFighter(carrier, 101, Pos{X: 10, Y: 15}) || carrier.CarriedFighter != nil {
		t.Error("throw while carrying an enemy should succeed and clear the link")
	}
}

func TestCastCriteria(t *testing.T) {
	f := &Fight{Teams: [2]*FightTeam{{ID: 0}, {ID: 1}}}
	c := &FightFighter{WireID: 1, TeamID: 0, HP: 70, MaxHP: 70}
	f.Teams[0].Fighters = []*FightFighter{c}

	// Empty / unknown tokens are permissive.
	if !f.meetsCastCriteria(c, "") || !f.meetsCastCriteria(c, "someFutureToken") {
		t.Error("empty and unknown criteria should pass")
	}
	// HP thresholds.
	if f.meetsCastCriteria(c, "canCastWhenDying") { // full HP, not <=25%
		t.Error("canCastWhenDying should fail at full HP")
	}
	if f.meetsCastCriteria(c, "canCastWhenInjured") { // full HP, not <=99%
		t.Error("canCastWhenInjured should fail at full HP")
	}
	c.HP = 17 // <=25% of 70 (17.5)
	if !f.meetsCastCriteria(c, "canCastWhenDying") || !f.meetsCastCriteria(c, "canCastWhenInjured") {
		t.Error("dying/injured should pass at 17/70 HP")
	}
	// AND: all tokens must hold — dying passes but drunk does not.
	if f.meetsCastCriteria(c, "canCastWhenDying;canCastWhenDrunk") {
		t.Error("AND should fail when one token (drunk) fails")
	}
	c.addState(stateDrunk, 2)
	if !f.meetsCastCriteria(c, "canCastWhenDying;canCastWhenDrunk") {
		t.Error("AND should pass when both tokens hold")
	}
	// With no mask set: cannotCastWhenMask* passes, canCastWhenMask* fails.
	if !f.meetsCastCriteria(c, "cannotCastWhenMaskClass") || f.meetsCastCriteria(c, "canCastWhenMaskClass") {
		t.Error("unset mask state: cannot* should pass, can* should fail")
	}
}

// TestNbSummonsBuff verifies the NB_SUMMONS characteristic (client id 26): the
// base summon cap is one, an action-74 "+1 invocation" buff raises it to two, and
// the buff reverts on expiry — matching the client's canSummon formula
// (livingSummons < 1 + NB_SUMMONS) and the v2.04b reference.
func TestNbSummonsBuff(t *testing.T) {
	summoner := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	pet := &FightFighter{WireID: 2, TeamID: 0, Pos: Pos{X: 8, Y: 15}, HP: 20, MaxHP: 20, Father: summoner}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{summoner, pet}},
		{ID: 1},
	}}
	f.setPhase(PhaseAction)

	// Base cap is one: with one living summon out, canSummon is false.
	if f.meetsCastCriteria(summoner, "canSummon") {
		t.Fatal("canSummon should be false at the base cap of 1 with a summon out")
	}
	// The real "+1 invocation" self-buff (action 74, params=[1], INFINITE dur) lifts
	// NB_SUMMONS to 1 -> cap 2 -> canSummon true, and is permanent (survives a tick).
	f.applyBuff(summoner, gamedata.Effect{ActionID: 74, Params: []float32{1}, Duration: []int32{63, 0}}, summoner.Pos)
	if summoner.NbSummons != 1 {
		t.Fatalf("NbSummons after +1 buff = %d, want 1", summoner.NbSummons)
	}
	if !f.meetsCastCriteria(summoner, "canSummon") {
		t.Error("canSummon should be true at cap 2 with one summon out")
	}
	// A second living summon fills cap 2 -> canSummon false again.
	pet2 := &FightFighter{WireID: 3, TeamID: 0, Pos: Pos{X: 9, Y: 15}, HP: 20, MaxHP: 20, Father: summoner}
	f.Teams[0].Fighters = append(f.Teams[0].Fighters, pet2)
	if f.meetsCastCriteria(summoner, "canSummon") {
		t.Error("canSummon should be false with two summons out at cap 2")
	}
	f.tickBuffs() // the infinite +1 buff is untracked -> persists
	if summoner.NbSummons != 1 {
		t.Errorf("infinite +1 summon buff must persist, got NbSummons=%d", summoner.NbSummons)
	}
	// The real Masqueraider "summon steal" (action 74, params=[-1], 1 turn) is
	// PARAM-SIGNED: it lowers NB_SUMMONS by one for a turn, then reverts exactly.
	f.applyBuff(summoner, gamedata.Effect{ActionID: 74, Params: []float32{-1}, Duration: []int32{1, 0}}, summoner.Pos)
	if summoner.NbSummons != 0 {
		t.Fatalf("NbSummons after -1 steal = %d, want 0", summoner.NbSummons)
	}
	f.tickBuffs() // 1-turn steal expires -> reverts to the permanent +1
	if summoner.NbSummons != 1 {
		t.Fatalf("NbSummons after steal expiry = %d, want 1 (revert must be symmetric)", summoner.NbSummons)
	}
}

// TestMaskStates verifies the three Masqueraider masks (actions 173/174/175):
// they set the matching state, drive the canCastWhenMask* criteria, switch (the
// grant spell's bundled action-149 strips the old mask by effectId) and are
// permanent (infinite duration does not age out).
func TestMaskStates(t *testing.T) {
	wearer := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70}
	f := &Fight{Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{wearer}}, {ID: 1}}}
	f.setPhase(PhaseAction)

	// Don the Class mask (action 173, effId 9192, infinite duration in real data).
	f.applyState(wearer, gamedata.Effect{EffectID: 9192, ActionID: 173, Duration: []int32{63, 0}}, wearer.Pos)
	if !wearer.hasState(stateMaskClass) {
		t.Fatal("action 173 should set the Class mask")
	}
	if !f.meetsCastCriteria(wearer, "canCastWhenMaskClass") || f.meetsCastCriteria(wearer, "cannotCastWhenMaskClass") {
		t.Error("Class mask: canCastWhenMaskClass should pass, cannotCastWhenMaskClass should fail")
	}
	// Switch to Berzerk: the real spell applies the Berzerk state AND bundles a 149
	// that strips the Class state by its effectId (exactly what spell 471 does).
	f.applyState(wearer, gamedata.Effect{EffectID: 9194, ActionID: 175, Duration: []int32{63, 0}}, wearer.Pos)
	f.applyRemoveEffect(wearer, gamedata.Effect{ActionID: 149, Params: []float32{9192}}, wearer.Pos)
	if wearer.hasState(stateMaskClass) {
		t.Error("the switch's 149 should strip the Class mask")
	}
	if !wearer.hasState(stateMaskBerzerk) {
		t.Error("action 175 should set the Berzerk mask")
	}
	if !f.meetsCastCriteria(wearer, "canCastWhenMaskBerzerk") || f.meetsCastCriteria(wearer, "canCastWhenMaskClass") {
		t.Error("after switch: Berzerk should pass, Class should fail")
	}
	// Masks are permanent (infinite): a normal turn-tick does not age them out.
	f.tickStates()
	if !wearer.hasState(stateMaskBerzerk) {
		t.Error("a mask (infinite duration) must not age out on tickStates")
	}
}

// TestRemoveEffectByID verifies action 149 ("Retire un effet") strips each kind
// of running-effect by its source effectId and reverts it — the full mask bundle:
// a status state (173), an INFINITE stat malus (102, MP−), and a self-aura (176).
func TestRemoveEffectByID(t *testing.T) {
	tmpl := &gamedata.StaticEffect{ID: 1017, Type: "TRAP", AreaShape: 2, AreaSize: []int32{2}, MaxExec: 63,
		Effects: []gamedata.Effect{{ActionID: 2, Params: []float32{5}}}}
	wearer := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, MP: 3, MaxMP: 3}
	f := &Fight{
		Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{wearer}}, {ID: 1}},
		deps:  &Deps{StaticEffects: gamedata.NewStaticEffects(tmpl), Fights: NewFightManager()},
	}
	f.setPhase(PhaseAction)

	// Apply a Class-mask bundle: state (effId 9192), infinite MP malus (effId 9213),
	// self-aura (effId 9260) — each a distinct effectId, as in the shipped data.
	f.applyState(wearer, gamedata.Effect{EffectID: 9192, ActionID: 173, Duration: []int32{63, 0}}, wearer.Pos)
	f.applyBuff(wearer, gamedata.Effect{EffectID: 9213, ActionID: 102, Params: []float32{1}, Duration: []int32{63, 0}}, wearer.Pos)
	f.applySetAura(wearer, gamedata.Effect{EffectID: 9260, ActionID: 176, Params: []float32{1017}, Duration: []int32{63, 0}}, wearer.Pos)

	if wearer.MaxMP != 2 { // 3 - 1 (action 102 MP deboost)
		t.Fatalf("MP malus not applied: MaxMP=%d want 2", wearer.MaxMP)
	}
	if !wearer.hasState(stateMaskClass) || len(wearer.Buffs) != 1 || len(f.effectAreas) != 1 {
		t.Fatalf("bundle not all present: state=%v buffs=%d areas=%d",
			wearer.hasState(stateMaskClass), len(wearer.Buffs), len(f.effectAreas))
	}
	// The infinite malus must NOT age out on a normal turn.
	f.tickBuffs()
	if len(wearer.Buffs) != 1 || wearer.MaxMP != 2 {
		t.Fatalf("infinite malus wrongly aged: buffs=%d MaxMP=%d", len(wearer.Buffs), wearer.MaxMP)
	}

	// Each component's 149 strips it by effectId and reverts it.
	for _, rid := range []int32{9192, 9213, 9260} {
		f.applyRemoveEffect(wearer, gamedata.Effect{ActionID: 149, Params: []float32{float32(rid)}}, wearer.Pos)
	}
	if wearer.hasState(stateMaskClass) {
		t.Error("149 should have removed the mask state (9192)")
	}
	if len(wearer.Buffs) != 0 || wearer.MaxMP != 3 {
		t.Errorf("149 should have removed+reverted the MP malus (9213): buffs=%d MaxMP=%d", len(wearer.Buffs), wearer.MaxMP)
	}
	if len(f.effectAreas) != 0 {
		t.Error("149 should have destroyed the self-aura (9260)")
	}
}

func TestCarriedUntargetable(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70}
	target := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{target}},
	}}
	f.setPhase(PhaseAction)
	f.applyCarry(caster, gamedata.Effect{ActionID: 58}, target.Pos)
	// Both now share the caster's cell; a single-target lookup returns the CARRIER.
	if got := f.fighterAtCell(caster.Pos); got != caster {
		t.Errorf("fighterAtCell on the shared cell = %v, want the carrier", got)
	}
	// A single-target damage effect aimed at that cell hits the carrier, not the
	// carried fighter.
	f.applyDamageEffect(caster, gamedata.Effect{ActionID: 1, Params: []float32{10}}, caster.Pos, false)
	if target.HP != 60 {
		t.Errorf("carried fighter took single-target damage: HP=%d want 60", target.HP)
	}
}

func TestAuraTargetFilter(t *testing.T) {
	// An enemies-only aura (inner effect targets IS_ENEMY) skips allies.
	tmpl := &gamedata.StaticEffect{ID: 5, Type: "TRAP", AreaShape: 2, AreaSize: []int32{2}, MaxExec: 63,
		Effects: []gamedata.Effect{{ActionID: 2, Params: []float32{10}, Targets: []int64{condIsEnemy}}}}
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70}
	ally := &FightFighter{WireID: 2, TeamID: 0, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60}
	enemy := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{
		Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{caster, ally}}, {ID: 1, Fighters: []*FightFighter{enemy}}},
		deps:  &Deps{StaticEffects: gamedata.NewStaticEffects(tmpl), Fights: NewFightManager()},
	}
	f.setPhase(PhaseAction)
	f.applySetAura(caster, gamedata.Effect{ActionID: 176, Params: []float32{5}, Duration: []int32{5, 0}}, caster.Pos)

	// The ally starts its turn in the aura but is NOT an enemy -> unharmed.
	f.checkEffectAreasTurnStart(ally)
	if ally.HP != 60 {
		t.Errorf("enemies-only aura hit an ally: HP=%d want 60", ally.HP)
	}
	// The enemy starts its turn in the aura -> takes the inner fire effect.
	f.checkEffectAreasTurnStart(enemy)
	if enemy.HP != 50 {
		t.Errorf("enemies-only aura missed an enemy: HP=%d want 50", enemy.HP)
	}
}
