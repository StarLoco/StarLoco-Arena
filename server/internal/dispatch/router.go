// Package dispatch wires incoming protocol.InboundFrame opcodes to their
// handler functions, replacing the legacy Parser.java switch statement with
// an explicit, testable routing table. See go-server/docs/02-protocol.md
// §2.3.2 for the full opcode list this router is expected to eventually
// cover.
package dispatch

import (
	"encoding/hex"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// HandlerFunc processes one inbound message's payload for session.
type HandlerFunc func(session *netio.Session, payload *protocol.Reader)

// Router maps RecvOpcode to HandlerFunc and implements netio.Handler.
type Router struct {
	handlers map[protocol.RecvOpcode]HandlerFunc
	logger   zerolog.Logger
	trace    bool
}

// NewRouter creates an empty Router. Use Register to add handlers.
func NewRouter(logger zerolog.Logger) *Router {
	return &Router{
		handlers: make(map[protocol.RecvOpcode]HandlerFunc),
		logger:   logger,
	}
}

// SetTracePackets enables/disables inbound packet-trace logging.
func (r *Router) SetTracePackets(enabled bool) {
	r.trace = enabled
}

// Register associates opcode with fn. Panics on duplicate registration,
// since that always indicates a wiring bug at startup.
func (r *Router) Register(opcode protocol.RecvOpcode, fn HandlerFunc) {
	if _, exists := r.handlers[opcode]; exists {
		panic("dispatch: duplicate handler registration for opcode")
	}
	r.handlers[opcode] = fn
}

// Handle implements netio.Handler: looks up and invokes the registered
// handler for frame.Opcode, logging (at debug level) and no-op'ing for
// opcodes without a registered handler rather than treating it as fatal --
// this lets the server run in a partially-implemented state during
// incremental porting (see docs/07-roadmap.md).
func (r *Router) Handle(session *netio.Session, frame protocol.InboundFrame) {
	fn, ok := r.handlers[frame.Opcode]
	if !ok {
		r.logger.Debug().
			Uint16("opcode", uint16(frame.Opcode)).
			Uint64("session_id", session.ID()).
			Msg("dispatch: no handler registered for opcode")
		return
	}

	if r.trace {
		r.logger.Info().
			Uint64("session", session.ID()).
			Str("dir", "IN ").
			Str("opcode", frame.Opcode.Name()).
			Int("len", len(frame.Payload)).
			Str("hex", hex.EncodeToString(frame.Payload)).
			Msg("packet")
	}

	reader := protocol.NewReader(frame.Payload)
	fn(session, reader)

	if err := reader.Err(); err != nil {
		r.logger.Warn().
			Err(err).
			Uint16("opcode", uint16(frame.Opcode)).
			Uint64("session_id", session.ID()).
			Msg("dispatch: error reading packet payload")
	}
}
