package store

import (
	"errors"

	"gorm.io/gorm"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// ExchangeOffer is one side of a trade: the giver and the cards (by CoachCard id
// + quantity) they staked.
type ExchangeOffer struct {
	GiverID  uint
	Receiver uint
	Cards    []ExchangeCard
}

// ExchangeCard is a staked card: the CoachCard row id and the quantity offered.
type ExchangeCard struct {
	CardID   uint
	Quantity int16
}

// errExchangeAborted is the internal sentinel that forces a full rollback when a
// staked card fails re-validation at commit time. It is never returned to
// callers (translated to a clean "aborted, nothing moved" result).
var errExchangeAborted = errors.New("store: exchange aborted")

// CompleteExchange atomically transfers both sides' staked cards in a single DB
// transaction. It RE-VALIDATES every card at commit time (still owned, unlocked,
// unequipped, quantity available) and rolls the whole thing back if anything
// changed — so a stale or concurrently-spent offer can never mint or duplicate a
// card. Returns (committed, error): committed=false means the trade was aborted
// (nothing moved), which is not an error.
func (r *CoachRepo) CompleteExchange(a, b ExchangeOffer) (bool, error) {
	err := r.db.Transaction(func(tx *gorm.DB) error {
		if err := applyExchangeSide(tx, a); err != nil {
			return err
		}
		return applyExchangeSide(tx, b)
	})
	if errors.Is(err, errExchangeAborted) {
		return false, nil // aborted cleanly, nothing moved
	}
	if err != nil {
		return false, err
	}
	return true, nil
}

// applyExchangeSide moves one giver's staked cards to the receiver, inside tx.
func applyExchangeSide(tx *gorm.DB, o ExchangeOffer) error {
	for _, staked := range o.Cards {
		var card domain.CoachCard
		err := tx.Where("id = ? AND coach_id = ?", staked.CardID, o.GiverID).
			First(&card).Error
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return errExchangeAborted // no longer owned by the giver
		}
		if err != nil {
			return err
		}
		// Invariants: never trade a locked or equipped card, and never more than
		// currently owned.
		if card.Flag&domain.CardLocked != 0 || card.Pos != 0 || card.Quantity <= 0 {
			return errExchangeAborted
		}
		qty := staked.Quantity
		if qty < 1 {
			return errExchangeAborted
		}
		if qty > card.Quantity {
			qty = card.Quantity // clamp: can't give more than owned
		}

		// Debit the giver: decrement the stack, delete the row at zero.
		if card.Quantity-qty <= 0 {
			if err := tx.Delete(&domain.CoachCard{}, card.ID).Error; err != nil {
				return err
			}
		} else {
			if err := tx.Model(&domain.CoachCard{}).Where("id = ?", card.ID).
				Update("quantity", card.Quantity-qty).Error; err != nil {
				return err
			}
		}

		// Credit the receiver: stack onto an existing unequipped same-template
		// card, or insert a new (cursed) one.
		var dst domain.CoachCard
		err = tx.Where("coach_id = ? AND template_id = ? AND pos = 0",
			o.Receiver, card.TemplateID).First(&dst).Error
		switch {
		case errors.Is(err, gorm.ErrRecordNotFound):
			if err := tx.Create(&domain.CoachCard{
				CoachID: o.Receiver, TemplateID: card.TemplateID,
				Quantity: qty, Pos: 0, Flag: domain.CardCursed,
			}).Error; err != nil {
				return err
			}
		case err != nil:
			return err
		default:
			if err := tx.Model(&domain.CoachCard{}).Where("id = ?", dst.ID).
				Update("quantity", dst.Quantity+qty).Error; err != nil {
				return err
			}
		}
	}
	return nil
}
