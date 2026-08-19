package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestBuffBroadcastNamesItsSourceSpell is the reason part 4 exists.
//
// The client's buff bar is built by walking the fighter's running effects and
// SKIPPING every one whose source is not a spell (ee_2.java:562 and :594 both
// require `mi() != null && mi().iP() == 13`). A buff can therefore be perfectly
// applied, ticked and reverted server-side and still show no icon — which is
// exactly what happened, because the server only ever wrote blob parts 0/1/2.
//
// Driven through resolveSpellEffects rather than applyBuff so the test covers the
// plumbing (the spell id being recorded at all), not just the packet builder.
func TestBuffBroadcastNamesItsSourceSpell(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	drain := captureFight(t, f)

	// Action 21 (flat earth resistance) is a plain timed characteristic buff.
	sp := &gamedata.Spell{ID: 447, Effects: []gamedata.Effect{{
		ActionID: 21, EffectID: 900, Params: []float32{15}, Duration: []int32{3},
	}}}
	f.resolveSpellEffects(caster, sp, victim.Pos, false)

	got := runningEffectFrames(t, drain(), 21)
	if len(got) != 1 {
		t.Fatalf("got %d buff broadcasts, want 1", len(got))
	}
	p4, ok := got[0][4]
	if !ok {
		t.Fatal("buff broadcast has no part 4; the client's buff bar drops every " +
			"effect whose mi() is null, so no icon would ever appear")
	}
	if src := be32(p4[0:4]); src != 13 {
		t.Errorf("source type = %d, want 13 (Spell)", src)
	}
	if id := be64(p4[4:12]); id != 447 {
		t.Errorf("source spell = %d, want 447", id)
	}
}

// TestNonSpellEffectHasNoSourceSpell pins the other side: effects that are not
// cast by a spell (poison ticks, special-cell boosts, trap damage) must NOT claim
// one. sourceSpellID is saved and restored around spell resolution precisely so a
// nested effect cannot inherit a stale id after the cast finishes.
func TestNonSpellEffectHasNoSourceSpell(t *testing.T) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	victim := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{victim}},
	}}
	sp := &gamedata.Spell{ID: 447, Effects: []gamedata.Effect{{
		ActionID: 21, EffectID: 900, Params: []float32{15}, Duration: []int32{3},
	}}}
	f.resolveSpellEffects(caster, sp, victim.Pos, false)
	if f.sourceSpellID != 0 {
		t.Fatalf("sourceSpellID = %d after the cast finished, want 0", f.sourceSpellID)
	}

	// A buff applied outside any cast must carry no source spell.
	drain := captureFight(t, f)
	f.applyBuff(caster, gamedata.Effect{
		ActionID: 21, EffectID: 900, Params: []float32{15}, Duration: []int32{3},
	}, victim.Pos)
	got := runningEffectFrames(t, drain(), 21)
	if len(got) != 1 {
		t.Fatalf("got %d broadcasts, want 1", len(got))
	}
	if _, ok := got[0][4]; ok {
		t.Error("an effect applied outside a cast still named a source spell")
	}
}
