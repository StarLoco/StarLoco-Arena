// Package gamedata provides lazily-loaded, indexed, read-only repositories
// over the legacy binary .dat game-data files (cards, spells, effects,
// events, summonings). See go-server/docs/04-game-data-format.md for the
// exact byte layouts these repositories parse.
package gamedata

import (
	"sync"
)

// Identifiable is implemented by every game-data template type so
// Repository can index it by ID.
type Identifiable interface {
	TemplateID() int32
}

// Repository is a generic, lazily-loaded, ID-indexed collection of game
// data templates. The underlying loader function runs at most once (guarded
// by sync.Once), on first access, so a freshly-booted server pays zero
// parse cost for data types no connected player has triggered yet -- see
// docs/01-architecture.md §1.4.2.
type Repository[T Identifiable] struct {
	once sync.Once
	load func() (map[int32]T, error)

	mu   sync.RWMutex
	data map[int32]T
	err  error
}

// NewRepository creates a Repository backed by loadFn, which is invoked at
// most once, lazily, on first Get/All/Len call.
func NewRepository[T Identifiable](loadFn func() (map[int32]T, error)) *Repository[T] {
	return &Repository[T]{load: loadFn}
}

func (r *Repository[T]) ensureLoaded() {
	r.once.Do(func() {
		data, err := r.load()
		r.mu.Lock()
		defer r.mu.Unlock()
		r.data, r.err = data, err
	})
}

// Get returns the template with the given ID, or the zero value and false
// if it doesn't exist or the underlying data failed to load (check Err()
// for the load failure reason).
func (r *Repository[T]) Get(id int32) (T, bool) {
	r.ensureLoaded()
	r.mu.RLock()
	defer r.mu.RUnlock()
	v, ok := r.data[id]
	return v, ok
}

// All returns every loaded template. The returned slice is a fresh copy
// safe for the caller to mutate/retain.
func (r *Repository[T]) All() []T {
	r.ensureLoaded()
	r.mu.RLock()
	defer r.mu.RUnlock()
	out := make([]T, 0, len(r.data))
	for _, v := range r.data {
		out = append(out, v)
	}
	return out
}

// Len returns the number of loaded templates, triggering the load if it
// hasn't happened yet.
func (r *Repository[T]) Len() int {
	r.ensureLoaded()
	r.mu.RLock()
	defer r.mu.RUnlock()
	return len(r.data)
}

// Err returns any error encountered during the (single) load attempt. A
// nil return before the first access simply means loading hasn't been
// triggered yet.
func (r *Repository[T]) Err() error {
	r.mu.RLock()
	defer r.mu.RUnlock()
	return r.err
}

// WarmUp forces the load to happen immediately rather than lazily. Used by
// the optional --warm-cache startup flag (docs/01-architecture.md §1.4.2).
func (r *Repository[T]) WarmUp() error {
	r.ensureLoaded()
	return r.Err()
}
