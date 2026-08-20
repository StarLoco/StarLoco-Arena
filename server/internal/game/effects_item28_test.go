package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestSelfPushMovesTheCasterNotTheTarget is the whole point of 153: it is push
// with the roles swapped. Getting it backwards would look almost right (someone
// moves, collision maths still run) which is exactly why it is asserted both
// ways round.
func TestSelfPushMovesTheCasterNotTheTarget(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 5, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	drain := captureFight(t, f)

	f.resolveEffect(caster, gamedata.Effect{ActionID: 153, EffectID: 700, Params: []float32{2}}, victim.Pos)

	if victim.Pos.X != 5 {
		t.Errorf("the TARGET moved to x=%d; 153 must move the caster", victim.Pos.X)
	}
	if caster.Pos.X != 10 {
		t.Fatalf("caster.x = %d, want 10 (recoiled 2 cells away from the target at x=5)", caster.Pos.X)
	}
	// The client moves the effect's CASTER (azw_0 uses bWl), so the destination
	// in part 3 must be the caster's new cell.
	got := runningEffectFrames(t, drain(), 153)
	if len(got) != 1 {
		t.Fatalf("got %d broadcasts, want 1", len(got))
	}
	p3 := got[0][3]
	if p3 == nil {
		t.Fatal("153 broadcast has no part 3 - the client would NPE exactly as push did")
	}
	if x := be32(p3[0:4]); x != caster.Pos.X {
		t.Errorf("part 3 destination x = %d, want the caster's new x %d", x, caster.Pos.X)
	}
}

// TestSelfPushRespectsStabilisationOfTheCaster: the guard has to move with the
// role. `azw_0` tests the stability state on `bWl`, so it is the CASTER that
// cannot be shifted - checking the target instead would let a stabilised caster
// slide.
func TestSelfPushRespectsStabilisationOfTheCaster(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 5, Y: 15}, HP: 60, MaxHP: 60}
	caster.addState(stateStabilized, 3)
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	f.resolveEffect(caster, gamedata.Effect{ActionID: 153, EffectID: 700, Params: []float32{2}}, victim.Pos)
	if caster.Pos.X != 8 {
		t.Errorf("a stabilised caster was still shoved to x=%d", caster.Pos.X)
	}
}

// TestRevealInvisibleClearsTheState covers 84's server half. The client expires
// its own `co_0`, so the risk is the mirror image: the sprite reappears while the
// server still thinks the fighter is hidden and keeps it out of AI targeting.
func TestRevealInvisibleClearsTheState(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	victim.addState(stateInvisible, 5)
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	drain := captureFight(t, f)

	f.resolveEffect(caster, gamedata.Effect{ActionID: 84, EffectID: 800}, victim.Pos)

	if victim.hasState(stateInvisible) {
		t.Fatal("target is still invisible server-side")
	}
	if len(runningEffectFrames(t, drain(), 84)) != 1 {
		t.Error("no 84 broadcast, so the client would never expire its own co_0")
	}
}

// TestRevealInvisibleIsSilentOnAVisibleTarget: `aum` collects the target's
// invisibility effects and expires them - on a visible fighter that set is empty
// and nothing happens. Broadcasting anyway would put a bogus reveal in the log.
func TestRevealInvisibleIsSilentOnAVisibleTarget(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	drain := captureFight(t, f)
	f.resolveEffect(caster, gamedata.Effect{ActionID: 84, EffectID: 800}, victim.Pos)
	if n := len(runningEffectFrames(t, drain(), 84)); n != 0 {
		t.Errorf("%d reveal broadcasts for an already-visible fighter, want 0", n)
	}
}

