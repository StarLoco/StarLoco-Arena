package domain

import "time"

// Fighter is one deck-built combatant belonging to a Coach, usable in
// Teams. Spell/object loadouts are stored via proper join tables
// (FighterSpell/FighterObject) rather than the legacy Java version's CSV
// string columns.
type Fighter struct {
	ID      uint   `gorm:"primaryKey"`
	CoachID uint   `gorm:"not null;index"`
	Name    string `gorm:"size:32;not null"`
	Breed   uint8  `gorm:"not null"`
	Sex     uint8  `gorm:"not null"`
	Skin    uint8  `gorm:"not null"`
	Budget  int16  `gorm:"not null"`

	CreatedAt time.Time
}

func (Fighter) TableName() string { return "fighters" }

// FighterSpell is a join row associating a Fighter with a spell template ID
// (spell templates live in gamedata, not the relational DB).
type FighterSpell struct {
	FighterID uint  `gorm:"primaryKey"`
	SpellID   int32 `gorm:"primaryKey"`
}

func (FighterSpell) TableName() string { return "fighter_spells" }

// FighterObject is a join row associating a Fighter with an equipped
// fighter-card template ID.
type FighterObject struct {
	FighterID  uint  `gorm:"primaryKey"`
	TemplateID int32 `gorm:"primaryKey"`
}

func (FighterObject) TableName() string { return "fighter_objects" }

// Team is a named preset roster of up to a handful of Fighters, referenced
// by a per-coach Slot number on the wire (see docs/02-protocol.md
// TEAM_PRESET_* messages).
type Team struct {
	ID      uint   `gorm:"primaryKey"`
	CoachID uint   `gorm:"not null;index"`
	Slot    int16  `gorm:"not null"`
	Name    string `gorm:"size:32;not null"`

	Fighters []Fighter `gorm:"many2many:team_fighters;constraint:OnDelete:CASCADE"`
}

func (Team) TableName() string { return "teams" }
