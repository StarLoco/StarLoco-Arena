package netio

import (
	"errors"
	"io"
	"net"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/protocol"
)

// Handler processes one fully-framed inbound message for a session. It is
// supplied by the dispatch layer (internal/dispatch) at server composition
// time and must not block for long periods -- long-running work (DB calls,
// fight actor round-trips) should be kept reasonably quick or moved off
// this goroutine, since it also drives the read loop for this connection.
type Handler func(session *Session, frame protocol.InboundFrame)

// DisconnectHandler is invoked once when a session's connection ends, for
// any reason (client disconnect, read error, forced kick).
type DisconnectHandler func(session *Session)

// Conn drives the read and write loops for a single accepted connection.
// Each Conn owns exactly two goroutines: one blocking on reads (calling
// Handler synchronously per frame) and one draining the session's outbound
// queue. A panic in either loop is recovered and only tears down this one
// connection.
type Conn struct {
	session    *Session
	logger     zerolog.Logger
	onMessage  Handler
	onDisconnect DisconnectHandler
}

// NewConn constructs a Conn for an already-accepted net.Conn.
func NewConn(raw net.Conn, logger zerolog.Logger, onMessage Handler, onDisconnect DisconnectHandler) *Conn {
	session := NewSession(raw)
	return &Conn{
		session:      session,
		logger:       logger.With().Uint64("session_id", session.ID()).Str("remote_addr", session.RemoteAddr()).Logger(),
		onMessage:    onMessage,
		onDisconnect: onDisconnect,
	}
}

// Session returns the underlying Session, e.g. for registering it in the
// world registry once authenticated.
func (c *Conn) Session() *Session {
	return c.session
}

// Serve runs the read and write loops until the connection closes. It
// blocks until both loops have exited, so callers typically invoke it in
// its own goroutine (one per accepted connection).
func (c *Conn) Serve() {
	done := make(chan struct{})
	go c.writeLoop(done)
	c.readLoop()
	c.session.Close()
	<-done
	if c.onDisconnect != nil {
		safeCall(c.logger, "disconnect handler", func() { c.onDisconnect(c.session) })
	}
}

func (c *Conn) readLoop() {
	defer func() {
		if r := recover(); r != nil {
			c.logger.Error().Interface("panic", r).Msg("netio: recovered panic in read loop")
		}
	}()

	for {
		frame, err := protocol.ReadInboundFrame(c.session.reader)
		if err != nil {
			if !isClosedOrEOF(err) {
				c.logger.Debug().Err(err).Msg("netio: read loop terminating")
			}
			return
		}

		func() {
			defer func() {
				if r := recover(); r != nil {
					c.logger.Error().
						Interface("panic", r).
						Uint16("opcode", uint16(frame.Opcode)).
						Msg("netio: recovered panic handling inbound frame")
				}
			}()
			if c.onMessage != nil {
				c.onMessage(c.session, frame)
			}
		}()

		select {
		case <-c.session.Done():
			return
		default:
		}
	}
}

// writeLoop drains the session's outbound queue and writes each frame to
// the socket. It intentionally prioritizes draining any already-queued
// outbound frames over honoring a Close() signal: a handler that does
// `session.Send(...)` immediately followed by `session.Close()` (e.g.
// rejecting a duplicate login after sending the error result, see
// dispatch.handleAuthentication) relies on that queued frame actually being
// written before the connection tears down. A naive single `select {
// case <-done: ...; case frame := <-outbound: ... }` would race here: Go's
// select picks uniformly at random among simultaneously-ready cases, so it
// could pick the done case and silently drop the still-buffered frame.
// Instead, every iteration first attempts a non-blocking drain of
// outbound; only once it's empty do we block on select(done, outbound),
// and even then we do one final non-blocking drain pass before returning.
func (c *Conn) writeLoop(done chan<- struct{}) {
	defer close(done)
	// The write loop is the sole owner of physically closing the socket
	// (see Session.closeConn's doc comment): whichever path we return
	// through below, the connection must actually be closed here so the
	// read loop (blocked in a socket read) unblocks and Serve can proceed
	// to run the disconnect handler.
	defer c.session.closeConn()
	defer func() {
		if r := recover(); r != nil {
			c.logger.Error().Interface("panic", r).Msg("netio: recovered panic in write loop")
		}
	}()

	for {
		if c.drainOnePending() {
			continue
		}

		select {
		case <-c.session.Done():
			c.drainRemaining()
			return
		case frame, ok := <-c.session.outbound:
			if !ok {
				return
			}
			if !c.writeFrame(frame) {
				return
			}
		}
	}
}

// drainOnePending performs a single non-blocking attempt to receive and
// write one queued outbound frame. Returns true if a frame was processed
// (caller should loop again to check for more), false if the queue was
// empty or the connection needs to close.
func (c *Conn) drainOnePending() bool {
	select {
	case frame, ok := <-c.session.outbound:
		if !ok {
			return false
		}
		return c.writeFrame(frame)
	default:
		return false
	}
}

// drainRemaining flushes every currently-queued outbound frame
// non-blockingly, called once right after Done() fires so frames enqueued
// just before Close() was called are not silently lost.
func (c *Conn) drainRemaining() {
	for {
		select {
		case frame, ok := <-c.session.outbound:
			if !ok {
				return
			}
			if !c.writeFrame(frame) {
				return
			}
		default:
			return
		}
	}
}

// writeFrame writes a single frame to the socket, closing the session and
// returning false on error.
func (c *Conn) writeFrame(frame protocol.OutboundFrame) bool {
	if err := protocol.WriteOutboundFrame(c.session.writer, frame); err != nil {
		c.logger.Debug().Err(err).Msg("netio: write failed, closing session")
		c.session.Close()
		return false
	}
	return true
}

func safeCall(logger zerolog.Logger, what string, fn func()) {
	defer func() {
		if r := recover(); r != nil {
			logger.Error().Interface("panic", r).Str("during", what).Msg("netio: recovered panic")
		}
	}()
	fn()
}

func isClosedOrEOF(err error) bool {
	return errors.Is(err, io.EOF) || errors.Is(err, net.ErrClosed) || errors.Is(err, io.ErrUnexpectedEOF)
}
