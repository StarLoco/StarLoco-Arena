// Package domain holds the persistent data models (GORM) for the 2.70 server.
//
// Mirrors the proven 2006 server's schema: an Account owns at most one Coach
// (nullable FK CoachID is the existing-vs-new-coach discriminator); a Coach has
// cards, friends and ignores. Layouts on the wire differ for 2.70 but the
// persistence design is identical.
package domain

import (
	"sync"
	"time"
)

// Account is a login account.
type Account struct {
	ID           uint   `gorm:"primaryKey"`
	Name         string `gorm:"uniqueIndex;size:64;not null"`
	PasswordHash string `gorm:"size:120;not null"` // bcrypt
	Connected    bool   `gorm:"not null;default:false"`
	IsAdmin      bool   `gorm:"not null;default:false"`
	CoachID      *uint  // nullable FK — nil => no coach yet
	Coach        *Coach `gorm:"foreignKey:CoachID"`
	CreatedAt    time.Time
	UpdatedAt    time.Time
}

// Coach is a player's in-world character.
type Coach struct {
	// Mu guards concurrent mutation of the in-memory coach fields (a coach can
	// be touched by both its session goroutine and a fight actor). Not persisted.
	Mu sync.Mutex `gorm:"-"`

	ID   uint   `gorm:"primaryKey"`
	Name string `gorm:"uniqueIndex;size:32;not null"`

	// Look
	Hair uint8 `gorm:"not null;default:0"`
	Skin uint8 `gorm:"not null;default:0"`
	Sex  uint8 `gorm:"not null;default:0"`

	// World position
	PosX int32 `gorm:"not null;default:1"`
	PosY int32 `gorm:"not null;default:1"`
	PosZ int16 `gorm:"not null;default:0"`

	// Standing is the coach's EVOLUTION experience ("Réputation" in the client,
	// aez_0.bMU, flag 0x400). It is a second, independent progression track from
	// Strength: Strength is the ELO-like ladder rating, Standing accumulates and
	// never drops. The client derives the evolution level from it with
	// StandingToLevel and pops its level-up dialog when that level changes.
	Standing int32 `gorm:"not null;default:0"`

	// Ladder / lifetime stats
	Strength          int32 `gorm:"not null;default:0"` // 1v1 rating; 0 = unranked
	StatFights        int32 `gorm:"not null;default:0"`
	StatWins          int32 `gorm:"not null;default:0"`
	StatLosses        int32 `gorm:"not null;default:0"`
	ConsecutiveWins   int32 `gorm:"not null;default:0"`
	ConsecutiveLosses int32 `gorm:"not null;default:0"`
	TimeInFightSecs   int64 `gorm:"not null;default:0"`
	TotalPlaySecs     int64 `gorm:"not null;default:0"`

	Inventory []CoachCard     `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`
	Friends   []CoachFriend   `gorm:"foreignKey:OwnerID;constraint:OnDelete:CASCADE"`
	Ignored   []CoachIgnored  `gorm:"foreignKey:OwnerID;constraint:OnDelete:CASCADE"`
	Wallet    []CoachCurrency `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`
	Stats     []CoachStat     `gorm:"foreignKey:CoachID;constraint:OnDelete:CASCADE"`

	CreatedAt time.Time
	UpdatedAt time.Time
}

// CoachStat is one keyed achievement/statistic counter for a coach. StatID is
// the client's statistic-enum ordinal (client `or_0`); the client reports
// progress via opcode 22003. Kept in a keyed table (rather than scalar Coach
// columns) because the id space is large and open-ended.
type CoachStat struct {
	ID      uint  `gorm:"primaryKey"`
	CoachID uint  `gorm:"uniqueIndex:idx_stat_pair;not null"`
	StatID  int16 `gorm:"uniqueIndex:idx_stat_pair;not null"`
	Value   int32 `gorm:"not null;default:0"`
}

