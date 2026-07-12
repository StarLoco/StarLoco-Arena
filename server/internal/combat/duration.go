package combat

import "github.com/dofusarena/go-server/internal/gamedata"

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase J: a
// minimal, table-turn-granularity duration-tracking primitive on Fighter,
// standing in for the reference engine's full generic TimeEvent duration
// queue (RunningEffectDurationTimeEvent/TurnBasedTimeInterval, see
// docs/05-combat-engine.md §5.4/§8.11 item 1's explicit note that this
// simpler approach is acceptable as long as observable behavior matches).
//
// Reference semantics ported here (confirmed via the decompiled
// TurnBasedTimeInterval.java/RunningEffect.java):
//   - EffectDef.Duration is [tableTurnDuration, turnDuration] (int32 pair,
//     parsed from spells.dat/cards.dat/events.dat -- see
//     docs/04-game-data-format.md §4.2 section 3). A value >= 63 in
//     EITHER slot means infinite (TurnBasedTimeInterval.isInfinite()) --
//     never expires on its own (only ends via explicit removal, e.g.
//     dispel or death).
//   - A finite-duration effect is tracked per-table-turn: each table-turn
//     boundary (Timeline wrapping back to the start of the turn order,
//     see timeline.go's StartNextTurn isNewTableTurn signal) decrements
//     the effect's remaining table-turn counter; at 0, it reverts/expires.
//     This project's Timeline has no per-fighter "turn" counter distinct
//     from table-turn (see timeline.go's doc comment on m_currentTurn not
//     being ported), so only the Duration[0] (table-turn) component is
//     actually actionable here -- Duration[1] (turn) is preserved on
//     ActiveEffect for completeness/future use but not currently
//     decremented separately.

// ActiveEffectKind discriminates what ActiveEffect.Revert should do when
// the effect expires.
type ActiveEffectKind int

const (
	// ActiveEffectPoisonTick: a recurring DoT that re-executes its damage
	// every table-turn until its duration runs out (or forever, if
	// infinite) -- no revert action needed at expiry (there's nothing to
	// "undo" for damage-over-time, it simply stops ticking).
	ActiveEffectPoisonTick ActiveEffectKind = iota
	// ActiveEffectCharacBuff: a timed CharacBuff/CharacDebuff whose signed
	// Max delta must be reverted (AddMax(-Delta)) at expiry, mirroring
	// CharacBuff/CharacDebuff's unapply() semantics (see effects.go's
	// EffectCharacBuff/EffectCharacDebuff doc comments for the
	// max-vs-current-value distinction this mirrors).
	ActiveEffectCharacBuff
	// ActiveEffectCharacValue: a timed CharacGain/CharacLoss whose signed
	// current-VALUE delta must be reverted (AddCharacteristic(-Delta)) at
	// expiry, mirroring CharacGain.unapply()/CharacLoss.unapply() (which
	// substract/add the value back, never touching Max). This is what makes
	// Feca's Immunity/Truce (+100 resist, 1 round), the timed resist armors,
	// and Weakness (-damage, 1 round) actually decay instead of lasting the
	// whole fight.
	ActiveEffectCharacValue
	// ActiveEffectSpellRebound: a timed SpellRebound buff (Feca's Spell
	// Rebound) whose reflect-chance delta must be removed from the fighter's
	// SpellReboundRate at expiry, so the reflect chance drops off after the
	// buff's duration instead of persisting for the whole fight.
	ActiveEffectSpellRebound
	// ActiveEffectProperty: a timed fighter PROPERTY (e.g. PETRIFIED from
	// Enutrof's Petrifaction, or a timed ROOT) that must be cleared from
	// Fighter.Properties at expiry, mirroring the reference Petrified.unapply
	// (substract(PETRIFIED)). Without this an "immobilize for 1 round"
	// property would last the whole fight.
	ActiveEffectProperty
)

// ActiveEffect is one duration-tracked effect instance on a Fighter.
type ActiveEffect struct {
	Kind ActiveEffectKind

	Caster   *Fighter // may be nil (e.g. an environmental source); used for poison re-ticks
	Charc    CharacteristicType
	Elem     Element
	Delta    int32         // signed Max delta already applied (ActiveEffectCharacBuff) or base damage value (ActiveEffectPoisonTick)
	Property PropertyFlags // the property to clear at expiry (ActiveEffectProperty)
	Params   []float32     // original effect params, re-rolled each poison tick (mirrors a real dice-based DoT re-rolling its damage every tick, not repeating the first roll)

	AffectedByLocalisation bool

	RemainingTableTurns int32 // decremented once per table-turn boundary; effect expires/reverts at 0
	Infinite            bool  // Duration[0]>=63 or Duration[1]>=63 -- never auto-expires
}

