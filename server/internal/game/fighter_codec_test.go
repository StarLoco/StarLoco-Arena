package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestFighterBlobRoundTrip encodes a fighter to an et_2 blob and decodes it
// back, verifying the wire layout is symmetric for the fields the server uses.
func TestFighterBlobRoundTrip(t *testing.T) {
	f := &domain.Fighter{
		ID:      7,
		BreedID: 5,
		Name:    "Warrior",
		Sex:     1,
		Hair:    3,
		Skin:    4,
		Eye:     2,
		Budget:  650,
		Spells: []domain.FighterSpell{
			{SpellID: 101}, {SpellID: 202}, {SpellID: 303},
		},
		Objects: []domain.FighterObject{
			{TemplateID: 5001, Slot: 0},
			{TemplateID: 5002, Slot: 1},
		},
	}

	blob := encodeFighterBlob(f)
	got, err := decodeFighterBlob(blob)
	if err != nil {
		t.Fatalf("decode: %v", err)
	}

	if got.BreedID != f.BreedID {
		t.Errorf("breed = %d, want %d", got.BreedID, f.BreedID)
	}
	if got.Name != f.Name {
		t.Errorf("name = %q, want %q", got.Name, f.Name)
	}
	if got.Sex != f.Sex {
		t.Errorf("sex = %d, want %d", got.Sex, f.Sex)
	}
	if got.Hair != 3 || got.Skin != 4 || got.Eye != 2 {
		t.Errorf("colors = (%d,%d,%d), want (3,4,2)", got.Hair, got.Skin, got.Eye)
	}
	if len(got.SpellIDs) != 3 || got.SpellIDs[0] != 101 || got.SpellIDs[2] != 303 {
		t.Errorf("spells = %v, want [101 202 303]", got.SpellIDs)
	}
	if len(got.Cards) != 2 || got.Cards[0].ID != 5001 || got.Cards[1].Slot != 1 {
		t.Errorf("cards = %+v, want 2 entries", got.Cards)
	}
	if got.Budget != 650 {
		t.Errorf("budget = %d, want 650", got.Budget)
	}
}

// TestBuildFighterValidation checks name/breed sanitation and spell cap.
func TestBuildFighterValidation(t *testing.T) {
	s := &Session{deps: &Deps{}} // no gamedata -> budget = breed base only
	fb := &FighterBlob{
		BreedID:  99, // invalid -> clamped to minBreedID
		Name:     "  ThisNameIsWayTooLongToKeep  ",
		Sex:      1,
		SpellIDs: []int32{1, 1, 2, 3, 4, 5, 6, 7, 8}, // dup + over cap
	}
	f := s.buildFighter(42, fb)

	if f.BreedID != minBreedID {
		t.Errorf("breed = %d, want clamped to %d", f.BreedID, minBreedID)
	}
	if len(f.Name) > maxFighterNameLen {
		t.Errorf("name %q longer than %d", f.Name, maxFighterNameLen)
	}
	if len(f.Spells) > maxFighterSpells {
		t.Errorf("spells = %d, want <= %d", len(f.Spells), maxFighterSpells)
	}
	if f.Budget != breedBaseValue {
		t.Errorf("budget = %d, want %d (no cards, no gamedata)", f.Budget, breedBaseValue)
	}
}

// TestFighterBudgetUsesTheFighterCardTable is the regression guard for the
// equipment-valuation bug: a fighter's objects are FIGHTER cards (type 250), so
// their value must come from that table and not from the coach-card table, whose
// ids overlap almost completely with entirely different prices.
func TestFighterBudgetUsesTheFighterCardTable(t *testing.T) {
	s := &Session{deps: &Deps{
		// Same id in both tables, deliberately different values.
		FighterCards: gamedata.NewFighterCards(&gamedata.FighterCard{ID: 85, Value: 200}),
		Cards:        gamedata.NewCards(&gamedata.CoachCard{ID: 85, Value: 18200}),
	}}
	got := s.computeLoadoutBudget([]domain.FighterObject{{TemplateID: 85}})
	if want := int16(breedBaseValue + 200); got != want {
		t.Errorf("loadout budget = %d, want %d (breed base + the FIGHTER card value)", got, want)
	}
	f := &domain.Fighter{Objects: []domain.FighterObject{{TemplateID: 85}}}
	if got := s.computeFighterBudget(f); got != int16(breedBaseValue+200) {
		t.Errorf("fighter budget = %d, want %d", got, breedBaseValue+200)
	}
}
