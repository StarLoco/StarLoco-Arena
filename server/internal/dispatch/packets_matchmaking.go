package dispatch

import "github.com/dofusarena/go-server/internal/protocol"

// buildOpponentSearchInProgress serializes OPPONENT_SEARCH_IN_PROGRESS
// (empty payload), see OpponentSearchRequest.java:23.
func buildOpponentSearchInProgress() protocol.OutboundFrame {
	return protocol.OutboundFrame{Opcode: protocol.SendOpponentSearchInProgress}
}

// buildOpponentSearchCancelResult serializes OPPONENT_SEARCH_CANCEL_RESULT
// (empty payload), see OpponentSearchCancel.java:15.
func buildOpponentSearchCancelResult() protocol.OutboundFrame {
	return protocol.OutboundFrame{Opcode: protocol.SendOpponentSearchCancelResult}
}

// buildOpponentSearchError serializes OPPONENT_SEARCH_ERROR (2302) with a
// single errorCode byte, matching the client's OpponentSearchErrorMessage
// decode (rawDatas[0]). The opcode is dead code in the legacy Java server;
// this is a Go-only robustness signal sent when an OPPONENT_SEARCH_REQUEST
// fails validation (previously such requests were dropped silently). Unlike
// the empty-payload cancel-result, this correctly emits the >=1 byte the
// client reads. See protocol.OpponentSearchErrorCode.
func buildOpponentSearchError(code protocol.OpponentSearchErrorCode) protocol.OutboundFrame {
	w := protocol.NewWriter(1)
	w.PutByte(byte(code))
	return protocol.OutboundFrame{Opcode: protocol.SendOpponentSearchError, Payload: w.Bytes()}
}

// buildOpponentFound serializes OPPONENT_FOUND, see
// WaitingOpponent.java:35.
func buildOpponentFound(fightID int64, bet int32, fightType byte) protocol.OutboundFrame {
	w := protocol.NewWriter(13)
	w.PutInt64(fightID).PutInt32(bet).PutByte(fightType)
	return protocol.OutboundFrame{Opcode: protocol.SendOpponentFound, Payload: w.Bytes()}
}
