package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// Exchange invitation result codes (client Ul.bPI): 0=pending, 2=refused, 3=accepted.
const (
	exchPending  uint8 = 0
	exchRefused  uint8 = 2
	exchAccepted uint8 = 3
)

func registerExchangeHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpExchangeInvite, handleExchangeInvite)
	r.Register(protocol.OpExchangeAnswer, handleExchangeAnswer)
	r.Register(protocol.OpExchangeAddCard, handleExchangeAddCard)
	r.Register(protocol.OpExchangeRemoveCard, handleExchangeRemoveCard)
	r.Register(protocol.OpExchangeSetReady, handleExchangeSetReady)
	r.Register(protocol.OpExchangeCancel, handleExchangeCancel)
}

// handleExchangeInvite (5101 C2S: [i64 targetId]) starts an exchange and
// notifies the target (5102) + the initiator (5104 pending).
func handleExchangeInvite(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	targetID, err := protocol.NewReader(f.Payload).I64()
	if err != nil {
		return err
	}
	target := s.deps.World.Get(uint(targetID))
	if target == nil {
		return s.sendUserNotFound("")
	}
	ex := s.deps.Exchanges.Start(s, target.Session)
	if ex == nil {
		return nil // one party already busy
	}

	inv := protocol.NewWriter().
		I64(ex.ID).
		I64(int64(s.Coach.ID)).
		StringU8(s.Coach.Name)
	frame, err := protocol.EncodeS2C(protocol.OpExchangeInvitationRequest, inv.Bytes())
	if err != nil {
		return err
	}
	if err := target.Session.Send(frame); err != nil {
		return err
	}
	return s.sendExchangeConfirm(ex, exchPending, target.Coach.ID)
}

// handleExchangeAnswer (5103 C2S: [i64 exId][i8 accept]).
func handleExchangeAnswer(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	if _, err := r.I64(); err != nil {
		return err
	}
	accept, err := r.U8()
	if err != nil {
		return err
	}
	ex := s.deps.Exchanges.Get(s.Coach.ID)
	if ex == nil {
		return nil
	}
	other := ex.Other(s.Coach.ID)

	if accept == 0 {
		s.deps.Exchanges.Remove(ex)
		_ = s.sendExchangeConfirm(ex, exchRefused, otherCoachID(other))
		if other != nil {
			_ = other.sendExchangeConfirm(ex, exchRefused, s.Coach.ID)
		}
		return nil
	}

	ex.setAccepted()
	_ = s.sendExchangeConfirm(ex, exchAccepted, otherCoachID(other))
	if other != nil {
		_ = other.sendExchangeConfirm(ex, exchAccepted, s.Coach.ID)
	}
	return nil
}

// handleExchangeAddCard (5105 C2S: [i64 exId][i64 cardUid][i16 qty]).
func handleExchangeAddCard(s *Session, f *protocol.C2SFrame) error {
	return s.exchangeMoveCard(f, true)
}

// handleExchangeRemoveCard (5106 C2S: [i64 exId][i64 cardUid][i16 qty]).
func handleExchangeRemoveCard(s *Session, f *protocol.C2SFrame) error {
	return s.exchangeMoveCard(f, false)
}

