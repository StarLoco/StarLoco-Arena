package combat

import (
	"sync"
	"sync/atomic"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// Manager owns the registry of active Fight actors, replacing the legacy
// FightManager singleton. Fight IDs are process-unique and monotonically
// increasing, matching the legacy `long id = 1; id++` scheme.
type Manager struct {
	clocks Clocks
	logger zerolog.Logger
	data   *gamedata.Store

	nextID atomic.Int64

	mu     sync.RWMutex
	fights map[int64]*Fight
}

// NewManager creates an empty fight manager. clocks configures the default
// per-phase timers applied to every fight it creates (see
// docs/06-config-and-ops.md's combat.* config keys). data is the gamedata
// store used for spell/card lookups during casting (Phase F) -- may be nil
// in tests that never exercise spell/card casting.
func NewManager(clocks Clocks, data *gamedata.Store, logger zerolog.Logger) *Manager {
	m := &Manager{
		clocks: clocks,
		logger: logger,
		data:   data,
		fights: make(map[int64]*Fight),
	}
	m.nextID.Store(1)
	return m
}

// Create allocates a new Fight populated with teams, registers it, and
// starts its actor goroutine. The fight begins in PhasePresentation.
// broadcaster is how the Fight actor delivers outbound packets back to
// coaches -- see broadcast.go's Broadcaster interface; the dispatch layer
// supplies a concrete implementation backed by world.Registry.
func (m *Manager) Create(fightType byte, teams []*Team, broadcaster Broadcaster) *Fight {
	id := m.nextID.Add(1) - 1

	f := NewFight(id, fightType, m.clocks, teams, broadcaster, m.data, m.logger)

	m.mu.Lock()
	m.fights[id] = f
	m.mu.Unlock()

	go func() {
		f.Run()
		m.mu.Lock()
		delete(m.fights, id)
		m.mu.Unlock()
	}()

	return f
}

// Get looks up an active fight by ID.
func (m *Manager) Get(id int64) (*Fight, bool) {
	m.mu.RLock()
	defer m.mu.RUnlock()
	f, ok := m.fights[id]
	return f, ok
}

// Count returns the number of currently-active fights.
func (m *Manager) Count() int {
	m.mu.RLock()
	defer m.mu.RUnlock()
	return len(m.fights)
}
