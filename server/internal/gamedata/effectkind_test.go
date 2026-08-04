package gamedata

import (
	"math/rand"
	"testing"
)

func TestEffectKindClassification(t *testing.T) {
	cases := []struct {
		action int32
		want   EffectKind
	}{
		{1, KindDamage}, {5, KindDamage}, {132, KindDamage}, {134, KindDamage},
		{6, KindLeech}, {10, KindLeech},
		{69, KindHeal},
		{125, KindPercentHP},
		{61, KindPoison},
		{63, KindInstantDeath},
		{16, KindAPLoss}, {20, KindMPLoss},
		{85, KindAPSteal}, {103, KindMPSteal},
		{15, KindAPGain}, {19, KindMPGain},
		{39, KindTeleport}, {64, KindSwap}, {37, KindPush}, {38, KindPull},
		{151, KindScaledAP}, {152, KindScaledMP},
		{11, KindBuff}, {13, KindBuff}, {72, KindBuff}, {82, KindBuff}, {123, KindBuff},
		{67, KindSummon}, {75, KindSummon}, {97, KindSummon},
		{65, KindState}, {127, KindState}, {96, KindState}, {94, KindState},
		{57, KindState}, {124, KindState}, {56, KindState}, {111, KindState},
		{62, KindDispel}, {126, KindState}, {139, KindVisual},
		{58, KindCarry}, {59, KindThrow}, {176, KindAura}, {177, KindZoneMPLoss},
		{129, KindDamageTransfer},
		{178, KindLineDamage}, {181, KindLineDamage},
		// Truly unsupported exotics.
		{170, KindUnsupported}, {999, KindUnsupported},
	}
	for _, c := range cases {
		if got := (Effect{ActionID: c.action}).Kind(); got != c.want {
			t.Errorf("Kind(action=%d) = %d, want %d", c.action, got, c.want)
		}
	}
}

func TestEffectRoll(t *testing.T) {
	rng := rand.New(rand.NewSource(1))
	// 0 params -> 0.
	if v := (Effect{}).Roll(rng); v != 0 {
		t.Errorf("empty params roll = %d, want 0", v)
	}
	// 1 param -> fixed.
	if v := (Effect{Params: []float32{25}}).Roll(rng); v != 25 {
		t.Errorf("fixed roll = %d, want 25", v)
	}
	// 3-param dice [2,4,3] -> 2d4+3 ∈ [5,11]; sample many and check the range.
	dice := Effect{Params: []float32{2, 4, 3}}
	min, max := int32(1<<30), int32(-1<<30)
	for i := 0; i < 2000; i++ {
		v := dice.Roll(rng)
		if v < min {
			min = v
		}
		if v > max {
			max = v
		}
	}
	if min < 5 || max > 11 {
		t.Errorf("2d4+3 range = [%d,%d], want within [5,11]", min, max)
	}
	if min != 5 || max != 11 {
		t.Errorf("2d4+3 observed range = [%d,%d], want full [5,11]", min, max)
	}
	// Dice with faces==1 is deterministic (count*1 + mod).
	if v := (Effect{Params: []float32{3, 1, 2}}).Roll(nil); v != 5 {
		t.Errorf("3d1+2 = %d, want 5", v)
	}
}

func TestDurationTurns(t *testing.T) {
	cases := []struct {
		dur      []int32
		want     int32
		infinite bool
	}{
		{nil, 0, false},
		{[]int32{0, 0}, 0, false},
		{[]int32{3, 0}, 3, false},
		{[]int32{63, 0}, 63, true}, // >=63 sentinel = infinite
		{[]int32{0, 100}, 100, true},
	}
	for _, c := range cases {
		turns, inf := (Effect{Duration: c.dur}).DurationTurns()
		if turns != c.want || inf != c.infinite {
			t.Errorf("DurationTurns(%v) = (%d,%v), want (%d,%v)", c.dur, turns, inf, c.want, c.infinite)
		}
	}
}

func TestBuffResource(t *testing.T) {
	cases := []struct {
		action     int32
		res        BuffResource
		sign       int32
		affectsMax bool
		ok         bool
	}{
		{13, BuffAP, +1, true, true},     // AP boost
		{14, BuffAP, -1, true, true},     // AP debuff
		{17, BuffMP, +1, true, true},     // MP boost
		{11, BuffHP, +1, true, true},     // HP boost
		{72, BuffRange, +1, false, true}, // range gain
		{73, BuffRange, -1, false, true}, // range loss
		{82, BuffNone, 0, false, false},  // damage% — pure stat, not a resource
		{999, BuffNone, 0, false, false},
	}
	for _, c := range cases {
		res, sign, am, ok := (Effect{ActionID: c.action}).BuffResource()
		if res != c.res || sign != c.sign || am != c.affectsMax || ok != c.ok {
			t.Errorf("BuffResource(%d) = (%d,%d,%v,%v), want (%d,%d,%v,%v)",
				c.action, res, sign, am, ok, c.res, c.sign, c.affectsMax, c.ok)
		}
	}
}
