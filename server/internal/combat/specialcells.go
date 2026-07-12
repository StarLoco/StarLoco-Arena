package combat

import "github.com/dofusarena/go-server/internal/gamedata"

// This file implements the special battlefield cells described in the
// game manual (Game_Guide_DofusArena_v2.pdf §5.0.4): a handful of named
// cell types that trigger an effect exactly once, only when a fighter
// STARTS their activation phase standing on one ("It's no use to walk on
// or to fly over a special cell: the fighter must start his turn on it").
// No equivalent server-authoritative system exists in the decompiled
// reference source under this pass's scope (see
// docs/opcodes/README.md's StaticEffectAreaManager/special-cell wire
// format, which is a real but separate mechanism for map-authored static
// effect areas -- this file is the simpler manual-described "instant
// numbered-effect tile" model, distinct from that richer trap/glyph
// system in docs/08-java-parity-roadmap.md §8.11 item 6). Since no real
// map (.amw) data is parsed in this port, SpecialCells on a Fight starts
// empty; population is left to whatever eventually sources per-map cell
// layouts (a follow-up item, not blocking the mechanic itself from being
// correct once cells exist).
type SpecialCellType int

const (
	SpecialCellNone SpecialCellType = iota
	SpecialCellTrap
	SpecialCellEnthusiasm
	SpecialCellShield
	SpecialCellEagleEye
	SpecialCellPanacea
	SpecialCellMotivation
	SpecialCellHealingHeart
	SpecialCellKiller
)

const (
	trapDamage           = 10
	enthusiasmDmgPercent = 10
	shieldResPercent     = 10
	eagleEyeRangeBonus   = 1
	panaceaHealPercent   = 10
	motivationAPBonus    = 1
	healingHeartAmount   = 5
)

// specialCellKey identifies one battlefield cell by X/Y (special cells are
// a 2D concept -- altitude doesn't disambiguate them, matching the
// manual's plain grid description).
type specialCellKey struct{ X, Y int32 }

// activeCellBuff tracks a temporary characteristic bonus granted by a
// special cell, to be reverted at the end of the same fighter's turn
// (manual: "the effect...last[s] during the entire fighter's turn, from
// the beginning of his activation phase to the beginning of his next
// activation phase").
type activeCellBuff struct {
	FighterID int64
	Charc     CharacteristicType
	Delta     int32
}

// SetSpecialCell registers a special-cell type at (x,y) for this fight.
// Exposed for callers (map-data loading, or tests) to populate the
// battlefield; a Fight with no cells registered behaves exactly as if the
// mechanic didn't exist.
func (f *Fight) SetSpecialCell(x, y int32, cellType SpecialCellType) {
	if f.specialCells == nil {
		f.specialCells = make(map[specialCellKey]SpecialCellType)
	}
	f.specialCells[specialCellKey{X: x, Y: y}] = cellType
}

// SpecialCellRender is one special cell's client-facing render descriptor,
// matching the CREATE_FIGHT specialCell wire tuple
// (docs/opcodes/07-fight-lifecycle.md): CellBaseID is the client's
// StaticEffectAreaManager template id (SPECIAL 1002-1009 / TRAP 1-2), CellID
// is a per-fight unique instance id, and X/Y/Z is the placement.
type SpecialCellRender struct {
	CellBaseID int64
	CellID     int64
	X, Y       int32
	Z          int16
}

// AddSpecialCellRender records a client render descriptor for a special cell
// (called alongside SetSpecialCell by the dispatch layer). CellID is
// auto-assigned as a stable per-fight sequence so the client can key each
// placed cell distinctly. Purely presentational -- the gameplay effect is
// driven by SetSpecialCell, not by this list.
func (f *Fight) AddSpecialCellRender(cellBaseID int64, x, y int32, z int16) {
	f.specialCellRenders = append(f.specialCellRenders, SpecialCellRender{
		CellBaseID: cellBaseID,
		CellID:     int64(len(f.specialCellRenders) + 1),
		X:          x,
		Y:          y,
		Z:          z,
	})
}

