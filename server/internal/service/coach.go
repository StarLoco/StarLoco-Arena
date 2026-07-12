package service

import (
	"context"
	"errors"
	"fmt"
	"math"
	"strings"

	"gorm.io/gorm"

	"github.com/dofusarena/go-server/internal/domain"
)

// CoachCreationResult mirrors the legacy COACH_CREATION_RESULT codes, see
// docs/02-protocol.md §2.4.2.
type CoachCreationResult byte

const (
	CoachCreationOK          CoachCreationResult = 0
	CoachCreationInvalidName CoachCreationResult = 11
	CoachCreationNameTaken   CoachCreationResult = 12
)

// forbiddenNameFragments mirrors CoachCreation.java's forbidden list:
// characters/substrings disallowed anywhere in a coach name.
var forbiddenNameFragments = []string{
	"admin", "modo", " ", "&", "'", "\"", "(", "-", "_", ")", "=", "~", "#",
	"{", "[", "|", "`", "^", "@", "]", "}", "+", "$", "*", ",", ";", ":",
	"!", "<", ">", "%", "?", ".", "/", "\n",
}

// CoachService handles coach creation and profile lookups.
type CoachService struct {
	db *gorm.DB
}

// NewCoachService constructs a CoachService bound to db.
func NewCoachService(db *gorm.DB) *CoachService {
	return &CoachService{db: db}
}

// DB exposes the underlying *gorm.DB for read-only ad-hoc lookups from the
// dispatch layer (e.g. resolving a coach by name for social/chat opcodes)
// that don't warrant their own dedicated service method.
func (s *CoachService) DB() *gorm.DB {
	return s.db
}

// CreateCoach validates and persists a new coach for accountID, and links
// the account to it. Name casing is normalized to Title-case, matching the
// legacy server's `substring(0,1).toUpperCase() + substring(1)` behavior.
func (s *CoachService) CreateCoach(ctx context.Context, accountID uint, name string, skin, hair, sex uint8) (*domain.Coach, CoachCreationResult, error) {
	lower := strings.ToLower(name)
	for _, frag := range forbiddenNameFragments {
		if strings.Contains(lower, frag) {
			return nil, CoachCreationInvalidName, nil
		}
	}
	if len(lower) == 0 {
		return nil, CoachCreationInvalidName, nil
	}
	normalized := strings.ToUpper(lower[:1]) + lower[1:]

	var existing domain.Coach
	err := s.db.WithContext(ctx).Where("name = ?", normalized).First(&existing).Error
	if err == nil {
		return nil, CoachCreationNameTaken, nil
	}
	if !errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, CoachCreationInvalidName, fmt.Errorf("service: check coach name: %w", err)
	}

	coach := &domain.Coach{
		Name: normalized,
		Skin: skin,
		Hair: hair,
		Sex:  sex,
		PosX: 1,
		PosY: 1,
		PosZ: 0,
	}

	err = s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		if err := tx.Create(coach).Error; err != nil {
			return err
		}
		return tx.Model(&domain.Account{}).Where("id = ?", accountID).Update("coach_id", coach.ID).Error
	})
	if err != nil {
		return nil, CoachCreationInvalidName, fmt.Errorf("service: create coach: %w", err)
	}

	return coach, CoachCreationOK, nil
}

// GetCoachByAccountID loads the coach linked to an account, including
// inventory and social lists (with the target coach's name preloaded)
// needed for the post-login information broadcast (docs/02-protocol.md
// §2.4.3).
func (s *CoachService) GetCoachByAccountID(ctx context.Context, accountID uint) (*domain.Coach, error) {
	var account domain.Account
	err := s.db.WithContext(ctx).
		Preload("Coach.Inventory").
		Preload("Coach.Friends.Friend").
		Preload("Coach.Ignored.Ignored").
		First(&account, accountID).Error
	if err != nil {
		return nil, fmt.Errorf("service: load account %d: %w", accountID, err)
	}
	return account.Coach, nil
}

// GetByID loads a coach by ID with no associations preloaded.
func (s *CoachService) GetByID(ctx context.Context, coachID uint) (*domain.Coach, error) {
	var coach domain.Coach
	if err := s.db.WithContext(ctx).First(&coach, coachID).Error; err != nil {
		return nil, fmt.Errorf("service: load coach %d: %w", coachID, err)
	}
	return &coach, nil
}

