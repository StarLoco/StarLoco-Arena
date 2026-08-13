package web

import (
	"encoding/base64"
	"net/http"
	"strings"
)

// flashCookieName carries a one-shot notice across the POST → redirect → GET
// cycle, so "Password changed" survives the redirect that stops a refresh from
// re-submitting the form.
//
// It is not signed. Forging one only lets an attacker show a victim a message
// on their own screen, which they could do with a link anyway; no decision is
// ever made from its contents.
const flashCookieName = "arena_flash"

// flash is a rendered notice. Kind is one of success, error, info.
type flash struct {
	Kind    string
	Message string
}

// setFlash queues a notice for the next page this browser renders.
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

// consumeFlash reads and immediately clears any pending notice, so it shows
// exactly once even if the page is refreshed.
func consumeFlash(w http.ResponseWriter, r *http.Request) *flash {
	cookie, err := r.Cookie(flashCookieName)
	if err != nil || cookie.Value == "" {
		return nil
	}
	// Clear it whatever happens next: a flash that fails to parse must not
	// stick around and fail to parse on every subsequent page too.
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
	msg, err := base64.RawURLEncoding.DecodeString(encoded)
	if err != nil {
		return nil
	}
	switch kind {
	case "success", "error", "info":
	default:
		kind = "info"
	}
	return &flash{Kind: kind, Message: string(msg)}
}
