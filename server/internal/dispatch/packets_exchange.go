package dispatch

import (
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/world"
)

// Item-exchange invitation-confirmation result codes, mirroring the
// legacy CoachExchange.start()/ItemExchangeInvitationAnswerMessage.java
// inline comment: 0=pending, 2=refused, 3=accepted.
const (
	ExchangeConfirmPending  = 0
	ExchangeConfirmRefused  = 2
	ExchangeConfirmAccepted = 3
)

// buildItemExchangeInvitationConfirmation serializes
// ITEM_EXCHANGE_INVITATION_CONFIRMATION_MESSAGE, see
// CoachExchange.java:68-70 and ItemExchangeInvitationAnswerMessage.java:21-23.
func buildItemExchangeInvitationConfirmation(result byte, exchangeID int64, otherCoachID uint) protocol.OutboundFrame {
	w := protocol.NewWriter(17)
	w.PutByte(result).PutInt64(exchangeID).PutInt64(int64(otherCoachID))
	return protocol.OutboundFrame{Opcode: protocol.SendItemExchangeInvitationConfirmation, Payload: w.Bytes()}
}

// buildItemExchangeInvitationRequest serializes
// ITEM_EXCHANGE_INVITATION_REQUEST_MESSAGE, see CoachExchange.java:72-77.
func buildItemExchangeInvitationRequest(exchangeID int64, fromCoachID uint, fromName string) protocol.OutboundFrame {
	w := protocol.NewWriter(17 + len(fromName))
	w.PutInt64(exchangeID).PutInt64(int64(fromCoachID)).PutString(fromName)
	return protocol.OutboundFrame{Opcode: protocol.SendItemExchangeInvitationRequest, Payload: w.Bytes()}
}

// buildItemExchangeCardAdded serializes ITEM_EXCHANGE_CARD_ADDED_MESSAGE,
// see ItemExchangeAddCardMessage.java:22-31.
func buildItemExchangeCardAdded(exchangeID int64, isFromSide bool, entry world.ExchangeCardEntry) protocol.OutboundFrame {
	w := protocol.NewWriter(24)
	w.PutInt64(exchangeID)
	w.PutByte(sideByte(isFromSide))
	w.PutInt32(entry.TemplateID).PutInt64(int64(entry.CoachCardID)).PutByte(entry.Flag).PutInt16(entry.Quantity)
	return protocol.OutboundFrame{Opcode: protocol.SendItemExchangeCardAdded, Payload: w.Bytes()}
}

// buildItemExchangeCardRemoved serializes
// ITEM_EXCHANGE_CARD_REMOVED_MESSAGE, see
// ItemExchangeRemoveCardMessage.java:22-30.
func buildItemExchangeCardRemoved(exchangeID int64, isFromSide bool, entry world.ExchangeCardEntry) protocol.OutboundFrame {
	w := protocol.NewWriter(26)
	w.PutInt64(exchangeID)
	w.PutByte(sideByte(isFromSide))
	w.PutInt32(entry.TemplateID).PutInt64(int64(entry.CoachCardID)).PutByte(entry.Flag).PutInt16(entry.Quantity)
	return protocol.OutboundFrame{Opcode: protocol.SendItemExchangeCardRemoved, Payload: w.Bytes()}
}

// buildItemExchangeEnd serializes ITEM_EXCHANGE_END_MESSAGE, see
// ItemExchangeCancelMessage.java:17-19 (result byte: 1 = ended/canceled;
// the legacy code never sends any other value for this opcode).
func buildItemExchangeEnd(exchangeID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(9)
	w.PutByte(1).PutInt64(exchangeID)
	return protocol.OutboundFrame{Opcode: protocol.SendItemExchangeEnd, Payload: w.Bytes()}
}

// buildItemExchangeUserReady serializes ITEM_EXCHANGE_USER_READY_MESSAGE,
// see ItemExchangeSetReadyMessage.java:23-33.
func buildItemExchangeUserReady(exchangeID int64, isFromSide bool) protocol.OutboundFrame {
	w := protocol.NewWriter(9)
	w.PutInt64(exchangeID).PutByte(sideByte(isFromSide))
	return protocol.OutboundFrame{Opcode: protocol.SendItemExchangeUserReady, Payload: w.Bytes()}
}

func sideByte(isFromSide bool) byte {
	if isFromSide {
		return 0
	}
	return 1
}
