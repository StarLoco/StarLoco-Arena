package combat

import (
	"sync"
	"testing"
	"time"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
)

// fakeBroadcaster records every frame sent to each coachID, for assertions
// in fight lifecycle tests. Guarded by a mutex since the Fight actor
// goroutine writes to it concurrently with the test goroutine's reads.
type fakeBroadcaster struct {
	mu   sync.Mutex
	sent map[uint][]protocol.OutboundFrame
}

func newFakeBroadcaster() *fakeBroadcaster {
	return &fakeBroadcaster{sent: make(map[uint][]protocol.OutboundFrame)}
}

func (b *fakeBroadcaster) SendToCoach(coachID uint, frame protocol.OutboundFrame) {
	b.mu.Lock()
	defer b.mu.Unlock()
	b.sent[coachID] = append(b.sent[coachID], frame)
}

func (b *fakeBroadcaster) opcodesFor(coachID uint) []protocol.SendOpcode {
	b.mu.Lock()
	defer b.mu.Unlock()
	var out []protocol.SendOpcode
	for _, f := range b.sent[coachID] {
		out = append(out, f.Opcode)
	}
	return out
}

func (b *fakeBroadcaster) lastFrame(coachID uint, opcode protocol.SendOpcode) (protocol.OutboundFrame, bool) {
	b.mu.Lock()
	defer b.mu.Unlock()
	frames := b.sent[coachID]
	for i := len(frames) - 1; i >= 0; i-- {
		if frames[i].Opcode == opcode {
			return frames[i], true
		}
	}
	return protocol.OutboundFrame{}, false
}

// stopFight forces a fight to end (via forfeit if not already ended), acks
// EndFightDone for every given coachID, and waits for the actor to exit --
// a shared test-cleanup helper since Run() only returns once every coach
// has acked.
func stopFight(t *testing.T, f *Fight, coachIDs ...uint) {
	t.Helper()
	f.Send(cmdGiveUp{CoachID: coachIDs[0]})
	for _, id := range coachIDs {
		f.Send(cmdEndFightDone{CoachID: id})
	}
	select {
	case <-f.Done():
	case <-time.After(2 * time.Second):
		t.Fatalf("fight actor did not exit during test cleanup")
	}
}

func testClocks() Clocks {
	return Clocks{
		Presentation: 50 * time.Millisecond,
		Placement:    50 * time.Millisecond,
		Observation:  50 * time.Millisecond,
		Turn:         2 * time.Second, // long enough to not fire during fast tests
	}
}

func twoTeamFight(t *testing.T, broadcaster Broadcaster) (*Fight, *Fighter, *Fighter) {
	t.Helper()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100

	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 50

	teamA := &Team{ID: 1, Name: "team1", Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Name: "team2", Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, broadcaster, nil, zerolog.Nop())
	return f, a, b
}

// TestFight_ForceCompletesWhenOneCoachNeverAcks is the regression guard for
// the leaked-duel vector: if the fight ends but one coach never sends its
// EndFightDoneMessage(4321) ack (e.g. a MITM drops it while staying
// connected), the fight actor must still exit via the force-complete clock
// so its duel is removed and both players' inventories aren't frozen
// forever.
func TestFight_ForceCompletesWhenOneCoachNeverAcks(t *testing.T) {
	bc := newFakeBroadcaster()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	teamA := &Team{ID: 1, Name: "team1", Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Name: "team2", Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := testClocks()
	clocks.EndFightAck = 100 * time.Millisecond // short so the test is fast
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, bc, nil, zerolog.Nop())

	go f.Run()

	// End the fight via forfeit, then ack ONLY coach 100 (coach 200 never
	// acks -- the attack scenario).
	f.Send(cmdGiveUp{CoachID: 100})
	f.Send(cmdEndFightDone{CoachID: 100})

	select {
	case <-f.Done():
		// Force-complete clock fired and released the fight -- correct.
	case <-time.After(2 * time.Second):
		t.Fatal("fight actor never exited -- leaked-duel force-complete did not fire")
	}
}

