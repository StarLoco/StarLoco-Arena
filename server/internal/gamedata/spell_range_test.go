package gamedata

import (
	"os"
	"testing"
)

// TestNormalizeSpellRange verifies the AbstractSpell.java:54-55 range
// normalization: both clamped to >= 0 (automatic for a byte) and swapped
// so rangeMin <= rangeMax.
func TestNormalizeSpellRange(t *testing.T) {
	cases := []struct {
		rawMin, rawMax   byte
		wantMin, wantMax byte
	}{
		{0, 6, 0, 6}, // already ordered
		{6, 0, 0, 6}, // inverted (the Feca-teleport case) -> swapped
		{1, 6, 1, 6}, // ordered, non-zero min
		{6, 1, 1, 6}, // inverted, non-zero
		{4, 4, 4, 4}, // equal
		{0, 0, 0, 0}, // both zero (a self/no-range spell stays empty)
	}
	for _, c := range cases {
		gotMin, gotMax := normalizeSpellRange(c.rawMin, c.rawMax)
		if gotMin != c.wantMin || gotMax != c.wantMax {
			t.Errorf("normalizeSpellRange(%d,%d) = (%d,%d), want (%d,%d)",
				c.rawMin, c.rawMax, gotMin, gotMax, c.wantMin, c.wantMax)
		}
	}
}

// TestFecaTeleportSpellHasCastableRange is the regression guard for the
// reported "Feca teleport doesn't work" bug. The ROOT cause turned out to
// be a spells.dat field-order parser bug (NeedFreeCell was read one byte
// too early, shifting rangeMin/rangeMax and every later field). With the
// parser fixed, spell 140's real range is a normal (1,6) -- min <= max and
// max > 0, so it is castable. (The normalizeSpellRange swap remains in the
// loader to mirror the reference AbstractSpell constructor, but is a no-op
// for correctly-ordered data like this.)
func TestFecaTeleportSpellHasCastableRange(t *testing.T) {
	const dataDir = "../../data"
	if _, err := os.Stat(dataDir + "/spells.dat"); err != nil {
		t.Skipf("real data dir not available (%v), skipping", err)
	}
	store := NewStore(dataDir)
	sp, ok := store.Spells.Get(140)
	if !ok {
		t.Fatal("Feca teleport spell 140 not found in real data")
	}
	if sp.RangeMin > sp.RangeMax {
		t.Errorf("spell 140 range inverted: rangeMin=%d rangeMax=%d (want min <= max)", sp.RangeMin, sp.RangeMax)
	}
	if sp.RangeMax == 0 {
		t.Errorf("spell 140 rangeMax=0 -> empty castable range")
	}
	if sp.RangeMin != 1 || sp.RangeMax != 6 {
		t.Errorf("spell 140 range = (%d,%d), want (1,6)", sp.RangeMin, sp.RangeMax)
	}
}
