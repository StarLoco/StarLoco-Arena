package web

import (
	"bytes"
	"crypto/sha256"
	"embed"
	"encoding/hex"
	"fmt"
	"html/template"
	"io/fs"
	"net/http"
	"strings"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/version"
)

//go:embed templates/*.html
var templatesFS embed.FS

//go:embed static/app.css static/favicon.svg static/fonts/*.woff2
var staticFS embed.FS

// templateFuncs are the helpers available inside every template. They are
// presentation-only on purpose: anything that decides something belongs in a
// handler where it can be tested.
// templateFuncs are the language-independent helpers. The ones that produce
// WORDS live in buildFuncs, which binds them to a language.
var templateFuncs = template.FuncMap{
	"add": func(a, b int) int { return a + b },
	"sub": func(a, b int) int { return a - b },

	// formatUptime renders a second count compactly: "2d 4h", "13m 6s", "42s".
	"formatUptime": func(seconds int64) string {
		if seconds < 0 {
			seconds = 0
		}
		d := time.Duration(seconds) * time.Second
		days := int(d.Hours()) / 24
		hours := int(d.Hours()) % 24
		mins := int(d.Minutes()) % 60
		secs := int(d.Seconds()) % 60
		switch {
		case days > 0:
			return fmt.Sprintf("%dd %dh", days, hours)
		case hours > 0:
			return fmt.Sprintf("%dh %dm", hours, mins)
		case mins > 0:
			return fmt.Sprintf("%dm %ds", mins, secs)
		default:
			return fmt.Sprintf("%ds", secs)
		}
	},

	"formatDate": func(t time.Time) string {
		if t.IsZero() {
			return "—"
		}
		return t.Format("2 Jan 2006")
	},

	// thousands groups a number so the landing page's stat band reads the way
	// a marketing stat should ("4,500"). It takes any integer type because the
	// counts it renders come from both int and int64 sources and a template
	// cannot convert between them.
	"thousands": func(v any) string {
		var n int64
		switch t := v.(type) {
		case int:
			n = int64(t)
		case int32:
			n = int64(t)
		case int64:
			n = t
		default:
			return fmt.Sprintf("%v", v)
		}
		s := fmt.Sprintf("%d", n)
		if n < 0 {
			return s
		}
		var out []byte
		for i, c := range []byte(s) {
			if i > 0 && (len(s)-i)%3 == 0 {
				out = append(out, ',')
			}
			out = append(out, c)
		}
		return string(out)
	},
}

// buildFuncs returns the func map for ONE language: the shared helpers above,
// plus `t` and the label helpers that emit words.
//
// Binding the language into the funcs (rather than threading it through every
// template model) is what lets `{{t "key"}}` work inside shared partials too -
// a partial is rendered with a sub-model that knows nothing about the request,
// so it could not reach a language carried on the page model.
func buildFuncs(cat catalog, lang string) template.FuncMap {
	funcs := make(template.FuncMap, len(templateFuncs)+6)
	for k, v := range templateFuncs {
		funcs[k] = v
	}
	tr := func(key string) string { return cat.t(lang, key) }
	funcs["t"] = tr

	// slotLabel names a card position: 0 is the bag, anything else is an
	// equipped slot (the wire numbers them from 0, the column from 1).
	funcs["slotLabel"] = func(pos int16) string {
		if pos == 0 {
			return tr("label.bag")
		}
		return fmt.Sprintf("%s %d", tr("label.slot"), pos)
	}
	funcs["sexLabel"] = func(sex uint8) string {
		if sex == 1 {
			return tr("label.female")
		}
		return tr("label.male")
	}
	// fighterStateLabel names the roster bucket a fighter sits in.
	funcs["fighterStateLabel"] = func(state uint8) string {
		switch state {
		case domain.FighterStateTitular:
			return tr("label.titular")
		case domain.FighterStateBench:
			return tr("label.bench")
		case domain.FighterStateDead:
			return tr("label.dead")
		case domain.FighterStateGraveyard:
			return tr("label.graveyard")
		case domain.FighterStateLegendary:
			return tr("label.legendary")
		case domain.FighterStateLegBench:
			return tr("label.legbench")
		}
		return fmt.Sprintf("%s %d", tr("label.state"), state)
	}
	// rankLabel turns a ladder rating into the level the client shows, or a
	// dash when the coach has never been ranked.
	funcs["rankLabel"] = func(strength int32) string {
		lvl := domain.StrengthToLevel(strength)
		if lvl == 0 {
			return "\u2014"
		}
		return fmt.Sprintf("%s %d", tr("label.level"), lvl)
	}
	return funcs
}

// templateSet holds one fully-parsed template per page.
//
// Each page gets its own namespace — layout + partials + that one page —
// because every page defines a block called "content". Parsing them all into a
// single tree would let the last one parsed silently overwrite everyone else's.
type templateSet struct {
	pages map[string]*template.Template
}