func TestFight_PhaseProgressesViaClocksToAction(t *testing.T) {
	bc := newFakeBroadcaster()
	f, _, _ := twoTeamFight(t, bc)

	go f.Run()
	defer stopFight(t, f, 100, 200)

	deadline := time.After(2 * time.Second)
	for f.CurrentPhase() != PhaseAction {
		select {
		case <-deadline:
			t.Fatalf("fight did not reach PhaseAction in time, stuck at %s", f.CurrentPhase())
		case <-time.After(10 * time.Millisecond):
		}
	}

	if _, ok := bc.lastFrame(100, protocol.SendEndPresentation); !ok {
		t.Errorf("expected END_PRESENTATION sent to coach 100")
	}
	if _, ok := bc.lastFrame(100, protocol.SendStartPlacement); !ok {
		t.Errorf("expected START_PLACEMENT sent to coach 100")
	}
	if _, ok := bc.lastFrame(100, protocol.SendStartObservation); !ok {
		t.Errorf("expected START_OBSERVATION sent to coach 100")
	}
	if _, ok := bc.lastFrame(100, protocol.SendStartAction); !ok {
		t.Errorf("expected START_ACTION sent to coach 100")
	}
	if _, ok := bc.lastFrame(100, protocol.SendFighterTurnBegin); !ok {
		t.Errorf("expected FIGHTER_TURN_BEGIN sent to coach 100")
	}
}

func TestFight_PresentationReadyBothCoachesSkipsClock(t *testing.T) {
	bc := newFakeBroadcaster()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := Clocks{
		Presentation: 30 * time.Second, // would time out the test if not skipped by the ready-gate
		Placement:    10 * time.Millisecond,
		Observation:  10 * time.Millisecond,
		Turn:         2 * time.Second,
	}
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	go f.Run()
	defer stopFight(t, f, 100, 200)

	// Fight starts in presentation.
	if f.CurrentPhase() != PhasePresentation {
		t.Fatalf("fight should start in PhasePresentation, got %s", f.CurrentPhase())
	}

	// One coach ready is NOT enough -- must stay in presentation.
	f.Send(cmdCoachReadyPresentation{CoachID: 100})
	time.Sleep(30 * time.Millisecond)
	if f.CurrentPhase() != PhasePresentation {
		t.Fatalf("presentation ended after only ONE coach readied -- should require both, phase=%s", f.CurrentPhase())
	}

	// Both coaches ready -> presentation ends immediately (30s clock skipped).
	f.Send(cmdCoachReadyPresentation{CoachID: 200})

	deadline := time.After(1 * time.Second)
	for f.CurrentPhase() == PhasePresentation {
		select {
		case <-deadline:
			t.Fatalf("presentation did not end after both coaches readied (30s clock should have been skipped)")
		case <-time.After(5 * time.Millisecond):
		}
	}

	// The 8012 ready-ack must have been broadcast for each coach.
	if _, ok := bc.lastFrame(100, protocol.SendTeamMateSetReadyForPlacementMessage); !ok {
		t.Errorf("expected TEAM_MATE_SET_READY_FOR_PLACEMENT (8012) ack broadcast to coach 100")
	}
}

func TestFight_PlacementReadyBothCoachesSkipsClock(t *testing.T) {
	bc := newFakeBroadcaster()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := Clocks{
		Presentation: 10 * time.Millisecond,
		Placement:    30 * time.Second, // would time out the test if not ready-gated
		Observation:  10 * time.Millisecond,
		Turn:         2 * time.Second,
	}
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	go f.Run()
	defer stopFight(t, f, 100, 200)

	// Wait for presentation to end (short clock).
	deadline := time.After(1 * time.Second)
	for f.CurrentPhase() != PhasePlacement {
		select {
		case <-deadline:
			t.Fatalf("did not reach PhasePlacement, stuck at %s", f.CurrentPhase())
		case <-time.After(5 * time.Millisecond):
		}
	}

	// Both coaches signal ready-for-observation; should skip the 30s clock.
	f.Send(cmdCoachReadyObservation{CoachID: 100})
	f.Send(cmdCoachReadyObservation{CoachID: 200})

	deadline2 := time.After(1 * time.Second)
	for f.CurrentPhase() != PhaseAction {
		select {
		case <-deadline2:
			t.Fatalf("did not reach PhaseAction after both-ready, stuck at %s (placement clock should have been skipped)", f.CurrentPhase())
		case <-time.After(5 * time.Millisecond):
		}
	}
}

