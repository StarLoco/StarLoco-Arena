package webadmin

import (
	"net/http"
	"time"
)

// statusPageData powers the public /status page: a minimal, unauthenticated
// snapshot of basic server health, deliberately limited to numbers that
// carry no player-identifying information (no names, no account details --
// just counts), unlike the admin dashboard.
type statusPageData struct {
	OnlinePlayers int
	ActiveFights  int
	UptimeSeconds int64
}

// handleStatus serves a small public status page (no login required)
// showing the online-player count, active-fight count, and server uptime.
// This intentionally overlaps with internal/adminhttp's /stats and
// /healthz JSON endpoints but serves a different audience: adminhttp is a
// loopback-only ops tool (curl/pprof), while this is a human-friendly page
// on the public-facing portal for players/visitors to check "is the
// server up, and is anyone playing".
func (h *Handler) handleStatus(w http.ResponseWriter, r *http.Request) {
	online := 0
	if h.deps.World != nil {
		online = h.deps.World.Len()
	}
	fights := 0
	if h.deps.Fights != nil {
		fights = h.deps.Fights.Count()
	}

	h.render(w, r, http.StatusOK, "status.html", "Server status", "", statusPageData{
		OnlinePlayers: online,
		ActiveFights:  fights,
		UptimeSeconds: int64(time.Since(h.startedAt).Seconds()),
	})
}
