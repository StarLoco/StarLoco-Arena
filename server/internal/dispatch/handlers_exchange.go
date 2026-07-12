package dispatch

import (
	"context"

	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/service"
	"github.com/dofusarena/go-server/internal/world"
)

// RegisterExchangeHandlers wires the item-exchange (player trading)
// opcodes, see docs/02-protocol.md ITEM_EXCHANGE_* and
// ItemExchangeInvitationRequestMessage.java/
// ItemExchangeInvitationAnswerMessage.java/ItemExchangeAddCardMessage.java/
// ItemExchangeRemoveCardMessage.java/ItemExchangeSetReadyMessage.java/
// ItemExchangeCancelMessage.java/CoachExchange.java.
func RegisterExchangeHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvItemExchangeInvitationRequest, func(session *netio.Session, payload *protocol.Reader) {
		handleExchangeInvitationRequest(session, payload, deps)
	})
	r.Register(protocol.RecvItemExchangeInvitationAnswer, func(session *netio.Session, payload *protocol.Reader) {
		handleExchangeInvitationAnswer(session, payload, deps)
	})
	r.Register(protocol.RecvItemExchangeAddCard, func(session *netio.Session, payload *protocol.Reader) {
		handleExchangeAddCard(session, payload, deps)
	})
	r.Register(protocol.RecvItemExchangeRemoveCard, func(session *netio.Session, payload *protocol.Reader) {
		handleExchangeRemoveCard(session, payload, deps)
	})
	r.Register(protocol.RecvItemExchangeSetReady, func(session *netio.Session, payload *protocol.Reader) {
		handleExchangeSetReady(session, payload, deps)
	})
	r.Register(protocol.RecvItemExchangeCancel, func(session *netio.Session, payload *protocol.Reader) {
		handleExchangeCancel(session, payload, deps)
	})
}

func handleExchangeInvitationRequest(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	targetCoachID := uint(payload.Int64())
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}

	target, ok := deps.World.Get(targetCoachID)
	if !ok {
		return
	}

	// A coach can only be in ONE exchange at a time, and cannot exchange
	// while in a duel/fight. Without this guard a malicious coach could
	// open two concurrent exchanges (or one exchange while staking the
	// same card in a fight) and complete both, duplicating a card. The
	// exchange-busy check and the registration happen ATOMICALLY inside
	// StartExclusive (under one lock) so two racing invitations can't both
	// pass a separate check and both create an exchange (TOCTOU). The
	// in-duel check is supplied as the externallyBusy predicate so it's
	// evaluated in the same atomic step. Reject if EITHER party is busy.
	exchange, started := deps.Exchanges.StartExclusive(coach.ID, targetCoachID, func(id uint) bool {
		_, inDuel := deps.Duels.GetByCoach(id)
		return inDuel
	})
	if !started {
		session.Send(buildItemExchangeInvitationConfirmation(ExchangeConfirmRefused, 0, targetCoachID))
		return
	}

	session.Send(buildItemExchangeInvitationConfirmation(ExchangeConfirmPending, 0, targetCoachID))
	target.Session.Send(buildItemExchangeInvitationRequest(exchange.ID, coach.ID, coach.Name))
}

