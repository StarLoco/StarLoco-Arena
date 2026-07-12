// Package netio implements the TCP listener and per-connection lifecycle:
// framing (via internal/protocol), a bounded outbound write queue per
// connection, and panic-isolated connection goroutines so a bug handling
// one player's packets can never take the whole server down.
//
// See go-server/docs/01-architecture.md §1.3 for the concurrency model
// this package implements.
package netio

import (
	"bufio"
	"net"
	"sync"
	"sync/atomic"

	"github.com/dofusarena/go-server/internal/protocol"
)

// outboundQueueSize bounds how many pending outbound frames a session can
// buffer before the sender starts dropping (with a logged warning) rather
// than blocking. Prevents one slow/stalled client from backing up memory or
// stalling a broadcaster (e.g. World chat) indefinitely.
const outboundQueueSize = 256

// AccountRef is the minimal identity a Session carries once authenticated.
// Kept intentionally small/decoupled from internal/domain so netio has no
// dependency on the persistence layer -- services attach richer state via
// UserData.
type AccountRef struct {
	AccountID uint
	CoachID   uint
	// IsAdmin gates the in-game GM chat-cheat-commands (see
	// docs/08-java-parity-roadmap.md §8.4). Snapshotted from the DB at
	// login time; a mid-session admin-flag change (e.g. via the web admin
	// console) only takes effect on the player's next login, which is an
	// acceptable and simpler tradeoff than re-querying the DB per chat
	// message.
	IsAdmin bool
}

// Session represents one connected client for the lifetime of its TCP
// connection. It replaces the legacy Client.java abstraction: instead of a
// direct blocking IoSession.write() call from arbitrary goroutines, all
// sends go through a buffered channel drained by a single writer goroutine
// per connection, avoiding torn/interleaved writes without a socket mutex.
type Session struct {
	id     uint64
	conn   net.Conn
	reader *bufio.Reader
	writer *bufio.Writer

	outbound        chan protocol.OutboundFrame
	closeCh         chan struct{}
	closeSignalOnce sync.Once
	closeConnOnce   sync.Once

	account atomic.Pointer[AccountRef]

	// UserData is a free-form slot for service-layer state attached to this
	// session (e.g. a *domain.Coach cache, exchange session pointer). Guarded
	// by userDataMu since it may be read/written from different goroutines
	// (the connection's own goroutine and, e.g., a fight actor delivering a
	// direct message).
	userDataMu sync.RWMutex
	userData   map[string]any
}

var sessionIDCounter atomic.Uint64

// NewSession wraps conn into a Session ready for use by Conn's read/write
// loops.
func NewSession(conn net.Conn) *Session {
	return &Session{
		id:       sessionIDCounter.Add(1),
		conn:     conn,
		reader:   bufio.NewReader(conn),
		writer:   bufio.NewWriter(conn),
		outbound: make(chan protocol.OutboundFrame, outboundQueueSize),
		closeCh:  make(chan struct{}),
		userData: make(map[string]any),
	}
}

// ID returns a process-unique session identifier, useful for log
// correlation.
func (s *Session) ID() uint64 { return s.id }

// RemoteAddr returns the underlying connection's remote address.
func (s *Session) RemoteAddr() string {
	if s.conn == nil {
		return ""
	}
	return s.conn.RemoteAddr().String()
}

// Account returns the authenticated account reference, or nil if the
// session hasn't authenticated yet.
func (s *Session) Account() *AccountRef {
	return s.account.Load()
}

// SetAccount attaches the authenticated account reference to this session.
func (s *Session) SetAccount(ref *AccountRef) {
	s.account.Store(ref)
}

// OutboundTracer, if set via SetOutboundTracer, is invoked for every frame
// passed to Session.Send, before it is enqueued. Used by the optional
// packet-trace debugging feature; nil (the default) means no tracing.
var outboundTracer func(sessionID uint64, frame protocol.OutboundFrame)

// SetOutboundTracer installs a global outbound-frame tracer. Call once at
// startup (e.g. from cmd/server when --trace-packets is enabled). Passing
// nil disables tracing.
func SetOutboundTracer(fn func(sessionID uint64, frame protocol.OutboundFrame)) {
	outboundTracer = fn
}

// Send enqueues a frame for asynchronous delivery. It never blocks: if the
// outbound queue is full (a stalled/slow client), the frame is dropped and
// ok is false so the caller can log it. This favors overall server
// responsiveness over guaranteed delivery to a misbehaving client.
func (s *Session) Send(frame protocol.OutboundFrame) (ok bool) {
	if outboundTracer != nil {
		outboundTracer(s.id, frame)
	}
	select {
	case s.outbound <- frame:
		return true
	default:
		return false
	}
}

// Close signals the session to shut down. Safe to call multiple times and
// from multiple goroutines.
//
// This only closes the Done() channel -- it deliberately does NOT close
// the underlying net.Conn directly. Closing the socket is owned by Conn's
// write loop (see closeConn), which does so only after fully draining any
// frames still sitting in the outbound queue at the moment Close() was
// called. This matters because handlers commonly do
// `session.Send(errorFrame); session.Close()` in sequence (e.g. rejecting
// a duplicate login after telling the client why) and expect that queued
// frame to actually reach the client before the connection dies.
func (s *Session) Close() {
	s.closeSignalOnce.Do(func() {
		close(s.closeCh)
	})
}

// Done returns a channel closed once the session has been asked to shut
// down.
func (s *Session) Done() <-chan struct{} {
	return s.closeCh
}

// closeConn physically closes the underlying net.Conn. Only Conn's write
// loop should call this, and only after it has finished flushing any
// pending outbound frames -- see the Close doc comment above.
func (s *Session) closeConn() {
	s.closeConnOnce.Do(func() {
		_ = s.conn.Close()
	})
}

// SetUserData stores a value under key, for service-layer state that needs
// to travel with the session (e.g. cached domain.Coach).
func (s *Session) SetUserData(key string, value any) {
	s.userDataMu.Lock()
	defer s.userDataMu.Unlock()
	s.userData[key] = value
}

// UserData retrieves a previously-stored value.
func (s *Session) UserData(key string) (any, bool) {
	s.userDataMu.RLock()
	defer s.userDataMu.RUnlock()
	v, ok := s.userData[key]
	return v, ok
}
