package domain

// Fighter is a coach's combat unit (a "monster"/champion the coach fields in a
// team). The 2.70 wire serialization (et_2) is not yet implemented; these
// models capture the persistent shape so the roster survives reconnects.
type Fighter struct {
	ID      uint   `gorm:"primaryKey"`
	CoachID uint   `gorm:"index;not null"`
	BreedID uint8  `gorm:"not null"`
	Name    string `gorm:"size:32;not null"`
	Hair    uint8
	Skin    uint8
	Eye     uint8
	Sex     uint8
	Budget  int16           `gorm:"not null;default:0"` // recomputed server-side (breed+spells+cards)
	Spells  []FighterSpell  `gorm:"foreignKey:FighterID;constraint:OnDelete:CASCADE"`
	Objects []FighterObject `gorm:"foreignKey:FighterID;constraint:OnDelete:CASCADE"`

	// Conditions are the persistent statuses this fighter carries BETWEEN fights
	// — wounds above all (see gamedata type 902). They ride on the wire in the
	// evolution tail and modify the fighter for the whole of every fight until
	// healed or expired.
	Conditions []FighterCondition `gorm:"foreignKey:FighterID;constraint:OnDelete:CASCADE"`

	// --- Evolution mode -------------------------------------------------------
	// In "evolution" mode a fighter accumulates XP, tires, and can die for good.
	// State drives that lifecycle (see the FighterState* constants); a fighter is
	// only serialized as an evolution (type-2) fighter while State is not
	// FighterStateTitular, which is what puts it in the client's evolution roster
	// and — at FighterStateGraveyard — in the graveyard.
	// Evolution marks the fighter as belonging to the EVOLUTION roster rather than
	// the classic (Élite) one. It comes from the `type` byte of the et_2 blob the
	// client sends when creating it (1 = classic, 2 = evolution) and decides
	// whether we serialize the evolution tail back.
	//
	// It is deliberately SEPARATE from State. State is the fighter's position in
	// its roster — the client buckets line-up as state 0 *or* 2, bench 1,
	// graveyard 3 (xz_0) — so "state 0" means "in the line-up", not "not an
	// evolution fighter". Conflating the two is what used to make every
	// newly-created evolution fighter come back as a classic one.
	Evolution bool  `gorm:"not null;default:false"`
	State     uint8 `gorm:"not null;default:0"`
	XP        int32 `gorm:"not null;default:0"`
	TotalXP   int32 `gorm:"not null;default:0"`

	// --- Sphere Board (Kanodo) -------------------------------------------------
	// SphereX and SphereY are the progression cursor: the 1-BASED grid position of
	// the node the fighter currently sits on. The client feeds them straight to
	// `Ei.X(x, y)` after `fi(x-1)/fj(y-1)`, so they are the node's own coordinates
	// and not an offset. Zero means "not placed yet" and is repaired on first use
	// from the board's root (gamedata.SphereBoards.Root).
	SphereX int16 `gorm:"not null;default:0"`
	SphereY int16 `gorm:"not null;default:0"`
	// Spheres are the nodes this fighter has bought - the client's `ee_2.NE()`.
	Spheres   []FighterSphere `gorm:"foreignKey:FighterID;constraint:OnDelete:CASCADE"`
	Tiredness uint8           `gorm:"not null;default:0"`
	Morale    uint8           `gorm:"not null;default:0"`

	// LastFightAt is the unix time (seconds) this fighter last finished a fight.
	// It drives BOTH post-fight formulas that depend on elapsed time: fatigue
	// recovery (et_2.a: newFatigue = (sqrt(old) - sqrt(hours-1))^2) and the
	// "good rest" XP bonus (+50% when idle for more than 12 hours). 0 = never
	// fought, which the formulas treat as fully rested.
	LastFightAt int64 `gorm:"not null;default:0"`
}

// Fighter evolution states — the client's et_2 "state" byte (aRz).
const (
	FighterStateTitular   uint8 = 0 // in the starting line-up
	FighterStateBench     uint8 = 1 // reserve
	FighterStateDead      uint8 = 2 // dead but still occupying a team slot
	FighterStateGraveyard uint8 = 3 // interred; needs a resurrection card to come back
	FighterStateLegendary uint8 = 4 // legendary titular
	FighterStateLegBench  uint8 = 5 // legendary reserve
)