// TestFight_ManualEndTurnAdvancesImmediately confirms that a fighter
// ending its turn manually (FIGHTER_END_TURN_REQUEST) advances to the next
// fighter's turn RIGHT AWAY -- it does NOT wait out the 30s turn clock.
// (A user reported "if a fighter ends his turn we wait 30s anyway"; the
// 30s they observed was the OPPONENT's fresh turn clock, not the acting
// player's -- this test guards the acting player's immediate advance.)
func TestFight_ManualEndTurnAdvancesImmediately(t *testing.T) {
	bc := newFakeBroadcaster()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100 // A goes first
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 10
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := Clocks{
		Presentation: 10 * time.Millisecond,
		Placement:    10 * time.Millisecond,
		Observation:  10 * time.Millisecond,
		Turn:         30 * time.Second, // long: a manual end-turn must NOT wait this out
	}
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	go f.Run()
	defer stopFight(t, f, 100, 200)

	deadline := time.After(2 * time.Second)
	for f.CurrentFighterID() != a.ID {
		select {
		case <-deadline:
			t.Fatalf("A never got the first turn (current=%d)", f.CurrentFighterID())
		case <-time.After(5 * time.Millisecond):
		}
	}

	// A (coach 100) ends its own turn -> should advance to B within
	// milliseconds, NOT after the 30s clock.
	f.Send(cmdFighterEndTurn{RequesterCoachID: 100, FighterID: a.ID})

	deadline2 := time.After(1 * time.Second) // far under the 30s clock
	for f.CurrentFighterID() != b.ID {
		select {
		case <-deadline2:
			t.Fatalf("B did not get the turn promptly after A's manual end-turn -- the 30s clock should have been skipped (current=%d)", f.CurrentFighterID())
		case <-time.After(5 * time.Millisecond):
		}
	}

	if _, ok := bc.lastFrame(100, protocol.SendFighterTurnEnd); !ok {
		t.Errorf("expected FIGHTER_TURN_END broadcast after A's manual end-turn")
	}
}

// TestFight_EndTurnRejectedForForeignFighter confirms a coach cannot end
// the OPPONENT's fighter's turn (ownership check), and doing so does NOT
// advance the turn.
func TestFight_EndTurnRejectedForForeignFighter(t *testing.T) {
	bc := newFakeBroadcaster()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 10
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := Clocks{
		Presentation: 10 * time.Millisecond,
		Placement:    10 * time.Millisecond,
		Observation:  10 * time.Millisecond,
		Turn:         30 * time.Second,
	}
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	go f.Run()
	defer stopFight(t, f, 100, 200)

	deadline := time.After(2 * time.Second)
	for f.CurrentFighterID() != a.ID {
		select {
		case <-deadline:
			t.Fatalf("A never got the first turn")
		case <-time.After(5 * time.Millisecond):
		}
	}

	// Coach 200 (owner of B) tries to end A's turn -> rejected, turn stays A.
	f.Send(cmdFighterEndTurn{RequesterCoachID: 200, FighterID: a.ID})
	time.Sleep(50 * time.Millisecond)
	if f.CurrentFighterID() != a.ID {
		t.Errorf("coach 200 ended coach 100's fighter's turn -- must have been rejected (current=%d, want A=%d)", f.CurrentFighterID(), a.ID)
	}
}

func TestFight_CloseCombatDealsDamageAndEndsFight(t *testing.T) {
	bc := newFakeBroadcaster()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100
	a.Position = Point3{X: 0, Y: 0}

	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 10
	b.Position = Point3{X: 1, Y: 0}
	// Set B's HP very low so one close-combat hit kills it.
	b.Characteristics[HP].Value = 1
	b.Characteristics[HP].Max = 1

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := Clocks{
		Presentation: 10 * time.Millisecond,
		Placement:    10 * time.Millisecond,
		Observation:  10 * time.Millisecond,
		Turn:         5 * time.Second,
	}
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	f.SetRNGSeed(42)
	go f.Run()

	// Generous deadline: the phase clocks are 10ms each, but under `go test
	// -race` with the whole suite running in parallel the actor goroutine
	// and its timers can be starved for far longer than 2s, so a tight
	// deadline made this occasionally flake (the fight is correct, it just
	// hadn't been scheduled yet). 10s is comfortably above observed
	// worst-case scheduling latency while still failing fast on a real hang.
	deadline := time.After(10 * time.Second)
	for f.CurrentPhase() != PhaseAction {
		select {
		case <-deadline:
			t.Fatalf("did not reach PhaseAction, stuck at %s", f.CurrentPhase())
		case <-time.After(5 * time.Millisecond):
		}
	}

	// A has the higher INIT so goes first. CurrentFighterID() is
	// atomic-backed, safe to read from the test goroutine.
	if got := f.CurrentFighterID(); got != a.ID {
		t.Fatalf("expected A (id=%d) to have first turn, got fighter id=%d", a.ID, got)
	}

	f.Send(cmdCloseCombat{RequesterCoachID: 100, FighterID: a.ID, Target: b.Position})

	// Fight should end since only team A survives once B dies -- wait via
	// the atomic-backed CurrentPhase() rather than reading b.IsDead
	// directly (which the actor goroutine mutates without a lock by
	// design -- see docs/01-architecture.md §1.3).
	deadline3 := time.After(10 * time.Second)
	for f.CurrentPhase() != PhaseEnded {
		select {
		case <-deadline3:
			t.Fatalf("fight did not end after last opponent died (phase stuck at %s)", f.CurrentPhase())
		case <-time.After(5 * time.Millisecond):
		}
	}

	// Now that the actor has fully stopped mutating fighters (Phase ==
	// Ended is only set from inside the actor loop, and no further
	// combat commands mutate state past that point), it's safe to read
	// b.IsDead directly for the final assertion.
	if !b.IsDead {
		t.Errorf("expected B to be dead after the fight ended, HP=%d", b.Characteristic(HP))
	}

	if _, ok := bc.lastFrame(100, protocol.SendEndFight); !ok {
		t.Errorf("expected END_FIGHT sent to coach 100")
	}

	// Ack EndFightDone from both coaches so Run() can return.
	f.Send(cmdEndFightDone{CoachID: 100})
	f.Send(cmdEndFightDone{CoachID: 200})
	select {
	case <-f.Done():
	case <-time.After(1 * time.Second):
		t.Fatalf("fight actor did not exit after EndFightDone acks")
	}
}

