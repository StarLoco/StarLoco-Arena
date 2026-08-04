package game

import "github.com/StarLoco/arena-2.70/internal/gamedata"

// carry.go implements the carry/throw mechanic (58 "Porter quelqu'un" / 59
// "Jeter quelqu'un"), a direct port of the v2.04b resolver: the caster picks up
// a target fighter (stacked onto the caster's cell, so it moves with it) and
// later throws it to a chosen cell. The relationship is a bidirectional link
// (CarriedFighter / CarriedByFighter). Whether an ally or an enemy may be
// carried, and the range, are decided by the spell's targeting — not here.

// applyCarry (58) makes the caster carry the fighter on `cell`. No-op if either
// fighter is already in a carry relationship, or the target is the caster.
func (f *Fight) applyCarry(caster *FightFighter, ef gamedata.Effect, cell Pos) {
	target := f.fighterAtCell(cell)
	if caster == nil || target == nil || caster == target {
		return
	}
	if caster.CarriedFighter != nil || caster.CarriedByFighter != nil {
		return
	}
	if target.CarriedFighter != nil || target.CarriedByFighter != nil {
		return
	}
	if target.hasState(stateAnchored) {
		return // "S'enraciner" (127 → property deA) blocks Jk carries
	}
	target.Pos = caster.Pos // stacked onto the carrier's cell (moves with it)
	caster.CarriedFighter = target
	target.CarriedByFighter = caster
	eff, _ := buildRunningEffect(f.nextActionUID(), ef.ActionID, ef.EffectID,
		caster.WireID, target.WireID, target.Pos, 0, 0, false)
	f.broadcast(eff)
}

// applyThrow (59) throws the caster's carried fighter to `cell`. No-op if the
// caster is carrying no one, or the destination is not a free arena cell. No
// landing damage is applied (matching the reference Throw).
func (f *Fight) applyThrow(caster *FightFighter, ef gamedata.Effect, cell Pos) {
	if caster == nil || caster.CarriedFighter == nil {
		return
	}
	carried := caster.CarriedFighter
	if !f.Arena().walkable(cell.X, cell.Y) || f.cellHeldByOther(cell, carried) {
		return
	}
	carried.Pos = Pos{X: cell.X, Y: cell.Y, Z: f.Arena().altitudeAt(cell.X, cell.Y)}
	carried.CarriedByFighter = nil
	caster.CarriedFighter = nil
	eff, _ := buildRunningEffect(f.nextActionUID(), ef.ActionID, ef.EffectID,
		caster.WireID, carried.WireID, carried.Pos, 0, 0, false)
	f.broadcast(eff)
}

// dismountIfCarried drops a carried fighter onto its carrier's cell and breaks
// the link, called before the fighter itself walks. Returns whether it happened.
func (f *Fight) dismountIfCarried(ff *FightFighter) bool {
	carrier := ff.CarriedByFighter
	if carrier == nil {
		return false
	}
	ff.Pos = carrier.Pos
	ff.CarriedByFighter = nil
	carrier.CarriedFighter = nil
	return true
}

// breakCarryLinks severs any carry relationship a (dying) fighter is part of,
// leaving its partner unharmed.
func (ff *FightFighter) breakCarryLinks() {
	if ff == nil {
		return
	}
	if ff.CarriedByFighter != nil {
		ff.CarriedByFighter.CarriedFighter = nil
		ff.CarriedByFighter = nil
	}
	if ff.CarriedFighter != nil {
		ff.CarriedFighter.CarriedByFighter = nil
		ff.CarriedFighter = nil
	}
}
