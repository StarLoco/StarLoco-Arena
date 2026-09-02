package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// Mid-fight reconnect/resume opcodes (server pushes 26333, client answers 26334).
const (
	opReconnectQuestion = 26333 // S2C: empty — "resume your fight?"
	opReconnectAnswer   = 26334 // C2S: [i8 accept] — 1=resume, 0=decline
)

// TestReconnectResumeFight drives two real coaches into a matchmade fight, drops
// one mid-fight, reconnects them, and asserts the full RESUME handshake over the
// wire: on return the server pushes the reconnect QUESTION (26333); the client
// accepts with 26334(1); the server replays the fight presentation (CREATE_FIGHT
// 8000 + the current turn 8104) and the fight keeps going (the opponent gets NO
// END_FIGHT).
func TestReconnectResumeFight(t *testing.T) {
	t.Parallel()
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(the reconnect/resume logic itself is unit-tested race-clean in internal/game)")
	}
	addr := testServer(t)
	a, b := startFightForCombat(t, addr) // c1 = A, c2 = B, both in the action phase
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	// A drops its connection. The fight stays alive on the reconnect grace.
	_ = a.Close()

	// A reconnects (same account/coach). On re-entering the world the server must
	// push the reconnect QUESTION (26333) instead of forfeiting.
	a2, _ := dialLogin(t, addr, "c1", "Combatant1")
	if _, _, err := a2.WaitFor(opReconnectQuestion, testclient.DefaultTimeout); err != nil {
		t.Fatalf("expected reconnect question 26333 on return: %v", err)
	}

	// A accepts: the server replays the fight. Expect CREATE_FIGHT(8000) then the
	// current turn (FIGHTER_TURN_BEGIN 8104) — i.e. the client is driven all the
	// way back to the live action phase.
	_ = a2.Send(2, opReconnectAnswer, []byte{1})
	if _, _, err := a2.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("expected CREATE_FIGHT(8000) resync after accept: %v", err)
	}
	if _, _, err := a2.WaitFor(testclient.OpFighterTurnBegin, testclient.DefaultTimeout); err != nil {
		t.Fatalf("expected FIGHTER_TURN_BEGIN(8104) in the resync: %v", err)
	}

	// The fight must NOT have ended: the opponent gets no END_FIGHT.
	if f, _, err := b.WaitFor(testclient.OpEndFight, 600*time.Millisecond); err == nil {
		t.Fatalf("resume must not end the fight; opponent got END_FIGHT op=%d", f.Opcode)
	}
}

// TestReconnectDeclineForfeits asserts that declining the reconnect (26334=0)
// forfeits the abandoned fight, so the surviving opponent receives END_FIGHT.
func TestReconnectDeclineForfeits(t *testing.T) {
	t.Parallel()
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(the forfeit logic itself is unit-tested race-clean in internal/game)")
	}
	addr := testServer(t)
	a, b := startFightForCombat(t, addr)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Close()

	a2, _ := dialLogin(t, addr, "c1", "Combatant1")
	if _, _, err := a2.WaitFor(opReconnectQuestion, testclient.DefaultTimeout); err != nil {
		t.Fatalf("expected reconnect question 26333: %v", err)
	}

	// A declines -> forfeit -> the opponent wins and receives END_FIGHT(8300).
	_ = a2.Send(2, opReconnectAnswer, []byte{0})
	if _, _, err := b.WaitFor(testclient.OpEndFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("decline should forfeit; opponent should receive END_FIGHT(8300): %v", err)
	}
}
