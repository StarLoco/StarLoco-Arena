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

// TestPlacementMove: a MoveToPlacementReq(8021) for one's own fighter is echoed
// to both clients as MoveToFreePlacement(8022) carrying the requested cell.
//
// The placement handler has no phase guard (it just checks fighter ownership),
// so we obtain a real team-A fighter wire id via the action phase and then
// exercise the 8021 -> 8022 wire round-trip.
func TestPlacementMove(t *testing.T) {
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(server logic itself is race-clean -- internal/game passes -race)")
	}
	addr := testServer(t)
	a, b := startFightForCombat(t, addr)

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

	// Find a fighter WE own by probing: the placement handler checks ownership and
	// only echoes 8022 for a fighter belonging to the requesting coach, so a
	// successful echo IS the proof of ownership. Probing beats assuming a side —
	// side 0 is merely whoever reached the matchmaker first (see combat_test.go).
	wantX, wantY := int32(2), int32(9)
	var mover int64
	var f *testclient.Frame
	for attempt := 0; attempt < 4 && f == nil; attempt++ {
		id, err := a.WaitForTurn(15 * time.Second)
		if err != nil {
			t.Fatalf("no turn (attempt %d): %v", attempt, err)
		}
		mover = id
		p := testclient.NewW().I64(mover).I32(wantX).I32(wantY).U16(0).Bytes()
		_ = a.Send(3, testclient.OpMoveToPlacementReq, p)
		f, _, _ = a.WaitFor(testclient.OpMoveToFreePlacement, 2*time.Second)
		if f == nil {
			_ = a.EndTurn(id) // not ours (or refused): let the timeline advance
		}
	}
	if f == nil {
		t.Fatal("no MoveToFreePlacement (8022) for any fighter we own")
	}
	// Layout: [i64 fighterId][i32 x][i32 y][u16 z].
	r := testclient.NewR(f.Payload)
	if id := r.I64(); id != mover {
		t.Errorf("placement fighter id = %d, want %d", id, mover)
	}
	if x := r.I32(); x != wantX {
		t.Errorf("placement x = %d, want %d", x, wantX)
	}
	if y := r.I32(); y != wantY {
		t.Errorf("placement y = %d, want %d", y, wantY)
	}
}
