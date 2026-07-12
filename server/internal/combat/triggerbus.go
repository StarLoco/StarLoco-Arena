package combat

import "github.com/dofusarena/go-server/internal/gamedata"

// This file implements the reactive trigger-bus (docs/08-java-parity-
// roadmap.md §8.11 item 2 / §8.12 Phase J's remaining item): the mechanism
// by which an effect declaring TriggersBefore/TriggersAfter is DEFERRED at
// cast time (stored on its target rather than executed instantly) and later
// fires in response to a specific in-fight event -- e.g. a "reflect the
// next hit" or "counter-attack when struck" spell, or a heal-over-time that
// re-applies each table-turn.
//
// # Reference model
//
// Every RunningEffect declares, via its EffectDef data:
//   - TriggersAfter  (getTriggersAfterToListen)  -- fire AFTER a matching
//     event resolves on the carrier.
//   - TriggersBefore (getTriggersBeforeToListen) -- fire BEFORE a matching
//     event resolves on the carrier (used by pre-emptive effects like
//     SPELL_REBOUND, which must redirect a hit before it lands).
//   - EndTriggers (getEndTriggers) -- events that END/remove the effect.
//     Not present in this project's real data (EffectContentDocumentLoader
//     reads it as null), so it is parsed-but-unused here.
//
// RunningEffect.mustBeTriggered() returns true iff either listen-set is
// non-empty; such an effect is STORED (RunningEffectManager.storeEffect)
// rather than executed immediately -- it waits for its trigger.
//
// The trigger IDs themselves are the numeric ids each RunningEffect EMITS
// when it executes (its m_triggers bitset, set in setTriggersToExecute()).
// The subset that actually appears in this project's real spells.dat/
// events.dat listen-sets is small and enumerated below (trigTable). An
// effect listening for e.g. trigger 2 fires whenever ANY effect emitting
// trigger 2 (an HP loss / "was attacked" on the same fighter) resolves.
//
// # Scope of this implementation
//
// This is a FOCUSED bus covering exactly the trigger ids the real game data
// uses (confirmed empirically by scanning spells.dat/cards.dat/events.dat:
// only ids 2, 52, 54, 55, 56, 64, and the table-turn id 1001 appear in any
// listen-set). It is deliberately not the full generic BitSet-vs-BitSet
// activation engine from the reference (RunningEffect.mustBeTriggered /
// EffectContext trigger dispatch), which would carry cost with zero
// observable benefit for data that never exercises the other ~180 possible
// trigger ids. New game data introducing an unlisted trigger id degrades
// gracefully: the effect is still stored, it simply never fires (logged at
// Debug), exactly as an unhandled reference trigger would be inert.

// Trigger event ids that appear in this project's real data's listen-sets.
// Values are the reference m_triggers ids, decoded from the per-
// RunningEffect setTriggersToExecute() switch tables (each keyed on the
// effect's operation + target characteristic), cross-checked against the
// decompiled source:
//   - trigOnAttacked (2): emitted by HPLoss/Death/CharacPoison and by a
//     CharacLoss on AP -- the "was attacked / lost HP or AP" event. By far
//     the most common listen trigger in the data (counter-attack, reflect,
//     retaliate-style spells).
//   - trigOnAPLoss (52): APLoss, and CharacLoss(CRITICAL_RATE) -- "AP (or
//     crit-rate) was lost".
//   - trigOnCritRateDebuff (54): CharacDebuff(CRITICAL_RATE).
//   - trigOnAPUse (55): APUse -- an AP spend (movement/cast cost).
//   - trigOnCritRateLeech (56): CharacLeech(CRITICAL_RATE).
//   - trigOnDmgDebuff (64): CharacDebuff(DMG).
//   - trigTableTurn (1001): the table-turn boundary tick, used by
//     heal-over-time / renewing effects (after=[1001] with a real
//     Duration). Bridged to the existing table-turn duration machinery so
//     these behave like the DoT/HoT they are.
//
// These are the ONLY trigger ids that appear in any listen-set in this
// project's real spells.dat/events.dat (empirically); every one is now
// EMITTED at its source event (see fireTrigger call sites + emitCharacTrigger).
const (
	trigOnAttacked       int32 = 2
	trigOnAPLoss         int32 = 52
	trigOnCritRateDebuff int32 = 54
	trigOnAPUse          int32 = 55
	trigOnCritRateLeech  int32 = 56
	trigOnDmgDebuff      int32 = 64
	trigTableTurn        int32 = 1001
)

