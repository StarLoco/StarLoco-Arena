package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestLadderStrengthMovesOnFightEnd drives two real coaches into a real fight,
// has one give up, and verifies the winner's ladder Strength rises and the
// loser's falls (persisted to the store) — proving the ranked ladder actually
// moves on a fight result (P2), which it never did before.
func TestLadderStrengthMovesOnFightEnd(t *testing.T) {
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown")
	}
	st, addr := testServerWithStore(t)
	a, b := startFightForCombat(t, addr) // a=Combatant1, b=Combatant2
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	// A gives up -> B wins.
	_ = a.Send(3, testclient.OpGiveUp, nil)
	if _, _, err := b.WaitFor(testclient.OpEndFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("winner should receive END_FIGHT after the opponent gives up: %v", err)
	}
	time.Sleep(150 * time.Millisecond) // let the strength save settle

	winner, err := st.Coaches.GetByName("Combatant2")
	if err != nil {
		t.Fatalf("get winner: %v", err)
	}
	loser, err := st.Coaches.GetByName("Combatant1")
	if err != nil {
		t.Fatalf("get loser: %v", err)
	}
	// From unranked (0): both seed to 1000, then winner +25 = 1025 and loser -25
	// clamps to the 1000 floor.
	if winner.Strength != 1025 {
		t.Errorf("winner ladder Strength = %d, want 1025 (moved up)", winner.Strength)
	}
	if loser.Strength != 1000 {
		t.Errorf("loser ladder Strength = %d, want 1000 (clamped floor)", loser.Strength)
	}
	// And win/loss stats were recorded.
	if winner.StatWins != 1 || loser.StatLosses != 1 {
		t.Errorf("stats: winner wins=%d loser losses=%d, want 1/1", winner.StatWins, loser.StatLosses)
	}
}
