package world

import "sync"

// WaitingOpponent is a coach currently searching for an opponent, mirroring
// the legacy WaitingOpponent.Opponent inner class. See
// docs/02-protocol.md OPPONENT_SEARCH_* opcodes.
type WaitingOpponent struct {
	CoachID uint
	Type    byte
	Bet     int32
}

// Matchmaker holds the waiting-opponent queue. Access is synchronized
// (unlike the legacy version, whose search() was synchronized but add()
// and list iteration were not -- a real race).
type Matchmaker struct {
	mu   sync.Mutex
	list []*WaitingOpponent
}

// NewMatchmaker creates an empty matchmaking queue.
func NewMatchmaker() *Matchmaker {
	return &Matchmaker{}
}

// FindMatch looks for a compatible waiting opponent (same Type and Bet,
// different coach) and, if found, removes both from the queue and returns
// them. If no match is found, searcher is added to the queue and the
// second return value is false.
func (m *Matchmaker) FindMatch(searcher *WaitingOpponent) (*WaitingOpponent, bool) {
	m.mu.Lock()
	defer m.mu.Unlock()

	for i, candidate := range m.list {
		if candidate.CoachID == searcher.CoachID {
			continue
		}
		if candidate.Type == searcher.Type && candidate.Bet == searcher.Bet {
			m.list = append(m.list[:i], m.list[i+1:]...)
			return candidate, true
		}
	}

	m.list = append(m.list, searcher)
	return nil, false
}

// Cancel removes a coach from the waiting queue, if present.
func (m *Matchmaker) Cancel(coachID uint) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	for i, candidate := range m.list {
		if candidate.CoachID == coachID {
			m.list = append(m.list[:i], m.list[i+1:]...)
			return true
		}
	}
	return false
}
