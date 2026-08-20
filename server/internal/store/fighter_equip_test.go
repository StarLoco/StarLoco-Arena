package store

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// slotByType is a stand-in for the real card table: id/10 is the card type
// (1=weapon .. 5=dofus), so 10..19 are weapons, 20..29 pets, and so on. Ids
// outside 10..59 are not equippable.
func slotByType(id int32) (int16, bool) {
	t := id / 10
	if t < 1 || t > 5 {
		return 0, false
	}
	return int16(t) - 1, true
}

// TestNormalizeEquipmentPutsEachCardInItsTypeSlot is the fix for the 30 in-fight
// "impossible d'ajouter l'item" rejections that survived the range clamp. Being
// inside 0..4 is not enough: `ne_2.a` compares the position to the item's OWN
// type slot and refuses anything else, so the stored slot can never be trusted.
func TestNormalizeEquipmentPutsEachCardInItsTypeSlot(t *testing.T) {
	r := &FighterRepo{EquipSlotOf: slotByType}
	f := &domain.Fighter{Objects: []domain.FighterObject{
		{TemplateID: 31, Slot: 0}, // cloak -> 2
		{TemplateID: 12, Slot: 1}, // weapon -> 0
		{TemplateID: 54, Slot: 2}, // dofus -> 4
	}}
	r.normalizeEquipment(f)

	if len(f.Objects) != 3 {
		t.Fatalf("kept %d items, want 3", len(f.Objects))
	}
	want := map[int32]int16{12: 0, 31: 2, 54: 4}
	for _, o := range f.Objects {
		if want[o.TemplateID] != o.Slot {
			t.Errorf("card %d at slot %d, want %d", o.TemplateID, o.Slot, want[o.TemplateID])
		}
	}
	for i := 1; i < len(f.Objects); i++ {
		if f.Objects[i-1].Slot >= f.Objects[i].Slot {
			t.Errorf("slots not ascending: %+v", f.Objects)
		}
	}
}

// TestNormalizeEquipmentCollapsesDuplicateTypes covers the real rows seen live: a
// fighter stored TEN cards, several sharing a type. Only one item can occupy a
// position, so the rest cannot exist client-side however they were saved.
func TestNormalizeEquipmentCollapsesDuplicateTypes(t *testing.T) {
	r := &FighterRepo{EquipSlotOf: slotByType}
	f := &domain.Fighter{Objects: []domain.FighterObject{
		{TemplateID: 51, Slot: 0}, {TemplateID: 21, Slot: 1},
		{TemplateID: 41, Slot: 2}, {TemplateID: 52, Slot: 3},
		{TemplateID: 31, Slot: 4}, {TemplateID: 32, Slot: 5},
		{TemplateID: 33, Slot: 6}, {TemplateID: 34, Slot: 7},
		{TemplateID: 22, Slot: 8}, {TemplateID: 42, Slot: 9},
	}}
	r.normalizeEquipment(f)

	if len(f.Objects) > maxEquipSlots {
		t.Fatalf("kept %d items, more than the client's %d slots", len(f.Objects), maxEquipSlots)
	}
	seen := map[int16]bool{}
	for _, o := range f.Objects {
		if seen[o.Slot] {
			t.Errorf("two items in slot %d", o.Slot)
		}
		seen[o.Slot] = true
		if o.Slot < 0 || o.Slot >= maxEquipSlots {
			t.Errorf("slot %d is outside the inventory", o.Slot)
		}
		if got, _ := slotByType(o.TemplateID); got != o.Slot {
			t.Errorf("card %d parked at slot %d, not its type's %d", o.TemplateID, o.Slot, got)
		}
	}
	// No weapon (type 1) in the input, so slot 0 must stay empty rather than be
	// filled by whatever happened to be first.
	if seen[0] {
		t.Error("slot 0 filled although the fighter has no weapon")
	}
}

