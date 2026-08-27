package game

import (
	"testing"
)

// Settling a closed opponent-search period: whoever was searching takes the
// fixture, whoever was not is declared forfeit. This is what lets a tournament
// progress when the two halves of a fixture are never online at the same time.

// allPresent is the "everyone can fight" predicate.
func allPresent(uint) bool { return true }

// onlyPresent builds a predicate where exactly these coaches are online.
func onlyPresent(ids ...uint) func(uint) bool {
	set := map[uint]bool{}
	for _, id := range ids {
		set[id] = true
	}
	return func(c uint) bool { return set[c] }
}

func TestForfeitAwardsTheFixtureToWhoeverWasSearching(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	// Coach 1 is searching; coach 2 (its sibling) never turned up.
	if _, paired := m.ReadyUp(7, 1); paired {
		t.Fatal("paired with nobody")
	}
	got := m.SettleClosedPeriod(7, allPresent)
	if len(got) != 1 {
		t.Fatalf("settled %d fixtures, want 1", len(got))
	}
	if got[0].Winner != 1 || got[0].Loser != 2 {
		t.Errorf("settled %+v, want winner 1 / loser 2", got[0])
	}
	if slot := m.BracketSlots(7)[8]; slot != 1 {
		t.Errorf("slot 8 = coach %d, want 1", slot)
	}
}

// TestForfeitIgnoresAnAbsentSearcher is the reason the predicate exists.
//
// Nothing clears the ready set on disconnect, so a coach could ready up, log off
// and still be "searching" when the window closed - winning fixtures while
// absent, which is the exact behaviour the forfeit rule exists to punish.
func TestForfeitIgnoresAnAbsentSearcher(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	if _, paired := m.ReadyUp(7, 1); paired {
		t.Fatal("paired with nobody")
	}
	// Coach 1 readied, then went offline. Coach 2 never readied either.
	if got := m.SettleClosedPeriod(7, onlyPresent(2, 3, 4)); len(got) != 0 {
		t.Fatalf("settled %+v: an offline coach won a fixture by forfeit while "+
			"absent", got)
	}
	if _, decided := m.BracketSlots(7)[8]; decided {
		t.Error("slot 8 was decided with neither coach present")
	}
}

// TestNeitherSideSearchingDecidesNothing: there is no fair winner to pick, and
// picking one would hand the fixture to whoever was seeded lower.
func TestNeitherSideSearchingDecidesNothing(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	if got := m.SettleClosedPeriod(7, allPresent); len(got) != 0 {
		t.Fatalf("settled %+v with nobody searching", got)
	}
	if len(m.BracketSlots(7)) != 4 {
		t.Errorf("bracket changed: %v", m.BracketSlots(7))
	}
}

// TestSettlementIsIdempotent: it only touches fixtures whose parent is still
// empty, so a second pass must decide nothing and must not re-announce.
func TestSettlementIsIdempotent(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	m.ReadyUp(7, 1)
	if got := m.SettleClosedPeriod(7, allPresent); len(got) != 1 {
		t.Fatalf("first pass settled %d, want 1", len(got))
	}
	if got := m.SettleClosedPeriod(7, allPresent); len(got) != 0 {
		t.Errorf("second pass settled %+v: the period would be announced twice", got)
	}
}

// TestForfeitConsumesTheReadyState: the period is over, so a coach that won by
// forfeit must not still count as searching for the NEXT one.
func TestForfeitConsumesTheReadyState(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	m.ReadyUp(7, 1)
	m.SettleClosedPeriod(7, allPresent)

	// Coach 3 beats 4 the honest way, so 1 (slot 8) and 3 (slot 9) now face each
	// other. If coach 1 were still flagged as searching, the next settlement would
	// hand it that fixture too, without it doing anything.
	m.RecordMatchResult(7, 3, 4)
	if got := m.SettleClosedPeriod(7, allPresent); len(got) != 0 {
		t.Errorf("settled %+v: the forfeit winner was still marked as searching "+
			"in the next period", got)
	}
}

// TestForfeitCarriesTheWinnerThroughByes: with the other half of the draw empty,
// a forfeit win can BE the tournament win, and the caller keys the prize off the
// slot it reports.
func TestForfeitCarriesTheWinnerThroughByes(t *testing.T) {
	m := tmWithEntrants(7, 1, 2) // two entrants: slots 16/17, everything above is a bye
	m.ReadyUp(7, 1)
	got := m.SettleClosedPeriod(7, allPresent)
	if len(got) != 1 {
		t.Fatalf("settled %d fixtures, want 1", len(got))
	}
	if got[0].Slot != bracketWinnerSlot {
		t.Errorf("winner landed at slot %d, want %d - the byes above the fixture "+
			"were not followed, so the tournament would never pay out",
			got[0].Slot, bracketWinnerSlot)
	}
}
