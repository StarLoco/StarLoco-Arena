package gamedata

import (
	"os"
	"testing"
)

// clientBdataDir is where the (git-ignored) 2.70 client data lives locally.
const clientBdataDir = `E:\Projets\DofusArena2-06\client\compiled\game\contents\bdata`

// TestOpenRealData loads the real client store if present; skips otherwise so
// the suite passes on machines without the copyrighted data files.
func TestOpenRealData(t *testing.T) {
	if _, err := os.Stat(clientBdataDir + `\data.bdat`); err != nil {
		t.Skip("client data.bdat not present; skipping")
	}
	store, err := Open(clientBdataDir)
	if err != nil {
		t.Fatalf("Open: %v", err)
	}

	total := 0
	for _, typ := range []int32{TypeCoachCard, TypeSpell, TypeEffect, TypeFighterCard, TypeSummoning} {
		n := len(store.EntriesOf(typ))
		t.Logf("type %d: %d entries", typ, n)
		total += n
	}
	if total == 0 {
		t.Fatal("no records indexed")
	}

	// Decode all coach cards and sanity-check.
	cards, err := store.LoadCards()
	if err != nil {
		t.Fatalf("LoadCards: %v", err)
	}
	t.Logf("loaded %d coach cards", cards.Len())
	if cards.Len() == 0 {
		t.Fatal("no coach cards decoded")
	}
	// Every decoded card should have a positive id.
	for id, c := range cards.All() {
		if c.ID != id || c.ID <= 0 {
			t.Fatalf("bad card id: key=%d rec=%d", id, c.ID)
		}
	}

	// The token-price map (field 5) should decode for at least some cards.
	// Log a few examples so the currency-type bytes in use are visible.
	priced, logged := 0, 0
	for _, c := range cards.All() {
		if len(c.Price) > 0 {
			priced++
			if logged < 5 {
				t.Logf("card %d value=%d price=%v", c.ID, c.Value, c.Price)
				logged++
			}
		}
	}
	t.Logf("%d/%d cards have a token price", priced, cards.Len())

	// Priced() must return exactly the priced set, in ascending order.
	ids := cards.Priced()
	if len(ids) != priced {
		t.Errorf("Priced() returned %d ids, want %d", len(ids), priced)
	}
	for i := 1; i < len(ids); i++ {
		if ids[i-1] >= ids[i] {
			t.Fatalf("Priced() not ascending at %d: %d >= %d", i, ids[i-1], ids[i])
		}
	}
}
