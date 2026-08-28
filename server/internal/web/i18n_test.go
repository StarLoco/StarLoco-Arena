package web

import (
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/config"
)

// TestEveryLocaleIsComplete is the invariant that keeps translations honest:
// English is the reference, and every other language must define exactly the
// same keys. Without this a page silently falls back to English mid-sentence,
// which reads like a bug and is very hard to spot by eye across four languages.
func TestEveryLocaleIsComplete(t *testing.T) {
	cat, err := loadCatalog()
	if err != nil {
		t.Fatalf("loadCatalog: %v", err)
	}
	ref := cat[LangEN]
	if len(ref) == 0 {
		t.Fatal("the English locale is empty")
	}

	for _, lang := range Languages {
		if lang == LangEN {
			continue
		}
		var missing, extra []string
		for k := range ref {
			if v, ok := cat[lang][k]; !ok || strings.TrimSpace(v) == "" {
				missing = append(missing, k)
			}
		}
		for k := range cat[lang] {
			if _, ok := ref[k]; !ok {
				extra = append(extra, k)
			}
		}
		if len(missing) > 0 {
			t.Errorf("%s is missing %d key(s) present in English: %v", lang, len(missing), truncateList(missing))
		}
		if len(extra) > 0 {
			t.Errorf("%s defines %d key(s) English does not: %v", lang, len(extra), truncateList(extra))
		}
	}
}

// Every key a template asks for must exist, or the reader sees a raw key like
// "nav.home" in the page.
func TestTemplatesOnlyUseDefinedKeys(t *testing.T) {
	cat, err := loadCatalog()
	if err != nil {
		t.Fatalf("loadCatalog: %v", err)
	}
	files, err := templatesFS.ReadDir("templates")
	if err != nil {
		t.Fatalf("read templates: %v", err)
	}
	for _, f := range files {
		src, err := templatesFS.ReadFile("templates/" + f.Name())
		if err != nil {
			t.Fatalf("read %s: %v", f.Name(), err)
		}
		for _, key := range templateKeys(string(src)) {
			if _, ok := cat[LangEN][key]; !ok {
				t.Errorf("%s uses {{t %q}} but no such key is defined", f.Name(), key)
			}
		}
	}
}

// templateKeys pulls the key out of every `{{t "key"}}` in a template.
func templateKeys(src string) []string {
	var out []string
	const open = `{{t "`
	for i := 0; ; {
		j := strings.Index(src[i:], open)
		if j < 0 {
			return out
		}
		start := i + j + len(open)
		end := strings.Index(src[start:], `"`)
		if end < 0 {
			return out
		}
		out = append(out, src[start:start+end])
		i = start + end
	}
}

func truncateList(s []string) []string {
	if len(s) > 8 {
		return append(s[:8:8], "...")
	}
	return s
}

func TestLanguageResolution(t *testing.T) {
	s, _ := newTestServer(t, func(c *config.WebConfig) { c.DefaultLanguage = "en" })

	cases := []struct {
		name   string
		build  func(*http.Request)
		expect string
	}{
		{"default when nothing is expressed", func(r *http.Request) {}, LangEN},
		{"explicit query wins", func(r *http.Request) { r.URL.RawQuery = "lang=de" }, LangDE},
		{"cookie is remembered", func(r *http.Request) {
			r.AddCookie(&http.Cookie{Name: langCookieName, Value: "es"})
		}, LangES},
		{"query beats cookie", func(r *http.Request) {
			r.URL.RawQuery = "lang=fr"
			r.AddCookie(&http.Cookie{Name: langCookieName, Value: "de"})
		}, LangFR},
		{"browser preference is honoured", func(r *http.Request) {
			r.Header.Set("Accept-Language", "fr-FR,fr;q=0.9,en;q=0.8")
		}, LangFR},
		{"q-weights are respected", func(r *http.Request) {
			r.Header.Set("Accept-Language", "en;q=0.3, de;q=0.9")
		}, LangDE},
		{"unsupported languages fall through", func(r *http.Request) {
			r.Header.Set("Accept-Language", "ja,ko;q=0.9")
		}, LangEN},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			r := httptest.NewRequest(http.MethodGet, "/", nil)
			tc.build(r)
			if got := s.resolveLang(r); got != tc.expect {
				t.Errorf("resolveLang = %q, want %q", got, tc.expect)
			}
		})
	}
}

// The pages must actually come out in the chosen language, not merely resolve it.
func TestPagesRenderInTheChosenLanguage(t *testing.T) {
	s, _ := newTestServer(t, nil)
	cat, _ := loadCatalog()

	for _, lang := range Languages {
		req := httptest.NewRequest(http.MethodGet, "/?lang="+lang, nil)
		rec := httptest.NewRecorder()
		s.Handler().ServeHTTP(rec, req)
		if rec.Code != http.StatusOK {
			t.Fatalf("%s: status = %d", lang, rec.Code)
		}
		want := cat.t(lang, "index.stat.players")
		if !strings.Contains(rec.Body.String(), want) {
			t.Errorf("%s: landing page does not contain %q", lang, want)
		}
	}
}

// Switching stores the choice, and must not be usable as an open redirector:
// "next" is attacker-supplied.
func TestLanguageSwitchIsSafeAndSticky(t *testing.T) {
	s, _ := newTestServer(t, nil)

	rec := get(t, s, "/lang?lang=de&next=%2Fladder")
	if rec.Code != http.StatusSeeOther {
		t.Fatalf("status = %d, want 303", rec.Code)
	}
	if loc := rec.Header().Get("Location"); loc != "/ladder" {
		t.Errorf("Location = %q, want /ladder", loc)
	}
	if !strings.Contains(rec.Header().Get("Set-Cookie"), langCookieName+"=de") {
		t.Errorf("choice was not stored: %q", rec.Header().Get("Set-Cookie"))
	}

	for _, evil := range []string{"https://evil.example", "//evil.example", "http://evil.example/x"} {
		rec := get(t, s, "/lang?lang=fr&next="+evil)
		if loc := rec.Header().Get("Location"); loc != "/" {
			t.Errorf("next=%q redirected to %q; must refuse anything off-site", evil, loc)
		}
	}
}
