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
	// pv_2.encode writes a 14-byte payload: the exchange id, the REFERENCE CARD
	// id, and a quantity. There is no per-instance uid on the wire — eb_1.b
	// reads four bytes and generates its own local uid — so the card is
	// identified by template, exactly as the client sees it.
	r := protocol.NewReader(f.Payload)
	if _, err := r.I64(); err != nil { // exchangeId
		return err
	}
	templateID, err := r.I32()
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

	// Removing needs no lookup: the table is keyed by template, and a card the
	// coach no longer owns should still come off the table.
	if !add {
		ex.unstageCard(side, templateID)
		frame, err := buildExchangeCardMove(protocol.OpExchangeCardRemoved,
			ex.ID, uint8(side), templateID, int16(qty))
		if err != nil {
			return err
		}
		s.broadcastExchange(ex, frame)
		return nil
	}

	// Resolve the card in the coach's BAG. pos = 0 is part of the key rather
	// than a check afterwards: an equipped copy is a different row and must not
	// be found here at all.
	var card domain.CoachCard
	err = s.deps.Store.DB().
		Where("coach_id = ? AND template_id = ? AND pos = 0", s.Coach.ID, templateID).
		First(&card).Error
	if err != nil {
		return nil // not owned / equipped / gone
	}
	// Template-level tradability. The client refuses these itself
	// ("error.exchange.linkedCard" / "coachInventory.undestructibleCard"), but
	// a forged 5105 would otherwise trade a card that must never move: 171 of
	// the 907 shipped cards are Bound and 65 Undestructible.
	if !s.deps.cardIsTradable(card.TemplateID) {
		s.log.Debug("exchange: refused a non-tradable card",
			"coach", s.Coach.ID, "template", card.TemplateID)
		// Tell the client why rather than letting the card silently fail to
		// appear; pg_1 case 5113 renders the linked-card message for any code
		// other than 1.
		if frame, err := buildExchangeError(ex.ID, exchangeErrLinkedCard); err == nil {
			_ = s.Send(frame)
		}
		return nil
	}
	// A unique card cannot be handed to somebody who already owns one: the
	// client refuses to take it (ky_2.a returns 2 when isUnique() and the id is
	// already held), so the trade would commit server-side and then desync the
	// receiver's inventory. Checked here because the giver's client cannot see
	// the receiver's collection.
	if s.deps.cardIsUnique(card.TemplateID) {
		if other := ex.Other(s.Coach.ID); other != nil && other.Coach != nil {
			var n int64
			if err := s.deps.Store.DB().Model(&domain.CoachCard{}).
				Where("coach_id = ? AND template_id = ?", other.Coach.ID, card.TemplateID).
				Count(&n).Error; err == nil && n > 0 {
				if frame, err := buildExchangeError(ex.ID, exchangeErrUniqueExists); err == nil {
					_ = s.Send(frame)
				}
				return nil
			}
		}
	}

	useQty := int16(qty)
	if useQty < 1 {
		useQty = 1
	}
	if useQty > card.Quantity {
		useQty = card.Quantity
	}
	ex.stageCard(side, StagedCard{CardID: card.ID, TemplateID: card.TemplateID, Quantity: useQty})

	frame, err := buildExchangeCardMove(protocol.OpExchangeCardAdded,
		ex.ID, uint8(side), card.TemplateID, useQty)
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

// buildExchangeCardMove builds 5110 (added) / 5112 (removed):
// [i64 exId][i8 userIdx][i32 refCardId][i16 qty] — 15 bytes.
//
// asH/aaz_1 read exactly that and stop. There is no uid and no flags byte in
// 2.70: the card object on the wire is eb_1's four-byte reference id (NT() == 4)
// and nothing else. Sending the extra 9 bytes the 2006 layout had would leave
// the client's ByteBuffer short of the quantity it reads next.
func buildExchangeCardMove(opcode uint16, exID int64, userIdx uint8, templateID int32, qty int16) ([]byte, error) {
	w := protocol.NewWriter().
		I64(exID).
		U8(userIdx).
		I32(templateID).
		U16(uint16(qty))
	return protocol.EncodeS2C(opcode, w.Bytes())
}

// Exchange refusal codes carried by 5113. The client special-cases 1 and shows
// the linked-card message for everything else (pg_1 case 5113).
const (
	exchangeErrUniqueExists uint8 = 1
	exchangeErrLinkedCard   uint8 = 2
)

// buildExchangeError builds 5113: [i8 code][i64 exId].
func buildExchangeError(exID int64, code uint8) ([]byte, error) {
	w := protocol.NewWriter().U8(code).I64(exID)
	return protocol.EncodeS2C(protocol.OpExchangeError, w.Bytes())
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
// cardIsUnique reports whether only one copy of a template may be owned
// (aPp field 9, isUnique()). Unknown or missing data means "not unique", so an
// operator without game data is not blocked from trading.
func (d *Deps) cardIsUnique(templateID int32) bool {
	if d == nil || d.Cards == nil {
		return false
	}
	tmpl := d.Cards.Get(templateID)
	return tmpl != nil && tmpl.IsUnique
}

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
