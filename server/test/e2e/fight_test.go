package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// reachWorld logs a client fully into the world (drains the post-login burst).
func reachWorld(t *testing.T, c *testclient.Client) {
	t.Helper()
	if _, _, err := c.WaitFor(testclient.OpEnterInstance, testclient.DefaultTimeout); err != nil {
		t.Fatalf("reach world: %v", err)
	}
	c.DrainReceived(200 * time.Millisecond) // flush inventory/fighter/team pushes
}

// TestTwoClientsMatchAndCreateFight: two coaches search, get matched, both
// accept, and both receive CREATE_FIGHT(8000).
func TestTwoClientsMatchAndCreateFight(t *testing.T) {
	addr := testServer(t)

	a, _ := dialLogin(t, addr, "p1", "Player1")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "p2", "Player2")
	reachWorld(t, b)

	// Both search (mode 1). Empty roster -> server synthesizes a placeholder.
	search := testclient.NewW().U16(1).U16(0).I32(0).Bytes()
	if err := a.Send(2, testclient.OpSearch, search); err != nil {
		t.Fatal(err)
	}
	if err := b.Send(2, testclient.OpSearch, search); err != nil {
		t.Fatal(err)
	}

	// Both should receive MatchFound(23110).
	fa, _, err := a.WaitFor2(testclient.OpMatchFound, testclient.OpMatchFound, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("client A no match: %v", err)
	}
	if _, _, err := b.WaitFor2(testclient.OpMatchFound, testclient.OpMatchFound, testclient.DefaultTimeout); err != nil {
		t.Fatalf("client B no match: %v", err)
	}

	// Read the match id from A's MatchFound: [i64 matchId]...
	matchID := testclient.NewR(fa.Payload).I64()

	// Both accept (23114): [i64 matchId][i64 oppId][i16 mode][i16 ft][i32 N][i8 accept]
	accept := func(c *testclient.Client) {
		p := testclient.NewW().I64(matchID).I64(0).U16(1).U16(1).I32(0).U8(1).Bytes()
		if err := c.Send(2, testclient.OpMatchAccept, p); err != nil {
			t.Fatal(err)
		}
	}
	accept(a)
	accept(b)

	// Both should receive CREATE_FIGHT(8000).
	if _, _, err := a.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("client A no CREATE_FIGHT: %v", err)
	}
	if _, _, err := b.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("client B no CREATE_FIGHT: %v", err)
	}
}

// TestFullFightToVictory drives the whole fight: both clients ready through the
// phases, one gives up, and both receive END_FIGHT(8300).
func TestFullFightToVictory(t *testing.T) {
	addr := testServer(t)

	a, _ := dialLogin(t, addr, "f1", "Fighter1")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "f2", "Fighter2")
	reachWorld(t, b)

	search := testclient.NewW().U16(1).U16(0).I32(0).Bytes()
	_ = a.Send(2, testclient.OpSearch, search)
	_ = b.Send(2, testclient.OpSearch, search)

	fa, _, err := a.WaitFor(testclient.OpMatchFound, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no match: %v", err)
	}
	_, _, _ = b.WaitFor(testclient.OpMatchFound, testclient.DefaultTimeout)
	matchID := testclient.NewR(fa.Payload).I64()

	acc := testclient.NewW().I64(matchID).I64(0).U16(1).U16(1).I32(0).U8(1).Bytes()
	_ = a.Send(2, testclient.OpMatchAccept, acc)
	_ = b.Send(2, testclient.OpMatchAccept, acc)

	// Both drain up to CREATE_FIGHT + presentation.
	_, _, _ = a.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout)
	_, _, _ = b.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// Phase gates: ready-for-placement, observation, action (both coaches).
	gate := func(op uint16) {
		_ = a.Send(3, op, nil)
		_ = b.Send(3, op, nil)
		a.DrainReceived(120 * time.Millisecond)
		b.DrainReceived(120 * time.Millisecond)
	}
	gate(testclient.OpReadyForPlacement)
	gate(testclient.OpReadyForObservation)

	// After both ready-for-action, the server starts the action phase + first
	// turn. Then coach A gives up -> both get END_FIGHT.
	_ = a.Send(3, testclient.OpReadyForAction, nil)
	_ = b.Send(3, testclient.OpReadyForAction, nil)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// Clear any buffered frames (incl. the login-time wallet sync) so the
	// post-fight reward WalletUpdate is unambiguous.
	b.DrainReceived(150 * time.Millisecond)

	_ = a.Send(3, testclient.OpGiveUp, nil)

	// The forfeiter (A) just gets END_FIGHT (no reward).
	if _, _, err := a.WaitFor(testclient.OpEndFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("client A no END_FIGHT: %v", err)
	}
	// The winner (B, who did not forfeit) is credited tokens and receives a
	// WalletUpdate(4001) reward, which is broadcast BEFORE the END_FIGHT result —
	// so wait for the reward first, then the END_FIGHT.
	if _, _, err := b.WaitFor(testclient.OpWalletUpdate, testclient.DefaultTimeout); err != nil {
		t.Fatalf("winner B no WalletUpdate(4001) reward: %v", err)
	}
	if _, _, err := b.WaitFor(testclient.OpEndFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("client B no END_FIGHT: %v", err)
	}
}
