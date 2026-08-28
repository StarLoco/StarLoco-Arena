package store

import (
	"errors"

	"gorm.io/gorm"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// BugReportRepo stores what the retail client's bug dialog submits.
type BugReportRepo struct{ db *gorm.DB }

// Create persists a report.
func (r *BugReportRepo) Create(b *domain.BugReport) error {
	return r.db.Create(b).Error
}

// Get loads one report by id.
func (r *BugReportRepo) Get(id uint) (*domain.BugReport, error) {
	var b domain.BugReport
	err := r.db.First(&b, id).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, ErrNotFound
	}
	if err != nil {
		return nil, err
	}
	return &b, nil
}

// List returns a page of reports, newest first. When onlyOpen is set, resolved
// reports are excluded. It also returns the total matching the same filter, so
// the caller can paginate without a second query shape.
//
// The Log, Replay and SystemInfo columns are deliberately NOT selected: they are
// the large ones and the list never shows them, so loading them would pull
// megabytes to render a table of titles.
func (r *BugReportRepo) List(onlyOpen bool, limit, offset int) ([]domain.BugReport, int64, error) {
	if limit <= 0 {
		limit = 50
	}
	if offset < 0 {
		offset = 0
	}

	q := r.db.Model(&domain.BugReport{})
	if onlyOpen {
		q = q.Where("resolved = ?", false)
	}

	var total int64
	if err := q.Count(&total).Error; err != nil {
		return nil, 0, err
	}

	var rows []domain.BugReport
	err := q.Select("id", "created_at", "title", "type", "account_name",
		"coach_name", "world_name", "client_version", "screenshot_file", "resolved").
		Order("created_at DESC").Limit(limit).Offset(offset).Find(&rows).Error
	if err != nil {
		return nil, 0, err
	}
	return rows, total, nil
}

// CountOpen is the unresolved total, for the admin dashboard tile.
func (r *BugReportRepo) CountOpen() (int64, error) {
	var n int64
	err := r.db.Model(&domain.BugReport{}).Where("resolved = ?", false).Count(&n).Error
	return n, err
}

// SetResolved flips the triage flag.
func (r *BugReportRepo) SetResolved(id uint, resolved bool) error {
	res := r.db.Model(&domain.BugReport{}).Where("id = ?", id).
		Update("resolved", resolved)
	if res.Error != nil {
		return res.Error
	}
	if res.RowsAffected == 0 {
		return ErrNotFound
	}
	return nil
}

// Delete removes a report. The caller is responsible for its screenshot file -
// the repo does not touch the filesystem.
func (r *BugReportRepo) Delete(id uint) error {
	res := r.db.Delete(&domain.BugReport{}, id)
	if res.Error != nil {
		return res.Error
	}
	if res.RowsAffected == 0 {
		return ErrNotFound
	}
	return nil
}
