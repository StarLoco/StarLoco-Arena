package game

import (
	"testing"
	"time"
)

// TestOpcodeGateThrottlesExpensiveAndSpammyOpcodes covers the packet-rate
// control. Only chat and login were throttled, so every other opcode could be
// sent as fast as a socket allows.
func TestOpcodeGateThrottlesExpensiveAndSpammyOpcodes(t *testing.T) {
	now := time.Now()
	g := newOpcodeGate()
	g.now = func() time.Time { return now }

	const op = 28601 // OpTournamentListReq: ~2+2N DB queries per packet
	budget := opcodeBudget[op]
	if budget == 0 {
		t.Fatalf("fixture broken: opcode %d carries no budget, so nothing is tested", op)
	}
	for i := 0; i < budget; i++ {
		if !g.allow(op) {
			t.Fatalf("packet %d should be inside the budget of %d", i+1, budget)
		}
	}
	if g.allow(op) {
		t.Errorf("packet %d exceeded the budget of %d but was allowed", budget+1, budget)
	}

	// A DIFFERENT opcode keeps its own budget - one busy opcode must not lock the
	// player out of everything else.
	if !g.allow(28649) {
		t.Error("a different opcode was throttled by another opcode's usage")
	}

	// The window slides.
	now = now.Add(opcodeWindow + time.Second)
	if !g.allow(op) {
		t.Error("the budget did not refill after the window elapsed")
	}
}

// TestOpcodeGateLeavesCombatAlone is the guard against over-throttling: combat and
// movement are latency-sensitive and already bounded by turn order, AP/MP and the
// fight actor's serialisation. A blanket limit there would drop legitimate input
// during a busy turn.
func TestOpcodeGateLeavesCombatAlone(t *testing.T) {
	g := newOpcodeGate()
	for _, op := range []uint16{
		8109, // spell cast
		8111, // close combat
		4503, // move in fight
		8105, // end turn
		4501, // overworld move
		8107, // fighter card use
	} {
		for i := 0; i < 500; i++ {
			if !g.allow(op) {
				t.Fatalf("combat opcode %d was throttled after %d packets", op, i)
			}
		}
	}
}

// TestOpcodeGateCoversBothProblemShapes pins that the budget list contains the
// two distinct reasons an opcode belongs there, so a future edit cannot quietly
// drop one: work proportional to server state, and pushing a modal at someone.
func TestOpcodeGateCoversBothProblemShapes(t *testing.T) {
	expensive := []uint16{28601, 28649, 28611}
	spammy := []uint16{501, 6024, 26301, 5101}

	for _, op := range expensive {
		if opcodeBudget[op] == 0 {
			t.Errorf("opcode %d does work proportional to server state but is not "+
				"throttled", op)
		}
	}
	for _, op := range spammy {
		if opcodeBudget[op] == 0 {
			t.Errorf("opcode %d pushes a modal or broadcast at another player but is "+
				"not throttled", op)
		}
	}
}
