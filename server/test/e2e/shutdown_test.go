package e2e

import (
	"context"
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/protocol"
)

// TestE2E_GracefulShutdownNotifiesConnectedClients verifies that App.Shutdown
// broadcasts WORLD_SERVER_UNAVAILABLE (1026) to every connected coach and then
// closes their connections. Each client should receive the notification frame
// (proving netio's drain-before-close contract flushed it) and then see its
// socket close. Shutdown itself should return nil once every session has torn
// down within the grace period.
func TestE2E_GracefulShutdownNotifiesConnectedClients(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")

	// Bob's login broadcasts an ACTOR_SPAWN to the already-online Alice;
	// drain it so the next frame Alice reads is unambiguously the shutdown
	// notification.
	cAlice.drainOptionalActorSpawn()

	shutdownErr := make(chan error, 1)
	go func() {
		ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
		defer cancel()
		shutdownErr <- a.Shutdown(ctx)
	}()

	// Both clients must receive WORLD_SERVER_UNAVAILABLE (empty payload).
	for _, c := range []*testClient{cAlice, cBob} {
		payload := c.expectOpcode(protocol.SendWorldServerUnavailable)
		if len(payload) != 0 {
			t.Errorf("WORLD_SERVER_UNAVAILABLE payload = %v, want empty", payload)
		}
	}

	// After the notification, each connection should be closed by the
	// server: the next read returns an error (EOF / connection reset).
	for _, c := range []*testClient{cAlice, cBob} {
		_ = c.conn.SetReadDeadline(time.Now().Add(2 * time.Second))
		if _, _, err := c.recvFrame(); err == nil {
			t.Error("expected connection to be closed after shutdown notification, but a frame was read")
		}
	}

	select {
	case err := <-shutdownErr:
		if err != nil {
			t.Fatalf("Shutdown returned error: %v", err)
		}
	case <-time.After(6 * time.Second):
		t.Fatal("Shutdown did not return within the grace period")
	}
}

// TestE2E_GracefulShutdownWithNoClients verifies Shutdown is a clean no-op
// broadcast when nobody is connected: it returns promptly with no error.
func TestE2E_GracefulShutdownWithNoClients(t *testing.T) {
	a, _ := startTestServer(t)

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	if err := a.Shutdown(ctx); err != nil {
		t.Fatalf("Shutdown with no clients returned error: %v", err)
	}
}
