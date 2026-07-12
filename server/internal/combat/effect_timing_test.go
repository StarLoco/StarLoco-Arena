package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// These tests pin down the cast-animation -> effect-result TIMING contract
// (see broadcastRunningEffect's doc comment). The client sequences the
// effect result relative to the cast animation ENTIRELY via the spell's Lua
// cast script, but ONLY if the effect is QUEUED (mustBeExecutedNow=false).
// Sending true makes the client run the effect the instant the packet
// arrives -- so the damage/teleport/summon lands while the cast animation
// has barely started (the reported "result already there" bug). AP/MP
// counter debits are the deliberate exception: they apply instantly.

func TestBroadcastRunningEffect_QueuesEffect_MustBeExecutedNowFalse(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	bc := f.broadcaster.(*fakeBroadcaster)

	def := runningEffectDef{Kind: EffectHPGain}
	f.applyRunningEffect(a, b, def, gamedata.EffectDef{ID: 42, ActionID: 69, Params: []float32{5}}, -1)

	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatal("no RUNNING_EFFECT_ACTION broadcast")
	}
	if mustBeExecutedNowOf(t, fr) {
		t.Error("spell effect must be QUEUED (mustBeExecutedNow=false) so the client plays it at the cast script's hit frame, not on arrival")
	}
	// The earlier id/generic-id fixes must remain intact.
	if id := runningEffectIDOf(t, fr); id != 69 {
		t.Errorf("runningEffectId = %d, want 69 (must stay non-zero or the client drops the packet)", id)
	}
	if gid := genericEffectIDOf(t, fr); gid != 42 {
		t.Errorf("GenericEffectID = %d, want 42", gid)
	}
}

func TestTeleportEffect_QueuedNotImmediate(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	bc := f.broadcaster.(*fakeBroadcaster)
	a.Position = Point3{X: 1, Y: 1, Z: 0}

	// TELEPORT (actionID 39) routes through applyTeleport -> broadcast.
	f.executeOneEffect(a, gamedata.EffectDef{ActionID: 39}, Point3{X: 6, Y: 7, Z: 0}, -1)

	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatal("no RUNNING_EFFECT_ACTION broadcast for teleport")
	}
	if mustBeExecutedNowOf(t, fr) {
		t.Error("teleport must be QUEUED so the reposition plays at the cast script's hit frame (e.g. 2000ms for the Feca teleport), not instantly")
	}
	if id := runningEffectIDOf(t, fr); id != 39 {
		t.Errorf("teleport runningEffectId = %d, want 39", id)
	}
}

func TestSummonEffect_QueuedNotImmediate(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	bc := f.broadcaster.(*fakeBroadcaster)

	f.applySummon(a, Point3{X: 9, Y: 9}, gamedata.EffectDef{ActionID: 75}, -1)

	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatal("no RUNNING_EFFECT_ACTION broadcast for summon")
	}
	if mustBeExecutedNowOf(t, fr) {
		t.Error("summon reveal effect must be QUEUED so it plays at the cast script's hit frame (e.g. 850ms for the Sram double), not instantly")
	}
}

func TestDamageEffect_QueuedNotImmediate(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	bc := f.broadcaster.(*fakeBroadcaster)
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// A neutral HP-loss via applyDamageFromEffect (e.g. spell damage path).
	f.applyDamageFromEffect(a, b, 10, gamedata.EffectDef{ID: 1, ActionID: 1}, -1)

	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatal("no RUNNING_EFFECT_ACTION broadcast for damage")
	}
	if mustBeExecutedNowOf(t, fr) {
		t.Error("damage effect must be QUEUED so the damage number lands at the cast script's hit frame, not on arrival")
	}
	if id := runningEffectIDOf(t, fr); id != 1 {
		t.Errorf("damage runningEffectId = %d, want 1", id)
	}
}

func TestCharacUse_APDebit_ExecutesImmediately(t *testing.T) {
	f, _, b := newTestFightForEffects(t)
	bc := f.broadcaster.(*fakeBroadcaster)

	// AP/MP debits are the DELIBERATE exception: they apply instantly
	// (mustBeExecutedNow=true), matching the reference scripts pulling
	// AP-use via executeFirstAction(3,91) on their first line.
	f.broadcastCharacUse(runningEffectAPUse, b, 4)

	fr, ok := lastRunningEffectFrame(t, bc, 100)
	if !ok {
		t.Fatal("no RUNNING_EFFECT_ACTION broadcast for AP-use")
	}
	if !mustBeExecutedNowOf(t, fr) {
		t.Error("AP/MP counter debit should execute immediately (mustBeExecutedNow=true), not be queued")
	}
}
