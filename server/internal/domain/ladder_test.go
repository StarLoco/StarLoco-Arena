package domain

import "testing"

func TestStrengthToLevel(t *testing.T) {
	cases := []struct {
		strength int32
		want     int
	}{
		{0, 0},     // unranked sentinel -> "-"
		{-5, 0},    // negative guarded to 0
		{1000, 1},  // COACH_MIN_STRENGTH -> Level 1
		{3000, 50}, // COACH_MAX_STRENGTH -> Level 50 (LevelMax)
		{2000, 26}, // midpoint: 1 + round(1000/2000*49)=1+round(24.5)=1+25
		{1500, 13}, // 1 + round(500/2000*49)=1+round(12.25)=1+12
		{2500, 38}, // 1 + round(1500/2000*49)=1+round(36.75)=1+37
	}
	for _, c := range cases {
		if got := StrengthToLevel(c.strength); got != c.want {
			t.Errorf("StrengthToLevel(%d) = %d, want %d", c.strength, got, c.want)
		}
	}
}

func TestLevelToRank(t *testing.T) {
	cases := []struct {
		level int
		want  int16
	}{
		{0, 1}, {1, 1}, {15, 1}, // <=15 -> rank 1
		{16, 2}, {30, 2}, // <=30 -> rank 2
		{31, 3}, {50, 3}, // otherwise rank 3
	}
	for _, c := range cases {
		if got := LevelToRank(c.level); got != c.want {
			t.Errorf("LevelToRank(%d) = %d, want %d", c.level, got, c.want)
		}
	}
}

func TestClampStrength(t *testing.T) {
	cases := []struct {
		in, want int32
	}{
		{0, 0},       // unranked sentinel preserved
		{500, 1000},  // below floor
		{1000, 1000}, // at floor
		{2200, 2200}, // in range
		{3000, 3000}, // at ceiling
		{5000, 3000}, // above ceiling
	}
	for _, c := range cases {
		if got := ClampStrength(c.in); got != c.want {
			t.Errorf("ClampStrength(%d) = %d, want %d", c.in, got, c.want)
		}
	}
}

func TestApplyFightStrength(t *testing.T) {
	// Unranked coach's first result seeds from StrengthUnranked (1000).
	if got := ApplyFightStrength(0, true); got != StrengthUnranked+StrengthDelta {
		t.Errorf("first win from unranked = %d, want %d", got, StrengthUnranked+StrengthDelta)
	}
	if got := ApplyFightStrength(0, false); got != StrengthMin {
		// 1000 - 25 = 975 -> clamped up to 1000
		t.Errorf("first loss from unranked = %d, want %d", got, StrengthMin)
	}

	// Ranked coach shifts by delta.
	if got := ApplyFightStrength(2000, true); got != 2025 {
		t.Errorf("win from 2000 = %d, want 2025", got)
	}
	if got := ApplyFightStrength(2000, false); got != 1975 {
		t.Errorf("loss from 2000 = %d, want 1975", got)
	}

	// Clamped at the ceiling / floor.
	if got := ApplyFightStrength(StrengthMax, true); got != StrengthMax {
		t.Errorf("win at ceiling = %d, want %d", got, StrengthMax)
	}
	if got := ApplyFightStrength(StrengthMin, false); got != StrengthMin {
		t.Errorf("loss at floor = %d, want %d", got, StrengthMin)
	}
}
