package webadmin

import (
	"embed"
	"fmt"
	"html/template"
	"io/fs"
	"net/http"
	"strings"
	"time"
)

//go:embed templates/*.html
var templatesFS embed.FS

//go:embed static/*
var staticFS embed.FS

// templateFuncs are the helpers available inside every template. They're
// intentionally small and presentation-only.
var templateFuncs = template.FuncMap{
	// cardFlagLabel renders the CoachCard flag byte as human-readable chips.
	"cardFlagLabel": func(flag uint8) string {
		var parts []string
		if flag&1 != 0 {
			parts = append(parts, "Locked")
		}
		if flag&2 != 0 {
			parts = append(parts, "Cursed")
		}
		if len(parts) == 0 {
			return "None"
		}
		return strings.Join(parts, ", ")
	},
	// sexLabel maps the sex byte to a label (0 = male, 1 = female per the
	// legacy client convention).
	"sexLabel": func(sex uint8) string {
		if sex == 1 {
			return "Female"
		}
		return "Male"
	},
	// slotLabel labels a card position: 0 = bag, otherwise the equip slot.
	"slotLabel": func(pos int16) string {
		if pos == 0 {
			return "Bag"
		}
		return fmt.Sprintf("Slot %d", pos)
	},
	"add": func(a, b int) int { return a + b },
	"join": func(sep string, items []int32) string {
		if len(items) == 0 {
			return "—"
		}
		strs := make([]string, len(items))
		for i, v := range items {
			strs[i] = fmt.Sprintf("%d", v)
		}
		return strings.Join(strs, ", ")
	},
	"len32": func(items []int32) int { return len(items) },
	// formatUptime renders a second count as a compact human duration,
	// e.g. "2d 4h", "13m", "42s". Used by the admin dashboard's uptime
	// tile.
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
}

// templateSet holds one fully-parsed *template.Template per page. Each page
// lives in its OWN template namespace (layout + partials + that page only),
// so the {{define "content"}} block every page declares can't collide with
// another page's -- a single shared tree would let the last-parsed page's
// "content" overwrite all the others.
type templateSet struct {
	pages map[string]*template.Template
}

// execute renders the named page (e.g. "account.html") with data, writing to
// w. The layout is the entry template executed by name.
func (ts *templateSet) execute(w interface{ Write([]byte) (int, error) }, name string, data any) error {
	t, ok := ts.pages[name]
	if !ok {
		return fmt.Errorf("webadmin: unknown template %q", name)
	}
	return t.ExecuteTemplate(w, "layout", data)
}

// parseTemplates parses the embedded template set into a per-page
// templateSet. Each page template {{define}}s a "content" block and is
// parsed together with layout.html (which references it) plus the shared
// partials, in an isolated namespace per page.
func parseTemplates() (*templateSet, error) {
	// Load the shared layout + partials sources once. Both are parsed into
	// every page template so page-level {{template "content"}} (layout) and
	// {{template "coach_data"}} (partials) resolve regardless of which page
	// is being rendered.
	layoutSrc, err := templatesFS.ReadFile("templates/layout.html")
	if err != nil {
		return nil, fmt.Errorf("webadmin: read layout template: %w", err)
	}
	partialsSrc, err := templatesFS.ReadFile("templates/_partials.html")
	if err != nil {
		return nil, fmt.Errorf("webadmin: read partials template: %w", err)
	}

	pages, err := fs.Glob(templatesFS, "templates/*.html")
	if err != nil {
		return nil, fmt.Errorf("webadmin: glob templates: %w", err)
	}

	set := &templateSet{pages: make(map[string]*template.Template)}
	for _, page := range pages {
		base := page[len("templates/"):]
		// layout.html and files prefixed "_" are shared fragments, not
		// standalone pages -- they get parsed *into* each page below rather
		// than rendered by name.
		if base == "layout.html" || strings.HasPrefix(base, "_") {
			continue
		}
		src, err := templatesFS.ReadFile(page)
		if err != nil {
			return nil, fmt.Errorf("webadmin: read template %s: %w", page, err)
		}
		// Each page gets its OWN root template so its "content" definition is
		// isolated from every other page's. We parse layout (defines
		// "layout"), the shared partials, and finally the page (defines
		// "content"). execute() renders the "layout" entry template.
		t := template.New(base).Funcs(templateFuncs)
		if _, err := t.Parse(string(layoutSrc)); err != nil {
			return nil, fmt.Errorf("webadmin: parse layout for %s: %w", base, err)
		}
		if _, err := t.Parse(string(partialsSrc)); err != nil {
			return nil, fmt.Errorf("webadmin: parse partials for %s: %w", base, err)
		}
		if _, err := t.Parse(string(src)); err != nil {
			return nil, fmt.Errorf("webadmin: parse template %s: %w", base, err)
		}
		set.pages[base] = t
	}
	return set, nil
}

// staticFileServer serves the embedded static/ directory (CSS, favicon).
func staticFileServer() http.Handler {
	sub, err := fs.Sub(staticFS, "static")
	if err != nil {
		// Embedded FS is compile-time guaranteed; a failure here is a bug.
		panic(fmt.Sprintf("webadmin: static sub-fs: %v", err))
	}
	return http.FileServer(http.FS(sub))
}
