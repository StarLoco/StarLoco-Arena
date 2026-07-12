package world

import (
	"math/rand"
	"sync"
)

// ExchangeCardEntry is one card offered by a side of an exchange, keyed by
// card template ID (mirroring the legacy CoachExchange's
// Map<CoachCard, Short>, which is effectively keyed by template identity
// via getEntry's linear scan).
type ExchangeCardEntry struct {
	CoachCardID uint // the offering coach's CoachCard instance ID
	TemplateID  int32
	Flag        uint8
	Quantity    int16
}

// Exchange is an in-progress item trade between two coaches, mirroring the
// legacy CoachExchange.
type Exchange struct {
	ID     int64
	FromID uint
	ToID   uint

	mu        sync.Mutex
	cardsFrom map[int32]*ExchangeCardEntry // keyed by TemplateID
	cardsTo   map[int32]*ExchangeCardEntry
	readyFrom bool
	readyTo   bool
}

func newExchange(from, to uint) *Exchange {
	return &Exchange{
		// The legacy server uses `new Random().nextLong()` for exchange IDs
		// (CoachExchange.java:22) rather than a sequential counter --
		// preserved here since the ID is opaque to the client and this
		// avoids adding a second ID-allocation scheme to ExchangeManager.
		ID:        rand.Int63(),
		FromID:    from,
		ToID:      to,
		cardsFrom: make(map[int32]*ExchangeCardEntry),
		cardsTo:   make(map[int32]*ExchangeCardEntry),
	}
}

// InvolvesCoach reports whether coachID is a participant.
func (e *Exchange) InvolvesCoach(coachID uint) bool {
	return coachID == e.FromID || coachID == e.ToID
}

// IsInitiator reports whether coachID is the "from" side (the one who sent
// the original invitation).
func (e *Exchange) IsInitiator(coachID uint) bool {
	return coachID == e.FromID
}

// AddCard adds or stacks a card offer on behalf of coachID, mirroring
// CoachExchange.addCard's clamping behavior (quantity clamped to
// [1, card.Quantity]).
func (e *Exchange) AddCard(coachID uint, entry ExchangeCardEntry, ownedQuantity int16) ExchangeCardEntry {
	e.mu.Lock()
	defer e.mu.Unlock()

	cards := e.sideFor(coachID)
	if existing, ok := cards[entry.TemplateID]; ok {
		entry.Quantity += existing.Quantity
	}
	if entry.Quantity > ownedQuantity {
		entry.Quantity = ownedQuantity
	}
	if entry.Quantity <= 0 {
		entry.Quantity = 1
	}
	cards[entry.TemplateID] = &entry
	// Any card change invalidates prior ready state, mirroring the fact
	// that the legacy client requires re-confirming readiness after the
	// offer changes (enforced client-side there; enforced here server-side
	// for correctness).
	e.readyFrom, e.readyTo = false, false
	return entry
}

// RemoveCard reduces or removes a card offer, mirroring
// CoachExchange.removeCard. Returns false if the template wasn't offered.
func (e *Exchange) RemoveCard(coachID uint, templateID int32, quantity int16) bool {
	e.mu.Lock()
	defer e.mu.Unlock()

	cards := e.sideFor(coachID)
	existing, ok := cards[templateID]
	if !ok {
		return false
	}
	remaining := existing.Quantity - quantity
	delete(cards, templateID)
	if remaining > 0 {
		existing.Quantity = remaining
		cards[templateID] = existing
	}
	e.readyFrom, e.readyTo = false, false
	return true
}

func (e *Exchange) sideFor(coachID uint) map[int32]*ExchangeCardEntry {
	if coachID == e.FromID {
		return e.cardsFrom
	}
	return e.cardsTo
}

