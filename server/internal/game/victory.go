package game

// victory.go — ALTERNATIVE WIN CONDITIONS (np_1 type 14).
//
// A fight normally ends when one side has no living fighter. A ruleset can add
// a second way to win: the `mp_2` "victory condition" carried by a type-14
// parameter. Nine shipped challenges use one — challenge 14 plus the seven
// "Défi du temps" ("time challenge") demons and their finale.
//
// WHAT THE CLIENT PROVES, AND WHAT IT DOES NOT
//
// The client carries four concrete `mp_2` subclasses whose one-line bodies are
// unambiguous statements of meaning (see gamedata's Victory* constants). Those
// bodies are the evidence for the CONDITION.
//
// It does NOT tell us how a met condition is ARBITRATED. `wi_0.a(mv_1)` hands
// the decoded condition to the fight via `mv_1.b(mp_2)` — and `mv_1.b` is an
// EMPTY METHOD. The three-argument evaluator `a(mv_1, yg_0, yg_0)` has no call
// site anywhere in the client, and `rh()`/`ri()`/`rj()` (is_necessary,
// victory_points, affected_team) have no callers either. There is not even a
// display label: `content.55` stops at entry 1, so the client cannot render a
// type-4 condition at all.
//
// So retail arbitrated victory conditions entirely server-side and the client
// kept the structure as dead reference. The condition semantics are recovered;
// the arbitration is OURS, and is marked as such below.
//
// WHAT THE SHIPPED DATA SAYS
//
// All nine conditions are identical in shape: subtype 4 ("Atteindre un tour
// donné"), one parameter (20 or 30), is_necessary=true, victory_points=0,
// affected_team=0. Their holders are named "Défi du temps : Poison / Violence /
// Pont mortel / Kawotte / Lac / Quai des brumes / Altruisme" — literally *time*
// challenges. Condition, label and parameter all say the same thing: survive to
// the given turn and you win. None of them touches sudden death, so the default
// collapse at turn 15 still lands first and the last 5-15 rounds are fought on a
// shrinking arena. That is the mechanic, not an accident.

import "github.com/StarLoco/arena-2.70/internal/gamedata"

// applyFightStartEffects runs the np_1 type-12 effects — "Lance un effet sur
// tous les combattants à la création du combat" — once per fight.
//
// Modelled on applyRoundEvent, which solves the identical problem for round
// cards, and for the same reasons: each fighter is both CASTER and TARGET so a
// percentage effect scales off its own stats, and every effect is still gated by
// its own target conditions rather than by a blanket rule.
//
// The three shipped users (challenges 29/30/31, the "Défi Démon III" trio) each
// carry one effect: action 122 (dodge gain) +40, duration [63 0] = infinite,
// target mask 1024 = "breed is not 0", i.e. real fighters but not summons. So a
// summon conjured mid-fight does NOT inherit the bonus — which is why the mask
// is there, and why it is evaluated instead of assumed.
func (f *Fight) applyFightStartEffects() {
	if f == nil || f.startEffectsDone || len(f.Rules.StartEffects) == 0 {
		return
	}
	f.startEffectsDone = true
	for _, ef := range f.Rules.StartEffects {
		for _, ff := range f.livingFighters() {
			if !effectTargetAllowed(ff, ff, ef.Targets) {
				continue
			}
			f.applyPerTargetEffect(ff, ef, ff.Pos)
		}
	}
}

// victoryConditionMet reports whether one condition currently holds, and is the
// direct port of the matching `mp_2` subclass.
//
// ONLY subtype 4 is evaluated. The other three are decoded and carried, and
// their semantics are recorded on gamedata's Victory* constants, but no shipped
// record uses them — implementing them would be writing a mechanic no data
// exercises and no test could validate against real content. An unrecognised
// subtype is INERT (never met), never fatal, matching how this server treats
// unknown np_1 rule types.
func (f *Fight) victoryConditionMet(c gamedata.VictoryCondition) bool {
	switch c.Type {
	case gamedata.VictoryReachTurn:
		// ajm_0: `return fight.ZB().JI() > this.JI[0];`
		//
		// `JI()` returns `NC`, the client's own table-turn counter, incremented
		// in `cn_0.dm()` on each wrap of the timeline — the same counter this
		// server calls tableTurn. The test is strictly-greater, so a parameter
		// of 20 is met when round 21 begins, i.e. after 20 full rounds.
		if len(c.Params) < 1 {
			return false
		}
		return f.tableTurn > c.Params[0]
	default:
		return false
	}
}

// checkVictoryConditions ends the fight if any victory condition is met.
//
// MUST run on the fight actor. Called once per new table turn, next to the
// sudden-death check, because the only shipped condition is turn-based.
func (d *Deps) checkVictoryConditions(f *Fight) {
	if f == nil || f.Phase() == PhaseEnded || len(f.Rules.Victory) == 0 {
		return
	}
	for _, c := range f.Rules.Victory {
		if !f.victoryConditionMet(c) {
			continue
		}
		// ARBITRATION — ours, not the client's (see the file header).
		//
		// `affected_team` is the only field that could name a winner, it is 0 on
		// every shipped condition, and the client never reads it. We read it as
		// the team index it is named for. In a PvE challenge this server builds
		// the coach as team 0 and the demons as team 1, so the shipped value
		// makes the coach win by surviving — which is what "Défi du temps" and a
		// condition literally named "reach a given turn" describe.
		//
		// `victory_points` (all 0) and `is_necessary` (all true) would matter
		// only for scoring multiple partial conditions; with one all-or-nothing
		// condition per fight there is nothing for them to change, so they are
		// carried and deliberately unused rather than guessed at.
		winner := c.AffectedTeam
		if f.teamByID(winner) == nil {
			continue // a condition naming a team this fight does not have
		}
		f.decidedWinner = &winner
		if d.Log != nil {
			d.Log.Info("victory condition met",
				"fight", f.ID, "challenge", f.ChallengeID,
				"conditionType", c.Type, "params", c.Params,
				"round", f.tableTurn, "winner", winner)
		}
		d.checkFightEnd(f)
		return
	}
}
