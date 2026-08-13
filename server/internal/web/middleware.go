package web

import (
	"net/http"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// authedHandler receives the verified session so it does not have to re-read
// and re-validate the cookie itself.
type authedHandler func(w http.ResponseWriter, r *http.Request, s session)

// requireUser admits any request carrying a valid session and bounces the rest
// to the login page.
//
// The effective account is re-loaded from the database and its continued
// existence confirmed on every request, so an account deleted (or deleted out
// from under an admin mid-impersonation) results in a clean sign-out rather
// than handlers working with a ghost.
func (s *Server) requireUser(next authedHandler) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		sess, ok := s.readSession(r)
		if !ok {
			redirect(w, r, "/login")
			return
		}
		if _, err := s.store.Accounts.FindByID(sess.effectiveID()); err != nil {
			s.clearSession(w)
			redirect(w, r, "/login")
			return
		}
		next(w, r, sess)
	}
}

// requireAdmin admits only requests whose REAL account is an admin.
//
// Gating on the real account rather than the effective one is the whole point:
// impersonation must never be able to elevate. An admin who is impersonating
// still reaches the console, which is what lets them stop.
func (s *Server) requireAdmin(next authedHandler) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		sess, ok := s.readSession(r)
		if !ok {
			redirect(w, r, "/login")
			return
		}
		real, err := s.store.Accounts.FindByID(sess.AccountID)
		if err != nil {
			s.clearSession(w)
			redirect(w, r, "/login")
			return
		}
		if !real.IsAdmin {
			s.renderError(w, r, http.StatusForbidden,
				"You do not have permission to open the admin console.")
			return
		}
		next(w, r, sess)
	}
}

// requirePost guards a state-changing handler with the two checks every POST
// needs: the request came from our own page, and it carries a valid CSRF token
// for the real signed-in account. It reports whether the handler may proceed
// and has already written the response when it returns false.
func (s *Server) requirePost(w http.ResponseWriter, r *http.Request, sess session) bool {
	if !sameOrigin(r) {
		s.renderError(w, r, http.StatusForbidden,
			"That request did not come from this site.")
		return false
	}
	if err := r.ParseForm(); err != nil {
		s.renderError(w, r, http.StatusBadRequest, "The form could not be read.")
		return false
	}
	// Bind the token to the REAL account: while impersonating, forms are still
	// signed by whoever is actually logged in.
	if !s.verifyCSRF(r, sess.AccountID) {
		s.renderError(w, r, http.StatusForbidden,
			"That form has expired. Please go back, reload the page and try again.")
		return false
	}
	return true
}

// effectiveAccount loads the account whose data a request should operate on:
// the impersonated one while viewing-as, otherwise the signed-in one.
func (s *Server) effectiveAccount(sess session) (*domain.Account, error) {
	return s.store.Accounts.FindByID(sess.effectiveID())
}