// UpdatePosition persists a coach's world position, mirroring
// Coach.setPosition + the implicit save on next update
// (Coach.java:118-122).
func (s *CoachService) UpdatePosition(ctx context.Context, coachID uint, x, y int32, z int16) error {
	err := s.db.WithContext(ctx).Model(&domain.Coach{}).Where("id = ?", coachID).
		Updates(map[string]any{"pos_x": x, "pos_y": y, "pos_z": z}).Error
	if err != nil {
		return fmt.Errorf("service: update coach %d position: %w", coachID, err)
	}
	return nil
}

// ApplyFightResult atomically records the outcome of a finished fight on a
// coach's persisted statistics (backing the "Statistiques du Coach" window
// and the ladder Level/Rank) and returns the updated coach so the caller
// can serialize END_FIGHT's per-coach Strength/Report and a fresh
// PLAYER_STATISTICS_REPORT. It:
//
//   - increments StatFights, and StatWins or StatLosses;
//   - bumps ConsecutiveWins on a win, resets it to 0 on a loss;
//   - adds fightDurationSecs to TimeInFightSecs;
//   - shifts Strength by the fixed ladder delta (seeding an unranked coach
//     first), clamped to the ranked range (see domain.ApplyFightStrength).
//
// The whole read-modify-write runs in one transaction so concurrent fight
// endings for the same coach can't lose an update.
func (s *CoachService) ApplyFightResult(ctx context.Context, coachID uint, won bool, fightDurationSecs int64) (*domain.Coach, error) {
	var coach domain.Coach
	err := s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		if err := tx.First(&coach, coachID).Error; err != nil {
			return err
		}

		coach.StatFights++
		if won {
			coach.StatWins++
			coach.ConsecutiveWins++
		} else {
			coach.StatLosses++
			coach.ConsecutiveWins = 0
		}
		coach.TimeInFightSecs += fightDurationSecs
		coach.Strength = domain.ApplyFightStrength(coach.Strength, won)

		return tx.Model(&coach).Updates(map[string]any{
			"stat_fights":        coach.StatFights,
			"stat_wins":          coach.StatWins,
			"stat_losses":        coach.StatLosses,
			"consecutive_wins":   coach.ConsecutiveWins,
			"time_in_fight_secs": coach.TimeInFightSecs,
			"strength":           coach.Strength,
		}).Error
	})
	if err != nil {
		return nil, fmt.Errorf("service: apply fight result for coach %d: %w", coachID, err)
	}
	return &coach, nil
}

// MaxLockedCards is the maximum number of coach cards a coach may keep
// "locked" (protected from being staked in a bet fight), mirroring the
// client's hardcoded cap (CoachCardInventories.canLockCard's
// `getLockedItemsCount() < 10` and LocalCoach.lockedCardsMaxCount = 10).
const MaxLockedCards = 10

// SelectStakeCard picks one random UNLOCKED, unequipped (pos=0) coach card
// from a coach's inventory to put at stake in a bet fight, mirroring the
// card-wagering rule "each coach stakes one random non-locked card." Returns
// (nil, nil) if the coach has no eligible card -- the caller must then reject
// the bet fight (CancelReasonCantHoldTheBet). Selection uses the fight's
// RNG-independent random source (math/rand via ORDER BY RANDOM()) so it isn't
// tied to the deterministic combat RNG.
//
// A locked OR equipped (pos>0) OR quantity<=0 card is never eligible: locked
// cards are explicitly protected, and an equipped card is in active use as
// part of the coach's on-map look. The cursed flag does NOT exclude a card
// from being staked (a cursed card is a normal, wager-able card that merely
// stacks differently on receipt).
func (s *CoachService) SelectStakeCard(ctx context.Context, coachID uint) (*domain.CoachCard, error) {
	var card domain.CoachCard
	err := s.db.WithContext(ctx).
		Where("coach_id = ? AND pos = 0 AND quantity > 0 AND flag & ? = 0", coachID, domain.CoachCardFlagLocked).
		Order("RANDOM()").
		First(&card).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, nil
	}
	if err != nil {
		return nil, fmt.Errorf("service: select stake card for coach %d: %w", coachID, err)
	}
	return &card, nil
}

