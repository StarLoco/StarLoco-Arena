package service

import (
	"context"
	"errors"
	"fmt"
	"strings"

	"golang.org/x/crypto/bcrypt"
	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
)

// AccountService handles account lifecycle concerns that live outside the
// game wire-protocol: web-portal registration, password changes, and the
// admin console's account management (list/create/delete/flag) plus the
// deep per-account data aggregation used by the admin detail view. See
// docs/10-web-portal.md.
//
// It is deliberately separate from AuthService (which owns the login
// handshake's connected-flag bookkeeping) so the web layer can depend on
// account CRUD without pulling in game-session semantics.
type AccountService struct {
	db *gorm.DB
}

// NewAccountService constructs an AccountService bound to db.
func NewAccountService(db *gorm.DB) *AccountService {
	return &AccountService{db: db}
}

// DB exposes the underlying handle for ad-hoc read-only lookups.
func (s *AccountService) DB() *gorm.DB { return s.db }

// Registration/validation errors returned by Register. They are sentinel
// values so the web layer can map them to friendly, field-specific form
// messages without string matching.
var (
	ErrAccountNameTaken   = errors.New("service: account name already taken")
	ErrAccountNameInvalid = errors.New("service: account name invalid")
	ErrPasswordTooShort   = errors.New("service: password too short")
	ErrAccountNotFound    = errors.New("service: account not found")
	ErrWrongPassword      = errors.New("service: wrong password")
)

// Account-name constraints for web registration. The game protocol itself
// never exposed registration, so these rules are new policy (kept liberal
// but safe): 3-64 chars, alphanumeric plus a small punctuation set.
const (
	minAccountNameLen = 3
	maxAccountNameLen = 64
	minPasswordLen    = 6
)

// validAccountNameChar reports whether r is allowed in an account name.
func validAccountNameChar(r rune) bool {
	switch {
	case r >= 'a' && r <= 'z':
		return true
	case r >= 'A' && r <= 'Z':
		return true
	case r >= '0' && r <= '9':
		return true
	case r == '_' || r == '-' || r == '.':
		return true
	default:
		return false
	}
}

// ValidateName checks an account name against the registration policy,
// returning ErrAccountNameInvalid on any violation. Exported so the web
// layer can pre-validate before hitting the DB.
func ValidateName(name string) error {
	if len(name) < minAccountNameLen || len(name) > maxAccountNameLen {
		return ErrAccountNameInvalid
	}
	for _, r := range name {
		if !validAccountNameChar(r) {
			return ErrAccountNameInvalid
		}
	}
	return nil
}

// ValidatePassword checks a plaintext password against the registration
// policy, returning ErrPasswordTooShort if it fails.
func ValidatePassword(password string) error {
	if len(password) < minPasswordLen {
		return ErrPasswordTooShort
	}
	return nil
}

// Register creates a new account with a bcrypt-hashed password. It
// validates the name/password policy and enforces name uniqueness
// (case-insensitive). isAdmin is honored so the same path can seed the
// first admin, but the public web registration handler always passes
// false.
func (s *AccountService) Register(ctx context.Context, name, password string, isAdmin bool) (*domain.Account, error) {
	name = strings.TrimSpace(name)
	if err := ValidateName(name); err != nil {
		return nil, err
	}
	if err := ValidatePassword(password); err != nil {
		return nil, err
	}

	// Case-insensitive uniqueness so "Bob" and "bob" can't both register;
	// the login lookup is exact-match by design, so we normalize the check
	// here rather than lowercasing the stored name.
	var count int64
	if err := s.db.WithContext(ctx).Model(&domain.Account{}).
		Where("LOWER(name) = LOWER(?)", name).Count(&count).Error; err != nil {
		return nil, fmt.Errorf("service: check account name: %w", err)
	}
	if count > 0 {
		return nil, ErrAccountNameTaken
	}

	hash, err := HashPassword(password)
	if err != nil {
		return nil, err
	}

	account := &domain.Account{Name: name, PasswordHash: hash, IsAdmin: isAdmin}
	if err := s.db.WithContext(ctx).Create(account).Error; err != nil {
		if errors.Is(err, gorm.ErrDuplicatedKey) {
			return nil, ErrAccountNameTaken
		}
		return nil, fmt.Errorf("service: create account: %w", err)
	}
	return account, nil
}

// GetByID loads a bare account row (no associations).
func (s *AccountService) GetByID(ctx context.Context, id uint) (*domain.Account, error) {
	var account domain.Account
	if err := s.db.WithContext(ctx).First(&account, id).Error; err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, ErrAccountNotFound
		}
		return nil, fmt.Errorf("service: load account %d: %w", id, err)
	}
	return &account, nil
}

// GetByName loads a bare account row by exact name (no associations).
func (s *AccountService) GetByName(ctx context.Context, name string) (*domain.Account, error) {
	var account domain.Account
	if err := s.db.WithContext(ctx).Where("name = ?", name).First(&account).Error; err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, ErrAccountNotFound
		}
		return nil, fmt.Errorf("service: load account %q: %w", name, err)
	}
	return &account, nil
}

