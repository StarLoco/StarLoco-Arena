package world

import (
	"sync"
	"sync/atomic"
)

// Invitation is a pending "challenge by right-click" between two coaches,
// from the moment the inviter sends FIGHT_INVITATION_REQUEST_MESSAGE (4301)
// until the target accepts (4305) or rejects (4307) it -- see
// docs/opcodes/04-matchmaking-invitation.md.
//
// This is the invitation-flow analog of matchmaking's WaitingOpponent: it
// tracks the pending challenge state, but does NOT itself create a fight.
// On accept, the dispatch layer creates a real Duel (via DuelManager,
// exactly as matchmaking does after OPPONENT_FOUND) so the shared
// SET_READY_FOR_FIGHT -> CREATE_FIGHT -> presentation pipeline takes over
// unchanged. The InvitationId is a distinct id from the eventual fightId
// (the client's FIGHT_INVITATION_ACCEPTED message carries both).
//
// The legacy Java server never implemented this flow past parsing the 4301
// request (its handler ended with a "TODO: Finir d'implementer le defi par
// clic droit" comment); this is a Go-only enhancement.
type Invitation struct {
	ID        int64
	InviterID uint
	TargetID  uint
	Type      byte
	Bet       int32
}

// InvolvesCoach reports whether coachID is either party to this invitation.
func (i *Invitation) InvolvesCoach(coachID uint) bool {
	return coachID == i.InviterID || coachID == i.TargetID
}

// OpponentOf returns the other party's coach id (the inviter for the
// target, the target for the inviter).
func (i *Invitation) OpponentOf(coachID uint) uint {
	if coachID == i.InviterID {
		return i.TargetID
	}
	return i.InviterID
}

// InvitationManager tracks all pending fight invitations, mirroring the
// DuelManager pattern (monotonic id allocation + a synchronized map). A
// coach may be the inviter of, or target of, at most one pending
// invitation at a time in practice; GetByCoach enforces the "already busy"
// check callers rely on.
type InvitationManager struct {
	nextID atomic.Int64

	mu          sync.RWMutex
	invitations map[int64]*Invitation
}

// NewInvitationManager creates an empty invitation registry.
func NewInvitationManager() *InvitationManager {
	m := &InvitationManager{invitations: make(map[int64]*Invitation)}
	m.nextID.Store(1)
	return m
}

// Create allocates and registers a new pending invitation from inviter to
// target for the given fight type and bet.
func (m *InvitationManager) Create(inviter, target uint, fightType byte, bet int32) *Invitation {
	id := m.nextID.Add(1) - 1
	inv := &Invitation{
		ID:        id,
		InviterID: inviter,
		TargetID:  target,
		Type:      fightType,
		Bet:       bet,
	}

	m.mu.Lock()
	m.invitations[id] = inv
	m.mu.Unlock()

	return inv
}

// Get looks up a pending invitation by id.
func (m *InvitationManager) Get(id int64) (*Invitation, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	inv, ok := m.invitations[id]
	return inv, ok
}

// GetByCoach returns any pending invitation involving coachID (as either
// inviter or target). Used to reject a new invitation when either party is
// already tied up in a pending one ("busy"). Since a coach can only be in
// one pending invitation at a time in practice, the first match is
// returned.
func (m *InvitationManager) GetByCoach(coachID uint) (*Invitation, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	for _, inv := range m.invitations {
		if inv.InvolvesCoach(coachID) {
			return inv, true
		}
	}
	return nil, false
}

// Remove unregisters an invitation (accepted / rejected / canceled on
// disconnect). No-op if the id is unknown.
func (m *InvitationManager) Remove(id int64) {
	m.mu.Lock()
	defer m.mu.Unlock()
	delete(m.invitations, id)
}

// RemoveByCoach removes every pending invitation involving coachID and
// returns them, so the caller can notify the other party (e.g. on
// disconnect). A coach normally has at most one, but this handles any
// number safely.
func (m *InvitationManager) RemoveByCoach(coachID uint) []*Invitation {
	m.mu.Lock()
	defer m.mu.Unlock()
	var removed []*Invitation
	for id, inv := range m.invitations {
		if inv.InvolvesCoach(coachID) {
			removed = append(removed, inv)
			delete(m.invitations, id)
		}
	}
	return removed
}