// TransferCard moves one unit of a staked card from the loser to the winner
// at fight end, transactionally, mirroring the server-authoritative card
// grant the client's END_FIGHT payload only DISPLAYS. It:
//   - re-verifies the loser still owns the card (by id+coachID) and that it
//     is still unlocked and unequipped (guards against a mid-fight lock/equip
//     race -- if it changed, the transfer is skipped and (nil,nil) returned);
//   - decrements the loser's stack (deleting the row at quantity 0), so a
//     multi-quantity stack only loses one card;
//   - grants one unit to the winner via the same stacking/cursed rules as
//     AddCard (mirrors CoachCardInventories.addToInventory, including the
//     cursed-stacking special case handled inside AddCard's callers here we
//     simply reuse AddCard which stacks by template+pos=0).
//
// Returns the transferred card's template id + cursed flag (for the END_FIGHT
// won/lost blobs), or (nil, ...) if nothing was transferred (card gone/locked).
func (s *CoachService) TransferCard(ctx context.Context, loserID, winnerID, cardID uint) (*TransferredCard, error) {
	var out *TransferredCard
	err := s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		var card domain.CoachCard
		err := tx.Where("id = ? AND coach_id = ?", cardID, loserID).First(&card).Error
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil // loser no longer owns it -- skip silently
		}
		if err != nil {
			return err
		}
		// Re-check eligibility at transfer time (mid-fight the card must not
		// have become locked or equipped).
		if card.Flag&domain.CoachCardFlagLocked != 0 || card.Pos != 0 || card.Quantity <= 0 {
			return nil
		}

		cursed := card.Flag&domain.CoachCardFlagCursed != 0
		templateID := card.TemplateID

		// Remove one unit from the loser.
		if card.Quantity <= 1 {
			if err := tx.Delete(&card).Error; err != nil {
				return err
			}
		} else {
			card.Quantity--
			if err := tx.Save(&card).Error; err != nil {
				return err
			}
		}

		// Grant one unit to the winner, stacking onto an existing unequipped
		// card of the same template if present (mirrors AddCard, inlined here
		// so it participates in the same transaction).
		var existing domain.CoachCard
		wErr := tx.Where("coach_id = ? AND template_id = ? AND pos = 0", winnerID, templateID).First(&existing).Error
		switch {
		case wErr == nil:
			existing.Quantity++
			if err := tx.Save(&existing).Error; err != nil {
				return err
			}
		case errors.Is(wErr, gorm.ErrRecordNotFound):
			flag := uint8(0)
			if cursed {
				flag = domain.CoachCardFlagCursed
			}
			granted := &domain.CoachCard{CoachID: winnerID, TemplateID: templateID, Quantity: 1, Pos: 0, Flag: flag}
			if err := tx.Create(granted).Error; err != nil {
				return err
			}
		default:
			return wErr
		}

		out = &TransferredCard{TemplateID: templateID, Cursed: cursed}
		return nil
	})
	if err != nil {
		return nil, fmt.Errorf("service: transfer card %d from coach %d to %d: %w", cardID, loserID, winnerID, err)
	}
	return out, nil
}

// TransferredCard is the outcome of a successful stake transfer, used to
// populate the END_FIGHT won/lost card blobs (template id + cursed flag,
// exactly what the wire format carries).
type TransferredCard struct {
	TemplateID int32
	Cursed     bool
}

// ExchangeOffer is one card a side of an item-exchange is giving away,
// identified by the giver's CoachCard instance id and the quantity offered.
// The service re-validates every field at commit time (ownership, lock,
// equip, quantity) -- callers must NOT assume the client-supplied values
// are still valid.
type ExchangeOffer struct {
	CoachCardID uint
	Quantity    int16
}

// ExchangeResult is the outcome of CompleteExchange: for each side, the
// giver's removed card instance ids and the receiver's granted cards, used
// to build the two INVENTORY_UPDATE frames.
type ExchangeResult struct {
	FromRemovedCardIDs []uint
	ToRemovedCardIDs   []uint
	FromGranted        []domain.CoachCard // cards the FROM coach received
	ToGranted          []domain.CoachCard // cards the TO coach received
}

