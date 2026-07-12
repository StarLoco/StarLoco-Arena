package webadmin

import "net/http"

// authedHandler is a handler that receives the verified session so it
// doesn't have to re-read/re-validate the cookie.
type authedHandler func(w http.ResponseWriter, r *http.Request, s session)

// requireUser wraps a handler so only requests with a valid session (any
// account) reach it; others are redirected to the login page. The effective
// account is re-loaded from the DB and its continued existence verified, so
// a deleted-but-still-cookied user is logged out cleanly.
func (h *Handler) requireUser(next authedHandler) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		s, ok := h.readSession(r)
		if !ok {
			redirect(w, r, "/login")
			return
		}
		ctx, cancel := reqCtx(r)
		defer cancel()
		if _, err := h.deps.Accounts.GetByID(ctx, s.effectiveID()); err != nil {
			// Referenced account vanished (e.g. admin deleted it mid-
			// impersonation) -- drop the session.
			h.clearSession(w)
			redirect(w, r, "/login")
			return
		}
		next(w, r, s)
	}
}

// requireAdmin wraps a handler so only the *real* logged-in account being an
// admin grants access -- impersonation never elevates a non-admin, and an
// admin who is impersonating still counts as admin for console routes
// (though the console UI encourages stopping impersonation first). A
// non-admin is shown 403.
func (h *Handler) requireAdmin(next authedHandler) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		s, ok := h.readSession(r)
		if !ok {
			redirect(w, r, "/login")
			return
		}
		ctx, cancel := reqCtx(r)
		defer cancel()
		admin, err := h.deps.Accounts.GetByID(ctx, s.AccountID)
		if err != nil {
			h.clearSession(w)
			redirect(w, r, "/login")
			return
		}
		if !admin.IsAdmin {
			h.render(w, r, http.StatusForbidden, "error.html", "Forbidden", "", errorData{
				Code:    403,
				Message: "You don't have permission to access the admin console.",
			})
			return
		}
		next(w, r, s)
	}
}

// errorData is the view model for the generic error page.
type errorData struct {
	Code    int
	Message string
}
