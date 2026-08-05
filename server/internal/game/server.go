package game

import (
	"context"
	"net"
	"sync"
)

// Server accepts client connections and runs a Session per connection.
type Server struct {
	addr   string
	router *Router
	deps   *Deps

	mu     sync.Mutex
	active map[*Session]struct{} // live sessions, for shutdown cleanup
}

// NewServer builds a server with all handlers registered.
func NewServer(addr string, deps *Deps) *Server {
	router := NewRouter(deps.Log)
	deps.initArenas() // build the arena registry from the loaded fight maps
	RegisterAll(router, deps)
	return &Server{addr: addr, router: router, deps: deps, active: make(map[*Session]struct{})}
}

func (s *Server) track(sess *Session)   { s.mu.Lock(); s.active[sess] = struct{}{}; s.mu.Unlock() }
func (s *Server) untrack(sess *Session) { s.mu.Lock(); delete(s.active, sess); s.mu.Unlock() }

// closeAll kicks every live session (unblocks their read loops) on shutdown, so
// no session goroutine leaks after the context is cancelled.
func (s *Server) closeAll() {
	s.mu.Lock()
	sessions := make([]*Session, 0, len(s.active))
	for sess := range s.active {
		sessions = append(sessions, sess)
	}
	s.mu.Unlock()
	for _, sess := range sessions {
		sess.kick()
	}
}

// Listen binds the TCP port without accepting connections yet.
//
// Binding separately from serving lets a caller report a port clash before it
// has told the operator the server is up.
func (s *Server) Listen(ctx context.Context) (net.Listener, error) {
	var lc net.ListenConfig
	return lc.Listen(ctx, "tcp", s.addr)
}

// ListenAndServe binds the TCP port and serves until ctx is cancelled.
func (s *Server) ListenAndServe(ctx context.Context) error {
	ln, err := s.Listen(ctx)
	if err != nil {
		return err
	}
	return s.Serve(ctx, ln)
}

// Serve accepts connections on ln until ctx is cancelled.
func (s *Server) Serve(ctx context.Context, ln net.Listener) error {
	// Debug, not Info: cmd/server already tells the operator the listen address
	// in its startup banner, and repeating it is noise.
	s.deps.Log.Debug("listening", "addr", ln.Addr().String())

	go func() {
		<-ctx.Done()
		_ = ln.Close()
		s.closeAll()
	}()

	for {
		conn, err := ln.Accept()
		if err != nil {
			select {
			case <-ctx.Done():
				return nil
			default:
				s.deps.Log.Warn("accept error", "err", err)
				continue
			}
		}
		if tcp, ok := conn.(*net.TCPConn); ok {
			_ = tcp.SetNoDelay(true)
		}
		sess := newSession(conn, s.router, s.deps)
		s.track(sess)
		go func() {
			defer s.untrack(sess)
			sess.serve()
		}()
	}
}
