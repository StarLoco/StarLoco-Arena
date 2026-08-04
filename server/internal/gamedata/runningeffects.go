package gamedata

// Running-effect action ids for the 2.70 client, transcribed from the client's
// RunningEffectConstants (obfuscated mh_2). Every effect decoded from a spell or
// card record carries one of these as its ActionID; this file is the single
// authoritative id→meaning map the combat resolver builds on.
//
// NOTE: 2.70 EXTENDED the id set well past the older (2.0x) map — in particular
// the "…par sort" spell-damage effects live at 130-134, and those (not the low
// 1-5 set) are what most 2.70 spells actually use to deal damage.

// Element is the damage/resistance element of an elemental effect.
type Element uint8

const (
	ElementNeutral Element = iota // "physique"/neutral (fv_1.bal)
	ElementFire                   // fv_1.bam
	ElementEarth                  // fv_1.bap
	ElementWater                  // fv_1.ban
	ElementAir                    // fv_1.bao
)

// Selected running-effect action ids (see mh_2). Only the combat-relevant ones
// are named; the rest are addressed numerically where needed.
const (
	// Direct HP loss by element (mh_2 ids 1-5).
	actionHPLoss      = 1
	actionHPLossFire  = 2
	actionHPLossEarth = 3
	actionHPLossWater = 4
	actionHPLossAir   = 5

	// HP leech (damage + self-heal) by element (ids 6-10).
	actionHPLeech = 6

	// "…par sort" HP loss — the bread-and-butter spell damage (ids 130-134).
	actionSpellHPLoss      = 130
	actionSpellHPLossFire  = 131
	actionSpellHPLossEarth = 132
	actionSpellHPLossWater = 133
	actionSpellHPLossAir   = 134

	actionHPLossPercent = 125 // % of max HP

	actionHeal = 69 // "Soin"

	// AP/MP attrition.
	actionAPLossFlat = 16
	actionMPLossFlat = 20
	actionAPLeech    = 85
	actionMPLeech    = 103

	// Movement/utility.
	actionPush     = 37
	actionPull     = 38
	actionTeleport = 39

	// Range boost (RANGE_GAIN): raises a fighter's Range characteristic, which
	// extends the max range of its boostable (RangeMax>1) spells.
	actionRangeGain = 72
)

// flatDamageElement maps every "remove N HP now" running-effect id to its
// element. These are the effects whose params[0] is a flat HP amount the caster
// removes from the target: the direct HP-loss set (1-5), the HP-leech set (6-10,
// whose self-heal we defer), and the "…par sort" spell-damage set (130-134).
// (Damage-over-time, %-HP, AP/PM-scaled and zone/line variants are deliberately
// excluded from this first cut; they need their own resolution.)
var flatDamageElement = map[int32]Element{
	actionHPLoss:           ElementNeutral,
	actionHPLossFire:       ElementFire,
	actionHPLossEarth:      ElementEarth,
	actionHPLossWater:      ElementWater,
	actionHPLossAir:        ElementAir,
	actionHPLeech:          ElementNeutral, // 6
	actionHPLeech + 1:      ElementFire,    // 7
	actionHPLeech + 2:      ElementEarth,   // 8
	actionHPLeech + 3:      ElementWater,   // 9
	actionHPLeech + 4:      ElementAir,     // 10
	actionSpellHPLoss:      ElementNeutral,
	actionSpellHPLossFire:  ElementFire,
	actionSpellHPLossEarth: ElementEarth,
	actionSpellHPLossWater: ElementWater,
	actionSpellHPLossAir:   ElementAir,
}

// isFlatDamage reports whether an action id is a direct HP-removal (damage)
// effect, returning its element.
func isFlatDamage(actionID int32) (Element, bool) {
	e, ok := flatDamageElement[actionID]
	return e, ok
}

// --- Exported classification for the combat effect resolver (game package) ---

// Effect action ids the combat resolver handles (mh_2). Exported so the game
// package can classify a decoded spell/card effect without duplicating the map.
const (
	ActionHeal     = actionHeal       // 69  "Soin" (HP gain)
	ActionAPLoss   = actionAPLossFlat // 16  "Perte de PA"
	ActionMPLoss   = actionMPLossFlat // 20  "Perte de PM"
	ActionAPSteal  = actionAPLeech    // 85  "Vol de PA" (target loses, caster gains)
	ActionMPSteal  = actionMPLeech    // 103 "Vol de PM"
	ActionPush     = actionPush       // 37  push away
	ActionPull     = actionPull       // 38  pull toward
	ActionTeleport = actionTeleport   // 39  teleport
)

// FlatDamage reports whether this effect deals flat HP damage, and its element.
// The magnitude is Val().
func (e Effect) FlatDamage() (Element, bool) { return isFlatDamage(e.ActionID) }

// Val returns the effect's primary magnitude (params[0]) as an int, or 0.
func (e Effect) Val() int32 {
	if len(e.Params) > 0 {
		return int32(e.Params[0])
	}
	return 0
}
