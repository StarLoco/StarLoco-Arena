package combat

import (
	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file implements the equip-time PASSIVE characteristic bonuses that
// equipped fighter cards grant, mirroring the reference client's
// AbstractFighter.applyCardEffects()
// (client/.../game/fighter/AbstractFighter.java:602-615), which, for every
// equipped card, iterates that card's EQUIP-time effects
// (effectsAtEquippementTimeIterator) and applies each one to the fighter's
// characteristics -- e.g. a card carrying a CharacBuff(INIT, +60) raises the
// fighter's Initiative by 60 while equipped.
//
// Crucially the reference applies these on the inventory ITEM_ADDED event,
// BEFORE combat begins, so a fighter enters the fight already carrying its
// equipment deltas -- which is why they influence turn order (the timeline
// is built from the post-equip Init characteristic). We reproduce that by
// applying the bonuses at fight-build time (buildCombatTeam), BEFORE the
// combat.Timeline is constructed from the fighters' Init values.
//
// Only the passive stat kinds are handled here -- the ones the real
// FIGHTER_CARD_EQUIP effect subset actually uses (confirmed by decoding
// cards.dat): CharacGain (add current value), CharacBuff (raise max + add),
// CharacDebuff (lower max). Any other kind that ever appears in an equip
// container (e.g. a movement/summon effect, which the data never puts there)
// is deliberately ignored: a passive "while equipped" slot has no cast-time,
// caster/target-cell context to resolve such effects against, so applying
// them would be meaningless. This matches the reference in practice, where
// every FIGHTER_CARD_EQUIP effect in the shipped data is a CharacBuff/
// CharacGain/CharacDebuff.
//
// Unlike the in-combat effect executor (effects.go), equip bonuses are NOT
// broadcast as RUNNING_EFFECT_ACTION frames: the client computes them itself
// from the equipped inventory it already knows about (it applied them on its
// own ITEM_ADDED event), so the server must NOT also send an effect frame or
// the bonus would be double-counted client-side. The server simply arrives
// at the same post-equip characteristic values.

// ApplyEquipmentBonuses applies the passive equip-time characteristic
// bonuses of every equipped card in cards to f, in order. Each card's
// EquipEffects (the FIGHTER_CARD_EQUIP subset, see
// gamedata.splitFighterCardEffects) is aggregated onto f's characteristics.
// Cards with no equip effects contribute nothing.
//
// f's current values are re-topped for the resource stats (HP/AP/MP/Init)
// after applying, so a +HP/+AP/+MP/+Init buff makes the fighter start the
// fight at the boosted maximum (mirroring the reference, where the fighter's
// initializeCharacteristics runs first, then equip buffs raise max AND
// current together via CharacBuff.execute's updateMaxValue+add).
func ApplyEquipmentBonuses(f *Fighter, cards []gamedata.FighterCardTemplate) {
	if f == nil {
		return
	}
	for _, card := range cards {
		for _, eff := range card.EquipEffects {
			applyEquipEffect(f, eff)
		}
	}
}

// applyEquipEffect applies one equip-time effect to a fighter, resolving the
// effect's actionID to a characteristic + kind via the same
// RunningEffectConstants mapping the in-combat executor uses
// (LookupRunningEffect). Only CharacGain/CharacBuff/CharacDebuff are
// meaningful as passive equipment bonuses (see this file's header).
func applyEquipEffect(f *Fighter, eff gamedata.EffectDef) {
	def, ok := LookupRunningEffect(eff.ActionID)
	if !ok {
		return
	}
	v := equipEffectValue(eff.Params)
	switch def.Kind {
	// CharacGain: plain current-value add (mirrors CharacGain.execute,
	// which calls only add()). Used for the % damage/resist, crit, range
	// and rebound equip bonuses -- stats with no Max, so add() is the whole
	// story.
	case EffectCharacGain:
		f.AddCharacteristic(def.Charc, v)

	// CharacBuff: raise the Max ceiling AND the current value (mirrors
	// CharacBuff.execute: updateMaxValue(v) then add(v)). This is how the
	// HP/AP/MP/Init equip bonuses work -- a card that gives +60 Init raises
	// both the Init max and the starting Init value by 60, so the fighter
	// enters combat with the boosted stat AND it feeds turn-order.
	case EffectCharacBuff:
		charac, ok := f.Characteristics[def.Charc]
		if !ok {
			return
		}
		charac.AddMax(v)
		f.AddCharacteristic(def.Charc, v)

	// CharacDebuff: lower the Max ceiling only (mirrors CharacDebuff.execute:
	// updateMaxValue(-v); current value follows down only via the clamp).
	// This is the negative-Initiative tradeoff many equipment cards carry
	// (INIT_DEBOOST, actionID 77) -- e.g. a strong weapon that costs -40
	// Initiative.
	case EffectCharacDebuff:
		charac, ok := f.Characteristics[def.Charc]
		if !ok {
			return
		}
		charac.AddMax(-v)

	default:
		// Any non-stat effect kind erroneously placed in an equip container
		// has no passive meaning (see header) -- ignore it.
	}
}

// equipEffectValue resolves an equip-time effect's fixed magnitude from its
// Params. Equipment bonuses in the real cards.dat are single fixed values
// (params = [value]); the 3-param dice form (diceCount,diceFaces,modifier)
// is resolved to its FIXED, non-random floor+modifier baseline here rather
// than rolled, because equipping an item is a deterministic, pre-combat
// action with no fight RNG in scope (the reference rolls once at equip time,
// but the shipped equip data never uses the dice form, so the distinction is
// moot in practice; a deterministic value keeps fight setup reproducible).
func equipEffectValue(params []float32) int32 {
	switch len(params) {
	case 0:
		return 0
	case 1:
		return int32(params[0])
	default:
		// diceCount,diceFaces,modifier -> minimum roll (diceCount*1) plus
		// modifier, a deterministic floor. Not exercised by real equip data.
		diceCount := int32(params[0])
		modifier := int32(params[2])
		if diceCount < 0 {
			diceCount = 0
		}
		return diceCount + modifier
	}
}
