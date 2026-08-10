package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// The mh_2 "triggerée en zone" family, transcribed from the client's table:
//
//	165 aez_1(fv_1.bam) "Perte de points de vie feu triggerée en zone"
//	166 aez_1(fv_1.ban) ... eau
//	167 aez_1(fv_1.bao) ... air
//	168 aez_1(fv_1.bap) ... terre
//	169 MM()            "Perte de points d'action triggerée en zone"
//	177 vn_1()          "Perte de points de mouvement triggerée en zone"
//
// 177 was implemented; the rest were documented no-ops. They share one shape —
// the spell's own zone, centred on the CASTER, caster excluded — so 169 reuses
// 177's body and 165-168 differ only by the element damageElement returns. 167
// and 168 have no shipped rows but cost nothing to include, and omitting them
// would leave the same silent hole if data ever uses them.

func zoneFight() (*Fight, *FightFighter, *FightFighter, *FightFighter) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	near := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 8, Y: 15}, HP: 60, MaxHP: 60, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	far := &FightFighter{WireID: 3, TeamID: 1, Pos: Pos{X: 12, Y: 15}, HP: 60, MaxHP: 60, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{near, far}},
	}}
	f.deps = &Deps{Log: testLogger(), Fights: NewFightManager()}
	f.setPhase(PhaseAction)
	return f, caster, near, far
}

// TestZoneAPLoss mirrors TestZoneMPLoss: 169 is the AP twin of 177.
func TestZoneAPLoss(t *testing.T) {
	f, caster, near, far := zoneFight()

	f.resolveEffect(caster, gamedata.Effect{ActionID: 169, Params: []float32{2}, AreaShape: 2, AreaSize: []int32{2}}, caster.Pos)

	if near.AP != 4 {
		t.Errorf("near AP = %d, want 4 (lost 2)", near.AP)
	}
	if far.AP != 6 {
		t.Errorf("far AP = %d, want 6 (out of zone)", far.AP)
	}
	if caster.AP != 6 {
		t.Errorf("caster AP = %d, want 6 (excluded from its own zone)", caster.AP)
	}
	if near.MP != 3 {
		t.Errorf("169 drained MP (%d) — it is the AP action", near.MP)
	}
}

// TestZoneAPLossClampsToWhatTheVictimHas: AP can never go negative.
func TestZoneAPLossClampsToWhatTheVictimHas(t *testing.T) {
	f, caster, near, _ := zoneFight()
	near.AP = 1

	f.resolveEffect(caster, gamedata.Effect{ActionID: 169, Params: []float32{5}, AreaShape: 2, AreaSize: []int32{2}}, caster.Pos)

	if near.AP != 0 {
		t.Errorf("near AP = %d, want 0 (clamped, never negative)", near.AP)
	}
}

// TestZoneDamageElementAndFootprint covers all four elemental variants: each
// must damage in-zone enemies, spare out-of-zone ones, spare the caster, and
// resolve through the elemental pipeline (so resistance applies).
func TestZoneDamageElementAndFootprint(t *testing.T) {
	for _, tc := range []struct {
		action int32
		elem   int
		name   string
	}{
		{165, elemFire, "fire"},
		{166, elemWater, "water"},
		{167, elemAir, "air"},
		{168, elemEarth, "earth"},
	} {
		f, caster, near, far := zoneFight()
		if got := damageElement(tc.action); got != tc.elem {
			t.Errorf("action %d: damageElement = %d, want %d (%s)", tc.action, got, tc.elem, tc.name)
		}

		f.resolveEffect(caster, gamedata.Effect{ActionID: tc.action, Params: []float32{10}, AreaShape: 2, AreaSize: []int32{2}}, caster.Pos)

		if near.HP != 50 {
			t.Errorf("action %d (%s): near HP = %d, want 50 (took 10)", tc.action, tc.name, near.HP)
		}
		if far.HP != 60 {
			t.Errorf("action %d (%s): far HP = %d, want 60 (out of zone)", tc.action, tc.name, far.HP)
		}
		if caster.HP != 70 {
			t.Errorf("action %d (%s): caster HP = %d, want 70 (excluded)", tc.action, tc.name, caster.HP)
		}
	}
}

// TestZoneDamageHonoursResistance proves it goes through the ordinary elemental
// pipeline rather than writing HP directly — the reason to reuse
// computeElementalDamage instead of subtracting.
func TestZoneDamageHonoursResistance(t *testing.T) {
	f, caster, near, _ := zoneFight()
	near.Stats.resFlat[elemFire] = 4

	f.resolveEffect(caster, gamedata.Effect{ActionID: 165, Params: []float32{10}, AreaShape: 2, AreaSize: []int32{2}}, caster.Pos)

	if near.HP != 54 {
		t.Errorf("near HP = %d, want 54 (10 fire - 4 flat fire resistance)", near.HP)
	}
}
