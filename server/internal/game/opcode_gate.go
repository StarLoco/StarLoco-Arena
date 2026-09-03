package game

import (
	"sync"
	"time"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Per-opcode packet throttling.
//
// SECURITY: only chat and login were throttled, so every other opcode could be
// sent as fast as a socket allows. Two distinct problems, one control:
//
//  1. DB AMPLIFICATION. Some handlers do work proportional to server state for a
//     single small packet - 28601 costs roughly 2+2N queries (N = enabled
//     tournaments) and 28649 up to 31 (one per occupied bracket slot), both driven
//     by a wire-supplied id. That is amplification on ANY backend; SQLite merely
//     removes the headroom, and SQLite is what a fresh install runs
//     (driver "sqlite", max_open_conns 1 in the shipped template).
//
//  2. SPAM WITH NO DATABASE INVOLVED. Guild invites (501) and 2v2 invites (6024)
//     require no relationship with the target, are not rate-limited, and each one
//     pushes a MODAL dialog. Coach ids are small sequential integers, so a loop
//     produces a modal storm for every online player.
//
// The budget is deliberately generous: it exists to stop a loop, not to police
// normal play. A human clicking as fast as they can will not reach it, and the
// retail client sends these opcodes in response to UI actions.
type opcodeGate struct {
	mu   sync.Mutex
	hits map[uint16][]time.Time
	now  func() time.Time // swappable in tests
}

func newOpcodeGate() *opcodeGate {
	return &opcodeGate{hits: make(map[uint16][]time.Time)}
}

func (g *opcodeGate) clock() time.Time {
	if g.now != nil {
		return g.now()
	}
	return time.Now()
}

// opcodeBudget is the per-opcode allowance inside opcodeWindow, for the opcodes
// that are either expensive to serve or push something at another player.
//
// An opcode absent from this map is NOT throttled: the combat and movement paths
// are latency-sensitive and already bounded by turn order, AP/MP and the fight
// actor's serialisation, so a blanket limit there would risk dropping legitimate
// input during a busy turn.
var opcodeBudget = map[uint16]int{
	// Expensive to serve: work proportional to server state, wire-supplied id.
	protocol.OpTournamentListReq:       20, // 28601: ~2+2N queries
	protocol.OpTournamentTreeReq:       20, // 28649: up to 31 queries
	protocol.OpTournamentSearchRequest: 20, // 28611: pairing + bracket work

	// Pushes a modal or a broadcast at ANOTHER player.
	protocol.OpGuildInvite:     10, // 501
	protocol.OpTeamUpRequest:   10, // 6024
	protocol.OpChallengeInvite: 10, // 26301
	protocol.OpExchangeInvite:  10, // 5100
}

// opcodeWindow is the sliding window the budgets apply to.
const opcodeWindow = 10 * time.Second

// allow reports whether this session may serve another packet of this opcode.
func (g *opcodeGate) allow(opcode uint16) bool {
	budget, limited := opcodeBudget[opcode]
	if !limited {
		return true
	}
	g.mu.Lock()
	defer g.mu.Unlock()

	now := g.clock()
	cutoff := now.Add(-opcodeWindow)
	kept := g.hits[opcode][:0]
	for _, h := range g.hits[opcode] {
		if h.After(cutoff) {
			kept = append(kept, h)
		}
	}
	if len(kept) >= budget {
		g.hits[opcode] = kept
		return false
	}
	g.hits[opcode] = append(kept, now)
	return true
}
