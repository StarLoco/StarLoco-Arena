// Package service implements the game server's business logic: one file
// per feature area, each operating on internal/domain models via GORM and
// producing internal/protocol responses. This layer replaces the legacy
// Java version's "fat entity" classes (e.g. Coach.java mixing persistence,
// business logic, and packet serialization) -- see
// docs/01-architecture.md's package layout rationale.
package service

import (
	"context"
	"errors"
	"fmt"

	"golang.org/x/crypto/bcrypt"
	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
)

// AuthResult is the outcome of an authentication attempt.
type AuthResult int

const (
	AuthResultOK AuthResult = iota
	AuthResultInvalidLogin
	AuthResultAlreadyConnected
)

// AuthService handles login and account-credential concerns. Passwords are
// always bcrypt-hashed at rest and compared via bcrypt, fixing the legacy
// server's cleartext `password.equals()` comparison (see
// docs/02-protocol.md §2.5).
type AuthService struct {
	db *gorm.DB
}

// NewAuthService constructs an AuthService bound to db.
func NewAuthService(db *gorm.DB) *AuthService {
	return &AuthService{db: db}
}

// bcryptCost is deliberately not gorm/bcrypt.DefaultCost's exact value
// spelled out here so it's easy to find/tune; DefaultCost (10) is a
// reasonable default for an interactive login path.
const bcryptCost = bcrypt.DefaultCost

// Authenticate verifies a login/password pair. On success it marks the
// account connected and returns it; callers are responsible for wiring the
// returned account to a live Session.
func (s *AuthService) Authenticate(ctx context.Context, login, password string) (*domain.Account, AuthResult, error) {
	var account domain.Account
	err := s.db.WithContext(ctx).Where("name = ?", login).First(&account).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, AuthResultInvalidLogin, nil
	}
	if err != nil {
		return nil, AuthResultInvalidLogin, fmt.Errorf("service: authenticate query: %w", err)
	}

	if bcrypt.CompareHashAndPassword([]byte(account.PasswordHash), []byte(password)) != nil {
		return nil, AuthResultInvalidLogin, nil
	}

	if account.Connected {
		return &account, AuthResultAlreadyConnected, nil
	}

	account.Connected = true
	if err := s.db.WithContext(ctx).Model(&account).Update("connected", true).Error; err != nil {
		return nil, AuthResultInvalidLogin, fmt.Errorf("service: mark connected: %w", err)
	}

	return &account, AuthResultOK, nil
}

// SetDisconnected marks an account as no longer connected. Called both on
// clean disconnect and, defensively, on every server boot for any rows
// left dangling by an ungraceful shutdown (see docs/06-config-and-ops.md
// §6.4).
func (s *AuthService) SetDisconnected(ctx context.Context, accountID uint) error {
	return s.db.WithContext(ctx).Model(&domain.Account{}).Where("id = ?", accountID).Update("connected", false).Error
}

// ResetAllConnectedFlags clears every account's Connected flag. Run once at
// startup as a defensive cleanup for rows left dangling by a prior
// ungraceful shutdown, mirroring the legacy Database.java constructor's
// cleanup query -- kept here even though graceful shutdown now handles the
// common case, as belt-and-suspenders for crash/kill -9 scenarios.
func (s *AuthService) ResetAllConnectedFlags(ctx context.Context) error {
	return s.db.WithContext(ctx).Model(&domain.Account{}).Where("connected = ?", true).Update("connected", false).Error
}

// HashPassword bcrypt-hashes a plaintext password for storage, used by
// account-creation / registration flows.
func HashPassword(password string) (string, error) {
	hash, err := bcrypt.GenerateFromPassword([]byte(password), bcryptCost)
	if err != nil {
		return "", fmt.Errorf("service: hash password: %w", err)
	}
	return string(hash), nil
}