func handleExchangeInvitationAnswer(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	// The exchangeId sent by the client here is NOT the real server-side
	// exchange ID -- it's a client-side quirk (ItemExchanger.java /
	// CardTrade.java: the ItemExchanger's internal id is initialized from
	// the *requester's coachID*, not the ID the server generated and sent
	// in ITEM_EXCHANGE_INVITATION_REQUEST/CONFIRMATION_MESSAGE). The
	// legacy Java server's World.getCoachExchangeById(coach, exchangeId)
	// ignores this parameter entirely and looks up by coach instead
	// (World.java:51-53) -- so we must do the same via GetByCoach rather
	// than Get(exchangeID), or every invitation answer silently fails to
	// find its exchange.
	_ = payload.Int64()             // exchangeId, unused (see comment above)
	accepted := payload.Byte() == 0 // 0 = yes, 1 = no, matches legacy comment
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}
	exchange, ok := deps.Exchanges.GetByCoach(coach.ID)
	if !ok {
		return
	}

	// If the coach entered a duel/fight between receiving the invitation
	// and answering it, refuse the exchange -- otherwise a card could be
	// staked in the fight AND traded here (cross-system double-spend). The
	// initiator side is covered at StartExclusive time.
	if accepted {
		if _, inDuel := deps.Duels.GetByCoach(coach.ID); inDuel {
			endExchangeWithError(deps, exchange)
			return
		}
	}

	result := byte(ExchangeConfirmRefused)
	if accepted {
		result = ExchangeConfirmAccepted
	}

	otherID := exchange.ToID
	if !exchange.IsInitiator(coach.ID) {
		otherID = exchange.FromID
	}

	session.Send(buildItemExchangeInvitationConfirmation(result, exchange.ID, otherID))
	sendToCoachID(deps, otherID, buildItemExchangeInvitationConfirmation(result, exchange.ID, otherID))

	if !accepted {
		deps.Exchanges.Remove(exchange.ID)
	}
}

func handleExchangeAddCard(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	// See the comment in handleExchangeInvitationAnswer: the client-sent
	// exchangeId is not the real ID, so look up by coach instead.
	_ = payload.Int64() // exchangeId, unused
	cardID := uint(payload.Int64())
	quantity := payload.Int16()
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}
	exchange, ok := deps.Exchanges.GetByCoach(coach.ID)
	if !ok {
		return
	}

	ctx := context.Background()
	card, err := deps.Coach.GetCardByID(ctx, coach.ID, cardID)
	if err != nil {
		endExchangeWithError(deps, exchange)
		return
	}

	entry := exchange.AddCard(coach.ID, world.ExchangeCardEntry{
		CoachCardID: card.ID,
		TemplateID:  card.TemplateID,
		Flag:        card.Flag,
		Quantity:    quantity,
	}, card.Quantity)

	frame := buildItemExchangeCardAdded(exchange.ID, exchange.IsInitiator(coach.ID), entry)
	sendToCoachID(deps, exchange.FromID, frame)
	sendToCoachID(deps, exchange.ToID, frame)
}

func handleExchangeRemoveCard(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	// See the comment in handleExchangeInvitationAnswer: the client-sent
	// exchangeId is not the real ID, so look up by coach instead.
	_ = payload.Int64() // exchangeId, unused
	cardID := uint(payload.Int64())
	quantity := payload.Int16()
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}
	exchange, ok := deps.Exchanges.GetByCoach(coach.ID)
	if !ok {
		return
	}

	ctx := context.Background()
	card, err := deps.Coach.GetCardByID(ctx, coach.ID, cardID)
	if err != nil {
		endExchangeWithError(deps, exchange)
		return
	}

	removed := exchange.RemoveCard(coach.ID, card.TemplateID, quantity)
	if !removed {
		endExchangeWithError(deps, exchange)
		return
	}

	entry := world.ExchangeCardEntry{CoachCardID: card.ID, TemplateID: card.TemplateID, Flag: card.Flag, Quantity: quantity}
	frame := buildItemExchangeCardRemoved(exchange.ID, exchange.IsInitiator(coach.ID), entry)
	sendToCoachID(deps, exchange.FromID, frame)
	sendToCoachID(deps, exchange.ToID, frame)
}

func handleExchangeSetReady(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	// See the comment in handleExchangeInvitationAnswer: the client-sent
	// exchangeId is not the real ID, so look up by coach instead.
	_ = payload.Int64() // exchangeId, unused
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}
	exchange, ok := deps.Exchanges.GetByCoach(coach.ID)
	if !ok {
		return
	}

	isFromSide, bothReady := exchange.SetReady(coach.ID)
	frame := buildItemExchangeUserReady(exchange.ID, isFromSide)
	sendToCoachID(deps, exchange.FromID, frame)
	sendToCoachID(deps, exchange.ToID, frame)

	if bothReady {
		completeExchange(deps, exchange)
	}
}

