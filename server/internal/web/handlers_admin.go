package web

import (
	"errors"
	"net/http"
	"strconv"
	"strings"
	"time"

	"github.com/StarLoco/arena-2.70/internal/store"
)

// ---------------------------------------------------------------------------
// Dashboard
// ---------------------------------------------------------------------------

type adminDashboardData struct {
	*baseData
	AdminTab      string
	TotalAccounts int64
	Connected     int64
	RankedCoaches int64
	UptimeSeconds int64
	Recent        []store.AccountSummary
}

func (s *Server) handleAdminDashboard(w http.ResponseWriter, r *http.Request, sess session) {
	d := &adminDashboardData{
		baseData:      s.newBase(w, r, "Admin", "admin"),
		AdminTab:      "overview",
		UptimeSeconds: s.uptimeSeconds(),
	}
	if n, err := s.store.Accounts.Count(); err == nil {
		d.TotalAccounts = n
	}
	if n, err := s.store.Accounts.CountConnected(); err == nil {
		d.Connected = n
	}
	if n, err := s.store.Coaches.LadderCount(); err == nil {
		d.RankedCoaches = int64(n)
	}
	// The most recent accounts are the highest ids; ListAccounts orders
	// ascending, so page to the end and reverse.
	if _, total, err := s.store.Accounts.ListAccounts("", 0, 1); err == nil {
		const recentCount = 8
		offset := int(total) - recentCount
		if offset < 0 {
			offset = 0
		}
		if rows, _, err := s.store.Accounts.ListAccounts("", offset, recentCount); err == nil {
			for i := len(rows) - 1; i >= 0; i-- {
				d.Recent = append(d.Recent, rows[i])
			}
		}
	}
	s.render(w, http.StatusOK, "admin_dashboard.html", d)
}

// ---------------------------------------------------------------------------
// Account list
// ---------------------------------------------------------------------------

type adminAccountsData struct {
	*baseData
	AdminTab string
	Query    string
	Rows     []store.AccountSummary
	Page     int
	LastPage int
	Total    int64
}

func (s *Server) handleAdminAccounts(w http.ResponseWriter, r *http.Request, sess session) {
	query := strings.TrimSpace(r.URL.Query().Get("q"))

	page := 1
	if v := r.URL.Query().Get("page"); v != "" {
		if n, err := strconv.Atoi(v); err == nil && n > 1 {
			page = n
		}
	}

	rows, total, err := s.store.Accounts.ListAccounts(query, (page-1)*accountsPerPage, accountsPerPage)
	if err != nil {
		s.log.Error("web: list accounts failed", "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "The account list could not be loaded.")
		return
	}

	lastPage := int((total + accountsPerPage - 1) / accountsPerPage)
	if lastPage < 1 {
		lastPage = 1
	}

	s.render(w, http.StatusOK, "admin_accounts.html", &adminAccountsData{
		baseData: s.newBase(w, r, "Accounts", "admin"),
		AdminTab: "accounts",
		Query:    query,
		Rows:     rows,
		Page:     page,
		LastPage: lastPage,
		Total:    total,
	})
}

// ---------------------------------------------------------------------------
// Account detail
// ---------------------------------------------------------------------------

type adminDetailData struct {
	*baseData
	AdminTab string
	Detail   *accountDetail
	// IsSelf disables the destructive controls on one's own row, so an admin
	// cannot delete or demote themselves and lock everyone out of the console.
	IsSelf bool
}

func (s *Server) handleAdminAccountDetail(w http.ResponseWriter, r *http.Request, sess session) {
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such account.")
		return
	}
	detail, err := s.loadDetail(id)
	if err != nil {
		if errors.Is(err, store.ErrNotFound) {
			s.renderError(w, r, http.StatusNotFound, "No such account.")
			return
		}
		s.log.Error("web: load account detail failed", "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "That account could not be loaded.")
		return
	}
	s.render(w, http.StatusOK, "admin_detail.html", &adminDetailData{
		baseData: s.newBase(w, r, detail.Account.Name, "admin"),
		AdminTab: "accounts",
		Detail:   detail,
		IsSelf:   id == sess.AccountID,
	})
}

// ---------------------------------------------------------------------------
// Create
// ---------------------------------------------------------------------------

type adminCreateData struct {
	*baseData
	AdminTab  string
	Error     string
	FormLogin string
	FormAdmin bool
}

func (s *Server) handleAdminCreateForm(w http.ResponseWriter, r *http.Request, sess session) {
	s.render(w, http.StatusOK, "admin_create.html", &adminCreateData{
		baseData: s.newBase(w, r, "New account", "admin"),
		AdminTab: "accounts",
	})
}

func (s *Server) handleAdminCreateSubmit(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	d := &adminCreateData{
		baseData: s.newBase(w, r, "New account", "admin"),
		AdminTab: "accounts",
	}

	login := strings.TrimSpace(r.PostFormValue("login"))
	password := r.PostFormValue("password")
	admin := r.PostFormValue("is_admin") != ""
	d.FormLogin, d.FormAdmin = login, admin

	if err := s.validate(login, password); err != nil {
		d.Error = err.Error()
		s.render(w, http.StatusBadRequest, "admin_create.html", d)
		return
	}
	if _, err := s.store.Accounts.FindByName(login); err == nil {
		d.Error = "That account name is already taken."
		s.render(w, http.StatusConflict, "admin_create.html", d)
		return
	} else if !errors.Is(err, store.ErrNotFound) {
		s.log.Error("web: account lookup failed", "err", err)
		d.Error = "The server could not reach its database."
		s.render(w, http.StatusInternalServerError, "admin_create.html", d)
		return
	}

	acc, err := s.store.Accounts.CreateAccount(login, password, admin)
	if err != nil {
		if isDuplicate(err) {
			d.Error = "That account name is already taken."
			s.render(w, http.StatusConflict, "admin_create.html", d)
			return
		}
		s.log.Error("web: admin account creation failed", "err", err)
		d.Error = "The account could not be created."
		s.render(w, http.StatusInternalServerError, "admin_create.html", d)
		return
	}

	s.log.Info("web: account created by admin", "login", login, "admin", admin, "by", sess.AccountID)
	setFlash(w, "success", "Account "+acc.Name+" created.")
	redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(acc.ID), 10))
}

