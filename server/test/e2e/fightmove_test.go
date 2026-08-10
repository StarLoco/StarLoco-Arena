package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestFighterMoveInFight: on a team-A fighter's turn, a move request (4503)
// is echoed back as a FIGHTER_MOVE (4524) broadcast and debits MP (8120 id 92).
func TestFighterMoveInFight(t *testing.T) {
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(server logic itself is race-clean -- internal/game passes -race)")
	}
	addr := testServer(t)
	a, b := startFightForCombat(t, addr)

	// Keep B drained so its write queue never back-pressures the fight actor.
	stopB := make(chan struct{})
	go func() {
		for {
			select {
			case <-stopB:
				return
			default:
				b.DrainReceived(30 * time.Millisecond)
			}
		}
	}()
	defer close(stopB)

	var sawMove, sawMPUse bool

	for attempt := 0; attempt < 4 && !sawMove; attempt++ {
		mover, err := a.WaitForTurn(15 * time.Second)
		if err != nil {
			t.Fatalf("no turn (attempt %d): %v", attempt, err)
		}
		// Try the move for whichever fighter holds the turn, stepping from THAT
		// fighter's own start cell. A request for a fighter we do not own is a
		// silent server-side no-op, so we need no assumption about which side we
		// drew — see the note in combat_test.go: side 0 is just whoever hit the
		// matchmaker queue first, which this harness does not serialise.
		//
		// The retail client's 4503 path is the STEP cells only, EXCLUDING the
		// fighter's origin cell: [i64 fighterId]{i32 x,i32 y,u16 z}×N. One step to
		// an adjacent cell is a 1-cell move = 1 MP (path[0] must be adjacent to the
		// origin). The server prepends the origin when it echoes FIGHTER_MOVE (4524).
		step := oneStepFromStart(mover)
		p := testclient.NewW().I64(mover).
			I32(step.x).I32(step.y).U16(uint16(step.z)).
			Bytes()
		_ = a.Send(3, testclient.OpFighterMoveInFightReq, p)

		deadline := time.Now().Add(4 * time.Second)
	collect:
		for time.Now().Before(deadline) {
			f, err := a.Recv(time.Until(deadline))
			if err != nil {
				break
			}
			switch f.Opcode {
			case testclient.OpFighterMoveInFight:
				sawMove = true
			case testclient.OpRunningEffect:
				if testclient.ParseRunningEffect(f).EffectID == 92 { // MP-use
					sawMPUse = true
				}
			case 8200: // ActionSequenceExecute = end of the action group
				break collect
			}
		}
		_ = a.EndTurn(mover)
	}

	if !sawMove {
		t.Fatal("no FIGHTER_MOVE (4524) broadcast after move request")
	}
	if !sawMPUse {
		t.Error("no MP-use RUNNING_EFFECT (id 92)")
	}
}

// TestPlacementMove: during the PLACEMENT phase, a MoveToPlacementReq(8021) for
// one's own fighter onto one of its own side's start cells is echoed to both
// clients as MoveToFreePlacement(8022) carrying the requested cell.
//
// This test used to reach the ACTION phase and place from there, with a comment
// noting that "the placement handler has no phase guard". That was the bug, not
// a fixture detail: 8021 outside the placement phase was a free teleport to any
// coordinate on the map, at no MP cost, ignoring rooting, tackle, traps and line
// of sight. It now asserts the contract instead of the defect.
func TestPlacementMove(t *testing.T) {
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(server logic itself is race-clean -- internal/game passes -race)")
	}
	st, addr := testServerWithStore(t)

	// Real fighters, so the wire id is derivable: the server allocates
	// FighterWireIDBase + fighterDbId*16 + side*8 + index (handlers_fight.go).
	// Which SIDE we drew is not knowable here - side 0 is merely whoever reached
	// the matchmaker first - so both candidates are probed and the 8022 echo
	// identifies the real one, exactly as the old probe did.
	var ids []uint
	a, b := matchIntoFight(t, addr, func(c *testclient.Client, coachID int64) {
		ids = append(ids, createFighter(t, c, st, uint(coachID), "Placer", 8))
	})
	if len(ids) != 2 {
		t.Fatalf("created %d fighters, want 2", len(ids))
	}

	stopB := make(chan struct{})
	go func() {
		for {
			select {
			case <-stopB:
				return
			default:
				b.DrainReceived(30 * time.Millisecond)
			}
		}
	}()
	defer close(stopB)

	// Presentation -> PLACEMENT. Stop here: this is the only phase in which
	// 8021 is legal.
	readyGate(a, b, testclient.OpReadyForPlacement)

	const wireBase = int64(1) << 40
	// A start cell of each side that is NOT that side's first cell (where the
	// single fighter is seeded), from practiceArena in internal/game/arena.go.
	dest := map[int64]cell{
		0: {13, 16, 0}, // team0[7]
		1: {13, 3, 0},  // team1[7]
	}

	var mover int64
	var want cell
	var f *testclient.Frame
	for side := int64(0); side < 2 && f == nil; side++ {
		mover = wireBase + int64(ids[0])*16 + side*8
		want = dest[side]
		p := testclient.NewW().I64(mover).I32(want.x).I32(want.y).U16(uint16(want.z)).Bytes()
		_ = a.Send(3, testclient.OpMoveToPlacementReq, p)
		f, _, _ = a.WaitFor(testclient.OpMoveToFreePlacement, 2*time.Second)
	}
	if f == nil {
		t.Fatal("no MoveToFreePlacement (8022) for a legal placement of our own fighter")
	}
	// Layout: [i64 fighterId][i32 x][i32 y][u16 z].
	r := testclient.NewR(f.Payload)
	if id := r.I64(); id != mover {
		t.Errorf("placement fighter id = %d, want %d", id, mover)
	}
	if x := r.I32(); x != want.x {
		t.Errorf("placement x = %d, want %d", x, want.x)
	}
	if y := r.I32(); y != want.y {
		t.Errorf("placement y = %d, want %d", y, want.y)
	}
}