// Capacities the client enforces per state; mirrored server-side so a coach can
// never end up in a state its own UI would refuse.
const (
	GraveyardCapacity = 5 // "error.evolution.graveyardFull"
	BenchCapacity     = 7 // "error.evolution.tooManyFightersOnBench"
)

// IsEvolution reports whether the fighter is in evolution mode, i.e. whether it
// must be serialized as a type-2 et_2 blob (with the evolution tail).
// IsEvolution reports whether this fighter belongs to the evolution roster, i.e.
// whether its et_2 blob must carry the type-2 evolution tail.
//
// The State fallback keeps rows written before the Evolution column existed
// working: a fighter that is benched, dead or interred could only ever have got
// there through evolution play, so it is one regardless of the flag.
func (f *Fighter) IsEvolution() bool {
	return f.Evolution || f.State != FighterStateTitular
}

// InGraveyard reports whether the fighter is interred.
func (f *Fighter) InGraveyard() bool { return f.State == FighterStateGraveyard }

// FighterSpell is a spell equipped on a fighter. Slot is the en_1 wire slot
// index (0..4) carried by the fighter-inventory blob (6010/6011); the et_2
// roster blob (6006) does not carry per-spell slots.
type FighterSpell struct {
	ID        uint  `gorm:"primaryKey"`
	FighterID uint  `gorm:"index;not null"`
	Slot      int16 `gorm:"not null;default:0"`
	SpellID   int32 `gorm:"not null"`
}

// FighterObject is a card/item equipped on a fighter.
type FighterObject struct {
	ID         uint  `gorm:"primaryKey"`
	FighterID  uint  `gorm:"index;not null"`
	TemplateID int32 `gorm:"not null"`
	Slot       int16 `gorm:"not null;default:0"`
}

// Team is a saved fighter-team preset. Type is the wire "Gp" preset type; when
// it is a special value the four appearance bytes are also serialized.
type Team struct {
	ID       uint   `gorm:"primaryKey"`
	CoachID  uint   `gorm:"index;not null"`
	Name     string `gorm:"size:32;not null"`
	Type     int16  `gorm:"not null;default:0"`
	GameMode int16  `gorm:"not null;default:0"`
	App1     uint8
	App2     uint8
	App3     uint8
	App4     uint8
	Members  []TeamFighter `gorm:"foreignKey:TeamID;constraint:OnDelete:CASCADE"`
}

// TeamFighter links a fighter into a team preset.
type TeamFighter struct {
	ID        uint `gorm:"primaryKey"`
	TeamID    uint `gorm:"index;not null"`
	FighterID uint `gorm:"not null"`
}

func (Fighter) TableName() string       { return "fighters" }
func (FighterSpell) TableName() string  { return "fighter_spells" }
func (FighterObject) TableName() string { return "fighter_objects" }
func (Team) TableName() string          { return "teams" }
func (TeamFighter) TableName() string   { return "team_fighters" }

// FighterCondition is one persistent status held by a fighter (gamedata type
// 902): a wound, a blessing or a curse.
//
// The client models this as a map conditionId -> duration byte (`et_2.uk`, a
// `vy_1`), which is why the pair is the whole row. Remaining is the number of
// FIGHTS left, not turns; DurationPermanent (-1) never expires and is what every
// wound carries.
type FighterCondition struct {
	ID          uint  `gorm:"primaryKey"`
	FighterID   uint  `gorm:"index;not null"`
	ConditionID int16 `gorm:"not null"`
	// Remaining fights before the condition expires. -1 = permanent.
	Remaining int8 `gorm:"not null;default:-1"`
}

// ConditionPermanent mirrors gamedata.DurationPermanent for the persistence
// layer (kept separate so domain does not import gamedata).
const ConditionPermanent int8 = -1

// HasCondition reports whether the fighter currently carries a condition id.
func (f *Fighter) HasCondition(id int16) bool {
	for _, c := range f.Conditions {
		if c.ConditionID == id {
			return true
		}
	}
	return false
}

// FighterSphere is one Sphere Board node a fighter has bought. The board itself
// is client-side data (gamedata types 900/901); only which nodes were purchased,
// and where the cursor stands, are the server's to keep.
type FighterSphere struct {
	ID        uint  `gorm:"primaryKey"`
	FighterID uint  `gorm:"index;not null"`
	SphereID  int32 `gorm:"not null"`
}

func (FighterSphere) TableName() string { return "fighter_spheres" }
