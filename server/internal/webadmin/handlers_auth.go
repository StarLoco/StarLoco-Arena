package webadmin

import (
	"errors"
	"net/http"
	"strings"
	"time"

	"github.com/dofusarena/go-server/internal/service"
)

// handleIndex is the landing page. Logged-in users are sent straight to
// their account overview; visitors see the marketing/hero landing with
// login/register calls to action.
func (h *Handler) handleIndex(w http.ResponseWriter, r *http.Request) {
	// Only "/" exactly -- the catch-all pattern would otherwise swallow
	// unknown paths; return a proper 404 for those.
	if r.URL.Path != "/" {
		h.render(w, r, http.StatusNotFound, "error.html", "Not Found", "", errorData{
			Code:    404,
			Message: "The page you're looking for doesn't exist.",
		})
		return
	}
	if _, ok := h.readSession(r); ok {
		redirect(w, r, "/account")
		return
	}
	h.render(w, r, http.StatusOK, "index.html", "Welcome", "", nil)
}

// loginFormData carries pre-filled values + an error message back to the
// login form on a failed attempt.
type loginFormData struct {
	Name  string
	Error string
}

func (h *Handler) handleLoginForm(w http.ResponseWriter, r *http.Request) {
	if _, ok := h.readSession(r); ok {
		redirect(w, r, "/account")
		return
	}
	h.render(w, r, http.StatusOK, "login.html", "Sign in", "", loginFormData{})
}

func (h *Handler) handleLoginSubmit(w http.ResponseWriter, r *http.Request) {
	name := strings.TrimSpace(r.FormValue("name"))
	password := r.FormValue("password")

	ctx, cancel := reqCtx(r)
	defer cancel()

	acc, err := h.deps.Accounts.VerifyPassword(ctx, name, password)
	if err != nil {
		// Uniform message for both unknown-account and wrong-password so we
		// don't leak which accounts exist.
		h.render(w, r, http.StatusUnauthorized, "login.html", "Sign in", "", loginFormData{
			Name:  name,
			Error: "Invalid account name or password.",
		})
		return
	}

	h.writeSession(w, session{AccountID: acc.ID, IssuedAt: time.Now()})
	setFlash(w, "success", "Welcome back, "+acc.Name+"!")
	redirect(w, r, "/account")
}

// registerFormData carries pre-filled values + a field error back on a
// failed registration.
type registerFormData struct {
	Name  string
	Error string
}

func (h *Handler) handleRegisterForm(w http.ResponseWriter, r *http.Request) {
	if _, ok := h.readSession(r); ok {
		redirect(w, r, "/account")
		return
	}
	h.render(w, r, http.StatusOK, "register.html", "Create account", "", registerFormData{})
}

func (h *Handler) handleRegisterSubmit(w http.ResponseWriter, r *http.Request) {
	name := strings.TrimSpace(r.FormValue("name"))
	password := r.FormValue("password")
	confirm := r.FormValue("password_confirm")

	fail := func(status int, msg string) {
		h.render(w, r, status, "register.html", "Create account", "", registerFormData{
			Name:  name,
			Error: msg,
		})
	}

	if password != confirm {
		fail(http.StatusBadRequest, "Passwords don't match.")
		return
	}

	ctx, cancel := reqCtx(r)
	defer cancel()

	// Public registration never grants admin.
	acc, err := h.deps.Accounts.Register(ctx, name, password, false)
	if err != nil {
		switch {
		case errors.Is(err, service.ErrAccountNameTaken):
			fail(http.StatusConflict, "That account name is already taken.")
		case errors.Is(err, service.ErrAccountNameInvalid):
			fail(http.StatusBadRequest, "Account names must be 3-64 characters: letters, digits, and . _ - only.")
		case errors.Is(err, service.ErrPasswordTooShort):
			fail(http.StatusBadRequest, "Password must be at least 6 characters.")
		default:
			h.deps.Logger.Error().Err(err).Msg("webadmin: register failed")
			fail(http.StatusInternalServerError, "Something went wrong. Please try again.")
		}
		return
	}

	h.writeSession(w, session{AccountID: acc.ID, IssuedAt: time.Now()})
	setFlash(w, "success", "Account created. Welcome to "+h.serverName+", "+acc.Name+"!")
	redirect(w, r, "/account")
}

// handleLogout clears the session cookie (and any impersonation) and returns
// to the landing page.
func (h *Handler) handleLogout(w http.ResponseWriter, r *http.Request) {
	if s, ok := h.readSession(r); ok {
		if !h.verifyCSRF(r, s.AccountID) {
			h.render(w, r, http.StatusForbidden, "error.html", "Forbidden", "", errorData{
				Code:    403,
				Message: "Invalid request token. Please try again.",
			})
			return
		}
	}
	h.clearSession(w)
	redirect(w, r, "/")
}
