package netio

import (
	"context"
	"errors"
	"net"

	"github.com/rs/zerolog"
)

// Listener accepts incoming TCP connections and spawns a Conn (and its two
// goroutines) for each one. It supports graceful shutdown via Close, which
// stops accepting new connections without forcibly severing existing ones
// (callers coordinate draining existing sessions separately, see
// docs/06-config-and-ops.md §6.4).
type Listener struct {
	addr         string
	logger       zerolog.Logger
	onMessage    Handler
	onConnect    func(*Session)
	onDisconnect DisconnectHandler

	ln net.Listener
}

// NewListener constructs a Listener bound to addr (e.g. ":443"). It does
// not start accepting connections until Serve is called.
func NewListener(addr string, logger zerolog.Logger, onConnect func(*Session), onMessage Handler, onDisconnect DisconnectHandler) *Listener {
	return &Listener{
		addr:         addr,
		logger:       logger,
		onMessage:    onMessage,
		onConnect:    onConnect,
		onDisconnect: onDisconnect,
	}
}

// Listen binds the TCP socket. Separated from Serve so callers can
// distinguish a bind failure (fatal, should abort startup) from a later
// accept-loop error (recoverable, logged and retried).
func (l *Listener) Listen() error {
	ln, err := net.Listen("tcp", l.addr)
	if err != nil {
		return err
	}
	l.ln = ln
	return nil
}

// Addr returns the bound address. Only valid after a successful Listen.
func (l *Listener) Addr() net.Addr {
	if l.ln == nil {
		return nil
	}
	return l.ln.Addr()
}

// Serve runs the accept loop until ctx is canceled or the listener is
// closed. Each accepted connection is served in its own goroutine.
func (l *Listener) Serve(ctx context.Context) error {
	go func() {
		<-ctx.Done()
		_ = l.ln.Close()
	}()

	for {
		raw, err := l.ln.Accept()
		if err != nil {
			if errors.Is(err, net.ErrClosed) {
				return nil
			}
			l.logger.Warn().Err(err).Msg("netio: accept error")
			continue
		}

		conn := NewConn(raw, l.logger, l.onMessage, l.onDisconnect)
		if l.onConnect != nil {
			l.onConnect(conn.Session())
		}
		go conn.Serve()
	}
}

// Close stops the listener from accepting new connections.
func (l *Listener) Close() error {
	if l.ln == nil {
		return nil
	}
	return l.ln.Close()
}
