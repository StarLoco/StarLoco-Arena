package combat

import "github.com/dofusarena/go-server/internal/gamedata"

// This file implements Phase E's close combat action and Phase F's
// spell/card casting, sharing a common validation shape per
// docs/05-combat-engine.md §5.5's Castable pipeline description. Phase L
// (docs/08-java-parity-roadmap.md) completed the validation pipeline with
// cast-frequency history (spell_cast_history.go), line-of-sight
// (line_of_sight.go), and custom criteria (criteria.go).

// noTriggeringAction is the fight-action-header triggeringActionUniqueId
// sentinel meaning "this action is NOT caused by (triggered by) another
// action" (docs/opcodes/08-fight-combat-engine.md: -1 if none). A cast's
// OWN primary effects use this: the client's cast script pulls them out of
// the pending action group directly, so they must not point back at the
// cast action (which would make ActionGroup.runAction re-run the cast and
// loop its animation). Only genuine reactive/triggered sub-actions carry a
// real parent uniqueId.
const noTriggeringAction int32 = -1

// manhattanDistance returns |dx|+|dy| between two cells, ignoring Z.
func manhattanDistance(a, b Point3) int32 {
	return abs32(a.X-b.X) + abs32(a.Y-b.Y)
}

// handleCloseCombat processes CloseCombatRequestMessage(8111): the
// simplest damage-dealing action -- fixed AP cost from breed data, range
// must be exactly 1 (adjacent), per docs/opcodes/08-fight-combat-engine.md
// Part 2 and docs/08-java-parity-roadmap.md Phase E.
func (f *Fight) handleCloseCombat(c cmdCloseCombat) {
	if f.CurrentPhase() != PhaseAction {
		return
	}
	attacker, ok := f.resolveOwnedFighter(c.RequesterCoachID, c.FighterID)
	if !ok || attacker.IsDead {
		return
	}
	if f.Timeline.CurrentFighter() != attacker {
		return
	}

	stats, ok := attacker.BreedStats()
	if !ok {
		return
	}
	if attacker.Characteristic(AP) < stats.CloseCombatAP {
		return
	}
	if manhattanDistance(attacker.Position, c.Target) != 1 {
		return
	}

	target := f.fighterAt(c.Target, attacker)
	if target == nil || !f.AreOpponents(attacker, target) {
		return
	}

	attacker.AddCharacteristic(AP, -stats.CloseCombatAP)

	fumble := RollFumble(attacker, f.rng)
	crit := !fumble && RollCriticalHit(attacker, f.rng)

	actionID := f.nextActionID()
	f.broadcastAll(buildCloseCombat(actionID, -1, attacker.ID, fumble, crit, c.Target))
	// Debit the attacker's AP counter client-side (see broadcastCharacUse).
	f.broadcastCharacUse(runningEffectAPUse, attacker, int32(stats.CloseCombatAP))

	if !fumble {
		base := float64(stats.CloseCombatDamages)
		if crit {
			base = float64(stats.CloseCombatCriticalDamages)
		}
		dmg := ComputeHPLoss(DamageParams{
			Caster:    attacker,
			Target:    target,
			BaseValue: base,
			Element:   stats.CloseCombatElement,
		}, f.rng)
		// noTriggeringAction (-1), NOT actionID: the close-combat script
		// pulls the damage effect via executeFirstAction; pointing it back
		// at the CLOSE_COMBAT action would re-run the attack animation. See
		// castSpell's fuller comment on this.
		f.applyDamageFrom(attacker, target, dmg, noTriggeringAction)
	}

	f.flushActionSequence()
	f.markActionTaken()
}

// fighterAt returns the living fighter (other than exclude) standing at
// pos, or nil.
func (f *Fight) fighterAt(pos Point3, exclude *Fighter) *Fighter {
	for _, fr := range f.Timeline.Order() {
		if fr == exclude || fr.IsDead {
			continue
		}
		if fr.Position.X == pos.X && fr.Position.Y == pos.Y {
			return fr
		}
	}
	return nil
}