func handleExchangeCancel(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	// See the comment in handleExchangeInvitationAnswer: the client-sent
	// exchangeId is not the real ID, so look up by coach instead.
	_ = payload.Int64() // exchangeId, unused
	if payload.Err() != nil {
		return
	}

	coach, ok := sessionCoach(session)
	if !ok {
		return
	}
	exchange, ok := deps.Exchanges.GetByCoach(coach.ID)
	if !ok {
		return
	}

	endExchangeWithError(deps, exchange)
}

// endExchangeWithError broadcasts ITEM_EXCHANGE_END and tears down the
// exchange, used for both explicit cancellation and error conditions
// (invalid card reference etc), mirroring the legacy pattern repeated in
// ItemExchangeAddCardMessage/RemoveCardMessage/CancelMessage.
func endExchangeWithError(deps *Deps, exchange *world.Exchange) {
	frame := buildItemExchangeEnd(exchange.ID)
	sendToCoachID(deps, exchange.FromID, frame)
	sendToCoachID(deps, exchange.ToID, frame)
	deps.Exchanges.Remove(exchange.ID)
}

// completeExchange performs the actual card transfer once both sides are
// ready, mirroring CoachExchange.ok() (CoachExchange.java:127-148). The
// whole two-sided swap runs in ONE database transaction that re-validates
// every offered card at commit time (ownership, unlocked, unequipped,
// quantity) -- see service.CoachService.CompleteExchange. If any offer is
// no longer valid the exchange is aborted with no transfer (the safe
// outcome), closing the concurrent-exchange / stale-offer duplication
// vectors.
//
// completeExchange consumes the exchange exactly once: the exchange is
// removed from the registry BEFORE the transfer runs, so a second
// both-ready (e.g. a racing SetReady) can't find it and double-commit.
func completeExchange(deps *Deps, exchange *world.Exchange) {
	// Consume the exchange first (idempotency guard against double-commit).
	if !deps.Exchanges.Remove(exchange.ID) {
		return // already completed/canceled by a concurrent path
	}

	ctx := context.Background()
	fromEntries, toEntries := exchange.Offers()

	fromOffers := make([]service.ExchangeOffer, 0, len(fromEntries))
	for _, e := range fromEntries {
		fromOffers = append(fromOffers, service.ExchangeOffer{CoachCardID: e.CoachCardID, Quantity: e.Quantity})
	}
	toOffers := make([]service.ExchangeOffer, 0, len(toEntries))
	for _, e := range toEntries {
		toOffers = append(toOffers, service.ExchangeOffer{CoachCardID: e.CoachCardID, Quantity: e.Quantity})
	}

	res, err := deps.Coach.CompleteExchange(ctx, exchange.FromID, exchange.ToID, fromOffers, toOffers)
	if err != nil {
		deps.Logger.Error().Err(err).Msg("dispatch: exchange completion failed")
		// Tell both sides the exchange ended so their UIs don't hang.
		frame := buildItemExchangeEnd(exchange.ID)
		sendToCoachID(deps, exchange.FromID, frame)
		sendToCoachID(deps, exchange.ToID, frame)
		return
	}
	if res == nil {
		// Aborted: an offered card was no longer valid at commit time. No
		// card moved; just tear the exchange down on both clients.
		frame := buildItemExchangeEnd(exchange.ID)
		sendToCoachID(deps, exchange.FromID, frame)
		sendToCoachID(deps, exchange.ToID, frame)
		return
	}

	fromDelta := &inventoryDelta{}
	toDelta := &inventoryDelta{}
	fromDelta.RemoveInventory = append(fromDelta.RemoveInventory, res.FromRemovedCardIDs...)
	toDelta.RemoveInventory = append(toDelta.RemoveInventory, res.ToRemovedCardIDs...)
	for _, c := range res.FromGranted {
		fromDelta.AddInventory = append(fromDelta.AddInventory, c)
	}
	for _, c := range res.ToGranted {
		toDelta.AddInventory = append(toDelta.AddInventory, c)
	}

	if frame, ok := fromDelta.build(); ok {
		sendToCoachID(deps, exchange.FromID, frame)
	}
	if frame, ok := toDelta.build(); ok {
		sendToCoachID(deps, exchange.ToID, frame)
	}
}
