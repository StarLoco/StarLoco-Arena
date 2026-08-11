package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func maskFight() (*Fight, *FightFighter, *FightFighter, *FightFighter) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6,
		Fighter: &domain.Fighter{BreedID: 8, Spells: []domain.FighterSpell{{SpellID: 900}}}}
	ally := &FightFighter{WireID: 2, TeamID: 0, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60,
		Fighter: &domain.Fighter{BreedID: 3}}
	enemy := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 9, Y: 15}, HP: 60, MaxHP: 60,
		Fighter: &domain.Fighter{BreedID: 3}}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster, ally}},
		{ID: 1, Fighters: []*FightFighter{enemy}},
	}}
	f.Timeline = []*FightFighter{caster, ally, enemy}
	f.turnIndex = 0
	f.tableTurn = 1
	f.setPhase(PhaseAction)
	return f, caster, ally, enemy
}

// TestSpellTargetMaskEnforcement covers the CAST-level target conditions
// (field 22), which the client only applies when EnforceTargetMasks (field 19)
// is set — exactly 3 of the 203 shipped spells.
func TestSpellTargetMaskEnforcement(t *testing.T) {
	f, caster, ally, enemy := maskFight()

	// mask 4 = bit 2 = "is ally" — spell 468's actual mask.
	allyOnly := &gamedata.Spell{ID: 900, AP: 1, RangeMax: 6,
		EnforceTargetMasks: true, TargetMasks: []int64{condIsAlly},
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	f.deps = &Deps{Spells: gamedata.NewSpells(allyOnly), Fights: NewFightManager(), Log: testLogger()}

	if !f.spellTargetValid(caster, allyOnly, ally.Pos) {
		t.Error("an ally-masked spell was refused on an ALLY")
	}
	if f.spellTargetValid(caster, allyOnly, enemy.Pos) {
		t.Error("an ally-masked spell was allowed on an ENEMY")
	}

	// The flag is the gate: the identical mask must be ignored without it.
	unenforced := *allyOnly
	unenforced.EnforceTargetMasks = false
	if !f.spellTargetValid(caster, &unenforced, enemy.Pos) {
		t.Error("masks were enforced on a spell whose EnforceTargetMasks flag is false")
	}
}

// TestSpellTargetMaskAllySummon is spell 83's real mask: bits 2 and 5 together,
// i.e. an ALLIED SUMMON. Both bits must hold, so an ordinary ally fails.
func TestSpellTargetMaskAllySummon(t *testing.T) {
	f, caster, ally, _ := maskFight()
	summon := &FightFighter{WireID: 4, TeamID: 0, Pos: Pos{X: 6, Y: 15}, HP: 30, MaxHP: 30, Father: caster}
	f.Teams[0].Fighters = append(f.Teams[0].Fighters, summon)

	sp := &gamedata.Spell{ID: 901, AP: 1, RangeMax: 6,
		EnforceTargetMasks: true, TargetMasks: []int64{condIsAlly | condIsSummoned},
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	f.deps = &Deps{Spells: gamedata.NewSpells(sp), Fights: NewFightManager(), Log: testLogger()}

	if !f.spellTargetValid(caster, sp, summon.Pos) {
		t.Error("ally+summoned mask refused an allied SUMMON")
	}
	if f.spellTargetValid(caster, sp, ally.Pos) {
		t.Error("ally+summoned mask allowed a non-summon ally (both bits must hold)")
	}
}

// TestSpellTargetMaskUnrepresentableBitStaysPermissive: spell 449's mask is
// bit 62, which in the client's aLc means "the target is a ground effect area" —
// a targeting mode this server does not model. Judging it with the FIGHTER
// evaluator would reject every cast, which is worse than not enforcing it.
//
// The MIXED case is what actually exercises the escape, and it is the one to
// assert on. A pure-unknown mask like spell 449's is permissive either way,
// because targetConditionPasses ignores bits it does not know — so a test using
// only that mask would pass with the escape removed and prove nothing. A mask
// combining a bit we CAN decide with one we cannot is the case where the two
// behaviours diverge: partial enforcement (wrong — half a rule is not the rule)
// versus staying out of it.
func TestSpellTargetMaskUnrepresentableBitStaysPermissive(t *testing.T) {
	f, caster, _, enemy := maskFight()

	// Pure unknown bit (spell 449's actual mask): permissive.
	pure := &gamedata.Spell{ID: 902, AP: 1, RangeMax: 6,
		EnforceTargetMasks: true, TargetMasks: []int64{int64(1) << 62},
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	f.deps = &Deps{Spells: gamedata.NewSpells(pure), Fights: NewFightManager(), Log: testLogger()}
	if !f.spellTargetValid(caster, pure, enemy.Pos) {
		t.Error("a mask of only unrepresentable bits rejected the cast; it must stay permissive")
	}

	// MIXED: "is ally" (decidable) + bit 62 (not). Enforcing only the half we
	// understand would refuse this cast at an ENEMY, which is a rule the client
	// never applied. The whole mask is skipped instead.
	mixed := &gamedata.Spell{ID: 903, AP: 1, RangeMax: 6,
		EnforceTargetMasks: true, TargetMasks: []int64{condIsAlly | (int64(1) << 62)},
		Effects: []gamedata.Effect{{ActionID: 1, Params: []float32{5}}}}
	f.deps = &Deps{Spells: gamedata.NewSpells(mixed), Fights: NewFightManager(), Log: testLogger()}
	if !f.spellTargetValid(caster, mixed, enemy.Pos) {
		t.Error("a MIXED mask was half-enforced; a rule we cannot fully evaluate must not be applied at all")
	}
}

// TestShippedEnforcedMasksAreTheThreeWeExpect is the real-data canary: if a
// future data set enforces masks on more spells, or with bits this evaluator
// cannot decide, this fails loudly rather than silently changing what casts.
func TestShippedEnforcedMasksAreTheThreeWeExpect(t *testing.T) {
	gd := openRealGameData(t)
	spells, err := gd.LoadSpells()
	if err != nil {
		t.Fatalf("LoadSpells: %v", err)
	}
	enforced := map[int32][]int64{}
	for id, sp := range spells.All() {
		if sp.EnforceTargetMasks {
			enforced[id] = sp.TargetMasks
		}
	}
	if len(enforced) != 3 {
		t.Errorf("%d spells enforce target masks, want 3: %v", len(enforced), enforced)
	}
	want := map[int32]int64{
		468: condIsAlly,                  // an ally
		83:  condIsAlly | condIsSummoned, // an allied summon
		449: int64(1) << 62,              // a ground effect area (not modelled)
	}
	for id, w := range want {
		got, ok := enforced[id]
		if !ok {
			t.Errorf("spell %d no longer enforces its target mask", id)
			continue
		}
		if len(got) != 1 || got[0] != w {
			t.Errorf("spell %d mask = %v, want [%d]", id, got, w)
		}
	}
}