// hpLossEffectDef synthesizes the EffectDef used to broadcast a HP-loss
// RUNNING_EFFECT_ACTION for a damage source that has no originating spell
// effect (fall damage from push/pull, close combat, trap/poison ticks,
// reactive strike-back/rebound returns). The client needs a NON-ZERO
// runningEffectId to instantiate the effect (getObjectFromId) or it drops
// the packet -- so we key it to HP_LOSS_NEUTRAL (RunningEffectConstants id
// 9, the physical/neutral HP-loss), which the client renders as a plain
// damage number. No spell container (ParentType empty) since these hits
// aren't owned by a spell.
func hpLossEffectDef() gamedata.EffectDef {
	return gamedata.EffectDef{ActionID: runningEffectHPLossNeutral}
}

// runningEffectHPLossNeutral is RunningEffectConstants HP_LOSS_NEUTRAL (id
// 9): the neutral/physical HP-loss effect the client animates as a plain
// damage number. Used for damage with no owning spell effect.
const runningEffectHPLossNeutral int32 = 9

// applyDamage subtracts dmg HP from target, broadcasts the resulting
// RUNNING_EFFECT_ACTION for the HP-loss, and handles death/fight-end
// checking (docs/opcodes/08-fight-combat-engine.md §1.2: checkFightEnd is
// NOT automatic, the caller -- here, applyDamage itself, playing the role
// of "caller of killFighter" -- must explicitly invoke it after a kill).
// Uses a synthesized neutral HP-loss effect for the client broadcast (no
// originating spell effect); callers that DO have the spell effect should
// use applyDamageFromEffect so the correct effect id/element/container is
// sent.
func (f *Fight) applyDamage(target *Fighter, dmg int, triggeringActionID int32) {
	f.applyDamageFromEffect(nil, target, dmg, hpLossEffectDef(), triggeringActionID)
}

// applyDamageFrom is applyDamage plus the reactive damage-return handling
// that needs to know WHO dealt the hit (attacker may be nil for
// environmental/DoT sources, in which case no return is possible). It
// implements two reference mechanics that lack a full trigger-bus in this
// engine (see effects.go's header + Fighter.StrikeBackPercent/
// Fighter.Characteristics[DmgRebound] doc comments):
//   - STRIKE_BACK (id 90): target returns StrikeBackPercent% of the hit to
//     the attacker.
//   - DAMAGES_REBOUND_IN_PERCENT (id 89, DmgRebound characteristic):
//     target returns DmgRebound% of the hit to the attacker.
//
// Both are applied to the SAME triggering hit and stack additively,
// matching the reference (the two are independent RunningEffects that each
// fire on the "was attacked" trigger). Recursion is prevented by only
// returning damage when attacker != nil AND the return target (attacker)
// is not the one that just took the returned hit (guarded by the nil
// attacker on the recursive call).
// applyDamageFrom is the reactive-aware damage entry point for callers that
// have NO originating spell effect (fall damage, reactive returns). It uses
// a synthesized neutral HP-loss effect for the client broadcast.
func (f *Fight) applyDamageFrom(attacker, target *Fighter, dmg int, triggeringActionID int32) {
	f.applyDamageFromEffect(attacker, target, dmg, hpLossEffectDef(), triggeringActionID)
}

