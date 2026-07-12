package combat

// Element is a damage/resist element, mirroring
// dofusarena/common/game/effect/Elements.java (PHYSICAL, FIRE, WATER, WIND,
// EARTH -- no "neutral" element exists, see the corrections section of
// docs/opcodes/08-fight-combat-engine.md).
type Element int

const (
	ElementPhysical Element = iota
	ElementFire
	ElementWater
	ElementWind
	ElementEarth
)

func (e Element) String() string {
	switch e {
	case ElementPhysical:
		return "physical"
	case ElementFire:
		return "fire"
	case ElementWater:
		return "water"
	case ElementWind:
		return "wind"
	case ElementEarth:
		return "earth"
	default:
		return "unknown"
	}
}

// DmgCharacteristic returns the flat damage characteristic for this
// element (DMG_FIRE/DMG_WATER/... or plain DMG for physical).
func (e Element) DmgCharacteristic() CharacteristicType {
	switch e {
	case ElementFire:
		return DmgFire
	case ElementWater:
		return DmgWater
	case ElementWind:
		return DmgWind
	case ElementEarth:
		return DmgEarth
	default:
		return Dmg
	}
}

// DmgPercentCharacteristic returns the %-modifier damage characteristic
// for this element.
func (e Element) DmgPercentCharacteristic() CharacteristicType {
	switch e {
	case ElementFire:
		return DmgFirePercent
	case ElementWater:
		return DmgWaterPercent
	case ElementWind:
		return DmgWindPercent
	case ElementEarth:
		return DmgEarthPercent
	default:
		return DmgPercent
	}
}

// ResCharacteristic returns the flat resist characteristic for this
// element (RES_FIRE/RES_WATER/... or plain RES for physical).
func (e Element) ResCharacteristic() CharacteristicType {
	switch e {
	case ElementFire:
		return ResFire
	case ElementWater:
		return ResWater
	case ElementWind:
		return ResWind
	case ElementEarth:
		return ResEarth
	default:
		return Res
	}
}

// ResPercentCharacteristic returns the %-modifier resist characteristic
// for this element.
func (e Element) ResPercentCharacteristic() CharacteristicType {
	switch e {
	case ElementFire:
		return ResFirePercent
	case ElementWater:
		return ResWaterPercent
	case ElementWind:
		return ResWindPercent
	case ElementEarth:
		return ResEarthPercent
	default:
		return ResPercent
	}
}

// Breed IDs, mirroring
// client/com/ankamagames/dofusarena/common/game/fighter/Breed.java's byte
// ids exactly. BreedNone is stored as 255 (the Go uint8 domain.Fighter.Breed
// column has no signed sentinel; Java's byte -1 wraps to 255 unsigned).
const (
	BreedNone     byte = 255
	BreedMonster  byte = 0
	BreedFeca     byte = 1
	BreedOsamodas byte = 2
	BreedEnutrof  byte = 3
	BreedSram     byte = 4
	BreedXelor    byte = 5
	BreedEcaflip  byte = 6
	BreedEniripsa byte = 7
	BreedIop      byte = 8
	BreedCra      byte = 9
	BreedSadida   byte = 10
	BreedSacrier  byte = 11
	BreedPandawa  byte = 12
	BreedGod      byte = 98
	BreedCoach    byte = 99
)

// BreedStats is the static per-breed base-stat table, ported field-for-field
// from Breed.java's enum constructor
// (id, baseHp, baseAp, baseMp, baseInit, baseCH, baseCM, value,
// closeCombatElement, closeCombatAp, closeCombatDamages,
// closeCombatCriticalDamages) -- see docs/08-java-parity-roadmap.md Phase A.
type BreedStats struct {
	ID   byte
	Name string

	BaseHP           int32
	BaseAP           int32
	BaseMP           int32
	BaseInit         int32
	BaseCriticalRate int32 // "CH" in the Java source
	BaseFumbleRate   int32 // "CM" in the Java source

	Value int32 // point-cost value used for team-budget calculations

	CloseCombatElement         Element
	CloseCombatAP              int32
	CloseCombatDamages         int32
	CloseCombatCriticalDamages int32
}