// CompleteExchange atomically performs a two-sided item exchange in a
// SINGLE transaction, re-validating every offered card at commit time.
// This closes the exchange card-duplication/theft vectors: the previous
// implementation removed and granted cards in separate, unsynchronized
// autocommit statements with no commit-time ownership/lock/pos/quantity
// re-check, and deleted the whole stack regardless of the offered
// quantity.
//
// For every offered card (on both sides) the transaction:
//   - re-loads the card by (id, giver) INSIDE the tx -- a card sold/traded/
//     staked/locked/equipped since it was added to the offer is rejected;
//   - requires it to be unequipped (pos == 0) and unlocked, matching the
//     wager-protection invariant enforced elsewhere (a locked/equipped card
//     must never be tradeable);
//   - clamps the transferred quantity to what the giver actually still owns
//     (>= 1), and decrements the giver's stack by exactly that amount
//     (deleting the row only at quantity 0) instead of deleting the whole
//     row;
//   - grants that many units to the receiver, stacking onto an existing
//     unequipped same-template card, stamping received cards CURSED
//     (CoachExchange.ok()).
//
// If ANY offered card fails validation the whole exchange is aborted (no
// partial transfer) and (nil, nil) is returned -- the caller should tear
// the exchange down without transferring anything, which is the safe
// behavior (a well-behaved client never offers an invalid card).
//
// CRITICAL: abort MUST roll the transaction back. gorm's Transaction helper
// only rolls back when the callback returns a non-nil error (a nil return
// COMMITS whatever was staged). Since applyExchangeSide writes each offer's
// remove/grant as it goes, a naive `return nil` on a mid-way abort would
// COMMIT the writes done before the abort point -- e.g. a valid FROM side
// plus an invalid TO side would leave the FROM->TO transfer persisted with
// nothing coming back (one-sided theft). We therefore return the sentinel
// errExchangeAborted to force a rollback, and translate it back to
// (nil, nil) for the caller so an abort is not treated as a DB error.
func (s *CoachService) CompleteExchange(ctx context.Context, fromID, toID uint, fromOffers, toOffers []ExchangeOffer) (*ExchangeResult, error) {
	result := &ExchangeResult{}

	err := s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		// Give the FROM coach's offers to TO, and TO's offers to FROM. Both
		// run in the same tx so the whole swap is atomic.
		fromRemoved, toGranted, err := applyExchangeSide(tx, fromID, toID, fromOffers)
		if err != nil {
			return err // errExchangeAborted or a real DB error -> rollback
		}
		toRemoved, fromGranted, err := applyExchangeSide(tx, toID, fromID, toOffers)
		if err != nil {
			return err
		}
		result.FromRemovedCardIDs = fromRemoved
		result.ToRemovedCardIDs = toRemoved
		result.ToGranted = toGranted
		result.FromGranted = fromGranted
		return nil
	})
	if errors.Is(err, errExchangeAborted) {
		return nil, nil // validation abort: rolled back, nothing transferred
	}
	if err != nil {
		return nil, fmt.Errorf("service: complete exchange %d<->%d: %w", fromID, toID, err)
	}
	return result, nil
}

// errExchangeAborted is the sentinel returned by applyExchangeSide when an
// offered card fails commit-time validation. It is not a DB error -- it
// exists solely to force gorm's Transaction to roll back (a nil return
// would COMMIT partial writes). CompleteExchange maps it back to a clean
// (nil, nil) "aborted, nothing transferred" result.
var errExchangeAborted = errors.New("service: exchange aborted -- offered card no longer valid")

