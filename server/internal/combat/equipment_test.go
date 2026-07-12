package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// equipEff builds an equip-time EffectDef with a single fixed value param.
// (ParentType is irrelevant here -- the use/equip split already happened in
// gamedata.splitFighterCardEffects; ApplyEquipmentBonuses works on the
// already-filtered EquipEffects list.)
func equipEff(actionID int32, value float32) gamedata.EffectDef {
	return gamedata.EffectDef{ActionID: actionID, Params: []float32{value}}
}

func TestApplyEquipmentBonuses_InitiativeFeedsBaseStat(t *testing.T) {
	// Iop base Init is 40 (breed.go). A card granting +60 Initiative
	// (actionID 76 = INIT_BOOST / CharacBuff(Init)) must raise the fighter's
	// Init characteristic to 100 BEFORE any timeline is built.
	f := NewFighterFromBreed(1, 1, BreedIop, "Iop", 0, 0)
	baseInit := f.Characteristic(Init)

	card := gamedata.FighterCardTemplate{
		ID:           117,
		EquipEffects: []gamedata.EffectDef{equipEff(76, 60)},
	}
	ApplyEquipmentBonuses(f, []gamedata.FighterCardTemplate{card})

	if got := f.Characteristic(Init); got != baseInit+60 {
		t.Errorf("Init after +60 equip = %d, want %d (base %d + 60)", got, baseInit+60, baseInit)
	}
}

func TestApplyEquipmentBonuses_AffectsTurnOrder(t *testing.T) {
	// Two identical-breed fighters have equal base Init, so raw order is
	// insertion order. Equipping +100 Init on the SECOND one must flip the
	// turn order so it acts first -- proving equipment feeds the timeline.
	first := NewFighterFromBreed(1, 1, BreedIop, "First", 0, 0)
	second := NewFighterFromBreed(2, 2, BreedIop, "Second", 0, 0)

	card := gamedata.FighterCardTemplate{
		ID:           121,
		EquipEffects: []gamedata.EffectDef{equipEff(76, 100)},
	}
	ApplyEquipmentBonuses(second, []gamedata.FighterCardTemplate{card})

	order := BuildTurnOrder([]*Fighter{first, second})
	if len(order) != 2 {
		t.Fatalf("turn order length = %d, want 2", len(order))
	}
	if order[0] != second {
		t.Errorf("turn order[0] = %q, want the +100-Init fighter %q (equipment must feed initiative)", order[0].Name, second.Name)
	}
}

func TestApplyEquipmentBonuses_HPAPMPBuffs(t *testing.T) {
	f := NewFighterFromBreed(1, 1, BreedIop, "Iop", 0, 0)
	baseHP := f.Characteristics[HP].Max
	baseAP := f.Characteristics[AP].Max
	baseMP := f.Characteristics[MP].Max

	// +25 HP (id 11), +1 AP (id 13), +1 MP (id 17) -- all CharacBuff, which
	// raises BOTH max and current, so the fighter starts topped up.
	card := gamedata.FighterCardTemplate{
		ID: 130,
		EquipEffects: []gamedata.EffectDef{
			equipEff(11, 25),
			equipEff(13, 1),
			equipEff(17, 1),
		},
	}
	ApplyEquipmentBonuses(f, []gamedata.FighterCardTemplate{card})

	if got := f.Characteristics[HP].Max; got != baseHP+25 {
		t.Errorf("HP max = %d, want %d", got, baseHP+25)
	}
	if got := f.Characteristic(HP); got != baseHP+25 {
		t.Errorf("HP value = %d, want %d (CharacBuff tops up current too)", got, baseHP+25)
	}
	// AP/MP are bounded (12/8). Iop base AP=6, MP=3 (breed.go) so +1 each is
	// well within bounds.
	if got := f.Characteristics[AP].Max; got != baseAP+1 {
		t.Errorf("AP max = %d, want %d", got, baseAP+1)
	}
	if got := f.Characteristics[MP].Max; got != baseMP+1 {
		t.Errorf("MP max = %d, want %d", got, baseMP+1)
	}
}

func TestApplyEquipmentBonuses_NegativeInitiativeDebuff(t *testing.T) {
	// Many equipment cards trade -Init (actionID 77 = INIT_DEBOOST /
	// CharacDebuff(Init)) for other stats. Iop base Init 40, -40 -> 0.
	f := NewFighterFromBreed(1, 1, BreedIop, "Iop", 0, 0)
	baseInit := f.Characteristic(Init)

	card := gamedata.FighterCardTemplate{
		ID:           99,
		EquipEffects: []gamedata.EffectDef{equipEff(77, 40)},
	}
	ApplyEquipmentBonuses(f, []gamedata.FighterCardTemplate{card})

	want := baseInit - 40
	if want < 0 {
		want = 0
	}
	if got := f.Characteristic(Init); got != want {
		t.Errorf("Init after -40 debuff = %d, want %d", got, want)
	}
}

func TestApplyEquipmentBonuses_PercentDamageGain(t *testing.T) {
	// A CharacGain of +10% water damage (actionID 52) is a no-max stat, so
	// it's a plain current-value add.
	f := NewFighterFromBreed(1, 1, BreedIop, "Iop", 0, 0)

	card := gamedata.FighterCardTemplate{
		ID:           113,
		EquipEffects: []gamedata.EffectDef{equipEff(52, 10)},
	}
	ApplyEquipmentBonuses(f, []gamedata.FighterCardTemplate{card})

	if got := f.Characteristic(DmgWaterPercent); got != 10 {
		t.Errorf("DmgWaterPercent = %d, want 10", got)
	}
}

func TestApplyEquipmentBonuses_MultipleCardsStack(t *testing.T) {
	f := NewFighterFromBreed(1, 1, BreedIop, "Iop", 0, 0)
	baseInit := f.Characteristic(Init)

	cards := []gamedata.FighterCardTemplate{
		{ID: 1, EquipEffects: []gamedata.EffectDef{equipEff(76, 20)}},
		{ID: 2, EquipEffects: []gamedata.EffectDef{equipEff(76, 30)}},
	}
	ApplyEquipmentBonuses(f, cards)

	if got := f.Characteristic(Init); got != baseInit+50 {
		t.Errorf("Init after +20 and +30 equip = %d, want %d", got, baseInit+50)
	}
}

func TestApplyEquipmentBonuses_NilAndEmptySafe(t *testing.T) {
	// Must not panic on a nil fighter or empty/no-equip-effect cards.
	ApplyEquipmentBonuses(nil, []gamedata.FighterCardTemplate{{ID: 1}})

	f := NewFighterFromBreed(1, 1, BreedIop, "Iop", 0, 0)
	baseInit := f.Characteristic(Init)
	ApplyEquipmentBonuses(f, nil)
	ApplyEquipmentBonuses(f, []gamedata.FighterCardTemplate{{ID: 1}}) // no EquipEffects
	if got := f.Characteristic(Init); got != baseInit {
		t.Errorf("Init changed by no-op equip = %d, want %d", got, baseInit)
	}
}