// trackActiveEffect records a new duration-tracked effect on target,
// mirroring RunningEffectManager.storeEffect()'s role (minus the full
// stacking/trigger-bus machinery, deferred per the roadmap). Only called
// for effect kinds whose EffectDef.Duration is actually meaningful
// (EffectCharacPoison/EffectCharacBuff/EffectCharacDebuff) -- see
// applyRunningEffect's call sites in effects.go.
func (f *Fighter) trackActiveEffect(ae ActiveEffect) {
	f.ActiveEffects = append(f.ActiveEffects, ae)
}

// trackDurationIfAny registers a timed-buff/debuff ActiveEffect on target
// if eff's Duration specifies a real (finite or infinite) table-turn
// count, so it auto-reverts at the documented expiry boundary (see
// tickActiveEffects). appliedDelta is the SIGNED Max delta that was
// already applied to the characteristic (positive for a buff, negative
// for a debuff) -- reverting simply re-applies its negation. A
// zero/absent Duration (the common case for most existing spell data,
// which doesn't specify one) is a no-op: the change is treated as lasting
// for the rest of the fight, matching this engine's pre-Phase-J behavior
// for that case exactly (nothing new to revert, since nothing was tracked
// before either).
func (f *Fight) trackDurationIfAny(target, caster *Fighter, charc CharacteristicType, elem Element, appliedDelta int32, eff gamedata.EffectDef) {
	tableTurns := durationTableTurns(eff.Duration)
	infinite := isInfiniteDuration(eff.Duration)
	if tableTurns <= 0 && !infinite {
		return
	}
	target.trackActiveEffect(ActiveEffect{
		Kind:                ActiveEffectCharacBuff,
		Caster:              caster,
		Charc:               charc,
		Elem:                elem,
		Delta:               appliedDelta,
		RemainingTableTurns: tableTurns,
		Infinite:            infinite,
	})
}

// trackCharacValueDurationIfAny is trackDurationIfAny for CharacGain/CharacLoss
// (current-VALUE changes, not Max): registers a timed revert of appliedDelta
// (the SIGNED value already added to the characteristic) so a finite-duration
// resist/damage buff or debuff decays at expiry via AddCharacteristic(-Delta).
// A zero/absent Duration is a no-op (lasts the rest of the fight), preserving
// prior behavior for the many CharacGain effects that carry no duration.
func (f *Fight) trackCharacValueDurationIfAny(target, caster *Fighter, charc CharacteristicType, appliedDelta int32, eff gamedata.EffectDef) {
	tableTurns := durationTableTurns(eff.Duration)
	infinite := isInfiniteDuration(eff.Duration)
	if tableTurns <= 0 && !infinite {
		return
	}
	if appliedDelta == 0 {
		return // nothing was actually applied (e.g. already clamped) -> nothing to revert
	}
	target.trackActiveEffect(ActiveEffect{
		Kind:                ActiveEffectCharacValue,
		Caster:              caster,
		Charc:               charc,
		Delta:               appliedDelta,
		RemainingTableTurns: tableTurns,
		Infinite:            infinite,
	})
}

// trackSpellReboundDurationIfAny registers a timed revert of appliedRate (the
// reflect-chance actually added to target.SpellReboundRate) so a Spell Rebound
// buff's reflect chance drops off at expiry. A zero/absent Duration is a no-op
// (lasts the fight); appliedRate==0 (already capped at 99) needs no revert.
func (f *Fight) trackSpellReboundDurationIfAny(target, caster *Fighter, appliedRate int32, eff gamedata.EffectDef) {
	tableTurns := durationTableTurns(eff.Duration)
	infinite := isInfiniteDuration(eff.Duration)
	if (tableTurns <= 0 && !infinite) || appliedRate == 0 {
		return
	}
	target.trackActiveEffect(ActiveEffect{
		Kind:                ActiveEffectSpellRebound,
		Caster:              caster,
		Delta:               appliedRate,
		RemainingTableTurns: tableTurns,
		Infinite:            infinite,
	})
}

// trackPropertyDurationIfAny registers a timed clear of a fighter property
// (e.g. PETRIFIED) so it is removed at duration expiry (Petrified.unapply).
// A zero/absent Duration is a no-op -- the property would then persist for
// the fight, but every real property-applying spell carries a finite duration.
func (f *Fight) trackPropertyDurationIfAny(target, caster *Fighter, prop PropertyFlags, eff gamedata.EffectDef) {
	tableTurns := durationTableTurns(eff.Duration)
	infinite := isInfiniteDuration(eff.Duration)
	if tableTurns <= 0 && !infinite {
		return
	}
	target.trackActiveEffect(ActiveEffect{
		Kind:                ActiveEffectProperty,
		Caster:              caster,
		Property:            prop,
		RemainingTableTurns: tableTurns,
		Infinite:            infinite,
	})
}

// isInfiniteDuration mirrors TurnBasedTimeInterval.isInfinite(): either
// slot of the [tableTurns, turns] pair being >= 63 means "never expires".
func isInfiniteDuration(d []int32) bool {
	for _, v := range d {
		if v >= 63 {
			return true
		}
	}
	return false
}

