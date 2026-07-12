package webadmin

import (
	"errors"
	"net/http"
	"strconv"
	"strings"
	"time"

	"github.com/dofusarena/go-server/internal/service"
)

// adminDashboardData powers the admin landing tiles.
type adminDashboardData struct {
	TotalAccounts int64
	Connected     int64
	OnlineCoaches int
	ActiveFights  int
	UptimeSeconds int64
	Recent        []service.AccountListItem
}

func (h *Handler) handleAdminDashboard(w http.ResponseWriter, r *http.Request, _ session) {
	ctx, cancel := reqCtx(r)
	defer cancel()

	total, _ := h.deps.Accounts.CountAccounts(ctx)
	connected, _ := h.deps.Accounts.CountConnected(ctx)
	recent, _, _ := h.deps.Accounts.ListAccounts(ctx, service.ListAccountsParams{Limit: 8})

	online := 0
	if h.deps.World != nil {
		online = h.deps.World.Len()
	}
	fights := 0
	if h.deps.Fights != nil {
		fights = h.deps.Fights.Count()
	}

	h.render(w, r, http.StatusOK, "admin_dashboard.html", "Admin", "admin", adminDashboardData{
		TotalAccounts: total,
		Connected:     connected,
		OnlineCoaches: online,
		ActiveFights:  fights,
		UptimeSeconds: int64(time.Since(h.startedAt).Seconds()),
		Recent:        recent,
	})
}

const adminPageSize = 25

// adminAccountsData powers the searchable/paginated account table.
type adminAccountsData struct {
	Items      []service.AccountListItem
	Search     string
	Page       int
	TotalPages int
	Total      int64
	HasPrev    bool
	HasNext    bool
	PrevQuery  string
	NextQuery  string
}

func (h *Handler) handleAdminAccounts(w http.ResponseWriter, r *http.Request, _ session) {
	ctx, cancel := reqCtx(r)
	defer cancel()

	search := strings.TrimSpace(r.URL.Query().Get("q"))
	page, _ := strconv.Atoi(r.URL.Query().Get("page"))
	if page < 1 {
		page = 1
	}

	items, total, err := h.deps.Accounts.ListAccounts(ctx, service.ListAccountsParams{
		Search: search,
		Limit:  adminPageSize,
		Offset: (page - 1) * adminPageSize,
	})
	if err != nil {
		h.deps.Logger.Error().Err(err).Msg("webadmin: list accounts")
		h.render(w, r, http.StatusInternalServerError, "error.html", "Error", "admin", errorData{
			Code: 500, Message: "Couldn't load accounts.",
		})
		return
	}

	totalPages := int((total + adminPageSize - 1) / adminPageSize)
	if totalPages < 1 {
		totalPages = 1
	}

	mkQuery := func(p int) string {
		q := r.URL.Query()
		q.Set("page", strconv.Itoa(p))
		return "?" + q.Encode()
	}

	h.render(w, r, http.StatusOK, "admin_accounts.html", "Accounts", "admin", adminAccountsData{
		Items:      items,
		Search:     search,
		Page:       page,
		TotalPages: totalPages,
		Total:      total,
		HasPrev:    page > 1,
		HasNext:    page < totalPages,
		PrevQuery:  mkQuery(page - 1),
		NextQuery:  mkQuery(page + 1),
	})
}

// adminCreateData carries pre-filled values + an error back to the create
// form.
type adminCreateData struct {
	Name    string
	IsAdmin bool
	Error   string
}

func (h *Handler) handleAdminCreateForm(w http.ResponseWriter, r *http.Request, _ session) {
	h.render(w, r, http.StatusOK, "admin_create.html", "New account", "admin", adminCreateData{})
}

