package game

import "github.com/StarLoco/arena-2.70/internal/gamedata"

// AI ally support.
//
// The attack path gates every candidate on aiSpellHarmsEnemy, which is what stops
// the AI ever aiming a heal at an opponent - but it also meant support spells
// were filtered out entirely and never cast at all. The AI never mis-targeted; it
// simply had no support behaviour. A player holding a heal with a dying teammate
// uses it.
//
// Scope note: HEALS only. A heal's value is measurable from the data - the effect
// rolls an amount, and the useful part is bounded by the ally's missing HP, so
// "was this worth an action" is answerable. A buff's value is not: nothing in the
// record says what a +damage% for 3 turns is worth against spending the same AP
// on a cast now, and inventing a number would be guessing rather than reading the
// data. Buffs stay unused until there is a real basis for valuing them.

// aiHealThresholdPct is how hurt an ally must be before the AI spends an action
// healing it. Server policy, not client-derived: nothing in the spell record says
// when a heal is "worth it".
//
// Chosen so the AI does not burn its turn topping up scratches (which is what a
// naive "heal anyone damaged" rule does and looks obviously robotic), while still
// reacting before a teammate dies.
const aiHealThresholdPct = 60

// aiSpellHealsAlly reports whether sp is a pure support heal: it restores HP and
// carries NO harmful effect. A spell that both damages and heals is an attack and
// belongs to the attack path.
func aiSpellHealsAlly(sp *gamedata.Spell) bool {
	if sp == nil {
		return false
	}
	heals := false
	for _, ef := range sp.Effects {
		if aiEffectHarms(ef) {
			return false
		}
		if ef.Kind() == gamedata.KindHeal {
			heals = true
		}
	}
	return heals
}

// aiEstimatedHeal is the HP a heal is expected to restore on `target`, capped by
// what the target is actually missing - a 200-point heal on someone 10 HP down is
// worth 10, and the AI should not value it as 200.
//
// Uses Roll(nil), which yields the deterministic MINIMUM for a dice effect and
// the flat value otherwise. Two reasons: drawing from the real RNG here would
// consume rolls the actual cast never makes (shifting every later roll in the
// fight), and it would let the AI see its own luck before committing - the same
// class of hidden information the knowledge model exists to prevent. Erring low
// also means the AI never talks itself into a heal on the strength of a roll it
// has not made.
func (f *Fight) aiEstimatedHeal(ff, target *FightFighter, sp *gamedata.Spell) int32 {
	if ff == nil || target == nil || sp == nil {
		return 0
	}
	missing := target.MaxHP - target.HP
	if missing <= 0 {
		return 0
	}
	var total int32
	for _, ef := range sp.Effects {
		if ef.Kind() != gamedata.KindHeal {
			continue
		}
		total += ef.Roll(nil)
	}
	if total <= 0 {
		return 0
	}
	total = total * (100 + clampPct(ff.Stats.healPct)) / 100
	if total > missing {
		total = missing // overheal is wasted, and the AI must not count it
	}
	return total
}

// aiSupportCast heals the ally that needs it most, if that is worth doing, and
// reports whether it cast.
//
// The ally guard is the mirror of aiWouldHitOwnTeam: a support spell may only be
// aimed at the caster's OWN side. Without it the same code that stops the AI
// healing enemies would be one sign flip away from doing exactly that.
func (f *Fight) aiSupportCast(ff *FightFighter) bool {
	if ff == nil || f.deps == nil || f.deps.Spells == nil || ff.AP <= 0 {
		return false
	}
	var (
		bestSpell  int32
		bestTarget *FightFighter
		bestValue  int32
	)
	for _, fr := range f.allFighters() {
		if fr.HP <= 0 || f.areOpponents(ff, fr) {
			continue // ALLIES ONLY - the mirror of the friendly-fire guard
		}
		if fr.MaxHP <= 0 || fr.HP*100/fr.MaxHP > aiHealThresholdPct {
			continue // not hurt enough to spend a turn on
		}
		for _, id := range f.aiRepertoire(ff) {
			sp := f.deps.Spells.Get(id)
			if !aiSpellHealsAlly(sp) {
				continue
			}
			if aiSpellAPCost(sp) > ff.AP {
				continue
			}
			if !ff.CastHistory.canCast(sp.LimitKeyID(), sp.Cooldown, sp.CastMaxPerTurn,
				sp.CastMaxPerTarget, f.tableTurn, fr.WireID, true) {
				continue
			}
			if !f.spellTargetValidFrom(ff, ff.Pos, sp, fr.Pos) {
				continue
			}
			v := f.aiEstimatedHeal(ff, fr, sp)
			if v > bestValue {
				bestSpell, bestTarget, bestValue = id, fr, v
			}
		}
	}
	if bestSpell == 0 || bestTarget == nil {
		return false
	}
	return f.castSpellByFighter(ff, bestSpell, bestTarget.Pos)
}
