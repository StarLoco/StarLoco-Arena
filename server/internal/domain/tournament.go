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

	// SearchPeriodStart / SearchPeriodMinutes describe the tournament's
	// opponent-search period: the window in which entrants are expected to press
	// Combattre, and outside which retail declares the absentees forfeit.
	//
	// This is the SAME schedule the client is shown. The calendar event (17003
	// qr_0) carries a "phase" pair which used to be invented as now+1h..now+2h on
	// every request - display-only, and drifting each time the window was opened.
	// Driving both from these columns is what stops the advertised schedule and
	// the server's own idea of it from disagreeing.
	//
	// A zero SearchPeriodStart means "not scheduled"; the calendar then falls back
	// to the old synthesized window so existing rows keep working.
	SearchPeriodStart   time.Time
	SearchPeriodMinutes int32 `gorm:"not null;default:0"`

	CreatedAt time.Time
	UpdatedAt time.Time
}

// SearchPeriodEnd is when the opponent-search window closes, or the zero time if
// none is scheduled.
func (t *Tournament) SearchPeriodEnd() time.Time {
	if t == nil || t.SearchPeriodStart.IsZero() || t.SearchPeriodMinutes <= 0 {
		return time.Time{}
	}
	return t.SearchPeriodStart.Add(time.Duration(t.SearchPeriodMinutes) * time.Minute)
}

// SearchPeriodOpenAt reports whether the search window contains `now`.
func (t *Tournament) SearchPeriodOpenAt(now time.Time) bool {
	end := t.SearchPeriodEnd()
	if end.IsZero() {
		return false
	}
	return !now.Before(t.SearchPeriodStart) && now.Before(end)
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
// TournamentSlot records who occupies a bracket slot.
//
// The bracket is a 1-indexed binary heap (see game.bracket*): slot 1 is the
// winner, 16-31 the first round, and slot i is decided by the pair in 2i / 2i+1.
// Only slots ABOVE the first round are stored - the first round is derived from
// the registrations, so seeding cannot drift away from who is actually entered.
type TournamentSlot struct {
	ID uint `gorm:"primaryKey"`

	TournamentWireID int64 `gorm:"index;not null;uniqueIndex:idx_tourn_slot"`
	Slot             int32 `gorm:"not null;uniqueIndex:idx_tourn_slot"`
	CoachID          uint  `gorm:"index;not null"`

	CreatedAt time.Time
}
type TournamentRegistration struct {
	ID      uint `gorm:"primaryKey"`
	CoachID uint `gorm:"index;not null;uniqueIndex:idx_tourn_reg_coach_tid"`

	TournamentWireID int64 `gorm:"index;not null;uniqueIndex:idx_tourn_reg_coach_tid"`

	// PrizeAwarded records that this coach has already been paid this
	// tournament's reward card.
	//
	// SECURITY: awardTournamentPrize had no already-paid record at all, and the
	// path that reaches it (28611, the tournament search) re-derives
	// "unopposed in this tournament" from persisted bracket state - so it stays
	// true forever once you hold the root slot, and each packet paid another copy
	// of the reward card. This row already carries a unique (coach, tournament)
	// index, which makes it the natural place to make payment idempotent.
	PrizeAwarded bool `gorm:"not null;default:false"`

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
