package gamedata

import (
	"os"
	"testing"
)

// TestRealDataFilesParse loads the actual production .dat files (from
// ../../data relative to this package) to catch format-parsing
// regressions against real data, not just synthetic buffers. Skipped if
// the data directory isn't present (e.g. in a CI checkout without the
// game assets).
func TestRealDataFilesParse(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/cards.dat"); err != nil {
		t.Skipf("real data dir not available (%v), skipping", err)
	}

	store := NewStore(dataDir)

	t.Run("CoachCards", func(t *testing.T) {
		if err := store.CoachCards.WarmUp(); err != nil {
			t.Fatalf("CoachCards load failed: %v", err)
		}
		t.Logf("loaded %d coach cards", store.CoachCards.Len())
	})

	t.Run("FighterCards", func(t *testing.T) {
		if err := store.FighterCards.WarmUp(); err != nil {
			t.Fatalf("FighterCards load failed: %v", err)
		}
		n := store.FighterCards.Len()
		t.Logf("loaded %d fighter cards", n)
		if n == 0 {
			t.Error("FighterCards loaded ZERO entries -- equipment save would silently drop all objects")
		}
		// Print a few IDs so we can eyeball whether they look sane.
		for i, fc := range store.FighterCards.All() {
			if i >= 5 {
				break
			}
			t.Logf("  fighter card id=%d type=%d value=%d subType=%d", fc.ID, fc.Type, fc.Value, fc.SubType)
		}

		// Verify the USE/EQUIP effect split actually finds passive
		// equip-time bonuses in the shipped data -- otherwise the
		// equipment-stat feature (combat.ApplyEquipmentBonuses) would be
		// dead code. Confirmed by decoding cards.dat: ~122 FIGHTER_CARD_EQUIP
		// effects across the deck, including many Initiative buffs
		// (actionID 76) and debuffs (actionID 77).
		var totalEquip, totalUse, initEquip int
		for _, fc := range store.FighterCards.All() {
			totalEquip += len(fc.EquipEffects)
			totalUse += len(fc.UseEffects)
			if len(fc.EquipEffects)+len(fc.UseEffects) != len(fc.Effects) {
				t.Errorf("card id=%d: use(%d)+equip(%d) != total(%d) -- split lost/duplicated effects",
					fc.ID, len(fc.UseEffects), len(fc.EquipEffects), len(fc.Effects))
			}
			for _, e := range fc.EquipEffects {
				if e.ActionID == 76 || e.ActionID == 77 { // INIT_BOOST / INIT_DEBOOST
					initEquip++
				}
			}
		}
		t.Logf("fighter-card effects: %d use-time, %d equip-time (%d of them Initiative buffs/debuffs)",
			totalUse, totalEquip, initEquip)
		if totalEquip == 0 {
			t.Error("ZERO equip-time effects found in real cards.dat -- equipment passive stats (incl. Initiative) would never apply in fights")
		}
		if initEquip == 0 {
			t.Error("ZERO equip-time Initiative effects found -- expected many (actionID 76/77) per the decoded data")
		}
	})

	t.Run("Spells", func(t *testing.T) {
		if err := store.Spells.WarmUp(); err != nil {
			t.Fatalf("Spells load failed: %v", err)
		}
		n := store.Spells.Len()
		t.Logf("loaded %d spells", n)
		if n == 0 {
			t.Error("Spells loaded ZERO entries")
		}
	})

	t.Run("Events", func(t *testing.T) {
		if err := store.Events.WarmUp(); err != nil {
			t.Fatalf("Events load failed: %v", err)
		}
		t.Logf("loaded %d events", store.Events.Len())
	})

	t.Run("Summonings", func(t *testing.T) {
		if err := store.Summonings.WarmUp(); err != nil {
			t.Fatalf("Summonings load failed: %v", err)
		}
		t.Logf("loaded %d summonings", store.Summonings.Len())
	})

	t.Run("StaticEffectAreas", func(t *testing.T) {
		if err := store.StaticEffectAreas.WarmUp(); err != nil {
			t.Fatalf("StaticEffectAreas load failed: %v", err)
		}
		n := store.StaticEffectAreas.Len()
		t.Logf("loaded %d static effect areas", n)
		if n == 0 {
			t.Error("StaticEffectAreas loaded ZERO entries -- confirmed via manual reverse-engineering that staticEffects.dat has 10 real entries (docs/04-game-data-format.md §4.5)")
		}
		var trapCount int
		for _, a := range store.StaticEffectAreas.All() {
			if a.EffectAreaType == "TRAP" {
				trapCount++
				t.Logf("  trap id=%d areaShapeId=%d maxExecutionCount=%d effects=%d", a.ID, a.AreaShapeID, a.MaxExecutionCount, len(a.Effects))
			}
		}
		if trapCount != 2 {
			t.Errorf("trapCount = %d, want 2 (confirmed via manual reverse-engineering)", trapCount)
		}
	})
}
