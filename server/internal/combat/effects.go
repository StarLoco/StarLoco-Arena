package combat

import (
	"math"
	"strings"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file implements Phase G: the effect executor. Effects are resolved
// from gamedata.EffectDef (static definition, parsed from spells.dat/
// cards.dat/events.dat) via LookupRunningEffect (effects_registry.go) to a
// concrete EffectKind, then dispatched to one of the handlers below --
// mirroring the two-layer Effect/RunningEffect design in
// docs/05-combat-engine.md §5.4 and docs/opcodes/08-fight-combat-engine.md
// Part 3, simplified: rather than a full pooled RunningEffect object
// hierarchy with a generic TimeEvent duration-queue, this implements the
// synchronous "instant execute" path (damage/heal/characteristic-mod/
// movement/property-toggle/summon) for every effect kind, PLUS (Phase J,
// docs/08-java-parity-roadmap.md) a minimal table-turn-granularity
// duration primitive (duration.go's ActiveEffect/tickActiveEffects) for
// the two kinds whose EffectDef.Duration is actually meaningful today:
// EffectCharacPoison (recurring DoT re-ticks) and EffectCharacBuff/
// EffectCharacDebuff (timed stat changes that auto-revert at expiry).
// Every other effect kind remains a genuine one-shot instant action, since
// no other kind in this project's spell/card catalog carries a real
// non-zero Duration.
//
// The reactive trigger-bus (TriggersBefore/TriggersAfter) is now
// implemented in triggerbus.go: an effect declaring a before/after
// listen-set is DEFERRED here (stored on its carrier) rather than executed
// instantly, and fires later on the matching in-fight event (on-attacked,
// AP-used, table-turn tick). EndTriggers is parsed as null in this
// project's data, so there is nothing to wire.
//
// State-bundle expansion (EffectStateApply/STATE_APPLY) is now fully
// implemented in state.go: a State is a (baseId, level)-keyed bundle of
// sub-effects, and applying it executes every sub-effect through this same
// executor. The per-fight state registry is EMPTY by default -- no state
// data exists anywhere in this project's artifacts (no StateLoader, no
// states.dat, no server source) AND a scan of the real spell/card/event
// data confirmed STATE_APPLY (actionID 112) is used ZERO times -- so the
// mechanic is complete but inert until a state table is ever sourced
// (RegisterState), the same "built, awaiting optional data" posture as
// specialcells.go/effectarea.go. See state.go's header for the full
// evidence trail.
func (f *Fight) executeEffects(caster *Fighter, effects []gamedata.EffectDef, target Point3, triggeringActionID int32) {
	for _, eff := range effects {
		f.executeOneEffect(caster, eff, target, triggeringActionID)
	}
}

// executeEffectsForHit runs a spell/card's effects for a single hit,
// selecting the CRITICAL or NORMAL effect subset per the reference
// Ankama effect model: every Effect carries an isCritical flag
// (gamedata.EffectDef.IsCritical, parsed from the effectIsCritical byte in
// EffectContentDocumentLoader.readAndLoadEffect -> Effect flag bit 1,
// AbstractSpell.addEffect's checkFlags(1L) -> canCanBeCritical). A spell's
// effect list holds BOTH its normal effects and its (separately-authored)
// critical effects; on a critical hit the critical-flagged subset is
// executed instead of the normal one -- this is how a crit "does more"
// for a spell/card (a bigger damage roll / extra effect), mirroring close
// combat's own CloseCombatCriticalDamages swap in handleCloseCombat.
//
// Fallback: if crit is true but the container authored NO critical-flagged
// effects (canCanBeCritical()==false -- most utility spells), the normal
// effects run unchanged, so a lucky crit roll on a non-critical spell is
// simply a normal cast (never a no-op). Symmetrically, a container that
// ONLY authored critical effects (no normal ones) still runs them on a
// non-crit rather than doing nothing.
func (f *Fight) executeEffectsForHit(caster *Fighter, effects []gamedata.EffectDef, target Point3, triggeringActionID int32, crit bool) {
	selected := selectEffectsForCrit(effects, crit)
	for _, eff := range selected {
		f.executeOneEffect(caster, eff, target, triggeringActionID)
	}
}

// selectEffectsForCrit partitions a container's effects into the subset
// that should execute for the given hit type. On a crit it returns the
// isCritical effects (falling back to the non-critical ones if none are
// critical-flagged); on a normal hit it returns the non-critical effects
// (falling back to the critical ones if EVERY effect is critical-flagged).
// The fallbacks guarantee a cast never silently does nothing just because
// its authored effects don't match the rolled hit type.
func selectEffectsForCrit(effects []gamedata.EffectDef, crit bool) []gamedata.EffectDef {
	var match, other []gamedata.EffectDef
	for _, eff := range effects {
		if eff.IsCritical == crit {
			match = append(match, eff)
		} else {
			other = append(other, eff)
		}
	}
	if len(match) == 0 {
		return other
	}
	return match
}

func (f *Fight) executeOneEffect(caster *Fighter, eff gamedata.EffectDef, target Point3, triggeringActionID int32) {
	def, ok := LookupRunningEffect(eff.ActionID)
	if !ok {
		f.logger.Debug().Int32("action_id", eff.ActionID).Msg("combat: unresolved effect actionID, skipping")
		return
	}

	// EffectTeleport moves the CASTER directly to the target cell and has
	// no target-fighter concept at all -- mirrors the decompiled
	// Teleport.java's useCaster()=true/useTarget()=false/
	// useTargetCell()=true. It must bypass the normal target-fighter
	// resolution below entirely: resolving "targets" via AreaOfEffect at
	// the destination cell (as every other effect kind does) would only
	// ever find a fighter already standing there, which is backwards --
	// the whole point of Teleport is moving the caster onto a (typically
	// empty) cell.
	if def.Kind == EffectTeleport {
		f.applyTeleport(caster, target, eff, triggeringActionID)
		return
	}

	// EffectSetArea (actionID 66, Phase M -- docs/08-java-parity-roadmap.md)
	// similarly has no target-fighter concept: mirrors SetEffectArea.java's
	// useCaster()=false/useTarget()=false/useTargetCell()=true. It places
	// a persistent ground-effect area AT the target cell rather than
	// affecting whichever fighter happens to be standing there right now.
	if def.Kind == EffectSetArea {
		f.applySetEffectArea(caster, target, eff, triggeringActionID)
		return
	}

	// EffectThrow drops the caster's currently-carried fighter at the
	// target CELL (Throw.java: useCaster=true, useTargetCell=true,
	// useTarget=false). Like Teleport/SetArea it operates on the raw cell,
	// never on whichever fighter happens to stand there, so it must bypass
	// the target-fighter resolution below.
	if def.Kind == EffectThrow {
		f.applyThrow(caster, target, eff, triggeringActionID)
		return
	}

	// EffectRapprochement moves the CASTER toward the target cell
	// (Rapprochement.java: useCaster=true, useTargetCell=true,
	// useTarget=false), stopping one cell short, taking fall damage on
	// obstacle/off-map -- same cell-walk semantics as push/pull. Bypasses
	// target-fighter resolution for the same reason as Teleport.
	if def.Kind == EffectRapprochement {
		f.applyRapprochement(caster, target, eff, triggeringActionID)
		return
	}

	// Summon effects place a NEW creature at the target CELL (which is
	// deliberately EMPTY -- summon spells carry testFreeCell=true, e.g.
	// spell 69's canSummon double). They therefore must bypass the
	// target-fighter resolution below: resolving "targets" via AreaOfEffect
	// on the (empty) destination cell would find no fighter and the effect
	// would silently no-op -- the exact "animation plays but no invocation
	// appears" bug. Mirrors the reference Summon effects' useTargetCell
	// semantics.
	if def.Kind == EffectSummon || def.Kind == EffectSummonDouble || def.Kind == EffectSummonMirror {
		f.applySummon(caster, target, eff, triggeringActionID)
		return
	}

	// EffectAttractSight (TurnSightOnCell, actionID 68 -- Sram's Diversion)
	// turns each affected fighter's sight toward the CAST CELL, not toward
	// the caster (reference TurnSightOnCell.execute sets direction =
	// Vector3i(target.position -> m_targetCell); useTargetCell=true,
	// useCaster=false). It needs the raw cast cell, which the per-target
	// applyRunningEffect path below doesn't carry, so it's handled here.
	if def.Kind == EffectAttractSight {
		f.applyAttractSight(caster, target, eff, triggeringActionID)
		return
	}

	area := AreaOfEffect{Shape: AreaShape(eff.AreaShape), Size: eff.AreaSize}

	var targets []*Fighter
	if area.Shape == AreaEmpty {
		// AreaShape 32767 (AreaOfEffectEnum.EMPTY) is the "Target: All"
		// sentinel used by spells like Sadida's Shaking/Tremblement ("inflicts
		// a small damage to ALL characters, caster also affected" -- pbworks
		// wiki) and by every event card. The client's EmptyAOE.isPointInside
		// returns false, so cell-area resolution (ResolveTargets) finds NOBODY
		// -- which is exactly why Tremblement dealt no damage. Instead, an
		// EMPTY-area effect targets every LIVING fighter (the caster included),
		// then the per-target condition mask below still filters who it lands
		// on (Shaking/events carry the permissive [0] mask = truly everyone).
		// Summons ARE included here (unlike event cards, which skip summons in
		// applyEventEffects); a "hit all" spell like Shaking legitimately hits
		// summoned creatures too.
		targets = f.livingFighters()
	} else {
		targets = f.ResolveTargets(area, caster.Position, target)
		if len(targets) == 0 && area.Shape == AreaPoint {
			// Point-shaped effects with no living fighter at the exact target
			// cell (e.g. an offensive spell cast on an empty tile) still
			// "succeed" per docs/05-combat-engine.md §5.5 step 10
			// (ValidButNoEffectOnTarget) -- simply nothing happens. Logged at
			// Debug because it's the #1 reason a spell "animates but does
			// nothing": the client's target cell didn't line up with any
			// fighter's server-side X/Y.
			f.logger.Debug().
				Int32("action_id", eff.ActionID).
				Int32("caster_x", caster.Position.X).Int32("caster_y", caster.Position.Y).
				Int32("target_x", target.X).Int32("target_y", target.Y).
				Msg("combat: point-target effect resolved no fighter at the target cell (animates but no effect)")
			return
		}
	}

	for _, t := range targets {
		// Enforce the effect's own target-condition bitmask
		// (EffectDef.Targets), ported from the reference FightTargetValidator
		// -- so a self/ally-only effect never lands on an enemy in the area
		// (and vice-versa). Empty Targets = no restriction (see
		// effectTargetAllowed). Skipped fighters simply don't receive this
		// effect, matching the client's own per-target validity filtering.
		if !f.effectTargetAllowed(caster, t, eff.Targets) {
			continue
		}
		// SPELL_REBOUND (see EffectSpellRebound handler): if this target
		// carries a spell-rebound reflect chance and the effect being
		// resolved is a hostile one aimed at it by an opponent, ROLL its
		// SpellReboundRate% -- on success, redirect the effect onto its
		// caster instead. Mirrors SpellRebound.java's per-incoming-spell
		// DiceRoll.roll(100) <= executionRate then linkedRE.setTarget(
		// getCaster()). Persistent (rolls every incoming spell for the
		// buff's duration), NOT a one-shot bounce. Skipped for self-casts
		// and for the carrier's own beneficial effects.
		effTarget := t
		if t.SpellReboundRate > 0 && caster != nil && caster != t && f.AreOpponents(caster, t) && isHostileKind(def.Kind) {
			if int32(f.rng.Intn(100)+1) <= t.SpellReboundRate {
				effTarget = caster
				// No separate broadcast here: the redirected effect itself is
				// broadcast by applyRunningEffect below, now landing on the
				// caster instead of t (an extra id-0 packet would only be
				// dropped by the client anyway).
			}
		}
		// Reactive (triggered) effects declare a before/after listen-set:
		// they must be STORED on their carrier and fire later on a matching
		// event, NOT executed now (RunningEffect.mustBeTriggered ->
		// storeEffect). See triggerbus.go.
		if effectMustBeDeferred(eff) {
			f.deferReactiveEffect(caster, effTarget, def, eff)
			continue
		}
		f.applyRunningEffect(caster, effTarget, def, eff, triggeringActionID)
	}
}

// isHostileKind reports whether an effect kind is an offensive effect that
// SPELL_REBOUND is allowed to bounce back at its caster. Only damage-style
// and hostile resource/movement effects qualify -- a beneficial effect
// (heal/buff) is never redirected (it would make no sense to bounce a
// friendly effect, and the reference only ever attaches SpellRebound to
// deflect harmful spells).
func isHostileKind(k EffectKind) bool {
	switch k {
	case EffectHPLoss, EffectHPLeech, EffectHPDebuff,
		EffectAPUse, EffectMPUse,
		EffectCharacLoss, EffectCharacDebuff, EffectCharacLeech,
		EffectCharacPoison, EffectDeath,
		EffectRoot, EffectPetrified:
		return true
	default:
		return false
	}
}

func (f *Fight) applyRunningEffect(caster, target *Fighter, def runningEffectDef, eff gamedata.EffectDef, triggeringActionID int32) {
	switch def.Kind {
	case EffectHPLoss:
		if !f.AreOpponents(caster, target) && caster != target {
			f.logger.Debug().
				Int64("caster", caster.ID).Uint8("caster_team", caster.TeamID).
				Int64("target", target.ID).Uint8("target_team", target.TeamID).
				Msg("combat: HP-loss skipped -- target is a teammate (not caster)")
			return
		}
		base := rollBaseValue(eff.Params, f.rng)
		dmg := ComputeHPLoss(DamageParams{Caster: caster, Target: target, BaseValue: base, Element: def.Elem, AffectedByLocation: eff.AffectedByLocalisation}, f.rng)
		f.applyDamageFromEffect(caster, target, dmg, eff, triggeringActionID)

	case EffectHPGain:
		base := rollBaseValue(eff.Params, f.rng)
		heal := ComputeHeal(caster, base, f.rng)
		// Broadcast the ACTUAL HP restored (post-clamp), not the raw computed
		// heal: the client re-applies this value to its own HP bar via
		// FighterCharacteristic.add, so sending the uncapped amount makes the
		// client overshoot its max (the reported "healing goes above max HP"
		// bug). applied == 0 when already at full HP.
		applied := target.AddCharacteristicClamped(HP, int32(heal))
		f.broadcastRunningEffect(eff, caster, target, applied, triggeringActionID)

	case EffectHPLeech:
		// HP-leech (e.g. Sram's Life Theft) works on ANY target -- enemies,
		// ALLIES, and SUMMONS alike (wiki: "possible to steal from both
		// enemies as well as allies including summons such as the sram's
		// double"). The decompiled HPLeech.execute has NO opponent gate: it
		// simply heals the caster by min(value, target HP). A previous
		// opponent-only guard here wrongly blocked ally/summon leech, so drop
		// it. Self-cast is still meaningless (leech from self) but harmless.
		base := rollBaseValue(eff.Params, f.rng)
		dmg := ComputeHPLoss(DamageParams{Caster: caster, Target: target, BaseValue: base, Element: def.Elem, AffectedByLocation: eff.AffectedByLocalisation}, f.rng)
		// The caster gains as much HP as damage actually inflicted, capped by
		// the target's current HP (can't steal more life than the target has
		// -- HPLeech.execute's Math.min(m_value, target.HP)). AddCharacteristic
		// then clamps the caster's own gain to their max HP.
		if tgtHP := int(target.Characteristic(HP)); dmg > tgtHP {
			dmg = tgtHP
		}
		f.applyDamageFromEffect(caster, target, dmg, eff, triggeringActionID)
		caster.AddCharacteristic(HP, int32(dmg))

	case EffectHPDebuff:
		base := int32(rollBaseValue(eff.Params, f.rng))
		target.Characteristics[HP].Max += base
		if target.Characteristics[HP].Max < 0 {
			target.Characteristics[HP].Max = 0
		}
		if target.Characteristic(HP) > target.Characteristics[HP].Max {
			target.Characteristics[HP].Value = target.Characteristics[HP].Max
		}
		f.broadcastRunningEffect(eff, caster, target, base, triggeringActionID)

	// EffectCharacGain: a plain current-value add, clamped to the
	// characteristic's EXISTING max (mirrors CharacGain.execute(), which
	// calls only AbstractCharacteristic.add(), never touching max()).
	// Used for instant, non-persistent effects like a one-shot heal-style
	// "gain N AP this turn" that doesn't raise the ceiling.
	case EffectCharacGain:
		v := int32(rollBaseValue(eff.Params, f.rng))
		charac := target.Characteristics[def.Charc]
		before := charac.Value
		target.AddCharacteristic(def.Charc, v)
		actual := charac.Value - before
		f.broadcastRunningEffect(eff, caster, target, actual, triggeringActionID)
		// A CharacGain with a finite Duration (e.g. Feca's Immunity/Truce =
		// +100 resist for 1 round, or the 5-round resist armors) must REVERT
		// at expiry -- the decompiled CharacGain.unapply() subtracts the
		// value back. Track it so timed resist/stat buffs decay instead of
		// lasting the whole fight (they previously never expired). Reverting
		// the ACTUAL applied delta keeps the clamp-at-max cases correct.
		f.trackCharacValueDurationIfAny(target, caster, def.Charc, actual, eff)

	// EffectCharacBuff: raises BOTH max and current value (mirrors
	// CharacBuff.execute(): updateMaxValue(v) then add(v)) -- a real,
	// lasting stat buff (e.g. a max-HP or max-AP increase), not just a
	// one-turn resource top-up. If eff.Duration specifies a finite
	// table-turn count, the buff is tracked and automatically reverted
	// (AddMax(-v)) at expiry -- see duration.go (Phase J). A zero/absent
	// Duration means "lasts for the rest of the fight" (never auto-
	// reverted here), matching the pre-Phase-J behavior for effects that
	// genuinely have no duration data.
	case EffectCharacBuff:
		v := int32(rollBaseValue(eff.Params, f.rng))
		charac := target.Characteristics[def.Charc]
		charac.AddMax(v)
		target.AddCharacteristic(def.Charc, v)
		f.broadcastRunningEffect(eff, caster, target, v, triggeringActionID)
		f.trackDurationIfAny(target, caster, def.Charc, def.Elem, v, eff)

	// EffectCharacLoss: a plain current-value subtract (mirrors
	// CharacLoss.execute(): only substract(), never touches max()).
	case EffectCharacLoss:
		v := int32(rollBaseValue(eff.Params, f.rng))
		resisted := applyResistance(target, def.Charc, v)
		charac := target.Characteristics[def.Charc]
		before := charac.Value
		target.AddCharacteristic(def.Charc, -resisted)
		actual := before - charac.Value
		f.broadcastRunningEffect(eff, caster, target, -actual, triggeringActionID)
		// A CharacLoss with a finite Duration (e.g. Feca's Weakness =
		// -3 damage of all elements for 1 round) must REVERT at expiry
		// (CharacLoss.unapply() adds the value back). Track the negative
		// delta so the debuff decays instead of lasting the whole fight.
		f.trackCharacValueDurationIfAny(target, caster, def.Charc, -actual, eff)
		// Reactive trigger-bus: a characteristic LOSS emits its (charac,op)
		// trigger (e.g. AP/crit-rate loss -> 52) so a reactive effect armed
		// on the target for it fires (see emitCharacTrigger / triggerbus.go).
		f.emitCharacTrigger(target, EffectCharacLoss, def.Charc)

	// EffectCharacDebuff: lowers the MAX bound only (mirrors
	// CharacDebuff.execute(): updateMaxValue(-v), current value is
	// clamped down only as a side effect of Max shrinking below it, it is
	// NOT separately subtracted) -- a real stat debuff (e.g. lowering max
	// AP for the fight), not a one-turn resource drain. Duration-tracked
	// the same way as EffectCharacBuff (a debuff is just a buff with a
	// negative delta from this tracking primitive's point of view -- see
	// duration.go).
	case EffectCharacDebuff:
		v := int32(rollBaseValue(eff.Params, f.rng))
		resisted := applyResistance(target, def.Charc, v)
		charac := target.Characteristics[def.Charc]
		charac.AddMax(-resisted)
		f.broadcastRunningEffect(eff, caster, target, -resisted, triggeringActionID)
		f.trackDurationIfAny(target, caster, def.Charc, def.Elem, -resisted, eff)
		// Reactive trigger-bus: a DEBUFF of crit-rate/damage emits its
		// (charac,op) trigger (54/64) -- e.g. a spell-rebound that also arms
		// when the carrier's damage or crit is debuffed (see triggerbus.go).
		f.emitCharacTrigger(target, EffectCharacDebuff, def.Charc)

	// EffectCharacLeech: mirrors CharacLeech.execute() (extends
	// CharacDebuff): steals min(v, target's CURRENT value) from the
	// target's max (via the inherited CharacDebuff behavior) and adds
	// that same amount to the CASTER's current value (a plain add(), not
	// a max change on the caster's side).
	case EffectCharacLeech:
		v := int32(rollBaseValue(eff.Params, f.rng))
		targetCharac := target.Characteristics[def.Charc]
		leeched := v
		if targetCharac.Value < leeched {
			leeched = targetCharac.Value
		}
		if leeched < 0 {
			leeched = 0
		}
		resisted := applyResistance(target, def.Charc, leeched)
		targetCharac.AddMax(-resisted)
		caster.AddCharacteristic(def.Charc, resisted)
		f.broadcastRunningEffect(eff, caster, target, -resisted, triggeringActionID)
		// Reactive trigger-bus: a crit-rate LEECH emits trigger 56.
		f.emitCharacTrigger(target, EffectCharacLeech, def.Charc)

	case EffectCharacPoison:
		// First tick fires immediately (matching a spell's normal
		// "apply now" behavior); if eff.Duration specifies a finite or
		// infinite table-turn count, subsequent ticks are scheduled via
		// duration.go's ActiveEffect tracking (Phase J) -- re-executed
		// once per table-turn boundary by tickActiveEffects, each
		// re-rolling its own damage rather than repeating the first
		// tick's roll (mirroring a real dice-based DoT).
		// Poison damage BYPASSES resistance: the decompiled CharacPoison
		// does HP.substract(m_value) directly (it does NOT extend HPLoss and
		// never applies Res*/ResIn%), matching the wiki ("Immunity does not
		// help against Poisoning"). So use the raw rolled value, NOT
		// ComputeHPLoss -- routing it through resistance let a +100% ResIn
		// (Immunity) wrongly zero out poison.
		base := rollBaseValue(eff.Params, f.rng)
		dmg := int(base)
		f.applyDamage(target, dmg, triggeringActionID)
		if tableTurns := durationTableTurns(eff.Duration); tableTurns > 0 || isInfiniteDuration(eff.Duration) {
			target.trackActiveEffect(ActiveEffect{
				Kind:                   ActiveEffectPoisonTick,
				Caster:                 caster,
				Elem:                   def.Elem,
				Params:                 eff.Params,
				AffectedByLocalisation: eff.AffectedByLocalisation,
				RemainingTableTurns:    tableTurns,
				Infinite:               isInfiniteDuration(eff.Duration),
			})
		}

	case EffectAPUse:
		v := int32(rollBaseValue(eff.Params, f.rng))
		target.AddCharacteristic(AP, -v)
		f.broadcastRunningEffect(eff, caster, target, -v, triggeringActionID)

	case EffectMPUse:
		v := int32(rollBaseValue(eff.Params, f.rng))
		resisted := applyResistance(target, MP, v)
		target.AddCharacteristic(MP, -resisted)
		f.broadcastRunningEffect(eff, caster, target, -resisted, triggeringActionID)

	case EffectPush:
		f.applyPushPull(caster, target, eff, true, triggeringActionID)
	case EffectPull:
		f.applyPushPull(caster, target, eff, false, triggeringActionID)

	case EffectExchangePosition:
		// Instant position swap between caster and target, mirroring
		// ExchangePosition.java's execute(): both useCaster() and
		// useTarget() are true, useTargetCell() is false -- it operates
		// on two fighters, never a bare cell. (EffectTeleport is handled
		// separately in executeOneEffect, before target-fighter
		// resolution even happens -- see the comment there.)
		caster.Position, target.Position = target.Position, caster.Position
		f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)

	case EffectRoot:
		target.Properties |= PropertyRooted
		f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
		// Root is a TIMED immobilization (Cra's Paralyzing Arrow dur[1,0]):
		// track its duration so it clears at expiry instead of rooting the
		// target for the whole fight (was previously permanent).
		f.trackPropertyDurationIfAny(target, caster, PropertyRooted, eff)
	case EffectStabilize:
		target.Properties |= PropertyStabilized
		f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
		// Stabilize can carry a finite duration too; track it so a timed
		// stabilization reverts (a zero/infinite duration is a no-op / lasts
		// the fight, preserving prior behavior for permanent stabilizers).
		f.trackPropertyDurationIfAny(target, caster, PropertyStabilized, eff)
	case EffectPetrified:
		// Action 96 (Petrified) rolls a PERCENT chance = param[0] to apply,
		// mirroring the decompiled Petrified.computeValue:
		// m_mustBeExecuted = DiceRoll.roll(100) <= min(100, param[0]). So
		// Petrifaction (params [20,..]) immobilizes 20% of the time, while
		// Bribery (params [100,..]) always applies. Previously this ALWAYS
		// applied (ignored the params), so Petrifaction immobilized 100% of
		// the time. A missing/zero param defaults to always-apply.
		// chance = param[0] percent (min 100). With NO params at all, default
		// to always-apply (100%) -- the reference always has a param, but our
		// internal/test callers may omit it. An explicit param[0]=0 means
		// never (a 0% roll can't pass), which must be honored.
		chance := int32(100)
		if len(eff.Params) >= 1 {
			chance = int32(eff.Params[0])
			if chance > 100 {
				chance = 100
			}
		}
		if int32(f.rng.Intn(100)+1) <= chance {
			target.Properties |= PropertyPetrified
			f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
			// PETRIFIED is a timed immobilization: track its duration so it
			// clears at expiry (Petrified.unapply removes the property),
			// letting the fighter move again next round.
			f.trackPropertyDurationIfAny(target, caster, PropertyPetrified, eff)
		}
	case EffectSetInvisible:
		target.Properties |= PropertyInvisible
		f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
		// Invisibility is a TIMED property (Sram Invisibility dur[3,0], Sram
		// Invisibility of Others dur[2,0], Enutrof Rocks Den dur[3,0],
		// Eniripsa Wiping Word dur[2,0], Xelor... ): track its duration so it
		// clears at expiry instead of lasting the whole fight. Previously the
		// duration was ignored, so any invisibility spell made the target
		// permanently invisible.
		f.trackPropertyDurationIfAny(target, caster, PropertyInvisible, eff)
	case EffectSetVisible:
		target.Properties &^= PropertyInvisible
		f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)

	case EffectDeath:
		target.AddCharacteristic(HP, -target.Characteristic(HP)-1)
		f.applyDamage(target, 0, triggeringActionID) // ensures death check runs
		if !target.IsDead && target.ShouldBeDead() {
			f.killFighter(target, triggeringActionID)
		}

	case EffectAutomaticEndTurn:
		if f.Timeline.CurrentFighter() == target {
			f.askForFighterEndTurn(target.ID)
		}

	case EffectSummon:
		// Summon places a creature at the cast CELL, handled specially in
		// executeOneEffect (which passes the raw target cell, not a
		// fighter's position). Safety-net no-op if ever reached directly
		// (e.g. via an AoE fan-out), since target.Position here would be a
		// fighter's cell, not the intended empty summon cell.

	case EffectCardEquipped:
		// No server-side effect beyond acknowledging the action; the
		// client manages its own equipment-bar UI state.

	// --- Phase N effects (previously deferred, see effects_registry.go) ---

	case EffectCarry:
		f.applyCarry(caster, target, eff, triggeringActionID)

	case EffectThrow:
		// THROW operates on the caster's carried fighter, dropping it at
		// the effect's target cell (Throw.java: useCaster=true,
		// useTargetCell=true, useTarget=false). It is dispatched here via
		// the normal target-fighter path only for uniformity; the actual
		// drop cell is `eff`'s target cell, which executeOneEffect passed
		// through as `target.Position` is NOT correct -- so THROW is
		// instead handled specially in executeOneEffect. This case is a
		// safety net (no-op) if ever reached directly.

	case EffectDecurse:
		f.applyDecurse(caster, target, eff, triggeringActionID)

	case EffectAttractSight:
		// Handled specially in executeOneEffect (applyAttractSight), which
		// turns each affected fighter toward the CAST CELL -- info this
		// per-target path doesn't carry. Safety-net no-op if reached
		// directly (e.g. via a reactive/state fan-out).

	case EffectSummonDouble, EffectSummonMirror:
		// Like EffectSummon, handled specially in executeOneEffect against
		// the raw cast cell. Safety-net no-op if reached directly.

	case EffectSpellRebound:
		// Grant the target a persistent spell-rebound reflect CHANCE, mirroring
		// SpellRebound.java: param[0]% (capped 99, default 99) is added to the
		// fighter's SpellReboundRate, which then rolls PER incoming hostile
		// spell for the buff's whole duration (see executeOneEffect). Multiple
		// casts STACK the rate (stackWith caps at 99). The rate is reverted at
		// duration expiry via ActiveEffectSpellRebound.
		rate := spellReboundRate(eff.Params)
		before := target.SpellReboundRate
		target.SpellReboundRate += rate
		if target.SpellReboundRate > 99 {
			target.SpellReboundRate = 99
		}
		applied := target.SpellReboundRate - before
		f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
		// Track expiry so the reflect chance drops off after its duration
		// (Feca's Spell Rebound is a timed buff, not permanent). Reverting the
		// ACTUAL applied delta keeps stacked/capped rates correct.
		f.trackSpellReboundDurationIfAny(target, caster, applied, eff)

	case EffectStrikeBack:
		// Store the strike-back percentage on the target; applyDamageFrom
		// consults it to return that % of any incoming hit to the
		// attacker (StrikeBack.java returns param[0]% of the triggering
		// hit's value). param[0] is the percentage.
		pct := int32(rollBaseValue(eff.Params, f.rng))
		if pct > 0 {
			target.StrikeBackPercent += pct
			f.broadcastRunningEffect(eff, caster, target, pct, triggeringActionID)
		}

	case EffectChangeLook, EffectAdaptLook:
		// Cosmetic only: the reference swaps the target's rendered look
		// (ChangeLook/AdaptLook). There is no combat-state consequence
		// server-side, so this is a broadcast-only acknowledgement (the
		// client animates the look change), mirroring EffectCardEquipped's
		// no-op-with-broadcast pattern.
		f.broadcastRunningEffect(eff, caster, target, int32(rollBaseValue(eff.Params, f.rng)), triggeringActionID)

	case EffectStateApply:
		// STATE_APPLY expands a "state" (a bundle of sub-effects keyed by
		// [baseId, level]) via the per-fight state registry, then executes
		// each bundled sub-effect against the target and arms the state's
		// endTriggers -- a full port of ApplyState.computeValue/execute (see
		// state.go). The registry is empty by default (no state data exists
		// in this project's artifacts, and no real spell/card/event uses
		// STATE_APPLY -- empirically confirmed), so an unregistered state
		// degrades to a broadcast-only acknowledgement; a REGISTERED state
		// (RegisterState) expands correctly.
		f.applyStateBundle(caster, target, eff, triggeringActionID)

	case EffectRapprochement:
		// Handled specially in executeOneEffect (moves the CASTER toward
		// the target cell, like Teleport/SetArea it has no target-fighter
		// semantics). Safety-net no-op if reached directly.

	default:
		f.logger.Debug().Int("kind", int(def.Kind)).Msg("combat: effect kind not implemented, skipping")
	}
}

