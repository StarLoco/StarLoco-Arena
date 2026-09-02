package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func spellDeps(t *testing.T) *Deps {
	t.Helper()
	return &Deps{Spells: gamedata.NewSpells(
		&gamedata.Spell{ID: 10, BreedID: 1},
		&gamedata.Spell{ID: 11, BreedID: 1},
		&gamedata.Spell{ID: 20, BreedID: 2},
		&gamedata.Spell{ID: 428, BreedID: 0},  // monster/summon material
		&gamedata.Spell{ID: 193, BreedID: 99}, // boss/utility
	)}
}

// TestSpellLoadoutLegality is the SECURITY regression for the 6011 spell hole.
//
// castSpellByFighter gates casting on fighterKnowsSpell, which reads the list the
// CLIENT writes through 6011 - so the mitigation combat believes it has was
// anchored on attacker-controlled data. A forged loadout let any fighter cast any
// of the 203 spells, and it persisted across reconnect.
func TestSpellLoadoutLegality(t *testing.T) {
	d := spellDeps(t)

	cases := []struct {
		name    string
		breed   uint8
		spellID int32
		want    bool
	}{
		{"own breed", 1, 10, true},
		{"own breed, second spell", 1, 11, true},
		{"ANOTHER breed's spell", 1, 20, false},
		{"pseudo-breed 0 (monster/summon material)", 1, 428, false},
		{"pseudo-breed 99 (boss/utility)", 1, 193, false},
		{"unknown spell id", 1, 9999, false},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			if got := spellLegalForBreed(d, tc.breed, tc.spellID); got != tc.want {
				t.Errorf("spellLegalForBreed(breed=%d, spell=%d) = %v, want %v",
					tc.breed, tc.spellID, got, tc.want)
			}
		})
	}
}

// TestFilterLoadoutSpellsDropsIllegalAndRenumbers pins the filter's behaviour:
// keep the legal part (so a modified client leaves a usable fighter rather than a
// broken one), drop the rest, and keep slots contiguous.
func TestFilterLoadoutSpellsDropsIllegalAndRenumbers(t *testing.T) {
	d := spellDeps(t)
	in := []domain.FighterSpell{
		{SpellID: 20, Slot: 0},  // another breed - dropped
		{SpellID: 10, Slot: 1},  // legal
		{SpellID: 428, Slot: 2}, // breed 0 - dropped
		{SpellID: 11, Slot: 3},  // legal
		{SpellID: 10, Slot: 4},  // duplicate - dropped
	}
	got := filterLoadoutSpells(d, 1, in)
	if len(got) != 2 {
		t.Fatalf("kept %d spells, want 2 (ids %v)", len(got), got)
	}
	if got[0].SpellID != 10 || got[1].SpellID != 11 {
		t.Errorf("kept %v, want spells 10 and 11 in order", got)
	}
	for i, sp := range got {
		if sp.Slot != int16(i) {
			t.Errorf("spell %d has slot %d, want %d - slots must stay contiguous",
				sp.SpellID, sp.Slot, i)
		}
	}
}

// TestFilterIsPermissiveWithoutSpellData records the deliberate fallback: a build
// with no spell data must not lose every spell, because this is hardening rather
// than a correctness invariant and refusing everything would break all fights.
func TestFilterIsPermissiveWithoutSpellData(t *testing.T) {
	in := []domain.FighterSpell{{SpellID: 10}, {SpellID: 20}}
	if got := filterLoadoutSpells(&Deps{}, 1, in); len(got) != 2 {
		t.Errorf("kept %d of %d spells with no spell data loaded; want all", len(got), len(in))
	}
	if got := filterLoadoutSpells(nil, 1, in); len(got) != 2 {
		t.Errorf("nil deps dropped spells; want all")
	}
}

// TestSphereSpellsBypassTheClientFilter documents why this filter is safe: the
// legitimate ways a fighter gains a non-breed spell all run SERVER-side, after
// the client's list has been filtered.
func TestSphereSpellsBypassTheClientFilter(t *testing.T) {
	// sphereSpellIDs is fed from the stored fighter's bought nodes, never from a
	// request, so a sphere unlock is unaffected by breed legality.
	f := &domain.Fighter{ID: 1, BreedID: 1}
	if got := sphereSpellIDs(f, nil); len(got) != 0 {
		t.Fatalf("fixture expectation wrong: %v", got)
	}
	// The point is structural: filterLoadoutSpells is only ever applied to
	// client-decoded blobs (6001, 6011), and fighterWithSphereSpells appends
	// afterwards. If that ever changes, sphere spells would start being filtered
	// out and this comment is where to look.
}