// applyExchangeSide transfers giverID's offered cards to receiverID within
// an open transaction. Returns the removed card ids and the receiver's
// granted cards, or errExchangeAborted if any offer failed commit-time
// validation (which the caller must propagate so the whole transaction
// rolls back -- no partial transfer). A genuine DB error is returned as-is
// and likewise rolls the transaction back.
func applyExchangeSide(tx *gorm.DB, giverID, receiverID uint, offers []ExchangeOffer) (removed []uint, granted []domain.CoachCard, err error) {
	for _, offer := range offers {
		var card domain.CoachCard
		lookupErr := tx.Where("id = ? AND coach_id = ?", offer.CoachCardID, giverID).First(&card).Error
		if errors.Is(lookupErr, gorm.ErrRecordNotFound) {
			return nil, nil, errExchangeAborted // giver no longer owns it -- abort
		}
		if lookupErr != nil {
			return nil, nil, lookupErr
		}
		// Re-validate eligibility at commit time.
		if card.Flag&domain.CoachCardFlagLocked != 0 || card.Pos != 0 || card.Quantity <= 0 {
			return nil, nil, errExchangeAborted
		}
		// Clamp the transferred amount to what the giver still owns.
		qty := offer.Quantity
		if qty < 1 {
			qty = 1
		}
		if qty > card.Quantity {
			qty = card.Quantity
		}

		templateID := card.TemplateID

		// Remove `qty` units from the giver.
		if card.Quantity <= qty {
			if err := tx.Delete(&card).Error; err != nil {
				return nil, nil, err
			}
		} else {
			card.Quantity -= qty
			if err := tx.Save(&card).Error; err != nil {
				return nil, nil, err
			}
		}
		removed = append(removed, offer.CoachCardID)

		// Grant `qty` units to the receiver, stamped CURSED, stacking onto
		// an existing unequipped same-template card. Trade-received cards
		// are always cursed, matching CoachExchange.ok()
		// (CoachExchange.java:131,140).
		flag := domain.CoachCardFlagCursed
		var existing domain.CoachCard
		wErr := tx.Where("coach_id = ? AND template_id = ? AND pos = 0", receiverID, templateID).First(&existing).Error
		switch {
		case wErr == nil:
			existing.Quantity += qty
			if err := tx.Save(&existing).Error; err != nil {
				return nil, nil, err
			}
			granted = append(granted, existing)
		case errors.Is(wErr, gorm.ErrRecordNotFound):
			newCard := domain.CoachCard{CoachID: receiverID, TemplateID: templateID, Quantity: qty, Pos: 0, Flag: flag}
			if err := tx.Create(&newCard).Error; err != nil {
				return nil, nil, err
			}
			granted = append(granted, newCard)
		default:
			return nil, nil, wErr
		}
	}
	return removed, granted, nil
}

// AddPlayTime accrues elapsed session seconds onto a coach's lifetime
// TotalPlayTimeSecs ("Temps total de jeu"), called once per session at
// disconnect. A non-positive delta is a no-op.
func (s *CoachService) AddPlayTime(ctx context.Context, coachID uint, seconds int64) error {
	if seconds <= 0 {
		return nil
	}
	err := s.db.WithContext(ctx).Model(&domain.Coach{}).Where("id = ?", coachID).
		UpdateColumn("total_play_time_secs", gorm.Expr("total_play_time_secs + ?", seconds)).Error
	if err != nil {
		return fmt.Errorf("service: add play time for coach %d: %w", coachID, err)
	}
	return nil
}

// AddCard adds (or stacks onto an existing unequipped card of the same
// template) a CoachCard to a coach's inventory, mirroring
// Coach.addCard(CoachCard) (Coach.java:313-323). flag is the initial flag
// byte for a newly-created card (ignored when stacking onto an existing
// one, matching Java, which never touches the existing card's flag when
// stacking quantity). Callers should pass an explicit flag rather than
// assuming a blanket default: the legacy Java call sites disagree on this
// -- CoachExchange.ok() creates trade-received cards with FLAG_CURSED
// (CoachExchange.java:131,140), while VicinityMessage.java's /CARD and
// /ALLCARDS GM commands create granted cards with flag 0 (not cursed,
// VicinityMessage.java:58,76). See docs/08-java-parity-roadmap.md §8.2.
func (s *CoachService) AddCard(ctx context.Context, coachID uint, templateID int32, quantity int16, flag uint8) (*domain.CoachCard, error) {
	var existing domain.CoachCard
	err := s.db.WithContext(ctx).Where("coach_id = ? AND template_id = ? AND pos = 0", coachID, templateID).First(&existing).Error
	if err == nil {
		// Clamp the stacked quantity to the int16 max so a huge stack can't
		// wrap negative (defense-in-depth: transfer/exchange paths already
		// clamp to owned amounts, but AddCard is also reached from grants).
		sum := int32(existing.Quantity) + int32(quantity)
		if sum > math.MaxInt16 {
			sum = math.MaxInt16
		}
		existing.Quantity = int16(sum)
		if err := s.db.WithContext(ctx).Save(&existing).Error; err != nil {
			return nil, fmt.Errorf("service: stack card: %w", err)
		}
		return &existing, nil
	}
	if !errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, fmt.Errorf("service: check existing card: %w", err)
	}

	card := &domain.CoachCard{
		CoachID:    coachID,
		TemplateID: templateID,
		Quantity:   quantity,
		Pos:        0,
		Flag:       flag,
	}
	if err := s.db.WithContext(ctx).Create(card).Error; err != nil {
		return nil, fmt.Errorf("service: create card: %w", err)
	}
	return card, nil
}