// VerifyPassword checks a plaintext password against the account's stored
// hash without touching the connected flag (unlike AuthService.Authenticate).
// Used by the web login flow and the change-password re-auth step.
func (s *AccountService) VerifyPassword(ctx context.Context, name, password string) (*domain.Account, error) {
	account, err := s.GetByName(ctx, name)
	if err != nil {
		return nil, err
	}
	if bcrypt.CompareHashAndPassword([]byte(account.PasswordHash), []byte(password)) != nil {
		return nil, ErrWrongPassword
	}
	return account, nil
}

// ChangePassword sets a new bcrypt-hashed password for accountID after
// validating the new password's policy. The caller is responsible for
// re-authenticating the current password first (the web handler does).
func (s *AccountService) ChangePassword(ctx context.Context, accountID uint, newPassword string) error {
	if err := ValidatePassword(newPassword); err != nil {
		return err
	}
	hash, err := HashPassword(newPassword)
	if err != nil {
		return err
	}
	res := s.db.WithContext(ctx).Model(&domain.Account{}).
		Where("id = ?", accountID).Update("password_hash", hash)
	if res.Error != nil {
		return fmt.Errorf("service: change password: %w", res.Error)
	}
	if res.RowsAffected == 0 {
		return ErrAccountNotFound
	}
	return nil
}

// SetAdmin flips an account's admin flag.
func (s *AccountService) SetAdmin(ctx context.Context, accountID uint, isAdmin bool) error {
	res := s.db.WithContext(ctx).Model(&domain.Account{}).
		Where("id = ?", accountID).Update("is_admin", isAdmin)
	if res.Error != nil {
		return fmt.Errorf("service: set admin: %w", res.Error)
	}
	if res.RowsAffected == 0 {
		return ErrAccountNotFound
	}
	return nil
}

// AccountListItem is a lightweight projection for the admin account table:
// enough to render each row (status chips, coach name) without loading full
// coach associations for every account.
type AccountListItem struct {
	ID        uint
	Name      string
	Connected bool
	IsAdmin   bool
	CoachID   *uint
	CoachName string
}

// ListAccountsParams filters/paginates the admin account list.
type ListAccountsParams struct {
	// Search matches (case-insensitive, substring) against the account name
	// or the linked coach name. Empty means no filter.
	Search string
	// Limit/Offset paginate. Limit is clamped to [1, 200]; a zero Limit
	// defaults to 50.
	Limit  int
	Offset int
}

// ListAccounts returns a page of accounts plus the total count matching the
// filter (for pagination UI), joined to the coach name when present.
func (s *AccountService) ListAccounts(ctx context.Context, p ListAccountsParams) (items []AccountListItem, total int64, err error) {
	if p.Limit <= 0 {
		p.Limit = 50
	}
	if p.Limit > 200 {
		p.Limit = 200
	}
	if p.Offset < 0 {
		p.Offset = 0
	}

	base := s.db.WithContext(ctx).
		Table("accounts AS a").
		Joins("LEFT JOIN coachs AS c ON c.id = a.coach_id")

	if search := strings.TrimSpace(p.Search); search != "" {
		like := "%" + strings.ToLower(search) + "%"
		base = base.Where("LOWER(a.name) LIKE ? OR LOWER(c.name) LIKE ?", like, like)
	}

	if err := base.Count(&total).Error; err != nil {
		return nil, 0, fmt.Errorf("service: count accounts: %w", err)
	}

	rows, err := base.
		Select("a.id AS id, a.name AS name, a.connected AS connected, a.is_admin AS is_admin, a.coach_id AS coach_id, c.name AS coach_name").
		Order("a.id ASC").
		Limit(p.Limit).
		Offset(p.Offset).
		Rows()
	if err != nil {
		return nil, 0, fmt.Errorf("service: list accounts: %w", err)
	}
	defer rows.Close()

	for rows.Next() {
		var it AccountListItem
		var coachName *string
		if err := rows.Scan(&it.ID, &it.Name, &it.Connected, &it.IsAdmin, &it.CoachID, &coachName); err != nil {
			return nil, 0, fmt.Errorf("service: scan account row: %w", err)
		}
		if coachName != nil {
			it.CoachName = *coachName
		}
		items = append(items, it)
	}
	if err := rows.Err(); err != nil {
		return nil, 0, fmt.Errorf("service: iterate accounts: %w", err)
	}
	return items, total, nil
}

// AccountDetail is the fully-hydrated view of a single account and every
// piece of data owned through it, powering both the admin deep-view page
// and each user's own portal. Coach may be nil for accounts that never
// completed character creation.
type AccountDetail struct {
	Account domain.Account
	Coach   *domain.Coach
	// Cards is the coach's inventory (equipped and unequipped).
	Cards []domain.CoachCard
	// Fighters with their spell/object loadouts resolved.
	Fighters []FighterDetail
	// Teams with fighter rosters preloaded.
	Teams []domain.Team
	// Friends/Ignored with the target coach's name preloaded.
	Friends []domain.CoachFriend
	Ignored []domain.CoachIgnored
}