// characteristicTriggerEmission maps an effect's (operation, characteristic)
// to the trigger id it EMITS when it executes on a target, ported from the
// reference RunningEffect subclasses' setTriggersToExecute() switch tables
// (CharacLoss/CharacDebuff/CharacLeech/APLoss). Only the entries whose
// trigger id is actually LISTENED for anywhere in this project's real data
// are included -- the reference tables define ~180 such (charac,op) trigger
// ids, but emitting ones nothing listens for would be pure dead work. The
// key is a (EffectKind, CharacteristicType) pair.
//
// Note trigOnAPUse(55) and trigOnAttacked(2) are emitted directly at their
// dedicated call sites (broadcastCharacUse / applyDamageFromEffect), not via
// this table, since they're not plain characteristic-mutation events.
type characteristicTriggerKey struct {
	kind  EffectKind
	charc CharacteristicType
}

var characteristicTriggerEmission = map[characteristicTriggerKey]int32{
	{EffectCharacLoss, CriticalRate}:   trigOnAPLoss,         // CharacLoss(CRITICAL_RATE) -> 52
	{EffectCharacLoss, AP}:             trigOnAPLoss,         // APLoss -> 52 (AP loss modeled as CharacLoss(AP))
	{EffectCharacDebuff, CriticalRate}: trigOnCritRateDebuff, // CharacDebuff(CRITICAL_RATE) -> 54
	{EffectCharacDebuff, Dmg}:          trigOnDmgDebuff,      // CharacDebuff(DMG) -> 64
	{EffectCharacLeech, CriticalRate}:  trigOnCritRateLeech,  // CharacLeech(CRITICAL_RATE) -> 56
}

// emitCharacTrigger fires the reactive trigger (if any) that a
// characteristic-mutating effect emits on its target, per
// characteristicTriggerEmission. Called from the CharacLoss/CharacDebuff/
// CharacLeech handlers after the mutation is applied, so an armed reactive
// effect on the target (e.g. a spell-rebound that also arms when its
// carrier's damage/crit is debuffed) fires. No-op for (kind, charac) pairs
// no real spell listens for.
func (f *Fight) emitCharacTrigger(target *Fighter, kind EffectKind, charc CharacteristicType) {
	if target == nil {
		return
	}
	if trig, ok := characteristicTriggerEmission[characteristicTriggerKey{kind, charc}]; ok {
		f.fireTrigger(target, trig, false)
	}
}

// reactiveActiveEffectKind marks an ActiveEffect stored by the trigger-bus
// (as opposed to the poison/buff duration kinds already in duration.go). It
// is stored on the CARRIER (the fighter the effect was applied to) and
// carries everything needed to execute the deferred effect when a matching
// trigger fires.
type reactiveEffect struct {
	Caster    *Fighter // the original caster (may be nil for environmental)
	Def       runningEffectDef
	Eff       gamedata.EffectDef
	Before    []int32 // TriggersBefore listen-set
	After     []int32 // TriggersAfter listen-set
	Remaining int32   // table-turns left before this reactive effect expires
	Infinite  bool    // never auto-expires (Duration >= 63)
}

// effectMustBeDeferred reports whether an effect declares any before/after
// listen-trigger and therefore should be STORED as a reactive effect rather
// than executed instantly, mirroring RunningEffect.mustBeTriggered().
func effectMustBeDeferred(eff gamedata.EffectDef) bool {
	return len(eff.TriggersBefore) > 0 || len(eff.TriggersAfter) > 0
}