// applyTeleport moves caster directly onto the target cell, mirroring
// Teleport.java's execute(): useCaster()=true, useTarget()=false,
// useTargetCell()=true -- it is the CASTER who relocates, and there is no
// target-fighter concept at all (a self-cast spell whose target cell just
// happens to be empty is the common case, e.g. a blink/recall spell).
func (f *Fight) applyTeleport(caster *Fighter, targetCell Point3, eff gamedata.EffectDef, triggeringActionID int32) {
	caster.Position = targetCell
	// eff.ActionID (39 TELEPORT) is REQUIRED: the client instantiates the
	// Teleport RunningEffect from this id and its execute() calls
	// setPosition on the caster's actor -- which is what actually moves the
	// sprite. The TargetCell in the payload is the caster's new position
	// (broadcastRunningEffect reads target.Position, and target==caster
	// here). Previously this passed 0, so the client dropped the packet and
	// the fighter appeared not to teleport at all.
	f.broadcastRunningEffect(eff, caster, caster, 0, triggeringActionID)
}

// applyAttractSight implements actionID 68 (ATTRACT_SIGHT / TurnSightOnCell,
// Sram's Diversion): every fighter inside the effect's area (Diversion is a
// radius-2 circle) has its facing turned toward the CAST CELL, mirroring the
// reference TurnSightOnCell.execute() (direction = target->m_targetCell). The
// previous implementation turned the target toward the CASTER instead, which
// is why Diversion faced the wrong way. Each turned fighter's new facing is
// broadcast so the client re-orients the sprite. The effect's target-condition
// mask (EffectDef.Targets) is still honored so it only turns intended fighters.
func (f *Fight) applyAttractSight(caster *Fighter, castCell Point3, eff gamedata.EffectDef, triggeringActionID int32) {
	area := AreaOfEffect{Shape: AreaShape(eff.AreaShape), Size: eff.AreaSize}
	for _, t := range f.ResolveTargets(area, caster.Position, castCell) {
		if !f.effectTargetAllowed(caster, t, eff.Targets) {
			continue
		}
		// Turn toward the cast cell using the reference's 4-way isometric
		// quantization (toDirection4), NOT the 8-way directionFrom -- the
		// reference TurnSightOnCell only ever faces NE/SE/SW/NW, so a fighter
		// due-N/S/E/W of the cast cell still snaps to the nearest diagonal
		// facing (the "fixed pattern" the wiki describes). If a fighter is
		// standing exactly on the cast cell, keep its current facing (zero
		// vector).
		if t.Position.X != castCell.X || t.Position.Y != castCell.Y {
			t.Direction = direction4From(t.Position, castCell)
		}
		f.broadcastRunningEffect(eff, caster, t, 0, triggeringActionID)
	}
}

