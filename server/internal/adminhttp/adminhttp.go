// Package adminhttp serves a small loopback-oriented HTTP server exposing
// basic operational endpoints: a liveness probe (/healthz), Go's stdlib
// profiler (/debug/pprof/*), and a cheap manual-inspection endpoint
// (/stats) reporting online-player/active-fight counts. It's intentionally
// minimal (stdlib only, no new dependencies) -- see
// docs/08-java-parity-roadmap.md §8.3.2 and docs/06-config-and-ops.md §6.5.
//
// This is a separate HTTP server from the account web portal
// (internal/webadmin): the portal is a public-facing site meant to be
// exposed to end users, while this one is meant to stay bound to a
// loopback/internal-only address for operators and debugging tooling
// (e.g. `go tool pprof`).
package adminhttp

import (
	"context"
	"encoding/json"
	"errors"
	"net"
	"net/http"
	"net/http/pprof"
	"time"

	"github.com/rs/zerolog"
)

// Deps bundles the read-only counters /stats reports. Both fields are
// optional (nil-safe): a nil World or Fights simply omits/zeroes that
// counter, so this package doesn't force every caller (e.g. minimal test
// setups) to wire up the full app.
type Deps struct {
	// World reports the number of currently-online coaches, via Len().
	World interface{ Len() int }
	// Fights reports the number of currently-active fights, via Count().
	Fights interface{ Count() int }
}

// healthResponse is the JSON body served by /healthz.
type healthResponse struct {
	Status        string `json:"status"`
	UptimeSeconds int64  `json:"uptime_seconds"`
}

// statsResponse is the JSON body served by /stats.
type statsResponse struct {
	OnlinePlayers int `json:"online_players"`
	ActiveFights  int `json:"active_fights"`
}

// NewMux builds the admin HTTP handler: /healthz, /debug/pprof/*, and
// /stats. Uses a dedicated http.ServeMux, explicitly registering each
// pprof handler function rather than relying on net/http/pprof's init()
// side effect (which unconditionally registers onto http.DefaultServeMux
// as an unavoidable consequence of importing the package, regardless of
// how it's used). The important property this buys: the admin
// http.Server's Handler is always this dedicated mux, never
// http.DefaultServeMux/nil, so pprof is only ever actually served over the
// network on the intended AdminAddr -- nothing else in this codebase calls
// http.ListenAndServe(addr, nil), so DefaultServeMux's pprof registrations
// are never network-reachable.
func NewMux(deps Deps, startedAt time.Time) *http.ServeMux {
	mux := http.NewServeMux()

	mux.HandleFunc("/healthz", func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		_ = json.NewEncoder(w).Encode(healthResponse{
			Status:        "ok",
			UptimeSeconds: int64(time.Since(startedAt).Seconds()),
		})
	})

	mux.HandleFunc("/stats", func(w http.ResponseWriter, r *http.Request) {
		stats := statsResponse{}
		if deps.World != nil {
			stats.OnlinePlayers = deps.World.Len()
		}
		if deps.Fights != nil {
			stats.ActiveFights = deps.Fights.Count()
		}
		w.Header().Set("Content-Type", "application/json")
		_ = json.NewEncoder(w).Encode(stats)
	})

	// Registering these directly on our own mux (rather than relying on
	// net/http/pprof's init() side effect, which only touches
	// http.DefaultServeMux) is what keeps pprof off of any other server
	// sharing the default mux.
	mux.HandleFunc("/debug/pprof/", pprof.Index)
	mux.HandleFunc("/debug/pprof/cmdline", pprof.Cmdline)
	mux.HandleFunc("/debug/pprof/profile", pprof.Profile)
	mux.HandleFunc("/debug/pprof/symbol", pprof.Symbol)
	mux.HandleFunc("/debug/pprof/trace", pprof.Trace)

	return mux
}

// Serve runs the admin HTTP server on addr until ctx is canceled, then
// gracefully shuts it down. Mirrors webadmin.Serve's shape so both
// "internal HTTP server" packages behave consistently.
func Serve(ctx context.Context, addr string, deps Deps, logger zerolog.Logger) error {
	ln, err := net.Listen("tcp", addr)
	if err != nil {
		return err
	}
	return ServeListener(ctx, ln, deps, logger)
}

// ServeListener is like Serve but accepts an already-bound net.Listener,
// letting callers (notably tests) resolve the actual bound address --
// e.g. when addr uses an OS-assigned port (":0" / "127.0.0.1:0") -- before
// the accept loop starts, which a bare Serve(ctx, addr, ...) can't expose
// since binding happens inside its own goroutine.
func ServeListener(ctx context.Context, ln net.Listener, deps Deps, logger zerolog.Logger) error {
	srv := &http.Server{
		Handler:           NewMux(deps, time.Now()),
		ReadHeaderTimeout: 10 * time.Second,
		ReadTimeout:       20 * time.Second,
		WriteTimeout:      30 * time.Second,
		IdleTimeout:       120 * time.Second,
	}

	errCh := make(chan error, 1)
	go func() {
		logger.Info().Str("addr", ln.Addr().String()).Msg("admin http listening")
		if err := srv.Serve(ln); err != nil && !errors.Is(err, http.ErrServerClosed) {
			errCh <- err
			return
		}
		errCh <- nil
	}()

	select {
	case <-ctx.Done():
		shutdownCtx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
		defer cancel()
		if err := srv.Shutdown(shutdownCtx); err != nil {
			logger.Warn().Err(err).Msg("admin http shutdown error")
		}
		logger.Info().Msg("admin http stopped")
		return nil
	case err := <-errCh:
		return err
	}
}
