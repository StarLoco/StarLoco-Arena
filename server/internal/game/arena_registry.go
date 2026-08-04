package game

import (
	"math/rand"
	"sort"
	"sync"
)

// The server supports every arena the client ships: 46 playable fight maps decoded
// from data/maps (see gamedata.LoadFightMaps). Each has its own topology, scenery,
// start cells and special cells, so a Fight carries the arena it is being played on
// rather than the whole package sharing one.
//
// When the map files are absent (a bare checkout, or a unit test) the registry is
// empty and everything falls back to the hand-decoded world 5, which is why
// practiceArena still exists.

var (
	arenaMu    sync.RWMutex
	arenaCache = map[uint16]*arena{}
	arenaIDs   []uint16 // sorted, for deterministic random selection
)

// initArenas converts every decoded fight map into an arena. Safe to call once at
// start-up; a nil or empty set leaves the fallback in place.
func (d *Deps) initArenas() {
	if d == nil || d.FightMaps == nil || d.FightMaps.Len() == 0 {
		return
	}
	arenaMu.Lock()
	defer arenaMu.Unlock()
	arenaCache = make(map[uint16]*arena, d.FightMaps.Len())
	arenaIDs = arenaIDs[:0]
	for _, id := range d.FightMaps.IDs() {
		m := d.FightMaps.Get(id)
		if m == nil {
			continue
		}
		a := newArenaFromMap(m)
		arenaCache[a.worldID] = a
		arenaIDs = append(arenaIDs, a.worldID)
	}
	sort.Slice(arenaIDs, func(i, j int) bool { return arenaIDs[i] < arenaIDs[j] })
}

// arenaByID returns the arena for a world id, or the fallback when unknown.
func arenaByID(id uint16) *arena {
	arenaMu.RLock()
	defer arenaMu.RUnlock()
	if a := arenaCache[id]; a != nil {
		return a
	}
	return &practiceArena
}

// pickArena chooses an arena for a new fight. With map data loaded this rotates
// over every shipped arena, so fights are not all played on world 5.
func pickArena() *arena {
	arenaMu.RLock()
	defer arenaMu.RUnlock()
	if len(arenaIDs) == 0 {
		return &practiceArena
	}
	return arenaCache[arenaIDs[rand.Intn(len(arenaIDs))]]
}

// loadedArenaCount reports how many arenas the registry holds (0 = fallback only).
func loadedArenaCount() int {
	arenaMu.RLock()
	defer arenaMu.RUnlock()
	return len(arenaIDs)
}