// applyResistance reduces the magnitude of an AP/MP-loss-style effect by
// the target's resist characteristic (ResAPLoss/ResMPLoss), expressed as a
// percentage reduction, floored at 0.
func applyResistance(target *Fighter, charc CharacteristicType, v int32) int32 {
	var resist int32
	switch charc {
	case AP:
		resist = target.Characteristic(ResAPLoss)
	case MP:
		resist = target.Characteristic(ResMPLoss)
	default:
		return v
	}
	reduced := v - (v * resist / 100)
	if reduced < 0 {
		return 0
	}
	return reduced
}

// Running-effect IDs the client applies to its own AP/MP counters (from
// the decompiled RunningEffectConstants: AP_USE=91 debits AP, MP_USE=92
// debits MP -- both via APUse/MPUse.execute() -> characteristic.substract).
const (
	runningEffectAPUse int32 = 91
	runningEffectMPUse int32 = 92
)

// runningEffectHPGain (RunningEffectConstants HP_GAIN=69) is used by the
// healing-heart special cell, which has no spell/card container and so
// builds a bare EffectDef carrying only this ActionID. The movement/summon
// helpers no longer need standalone id constants: they receive the real
// EffectDef (whose ActionID is already the correct id, plus the generic
// effect id + container), so they pass it straight through to
// broadcastRunningEffect.
const runningEffectHPGain int32 = 69

