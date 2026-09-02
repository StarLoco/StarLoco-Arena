package store

import (
	"testing"
)

// TestTournamentPrizeCanOnlyBeClaimedOnce is the SECURITY regression for
// repeatable tournament reward cards.
//
// awardTournamentPrize had no already-paid record. Its reachable caller (28611,
// the tournament search) re-derives "unopposed in this tournament" from persisted
// bracket state, so it stays true once you hold the root slot - one reward card
// per packet, indefinitely.
func TestTournamentPrizeCanOnlyBeClaimedOnce(t *testing.T) {
	st := newTestStore(t)
	const coachID, tid = uint(7), int64(4242)

	if err := st.Tournaments.AddRegistration(coachID, tid); err != nil {
		t.Fatalf("register: %v", err)
	}

	first, err := st.Tournaments.ClaimTournamentPrize(coachID, tid)
	if err != nil {
		t.Fatalf("first claim: %v", err)
	}
	if !first {
		t.Fatal("fixture broken: the first claim must succeed, otherwise this " +
			"test proves nothing about the second")
	}

	for i := 0; i < 5; i++ {
		again, err := st.Tournaments.ClaimTournamentPrize(coachID, tid)
		if err != nil {
			t.Fatalf("repeat claim %d: %v", i, err)
		}
		if again {
			t.Fatalf("claim %d succeeded again - the reward card can be farmed", i+2)
		}
	}
}

// TestTournamentPrizeIsPayOnceEvenWithoutARegistration records a deliberate
// design choice.
//
// My first version refused when no registration row existed, which broke three
// existing prize tests - and that was the useful signal, not a nuisance:
// requiring registration adds a second rule about WHO may be paid, and
// unregistration happens elsewhere in the tournament lifecycle, so a legitimate
// winner could have been silently denied. This function guarantees "at most
// once" and nothing else; eligibility stays with the caller.
func TestTournamentPrizeIsPayOnceEvenWithoutARegistration(t *testing.T) {
	st := newTestStore(t)
	const coachID, tid = uint(99), int64(1234)

	first, err := st.Tournaments.ClaimTournamentPrize(coachID, tid)
	if err != nil {
		t.Fatalf("first claim: %v", err)
	}
	if !first {
		t.Fatal("an unregistered winner must still be payable once - refusing " +
			"here would deny a legitimate prize")
	}
	again, err := st.Tournaments.ClaimTournamentPrize(coachID, tid)
	if err != nil {
		t.Fatalf("second claim: %v", err)
	}
	if again {
		t.Error("second claim succeeded - the prize is farmable")
	}
}

// TestTournamentPrizeIsPerCoachAndPerTournament guards against the flag being
// stored too coarsely - one winner's claim must not block another's.
func TestTournamentPrizeIsPerCoachAndPerTournament(t *testing.T) {
	st := newTestStore(t)
	for _, r := range []struct {
		coach uint
		tid   int64
	}{{1, 100}, {2, 100}, {1, 200}} {
		if err := st.Tournaments.AddRegistration(r.coach, r.tid); err != nil {
			t.Fatalf("register %d/%d: %v", r.coach, r.tid, err)
		}
	}
	if ok, _ := st.Tournaments.ClaimTournamentPrize(1, 100); !ok {
		t.Fatal("coach 1 / tournament 100 should claim")
	}
	if ok, _ := st.Tournaments.ClaimTournamentPrize(2, 100); !ok {
		t.Error("a DIFFERENT coach in the same tournament was blocked")
	}
	if ok, _ := st.Tournaments.ClaimTournamentPrize(1, 200); !ok {
		t.Error("the same coach in a DIFFERENT tournament was blocked")
	}
}
