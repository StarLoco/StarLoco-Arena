package game

import (
	"testing"
	"time"
)

// TestPhaseClockForceAdvances verifies that the phase clock advances the fight
// even when no coach signals ready (anti-deadlock).
func TestPhaseClockForceAdvances(t *testing.T) {
	// Shorten clocks for the test, restore after the actor has stopped (defers
	// run LIFO: stopActor first, then restoreClocks -- so no read/write race on
	// the package clock vars).
	defer restoreClocks(presentationClock, placementClock, observationClock, turnClock)
	presentationClock = 20 * time.Millisecond
	placementClock = 20 * time.Millisecond
	observationClock = 20 * time.Millisecond
	turnClock = 20 * time.Millisecond

	f := buildTestFight()
	f.startActor()
	defer func() { f.stopActor(); f.waitStopped() }()
	f.setPhase(PhasePresentation)

	// Arm the presentation clock as startFight would (on the actor goroutine).
	f.Post(func(f *Fight) {
		f.armClock(presentationClock, (*Fight).advanceToPlacement)
	})

	// Without any ready signal, the fight should march through the phases on its
	// own (advanceToPlacement arms the placement clock, which arms the observation
	// clock, etc.).
	//
	// Only the LAST phase is waited on, deliberately. waitPhase polls, and each
	// intermediate phase lasts one 20ms clock, so a poll that is not scheduled
	// inside that window misses the state entirely and fails with the phase
	// already past the one it wanted ("never reached Placement (stuck at
	// Observation)"). That is a race in the test, not a fault in the fight, and it
	// fired under the load of the full package run.
	//
	// Nothing is lost by dropping the intermediate waits: the phases are chained,
	// each transition arming the next one's clock, so arriving at Action is only
	// possible by having passed through Placement and Observation. A transition
	// that failed to arm its clock stalls the chain and this still fails.
	waitPhase(t, f, PhaseAction, 2*time.Second)
}

// TestManualAdvanceCancelsClock: a manual "both ready" advance means the stale
// clock firing later is a no-op (no double transition).
func TestManualAdvanceCancelsClock(t *testing.T) {
	defer restoreClocks(presentationClock, placementClock, observationClock, turnClock)
	presentationClock = 50 * time.Millisecond

	f := buildTestFight()
	f.startActor()
	defer func() { f.stopActor(); f.waitStopped() }()
	f.setPhase(PhasePresentation)

	// Arm the clock then immediately advance manually, both on the actor.
	done := make(chan struct{})
	f.Post(func(f *Fight) {
		f.armClock(presentationClock, (*Fight).advanceToPlacement)
		f.advanceToPlacement()
		close(done)
	})
	<-done
	if f.Phase() != PhasePlacement {
		t.Fatalf("phase = %v, want Placement", f.Phase())
	}

	// Let the old clock fire; it must NOT advance again (still Placement, or
	// further along if the placement clock legitimately advanced it -- but NOT
	// via the stale presentation timer).
	time.Sleep(100 * time.Millisecond)
	if f.Phase() < PhasePlacement {
		t.Errorf("stale clock reverted/double-advanced to %v", f.Phase())
	}
}

func waitPhase(t *testing.T, f *Fight, want FightPhase, timeout time.Duration) {
	t.Helper()
	deadline := time.Now().Add(timeout)
	for time.Now().Before(deadline) {
		if f.Phase() == want {
			return
		}
		time.Sleep(5 * time.Millisecond)
	}
	t.Fatalf("phase never reached %v (stuck at %v)", want, f.Phase())
}

func restoreClocks(p, pl, o, tu time.Duration) {
	presentationClock, placementClock, observationClock, turnClock = p, pl, o, tu
}