// FighterDetail bundles a fighter with its resolved spell/object template
// IDs so the view layer doesn't issue per-fighter queries.
type FighterDetail struct {
	Fighter   domain.Fighter
	SpellIDs  []int32
	ObjectIDs []int32
}

// GetAccountDetail aggregates everything owned by accountID: the account,
// its coach (if any), and the coach's full inventory/fighters/teams/social
// graph. This is the single query surface behind the admin detail page and
// the user's own "all my data" view.
func (s *AccountService) GetAccountDetail(ctx context.Context, accountID uint) (*AccountDetail, error) {
	var account domain.Account
	err := s.db.WithContext(ctx).
		Preload("Coach.Inventory").
		Preload("Coach.Friends.Friend").
		Preload("Coach.Ignored.Ignored").
		First(&account, accountID).Error
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, ErrAccountNotFound
		}
		return nil, fmt.Errorf("service: load account detail %d: %w", accountID, err)
	}

	detail := &AccountDetail{Account: account, Coach: account.Coach}
	if account.Coach == nil {
		return detail, nil
	}

	coachID := account.Coach.ID
	detail.Cards = account.Coach.Inventory
	detail.Friends = account.Coach.Friends
	detail.Ignored = account.Coach.Ignored

	var teams []domain.Team
	if err := s.db.WithContext(ctx).Preload("Fighters").
		Where("coach_id = ?", coachID).Find(&teams).Error; err != nil {
		return nil, fmt.Errorf("service: load teams for coach %d: %w", coachID, err)
	}
	detail.Teams = teams

	var fighters []domain.Fighter
	if err := s.db.WithContext(ctx).Where("coach_id = ?", coachID).
		Order("id ASC").Find(&fighters).Error; err != nil {
		return nil, fmt.Errorf("service: load fighters for coach %d: %w", coachID, err)
	}

	fighterIDs := make([]uint, len(fighters))
	for i, f := range fighters {
		fighterIDs[i] = f.ID
	}
	spellMap, objMap, err := NewFighterService(s.db).LoadoutMaps(ctx, fighterIDs)
	if err != nil {
		return nil, err
	}
	detail.Fighters = make([]FighterDetail, len(fighters))
	for i, f := range fighters {
		detail.Fighters[i] = FighterDetail{
			Fighter:   f,
			SpellIDs:  spellMap[f.ID],
			ObjectIDs: objMap[f.ID],
		}
	}

	return detail, nil
}

// DeleteAccount removes an account and, through it, all owned game data.
// Because accounts.coach_id is ON DELETE SET NULL (not CASCADE), deleting
// the account alone would orphan the coach and everything hanging off it.
// So we delete the coach first (which cascades to cards/fighters/teams/
// social) inside a transaction, then the account row.
//
// It refuses to delete a currently-connected account (returns
// ErrAccountConnected) so the web admin can't yank state out from under a
// live game session; the caller surfaces this as a warning.
func (s *AccountService) DeleteAccount(ctx context.Context, accountID uint) error {
	return s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		var account domain.Account
		if err := tx.First(&account, accountID).Error; err != nil {
			if errors.Is(err, gorm.ErrRecordNotFound) {
				return ErrAccountNotFound
			}
			return fmt.Errorf("service: load account for delete: %w", err)
		}
		if account.Connected {
			return ErrAccountConnected
		}

		if account.CoachID != nil {
			// Clear the FK first so the SET NULL side of the relationship
			// doesn't leave a dangling reference mid-transaction, then hard
			// delete the coach (cascades to all owned rows).
			if err := tx.Model(&domain.Account{}).Where("id = ?", accountID).
				Update("coach_id", nil).Error; err != nil {
				return fmt.Errorf("service: unlink coach: %w", err)
			}
			if err := tx.Delete(&domain.Coach{}, *account.CoachID).Error; err != nil {
				return fmt.Errorf("service: delete coach %d: %w", *account.CoachID, err)
			}
		}

		if err := tx.Delete(&domain.Account{}, accountID).Error; err != nil {
			return fmt.Errorf("service: delete account %d: %w", accountID, err)
		}
		return nil
	})
}

// ErrAccountConnected is returned by DeleteAccount when the target account
// is currently online, to avoid corrupting a live game session's state.
var ErrAccountConnected = errors.New("service: account is currently connected")

// CountAccounts returns the total number of accounts, used for the admin
// dashboard summary tiles.
func (s *AccountService) CountAccounts(ctx context.Context) (int64, error) {
	var n int64
	if err := s.db.WithContext(ctx).Model(&domain.Account{}).Count(&n).Error; err != nil {
		return 0, fmt.Errorf("service: count accounts: %w", err)
	}
	return n, nil
}

// CountConnected returns the number of accounts flagged connected.
func (s *AccountService) CountConnected(ctx context.Context) (int64, error) {
	var n int64
	if err := s.db.WithContext(ctx).Model(&domain.Account{}).
		Where("connected = ?", true).Count(&n).Error; err != nil {
		return 0, fmt.Errorf("service: count connected: %w", err)
	}
	return n, nil
}
