package gamedata

import (
	"os"
	"path/filepath"
	"testing"
)

// TestCardResurrectPercentReal pins the resurrection-effect decode against the
// real card table. The percentages are read from each card's effect array
// (action 13, param[0]); getting the aPp field layout wrong (e.g. the i32
// floatParams count) would misalign the array and yield garbage, so this asserts
// the exact known values and that NO other card is mistaken for a resurrection
// card.
func TestCardResurrectPercentReal(t *testing.T) {
	st, err := Open(filepath.Join("..", "..", "data"))
	if err != nil {
		t.Skipf("game data not available: %v", err)
	}
	cards, err := st.LoadCards()
	if err != nil {
		t.Fatalf("LoadCards: %v", err)
	}

	want := map[int32]int32{
		35: 5, 51: 12, 53: 10, 137: 1,
		305: 100, 316: 100, 317: 100, 318: 100,
	}
	for id, pct := range want {
		c := cards.Get(id)
		if c == nil {
			t.Errorf("card %d missing", id)
			continue
		}
		if c.ResurrectPercent != pct {
			t.Errorf("card %d ResurrectPercent = %d, want %d", id, c.ResurrectPercent, pct)
		}
	}

	// Exactly the eight known cards carry a resurrection effect, and every percent
	// is in range â€” a misaligned decode would flag extra cards or wild values.
	got := 0
	for _, c := range cards.All() {
		if c.ResurrectPercent == 0 {
			continue
		}
		got++
		if _, ok := want[c.ID]; !ok {
			t.Errorf("unexpected resurrection card %d (%d%%)", c.ID, c.ResurrectPercent)
		}
		if c.ResurrectPercent < 1 || c.ResurrectPercent > 100 {
			t.Errorf("card %d ResurrectPercent = %d out of 1..100 (decode misaligned?)",
				c.ID, c.ResurrectPercent)
		}
	}
	if got != len(want) {
		t.Errorf("resurrection card count = %d, want %d", got, len(want))
	}
}

// TestCoachCardFullDecodeReal pins the fields beyond the header that used to be
// skipped. The two tradability flags are the ones that matter: the server, not the
// client, has to police what may be staked in an exchange.
func TestCoachCardFullDecodeReal(t *testing.T) {
	if _, err := os.Stat(clientBdataDir + `\data.bdat`); err != nil {
		t.Skip("no client data")
	}
	st, err := Open(clientBdataDir)
	if err != nil {
		t.Fatal(err)
	}
	cards, err := st.LoadCards()
	if err != nil {
		t.Fatal(err)
	}

	var bound, undestructible, withLevel, obtainable, ranked, usable int
	for id, c := range cards.All() {
		if c.Bound {
			bound++
		}
		if c.Undestructible {
			undestructible++
		}
		if c.RequiredLevel > 0 {
			withLevel++
		}
		if c.ObtainableInDraw {
			obtainable++
		}
		if c.Rank > 0 {
			ranked++
		}
		if c.HasUsableAction {
			usable++
		}
		// Canaries against a field-order slip: these are all small bounded values.
		if c.RequiredLevel < 0 || c.RequiredLevel > 200 {
			t.Errorf("card %d: implausible requiredLevel %d", id, c.RequiredLevel)
		}
		if c.Rank < 0 || c.Rank > 100 {
			t.Errorf("card %d: implausible rank %d", id, c.Rank)
		}
		if c.DropPercent < 0 || c.DropPercent > 100 {
			t.Errorf("card %d: implausible dropPercent %d", id, c.DropPercent)
		}
	}
	if ranked == 0 {
		t.Error("no card has a rank; field 18 is misaligned")
	}
	if bound == 0 && undestructible == 0 {
		t.Error("no card is bound or undestructible; fields 12/13 look misaligned")
	}
	t.Logf("%d cards: bound=%d undestructible=%d requiredLevel>0=%d obtainable=%d ranked=%d usable=%d",
		cards.Len(), bound, undestructible, withLevel, obtainable, ranked, usable)
}