// durationTableTurns extracts the table-turn component (index 0) of an
// EffectDef.Duration pair, defaulting to 0 (no duration / instant only)
// if the slice is empty or malformed.
func durationTableTurns(d []int32) int32 {
	if len(d) == 0 {
		return 0
	}
	return d[0]
}

// tickActiveEffects runs once per table-turn boundary (called from
// Timeline's wrap-detection in turns.go's startNextTurn), for every
// fighter in the fight: re-executes recurring poison ticks, decrements
// every finite-duration effect's remaining counter, and reverts/removes
// any that just hit zero. triggeringActionID is used for the
// RUNNING_EFFECT_ACTION broadcasts this generates, mirroring how a normal
// spell-driven effect application broadcasts (see broadcastRunningEffect).
func (f *Fight) tickActiveEffects(triggeringActionID int32) {
	for _, fighter := range f.Timeline.Order() {
		if fighter.IsDead || len(fighter.ActiveEffects) == 0 {
			continue
		}
		f.tickFighterActiveEffects(fighter, triggeringActionID)
	}
}

func (f *Fight) tickFighterActiveEffects(fighter *Fighter, triggeringActionID int32) {
	// Reactive trigger-bus: fire table-turn-tick listeners and age out
	// expired reactive effects on the same table-turn boundary hook (see
	// triggerbus.go). Done first so a heal/effect-over-time reactive fires
	// before any poison DoT this same tick, matching the reference's
	// timeline ordering (buffs/regen resolve before residual damage).
	f.tickReactiveEffects(fighter, triggeringActionID)

	remaining := fighter.ActiveEffects[:0]
	for _, ae := range fighter.ActiveEffects {
		switch ae.Kind {
		case ActiveEffectPoisonTick:
			// Re-roll and re-apply the DoT's damage every table-turn,
			// mirroring a real RunningEffect re-executing on its next
			// scheduled TimeEvent tick (RunningEffect.executeOnTrigger).
			if !fighter.IsDead {
				// Poison bypasses resistance (decompiled CharacPoison does
				// HP.substract(value) directly) -- use the raw rolled value,
				// not ComputeHPLoss, so Immunity/resist can't reduce a DoT
				// (wiki: "Immunity does not help against Poisoning").
				base := rollBaseValue(ae.Params, f.rng)
				f.applyDamage(fighter, int(base), triggeringActionID)
			}

		case ActiveEffectCharacBuff, ActiveEffectCharacValue, ActiveEffectSpellRebound, ActiveEffectProperty:
			// Nothing to do on an ordinary tick -- the buff/debuff/property
			// is already applied; only expiry (below) reverts it.
		}

		if ae.Infinite {
			remaining = append(remaining, ae)
			continue
		}

		ae.RemainingTableTurns--
		if ae.RemainingTableTurns > 0 {
			remaining = append(remaining, ae)
			continue
		}

		// Expired this boundary: revert if it's a lasting stat change.
		switch ae.Kind {
		case ActiveEffectCharacBuff:
			if c := fighter.Characteristics[ae.Charc]; c != nil {
				c.AddMax(-ae.Delta)
				// Expiry revert: broadcast the reverse stat change so the
				// client updates the number. No spell container here (the
				// buff's own duration TimeEvent already removed its icon
				// client-side when it expired), only the characteristic
				// ActionID is set.
				f.broadcastRunningEffect(gamedata.EffectDef{ActionID: charcRunningEffectID(ae.Charc)}, ae.Caster, fighter, -ae.Delta, triggeringActionID)
			}
		case ActiveEffectCharacValue:
			// A CharacGain/CharacLoss reverts its current-VALUE delta
			// (CharacGain.unapply subtracts m_value; CharacLoss.unapply adds
			// it back) -- e.g. Immunity's +100 resist drops off after 1 round.
			if c := fighter.Characteristics[ae.Charc]; c != nil {
				fighter.AddCharacteristic(ae.Charc, -ae.Delta)
				f.broadcastRunningEffect(gamedata.EffectDef{ActionID: charcRunningEffectID(ae.Charc)}, ae.Caster, fighter, -ae.Delta, triggeringActionID)
			}
		case ActiveEffectSpellRebound:
			// The Spell Rebound buff expired: remove its reflect-chance
			// contribution so the fighter stops reflecting incoming spells.
			fighter.SpellReboundRate -= ae.Delta
			if fighter.SpellReboundRate < 0 {
				fighter.SpellReboundRate = 0
			}
		case ActiveEffectProperty:
			// A timed property (e.g. PETRIFIED) expired: clear it so the
			// fighter is no longer immobilized (Petrified.unapply).
			fighter.Properties &^= ae.Property
		}
		// ActiveEffectPoisonTick simply stops -- nothing to revert.
	}
	fighter.ActiveEffects = remaining
}

// clearActiveEffectsOnDeath drops every duration-tracked effect from a
// fighter who just died -- a dead fighter's poison shouldn't keep ticking
// (there's no HP left to lose) and any buff they had is moot. Called from
// killFighter (fightend.go).
func (f *Fighter) clearActiveEffectsOnDeath() {
	f.ActiveEffects = nil
}