// TestPlacementRejectsIllegalCellsAndPhases pins the guard itself. Each of these
// was silently accepted before, and the last two are the ones that mattered:
// stacking two fighters on one cell corrupts targeting, tackle and line of sight
// for the whole fight, and placing outside the placement phase is a teleport.
func TestPlacementRejectsIllegalCellsAndPhases(t *testing.T) {
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown")
	}
	st, addr := testServerWithStore(t)
	var ids []uint
	a, b := matchIntoFight(t, addr, func(c *testclient.Client, coachID int64) {
		ids = append(ids, createFighter(t, c, st, uint(coachID), "Placer", 8))
	})

	stopB := make(chan struct{})
	go func() {
		for {
			select {
			case <-stopB:
				return
			default:
				b.DrainReceived(30 * time.Millisecond)
			}
		}
	}()
	defer close(stopB)

	readyGate(a, b, testclient.OpReadyForPlacement)

	const wireBase = int64(1) << 40
	// Identify our fighter + side via a legal placement (the 8022 echo proves it).
	dest := map[int64]cell{0: {13, 16, 0}, 1: {13, 3, 0}}
	var mover int64
	var side int64
	var ok bool
	for s := int64(0); s < 2 && !ok; s++ {
		id := wireBase + int64(ids[0])*16 + s*8
		d := dest[s]
		p := testclient.NewW().I64(id).I32(d.x).I32(d.y).U16(uint16(d.z)).Bytes()
		_ = a.Send(3, testclient.OpMoveToPlacementReq, p)
		if f, _, _ := a.WaitFor(testclient.OpMoveToFreePlacement, 2*time.Second); f != nil {
			mover, side, ok = id, s, true
		}
	}
	if !ok {
		t.Fatal("could not place our own fighter legally; the rest of this test cannot run")
	}
	a.DrainReceived(150 * time.Millisecond)

	enemy := dest[1-side] // a start cell belonging to the OTHER side

	for _, tc := range []struct {
		name string
		c    cell
	}{
		{"off-map", cell{-5, -5, 0}},
		{"far outside any start area", cell{2, 9, 0}},
		{"scenery/void", cell{0, 0, 0}},
		{"the ENEMY's start cell", enemy},
	} {
		p := testclient.NewW().I64(mover).I32(tc.c.x).I32(tc.c.y).U16(uint16(tc.c.z)).Bytes()
		_ = a.Send(3, testclient.OpMoveToPlacementReq, p)
		if f, _, _ := a.WaitFor(testclient.OpMoveToFreePlacement, 700*time.Millisecond); f != nil {
			t.Errorf("%s (%d,%d) was accepted as a placement cell", tc.name, tc.c.x, tc.c.y)
		}
	}

	// And once the fight leaves the placement phase, 8021 must do nothing at all.
	readyGate(a, b, testclient.OpReadyForObservation)
	_ = a.Send(3, testclient.OpReadyForAction, nil)
	_ = b.Send(3, testclient.OpReadyForAction, nil)
	if _, _, err := a.WaitFor(testclient.OpStartAction, testclient.DefaultTimeout); err != nil {
		t.Fatalf("never reached the action phase: %v", err)
	}
	a.DrainReceived(200 * time.Millisecond)

	back := dest[side]
	p := testclient.NewW().I64(mover).I32(back.x).I32(back.y).U16(uint16(back.z)).Bytes()
	_ = a.Send(3, testclient.OpMoveToPlacementReq, p)
	if f, _, _ := a.WaitFor(testclient.OpMoveToFreePlacement, time.Second); f != nil {
		t.Error("8021 was honoured during the ACTION phase: that is a free teleport")
	}
}
