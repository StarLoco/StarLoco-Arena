package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestUnpricedCardsAreNotPurchasable is the SECURITY regression for free card
// minting through the Card Master (5450).
//
// Cost was derived only from the template's Price map, and BuyCards skips any
// entry with amount <= 0 - so an empty or all-zero price meant a free card.
// Measured against shipped data, not assumed: of 907 cards, 62 sit in a card set
// with NO price and 702 more carry an all-zero price, so 764 templates were
// mintable in batches of 64 per packet. shopID arrives unvalidated, so the
// attacker picks whichever set holds the card it wants and never goes near a
// Card Master.
//
// That these are genuinely not-for-sale rather than free was confirmed by reading
// the data: card 51 has price map[1:0] and value 36200, card 449 price map[1:0]
// and value 17234. A zero entry means "no price in this currency", not "free" -
// those cards come from drops, rewards and fusion.
func TestUnpricedCardsAreNotPurchasable(t *testing.T) {
	cases := []struct {
		name string
		card *gamedata.CoachCard
		want bool
	}{
		{"nil card", nil, false},
		{"no price map at all", &gamedata.CoachCard{ID: 1, CardSet: 5}, false},
		{"empty price map", &gamedata.CoachCard{ID: 2, CardSet: 5, Price: map[uint8]int32{}}, false},
		{"all-zero price (the 702-card case)", &gamedata.CoachCard{ID: 3, CardSet: 5, Price: map[uint8]int32{1: 0}}, false},
		{"negative price", &gamedata.CoachCard{ID: 4, CardSet: 5, Price: map[uint8]int32{1: -50}}, false},
		{"mixed zero and negative", &gamedata.CoachCard{ID: 5, Price: map[uint8]int32{1: 0, 2: -1}}, false},
		{"one positive currency", &gamedata.CoachCard{ID: 6, Price: map[uint8]int32{1: 100}}, true},
		{"positive alongside zero", &gamedata.CoachCard{ID: 7, Price: map[uint8]int32{1: 0, 2: 40}}, true},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			if got := cardIsPurchasable(tc.card); got != tc.want {
				t.Errorf("cardIsPurchasable = %v, want %v", got, tc.want)
			}
		})
	}
}

// TestStockedIsNotTheSameAsPurchasable records the deliberate asymmetry, so
// nobody "fixes" one side into agreement with the other by accident.
//
// A Card Master advertises its whole panoplie including unpriced cards
// (TestCardMasterStockIsItsCardSet pins that, and it predates this work). I did
// briefly filter the catalogue too and it broke that invariant - reverted,
// because what retail DISPLAYED is a parity question I have no client evidence
// for, while what the server HANDS OVER is not.
func TestStockedIsNotTheSameAsPurchasable(t *testing.T) {
	free := &gamedata.CoachCard{ID: 17, CardSet: 5}
	cards := gamedata.NewCards(
		&gamedata.CoachCard{ID: 16, CardSet: 5, Price: map[uint8]int32{0: 1}},
		free,
	)

	if !shopSells(cards, 5, free.ID) {
		t.Error("the unpriced card should still be STOCKED by its card master")
	}
	if cardIsPurchasable(free) {
		t.Error("...but it must not be PURCHASABLE")
	}
}
