package game

import "sync"

// StagedCard is a card a coach has put on the trade table.
type StagedCard struct {
	CardID     uint  // CoachCard row id (uid)
	TemplateID int32 // reference card id
	Quantity   int16
}

// Exchange is an in-progress card trade between two coaches. Side 0 = initiator
// (A), side 1 = target (B) — matching the client's userIndex.
type Exchange struct {
	ID   int64
	A, B *Session // A = initiator (side 0), B = target (side 1)

	mu       sync.Mutex
	accepted bool                   // both answered the invite (trade UI open)
	staged   [2]map[uint]StagedCard // per side: cardID -> staged card
	ready    [2]bool
}

// ExchangeManager tracks active exchanges, keyed by coach id, with a busy
// guard so a coach can only be in one exchange at a time (dupe-safety).
type ExchangeManager struct {
	mu      sync.Mutex
	byCoach map[uint]*Exchange
	nextID  int64
}

// NewExchangeManager creates an empty manager.
func NewExchangeManager() *ExchangeManager {
	return &ExchangeManager{byCoach: make(map[uint]*Exchange), nextID: 1}
}

// Start atomically registers a new exchange if neither party is busy.
// Returns nil if either coach is already exchanging.
func (m *ExchangeManager) Start(a, b *Session) *Exchange {
	m.mu.Lock()
	defer m.mu.Unlock()
	if _, busy := m.byCoach[a.Coach.ID]; busy {
		return nil
	}
	if _, busy := m.byCoach[b.Coach.ID]; busy {
		return nil
	}
	ex := &Exchange{ID: m.nextID, A: a, B: b}
	ex.staged[0] = make(map[uint]StagedCard)
	ex.staged[1] = make(map[uint]StagedCard)
	m.nextID++
	m.byCoach[a.Coach.ID] = ex
	m.byCoach[b.Coach.ID] = ex
	return ex
}

// Remove atomically deregisters an exchange, returning true only for the caller
// that actually removed it (consume-once: prevents a double both-ready from
// committing the swap twice).
func (m *ExchangeManager) Remove(ex *Exchange) bool {
	if ex == nil {
		return false
	}
	m.mu.Lock()
	defer m.mu.Unlock()
	if ex.A == nil || ex.A.Coach == nil || m.byCoach[ex.A.Coach.ID] != ex {
		return false
	}
	delete(m.byCoach, ex.A.Coach.ID)
	if ex.B != nil && ex.B.Coach != nil {
		delete(m.byCoach, ex.B.Coach.ID)
	}
	return true
}

// Get returns the exchange a coach is part of, or nil.
func (m *ExchangeManager) Get(coachID uint) *Exchange {
	m.mu.Lock()
	defer m.mu.Unlock()
	return m.byCoach[coachID]
}

// Other returns the session on the far side of the exchange from coachID.
func (ex *Exchange) Other(coachID uint) *Session {
	if ex.A != nil && ex.A.Coach != nil && ex.A.Coach.ID == coachID {
		return ex.B
	}
	return ex.A
}

// sideOf returns the user index (0=A, 1=B) for a coach id, or -1.
func (ex *Exchange) sideOf(coachID uint) int {
	if ex.A != nil && ex.A.Coach != nil && ex.A.Coach.ID == coachID {
		return 0
	}
	if ex.B != nil && ex.B.Coach != nil && ex.B.Coach.ID == coachID {
		return 1
	}
	return -1
}

// setAccepted marks the trade UI as open (both answered the invite).
func (ex *Exchange) setAccepted() {
	ex.mu.Lock()
	ex.accepted = true
	ex.mu.Unlock()
}

// stageCard adds/updates a card on a side's table and resets BOTH ready flags
// (any content change un-readies both, matching the client — prevents
// "ready then sneak a card in").
func (ex *Exchange) stageCard(side int, c StagedCard) {
	ex.mu.Lock()
	ex.staged[side][c.CardID] = c
	ex.ready = [2]bool{false, false}
	ex.mu.Unlock()
}

// unstageCard removes a card from a side's table and resets both ready flags.
func (ex *Exchange) unstageCard(side int, cardID uint) {
	ex.mu.Lock()
	delete(ex.staged[side], cardID)
	ex.ready = [2]bool{false, false}
	ex.mu.Unlock()
}

// toggleReady flips a side's ready flag and reports whether BOTH are now ready.
func (ex *Exchange) toggleReady(side int) (nowReady, bothReady bool) {
	ex.mu.Lock()
	defer ex.mu.Unlock()
	ex.ready[side] = !ex.ready[side]
	return ex.ready[side], ex.ready[0] && ex.ready[1]
}

// stagedCards returns a snapshot of a side's staged cards.
func (ex *Exchange) stagedCards(side int) []StagedCard {
	ex.mu.Lock()
	defer ex.mu.Unlock()
	out := make([]StagedCard, 0, len(ex.staged[side]))
	for _, c := range ex.staged[side] {
		out = append(out, c)
	}
	return out
}
