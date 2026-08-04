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