// applyDamageFromEffect subtracts dmg HP from target and broadcasts the
// RUNNING_EFFECT_ACTION using `eff` so the client receives a NON-ZERO
// runningEffectId + the correct GenericEffectID/spell container -- WITHOUT
// which the client's case-8120 handler calls getObjectFromId(0), gets null,
// and DROPS the packet, so the spell animates but the damage number never
// shows and the client-side HP never updates (the reported "no damage"
// bug). Also applies reactive STRIKE_BACK/DmgRebound returns to the
// attacker (attacker may be nil for environmental/DoT sources or for the
// reactive-return recursion, which prevents an infinite ping-pong).
func (f *Fight) applyDamageFromEffect(attacker, target *Fighter, dmg int, eff gamedata.EffectDef, triggeringActionID int32) {
	if dmg <= 0 {
		return
	}
	// Sacrieur's Sacrifice fires BEFORE the hit lands: if `target` carries a
	// Sacrifice buff, swap it with the Sacrieur and redirect this whole hit
	// onto them (the protected ally stays unharmed). Applies to ALL damage
	// sources -- spells, close combat, fall damage, AP-poison DoT -- because
	// this is the single chokepoint every hit flows through. One hop only:
	// the redirected hit below lands on the Sacrieur without re-checking, so
	// there's no chain/ping-pong (see applySacrificeRedirect).
	if protector := f.applySacrificeRedirect(target, triggeringActionID); protector != nil {
		target = protector
	}
	// Apply the hit and capture the ACTUAL HP removed (clamped at 0): a
	// 10-damage hit on a fighter with 5 HP only removes 5. The client
	// re-applies the broadcast Value to its own HP bar AND shows it as the
	// floating damage number (HPLoss.execute does substract(m_value) verbatim,
	// without recomputing like HPGain does), so broadcasting the raw 10 would
	// display "-10" over a fighter that only lost 5. Send the real delta.
	removed := -target.AddCharacteristicClamped(HP, -int32(dmg))
	if removed < 0 {
		removed = 0
	}

	var casterID int64
	if attacker != nil {
		casterID = attacker.ID
	}
	containerType, containerID := effectContainer(eff)
	f.broadcastAll(buildRunningEffectAction(
		f.nextActionID(), triggeringActionID,
		// mustBeExecutedNow=false: QUEUE the damage effect so it plays at
		// the cast script's authored hit frame (invoke(<ms>,...) ->
		// executeFirstAction(3, <runningEffectId>)) instead of the instant
		// the packet arrives. See broadcastRunningEffect's fuller comment;
		// true made the damage number land while the cast animation had
		// barely started. Reactive returns (nil attacker) also queue, so
		// strike-back damage plays in sequence after the triggering hit.
		false, false, eff.ActionID,
		runningEffectPayload{
			GenericEffectID: eff.ID,
			CasterID:        casterID,
			TargetID:        target.ID,
			TargetCell:      target.Position,
			Value:           removed,
		},
		containerType, containerID,
	))

	// Reactive damage-return to the attacker (STRIKE_BACK only).
	//
	// IMPORTANT -- do NOT add DmgRebound here. In the reference,
	// DAMAGES_REBOUND_IN_PERCENT (DMG_REBOUND) is applied INSIDE
	// HPLoss.computeValue() (see HPLoss.java lines 312-331: it deals
	// rebound% of the computed value straight to the caster and subtracts
	// it from the delivered damage), which this port reproduces in
	// ComputeHPLoss (damage.go). Every hostile hit that carries a rebound
	// (close combat + spell HP-loss) is routed through ComputeHPLoss, so
	// the rebound is already accounted for by the time we get here. Adding
	// it again in this reactive step double-counted it (the bug flagged in
	// FEATURES-STATUS.md §1 / roadmap). STRIKE_BACK, by contrast, is a
	// genuinely separate reactive RunningEffect (StrikeBack.java, keyed to
	// the "was attacked" trigger) with no place in computeValue -- so it
	// correctly lives here and here only.
	//
	// Only fires when a real attacker dealt the hit, the attacker is still
	// alive, and it isn't a self-hit; the returned damage is dealt with a
	// nil attacker so it cannot itself trigger a return (no ping-pong).
	if attacker != nil && attacker != target && !attacker.IsDead {
		if returnPct := target.StrikeBackPercent; returnPct > 0 {
			returned := int(int32(dmg) * returnPct / 100)
			if returned > 0 {
				f.applyDamageFrom(nil, attacker, returned, triggeringActionID)
			}
		}
	}

	// Reactive trigger-bus: the target "was attacked" (lost HP), the most
	// common listen event in the real spell data (trigOnAttacked=2). Fire
	// any armed reactive effect on the target listening for it -- e.g. a
	// counter-attack or damage-reflect spell (see triggerbus.go). Skipped
	// for a nil attacker (environmental/DoT/reactive-return damage) so a
	// reflected hit can't itself re-trigger the target's reflect (no
	// infinite ping-pong), matching the reactive-return guard above.
	if attacker != nil && !target.IsDead {
		f.fireTrigger(target, trigOnAttacked, false)
	}

	if target.ShouldBeDead() && !target.IsDead {
		f.killFighter(target, triggeringActionID)
	}
}

