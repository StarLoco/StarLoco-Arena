package game

import (
	"sort"
	"sync"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Tournaments in 2.70 are scheduled events. The tournament totem (or the calendar
// menu button) opens a window that fires two empty requests — the calendar (17002)
// and the tournament list (28601) — and the server answers with the events (17003)
// and the registerable tournaments (28602). Clicking "register" sends 4607, which
// we accept with 28608; the bracket button sends 28649, answered by an empty tree
// (28650).
//
// This server offers a set of STANDING tournaments that are always open for
// registration. The live-match layer — opponent search, scheduled fights, bracket
// progression and rewards — is the deferred deep part (it needs many coaches and
// wall-clock scheduling); registration here just records intent and reports it back.
//
// The line-up lives in the database (domain.Tournament) and is edited from the web
// admin console, so changing it no longer needs a rebuild. A fresh database is
// seeded with the three that used to be compiled in.
//
// Each tournament references a REAL client definition id (a data.bdat type-1000
// `aub` record; the retail client ships 22 of them, ids {1,4..24}). The client's
// list/detail/register code dereferences that definition unguarded, so a row whose
// defId is not one of those 22 would NPE the client, and only definitions whose
// inscription card is 0 can ever complete registration here. Both rules are
// enforced where tournaments are created — see internal/web's validateTournament,
// which checks them against the decoded catalogue before anything reaches a player.

// tournamentWindow is how long a standing tournament stays on the calendar / open
// for registration, measured from "now" at request time.
const tournamentWindow = 7 * 24 * time.Hour

// tournamentContentTypeID is the client content id whose prototype decodes a
// tournament calendar entry (data.bdat type-700 record id 4 -> class qr_0). A 17003
// entry must lead with a registered content id or the client's awa_0 decoder NPEs.
const tournamentContentTypeID int32 = 4

// coachStatus values carried by a 28602 row (client enum, lower = deeper round):
// -128 means "not registered" (the register button stays eligible); 0..3 are
// qualified phases. We report a freshly registered coach as "first round".
const (
	tournamentCoachNotRegistered int8 = -128
	tournamentCoachFirstRound    int8 = 3
)

// tournamentRegistrationStore is the slice of the store the manager needs. Kept
// as an interface so the manager can be built without a database (unit tests, and
// any dev run without a store) and so this file does not depend on the store
// package.
type tournamentRegistrationStore interface {
	ListRegistrations() ([]domain.TournamentRegistration, error)
	AddRegistration(coachID uint, tid int64) error
	RemoveRegistration(coachID uint, tid int64) error
}

// TournamentManager records which coaches have registered for which standing
// tournaments, cached in memory and written through to the store. Thread-safe.
//
// It used to be process-lived only, which meant every restart silently
// un-registered everybody — the player saw themselves signed up, the server
// bounced, and their entry was gone with no message (B-101). Registrations are
// keyed by the tournament's WIRE id, which is derived from its row id and so is
// stable across restarts; that is what makes persisting them meaningful.
//
// The store is optional: with none, the manager behaves exactly as it used to.
type TournamentManager struct {
	mu  sync.Mutex
	reg map[uint]map[int64]bool // coachID -> set of registered tournament wire ids
	// ready holds the one entrant waiting for a match in each tournament. It is
	// process-lived on purpose: a "ready" state is a player sitting in front of a
	// waiting dialog, which cannot survive their disconnection anyway.
	ready map[int64]uint // tournament wire id -> waiting coach
	store tournamentRegistrationStore
}

// NewTournamentManager returns an empty, non-persisting registration tracker.
func NewTournamentManager() *TournamentManager {
	return &TournamentManager{reg: make(map[uint]map[int64]bool)}
}

// NewTournamentManagerWithStore returns a tracker primed from the store and
// writing through to it. A load failure is returned rather than swallowed: an
// operator restarting into an empty tournament list should hear about it, not
// discover it from players.
func NewTournamentManagerWithStore(s tournamentRegistrationStore) (*TournamentManager, error) {
	m := &TournamentManager{reg: make(map[uint]map[int64]bool), store: s}
	if s == nil {
		return m, nil
	}
	regs, err := s.ListRegistrations()
	if err != nil {
		return m, err
	}
	for _, r := range regs {
		set := m.reg[r.CoachID]
		if set == nil {
			set = make(map[int64]bool)
			m.reg[r.CoachID] = set
		}
		set[r.TournamentWireID] = true
	}
	return m, nil
}

// Loaded reports how many registrations are held, for the startup log.
func (m *TournamentManager) Loaded() int {
	m.mu.Lock()
	defer m.mu.Unlock()
	n := 0
	for _, set := range m.reg {
		n += len(set)
	}
	return n
}

// Register marks coachID as registered for tid. It is idempotent; the return value
// reports whether this call was the one that added the registration.
func (m *TournamentManager) Register(coachID uint, tid int64) bool {
	m.mu.Lock()
	set := m.reg[coachID]
	if set == nil {
		set = make(map[int64]bool)
		m.reg[coachID] = set
	}
	if set[tid] {
		m.mu.Unlock()
		return false
	}
	set[tid] = true
	store := m.store
	m.mu.Unlock()

	// Write through outside the lock: the store call can block, and nothing else
	// needs to observe the registration atomically with its persistence.
	if store != nil {
		_ = store.AddRegistration(coachID, tid)
	}
	return true
}

// Unregister withdraws coachID from tid. Idempotent; reports whether anything was
// actually removed.
func (m *TournamentManager) Unregister(coachID uint, tid int64) bool {
	m.mu.Lock()
	set := m.reg[coachID]
	if !set[tid] {
		m.mu.Unlock()
		return false
	}
	delete(set, tid)
	if len(set) == 0 {
		delete(m.reg, coachID)
	}
	store := m.store
	m.mu.Unlock()

	if store != nil {
		_ = store.RemoveRegistration(coachID, tid)
	}
	return true
}

// IsRegistered reports whether coachID is registered for tid.
func (m *TournamentManager) IsRegistered(coachID uint, tid int64) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	return m.reg[coachID][tid]
}

// CountFor returns how many coaches are registered for tid. The web console
// shows it next to each tournament, so an admin can see whether anyone would be
// affected before editing or deleting one.
func (m *TournamentManager) CountFor(tid int64) int {
	m.mu.Lock()
	defer m.mu.Unlock()
	n := 0
	for _, set := range m.reg {
		if set[tid] {
			n++
		}
	}
	return n
}

// EntrantsFor returns the coach ids registered for a tournament, ordered so the
// bracket is stable between requests (the client re-asks on every page turn).
//
// Registration order is not recoverable - the tracker is a set - so ids are
// sorted, which at least guarantees the same seeding every time rather than map
// order shuffling the bracket under the player.
func (m *TournamentManager) EntrantsFor(tid int64) []uint {
	m.mu.Lock()
	defer m.mu.Unlock()
	var out []uint
	for coachID, set := range m.reg {
		if set[tid] {
			out = append(out, coachID)
		}
	}
	sort.Slice(out, func(i, j int) bool { return out[i] < out[j] })
	return out
}

// ReadyUp records that a registered entrant is ready to play its next tournament
// match, and returns an opponent if one of the SAME tournament is already
// waiting.
//
// The pairing is deliberately per-tournament rather than through the shared
// matchmaker: a tournament match is a fixture between two entrants of one
// tournament, so pairing across tournaments - or against a coach who never
// registered - would produce a fight that advances nothing. The caller is
// expected to have checked IsRegistered first; this only guards the pairing.
//
// A coach readying twice replaces its own entry rather than matching itself.
func (m *TournamentManager) ReadyUp(tid int64, coachID uint) (uint, bool) {
	m.mu.Lock()
	defer m.mu.Unlock()
	if m.ready == nil {
		m.ready = make(map[int64]uint)
	}
	if waiting, ok := m.ready[tid]; ok && waiting != coachID {
		delete(m.ready, tid)
		return waiting, true
	}
	m.ready[tid] = coachID
	return 0, false
}

// CancelReady removes a coach from every tournament's ready slot.
func (m *TournamentManager) CancelReady(coachID uint) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	found := false
	for tid, waiting := range m.ready {
		if waiting == coachID {
			delete(m.ready, tid)
			found = true
		}
	}
	return found
}

