package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestReplacedSessionReleasesItsChallenge is the SECURITY regression for the
// duplicate-login state leak.
//
// onClose returned EARLY for a session displaced by a newer login, skipping the
// whole teardown. That is deterministic, not a race: handleAuthentication calls
// Sessions.Swap BEFORE old.kick(), so Sessions.Remove always reports false on
// that path. Every duplicate login therefore leaked the coach into the
// matchmaker, challenges, exchanges and 2v2 pairing, each holding a DEAD socket.
//
// This test covers the CHALLENGE leak specifically, because the matchmaker's own
// ghost purge already masks the queue case - so the queue is not proof that the
// release runs. B must be told its challenge fell through.
func TestReplacedSessionReleasesItsChallenge(t *testing.T) {
	t.Parallel()
	addr := testServer(t)

	a, _ := dialLogin(t, addr, "replace_a", "Repa")
	reachWorld(t, a)
	b, bid := dialLogin(t, addr, "replace_b", "Repb")
	reachWorld(t, b)

	// A challenges B; B sees the invitation.
	_ = a.Send(2, opChallengeInvite, testclient.NewW().I64(bid).U8(0).Bytes())
	if _, _, err := b.WaitFor(opChallengeInvitation, testclient.DefaultTimeout); err != nil {
		t.Fatalf("fixture broken: B never saw the invitation, so nothing is leaked: %v", err)
	}
	b.DrainReceived(200 * time.Millisecond)

	// A logs in AGAIN on a second socket. This displaces session A.
	a2, err := testclient.Dial(addr)
	if err != nil {
		t.Fatalf("dial second session: %v", err)
	}
	t.Cleanup(func() { _ = a2.Close() })
	if err := a2.Login("replace_a", "pw"); err != nil {
		t.Fatalf("second login: %v", err)
	}

	// B must be told the challenge fell through. Before the fix, B waited forever
	// on a coach whose socket was gone, and could not be challenged again.
	if _, _, err := b.WaitFor(opChallengeCancelled, 3*time.Second); err != nil {
		t.Errorf("B was never told its challenge was cancelled after the "+
			"challenger's session was replaced: %v", err)
	}
}
