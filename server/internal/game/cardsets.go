package game

// cardsets.go applies card-SET bonuses ("panoplies", gamedata type 101).
//
// The client's rule, from sj_1's aggregated-bonus builder:
//
//	count the coach's EQUIPPED cards per set id
//	for each effect of each such set: if effect.Threshold <= count, it applies
//	several sources of the same action id are SUMMED
//
// Set effects use the client's AI enum — the coach META layer (XP, wounds,
// morale, fatigue, drops, reputation, resurrection), not in-fight combat stats.
// Of those, resurrection is currently the only one this server implements, so it
// is the only one wired up; the rest are decoded and waiting for their mechanic
// (see docs/DATA-COVERAGE.md).

// AI-enum action ids carried by coach-card and card-set effects. Only the ones we
// act on are named here; the full list is in the client's `AI` enum.
const (
	aiActionResurrect int32 = 13 // "x% chance to resurrect a fighter that just died"
)

// setBonusFor returns the summed magnitude of every unlocked set effect with the
// given AI action id, across all sets the coach has cards of. Returns 0 when the
// catalogues are absent, so a server without data files behaves as before.
//
// "Unlocked" means the coach has at least the effect's threshold worth of that
// set's cards EQUIPPED (Pos >= 1) — the same set the client counts.
func (s *Session) setBonusFor(action int32) int32 {
	if s == nil || s.Coach == nil || s.deps == nil {
		return 0
	}
	if s.deps.CardSets == nil || s.deps.Cards == nil {
		return 0
	}
	equipped := s.equippedCountsPerSet()
	var total int32
	for setID, count := range equipped {
		set := s.deps.CardSets.Get(setID)
		if set == nil {
			continue
		}
		for _, ef := range set.ActiveEffects(count) {
			if ef.Action == action && len(ef.Params) > 0 {
				total += ef.Params[0]
			}
		}
	}
	return total
}

// equippedCountsPerSet counts the coach's equipped cards by their set id. A card
// with no set (CardSet 0) is skipped.
func (s *Session) equippedCountsPerSet() map[int32]int {
	out := map[int32]int{}
	for _, inv := range s.Coach.Inventory {
		if inv.Pos < 1 {
			continue // not equipped
		}
		tmpl := s.deps.Cards.Get(inv.TemplateID)
		if tmpl == nil || tmpl.CardSet == 0 {
			continue
		}
		out[tmpl.CardSet]++
	}
	return out
}
