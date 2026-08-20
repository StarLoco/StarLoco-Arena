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

	// recastFrom is an absolute table-turn override written by effect 140
	// ("diminution du cooldown d'un sort"): from this turn on the spell is
	// castable again regardless of its normal cooldown. Mirrors the client's
	// `sH.akS` map, which stores exactly this — an absolute expiry turn.
	recastFrom    int32
	hasRecastFrom bool
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
		// Once effect 140 has written an expiry it IS the cooldown, exactly as in
		// the client, where `sH.akS` holds one absolute expiry turn per spell and
		// the cast gate reads only that. Keeping the normal rule as a second
		// opinion would make the override unable to do anything but grant.
		if r.hasRecastFrom {
			if currentTableTurn < r.recastFrom {
				return false
			}
		} else if cooldown == 63 || currentTableTurn-r.lastCastTableTurn < int32(cooldown) {
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

// recastAfter implements effect 140: make the spell castable again `turns` table
// turns from now.
//
// It mirrors `sH.a(fv, turn, r)` exactly, including both of its guards: the spell
// must currently BE on cooldown (there is nothing to shorten otherwise), and the
// new expiry must actually be sooner than the old one — with an infinite (63)
// cooldown counting as later than any finite turn, which is the whole point of
// the effect on a once-per-fight spell.
func (h *spellCastHistory) recastAfter(spellID int32, cooldown uint8, turns, currentTableTurn int32) {
	if cooldown == 0 {
		return // not a cooldown spell: nothing to shorten
	}
	r, ok := h.records[spellID]
	if !ok || !r.hasLastCast {
		return // never cast, so not on cooldown
	}
	infinite := cooldown == 63
	expiry := r.lastCastTableTurn + int32(cooldown)
	if !infinite && currentTableTurn >= expiry {
		return // already off cooldown
	}
	if r.hasRecastFrom && r.recastFrom <= currentTableTurn {
		return // an earlier 140 already freed it
	}
	newExpiry := currentTableTurn + turns
	if !infinite && newExpiry >= expiry {
		return // would delay it, not shorten it
	}
	if r.hasRecastFrom && newExpiry >= r.recastFrom {
		return
	}
	r.recastFrom, r.hasRecastFrom = newExpiry, true
}

// storeCast records a successful cast; call AFTER canCast passed and the cast is
// committed.
func (h *spellCastHistory) storeCast(spellID int32, cooldown, castMaxPerTurn, castMaxPerTarget uint8, currentTableTurn int32, targetWireID int64, hasTarget bool) {
	r := h.ensure(spellID)
	if cooldown > 0 {
		r.lastCastTableTurn = currentTableTurn
		r.hasLastCast = true
		// A fresh cast re-arms the normal cooldown: any 140 discount belonged to
		// the PREVIOUS cast and must not carry over, or the spell would stay
		// permanently discounted.
		r.recastFrom, r.hasRecastFrom = 0, false
	}
	if castMaxPerTurn > 0 {
		r.castsThisTurn++
	}
	if hasTarget && castMaxPerTarget > 0 {
		r.castsThisTurnByTarget[targetWireID]++
	}
}