// deferReactiveEffect stores a triggered effect on target so it can fire
// later when a matching game event occurs. Mirrors
// RunningEffectManager.storeEffect for a must-be-triggered effect. The
// effect's own Duration bounds how long it stays armed (an infinite
// duration -- Duration>=63 -- arms it for the rest of the fight); a zero
// duration still arms it for a single table-turn window so a "reflect the
// next hit" effect at least survives to the carrier's next turn.
func (f *Fight) deferReactiveEffect(caster, target *Fighter, def runningEffectDef, eff gamedata.EffectDef) {
	remaining := durationTableTurns(eff.Duration)
	infinite := isInfiniteDuration(eff.Duration)
	if remaining <= 0 && !infinite {
		remaining = 1 // arm for at least the coming table-turn
	}
	target.ReactiveEffects = append(target.ReactiveEffects, reactiveEffect{
		Caster:    caster,
		Def:       def,
		Eff:       eff,
		Before:    eff.TriggersBefore,
		After:     eff.TriggersAfter,
		Remaining: remaining,
		Infinite:  infinite,
	})
	// Broadcast the effect application so the client shows the buff/reflect
	// icon on the carrier even though nothing has happened yet.
	f.broadcastRunningEffect(eff, caster, target, 0, -1)
	f.logger.Debug().
		Int64("carrier", target.ID).Int32("action_id", eff.ActionID).
		Ints32("before", eff.TriggersBefore).Ints32("after", eff.TriggersAfter).
		Msg("combat: stored reactive (triggered) effect")
}

// listens reports whether re's before-set (when phaseBefore) or after-set
// (otherwise) contains triggerID.
func (re reactiveEffect) listens(triggerID int32, phaseBefore bool) bool {
	set := re.After
	if phaseBefore {
		set = re.Before
	}
	for _, id := range set {
		if id == triggerID {
			return true
		}
	}
	return false
}

// fireTrigger runs every armed reactive effect on carrier that is listening
// for triggerID in the given phase (before/after the causing event). Called
// from the combat event sites (fighterWasAttacked, an AP-use, a table-turn
// tick, ...). The deferred effect executes against carrier as its target
// (the carrier is who armed it); its own instantaneous handler runs via
// applyRunningEffect, so a reflected/countered effect goes through the same
// damage/heal/charac path as a fresh cast. A reactive effect is NOT
// consumed by firing (a "reflect all hits this turn" effect fires on every
// hit until its duration expires) -- matching the reference, where a stored
// RunningEffect keeps listening until its TimeEvent removes it.
func (f *Fight) fireTrigger(carrier *Fighter, triggerID int32, phaseBefore bool) {
	if carrier == nil || len(carrier.ReactiveEffects) == 0 {
		return
	}
	// Snapshot: applyRunningEffect below could mutate carrier.ReactiveEffects
	// (e.g. a reflected effect that itself defers). Iterate a copy.
	armed := make([]reactiveEffect, len(carrier.ReactiveEffects))
	copy(armed, carrier.ReactiveEffects)
	for _, re := range armed {
		if !re.listens(triggerID, phaseBefore) {
			continue
		}
		f.logger.Debug().
			Int64("carrier", carrier.ID).Int32("trigger", triggerID).Bool("before", phaseBefore).
			Int32("action_id", re.Eff.ActionID).Msg("combat: reactive effect fired")
		f.applyRunningEffect(re.Caster, carrier, re.Def, re.Eff, -1)
	}
}

// tickReactiveEffects decrements every armed reactive effect's remaining
// table-turn counter once per table-turn boundary and drops any that
// expired, plus fires those listening for the table-turn tick itself
// (trigTableTurn). Called from tickActiveEffects (duration.go) so the
// trigger-bus shares the single table-turn boundary hook.
func (f *Fight) tickReactiveEffects(fighter *Fighter, triggeringActionID int32) {
	if len(fighter.ReactiveEffects) == 0 {
		return
	}
	// First fire any table-turn-tick listeners (heal/effect over time).
	f.fireTrigger(fighter, trigTableTurn, false)

	remaining := fighter.ReactiveEffects[:0]
	for _, re := range fighter.ReactiveEffects {
		if re.Infinite {
			remaining = append(remaining, re)
			continue
		}
		re.Remaining--
		if re.Remaining > 0 {
			remaining = append(remaining, re)
		}
	}
	fighter.ReactiveEffects = remaining
}
