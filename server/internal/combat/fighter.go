package combat

import "math"

// Point3 is the universal in-fight coordinate type: grid X/Y plus a
// fine-grained altitude Z (used for jumps/heights, not just a floor flag),
// mirroring the reference implementation's Point3, see
// docs/05-combat-engine.md §5.8.
type Point3 struct {
	X, Y int32
	Z    int16
}

// Step returns the cell one step away from p in direction dir (grid-only;
// Z is left unchanged -- height resolution is the caller's job via a
// CellInfoProvider, see docs/05-combat-engine.md §5.6.2/§5.8).
//
// The grid vectors below are the EXACT ones from the client's
// framework.kernel.core.maths.Direction8.getVector() (verified against the
// decompiled Direction8.java enum, correcting the JD-Core decompiler's
// dropped trailing zero on SOUTH_EAST/NORTH_WEST). Matching them exactly is
// REQUIRED because the movement path's raw X/Y cells are sent on the wire
// (buildFighterMove) and the client re-derives each step's facing from the
// grid delta between consecutive cells (PathMobile.getDirection8FromVector /
// NetFightActionFrame.getDirection4FromVector). If these vectors disagree
// with the client's, the client computes the wrong facing -- specifically a
// CARDINAL facing, which has no fighter sprite art and renders the fighter
// invisible -- and animates toward the wrong destination.
//
// Note the isometric naming: the four "diagonal-named" fight directions
// (SOUTH_EAST/SOUTH_WEST/NORTH_WEST/NORTH_EAST) are SINGLE-AXIS grid moves
// (only X or only Y changes); they merely LOOK diagonal on the 2:1
// isometric screen projection. The four "cardinal-named" directions
// (EAST/SOUTH/WEST/NORTH) are the two-axis grid moves and are forbidden in
// fight movement (see fightMoveDirections in pathfind.go).
func (p Point3) Step(dir Direction8) Point3 {
	switch dir {
	case DirEast: // {1, -1}
		return Point3{p.X + 1, p.Y - 1, p.Z}
	case DirSouthEast: // {1, 0}
		return Point3{p.X + 1, p.Y, p.Z}
	case DirSouth: // {1, 1}
		return Point3{p.X + 1, p.Y + 1, p.Z}
	case DirSouthWest: // {0, 1}
		return Point3{p.X, p.Y + 1, p.Z}
	case DirWest: // {-1, 1}
		return Point3{p.X - 1, p.Y + 1, p.Z}
	case DirNorthWest: // {-1, 0}
		return Point3{p.X - 1, p.Y, p.Z}
	case DirNorth: // {-1, -1}
		return Point3{p.X - 1, p.Y - 1, p.Z}
	case DirNorthEast: // {0, -1}
		return Point3{p.X, p.Y - 1, p.Z}
	default:
		return p
	}
}

// Direction8 is an 8-way facing, used for hit-location and push-direction
// calculations (docs/05-combat-engine.md §5.6). The numeric values here
// are wire-exact, ported directly from
// framework.kernel.core.maths.Direction8's enum ordinals (confirmed via
// the decompiled client source) -- FIGHTER_CHANGE_DIRECTION(4522) and
// FighterActorDirectionChangeRequestMessage(4521) both send/receive this
// as a raw byte that the client resolves via
// Direction8.getDirectionFromIndex(byte), so getting these values wrong
// would desync facing between server and client even though it doesn't
// affect any other wire field.
type Direction8 uint8

const (
	DirEast      Direction8 = 0
	DirSouthEast Direction8 = 1
	DirSouth     Direction8 = 2
	DirSouthWest Direction8 = 3
	DirWest      Direction8 = 4
	DirNorthWest Direction8 = 5
	DirNorth     Direction8 = 6
	DirNorthEast Direction8 = 7
)

// defaultTeamFacing returns a sensible initial orientation for a fighter on
// the given team. ONLY the four DIAGONAL directions (SOUTH_EAST=1,
// SOUTH_WEST=3, NORTH_WEST=5, NORTH_EAST=7) have fighter sprite art in the
// isometric client -- the four CARDINAL directions (EAST=0, SOUTH=2,
// WEST=4, NORTH=6) render wrong/blank (confirmed empirically via the
// /APPEAR GM debug command: odd/diagonal indices render correctly, even/
// cardinal ones don't; matches Direction8.DIRECTION_4_VALUES in the
// decompiled client). The legacy reference server's hardcoded DirSouth(2)
// for ACTOR_APPEAR was therefore wrong -- but it never actually ran (its
// send was commented out), so the bug was never exposed until now.
//
// Team A (id 1) faces NORTH_WEST and team B faces SOUTH_EAST, matching
// their respective COACH's facing (see buildActorAppearForFight) so each
// team's fighters look the same way as their coach -- i.e. toward the
// battlefield / the opposing side, rather than turning their backs.
// Both are valid diagonals (odd Direction8 indices), which is required for
// fighter sprites to render correctly (§8.17).
func defaultTeamFacing(teamID uint8) Direction8 {
	if teamID == 1 {
		return DirNorthWest
	}
	return DirSouthEast
}

