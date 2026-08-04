package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func TestCarryThrow(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70}
	target := &FightFighter{WireID: 2, TeamID: 0, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{caster, target}}, {ID: 1}}}
	f.setPhase(PhaseAction)

	f.applyCarry(caster, gamedata.Effect{ActionID: 58}, target.Pos)
	if caster.CarriedFighter != target || target.CarriedByFighter != caster || target.Pos != caster.Pos {
		t.Fatalf("carry: links=%v/%v pos=%v", caster.CarriedFighter == target, target.CarriedByFighter == caster, target.Pos)
	}
	// The carrier drags the passenger when it walks.
	f.applyFighterMove(caster, []Pos{{X: 6, Y: 15}})
	if target.Pos != caster.Pos {
		t.Errorf("passenger not dragged: %v vs %v", target.Pos, caster.Pos)
	}
	// Throw to a free cell clears the links.
	f.applyThrow(caster, gamedata.Effect{ActionID: 59}, Pos{X: 10, Y: 15})
	if caster.CarriedFighter != nil || target.CarriedByFighter != nil {
		t.Errorf("throw did not clear links")
	}
	if target.Pos.X != 10 || target.Pos.Y != 15 {
		t.Errorf("thrown to %v, want (10,15)", target.Pos)
	}
	// Throw with an empty grip is a no-op.
	f.applyThrow(caster, gamedata.Effect{ActionID: 59}, Pos{X: 11, Y: 15})
}

func TestLineDamage(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	mid := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 7, Y: 15}, HP: 60, MaxHP: 60}     // in the box
	target := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 9, Y: 15}, HP: 60, MaxHP: 60}  // aimed end (excluded)
	outside := &FightFighter{WireID: 4, TeamID: 1, Pos: Pos{X: 7, Y: 12}, HP: 60, MaxHP: 60} // off the line
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{mid, target, outside}},
	}}
	f.setPhase(PhaseAction)

	// Line fire (178) base 20, from (5,15) to (9,15): box = row 15, x in [5,9].
	f.applyLineDamage(caster, gamedata.Effect{ActionID: 178, Params: []float32{20}}, target.Pos)
	if mid.HP != 40 {
		t.Errorf("mid (in line) HP=%d want 40", mid.HP)
	}
	if target.HP != 60 {
		t.Errorf("aimed target excluded: HP=%d want 60", target.HP)
	}
	if outside.HP != 60 {
		t.Errorf("off-line fighter spared: HP=%d want 60", outside.HP)
	}
}

func TestZoneMPLoss(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, MP: 3, MaxMP: 3}
	near := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60, MP: 3, MaxMP: 3}
	far := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 12, Y: 15}, HP: 60, MaxHP: 60, MP: 3, MaxMP: 3}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{near, far}},
	}}
	f.setPhase(PhaseAction)

	// Circle radius 2 centered on the caster, drain 2 MP.
	f.applyZoneMPLoss(caster, gamedata.Effect{ActionID: 177, Params: []float32{2}, AreaShape: 2, AreaSize: []int32{2}}, caster.Pos)
	if near.MP != 1 {
		t.Errorf("near MP=%d want 1", near.MP)
	}
	if far.MP != 3 {
		t.Errorf("far MP=%d want 3 (out of zone)", far.MP)
	}
	if caster.MP != 3 {
		t.Errorf("caster MP=%d want 3 (excluded)", caster.MP)
	}
}

func TestDamageTransfer(t *testing.T) {
	absorber := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	protected := &FightFighter{WireID: 2, TeamID: 0, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60}
	attacker := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 9, Y: 15}, HP: 60, MaxHP: 60, AP: 6, MaxAP: 6}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{absorber, protected}},
		{ID: 1, Fighters: []*FightFighter{attacker}},
	}}
	f.setPhase(PhaseAction)

	// 50% of `protected`'s incoming damage is absorbed by `absorber` (the caster).
	f.applyDamageTransfer(absorber, gamedata.Effect{ActionID: 129, Params: []float32{50}, Duration: []int32{3, 0}}, protected.Pos)
	if protected.transfer == nil || protected.transfer.to != absorber || protected.transfer.pct != 50 {
		t.Fatal("transfer link not set")
	}
	// Neutral 20 hit on `protected`: 10 lands, 10 is redirected to `absorber`.
	f.applyDamageEffect(attacker, gamedata.Effect{ActionID: 1, Params: []float32{20}}, protected.Pos, false)
	if protected.HP != 50 || absorber.HP != 60 {
		t.Errorf("transfer split: protected=%d absorber=%d want 50/60", protected.HP, absorber.HP)
	}
	f.tickTransfers()
	f.tickTransfers()
	f.tickTransfers()
	if protected.transfer != nil {
		t.Error("transfer should expire after 3 ticks")
	}
}

func TestAura(t *testing.T) {
	tmpl := &gamedata.StaticEffect{ID: 5, Type: "TRAP", AreaShape: 2, AreaSize: []int32{2}, MaxExec: 63,
		Effects: []gamedata.Effect{{ActionID: 2, Params: []float32{10}}}} // fire 10
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70}
	enemy := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{
		Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{caster}}, {ID: 1, Fighters: []*FightFighter{enemy}}},
		deps:  &Deps{StaticEffects: gamedata.NewStaticEffects(tmpl), Fights: NewFightManager()},
	}
	f.setPhase(PhaseAction)

	f.applySetAura(caster, gamedata.Effect{ActionID: 176, Params: []float32{5}, Duration: []int32{3, 0}}, caster.Pos)
	if len(f.effectAreas) != 1 || !f.effectAreas[0].isAura() {
		t.Fatal("aura not created")
	}
	a := f.effectAreas[0]
	// The aura's centre follows the caster.
	if !a.contains(Pos{X: 8, Y: 15}) {
		t.Error("aura should contain (8,15) near the caster")
	}
	caster.Pos = Pos{X: 15, Y: 20}
	if a.contains(Pos{X: 8, Y: 15}) || !a.contains(Pos{X: 16, Y: 20}) {
		t.Error("aura centre did not follow the caster")
	}
	caster.Pos = Pos{X: 7, Y: 15}

	// An enemy starting its turn in the aura takes the inner fire effect.
	f.checkEffectAreasTurnStart(enemy)
	if enemy.HP != 50 {
		t.Errorf("enemy in aura at turn-start HP=%d want 50", enemy.HP)
	}
	// The aura does NOT fire on its own caster.
	f.checkEffectAreasTurnStart(caster)
	if caster.HP != 70 {
		t.Errorf("aura fired on its own caster: HP=%d want 70", caster.HP)
	}
	// Lifetime expires after its duration.
	f.tickEffectAreas()
	f.tickEffectAreas()
	f.tickEffectAreas()
	if len(f.effectAreas) != 0 {
		t.Errorf("aura should expire: %d areas left", len(f.effectAreas))
	}
}