// ---------------------------------------------------------------------------
// Delete / grant / revoke
// ---------------------------------------------------------------------------

func (s *Server) handleAdminDelete(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such account.")
		return
	}
	// Deleting the account you are signed in as would leave the session
	// pointing at nothing and, if it is the only admin, lock the console.
	if id == sess.AccountID {
		setFlash(w, "error", "You cannot delete the account you are signed in as.")
		redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
		return
	}

	acc, err := s.store.Accounts.FindByID(id)
	if err != nil {
		s.renderError(w, r, http.StatusNotFound, "No such account.")
		return
	}
	name := acc.Name

	switch err := s.store.Accounts.DeleteAccount(id); {
	case err == nil:
		s.log.Info("web: account deleted", "login", name, "by", sess.AccountID)
		setFlash(w, "success", "Account "+name+" and all of its data were deleted.")
		redirect(w, r, "/admin/accounts")
	case errors.Is(err, store.ErrAccountConnected):
		setFlash(w, "error", name+" is connected right now. Ask them to log out, then try again.")
		redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
	default:
		s.log.Error("web: account delete failed", "err", err)
		setFlash(w, "error", "That account could not be deleted.")
		redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
	}
}

func (s *Server) handleAdminToggleAdmin(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such account.")
		return
	}
	// Revoking your own admin rights is how a one-admin server locks itself
	// out of its own console for good.
	if id == sess.AccountID {
		setFlash(w, "error", "You cannot change your own administrator rights.")
		redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
		return
	}

	acc, err := s.store.Accounts.FindByID(id)
	if err != nil {
		s.renderError(w, r, http.StatusNotFound, "No such account.")
		return
	}
	if err := s.store.Accounts.SetAdmin(id, !acc.IsAdmin); err != nil {
		s.log.Error("web: set admin failed", "err", err)
		setFlash(w, "error", "That change could not be saved.")
	} else {
		s.log.Info("web: admin flag changed",
			"login", acc.Name, "admin", !acc.IsAdmin, "by", sess.AccountID)
		if acc.IsAdmin {
			setFlash(w, "success", "Administrator rights removed from "+acc.Name+".")
		} else {
			setFlash(w, "success", acc.Name+" is now an administrator.")
		}
	}
	redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
}

// ---------------------------------------------------------------------------
// Impersonation
// ---------------------------------------------------------------------------

// handleImpersonateStart begins viewing the site as another account.
//
// The session keeps BOTH identities: the real one stays the audit identity and
// the only thing requireAdmin ever looks at, so impersonation grants a view and
// never a privilege.
func (s *Server) handleImpersonateStart(w http.ResponseWriter, r *http.Request, sess session) {
	if !s.requirePost(w, r, sess) {
		return
	}
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such account.")
		return
	}
	target, err := s.store.Accounts.FindByID(id)
	if err != nil {
		s.renderError(w, r, http.StatusNotFound, "No such account.")
		return
	}
	if id == sess.AccountID {
		setFlash(w, "info", "That is already you.")
		redirect(w, r, "/admin/accounts/"+strconv.FormatUint(uint64(id), 10))
		return
	}

	s.writeSession(w, session{
		AccountID:      sess.AccountID,
		ImpersonatedID: id,
		IssuedAt:       time.Now(),
	})
	s.log.Info("web: impersonation started", "target", target.Name, "by", sess.AccountID)
	setFlash(w, "info", "You are now viewing the site as "+target.Name+".")
	redirect(w, r, "/account")
}

// handleImpersonateStop is deliberately NOT admin-gated: whoever is
// impersonating must always be able to get back to themselves, including an
// admin whose rights were revoked while they were viewing as someone else.
func (s *Server) handleImpersonateStop(w http.ResponseWriter, r *http.Request) {
	sess, ok := s.readSession(r)
	if !ok {
		redirect(w, r, "/login")
		return
	}
	if !sameOrigin(r) {
		s.renderError(w, r, http.StatusForbidden, "That request did not come from this site.")
		return
	}
	if !sess.impersonating() {
		redirect(w, r, "/account")
		return
	}

	s.writeSession(w, session{AccountID: sess.AccountID, IssuedAt: time.Now()})
	s.log.Info("web: impersonation stopped", "by", sess.AccountID)
	setFlash(w, "info", "You are yourself again.")
	redirect(w, r, "/admin/accounts")
}
