package game

import "testing"

// TestTournamentPrizeIsPaidOnlyOnce drives the real granting function twice, the
// way spamming 28611 would. The store-level test proves the ledger; this proves
// awardTournamentPrize actually consults it.
func TestTournamentPrizeIsPaidOnlyOnce(t *testing.T) {
	d, coachID, tid := tournamentPrizeDeps(t, 11)
	const prizeCard = 26

	before := ownedQty(t, d, coachID, prizeCard)
	d.awardTournamentPrize(tid, coachID, nil)
	afterFirst := ownedQty(t, d, coachID, prizeCard)
	if afterFirst != before+1 {
		t.Fatalf("fixture broken: first award did not pay (%d -> %d), so the "+
			"repeat check below would pass vacuously", before, afterFirst)
	}

	for i := 0; i < 4; i++ {
		d.awardTournamentPrize(tid, coachID, nil)
	}
	final := ownedQty(t, d, coachID, prizeCard)
	if final != afterFirst {
		t.Errorf("card %d quantity %d -> %d after repeat awards: the tournament "+
			"reward can be farmed by re-sending 28611", prizeCard, afterFirst, final)
	}
}
