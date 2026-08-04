package game

import "sync"

// challenge is a direct fight invitation from one coach to another ("Proposer un
// entraînement"). After the target accepts, both coaches pick a team (26303) and,
// once both have confirmed, the fight starts. The handle sent on the wire
// (invitation / accepted / cancelled id field) is the CHALLENGER's coach id; the
// server locates a challenge by EITHER coach's id (both map to it), so a message's
// sender identifies its own challenge regardless of the echoed handle.
type challenge struct {
	id         int64 // = challenger coach id (the wire handle)
	challenger *Session
	target     *Session
	evolution  bool
	accepted   bool // target accepted → both coaches are in team-select

	teamChallenger int16
	teamTarget     int16
	confChallenger bool
	confTarget     bool
}

// ChallengeManager tracks pending direct-challenge invitations. Thread-safe; a
// coach can be in at most one challenge at a time (as challenger OR target).
type ChallengeManager struct {
	mu      sync.Mutex
	byCoach map[uint]*challenge // both challenger + target map to the same challenge
}

// NewChallengeManager creates an empty challenge manager.
func NewChallengeManager() *ChallengeManager {
	return &ChallengeManager{byCoach: make(map[uint]*challenge)}
}

// Create registers a challenge from challenger to target, unless EITHER coach is
// already in one. Returns the challenge, or nil if busy.
func (m *ChallengeManager) Create(challenger, target *Session, evolution bool) *challenge {
	m.mu.Lock()
	defer m.mu.Unlock()
	cid, tid := challenger.Coach.ID, target.Coach.ID
	if m.byCoach[cid] != nil || m.byCoach[tid] != nil {
		return nil
	}
	c := &challenge{id: int64(cid), challenger: challenger, target: target, evolution: evolution}
	m.byCoach[cid] = c
	m.byCoach[tid] = c
	return c
}

// Get returns the challenge a coach is part of, or nil.
func (m *ChallengeManager) Get(coachID uint) *challenge {
	m.mu.Lock()
	defer m.mu.Unlock()
	return m.byCoach[coachID]
}

// Accept marks the TARGET's acceptance. Returns the challenge only if coachID is
// the target of a not-yet-accepted challenge (so a challenger can't accept its own
// invite), else nil.
func (m *ChallengeManager) Accept(coachID uint) *challenge {
	m.mu.Lock()
	defer m.mu.Unlock()
	c := m.byCoach[coachID]
	if c == nil || c.accepted || c.target.Coach.ID != coachID {
		return nil
	}
	c.accepted = true
	return c
}

// Remove drops the challenge a coach is part of and returns it (for notifying the
// other side). Used on decline, cancel and disconnect.
func (m *ChallengeManager) Remove(coachID uint) *challenge {
	m.mu.Lock()
	defer m.mu.Unlock()
	c := m.byCoach[coachID]
	if c == nil {
		return nil
	}
	m.removeLocked(c)
	return c
}

// ConfirmTeam records a coach's team choice after acceptance and returns the
// challenge plus whether BOTH sides have now confirmed (so the fight can start).
// A confirm before acceptance, or from a coach not in a challenge, returns nil.
func (m *ChallengeManager) ConfirmTeam(coachID uint, teamID int16) (*challenge, bool) {
	m.mu.Lock()
	defer m.mu.Unlock()
	c := m.byCoach[coachID]
	if c == nil || !c.accepted {
		return nil, false
	}
	if c.challenger.Coach.ID == coachID {
		c.teamChallenger = teamID
		c.confChallenger = true
	} else {
		c.teamTarget = teamID
		c.confTarget = true
	}
	both := c.confChallenger && c.confTarget
	if both {
		m.removeLocked(c)
	}
	return c, both
}

func (m *ChallengeManager) removeLocked(c *challenge) {
	delete(m.byCoach, c.challenger.Coach.ID)
	delete(m.byCoach, c.target.Coach.ID)
}

// other returns the session on the far side of the challenge from coachID.
func (c *challenge) other(coachID uint) *Session {
	if c.challenger.Coach.ID == coachID {
		return c.target
	}
	return c.challenger
}