// exchangeMoveCard stages/unstages a card and broadcasts 5109/5110 to both.
func (s *Session) exchangeMoveCard(f *protocol.C2SFrame, add bool) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	if _, err := r.I64(); err != nil { // exchangeId
		return err
	}
	cardUID, err := r.I64()
	if err != nil {
		return err
	}
	qty, err := r.U16()
	if err != nil {
		return err
	}
	ex := s.deps.Exchanges.Get(s.Coach.ID)
	if ex == nil {
		return nil
	}
	side := ex.sideOf(s.Coach.ID)
	if side < 0 {
		return nil
	}

	// Resolve + validate the card belongs to this coach, unlocked, unequipped.
	var card domain.CoachCard
	err = s.deps.Store.DB().Where("id = ? AND coach_id = ?", cardUID, s.Coach.ID).
		First(&card).Error
	if err != nil {
		return nil // not owned / gone
	}
	if add {
		if card.Flag&domain.CardLocked != 0 || card.Pos != 0 {
			return nil // can't stake a locked/equipped card
		}
		// Template-level tradability. The client refuses these itself
		// ("error.exchange.linkedCard" / "coachInventory.undestructibleCard"), but
		// a forged 5105 would otherwise trade a card that must never move: 171 of
		// the 907 shipped cards are Bound and 65 Undestructible.
		if !s.deps.cardIsTradable(card.TemplateID) {
			s.log.Debug("exchange: refused a non-tradable card",
				"coach", s.Coach.ID, "template", card.TemplateID)
			return nil
		}
		useQty := int16(qty)
		if useQty < 1 {
			useQty = 1
		}
		if useQty > card.Quantity {
			useQty = card.Quantity
		}
		ex.stageCard(side, StagedCard{CardID: card.ID, TemplateID: card.TemplateID, Quantity: useQty})
	} else {
		ex.unstageCard(side, card.ID)
	}

	// Broadcast the move to both parties.
	opcode := uint16(protocol.OpExchangeCardAdded)
	if !add {
		opcode = protocol.OpExchangeCardRemoved
	}
	frame, err := buildExchangeCardMove(opcode, ex.ID, uint8(side), card, int16(qty))
	if err != nil {
		return err
	}
	s.broadcastExchange(ex, frame)
	return nil
}

// handleExchangeSetReady (5107 C2S: [i64 exId]) toggles this side's ready flag;
// broadcasts 5112, and on BOTH-ready runs the atomic swap + 5111 success.
func handleExchangeSetReady(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	ex := s.deps.Exchanges.Get(s.Coach.ID)
	if ex == nil {
		return nil
	}
	side := ex.sideOf(s.Coach.ID)
	if side < 0 {
		return nil
	}
	_, both := ex.toggleReady(side)

	// Show the other side this side's ready state.
	ready, _ := buildExchangeUserReady(ex.ID, uint8(side))
	s.broadcastExchange(ex, ready)

	if !both {
		return nil
	}
	// Consume-once: only the goroutine that removes the exchange commits.
	if !s.deps.Exchanges.Remove(ex) {
		return nil
	}
	s.commitExchange(ex)
	return nil
}

// commitExchange runs the dupe-safe transactional swap and ends the trade.
func (s *Session) commitExchange(ex *Exchange) {
	aOffer := offerFor(ex, 0)
	bOffer := offerFor(ex, 1)

	committed, err := s.deps.Store.Coaches.CompleteExchange(aOffer, bOffer)
	if err != nil {
		s.deps.Log.Warn("exchange commit failed", "err", err)
	}
	reason := uint8(protocol.ExchangeEndSuccess)
	if !committed {
		reason = protocol.ExchangeEndCancel // re-validation aborted: nothing moved
	}
	if end, err := buildExchangeEnd(ex.ID, reason); err == nil {
		s.broadcastExchange(ex, end)
	}
	// Push authoritative inventories to both so the UI reflects the swap.
	if committed {
		s.refreshExchangeInventory(ex.A)
		s.refreshExchangeInventory(ex.B)
	}
}

// offerFor builds an ExchangeOffer for a side (giver -> the other side).
func offerFor(ex *Exchange, side int) store.ExchangeOffer {
	giver, receiver := ex.A, ex.B
	if side == 1 {
		giver, receiver = ex.B, ex.A
	}
	o := store.ExchangeOffer{}
	if giver != nil && giver.Coach != nil {
		o.GiverID = giver.Coach.ID
	}
	if receiver != nil && receiver.Coach != nil {
		o.Receiver = receiver.Coach.ID
	}
	for _, c := range ex.stagedCards(side) {
		o.Cards = append(o.Cards, store.ExchangeCard{CardID: c.CardID, Quantity: c.Quantity})
	}
	return o
}

