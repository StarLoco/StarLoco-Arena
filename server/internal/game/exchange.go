package game

import "sync"

// StagedCard is a card a coach has put on the trade table.
//
// The table is keyed by TemplateID, not by row id, because that is the only
// identity the client has: every card message in 2.70 carries just the i32
// reference-card id (eb_1.b reads four bytes and invents its own local uid), so
// a row id would be meaningless to it. The rest of the codebase already treats
// (coach, template, pos=0) as unique — see ConsumeAndGrant — so nothing is lost.
//
// CardID is still carried for the commit, which debits the exact row.
type StagedCard struct {
	CardID     uint  // CoachCard row id, resolved when staged
	TemplateID int32 // reference card id — the key the client uses
	Quantity   int16
}

// Exchange is an in-progress card trade between two coaches. Side 0 = initiator
// (A), side 1 = target (B) — matching the client's userIndex.
type Exchange struct {
	ID   int64
	A, B *Session // A = initiator (side 0), B = target (side 1)

	mu       sync.Mutex
	accepted bool                    // both answered the invite (trade UI open)
	staged   [2]map[int32]StagedCard // per side: templateID -> staged card
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
	// SECURITY: a coach cannot trade with itself.
	//
	// Start(s, s) passed both busy checks (nothing is inserted until after them)
	// and then wrote byCoach[id] twice, leaving ex.A == ex.B. sideOf then always
	// returned 0, so ready[1] could never be set and the exchange could never
	// commit or complete - but the coach was now permanently "busy" and unable to
	// trade with anyone until it disconnected. A self-inflicted lockout, and the
	// same shape aimed at a stranger locked THEM out instead, since the invite
	// path checks neither proximity nor the ignore list.
	aID, aok := sessionCoachID(a)
	bID, bok := sessionCoachID(b)
	if !aok || !bok || aID == bID {
		return nil
	}
	if _, busy := m.byCoach[a.Coach.ID]; busy {
		return nil
	}
	if _, busy := m.byCoach[b.Coach.ID]; busy {
		return nil
	}
	ex := &Exchange{ID: m.nextID, A: a, B: b}
	ex.staged[0] = make(map[int32]StagedCard)
	ex.staged[1] = make(map[int32]StagedCard)
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
// maxStagedPerSide mirrors the client's own cap (CG.java:225-228:
// `this.Of[n2].keySet().size() < 5`). Staging is broadcast to the OTHER player,
// whose 5-slot trade UI was never built for more, so an unbounded stage is a
// griefing / trade-scam surface against them rather than an economy break.
const maxStagedPerSide = 5

// stageCard adds a card to this side's offer. Returns false when the side is
// already full.
func (ex *Exchange) stageCard(side int, c StagedCard) bool {
	ex.mu.Lock()
	defer ex.mu.Unlock()
	// Re-staging a template already on the table is an update, not a new slot.
	if _, present := ex.staged[side][c.TemplateID]; !present &&
		len(ex.staged[side]) >= maxStagedPerSide {
		return false
	}
	ex.staged[side][c.TemplateID] = c
	ex.ready = [2]bool{false, false}
	return true
}

// unstageCard removes a card from a side's table and resets both ready flags.
func (ex *Exchange) unstageCard(side int, templateID int32) {
	ex.mu.Lock()
	delete(ex.staged[side], templateID)
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

// Accepted reports whether the invited side has answered yes.
//
// SECURITY: this flag was written by setAccepted and never read anywhere, so
// staging cards and readying up both worked BEFORE the invitation was answered.
// Not exploitable alone - a swap still needs both sides to send 5109 - but the
// gate the code appeared to have did not exist, and "an invite you never accepted
// can already hold your trade slot" is the kind of thing that becomes exploitable
// once something else changes.
func (ex *Exchange) Accepted() bool {
	ex.mu.Lock()
	defer ex.mu.Unlock()
	return ex.accepted
}
