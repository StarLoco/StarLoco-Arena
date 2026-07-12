package dispatch

import (
	"testing"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/gamedata"
)

// newTestLoadoutStore builds a *gamedata.Store seeded with both spells and
// fighter cards so the loadout validators can be exercised without real
// data files.
func newTestLoadoutStore(spells map[int32]gamedata.SpellTemplate, cards map[int32]gamedata.FighterCardTemplate) *gamedata.Store {
	return &gamedata.Store{
		Spells: gamedata.NewRepository(func() (map[int32]gamedata.SpellTemplate, error) {
			return spells, nil
		}),
		FighterCards: gamedata.NewRepository(func() (map[int32]gamedata.FighterCardTemplate, error) {
			return cards, nil
		}),
	}
}

func idset(ids []int32) map[int32]bool {
	m := make(map[int32]bool, len(ids))
	for _, id := range ids {
		m[id] = true
	}
	return m
}

func TestValidateFighterSpells_DropsWrongBreedUnknownAndDuplicates(t *testing.T) {
	const breed = 1
	store := newTestLoadoutStore(map[int32]gamedata.SpellTemplate{
		10: {ID: 10, BreedID: 1, Price: 100}, // ok
		11: {ID: 11, BreedID: 1, Price: 100}, // ok
		20: {ID: 20, BreedID: 2, Price: 100}, // wrong breed -> dropped
	}, nil)

	// Includes: a valid spell, a duplicate of it, an unknown id, and a
	// wrong-breed spell.
	got := validateFighterSpells(store, breed, []int32{10, 10, 999, 20, 11})
	set := idset(got)
	if !set[10] || !set[11] {
		t.Errorf("valid breed-1 spells 10,11 must survive, got %v", got)
	}
	if set[20] {
		t.Errorf("wrong-breed spell 20 must be dropped, got %v", got)
	}
	if set[999] {
		t.Errorf("unknown spell 999 must be dropped, got %v", got)
	}
	// No duplicate 10.
	count10 := 0
	for _, id := range got {
		if id == 10 {
			count10++
		}
	}
	if count10 != 1 {
		t.Errorf("spell 10 appears %d times, want 1 (no duplicates)", count10)
	}
}

func TestValidateFighterSpells_CapsAtSix(t *testing.T) {
	const breed = 1
	spells := map[int32]gamedata.SpellTemplate{}
	ids := []int32{}
	for i := int32(1); i <= 10; i++ {
		spells[i] = gamedata.SpellTemplate{ID: i, BreedID: 1}
		ids = append(ids, i)
	}
	store := newTestLoadoutStore(spells, nil)
	got := validateFighterSpells(store, breed, ids)
	if len(got) != maxFighterSpells {
		t.Errorf("got %d spells, want capped at %d", len(got), maxFighterSpells)
	}
}

func TestValidateFighterObjects_OneCardPerSlotAndValidType(t *testing.T) {
	store := newTestLoadoutStore(nil, map[int32]gamedata.FighterCardTemplate{
		9:  {ID: 9, Type: gamedata.FighterCardTypeWeapon},
		19: {ID: 19, Type: gamedata.FighterCardTypeWeapon}, // second weapon -> dropped
		93: {ID: 93, Type: gamedata.FighterCardTypePet},
		77: {ID: 77, Type: 99}, // invalid type -> dropped
	})
	got := validateFighterObjects(store, []int32{9, 19, 93, 77, 12345})
	set := idset(got)
	if !set[9] || !set[93] {
		t.Errorf("first weapon (9) and pet (93) must survive, got %v", got)
	}
	if set[19] {
		t.Errorf("second weapon (19) must be dropped (one card per slot), got %v", got)
	}
	if set[77] {
		t.Errorf("invalid-type card (77) must be dropped, got %v", got)
	}
	if set[12345] {
		t.Errorf("unknown card (12345) must be dropped, got %v", got)
	}
}

func TestComputeFighterBudget_SumsBreedSpellsCards(t *testing.T) {
	// Breed 1 (Feca) base value is 400 (see combat.breedTable).
	breedStats, ok := combat.GetBreedStats(1)
	if !ok {
		t.Fatal("breed 1 stats missing")
	}
	store := newTestLoadoutStore(
		map[int32]gamedata.SpellTemplate{
			10: {ID: 10, BreedID: 1, Price: 100},
			11: {ID: 11, BreedID: 1, Price: 300},
		},
		map[int32]gamedata.FighterCardTemplate{
			9: {ID: 9, Type: gamedata.FighterCardTypeWeapon, Value: 150},
		},
	)
	got := computeFighterBudget(store, 1, []int32{10, 11}, []int32{9})
	want := int16(breedStats.Value + 100 + 300 + 150)
	if got != want {
		t.Errorf("computeFighterBudget = %d, want %d (400 base + 100 + 300 + 150)", got, want)
	}
}

func TestComputeTeamValue_SumsBudgetsPlusBreedPenalty(t *testing.T) {
	// Distinct breeds: no per-breed surcharge, just the sum of budgets.
	distinct := []domain.Fighter{
		{Budget: 400, Breed: 1},
		{Budget: 500, Breed: 2},
	}
	if got := computeTeamValue(distinct); got != 900 {
		t.Errorf("distinct-breed team value = %d, want 900 (no surcharge)", got)
	}

	// Two of the same breed: surcharge loop runs for count=2 -> b1=1 only:
	// previousValue += previousValue + 1*100 => 100. Total = budgets + 100.
	dup2 := []domain.Fighter{
		{Budget: 400, Breed: 1},
		{Budget: 400, Breed: 1},
	}
	if got := computeTeamValue(dup2); got != 900 {
		t.Errorf("2-same-breed team value = %d, want 900 (800 + 100 surcharge)", got)
	}

	// Three of the same breed: count=3 -> b1=1: pv=100; b1=2: pv=pv+pv+200=400.
	// Total = budgets + 400.
	dup3 := []domain.Fighter{
		{Budget: 400, Breed: 1},
		{Budget: 400, Breed: 1},
		{Budget: 400, Breed: 1},
	}
	if got := computeTeamValue(dup3); got != 1600 {
		t.Errorf("3-same-breed team value = %d, want 1600 (1200 + 400 surcharge)", got)
	}
}

func TestComputeTeamValue_OverCapDetected(t *testing.T) {
	// A large roster (20 fighters at 400 each plus per-breed surcharges)
	// must exceed the 5000 cap -- the oversized-team a MITM could field.
	many := make([]domain.Fighter, 0, 20)
	for i := 0; i < 20; i++ {
		many = append(many, domain.Fighter{Budget: 400, Breed: byte(1 + i%12)})
	}
	if got := computeTeamValue(many); got <= MaxTeamValue {
		t.Errorf("20-fighter team value = %d, should exceed MaxTeamValue %d", got, MaxTeamValue)
	}
}