// RemoveCard deletes a card from a coach's inventory by its instance ID,
// scoped to coachID for ownership verification, mirroring
// Coach.removeCard(long) which operates on `this.inventory`
// (Coach.java:325-335).
func (s *CoachService) RemoveCard(ctx context.Context, coachID uint, cardID uint) (bool, error) {
	res := s.db.WithContext(ctx).Where("id = ? AND coach_id = ?", cardID, coachID).Delete(&domain.CoachCard{})
	if res.Error != nil {
		return false, fmt.Errorf("service: remove card %d: %w", cardID, res.Error)
	}
	return res.RowsAffected > 0, nil
}

// SetCardFlag overwrites a single card's entire flag byte, scoped to
// coachID for ownership verification. Kept as a general-purpose setter, but
// lock/unlock should use LockCard/UnlockCard below instead of calling this
// directly: those correctly toggle only the locked bit rather than
// clobbering other flag bits (e.g. the cursed bit).
func (s *CoachService) SetCardFlag(ctx context.Context, coachID uint, cardID uint, flag uint8) (*domain.CoachCard, error) {
	var card domain.CoachCard
	if err := s.db.WithContext(ctx).Where("id = ? AND coach_id = ?", cardID, coachID).First(&card).Error; err != nil {
		return nil, fmt.Errorf("service: load card %d: %w", cardID, err)
	}
	card.Flag = flag
	if err := s.db.WithContext(ctx).Save(&card).Error; err != nil {
		return nil, fmt.Errorf("service: update card %d flag: %w", cardID, err)
	}
	return &card, nil
}

// LockCard sets the CoachCardFlagLocked bit on a card, scoped to coachID
// for ownership verification, leaving any other flag bits (e.g. cursed)
// untouched. Mirrors the client's own AbstractCoachCard.setLocked(true)
// (bitwise OR), which is the correct symmetric counterpart to UnlockCard
// below -- unlike the legacy Java *server*'s CoachInventoryUpdateRequest,
// which set the whole flag byte to FLAG_LOCKED, clobbering the cursed bit
// (see docs/08-java-parity-roadmap.md §8.2).
func (s *CoachService) LockCard(ctx context.Context, coachID uint, cardID uint) (*domain.CoachCard, error) {
	var card domain.CoachCard
	// The load, cap-count, and save run in ONE transaction so two
	// concurrent lock requests can't both read a count of 9 and each write
	// a 10th lock, exceeding the cap (the check-then-act race). On SQLite
	// the single-writer pool already serializes this, but the transaction
	// makes the invariant correct on any driver.
	err := s.db.WithContext(ctx).Transaction(func(tx *gorm.DB) error {
		if err := tx.Where("id = ? AND coach_id = ?", cardID, coachID).First(&card).Error; err != nil {
			return fmt.Errorf("service: load card %d: %w", cardID, err)
		}
		// Already locked: idempotent no-op (don't count it against the cap twice).
		if card.Flag&domain.CoachCardFlagLocked != 0 {
			return nil
		}
		// Enforce the client's cap (CoachCardInventories.canLockCard: max
		// MaxLockedCards locked at once). Beyond the cap the lock is
		// silently refused -- the client already greys out the lock action
		// past 10, so a well-behaved client never sends an over-cap lock,
		// but the server must not trust it (a locked card is protected from
		// being wagered).
		var lockedCount int64
		if err := tx.Model(&domain.CoachCard{}).
			Where("coach_id = ? AND flag & ? != 0", coachID, domain.CoachCardFlagLocked).
			Count(&lockedCount).Error; err != nil {
			return fmt.Errorf("service: count locked cards for coach %d: %w", coachID, err)
		}
		if lockedCount >= MaxLockedCards {
			return nil // at the cap; leave this card unlocked
		}
		card.Flag |= domain.CoachCardFlagLocked
		if err := tx.Save(&card).Error; err != nil {
			return fmt.Errorf("service: lock card %d: %w", cardID, err)
		}
		return nil
	})
	if err != nil {
		return nil, err
	}
	return &card, nil
}

