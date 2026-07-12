// Package world holds process-wide online-player state: the registry of
// currently-connected coaches and the opponent-matchmaking queue. This
// replaces the legacy Java World singleton, whose plain ArrayList<Coach>
// was mutated from concurrent MINA I/O threads with no synchronization (a
// real data race) -- see docs/01-architecture.md §1.3.
package world

import (
	"strings"
	"sync"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
)

// OnlineCoach bundles a connected coach's live domain state with the
// Session used to deliver packets to them. Holding the *domain.Coach here
// (rather than just an ID) lets broadcast packet builders (ACTOR_SPAWN,
// friend online-status) access look/position/equipment without a DB
// round-trip per broadcast, mirroring how the legacy World held live Coach
// object references directly.
type OnlineCoach struct {
	Coach   *domain.Coach
	Session *netio.Session
}

// ID is a convenience accessor for Coach.ID.
func (o *OnlineCoach) ID() uint { return o.Coach.ID }

// Name is a convenience accessor for Coach.Name.
func (o *OnlineCoach) Name() string { return o.Coach.Name }

// Registry is the thread-safe online-coach directory. All access is
// synchronized via a RWMutex protecting a plain map -- with the expected
// player counts for a niche PvP arena server, this is simpler and more than
// fast enough; a sync.Map would only help under heavy write contention that
// doesn't apply here.
type Registry struct {
	mu   sync.RWMutex
	byID map[uint]*OnlineCoach
}

// NewRegistry creates an empty online-coach registry.
func NewRegistry() *Registry {
	return &Registry{byID: make(map[uint]*OnlineCoach)}
}

// Add registers a coach as online. Returns false if that coach ID was
// already registered (caller should treat this as a duplicate-login race).
func (r *Registry) Add(coach *OnlineCoach) bool {
	r.mu.Lock()
	defer r.mu.Unlock()
	if _, exists := r.byID[coach.ID()]; exists {
		return false
	}
	r.byID[coach.ID()] = coach
	return true
}

// Remove unregisters a coach.
func (r *Registry) Remove(coachID uint) {
	r.mu.Lock()
	defer r.mu.Unlock()
	delete(r.byID, coachID)
}

// Get looks up an online coach by ID.
func (r *Registry) Get(coachID uint) (*OnlineCoach, bool) {
	r.mu.RLock()
	defer r.mu.RUnlock()
	c, ok := r.byID[coachID]
	return c, ok
}

// GetByName looks up an online coach by name, case-insensitively, mirroring
// the legacy World lookups used by private/vicinity chat
// (`c.getName().equalsIgnoreCase(name)`).
func (r *Registry) GetByName(name string) (*OnlineCoach, bool) {
	r.mu.RLock()
	defer r.mu.RUnlock()
	for _, c := range r.byID {
		if strings.EqualFold(c.Name(), name) {
			return c, true
		}
	}
	return nil, false
}

// IsOnline reports whether coachID is currently registered.
func (r *Registry) IsOnline(coachID uint) bool {
	_, ok := r.Get(coachID)
	return ok
}

// Snapshot returns a copy of all currently-online coaches, safe for the
// caller to range over without holding the registry lock (e.g. while
// building a broadcast packet).
func (r *Registry) Snapshot() []*OnlineCoach {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]*OnlineCoach, 0, len(r.byID))
	for _, c := range r.byID {
		out = append(out, c)
	}
	return out
}

// SnapshotWithout is like Snapshot but excludes the given coach ID, useful
// for "broadcast to everyone else" (see ACTOR_DESPAWN, vicinity chat).
func (r *Registry) SnapshotWithout(excludeCoachID uint) []*OnlineCoach {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]*OnlineCoach, 0, len(r.byID))
	for id, c := range r.byID {
		if id == excludeCoachID {
			continue
		}
		out = append(out, c)
	}
	return out
}

// Len returns the number of currently-online coaches.
func (r *Registry) Len() int {
	r.mu.RLock()
	defer r.mu.RUnlock()
	return len(r.byID)
}