// SetReady toggles (matching the legacy `!isReadyFrom()` toggle behavior)
// the ready flag for coachID's side and returns whether both sides are now
// ready.
func (e *Exchange) SetReady(coachID uint) (isFromSide bool, bothReady bool) {
	e.mu.Lock()
	defer e.mu.Unlock()

	if coachID == e.FromID {
		e.readyFrom = !e.readyFrom
		return true, e.readyFrom && e.readyTo
	}
	e.readyTo = !e.readyTo
	return false, e.readyFrom && e.readyTo
}

// Offers returns a snapshot of both sides' current card offers.
func (e *Exchange) Offers() (from, to []ExchangeCardEntry) {
	e.mu.Lock()
	defer e.mu.Unlock()
	for _, c := range e.cardsFrom {
		from = append(from, *c)
	}
	for _, c := range e.cardsTo {
		to = append(to, *c)
	}
	return from, to
}

// ExchangeManager tracks all in-progress item exchanges, replacing the
// legacy World.coachExchanges list (which was accessed unsynchronized from
// concurrent connection threads).
type ExchangeManager struct {
	mu        sync.RWMutex
	exchanges map[int64]*Exchange
}

// NewExchangeManager creates an empty exchange registry.
func NewExchangeManager() *ExchangeManager {
	return &ExchangeManager{exchanges: make(map[int64]*Exchange)}
}

// Start creates and registers a new Exchange from `from` to `to`.
func (m *ExchangeManager) Start(from, to uint) *Exchange {
	e := newExchange(from, to)
	m.mu.Lock()
	m.exchanges[e.ID] = e
	m.mu.Unlock()
	return e
}

// StartExclusive atomically starts a new exchange from->to ONLY if neither
// coach is already in an exchange AND the caller-supplied externallyBusy
// predicate returns false for both coaches. The busy-check and the insert
// happen under the SAME held lock, closing the TOCTOU window that a
// separate HasCoach()-then-Start() pair leaves open (two concurrent
// invitations could otherwise both pass the check and both create an
// exchange for the same coach, re-enabling the duplication vector).
//
// externallyBusy lets the caller also reject a coach busy in another
// subsystem (e.g. an in-progress duel/fight) as part of the same atomic
// decision. It is invoked while m.mu is held, so it MUST NOT call back into
// this ExchangeManager (it only needs to consult the duel manager, a
// different lock).
//
// Returns (nil, false) if either coach is busy; (exchange, true) otherwise.
func (m *ExchangeManager) StartExclusive(from, to uint, externallyBusy func(coachID uint) bool) (*Exchange, bool) {
	m.mu.Lock()
	defer m.mu.Unlock()
	for _, e := range m.exchanges {
		if e.InvolvesCoach(from) || e.InvolvesCoach(to) {
			return nil, false
		}
	}
	if externallyBusy != nil && (externallyBusy(from) || externallyBusy(to)) {
		return nil, false
	}
	e := newExchange(from, to)
	m.exchanges[e.ID] = e
	return e, true
}

// Get looks up an exchange by ID.
func (m *ExchangeManager) Get(id int64) (*Exchange, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	e, ok := m.exchanges[id]
	return e, ok
}

// GetByCoach finds an active exchange involving coachID, mirroring
// World.getCoachExchangeById's actual lookup-by-coach behavior (the
// exchangeId parameter is accepted by the legacy method but never
// actually used to filter -- confirmed by reading
// World.java:51-53 -- so this preserves that exact, somewhat surprising
// behavior: any exchangeId value finds *a* matching exchange for that
// coach, not necessarily the one whose ID was passed).
func (m *ExchangeManager) GetByCoach(coachID uint) (*Exchange, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	for _, e := range m.exchanges {
		if e.InvolvesCoach(coachID) {
			return e, true
		}
	}
	return nil, false
}

// Remove unregisters an exchange (completed, canceled, or aborted).
// Returns true if the exchange was present and is now removed, false if it
// had already been removed -- callers use this as an idempotency guard so a
// racing double both-ready can't trigger the card transfer twice.
func (m *ExchangeManager) Remove(id int64) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	if _, ok := m.exchanges[id]; !ok {
		return false
	}
	delete(m.exchanges, id)
	return true
}
