package webadmin

import "net/http"

// routes registers every URL the portal serves on a private mux. The portal
// deliberately uses Go 1.22+ method+path patterns so GET and POST for the
// same URL map to distinct handlers without in-handler method switches.
func (h *Handler) routes() {
	mux := http.NewServeMux()

	// Static assets (CSS, favicon) embedded in the binary.
	mux.Handle("GET /static/", http.StripPrefix("/static/", staticFileServer()))

	// Public pages.
	mux.HandleFunc("GET /", h.handleIndex)
	mux.HandleFunc("GET /status", h.handleStatus)
	mux.HandleFunc("GET /login", h.handleLoginForm)
	mux.HandleFunc("POST /login", h.handleLoginSubmit)
	mux.HandleFunc("GET /register", h.handleRegisterForm)
	mux.HandleFunc("POST /register", h.handleRegisterSubmit)
	mux.HandleFunc("POST /logout", h.handleLogout)

	// Authenticated user pages (any logged-in account).
	mux.HandleFunc("GET /account", h.requireUser(h.handleAccount))
	mux.HandleFunc("GET /account/password", h.requireUser(h.handlePasswordForm))
	mux.HandleFunc("POST /account/password", h.requireUser(h.handlePasswordSubmit))

	// Admin console (admin accounts only). Impersonation start/stop is
	// admin-gated on start; stop is allowed for anyone currently
	// impersonating so they can always get back to themselves.
	mux.HandleFunc("GET /admin", h.requireAdmin(h.handleAdminDashboard))
	mux.HandleFunc("GET /admin/monitoring", h.requireAdmin(h.handleAdminMonitoring))
	mux.HandleFunc("GET /admin/monitoring/pprof/", h.requireAdmin(h.handleAdminPprofProxy))
	mux.HandleFunc("GET /admin/accounts", h.requireAdmin(h.handleAdminAccounts))
	mux.HandleFunc("GET /admin/accounts/new", h.requireAdmin(h.handleAdminCreateForm))
	mux.HandleFunc("POST /admin/accounts/new", h.requireAdmin(h.handleAdminCreateSubmit))
	mux.HandleFunc("GET /admin/accounts/{id}", h.requireAdmin(h.handleAdminAccountDetail))
	mux.HandleFunc("POST /admin/accounts/{id}/delete", h.requireAdmin(h.handleAdminDelete))
	mux.HandleFunc("POST /admin/accounts/{id}/toggle-admin", h.requireAdmin(h.handleAdminToggleAdmin))
	mux.HandleFunc("POST /admin/accounts/{id}/impersonate", h.requireAdmin(h.handleImpersonateStart))
	mux.HandleFunc("POST /impersonate/stop", h.handleImpersonateStop)

	h.mux = mux
}