// castCandidate bundles what the validation pipeline needs from either a
// spell or a card, per the Castable interface sketched in
// docs/05-combat-engine.md §5.5. Cards never carry cast-frequency/LOS/
// criterion data at all (confirmed: FighterCardTemplate has no such
// fields, only SpellTemplate does) -- their zero-value fields (0 bytes /
// false / "") correctly disable each corresponding check below.
type castCandidate struct {
	SpellID      int32 // 0 for a card (cards have no cast-frequency history to key by)
	APCost       byte
	RangeMin     byte
	RangeMax     byte
	NeedFreeCell bool
	CastOnlyLine bool
	Effects      []gamedata.EffectDef

	// Phase L additions (docs/08-java-parity-roadmap.md):
	CastTestLineOfSight       bool
	Criterion                 string
	CastFrequencyMinInterval  byte
	CastFrequencyMaxPerTurn   byte
	CastFrequencyMaxPerTarget byte // see spell_cast_history.go's doc comment on the CastFrequencyMaxPerPlayer naming quirk
}

// validateCast runs the full cast validation pipeline (per the 10-step
// list in docs/05-combat-engine.md §5.5): ownership is checked by the
// caller before this is invoked (fighterOwnsSpell/fighterOwnsCard), AP
// cost, range (+ Range characteristic extension), line-alignment,
// free-cell, cast-frequency history (spells only), line-of-sight (when
// real map data is attached), and custom criteria. Step 10
// (ValidButNoEffectOnTarget) is handled separately at effect-execution
// time (effects.go), not here, since it's about individual effect
// resolution rather than whether the cast itself may proceed at all.
// targetFighterID/hasTarget feed the per-target cast-frequency check
// (0/false for a bare-cell cast with no single-fighter target).
func (f *Fight) validateCast(caster *Fighter, cand castCandidate, target Point3, targetFighterID int64, hasTarget bool) bool {
	if caster.Characteristic(AP) < int32(cand.APCost) {
		return false
	}
	dist := manhattanDistance(caster.Position, target)
	maxRange := int32(cand.RangeMax)
	if maxRange > 1 {
		maxRange += caster.Characteristic(Range)
		if maxRange < int32(cand.RangeMin) {
			maxRange = int32(cand.RangeMin)
		}
	}
	if dist < int32(cand.RangeMin) || dist > maxRange {
		return false
	}
	if cand.CastOnlyLine && target.X != caster.Position.X && target.Y != caster.Position.Y {
		return false
	}
	if cand.NeedFreeCell && f.IsOccupied(target, nil) {
		return false
	}

	if cand.SpellID != 0 {
		v := caster.CastHistory.CanCastSpell(
			cand.SpellID, cand.CastFrequencyMinInterval, cand.CastFrequencyMaxPerTurn, cand.CastFrequencyMaxPerTarget,
			int32(f.Timeline.TableTurn()), targetFighterID, hasTarget,
		)
		if v != SpellCastValidityOK {
			return false
		}
	}

	if cand.CastTestLineOfSight && !f.hasLineOfSight(caster.Position, target) {
		return false
	}

	if cand.Criterion != "" && !f.evaluateCastCriteria(cand.Criterion, caster) {
		return false
	}

	return true
}

