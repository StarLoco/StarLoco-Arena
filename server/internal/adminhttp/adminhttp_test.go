package adminhttp

import (
	"context"
	"encoding/json"
	"net"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"

	"github.com/rs/zerolog"
)

// fakeCounter is a minimal World/Fights stand-in for exercising /stats
// without needing a real *world.Registry / *combat.Manager.
type fakeCounter struct {
	n int
}

func (f fakeCounter) Len() int   { return f.n }
func (f fakeCounter) Count() int { return f.n }

func TestHealthzReturnsOK(t *testing.T) {
	mux := NewMux(Deps{}, time.Now().Add(-5*time.Second))
	rec := httptest.NewRecorder()
	req := httptest.NewRequest(http.MethodGet, "/healthz", nil)
	mux.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, want 200", rec.Code)
	}
	var body healthResponse
	if err := json.Unmarshal(rec.Body.Bytes(), &body); err != nil {
		t.Fatalf("decode body: %v", err)
	}
	if body.Status != "ok" {
		t.Errorf("status field = %q, want %q", body.Status, "ok")
	}
	if body.UptimeSeconds < 5 {
		t.Errorf("uptime_seconds = %d, want >= 5", body.UptimeSeconds)
	}
}

func TestStatsReportsCounters(t *testing.T) {
	mux := NewMux(Deps{
		World:  fakeCounter{n: 3},
		Fights: fakeCounter{n: 2},
	}, time.Now())
	rec := httptest.NewRecorder()
	req := httptest.NewRequest(http.MethodGet, "/stats", nil)
	mux.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("status = %d, want 200", rec.Code)
	}
	var body statsResponse
	if err := json.Unmarshal(rec.Body.Bytes(), &body); err != nil {
		t.Fatalf("decode body: %v", err)
	}
	if body.OnlinePlayers != 3 {
		t.Errorf("online_players = %d, want 3", body.OnlinePlayers)
	}
	if body.ActiveFights != 2 {
		t.Errorf("active_fights = %d, want 2", body.ActiveFights)
	}
}

func TestStatsWithNilDepsReportsZero(t *testing.T) {
	mux := NewMux(Deps{}, time.Now())
	rec := httptest.NewRecorder()
	req := httptest.NewRequest(http.MethodGet, "/stats", nil)
	mux.ServeHTTP(rec, req)

	var body statsResponse
	if err := json.Unmarshal(rec.Body.Bytes(), &body); err != nil {
		t.Fatalf("decode body: %v", err)
	}
	if body.OnlinePlayers != 0 || body.ActiveFights != 0 {
		t.Errorf("expected zero counters with nil Deps fields, got %+v", body)
	}
}

func TestPprofIndexIsRegistered(t *testing.T) {
	mux := NewMux(Deps{}, time.Now())
	rec := httptest.NewRecorder()
	req := httptest.NewRequest(http.MethodGet, "/debug/pprof/", nil)
	mux.ServeHTTP(rec, req)

	if rec.Code != http.StatusOK {
		t.Fatalf("/debug/pprof/ status = %d, want 200", rec.Code)
	}
}

func TestServeStartsAndStopsOnContextCancel(t *testing.T) {
	// Bind to an OS-assigned free loopback port to avoid clashing with a
	// real admin server or other tests running in parallel.
	ln, err := net.Listen("tcp", "127.0.0.1:0")
	if err != nil {
		t.Fatalf("reserve port: %v", err)
	}
	addr := ln.Addr().String()
	_ = ln.Close()

	ctx, cancel := context.WithCancel(context.Background())
	done := make(chan error, 1)
	go func() {
		done <- Serve(ctx, addr, Deps{}, zerolog.Nop())
	}()

	// Poll until the server is actually accepting connections.
	deadline := time.Now().Add(2 * time.Second)
	var resp *http.Response
	for time.Now().Before(deadline) {
		resp, err = http.Get("http://" + addr + "/healthz")
		if err == nil {
			break
		}
		time.Sleep(10 * time.Millisecond)
	}
	if err != nil {
		t.Fatalf("GET /healthz never succeeded: %v", err)
	}
	if resp.StatusCode != http.StatusOK {
		t.Errorf("status = %d, want 200", resp.StatusCode)
	}
	_ = resp.Body.Close()

	cancel()
	select {
	case err := <-done:
		if err != nil {
			t.Errorf("Serve returned error after cancel: %v", err)
		}
	case <-time.After(2 * time.Second):
		t.Fatal("Serve did not shut down within 2s of context cancellation")
	}
}
