package game

import (
	"log/slog"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestDispatchAppliesTheOpcodeGate covers the WIRING. My first tests called
// opcodeGate.allow directly, so a mutation bypassing the gate in dispatchSafely
// survived - testing the predicate is not testing the caller, which is the
// mistake this work keeps repeating.
func TestDispatchAppliesTheOpcodeGate(t *testing.T) {
	const op = 28601 // OpTournamentListReq, a throttled opcode
	budget := opcodeBudget[op]
	if budget == 0 {
		t.Fatalf("fixture broken: opcode %d is not throttled", op)
	}

	served := 0
	r := NewRouter(slog.Default())
	r.Register(op, func(s *Session, f *protocol.C2SFrame) error {
		served++
		return nil
	})

	s := &Session{router: r, log: slog.Default(), opcodes: newOpcodeGate()}
	frame := &protocol.C2SFrame{Opcode: op}

	for i := 0; i < budget*3; i++ {
		if err := s.dispatchSafely(frame); err != nil {
			t.Fatalf("dispatch %d: %v", i, err)
		}
	}
	if served > budget {
		t.Errorf("handler ran %d times for a budget of %d: the gate is not applied "+
			"in dispatchSafely", served, budget)
	}
	if served == 0 {
		t.Error("handler never ran; the gate is refusing everything")
	}

	// An UNTHROTTLED opcode must pass through freely, or the gate has become a
	// blanket limit on all traffic.
	const free = 8109 // spell cast
	freeServed := 0
	r.Register(free, func(s *Session, f *protocol.C2SFrame) error {
		freeServed++
		return nil
	})
	for i := 0; i < 200; i++ {
		_ = s.dispatchSafely(&protocol.C2SFrame{Opcode: free})
	}
	if freeServed != 200 {
		t.Errorf("unthrottled opcode served %d/200 times", freeServed)
	}
}
