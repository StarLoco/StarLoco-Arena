package combat

import (
	"testing"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// targetCondFight builds a 2v2 fight: teamA = {a, ally}, teamB = {enemy}.
func targetCondFight(t *testing.T) (f *Fight, a, ally, enemy *Fighter) {
	t.Helper()
	a = NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	ally = NewFighterFromBreed(2, 1, BreedFeca, "Ally", 0, 0)
	ally.CoachID = 100
	enemy = NewFighterFromBreed(3, 2, BreedCra, "Enemy", 0, 0)
	enemy.CoachID = 200
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a, ally}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{enemy}}}}
	f = NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())
	return f, a, ally, enemy
}

// TestBreedConditionTargeting verifies breed-restricted target conditions
// (e.g. Enutrof's Prime of Life, condition CONDITION_BREED_ENUTROF=262144):
// only a fighter of the required breed passes; other breeds are rejected.
func TestBreedConditionTargeting(t *testing.T) {
	const condBreedEnutrof int32 = 262144 // 1<<18 = CONDITION_BREED_ENUTROF

	enutrof := NewFighterFromBreed(1, 1, BreedEnutrof, "Enu", 0, 0)
	iop := NewFighterFromBreed(2, 1, BreedIop, "Iop", 0, 0)
	summon := NewFighterFromBreed(3, 1, BreedEnutrof, "Summon", 0, 0)
	summon.Father = enutrof // summons have no breed of their own

	if !breedConditionAllows(condBreedEnutrof, enutrof) {
		t.Errorf("Enutrof must pass CONDITION_BREED_ENUTROF")
	}
	if breedConditionAllows(condBreedEnutrof, iop) {
		t.Errorf("Iop must NOT pass CONDITION_BREED_ENUTROF")
	}
	if breedConditionAllows(condBreedEnutrof, summon) {
		t.Errorf("a summon must NOT pass a breed-restricted condition")
	}

	// Combined with IN_AOE (Prime of Life's targets=[262144] is exactly this
	// single condition): only Enutrofs in the area receive the buff.
	f, a, _, _ := targetCondFight(t)
	if f.effectTargetAllowed(a, iop, []int32{condBreedEnutrof}) {
		t.Errorf("Prime-of-Life-style breed condition must reject a non-Enutrof")
	}
	if !f.effectTargetAllowed(a, enutrof, []int32{condBreedEnutrof}) {
		t.Errorf("Prime-of-Life-style breed condition must accept an Enutrof")
	}
}

func TestEffectTargetAllowed_Conditions(t *testing.T) {
	f, a, ally, enemy := targetCondFight(t)

	cases := []struct {
		name    string
		targets []int32
		target  *Fighter
		want    bool
	}{
		{"empty = permissive (enemy)", nil, enemy, true},
		{"empty = permissive (self)", nil, a, true},
		{"IS_ENEMY on enemy", []int32{condIsEnemy}, enemy, true},
		{"IS_ENEMY on ally", []int32{condIsEnemy}, ally, false},
		{"IS_ENEMY on self", []int32{condIsEnemy}, a, false},
		{"IS_CASTER on self", []int32{condIsCaster}, a, true},
		{"IS_CASTER on ally", []int32{condIsCaster}, ally, false},
		{"IS_ALLY on ally (incl self)", []int32{condIsAlly}, ally, true},
		{"IS_ALLY on self", []int32{condIsAlly}, a, true},
		{"IS_ALLY on enemy", []int32{condIsAlly}, enemy, false},
		{"ALLY_EXCEPT_CASTER on ally", []int32{condIsAllyNotSelf}, ally, true},
		{"ALLY_EXCEPT_CASTER on self", []int32{condIsAllyNotSelf}, a, false},
		{"NOT_CASTER on enemy", []int32{condIsNotCaster}, enemy, true},
		{"NOT_CASTER on self", []int32{condIsNotCaster}, a, false},
		{"caster+ally [5] on ally", []int32{condInAOE | condIsAlly}, ally, true},
		{"caster+ally [5] on enemy", []int32{condInAOE | condIsAlly}, enemy, false},
		{"IN_AOE only [1] hits anyone in area", []int32{condInAOE}, enemy, true},
		// OR across conditions: {enemy} OR {self} allows both.
		{"OR enemy-or-self on enemy", []int32{condIsEnemy, condIsCaster}, enemy, true},
		{"OR enemy-or-self on self", []int32{condIsEnemy, condIsCaster}, a, true},
		{"OR enemy-or-self on ally", []int32{condIsEnemy, condIsCaster}, ally, false},
	}
	for _, tc := range cases {
		if got := f.effectTargetAllowed(a, tc.target, tc.targets); got != tc.want {
			t.Errorf("%s: effectTargetAllowed = %v, want %v", tc.name, got, tc.want)
		}
	}
}

// TestEffectTargets_SelfBuffDoesNotHitEnemy verifies the enforcement path
// through executeOneEffect: a self-only (IS_CASTER) buff cast on a cell shared
// by the caster does not leak onto an enemy standing in the same area.
func TestEffectTargets_SelfBuffDoesNotHitEnemy(t *testing.T) {
	f, a, _, enemy := targetCondFight(t)
	f.Timeline = NewTimeline([]*Fighter{a, enemy})
	a.Position = Point3{X: 5, Y: 5}
	enemy.Position = Point3{X: 5, Y: 5} // same cell as caster (overlapping area)
	enemy.Properties = 0

	// A stabilize (property) effect restricted to the caster only.
	eff := gamedata.EffectDef{
		ActionID:  94, // STABILIZE
		AreaShape: int16(AreaCircle), AreaSize: []int32{2},
		Targets: []int32{condIsCaster},
	}
	f.executeOneEffect(a, eff, a.Position, -1)

	if !a.Properties.Has(PropertyStabilized) {
		t.Errorf("caster should be stabilized by a self-targeted effect")
	}
	if enemy.Properties.Has(PropertyStabilized) {
		t.Errorf("enemy must NOT be stabilized by a caster-only effect (target condition not enforced)")
	}
}
