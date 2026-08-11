package game

// postfight_apply.go — runs the post-fight META pass at the end of a fight:
// build each fighter's report (postfight.go), let the coach's card-set bonuses
// modify it, bank the results onto the persistent fighters, and award the coach
// its reputation.
//
// Scope: XP, morale, fatigue and reputation, PLUS the wound and death rolls
// (applyWoundAndDeathRolls, B-066) and condition ageing. This note used to say
// the rolls were not run; they are.

import (
	"math/rand"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// AI-enum action ids that this META pass consumes. The full list lives in the
// client's `AI` enum, which still carries its original French descriptions.
const (
	aiXPPercent      int32 = 1  // "Bonus ou malus d'XP en %"
	aiXPFlat         int32 = 2  // "Bonus ou malus d'XP"
	aiXPPercentEnemy int32 = 3  // "... en % pour l'adversaire"
	aiXPFlatEnemy    int32 = 4  // "... pour l'adversaire"
	aiWound          int32 = 5  // "Modification des blessures de ses combattants"
	aiWoundEnemy     int32 = 6  // "... des blessures des combattants adverses"
	aiDeath          int32 = 7  // "Modification des chances de mort de ses combattants"
	aiDeathEnemy     int32 = 8  // "... des chances de mort des adversaires"
	aiMorale         int32 = 9  // "Modification du moral de ses combattants"
	aiMoraleEnemy    int32 = 10 // "... des combattants adverses"
	aiCancelWound    int32 = 11 // "Donne x% de chance d'annuler les blessures en fin de combat"
	aiReputation     int32 = 14 // "Modifie le gain de réputation"
	aiTiredness      int32 = 16 // "Modification de fatigue de ses combattants"
	aiTirednessEnemy int32 = 17 // "... des combattants adverses"
)

// baseXPPerFight is the XP a fight is worth before any modifier.
//
// HONEST LIMIT: this number is NOT recoverable from the client. `adl_0` receives
// `baseXp` already computed (the client only ever *reads* `cnr`), so the real
// server's formula for it is not in `core.jar`. Everything the value then flows
// through IS client-exact; only this seed is ours. It is deliberately a single
// named constant so it can be replaced the moment evidence turns up, rather than
// being smeared through the code.
//
// Chosen so that a fighter at neutral morale needs a plausible number of fights
// to clear level 1 (860 XP → ~9 fights) rather than to feel tuned.
const baseXPPerFight int32 = 100

// timeChallengeXPPerTurn mirrors the one XP rule the client DOES compute itself
// (WE.java, time challenges): turns/2 rounded両 ways, times the challenge's own
// multiplier. Not used yet — recorded here so the next person does not re-derive
// it. See docs/DATA-COVERAGE.md.

// runPostFightMeta produces the per-fighter debrief reports for a finished fight
// and applies their results. Returns the reports (wire-ready), the reputation
// each coach won, and the killed/injured tallies for the achievement counters.
//
// Practice and challenge fights are excluded: they must not feed progression (the
// client says as much for time challenges — "tu n'auras pas de fatigue ni de
// blessure ou de mort dans un défi du temps").
func (d *Deps) runPostFightMeta(f *Fight, winnerTeam uint8) (
	reports []endFightReport, standingByCoach map[uint]int32, killed, injured uint8) {

	standingByCoach = map[uint]int32{}
	if f == nil || !fightFeedsProgression(f) {
		return nil, standingByCoach, 0, 0
	}
	now := time.Now().Unix()
	rng := f.rngSource()
	if rng == nil {
		rng = rand.New(rand.NewSource(now))
	}

	for _, t := range f.Teams {
		if t == nil || t.Coach == nil || isSyntheticCoach(t.Coach.ID) {
			continue
		}
		won := t.ID == winnerTeam
		sess := t.Session

		// Set bonuses are per-COACH, so resolve them once per team rather than
		// per fighter.
		xpPct := sessionSetBonus(sess, aiXPPercent)
		xpFlat := sessionSetBonus(sess, aiXPFlat)
		moraleMod := sessionSetBonus(sess, aiMorale)
		tirednessMod := sessionSetBonus(sess, aiTiredness)
		repMod := sessionSetBonus(sess, aiReputation)
		woundMod := sessionSetBonus(sess, aiWound)
		deathMod := sessionSetBonus(sess, aiDeath)
		cancelWound := sessionSetBonus(sess, aiCancelWound)
		// The opponent-facing variants are applied to the OTHER side.
		xpPct += opposingSetBonus(f, t.ID, aiXPPercentEnemy)
		xpFlat += opposingSetBonus(f, t.ID, aiXPFlatEnemy)
		moraleMod += opposingSetBonus(f, t.ID, aiMoraleEnemy)
		tirednessMod += opposingSetBonus(f, t.ID, aiTirednessEnemy)
		woundMod += opposingSetBonus(f, t.ID, aiWoundEnemy)
		deathMod += opposingSetBonus(f, t.ID, aiDeathEnemy)

		var teamStanding int32
		for _, ff := range t.Fighters {
			if ff == nil || ff.Fighter == nil || ff.Fighter.ID == 0 {
				continue // summons and synthetic fighters have no persistent row
			}
			fr := ff.Fighter
			rep := &postFightReport{won: won}
			hours := hoursSince(fr.LastFightAt, now)

			// 1. XP: base, scaled by morale, then the rest bonus.
			rep.applyXP(baseXPPerFight, hours, int8(clampInt32(int32(fr.Morale), 0, maxMorale)))
			// 2. Gear/set XP modifiers stack onto the final figure only. A HEAD
			//    WOUND lives here too: it is a meta effect (AI 1, -10%/-20% XP),
			//    which is why the fighter's own conditions are summed in.
			xpPctTotal := xpPct + conditionMetaBonus(d.Conditions, fr, aiXPPercent)
			if xpPctTotal != 0 {
				rep.addXP(rep.xpBeforeGear * xpPctTotal / 100)
			}
			if xpFlat != 0 {
				rep.addXP(xpFlat)
			}
			// 3. Reputation set bonus is converted into XP at the client's rate
			//    (ga_0: cnv += x * nr_0.Po) AND earns the coach standing.
			if repMod != 0 {
				rep.addXP(repMod * standingXPScale)
				teamStanding += repMod
			}
			// 4. Morale drifts with the result, then set bonuses and the
			//    fighter's own conditions shift it (a serious "other" wound is
			//    AI 9, -1 morale).
			rep.applyMoraleDrift(int8(clampInt32(int32(fr.Morale), 0, maxMorale)), won, rng)
			moraleTotal := moraleMod + conditionMetaBonus(d.Conditions, fr, aiMorale)
			if moraleTotal != 0 {
				rep.addMorale(int8(moraleTotal))
			}
			// 5. Fatigue: recover elapsed time, add this fight's cost, then
			//    apply set bonuses (usually negative — rest gear).
			rep.applyTiredness(fr.Tiredness, hours, true, rng)
			if tirednessMod != 0 {
				rep.addTiredness(int8(tirednessMod))
				rep.finaliseTiredness()
			}
			// 6. Wounds and death. A fighter the FIGHT already killed skips the
			//    roll — it cannot be hurt twice — but is still reported dead.
			if f.Evolution && ff.HP <= 0 {
				rep.dead = true
				fr.State = domain.FighterStateDead
			} else if d.Conditions != nil {
				rep.rollInjuryChances(fr.TotalXP, rng)
				// Card/set modifiers shift the chances, guarded exactly as the
				// client guards them: `jT` only applies once the roll happened,
				// and `jU` refuses to raise the death chance of a fighter whose
				// lifetime XP is still under a tenth of the scale — i.e. rookies
				// are protected from death-chance stacking.
				if woundMod != 0 && rep.injuryRolled {
					rep.injuryChance += woundMod
				}
				if deathMod != 0 && rep.injuryRolled && fr.TotalXP*10 < deathXPScale {
					rep.deathChance += deathMod
				}
				if cancelWound > 0 && int32(rng.Intn(100)) < cancelWound {
					rep.injuryCancel = true
				}
				d.applyWoundAndDeathRolls(rep, fr, rng)
			}
			if rep.dead {
				killed++
			}
			if rep.woundID != 0 {
				injured++
			}

			// 7. Age the fighter's timed conditions by one fight. Done LAST so a
			//    wound taken this fight is not immediately aged.
			expireConditions(fr)

			rep.bank(fr, now)
			if d.Store != nil {
				_ = d.Store.Fighters.SaveProgress(fr)
				_ = d.Store.Fighters.SaveConditions(fr.ID, fr.Conditions)
			}
			reports = append(reports, endFightReport{WireID: ff.WireID, Blob: rep.encode()})
		}

		// Coach reputation for the fight itself, plus any set bonus.
		standing := standingForResult(won) + teamStanding
		if standing < 0 {
			standing = 0
		}
		standingByCoach[t.Coach.ID] = standing
		if standing > 0 {
			t.Coach.Mu.Lock()
			before := StandingToLevel(t.Coach.Standing)
			t.Coach.Standing += standing
			after := StandingToLevel(t.Coach.Standing)
			t.Coach.Mu.Unlock()
			if d.Store != nil {
				_ = d.Store.Coaches.Save(t.Coach)
			}
			if after > before {
				d.Log.Info("coach evolution level up", "coach", t.Coach.Name,
					"level", after, "standing", t.Coach.Standing)
			}
		}
	}
	if len(reports) > 0 {
		d.Log.Info("post-fight meta", "fight", f.ID, "reports", len(reports),
			"killed", killed, "injured", injured, "standing", standingByCoach)
	}
	return reports, standingByCoach, killed, injured
}

// fightFeedsProgression decides whether a finished fight runs the META pass.
//
//   - An EVOLUTION fight always does. It IS the progression mode — the client
//     ships a whole dialog for its debrief (`fightResultEvolutionDialog`) — and
//     it is flagged practice as well when started solo against the sparring
//     dummy, so testing `Practice` alone would wrongly skip exactly the mode that
//     needs this most.
//   - A plain PRACTICE ("Tester") fight never does: no ladder, no progression.
//   - A CHALLENGE (PvE) never does either. The client is explicit for the time
//     challenges — "tu n'auras pas de fatigue ni de blessure ou de mort dans un
//     défi du temps" — and challenge rewards are cards, granted elsewhere.
func fightFeedsProgression(f *Fight) bool {
	if f.Evolution {
		return true
	}
	return !f.Practice && f.ChallengeID == 0
}

// standingForResult is the reputation a fight itself is worth.
//
// HONEST LIMIT: like baseXPPerFight, the real value arrives pre-computed on the
// wire (`YP.cbG`) and is not derivable from the client. A win being worth more
// than a loss — and a loss still being worth something — is the shape every
// Ankama progression uses, but the magnitudes here are ours.
func standingForResult(won bool) int32 {
	if won {
		return 10
	}
	return 3
}

// sessionSetBonus is setBonusFor guarded for an absent session (a coach can
// finish a fight disconnected, and the synthetic sides have no session at all).
func sessionSetBonus(s *Session, action int32) int32 {
	if s == nil {
		return 0
	}
	return s.setBonusFor(action)
}

// opposingSetBonus sums an "…pour l'adversaire" bonus owned by the OTHER team and
// therefore applied to this one.
func opposingSetBonus(f *Fight, myTeam uint8, action int32) int32 {
	var total int32
	for _, t := range f.Teams {
		if t == nil || t.ID == myTeam {
			continue
		}
		total += sessionSetBonus(t.Session, action)
	}
	return total
}