// UnlockCard clears the CoachCardFlagLocked bit on a card, scoped to
// coachID for ownership verification, leaving any other flag bits (e.g.
// cursed) untouched. This is the fix for the bug documented in
// docs/08-java-parity-roadmap.md §8.2: the legacy Java server's unlock
// branch set the whole flag byte to FLAG_CURSED instead of clearing the
// locked bit -- clearly a copy-paste mistake, since the client's own
// setLocked(false) correctly does a bitwise clear, not an overwrite.
func (s *CoachService) UnlockCard(ctx context.Context, coachID uint, cardID uint) (*domain.CoachCard, error) {
	var card domain.CoachCard
	if err := s.db.WithContext(ctx).Where("id = ? AND coach_id = ?", cardID, coachID).First(&card).Error; err != nil {
		return nil, fmt.Errorf("service: load card %d: %w", cardID, err)
	}
	card.Flag &^= domain.CoachCardFlagLocked
	if err := s.db.WithContext(ctx).Save(&card).Error; err != nil {
		return nil, fmt.Errorf("service: unlock card %d: %w", cardID, err)
	}
	return &card, nil
}

// SetCardPosition moves a card between inventory (pos=0) and an equipment
// slot (pos>0), scoped to coachID for ownership verification, mirroring the
// loop in CoachEquipmentUpdateRequest.java:36-53.
func (s *CoachService) SetCardPosition(ctx context.Context, coachID uint, cardID uint, pos int16) (*domain.CoachCard, error) {
	var card domain.CoachCard
	if err := s.db.WithContext(ctx).Where("id = ? AND coach_id = ?", cardID, coachID).First(&card).Error; err != nil {
		return nil, fmt.Errorf("service: load card %d: %w", cardID, err)
	}
	card.Pos = pos
	if err := s.db.WithContext(ctx).Save(&card).Error; err != nil {
		return nil, fmt.Errorf("service: update card %d position: %w", cardID, err)
	}
	return &card, nil
}

// GetCardByID loads a single card, scoped to coachID for ownership
// verification.
func (s *CoachService) GetCardByID(ctx context.Context, coachID uint, cardID uint) (*domain.CoachCard, error) {
	var card domain.CoachCard
	if err := s.db.WithContext(ctx).Where("id = ? AND coach_id = ?", cardID, coachID).First(&card).Error; err != nil {
		return nil, fmt.Errorf("service: load card %d for coach %d: %w", cardID, coachID, err)
	}
	return &card, nil
}

// GetEquippedCards returns every card currently equipped (Pos != 0) for a
// coach, mirroring the inventory.stream().filter(pos != 0) pattern used
// throughout the legacy server.
func (s *CoachService) GetEquippedCards(ctx context.Context, coachID uint) ([]domain.CoachCard, error) {
	var cards []domain.CoachCard
	if err := s.db.WithContext(ctx).Where("coach_id = ? AND pos != 0", coachID).Find(&cards).Error; err != nil {
		return nil, fmt.Errorf("service: load equipped cards for coach %d: %w", coachID, err)
	}
	return cards, nil
}

// UnequipAll sets every equipped card (Pos != 0) back to inventory (Pos = 0)
// for a coach, in a single UPDATE. Used at the start of an equipment update
// so the incoming slot layout is applied from a clean slate, mirroring
// CoachEquipmentUpdateRequest.java:24-34's "unequip everything, then
// re-equip" approach.
func (s *CoachService) UnequipAll(ctx context.Context, coachID uint) error {
	err := s.db.WithContext(ctx).Model(&domain.CoachCard{}).
		Where("coach_id = ? AND pos != 0", coachID).
		Update("pos", 0).Error
	if err != nil {
		return fmt.Errorf("service: unequip all cards for coach %d: %w", coachID, err)
	}
	return nil
}
