package web

import (
	"errors"
	"net/http"
	"strings"
	"time"

	"github.com/StarLoco/arena-2.70/internal/store"
)

// authData is the model for the login and register pages.
type authData struct {
	*baseData
	Error     string
	FormLogin string
	// FirstAccount marks a server with no accounts yet, where whoever
	// registers next becomes the owner.
	FirstAccount bool
}

func (s *Server) newAuthData(w http.ResponseWriter, r *http.Request, title, nav string) *authData {
	d := &authData{baseData: s.newBase(w, r, title, nav)}
	if n, err := s.store.Accounts.Count(); err == nil {
		d.FirstAccount = n == 0
	}
	return d
}

// ---------------------------------------------------------------------------
// Login
// ---------------------------------------------------------------------------

func (s *Server) handleLoginForm(w http.ResponseWriter, r *http.Request) {
	if _, ok := s.readSession(r); ok {
		redirect(w, r, "/account")
		return
	}
	s.render(w, http.StatusOK, "login.html", s.newAuthData(w, r, "Sign in", "login"))
}

func (s *Server) handleLoginSubmit(w http.ResponseWriter, r *http.Request) {
	data := s.newAuthData(w, r, "Sign in", "login")

	if !sameOrigin(r) {
		data.Error = "That request did not come from this page. Please try again."
		s.render(w, http.StatusForbidden, "login.html", data)
		return
	}
	if err := r.ParseForm(); err != nil {
		data.Error = "The form could not be read. Please try again."
		s.render(w, http.StatusBadRequest, "login.html", data)
		return
	}

	login := strings.TrimSpace(r.PostFormValue("login"))
	password := r.PostFormValue("password")
	data.FormLogin = login

	// Rate-limit sign-in attempts per address. This is the brute-force guard;
	// it is deliberately more generous than registration because a household
	// behind one address will legitimately mistype passwords.
	if !s.loginLimiter.allow(s.clientIP(r)) {
		data.Error = "Too many sign-in attempts from your address. Please wait a few minutes."
		s.render(w, http.StatusTooManyRequests, "login.html", data)
		return
	}

	acc, err := s.store.Accounts.FindByName(login)
	if err != nil || !s.store.Accounts.VerifyPassword(acc, password) {
		if err != nil && !errors.Is(err, store.ErrNotFound) {
			s.log.Error("web: account lookup failed", "err", err)
		}
		// One message for both "no such account" and "wrong password", so the
		// form cannot be used to find out which account names exist.
		data.Error = "Wrong account name or password."
		s.render(w, http.StatusUnauthorized, "login.html", data)
		return
	}

	s.writeSession(w, session{AccountID: acc.ID, IssuedAt: time.Now()})
	s.log.Info("web: signed in", "login", acc.Name)
	redirect(w, r, "/account")
}

func (s *Server) handleLogout(w http.ResponseWriter, r *http.Request) {
	// Logout changes state, so it is a POST and needs the same origin check as
	// any other form; without it a third-party page could sign visitors out.
	if !sameOrigin(r) {
		s.renderError(w, r, http.StatusForbidden, "That request did not come from this site.")
		return
	}
	s.clearSession(w)
	setFlash(w, "info", "You have been signed out.")
	redirect(w, r, "/")
}

// ---------------------------------------------------------------------------
// Registration
// ---------------------------------------------------------------------------

func (s *Server) handleRegisterForm(w http.ResponseWriter, r *http.Request) {
	if _, ok := s.readSession(r); ok {
		redirect(w, r, "/account")
		return
	}
	data := s.newAuthData(w, r, "Create an account", "register")
	if !s.cfg.RegistrationEnabled {
		data.Error = "Registration is closed on this server."
		s.render(w, http.StatusForbidden, "register.html", data)
		return
	}
	s.render(w, http.StatusOK, "register.html", data)
}

func (s *Server) handleRegisterSubmit(w http.ResponseWriter, r *http.Request) {
	data := s.newAuthData(w, r, "Create an account", "register")

	if !s.cfg.RegistrationEnabled {
		data.Error = "Registration is closed on this server."
		s.render(w, http.StatusForbidden, "register.html", data)
		return
	}
	// Reject cross-site form posts. The portal's own form sends either no
	// Origin or its own; anything else is a third-party page driving the
	// visitor's browser.
	if !sameOrigin(r) {
		data.Error = "That request did not come from this page. Please try again."
		s.render(w, http.StatusForbidden, "register.html", data)
		return
	}
	if err := r.ParseForm(); err != nil {
		data.Error = "The form could not be read. Please try again."
		s.render(w, http.StatusBadRequest, "register.html", data)
		return
	}

	login := strings.TrimSpace(r.PostFormValue("login"))
	password := r.PostFormValue("password")
	data.FormLogin = login

	if err := s.validate(login, password); err != nil {
		data.Error = err.Error()
		s.render(w, http.StatusBadRequest, "register.html", data)
		return
	}
	if confirm := r.PostFormValue("confirm"); confirm != "" && confirm != password {
		data.Error = "The two passwords do not match."
		s.render(w, http.StatusBadRequest, "register.html", data)
		return
	}
	// Rate-limit only once the input is well-formed, so a player fumbling the
	// form does not burn their allowance.
	if !s.limiter.allow(s.clientIP(r)) {
		data.Error = "Too many accounts created from your address recently. Please try again later."
		s.render(w, http.StatusTooManyRequests, "register.html", data)
		return
	}

	if _, err := s.store.Accounts.FindByName(login); err == nil {
		data.Error = "That account name is already taken."
		s.render(w, http.StatusConflict, "register.html", data)
		return
	} else if !errors.Is(err, store.ErrNotFound) {
		s.log.Error("web: account lookup failed", "err", err)
		data.Error = "The server could not reach its database. Please try again."
		s.render(w, http.StatusInternalServerError, "register.html", data)
		return
	}

	// A brand new server has no owner yet, so the first account registered
	// becomes the administrator — otherwise nobody could ever run a GM command
	// or open the console on a machine where only the release binary is
	// installed. Every later account is an ordinary player.
	first := false
	if n, err := s.store.Accounts.Count(); err != nil {
		s.log.Error("web: account count failed", "err", err)
	} else {
		first = n == 0
	}

	acc, err := s.store.Accounts.CreateAccount(login, password, first)
	if err != nil {
		// The unique index is the real guard; a duplicate here means someone
		// took the name between the check above and now.
		if isDuplicate(err) {
			data.Error = "That account name is already taken."
			s.render(w, http.StatusConflict, "register.html", data)
			return
		}
		s.log.Error("web: account creation failed", "err", err)
		data.Error = "The account could not be created. Please try again."
		s.render(w, http.StatusInternalServerError, "register.html", data)
		return
	}

	s.log.Info("account registered via web portal", "login", login, "admin", first)

	// Sign them straight in: making somebody type the password they just chose
	// a second time achieves nothing.
	s.writeSession(w, session{AccountID: acc.ID, IssuedAt: time.Now()})
	if first {
		setFlash(w, "success", "Welcome! Yours is the first account on this server, so it is the administrator.")
	} else {
		setFlash(w, "success", "Welcome! Your account is ready — sign in with it from the game client.")
	}
	redirect(w, r, "/account")
}