// charcRunningEffectID maps a characteristic to the RunningEffectConstants
// "gain/boost" action ID the client uses to animate a positive change to
// that characteristic (and, for our purposes, its reverse) -- used by the
// special-cell buff and timed-buff-revert broadcasts, which apply a raw
// stat delta outside the applyRunningEffect switch. The specific id mainly
// drives the client's floating-number/animation choice; the authoritative
// stat value itself travels in the payload's Value field. Falls back to
// HP_BOOST (11) for characteristics without a dedicated boost effect,
// which the client renders as a generic characteristic change.
func charcRunningEffectID(charc CharacteristicType) int32 {
	switch charc {
	case HP:
		return 11 // HP_BOOST
	case AP:
		return 13 // AP_BOOST
	case MP:
		return 17 // MP_BOOST
	case Init:
		return 76 // INIT_BOOST
	case Range:
		return 72 // RANGE_GAIN
	case Heal:
		return 78 // HEAL_GAIN
	case ResInPercent:
		return 80 // RES_GAIN_IN_PERCENT
	case DmgInPercent:
		return 82 // DMG_GAIN_IN_PERCENT
	case ResFirePercent:
		return 29 // RES_FIRE_GAIN_PERCENT
	case ResEarthPercent:
		return 31 // RES_EARTH_GAIN_PERCENT
	case ResWaterPercent:
		return 33 // RES_WATER_GAIN_PERCENT
	case ResWindPercent:
		return 35 // RES_WIND_GAIN_PERCENT
	default:
		return 11 // HP_BOOST as a generic characteristic-change fallback
	}
}

