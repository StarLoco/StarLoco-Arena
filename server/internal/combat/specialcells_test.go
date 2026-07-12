package combat

import (
	"testing"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/protocol"
)

// TestSpecialCell_TrapDealsDamage is a direct, synchronous unit test of
// applyTurnStartSpecialCell (no Fight actor goroutine involved), avoiding
// any need to poll Fighter state from a different goroutine than the one
// mutating it -- see docs/01-architecture.md §1.3 on Fight's serial-access
// guarantee, which this test respects by never calling f.Run().
func TestSpecialCell_TrapDealsDamage(t *testing.T) {
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Position = Point3{X: 0, Y: 0}
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())
	f.SetSpecialCell(0, 0, SpecialCellTrap)

	beforeHP := a.Characteristic(HP)
	f.applyTurnStartSpecialCell(a, 1)

	if got := beforeHP - a.Characteristic(HP); got != trapDamage {
		t.Errorf("trap damage = %d, want %d", got, trapDamage)
	}
}

func TestSpecialCell_MotivationGrantsAndRevertsAPBonus(t *testing.T) {
	a := NewFighter(1, 1, BreedIop)
	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 6

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())
	f.SetSpecialCell(0, 0, SpecialCellMotivation)

	f.applyTurnStartSpecialCell(a, 1)
	if got := a.Characteristic(AP); got != 7 {
		t.Fatalf("AP after motivation cell = %d, want 7", got)
	}

	f.revertTurnEndSpecialCellBuffs(a)
	if got := a.Characteristic(AP); got != 6 {
		t.Fatalf("AP after end-of-turn revert = %d, want 6", got)
	}
}

func TestSpecialCell_KillerCellKillsFighter(t *testing.T) {
	a := NewFighter(1, 1, BreedIop)
	a.Characteristics[HP].Value = 50
	a.Characteristics[HP].Max = 50

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{NewFighter(2, 2, BreedFeca)}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())
	f.SetSpecialCell(0, 0, SpecialCellKiller)

	died := f.applyTurnStartSpecialCell(a, 1)
	if !died {
		t.Fatalf("expected killer cell to report death")
	}
	if !a.IsDead {
		t.Fatalf("expected fighter to be marked dead by killer cell")
	}
}

func TestSpecialCell_HealingHeartOnlyHealsIfInjured(t *testing.T) {
	a := NewFighter(1, 1, BreedIop)
	a.Characteristics[HP].Max = 50
	a.Characteristics[HP].Value = 50 // full HP, should not be healed

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())
	f.SetSpecialCell(0, 0, SpecialCellHealingHeart)

	f.applyTurnStartSpecialCell(a, 1)
	if got := a.Characteristic(HP); got != 50 {
		t.Fatalf("healing heart on full-HP fighter: HP = %d, want unchanged 50", got)
	}

	a.Characteristics[HP].Value = 40
	f.applyTurnStartSpecialCell(a, 1)
	if got := a.Characteristic(HP); got != 45 {
		t.Fatalf("healing heart on injured fighter: HP = %d, want 45", got)
	}
}

// TestSpecialCell_TurnStartFlushesSoEffectPlaysImmediately is the
// regression for the reported "the cell effect only applies when the fighter
// takes their first action" bug. startNextTurn broadcasts the special-cell
// effect as a QUEUED action (mustBeExecutedNow=false); without a following
// FIGHT_ACTION_SEQUENCE_EXECUTE(8200) flush the client holds it in the
// pending group until the fighter's first action. This asserts the flush is
// emitted (after FIGHTER_TURN_BEGIN) when the fighter starts on a special
// cell.
func TestSpecialCell_TurnStartFlushesSoEffectPlaysImmediately(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	// Only a in the timeline so startNextTurn deterministically selects it.
	f.Timeline = NewTimeline([]*Fighter{a})
	f.setPhase(PhaseAction)
	a.Position = Point3{X: 3, Y: 4}
	a.Characteristics[HP].Value = 50
	a.Characteristics[HP].Max = 50
	f.SetSpecialCell(3, 4, SpecialCellTrap)
	f.AddSpecialCellRender(1003, 3, 4, 0)

	// A summon-less human fighter: startNextTurn arms the turn clock instead
	// of auto-playing, so it returns after the turn-begin + cell handling.
	f.startNextTurn()
	defer f.cancelTurnClock()

	ops := f.broadcaster.(*fakeBroadcaster).opcodesFor(a.CoachID)
	turnBeginIdx, flushIdx := -1, -1
	for i, op := range ops {
		if op == protocol.SendFighterTurnBegin {
			turnBeginIdx = i
		}
		if op == protocol.SendFightActionSequenceExecute && i > turnBeginIdx && turnBeginIdx != -1 {
			flushIdx = i
			break
		}
	}
	if turnBeginIdx == -1 {
		t.Fatalf("no FIGHTER_TURN_BEGIN broadcast; opcodes=%v", ops)
	}
	if flushIdx == -1 {
		t.Fatalf("no FIGHT_ACTION_SEQUENCE_EXECUTE(8200) flush after turn begin -- the trap effect would sit queued until the fighter's first action; opcodes=%v", ops)
	}
	// The trap must have dealt its damage server-side too.
	if got := a.Characteristic(HP); got != 50-trapDamage {
		t.Errorf("trap HP after turn start = %d, want %d", got, 50-trapDamage)
	}
	_ = b
}

