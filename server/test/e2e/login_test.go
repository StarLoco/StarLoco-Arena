package e2e

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestLoginToWorld: a fresh account logs in, is prompted to create a coach,
// creates one, and reaches the world (EnterInstance 4600).
func TestLoginToWorld(t *testing.T) {
	addr := testServer(t)

	c, err := testclient.Dial(addr)
	if err != nil {
		t.Fatalf("dial: %v", err)
	}
	defer c.Close()

	if err := c.Login("alice", "pw"); err != nil {
		t.Fatalf("login: %v", err)
	}

	coachID, err := c.CreateCoach("Alice")
	if err != nil {
		t.Fatalf("create coach: %v", err)
	}
	if coachID == 0 {
		t.Error("coach id should be non-zero")
	}

	// After 2052 the server pushes lists + stats + EnterInstance(4600).
	if _, _, err := c.WaitFor(testclient.OpEnterInstance, testclient.DefaultTimeout); err != nil {
		t.Fatalf("did not reach world (4600): %v", err)
	}
}

// TestReconnectKeepsCoach: after creating a coach, a second login for the SAME
// account skips creation (existing coach) and goes straight to the world.
func TestReconnectKeepsCoach(t *testing.T) {
	addr := testServer(t)

	// First login: create coach.
	c1, id1 := dialLogin(t, addr, "bob", "Bob")
	if _, _, err := c1.WaitFor(testclient.OpEnterInstance, testclient.DefaultTimeout); err != nil {
		t.Fatalf("first login world: %v", err)
	}
	_ = c1.Close()

	// Second login: same account -> existing coach, no creation prompt.
	c2, err := testclient.Dial(addr)
	if err != nil {
		t.Fatalf("redial: %v", err)
	}
	defer c2.Close()
	if err := c2.Login("bob", "pw"); err != nil {
		t.Fatalf("relogin: %v", err)
	}
	// CreateCoach handles both prompt (2048) and existing (2052); here it must
	// see 2052 directly with the SAME coach id.
	id2, err := c2.CreateCoach("Bob")
	if err != nil {
		t.Fatalf("relogin coach: %v", err)
	}
	if id2 != id1 {
		t.Errorf("coach id changed on reconnect: %d != %d", id2, id1)
	}
}
