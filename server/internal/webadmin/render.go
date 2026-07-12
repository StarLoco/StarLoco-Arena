package webadmin

import (
	"bytes"
	"net/http"

	"github.com/dofusarena/go-server/internal/domain"
)

// pageData is the root view model handed to every page template. Page-
// specific payloads live in the Data field. The layout template reads
// Title/User/Impersonation/Flash to render chrome consistently.
type pageData struct {
	Title       string
	ServerName  string
	Active      string // nav highlight key: "account" | "admin" | ""
	User        *viewUser
	CSRFToken   string
	Flash       *flash
	Impersonate *impersonationBanner
	Data        any
}

// viewUser is the minimal identity the layout needs to render the header
// (name, admin badge, links). It reflects the *effective* account when
// impersonating.
type viewUser struct {
	ID      uint
	Name    string
	IsAdmin bool
}

// impersonationBanner drives the persistent "you are viewing as X" bar shown
// while an admin impersonates another account.
type impersonationBanner struct {
	AdminName  string
	TargetName string
	CSRFToken  string
}

// flash is a one-shot notice rendered at the top of a page (success/error).
type flash struct {
	Kind    string // "success" | "error" | "info"
	Message string
}

// render executes the named template with a fully-populated pageData,
// writing to a buffer first so a template error yields a clean 500 rather
// than a half-written response.
func (h *Handler) render(w http.ResponseWriter, r *http.Request, status int, tmplName, title, active string, data any) {
	pd := pageData{
		Title:      title,
		ServerName: h.serverName,
		Active:     active,
		Data:       data,
	}

	if s, ok := h.readSession(r); ok {
		if eff := h.loadEffectiveUser(r, s); eff != nil {
			pd.User = eff
			pd.CSRFToken = h.csrfToken(s.AccountID)
			if s.impersonating() {
				pd.Impersonate = h.buildImpersonationBanner(r, s)
			}
		}
	}
	if fl := consumeFlash(w, r); fl != nil {
		pd.Flash = fl
	}

	var buf bytes.Buffer
	if err := h.tmpl.execute(&buf, tmplName, pd); err != nil {
		h.deps.Logger.Error().Err(err).Str("template", tmplName).Msg("webadmin: render failed")
		http.Error(w, "internal server error", http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	w.WriteHeader(status)
	_, _ = buf.WriteTo(w)
}

// loadEffectiveUser resolves the effective account (impersonated when set,
// else the real one) into a viewUser, or nil if it no longer exists.
func (h *Handler) loadEffectiveUser(r *http.Request, s session) *viewUser {
	ctx, cancel := reqCtx(r)
	defer cancel()
	acc, err := h.deps.Accounts.GetByID(ctx, s.effectiveID())
	if err != nil {
		return nil
	}
	return accountToViewUser(acc)
}

func accountToViewUser(acc *domain.Account) *viewUser {
	return &viewUser{ID: acc.ID, Name: acc.Name, IsAdmin: acc.IsAdmin}
}

// buildImpersonationBanner resolves the admin + target names for the banner.
func (h *Handler) buildImpersonationBanner(r *http.Request, s session) *impersonationBanner {
	ctx, cancel := reqCtx(r)
	defer cancel()
	admin, err := h.deps.Accounts.GetByID(ctx, s.AccountID)
	if err != nil {
		return nil
	}
	target, err := h.deps.Accounts.GetByID(ctx, s.ImpersonatedID)
	if err != nil {
		return nil
	}
	return &impersonationBanner{
		AdminName:  admin.Name,
		TargetName: target.Name,
		CSRFToken:  h.csrfToken(s.AccountID),
	}
}

// redirect issues a 303 See Other (correct for post-POST redirects).
func redirect(w http.ResponseWriter, r *http.Request, to string) {
	http.Redirect(w, r, to, http.StatusSeeOther)
}
