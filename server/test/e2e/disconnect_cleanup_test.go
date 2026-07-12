package e2e

import (
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/protocol"
)

// TestE2E_DisconnectCancelsMatchmakingSearch verifies that a coach who
// disconnects while queued in matchmaking is actually removed from the
// waiting-opponent queue, so a subsequent search from another coach
// doesn't spuriously match against the stale entry (see
// docs/08-java-parity-roadmap.md §8.3.1).
func TestE2E_DisconnectCancelsMatchmakingSearch(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")

	// Alice starts searching for an opponent and is queued (no match yet).
	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)

	// Alice disconnects while still queued.
	cAlice.conn.Close()

	// Give the server a moment to process the disconnect (the close
	// callback runs on the connection's own read-loop goroutine).
	waitForCoachOffline(t, a, "Alice")

	// Bob now searches with the same type/bet. If Alice's stale queue
	// entry wasn't cleaned up, Bob would immediately "match" against a
	// coach who is no longer online -- instead, Bob should just be queued
	// (SendOpponentSearchInProgress), not matched (SendOpponentFound).
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)
}

// TestE2E_DisconnectMidDuelSetupNotifiesOpponent verifies that when one
// coach disconnects after being matched (OPPONENT_FOUND) but before both
// sides are ready, the other coach receives
// FIGHT_CREATION_CANCELED_MESSAGE with CancelReasonTargetDisconnected, and
// the duel is torn down server-side (see
// docs/08-java-parity-roadmap.md §8.3.1).
func TestE2E_DisconnectMidDuelSetupNotifiesOpponent(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)

	// Alice disconnects before either side sends SET_READY_FOR_FIGHT.
	cAlice.conn.Close()

	// Bob should be told the fight was canceled because the target
	// disconnected.
	canceled := cBob.expectOpcode(protocol.SendFightCreationCanceledMessage)
	r := newPayloadReader(canceled)
	r.int64() // fightId
	reason := r.byte_()
	if reason != 35 { // CancelReasonTargetDisconnected
		t.Errorf("cancel reason = %d, want 35 (CancelReasonTargetDisconnected)", reason)
	}
}

// TestE2E_DisconnectMidItemExchangeNotifiesOpponent verifies that when one
// coach disconnects mid-trade, the other coach receives ITEM_EXCHANGE_END
// and the exchange is torn down server-side (see
// docs/08-java-parity-roadmap.md §8.3.1).
func TestE2E_DisconnectMidItemExchangeNotifiesOpponent(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	bobCoachID := cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	cAlice.send(5, protocol.RecvItemExchangeInvitationRequest, putInt64(bobCoachID))
	cAlice.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	cBob.expectOpcode(protocol.SendItemExchangeInvitationRequest)

	// Alice disconnects mid-trade (before either side accepts/readies up).
	cAlice.conn.Close()

	endBob := cBob.expectOpcode(protocol.SendItemExchangeEnd)
	if len(endBob) == 0 {
		t.Error("Bob should receive a non-empty ITEM_EXCHANGE_END after Alice disconnects")
	}
}

// waitForCoachOffline polls the world registry until the named coach is no
// longer present (or a deadline elapses), used to deterministically wait
// for a disconnect's async cleanup to complete after closing a raw socket
// (HandleDisconnect runs on the connection's own read-loop goroutine, so
// there's no synchronous signal back to the test client on close).
func waitForCoachOffline(t *testing.T, a *app.App, name string) {
	t.Helper()
	deadline := time.Now().Add(5 * time.Second)
	for time.Now().Before(deadline) {
		if _, ok := a.Deps.World.GetByName(name); !ok {
			return
		}
		time.Sleep(10 * time.Millisecond)
	}
	t.Fatalf("coach %q still in world registry after disconnect", name)
}