func TestFight_ForfeitEndsFightImmediately(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, _ := twoTeamFight(t, bc)
	_ = a

	go f.Run()

	f.Send(cmdGiveUp{CoachID: 100})

	deadline := time.After(1 * time.Second)
	for f.CurrentPhase() != PhaseEnded {
		select {
		case <-deadline:
			t.Fatalf("fight did not end after forfeit")
		case <-time.After(5 * time.Millisecond):
		}
	}

	frame, ok := bc.lastFrame(200, protocol.SendEndFight)
	if !ok {
		t.Fatalf("expected END_FIGHT sent to coach 200 (the non-forfeiting side)")
	}
	// flee byte is right after the 8-byte header.
	if len(frame.Payload) < 9 || frame.Payload[8] != 1 {
		t.Errorf("expected flee=1 in END_FIGHT payload")
	}

	f.Send(cmdEndFightDone{CoachID: 100})
	f.Send(cmdEndFightDone{CoachID: 200})
	select {
	case <-f.Done():
	case <-time.After(1 * time.Second):
		t.Fatalf("fight actor did not exit after EndFightDone acks")
	}
}

// TestFight_NoTurnTrafficAfterEnd is the regression for the "results popup
// shows but the client stays on the fight map" bug. A fighter's action can
// kill the last enemy mid-turn -> endFight(setPhase(Ended)) -> END_FIGHT,
// but control then returns to the caller (e.g. the summon AI's runSummonTurn)
// which calls askForFighterEndTurn -> startNextTurn. Those MUST be no-ops
// once the fight has ended: otherwise the server sends a trailing
// FIGHTER_TURN_END + NEW_TABLE_TURN_BEGIN (opening a new round) AFTER
// END_FIGHT, which desyncs the client's fight teardown.
func TestFight_NoTurnTrafficAfterEnd(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a})
	f.Timeline.StartNextTurn() // a is current
	f.setPhase(PhaseEnded)

	bc := f.broadcaster.(*fakeBroadcaster)
	before := len(bc.opcodesFor(a.CoachID))

	// Both turn-advance entry points must bail immediately on a dead fight.
	f.askForFighterEndTurn(a.ID)
	f.startNextTurn()

	after := len(bc.opcodesFor(a.CoachID))
	if after != before {
		t.Errorf("turn frames were broadcast after the fight ended: %d new frames (want 0 -- no FIGHTER_TURN_END / NEW_TABLE_TURN_BEGIN past END_FIGHT)", after-before)
	}
}

// placeKillerTrap registers a single-cell (AreaPoint) ground-effect area at
// `cell` whose enter-trigger runs EffectDeath (actionID 63), i.e. it kills
// whatever fighter walks onto it -- a minimal in-package stand-in for a
// map-authored trap, used by the mid-move self-death regression test. Note
// the EffectDef's AreaShape is set to AreaPoint too: executeOneEffect
// resolves the effect's targets from eff.AreaShape/AreaSize (not the area's
// own shape), so a zero AreaShape would mis-resolve the victim. A caster is
// required because executeOneEffect reads caster.Position when resolving
// targets (a nil caster nil-derefs -- a separate concern for real
// map-authored ownerless traps, out of scope here).
func placeKillerTrap(f *Fight, cell Point3, caster *Fighter) {
	if f.effectAreas == nil {
		f.effectAreas = newEffectAreaManager()
	}
	f.effectAreas.Add(&EffectArea{
		ID:                  f.nextEffectAreaID(),
		Position:            cell,
		Area:                AreaOfEffect{Shape: AreaPoint},
		Caster:              caster,
		Effects:             []gamedata.EffectDef{{ActionID: 63, AreaShape: int16(AreaPoint)}}, // 63 => EffectDeath
		applicationTriggers: map[EffectAreaTriggerKind]bool{EffectAreaTriggerEnter: true},
		maxExecutionCount:   63, // unlimited (>=63)
	})
}

