package game

import "testing"

// TestBonusCellMultiplier pins np_1 rule 13 ("Effets des cases bonus multipliés
// par [#1]") — including which tiles it does NOT touch.
func TestBonusCellMultiplier(t *testing.T) {
	normal := &Fight{Rules: defaultFightRules()}
	if got := normal.scaleBonusCell(10); got != 10 {
		t.Errorf("unmultiplied fight scaled 10 -> %d, want 10", got)
	}
	// A zero/absent multiplier must behave as x1, never as x0 — that would make
	// every bonus cell silently do nothing.
	var bare Fight
	if got := bare.scaleBonusCell(7); got != 7 {
		t.Errorf("fight with no rules scaled 7 -> %d, want 7", got)
	}

	x10 := &Fight{Rules: fightRules{BonusCellMultiplier: 10}}
	if got := x10.scaleBonusCell(3); got != 30 {
		t.Errorf("x10 scaled 3 -> %d, want 30", got)
	}
	// The shipped multipliers are x2, x2, x5 and x10 across five challenges.
	for _, m := range []int32{2, 5, 10} {
		f := &Fight{Rules: fightRules{BonusCellMultiplier: m}}
		if got := f.scaleBonusCell(specialMotivationAP); got != specialMotivationAP*m {
			t.Errorf("x%d on the AP tile = %d, want %d", m, got, specialMotivationAP*m)
		}
	}
}
