package webadmin

import (
	"crypto/hmac"
	"crypto/sha256"
	"crypto/subtle"
	"encoding/base64"
	"net/http"
	"time"
)

// CSRF protection uses a signed, per-session double-submit token. The token
// is an HMAC over the account ID (+ a day bucket so it rotates), embedded in
// every state-changing form as a hidden field and re-derived + compared on
// POST. Because the HMAC key is the same server secret used for session
// cookies, an attacker can't forge a valid token without it, and because the
// token is bound to the account ID it can't be replayed across users.
//
// GET requests never mutate state, so they're exempt; only POST handlers
// call verifyCSRF.

// csrfToken derives the token for a given account. The day bucket bounds a
// token's lifetime to ~48h (current or previous day accepted, see verify),
// which is plenty for form lifetimes without needing server-side storage.
func (h *Handler) csrfToken(accountID uint) string {
	return h.csrfTokenForBucket(accountID, time.Now().UTC().Unix()/86400)
}

func (h *Handler) csrfTokenForBucket(accountID uint, bucket int64) string {
	mac := hmac.New(sha256.New, h.codec.key)
	mac.Write([]byte("csrf"))
	mac.Write([]byte{byte(accountID), byte(accountID >> 8), byte(accountID >> 16), byte(accountID >> 24)})
	mac.Write([]byte{byte(bucket), byte(bucket >> 8), byte(bucket >> 16), byte(bucket >> 24)})
	return base64.RawURLEncoding.EncodeToString(mac.Sum(nil))
}

// verifyCSRF checks the submitted token against the current (or previous)
// day bucket for the request's real account. Returns true on success.
func (h *Handler) verifyCSRF(r *http.Request, accountID uint) bool {
	submitted := r.FormValue("csrf_token")
	if submitted == "" {
		return false
	}
	now := time.Now().UTC().Unix() / 86400
	for _, bucket := range []int64{now, now - 1} {
		expected := h.csrfTokenForBucket(accountID, bucket)
		if subtle.ConstantTimeCompare([]byte(submitted), []byte(expected)) == 1 {
			return true
		}
	}
	return false
}