// TestSpecialCell_EmitsTileAnimation verifies a triggering special cell
// broadcasts EFFECT_AREA_ACTION(6200) referencing the cell's CREATE_FIGHT
// render id (so the client plays the tile's own scripted animation), BEFORE
// the effect's own running-effect frame. Without a render descriptor at the
// cell (AddSpecialCellRender), no 6200 is sent (nothing for the client to
// resolve).
func TestSpecialCell_EmitsTileAnimation(t *testing.T) {
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Position = Point3{X: 5, Y: 6}
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	bc := newFakeBroadcaster()
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	f.SetSpecialCell(5, 6, SpecialCellTrap)
	// Register the client render descriptor so the animation frame has a
	// concrete cellId to reference.
	f.AddSpecialCellRender(1003, 5, 6, 0)

	f.applyTurnStartSpecialCell(a, 1)

	ops := bc.opcodesFor(100)
	var areaIdx, effIdx = -1, -1
	for i, op := range ops {
		if op == protocol.SendEffectAreaAction && areaIdx == -1 {
			areaIdx = i
		}
		if op == protocol.SendRunningEffectAction && effIdx == -1 {
			effIdx = i
		}
	}
	if areaIdx == -1 {
		t.Fatalf("no EFFECT_AREA_ACTION(6200) broadcast for a triggering special cell; opcodes=%v", ops)
	}
	if effIdx == -1 {
		t.Fatalf("no RUNNING_EFFECT_ACTION(8120) broadcast for the trap damage; opcodes=%v", ops)
	}
	if areaIdx > effIdx {
		t.Errorf("cell animation (6200) sent AFTER the effect (8120): areaIdx=%d effIdx=%d", areaIdx, effIdx)
	}
}

// TestSpecialCell_NoAnimationWithoutRenderDescriptor verifies a cell with a
// gameplay type but no client render descriptor emits no 6200 (the client
// would have nothing registered to resolve the areaId).
func TestSpecialCell_NoAnimationWithoutRenderDescriptor(t *testing.T) {
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Position = Point3{X: 0, Y: 0}
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	bc := newFakeBroadcaster()
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	f.SetSpecialCell(0, 0, SpecialCellTrap) // no AddSpecialCellRender

	f.applyTurnStartSpecialCell(a, 1)

	for _, op := range bc.opcodesFor(100) {
		if op == protocol.SendEffectAreaAction {
			t.Errorf("unexpected EFFECT_AREA_ACTION(6200) with no render descriptor registered")
		}
	}
}

// TestSpecialCellRenders verifies AddSpecialCellRender records the client
// render tuples with an auto-incrementing per-fight CellID, and that
// SpecialCellRenders returns them in order.
func TestSpecialCellRenders(t *testing.T) {
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())

	if got := f.SpecialCellRenders(); got != nil {
		t.Fatalf("SpecialCellRenders on fresh fight = %v, want nil", got)
	}

	f.AddSpecialCellRender(1002, 8, 9, -4)
	f.AddSpecialCellRender(1, 11, 3, -3)

	renders := f.SpecialCellRenders()
	want := []SpecialCellRender{
		{CellBaseID: 1002, CellID: 1, X: 8, Y: 9, Z: -4},
		{CellBaseID: 1, CellID: 2, X: 11, Y: 3, Z: -3},
	}
	if len(renders) != len(want) {
		t.Fatalf("SpecialCellRenders len = %d, want %d", len(renders), len(want))
	}
	for i := range want {
		if renders[i] != want[i] {
			t.Errorf("render[%d] = %+v, want %+v", i, renders[i], want[i])
		}
	}
}