// broadcastCharacUse sends a RUNNING_EFFECT_ACTION(8120) that tells the
// client to debit a fighter's own AP or MP counter by `value`. This is the
// ONLY way the client learns of AP/MP spent by MOVEMENT and by the AP cost
// of casting -- the client is server-authoritative for AP/MP and never
// debits them locally (confirmed via the decompiled client: FIGHTER_MOVE
// carries only the path, no MP field; MPUse/APUse effects, opcode 8120,
// are what actually call characteristic.substract client-side). Without
// this, the player's PM/PA counter never decreases and they can move/cast
// without limit. runningEffectID is runningEffectMPUse (92) or
// runningEffectAPUse (91); target is the fighter whose counter to debit;
// value is the amount spent. See §8.20 of docs/08-java-parity-roadmap.md.
func (f *Fight) broadcastCharacUse(runningEffectID int32, target *Fighter, value int32) {
	if value <= 0 {
		return
	}
	f.broadcastAll(buildRunningEffectAction(
		f.nextActionID(), -1,
		true, false, runningEffectID,
		runningEffectPayload{
			CasterID:   0, // MPUse/APUse.useCaster() is false -> casterId 0
			TargetID:   target.ID,
			TargetCell: target.Position,
			Value:      value,
		},
		1, 0,
	))
	// Reactive trigger-bus: an AP spend emits the "AP used" trigger
	// (trigOnAPUse=55, APUse.setTriggersToExecute -> m_triggers.set(55)),
	// which a few resource-reactive spells listen for. See triggerbus.go.
	if runningEffectID == runningEffectAPUse {
		f.fireTrigger(target, trigOnAPUse, false)
	}
}

// broadcastRunningEffect sends RUNNING_EFFECT_ACTION for a non-damage
// effect application (damage goes through applyDamage's own broadcast).
// caster may be nil (e.g. an environmental/special-cell effect with no
// fighter "caster" -- the wire format's casterId is simply 0 in that
// case, matching AbstractFighter's own "0 if no caster" convention noted
// in docs/opcodes/08-fight-combat-engine.md Part 2).
//
// eff carries the effect's identity so the packet can transmit BOTH:
//   - runningEffectID (= eff.ActionID): the RunningEffectConstants action
//     ID the client uses via getObjectFromId() to instantiate the concrete
//     RunningEffect (must be non-zero or the client drops the packet -- the
//     old "teleport doesn't work" bug).
//   - GenericEffectID (= eff.ID) + the effect container (containerType /
//     containerID, resolved from eff.ParentType/eff.ParentID): required for
//     a BUFF to actually SHOW. The client reads NO duration off the wire
//     (the 34-byte blob has none -- see ArenaRunningEffect.unserialize); it
//     looks up the *local* generic effect by GenericEffectID
//     (EffectManager.getEffect) to learn hasDuration()/duration[], then, in
//     SpellEffectAction.onActionFinished(), stores the effect in the
//     target's RunningEffectManager and pushes a duration TimeEvent ONLY IF
//     hasDuration() is true. Separately, the buff ICON+countdown is drawn
//     by Fighter's "runningEffects" field getter, which ONLY counts effects
//     whose getEffectContainer() instanceof Spell (containerType 13). So
//     with GenericEffectID=0 and containerType=1 (the old hardcoded
//     values), a buff was neither stored nor iconified -- exactly the
//     reported "I can't see the buff or its duration". Sending eff.ID as
//     GenericEffectID and the SPELL container fixes both.
func (f *Fight) broadcastRunningEffect(eff gamedata.EffectDef, caster, target *Fighter, value int32, triggeringActionID int32) {
	var casterID int64
	if caster != nil {
		casterID = caster.ID
	}
	containerType, containerID := effectContainer(eff)
	f.broadcastAll(buildRunningEffectAction(
		f.nextActionID(), triggeringActionID,
		// mustBeExecutedNow=false: QUEUE the effect into the client's
		// pending ActionGroup instead of running it the instant the packet
		// arrives. The client sequences cast-animation -> effect-result
		// entirely locally via the spell's Lua cast script
		// (client/content/data.jar/scripts/<scriptId>.lua): the script
		// starts the caster's animation, then at the authored hit frame
		// (e.g. invoke(2000,...) for the Feca teleport, invoke(850,...) for
		// the Sram double, invoke(300,...) for damage) calls
		// ScriptedAction.executeFirstAction(3, <runningEffectId>), which
		// pulls THIS queued SpellEffectAction out of the group and runs it
		// at the right frame. Sending true (the old value) made
		// NetFightActionFrame case 8120 call spellEffectAction.run()
		// immediately, so the damage/teleport/summon landed while the cast
		// animation had barely started -- the reported "result already
		// there, animation just started" bug. eff.ActionID IS the
		// runningEffectId the scripts reference, so the group lookup
		// (getActionByTypeAndId(3, id)) resolves. The 8200
		// FIGHT_ACTION_SEQUENCE_EXECUTE flush (flushActionSequence) then
		// commits the group for playback.
		false, false, eff.ActionID,
		runningEffectPayload{
			GenericEffectID: eff.ID,
			CasterID:        casterID,
			TargetID:        target.ID,
			TargetCell:      target.Position,
			Value:           value,
		},
		containerType, containerID,
	))
}

