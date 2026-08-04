package domain

// Ladder strength/level/rank model, ported from the DofusArena client's
// DofusArenaConstants (the same values the client uses to derive a coach's Level
// and Rank from its 1v1 ladder Strength). A fight shifts the winner's Strength up
// and the loser's down by a fixed delta; the client renders Level/Rank from the
// resulting absolute Strength the server sends in END_FIGHT (8300).
const (
	// StrengthMin/StrengthMax bound a ranked coach's 1v1 rating. Strength 0 is the
	// special "unranked / no fight played yet" sentinel, outside this range.
	StrengthMin int32 = 1000
	StrengthMax int32 = 3000

	// StrengthUnranked is the rating a coach is seeded to on its first fight result
	// (before the win/loss delta) — the floor of the ranked range (Level 1).
	StrengthUnranked int32 = StrengthMin

	// StrengthDelta is how much one fight shifts the winner (+) and loser (-). A
	// fixed-delta model (not full ELO, which would need both ratings at fight end).
	StrengthDelta int32 = 25

	LevelMin = 1
	LevelMax = 50
)

// ApplyFightStrength returns a coach's new ladder Strength after a fight. An
// unranked coach (0) is first seeded to StrengthUnranked so its first result
// places it on the ladder; the result is clamped to [StrengthMin, StrengthMax].
func ApplyFightStrength(current int32, won bool) int32 {
	if current == 0 {
		current = StrengthUnranked
	}
	if won {
		current += StrengthDelta
	} else {
		current -= StrengthDelta
	}
	return ClampStrength(current)
}

// ClampStrength constrains a ranked Strength to [StrengthMin, StrengthMax]. A
// Strength of exactly 0 (unranked) is passed through so the "-" sentinel survives.
func ClampStrength(strength int32) int32 {
	if strength == 0 {
		return 0
	}
	if strength < StrengthMin {
		return StrengthMin
	}
	if strength > StrengthMax {
		return StrengthMax
	}
	return strength
}

// StrengthToLevel converts a ladder Strength to a coach Level (0 = "-" for
// unranked), mirroring DofusArenaConstants.strengthToLevel:
// Level = 1 + round((s-1000)/2000*49).
func StrengthToLevel(strength int32) int {
	if strength < 1 {
		return 0
	}
	return 1 + int(roundHalfUp(float64(strength-1000)/2000.0*49.0))
}

// LevelToRank converts a Level to a ladder Rank (1/2/3), mirroring
// DofusArenaConstants.levelToRank.
func LevelToRank(level int) int16 {
	switch {
	case level <= 15:
		return 1
	case level <= 30:
		return 2
	default:
		return 3
	}
}

// roundHalfUp replicates java.lang.Math.round(float) (round half towards +inf).
func roundHalfUp(v float64) float64 {
	i := float64(int64(v + 0.5))
	if (v+0.5) < 0 && i != (v+0.5) {
		i--
	}
	return i
}