// PropertyFlags is a bitmask of the toggleable fighter properties from
// docs/05-combat-engine.md §5.4.1 item 5.
type PropertyFlags uint8

const (
	PropertyInvisible PropertyFlags = 1 << iota
	PropertyStabilized
	PropertyPetrified
	PropertyRooted
)

// Has reports whether flag is set.
func (p PropertyFlags) Has(flag PropertyFlags) bool { return p&flag != 0 }

// CharacteristicType enumerates every combat stat, mirroring the reference
// FighterCharacteristicType enum exactly (see
// docs/opcodes/08-fight-combat-engine.md §1.5's exact id/name table). Note:
// there is NO "DmgNeutral"/"ResNeutral" entry in the reference enum --
// physical/base damage uses plain Dmg/Res with no element suffix. An
// earlier version of this file invented a DmgNeutral constant; removed per
// the corrections list in that doc.
type CharacteristicType int

const (
	HP CharacteristicType = iota
	AP
	MP
	Init
	ResPercent
	ResFirePercent
	ResWaterPercent
	ResWindPercent
	ResEarthPercent
	DmgPercent
	DmgFirePercent
	DmgWaterPercent
	DmgWindPercent
	DmgEarthPercent
	Res
	ResFire
	ResWater
	ResWind
	ResEarth
	Dmg
	DmgFire
	DmgWater
	DmgWind
	DmgEarth
	Range
	CriticalRate
	FumbleRate
	NbSummons
	Heal
	ResAPLoss
	ResMPLoss
	ResInPercent
	DmgInPercent
	DmgRebound

	characteristicCount // sentinel, not a real characteristic
)

// characteristicBounds defines the [min, max] clamp applied whenever a
// characteristic's Value is mutated (via Add), ported from the exact
// per-characteristic bound table in
// docs/opcodes/08-fight-combat-engine.md §1.5. Types not listed here are
// genuinely unbounded in both directions (flat MIN..MAX_VALUE stats like
// plain Dmg/DmgFire/.../Range).
//
// Note: the decompiled source shows a literal "65036" as the lower bound
// for DMG_*_PERCENT (ids 9-12) and HEAL (id 29) -- confirmed to be a
// decompiler artifact of a packed negative short constant, not real game
// data (see the doc's explicit note). This table uses the semantically
// correct -100 bound for all of those instead of porting 65036 verbatim.
var characteristicBounds = map[CharacteristicType][2]int32{
	HP:              {0, math.MaxInt32},
	AP:              {0, 12},
	MP:              {0, 8},
	Init:            {0, math.MaxInt32},
	ResPercent:      {-100, 100},
	ResFirePercent:  {-100, 100},
	ResWaterPercent: {-100, 100},
	ResWindPercent:  {-100, 100},
	ResEarthPercent: {-100, 100},
	DmgPercent:      {-100, 100},
	DmgFirePercent:  {-100, 100},
	DmgWaterPercent: {-100, 100},
	DmgWindPercent:  {-100, 100},
	DmgEarthPercent: {-100, 100},
	Res:             {0, math.MaxInt32},
	ResFire:         {0, math.MaxInt32},
	ResWater:        {0, math.MaxInt32},
	ResWind:         {0, math.MaxInt32},
	ResEarth:        {0, math.MaxInt32},
	CriticalRate:    {0, math.MaxInt32},
	FumbleRate:      {0, math.MaxInt32},
	NbSummons:       {0, math.MaxInt32},
	Heal:            {-100, 100},
	ResAPLoss:       {-100, 100},
	ResMPLoss:       {-100, 100},
	ResInPercent:    {-100, 100},
	DmgInPercent:    {-100, 100},
	DmgRebound:      {0, 99},
}

// Characteristic is one stat's current/max value pair.
type Characteristic struct {
	Value int32
	Max   int32
}