// refreshExchangeInventory reloads a coach's cards and re-pushes the inventory.
func (s *Session) refreshExchangeInventory(sess *Session) {
	if sess == nil || sess.Coach == nil {
		return
	}
	if fresh, err := s.deps.Store.Coaches.Get(sess.Coach.ID); err == nil {
		sess.Coach.Inventory = fresh.Inventory
	}
	_ = sess.pushInventory(sess.Coach)
}

// handleExchangeCancel (5108 C2S: [i64 exId]) aborts the trade.
func handleExchangeCancel(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	ex := s.deps.Exchanges.Get(s.Coach.ID)
	if ex == nil {
		return nil
	}
	if !s.deps.Exchanges.Remove(ex) {
		return nil
	}
	if end, err := buildExchangeEnd(ex.ID, protocol.ExchangeEndCancel); err == nil {
		s.broadcastExchange(ex, end)
	}
	return nil
}

// broadcastExchange sends a frame to both parties.
func (s *Session) broadcastExchange(ex *Exchange, frame []byte) {
	if ex.A != nil {
		_ = ex.A.Send(frame)
	}
	if ex.B != nil {
		_ = ex.B.Send(frame)
	}
}

// --- packet builders ---

// buildExchangeCardMove builds 5109/5110: [i64 exId][i8 userIdx]
// [i32 refCardId][i64 uid][i8 flags][i16 qty].
func buildExchangeCardMove(opcode uint16, exID int64, userIdx uint8, card domain.CoachCard, qty int16) ([]byte, error) {
	w := protocol.NewWriter().
		I64(exID).
		U8(userIdx).
		I32(card.TemplateID).
		I64(int64(card.ID)).
		U8(card.Flag).
		U16(uint16(qty))
	return protocol.EncodeS2C(opcode, w.Bytes())
}

// buildExchangeUserReady builds 5112: [i64 exId][i8 userIdx].
func buildExchangeUserReady(exID int64, userIdx uint8) ([]byte, error) {
	w := protocol.NewWriter().I64(exID).U8(userIdx)
	return protocol.EncodeS2C(protocol.OpExchangeUserReady, w.Bytes())
}

// buildExchangeEnd builds 5111: [i8 reason][i64 exId] (reason FIRST).
func buildExchangeEnd(exID int64, reason uint8) ([]byte, error) {
	w := protocol.NewWriter().U8(reason).I64(exID)
	return protocol.EncodeS2C(protocol.OpExchangeEnd, w.Bytes())
}

// sendExchangeConfirm sends ExchangeInvitationConfirmation (5104):
// [i8 result][i64 exchangeId][i64 otherCoachId].
func (s *Session) sendExchangeConfirm(ex *Exchange, result uint8, otherID uint) error {
	w := protocol.NewWriter().U8(result).I64(ex.ID).I64(int64(otherID))
	frame, err := protocol.EncodeS2C(protocol.OpExchangeConfirmation, w.Bytes())
	if err != nil {
		return err
	}
	return s.Send(frame)
}

func otherCoachID(other *Session) uint {
	if other != nil && other.Coach != nil {
		return other.Coach.ID
	}
	return 0
}

// cardIsTradable reports whether a card TEMPLATE may be staked in an exchange.
// The client blocks two template flags; the server must too, since it is the only
// authority a forged packet cannot bypass:
//
//	Bound (aPp.tp)          -> "error.exchange.linkedCard"
//	Undestructible (aPp.tq) -> "coachInventory.undestructibleCard"
//
// Unknown templates (or an absent catalog) are permissive, so a server running
// without data files behaves as before rather than blocking every trade.
func (d *Deps) cardIsTradable(templateID int32) bool {
	if d == nil || d.Cards == nil {
		return true
	}
	tmpl := d.Cards.Get(templateID)
	if tmpl == nil {
		return true
	}
	return !tmpl.Bound && !tmpl.Undestructible
}