// broadcastRunningEffectAtCell is broadcastRunningEffect for effects that act
// on a CELL rather than a target fighter (useTargetCell=true,
// useTarget=false): SET_EFFECT_AREA (traps/fog like Sram's Brume). The wire
// TargetID is 0 (no target fighter) and TargetCell is the explicit cast cell,
// which the client's SetEffectArea.execute() reads to place the area. Shares
// the same queued/scripted delivery semantics documented on
// broadcastRunningEffect.
func (f *Fight) broadcastRunningEffectAtCell(eff gamedata.EffectDef, caster *Fighter, cell Point3, value int32, triggeringActionID int32) {
	var casterID int64
	if caster != nil {
		casterID = caster.ID
	}
	containerType, containerID := effectContainer(eff)
	f.broadcastAll(buildRunningEffectAction(
		f.nextActionID(), triggeringActionID,
		false, false, eff.ActionID,
		runningEffectPayload{
			GenericEffectID: eff.ID,
			CasterID:        casterID,
			TargetID:        0,
			TargetCell:      cell,
			Value:           value,
		},
		containerType, containerID,
	))
}

// Effect container types on the wire, mirroring the client's
// NetFightActionFrame case-8120 switch and AbstractSpell.getContainerType()
// etc. (see docs/opcodes/08-fight-combat-engine.md "EffectContainer").
// The client ONLY resolves these four; any other value leaves its
// container null (which suppresses the spell-buff icon).
const (
	containerTypeSpell       int32 = 13
	containerTypeEffectArea  int32 = 3
	containerTypeEvent       int32 = 14
	containerTypeFighterCard int32 = 12
)

// effectContainer maps an EffectDef's ParentType/ParentID (as parsed from
// spells.dat/cards.dat/events.dat/staticEffects.dat) to the wire
// containerType + containerID the client needs to resolve the owning
// Spell/FighterCard/Event/EffectArea. A recognized parent lets the client
// attach the effect to its container -- which is what makes a spell buff
// show its icon+duration (Fighter."runningEffects" requires a Spell
// container). Unknown/empty parents fall back to (0, 0): the client leaves
// the container null, which is correct for engine-internal effects (special
// cells, timed-buff reverts) that legitimately have no spell/card owner.
//
// IMPORTANT: the raw ParentType parsed from the .dat files is a
// fixed-width field padded with trailing spaces (e.g. "SPELL   "), and the
// card type is the underscored "FIGHTER_CARD", not "FIGHTERCARD" -- so this
// matches by PREFIX using the exact same prefixes the gamedata store uses
// to group effects (store.go groupEffectsByParent: "SPELL",
// "FIGHTER_CARD", "AREA", and events with no prefix). Matching the padded
// value exactly would silently return (0,0) and re-break the buff icon.
func effectContainer(eff gamedata.EffectDef) (int32, int64) {
	switch {
	case strings.HasPrefix(eff.ParentType, "SPELL"):
		return containerTypeSpell, int64(eff.ParentID)
	case strings.HasPrefix(eff.ParentType, "FIGHTER_CARD"):
		return containerTypeFighterCard, int64(eff.ParentID)
	case strings.HasPrefix(eff.ParentType, "EVENT"):
		return containerTypeEvent, int64(eff.ParentID)
	case strings.HasPrefix(eff.ParentType, "AREA"):
		return containerTypeEffectArea, int64(eff.ParentID)
	default:
		return 0, 0
	}
}

// applyPushPull ports Push/Pull's cell-walk + fall-damage algorithm from the
// reference Push.computeValue/execute (docs/05-combat-engine.md §5.6.2). The
// target is walked up to `distance` cells along the push/pull direction,
// stopping early on the first cell that is either off-map (the "void" case) or
// blocked by another fighter (the "obstacle" case). The damage for the cells
// that COULDN'T be traversed is:
//
//	lifePointsToLose = cellLeft * (stoppedOnVoid ? 6 : 3)
//
// i.e. 6 HP per remaining cell if the target was pushed into the void/off the
// map, 3 HP per remaining cell if stopped by an obstacle -- matching the wiki
// ("Fearing a character into a wall will hurt him 6 damage") and the
// decompiled Push.computeValue (m_stoppedOnVoid ? 6 : 3). If the obstacle is
// ANOTHER FIGHTER, that fighter ALSO takes the same damage (EARTH element in
// the reference), reproducing the wiki's "Fearing someone into another
// character will cause both to lose 3 damage each". Finally, moving across
// cells triggers any ground effect-areas (traps/glyphs) the target enters or
// exits (reference Push.execute -> checkInAndOut), so a push CAN shove a
// fighter into a trap.
//
// Direction is caster->target for push (away from caster) or target->caster
// for pull (toward caster).
func (f *Fight) applyPushPull(caster, target *Fighter, eff gamedata.EffectDef, isPush bool, triggeringActionID int32) {
	if target.Properties.Has(PropertyStabilized) || target.Properties.Has(PropertyRooted) {
		return
	}
	distance := int(rollBaseValue(eff.Params, f.rng))
	if distance <= 0 {
		return
	}

	var dir Direction8
	if isPush {
		dir = directionFrom(caster.Position, target.Position)
	} else {
		dir = directionFrom(target.Position, caster.Position)
	}

	startPos := target.Position
	pos := target.Position
	cellsMoved := 0
	var obstacleFighter *Fighter
	stoppedOnVoid := false
	for i := 0; i < distance; i++ {
		next := pos.Step(dir)
		// Obstacle: another living fighter blocks the next cell.
		if blocker := f.fighterAt(next, target); blocker != nil {
			obstacleFighter = blocker
			break
		}
		// Void/wall: no walkable cell exists at the next position (map edge
		// or a non-walkable tile). This is the reference's cell==null case.
		if !f.IsWalkable(next) {
			stoppedOnVoid = true
			break
		}
		pos = next
		cellsMoved++
	}

	// Damage for the cells that couldn't be traversed: 6/cell into the void,
	// 3/cell into an obstacle (reference Push.computeValue).
	cellLeft := distance - cellsMoved
	perCell := 3
	if stoppedOnVoid {
		perCell = 6
	}
	fallDamage := cellLeft * perCell

	target.Position = pos

	// Trigger ground effect-areas (traps/glyphs) entered or exited by the
	// push, mirroring Push.execute -> EffectAreaManager.checkInAndOut. Done
	// BEFORE the fall damage so a trap that kills doesn't double-apply, and
	// so "push into a trap" works (wiki). No-op if no areas are placed.
	if cellsMoved > 0 {
		f.checkInAndOut(startPos, pos, target)
	}

	if fallDamage > 0 && !target.IsDead {
		f.applyDamage(target, fallDamage, triggeringActionID)
		// The obstacle fighter shares the impact (both lose the same amount).
		if obstacleFighter != nil && !obstacleFighter.IsDead {
			f.applyDamage(obstacleFighter, fallDamage, triggeringActionID)
		}
	}

	// Broadcast the ACTUAL number of cells moved as the effect Value.
	// CRITICAL: the client unserializes the Push/Pull RunningEffect with
	// disableValueComputation() (NetFightActionFrame case 8120), so it does
	// NOT recompute the push distance from the spell params -- it uses the
	// wire Value as m_value (the cell count its computeMovement loop walks).
	// Broadcasting 0 (the old value) made the client push the target 0 cells
	// -> the reported "Peur/push does nothing" (the server moved the fighter
	// but the client never animated it). eff.ActionID is already PUSH(37) or
	// PULL(38); eff also carries the generic effect id + spell container.
	f.broadcastRunningEffect(eff, caster, target, int32(cellsMoved), triggeringActionID)
}