func (ts *templateSet) execute(w interface{ Write([]byte) (int, error) }, name string, data any) error {
	t, ok := ts.pages[name]
	if !ok {
		return fmt.Errorf("web: unknown template %q", name)
	}
	return t.ExecuteTemplate(w, "layout", data)
}

// parseTemplates builds the per-page set. Files named layout.html or starting
// with "_" are shared fragments and are parsed into every page rather than
// being renderable themselves.
// parseAllTemplates builds one fully-parsed set PER LANGUAGE.
//
// Templates are parsed once at startup and shared across requests, so a
// request-scoped `t` func is not possible in a single set. Four sets of a
// handful of small templates costs nothing and makes translation invisible to
// every handler and every partial.
func parseAllTemplates(cat catalog) (map[string]*templateSet, error) {
	out := make(map[string]*templateSet, len(Languages))
	for _, lang := range Languages {
		set, err := parseTemplates(buildFuncs(cat, lang))
		if err != nil {
			return nil, fmt.Errorf("web: templates for %s: %w", lang, err)
		}
		out[lang] = set
	}
	return out, nil
}

func parseTemplates(funcs template.FuncMap) (*templateSet, error) {
	layoutSrc, err := templatesFS.ReadFile("templates/layout.html")
	if err != nil {
		return nil, fmt.Errorf("web: read layout: %w", err)
	}
	partialsSrc, err := templatesFS.ReadFile("templates/_partials.html")
	if err != nil {
		return nil, fmt.Errorf("web: read partials: %w", err)
	}

	pages, err := fs.Glob(templatesFS, "templates/*.html")
	if err != nil {
		return nil, fmt.Errorf("web: glob templates: %w", err)
	}

	set := &templateSet{pages: make(map[string]*template.Template)}
	for _, page := range pages {
		base := strings.TrimPrefix(page, "templates/")
		if base == "layout.html" || strings.HasPrefix(base, "_") {
			continue
		}
		src, err := templatesFS.ReadFile(page)
		if err != nil {
			return nil, fmt.Errorf("web: read %s: %w", page, err)
		}
		t := template.New(base).Funcs(funcs)
		for _, chunk := range []struct {
			what string
			src  []byte
		}{{"layout", layoutSrc}, {"partials", partialsSrc}, {base, src}} {
			if _, err := t.Parse(string(chunk.src)); err != nil {
				return nil, fmt.Errorf("web: parse %s for %s: %w", chunk.what, base, err)
			}
		}
		set.pages[base] = t
	}
	return set, nil
}

// assetVersion is a short content hash of every embedded static file, used to
// fingerprint the stylesheet's URL.
//
// Without it the portal was unfixable in practice: /static/app.css is served
// with a long max-age, so a browser that had already loaded a page kept using
// the old stylesheet for a day while getting fresh HTML — which is how a
// corrected CSS rule still rendered the bug it had just fixed. Changing the
// content changes the URL, so the browser fetches it immediately, and an
// unchanged one stays cached.
var assetVersion = computeAssetVersion()

func computeAssetVersion() string {
	sum := sha256.New()
	// fs.WalkDir yields entries in lexical order, so the hash is stable across
	// builds and machines.
	err := fs.WalkDir(staticFS, ".", func(path string, d fs.DirEntry, err error) error {
		if err != nil || d.IsDir() {
			return err
		}
		b, err := staticFS.ReadFile(path)
		if err != nil {
			return err
		}
		sum.Write([]byte(path))
		sum.Write(b)
		return nil
	})
	if err != nil {
		// The FS is embedded at compile time; a failure here is a bug. Fall
		// back to something that at least still changes per build.
		return version.Short()
	}
	return hex.EncodeToString(sum.Sum(nil))[:12]
}

// staticFileServer serves the embedded static directory.
func staticFileServer() http.Handler {
	sub, err := fs.Sub(staticFS, "static")
	if err != nil {
		// The FS is embedded at compile time; a failure here is a bug, not a
		// runtime condition.
		panic(fmt.Sprintf("web: static sub-fs: %v", err))
	}
	return http.FileServer(http.FS(sub))
}

// ---------------------------------------------------------------------------
// View models
// ---------------------------------------------------------------------------