// TestNormalizeEquipmentDropsUnequippableIds: an id the card table does not know
// has no position at all, so the client could never store it.
func TestNormalizeEquipmentDropsUnequippableIds(t *testing.T) {
	r := &FighterRepo{EquipSlotOf: slotByType}
	f := &domain.Fighter{Objects: []domain.FighterObject{
		{TemplateID: 12, Slot: 0},
		{TemplateID: 999, Slot: 1}, // not equippable
	}}
	r.normalizeEquipment(f)
	if len(f.Objects) != 1 || f.Objects[0].TemplateID != 12 {
		t.Errorf("got %+v, want only card 12", f.Objects)
	}
}

// TestNormalizeEquipmentIsInertWithoutTheCardTable keeps a data-less dev server
// honest: with no resolver injected the rows must come back exactly as stored,
// not silently emptied.
func TestNormalizeEquipmentIsInertWithoutTheCardTable(t *testing.T) {
	r := &FighterRepo{}
	objs := []domain.FighterObject{{TemplateID: 12, Slot: 7}, {TemplateID: 31, Slot: 9}}
	f := &domain.Fighter{Objects: objs}
	r.normalizeEquipment(f)
	if len(f.Objects) != 2 || f.Objects[0].Slot != 7 {
		t.Errorf("rows were altered without a card table: %+v", f.Objects)
	}
}

// TestFighterReadPathsNormalizeEquipment is the call-site half: normalizeEquipment
// can be perfect and the client still choke, because what matters is whether the
// two READ paths actually invoke it. A unit test of the helper cannot see that,
// so this one goes through a real database.
func TestFighterReadPathsNormalizeEquipment(t *testing.T) {
	s := newTestStore(t)
	s.Fighters.EquipSlotOf = slotByType

	acc, err := s.Accounts.CreateAccount("EquipT", "secret", false)
	if err != nil {
		t.Fatalf("CreateAccount: %v", err)
	}
	coach, err := s.Coaches.Create(acc.ID, "EquipCoach", 1, 2, 0)
	if err != nil {
		t.Fatalf("Create coach: %v", err)
	}
	// Store the shape seen live: ten cards, sequential slots, duplicate types.
	fighter := &domain.Fighter{
		CoachID: coach.ID, Name: "Overloaded", BreedID: 5,
		Objects: []domain.FighterObject{
			{TemplateID: 51, Slot: 0}, {TemplateID: 21, Slot: 1},
			{TemplateID: 41, Slot: 2}, {TemplateID: 52, Slot: 3},
			{TemplateID: 31, Slot: 4}, {TemplateID: 32, Slot: 5},
			{TemplateID: 33, Slot: 6}, {TemplateID: 34, Slot: 7},
			{TemplateID: 22, Slot: 8}, {TemplateID: 42, Slot: 9},
		},
	}
	if err := s.Fighters.Create(fighter); err != nil {
		t.Fatalf("Create fighter: %v", err)
	}

	check := func(what string, objs []domain.FighterObject) {
		t.Helper()
		if len(objs) > maxEquipSlots {
			t.Errorf("%s returned %d items, more than the client's %d slots",
				what, len(objs), maxEquipSlots)
		}
		seen := map[int16]bool{}
		for _, o := range objs {
			if seen[o.Slot] {
				t.Errorf("%s: two items in slot %d", what, o.Slot)
			}
			seen[o.Slot] = true
			if got, ok := slotByType(o.TemplateID); !ok || got != o.Slot {
				t.Errorf("%s: card %d at slot %d, not its type's slot %d",
					what, o.TemplateID, o.Slot, got)
			}
		}
	}

	got, err := s.Fighters.Get(fighter.ID)
	if err != nil {
		t.Fatalf("Get: %v", err)
	}
	check("Get", got.Objects)

	list, err := s.Fighters.ListByCoach(coach.ID)
	if err != nil || len(list) != 1 {
		t.Fatalf("ListByCoach: %v (%d fighters)", err, len(list))
	}
	check("ListByCoach", list[0].Objects)

	// The repair is read-only: the rows on disk are untouched, so nothing is
	// destroyed if the card table is ever wrong or missing.
	var raw []domain.FighterObject
	if err := s.DB().Where("fighter_id = ?", fighter.ID).Find(&raw).Error; err != nil {
		t.Fatalf("raw read: %v", err)
	}
	if len(raw) != 10 {
		t.Errorf("stored rows = %d, want the original 10 left intact", len(raw))
	}
}
