package domain

// Ladder strength/level/rank model, ported verbatim from the client's
// DofusArenaConstants (com/ankamagames/dofusarena/common/constants/
// DofusArenaConstants.java) so server-computed Levels/Ranks match exactly
// what the client would derive from the same strength value.
const (
	// StrengthMin/StrengthMax bound a *ranked* coach's 1v1 ladder rating
	// (COACH_MIN_STRENGTH / COACH_MAX_STRENGTH in the client). A strength
	// of 0 is the special "unranked / no fight played yet" sentinel and is
	// deliberately outside this range.
	StrengthMin int32 = 1000
	StrengthMax int32 = 3000

	// StrengthUnranked is the rating a coach is seeded to the first time a
	// fight result is applied, before the win/loss delta. It corresponds to
	// Level 1 (the floor of the ranked range).
	StrengthUnranked int32 = StrengthMin

	// StrengthDelta is how much a single fight shifts the winner's (+) and
	// loser's (-) rating under the simple fixed-delta ladder model. Kept as
	// a tunable constant rather than a full ELO computation (which would
	// need both coaches' ratings at fight-end).
	StrengthDelta int32 = 25

	// LevelMin/LevelMax mirror COACH_MIN_LEVEL / COACH_MAX_LEVEL.
	LevelMin = 1
	LevelMax = 50
)

// StrengthToLevel converts a ladder strength to a coach Level. Mirrors
// DofusArenaConstants.strengthToLevel: strength < 1 yields Level 0 (the
// client renders this as "-"); otherwise Level = 1 + round((s-1000)/2000*49).
func StrengthToLevel(strength int32) int {
	if strength < 1 {
		return 0
	}
	// Match Java's Math.round(float): round half up on a float32
	// intermediate, exactly as the client computes it.
	level := 1 + int(roundHalfUp(float64(strength-1000)/2000.0*49.0))
	return level
}

// LevelToRank converts a Level to a ladder Rank (1/2/3). Mirrors
// DofusArenaConstants.levelToRank.
func LevelToRank(level int) int16 {
	if level <= 15 {
		return 1
	}
	if level <= 30 {
		return 2
	}
	return 3
}

// StrengthToRank is the composed convenience used for the Rank display.
func StrengthToRank(strength int32) int16 {
	return LevelToRank(StrengthToLevel(strength))
}

// ClampStrength constrains a ranked strength to [StrengthMin, StrengthMax].
// A strength of exactly 0 (unranked) is passed through untouched so the
// "-" sentinel is preserved.
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

// ApplyFightStrength computes a coach's new ladder strength after a fight
// under the fixed-delta model. An unranked coach (strength 0) is first
// seeded to StrengthUnranked so their very first result places them on the
// ladder. The result is always clamped to the ranked range.
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

// roundHalfUp replicates java.lang.Math.round(float)'s "round half towards
// positive infinity" semantics used by strengthToLevel.
func roundHalfUp(v float64) float64 {
	return floorFloat(v + 0.5)
}

func floorFloat(v float64) float64 {
	i := float64(int64(v))
	if v < 0 && i != v {
		i--
	}
	return i
}
