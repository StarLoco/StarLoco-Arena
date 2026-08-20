package game

import (
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// The four remaining modelled effect kinds from ROADMAP item 28. Each one was
// checked against the client before being written; the three that stayed
// unimplemented (68, 170, 171 -> KindVisual) and the one that is dead code (172)
// are documented in gamedata/effectkind.go.

// applySelfPush resolves 153 "Est repoussé de sa cible": the CASTER recoils away
// from the fighter it targeted.
//
// The client class `azw_0` is `na_2` (push) with `bWl`/`bWm` swapped — the same
// stability guard, the same ≤2 altitude step, the same collision damage to both
// parties, even the same "un Push" text in its pool errors. So this reuses the
// shared shove, with the roles reversed: the mover is the caster, the anchor it
// is repelled from is the target, and the effect's target slot names that anchor
// (the client moves `bWl`, i.e. the effect's caster).
func (f *Fight) applySelfPush(caster *FightFighter, ef gamedata.Effect, target Pos) {
	anchor := f.fighterAtCell(target)
	if anchor == nil || anchor == caster {
		return
	}
	if caster.hasState(stateStabilized) {
		return // the same `avx_0.dev` guard push honours — here it pins the CASTER
	}
	distance := ef.Roll(f.rngSource())
	if distance <= 0 {
		return
	}
	dx, dy := cardinalStep(caster.Pos.X-anchor.Pos.X, caster.Pos.Y-anchor.Pos.Y)
	if dx == 0 && dy == 0 {
		return
	}
	f.shoveFighter(caster, caster, anchor.WireID, ef, dx, dy, distance, nil)
}

// applyRevealInvisible resolves 84 "Révéler l'invisible": every fighter in the
// area loses invisibility.
//
// The client does its own half (`aum` walks the target's running effects and
// expires each `co_0`, reverting the +300 dodge / −300 block swing and re-showing
// the sprite), but it only does so for fighters it is told about — and the server
// must drop its OWN stateInvisible or targeting and the AI keep treating a
// revealed fighter as hidden.
func (f *Fight) applyRevealInvisible(caster *FightFighter, ef gamedata.Effect, cell Pos) {
	victim := f.fighterAtCell(cell)
	if victim == nil || !victim.hasState(stateInvisible) {
		return // nothing to reveal: the client would no-op too
	}
	delete(victim.States, stateInvisible)
	delete(victim.stateSrc, stateInvisible)
	eff, _ := buildRunningEffect(f.nextActionUID(), ef.ActionID, ef.EffectID,
		caster.WireID, victim.WireID, victim.Pos, 0, 0, false,
		sourceSpellPart(f.sourceSpellID))
	f.broadcast(eff)
}

// applySpellCooldown resolves 140 "Diminution du cooldown d'un sort": the spell
// that cast this effect becomes recastable in params[0] table-turns.
//
// Client semantics, from `sH.a(fv, turn, r)` (the only per-spell cooldown mutator
// in the whole client, reached only from this action): the change applies ONLY if
// the spell is currently on cooldown, and only if that shortens the wait — an
// infinite (63, "once per fight") cooldown counts as longer than anything, so it
// too collapses to now+r. The spell is the one named in blob part 4, which for a
// server-driven cast is simply the spell being resolved.
func (f *Fight) applySpellCooldown(caster *FightFighter, ef gamedata.Effect) {
	if f.sourceSpellID == 0 {
		return
	}
	turns := ef.Roll(f.rngSource())
	if turns < 0 {
		return
	}
	if f.deps == nil || f.deps.Spells == nil {
		return
	}
	sp := f.deps.Spells.Get(f.sourceSpellID)
	if sp == nil {
		return
	}
	caster.CastHistory.recastAfter(sp.LimitKeyID(), sp.Cooldown, turns, f.tableTurn)
}

// applyCurseBonusCells resolves 150 "Inverse les effets des cases bonus": the
// bonus tile on the target cell turns against whoever stands on it.
//
// Wholly server-side. The client's counterpart (`aas_1`) sets `yl_1.aDR` via
// `aX(true)`, but its getter `FI()` has no caller anywhere in the client and the
// EffectArea execute body is empty — so nothing client-side reads it. Since the
// special-cell effects are applied server-side (specialcells.go), the inversion
// has to be too.
func (f *Fight) applyCurseBonusCells(caster *FightFighter, ef gamedata.Effect, target Pos) {
	turns, _ := ef.DurationTurns()
	if turns <= 0 {
		turns = 1
	}
	if f.cursedCells == nil {
		f.cursedCells = make(map[[2]int32]int32)
	}
	key := [2]int32{target.X, target.Y}
	if until := f.tableTurn + turns; until > f.cursedCells[key] {
		f.cursedCells[key] = until
	}
	eff, _ := buildRunningEffect(f.nextActionUID(), ef.ActionID, ef.EffectID,
		caster.WireID, caster.WireID, target, turns, 0, false,
		sourceSpellPart(f.sourceSpellID))
	f.broadcast(eff)
}

// applySpellReturn resolves 88 "Renvoi de sort": the next damaging spell aimed at
// the target is sent back at whoever cast it.
//
// The client models this as a TRIGGER buff (`amv_1`): on an incoming HP-loss
// whose source is a spell, and with the effect's value > 0, it calls
// `xb_22.h(xb_22.ajQ())` — it simply re-points the damage at its own caster.
// Because the server resolves damage itself, the same redirect has to happen
// here; the shared piece is that both sides consume it on one hit.
func (f *Fight) applySpellReturn(caster *FightFighter, ef gamedata.Effect, cell Pos) {
	victim := f.fighterAtCell(cell)
	if victim == nil {
		return
	}
	pct := ef.Roll(f.rngSource())
	if pct <= 0 {
		return // r == 0 is the client's own "did not proc" gate
	}
	turns, _ := ef.DurationTurns()
	if turns <= 0 {
		turns = 1
	}
	victim.spellReturn = true
	eff, _ := buildRunningEffect(f.nextActionUID(), ef.ActionID, ef.EffectID,
		caster.WireID, victim.WireID, victim.Pos, pct, 0, false,
		sourceSpellPart(f.sourceSpellID))
	f.broadcast(eff)
}

// consumeSpellReturn reports whether this fighter's 88 buff fires on the hit
// being resolved, clearing it in the same breath. One shot: `amv_1` re-points a
// single incoming effect, and leaving it armed would make the fighter permanently
// immune to spell damage.
func (f *Fight) consumeSpellReturn(victim *FightFighter) bool {
	if victim == nil || !victim.spellReturn {
		return false
	}
	victim.spellReturn = false
	return true
}

// cellIsCursed reports whether a 150 curse is still live on (x,y).
func (f *Fight) cellIsCursed(x, y int32) bool {
	if f.cursedCells == nil {
		return false
	}
	return f.cursedCells[[2]int32{x, y}] > f.tableTurn
}
