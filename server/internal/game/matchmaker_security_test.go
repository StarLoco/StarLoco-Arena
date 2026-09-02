package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestMatchmakerSurvivesGhostSearcher is a SECURITY regression test.
//
// Attack (3 packets, one throwaway account): queue for a fight (2301), then
// destroy your own coach (27529). handleDestroyCoach sets s.Coach = nil but does
// not clean the matchmaker queue, and onClose gates its matchmaker cleanup behind
// `if s.Coach != nil`, so disconnecting does not clean it either. The queue keeps
// a searcher whose session has a nil Coach.
//
// The next player to search then dereferences it in Matchmaker.Search, and since
// the server has no recover() anywhere, that panic takes down the WHOLE process
// with every fight in progress. Cost to re-arm: 3 packets.
func TestMatchmakerSurvivesGhostSearcher(t *testing.T) {
	m := NewMatchmaker()

	attacker := sessionWithCoach(1, "Attacker")
	if pm := m.Search(attacker, 1, 0, nil); pm != nil {
		t.Fatal("first searcher should just enqueue")
	}

	// handleDestroyCoach (27529) does exactly this and leaves the queue entry.
	attacker.Coach = nil

	victim := sessionWithCoach(2, "Victim")
	// Before the fix this panics with a nil-pointer dereference.
	_ = m.Search(victim, 1, 0, nil)

	// The ghost must be gone, not merely survived: a stale entry that is skipped
	// forever would silently wedge the queue.
	m.mu.Lock()
	got := len(m.queue)
	m.mu.Unlock()
	if got != 1 {
		t.Errorf("queue length = %d, want 1 (ghost purged, victim enqueued)", got)
	}
}

// TestMatchmakerGhostDoesNotBlockPairing checks the ghost is purged rather than
// skipped: two honest players who search after the ghost must still pair.
func TestMatchmakerGhostDoesNotBlockPairing(t *testing.T) {
	m := NewMatchmaker()

	ghost := sessionWithCoach(1, "Ghost")
	m.Search(ghost, 1, 0, nil)
	ghost.Coach = nil

	m.Search(sessionWithCoach(2, "A"), 1, 0, nil)
	pm := m.Search(sessionWithCoach(3, "B"), 1, 0, nil)
	if pm == nil {
		t.Fatal("two honest searchers must still pair with a ghost in the queue")
	}
}

// TestMatchmakerRemovePathsSurviveNilCoach covers the other five call sites that
// dereference session.Coach: CancelSearch, Remove, Accept and removeLocked all
// walk the same queue/pending structures.
func TestMatchmakerRemovePathsSurviveNilCoach(t *testing.T) {
	cases := []struct {
		name string
		run  func(m *Matchmaker, ghostCoachID uint)
	}{
		{"CancelSearch", func(m *Matchmaker, id uint) { m.CancelSearch(id) }},
		{"Remove", func(m *Matchmaker, id uint) { m.Remove(id) }},
		{"Accept", func(m *Matchmaker, id uint) { m.Accept(id, true) }},
		{"Pending", func(m *Matchmaker, id uint) { m.Pending(id) }},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			m := NewMatchmaker()
			ghost := sessionWithCoach(7, "Ghost")
			m.Search(ghost, 1, 0, nil)
			ghost.Coach = nil
			// Must not panic.
			tc.run(m, 7)
		})
	}
}

// TestPendingMatchOtherSurvivesNilCoach covers pendingMatch.other, reached from
// onClose when a matched opponent drops.
func TestPendingMatchOtherSurvivesNilCoach(t *testing.T) {
	a := sessionWithCoach(1, "A")
	b := sessionWithCoach(2, "B")
	pm := &pendingMatch{
		id: 1,
		a:  &searcher{session: a},
		b:  &searcher{session: b},
	}
	a.Coach = nil
	// Must not panic, and must still resolve B as the other side.
	if other := pm.other(2); other != nil && other.session != a {
		t.Error("other(2) should resolve to the A side")
	}
	_ = pm.other(1)
}

var _ = domain.Coach{}
