package game

// postfight_wounds.go — the post-fight WOUND and DEATH rolls.
//
// This is the part of the META layer that gives a fight consequences: a fighter
// can come out of it with a broken leg, and enough broken bones kill it for good.
//
// The algorithm is `bf_1.b(et_2)` in the client, transcribed below. `bf_1.b` has
// **zero callers in the client** — it is server-side logic that happens to be
// shipped inside `core.jar`, which is why we can reproduce it exactly instead of
// inventing one.

import (
	"math/rand"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// seriousWoundsThatKill is the client's `n4 - mm_02.size() >= 3` test: once a
// fighter already carries THREE serious wounds, the next upgrade kills it
// instead of inflicting a fourth.
const seriousWoundsThatKill = 3

// lightWoundsForcingUpgrade is `mm_02.size() >= 3`: three light wounds guarantee
// that the next injury upgrades one of them rather than adding a fourth.
const lightWoundsForcingUpgrade = 3

// woundOutcome is what a roll did to a fighter.
type woundOutcome struct {
	// WoundID is the condition inflicted (0 = none).
	WoundID int16
	// Upgraded is true when a light wound was replaced by its serious form
	// rather than a new light wound being added.
	Upgraded bool
	// Died is true when the fighter took its fourth serious wound and is now
	// permanently dead (client state 2).
	Died bool
}

// rollWound runs the client's `bf_1.b`: decide whether this fight upgrades an
// existing light wound, kills the fighter, or inflicts a fresh light wound, and
// mutate the fighter accordingly.
//
// The client's own structure, kept:
//
//  1. Walk the held conditions. A wound on a body part — light OR serious, via a
//     deliberate switch fall-through — excludes that part from the new-wound
//     draw and counts toward `woundedParts`.
//  2. Upgrade if 3+ light wounds are held, or all 5 parts are wounded, or a
//     d100 lands under `lightHeld² × 10` (so 10% / 40% / 90% at 1 / 2 / 3 light
//     wounds).
//  3. On upgrade with 3 serious wounds already held → the fighter DIES.
//  4. On upgrade → remove a random light wound, add the serious wound of the
//     same body part.
//  5. Otherwise → add a light wound on a random un-wounded body part.
func rollWound(defs *gamedata.Conditions, f *domain.Fighter, rng *rand.Rand) woundOutcome {
	if defs == nil || f == nil || rng == nil {
		return woundOutcome{}
	}
	light, serious, woundedParts := heldWounds(defs, f)

	// --- 2. upgrade? -------------------------------------------------------
	upgrade := len(light) >= lightWoundsForcingUpgrade ||
		len(woundedParts) == gamedata.WoundBodyParts ||
		rng.Intn(100)+1 <= len(light)*len(light)*10

	// --- 3. death ----------------------------------------------------------
	if upgrade && len(serious) >= seriousWoundsThatKill {
		f.State = domain.FighterStateDead
		return woundOutcome{Died: true}
	}

	// --- 4. upgrade a held light wound to its serious form ------------------
	if upgrade {
		if len(light) == 0 {
			// The dice said "upgrade" but there is nothing to upgrade. The client
			// would call nextInt(0) and throw here; a fight actor must not panic,
			// so treat it as "no injury this fight".
			return woundOutcome{}
		}
		victim := light[rng.Intn(len(light))]
		seriousDefs := defs.OfType(gamedata.SeriousTypeOf(victim.BodyPartOf()))
		if len(seriousDefs) == 0 {
			return woundOutcome{}
		}
		promoted := seriousDefs[rng.Intn(len(seriousDefs))]
		removeCondition(f, victim.ID)
		f.Conditions = append(f.Conditions, domain.FighterCondition{
			FighterID:   f.ID,
			ConditionID: promoted.ID,
			Remaining:   promoted.Duration,
		})
		return woundOutcome{WoundID: promoted.ID, Upgraded: true}
	}

	// --- 5. a fresh light wound on an un-wounded body part ------------------
	//
	// The client builds its draw pool from body parts 1..4 ONLY — part 5
	// ("other") is summed into neither the pool nor any exclusion flag, so the
	// shipped roller can never inflict a light "other" wound. That is a quirk of
	// the shipped code, not of this port; it is reproduced deliberately, because
	// diverging would make our wound distribution differ from retail. See BUGS.md.
	var pool []*gamedata.Condition
	for part := int16(1); part <= 4; part++ {
		if woundedParts[part] {
			continue
		}
		pool = append(pool, defs.OfType(part)...)
	}
	if len(pool) == 0 {
		// Every drawable part is already wounded. The client calls nextInt(0)
		// here and throws; we report "no injury" instead.
		return woundOutcome{}
	}
	wound := pool[rng.Intn(len(pool))]
	f.Conditions = append(f.Conditions, domain.FighterCondition{
		FighterID:   f.ID,
		ConditionID: wound.ID,
		Remaining:   wound.Duration,
	})
	return woundOutcome{WoundID: wound.ID}
}

// rollInjuryChances is the client's `adl_0.atd()`: derive this fighter's injury
// and death percentages from its lifetime XP, and charge the extra fatigue the
// roll itself costs.
//
//	cni = totalXp * 100 / Pv          (Pv = 100 000, so injury% = totalXp / 1000)
//	if cni == 0 -> injuries cancelled
//	cnm = cni² / 100                  (death% grows QUADRATICALLY with injury%)
//	if cnm == 0 -> death cancelled
//	fatigue += rand(13); then ate()
//
// The shape is worth reading twice: a veteran is far more fragile than a rookie.
// At 10 000 lifetime XP the injury chance is 10% and death 1%; at 50 000 it is
// 50% and 25%.
func (r *postFightReport) rollInjuryChances(totalXP int32, rng *rand.Rand) {
	r.injuryRolled = true
	chance := totalXP * 100 / deathXPScale
	r.injuryChance = chance
	if r.injuryChance == 0 {
		r.injuryCancel = true
	}
	r.deathChance = chance * chance / 100
	if r.deathChance == 0 {
		r.resurrected = true
	}
	extra := int32(rng.Intn(13))
	r.tirednessDelta += int8(extra)
	r.tiredness = int8(clampInt32(int32(r.tiredness)+extra, 0, maxTiredness))
	r.finaliseTiredness()
}

// applyWoundAndDeathRolls decides whether this fighter is actually hurt or
// killed, and mutates it. Must run AFTER the card/set modifiers have shifted
// `injuryChance` / `deathChance` and the cancel flags.
//
// HONEST LIMIT: the client computes and modifies these percentages but never
// consumes them — the consumer is the real server. So the *chances* are exact
// and the *wound table* is exact, but "roll d100 against each, death first" is
// our reading of how they are spent. It is the only reading consistent with the
// two cancel flags (`injuryCancel`, `resurrected`) existing at all.
func (d *Deps) applyWoundAndDeathRolls(r *postFightReport, f *domain.Fighter, rng *rand.Rand) {
	if r == nil || f == nil || rng == nil || d.Conditions == nil {
		return
	}
	if !r.injuryRolled {
		return
	}
	// Death first: a fighter that dies takes no wound (it has bigger problems,
	// and the client's own roller returns early on death too).
	if !r.resurrected && r.deathChance > 0 && int32(rng.Intn(100)) < r.deathChance {
		f.State = domain.FighterStateDead
		r.dead = true
		return
	}
	if r.injuryCancel || r.injuryChance <= 0 {
		return
	}
	if int32(rng.Intn(100)) >= r.injuryChance {
		return // got away with it
	}
	out := rollWound(d.Conditions, f, rng)
	r.woundID = int32(out.WoundID)
	if out.Died {
		r.dead = true
	}
}
