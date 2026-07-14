package main

import (
	"math/rand"
	"os"
	"testing"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/gamedata"
)

// loadRealIndex loads the real game-data store and builds a dataIndex, or
// skips the test when the data directory is absent (matching the repo's
// real-data-tests-skip convention).
func loadRealIndex(t *testing.T) *dataIndex {
	t.Helper()
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir); err != nil {
		t.Skipf("gamedata dir %q not present, skipping real-data test", dataDir)
	}
	store := gamedata.NewStore(dataDir)
	idx := buildDataIndex(store)
	if len(idx.spellsByBreed) == 0 {
		t.Skip("no breed spells loaded, skipping")
	}
	return idx
}

func TestGenerateLoadout_ProducesLegalBuilds(t *testing.T) {
	idx := loadRealIndex(t)
	rng := rand.New(rand.NewSource(12345))

	playable := make(map[byte]bool)
	for _, b := range combat.AllPlayableBreeds() {
		playable[b] = true
	}

	for i := 0; i < 500; i++ {
		lo := idx.generateLoadout(rng)

		if !playable[lo.Breed] {
			t.Fatalf("iter %d: breed %d is not playable", i, lo.Breed)
		}
		if len(lo.SpellIDs) > maxFighterSpells {
			t.Fatalf("iter %d: %d spells > max %d", i, len(lo.SpellIDs), maxFighterSpells)
		}
		// No duplicate spells; every spell belongs to the breed.
		seen := map[int32]bool{}
		for _, id := range lo.SpellIDs {
			if seen[id] {
				t.Fatalf("iter %d: duplicate spell %d", i, id)
			}
			seen[id] = true
			if !containsInt32(idx.spellsByBreed[lo.Breed], id) {
				t.Fatalf("iter %d: spell %d not in breed %d pool", i, id, lo.Breed)
			}
		}
		// At most one card per slot.
		usedSlot := map[int16]bool{}
		for _, id := range lo.ObjectIDs {
			slot := slotOfCard(t, idx, id)
			if usedSlot[slot] {
				t.Fatalf("iter %d: two cards in slot %d", i, slot)
			}
			usedSlot[slot] = true
		}
		// A single fighter's value must be well under the team cap.
		if int(lo.Budget) > maxTeamValue {
			t.Fatalf("iter %d: fighter budget %d exceeds team cap %d", i, lo.Budget, maxTeamValue)
		}
	}
}

// slotOfCard finds which equipment slot a generated object id occupies by
// scanning the index's per-slot lists (a small, test-only reverse lookup).
func slotOfCard(t *testing.T, idx *dataIndex, id int32) int16 {
	t.Helper()
	for slot, ids := range idx.cardsBySlot {
		if containsInt32(ids, id) {
			return slot
		}
	}
	t.Fatalf("card %d not found in any slot", id)
	return 0
}

func TestGenerateLoadout_ProducesVariety(t *testing.T) {
	idx := loadRealIndex(t)
	rng := rand.New(rand.NewSource(999))

	// Over many generations we should see multiple distinct breeds and
	// multiple distinct spell-set signatures (the "never identical" goal).
	breeds := map[byte]bool{}
	sigs := map[string]bool{}
	for i := 0; i < 200; i++ {
		lo := idx.generateLoadout(rng)
		breeds[lo.Breed] = true
		sig := ""
		for _, id := range lo.SpellIDs {
			sig += string(rune(id)) + ","
		}
		sigs[sig] = true
	}
	if len(breeds) < 3 {
		t.Fatalf("expected variety across breeds, only saw %d distinct", len(breeds))
	}
	if len(sigs) < 20 {
		t.Fatalf("expected many distinct spell sets, only saw %d", len(sigs))
	}
}

func TestPickCoachCardSet_ReturnsDistinct(t *testing.T) {
	idx := loadRealIndex(t)
	if len(idx.coachCardIDs) < 3 {
		t.Skip("fewer than 3 coach cards, skipping distinctness check")
	}
	rng := rand.New(rand.NewSource(7))
	set := idx.pickCoachCardSet(rng, 3)
	if len(set) != 3 {
		t.Fatalf("set size = %d, want 3", len(set))
	}
	seen := map[int32]bool{}
	for _, id := range set {
		if seen[id] {
			t.Fatalf("duplicate coach card %d in set", id)
		}
		seen[id] = true
	}
}
