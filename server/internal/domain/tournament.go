package domain

import "time"

// Tournament is a standing tournament the server offers, editable from the web
// admin console.
//
// These used to be a compiled-in table, which meant changing the server's
// tournament line-up needed a rebuild. The rows are still deliberately thin:
// this describes what the client is TOLD about a tournament (the calendar entry
// and the registerable-list row), not a bracket engine. The live-match layer —
// opponent search, scheduled fights, progression, rewards — remains deferred.
//
// DefID is the part an operator cannot be allowed to get wrong. It references a
// real client definition (a data.bdat type-1000 `aub` record; retail ships 22,
// ids {1, 4..24}), and the client dereferences it unguarded — a row pointing at
// a definition the client does not have takes the client down with a null
// dereference rather than showing an error. It must also be a definition whose
// inscription card is 0, or the client demands an entry ticket this server never
// grants and registration can never complete. Both rules are enforced when a
// tournament is saved; see internal/web's tournament validation.
type Tournament struct {
	ID uint `gorm:"primaryKey"`

	// DefID is the client definition id (aub.Bw()).
	DefID uint16 `gorm:"not null"`

	// Name and Short go out in fields the wire length-prefixes with a SIGNED
	// byte, so anything past 127 bytes is truncated by the writer.
	Name  string `gorm:"size:127;not null"`
	Short string `gorm:"size:127;not null"`
	// Description is length-prefixed with 16 bits; the cap here is editorial,
	// not protocol.
	Description string `gorm:"size:1000;not null"`
	// Organizer is the label shown as who runs the tournament.
	Organizer string `gorm:"size:127;not null"`

	// Enabled hides a tournament from players without deleting it, so a
	// seasonal event can be prepared in advance and switched on later.
	Enabled bool `gorm:"not null;default:true"`
	// RegistrationOpen still lists the tournament but refuses new sign-ups.
	RegistrationOpen bool `gorm:"not null;default:true"`

	// Position orders the list players see; ties fall back to ID.
	Position int `gorm:"not null;default:0"`

	CreatedAt time.Time
	UpdatedAt time.Time
}

func (Tournament) TableName() string { return "tournaments" }

// TournamentRegistration is one coach's entry into one standing tournament.
//
// Keyed by the tournament's WIRE id rather than its row id, because that is what
// the client sends in 4607 and what every reply is keyed by. The wire id is
// derived from the row id (see WireID), so it is stable across restarts — which
// is precisely what makes storing it safe.
//
// This used to live only in memory, so every restart silently un-registered
// everyone (B-101).
// The field is deliberately named for what it holds — the WIRE id, not the
// tournament row id — because the two differ by TournamentWireBase and confusing
// them would silently register people for the wrong thing.
type TournamentRegistration struct {
	ID      uint `gorm:"primaryKey"`
	CoachID uint `gorm:"index;not null;uniqueIndex:idx_tourn_reg_coach_tid"`

	TournamentWireID int64 `gorm:"index;not null;uniqueIndex:idx_tourn_reg_coach_tid"`

	CreatedAt time.Time
}

func (TournamentRegistration) TableName() string { return "tournament_registrations" }

// TournamentWireBase keeps synthetic tournament handles clear of coach and
// fighter wire ids. The value only has to be stable, unique and non-zero.
//
// It lives here rather than in the game package because both the wire layer and
// the admin console need to agree on how a row id becomes a wire id.
const TournamentWireBase int64 = 2_600_000

// WireID is the handle the client sees. Deriving it from the row id keeps it
// stable across restarts, which matters because registrations are keyed by it.
func (t *Tournament) WireID() int64 { return TournamentWireBase + int64(t.ID) }

// TournamentKindPrivate is the 28602 wire kind (ks_1) every tournament here
// uses: a registerable event with no automatic opponent search and no bracket,
// which is the only shape this server actually runs. It is deliberately not
// editable from the console — the other kinds pull the client into flows that
// have no server behind them.
const TournamentKindPrivate uint8 = 1