// Add applies delta to the characteristic, clamping to its bounds (if any)
// and to [_, Max] for stats that have a Max (HP/AP/MP).
func (c *Characteristic) Add(delta int32) {
	c.Value += delta
	if c.Max > 0 && c.Value > c.Max {
		c.Value = c.Max
	}
}

// ToMax sets Value to Max, used at turn start for AP/MP reset (see
// docs/05-combat-engine.md §5.2.2).
func (c *Characteristic) ToMax() {
	c.Value = c.Max
}

// AddMax adjusts Max by delta, mirroring
// FighterCharacteristic.updateMaxValue()/setMax(): if Value now exceeds
// the new Max, Value is clamped down to match (a real max-characteristic
// buff/debuff, distinct from a plain current-value Add -- see the
// CharacGain-vs-CharacBuff / CharacLoss-vs-CharacDebuff distinction
// confirmed by cross-checking the decompiled
// dofusarena/common/game/effect/runningEffect/{CharacGain,CharacBuff,
// CharacLoss,CharacDebuff}.java against the game manual's characteristic
// descriptions).
func (c *Characteristic) AddMax(delta int32) {
	c.Max += delta
	if c.Max < 0 {
		c.Max = 0
	}
	if c.Value > c.Max {
		c.Value = c.Max
	}
}

// clampToBounds clamps v into t's [min,max] bound (if any is defined for
// t); returns v unchanged if t is not a bounded characteristic.
func clampToBounds(t CharacteristicType, v int32) int32 {
	b, ok := characteristicBounds[t]
	if !ok {
		return v
	}
	if v < b[0] {
		return b[0]
	}
	if v > b[1] {
		return b[1]
	}
	return v
}

// FighterWireIDBase is added to a fighter's real DB id to produce the id
// used on the wire (and as the fighter's in-engine ID) throughout a fight.
//
// This exists to keep fighter ids and COACH ids in disjoint numeric ranges,
// which the client requires: the client's ACTOR_APPEAR handler resolves each
// entry by fight.getFighterById(id) FIRST and only falls back to a
// coach/team-mate lookup if that returns nil (NetFightActorsFrame case
// 4102). Coach ids and fighter ids both come from separate DB tables that
// each auto-increment from 1, so without this offset coach 1 and fighter 1
// collide and the coach can never be resolved (leaving coaches invisible on
// the fight map). Coaches MUST keep their real ids on the wire (the client
// compares them against the login-supplied local coach id in setFight()/
// FightEndAction), so it is the FIGHTER ids that are shifted instead. A
// billion-scale base is far above any realistic coach id, and comfortably
// within int64/Java-long range. See §8.18 of docs/08-java-parity-roadmap.md.
//
// Because the client echoes fighter ids back verbatim (in move/cast/etc.
// requests) and the whole combat engine uses this same value as the
// fighter's identity, the offset is applied ONCE at fighter construction
// (buildCombatTeam / CREATE_FIGHT serialization) and needs no per-packet
// translation. Summons (nextSummonID) allocate above this base too.
const FighterWireIDBase int64 = 1_000_000_000