// directionFrom returns the 8-way direction pointing from a to b. It is the
// exact inverse of Point3.Step, using the client's Direction8 grid vectors
// (SE=(+1,0), SW=(0,+1), NW=(-1,0), NE=(0,-1) single-axis; E=(+1,-1),
// S=(+1,+1), W=(-1,+1), N=(-1,-1) two-axis). The sign of each axis picks the
// direction; pure single-axis deltas resolve to the diagonal-named
// directions (the ones with sprite art), matching the client's own
// Vector3i.getDirection8FromVector quadrant mapping.
func directionFrom(a, b Point3) Direction8 {
	dx := b.X - a.X
	dy := b.Y - a.Y
	switch {
	case dx > 0 && dy == 0:
		return DirSouthEast
	case dx == 0 && dy > 0:
		return DirSouthWest
	case dx < 0 && dy == 0:
		return DirNorthWest
	case dx == 0 && dy < 0:
		return DirNorthEast
	case dx > 0 && dy < 0:
		return DirEast
	case dx > 0 && dy > 0:
		return DirSouth
	case dx < 0 && dy > 0:
		return DirWest
	default: // dx < 0 && dy < 0
		return DirNorth
	}
}

// direction4From returns the ISO-cardinal (4-way) facing from a to b,
// mirroring the reference Vector3i.getDirection4FromVector exactly: it only
// ever yields one of the four diagonal-named directions that have sprite art
// (NE/SE/SW/NW), quantizing the a->b vector's angle into 90-degree isometric
// quadrants. Used by AttractSight/Diversion, whose reference (TurnSightOnCell)
// turns each fighter toward the cast cell via toDirection4() -- NOT the 8-way
// directionFrom, which would produce the axis-aligned E/S/W/N facings the
// reference's 4-way quantization never emits (the reason Diversion faced
// wrong). Zero vector keeps the caller's current facing (handled by caller).
func direction4From(a, b Point3) Direction8 {
	vx := float64(b.X - a.X)
	vy := float64(b.Y - a.Y)
	// atan(|vy|/|vx|); guard vx==0 (atan of +Inf = pi/2).
	var ang float64
	if vx == 0 {
		ang = math.Pi / 2
	} else {
		ang = math.Atan(math.Abs(vy) / math.Abs(vx))
	}
	if vx < 0 {
		ang = math.Pi - ang
	}
	if vy > 0 {
		ang = -ang
	}
	const q = math.Pi / 4 // 0.7853981633974483
	switch {
	case ang <= 3*q && ang >= q:
		return DirNorthEast
	case ang <= q && ang >= -q:
		return DirSouthEast
	case ang <= -q && ang >= -3*q:
		return DirSouthWest
	default:
		return DirNorthWest
	}
}

// applySummon spawns a new Fighter as a child of caster, inserted into
// turn order right after the summoner (docs/05-combat-engine.md §5.4.1
// item 7).
//
// Stat source depends on the summon KIND (all three route here):
//   - EffectSummon (Summon.java): summons a distinct CREATURE. The
//     reference's Summon.computeValue() reads params[0] into m_value and
//     execute() passes it to summonCreature(newId, cell, m_value) as the
//     SummoningTemplate reference id. We resolve that template from
//     gamedata.Summonings and override the summon's HP/AP/MP from it
//     (Gfx/SpellID drive its look/attack, not modeled as combat stats
//     here). If the template is missing or params are absent, we fall back
//     to the caster's breed stats rather than failing the summon.
//   - EffectSummonDouble/EffectSummonMirror (SummonDouble/SummonMirror.java):
//     summon a COPY of the caster, so the caster's own breed stats (already
//     applied by NewFighterFromBreed) are correct -- no template lookup.
func (f *Fight) applySummon(caster *Fighter, pos Point3, eff gamedata.EffectDef, triggeringActionID int32) {
	// A summon may only land on a free, walkable cell (summon spells carry
	// testFreeCell=true). If the requested cell is occupied, silently no-op
	// rather than stacking two mobiles on one tile.
	if f.IsOccupied(pos, nil) {
		return
	}
	id := f.nextSummonID()
	summon := NewFighterFromBreed(id, caster.TeamID, caster.Breed, caster.Name+"'s summon", caster.Sex, caster.Skin)
	summon.Position = pos
	summon.Father = caster
	summon.Direction = caster.Direction // face the same way as its summoner

	// A plain Summon (action 67) AND a Summon-MIRROR (action 97, Xelor's
	// Dial) instantiate a real creature whose stats + spell come from a
	// SummoningTemplate keyed by params[0]: the decompiled SummonMirror.execute
	// calls summonMirror(id, cell, m_value) with m_value=params[0] as the
	// SummoningDefinition id (spell 157 params=[7] -> the Dial template
	// HP50/AP4/MP1 + Mirwar). Only Summon-DOUBLE (action 75, Sram's Double)
	// is a true caster clone: summonDouble(id, cell) takes NO definition, so
	// it keeps the caster's breed stats (HP70 etc.). Previously Mirror was
	// wrongly treated as a clone, so the Dial spawned with the Xelor's stats
	// and never auto-cast Mirwar (no SummonSpellID).
	def, ok := LookupRunningEffect(eff.ActionID)
	if ok && (def.Kind == EffectSummon || def.Kind == EffectSummonMirror) {
		f.applySummonTemplateStats(summon, eff)
	}

	f.registerFighter(summon, caster.CoachID)
	f.Timeline.InsertAfter(caster, summon)

	// The client instantiates the summon ENTIRELY from the SUMMON
	// RUNNING_EFFECT_ACTION itself -- Summon.execute() (Summon.java) calls
	// caster.summonCreature(newTargetId, cell, value) -> summonFighter(),
	// which sets the id/position, adds it to the fight, and calls
	// NetFightActorsFrame.addMobile(). So we must NOT also send an
	// ACTOR_APPEAR (4102) for it: that would addMobile() a second mobile
	// with the SAME id, colliding in the client's MobileManager/fight and
	// wedging the action sequence (the reported "summon freezes the client"
	// bug). The running effect below IS the spawn message.
	//
	// Two wire fields the client reads out of that blob (see
	// ArenaRunningEffect.unserialize + Summon.unserializeTarget/computeValue):
	//   - the TARGET id = the new summon's id (Summon overrides
	//     unserializeTarget to store the target-long as m_newTargetId).
	//     broadcastRunningEffect already sends target.ID = summon.ID.
	//   - the VALUE = the SummoningDefinition id the client resolves via
	//     SummoningManager to build the SummonedFighter. Summon.computeValue
	//     sets m_value = params[0], so we must send params[0] here (NOT 0 --
	//     value 0 makes summonCreature log "SummoningDefinition id=0 est
	//     inconnue" and return null, so the doll never appears). SummonMirror
	//     likewise uses m_value as its definition id; SummonDouble ignores it.
	var summonValue int32
	if len(eff.Params) > 0 {
		summonValue = int32(eff.Params[0])
	}
	f.broadcastRunningEffect(eff, caster, summon, summonValue, triggeringActionID)
}

