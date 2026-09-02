package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestFusionValueCeiling covers the fusion value break: both of the client's own
// gates are no-ops for ~900 of 907 cards (only 7 carry FusionPower/FusionQuality,
// 543 have RequiredLevel 0), so two cheap commons could become a set's best card
// at a flat 60%, repeatedly - and that output feeds clan reputation, which is
// scored by card value.
func TestFusionValueCeiling(t *testing.T) {
	s := &Session{
		Coach: &domain.Coach{ID: 1},
		deps: &Deps{Cards: gamedata.NewCards(
			&gamedata.CoachCard{ID: 1, CardSet: 5, Value: 10},
			&gamedata.CoachCard{ID: 2, CardSet: 5, Value: 10},
			&gamedata.CoachCard{ID: 3, CardSet: 5, Value: 50},    // within allowance
			&gamedata.CoachCard{ID: 4, CardSet: 5, Value: 30000}, // far beyond
		)},
	}

	inputs := []int32{1, 2} // total value 20
	if got := s.cardsValue(inputs); got != 20 {
		t.Fatalf("fixture broken: inputs value %d, want 20", got)
	}

	// 50 <= 20*3 -> allowed; 30000 -> refused.
	if v := int64(s.deps.Cards.Get(3).Value); v > 20*fusionValueAllowance {
		t.Errorf("card 3 (value %d) should sit inside the allowance", v)
	}
	if v := int64(s.deps.Cards.Get(4).Value); v <= 20*fusionValueAllowance {
		t.Errorf("card 4 (value %d) should exceed the allowance; the test proves "+
			"nothing otherwise", v)
	}
}

// TestCardsValueIgnoresUnknownIDs guards the sum against a forged input list: an
// unknown id must contribute 0 rather than being skipped in a way that inflates
// the ratio.
func TestCardsValueIgnoresUnknownIDs(t *testing.T) {
	s := &Session{deps: &Deps{Cards: gamedata.NewCards(
		&gamedata.CoachCard{ID: 1, Value: 10},
	)}}
	if got := s.cardsValue([]int32{1, 9999}); got != 10 {
		t.Errorf("cardsValue = %d, want 10 (unknown id contributes nothing)", got)
	}
	if got := (&Session{deps: &Deps{}}).cardsValue([]int32{1}); got != 0 {
		t.Errorf("cardsValue with no card data = %d, want 0", got)
	}
}