// buildTournamentCalendar builds TOURNAMENT_CALENDAR (17003 awa_0): the scheduled
// events the tournament window shows on its calendar and under "Tournois du jour".
// Each entry is [i32 typeId=4][qr_0 body]; the body is the shared event base
// (iz_0) + the duration base (th_2) + the tournament fields. The registration-period
// list MUST hold at least one pair: the client's qr_0 reads element 0 of it
// unguarded when rendering the "registration period" label.
func buildTournamentCalendar(ts []domain.Tournament) ([]byte, error) {
	now := time.Now()
	w := protocol.NewWriter().U16(uint16(len(ts))) // [i16 count]
	for i := range ts {
		writeTournamentEvent(w, &ts[i], now)
	}
	return protocol.EncodeS2C(protocol.OpTournamentCalendar, w.Bytes())
}

// writeTournamentEvent appends one [i32 typeId][qr_0 body] calendar entry.
//
// The "Tournois du jour" filter (client vk_1.Cd -> de_2.a) reads the two event
// instants with meanings INVERTED relative to their field names: the iz_0
// "startDate" slot (OV) is really the "runs-until / not-yet-over" bound (it must be
// >= now, and >= end-of-today to stay listed for the whole day), while the th_2
// "extraDate" slot (bOF) is the "already-started" bound (it must be <= now / today).
// So the future expiry goes in the startDate slot and the past start goes in the
// extraDate slot; getting this backwards silently drops the event from the list (no
// exception, just filtered out). endDate is unused by the non-recurring filter.
func writeTournamentEvent(w *protocol.Writer, t *domain.Tournament, now time.Time) {
	startedMs := now.Add(-time.Hour).UnixMilli()      // event has already started (extraDate / bOF)
	expireMs := now.Add(tournamentWindow).UnixMilli() // event runs until (startDate slot / OV)
	phaseStartMs := now.Add(time.Hour).UnixMilli()
	phaseEndMs := now.Add(2 * time.Hour).UnixMilli()

	w.I32(tournamentContentTypeID) // [i32 typeId] -> qr_0

	// iz_0 shared event base.
	w.I64(t.WireID()) // eventId (reuse the tournament id)
	w.I64(expireMs)   // "startDate" slot (OV) = runs-until bound: >= end-of-today keeps it listed
	w.I64(expireMs)   // endDate (bhF): unused by the non-recurring filter; mirror OV
	w.I64(0)          // recurrence period: none (single occurrence)
	w.I32(0)          // label index: title is taken from the 28602 row when present

	// th_2 duration base.
	w.I64(startedMs) // extraDate (bOF) = already-started bound: <= now / today

	// qr_0 tournament fields.
	w.I64(t.WireID()) // tournamentId (must equal the 28602 row's id: the client links them)
	w.StringU8(t.Name)
	w.StringU16(t.Description)
	w.StringU8(t.Short)
	w.U8(1).I64(phaseStartMs).I64(phaseEndMs) // one schedule (phase) pair (display only)
	w.U8(1).I64(startedMs).I64(expireMs)      // one registration-period pair (>=1 required)
}

// buildTournamentList builds TOURNAMENT_LIST (28602 ng_2): the registerable
// tournaments, keyed by their client definition id. coachStatus reflects whether
// this coach has already registered, so a re-opened window shows the right state.
func buildTournamentList(coachID uint, tm *TournamentManager, ts []domain.Tournament) ([]byte, error) {
	w := protocol.NewWriter().I32(int32(len(ts))) // [i32 count]
	for i := range ts {
		t := &ts[i]
		status := tournamentCoachNotRegistered
		if tm != nil && tm.IsRegistered(coachID, t.WireID()) {
			status = tournamentCoachFirstRound
		}
		w.I64(t.WireID())
		w.U8(0)                            // openedSearch: opponent search closed
		w.U8(uint8(status))                // coachStatus
		w.U16(t.DefID)                     // tournamentDefinitionId (real aub id)
		w.U8(boolByte(t.RegistrationOpen)) // registrationOpen
		w.I32(0)                           // fightParamCount: no special rules
		w.StringU32(t.Name)
		w.StringU32(t.Description)
		w.StringU32(t.Organizer)
		w.U8(domain.TournamentKindPrivate)
	}
	return protocol.EncodeS2C(protocol.OpTournamentList, w.Bytes())
}
