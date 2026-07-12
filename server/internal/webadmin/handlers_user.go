package webadmin

import (
	"errors"
	"net/http"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/service"
)

// accountViewData is the "all my data" view model: the account plus its full
// coach graph, enriched with a few derived flags/labels for the template.
type accountViewData struct {
	Detail       *service.AccountDetail
	CoachOnline  bool
	CardsSummary cardsSummary
	// ReadOnly signals the template to hide self-service controls (used when
	// an admin views this via impersonation -- still read-only for safety).
	ReadOnly bool
}

// cardsSummary splits an inventory into equipped vs. bagged and counts them,
// for the overview tiles.
type cardsSummary struct {
	Total      int
	Equipped   []domain.CoachCard
	Unequipped []domain.CoachCard
}

func summarizeCards(cards []domain.CoachCard) cardsSummary {
	s := cardsSummary{Total: len(cards)}
	for _, c := range cards {
		if c.Pos != 0 {
			s.Equipped = append(s.Equipped, c)
		} else {
			s.Unequipped = append(s.Unequipped, c)
		}
	}
	return s
}

// handleAccount renders the logged-in (or impersonated) user's full data
// dashboard.
func (h *Handler) handleAccount(w http.ResponseWriter, r *http.Request, s session) {
	ctx, cancel := reqCtx(r)
	defer cancel()

	detail, err := h.deps.Accounts.GetAccountDetail(ctx, s.effectiveID())
	if err != nil {
		if errors.Is(err, service.ErrAccountNotFound) {
			h.clearSession(w)
			redirect(w, r, "/login")
			return
		}
		h.deps.Logger.Error().Err(err).Msg("webadmin: load account detail")
		h.render(w, r, http.StatusInternalServerError, "error.html", "Error", "account", errorData{
			Code: 500, Message: "Couldn't load your data. Please try again.",
		})
		return
	}

	vd := accountViewData{
		Detail:       detail,
		CardsSummary: summarizeCards(detail.Cards),
		// Impersonated sessions are always read-only in this build.
		ReadOnly: s.impersonating(),
	}
	if detail.Coach != nil && h.deps.World != nil {
		vd.CoachOnline = h.deps.World.IsOnline(detail.Coach.ID)
	}

	h.render(w, r, http.StatusOK, "account.html", "My Account", "account", vd)
}

// passwordFormData carries an inline error back to the change-password form.
type passwordFormData struct {
	Error    string
	ReadOnly bool
}

func (h *Handler) handlePasswordForm(w http.ResponseWriter, r *http.Request, s session) {
	// Changing password while impersonating would change the *target's*
	// password, which is not a safe/expected admin action here -- block it.
	if s.impersonating() {
		h.render(w, r, http.StatusForbidden, "error.html", "Forbidden", "account", errorData{
			Code: 403, Message: "Stop impersonating before changing a password.",
		})
		return
	}
	h.render(w, r, http.StatusOK, "password.html", "Change password", "account", passwordFormData{})
}

func (h *Handler) handlePasswordSubmit(w http.ResponseWriter, r *http.Request, s session) {
	if s.impersonating() {
		h.render(w, r, http.StatusForbidden, "error.html", "Forbidden", "account", errorData{
			Code: 403, Message: "Stop impersonating before changing a password.",
		})
		return
	}
	if !h.verifyCSRF(r, s.AccountID) {
		h.render(w, r, http.StatusForbidden, "error.html", "Forbidden", "account", errorData{
			Code: 403, Message: "Invalid request token. Please try again.",
		})
		return
	}

	current := r.FormValue("current_password")
	next := r.FormValue("new_password")
	confirm := r.FormValue("new_password_confirm")

	fail := func(status int, msg string) {
		h.render(w, r, status, "password.html", "Change password", "account", passwordFormData{Error: msg})
	}

	ctx, cancel := reqCtx(r)
	defer cancel()

	acc, err := h.deps.Accounts.GetByID(ctx, s.AccountID)
	if err != nil {
		h.clearSession(w)
		redirect(w, r, "/login")
		return
	}
	if _, err := h.deps.Accounts.VerifyPassword(ctx, acc.Name, current); err != nil {
		fail(http.StatusUnauthorized, "Your current password is incorrect.")
		return
	}
	if next != confirm {
		fail(http.StatusBadRequest, "New passwords don't match.")
		return
	}
	if err := h.deps.Accounts.ChangePassword(ctx, acc.ID, next); err != nil {
		if errors.Is(err, service.ErrPasswordTooShort) {
			fail(http.StatusBadRequest, "New password must be at least 6 characters.")
			return
		}
		h.deps.Logger.Error().Err(err).Msg("webadmin: change password")
		fail(http.StatusInternalServerError, "Something went wrong. Please try again.")
		return
	}

	setFlash(w, "success", "Your password has been updated.")
	redirect(w, r, "/account")
}
