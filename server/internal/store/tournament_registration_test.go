package store

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// These exercise the REAL repo against a real (temp) database. The game package
// tests the manager against an in-process fake, which proves the manager's logic
// but says nothing about whether the SQL works — the two together are the point.

// TestTournamentRegistrationRoundTrip: add, read back, and withdraw.
func TestTournamentRegistrationRoundTrip(t *testing.T) {
	st := newTestStore(t)
	const coach = uint(3)
	const tid = domain.TournamentWireBase + 7

	if err := st.Tournaments.AddRegistration(coach, tid); err != nil {
		t.Fatalf("add: %v", err)
	}
	rows, err := st.Tournaments.ListRegistrations()
	if err != nil {
		t.Fatalf("list: %v", err)
	}
	if len(rows) != 1 || rows[0].CoachID != coach || rows[0].TournamentWireID != tid {
		t.Fatalf("rows = %+v, want one {coach %d, tid %d}", rows, coach, tid)
	}

	if err := st.Tournaments.RemoveRegistration(coach, tid); err != nil {
		t.Fatalf("remove: %v", err)
	}
	if rows, _ = st.Tournaments.ListRegistrations(); len(rows) != 0 {
		t.Errorf("rows = %+v after remove, want none", rows)
	}
}

// TestTournamentRegistrationIsIdempotent: registering twice must not create two
// rows. The pair carries a unique index, so a plain Create would error on the
// second call; AddRegistration has to tolerate it.
func TestTournamentRegistrationIsIdempotent(t *testing.T) {
	st := newTestStore(t)
	const coach = uint(4)
	const tid = domain.TournamentWireBase + 9

	for i := 0; i < 3; i++ {
		if err := st.Tournaments.AddRegistration(coach, tid); err != nil {
			t.Fatalf("add #%d: %v", i+1, err)
		}
	}
	rows, _ := st.Tournaments.ListRegistrations()
	if len(rows) != 1 {
		t.Errorf("rows = %d after 3 identical adds, want 1", len(rows))
	}
}

// TestDeletingATournamentPurgesItsRegistrations: registrations are keyed by wire
// id with no foreign key, so deleting a tournament has to clean them up
// explicitly. Otherwise they outlive it as orphans AND — because the wire id is
// derived from the row id — get inherited by whatever row reuses that id.
func TestDeletingATournamentPurgesItsRegistrations(t *testing.T) {
	st := newTestStore(t)
	tr := &domain.Tournament{DefID: 1, Name: "Doomed", Short: "D", Enabled: true}
	if err := st.Tournaments.Create(tr); err != nil {
		t.Fatalf("create tournament: %v", err)
	}
	other := domain.TournamentWireBase + 999
	if err := st.Tournaments.AddRegistration(5, tr.WireID()); err != nil {
		t.Fatalf("add: %v", err)
	}
	if err := st.Tournaments.AddRegistration(5, other); err != nil {
		t.Fatalf("add other: %v", err)
	}

	if err := st.Tournaments.Delete(tr.ID); err != nil {
		t.Fatalf("delete: %v", err)
	}

	rows, _ := st.Tournaments.ListRegistrations()
	if len(rows) != 1 || rows[0].TournamentWireID != other {
		t.Fatalf("rows = %+v, want only the unrelated tid %d to survive", rows, other)
	}
}
