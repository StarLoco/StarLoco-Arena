package update

import (
	"context"
	"errors"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"
)

// stub serves a canned /releases/latest response and records the request.
func stub(t *testing.T, status int, body string) (*Checker, *http.Request) {
	t.Helper()
	var got *http.Request
	srv := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		got = r.Clone(context.Background())
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(status)
		_, _ = w.Write([]byte(body))
	}))
	t.Cleanup(srv.Close)
	return &Checker{BaseURL: srv.URL, Owner: "o", Repo: "r", Timeout: 2 * time.Second}, got
}

func TestCheckReportsNewerRelease(t *testing.T) {
	c, _ := stub(t, 200, `{"tag_name":"v1.2.0","html_url":"https://example.test/rel"}`)

	res, err := c.Check(context.Background(), "v1.1.0")
	if err != nil {
		t.Fatalf("Check: %v", err)
	}
	if !res.Available {
		t.Error("v1.2.0 > v1.1.0 should be reported as available")
	}
	if res.Major {
		t.Error("minor bump must not be flagged as a major upgrade")
	}
	if res.Latest != "v1.2.0" || res.URL != "https://example.test/rel" {
		t.Errorf("result = %+v", res)
	}
}

func TestCheckSameAndOlderReportNothing(t *testing.T) {
	for _, current := range []string{"v1.2.0", "v1.3.0"} {
		c, _ := stub(t, 200, `{"tag_name":"v1.2.0"}`)
		res, err := c.Check(context.Background(), current)
		if err != nil {
			t.Fatalf("Check(%s): %v", current, err)
		}
		if res.Available {
			t.Errorf("current=%s vs latest=v1.2.0 should not offer an update", current)
		}
	}
}

func TestCheckFlagsMajorUpgrade(t *testing.T) {
	c, _ := stub(t, 200, `{"tag_name":"v2.0.0"}`)
	res, err := c.Check(context.Background(), "v1.9.9")
	if err != nil {
		t.Fatalf("Check: %v", err)
	}
	if !res.Available || !res.Major {
		t.Errorf("major bump not flagged: %+v", res)
	}
}

// A pre-release must never look newer than the final release of that version.
func TestPrereleaseOrdering(t *testing.T) {
	c, _ := stub(t, 200, `{"tag_name":"v1.2.0-rc.1"}`)
	res, err := c.Check(context.Background(), "v1.2.0")
	if err != nil {
		t.Fatalf("Check: %v", err)
	}
	if res.Available {
		t.Error("v1.2.0-rc.1 must not be offered to a v1.2.0 install")
	}
}

func TestNoReleasesIsDistinctError(t *testing.T) {
	c, _ := stub(t, 404, `{"message":"Not Found"}`)
	if _, err := c.Check(context.Background(), "v1.0.0"); !errors.Is(err, ErrNoRelease) {
		t.Errorf("err = %v, want ErrNoRelease", err)
	}
}

func TestDevBuildSkipsNetwork(t *testing.T) {
	// BaseURL points nowhere reachable: if Check dialed it, this would hang or
	// fail differently. It must bail out before any request.
	c := &Checker{BaseURL: "http://127.0.0.1:1", Timeout: time.Second}
	if _, err := c.Check(context.Background(), "dev"); !errors.Is(err, ErrDevBuild) {
		t.Errorf("err = %v, want ErrDevBuild", err)
	}
}

func TestServerErrorIsReportedNotPanicked(t *testing.T) {
	c, _ := stub(t, 500, `boom`)
	if _, err := c.Check(context.Background(), "v1.0.0"); err == nil {
		t.Error("expected an error for HTTP 500")
	}
}

func TestUnreachableHostFailsQuietly(t *testing.T) {
	c := &Checker{BaseURL: "http://127.0.0.1:1", Timeout: 500 * time.Millisecond}
	start := time.Now()
	if _, err := c.Check(context.Background(), "v1.0.0"); err == nil {
		t.Error("expected an error for an unreachable host")
	}
	if elapsed := time.Since(start); elapsed > 5*time.Second {
		t.Errorf("check took %v — the timeout is not being honoured", elapsed)
	}
}

// GitHub rejects API requests that arrive without a User-Agent, so this is a
// functional requirement rather than politeness.
func TestSendsRequiredHeaders(t *testing.T) {
	var seen http.Header
	srv := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		seen = r.Header.Clone()
		_, _ = w.Write([]byte(`{"tag_name":"v1.0.0"}`))
	}))
	defer srv.Close()

	c := &Checker{BaseURL: srv.URL, Owner: "o", Repo: "r"}
	if _, err := c.Latest(context.Background()); err != nil {
		t.Fatalf("Latest: %v", err)
	}
	if seen.Get("User-Agent") == "" {
		t.Error("User-Agent must be set or GitHub returns 403")
	}
	if got := seen.Get("Accept"); got != "application/vnd.github+json" {
		t.Errorf("Accept = %q", got)
	}
}

func TestCancelledContextStops(t *testing.T) {
	srv := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		<-r.Context().Done()
	}))
	defer srv.Close()

	ctx, cancel := context.WithCancel(context.Background())
	cancel()
	c := &Checker{BaseURL: srv.URL, Owner: "o", Repo: "r"}
	if _, err := c.Check(ctx, "v1.0.0"); err == nil {
		t.Error("expected an error when the context is already cancelled")
	}
}
