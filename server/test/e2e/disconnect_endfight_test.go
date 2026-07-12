package e2e

import (
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/protocol"
)

// TestE2E_DisconnectAfterEndFightSynthesizesAck verifies
// docs/08-java-parity-roadmap.md §8.11 item 9's fix: a coach who
// disconnects AFTER receiving END_FIGHT but BEFORE sending their own
// EndFightDoneMessage(4321) must not leak the Fight actor/Manager entry
// forever. Confirms Manager.Count() drops back to 0 shortly after the
// disconnect, even though only ONE of the two coaches ever explicitly
// acked.
func TestE2E_DisconnectAfterEndFightSynthesizesAck(t *testing.T) {
	a, cAlice, cBob, _, _, _, _ := startFullFight(t)

	if got := a.Deps.Fights.Count(); got != 1 {
		t.Fatalf("active fights before forfeit = %d, want 1", got)
	}

	// Alice forfeits -- both sides receive END_FIGHT.
	cAlice.send(3, protocol.RecvGiveUpFightRequest, nil)
	// The fight-end stats hook pushes a PLAYER_STATISTICS_REPORT (2400) to
	// each coach just before END_FIGHT; drain past it.
	cAlice.drainUntil(protocol.SendEndFight, 4)
	cBob.drainUntil(protocol.SendEndFight, 4)

	// Bob acks normally.
	cBob.send(3, protocol.RecvEndFightDone, nil)

	// Alice disconnects WITHOUT ever sending her own EndFightDoneMessage
	// -- before this fix, the Fight actor would never exit (Bob's ack
	// alone isn't enough; allEndFightDoneAcked() requires every
	// participating coach).
	cAlice.conn.Close()

	waitForFightCount(t, a, 0)
}

// waitForFightCount polls Manager.Count() until it reaches want (or a
// deadline elapses), used to deterministically wait for the Fight actor's
// async teardown after a disconnect/ack.
func waitForFightCount(t *testing.T, a *app.App, want int) {
	t.Helper()
	deadline := time.Now().Add(5 * time.Second)
	for time.Now().Before(deadline) {
		if got := a.Deps.Fights.Count(); got == want {
			return
		}
		time.Sleep(10 * time.Millisecond)
	}
	t.Fatalf("active fights still != %d after deadline (got %d) -- Fight actor leaked", want, a.Deps.Fights.Count())
}
