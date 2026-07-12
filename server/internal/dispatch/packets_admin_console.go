package dispatch

import (
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// buildConsoleAdminResult serializes a CONSOLE_ADMIN_COMMAND_RESULT (8194)
// packet: a single message-type byte (TRACE/LOG/ERROR) followed by a
// 2-byte length-prefixed UTF-8 message. This matches the client's
// ConsoleAdminCommandResultMessage.decode (byte messageType + short length
// + bytes), which routes the line to ConsoleManager.trace/log/err.
func buildConsoleAdminResult(t protocol.AdminResultType, message string) protocol.OutboundFrame {
	w := protocol.NewWriter(3 + len(message))
	w.PutByte(byte(t)).PutStringShort(message)
	return protocol.OutboundFrame{Opcode: protocol.SendConsoleAdminCommandResult, Payload: w.Bytes()}
}

// buildDefaultResult serializes a DEFAULT_RESULT (8195) packet: a single
// big-endian int result code. The client passes it straight to
// onQueryResult (which only logs it), so it acts as a generic
// completion/ack for an operation. Convention here: 0 == success.
func buildDefaultResult(code int32) protocol.OutboundFrame {
	w := protocol.NewWriter(4)
	w.PutInt32(code)
	return protocol.OutboundFrame{Opcode: protocol.SendDefaultResult, Payload: w.Bytes()}
}

// sendAdminTrace/Log/Error are thin helpers that push a single console line
// of the corresponding severity back to the requesting client.
func sendAdminTrace(session *netio.Session, message string) {
	session.Send(buildConsoleAdminResult(protocol.AdminResultTrace, message))
}

func sendAdminLog(session *netio.Session, message string) {
	session.Send(buildConsoleAdminResult(protocol.AdminResultLog, message))
}

func sendAdminError(session *netio.Session, message string) {
	session.Send(buildConsoleAdminResult(protocol.AdminResultError, message))
}
