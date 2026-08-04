package game

// spellCastHistory tracks per-spell cast-frequency state for a single fighter so
// castSpellByFighter can enforce a spell's cooldown / CastMaxPerTurn /
// CastMaxPerTarget limits (gamedata fields 8/9/7). Ported from the v2.04b server's
// byte-for-byte port of the decompiled SpellCastHistory.java (three parallel maps
// collapsed into one record per spell, keyed by spellID).
//
// Semantics (all confirmed by the reference's own unit tests):
//   - cooldown: min table-turns (rounds) between two casts of the spell by
//     this fighter; the first cast is always allowed; 63 = "never again this
//     fight" (the same ≥63-is-infinite convention used for durations).
//   - CastMaxPerTurn: max casts of the spell in one of the fighter's turns.
//   - CastMaxPerTarget: max casts of the spell on ONE target this turn (skipped
//     for a bare-cell cast with no single-fighter target).
//   - A zero limit is unconstrained.

// spellCastRecord is one spell's per-fighter cast-frequency state.
type spellCastRecord struct {
	lastCastTableTurn     int32
	hasLastCast           bool
	castsThisTurn         int32
	castsThisTurnByTarget map[int64]int32 // targetWireID -> count, this table-turn only
}

// spellCastHistory is the per-fighter cast-frequency tracker. The zero value is
// ready to use (maps are created lazily).
type spellCastHistory struct {
	records map[int32]*spellCastRecord // spellID -> record
}

func (h *spellCastHistory) ensure(spellID int32) *spellCastRecord {
	if h.records == nil {
		h.records = make(map[int32]*spellCastRecord)
	}
	r, ok := h.records[spellID]
	if !ok {
		r = &spellCastRecord{castsThisTurnByTarget: make(map[int64]int32)}
		h.records[spellID] = r
	}
	return r
}

// onNewTurn clears every per-turn counter (castsThisTurn / castsThisTurnByTarget),
// called once for the fighter at the start of its own turn. lastCastTableTurn is
// deliberately NOT cleared — cooldown compares against it across turns.
func (h *spellCastHistory) onNewTurn() {
	for _, r := range h.records {
		r.castsThisTurn = 0
		for k := range r.castsThisTurnByTarget {
			delete(r.castsThisTurnByTarget, k)
		}
	}
}

// canCast reports whether the spell may be cast now under its frequency limits.
// hasTarget/targetWireID identify the single-fighter target (skipped when false).
func (h *spellCastHistory) canCast(spellID int32, cooldown, castMaxPerTurn, castMaxPerTarget uint8, currentTableTurn int32, targetWireID int64, hasTarget bool) bool {
	if h.records == nil {
		return true
	}
	r, ok := h.records[spellID]
	if !ok {
		return true
	}
	if cooldown > 0 && r.hasLastCast {
		if cooldown == 63 || currentTableTurn-r.lastCastTableTurn < int32(cooldown) {
			return false // last cast too recent (63 = never again this fight)
		}
	}
	if castMaxPerTurn > 0 && r.castsThisTurn >= int32(castMaxPerTurn) {
		return false
	}
	if hasTarget && castMaxPerTarget > 0 && r.castsThisTurnByTarget[targetWireID] >= int32(castMaxPerTarget) {
		return false
	}
	return true
}

// storeCast records a successful cast; call AFTER canCast passed and the cast is
// committed.
func (h *spellCastHistory) storeCast(spellID int32, cooldown, castMaxPerTurn, castMaxPerTarget uint8, currentTableTurn int32, targetWireID int64, hasTarget bool) {
	r := h.ensure(spellID)
	if cooldown > 0 {
		r.lastCastTableTurn = currentTableTurn
		r.hasLastCast = true
	}
	if castMaxPerTurn > 0 {
		r.castsThisTurn++
	}
	if hasTarget && castMaxPerTarget > 0 {
		r.castsThisTurnByTarget[targetWireID]++
	}
}