// handleSpellCast processes SpellCastRequestMessage(8109): validates then
// executes each of the spell's effects against resolved targets, per
// docs/05-combat-engine.md §5.5's execution steps.
func (f *Fight) handleSpellCast(c cmdSpellCast) {
	if f.CurrentPhase() != PhaseAction || f.data == nil {
		return
	}
	caster, ok := f.resolveOwnedFighter(c.RequesterCoachID, c.FighterID)
	if !ok || caster.IsDead || f.Timeline.CurrentFighter() != caster {
		return
	}
	if !fighterOwnsSpell(caster, c.SpellID) {
		return
	}
	f.castSpell(caster, c.SpellID, c.Target)
}

// castSpell runs the full validate -> debit -> broadcast -> execute -> flush
// pipeline for `caster` casting spell `spellID` at cell `target`. It is the
// shared core of both a player-requested cast (handleSpellCast) and an
// AI-driven summon cast (summon_ai.go). Ownership must already be verified
// by the caller (players via fighterOwnsSpell; the summon AI casts only its
// own SummonSpellID). Returns true iff the cast passed validation and fired
// (so the summon AI can decide whether to keep casting or move on). The
// caller is responsible for it being the caster's turn.
func (f *Fight) castSpell(caster *Fighter, spellID int32, target Point3) bool {
	if f.data == nil {
		return false
	}
	spell, ok := f.data.Spells.Get(spellID)
	if !ok {
		return false
	}
	cand := castCandidate{
		SpellID:                   spellID,
		APCost:                    spell.ActionPointsCost,
		RangeMin:                  spell.RangeMin,
		RangeMax:                  spell.RangeMax,
		NeedFreeCell:              spell.NeedFreeCell,
		CastOnlyLine:              spell.CastOnlyLine,
		Effects:                   spell.Effects,
		CastTestLineOfSight:       spell.CastTestLineOfSight,
		Criterion:                 spell.Criterion,
		CastFrequencyMinInterval:  spell.CastFrequencyMinInterval,
		CastFrequencyMaxPerTurn:   spell.CastFrequencyMaxPerTurn,
		CastFrequencyMaxPerTarget: spell.CastFrequencyMaxPerPlayer, // see spell_cast_history.go's naming-quirk doc comment
	}
	targetFighter := f.fighterAt(target, nil)
	var targetFighterID int64
	hasTarget := targetFighter != nil
	if hasTarget {
		targetFighterID = targetFighter.ID
	}
	if !f.validateCast(caster, cand, target, targetFighterID, hasTarget) {
		return false
	}

	caster.AddCharacteristic(AP, -int32(spell.ActionPointsCost))
	caster.CastHistory.StoreSpellCast(
		spellID, spell.CastFrequencyMinInterval, spell.CastFrequencyMaxPerTurn, spell.CastFrequencyMaxPerPlayer,
		int32(f.Timeline.TableTurn()), targetFighterID, hasTarget,
	)

	fumble := RollFumble(caster, f.rng)
	crit := !fumble && RollCriticalHit(caster, f.rng)
	actionID := f.nextActionID()
	f.broadcastAll(buildSpellCast(actionID, -1, caster.ID, spellID, fumble, crit, target))
	// Debit the caster's AP counter client-side (see broadcastCharacUse).
	f.broadcastCharacUse(runningEffectAPUse, caster, int32(spell.ActionPointsCost))

	f.logger.Info().
		Str("event", "spell_cast").
		Int64("caster_id", caster.ID).Bool("summon", isAISummon(caster)).
		Int32("spell_id", spellID).Int("ap_cost", int(spell.ActionPointsCost)).
		Int32("target_x", target.X).Int32("target_y", target.Y).
		Bool("fumble", fumble).Bool("crit", crit).
		Msg("fight: spell cast")

	if !fumble {
		// crit selects the spell's critical effect subset (bigger roll /
		// extra effect), mirroring close combat's CloseCombatCriticalDamages
		// swap -- see executeEffectsForHit.
		//
		// Primary effects carry triggeringActionUniqueId = -1 (noTriggeringAction),
		// NOT the SPELL_CAST's own actionID. The client's cast script pulls each
		// effect out of the pending action group via
		// executeFirstAction(3, <effectActionId>); ActionGroup.runAction then,
		// if the pulled effect's triggerActionUniqueId != -1, looks that parent
		// up and re-runs IT instead. Passing the SPELL_CAST id here made the
		// pulled effect point back at the still-running SpellAction, so the
		// client re-ran the whole cast script -> the caster's cast animation
		// looped forever and no further input was accepted (the reported
		// "La folle animation loops, can't act" bug). Only genuinely reactive
		// sub-actions (strike-back/rebound returns, DoT ticks) get a real parent
		// id, threaded separately through their own trigger paths.
		f.executeEffectsForHit(caster, spell.Effects, target, noTriggeringAction, crit)
	}
	f.flushActionSequence()
	// Record that an animated action just played, so a turn-end sent right
	// after (before the client finishes the cast group) is briefly deferred
	// -- see handleFighterEndTurn's settle guard.
	f.markActionTaken()
	return true
}