// Fighter is one combatant's live in-fight state: identity, position,
// characteristics, active effects, and carrying relationships. See
// docs/05-combat-engine.md §5.3.
type Fighter struct {
	ID      int64
	CoachID uint // owning coach (0 for a summon with no direct coach owner)
	TeamID  uint8
	Breed   uint8
	Name    string
	Sex     uint8
	Skin    uint8

	Position  Point3
	Direction Direction8

	Characteristics map[CharacteristicType]*Characteristic
	Properties      PropertyFlags

	// Father is set when this Fighter is a summon, pointing at its
	// summoner -- used by Timeline's turn-order insertion algorithm (see
	// docs/opcodes/08-fight-combat-engine.md §1.4's addFighter
	// description).
	Father *Fighter

	// SummonSpellID is the single spell a SUMMONED creature can cast on its
	// AI-driven turn, sourced from its SummoningTemplate.SpellID (mirroring
	// the client's SummonedFighter, which adds getSpellId() to the summon's
	// spell inventory). 0 means the summon has no spell (a pure blocker,
	// e.g. Blocker/Tree). Only meaningful when Father != nil. Read by
	// summon_ai.go to decide the summon's behavior + what to cast.
	SummonSpellID int32

	CarriedFighter   *Fighter
	CarriedByFighter *Fighter

	// StrikeBackPercent is the flat damage-return percentage granted by an
	// active STRIKE_BACK effect (RunningEffectConstants id 90). Unlike the
	// DmgRebound *characteristic* (id 89, a percentage of damage bounced
	// back that scales with the incoming hit), STRIKE_BACK in the
	// reference is a reactive RunningEffect keyed to the "was attacked"
	// trigger (trigger bit 2) that returns param[0]% of the triggering
	// hit's value to the attacker (see StrikeBack.java's execute/
	// computeValue). This project has no generic reactive trigger-bus yet
	// (see effects.go's header), so the effect is modeled as a stored
	// percentage consulted synchronously by applyDamage: whenever this
	// fighter takes spell/close-combat damage, StrikeBackPercent% of it is
	// dealt straight back to the attacker. Set by EffectStrikeBack, read
	// by applyDamageFrom.
	StrikeBackPercent int32

	// SpellReboundRate is the current spell-rebound reflect CHANCE (percent,
	// 0-99) on this fighter: each incoming hostile spell effect rolls against
	// it, and on success is redirected onto its caster instead (mirrors
	// SpellRebound.java, which rolls DiceRoll.roll(100) <= executionRate per
	// incoming spell). Multiple SpellRebound casts STACK the rate additively
	// (capped 99, matching stackWith). 0 = no rebound. The rate is reverted
	// to 0 when the buff's duration expires (see duration.go's
	// ActiveEffectSpellRebound). Persists across incoming spells for its whole
	// duration (NOT a single one-shot bounce).
	SpellReboundRate int32

	// SpellIDs/ObjectIDs are the fighter's owned spells and equipped
	// fight-cards, sourced from domain.FighterSpell/FighterObject at fight
	// creation time -- used by the spell/card cast validation pipeline
	// (docs/05-combat-engine.md §5.5).
	SpellIDs  []int32
	ObjectIDs []int32

	// ActiveEffects tracks every effect on this fighter whose EffectDef.
	// Duration is set to a real (non-zero, non-infinite) table-turn count
	// -- currently recurring DoT ticks (EffectCharacPoison) and
	// timed-buff/debuff expiry (EffectCharacBuff, reused for both buffs
	// and debuffs since a revert is just "undo the signed Max delta that
	// was actually applied"). See duration.go and
	// docs/08-java-parity-roadmap.md Phase J -- a minimal, table-turn-
	// granularity duration primitive deliberately chosen over porting the
	// reference's full generic TimeEvent duration-queue (see the roadmap
	// item's own explicit allowance for this simplification).
	ActiveEffects []ActiveEffect

	// ReactiveEffects holds effects DEFERRED by the trigger-bus: an effect
	// that declares TriggersBefore/TriggersAfter is stored here at cast time
	// (rather than executed instantly) and fires later when a matching
	// in-fight event occurs on this fighter -- e.g. a counter-attack or
	// damage-reflect that arms on the carrier and executes each time it's
	// struck. See triggerbus.go (and RunningEffect.mustBeTriggered() /
	// RunningEffectManager.storeEffect in the decompiled reference).
	ReactiveEffects []reactiveEffect

	// CastHistory tracks per-spell cast-frequency state (MinCastInterval/
	// CastMaxPerTurn/CastMaxPerTarget), mirroring the decompiled
	// SpellCastHistory.java exactly -- see spell_cast_history.go (Phase
	// L, docs/08-java-parity-roadmap.md).
	CastHistory SpellCastHistory

	// EvasionBonus adjusts this fighter's per-enemy tackle-evade success
	// chance (percentage points added to tackleBaseEvasionPercent). It is
	// deliberately NOT a wire-serialized CharacteristicType: the reference
	// FighterCharacteristicType enum (ids 1-34, ending at DMG_REBOUND) has
	// NO evasion/tackle characteristic at all, and the whole tackle
	// mechanic is greenfield (the client's TackleAction is a cosmetic
	// no-op -- see tackle.go's header). Modeling evasion as a plain,
	// non-wire fighter-local modifier keeps the wire-exact characteristic
	// enum untouched while still letting the manual's "modified by the
	// Evasion characteristic per-side" wording be honored -- effects/cells
	// that should make a fighter harder (or easier) to tackle adjust this
	// directly. Zero (the default) reproduces the flat-67%-base behavior.
	EvasionBonus int32

	IsDead bool
}

