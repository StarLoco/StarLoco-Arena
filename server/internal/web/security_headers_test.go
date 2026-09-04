package web

import (
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/config"
)

// TestSecurityHeadersBaseline pins the headers every response must carry.
//
// The CSP is the load-bearing one: default-src 'none' with no script source at
// all means a stored-XSS bug - in a coach name, a guild name, a bug report -
// could not execute. That is a real mitigation, not a formality, and it is worth
// a test so nobody relaxes it to add an analytics snippet without noticing.
func TestSecurityHeadersBaseline(t *testing.T) {
	h := securityHeaders(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
	}))
	rec := httptest.NewRecorder()
	h.ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/", nil))

	want := map[string]string{
		"X-Content-Type-Options": "nosniff",
		"Referrer-Policy":        "same-origin",
	}
	for k, v := range want {
		if got := rec.Header().Get(k); got != v {
			t.Errorf("%s = %q, want %q", k, got, v)
		}
	}
	csp := rec.Header().Get("Content-Security-Policy")
	for _, must := range []string{
		"default-src 'none'",
		"frame-ancestors 'none'", // clickjacking
		"base-uri 'none'",
		"form-action 'self'",
	} {
		if !contains(csp, must) {
			t.Errorf("CSP is missing %q; got %q", must, csp)
		}
	}
	if contains(csp, "script-src") {
		t.Errorf("CSP now allows scripts (%q). The portal ships none, and "+
			"default-src 'none' is what makes stored XSS unexploitable", csp)
	}
}

// TestHSTSOnlyWhenHTTPS pins the gate. Sending HSTS over plain HTTP is at best
// ignored and at worst locks a developer out of their own http://localhost portal
// for a year, so it follows secure_cookies - the existing "this deployment is
// HTTPS" switch - rather than being unconditional or having a second flag.
func TestHSTSOnlyWhenHTTPS(t *testing.T) {
	for _, tc := range []struct {
		https bool
		want  bool
	}{{false, false}, {true, true}} {
		h := securityHeadersHSTS(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {}), tc.https)
		rec := httptest.NewRecorder()
		h.ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/", nil))
		got := rec.Header().Get("Strict-Transport-Security") != ""
		if got != tc.want {
			t.Errorf("https=%v: HSTS present = %v, want %v", tc.https, got, tc.want)
		}
		if tc.https && !contains(rec.Header().Get("Strict-Transport-Security"), "max-age=31536000") {
			t.Errorf("HSTS max-age is not a year: %q",
				rec.Header().Get("Strict-Transport-Security"))
		}
		// preload is a months-long commitment; it must be opt-in, never shipped.
		if contains(rec.Header().Get("Strict-Transport-Security"), "preload") {
			t.Error("HSTS must not send preload: removal takes months and it is the " +
				"operator's decision, not ours")
		}
	}
}

func contains(s, sub string) bool {
	return len(sub) > 0 && len(s) >= len(sub) && indexOf(s, sub) >= 0
}

func indexOf(s, sub string) int {
	for i := 0; i+len(sub) <= len(s); i++ {
		if s[i:i+len(sub)] == sub {
			return i
		}
	}
	return -1
}

// TestRoutesWireHSTSToTheConfig covers the WIRING through the real route chain.
//
// My first version built the header chain inside the test, so a mutation
// hard-coding the gate to false survived - the same "testing the predicate, not
// the caller" mistake this codebase has made repeatedly. Driving s.routes() is
// what actually proves the config reaches the header.
func TestRoutesWireHSTSToTheConfig(t *testing.T) {
	for _, secure := range []bool{false, true} {
		s, _ := newTestServer(t, func(c *config.WebConfig) { c.SecureCookies = secure })
		rec := httptest.NewRecorder()
		s.Handler().ServeHTTP(rec, httptest.NewRequest(http.MethodGet, "/", nil))

		got := rec.Header().Get("Strict-Transport-Security") != ""
		if got != secure {
			t.Errorf("secure_cookies=%v -> HSTS present %v, want %v: Handler() must "+
				"pass the config through, not a constant", secure, got, secure)
		}
		// And the baseline must survive on the real chain too.
		if rec.Header().Get("Content-Security-Policy") == "" {
			t.Error("Handler() served a response with no CSP")
		}
	}
}
