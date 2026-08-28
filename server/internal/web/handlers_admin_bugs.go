package web

import (
	"errors"
	"net/http"
	"os"
	"strconv"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
)

const bugsPerPage = 30

type adminBugsData struct {
	*baseData
	AdminTab string
	Reports  []domain.BugReport
	Total    int64
	OnlyOpen bool
	Page     int
	Pages    int
	PrevPage int
	NextPage int
}

type adminBugDetailData struct {
	*baseData
	AdminTab string
	Report   *domain.BugReport
	// HasScreenshot is resolved against the filesystem, not just the column:
	// a report whose file was deleted by hand should not render a broken image.
	HasScreenshot bool
}

func (s *Server) handleAdminBugs(w http.ResponseWriter, r *http.Request, _ session) {
	onlyOpen := r.URL.Query().Get("all") != "1"
	page, _ := strconv.Atoi(r.URL.Query().Get("page"))
	if page < 1 {
		page = 1
	}

	reports, total, err := s.store.BugReports.List(onlyOpen, bugsPerPage, (page-1)*bugsPerPage)
	if err != nil {
		s.log.Error("admin: list bug reports", "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "Could not load bug reports.")
		return
	}

	pages := int((total + bugsPerPage - 1) / bugsPerPage)
	if pages < 1 {
		pages = 1
	}
	d := &adminBugsData{
		baseData: s.newBase(w, r, "Bug reports", "admin"),
		AdminTab: "bugs",
		Reports:  reports,
		Total:    total,
		OnlyOpen: onlyOpen,
		Page:     page,
		Pages:    pages,
		PrevPage: page - 1,
		NextPage: page + 1,
	}
	s.render(w, http.StatusOK, "admin_bugs.html", d)
}

func (s *Server) handleAdminBugDetail(w http.ResponseWriter, r *http.Request, _ session) {
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such bug report.")
		return
	}
	report, err := s.store.BugReports.Get(id)
	if err != nil {
		if errors.Is(err, store.ErrNotFound) {
			s.renderError(w, r, http.StatusNotFound, "No such bug report.")
			return
		}
		s.log.Error("admin: load bug report", "id", id, "err", err)
		s.renderError(w, r, http.StatusInternalServerError, "Could not load that bug report.")
		return
	}

	d := &adminBugDetailData{
		baseData: s.newBase(w, r, "Bug report #"+strconv.FormatUint(uint64(report.ID), 10), "admin"),
		AdminTab: "bugs",
		Report:   report,
	}
	if path, ok := s.screenshotPath(report.ScreenshotFile); ok {
		if _, err := os.Stat(path); err == nil {
			d.HasScreenshot = true
		}
	}
	s.render(w, http.StatusOK, "admin_bug_detail.html", d)
}

// handleAdminBugScreenshot streams a stored screenshot. Serving it through a
// handler rather than from a static directory keeps it behind requireAdmin -
// a bug screenshot can show a player's account name, chat and mailbox.
func (s *Server) handleAdminBugScreenshot(w http.ResponseWriter, r *http.Request, _ session) {
	id, ok := idParam(r)
	if !ok {
		http.NotFound(w, r)
		return
	}
	report, err := s.store.BugReports.Get(id)
	if err != nil {
		http.NotFound(w, r)
		return
	}
	path, ok := s.screenshotPath(report.ScreenshotFile)
	if !ok {
		http.NotFound(w, r)
		return
	}
	f, err := os.Open(path)
	if err != nil {
		http.NotFound(w, r)
		return
	}
	defer func() { _ = f.Close() }()

	w.Header().Set("Content-Type", "image/jpeg")
	w.Header().Set("Cache-Control", "private, max-age=300")
	// Never inline-render a submitted file as anything but an image.
	w.Header().Set("X-Content-Type-Options", "nosniff")
	http.ServeContent(w, r, report.ScreenshotFile, report.CreatedAt, f)
}

func (s *Server) handleAdminBugResolve(w http.ResponseWriter, r *http.Request, _ session) {
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such bug report.")
		return
	}
	resolved := r.FormValue("resolved") == "1"
	if err := s.store.BugReports.SetResolved(id, resolved); err != nil {
		s.log.Error("admin: resolve bug report", "id", id, "err", err)
		setFlash(w, "error", "Could not update that bug report.")
	} else if resolved {
		setFlash(w, "success", "Bug report marked as resolved.")
	} else {
		setFlash(w, "success", "Bug report reopened.")
	}
	redirect(w, r, "/admin/bugs/"+strconv.FormatUint(uint64(id), 10))
}

func (s *Server) handleAdminBugDelete(w http.ResponseWriter, r *http.Request, _ session) {
	id, ok := idParam(r)
	if !ok {
		s.renderError(w, r, http.StatusNotFound, "No such bug report.")
		return
	}

	// Read it first so the screenshot can go with it - the repo deliberately
	// does not touch the filesystem, so orphaned files are this handler's job.
	if report, err := s.store.BugReports.Get(id); err == nil {
		if path, ok := s.screenshotPath(report.ScreenshotFile); ok {
			if err := os.Remove(path); err != nil && !os.IsNotExist(err) {
				s.log.Warn("admin: could not delete bug screenshot", "id", id, "err", err)
			}
		}
	}

	if err := s.store.BugReports.Delete(id); err != nil {
		s.log.Error("admin: delete bug report", "id", id, "err", err)
		setFlash(w, "error", "Could not delete that bug report.")
		redirect(w, r, "/admin/bugs/"+strconv.FormatUint(uint64(id), 10))
		return
	}
	setFlash(w, "success", "Bug report deleted.")
	redirect(w, r, "/admin/bugs")
}
