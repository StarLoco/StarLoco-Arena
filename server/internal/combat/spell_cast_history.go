package combat

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase L's
// first item: a byte-for-byte port of the decompiled
// dofusarena/common/game/spell/SpellCastHistory.java, tracking per-spell
// cast-frequency state so validateCast (combat_actions.go) can enforce
// MinCastInterval/CastMaxPerTurn/CastMaxPerTarget exactly like the
// reference client/server did.
//
// IMPORTANT NAMING NOTE (confirmed by cross-referencing
// SpellLoader.java/AbstractSpell.java/UsableSpell.java): the byte field
// named `CastFrequencyMaxPerPlayer` in this project's gamedata parser/
// templates (matching the .dat loader's own local-variable name,
// `spellCastFrequencyMaxPerPlayer`) is actually wired into
// AbstractSpell's constructor as `castMaxPerTarget` and stored/exposed as
// `m_castMaxPerTarget`/`getCastMaxPerTarget()` -- SpellCastHistory then
// enforces it as a genuine PER-TARGET cap (`m_spellsCastedThisTurnOnTarget`,
// keyed by spell+target), not a per-player-overall cap despite the
// loader's own field name. This is a real naming inconsistency in the
// original codebase (the .dat loader's local variable name is misleading),
// preserved here only as a source-level curiosity -- this Go port's
// gamedata.SpellTemplate.CastFrequencyMaxPerPlayer field is treated as the
// PER-TARGET cap semantically, matching actual enforced behavior, not the
// field's literal name.

// SpellCastValidity mirrors the decompiled SpellCastValidity enum's
// distinct rejection reasons (dofusarena/common/game/fight/
// SpellCastValidity.java) -- exposed so a future caller (e.g. a richer
// client-facing error message) can distinguish "why" a cast was rejected,
// though validateCast today only checks for == SpellCastValidityOK.
type SpellCastValidity int

const (
	SpellCastValidityOK SpellCastValidity = iota
	SpellCastValidityLastCastTooRecent
	SpellCastValidityTooManyCastsThisTurn
	SpellCastValidityTooManyCastsOnThisTarget
)

// spellCastRecord tracks one spell's cast-frequency state for a single
// fighter, mirroring SpellCastHistory's three parallel maps collapsed
// into one struct per spell (this port keys by spellID directly rather
// than replicating the reference's `HashMap<AbstractSpell,...>` identity-
// keyed maps, since spellID is already the natural unique key here).
type spellCastRecord struct {
	lastCastTableTurn     int32
	hasLastCast           bool
	castsThisTurn         int32
	castsThisTurnByTarget map[int64]int32 // targetFighterID -> count, this table-turn only
}

// SpellCastHistory is the per-fighter cast-frequency tracker, a direct
// port of SpellCastHistory.java's three-map design.
type SpellCastHistory struct {
	records map[int32]*spellCastRecord // spellID -> record
}

func (h *SpellCastHistory) ensure(spellID int32) *spellCastRecord {
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

// OnNewTurn clears every per-table-turn counter (castsThisTurn/
// castsThisTurnByTarget), mirroring SpellCastHistory.onNewTurn() exactly
// -- called once per fighter at the start of their own turn (does NOT
// clear lastCastTableTurn, which persists across turns for
// MinCastInterval to compare against).
func (h *SpellCastHistory) OnNewTurn() {
	for _, r := range h.records {
		r.castsThisTurn = 0
		for k := range r.castsThisTurnByTarget {
			delete(r.castsThisTurnByTarget, k)
		}
	}
}

// CanCastSpell mirrors SpellCastHistory.canCastSpell(spell, currentTableTurn, target)
// exactly: targetFighterID may be 0 for a spell with no single-fighter
// target (a bare-cell cast) -- the per-target check is simply skipped in
// that case, matching the reference's `target != null` guard.
func (h *SpellCastHistory) CanCastSpell(spellID int32, minCastInterval, castMaxPerTurn, castMaxPerTarget byte, currentTableTurn int32, targetFighterID int64, hasTarget bool) SpellCastValidity {
	if h.records == nil {
		return SpellCastValidityOK
	}
	r, ok := h.records[spellID]
	if !ok {
		return SpellCastValidityOK
	}

	if minCastInterval > 0 && r.hasLastCast {
		// minCastInterval==63 means "never again this fight", mirroring
		// TurnBasedTimeInterval's own >=63-means-infinite convention
		// (duration.go's isInfiniteDuration) reused here for the exact
		// same magic-number contract, confirmed independently in
		// SpellCastHistory.canCastSpell's own explicit `== 63` check.
		if minCastInterval == 63 || currentTableTurn-r.lastCastTableTurn < int32(minCastInterval) {
			return SpellCastValidityLastCastTooRecent
		}
	}

	if castMaxPerTurn > 0 && r.castsThisTurn >= int32(castMaxPerTurn) {
		return SpellCastValidityTooManyCastsThisTurn
	}

	if hasTarget && castMaxPerTarget > 0 {
		if r.castsThisTurnByTarget[targetFighterID] >= int32(castMaxPerTarget) {
			return SpellCastValidityTooManyCastsOnThisTarget
		}
	}

	return SpellCastValidityOK
}

// StoreSpellCast records a successful cast, mirroring
// SpellCastHistory.storeSpellCast(spell, currentTableTurn, target)
// exactly -- must be called AFTER a cast has been validated and is about
// to actually execute (see handleSpellCast).
func (h *SpellCastHistory) StoreSpellCast(spellID int32, minCastInterval, castMaxPerTurn, castMaxPerTarget byte, currentTableTurn int32, targetFighterID int64, hasTarget bool) {
	r := h.ensure(spellID)
	if minCastInterval > 0 {
		r.lastCastTableTurn = currentTableTurn
		r.hasLastCast = true
	}
	if castMaxPerTurn > 0 {
		r.castsThisTurn++
	}
	if hasTarget && castMaxPerTarget > 0 {
		r.castsThisTurnByTarget[targetFighterID]++
	}
}
