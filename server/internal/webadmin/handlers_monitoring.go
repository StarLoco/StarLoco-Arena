package webadmin

import (
	"context"
	"encoding/json"
	"net/http"
	"net/http/httputil"
	"net/url"
	"strings"
	"time"
)

// monitoringData powers the /admin/monitoring page: live ops counters
// fetched from internal/adminhttp (when configured) plus a pprof profile
// index for one-click access to Go's built-in profiler through the portal.
type monitoringData struct {
	Configured    bool
	AdminHTTPAddr string
	Healthy       bool
	UptimeSeconds int64
	OnlinePlayers int
	ActiveFights  int
	FetchError    string
	Profiles      []pprofProfile
}

// pprofProfile is one link in the monitoring page's profiler grid.
type pprofProfile struct {
	Name string
	Href string
}

// knownPprofProfiles lists every profile net/http/pprof exposes: the four
// special handlers adminhttp registers individually (cmdline, profile,
// symbol, trace) plus every profile runtime/pprof.Profile always registers
// (allocs, block, goroutine, heap, mutex, threadcreate), all reachable
// through pprof.Index's generic dispatch. Listing them explicitly here
// lets the monitoring page link straight to each rather than scraping
// adminhttp's plaintext index page.
var knownPprofProfiles = []string{
	"allocs", "block", "cmdline", "goroutine", "heap", "mutex", "profile", "threadcreate", "trace",
}

// handleAdminMonitoring renders live health/stats counters (fetched from
// internal/adminhttp over HTTP, since that server may live in a different
// process than this portal -- e.g. the standalone cmd/web binary) plus a
// grid of links into Go's built-in profiler, proxied through
// handleAdminPprofProxy so an admin never needs direct network access to
// the loopback-bound ops server.
func (h *Handler) handleAdminMonitoring(w http.ResponseWriter, r *http.Request, _ session) {
	data := monitoringData{
		AdminHTTPAddr: h.adminHTTPAddr,
		Configured:    h.adminHTTPAddr != "",
	}

	if data.Configured {
		ctx, cancel := context.WithTimeout(r.Context(), 3*time.Second)
		defer cancel()

		health, err := h.fetchAdminJSON(ctx, "/healthz")
		if err != nil {
			data.FetchError = "Couldn't reach the admin HTTP server at " + h.adminHTTPAddr + ": " + err.Error()
		} else {
			data.Healthy = health["status"] == "ok"
			if up, ok := health["uptime_seconds"].(float64); ok {
				data.UptimeSeconds = int64(up)
			}
			if stats, err := h.fetchAdminJSON(ctx, "/stats"); err == nil {
				if v, ok := stats["online_players"].(float64); ok {
					data.OnlinePlayers = int(v)
				}
				if v, ok := stats["active_fights"].(float64); ok {
					data.ActiveFights = int(v)
				}
			}
		}

		for _, name := range knownPprofProfiles {
			data.Profiles = append(data.Profiles, pprofProfile{
				Name: name,
				Href: "/admin/monitoring/pprof/" + name,
			})
		}
	}

	h.render(w, r, http.StatusOK, "admin_monitoring.html", "Monitoring", "admin", data)
}

// fetchAdminJSON makes a small GET request against the adminhttp server and
// decodes a JSON object response (used for /healthz and /stats).
func (h *Handler) fetchAdminJSON(ctx context.Context, path string) (map[string]any, error) {
	req, err := http.NewRequestWithContext(ctx, http.MethodGet, "http://"+h.adminHTTPAddr+path, nil)
	if err != nil {
		return nil, err
	}
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		return nil, err
	}
	defer resp.Body.Close()

	var out map[string]any
	if err := json.NewDecoder(resp.Body).Decode(&out); err != nil {
		return nil, err
	}
	return out, nil
}

// handleAdminPprofProxy reverse-proxies GET /admin/monitoring/pprof/* to
// internal/adminhttp's /debug/pprof/* on h.adminHTTPAddr, so an
// authenticated admin can reach Go's built-in profiler (index, cmdline,
// profile, symbol, trace, and per-profile pages like heap/goroutine)
// through the public-facing portal without needing direct network access
// to the loopback-bound admin HTTP server. Gated by requireAdmin like every
// other /admin/* route -- adminhttp itself has no auth of its own, since
// it's meant to be unreachable except from localhost, so this proxy is the
// only *authenticated* path to it.
func (h *Handler) handleAdminPprofProxy(w http.ResponseWriter, r *http.Request, _ session) {
	if h.adminHTTPAddr == "" {
		h.notFound(w, r, "admin")
		return
	}

	target, err := url.Parse("http://" + h.adminHTTPAddr)
	if err != nil {
		h.deps.Logger.Error().Err(err).Msg("webadmin: parse admin http addr")
		h.render(w, r, http.StatusInternalServerError, "error.html", "Error", "admin", errorData{
			Code: 500, Message: "Monitoring proxy is misconfigured.",
		})
		return
	}

	proxy := httputil.NewSingleHostReverseProxy(target)
	baseDirector := proxy.Director
	proxy.Director = func(req *http.Request) {
		baseDirector(req)
		// Rewrite /admin/monitoring/pprof/xyz -> /debug/pprof/xyz (and the
		// bare /admin/monitoring/pprof/ -> /debug/pprof/, adminhttp's index
		// page), preserving everything after the prefix -- including query
		// strings like ?seconds=30, handled separately by Go's URL parsing.
		suffix := strings.TrimPrefix(req.URL.Path, "/admin/monitoring/pprof")
		req.URL.Path = "/debug/pprof" + suffix
		req.Host = target.Host
	}
	proxy.ErrorHandler = func(w http.ResponseWriter, r *http.Request, err error) {
		h.deps.Logger.Warn().Err(err).Str("addr", h.adminHTTPAddr).Msg("webadmin: pprof proxy error")
		h.render(w, r, http.StatusBadGateway, "error.html", "Error", "admin", errorData{
			Code: 502, Message: "Couldn't reach the admin HTTP server at " + h.adminHTTPAddr + ".",
		})
	}
	proxy.ServeHTTP(w, r)
}