// breedTable is the exact port of every Breed enum constant. NONE/MONSTER/
// GOD/COACH use Java's zero-arg constructor (Breed(byte id)), which zeroes
// every stat -- these are not real playable classes and are included only
// so a lookup by ID never panics.
var breedTable = map[byte]BreedStats{
	BreedFeca:     {ID: BreedFeca, Name: "Feca", BaseHP: 70, BaseAP: 6, BaseMP: 3, BaseInit: 50, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementWater, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedOsamodas: {ID: BreedOsamodas, Name: "Osamodas", BaseHP: 65, BaseAP: 6, BaseMP: 3, BaseInit: 60, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementEarth, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedEnutrof:  {ID: BreedEnutrof, Name: "Enutrof", BaseHP: 65, BaseAP: 6, BaseMP: 3, BaseInit: 60, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementWater, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedSram:     {ID: BreedSram, Name: "Sram", BaseHP: 70, BaseAP: 6, BaseMP: 3, BaseInit: 50, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementWind, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedXelor:    {ID: BreedXelor, Name: "Xelor", BaseHP: 60, BaseAP: 6, BaseMP: 3, BaseInit: 70, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementFire, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedEcaflip:  {ID: BreedEcaflip, Name: "Ecaflip", BaseHP: 70, BaseAP: 6, BaseMP: 3, BaseInit: 50, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementWater, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedEniripsa: {ID: BreedEniripsa, Name: "Eniripsa", BaseHP: 60, BaseAP: 6, BaseMP: 3, BaseInit: 70, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementFire, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedIop:      {ID: BreedIop, Name: "Iop", BaseHP: 75, BaseAP: 6, BaseMP: 3, BaseInit: 40, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementEarth, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedCra:      {ID: BreedCra, Name: "Cra", BaseHP: 65, BaseAP: 6, BaseMP: 3, BaseInit: 60, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementWater, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedSadida:   {ID: BreedSadida, Name: "Sadida", BaseHP: 65, BaseAP: 6, BaseMP: 3, BaseInit: 60, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementEarth, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedSacrier:  {ID: BreedSacrier, Name: "Sacrier", BaseHP: 80, BaseAP: 6, BaseMP: 3, BaseInit: 30, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementWind, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},
	BreedPandawa:  {ID: BreedPandawa, Name: "Pandawa", BaseHP: 75, BaseAP: 6, BaseMP: 3, BaseInit: 40, BaseCriticalRate: 5, BaseFumbleRate: 1, Value: 400, CloseCombatElement: ElementFire, CloseCombatAP: 5, CloseCombatDamages: 5, CloseCombatCriticalDamages: 7},

	BreedNone:    {ID: BreedNone, Name: "None"},
	BreedMonster: {ID: BreedMonster, Name: "Monster"},
	BreedGod:     {ID: BreedGod, Name: "God"},
	BreedCoach:   {ID: BreedCoach, Name: "Coach"},
}

// GetBreedStats looks up the base-stat table entry for a breed ID. ok is
// false for an unknown ID (never happened in the source data, but guards
// against a corrupt/out-of-range domain.Fighter.Breed value).
func GetBreedStats(id byte) (BreedStats, bool) {
	s, ok := breedTable[id]
	return s, ok
}

// AllPlayableBreeds returns the 12 real playable class breed IDs (excludes
// NONE/MONSTER/GOD/COACH), sorted ascending -- used by tests that want to
// exercise every real breed.
func AllPlayableBreeds() []byte {
	return []byte{
		BreedFeca, BreedOsamodas, BreedEnutrof, BreedSram, BreedXelor,
		BreedEcaflip, BreedEniripsa, BreedIop, BreedCra, BreedSadida,
		BreedSacrier, BreedPandawa,
	}
}
