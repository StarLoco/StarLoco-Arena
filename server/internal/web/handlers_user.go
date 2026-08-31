package web

import (
	"errors"
	"net/http"

	"github.com/StarLoco/arena-2.70/internal/store"
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

// ---------------------------------------------------------------------------
// Account deletion (GDPR right to erasure)
// ---------------------------------------------------------------------------

// The admin console could already delete an account, but only an operator
// could reach it. Article 17 gives the data subject the right, so it has to be
// something the player can do themselves, without asking anyone and without
// being talked out of it.
//
// It reuses store.AccountRepo.DeleteAccount, which removes the account, its
// coach and everything hanging off that coach in one transaction, so the
// player-facing path and the operator-facing path can never diverge in what
// they actually erase.

type deleteAccountData struct {
	*baseData
	Error string
	// Connected mirrors the account's in-world state. DeleteAccount refuses
	// while a session is live (the game process holds the coach in memory), so
	// the form is hidden rather than shown and then rejected.
	Connected bool
}

func (s *Server) newDeleteData(w http.ResponseWriter, r *http.Request, sess session) *deleteAccountData {
	d := &deleteAccountData{baseData: s.newBase(w, r, s.tr(r, "delete.title"), "account")}
	if acc, err := s.store.Accounts.FindByID(sess.effectiveID()); err == nil {
		d.Connected = acc.Connected
	}
	return d
}

func (s *Server) handleDeleteAccountForm(w http.ResponseWriter, r *http.Request, sess session) {
	s.render(w, http.StatusOK, "account_delete.html", s.newDeleteData(w, r, sess))
}

func (s *Server) handleDeleteAccountSubmit(w http.ResponseWriter, r *http.Request, sess session) {
	d := s.newDeleteData(w, r, sess)

	// Impersonation is read-only, exactly as it is for the password form: an
	// admin viewing as somebody must not be able to erase them.
	if sess.impersonating() {
		d.Error = s.tr(r, "delete.impersonating")
		s.render(w, http.StatusForbidden, "account_delete.html", d)
		return
	}
	if !s.requirePost(w, r, sess) {
		return
	}

	acc, err := s.store.Accounts.FindByID(sess.AccountID)
	if err != nil {
		s.renderError(w, r, http.StatusInternalServerError, "Your account could not be loaded.")
		return
	}

	// Two independent confirmations, because this is irreversible: the
	// password proves it is really them (not someone at an unlocked browser),
	// and typing the account name proves they read what the button does.
	if !s.store.Accounts.VerifyPassword(acc, r.PostFormValue("password")) {
		d.Error = s.tr(r, "err.currentpassword")
		s.render(w, http.StatusUnauthorized, "account_delete.html", d)
		return
	}
	if r.PostFormValue("confirm") != acc.Name {
		d.Error = s.tr(r, "delete.err.nomatch")
		s.render(w, http.StatusBadRequest, "account_delete.html", d)
		return
	}

	name := acc.Name
	switch err := s.store.Accounts.DeleteAccount(acc.ID); {
	case err == nil:
		s.log.Info("web: account self-deleted", "login", name)
		// Drop the session cookie before redirecting: it now points at a row
		// that no longer exists, and leaving it set would render every page
		// as a signed-in visitor whose account cannot be loaded.
		s.clearSession(w)
		setFlash(w, "success", s.tr(r, "delete.flash.done"))
		redirect(w, r, "/")
	case errors.Is(err, store.ErrAccountConnected):
		d.Connected = true
		d.Error = s.tr(r, "delete.connected")
		s.render(w, http.StatusConflict, "account_delete.html", d)
	default:
		s.log.Error("web: account self-delete failed", "err", err)
		d.Error = s.tr(r, "err.generic")
		s.render(w, http.StatusInternalServerError, "account_delete.html", d)
	}
}
