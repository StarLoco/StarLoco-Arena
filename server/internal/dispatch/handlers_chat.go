package dispatch

import (
	"strings"

	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// RegisterChatHandlers wires private and vicinity (world-broadcast) chat,
// see docs/02-protocol.md §2.3 PRIVATE_MESSAGE/VICINITY_MESSAGE and the
// wire shapes in client VicinityContentMessage/PrivateContentMessage.
func RegisterChatHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvPrivateMessage, func(session *netio.Session, payload *protocol.Reader) {
		handlePrivateMessage(session, payload, deps)
	})
	r.Register(protocol.RecvVicinityMessage, func(session *netio.Session, payload *protocol.Reader) {
		handleVicinityMessage(session, payload, deps)
	})
}

func handlePrivateMessage(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	targetName := payload.String()
	if payload.Err() != nil {
		return
	}

	target, ok := deps.World.GetByName(targetName)
	if !ok {
		sendUserNotFound(session, targetName)
		return
	}

	message := payload.String()
	if payload.Err() != nil {
		return
	}

	sender, ok := sessionCoach(session)
	if !ok {
		return
	}

	w := protocol.NewWriter(10 + len(sender.Name) + len(message))
	w.PutString(sender.Name).PutInt64(int64(sender.ID)).PutString(message)
	target.Session.Send(protocol.OutboundFrame{Opcode: protocol.SendPrivateMessage, Payload: w.Bytes()})
}

func handleVicinityMessage(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	// The wire format uses a 2-byte length prefix here (unlike most other
	// chat strings, which use 1 byte), matching VicinityMessage.java:25
	// (`recv.getShort()`).
	message := payload.StringShort()
	if payload.Err() != nil {
		return
	}

	sender, ok := sessionCoach(session)
	if !ok {
		return
	}

	// A message starting with '/' is a GM command, never broadcast as
	// vicinity chat -- mirrors VicinityMessage.java's parse() which always
	// returns true (swallowing the message) for any '/'-prefixed text,
	// matched or not. See handlers_gm_commands.go for the hardened port
	// (Java's version had zero permission check; this one gates on
	// sessionIsAdmin).
	if strings.HasPrefix(message, "/") {
		handleGMCommand(session, sender, message, deps)
		return
	}

	w := protocol.NewWriter(10 + len(sender.Name) + len(message))
	w.PutString(sender.Name).PutInt64(int64(sender.ID)).PutString(message)
	frame := protocol.OutboundFrame{Opcode: protocol.SendVicinityMessage, Payload: w.Bytes()}

	for _, oc := range deps.World.SnapshotWithout(sender.ID) {
		oc.Session.Send(frame)
	}
}
