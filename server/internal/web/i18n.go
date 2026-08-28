package web

import (
	"embed"
	"encoding/json"
	"fmt"
	"net/http"
	"sort"
	"strings"
)

// The portal speaks the same four languages as the game client, because those
// are the ones its own login dialog offers (logonDialog.xml: es, en, fr, de).
// Adding a fifth means adding a locale file and nothing else.
const (
	LangEN = "en"
	LangFR = "fr"
	LangES = "es"
	LangDE = "de"
)

// Languages is the offered set, in the order the switcher shows them.
var Languages = []string{LangEN, LangFR, LangES, LangDE}

// LanguageNames are endonyms - a language picker that names languages in the
// language the reader does not yet speak is not much use.
var LanguageNames = map[string]string{
	LangEN: "English",
	LangFR: "Fran\u00e7ais",
	LangES: "Espa\u00f1ol",
	LangDE: "Deutsch",
}

// langCookieName remembers an explicit choice. It is not signed: the worst a
// forged value can do is show the reader a different language.
const langCookieName = "arena_lang"

//go:embed locales/*.json
var localesFS embed.FS

// catalog is lang -> key -> text.
type catalog map[string]map[string]string

// loadCatalog reads every embedded locale. English is the reference: a key
// missing from another language falls back to it rather than showing the reader
// a raw key, so a half-finished translation degrades instead of breaking.
func loadCatalog() (catalog, error) {
	c := make(catalog, len(Languages))
	for _, lang := range Languages {
		raw, err := localesFS.ReadFile("locales/" + lang + ".json")
		if err != nil {
			return nil, fmt.Errorf("web: read locale %s: %w", lang, err)
		}
		var m map[string]string
		if err := json.Unmarshal(raw, &m); err != nil {
			return nil, fmt.Errorf("web: parse locale %s: %w", lang, err)
		}
		c[lang] = m
	}
	if len(c[LangEN]) == 0 {
		return nil, fmt.Errorf("web: the English locale is empty; it is the fallback for every other language")
	}
	return c, nil
}

// t translates key into lang, falling back to English and finally to the key
// itself. Returning the key is deliberate: a missing string shows up as an
// obvious "nav.home" in the page rather than as blank space nobody notices.
func (c catalog) t(lang, key string) string {
	if m, ok := c[lang]; ok {
		if s, ok := m[key]; ok && s != "" {
			return s
		}
	}
	if s, ok := c[LangEN][key]; ok && s != "" {
		return s
	}
	return key
}

// keys lists every key defined for a language, sorted.
func (c catalog) keys(lang string) []string {
	out := make([]string, 0, len(c[lang]))
	for k := range c[lang] {
		out = append(out, k)
	}
	sort.Strings(out)
	return out
}

// normaliseLang maps anything user- or browser-supplied onto a language we
// actually have, or "" when there is no match.
func normaliseLang(s string) string {
	s = strings.ToLower(strings.TrimSpace(s))
	if i := strings.IndexAny(s, "-_"); i > 0 { // fr-FR, de_AT
		s = s[:i]
	}
	for _, l := range Languages {
		if s == l {
			return l
		}
	}
	return ""
}

// resolveLang decides which language to render a request in:
//
//  1. an explicit ?lang= choice (which the handler also stores in a cookie),
//  2. that cookie, from a previous explicit choice,
//  3. the browser's Accept-Language,
//  4. the configured default, and finally English.
//
// The browser is consulted before the configured default so a French visitor to
// an English-defaulted server still lands on French, while an operator can still
// pick what a browser with no preference gets.
func (s *Server) resolveLang(r *http.Request) string {
	if r == nil {
		return s.defaultLang()
	}
	if l := normaliseLang(r.URL.Query().Get("lang")); l != "" {
		return l
	}
	if c, err := r.Cookie(langCookieName); err == nil {
		if l := normaliseLang(c.Value); l != "" {
			return l
		}
	}
	if l := acceptLanguage(r.Header.Get("Accept-Language")); l != "" {
		return l
	}
	return s.defaultLang()
}

func (s *Server) defaultLang() string {
	if l := normaliseLang(s.cfg.DefaultLanguage); l != "" {
		return l
	}
	return LangEN
}

// acceptLanguage picks the best supported language from an Accept-Language
// header, honouring q-weights. "fr-CH;q=0.9, en;q=0.8" -> fr.
func acceptLanguage(header string) string {
	if header == "" {
		return ""
	}
	best, bestQ := "", -1.0
	for _, part := range strings.Split(header, ",") {
		part = strings.TrimSpace(part)
		if part == "" {
			continue
		}
		tag, q := part, 1.0
		if i := strings.Index(part, ";"); i >= 0 {
			tag = strings.TrimSpace(part[:i])
			for _, p := range strings.Split(part[i+1:], ";") {
				p = strings.TrimSpace(p)
				if strings.HasPrefix(p, "q=") {
					if v, err := parseQ(p[2:]); err == nil {
						q = v
					}
				}
			}
		}
		if l := normaliseLang(tag); l != "" && q > bestQ {
			best, bestQ = l, q
		}
	}
	return best
}

func parseQ(s string) (float64, error) {
	var f float64
	_, err := fmt.Sscanf(strings.TrimSpace(s), "%g", &f)
	return f, err
}

// tr translates for the language a request resolves to. Handlers use it for
// flash messages and form errors, which are produced in Go rather than in a
// template.
func (s *Server) tr(r *http.Request, key string) string {
	return s.cat.t(s.resolveLang(r), key)
}

// handleSetLanguage records an explicit choice and returns the visitor to where
// they were. The cookie is what makes the choice outlast the current page.
func (s *Server) handleSetLanguage(w http.ResponseWriter, r *http.Request) {
	lang := normaliseLang(r.URL.Query().Get("lang"))
	if lang == "" {
		redirect(w, r, "/")
		return
	}
	http.SetCookie(w, &http.Cookie{
		Name:     langCookieName,
		Value:    lang,
		Path:     "/",
		HttpOnly: true,
		Secure:   s.cfg.SecureCookies,
		SameSite: http.SameSiteLaxMode,
		MaxAge:   int((365 * 24 * 60 * 60)),
	})

	// Only ever return to a path on this site: "next" comes from the query
	// string, so an absolute URL there would make this an open redirector.
	next := r.URL.Query().Get("next")
	if !strings.HasPrefix(next, "/") || strings.HasPrefix(next, "//") {
		next = "/"
	}
	redirect(w, r, next)
}
