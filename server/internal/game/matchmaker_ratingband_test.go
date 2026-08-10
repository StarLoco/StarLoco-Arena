package game

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// bandSearcher builds a session whose coach carries the given ladder strength.
func bandSession(id uint, strength int32) *Session {
	return &Session{Coach: &domain.Coach{ID: id, Strength: strength}}
}

// TestRatingBandRefusesMismatchThenWidens is the whole mechanic: a gap wider
// than the band leaves both coaches queued, and the band widens with waiting
// until they qualify. That widening is why there is no separate queue timeout —
// the requirement relaxes rather than the search being abandoned.
func TestRatingBandRefusesMismatchThenWidens(t *testing.T) {
	// Each probe must run against a FRESH queue holding only the waiting coach.
	// A failed Search enqueues its searcher, so probing the same matchmaker
	// repeatedly would pair two probes against each other rather than testing
	// the band — which is exactly what the first version of this test did.
	probe := func(wait time.Duration) *pendingMatch {
		now := time.Now()
		m := NewMatchmaker()
		m.now = func() time.Time { return now }
		m.SetRatingBand(300, 150) // 300 opening band, +150 per second waited

		if pm := m.Search(bandSession(1, 1000), 1, 0, nil); pm != nil {
			t.Fatal("first searcher should just queue")
		}
		now = now.Add(wait)
		return m.Search(bandSession(2, 3000), 1, 0, nil)
	}

	// Gap 2000. Band at t: 300 + 150t.
	if probe(0) != nil {
		t.Error("t=0 (band 300): a 2000-point gap must not pair")
	}
	if probe(5*time.Second) != nil {
		t.Error("t=5s (band 1050): still under the 2000 gap, must not pair")
	}
	// The exact boundary: 300 + 150t >= 2000 needs t >= 11.33, and the elapsed
	// seconds are truncated to a whole number, so 11s is still short and 12s is
	// the first that qualifies.
	if probe(11*time.Second) != nil {
		t.Error("t=11s (band 1950): still 50 short of the 2000 gap, must not pair")
	}
	if probe(12*time.Second) == nil {
		t.Error("t=12s (band 2100): the widened band covers the gap, must pair")
	}
}

// TestRatingBandPairsCloseRatingsImmediately: fairness must not add latency for
// the common case of two similarly-rated coaches.
func TestRatingBandPairsCloseRatingsImmediately(t *testing.T) {
	m := NewMatchmaker()
	m.SetRatingBand(300, 150)
	_ = m.Search(bandSession(1, 1000), 1, 0, nil)
	if pm := m.Search(bandSession(2, 1200), 1, 0, nil); pm == nil {
		t.Error("a 200-point gap inside a 300-point band was not paired immediately")
	}
}

// TestRatingBandDisabledPairsAnyone: band 0 is the documented "small private
// server" setting and must restore the old pair-anyone-instantly behaviour.
func TestRatingBandDisabledPairsAnyone(t *testing.T) {
	m := NewMatchmaker()
	m.SetRatingBand(0, 0)
	_ = m.Search(bandSession(1, 100), 1, 0, nil)
	if pm := m.Search(bandSession(2, 9000), 1, 0, nil); pm == nil {
		t.Error("band 0 should pair any two coaches instantly")
	}
}

// TestRatingBandUsesTheLongerWait: waiting earns a wider net, so a freshly
// queued coach must not veto a match for someone who has waited a long time.
func TestRatingBandUsesTheLongerWait(t *testing.T) {
	now := time.Now()
	m := NewMatchmaker()
	m.now = func() time.Time { return now }
	m.SetRatingBand(100, 100)

	_ = m.Search(bandSession(1, 1000), 1, 0, nil) // waits...
	now = now.Add(30 * time.Second)               // ...for 30s -> band 3100

	// The newcomer has waited 0s, so ITS own band is only 100. Taking the
	// shorter wait would refuse this pair; taking the longer one allows it.
	if pm := m.Search(bandSession(2, 3000), 1, 0, nil); pm == nil {
		t.Error("the long-waiting searcher's band should have covered the gap")
	}
}

// TestRatingBandOnlyAppliesToTheSameMode guards that adding the band did not
// weaken the existing mode filter.
func TestRatingBandOnlyAppliesToTheSameMode(t *testing.T) {
	m := NewMatchmaker()
	m.SetRatingBand(0, 0) // band disabled: only the mode can refuse
	_ = m.Search(bandSession(1, 1000), 1, 0, nil)
	if pm := m.Search(bandSession(2, 1000), 2, 0, nil); pm != nil {
		t.Error("coaches in different modes were paired")
	}
}
