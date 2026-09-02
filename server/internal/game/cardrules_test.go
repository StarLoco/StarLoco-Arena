package game

import "testing"

// TestCoachCardSlotRule pins the type->slot table against the client's own
// (aMK.java:6-36), where every wearable type declares exactly ONE legal position
// and everything else declares {-1}.
//
// applyEquipment previously mapped slot index -> Pos with an ownership check and
// nothing else, so any owned template could occupy any of the 14 slots. That
// matters because equippedCountsPerSet counts equipped cards per card SET, and
// set thresholds drive resurrection chance, XP, morale, fatigue, reputation and
// wound/death chance - fourteen cards of one set is every threshold at once.
func TestCoachCardSlotRule(t *testing.T) {
	// Type -> its one legal slot, straight from aMK.
	legal := map[int32]int16{
		2: 5, 3: 2, 4: 1, 5: 4, 6: 10, 7: 3,
		8: 8, 9: 6, 10: 11, 11: 0, 12: 7, 13: 9,
	}
	for typ, slot := range legal {
		if !coachCardFitsSlot(typ, slot) {
			t.Errorf("type %d should fit slot %d", typ, slot)
		}
		// ...and nowhere else.
		for other := int16(0); other < 14; other++ {
			if other == slot {
				continue
			}
			if coachCardFitsSlot(typ, other) {
				t.Errorf("type %d must NOT fit slot %d (only %d)", typ, other, slot)
			}
		}
	}

	// The non-equippable types: Smiley, Emote, Zaap, special card, firework,
	// title, Dofus and friends all declare {-1} in the client.
	for _, typ := range []int32{0, 1, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30} {
		if coachCardIsEquippable(typ) {
			t.Errorf("type %d is not equippable in the client but fits a slot here", typ)
		}
		for slot := int16(0); slot < 14; slot++ {
			if coachCardFitsSlot(typ, slot) {
				t.Errorf("non-equippable type %d accepted into slot %d", typ, slot)
			}
		}
	}
}

// TestExchangeStagingCap covers the 5-card table limit (CG.java:225-228). Each
// stage broadcasts to the OTHER player, whose 5-slot UI was never built for more.
func TestExchangeStagingCap(t *testing.T) {
	ex := &Exchange{ID: 1}
	ex.staged[0] = map[int32]StagedCard{}
	ex.staged[1] = map[int32]StagedCard{}

	for i := int32(0); i < maxStagedPerSide; i++ {
		if !ex.stageCard(0, StagedCard{TemplateID: i, Quantity: 1}) {
			t.Fatalf("stage %d should fit inside the cap", i)
		}
	}
	if ex.stageCard(0, StagedCard{TemplateID: 99, Quantity: 1}) {
		t.Error("staged past the cap")
	}
	// Re-staging a template already on the table is an UPDATE, not a new slot,
	// so it must still be allowed at the cap - otherwise changing a quantity
	// would be refused.
	if !ex.stageCard(0, StagedCard{TemplateID: 0, Quantity: 3}) {
		t.Error("updating an already-staged template was refused at the cap")
	}
	// The other side keeps its own budget.
	if !ex.stageCard(1, StagedCard{TemplateID: 1, Quantity: 1}) {
		t.Error("the other side was blocked by this side's table")
	}
}
