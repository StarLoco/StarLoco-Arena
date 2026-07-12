package dispatch

import (
	"net"
	"sync"
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/world"
)

// TestBuildWorldServerUnavailable verifies the 1026 frame carries the
// expected opcode and an empty payload (the client's
// WorldServerUnavailableMessage reads no fields).
func TestBuildWorldServerUnavailable(t *testing.T) {
	frame := buildWorldServerUnavailable()
	if frame.Opcode != protocol.SendWorldServerUnavailable {
		t.Errorf("opcode = %d, want %d (WORLD_SERVER_UNAVAILABLE)", frame.Opcode, protocol.SendWorldServerUnavailable)
	}
	if len(frame.Payload) != 0 {
		t.Errorf("payload = %v, want empty", frame.Payload)
	}
}

// TestBroadcastShutdownNotifiesEveryOnlineCoach verifies that
// BroadcastShutdown enqueues a WORLD_SERVER_UNAVAILABLE frame to every online
// coach's session and signals each session to close, returning the notified
// count.
//
// It installs a single process-global outbound tracer keyed by both session
// IDs (the tracer is a single global var, so per-session tracers would
// overwrite each other), recording every frame each session Sends.
func TestBroadcastShutdownNotifiesEveryOnlineCoach(t *testing.T) {
	clientA, serverA := net.Pipe()
	defer clientA.Close()
	defer serverA.Close()
	clientB, serverB := net.Pipe()
	defer clientB.Close()
	defer serverB.Close()

	sessionA := netio.NewSession(serverA)
	sessionB := netio.NewSession(serverB)

	var mu sync.Mutex
	sent := map[uint64][]protocol.OutboundFrame{}
	netio.SetOutboundTracer(func(sessionID uint64, frame protocol.OutboundFrame) {
		mu.Lock()
		sent[sessionID] = append(sent[sessionID], frame)
		mu.Unlock()
	})
	defer netio.SetOutboundTracer(nil)

	registry := world.NewRegistry()
	registry.Add(&world.OnlineCoach{Coach: &domain.Coach{ID: 1, Name: "Alice"}, Session: sessionA})
	registry.Add(&world.OnlineCoach{Coach: &domain.Coach{ID: 2, Name: "Bob"}, Session: sessionB})

	deps := &Deps{World: registry}

	notified := BroadcastShutdown(deps)
	if notified != 2 {
		t.Fatalf("notified = %d, want 2", notified)
	}

	for name, session := range map[string]*netio.Session{"Alice": sessionA, "Bob": sessionB} {
		mu.Lock()
		frames := sent[session.ID()]
		mu.Unlock()
		if len(frames) != 1 {
			t.Fatalf("%s: sent %d frames, want exactly 1", name, len(frames))
		}
		if frames[0].Opcode != protocol.SendWorldServerUnavailable {
			t.Errorf("%s: sent opcode %d, want WORLD_SERVER_UNAVAILABLE", name, frames[0].Opcode)
		}
		// Each session must have been signaled to close.
		select {
		case <-session.Done():
		default:
			t.Errorf("%s: session was not closed after BroadcastShutdown", name)
		}
	}
}

// TestBroadcastShutdownNoClients verifies a broadcast with an empty registry
// is a clean no-op returning zero.
func TestBroadcastShutdownNoClients(t *testing.T) {
	deps := &Deps{World: world.NewRegistry()}
	if notified := BroadcastShutdown(deps); notified != 0 {
		t.Errorf("notified = %d, want 0", notified)
	}
}
