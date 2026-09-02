package game

import (
	"sync"
	"time"
)

// searcher is a coach waiting in the matchmaking queue.
type searcher struct {
	session *Session
	mode    int16   // fA game mode / fight type
	subMode int16   // fO
	teamIDs []int64 // selected fighter ids
	// strength is the coach's ladder rating, snapshotted on queueing so the
	// pairing decision cannot be perturbed by a concurrent post-fight update.
	strength int32
	// since is when this searcher started waiting; it widens its own band.
	since time.Time
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
	// band is the strength gap two coaches may have when they first queue, and
	// bandGrowth is how much it widens per second of waiting. band <= 0 disables
	// the check. See withinBand.
	band       int32
	bandGrowth int32
	// now is the clock, swappable in tests so a widening band can be exercised
	// without sleeping.
	now func() time.Time
}

// NewMatchmaker creates an empty matchmaker that pairs any two coaches,
// regardless of rating.
func NewMatchmaker() *Matchmaker {
	return &Matchmaker{
		pending: make(map[int64]*pendingMatch), byCoach: make(map[uint]int64), nextID: 1,
		now: time.Now,
	}
}

// SetRatingBand configures fair pairing: two coaches are matched only while
// their ladder-strength gap is within the band, which widens by `growth` for
// every second EITHER has been waiting. A band of 0 or less pairs anyone with
// anyone.
//
// The widening is what makes this safe to enable on a small server, and it is
// the reason there is no separate "queue timeout": rather than giving up after
// N seconds, the requirement relaxes until somebody qualifies, so a lone
// high-rated coach ends up matched instead of dropped.
//
// THESE NUMBERS ARE OURS. The client has no say in matchmaking — it sends a
// search and is told about a match — so nothing here is recoverable from the
// retail data, exactly like the post-fight constants flagged as honest limits.
func (m *Matchmaker) SetRatingBand(band, growth int32) {
	m.mu.Lock()
	defer m.mu.Unlock()
	m.band, m.bandGrowth = band, growth
}

// withinBand reports whether two searchers may be paired right now. Caller holds
// the lock.
//
// The band grows with the LONGER of the two waits, not the shorter: the point is
// that waiting earns you a wider net, and taking the shorter wait would let a
// freshly-queued coach veto a match for someone who has been waiting for
// minutes.
func (m *Matchmaker) withinBand(a, b *searcher) bool {
	if m.band <= 0 {
		return true
	}
	gap := a.strength - b.strength
	if gap < 0 {
		gap = -gap
	}
	now := m.clock()
	waited := now.Sub(a.since)
	if w := now.Sub(b.since); w > waited {
		waited = w
	}
	allowed := m.band
	if m.bandGrowth > 0 && waited > 0 {
		allowed += int32(waited.Seconds()) * m.bandGrowth
	}
	return gap <= allowed
}

// Search enqueues a searcher and returns a found match if a compatible opponent
// was already waiting (same mode, different coach). Returns nil if just queued.
func (m *Matchmaker) Search(s *Session, mode, subMode int16, teamIDs []int64) *pendingMatch {
	m.mu.Lock()
	defer m.mu.Unlock()

	myID, ok := searcherCoachID(&searcher{session: s})
	if !ok {
		// No coach: nothing to queue. The handler already rejects this, but the
		// matchmaker must not depend on that (see purgeGhostsLocked).
		return nil
	}
	var strength int32
	if s.Coach != nil {
		strength = s.Coach.Strength
	}
	sr := &searcher{session: s, mode: mode, subMode: subMode, teamIDs: teamIDs,
		strength: strength, since: m.clock()}

	m.purgeGhostsLocked()

	for i, other := range m.queue {
		otherID, ok := searcherCoachID(other)
		if !ok {
			continue // purged above; belt and braces
		}
		if other.mode == mode && otherID != myID && m.withinBand(other, sr) {
			// Match! remove the waiting opponent and create a pending match.
			m.queue = append(m.queue[:i], m.queue[i+1:]...)
			pm := &pendingMatch{id: m.nextID, a: other, b: sr}
			m.nextID++
			m.pending[pm.id] = pm
			m.byCoach[otherID] = pm.id
			m.byCoach[myID] = pm.id
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
		if id, ok := searcherCoachID(sr); ok && id == coachID {
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
	if id, ok := searcherCoachID(pm.a); ok && id == coachID {
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
		if id, ok := searcherCoachID(sr); ok && id == coachID {
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
	if id, ok := searcherCoachID(pm.a); ok {
		delete(m.byCoach, id)
	}
	if id, ok := searcherCoachID(pm.b); ok {
		delete(m.byCoach, id)
	}
}

// other returns the searcher on the far side of the match from coachID.
func (pm *pendingMatch) other(coachID uint) *searcher {
	if id, ok := searcherCoachID(pm.a); ok && id == coachID {
		return pm.b
	}
	return pm.a
}

// clock returns the matchmaker's time source (swappable in tests).
func (m *Matchmaker) clock() time.Time {
	if m.now == nil {
		return time.Now()
	}
	return m.now()
}

// searcherCoachID resolves a queued searcher's coach id, reporting false when the
// searcher, its session or its coach is nil.
//
// SECURITY: the queue can legitimately outlive the coach it refers to. 27529
// ("destroy coach") sets Session.Coach = nil while the session stays connected,
// and onClose's matchmaker cleanup is itself gated on a non-nil coach, so a
// disconnect does not necessarily clear the entry either. Before this helper,
// Search dereferenced other.session.Coach.ID directly, so one attacker could
// queue, destroy its coach, and leave a landmine that panicked the next honest
// searcher. With no recover() in the accept loop that panic ended the whole
// process. Regression: TestMatchmakerSurvivesGhostSearcher.
func searcherCoachID(sr *searcher) (uint, bool) {
	if sr == nil || sr.session == nil || sr.session.Coach == nil {
		return 0, false
	}
	return sr.session.Coach.ID, true
}

// purgeGhostsLocked drops queue entries whose coach has gone away. Called on the
// Search path so the queue self-heals rather than accumulating landmines.
// Caller must hold m.mu.
func (m *Matchmaker) purgeGhostsLocked() {
	kept := m.queue[:0]
	for _, sr := range m.queue {
		if _, ok := searcherCoachID(sr); ok {
			kept = append(kept, sr)
		}
	}
	m.queue = kept
}

// IsBusyMatchmaking reports whether a coach is queued for a fight or holds a
// pending match.
//
// SECURITY: this closes a TOCTOU bait-and-switch. The matchmaker snapshots a
// roster's fighter IDS at queue time (and the coach's strength, for band
// pairing), but buildFightTeamFor re-reads each fighter's STATS from the database
// when the fight actually starts. So an attacker could queue with a cheap, legal,
// low-strength roster, then re-equip those same fighter ids with maximum-cost
// gear via 6011 while waiting, and the fight would be built from the updated rows
// - defeating both the budget rule and rating-band matchmaking at once.
//
// Retail refused roster edits during a search and had a dedicated error for it
// (code 69, "Action impossible pendant une recherche de combat"), which the
// client still renders. So this restores a retail rule rather than inventing one.
func (m *Matchmaker) IsBusyMatchmaking(coachID uint) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	if _, ok := m.byCoach[coachID]; ok {
		return true
	}
	for _, sr := range m.queue {
		if id, ok := searcherCoachID(sr); ok && id == coachID {
			return true
		}
	}
	return false
}