// CoachCard is one owned card stack. Pos==0 => in inventory; Pos>=1 => equipped
// in wire-slot Pos-1. TemplateID references a gamedata card template (not a FK).
//
// There is deliberately no per-instance flag here. 2.70 has none: the card
// object on the wire is a bare i32 reference id (eb_1.NT() == 4), and every rule
// about what may be traded, mailed, sold or destroyed reads the TEMPLATE's own
// booleans — Bound (tp()) and Undestructible (tq()) — which live in gamedata.
// A `Flag` column carrying "locked"/"cursed" bits used to exist here; nothing
// ever set the locked bit, the cursed bit was written to every card and meant
// nothing, and the client never saw either. See BUGS.md B-094.
type CoachCard struct {
	ID         uint  `gorm:"primaryKey"`
	CoachID    uint  `gorm:"index;not null"`
	TemplateID int32 `gorm:"not null"`
	Quantity   int16 `gorm:"not null;default:1"`
	Pos        int16 `gorm:"not null;default:0"`
}

// Mailbox limits enforced by the client and mirrored server-side.
const (
	// MailboxCapacity is how many mails a coach may hold before sending to them
	// fails ("votre boîte aux lettres est pleine (20 messages)").
	MailboxCapacity = 20
	// MailMaxAttachments is the per-mail attachment cap.
	MailMaxAttachments = 10
	// MailMaxTitle / MailMaxBody are the client's input limits.
	MailMaxTitle = 100
	MailMaxBody  = 800
)

// Mail is one in-game letter between coaches, optionally carrying card
// attachments. A mail stays in the row until BOTH sides have deleted it, so each
// side can delete independently (the client filters on its own flag).
type Mail struct {
	ID                uint   `gorm:"primaryKey"`
	SenderID          uint   `gorm:"index;not null"`
	SenderName        string `gorm:"not null"`
	ReceiverID        uint   `gorm:"index;not null"`
	ReceiverName      string `gorm:"not null"`
	Title             string `gorm:"not null"`
	Body              string `gorm:"not null"`
	SentAtMillis      int64  `gorm:"not null"` // unix millis; the wire carries millis
	Read              bool   `gorm:"not null;default:false"`
	DeletedBySender   bool   `gorm:"not null;default:false"`
	DeletedByReceiver bool   `gorm:"not null;default:false"`
	// Cards are the attachments still waiting to be collected.
	Cards []MailCard `gorm:"foreignKey:MailID;constraint:OnDelete:CASCADE"`
}

// MailCard is one card attached to a Mail. TemplateID references a gamedata card
// template (not a FK). Rows are deleted as the receiver collects them.
type MailCard struct {
	ID         uint  `gorm:"primaryKey"`
	MailID     uint  `gorm:"index;not null"`
	TemplateID int32 `gorm:"not null"`
}

// CoachFriend is a directed friend edge Owner -> Friend.
type CoachFriend struct {
	ID       uint   `gorm:"primaryKey"`
	OwnerID  uint   `gorm:"uniqueIndex:idx_friend_pair;not null"`
	FriendID uint   `gorm:"uniqueIndex:idx_friend_pair;not null"`
	Notify   bool   `gorm:"not null;default:true"`
	Friend   *Coach `gorm:"foreignKey:FriendID"`
}

// CoachCurrency is one balance slot of a coach's multi-currency "token" wallet.
// CurrencyType is the byte currency id used by the client (5403/4001 maps);
// type 1 is the primary shop token. Amount is the balance for that type.
type CoachCurrency struct {
	ID           uint  `gorm:"primaryKey"`
	CoachID      uint  `gorm:"uniqueIndex:idx_wallet_pair;not null"`
	CurrencyType uint8 `gorm:"uniqueIndex:idx_wallet_pair;not null"`
	Amount       int32 `gorm:"not null;default:0"`
}

// CoachIgnored is a directed ignore edge Owner -> Ignored.
type CoachIgnored struct {
	ID        uint   `gorm:"primaryKey"`
	OwnerID   uint   `gorm:"uniqueIndex:idx_ignore_pair;not null"`
	IgnoredID uint   `gorm:"uniqueIndex:idx_ignore_pair;not null"`
	Ignored   *Coach `gorm:"foreignKey:IgnoredID"`
}

// TableName overrides to match a stable snake_case naming.
func (Account) TableName() string       { return "accounts" }
func (*Coach) TableName() string        { return "coaches" }
func (CoachCard) TableName() string     { return "coach_cards" }
func (CoachFriend) TableName() string   { return "coach_friends" }
func (CoachIgnored) TableName() string  { return "coach_ignored" }
func (CoachCurrency) TableName() string { return "coach_currencies" }
func (CoachStat) TableName() string     { return "coach_stats" }
