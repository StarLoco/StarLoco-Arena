package web

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

// sessionTTL is how long a login lasts without signing in again.
const sessionTTL = 7 * 24 * time.Hour

// session is the authenticated state carried in the signed cookie. It is
// deliberately tiny — two ids and a timestamp — so the cookie stays small and,
// more importantly, so it never becomes a cache: every request re-loads the
// account row from the database rather than trusting what the cookie says
// about it. A player deleted or demoted mid-session is therefore handled on
// their very next click.
//
// AccountID is the real signed-in account and stays the audit identity.
// ImpersonatedID, when non-zero, is the account an admin is currently viewing
// as; that becomes the effective identity for data access only. A non-admin
// never gets a non-zero ImpersonatedID because the handler that sets it is
// admin-gated.
type session struct {
	AccountID      uint
	ImpersonatedID uint
	IssuedAt       time.Time
}

// effectiveID is the account whose data the request should see.
func (s session) effectiveID() uint {
	if s.ImpersonatedID != 0 {
		return s.ImpersonatedID
	}
	return s.AccountID
}

// impersonating reports whether an admin is currently viewing as someone else.
func (s session) impersonating() bool { return s.ImpersonatedID != 0 }

// sessionCodec signs and verifies session cookies with HMAC-SHA256 over a
// compact "accountID.impersonatedID.issuedUnix" payload.
//
// There is deliberately no encryption. The payload holds nothing secret — two
// row ids and a timestamp — so integrity is the only property needed, and an
// HMAC provides it without the key-management and silent-corruption failure
// modes of home-made encryption.
type sessionCodec struct {
	key []byte
}

// newSessionCodec derives the signing key from the configured secret. It
// reports whether it had to invent an ephemeral one, which the caller warns
// about: sessions then do not survive a restart.
func newSessionCodec(secret string) (*sessionCodec, bool, error) {
	if strings.TrimSpace(secret) == "" {
		key := make([]byte, 32)
		if _, err := rand.Read(key); err != nil {
			return nil, false, fmt.Errorf("web: generate ephemeral session key: %w", err)
		}
		return &sessionCodec{key: key}, true, nil
	}
	// Hash rather than use the bytes directly so an operator can paste a
	// passphrase of any length and still get a full-strength 256-bit key.
	sum := sha256.Sum256([]byte(secret))
	return &sessionCodec{key: sum[:]}, false, nil
}

// encode produces the cookie value "payload.signature", both base64url.
func (c *sessionCodec) encode(s session) string {
	payload := fmt.Sprintf("%d.%d.%d", s.AccountID, s.ImpersonatedID, s.IssuedAt.Unix())
	enc := base64.RawURLEncoding
	return enc.EncodeToString([]byte(payload)) + "." + enc.EncodeToString(c.sign(payload))
}

// errBadSession covers every rejection: malformed, forged, or expired.
var errBadSession = errors.New("web: invalid session")

// decode verifies a cookie value and enforces the TTL.
func (c *sessionCodec) decode(value string) (session, error) {
	enc := base64.RawURLEncoding
	payloadPart, sigPart, ok := strings.Cut(value, ".")
	if !ok {
		return session{}, errBadSession
	}
	payloadBytes, err := enc.DecodeString(payloadPart)
	if err != nil {
		return session{}, errBadSession
	}
	sig, err := enc.DecodeString(sigPart)
	if err != nil {
		return session{}, errBadSession
	}

	// Constant-time: a timing-variable compare here would leak the signature
	// one byte at a time to anyone willing to make enough requests.
	if subtle.ConstantTimeCompare(sig, c.sign(string(payloadBytes))) != 1 {
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

// writeSession sets the signed cookie.
func (s *Server) writeSession(w http.ResponseWriter, sess session) {
	http.SetCookie(w, &http.Cookie{
		Name:     sessionCookieName,
		Value:    s.codec.encode(sess),
		Path:     "/",
		HttpOnly: true,
		Secure:   s.cfg.SecureCookies,
		SameSite: http.SameSiteLaxMode,
		Expires:  time.Now().Add(sessionTTL),
		MaxAge:   int(sessionTTL / time.Second),
	})
}

// clearSession removes the cookie (logout, or a session pointing at an account
// that no longer exists).
func (s *Server) clearSession(w http.ResponseWriter) {
	http.SetCookie(w, &http.Cookie{
		Name:     sessionCookieName,
		Value:    "",
		Path:     "/",
		HttpOnly: true,
		Secure:   s.cfg.SecureCookies,
		SameSite: http.SameSiteLaxMode,
		MaxAge:   -1,
	})
}

// readSession extracts and verifies the cookie, reporting false when it is
// absent, malformed, forged or expired — the caller treats all four the same.
func (s *Server) readSession(r *http.Request) (session, bool) {
	cookie, err := r.Cookie(sessionCookieName)
	if err != nil {
		return session{}, false
	}
	sess, err := s.codec.decode(cookie.Value)
	if err != nil {
		return session{}, false
	}
	return sess, true
}
