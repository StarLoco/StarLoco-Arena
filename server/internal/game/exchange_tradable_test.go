package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestCardIsTradable is the regression guard for B-061: template-level tradability
// must be enforced server-side, not left to the client.
func TestCardIsTradable(t *testing.T) {
	d := &Deps{Cards: gamedata.NewCards(
		&gamedata.CoachCard{ID: 1},                       // plain
		&gamedata.CoachCard{ID: 2, Bound: true},          // linked to its owner
		&gamedata.CoachCard{ID: 3, Undestructible: true}, // cannot be destroyed/sold/traded
	)}
	for _, tc := range []struct {
		id   int32
		want bool
	}{{1, true}, {2, false}, {3, false}, {999, true}} {
		if got := d.cardIsTradable(tc.id); got != tc.want {
			t.Errorf("cardIsTradable(%d) = %v, want %v", tc.id, got, tc.want)
		}
	}
	// No catalog (server without data files) must stay permissive rather than
	// blocking every trade.
	if !(&Deps{}).cardIsTradable(2) {
		t.Error("a server with no card catalog should not block trading")
	}
}

// TestCardIsBoundMatchesTheClientsMailRule pins the replacement for the dead
// CardLocked bit (B-094). Mail is gated on the template's Bound flag ALONE:
// ay.java checks tp() and nothing else, so an indestructible card may be posted
// even though it cannot be destroyed or sold. Using cardIsTradable here instead
// would silently forbid a card the real client happily mails.
func TestCardIsBoundMatchesTheClientsMailRule(t *testing.T) {
	d := &Deps{Cards: gamedata.NewCards(
		&gamedata.CoachCard{ID: 1},
		&gamedata.CoachCard{ID: 2, Bound: true},
		&gamedata.CoachCard{ID: 3, Undestructible: true},
	)}
	for _, tc := range []struct {
		id      int32
		want    bool
		because string
	}{
		{1, false, "a plain card is not bound"},
		{2, true, "a Bound card cannot be mailed or traded"},
		{3, false, "Undestructible blocks destroying and selling, NOT mailing"},
		{999, false, "an unknown template must not be treated as bound"},
	} {
		if got := d.cardIsBound(tc.id); got != tc.want {
			t.Errorf("cardIsBound(%d) = %v, want %v — %s", tc.id, got, tc.want, tc.because)
		}
	}
	if (&Deps{}).cardIsBound(2) {
		t.Error("a server with no card catalog should not treat cards as bound")
	}
}

// TestCardIsUnique guards the 5113 refusal path: a unique card cannot be handed
// to somebody who already owns one (ky_2.a returns 2 for that case).
func TestCardIsUnique(t *testing.T) {
	d := &Deps{Cards: gamedata.NewCards(
		&gamedata.CoachCard{ID: 1},
		&gamedata.CoachCard{ID: 2, IsUnique: true},
	)}
	if d.cardIsUnique(1) {
		t.Error("a plain card was reported unique")
	}
	if !d.cardIsUnique(2) {
		t.Error("a unique card was not reported unique")
	}
	if d.cardIsUnique(999) || (&Deps{}).cardIsUnique(2) {
		t.Error("unknown template / no catalog must not report unique")
	}
}
