package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestDisconnectGraceHoldsFightForResume drives TWO real coaches into a real
// matchmade fight, then drops one coach's TCP connection mid-fight. It asserts the
// 2.70 behaviour end-to-end over the wire:
//
//   - a bare disconnect does NOT instantly end the fight — it is held open on the
//     reconnect grace (the 2.70 client can resume), so the opponent gets no
//     instant END_FIGHT; and
//   - when the dropped coach returns to the world the server OFFERS RESUME (pushes
//     the reconnect question 26333) rather than forfeiting, so the fight is still
//     held open and the opponent still gets no END_FIGHT. (Accepting the resume is
//     covered by TestReconnectResumeFight; declining/forfeiting by
//     TestReconnectDeclineForfeits.)
//
// This is the two-coach path the single-client GUI/practice harness cannot
// exercise (a practice fight has no second real coach, and the MCP harness has no
// close-client-without-killing-the-server control).
func TestDisconnectGraceHoldsFightForResume(t *testing.T) {
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(the disconnect/resume logic itself is unit-tested race-clean in internal/game)")
	}
	addr := testServer(t)
	a, b := startFightForCombat(t, addr) // c1 = A, c2 = B, both in the action phase
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	// A drops its connection. The fight must stay alive (grace period), so B is
	// NOT handed an instant victory.
	_ = a.Close()
	if f, _, err := b.WaitFor(testclient.OpEndFight, 700*time.Millisecond); err == nil {
		t.Fatalf("a bare disconnect must not instantly end the fight (grace period); got END_FIGHT op=%d", f.Opcode)
	}

	// A returns to the world (same account/coach). The server offers resume
	// (26333) instead of forfeiting, so the fight stays open and B still gets no
	// END_FIGHT.
	a2, _ := dialLogin(t, addr, "c1", "Combatant1")
	if _, _, err := a2.WaitFor(opReconnectQuestion, testclient.DefaultTimeout); err != nil {
		t.Fatalf("returning coach should be offered resume (26333): %v", err)
	}
	if f, _, err := b.WaitFor(testclient.OpEndFight, 500*time.Millisecond); err == nil {
		t.Fatalf("fight must stay open for resume, not end on return; opponent got END_FIGHT op=%d", f.Opcode)
	}
}
