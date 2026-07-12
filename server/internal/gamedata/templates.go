package gamedata

// CoachCardTemplate mirrors the legacy CoachCardTemplate.java: a cosmetic
// "coach card" definition (id/type/value/set), see
// docs/04-game-data-format.md §4.2 section 1.
type CoachCardTemplate struct {
	ID    int32
	Type  int32
	Value int32
	Set   int32
}

func (t CoachCardTemplate) TemplateID() int32 { return t.ID }

// EffectDef is the static, data-only definition of a spell/card/event
// effect, see docs/04-game-data-format.md §4.2 section 3 and
// docs/05-combat-engine.md §5.4.
type EffectDef struct {
	ID                     int32
	ParentType             string
	ParentID               int32
	Duration               []int32
	ActionID               int32
	IsCritical             bool
	Params                 []float32
	AreaShape              int16
	AreaSize               []int32
	Targets                []int32
	TriggersAfter          []int32
	TriggersBefore         []int32
	AffectedByLocalisation bool
}

func (e EffectDef) TemplateID() int32 { return e.ID }

// FighterCardTemplate mirrors the legacy FighterCardTemplate.java: an
// equippable in-combat item definition.
//
// A fighter card carries TWO distinct effect containers, exactly like the
// reference AbstractFighterCard (client/.../game/card/AbstractFighterCard.java):
//   - UseEffects are the ACTIVELY castable abilities (container type
//     "FIGHTER_CARD_USE") -- run when the coach uses the card on their turn
//     (handleCardUse), same shape as a spell cast.
//   - EquipEffects are the PASSIVE stat bonuses (container type
//     "FIGHTER_CARD_EQUIP") -- applied to the fighter's characteristics
//     while the card is equipped (e.g. +60 Initiative, +25 HP, +1 AP). The
//     reference client applies these on the inventory ITEM_ADDED event
//     (AbstractFighter.applyCardEffects) and reverses them on ITEM_REMOVED,
//     so a fighter enters combat already carrying its equipment deltas.
//
// Effects is the full, unsplit list (UseEffects + EquipEffects) preserved
// for backward compatibility with any consumer that wants every effect.
// Prefer UseEffects/EquipEffects for the use-vs-equip distinction; casting
// the equip subset on card USE (the pre-split behavior) applied passive
// stat bonuses at the wrong time.
type FighterCardTemplate struct {
	ID           int32
	Type         byte
	Value        int32
	ScriptID     int32
	SubType      int32
	Effects      []EffectDef
	UseEffects   []EffectDef
	EquipEffects []EffectDef
}

func (t FighterCardTemplate) TemplateID() int32 { return t.ID }

// Fighter-card equipment categories, mirroring the real client's
// FighterCardType enum (client/.../common/constants/FighterCardType.java).
// These are the values stored in FighterCardTemplate.Type (parsed from
// cards.dat), NOT arbitrary -- each has a fixed, client-enforced
// equipment-inventory slot (see FighterCardInventoryPosition).
const (
	FighterCardTypeWeapon byte = 1
	FighterCardTypePet    byte = 2
	FighterCardTypeCloak  byte = 3
	FighterCardTypeHat    byte = 4
	FighterCardTypeDofus  byte = 5
)

// FighterCardInventoryPosition returns the equipment ArrayInventory slot
// (client-side "position") required for a given FighterCardTemplate.Type,
// mirroring FighterCardType.getInventoryPosition() exactly:
// WEAPON->0, PET->1, CLOAK->2, HAT->3, DOFUS->4.
//
// This matters because the real client's ArrayInventory<FighterCard>
// (Fighter.java:258) uses a per-type content checker
// (AbstractFighterCardInventoryChecker.canAddItem(inv, item, position),
// see docs/opcodes/06-fighter-team.md) that REJECTS any (position, item)
// pair where position != item.getType().getInventoryPosition() -- the
// wire position is not cosmetic/sequential, it's a fixed per-category
// slot. Building the equipment blob with sequential array-index positions
// instead of this mapping causes every equipped item whose type doesn't
// coincidentally land on its correct slot index to be silently dropped by
// the client on unserialize (see internal/dispatch/inventory_codec.go).
func FighterCardInventoryPosition(cardType byte) (int16, bool) {
	switch cardType {
	case FighterCardTypeWeapon:
		return 0, true
	case FighterCardTypePet:
		return 1, true
	case FighterCardTypeCloak:
		return 2, true
	case FighterCardTypeHat:
		return 3, true
	case FighterCardTypeDofus:
		return 4, true
	default:
		return 0, false
	}
}

// SpellTemplate mirrors the legacy Spell.java, see
// docs/04-game-data-format.md §4.3.
type SpellTemplate struct {
	ID                        int32
	ActionPointsCost          byte
	CastFrequencyMaxPerPlayer byte
	CastFrequencyMaxPerTurn   byte
	CastFrequencyMinInterval  byte
	CastTestLineOfSight       bool
	CastOnlyLine              bool
	RangeMin                  byte
	RangeMax                  byte
	Price                     int32
	AiTargetID                int32
	NeedFreeCell              bool
	ScriptID                  int32
	BreedID                   int32
	Criterion                 string
	UseAutoDescription        bool
	Effects                   []EffectDef
}

func (t SpellTemplate) TemplateID() int32 { return t.ID }

// EventTemplate mirrors the legacy Event.java, see
// docs/04-game-data-format.md §4.4.
type EventTemplate struct {
	ID                 int32
	UseAutoDescription bool
	Effects            []EffectDef
}

func (t EventTemplate) TemplateID() int32 { return t.ID }

// SummoningTemplate mirrors the legacy Summoning.java, see
// docs/04-game-data-format.md §4.6.
type SummoningTemplate struct {
	ID      int32
	HP      int32
	AP      int32
	MP      int32
	Gfx     int32
	SpellID int32
}

func (t SummoningTemplate) TemplateID() int32 { return t.ID }

// StaticEffectAreaTemplate mirrors the legacy AbstractEffectArea/EffectArea
// static definition (parsed from staticEffects.dat), see
// docs/04-game-data-format.md §4.5. EffectAreaType is always "TRAP" or
// "SPECIAL" in this project's real data (confirmed empirically) --
// "TRAP" entries are persistent ground-effect areas (Phase M,
// docs/08-java-parity-roadmap.md), "SPECIAL" entries are the manual-
// described special battlefield cells already implemented in
// internal/combat/specialcells.go (this template type is the missing
// link to eventually source real per-map special-cell placements, not
// yet wired -- see specialcells.go's own doc comment on why
// SpecialCellNone starts empty).
type StaticEffectAreaTemplate struct {
	ID                    int32
	ScriptID              int32
	AreaShapeID           int16
	AreaParams            []int32
	ApplicationTriggers   []int32
	UnapplicationTriggers []int32
	MaxExecutionCount     int16
	ApplicationTargets    []int32
	UnapplicationTargets  []int32
	TargetsToShow         int32
	EffectAreaType        string // "TRAP" or "SPECIAL"
	DeactivationDelay     []int32
	ApplicationCondition  int32
	Effects               []EffectDef
}

func (t StaticEffectAreaTemplate) TemplateID() int32 { return t.ID }
