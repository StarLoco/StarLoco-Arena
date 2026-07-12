// Package domain contains the GORM-mapped persistence models for the game
// server. See go-server/docs/03-data-model.md for the full schema
// rationale and the differences from the legacy OrmLite/Java entities.
package domain

import "time"

// Account is a login identity. Exactly one optional Coach is associated
// once the player completes character creation.
type Account struct {
	ID           uint   `gorm:"primaryKey"`
	Name         string `gorm:"uniqueIndex;size:64;not null"`
	PasswordHash string `gorm:"size:60;not null"` // bcrypt hash, never a raw password
	Connected    bool   `gorm:"not null;default:false"`

	// IsAdmin gates the GM chat-cheat-commands (docs/08-java-parity-roadmap.md
	// §8.4) and the web portal's admin console -- account management and
	// impersonation (docs/10-web-portal.md).
	IsAdmin bool `gorm:"not null;default:false"`

	CoachID *uint  `gorm:"index"`
	Coach   *Coach `gorm:"foreignKey:CoachID"`

	CreatedAt time.Time
	UpdatedAt time.Time
}

func (Account) TableName() string { return "accounts" }
