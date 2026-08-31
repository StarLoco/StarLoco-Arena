package game

import (
	"strings"
	"sync"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// Online bundles a live coach with its session and its area-of-interest state.
type Online struct {
	Coach   *domain.Coach
	Session *Session
	inFight bool
	// sitting is the coach's sit state (client flag dBg / 0x40 in the actor blob,
	// toggled in bulk by 4601). In-memory only: standing up on relog is the
	// friendlier default and nothing persists it retail-side either.
	sitting bool
	// known is the set of other coach ids this coach currently has spawned in
	// its client (its area of interest). Guarded by the registry lock.
	known map[uint]bool
}

// CoachView is an immutable snapshot of an online coach's renderable fields,
// taken under the registry lock — safe to serialize for broadcasts without
// racing the owner's mutations.
type CoachView struct {
	ID   uint
	Name string
	Hair uint8
	Skin uint8
	Sex  uint8
	PosX int32
	PosY int32
	PosZ int16
	// Standing is the coach's evolution experience. The client turns it into the
	// evolution LEVEL shown next to the name, so a hardcoded 0 made every other
	// coach in the world render as level 1.
	Standing int32
	// Sitting drives the actor blob's dBg byte, which makes the client play
	// AnimAssis-Debut the moment the coach spawns into someone's view.
	Sitting bool
}

// Registry tracks online coaches for presence, spawn fan-out and chat.
type Registry struct {
	mu   sync.RWMutex
	byID map[uint]*Online

	// aoiRadius is the area-of-interest radius in cells. Overworld events reach
	// only coaches within this Euclidean distance of the source. 0 = unlimited
	// (broadcast to everyone).
	aoiRadius int
}

// NewRegistry creates an empty registry with the given AoI radius (cells).
func NewRegistry(aoiRadius int) *Registry {
	return &Registry{byID: make(map[uint]*Online), aoiRadius: aoiRadius}
}

// Add registers an online coach. Returns false if that id is already online
// (duplicate login guard).
func (r *Registry) Add(o *Online) bool {
	r.mu.Lock()
	defer r.mu.Unlock()
	if _, exists := r.byID[o.Coach.ID]; exists {
		return false
	}
	if o.known == nil {
		o.known = make(map[uint]bool)
	}
	r.byID[o.Coach.ID] = o
	return true
}

// Remove deregisters a coach by id.
func (r *Registry) Remove(id uint) {
	r.mu.Lock()
	delete(r.byID, id)
	r.mu.Unlock()
}

// Get returns the online coach by id, or nil.
func (r *Registry) Get(id uint) *Online {
	r.mu.RLock()
	defer r.mu.RUnlock()
	return r.byID[id]
}

// GetByName finds an online coach by case-insensitive name.
func (r *Registry) GetByName(name string) *Online {
	r.mu.RLock()
	defer r.mu.RUnlock()
	for _, o := range r.byID {
		if strings.EqualFold(o.Coach.Name, name) {
			return o
		}
	}
	return nil
}

// Len reports how many coaches are currently online.
func (r *Registry) Len() int {
	r.mu.RLock()
	defer r.mu.RUnlock()
	return len(r.byID)
}

// IsOnline reports whether a coach id is currently online.
func (r *Registry) IsOnline(id uint) bool {
	r.mu.RLock()
	defer r.mu.RUnlock()
	_, ok := r.byID[id]
	return ok
}

// viewOf builds an immutable view (caller must hold at least RLock).
func viewOf(o *Online) CoachView {
	c := o.Coach
	return CoachView{
		ID: c.ID, Name: c.Name, Hair: c.Hair, Skin: c.Skin, Sex: c.Sex,
		PosX: c.PosX, PosY: c.PosY, PosZ: c.PosZ, Standing: c.Standing,
		Sitting: o.sitting,
	}
}

// inAoI reports whether a coach at (ox,oy) is within the AoI radius of (x,y).
// radius 0 = unlimited. Uses squared Euclidean distance (no sqrt).
func (r *Registry) inAoI(x, y, ox, oy int32) bool {
	if r.aoiRadius <= 0 {
		return true
	}
	dx := int64(ox - x)
	dy := int64(oy - y)
	rad := int64(r.aoiRadius)
	return dx*dx+dy*dy <= rad*rad
}

// ViewsNear returns snapshots of overworld coaches within the AoI radius of
// (x,y), excluding excludeID.
func (r *Registry) ViewsNear(x, y int32, excludeID uint) []CoachView {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]CoachView, 0, 16)
	for id, o := range r.byID {
		if id == excludeID || o.inFight {
			continue
		}
		if r.inAoI(x, y, o.Coach.PosX, o.Coach.PosY) {
			out = append(out, viewOf(o))
		}
	}
	return out
}

