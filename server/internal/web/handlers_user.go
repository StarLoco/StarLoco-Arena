package web

import (
	"net/http"
)

// accountData is the model for a player's own dashboard.
type accountData struct {
	*baseData
	Detail *accountDetail
}

func (s *Server) handleAccount(w http.ResponseWriter, r *http.Request, sess session) {
	detail, err := s.loadDetail(sess.effectiveID())
	if err != nil {
		s.log.Error("web: load account detail failed", "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "Your account could not be loaded.")
		return
	}
	s.render(w, http.StatusOK, "account.html", &accountData{
		baseData: s.newBase(w, r, "My account", "account"),
		Detail:   detail,
	})
}

// ---------------------------------------------------------------------------
// Password change
// ---------------------------------------------------------------------------

type passwordData struct {
	*baseData
	Error string
}

func (s *Server) handlePasswordForm(w http.ResponseWriter, r *http.Request, sess session) {
	d := &passwordData{baseData: s.newBase(w, r, "Change password", "account")}
	if sess.impersonating() {
		d.Error = "You are viewing this account as an administrator. " +
			"Changing its password is blocked — stop impersonating first."
		s.render(w, http.StatusForbidden, "password.html", d)
		return
	}
	s.render(w, http.StatusOK, "password.html", d)
}

func (s *Server) handlePasswordSubmit(w http.ResponseWriter, r *http.Request, sess session) {
	d := &passwordData{baseData: s.newBase(w, r, "Change password", "account")}

	// Impersonation is strictly read-only. An admin who is viewing as somebody
	// must not be able to lock them out of their own account, even by accident.
	if sess.impersonating() {
		d.Error = "You are viewing this account as an administrator. " +
			"Changing its password is blocked — stop impersonating first."
		s.render(w, http.StatusForbidden, "password.html", d)
		return
	}
	if !s.requirePost(w, r, sess) {
		return
	}

	current := r.PostFormValue("current")
	next := r.PostFormValue("password")
	confirm := r.PostFormValue("confirm")

	acc, err := s.store.Accounts.FindByID(sess.AccountID)
	if err != nil {
		s.renderError(w, r, http.StatusInternalServerError, "Your account could not be loaded.")
		return
	}

	// Re-check the current password even though they are already signed in:
	// it is what stops somebody who walks up to an unlocked browser from
	// taking the account over.
	if !s.store.Accounts.VerifyPassword(acc, current) {
		d.Error = "Your current password is not correct."
		s.render(w, http.StatusUnauthorized, "password.html", d)
		return
	}
	if next != confirm {
		d.Error = "The two new passwords do not match."
		s.render(w, http.StatusBadRequest, "password.html", d)
		return
	}
	// Reuse the sign-up policy so the two can never disagree. The login half
	// of the check is satisfied by the existing name.
	if err := s.validate(acc.Name, next); err != nil {
		d.Error = err.Error()
		s.render(w, http.StatusBadRequest, "password.html", d)
		return
	}

	if err := s.store.Accounts.SetPassword(acc.ID, next); err != nil {
		s.log.Error("web: password change failed", "err", err)
		d.Error = "The password could not be changed. Please try again."
		s.render(w, http.StatusInternalServerError, "password.html", d)
		return
	}

	s.log.Info("web: password changed", "login", acc.Name)
	setFlash(w, "success", s.tr(r, "flash.passwordchanged"))
	redirect(w, r, "/account")
}
