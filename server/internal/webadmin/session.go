package webadmin

import (
	"crypto/hmac"
	"crypto/rand"
	"crypto/sha256"
	"crypto/subtle"
	"encoding/base64"
	"errors"
	"fmt"
	"net/http"
	"strconv"
	"strings"
	"time"
)

// sessionCookieName is the name of the signed session cookie.
const sessionCookieName = "arena_session"

// sessionTTL is how long a session stays valid without re-login.
const sessionTTL = 7 * 24 * time.Hour

// session is the authenticated state carried in the signed cookie. It is
// intentionally tiny (just IDs + issue time) so the cookie stays small and
// every request re-loads fresh account rows from the DB rather than
// trusting stale cookie contents for authorization decisions.
//
// AccountID is the *real* logged-in account. ImpersonatedID, when non-zero,
// is the account an admin is currently viewing-as; the effective identity
// for data access is ImpersonatedID while the audit identity remains
// AccountID. Non-admins never get a non-zero ImpersonatedID (the handler
// gates it).
type session struct {
	AccountID      uint
	ImpersonatedID uint
	IssuedAt       time.Time
}

// effectiveID returns the account whose data the request should see:
// the impersonated account when impersonating, else the real account.
func (s session) effectiveID() uint {
	if s.ImpersonatedID != 0 {
		return s.ImpersonatedID
	}
	return s.AccountID
}

// impersonating reports whether an admin is currently viewing-as another
// account.
func (s session) impersonating() bool { return s.ImpersonatedID != 0 }

// sessionCodec signs and verifies session cookies with HMAC-SHA256 over a
// compact "accountID.impersonatedID.issuedUnix" payload. It deliberately
// avoids encryption (nothing secret is stored, only IDs + a timestamp) --
// integrity/authenticity via HMAC is sufficient to prevent forgery, and the
// server always re-validates the referenced account against the DB.
type sessionCodec struct {
	key []byte
}

// newSessionCodec builds a codec from the configured secret. An empty
// secret triggers a random ephemeral key (logged by the caller): sessions
// then don't survive restarts and can't be shared across instances, which
// is acceptable for local dev but not production.
func newSessionCodec(secret string) (*sessionCodec, bool, error) {
	if strings.TrimSpace(secret) == "" {
		key := make([]byte, 32)
		if _, err := rand.Read(key); err != nil {
			return nil, false, fmt.Errorf("webadmin: generate ephemeral session key: %w", err)
		}
		return &sessionCodec{key: key}, true, nil
	}
	// Derive a fixed-length key from the provided secret so operators can
	// use an arbitrary-length passphrase.
	sum := sha256.Sum256([]byte(secret))
	return &sessionCodec{key: sum[:]}, false, nil
}

// encode produces the signed cookie value "payload.signature", both
// base64url (no padding).
func (c *sessionCodec) encode(s session) string {
	payload := fmt.Sprintf("%d.%d.%d", s.AccountID, s.ImpersonatedID, s.IssuedAt.Unix())
	sig := c.sign(payload)
	enc := base64.RawURLEncoding
	return enc.EncodeToString([]byte(payload)) + "." + enc.EncodeToString(sig)
}

// errBadSession indicates a malformed, tampered, or expired cookie.
var errBadSession = errors.New("webadmin: invalid session")

// decode verifies and parses a signed cookie value, enforcing the TTL.
func (c *sessionCodec) decode(value string) (session, error) {
	enc := base64.RawURLEncoding
	parts := strings.Split(value, ".")
	if len(parts) != 2 {
		return session{}, errBadSession
	}
	payloadBytes, err := enc.DecodeString(parts[0])
	if err != nil {
		return session{}, errBadSession
	}
	sig, err := enc.DecodeString(parts[1])
	if err != nil {
		return session{}, errBadSession
	}

	expected := c.sign(string(payloadBytes))
	if subtle.ConstantTimeCompare(sig, expected) != 1 {
		return session{}, errBadSession
	}

	fields := strings.Split(string(payloadBytes), ".")
	if len(fields) != 3 {
		return session{}, errBadSession
	}
	accountID, err1 := strconv.ParseUint(fields[0], 10, 64)
	impID, err2 := strconv.ParseUint(fields[1], 10, 64)
	issued, err3 := strconv.ParseInt(fields[2], 10, 64)
	if err1 != nil || err2 != nil || err3 != nil {
		return session{}, errBadSession
	}

	s := session{
		AccountID:      uint(accountID),
		ImpersonatedID: uint(impID),
		IssuedAt:       time.Unix(issued, 0),
	}
	if time.Since(s.IssuedAt) > sessionTTL {
		return session{}, errBadSession
	}
	return s, nil
}

func (c *sessionCodec) sign(payload string) []byte {
	mac := hmac.New(sha256.New, c.key)
	mac.Write([]byte(payload))
	return mac.Sum(nil)
}

// write sets the signed session cookie on the response.
func (h *Handler) writeSession(w http.ResponseWriter, s session) {
	http.SetCookie(w, &http.Cookie{
		Name:     sessionCookieName,
		Value:    h.codec.encode(s),
		Path:     "/",
		HttpOnly: true,
		Secure:   h.secureCookies,
		SameSite: http.SameSiteLaxMode,
		Expires:  time.Now().Add(sessionTTL),
		MaxAge:   int(sessionTTL / time.Second),
	})
}

// clearSession removes the session cookie (logout).
func (h *Handler) clearSession(w http.ResponseWriter) {
	http.SetCookie(w, &http.Cookie{
		Name:     sessionCookieName,
		Value:    "",
		Path:     "/",
		HttpOnly: true,
		Secure:   h.secureCookies,
		SameSite: http.SameSiteLaxMode,
		MaxAge:   -1,
	})
}

// readSession extracts and verifies the session cookie from a request,
// returning (session{}, false) when absent or invalid.
func (h *Handler) readSession(r *http.Request) (session, bool) {
	cookie, err := r.Cookie(sessionCookieName)
	if err != nil {
		return session{}, false
	}
	s, err := h.codec.decode(cookie.Value)
	if err != nil {
		return session{}, false
	}
	return s, true
}