// SpecialCellRenders returns the client render descriptors for this fight's
// special cells, for CREATE_FIGHT serialization. Returns nil if none.
func (f *Fight) SpecialCellRenders() []SpecialCellRender {
	return f.specialCellRenders
}

func (f *Fight) specialCellAt(pos Point3) SpecialCellType {
	if f.specialCells == nil {
		return SpecialCellNone
	}
	return f.specialCells[specialCellKey{X: pos.X, Y: pos.Y}]
}

// specialCellRenderIDAt returns the client-side instance id (the CREATE_FIGHT
// cellId) of the special cell at pos, or 0 if none was registered there. This
// is the id the client keyed the live EffectArea by
// (StaticEffectAreaManager.instanceAnother(cellId,...) -> addEffectArea), so
// it's what an EFFECT_AREA_ACTION(6200) must reference to animate that tile
// (see broadcastSpecialCellAnimation).
func (f *Fight) specialCellRenderIDAt(pos Point3) int64 {
	for _, r := range f.specialCellRenders {
		if r.X == pos.X && r.Y == pos.Y {
			return r.CellID
		}
	}
	return 0
}

// broadcastSpecialCellAnimation fires the EFFECT_AREA_ACTION(6200) that makes
// the client play the special tile's own animation when `fighter` starts
// their turn on it. The client resolves the live area by the CREATE_FIGHT
// cellId (areaId) and runs its staticEffects.dat scriptId
// (EffectAreaAction.run -> EffectArea.apply -> scripted animation); the
// client-side EffectArea.execute() is empty, so this is PURELY cosmetic and
// never double-applies the gameplay effect (HP/buffs stay server-authoritative
// via the RUNNING_EFFECT_ACTION the effect switch broadcasts). No-op when the
// cell has no render descriptor (e.g. a test that called SetSpecialCell
// without AddSpecialCellRender). Mirrors effectarea.go's applyEffectArea,
// which likewise pairs a 6200 with the effect's running-effect frame.
func (f *Fight) broadcastSpecialCellAnimation(fighter *Fighter) {
	areaID := f.specialCellRenderIDAt(fighter.Position)
	if areaID == 0 {
		return
	}
	f.broadcastAll(buildEffectAreaAction(f.nextActionID(), noTriggeringAction, true, areaID, fighter.ID))
}

// applyTurnStartSpecialCell runs whichever special-cell effect (if any)
// applies to fighter at the start of their own turn, per the manual's
// exact effect table. Returns true if the fighter died from a killer
// cell (in which case the caller must not proceed with a normal turn --
// see startNextTurn's use of this).
func (f *Fight) applyTurnStartSpecialCell(fighter *Fighter, triggeringActionID int32) (died bool) {
	cellType := f.specialCellAt(fighter.Position)
	if cellType == SpecialCellNone {
		return false
	}

	// Play the tile's own animation (EFFECT_AREA_ACTION 6200) before the
	// gameplay effect's running-effect frame, so the client shows the cell
	// activating. Purely cosmetic + server-authoritative -- see
	// broadcastSpecialCellAnimation.
	f.broadcastSpecialCellAnimation(fighter)

	switch cellType {
	case SpecialCellKiller:
		// "If one of your fighters starts his activation on a killer
		// cell, he is automatically killed." Insatiable/always active --
		// no resistance/save applies.
		f.killFighter(fighter, triggeringActionID)
		return true

	case SpecialCellTrap:
		f.applyDamage(fighter, trapDamage, triggeringActionID)
		return fighter.IsDead

	case SpecialCellHealingHeart:
		// "An injured fighter gains 5 HPs" -- only if actually injured
		// (below max), matching the manual's wording exactly.
		if fighter.Characteristic(HP) < fighter.Characteristics[HP].Max {
			// Broadcast the ACTUAL HP restored (post-clamp) so the client's
			// HP bar can't overshoot its max when the fighter is within 5 of
			// full (see AddCharacteristicClamped / the EffectHPGain note).
			applied := fighter.AddCharacteristicClamped(HP, healingHeartAmount)
			// Special-cell effects have no spell/card owner, so only the
			// ActionID is set (no GenericEffectID/container) -- the client
			// renders the HP-gain number but no persistent buff icon, which
			// is correct for a one-shot heal cell.
			f.broadcastRunningEffect(gamedata.EffectDef{ActionID: runningEffectHPGain}, fighter, fighter, applied, triggeringActionID)
		}
		return false

	case SpecialCellEnthusiasm:
		f.grantTurnCellBuff(fighter, DmgInPercent, enthusiasmDmgPercent, triggeringActionID)
	case SpecialCellShield:
		f.grantTurnCellBuff(fighter, ResInPercent, shieldResPercent, triggeringActionID)
	case SpecialCellEagleEye:
		f.grantTurnCellBuff(fighter, Range, eagleEyeRangeBonus, triggeringActionID)
	case SpecialCellPanacea:
		f.grantTurnCellBuff(fighter, Heal, panaceaHealPercent, triggeringActionID)
	case SpecialCellMotivation:
		f.grantTurnCellBuff(fighter, AP, motivationAPBonus, triggeringActionID)
	}
	return false
}

