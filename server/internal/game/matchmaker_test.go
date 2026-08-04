package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

func sessionWithCoach(id uint, name string) *Session {
	return &Session{Coach: &domain.Coach{ID: id, Name: name}}
}

// TestMatchmakerPairing: first searcher queues, second searcher (same mode)
// matches, and both accepting resolves the match.
func TestMatchmakerPairing(t *testing.T) {
	m := NewMatchmaker()
	a := sessionWithCoach(1, "Alice")
	b := sessionWithCoach(2, "Bob")

	if pm := m.Search(a, 1, 0, []int64{10}); pm != nil {
		t.Fatal("first searcher should queue, not match")
	}
	pm := m.Search(b, 1, 0, []int64{20})
	if pm == nil {
		t.Fatal("second searcher should match")
	}
	if pm.other(1).session != b || pm.other(2).session != a {
		t.Error("opponent wiring wrong")
	}

	// First accept: not both yet.
	if _, both := m.Accept(1, true); both {
		t.Error("should not be both-accepted after one")
	}
	// Second accept: both.
	if _, both := m.Accept(2, true); !both {
		t.Error("should be both-accepted after two")
	}
	// Match cleared.
	if m.Pending(1) != nil || m.Pending(2) != nil {
		t.Error("match should be removed after both accept")
	}
}

// TestMatchmakerDecline: a decline ends the match for both.
func TestMatchmakerDecline(t *testing.T) {
	m := NewMatchmaker()
	a := sessionWithCoach(1, "Alice")
	b := sessionWithCoach(2, "Bob")
	m.Search(a, 1, 0, nil)
	m.Search(b, 1, 0, nil)

	if _, both := m.Accept(1, false); both {
		t.Error("decline should not be both-accepted")
	}
	if m.Pending(2) != nil {
		t.Error("declined match should be removed")
	}
}

// TestMatchmakerDifferentModesDontMatch: different game modes stay queued.
func TestMatchmakerDifferentModesDontMatch(t *testing.T) {
	m := NewMatchmaker()
	m.Search(sessionWithCoach(1, "A"), 1, 0, nil)
	if pm := m.Search(sessionWithCoach(2, "B"), 2, 0, nil); pm != nil {
		t.Error("different modes should not match")
	}
}