// NewFighter constructs a Fighter with all characteristics zeroed and no
// breed-derived base stats populated. Prefer NewFighterFromBreed for any
// fighter that will actually take part in combat; this bare constructor
// remains for tests/callers that want to hand-populate every stat
// themselves.
func NewFighter(id int64, teamID uint8, breed uint8) *Fighter {
	return &Fighter{
		ID:              id,
		TeamID:          teamID,
		Breed:           breed,
		Characteristics: newZeroedCharacteristics(),
	}
}

// NewFighterFromBreed constructs a Fighter with HP/AP/MP/INIT maxes (and
// current values, fully topped up) plus CriticalRate/FumbleRate populated
// from the breed base-stat table (internal/combat/breed.go), mirroring
// AbstractFighter.initializeCharacteristics() -- see
// docs/opcodes/08-fight-combat-engine.md §1.5. Falls back to all-zero
// characteristics (but does not error) if breed is unknown, since a
// corrupt/out-of-range breed ID must never crash the fight actor.
func NewFighterFromBreed(id int64, teamID uint8, breed uint8, name string, sex, skin uint8) *Fighter {
	f := &Fighter{
		ID:              id,
		TeamID:          teamID,
		Breed:           breed,
		Name:            name,
		Sex:             sex,
		Skin:            skin,
		Direction:       defaultTeamFacing(teamID),
		Characteristics: newZeroedCharacteristics(),
	}

	stats, ok := GetBreedStats(breed)
	if !ok {
		return f
	}

	f.Characteristics[HP].Max = stats.BaseHP
	f.Characteristics[AP].Max = stats.BaseAP
	f.Characteristics[MP].Max = stats.BaseMP
	f.Characteristics[Init].Max = stats.BaseInit
	f.Characteristics[HP].ToMax()
	f.Characteristics[AP].ToMax()
	f.Characteristics[MP].ToMax()
	f.Characteristics[Init].ToMax()
	f.Characteristics[CriticalRate].Value = stats.BaseCriticalRate
	f.Characteristics[FumbleRate].Value = stats.BaseFumbleRate

	return f
}

func newZeroedCharacteristics() map[CharacteristicType]*Characteristic {
	chars := make(map[CharacteristicType]*Characteristic, characteristicCount)
	for t := CharacteristicType(0); t < characteristicCount; t++ {
		chars[t] = &Characteristic{}
	}
	return chars
}

// Characteristic returns the current value of the given stat.
func (f *Fighter) Characteristic(t CharacteristicType) int32 {
	if c, ok := f.Characteristics[t]; ok {
		return c.Value
	}
	return 0
}

// AddCharacteristic applies delta to characteristic t's current Value,
// clamping to t's bound (docs/opcodes/08-fight-combat-engine.md §1.5) and
// to [_, Max] for stats with a Max (HP/AP/MP/INIT).
func (f *Fighter) AddCharacteristic(t CharacteristicType, delta int32) {
	f.AddCharacteristicClamped(t, delta)
}

// AddCharacteristicClamped is AddCharacteristic that also returns the ACTUAL
// change applied to Value after clamping (to Max and to t's bounds). This
// matters for healing: a +15 heal on a fighter 5 below max only raises HP by
// 5, and the client (which re-applies the broadcast value via
// FighterCharacteristic.add) must be told the REAL delta (5), not the raw 15
// -- otherwise the client's HP bar overshoots its own max. Returns 0 if t is
// not a known characteristic.
func (f *Fighter) AddCharacteristicClamped(t CharacteristicType, delta int32) int32 {
	c, ok := f.Characteristics[t]
	if !ok {
		return 0
	}
	before := c.Value
	c.Add(delta)
	c.Value = clampToBounds(t, c.Value)
	return c.Value - before
}

// BreedStats returns this fighter's breed base-stat table entry (ok=false
// if the breed is unrecognized).
func (f *Fighter) BreedStats() (BreedStats, bool) {
	return GetBreedStats(f.Breed)
}

const baseFighterHeight = 6

// Height returns this fighter's stacked height for LOS/jump calculations,
// accounting for any carried fighter (piggyback mechanic), see
// docs/05-combat-engine.md §5.3.
func (f *Fighter) Height() int {
	if f.CarriedFighter != nil {
		return baseFighterHeight + f.CarriedFighter.Height()
	}
	return baseFighterHeight
}

// ShouldBeDead reports HP having reached exactly zero, mirroring
// AbstractFighter.shouldBeDead() -- this is a query, not automatically
// enforced; callers must check it after applying damage and then invoke
// fight-end handling (docs/opcodes/08-fight-combat-engine.md §1.5).
func (f *Fighter) ShouldBeDead() bool {
	return f.Characteristic(HP) <= 0
}
