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

// CoachView is an immutable value snapshot of an online coach's mutable
// display state, copied under the registry lock so broadcast packet
// builders can serialize it lock-free without racing the goroutines that
// mutate the underlying shared *domain.Coach.
//
// Why this exists: OnlineCoach.Coach is ONE shared pointer per player,
// aliased by the session cache, the registry, and the fight-end hook (see
// docs/01-architecture.md and internal/dispatch). The registry RWMutex
// only ever protected the map, NOT the fields of the Coach objects it
// handed out by pointer -- so a broadcast reading coach.Strength/PosX/
// equipment on one goroutine raced a concurrent fight-end (writing stats)
// or a movement handler (writing position) on another. Returning value
// copies taken under the read lock closes that race at the boundary while
// keeping serialization off the lock (copy under lock, serialize after).
type CoachView struct {
	ID   uint
	Name string
	Skin uint8
	Hair uint8
	Sex  uint8

	PosX int32
	PosY int32
	PosZ int16

	Strength          int32
	StatFights        int32
	StatWins          int32
	StatLosses        int32
	ConsecutiveWins   int32
	TimeInFightSecs   int64
	TotalPlayTimeSecs int64

	// Equipped is a COPY of the coach's equipped (Pos != 0) cards, so the
	// caller may retain/range it without aliasing the live inventory slice.
	Equipped []domain.CoachCard
}

// viewOfLocked builds a CoachView from a live OnlineCoach. MUST be called
// with r.mu held (read or write).
func viewOfLocked(oc *OnlineCoach) CoachView {
	c := oc.Coach
	v := CoachView{
		ID: c.ID, Name: c.Name,
		Skin: c.Skin, Hair: c.Hair, Sex: c.Sex,
		PosX: c.PosX, PosY: c.PosY, PosZ: c.PosZ,
		Strength: c.Strength, StatFights: c.StatFights, StatWins: c.StatWins,
		StatLosses: c.StatLosses, ConsecutiveWins: c.ConsecutiveWins,
		TimeInFightSecs: c.TimeInFightSecs, TotalPlayTimeSecs: c.TotalPlayTimeSecs,
	}
	for _, card := range c.Inventory {
		if card.Pos != 0 {
			v.Equipped = append(v.Equipped, card)
		}
	}
	return v
}

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

// Snapshot returns a copy of the online-coach POINTER slice, safe for the
// caller to range over without holding the registry lock -- e.g. to fan a
// pre-built frame out to every oc.Session.
//
// IMPORTANT: the returned *OnlineCoach values still alias the live shared
// *domain.Coach objects. Only read IMMUTABLE fields (ID, Name, Session)
// off them. To read MUTABLE fields (position, strength/stats, inventory)
// for serialization, use SnapshotViews/ViewOf instead, which copy those
// fields under the lock -- reading them here would race concurrent
// fight-end/movement writes (see CoachView).
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
// for "broadcast to everyone else" (see ACTOR_DESPAWN, vicinity chat). The
// same immutable-fields-only caveat as Snapshot applies -- use
// SnapshotViewsWithout to read mutable coach fields.
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

// ViewOf returns an immutable value snapshot of one online coach's mutable
// display state, taken under the read lock. Use this instead of reading
// fields off Get(id).Coach directly whenever the read happens on a
// goroutine other than that coach's own (e.g. a broadcast or a post-fight
// background task), which would otherwise race a concurrent field write.
func (r *Registry) ViewOf(coachID uint) (CoachView, bool) {
	r.mu.RLock()
	defer r.mu.RUnlock()
	oc, ok := r.byID[coachID]
	if !ok {
		return CoachView{}, false
	}
	return viewOfLocked(oc), true
}

// SnapshotViews returns immutable value snapshots of every online coach,
// taken under the read lock -- the race-safe replacement for Snapshot()
// when the caller will read coach fields (position/strength/equipment) to
// build a broadcast packet.
func (r *Registry) SnapshotViews() []CoachView {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]CoachView, 0, len(r.byID))
	for _, oc := range r.byID {
		out = append(out, viewOfLocked(oc))
	}
	return out
}

// SnapshotViewsWithout is SnapshotViews excluding one coach ID (the
// "everyone else" broadcast audience, e.g. ACTOR_SPAWN of every other
// online coach).
func (r *Registry) SnapshotViewsWithout(excludeCoachID uint) []CoachView {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]CoachView, 0, len(r.byID))
	for id, oc := range r.byID {
		if id == excludeCoachID {
			continue
		}
		out = append(out, viewOfLocked(oc))
	}
	return out
}

// UpdatePosition writes an online coach's world position under the write
// lock, so it can't race a concurrent broadcast reading that coach's
// position via a view. No-op if the coach is offline. The coach pointer is
// shared with the caller's session cache, so this updates that too (same
// object).
func (r *Registry) UpdatePosition(coachID uint, x, y int32, z int16) {
	r.mu.Lock()
	defer r.mu.Unlock()
	if oc, ok := r.byID[coachID]; ok {
		oc.Coach.PosX, oc.Coach.PosY, oc.Coach.PosZ = x, y, z
	}
}

// UpdateStats copies the persisted ladder/statistics fields from a freshly
// loaded coach onto the live online coach under the write lock, so a
// fight-end stat write can't race a concurrent broadcast/stats read via a
// view. Only the stat/ladder fields are touched; position and inventory
// are left intact. No-op if the coach is offline.
func (r *Registry) UpdateStats(coachID uint, from *domain.Coach) {
	r.mu.Lock()
	defer r.mu.Unlock()
	oc, ok := r.byID[coachID]
	if !ok {
		return
	}
	c := oc.Coach
	c.Strength = from.Strength
	c.StatFights = from.StatFights
	c.StatWins = from.StatWins
	c.StatLosses = from.StatLosses
	c.ConsecutiveWins = from.ConsecutiveWins
	c.TimeInFightSecs = from.TimeInFightSecs
	c.TotalPlayTimeSecs = from.TotalPlayTimeSecs
}

// UpdateInventory replaces the live online coach's cached card inventory
// under the write lock, keeping the in-memory copy authoritative after a
// DB-side card change (equip/unequip, lock/unlock, remove, wager transfer)
// so broadcast serializers (ACTOR_SPAWN's equipped list) reflect current
// state instead of the login-time snapshot. No-op if the coach is offline.
func (r *Registry) UpdateInventory(coachID uint, inventory []domain.CoachCard) {
	r.mu.Lock()
	defer r.mu.Unlock()
	if oc, ok := r.byID[coachID]; ok {
		oc.Coach.Inventory = inventory
	}
}

// Len returns the number of currently-online coaches.
func (r *Registry) Len() int {
	r.mu.RLock()
	defer r.mu.RUnlock()
	return len(r.byID)
}
