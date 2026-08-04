package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// castFreqFight builds a 1-caster-vs-N-target fight in the action phase whose
// deps carry the given spell templates, for exercising castSpellByFighter.
func castFreqFight(caster *FightFighter, enemies []*FightFighter, spells ...*gamedata.Spell) *Fight {
	f := &Fight{
		Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{caster}},
			{ID: 1, Fighters: enemies},
		},
		deps: &Deps{Spells: gamedata.NewSpells(spells...), Fights: NewFightManager()},
	}
	f.Timeline = append([]*FightFighter{caster}, enemies...)
	f.turnIndex = 0
	f.tableTurn = 1
	f.setPhase(PhaseAction)
	return f
}

// TestSpellCastMaxPerTurnAndInterval verifies CastMaxPerTurn (field 9) and
// Cooldown (field 8) are enforced in castSpellByFighter.
func TestSpellCastMaxPerTurnAndInterval(t *testing.T) {
	sp := &gamedata.Spell{ID: 200, AP: 1, RangeMax: 6, CastMaxPerTurn: 1, Cooldown: 2,
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70}
	f := castFreqFight(caster, []*FightFighter{victim}, sp)

	// Turn 1: first cast OK, second blocked by CastMaxPerTurn=1.
	if !f.castSpellByFighter(caster, 200, victim.Pos) {
		t.Fatal("first cast should succeed")
	}
	if f.castSpellByFighter(caster, 200, victim.Pos) {
		t.Error("second cast in the same turn should be blocked (CastMaxPerTurn=1)")
	}
	// New turn resets the per-turn counter, but Cooldown=2 still blocks a
	// recast at table-turn 2 (cast was at turn 1; needs >= 2 turns elapsed).
	caster.CastHistory.onNewTurn()
	f.tableTurn = 2
	if f.castSpellByFighter(caster, 200, victim.Pos) {
		t.Error("recast one turn later should be blocked (Cooldown=2)")
	}
	// Table-turn 3 (2 turns after the cast): allowed again.
	caster.CastHistory.onNewTurn()
	f.tableTurn = 3
	if !f.castSpellByFighter(caster, 200, victim.Pos) {
		t.Error("recast after the interval elapsed should succeed")
	}
}

// TestSpellCastMaxPerTarget verifies CastMaxPerTarget (field 7) is per-target:
// a spell capped at 1/target can still hit a DIFFERENT enemy the same turn.
func TestSpellCastMaxPerTarget(t *testing.T) {
	sp := &gamedata.Spell{ID: 201, AP: 1, RangeMax: 6, CastMaxPerTarget: 1,
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6}
	v1 := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 70, MaxHP: 70}
	v2 := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 70, MaxHP: 70}
	f := castFreqFight(caster, []*FightFighter{v1, v2}, sp)

	if !f.castSpellByFighter(caster, 201, v1.Pos) {
		t.Fatal("first cast on v1 should succeed")
	}
	if f.castSpellByFighter(caster, 201, v1.Pos) {
		t.Error("second cast on the SAME target should be blocked (CastMaxPerTarget=1)")
	}
	if !f.castSpellByFighter(caster, 201, v2.Pos) {
		t.Error("cast on a DIFFERENT target should succeed (per-target cap)")
	}
}

// TestSpellCastHistoryOncePerFight verifies Cooldown==63 blocks any recast
// for the rest of the fight (the ≥63-is-infinite convention).
func TestSpellCastHistoryOncePerFight(t *testing.T) {
	var h spellCastHistory
	// First cast at turn 1: allowed, then recorded.
	if !h.canCast(9, 63, 0, 0, 1, 0, false) {
		t.Fatal("first cast should be allowed")
	}
	h.storeCast(9, 63, 0, 0, 1, 0, false)
	// Even 100000 turns later it's still blocked.
	if h.canCast(9, 63, 0, 0, 100000, 0, false) {
		t.Error("minInterval=63 should block for the whole fight")
	}
}
