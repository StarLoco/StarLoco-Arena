package gamedata

import "testing"

// TestSplitFighterCardEffects verifies that a fighter card's effects are
// partitioned into USE-time (actively castable) and EQUIP-time (passive
// stat) subsets by their trimmed container type, mirroring
// AbstractFighterCard.addEffect. The real cards.dat pads the container type
// with trailing spaces, so the padded forms must be handled too.
func TestSplitFighterCardEffects(t *testing.T) {
	effects := []EffectDef{
		{ID: 1, ParentType: "FIGHTER_CARD_USE", ActionID: 4},      // castable
		{ID: 2, ParentType: "FIGHTER_CARD_EQUIP", ActionID: 76},   // passive +Init
		{ID: 3, ParentType: "FIGHTER_CARD_EQUIP  ", ActionID: 11}, // padded passive +HP
		{ID: 4, ParentType: "  FIGHTER_CARD_USE  ", ActionID: 2},  // padded castable
		{ID: 5, ParentType: "FIGHTER_CARD", ActionID: 42},         // legacy/unsuffixed -> use
	}
	use, equip := splitFighterCardEffects(effects)

	wantUse := map[int32]bool{1: true, 4: true, 5: true}
	wantEquip := map[int32]bool{2: true, 3: true}

	if len(use) != len(wantUse) {
		t.Fatalf("use len = %d, want %d (%+v)", len(use), len(wantUse), use)
	}
	for _, e := range use {
		if !wantUse[e.ID] {
			t.Errorf("effect id=%d wrongly classified as USE", e.ID)
		}
	}
	if len(equip) != len(wantEquip) {
		t.Fatalf("equip len = %d, want %d (%+v)", len(equip), len(wantEquip), equip)
	}
	for _, e := range equip {
		if !wantEquip[e.ID] {
			t.Errorf("effect id=%d wrongly classified as EQUIP", e.ID)
		}
	}
}

func TestTrimSpace(t *testing.T) {
	cases := map[string]string{
		"":                     "",
		"   ":                  "",
		"FIGHTER_CARD_EQUIP":   "FIGHTER_CARD_EQUIP",
		"  FIGHTER_CARD_USE  ": "FIGHTER_CARD_USE",
		"AREA              ":   "AREA",
		"no pad":               "no pad",
	}
	for in, want := range cases {
		if got := trimSpace(in); got != want {
			t.Errorf("trimSpace(%q) = %q, want %q", in, got, want)
		}
	}
}
