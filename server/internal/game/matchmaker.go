package game

import "sync"

// searcher is a coach waiting in the matchmaking queue.
type searcher struct {
	session *Session
	mode    int16   // fA game mode / fight type
	subMode int16   // fO
	teamIDs []int64 // selected fighter ids
}

// pendingMatch is a found pair awaiting both coaches' acceptance.
type pendingMatch struct {
	id   int64
	a, b *searcher
	accA bool
	accB bool
}

// Matchmaker pairs waiting coaches by game mode and tracks pending matches
// through the accept handshake.
type Matchmaker struct {
	mu      sync.Mutex
	queue   []*searcher
	pending map[int64]*pendingMatch
	byCoach map[uint]int64 // coach id -> pending match id
	nextID  int64
}

// NewMatchmaker creates an empty matchmaker.
func NewMatchmaker() *Matchmaker {
	return &Matchmaker{pending: make(map[int64]*pendingMatch), byCoach: make(map[uint]int64), nextID: 1}
}

// Search enqueues a searcher and returns a found match if a compatible opponent
// was already waiting (same mode, different coach). Returns nil if just queued.
func (m *Matchmaker) Search(s *Session, mode, subMode int16, teamIDs []int64) *pendingMatch {
	m.mu.Lock()
	defer m.mu.Unlock()

	sr := &searcher{session: s, mode: mode, subMode: subMode, teamIDs: teamIDs}
	for i, other := range m.queue {
		if other.mode == mode && other.session.Coach.ID != s.Coach.ID {
			// Match! remove the waiting opponent and create a pending match.
			m.queue = append(m.queue[:i], m.queue[i+1:]...)
			pm := &pendingMatch{id: m.nextID, a: other, b: sr}
			m.nextID++
			m.pending[pm.id] = pm
			m.byCoach[other.session.Coach.ID] = pm.id
			m.byCoach[s.Coach.ID] = pm.id
			return pm
		}
	}
	// No opponent: enqueue.
	m.queue = append(m.queue, sr)
	return nil
}

// CancelSearch removes a coach from the queue (does not touch pending matches).
// Returns true if the coach was actually queued.
func (m *Matchmaker) CancelSearch(coachID uint) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	for i, sr := range m.queue {
		if sr.session.Coach.ID == coachID {
			m.queue = append(m.queue[:i], m.queue[i+1:]...)
			return true
		}
	}
	return false
}

// Accept records a coach's acceptance of its pending match. Returns the match
// and whether BOTH sides have now accepted.
func (m *Matchmaker) Accept(coachID uint, accept bool) (*pendingMatch, bool) {
	m.mu.Lock()
	defer m.mu.Unlock()
	id, ok := m.byCoach[coachID]
	if !ok {
		return nil, false
	}
	pm := m.pending[id]
	if pm == nil {
		return nil, false
	}
	if !accept {
		m.removeLocked(pm)
		return pm, false
	}
	if pm.a.session.Coach.ID == coachID {
		pm.accA = true
	} else {
		pm.accB = true
	}
	both := pm.accA && pm.accB
	if both {
		m.removeLocked(pm)
	}
	return pm, both
}

// Pending returns the pending match a coach is part of, or nil.
func (m *Matchmaker) Pending(coachID uint) *pendingMatch {
	m.mu.Lock()
	defer m.mu.Unlock()
	if id, ok := m.byCoach[coachID]; ok {
		return m.pending[id]
	}
	return nil
}

// Remove clears a coach from queue + any pending match (used on disconnect).
func (m *Matchmaker) Remove(coachID uint) *pendingMatch {
	m.mu.Lock()
	defer m.mu.Unlock()
	// From queue:
	for i, sr := range m.queue {
		if sr.session.Coach.ID == coachID {
			m.queue = append(m.queue[:i], m.queue[i+1:]...)
			break
		}
	}
	if id, ok := m.byCoach[coachID]; ok {
		pm := m.pending[id]
		m.removeLocked(pm)
		return pm
	}
	return nil
}

// Discard drops a pending match from tracking without notifying anyone. Used by
// the "Combattre" ready-room, which starts the fight directly on pairing and so
// bypasses the accept handshake that would otherwise clear the pending match.
func (m *Matchmaker) Discard(pm *pendingMatch) {
	m.mu.Lock()
	defer m.mu.Unlock()
	m.removeLocked(pm)
}

func (m *Matchmaker) removeLocked(pm *pendingMatch) {
	if pm == nil {
		return
	}
	delete(m.pending, pm.id)
	if pm.a != nil {
		delete(m.byCoach, pm.a.session.Coach.ID)
	}
	if pm.b != nil {
		delete(m.byCoach, pm.b.session.Coach.ID)
	}
}

// other returns the searcher on the far side of the match from coachID.
func (pm *pendingMatch) other(coachID uint) *searcher {
	if pm.a != nil && pm.a.session.Coach.ID == coachID {
		return pm.b
	}
	return pm.a
}