// grantTurnCellBuff applies delta to fighter's charc as a real buff (both
// Max and current value raised, mirroring CharacBuff's semantics -- see
// effects.go's EffectCharacBuff case) and records it for reversal at
// end-of-turn. This matters specifically for AP/MP-capped
// characteristics: raising only the current value (a plain Add) would be
// silently clamped back down to the old Max by Characteristic.Add's own
// clamp, so a "+1 AP" cell would otherwise have no effect at all.
func (f *Fight) grantTurnCellBuff(fighter *Fighter, charc CharacteristicType, delta int32, triggeringActionID int32) {
	c := fighter.Characteristics[charc]
	if c == nil {
		return
	}
	// Mirrors CharacBuff.execute() exactly: updateMaxValue(delta) THEN
	// add(delta) as two separate calls (AddMax's own clamp only ever
	// pulls Value down if it would exceed the new Max, it never raises
	// Value on its own -- the current-value increase is a distinct step).
	c.AddMax(delta)
	c.Add(delta)
	f.activeCellBuffs = append(f.activeCellBuffs, activeCellBuff{FighterID: fighter.ID, Charc: charc, Delta: delta})
	// Special-cell buff: no spell container (engine-internal), so only the
	// characteristic-boost ActionID is set. The stat change is applied and
	// reverted server-side (end-of-turn), so a persistent client icon isn't
	// required -- the client just animates the boost number.
	f.broadcastRunningEffect(gamedata.EffectDef{ActionID: charcRunningEffectID(charc)}, fighter, fighter, delta, triggeringActionID)
}

// revertTurnEndSpecialCellBuffs undoes every still-active special-cell
// buff granted to fighter this turn, called from askForFighterEndTurn.
// Mirrors CharacBuff.unapply() exactly: only updateMaxValue(-delta) is
// called -- current value is deliberately NOT separately subtracted (it
// only drops if AddMax's own "value can't exceed max" clamp pulls it
// down), so a fighter who has since spent the bonus resource doesn't get
// double-charged when the buff naturally expires.
func (f *Fight) revertTurnEndSpecialCellBuffs(fighter *Fighter) {
	if len(f.activeCellBuffs) == 0 {
		return
	}
	remaining := f.activeCellBuffs[:0]
	for _, buff := range f.activeCellBuffs {
		if buff.FighterID == fighter.ID {
			if c := fighter.Characteristics[buff.Charc]; c != nil {
				c.AddMax(-buff.Delta)
			}
			continue
		}
		remaining = append(remaining, buff)
	}
	f.activeCellBuffs = remaining
}
