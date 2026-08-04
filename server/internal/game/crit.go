package game

import (
	"math/rand"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// crit.go models critical hits and fumbles (ported from the v2.04b damage.go /
// effects.go crit path). Every cast rolls a FUMBLE at the caster's FumbleRate%,
// then (if not a fumble) a CRIT at its CritRate%. A fumble spends the AP but
// applies NO effects; a crit runs the spell's isCritical effect subset (authored
// with bigger rolls) instead of the normal one. Base rates are 5% crit / 1% fumble
// per breed (Breed.java CH/CM); cards & in-fight buffs (actions 70/71) shift them.

// rollFumble reports whether a cast fumbles: rng.Intn(100)+1 <= FumbleRate.
func (ff *FightFighter) rollFumble(rng *rand.Rand) bool {
	return ff != nil && ff.FumbleRate > 0 && rng.Intn(100)+1 <= int(ff.FumbleRate)
}

// rollCrit reports whether a cast is a critical hit: rng.Intn(100)+1 <= CritRate.
func (ff *FightFighter) rollCrit(rng *rand.Rand) bool {
	return ff != nil && ff.CritRate > 0 && rng.Intn(100)+1 <= int(ff.CritRate)
}

// selectEffectsForCrit returns the effect subset to run for the rolled hit type —
// the isCritical==crit effects — falling back to the others when none match, so a
// spell whose effects aren't crit-authored still resolves. Ported verbatim from
// the v2.04b selectEffectsForCrit.
func selectEffectsForCrit(effects []gamedata.Effect, crit bool) []gamedata.Effect {
	var match, other []gamedata.Effect
	for _, ef := range effects {
		if ef.IsCritical == crit {
			match = append(match, ef)
		} else {
			other = append(other, ef)
		}
	}
	if len(match) == 0 {
		return other
	}
	return match
}
