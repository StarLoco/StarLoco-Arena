package game

import (
	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Special battlefield cells (game manual §5.0.4). A handful of named tiles are
// authored into each arena's .fmd; standing on one does nothing by itself —
// "It's no use to walk on or to fly over a special cell: the fighter must start
// his turn on it". The effect then lasts that fighter's turn.
//
// The client already draws the tile art (it reads the .fmd itself), but the
// MECHANIC is server-authoritative: the server sends the cells in CREATE_FIGHT
// so the client instantiates a live EffectArea per tile, then applies the effect
// at turn start and animates the tile with EFFECT_AREA_ACTION (6200). Without the
// CREATE_FIGHT list the tiles are inert decoration — which is exactly how they
// behaved before B-048.
//
// The effect table and the template-id mapping are ported from the v2.04b server
// (internal/combat/specialcells.go), which decoded them from the same manual.

// specialCell is one map-authored special tile.
type specialCell struct {
	Pos      Pos
	Template int64 // client staticEffect template id (SPECIAL 1002-1009)
}

// specialCellType is the gameplay behaviour a template id maps to.
type specialCellType uint8

const (
	specialCellNone specialCellType = iota
	specialCellKiller
	specialCellTrap
	specialCellEagleEye
	specialCellShield
	specialCellPanacea
	specialCellEnthusiasm
	specialCellMotivation
	specialCellHealingHeart
)

// specialCellByTemplate maps the client's staticEffect template id to the
// modelled behaviour (v2.04b parity).
var specialCellByTemplate = map[int64]specialCellType{
	1002: specialCellKiller,
	1003: specialCellTrap,
	1004: specialCellEagleEye,
	1005: specialCellShield,
	1006: specialCellPanacea,
	1007: specialCellEnthusiasm,
	1008: specialCellMotivation,
	1009: specialCellHealingHeart,
}

// Characteristic-boost running-effect action ids (client mh_2 ids). The client
// renders the boost number from these; v2.04b charcRunningEffectID parity.
const (
	runEffectAPBoost     int32 = 13 // "Gain de PA"
	runEffectRangeBoost  int32 = 72 // "Gain de portée"
	runEffectHealBoost   int32 = 78 // "Gain de soins"
	runEffectResPctBoost int32 = 80 // "Gain de résistance %"
	runEffectDmgPctBoost int32 = 82 // "Gain de dommages %"
)

// Effect magnitudes, straight from the manual's table.
const (
	specialTrapDamage     int32 = 10
	specialEnthusiasmDmg  int32 = 10 // +10% damage dealt
	specialShieldRes      int32 = 10 // +10% resistance
	specialEagleEyeRange  int32 = 1  // +1 range
	specialPanaceaHeal    int32 = 10 // +10% heal power
	specialMotivationAP   int32 = 1  // +1 AP
	specialHealingHeartHP int32 = 5  // +5 HP, only if injured
)

// cellBuff records the deltas one special cell granted a fighter this turn, so
// they can be reverted when that fighter's turn ends. Only the fields the tile
// actually touched are non-zero.
type cellBuff struct {
	wireID  int64
	ap      int32
	rng     int32
	dmgPct  int32
	resPct  int32
	healPct int32
}

// applyTurnStartSpecialCell fires the special cell (if any) under a fighter that
// is starting its turn. Returns true if the fighter died, in which case the
// caller must not continue with a normal turn.
//
// Must run on the fight goroutine.
func (f *Fight) applyTurnStartSpecialCell(ff *FightFighter) (died bool) {
	if ff == nil || ff.HP <= 0 {
		return false
	}
	sc, instanceID, ok := f.Arena().specialAt(ff.Pos.X, ff.Pos.Y)
	if !ok {
		return false
	}
	kind := specialCellByTemplate[sc.Template]
	if kind == specialCellNone {
		return false
	}

	// EVERYTHING this tile broadcasts must be closed with an
	// ACTION_SEQUENCE_EXECUTE barrier. The client queues fight frames and only
	// plays them when a flush arrives; without one the tile's animation and its
	// boost sat in the queue until the next flush later in the turn, so they
	// appeared at the END of the turn instead of the start (B-049). Deferred so
	// every exit path (killer / trap / heal / buff) flushes exactly once.
	defer func() {
		if seq, err := buildActionSequenceExecute(); err == nil {
			f.broadcast(seq)
		}
	}()

	// Animate the tile first, so the client shows it activating before the
	// gameplay numbers land. Purely cosmetic: the client's EffectArea.execute is
	// empty, so this can never double-apply the effect.
	if frame, err := buildEffectAreaAction(f.nextActionUID(), instanceID, sc.Template, ff.WireID); err == nil {
		f.broadcast(frame)
	}

	// A special cell has no spell/card owner, so the fighter is its own "caster"
	// and there is no generic effect container (id 0) — the client then renders
	// the number without a persistent buff icon, which is right for a one-shot tile.
	switch kind {
	case specialCellKiller:
		// "If one of your fighters starts his activation on a killer cell, he is
		// automatically killed." No save, no resistance. applyHPDelta broadcasts
		// the hit and FIGHTER_DIES.
		f.applyHPDelta(ff, ff, protocol.RunEffectHPLoss, 0, -ff.HP)
		f.deps.checkFightEnd(f)
		return true

	case specialCellTrap:
		f.applyHPDelta(ff, ff, protocol.RunEffectHPLoss, 0, -specialTrapDamage)
		if ff.HP <= 0 {
			f.deps.checkFightEnd(f)
			return true
		}
		return false

	case specialCellHealingHeart:
		// "An injured fighter gains 5 HPs" — only when actually below max.
		// applyHPDelta clamps to MaxHP and broadcasts the ACTUAL amount, so the
		// client's HP bar cannot overshoot.
		if ff.HP < ff.MaxHP {
			f.applyHPDelta(ff, ff, gamedata.ActionHeal, 0, specialHealingHeartHP)
		}
		return false
	}

	// The remaining tiles are turn-long stat buffs. Each one also broadcasts its
	// boost as a running effect, so the client animates the number on the fighter
	// — without it the stat changes silently server-side and the player sees
	// nothing happen (B-049). The action ids are the client's characteristic-boost
	// ids (v2.04b charcRunningEffectID parity).
	b := cellBuff{wireID: ff.WireID}
	var boostAction, boostValue int32
	switch kind {
	case specialCellEnthusiasm:
		b.dmgPct = specialEnthusiasmDmg
		ff.Stats.dmgPctAll += b.dmgPct
		boostAction, boostValue = runEffectDmgPctBoost, b.dmgPct
	case specialCellShield:
		b.resPct = specialShieldRes
		ff.Stats.resPctAll += b.resPct
		boostAction, boostValue = runEffectResPctBoost, b.resPct
	case specialCellEagleEye:
		b.rng = specialEagleEyeRange
		ff.Range += b.rng
		boostAction, boostValue = runEffectRangeBoost, b.rng
	case specialCellPanacea:
		b.healPct = specialPanaceaHeal
		ff.Stats.healPct += b.healPct
		boostAction, boostValue = runEffectHealBoost, b.healPct
	case specialCellMotivation:
		// Raise the ceiling as well as the current value: AP is clamped to MaxAP,
		// so bumping only the current value would be silently clamped straight
		// back down and the tile would do nothing.
		b.ap = specialMotivationAP
		ff.MaxAP += b.ap
		ff.AP += b.ap
		boostAction, boostValue = runEffectAPBoost, b.ap
	}
	f.cellBuffs = append(f.cellBuffs, b)
	if boostAction != 0 {
		eff, _ := buildRunningEffect(f.nextActionUID(), boostAction, 0,
			ff.WireID, ff.WireID, ff.Pos, boostValue, 0, false)
		f.broadcast(eff)
	}
	return false
}

// revertSpecialCellBuffs undoes every special-cell buff still held by ff. Called
// when ff's turn ends. Must run on the fight goroutine.
func (f *Fight) revertSpecialCellBuffs(ff *FightFighter) {
	if ff == nil || len(f.cellBuffs) == 0 {
		return
	}
	kept := f.cellBuffs[:0]
	for _, b := range f.cellBuffs {
		if b.wireID != ff.WireID {
			kept = append(kept, b)
			continue
		}
		ff.Stats.dmgPctAll -= b.dmgPct
		ff.Stats.resPctAll -= b.resPct
		ff.Stats.healPct -= b.healPct
		ff.Range -= b.rng
		if b.ap != 0 {
			// Mirror the buff's own semantics: drop the ceiling, and let the
			// clamp pull the current value down only if it now exceeds it. A
			// fighter that already SPENT the bonus AP must not be charged twice.
			ff.MaxAP -= b.ap
			if ff.AP > ff.MaxAP {
				ff.AP = ff.MaxAP
			}
		}
	}
	f.cellBuffs = kept
}

// writeSpecialCells emits the CREATE_FIGHT special-cell section:
// [i8 count] then count × [i64 templateId][i64 instanceId][i32 x][i32 y][i16 z].
// The instance id is what the client keys its live EffectArea by, and therefore
// what an EFFECT_AREA_ACTION (6200) must reference. Sending count 0 is safe but
// leaves every tile inert.
func writeSpecialCells(w *protocol.Writer, a *arena) {
	w.U8(uint8(len(a.specials)))
	for i, sc := range a.specials {
		w.I64(sc.Template)
		w.I64(int64(i + 1)) // instance id, 1-based and stable for the fight
		w.I32(sc.Pos.X)
		w.I32(sc.Pos.Y)
		w.U16(uint16(sc.Pos.Z))
	}
}

// buildEffectAreaAction builds EFFECT_AREA_ACTION (6200 jD): the ue_0 header +
// [i8 entering][i64 areaInstanceId][i64 templateId][i64 fighterId]. It makes the
// client play that tile's own animation script; it carries no gameplay.
func buildEffectAreaAction(uid int32, instanceID, templateID, fighterWireID int64) ([]byte, error) {
	w := protocol.NewWriter()
	writeActionHeader(w, uid)
	w.U8(1) // entering the area
	w.I64(instanceID)
	w.I64(templateID)
	w.I64(fighterWireID)
	return protocol.EncodeS2C(protocol.OpEffectAreaAction, w.Bytes())
}