// TestSpellCooldownShortensAnInfiniteCooldown is 140's headline case: the shipped
// users are once-per-fight spells (cooldown 63), and the client treats infinite
// as "later than anything", so the discount always applies there.
func TestSpellCooldownShortensAnInfiniteCooldown(t *testing.T) {
	var h spellCastHistory
	const spell = int32(23)
	h.storeCast(spell, 63, 0, 0, 4, 0, false)
	if h.canCast(spell, 63, 0, 0, 99, 0, false) {
		t.Fatal("a 63-cooldown spell became castable again on its own")
	}
	h.recastAfter(spell, 63, 3, 4) // recastable at turn 7
	if h.canCast(spell, 63, 0, 0, 6, 0, false) {
		t.Error("castable at turn 6, one turn early")
	}
	if !h.canCast(spell, 63, 0, 0, 7, 0, false) {
		t.Error("not castable at turn 7, the turn the effect promised")
	}
}

// TestSpellCooldownNeverDelays pins the client's second guard: `sH.a` only
// rewrites the expiry when the new one is SOONER. A 140 with a big parameter on a
// short cooldown must not extend it.
func TestSpellCooldownNeverDelays(t *testing.T) {
	var h spellCastHistory
	const spell = int32(32)
	h.storeCast(spell, 2, 0, 0, 4, 0, false) // castable again at turn 6
	h.recastAfter(spell, 2, 10, 4)           // would push it to 14
	if !h.canCast(spell, 2, 0, 0, 6, 0, false) {
		t.Error("the effect DELAYED the spell instead of only ever shortening it")
	}
}

// TestSpellCooldownKeepsTheBestDiscount: two 140s on the same spell in one fight
// must leave the EARLIER expiry standing. Since the stored expiry is now the
// authoritative cooldown, a later one would otherwise walk the spell back.
func TestSpellCooldownKeepsTheBestDiscount(t *testing.T) {
	var h spellCastHistory
	const spell = int32(36)
	h.storeCast(spell, 63, 0, 0, 1, 0, false)
	h.recastAfter(spell, 63, 2, 1) // free at turn 3
	h.recastAfter(spell, 63, 8, 1) // would push it out to 9
	if !h.canCast(spell, 63, 0, 0, 3, 0, false) {
		t.Error("the second, worse 140 overrode the first")
	}
}

// TestSpellCooldownDoesNotSurviveTheNextCast: the discount belongs to the cast it
// was applied to. If it carried over, the spell would stay permanently cheap.
func TestSpellCooldownDoesNotSurviveTheNextCast(t *testing.T) {
	var h spellCastHistory
	const spell = int32(33)
	h.storeCast(spell, 63, 0, 0, 1, 0, false)
	h.recastAfter(spell, 63, 1, 1) // free again at turn 2
	if !h.canCast(spell, 63, 0, 0, 2, 0, false) {
		t.Fatal("discount did not apply")
	}
	h.storeCast(spell, 63, 0, 0, 2, 0, false) // cast it again
	if h.canCast(spell, 63, 0, 0, 3, 0, false) {
		t.Error("the old 140 discount carried over, so a once-per-fight spell is now unlimited")
	}
}

// TestSpellCooldownIgnoresASpellNotOnCooldown: `sH.a` returns early unless the
// spell is actually waiting, so a 140 that fires with nothing to shorten must not
// create an expiry out of nothing (which would otherwise become a free pass for
// the NEXT cast).
func TestSpellCooldownIgnoresASpellNotOnCooldown(t *testing.T) {
	var h spellCastHistory
	const spell = int32(36)
	h.recastAfter(spell, 63, 2, 5)
	if h.records != nil {
		if r, ok := h.records[spell]; ok && r.hasRecastFrom {
			t.Error("recorded a recast turn for a spell that was never cast")
		}
	}
}

