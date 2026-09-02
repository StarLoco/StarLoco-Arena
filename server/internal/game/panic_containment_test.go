package game

import (
	"log/slog"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestHandlerPanicDoesNotKillServer pins the blast-radius control.
//
// Go terminates the whole process on an unrecovered panic in any goroutine, so
// before dispatchSafely a single hostile frame that reached a nil dereference
// disconnected every player and voided every fight in progress. Two such crashes
// were reachable in three packets (matchmaker + challenge ghost coaches).
//
// If this test ever fails, the containment is gone and every latent nil in every
// handler is a full-outage bug again.
func TestHandlerPanicDoesNotKillServer(t *testing.T) {
	const opPanic = 61001

	r := NewRouter(slog.Default())
	r.Register(opPanic, func(s *Session, f *protocol.C2SFrame) error {
		var victim *struct{ N int }
		_ = victim.N // deliberate nil dereference
		return nil
	})

	s := &Session{router: r, log: slog.Default()}
	frame := &protocol.C2SFrame{Opcode: opPanic, Payload: []byte{1, 2, 3}}

	err := s.dispatchSafely(frame)
	if err == nil {
		t.Fatal("a panicking handler must surface an error so the session is dropped")
	}
	if err != errPanicInHandler {
		t.Errorf("err = %v, want errPanicInHandler", err)
	}

	// The decisive part: we are still running. A second dispatch must also work,
	// proving the recover did not leave the router or session unusable.
	if err := s.dispatchSafely(frame); err != errPanicInHandler {
		t.Errorf("second dispatch err = %v, want errPanicInHandler", err)
	}
}

// TestNonPanickingHandlerIsUnaffected guards against the recover() swallowing
// normal outcomes - a containment layer that also hid real errors would be worse
// than none.
func TestNonPanickingHandlerIsUnaffected(t *testing.T) {
	const opOK, opErr = 61002, 61003
	sentinel := errPanicInHandler // any non-nil error distinct from success

	r := NewRouter(slog.Default())
	r.Register(opOK, func(s *Session, f *protocol.C2SFrame) error { return nil })
	r.Register(opErr, func(s *Session, f *protocol.C2SFrame) error { return sentinel })

	s := &Session{router: r, log: slog.Default()}
	if err := s.dispatchSafely(&protocol.C2SFrame{Opcode: opOK}); err != nil {
		t.Errorf("healthy handler returned %v, want nil", err)
	}
	if err := s.dispatchSafely(&protocol.C2SFrame{Opcode: opErr}); err != sentinel {
		t.Errorf("handler error was altered: got %v, want %v", err, sentinel)
	}
}

// TestFightEventPanicDoesNotKillServer covers the other bare goroutine: the fight
// actor. A panic there used to kill the process and with it every OTHER fight.
func TestFightEventPanicDoesNotKillServer(t *testing.T) {
	f := &Fight{ID: 1, deps: &Deps{Log: slog.Default()}}

	f.runEvent(func(f *Fight) {
		var victim *struct{ N int }
		_ = victim.N
	})

	// Still alive: a following event must run normally.
	ran := false
	f.runEvent(func(f *Fight) { ran = true })
	if !ran {
		t.Error("fight actor stopped processing events after a panic")
	}
}