// SessionsNear returns the sessions of overworld coaches within the AoI radius
// of (x,y), excluding excludeID (for scoped broadcasts).
func (r *Registry) SessionsNear(x, y int32, excludeID uint) []*Session {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]*Session, 0, 16)
	for id, o := range r.byID {
		if id == excludeID || o.inFight {
			continue
		}
		if r.inAoI(x, y, o.Coach.PosX, o.Coach.PosY) {
			out = append(out, o.Session)
		}
	}
	return out
}

// SessionsWithout returns the sessions of EVERY overworld coach except
// excludeID (unscoped). Used for despawn on disconnect, where the leaver's
// position may already be gone -- everyone who might see them is notified.
func (r *Registry) SessionsWithout(excludeID uint) []*Session {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]*Session, 0, len(r.byID))
	for id, o := range r.byID {
		if id == excludeID || o.inFight {
			continue
		}
		out = append(out, o.Session)
	}
	return out
}

// allSessions returns every online session (including in-fight).
func (r *Registry) allSessions() []*Session {
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]*Session, 0, len(r.byID))
	for _, o := range r.byID {
		out = append(out, o.Session)
	}
	return out
}

// UpdatePosition records a coach's new world position under lock.
func (r *Registry) UpdatePosition(id uint, x, y int32, z int16) {
	r.mu.Lock()
	if o := r.byID[id]; o != nil {
		o.Coach.PosX, o.Coach.PosY, o.Coach.PosZ = x, y, z
	}
	r.mu.Unlock()
}

// SetInFight marks/unmarks a coach as in-fight (excluded from overworld
// broadcasts while true). Entering a fight clears the coach's AoI known set and
// unlinks it from others (so returning to the world re-seeds cleanly). When
// entering a fight it returns the sessions that currently see the coach, so the
// caller can despawn it from the overworld.
func (r *Registry) SetInFight(id uint, inFight bool) []*Session {
	r.mu.Lock()
	defer r.mu.Unlock()
	o := r.byID[id]
	if o == nil {
		return nil
	}
	o.inFight = inFight
	if !inFight {
		return nil
	}
	var viewers []*Session
	for otherID := range o.known {
		if other := r.byID[otherID]; other != nil {
			delete(other.known, id)
			viewers = append(viewers, other.Session)
		}
	}
	o.known = make(map[uint]bool)
	return viewers
}

// AoIDelta describes the visibility changes caused by a coach's move, so the
// caller can send the exact SPAWN/DESPAWN frames.
type AoIDelta struct {
	// MoverSession is the moving coach's session.
	MoverSession *Session
	// SpawnToMover are coaches now visible to the mover (send ACTOR_SPAWN of
	// these to the mover).
	SpawnToMover []CoachView
	// DespawnToMover are coach ids no longer visible to the mover.
	DespawnToMover []uint
	// MoverView is the mover's own renderable snapshot (to spawn into others).
	MoverView CoachView
	// SpawnMoverTo are sessions that just gained sight of the mover (send the
	// mover's ACTOR_SPAWN to them).
	SpawnMoverTo []*Session
	// DespawnMoverFrom are sessions that lost sight of the mover.
	DespawnMoverFrom []*Session
	// MoveViewers are sessions that already see the mover and should receive the
	// ACTOR_MOVEMENT (they have the mover spawned).
	MoveViewers []*Session
}

