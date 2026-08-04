package game

import "sync"

// SessionRegistry tracks live sessions by account id so a new login can kick a
// stale one (last-writer-wins), which is essential when a client reconnects
// after a network drop before the server has noticed the old socket died.
type SessionRegistry struct {
	mu     sync.Mutex
	byAcct map[uint]*Session
}

// NewSessionRegistry creates an empty registry.
func NewSessionRegistry() *SessionRegistry {
	return &SessionRegistry{byAcct: make(map[uint]*Session)}
}

// Swap registers s for accountID and returns the previously-registered session
// (to be kicked), or nil.
func (r *SessionRegistry) Swap(accountID uint, s *Session) *Session {
	r.mu.Lock()
	defer r.mu.Unlock()
	old := r.byAcct[accountID]
	r.byAcct[accountID] = s
	return old
}

// Remove deregisters s for accountID only if s is still the current session
// (a newer login may have replaced it). Returns true if this session was the
// current one (i.e. the caller owns the account/coach teardown), false if it
// was already replaced.
func (r *SessionRegistry) Remove(accountID uint, s *Session) bool {
	r.mu.Lock()
	defer r.mu.Unlock()
	if r.byAcct[accountID] == s {
		delete(r.byAcct, accountID)
		return true
	}
	return false
}

// Count returns the number of live account sessions (for metrics).
func (r *SessionRegistry) Count() int {
	r.mu.Lock()
	defer r.mu.Unlock()
	return len(r.byAcct)
}

// Each snapshots the live sessions and invokes fn for each, returning the count.
// Used by the dev packet-inject endpoint to reach the connected client(s).
func (r *SessionRegistry) Each(fn func(*Session)) int {
	r.mu.Lock()
	sessions := make([]*Session, 0, len(r.byAcct))
	for _, s := range r.byAcct {
		sessions = append(sessions, s)
	}
	r.mu.Unlock()
	for _, s := range sessions {
		fn(s)
	}
	return len(sessions)
}
