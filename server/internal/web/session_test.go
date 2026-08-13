package web

import (
	"net/http"
	"net/http/httptest"
	"net/url"
	"strings"
	"testing"
	"time"
)

func TestSessionCodecRoundTrip(t *testing.T) {
	c, ephemeral, err := newSessionCodec("a-secret")
	if err != nil {
		t.Fatalf("newSessionCodec: %v", err)
	}
	if ephemeral {
		t.Error("a configured secret must not be reported as ephemeral")
	}

	want := session{AccountID: 42, ImpersonatedID: 7, IssuedAt: time.Now().Truncate(time.Second)}
	got, err := c.decode(c.encode(want))
	if err != nil {
		t.Fatalf("decode: %v", err)
	}
	if got.AccountID != want.AccountID || got.ImpersonatedID != want.ImpersonatedID {
		t.Errorf("round trip = %+v, want %+v", got, want)
	}
	if !got.IssuedAt.Equal(want.IssuedAt) {
		t.Errorf("IssuedAt = %v, want %v", got.IssuedAt, want.IssuedAt)
	}
}

func TestSessionCodecRejectsTampering(t *testing.T) {
	c, _, _ := newSessionCodec("a-secret")
	valid := c.encode(session{AccountID: 1, IssuedAt: time.Now()})

	// Flip the account id in the payload and re-encode it without a matching
	// signature — the classic privilege escalation this signing exists to stop.
	forged := c.encode(session{AccountID: 999, IssuedAt: time.Now()})
	payload, _, _ := strings.Cut(forged, ".")
	_, sig, _ := strings.Cut(valid, ".")
	if _, err := c.decode(payload + "." + sig); err == nil {
		t.Error("a re-signed payload with somebody else's signature was accepted")
	}

	for _, bad := range []string{
		"", "garbage", valid + "x", "x." + valid,
		strings.ToUpper(valid),
	} {
		if _, err := c.decode(bad); err == nil {
			t.Errorf("malformed cookie %q was accepted", bad)
		}
	}
}

// A cookie signed by a different server must not be honoured, or two servers
// sharing a database would trust each other's sessions.
func TestSessionCodecRejectsForeignKey(t *testing.T) {
	mine, _, _ := newSessionCodec("my-secret")
	theirs, _, _ := newSessionCodec("their-secret")

	cookie := theirs.encode(session{AccountID: 1, IssuedAt: time.Now()})
	if _, err := mine.decode(cookie); err == nil {
		t.Error("a cookie signed with a different secret was accepted")
	}
}

func TestSessionExpires(t *testing.T) {
	c, _, _ := newSessionCodec("a-secret")
	old := session{AccountID: 1, IssuedAt: time.Now().Add(-sessionTTL - time.Minute)}
	if _, err := c.decode(c.encode(old)); err == nil {
		t.Error("an expired session was accepted")
	}
	fresh := session{AccountID: 1, IssuedAt: time.Now().Add(-time.Hour)}
	if _, err := c.decode(c.encode(fresh)); err != nil {
		t.Errorf("a session inside the TTL was rejected: %v", err)
	}
}

func TestEmptySecretProducesEphemeralKey(t *testing.T) {
	c1, ephemeral, err := newSessionCodec("")
	if err != nil {
		t.Fatalf("newSessionCodec: %v", err)
	}
	if !ephemeral {
		t.Error("an empty secret must be reported as ephemeral so New can warn")
	}
	// Two blank-secret codecs must not agree, which is exactly why an
	// operator gets warned: restarting invalidates every session.
	c2, _, _ := newSessionCodec("")
	if _, err := c2.decode(c1.encode(session{AccountID: 1, IssuedAt: time.Now()})); err == nil {
		t.Error("two ephemeral keys agreed — they are supposed to be random")
	}
}

func TestEffectiveIDPrefersImpersonation(t *testing.T) {
	if got := (session{AccountID: 1}).effectiveID(); got != 1 {
		t.Errorf("effectiveID = %d, want 1", got)
	}
	s := session{AccountID: 1, ImpersonatedID: 2}
	if got := s.effectiveID(); got != 2 {
		t.Errorf("effectiveID while impersonating = %d, want 2", got)
	}
	if !s.impersonating() {
		t.Error("impersonating() should be true")
	}
}

// ---------------------------------------------------------------------------
// CSRF
// ---------------------------------------------------------------------------

func TestCSRFTokenIsPerAccount(t *testing.T) {
	s, _ := newTestServer(t, nil)

	a := s.csrfToken(1)
	b := s.csrfToken(2)
	if a == b {
		t.Error("two accounts share a CSRF token — one could replay the other's forms")
	}
	if a == "" {
		t.Error("empty CSRF token")
	}
	if s.csrfToken(1) != a {
		t.Error("the token for one account is not stable within a day")
	}
}

func TestCSRFAcceptsYesterdayButNotTomorrow(t *testing.T) {
	s, _ := newTestServer(t, nil)
	today := time.Now().UTC().Unix() / 86400

	// formWith builds the POST a browser would send carrying that token.
	formWith := func(tok string) *http.Request {
		body := url.Values{"csrf_token": {tok}}.Encode()
		req := httptest.NewRequest(http.MethodPost, "/", strings.NewReader(body))
		req.Header.Set("Content-Type", "application/x-www-form-urlencoded")
		return req
	}

	if !s.verifyCSRF(formWith(s.csrfTokenForBucket(1, today)), 1) {
		t.Error("today's token was rejected")
	}
	// A form opened just before midnight must still submit.
	if !s.verifyCSRF(formWith(s.csrfTokenForBucket(1, today-1)), 1) {
		t.Error("yesterday's token was rejected — a form open over midnight would break")
	}
	// Anything older, or from another account, must not work.
	if s.verifyCSRF(formWith(s.csrfTokenForBucket(1, today-2)), 1) {
		t.Error("a two-day-old token was accepted")
	}
	if s.verifyCSRF(formWith(s.csrfTokenForBucket(2, today)), 1) {
		t.Error("another account's token was accepted")
	}
	if s.verifyCSRF(formWith(""), 1) {
		t.Error("an empty token was accepted")
	}
}