// baseData is embedded in every page's model and carries everything the layout
// needs: branding, the signed-in identity, the impersonation banner, and the
// one-shot flash notice.
type baseData struct {
	Title    string
	NavKey   string // which nav item to mark current
	Flash    *flash
	CSRF     string
	Year     int
	Version  string
	Discord  string
	GameAddr string
	// AssetVersion fingerprints the stylesheet URL so a corrected stylesheet
	// actually reaches a browser that already cached the old one.
	AssetVersion string

	ServerName          string
	RegistrationEnabled bool
	ClientDownloadURL   string
	MinLogin            int
	MinPassword         int

	PlayersOnline int
	ActiveFights  int

	// LoggedIn and Account describe the EFFECTIVE identity: while an admin is
	// impersonating, this is the account being viewed.
	LoggedIn bool
	Account  *domain.Account

	// IsAdmin reflects the REAL signed-in account, never the impersonated one,
	// so viewing-as can never elevate.
	IsAdmin       bool
	Impersonating bool
	RealName      string

	// OpenBugs badges the admin nav with the unresolved bug-report count. It is
	// only looked up for admins, so a public page never pays for the query.
	OpenBugs int64

	// Lang is the language this page is being rendered in, and picks which
	// parsed template set render() uses. Languages/LanguageNames/CurrentPath
	// drive the switcher in the layout - CurrentPath is what lets it return the
	// reader to the page they were on rather than to the home page.
	Lang          string
	Languages     []string
	LanguageNames map[string]string
	CurrentPath   string
}

func (b *baseData) base() *baseData { return b }

// pageModel is implemented by every page model via the embedded baseData, so
// render can fill in the shared fields without each handler repeating them.
type pageModel interface{ base() *baseData }

// newBase builds the shared model for a request.
func (s *Server) newBase(w http.ResponseWriter, r *http.Request, title, navKey string) *baseData {
	b := &baseData{
		Title:               title,
		NavKey:              navKey,
		Flash:               consumeFlash(w, r),
		Year:                time.Now().Year(),
		Version:             version.Short(),
		AssetVersion:        assetVersion,
		GameAddr:            s.gameAddress(r),
		ServerName:          s.serverName(),
		RegistrationEnabled: s.cfg.RegistrationEnabled,
		ClientDownloadURL:   s.cfg.ClientDownloadURL,
		MinLogin:            s.cfg.MinLoginLength,
		MinPassword:         s.cfg.MinPasswordLength,
		PlayersOnline:       s.playersOnline(),
		ActiveFights:        s.activeFights(),
		Lang:                s.resolveLang(r),
		Languages:           Languages,
		LanguageNames:       LanguageNames,
		CurrentPath:         currentPath(r),
	}

	sess, ok := s.readSession(r)
	if !ok {
		return b
	}
	acc, err := s.store.Accounts.FindByID(sess.effectiveID())
	if err != nil {
		return b
	}
	b.LoggedIn = true
	b.Account = acc
	b.CSRF = s.csrfToken(sess.AccountID)

	if sess.impersonating() {
		b.Impersonating = true
		if real, err := s.store.Accounts.FindByID(sess.AccountID); err == nil {
			b.IsAdmin = real.IsAdmin
			b.RealName = real.Name
		}
	} else {
		b.IsAdmin = acc.IsAdmin
	}

	if b.IsAdmin && s.cfg.BugReportsEnabled {
		if n, err := s.store.BugReports.CountOpen(); err == nil {
			b.OpenBugs = n
		}
	}
	return b
}

// render writes a page. It renders into a buffer first so that a template
// error produces a clean 500 instead of a half-written page with a broken
// layout — by the time Execute fails, anything already written is unrecallable.
// currentPath is the path (plus query) the switcher should return to. Only the
// path is kept - never a caller-supplied absolute URL - so it cannot become an
// open redirect.
func currentPath(r *http.Request) string {
	if r == nil || r.URL == nil {
		return "/"
	}
	p := r.URL.EscapedPath()
	if p == "" {
		p = "/"
	}
	// Drop any existing ?lang= so switching twice does not stack them.
	q := r.URL.Query()
	q.Del("lang")
	if e := q.Encode(); e != "" {
		return p + "?" + e
	}
	return p
}

func (s *Server) render(w http.ResponseWriter, status int, name string, data pageModel) {
	set, ok := s.tmpl[data.base().Lang]
	if !ok {
		set = s.tmpl[LangEN]
	}
	var buf bytes.Buffer
	if err := set.execute(&buf, name, data); err != nil {
		s.log.Error("web: render failed", "template", name, "err", err)
		http.Error(w, "The page could not be rendered.", http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	w.WriteHeader(status)
	if _, err := buf.WriteTo(w); err != nil {
		s.log.Debug("web: write failed", "err", err)
	}
}

// errorData is the model for the generic error page.
type errorData struct {
	*baseData
	Code    int
	Message string
}

// renderError shows a styled error page rather than net/http's bare text.
func (s *Server) renderError(w http.ResponseWriter, r *http.Request, code int, message string) {
	s.render(w, code, "error.html", &errorData{
		baseData: s.newBase(w, r, http.StatusText(code), ""),
		Code:     code,
		Message:  message,
	})
}