// handleCardUse processes FighterCardUseRequestMessage(8107): same shape
// as spell casting but against the fighter's equipped fight-cards.
func (f *Fight) handleCardUse(c cmdCardUse) {
	if f.CurrentPhase() != PhaseAction || f.data == nil {
		return
	}
	caster, ok := f.resolveOwnedFighter(c.RequesterCoachID, c.FighterID)
	if !ok || caster.IsDead || f.Timeline.CurrentFighter() != caster {
		return
	}
	if !fighterOwnsCard(caster, c.CardID) {
		return
	}
	card, ok := f.data.FighterCards.Get(c.CardID)
	if !ok {
		return
	}
	// Cards do not work on summons (wiki: "Cards do not work on summons"):
	// reject a card aimed directly at a cell occupied by a summon (a fighter
	// with a Father). A card aimed at empty ground or a real fighter is
	// unaffected.
	if tf := f.fighterAt(c.Target, nil); tf != nil && tf.Father != nil {
		return
	}
	// USING a card triggers only its USE-time (FIGHTER_CARD_USE) effects --
	// the actively-castable abilities. The card's EQUIP-time
	// (FIGHTER_CARD_EQUIP) effects are the PASSIVE stat bonuses already
	// applied to the fighter's characteristics at fight-build time
	// (combat.ApplyEquipmentBonuses); re-running them on use would
	// double-apply +Init/+HP/etc. every time the card is played. This
	// mirrors the reference AbstractFighterCard, which keeps use-time and
	// equip-time effects in separate containers and only casts the use set
	// on FighterCardUse. (UseEffects falls back to the full list only for
	// legacy/synthetic data with no use/equip split -- see
	// gamedata.splitFighterCardEffects.)
	useEffects := card.UseEffects
	cand := castCandidate{Effects: useEffects}
	if !f.validateCast(caster, cand, c.Target, 0, false) {
		return
	}

	fumble := RollFumble(caster, f.rng)
	crit := !fumble && RollCriticalHit(caster, f.rng)
	actionID := f.nextActionID()
	f.broadcastAll(buildFighterCardUse(actionID, -1, caster.ID, c.CardID, fumble, crit, c.Target))

	if !fumble {
		// noTriggeringAction (-1): a card's own effects are pulled by its
		// cast script, same as spells -- see castSpell's comment.
		f.executeEffectsForHit(caster, useEffects, c.Target, noTriggeringAction, crit)
	}
	f.flushActionSequence()
	f.markActionTaken()
}

func fighterOwnsSpell(f *Fighter, spellID int32) bool {
	for _, id := range f.SpellIDs {
		if id == spellID {
			return true
		}
	}
	return false
}

func fighterOwnsCard(f *Fighter, cardID int32) bool {
	for _, id := range f.ObjectIDs {
		if id == cardID {
			return true
		}
	}
	return false
}