func (h *Handler) handleAdminCreateSubmit(w http.ResponseWriter, r *http.Request, s session) {
	if !h.verifyCSRF(r, s.AccountID) {
		h.render(w, r, http.StatusForbidden, "error.html", "Forbidden", "admin", errorData{
			Code: 403, Message: "Invalid request token. Please try again.",
		})
		return
	}

	name := strings.TrimSpace(r.FormValue("name"))
	password := r.FormValue("password")
	isAdmin := r.FormValue("is_admin") == "on"

	fail := func(status int, msg string) {
		h.render(w, r, status, "admin_create.html", "New account", "admin", adminCreateData{
			Name: name, IsAdmin: isAdmin, Error: msg,
		})
	}

	ctx, cancel := reqCtx(r)
	defer cancel()

	acc, err := h.deps.Accounts.Register(ctx, name, password, isAdmin)
	if err != nil {
		switch {
		case errors.Is(err, service.ErrAccountNameTaken):
			fail(http.StatusConflict, "That account name is already taken.")
		case errors.Is(err, service.ErrAccountNameInvalid):
			fail(http.StatusBadRequest, "Account names must be 3-64 characters: letters, digits, and . _ - only.")
		case errors.Is(err, service.ErrPasswordTooShort):
			fail(http.StatusBadRequest, "Password must be at least 6 characters.")
		default:
			h.deps.Logger.Error().Err(err).Msg("webadmin: admin create account")
			fail(http.StatusInternalServerError, "Something went wrong. Please try again.")
		}
		return
	}

	setFlash(w, "success", "Created account "+acc.Name+".")
	redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(acc.ID), 10))
}

// adminDetailData is the admin deep-view of one account: full data plus
// live-status and self-reference flags for conditional controls.
type adminDetailData struct {
	View      accountViewData
	IsSelf    bool // target == the admin viewing it
	CanDelete bool
	CanToggle bool
}

func (h *Handler) handleAdminAccountDetail(w http.ResponseWriter, r *http.Request, s session) {
	id, ok := parseIDParam(r)
	if !ok {
		h.notFound(w, r, "admin")
		return
	}

	ctx, cancel := reqCtx(r)
	defer cancel()

	detail, err := h.deps.Accounts.GetAccountDetail(ctx, id)
	if err != nil {
		if errors.Is(err, service.ErrAccountNotFound) {
			h.notFound(w, r, "admin")
			return
		}
		h.deps.Logger.Error().Err(err).Msg("webadmin: admin account detail")
		h.render(w, r, http.StatusInternalServerError, "error.html", "Error", "admin", errorData{
			Code: 500, Message: "Couldn't load the account.",
		})
		return
	}

	vd := accountViewData{
		Detail:       detail,
		CardsSummary: summarizeCards(detail.Cards),
		ReadOnly:     true,
	}
	if detail.Coach != nil && h.deps.World != nil {
		vd.CoachOnline = h.deps.World.IsOnline(detail.Coach.ID)
	}

	isSelf := detail.Account.ID == s.AccountID
	h.render(w, r, http.StatusOK, "admin_detail.html", detail.Account.Name, "admin", adminDetailData{
		View:      vd,
		IsSelf:    isSelf,
		CanDelete: !isSelf && !detail.Account.Connected,
		CanToggle: !isSelf,
	})
}

func (h *Handler) handleAdminDelete(w http.ResponseWriter, r *http.Request, s session) {
	if !h.verifyCSRF(r, s.AccountID) {
		h.forbidden(w, r, "admin", "Invalid request token. Please try again.")
		return
	}
	id, ok := parseIDParam(r)
	if !ok {
		h.notFound(w, r, "admin")
		return
	}
	if id == s.AccountID {
		setFlash(w, "error", "You can't delete your own account here.")
		redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
		return
	}

	ctx, cancel := reqCtx(r)
	defer cancel()

	if err := h.deps.Accounts.DeleteAccount(ctx, id); err != nil {
		switch {
		case errors.Is(err, service.ErrAccountConnected):
			setFlash(w, "error", "That account is currently connected. Ask them to log out first.")
			redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
		case errors.Is(err, service.ErrAccountNotFound):
			h.notFound(w, r, "admin")
		default:
			h.deps.Logger.Error().Err(err).Msg("webadmin: delete account")
			h.forbidden(w, r, "admin", "Couldn't delete the account.")
		}
		return
	}

	setFlash(w, "success", "Account and all related data were deleted.")
	redirect(w, r, "/admin/accounts")
}

