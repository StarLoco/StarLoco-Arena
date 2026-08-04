package game

import (
	"sync"
	"time"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Tournaments in 2.70 are scheduled events. The tournament totem (or the calendar
// menu button) opens a window that fires two empty requests — the calendar (17002)
// and the tournament list (28601) — and the server answers with the events (17003)
// and the registerable tournaments (28602). Clicking "register" sends 4607, which
// we accept with 28608; the bracket button sends 28649, answered by an empty tree
// (28650).
//
// This server offers a fixed set of STANDING tournaments that are always open for
// registration. The live-match layer — opponent search, scheduled fights, bracket
// progression and rewards — is the deferred deep part (it needs many coaches and
// wall-clock scheduling); registration here just records intent and reports it back.
//
// Each standing tournament references a REAL client definition id (a data.bdat
// type-1000 `aub` record; the retail client ships 22 of them, ids {1,4..24}). The
// client's list/detail/register code dereferences that definition unguarded, so a
// row whose defId is not one of those 22 would NPE the client — this table must use
// only real ids. We use only definitions whose referenceCardId == 0 (no card needed
// to join) so registration always completes: defIds {1,4,17} below all qualify.

// tournamentBaseID keeps our synthetic tournament handles clear of coach / fighter
// wire ids. The value only has to be a stable, unique, non-zero int64.
const tournamentBaseID int64 = 2_600_000

// tournamentWindow is how long a standing tournament stays on the calendar / open
// for registration, measured from "now" at request time.
const tournamentWindow = 7 * 24 * time.Hour

// tournamentContentTypeID is the client content id whose prototype decodes a
// tournament calendar entry (data.bdat type-700 record id 4 -> class qr_0). A 17003
// entry must lead with a registered content id or the client's awa_0 decoder NPEs.
const tournamentContentTypeID int32 = 4

// tournamentOrganizer is the organiser label shown on each tournament row.
const tournamentOrganizer = "StarLoco"

// coachStatus values carried by a 28602 row (client enum, lower = deeper round):
// -128 means "not registered" (the register button stays eligible); 0..3 are
// qualified phases. We report a freshly registered coach as "first round".
const (
	tournamentCoachNotRegistered int8 = -128
	tournamentCoachFirstRound    int8 = 3
)

// standingTournament is one server-offered tournament.
type standingTournament struct {
	id    int64  // wire handle (our own); must be stable and unique
	defID uint16 // client definition id (aub.Bw()); referenceCardId MUST be 0
	kind  uint8  // 28602 wire kind (ks_1): 1 = private — registerable, no auto search, no bracket
	name  string // display name (<=127 bytes: the qr_0 name field is i8-length-prefixed)
	desc  string // description / rules
	short string // short label
}

// standingTournamentTable is the fixed catalogue. defIDs are real no-card client
// definitions; kind 1 (private) keeps each one a simple registerable event that
// does not pull the client into the opponent-search or bracket flows we do not run.
var standingTournamentTable = []standingTournament{
	{
		id: tournamentBaseID + 1, defID: 1, kind: 1,
		name:  "Tournoi 1v1 Classique",
		short: "Classique",
		desc:  "Affrontez les meilleurs coachs dans un tournoi 1 contre 1.",
	},
	{
		id: tournamentBaseID + 2, defID: 4, kind: 1,
		name:  "Tournoi des Champions",
		short: "Champions",
		desc:  "Un tournoi d'elite reserve aux coachs les plus aguerris.",
	},
	{
		id: tournamentBaseID + 3, defID: 17, kind: 1,
		name:  "Tournoi du Cimetiere",
		short: "Cimetiere",
		desc:  "Un tournoi hante ou seuls les plus braves osent s'inscrire.",
	},
}

// findStandingTournament returns the standing tournament with the given wire id, or
// nil if none matches.
func findStandingTournament(id int64) *standingTournament {
	for i := range standingTournamentTable {
		if standingTournamentTable[i].id == id {
			return &standingTournamentTable[i]
		}
	}
	return nil
}

// TournamentManager records which coaches have registered for which standing
// tournaments. It is process-lived (not persisted): registrations survive a relog
// within one server run but reset on restart, which is acceptable while the
// live-match layer is deferred. Thread-safe.
type TournamentManager struct {
	mu  sync.Mutex
	reg map[uint]map[int64]bool // coachID -> set of registered tournament ids
}

// NewTournamentManager returns an empty registration tracker.
func NewTournamentManager() *TournamentManager {
	return &TournamentManager{reg: make(map[uint]map[int64]bool)}
}

// Register marks coachID as registered for tid. It is idempotent; the return value
// reports whether this call was the one that added the registration.
func (m *TournamentManager) Register(coachID uint, tid int64) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	set := m.reg[coachID]
	if set == nil {
		set = make(map[int64]bool)
		m.reg[coachID] = set
	}
	if set[tid] {
		return false
	}
	set[tid] = true
	return true
}

// IsRegistered reports whether coachID is registered for tid.
func (m *TournamentManager) IsRegistered(coachID uint, tid int64) bool {
	m.mu.Lock()
	defer m.mu.Unlock()
	return m.reg[coachID][tid]
}

// buildTournamentCalendar builds TOURNAMENT_CALENDAR (17003 awa_0): the scheduled
// events the tournament window shows on its calendar and under "Tournois du jour".
// Each entry is [i32 typeId=4][qr_0 body]; the body is the shared event base
// (iz_0) + the duration base (th_2) + the tournament fields. The registration-period
// list MUST hold at least one pair: the client's qr_0 reads element 0 of it
// unguarded when rendering the "registration period" label.
func buildTournamentCalendar() ([]byte, error) {
	now := time.Now()
	w := protocol.NewWriter().U16(uint16(len(standingTournamentTable))) // [i16 count]
	for i := range standingTournamentTable {
		writeTournamentEvent(w, &standingTournamentTable[i], now)
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
func writeTournamentEvent(w *protocol.Writer, t *standingTournament, now time.Time) {
	startedMs := now.Add(-time.Hour).UnixMilli()      // event has already started (extraDate / bOF)
	expireMs := now.Add(tournamentWindow).UnixMilli() // event runs until (startDate slot / OV)
	phaseStartMs := now.Add(time.Hour).UnixMilli()
	phaseEndMs := now.Add(2 * time.Hour).UnixMilli()

	w.I32(tournamentContentTypeID) // [i32 typeId] -> qr_0

	// iz_0 shared event base.
	w.I64(t.id)     // eventId (reuse the tournament id)
	w.I64(expireMs) // "startDate" slot (OV) = runs-until bound: >= end-of-today keeps it listed
	w.I64(expireMs) // endDate (bhF): unused by the non-recurring filter; mirror OV
	w.I64(0)        // recurrence period: none (single occurrence)
	w.I32(0)        // label index: title is taken from the 28602 row when present

	// th_2 duration base.
	w.I64(startedMs) // extraDate (bOF) = already-started bound: <= now / today

	// qr_0 tournament fields.
	w.I64(t.id) // tournamentId (must equal the 28602 row's id: the client links them)
	w.StringU8(t.name)
	w.StringU16(t.desc)
	w.StringU8(t.short)
	w.U8(1).I64(phaseStartMs).I64(phaseEndMs) // one schedule (phase) pair (display only)
	w.U8(1).I64(startedMs).I64(expireMs)      // one registration-period pair (>=1 required)
}

// buildTournamentList builds TOURNAMENT_LIST (28602 ng_2): the registerable
// tournaments, keyed by their client definition id. coachStatus reflects whether
// this coach has already registered, so a re-opened window shows the right state.
func buildTournamentList(coachID uint, tm *TournamentManager) ([]byte, error) {
	w := protocol.NewWriter().I32(int32(len(standingTournamentTable))) // [i32 count]
	for i := range standingTournamentTable {
		t := &standingTournamentTable[i]
		status := tournamentCoachNotRegistered
		if tm != nil && tm.IsRegistered(coachID, t.id) {
			status = tournamentCoachFirstRound
		}
		w.I64(t.id)
		w.U8(0)             // openedSearch: opponent search closed
		w.U8(uint8(status)) // coachStatus
		w.U16(t.defID)      // tournamentDefinitionId (real aub id)
		w.U8(1)             // registrationOpen: true
		w.I32(0)            // fightParamCount: no special rules
		w.StringU32(t.name)
		w.StringU32(t.desc)
		w.StringU32(tournamentOrganizer)
		w.U8(t.kind)
	}
	return protocol.EncodeS2C(protocol.OpTournamentList, w.Bytes())
}
