package e2e

import (
	"encoding/json"
	"net/http"
	"testing"
	"time"
)

// TestE2E_AdminHTTPHealthzAndStats verifies the admin/observability HTTP
// server (internal/adminhttp) is actually wired into the app's Serve
// lifecycle and bound to cfg.Server.AdminAddr, matching
// docs/08-java-parity-roadmap.md §8.3.2. Exercises /healthz and /stats
// through the real running App, not just internal/adminhttp's own unit
// tests.
func TestE2E_AdminHTTPHealthzAndStats(t *testing.T) {
	a, _ := startTestServer(t)

	adminAddr := a.AdminAddr()
	if adminAddr == "" {
		t.Fatal("AdminAddr() is empty, want a bound admin http address")
	}

	resp := httpGetWithRetry(t, "http://"+adminAddr+"/healthz")
	defer resp.Body.Close()
	if resp.StatusCode != http.StatusOK {
		t.Fatalf("/healthz status = %d, want 200", resp.StatusCode)
	}
	var health struct {
		Status string `json:"status"`
	}
	if err := json.NewDecoder(resp.Body).Decode(&health); err != nil {
		t.Fatalf("decode /healthz body: %v", err)
	}
	if health.Status != "ok" {
		t.Errorf("/healthz status field = %q, want %q", health.Status, "ok")
	}

	// Log a coach in so /stats reports a non-zero online count.
	seedAccount(t, a, "alice", "pw")
	c := dialTestClient(t, a.Addr())
	c.mustLogin("alice", "pw", "Alice")

	statsResp := httpGetWithRetry(t, "http://"+adminAddr+"/stats")
	defer statsResp.Body.Close()
	var stats struct {
		OnlinePlayers int `json:"online_players"`
		ActiveFights  int `json:"active_fights"`
	}
	if err := json.NewDecoder(statsResp.Body).Decode(&stats); err != nil {
		t.Fatalf("decode /stats body: %v", err)
	}
	if stats.OnlinePlayers != 1 {
		t.Errorf("/stats online_players = %d, want 1", stats.OnlinePlayers)
	}
}

// httpGetWithRetry retries an HTTP GET for a short window, tolerating the
// small startup race between startTestServer returning and the admin
// HTTP server's accept loop actually being ready to serve requests (the
// listener itself is bound synchronously in Listen, but srv.Serve(ln)
// still starts inside a background goroutine kicked off by app.Serve).
func httpGetWithRetry(t *testing.T, url string) *http.Response {
	t.Helper()
	deadline := time.Now().Add(3 * time.Second)
	var lastErr error
	for time.Now().Before(deadline) {
		resp, err := http.Get(url)
		if err == nil {
			return resp
		}
		lastErr = err
		time.Sleep(20 * time.Millisecond)
	}
	t.Fatalf("GET %s never succeeded: %v", url, lastErr)
	return nil
}
