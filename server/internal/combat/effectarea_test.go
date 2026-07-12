package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase M's
// test coverage for persistent ground-effect areas (traps/glyphs).

func TestApplySetEffectArea_PlacesAreaAtTargetCell(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	f.data = &gamedata.Store{}
	// Manually inject a fake StaticEffectAreaTemplate repository entry
	// via a Repository constructed with a synchronous loader func.
	f.data.StaticEffectAreas = gamedata.NewRepository(func() (map[int32]gamedata.StaticEffectAreaTemplate, error) {
		return map[int32]gamedata.StaticEffectAreaTemplate{
			1: {
				ID:                  1,
				AreaShapeID:         1, // AreaPoint
				MaxExecutionCount:   1,
				ApplicationTriggers: []int32{1},
				Effects: []gamedata.EffectDef{
					{ActionID: 1, Params: []float32{10}}, // EffectHPLoss physical
				},
			},
		}, nil
	})

	eff := gamedata.EffectDef{ActionID: 66, Params: []float32{1}}
	f.executeOneEffect(a, eff, Point3{X: 5, Y: 5}, -1)

	if f.effectAreas == nil || len(f.effectAreas.All()) != 1 {
		t.Fatalf("expected exactly 1 EffectArea placed, got %v", f.effectAreas)
	}
	area := f.effectAreas.All()[0]
	if area.Position != (Point3{X: 5, Y: 5}) {
		t.Errorf("area Position = %v, want {5,5}", area.Position)
	}
	if area.BaseID != 1 {
		t.Errorf("area BaseID = %d, want 1", area.BaseID)
	}
}

func TestApplySetEffectArea_UnknownTemplateIsNoOp(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	f.data = &gamedata.Store{}
	f.data.StaticEffectAreas = gamedata.NewRepository(func() (map[int32]gamedata.StaticEffectAreaTemplate, error) {
		return map[int32]gamedata.StaticEffectAreaTemplate{}, nil
	})

	eff := gamedata.EffectDef{ActionID: 66, Params: []float32{999}}
	f.executeOneEffect(a, eff, Point3{X: 5, Y: 5}, -1)

	if f.effectAreas != nil && len(f.effectAreas.All()) != 0 {
		t.Errorf("expected no area placed for unknown template id, got %v", f.effectAreas.All())
	}
}

func TestCheckInAndOut_EnteringTriggersApplication(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	f.effectAreas = newEffectAreaManager()
	area := &EffectArea{
		ID:                  1,
		Position:            Point3{X: 5, Y: 5},
		Area:                AreaOfEffect{Shape: AreaPoint},
		Caster:              a,
		Effects:             []gamedata.EffectDef{{ActionID: 1, Params: []float32{10}}},
		maxExecutionCount:   1,
		applicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
	}
	f.effectAreas.Add(area)

	// b moves from (4,5) [outside] to (5,5) [inside the trap cell].
	b.Position = Point3{X: 4, Y: 5}
	f.checkInAndOut(Point3{X: 4, Y: 5}, Point3{X: 5, Y: 5}, b)

	if got := b.Characteristic(HP); got != 90 {
		t.Errorf("HP after stepping into trap = %d, want 90", got)
	}
}

func TestCheckInAndOut_StayingInsideDoesNotRetrigger(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	f.effectAreas = newEffectAreaManager()
	area := &EffectArea{
		ID:                  1,
		Position:            Point3{X: 5, Y: 5},
		Area:                AreaOfEffect{Shape: AreaCircle, Size: []int32{2}},
		Caster:              a,
		Effects:             []gamedata.EffectDef{{ActionID: 1, Params: []float32{10}}},
		maxExecutionCount:   63, // unlimited
		applicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
	}
	f.effectAreas.Add(area)

	// Both start and arrival cells are inside the circle (radius 2 around
	// (5,5)) -- moving from (4,5) to (5,4) never LEAVES then re-ENTERS,
	// so no new application trigger should fire.
	f.checkInAndOut(Point3{X: 4, Y: 5}, Point3{X: 5, Y: 4}, b)

	if got := b.Characteristic(HP); got != 100 {
		t.Errorf("HP after moving within the same area (no new entry) = %d, want unchanged 100", got)
	}
}

func TestCheckInAndOut_ExitingTriggersUnapplication(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	f.effectAreas = newEffectAreaManager()
	bc := f.broadcaster.(*fakeBroadcaster)
	_ = bc

	area := &EffectArea{
		ID:                    1,
		Position:              Point3{X: 5, Y: 5},
		Area:                  AreaOfEffect{Shape: AreaPoint},
		Caster:                a,
		maxExecutionCount:     63,
		applicationTriggers:   map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
		unapplicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerExit: true},
	}
	f.effectAreas.Add(area)

	// b moves from (5,5) [inside] to (6,5) [outside].
	f.checkInAndOut(Point3{X: 5, Y: 5}, Point3{X: 6, Y: 5}, b)

	frame, ok := bc.lastFrame(100, protocol.SendEffectAreaAction)
	if !ok {
		t.Fatal("expected an EFFECT_AREA_ACTION broadcast for the exit trigger")
	}
	// apply byte is right after the 8-byte fight-action header.
	if len(frame.Payload) < 9 || frame.Payload[8] != 0 {
		t.Errorf("expected apply=0 in EFFECT_AREA_ACTION payload for an exit trigger")
	}
}

