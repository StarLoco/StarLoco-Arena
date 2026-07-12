package dispatch

import (
	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// buildWorldServerUnavailable builds a WORLD_SERVER_UNAVAILABLE (1026)
// frame. The payload is empty: the client's WorldServerUnavailableMessage
// (an InputOnlyProxyMessage) reads no fields, and its NetAuthenticationFrame
// handler reacts by clearing the login state, showing the
// "error.connection.worldLoading" message box, and closing the connection.
//
// It is the one server->client "the game backend is going away" signal the
// stock client actually handles (the sibling NO_INSTANCE_SERVER_AVAILABLE
// (5000) is decoded but wired to no frame, so it would be silently dropped),
// which makes it the correct message to broadcast on graceful shutdown so
// connected players get a clean "world unavailable" prompt instead of a
// silently-dead socket.
func buildWorldServerUnavailable() protocol.OutboundFrame {
	return protocol.OutboundFrame{Opcode: protocol.SendWorldServerUnavailable}
}

// BroadcastShutdown notifies every currently-online coach that the server is
// going away and then closes their session. For each online coach it enqueues
// a WORLD_SERVER_UNAVAILABLE (1026) frame followed by Session.Close().
//
// The ordering is deliberate and relies on netio's drain-before-close
// contract (see netio.Session.Close and Conn.writeLoop): Close() only signals
// shutdown; the connection's write loop drains any already-queued frames --
// including the 1026 we just sent -- before physically closing the socket. So
// the client reliably receives the "world unavailable" prompt before its
// connection dies.
//
// It returns the number of coaches notified. Sessions that connected but never
// authenticated are not in the registry and so are not notified here; the
// listener close plus process exit tears those down. That mirrors the fact
// that 1026 is only meaningful to a client that is past the login screen.
func BroadcastShutdown(deps *Deps) int {
	online := deps.World.Snapshot()
	frame := buildWorldServerUnavailable()
	for _, oc := range online {
		if oc.Session == nil {
			continue
		}
		notifySessionShutdown(oc.Session, frame)
	}
	return len(online)
}

// notifySessionShutdown sends the shutdown frame to a single session and then
// closes it. Split out so the enqueue-then-close ordering is expressed once
// and is easy to test.
func notifySessionShutdown(session *netio.Session, frame protocol.OutboundFrame) {
	session.Send(frame)
	session.Close()
}
