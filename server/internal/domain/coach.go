package domain

import "time"

// Coach is a player's in-world character: identity, look, world position,
// and the root of their owned cards/fighters/teams/social graph.
type Coach struct {
	ID   uint   `gorm:"primaryKey"`
	Name string `gorm:"uniqueIndex;size:32;not null"`

	Skin uint8 `gorm:"not null"`
	Hair uint8 `gorm:"not null"`
	Sex  uint8 `gorm:"not null"`

	// PosX/PosY/PosZ replace the legacy Java DataType.SERIALIZABLE Position
	// blob with plain, portable, queryable columns.
	PosX int32 `gorm:"not null;default:1"`
	PosY int32 `gorm:"not null;default:1"`
	PosZ int16 `gorm:"not null;default:0"`

	// Persisted ladder & lifetime statistics backing the "Statistiques du
	// Coach" window (PLAYER_STATISTICS_REPORT, opcode 2400) and the
	// COACH_INFORMATION ladder Level/Rank. The legacy Java server never
	// persisted these (TODO in PlayerStatisticsReport.java); this port
	// tracks them for real. See ladder.go for the strength->Level->Rank
	// derivation and docs/opcodes/03-coach-world.md.
	//
	// Strength is the 1v1 ladder rating. 0 means "unranked" (no fight
	// played yet) and renders as Level "-" client-side; once a coach
	// finishes their first fight it is seeded and clamped to
	// [StrengthMin, StrengthMax] (see ApplyFightStrength).
	Strength          int32 `gorm:"not null;default:0"`
	StatFights        int32 `gorm:"not null;default:0"`
	StatWins          int32 `gorm:"not null;default:0"`
	StatLosses        int32 `gorm:"not null;default:0"`
	ConsecutiveWins   int32 `gorm:"not null;default:0"`
	TimeInFightSecs   int64 `gorm:"not null;default:0"`
	TotalPlayTimeSecs int64 `gorm:"not null;default:0"`

	Inventory []CoachCard    `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`
	Friends   []CoachFriend  `gorm:"foreignKey:OwnerID;constraint:OnDelete:CASCADE"`
	Ignored   []CoachIgnored `gorm:"foreignKey:OwnerID;constraint:OnDelete:CASCADE"`
	Fighters  []Fighter      `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`
	Teams     []Team         `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`

	CreatedAt time.Time
	UpdatedAt time.Time
}

func (Coach) TableName() string { return "coachs" }

// CoachCard flags, mirroring the legacy CoachCard.FLAG_* constants.
const (
	CoachCardFlagLocked uint8 = 1 << iota
	CoachCardFlagCursed
)

// CoachCard is a card instance owned by a coach, either sitting in their
// inventory (Pos == 0) or equipped in a slot (Pos > 0).
type CoachCard struct {
	ID         uint  `gorm:"primaryKey"`
	CoachID    uint  `gorm:"not null;index"`
	TemplateID int32 `gorm:"not null;index"` // references gamedata coach-card template, not a DB FK
	Quantity   int16 `gorm:"not null;default:1"`
	Pos        int16 `gorm:"not null;default:0"`
	Flag       uint8 `gorm:"not null;default:2"`
}

func (CoachCard) TableName() string { return "coach_cards" }

// CoachFriend is a directed friendship edge: Owner considers Friend a
// friend. The Friend association is preloaded when the friend list needs
// to be serialized (name lookup for FRIEND_LIST_MESSAGE, see
// docs/02-protocol.md §2.4.3).
type CoachFriend struct {
	ID       uint `gorm:"primaryKey"`
	OwnerID  uint `gorm:"not null;index:idx_friend_owner_friend,unique"`
	FriendID uint `gorm:"not null;index:idx_friend_owner_friend,unique"`
	Notify   bool `gorm:"not null;default:true"`

	Friend *Coach `gorm:"foreignKey:FriendID"`
}

func (CoachFriend) TableName() string { return "coach_friends" }

// CoachIgnored is a directed ignore-list edge: Owner ignores Ignored.
type CoachIgnored struct {
	ID        uint `gorm:"primaryKey"`
	OwnerID   uint `gorm:"not null;index:idx_ignored_owner_ignored,unique"`
	IgnoredID uint `gorm:"not null;index:idx_ignored_owner_ignored,unique"`

	Ignored *Coach `gorm:"foreignKey:IgnoredID"`
}

func (CoachIgnored) TableName() string { return "coach_ignored" }