// applySummonTemplateStats overrides a fresh summon's HP/AP/MP from its
// SummoningTemplate, resolved via the effect's params[0] (the template
// reference id Summon.execute() passes to summonCreature). No-op if there's
// no gamedata store attached, no params, or no matching template -- in
// which case the summon keeps the caster-breed stats NewFighterFromBreed
// already gave it. Each stat is set to its template value as both Max and
// current (a summon spawns at full stats), and only overrides a stat when
// the template specifies a positive value (a 0 in summoning.dat means
// "unspecified", so the breed fallback is kept for that stat).
func (f *Fight) applySummonTemplateStats(summon *Fighter, eff gamedata.EffectDef) {
	if f.data == nil || len(eff.Params) == 0 {
		return
	}
	templateID := int32(eff.Params[0])
	tmpl, ok := f.data.Summonings.Get(templateID)
	if !ok {
		f.logger.Debug().Int32("template_id", templateID).Msg("combat: summon template not found, keeping breed stats")
		return
	}
	setSummonStat := func(t CharacteristicType, v int32) {
		if v <= 0 {
			return
		}
		summon.Characteristics[t].Max = v
		summon.Characteristics[t].ToMax()
	}
	setSummonStat(HP, tmpl.HP)
	setSummonStat(AP, tmpl.AP)
	setSummonStat(MP, tmpl.MP)

	// The summon's single castable spell (mirrors the client's
	// SummonedFighter adding getSpellId() to its spell inventory). Drives
	// the summon AI's behavior + what it casts (see summon_ai.go). 0 =
	// no spell (pure blocker, e.g. Blocker/Tree templates).
	summon.SummonSpellID = tmpl.SpellID
	if tmpl.SpellID != 0 {
		summon.SpellIDs = []int32{tmpl.SpellID}
	}
}

// applyCarry ports AbstractFighter.carry() (Carry.java): the caster picks
// up the target, stacking it on the caster's cell (target's Z raised by
// the caster's height). Fails silently (no-op) if either fighter is
// already in a carry relationship or target==caster, matching carry()'s
// guard clauses. On success the carry links are set on both fighters
// (CarriedFighter/CarriedByFighter), which Height() and the
// cantCastWhenCarrying/cantCastWhenCarried criteria (criteria.go) already
// consult.
func (f *Fight) applyCarry(caster, target *Fighter, eff gamedata.EffectDef, triggeringActionID int32) {
	if caster == nil || target == nil || caster == target {
		return
	}
	if caster.CarriedFighter != nil || caster.CarriedByFighter != nil {
		return // caster is already carrying or being carried
	}
	if target.CarriedFighter != nil || target.CarriedByFighter != nil {
		return // target is already carrying or being carried
	}
	// Stack the target onto the caster's cell (mirrors carry()'s
	// setPosition(casterX, casterY, casterZ + standardHeight)).
	target.Position = Point3{X: caster.Position.X, Y: caster.Position.Y, Z: caster.Position.Z + int16(baseFighterHeight)}
	caster.CarriedFighter = target
	target.CarriedByFighter = caster
	f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
}

// dismountIfCarried drops `fighter` off its carrier's shoulder onto the
// ground at its current X/Y, breaking the carry link, and returns true iff a
// dismount happened. Mirrors the client's MoveAction.onRun, which calls
// carrier.uncarry(here) the moment a CARRIED fighter starts moving: the
// carried fighter's Position carries the carrier's raised Z (carrierZ +
// height), which would make its own movement path fail altitude validation
// (it appears to start "in the air"). Resetting Z to the carrier's ground Z
// lets the carried fighter walk off normally. No-op (returns false) if the
// fighter isn't being carried.
func (f *Fight) dismountIfCarried(fighter *Fighter) bool {
	carrier := fighter.CarriedByFighter
	if carrier == nil {
		return false
	}
	// Land on the carrier's ground cell (same X/Y, carrier's Z). The client
	// dismounts at the move's first cell (here == the shared cell), then
	// walks the rest of the path from there.
	fighter.Position = Point3{X: carrier.Position.X, Y: carrier.Position.Y, Z: carrier.Position.Z}
	fighter.CarriedByFighter = nil
	carrier.CarriedFighter = nil
	return true
}

// applyThrow ports AbstractFighter.uncarry(pos) (Throw.java): drops the
// caster's currently-carried fighter at the effect's target cell. No-op if
// the caster isn't carrying anyone. Fall-type damage is NOT applied by the
// reference Throw (it simply places the carried fighter at the chosen
// cell), so none is applied here either.
func (f *Fight) applyThrow(caster *Fighter, targetCell Point3, eff gamedata.EffectDef, triggeringActionID int32) {
	if caster == nil || caster.CarriedFighter == nil {
		return
	}
	carried := caster.CarriedFighter
	carried.Position = targetCell
	carried.CarriedByFighter = nil
	caster.CarriedFighter = nil
	f.broadcastRunningEffect(eff, caster, carried, 0, triggeringActionID)
}

// applyRapprochement ports Rapprochement.java: the caster walks toward the
// target cell, stopping one cell short of it, taking fall-type damage for
// any cells it could NOT traverse (3 HP/cell if stopped by an obstacle,
// 6 HP/cell if the walk would leave the map) -- the same fall-damage
// schedule as push/pull (Elements.EARTH). Movement is blocked entirely if
// the caster is stabilized/rooted, mirroring the reference's obstacle
// checks preventing any displacement.
func (f *Fight) applyRapprochement(caster *Fighter, targetCell Point3, eff gamedata.EffectDef, triggeringActionID int32) {
	if caster == nil {
		return
	}
	if caster.Properties.Has(PropertyStabilized) || caster.Properties.Has(PropertyRooted) {
		return
	}
	// Chebyshev distance minus one: how many cells to advance toward the
	// target, stopping one short (Rapprochement.computeMovement:
	// max(|dx|,|dy|) - 1).
	dx := targetCell.X - caster.Position.X
	dy := targetCell.Y - caster.Position.Y
	steps := int(maxAbs(dx, dy)) - 1
	if steps <= 0 {
		return
	}
	dir := directionFrom(caster.Position, targetCell)
	pos := caster.Position
	fallDamage := 0
	moved := 0
	for i := 0; i < steps; i++ {
		next := pos.Step(dir)
		if next.X == targetCell.X && next.Y == targetCell.Y {
			break // never land on the target's own cell
		}
		if f.IsOccupied(next, caster) {
			fallDamage = 3 * (steps - i)
			break
		}
		pos = next
		moved++
	}
	caster.Position = pos
	f.broadcastRunningEffect(eff, caster, caster, 0, triggeringActionID)
	if fallDamage > 0 {
		f.applyDamageFrom(nil, caster, fallDamage, triggeringActionID)
	}
}

// applyDecurse ports Decurse.java: removes every duration-tracked
// buff/debuff currently on the target and reverts its stat change,
// mirroring the reference iterating the target's RunningEffectManager and
// asking each spell/card-container effect to unapply. This engine's
// duration primitive (duration.go) only tracks CharacBuff/CharacDebuff and
// poison ticks; decurse reverts the CharacBuff/CharacDebuff Max deltas
// (the "curses"/buffs) and drops poison ticks, which is the observable
// behavior of a dispel here. Also clears the reactive
// StrikeBackPercent/SpellReboundPending stand-ins, which represent active
// effect-driven states.
func (f *Fight) applyDecurse(caster, target *Fighter, eff gamedata.EffectDef, triggeringActionID int32) {
	for _, ae := range target.ActiveEffects {
		switch ae.Kind {
		case ActiveEffectCharacBuff:
			if c := target.Characteristics[ae.Charc]; c != nil {
				c.AddMax(-ae.Delta) // undo the applied signed Max delta
			}
		case ActiveEffectCharacValue:
			// Undo a timed CharacGain/CharacLoss's current-value delta.
			target.AddCharacteristic(ae.Charc, -ae.Delta)
		}
	}
	target.ActiveEffects = nil
	target.StrikeBackPercent = 0
	target.SpellReboundRate = 0
	f.broadcastRunningEffect(eff, caster, target, 0, triggeringActionID)
}

// spellReboundRate resolves SpellRebound.java's executionRate from the
// effect params: param[0] is the reflect percent, capped at 99; with no
// param the reference defaults the rate to 99 (see SpellRebound.newInstance).
func spellReboundRate(params []float32) int32 {
	rate := int32(99)
	if len(params) == 1 {
		rate = int32(params[0])
	}
	if rate > 99 {
		rate = 99
	}
	if rate < 0 {
		rate = 0
	}
	return rate
}

// maxAbs returns the larger of |a| and |b|.
func maxAbs(a, b int32) int32 {
	if a < 0 {
		a = -a
	}
	if b < 0 {
		b = -b
	}
	if a > b {
		return a
	}
	return b
}

// toInts32 converts an effect's float params to []int32 for structured
// logging (used by the data-limited STATE_APPLY handler).
func toInts32(params []float32) []int32 {
	out := make([]int32, len(params))
	for i, p := range params {
		out[i] = int32(p)
	}
	return out
}