// TestFight_CurrentFighterDyingMidMoveAdvancesTurn is the regression test
// for the reported hang: when the CURRENT fighter walks onto a trap during
// its own move and dies, the turn must still advance (previously
// handleFighterMove returned without advancing, leaving currentFighter nil
// and every later FIGHTER_END_TURN_REQUEST hitting askForFighterEndTurn's
// non-current guard -- the fight froze on nobody's turn, which also jammed
// the client so a subsequent forfeit's END_FIGHT never got its
// END_FIGHT_DONE ack). It also asserts NO FIGHTER_MOVE is broadcast for the
// now-dead mover (the DIES-then-MOVE desync half of the bug).
func TestFight_CurrentFighterDyingMidMoveAdvancesTurn(t *testing.T) {
	bc := newFakeBroadcaster()

	// A (the mover) goes first and will step onto the trap and die.
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100
	a.Position = Point3{X: 0, Y: 0}
	a.Characteristics[MP].Value = 6 // enough to step onto the trap
	a.Characteristics[MP].Max = 6

	// A2 is A's teammate, kept far away and untouched, so team 1 still has
	// a living fighter after A dies -> the fight does NOT end, forcing the
	// turn-advance path (not the fight-end path) to run.
	a2 := NewFighterFromBreed(3, 1, BreedIop, "A2", 0, 0)
	a2.CoachID = 100
	a2.Characteristics[Init].Value = 50
	a2.Position = Point3{X: 5, Y: 5}

	// B (opponent) is last and also far away.
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 10
	b.Position = Point3{X: 10, Y: 10}

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a, a2}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := Clocks{
		Presentation: 10 * time.Millisecond,
		Placement:    10 * time.Millisecond,
		Observation:  10 * time.Millisecond,
		Turn:         30 * time.Second, // long: recovery must NOT rely on the clock
	}
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	go f.Run()
	defer stopFight(t, f, 100, 200)

	deadline := time.After(2 * time.Second)
	for f.CurrentFighterID() != a.ID {
		select {
		case <-deadline:
			t.Fatalf("A never got the first turn (current=%d)", f.CurrentFighterID())
		case <-time.After(5 * time.Millisecond):
		}
	}

	// Place the killer trap one cell south-east of A (a single-axis fight
	// step: SE = (+1,0)), then have A move onto it. (Safe to touch
	// effectAreas from the test goroutine here: A is idle at the start of
	// its own turn, the actor is blocked waiting on inbox.) The trap's
	// caster is B (any fighter works: EffectDeath has no opponent gate).
	trapCell := Point3{X: 1, Y: 0}
	placeKillerTrap(f, trapCell, b)
	f.Send(cmdFighterMove{RequesterCoachID: 100, FighterID: a.ID, Path: []Point3{trapCell}})

	// The turn must advance to the next living fighter (A2). Previously it
	// stayed stuck with currentFighter == nil.
	deadline2 := time.After(1 * time.Second)
	for f.CurrentFighterID() != a2.ID {
		select {
		case <-deadline2:
			t.Fatalf("turn did not advance to A2 after A died mid-move -- fight is stuck on nobody's turn (current=%d)", f.CurrentFighterID())
		case <-time.After(5 * time.Millisecond):
		}
	}

	// A must have been reported dead, and NO FIGHTER_MOVE may have been
	// broadcast for it (the client already saw FIGHTER_DIES; a trailing
	// MOVE for a dead actor is the desync that jams the pipeline).
	if _, ok := bc.lastFrame(100, protocol.SendFighterDies); !ok {
		t.Errorf("expected FIGHTER_DIES broadcast for A after stepping on the trap")
	}
	if _, ok := bc.lastFrame(100, protocol.SendFighterMove); ok {
		t.Errorf("FIGHTER_MOVE was broadcast for the mover that died mid-move -- must be suppressed")
	}
}
