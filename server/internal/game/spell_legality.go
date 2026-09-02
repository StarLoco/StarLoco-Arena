package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
)

// spellLegalForBreed reports whether a fighter of breedID may KNOW spellID as
// part of a client-authored loadout.
//
// SECURITY: opcode 6011 lets the client write a fighter's spell list, and
// SaveLoadout validates only WHOSE fighter it is, never WHAT spells. Casting is
// gated on fighterKnowsSpell, which reads that same list - so the mitigation the
// combat code believes it has was anchored on attacker-controlled data. A forged
// loadout let any fighter cast any of the 203 spells, including boss and
// cross-breed ones, bypassing breed and Sphere-Board progression entirely. It
// persists, so it survived reconnect. Note the asymmetry this closes: the same
// handler already validated CARDS (canonicalEquipSlots + entitledEquip).
//
// The rule is own-breed only, which the data supports cleanly: breeds 1..14 each
// own 10-13 spells. The two pseudo-breeds are deliberately excluded -
//
//	breed 0  (44 spells) includes monster/summon material such as id 428
//	         (range 1-30, value 250), 429 (value 300) and 417 (value 300)
//	breed 99 (14 spells) is boss/utility (all value 0, range 0-8)
//
// - because neither belongs in a player's loadout.
//
// Legitimate extra spells are unaffected because they are added SERVER-side after
// this filter: sphere unlocks via fighterWithSphereSpells, summons and challenge
// demons via SummonSpellID. Nothing legitimate reaches a fighter's spell list
// through the client.
func spellLegalForBreed(deps *Deps, breedID uint8, spellID int32) bool {
	if deps == nil || deps.Spells == nil {
		// No spell data loaded (data-less test/dev build): fall back to permitting,
		// because refusing every spell would break fights entirely and this is a
		// hardening filter rather than a correctness invariant.
		return true
	}
	sp := deps.Spells.Get(spellID)
	if sp == nil {
		return false // unknown id: the client could not resolve it either
	}
	return sp.BreedID == int32(breedID)
}

// filterLoadoutSpells drops spells a fighter of breedID may not know, preserving
// order and re-numbering slots so the stored loadout stays contiguous.
//
// Dropping rather than rejecting the whole frame is deliberate: the retail client
// only ever offers legal spells, so a mix means a modified client, and silently
// keeping the legal part leaves the player's fighter usable instead of stuck.
func filterLoadoutSpells(deps *Deps, breedID uint8, in []domain.FighterSpell) []domain.FighterSpell {
	out := in[:0:0]
	seen := make(map[int32]bool, len(in))
	for _, sp := range in {
		if seen[sp.SpellID] || !spellLegalForBreed(deps, breedID, sp.SpellID) {
			continue
		}
		seen[sp.SpellID] = true
		sp.Slot = int16(len(out))
		out = append(out, sp)
	}
	return out
}

// filterLoadoutSpellIDs is the same filter for the raw-id form used by the
// fighter CREATE path, which builds its spell list from FighterBlob.SpellIDs.
func filterLoadoutSpellIDs(deps *Deps, breedID uint8, ids []int32) []int32 {
	out := ids[:0:0]
	seen := make(map[int32]bool, len(ids))
	for _, id := range ids {
		if seen[id] || !spellLegalForBreed(deps, breedID, id) {
			continue
		}
		seen[id] = true
		out = append(out, id)
	}
	return out
}
