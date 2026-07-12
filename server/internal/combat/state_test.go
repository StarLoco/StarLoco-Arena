package combat

import (
	"math/rand"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// Tests for the STATE_APPLY state-bundle system (state.go). The registry is
// empty by default (no state data exists in this project), so these tests
// register states explicitly to exercise the expansion path.

func TestStateUniqueID_MatchesReferenceFormula(t *testing.T) {
	// State.getUniqueIdFromBasicInformation: (baseId << 8) + level.
	cases := []struct {
		baseID int16
		level  uint8
		want   int32
	}{
		{0, 0, 0},
		{1, 0, 256},
		{1, 5, 261},
		{10, 3, 2563},
	}
	for _, c := range cases {
		if got := StateUniqueID(c.baseID, c.level); got != c.want {
			t.Errorf("StateUniqueID(%d,%d) = %d, want %d", c.baseID, c.level, got, c.want)
		}
	}
}

func TestRegisterAndLookupState(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	if _, ok := f.lookupState(1, 0); ok {
		t.Fatalf("expected empty state registry by default")
	}
	s := State{BaseID: 1, Level: 0, Effects: []gamedata.EffectDef{{ActionID: 4}}}
	f.RegisterState(s)
	got, ok := f.lookupState(1, 0)
	if !ok {
		t.Fatalf("registered state not found")
	}
	if got.UniqueID() != StateUniqueID(1, 0) {
		t.Errorf("state uniqueID = %d, want %d", got.UniqueID(), StateUniqueID(1, 0))
	}
}

func TestApplyStateBundle_ExpandsSubEffects(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100
	b.Characteristics[MP].Value = 4
	b.Characteristics[MP].Max = 4

	// A state bundling an HP loss (actionID 4, physical) of 10 + an MP use
	// (actionID 20 -> MP_LOSS) of 1. Registering under (baseId=7, level=2).
	f.RegisterState(State{
		BaseID: 7, Level: 2,
		Effects: []gamedata.EffectDef{
			{ID: 1, ActionID: 4, Params: []float32{10}}, // HP loss (physical)
			{ID: 2, ActionID: 20, Params: []float32{1}}, // MP loss
		},
	})

	// STATE_APPLY (actionID 112) with params [baseId=7, level=2].
	eff := gamedata.EffectDef{ActionID: 112, Params: []float32{7, 2}}
	f.applyStateBundle(a, b, eff, -1)

	if got := b.Characteristic(HP); got != 90 {
		t.Errorf("target HP after state HP-loss(10) = %d, want 90", got)
	}
	if got := b.Characteristic(MP); got != 3 {
		t.Errorf("target MP after state MP-loss(1) = %d, want 3", got)
	}
}

func TestApplyStateBundle_UnregisteredStateIsBroadcastOnlyNoOp(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// No state registered -> the (universal, real-data) case: no HP change,
	// just a broadcast acknowledgement.
	eff := gamedata.EffectDef{ActionID: 112, Params: []float32{99, 0}}
	f.applyStateBundle(a, b, eff, -1)

	if got := b.Characteristic(HP); got != 100 {
		t.Errorf("target HP after unregistered STATE_APPLY = %d, want unchanged 100", got)
	}
}

func TestApplyStateBundle_WrongParamCountNoOps(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.RegisterState(State{BaseID: 1, Level: 0, Effects: []gamedata.EffectDef{{ActionID: 4, Params: []float32{50}}}})
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// One param (should be two) -> ApplyState.computeValue default branch:
	// error + no-op, no expansion.
	eff := gamedata.EffectDef{ActionID: 112, Params: []float32{1}}
	f.applyStateBundle(a, b, eff, -1)
	if got := b.Characteristic(HP); got != 100 {
		t.Errorf("target HP after malformed STATE_APPLY = %d, want unchanged 100 (no-op)", got)
	}
}

func TestApplyStateBundle_TriggeredSubEffectIsDeferred(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// A state whose sub-effect is itself a triggered (reactive) effect:
	// applying the state should DEFER it (store on the carrier), not execute
	// it immediately -- mirroring routing through the shared executor.
	f.RegisterState(State{
		BaseID: 3, Level: 1,
		Effects: []gamedata.EffectDef{
			{ID: 1, ActionID: 69, Params: []float32{10}, TriggersAfter: []int32{trigOnAttacked}}, // HP gain on attacked
		},
	})
	eff := gamedata.EffectDef{ActionID: 112, Params: []float32{3, 1}}
	f.applyStateBundle(a, b, eff, -1)

	// The sub-effect was deferred (no immediate HP change), stored on b.
	if got := b.Characteristic(HP); got != 100 {
		t.Errorf("HP after state with triggered sub-effect = %d, want unchanged 100 (deferred)", got)
	}
	if len(b.ReactiveEffects) != 1 {
		t.Fatalf("triggered sub-effect not deferred onto carrier, got %d reactive effects", len(b.ReactiveEffects))
	}
}

func TestApplyStateBundle_EndToEndThroughStateApplyEffectKind(t *testing.T) {
	// Drive the full path via applyRunningEffect (the EffectStateApply case)
	// rather than calling applyStateBundle directly, confirming the wiring.
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100
	f.RegisterState(State{
		BaseID: 5, Level: 0,
		Effects: []gamedata.EffectDef{{ID: 1, ActionID: 4, Params: []float32{25}}},
	})

	def, ok := LookupRunningEffect(112)
	if !ok {
		t.Fatalf("actionID 112 (STATE_APPLY) not resolvable")
	}
	if def.Kind != EffectStateApply {
		t.Fatalf("actionID 112 resolved to kind %v, want EffectStateApply", def.Kind)
	}
	eff := gamedata.EffectDef{ActionID: 112, Params: []float32{5, 0}}
	f.applyRunningEffect(a, b, def, eff, -1)

	if got := b.Characteristic(HP); got != 75 {
		t.Errorf("HP after STATE_APPLY via applyRunningEffect = %d, want 75", got)
	}
}
