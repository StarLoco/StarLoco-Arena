package web

import (
	"crypto/hmac"
	"crypto/sha256"
	"crypto/subtle"
	"encoding/base64"
	"net/http"
	"time"
)

// CSRF protection is a signed double-submit token: an HMAC over the account id
// plus a day bucket, embedded as a hidden field in every state-changing form
// and re-derived and compared when the form comes back.
//
// Keying it with the same secret that signs session cookies means an attacker
// cannot mint one, and binding it to the account id means a token harvested
// from one account is useless against another. The day bucket rotates tokens
// without any server-side storage to expire.
//
// GET requests never change state, so only POST handlers call verifyCSRF.

// csrfToken derives the token an account's forms should carry.
func (s *Server) csrfToken(accountID uint) string {
	return s.csrfTokenForBucket(accountID, time.Now().UTC().Unix()/86400)
}

func (s *Server) csrfTokenForBucket(accountID uint, bucket int64) string {
	mac := hmac.New(sha256.New, s.codec.key)
	mac.Write([]byte("csrf"))
	// Domain-separate the two numbers so that, e.g., account 1 on bucket 2
	// cannot produce the same input as account 2 on bucket 1.
	mac.Write([]byte{byte(accountID), byte(accountID >> 8), byte(accountID >> 16), byte(accountID >> 24)})
	mac.Write([]byte{byte(bucket), byte(bucket >> 8), byte(bucket >> 16), byte(bucket >> 24)})
	return base64.RawURLEncoding.EncodeToString(mac.Sum(nil))
}

// verifyCSRF checks a submitted token against the current or previous day
// bucket, so a form opened just before midnight still submits successfully.
func (s *Server) verifyCSRF(r *http.Request, accountID uint) bool {
	submitted := r.FormValue("csrf_token")
	if submitted == "" {
		return false
	}
	now := time.Now().UTC().Unix() / 86400
	for _, bucket := range []int64{now, now - 1} {
		expected := s.csrfTokenForBucket(accountID, bucket)
		if subtle.ConstantTimeCompare([]byte(submitted), []byte(expected)) == 1 {
			return true
		}
	}
	return false
}
