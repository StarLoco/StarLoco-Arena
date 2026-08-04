// Package game is the DofusArena 2.70 game server: dispatch router, session
// handling, feature handlers and packet builders over the persistence and
// world layers.
package game

import (
	"log/slog"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// HandlerFunc processes one decoded client frame for a session.
type HandlerFunc func(s *Session, f *protocol.C2SFrame) error

// Router maps client opcodes to handlers. Unknown opcodes are logged no-ops so
// a partially-implemented server still runs.
type Router struct {
	handlers map[uint16]HandlerFunc
	log      *slog.Logger
}

// NewRouter creates an empty router.
func NewRouter(log *slog.Logger) *Router {
	return &Router{handlers: make(map[uint16]HandlerFunc), log: log}
}

// Register binds an opcode to a handler. Panics on duplicate registration to
// catch wiring bugs at startup.
func (r *Router) Register(opcode uint16, fn HandlerFunc) {
	if _, dup := r.handlers[opcode]; dup {
		panic("game: duplicate handler for opcode " + itoa(opcode))
	}
	r.handlers[opcode] = fn
}

// Dispatch routes a frame to its handler (no-op for unknown opcodes).
func (r *Router) Dispatch(s *Session, f *protocol.C2SFrame) error {
	fn, ok := r.handlers[f.Opcode]
	if !ok {
		s.log.Info("unhandled opcode", "opcode", f.Opcode, "arch", f.Arch, "len", len(f.Payload))
		return nil
	}
	return fn(s, f)
}

func itoa(v uint16) string {
	if v == 0 {
		return "0"
	}
	var b [5]byte
	i := len(b)
	for v > 0 {
		i--
		b[i] = byte('0' + v%10)
		v /= 10
	}
	return string(b[i:])
}
