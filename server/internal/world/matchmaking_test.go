package world

import "testing"

func TestMatchmakerNoMatchQueuesSearcher(t *testing.T) {
	m := NewMatchmaker()
	opponent, found := m.FindMatch(&WaitingOpponent{CoachID: 1, Type: 1, Bet: 0})
	if found {
		t.Fatalf("expected no match on empty queue, got %+v", opponent)
	}
}

func TestMatchmakerMatchesSameTypeAndBet(t *testing.T) {
	m := NewMatchmaker()
	_, found := m.FindMatch(&WaitingOpponent{CoachID: 1, Type: 1, Bet: 100})
	if found {
		t.Fatal("first searcher should not find a match")
	}

	opponent, found := m.FindMatch(&WaitingOpponent{CoachID: 2, Type: 1, Bet: 100})
	if !found {
		t.Fatal("second searcher with matching type/bet should find a match")
	}
	if opponent.CoachID != 1 {
		t.Errorf("matched opponent CoachID = %d, want 1", opponent.CoachID)
	}
}

func TestMatchmakerDoesNotMatchDifferentTypeOrBet(t *testing.T) {
	m := NewMatchmaker()
	m.FindMatch(&WaitingOpponent{CoachID: 1, Type: 1, Bet: 100})

	if _, found := m.FindMatch(&WaitingOpponent{CoachID: 2, Type: 2, Bet: 100}); found {
		t.Error("should not match on different Type")
	}
	if _, found := m.FindMatch(&WaitingOpponent{CoachID: 3, Type: 1, Bet: 50}); found {
		t.Error("should not match on different Bet")
	}
}

func TestMatchmakerDoesNotMatchSelf(t *testing.T) {
	m := NewMatchmaker()
	m.FindMatch(&WaitingOpponent{CoachID: 1, Type: 1, Bet: 0})

	// A second search call from the SAME coach ID (e.g. a duplicate
	// packet) must not match against its own queued entry.
	_, found := m.FindMatch(&WaitingOpponent{CoachID: 1, Type: 1, Bet: 0})
	if found {
		t.Error("a coach should never match against its own queued search")
	}
}

func TestMatchmakerCancel(t *testing.T) {
	m := NewMatchmaker()
	m.FindMatch(&WaitingOpponent{CoachID: 1, Type: 1, Bet: 0})

	if !m.Cancel(1) {
		t.Error("Cancel should succeed for a queued coach")
	}
	if m.Cancel(1) {
		t.Error("Cancel should return false for an already-removed coach")
	}

	// After canceling, a new searcher with matching criteria should not
	// match against the canceled entry.
	_, found := m.FindMatch(&WaitingOpponent{CoachID: 2, Type: 1, Bet: 0})
	if found {
		t.Error("canceled entry should not be matchable")
	}
}

func TestMatchmakerMatchedPairIsRemovedFromQueue(t *testing.T) {
	m := NewMatchmaker()
	m.FindMatch(&WaitingOpponent{CoachID: 1, Type: 1, Bet: 0})
	m.FindMatch(&WaitingOpponent{CoachID: 2, Type: 1, Bet: 0}) // matches and removes coach 1

	// A third searcher with the same criteria should not match against
	// the now-consumed coach 1 entry.
	_, found := m.FindMatch(&WaitingOpponent{CoachID: 3, Type: 1, Bet: 0})
	if found {
		t.Error("matched entries must be removed from the queue, not matchable again")
	}
}
