package game

import (
	"bufio"
	"errors"
	"io"
	"log/slog"
	"net"
	"runtime/debug"
	"sync"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Session is one client connection and its game state.
type Session struct {
	conn   net.Conn
	r      *bufio.Reader
	log    *slog.Logger
	router *Router
	deps   *Deps

	out       chan []byte   // async write queue (bounded; decouples broadcasters from slow clients)
	quit      chan struct{} // closed when the session is shutting down
	closeOnce sync.Once

	Account *domain.Account
	Coach   *domain.Coach

	// currentWorld is the overworld the coach is currently loaded into (the i16
	// world id last sent in ENTER_INSTANCE / 4600). Tracked in-memory so the
	// instance-ready ack (4517) can re-spawn that world's interactive elements
	// (Zaaps). Set on login and on Zaap teleport.
	currentWorld int16

	// chat is this session's chat rate state (trade cooldown + anti-repeat),
	// mirroring the client's own gates so a modified client cannot skip them.
	// Touched only by this session's goroutine, so it needs no lock.
	chat chatGate
	// spawnedElements tracks which interactive elements this client currently
	// holds, so refreshWorldElements can send only the delta. Reset on world
	// change - see resetSpawnedElements.
	spawnedElements map[int64]bool

	// spectating is the fight this session is watching as a spectator (nil = none).
	// Touched ONLY by this session's own goroutine (set on spectate-join, cleared
	// on fight-end ack or disconnect), so it needs no lock; the fight actor owns
	// the reciprocal f.spectators slice.
	spectating *Fight
	// playSince is when this coach entered the world; the gap up to disconnect is
	// added to Coach.TotalPlaySecs. Zero once credited, so it counts once.
	// Touched only by this session's own goroutine.
	playSince time.Time
}

// writeQueueSize bounds a session's pending outbound frames. A client that
// can't drain this fast enough is treated as dead and kicked, so one slow
// client never stalls a broadcast to everyone else.
const writeQueueSize = 256

// kick force-closes the session (used when a newer login replaces it). Closing
// quit stops the writer + unblocks Send; closing the conn unblocks the reader.
func (s *Session) kick() {
	s.closeOnce.Do(func() {
		close(s.quit)
		_ = s.conn.Close()
	})
}

func newSession(conn net.Conn, router *Router, deps *Deps) *Session {
	return &Session{
		conn:   conn,
		r:      bufio.NewReader(conn),
		log:    deps.Log.With("remote", conn.RemoteAddr().String()),
		router: router,
		deps:   deps,
		out:    make(chan []byte, writeQueueSize),
		quit:   make(chan struct{}),
	}
}

// Send enqueues a fully-framed S2C message for async delivery. Non-blocking:
// if the client's queue is full (it isn't reading fast enough), the session is
// kicked rather than blocking the caller (a broadcaster or the fight actor).
// This backpressure guard is the key to broadcast fan-out at scale. The `quit`
// select arm prevents a send-on-closed panic after teardown.
func (s *Session) Send(frame []byte) error {
	select {
	case <-s.quit:
		return net.ErrClosed
	default:
	}
	select {
	case s.out <- frame:
		return nil
	case <-s.quit:
		return net.ErrClosed
	default:
		s.log.Warn("write queue full, kicking slow client")
		s.kick()
		return net.ErrClosed
	}
}

// writeLoop drains the outbound queue to the socket, coalescing bursts through a
// buffered writer (a broadcast of many small frames becomes few syscalls).
func (s *Session) writeLoop() {
	bw := bufio.NewWriterSize(s.conn, 8192)
	for {
		select {
		case frame := <-s.out:
			if _, err := bw.Write(frame); err != nil {
				s.kick()
				return
			}
			// Flush when momentarily drained (batches bursts, keeps latency low).
			if len(s.out) == 0 {
				if err := bw.Flush(); err != nil {
					s.kick()
					return
				}
			}
		case <-s.quit:
			return
		}
	}
}

// serve reads and dispatches frames until the connection closes.
func (s *Session) serve() {
	defer s.onClose()
	s.log.Info("client connected")

	// Dedicated writer goroutine (async, backpressure-safe).
	go s.writeLoop()

	for {
		frame, err := protocol.ReadC2S(s.r)
		if err != nil {
			if errors.Is(err, io.EOF) {
				s.log.Info("client disconnected")
			} else {
				s.log.Warn("read error", "err", err)
			}
			return
		}
		if err := s.dispatchSafely(frame); err != nil {
			s.log.Warn("dispatch error", "opcode", frame.Opcode, "err", err)
			return
		}
	}
}

// creditPlayTime adds this session's time in the world to the coach's lifetime
// play counter, which the client's 2400 statistics panel and the web portal both
// show.
//
// It only touches the in-memory Coach; persisting is left to whoever saves it
// next. That matters for the replaced-session case below: the incoming session
// owns the coach and will save it on its own disconnect, carrying this session's
// time with it.
//
// Time is therefore lost if the server dies without a clean teardown. Crediting
// periodically would fix that, but at the cost of a timer per session for a
// cosmetic statistic — the trade is deliberate.
func (s *Session) creditPlayTime() {
	if s.Coach == nil || s.playSince.IsZero() {
		return
	}
	secs := int64(time.Since(s.playSince) / time.Second)
	s.playSince = time.Time{} // credit at most once
	if secs <= 0 {
		return
	}
	s.Coach.Mu.Lock()
	s.Coach.TotalPlaySecs += secs
	s.Coach.Mu.Unlock()
}

// onClose handles connection teardown: deregister from world, clear connected.
func (s *Session) onClose() {
	s.kick() // idempotent: ensures quit is closed + writer stops

	// Before anything else, and in particular before the replaced-session return
	// below: the time was really played whether or not this session still owns
	// the coach.
	s.creditPlayTime()

	// If a newer login already replaced this session (kick/reconnect), do NOT
	// tear down the shared account/coach state -- the new session owns it now.
	if s.Account != nil {
		if !s.deps.Sessions.Remove(s.Account.ID, s) {
			s.log.Info("stale session closed (replaced by newer login)")
			return
		}
	}

	if s.Coach != nil {
		// Mid-fight disconnect: keep the fight alive (reconnect-ready) and start the
		// grace period rather than destroying it — the leaver's team auto-passes and
		// forfeits if it doesn't return; a practice fight is torn down. The fight
		// actor may persist stats for this coach concurrently; CoachRepo.Save + the
		// actor both take Coach.Mu, so the two saves are serialized (no struct race).
		if f := s.deps.Fights.ByCoach(s.Coach.ID); f != nil {
			s.deps.coachLeftFight(f, s.Coach.ID)
		}
		// A spectator dropping out just detaches from the fight it was watching.
		if f := s.spectating; f != nil {
			s.spectating = nil
			sess := s
			f.Post(func(f *Fight) { f.removeSpectator(sess) })
		}
		// Remove from matchmaking (queue or pending); notify a matched opponent.
		if pm := s.deps.Matchmaker.Remove(s.Coach.ID); pm != nil {
			if other := pm.other(s.Coach.ID); other != nil {
				_ = sendMatchCancelled(other.session)
			}
		}
		// Cancel any pending direct challenge; tell the other coach it fell through.
		if c := s.deps.Challenges.Remove(s.Coach.ID); c != nil {
			if other := c.other(s.Coach.ID); other != nil {
				if frame, err := buildChallengeCancelled(c.id); err == nil {
					_ = other.Send(frame)
				}
			}
		}
		// Cancel any in-progress exchange, notifying the other party (5111 with
		// the cancel reason).
		if ex := s.deps.Exchanges.Get(s.Coach.ID); ex != nil {
			other := ex.Other(s.Coach.ID)
			if s.deps.Exchanges.Remove(ex) && other != nil {
				if end, err := buildExchangeEnd(ex.ID, protocol.ExchangeEndCancel); err == nil {
					_ = other.Send(end)
				}
			}
		}
		// Break any 2v2 pairing and TELL the partner. Every other pairing in this
		// teardown already notifies its counterparty; this one did not, so a
		// partner who dropped during team formation left the other player sitting
		// in the fighter picker waiting for someone who was gone.
		s.deps.releaseTeamUpAndNotify(s.Coach.ID)

		// Despawn the leaver from coaches that currently see it (AoI known set),
		// then remove it from the registry.
		viewers := s.deps.World.LeaveAoI(s.Coach.ID)
		s.deps.World.Remove(s.Coach.ID)
		// Persist the coach's final position/stats so they survive reconnect.
		// Coach.Mu (taken inside Save) serializes this with any concurrent
		// fight-actor stat save.
		if err := s.deps.Store.Coaches.Save(s.Coach); err != nil {
			s.log.Warn("save coach on disconnect", "err", err)
		}
		if frame, err := buildActorDespawn([]uint{s.Coach.ID}); err == nil {
			for _, other := range viewers {
				_ = other.Send(frame)
			}
		}
		// Push offline notifications to friends/ignorers watching this coach.
		s.notifyPresence(s.Coach, false)
	}
	if s.Account != nil {
		_ = s.deps.Store.Accounts.SetConnected(s.Account.ID, false)
	}
}

// errPanicInHandler is returned when a handler panicked. It ends the offending
// session but leaves the server, and everyone else's fights, running.
var errPanicInHandler = errors.New("handler panicked")

// dispatchSafely runs a handler and converts a panic into an error.
//
// SECURITY: this is the blast-radius control for the whole server. Go terminates
// the entire process on an unrecovered panic in ANY goroutine, so before this
// existed a single malformed or hostile frame that reached a nil dereference took
// down every logged-in player and every fight in progress - not just the sender.
// Two such crashes were reachable from three packets (the matchmaker and
// challenge ghost-coach bugs; see searcherCoachID and sessionCoachID).
//
// Those root causes are fixed, but the class is not: this file cannot know what a
// future handler will dereference. The stack trace is logged with the opcode so a
// crash is still loud and diagnosable - the goal is containment, not silence.
//
// Note this deliberately does NOT keep the session alive. A handler that panicked
// left its coach in an unknown state, and continuing to serve it risks acting on
// corrupt data; the caller drops the connection on any error.
func (s *Session) dispatchSafely(f *protocol.C2SFrame) (err error) {
	defer func() {
		if r := recover(); r != nil {
			s.log.Error("PANIC in handler - session dropped, server survives",
				"opcode", f.Opcode,
				"payload_len", len(f.Payload),
				"panic", r,
				"stack", string(debug.Stack()))
			err = errPanicInHandler
		}
	}()
	return s.router.Dispatch(s, f)
}
