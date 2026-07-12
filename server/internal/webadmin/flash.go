package webadmin

import (
	"encoding/base64"
	"net/http"
	"strings"
)

// flashCookieName holds a one-shot notice across a POST->redirect->GET
// cycle. It's not signed (it carries no security decision, only a UI
// message) but it is short-lived and cleared on read.
const flashCookieName = "arena_flash"

// setFlash stores a one-shot notice to be shown on the next rendered page.
func setFlash(w http.ResponseWriter, kind, message string) {
	value := kind + "|" + base64.RawURLEncoding.EncodeToString([]byte(message))
	http.SetCookie(w, &http.Cookie{
		Name:     flashCookieName,
		Value:    value,
		Path:     "/",
		HttpOnly: true,
		SameSite: http.SameSiteLaxMode,
		MaxAge:   60,
	})
}

// consumeFlash reads and immediately clears any pending flash notice.
func consumeFlash(w http.ResponseWriter, r *http.Request) *flash {
	cookie, err := r.Cookie(flashCookieName)
	if err != nil || cookie.Value == "" {
		return nil
	}
	// Clear it regardless of parse outcome.
	http.SetCookie(w, &http.Cookie{
		Name:     flashCookieName,
		Value:    "",
		Path:     "/",
		HttpOnly: true,
		SameSite: http.SameSiteLaxMode,
		MaxAge:   -1,
	})

	kind, encoded, ok := strings.Cut(cookie.Value, "|")
	if !ok {
		return nil
	}
	msgBytes, err := base64.RawURLEncoding.DecodeString(encoded)
	if err != nil {
		return nil
	}
	switch kind {
	case "success", "error", "info":
	default:
		kind = "info"
	}
	return &flash{Kind: kind, Message: string(msgBytes)}
}