func TestEffectArea_RemovedAfterExecutionCountExhausted(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	f.effectAreas = newEffectAreaManager()
	area := &EffectArea{
		ID:                  1,
		Position:            Point3{X: 5, Y: 5},
		Area:                AreaOfEffect{Shape: AreaPoint},
		Caster:              a,
		Effects:             []gamedata.EffectDef{{ActionID: 1, Params: []float32{1}}},
		maxExecutionCount:   1, // exactly matches real trap id=1's data
		applicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
	}
	f.effectAreas.Add(area)

	f.checkInAndOut(Point3{X: 4, Y: 5}, Point3{X: 5, Y: 5}, b)

	if len(f.effectAreas.All()) != 0 {
		t.Errorf("expected the single-use trap to remove itself after triggering once, got %d areas still active", len(f.effectAreas.All()))
	}
}

func TestEffectArea_UnlimitedExecutionsNeverRemoved(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	f.effectAreas = newEffectAreaManager()
	area := &EffectArea{
		ID:                  1,
		Position:            Point3{X: 5, Y: 5},
		Area:                AreaOfEffect{Shape: AreaCircle, Size: []int32{2}},
		Caster:              a,
		Effects:             []gamedata.EffectDef{{ActionID: 1, Params: []float32{1}}},
		maxExecutionCount:   63, // matches real trap id=2's data (unlimited)
		applicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
	}
	f.effectAreas.Add(area)

	// Trigger it multiple times by stepping in and out repeatedly.
	f.checkInAndOut(Point3{X: 0, Y: 5}, Point3{X: 5, Y: 5}, b) // enter
	f.checkInAndOut(Point3{X: 5, Y: 5}, Point3{X: 0, Y: 5}, b) // exit
	f.checkInAndOut(Point3{X: 0, Y: 5}, Point3{X: 5, Y: 5}, b) // enter again

	if len(f.effectAreas.All()) != 1 {
		t.Errorf("expected the unlimited-execution trap to remain active after multiple triggers, got %d areas", len(f.effectAreas.All()))
	}
}

func TestCheckInAndOut_NoAreasIsNoOp(t *testing.T) {
	f, _, b := newTestFightForEffects(t)
	// f.effectAreas is nil by default -- must not panic.
	f.checkInAndOut(Point3{X: 0, Y: 0}, Point3{X: 1, Y: 0}, b)
}

// TestFog_EnterMakesInvisibleExitRestoresVisibility models Sram's Fog (a
// SET_INVISIBLE effect-area, action 57, enter-trigger + exit-trigger): a
// fighter entering the fog becomes invisible, and LEAVING it becomes visible
// again (the previously-missing exit path -- unapplyEffectArea must reverse
// the invisibility, else a fighter who passed through fog stayed permanently
// invisible server-side).
func TestFog_EnterMakesInvisibleExitRestoresVisibility(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	f.effectAreas = newEffectAreaManager()
	f.effectAreas.Add(&EffectArea{
		ID:                    1,
		Position:              Point3{X: 5, Y: 5},
		Area:                  AreaOfEffect{Shape: AreaCircle, Size: []int32{2}},
		Caster:                a,
		Effects:               []gamedata.EffectDef{{ActionID: 57, Duration: []int32{3, 0}}}, // SET_INVISIBLE
		maxExecutionCount:     63,                                                            // unlimited (fog persists)
		applicationTriggers:   map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
		unapplicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerExit: true},
	})

	// Enter the fog -> invisible.
	f.checkInAndOut(Point3{X: 0, Y: 5}, Point3{X: 5, Y: 5}, b)
	if !b.Properties.Has(PropertyInvisible) {
		t.Fatalf("fighter entering fog should be invisible")
	}
	// Leave the fog -> visible again.
	f.checkInAndOut(Point3{X: 5, Y: 5}, Point3{X: 0, Y: 5}, b)
	if b.Properties.Has(PropertyInvisible) {
		t.Errorf("fighter leaving fog should be visible again (exit must clear invisibility)")
	}
}

func TestEffectArea_HasUnlimitedExecutions(t *testing.T) {
	tests := []struct {
		count int32
		want  bool
	}{
		{0, false},
		{1, false},
		{62, false},
		{63, true},
		{100, true},
		{-1, true}, // matches the reference's exact boundary condition
	}
	for _, tc := range tests {
		a := &EffectArea{maxExecutionCount: tc.count}
		if got := a.hasUnlimitedExecutions(); got != tc.want {
			t.Errorf("hasUnlimitedExecutions() with count=%d = %v, want %v", tc.count, got, tc.want)
		}
	}
}
