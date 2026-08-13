package web

import (
	"net/http"
	"net/http/pprof"
	"runtime"
)

// pprofProfiles are the runtime profiles exposed to an admin, in the order the
// monitoring page lists them.
//
// They are served IN-PROCESS rather than reverse-proxied to a separate
// debug listener, because the portal runs inside the game server: the profiles
// it dumps are the real ones. That also means there is no second, unauthorised
// port to leave open by accident — this admin-gated route is the only way in.
var pprofProfiles = []struct {
	Name string
	Desc string
}{
	{"goroutine", "Every running goroutine, with its stack. Where to look first when the server stops responding."},
	{"heap", "Live objects on the heap. Where to look when memory keeps climbing."},
	{"allocs", "Every allocation since start, including freed ones."},
	{"threadcreate", "OS threads the runtime has created."},
	{"block", "Goroutines blocked on locks or channels. Off unless enabled at build time."},
	{"mutex", "Lock contention. Off unless enabled at build time."},
}

type monitoringData struct {
	*baseData
	AdminTab string

	UptimeSeconds int64
	Goroutines    int
	NumGC         uint32
	HeapAllocMB   float64
	HeapSysMB     float64
	NumCPU        int
	GoVersion     string

	TotalAccounts int64
	Connected     int64

	Profiles []struct {
		Name string
		Desc string
	}
}

func (s *Server) handleAdminMonitoring(w http.ResponseWriter, r *http.Request, sess session) {
	var m runtime.MemStats
	runtime.ReadMemStats(&m)

	d := &monitoringData{
		baseData:      s.newBase(w, r, "Monitoring", "admin"),
		AdminTab:      "monitoring",
		UptimeSeconds: s.uptimeSeconds(),
		Goroutines:    runtime.NumGoroutine(),
		NumGC:         m.NumGC,
		HeapAllocMB:   float64(m.HeapAlloc) / (1 << 20),
		HeapSysMB:     float64(m.HeapSys) / (1 << 20),
		NumCPU:        runtime.NumCPU(),
		GoVersion:     runtime.Version(),
		Profiles:      pprofProfiles,
	}
	if n, err := s.store.Accounts.Count(); err == nil {
		d.TotalAccounts = n
	}
	if n, err := s.store.Accounts.CountConnected(); err == nil {
		d.Connected = n
	}
	s.render(w, http.StatusOK, "admin_monitoring.html", d)
}

// handleAdminPprof serves one runtime profile.
//
// Only the named profiles above are reachable: the request never reaches
// net/http/pprof's own mux, so there is no path to /debug/pprof/cmdline (which
// leaks the command line) or to the CPU profile's `seconds` parameter, which
// would let a caller pin a request open for as long as they liked and is a
// trivial way to exhaust the server's connection budget.
func (s *Server) handleAdminPprof(w http.ResponseWriter, r *http.Request, sess session) {
	name := r.PathValue("profile")

	allowed := false
	for _, p := range pprofProfiles {
		if p.Name == name {
			allowed = true
			break
		}
	}
	if !allowed {
		s.renderError(w, r, http.StatusNotFound, "No such profile.")
		return
	}

	s.log.Info("web: profile dumped", "profile", name, "by", sess.AccountID)

	// Serve as plain text so it renders in the browser. debug=1 gives the
	// human-readable form; a real investigation would take the raw form
	// through `go tool pprof`, which is what the page explains.
	q := r.URL.Query()
	if q.Get("debug") == "" {
		q.Set("debug", "1")
		r.URL.RawQuery = q.Encode()
	}
	pprof.Handler(name).ServeHTTP(w, r)
}
