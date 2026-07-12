package combat

import (
	"testing"
	"time"

	"github.com/rs/zerolog"
)

// TestEndTurnSettle_DefersAfterRecentAction verifies the FIGHTER_TURN_END
// settle guard: when a fighter took an animated action moments ago, ending
// the turn is deferred (not processed immediately) so the client can finish
// playing the action's group -- the fix for the "cast Teleport, end turn,
// fighter stuck" freeze. With ActionSettle set and a fresh lastActionAt, the
// current fighter must still be current right after the end-turn command.
func TestEndTurnSettle_DefersAfterRecentAction(t *testing.T) {
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100 // a goes first
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 10
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := testClocks()
	clocks.ActionSettle = 200 * time.Millisecond
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())
	f.setPhase(PhaseAction)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	f.Timeline.StartNextTurn() // a current (higher init)
	f.currentFighterID.Store(a.ID)
	if f.Timeline.CurrentFighter() != a {
		t.Fatalf("setup: expected a to be current, got %v", f.Timeline.CurrentFighter())
	}

	// Simulate a just-cast action.
	f.markActionTaken()

	// End-turn arrives immediately: the guard must DEFER it (a stays current).
	f.handleFighterEndTurn(cmdFighterEndTurn{RequesterCoachID: 100, FighterID: a.ID})
	if cur := f.Timeline.CurrentFighter(); cur != a {
		t.Fatalf("turn ended immediately despite a recent action; current = %v, want a (deferred)", cur)
	}

	// A deferred re-send (as the timer would produce) must process now.
	f.handleFighterEndTurn(cmdFighterEndTurn{RequesterCoachID: 100, FighterID: a.ID, deferred: true})
	if cur := f.Timeline.CurrentFighter(); cur == a {
		t.Errorf("deferred end-turn did not advance the turn; still on a")
	}
	f.cancelTurnClock()
}

// TestEndTurnSettle_NoDeferWithoutRecentAction verifies the guard does NOT
// delay a turn-end when no action was taken this turn (lastActionAt zero):
// ending the turn is immediate, so the normal "just move / pass" flow is
// unaffected.
func TestEndTurnSettle_NoDeferWithoutRecentAction(t *testing.T) {
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	clocks := testClocks()
	clocks.ActionSettle = 200 * time.Millisecond
	f := NewFight(1, 1, clocks, []*Team{teamA, teamB}, newFakeBroadcaster(), nil, zerolog.Nop())
	f.setPhase(PhaseAction)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	f.Timeline.StartNextTurn()
	f.currentFighterID.Store(a.ID)

	// No markActionTaken -> lastActionAt is zero -> no deferral.
	f.handleFighterEndTurn(cmdFighterEndTurn{RequesterCoachID: 100, FighterID: a.ID})
	if cur := f.Timeline.CurrentFighter(); cur == a {
		t.Errorf("turn-end with no recent action was wrongly deferred; still on a")
	}
	f.cancelTurnClock()
}