// TestCursedCellInvertsItsBonus is 150. The curse is server-owned (the client's
// own flag has no reader at all), so this is the only place the mechanic exists.
func TestCursedCellInvertsItsBonus(t *testing.T) {
	f := &Fight{}
	if got := f.scaleBonusCellAt(5, 3, 4); got != 5 {
		t.Fatalf("uncursed cell scaled to %d, want 5", got)
	}
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 1, Y: 1}, HP: 70, MaxHP: 70}
	f.Teams = [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{caster}}, {ID: 1}}
	f.applyCurseBonusCells(caster, gamedata.Effect{
		ActionID: 150, EffectID: 900, Duration: []int32{2},
	}, Pos{X: 3, Y: 4})

	if got := f.scaleBonusCellAt(5, 3, 4); got != -5 {
		t.Errorf("cursed cell scaled to %d, want -5 (the bonus must harm)", got)
	}
	if got := f.scaleBonusCellAt(5, 9, 9); got != 5 {
		t.Errorf("a DIFFERENT cell scaled to %d, want 5 - the curse leaked", got)
	}
	f.tableTurn = 99
	if got := f.scaleBonusCellAt(5, 3, 4); got != 5 {
		t.Errorf("expired curse still inverting (%d)", got)
	}
}

// TestCursedCellStacksWithTheMultiplier: the challenge rule "bonus-cell effects
// x N" and the curse compose - a cursed x10 tile hurts ten times as much.
func TestCursedCellStacksWithTheMultiplier(t *testing.T) {
	f := &Fight{}
	f.Rules.BonusCellMultiplier = 10
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 1, Y: 1}, HP: 70, MaxHP: 70}
	f.Teams = [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{caster}}, {ID: 1}}
	f.applyCurseBonusCells(caster, gamedata.Effect{
		ActionID: 150, EffectID: 900, Duration: []int32{2},
	}, Pos{X: 3, Y: 4})
	if got := f.scaleBonusCellAt(5, 3, 4); got != -50 {
		t.Errorf("cursed x10 tile = %d, want -50", got)
	}
}

// TestSpellReturnSendsTheHitBack is 88. The redirect must be server-side: part 4
// now lets the client arm its own `amv_1`, which redirects on receipt, so a
// server that did not would put the damage on a different fighter than the
// client draws it on.
func TestSpellReturnSendsTheHitBack(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	f.resolveEffect(caster, gamedata.Effect{
		ActionID: 88, EffectID: 950, Params: []float32{100}, Duration: []int32{2},
	}, victim.Pos)
	if !victim.spellReturn {
		t.Fatal("88 did not arm the return")
	}
	f.resolveEffect(caster, gamedata.Effect{ActionID: 1, EffectID: 10, Params: []float32{12}}, victim.Pos)
	if victim.HP != 60 {
		t.Errorf("victim HP = %d, want 60 (the hit was returned)", victim.HP)
	}
	if caster.HP == 70 {
		t.Error("caster took no damage, so the hit was not returned at all")
	}
}

// TestSpellReturnDoesNotArmOnAFailedRoll: the client gates the redirect on the
// effect's own value (`0 < this.r`), so a roll of 0 must leave the fighter
// unprotected rather than silently arming it.
func TestSpellReturnDoesNotArmOnAFailedRoll(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	f.resolveEffect(caster, gamedata.Effect{
		ActionID: 88, EffectID: 950, Params: []float32{0}, Duration: []int32{2},
	}, victim.Pos)
	if victim.spellReturn {
		t.Fatal("a 0 roll still armed the return")
	}
	f.resolveEffect(caster, gamedata.Effect{ActionID: 1, EffectID: 10, Params: []float32{12}}, victim.Pos)
	if victim.HP == 60 {
		t.Error("the hit was returned even though the buff never procced")
	}
}

// TestSpellReturnIsOneShot: `amv_1` re-points ONE incoming effect. Leaving it
// armed would make the fighter permanently immune to spell damage.
func TestSpellReturnIsOneShot(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	victim.spellReturn = true
	f.resolveEffect(caster, gamedata.Effect{ActionID: 1, EffectID: 10, Params: []float32{12}}, victim.Pos)
	hpAfterFirst := victim.HP
	f.resolveEffect(caster, gamedata.Effect{ActionID: 1, EffectID: 10, Params: []float32{12}}, victim.Pos)
	if victim.HP >= hpAfterFirst {
		t.Error("the second hit was returned too - the buff never disarmed")
	}
}