// ApplyMove updates the mover's position and computes the area-of-interest diff
// (who enters/leaves whose view) in one locked pass. It maintains the bilateral
// "known" sets so spawn/despawn are only sent on boundary crossings.
func (r *Registry) ApplyMove(moverID uint, x, y int32, z int16) AoIDelta {
	r.mu.Lock()
	defer r.mu.Unlock()

	mover := r.byID[moverID]
	if mover == nil {
		return AoIDelta{}
	}
	mover.Coach.PosX, mover.Coach.PosY, mover.Coach.PosZ = x, y, z

	d := AoIDelta{MoverSession: mover.Session, MoverView: viewOf(mover)}

	for id, o := range r.byID {
		if id == moverID || o.inFight || mover.inFight {
			continue
		}
		inRange := r.inAoI(x, y, o.Coach.PosX, o.Coach.PosY)
		known := mover.known[id]
		switch {
		case inRange && !known:
			// Entered range: mutual spawn.
			mover.known[id] = true
			o.known[moverID] = true
			d.SpawnToMover = append(d.SpawnToMover, viewOf(o))
			d.SpawnMoverTo = append(d.SpawnMoverTo, o.Session)
			d.MoveViewers = append(d.MoveViewers, o.Session)
		case !inRange && known:
			// Left range: mutual despawn.
			delete(mover.known, id)
			delete(o.known, moverID)
			d.DespawnToMover = append(d.DespawnToMover, id)
			d.DespawnMoverFrom = append(d.DespawnMoverFrom, o.Session)
		case inRange && known:
			// Still visible: they should see the move animation.
			d.MoveViewers = append(d.MoveViewers, o.Session)
		}
	}
	return d
}

// EnterAoI seeds a joining coach's known set: returns the coaches already in
// range (to spawn to the joiner) and the sessions that should spawn the joiner.
// Establishes the bilateral known links.
func (r *Registry) EnterAoI(coachID uint) (spawnToJoiner []CoachView, joinerView CoachView, spawnJoinerTo []*Session) {
	r.mu.Lock()
	defer r.mu.Unlock()
	joiner := r.byID[coachID]
	if joiner == nil {
		return nil, CoachView{}, nil
	}
	joinerView = viewOf(joiner)
	for id, o := range r.byID {
		if id == coachID || o.inFight {
			continue
		}
		if r.inAoI(joiner.Coach.PosX, joiner.Coach.PosY, o.Coach.PosX, o.Coach.PosY) {
			joiner.known[id] = true
			o.known[coachID] = true
			spawnToJoiner = append(spawnToJoiner, viewOf(o))
			spawnJoinerTo = append(spawnJoinerTo, o.Session)
		}
	}
	return spawnToJoiner, joinerView, spawnJoinerTo
}

// LeaveAoI clears a leaving coach from all known sets and returns the sessions
// that currently see it (to send a despawn).
// SetSitting records a coach's sit state and reports whether it CHANGED, so a
// caller can skip broadcasting a no-op (standing up when already standing).
func (r *Registry) SetSitting(coachID uint, sitting bool) bool {
	r.mu.Lock()
	defer r.mu.Unlock()
	o := r.byID[coachID]
	if o == nil || o.sitting == sitting {
		return false
	}
	o.sitting = sitting
	return true
}

// IsSitting reports a coach's sit state.
func (r *Registry) IsSitting(coachID uint) bool {
	r.mu.RLock()
	defer r.mu.RUnlock()
	o := r.byID[coachID]
	return o != nil && o.sitting
}

// ViewersOf returns the sessions whose client currently HAS this coach spawned,
// i.e. those whose AoI known-set contains it. Non-destructive.
//
// Use this, not SessionsNear, for any frame that makes the client look an actor
// up by id. SessionsNear is raw proximity and can include a session that never
// received an ActorSpawn for the coach. Walking DOES keep AoI in step (ApplyMove
// computes the enter/leave diff), but the teleports do not: /TP, Zaap travel and
// /resetPosition go through UpdatePosition, which only records coordinates, and
// re-seed AoI via a full EnterAoI. Between those two mechanisms proximity and
// membership can disagree. The retail client does not guard
// those lookups: `no_2` case 4700 dereferences the result of `bd_1.Is().bb(id)`
// with no null check, so an unknown actor id throws NullPointerException and the
// whole message is dropped - verified live against the retail client.
func (r *Registry) ViewersOf(coachID uint) []*Session {
	r.mu.RLock()
	defer r.mu.RUnlock()
	var viewers []*Session
	for id, o := range r.byID {
		if id == coachID || o.inFight {
			continue
		}
		if o.known[coachID] && o.Session != nil {
			viewers = append(viewers, o.Session)
		}
	}
	return viewers
}
func (r *Registry) LeaveAoI(coachID uint) []*Session {
	r.mu.Lock()
	defer r.mu.Unlock()
	var viewers []*Session
	for id, o := range r.byID {
		if id == coachID {
			continue
		}
		if o.known[coachID] {
			delete(o.known, coachID)
			viewers = append(viewers, o.Session)
		}
	}
	return viewers
}
