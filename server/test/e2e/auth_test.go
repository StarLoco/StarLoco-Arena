package e2e

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

func TestE2E_FullLoginFlow_NewCoach(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "password123")

	c := dialTestClient(t, addr)
	coachID := c.mustLogin("alice", "password123", "AliceCoach")
	if coachID == 0 {
		t.Error("expected a non-zero coach ID after creation")
	}
}

func TestE2E_ReturningUserSkipsCoachCreation(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "password123")

	c1 := dialTestClient(t, addr)
	firstCoachID := c1.mustLogin("alice", "password123", "AliceCoach")
	c1.conn.Close()

	c2 := dialTestClient(t, addr)
	code := c2.authenticate("alice", "password123")
	if code != 0 {
		t.Fatalf("second login result code = %d, want 0", code)
	}
	c2.expectOpcode(protocol.SendQueueNotification)
	// Returning user should go straight to COACH_INFORMATION, never
	// COACH_CREATION_REQUEST.
	payload := c2.expectOpcode(protocol.SendCoachInformation)
	r := newPayloadReader(payload)
	secondCoachID := r.int64()

	if secondCoachID != firstCoachID {
		t.Errorf("coach ID changed across logins: %d != %d", firstCoachID, secondCoachID)
	}
}

func TestE2E_WrongPasswordRejected(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "correct")

	c := dialTestClient(t, addr)
	code := c.authenticate("alice", "wrong-password")
	if code != 2 {
		t.Errorf("result code = %d, want 2 (invalid login)", code)
	}
}

func TestE2E_DuplicateLoginRejected(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")

	c1 := dialTestClient(t, addr)
	c1.mustLogin("alice", "pw", "AliceCoach")

	c2 := dialTestClient(t, addr)
	code := c2.authenticate("alice", "pw")
	if code != 3 {
		t.Errorf("second concurrent login result code = %d, want 3 (already connected)", code)
	}
}

func TestE2E_UnknownLoginRejected(t *testing.T) {
	_, addr := startTestServer(t)

	c := dialTestClient(t, addr)
	code := c.authenticate("nobody", "whatever")
	if code != 2 {
		t.Errorf("result code = %d, want 2 (invalid login)", code)
	}
}