func (h *Handler) handleAdminToggleAdmin(w http.ResponseWriter, r *http.Request, s session) {
	if !h.verifyCSRF(r, s.AccountID) {
		h.forbidden(w, r, "admin", "Invalid request token. Please try again.")
		return
	}
	id, ok := parseIDParam(r)
	if !ok {
		h.notFound(w, r, "admin")
		return
	}
	if id == s.AccountID {
		setFlash(w, "error", "You can't change your own admin flag.")
		redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
		return
	}

	ctx, cancel := reqCtx(r)
	defer cancel()

	target, err := h.deps.Accounts.GetByID(ctx, id)
	if err != nil {
		h.notFound(w, r, "admin")
		return
	}
	if err := h.deps.Accounts.SetAdmin(ctx, id, !target.IsAdmin); err != nil {
		h.deps.Logger.Error().Err(err).Msg("webadmin: toggle admin")
		h.forbidden(w, r, "admin", "Couldn't update the admin flag.")
		return
	}

	if target.IsAdmin {
		setFlash(w, "success", "Revoked admin from "+target.Name+".")
	} else {
		setFlash(w, "success", "Granted admin to "+target.Name+".")
	}
	redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
}

// handleImpersonateStart lets an admin view the portal as another account.
func (h *Handler) handleImpersonateStart(w http.ResponseWriter, r *http.Request, s session) {
	if !h.verifyCSRF(r, s.AccountID) {
		h.forbidden(w, r, "admin", "Invalid request token. Please try again.")
		return
	}
	id, ok := parseIDParam(r)
	if !ok {
		h.notFound(w, r, "admin")
		return
	}
	if id == s.AccountID {
		setFlash(w, "info", "That's already you.")
		redirect(w, r, "/account")
		return
	}

	ctx, cancel := reqCtx(r)
	defer cancel()

	target, err := h.deps.Accounts.GetByID(ctx, id)
	if err != nil {
		h.notFound(w, r, "admin")
		return
	}

	h.writeSession(w, session{
		AccountID:      s.AccountID,
		ImpersonatedID: target.ID,
		IssuedAt:       time.Now(),
	})
	setFlash(w, "info", "You are now viewing the portal as "+target.Name+".")
	redirect(w, r, "/account")
}

// handleImpersonateStop returns an impersonating admin to their own identity.
// Allowed for anyone with a session (a non-impersonating call is a harmless
// no-op) so the "stop" control in the banner always works.
func (h *Handler) handleImpersonateStop(w http.ResponseWriter, r *http.Request) {
	s, ok := h.readSession(r)
	if !ok {
		redirect(w, r, "/login")
		return
	}
	if !h.verifyCSRF(r, s.AccountID) {
		h.forbidden(w, r, "", "Invalid request token. Please try again.")
		return
	}
	if !s.impersonating() {
		redirect(w, r, "/account")
		return
	}
	h.writeSession(w, session{AccountID: s.AccountID, IssuedAt: time.Now()})
	setFlash(w, "info", "Stopped impersonating.")
	redirect(w, r, "/admin")
}

// parseIDParam extracts and parses the {id} path value as a uint.
func parseIDParam(r *http.Request) (uint, bool) {
	raw := r.PathValue("id")
	id, err := strconv.ParseUint(raw, 10, 64)
	if err != nil || id == 0 {
		return 0, false
	}
	return uint(id), true
}

func (h *Handler) notFound(w http.ResponseWriter, r *http.Request, active string) {
	h.render(w, r, http.StatusNotFound, "error.html", "Not Found", active, errorData{
		Code: 404, Message: "That account doesn't exist.",
	})
}

func (h *Handler) forbidden(w http.ResponseWriter, r *http.Request, active, msg string) {
	h.render(w, r, http.StatusForbidden, "error.html", "Forbidden", active, errorData{
		Code: 403, Message: msg,
	})
}
