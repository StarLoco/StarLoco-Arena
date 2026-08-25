package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// A tournament match is a fixture between two entrants of ONE tournament, not a
// free pairing. These pin the two rules that follow from that: only an entrant
// may ready up, and a pairing may only be made within the same tournament.

func tmSession(d *Deps, id uint, name string) *Session {
	s := &Session{
		log: testLogger(), deps: d, out: make(chan []byte, writeQueueSize),
		quit: make(chan struct{}), Coach: &domain.Coach{ID: id, Name: name},
	}
	d.Sessions.Swap(id, s)
	return s
}

func tmDeps() *Deps {
	return &Deps{
		World: NewRegistry(150), Fights: NewFightManager(),
		Matchmaker: NewMatchmaker(), Sessions: NewSessionRegistry(),
		Tournaments: NewTournamentManager(), Log: testLogger(),
	}
}

func readyFrame(tid int64, coachID uint, preset uint16) *protocol.C2SFrame {
	w := protocol.NewWriter().I64(tid).I64(int64(coachID)).U16(preset)
	return &protocol.C2SFrame{Opcode: protocol.OpTournamentSearchRequest, Arch: 2, Payload: w.Bytes()}
}

func TestTournamentReadyRefusesNonEntrants(t *testing.T) {
	d := tmDeps()
	s := tmSession(d, 1, "Outsider")
	// Registered for tournament 7, readying for 9.
	d.Tournaments.Register(1, 7)

	if err := handleTournamentSearchRequest(s, readyFrame(9, 1, 0)); err != nil {
		t.Fatalf("ready: %v", err)
	}
	got := drain(t, s)
	if len(got) != 1 || got[0] != protocol.OpTournamentSearchError {
		t.Fatalf("got %v, want a single 28616 error - a non-entrant must not join a "+
			"fixture list for a tournament it never registered for", got)
	}
	if _, paired := d.Tournaments.ReadyUp(9, 99); paired {
		t.Error("the refused coach was left waiting in tournament 9's queue")
	}
}

func TestTournamentReadyAcceptsAndWaits(t *testing.T) {
	d := tmDeps()
	s := tmSession(d, 1, "Entrant")
	d.Tournaments.Register(1, 7)

	if err := handleTournamentSearchRequest(s, readyFrame(7, 1, 0)); err != nil {
		t.Fatalf("ready: %v", err)
	}
	got := drain(t, s)
	if len(got) != 1 || got[0] != protocol.OpTournamentSearchResult {
		t.Fatalf("got %v, want a single 28612 accept - it is what opens the waiting "+
			"dialog, and without it the player is left with no overlay", got)
	}
}

// TestTournamentPairingIsPerTournament: two coaches ready in DIFFERENT
// tournaments must not be matched with each other.
func TestTournamentPairingIsPerTournament(t *testing.T) {
	d := tmDeps()
	a := tmSession(d, 1, "A")
	b := tmSession(d, 2, "B")
	d.Tournaments.Register(1, 7)
	d.Tournaments.Register(2, 8)

	_ = handleTournamentSearchRequest(a, readyFrame(7, 1, 0))
	_ = handleTournamentSearchRequest(b, readyFrame(8, 2, 0))
	for _, s := range []*Session{a, b} {
		for _, op := range drain(t, s) {
			if op == protocol.OpTournamentFightStarting {
				t.Fatalf("%s was matched across tournaments", s.Coach.Name)
			}
		}
	}
}

// TestTournamentReadyUpPairsSiblings: the opponent is the entrant in the SIBLING
// bracket slot (slot ^ 1), not whoever else happens to be waiting.
//
// Coaches 1..4 seed into slots 16,17,18,19, so the fixtures are 1-v-2 and 3-v-4.
// Pairing 1 with 3 would send two players into a match that advances neither -
// the bracket would refuse the result - which is worse than making them wait.
func TestTournamentReadyUpPairsSiblings(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)

	if _, paired := m.ReadyUp(7, 1); paired {
		t.Fatal("the first entrant paired with nobody")
	}
	if _, paired := m.ReadyUp(7, 1); paired {
		t.Fatal("a coach readying twice matched itself")
	}
	// Coach 3 sits in the other half of the draw: it must NOT take coach 1.
	if opp, paired := m.ReadyUp(7, 3); paired {
		t.Fatalf("coach 3 was paired with %d - it is not coach 1's bracket sibling", opp)
	}
	// Coach 2 IS coach 1's sibling.
	opp, paired := m.ReadyUp(7, 2)
	if !paired || opp != 1 {
		t.Fatalf("ReadyUp(2) = (%d, %v), want (1, true)", opp, paired)
	}
	// That fixture is consumed; coach 4 pairs with the still-waiting coach 3.
	opp, paired = m.ReadyUp(7, 4)
	if !paired || opp != 3 {
		t.Fatalf("ReadyUp(4) = (%d, %v), want (3, true)", opp, paired)
	}
	// BOTH sides of a returned fixture must leave the queue. If the opponent were
	// left behind, the next coach to ready would be handed a stale pairing for a
	// match that is already under way.
	if opp, paired := m.ReadyUp(7, 2); paired {
		t.Errorf("coach 2 was immediately re-paired with %d - the previous fixture "+
			"left a stale entrant in the queue", opp)
	}
}

// TestTournamentReadyUpPairsTheNextRound: after a round is decided, the winners
// become each other's siblings one level up and pair there.
func TestTournamentReadyUpPairsTheNextRound(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	if got := m.RecordMatchResult(7, 1, 2); got != 8 {
		t.Fatalf("advanced to %d, want 8", got)
	}
	if got := m.RecordMatchResult(7, 3, 4); got != 9 {
		t.Fatalf("advanced to %d, want 9", got)
	}
	// 1 is now at slot 8, 3 at slot 9: siblings (8^1 == 9).
	if _, paired := m.ReadyUp(7, 1); paired {
		t.Fatal("paired before the opponent was ready")
	}
	opp, paired := m.ReadyUp(7, 3)
	if !paired || opp != 1 {
		t.Fatalf("ReadyUp(3) = (%d, %v), want (1, true) - the semi-final winners "+
			"must meet in the next round", opp, paired)
	}
}

func TestTournamentCancelReadyClearsTheSlot(t *testing.T) {
	m := NewTournamentManager()
	m.ReadyUp(7, 1)
	if !m.CancelReady(1) {
		t.Fatal("CancelReady did not find the waiting coach")
	}
	if _, paired := m.ReadyUp(7, 2); paired {
		t.Error("a cancelled coach was still matched")
	}
}
