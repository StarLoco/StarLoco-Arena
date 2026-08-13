package web

import (
	"errors"
	"sort"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// accountDetail is the deep view of everything the server stores about one
// account. It powers both the player's own "my data" page and the admin
// console's per-account view, so the two can never drift apart — an admin sees
// exactly what the player sees, plus the moderation controls.
//
// It is a flat DTO rather than the live domain objects because domain.Coach
// embeds a sync.Mutex and must not be copied, and because a template should not
// be able to reach a repository.
type accountDetail struct {
	Account *domain.Account
	Coach   *domain.Coach

	// Rank is the coach's ladder position, 0 when unranked.
	Rank int

	// Equipped and Bag split the inventory the way the client shows it.
	Equipped []domain.CoachCard
	Bag      []domain.CoachCard

	Fighters []domain.Fighter
	Teams    []domain.Team

	Friends []socialEntry
	Ignored []socialEntry

	Wallet []domain.CoachCurrency
	Stats  []domain.CoachStat

	MailCount int64
}

// socialEntry is one friend or ignored coach, flattened to the two fields a
// template needs so it never dereferences a possibly-nil association.
type socialEntry struct {
	Name   string
	Notify bool
	Online bool
}

// HasCoach reports whether the account got as far as creating a character.
func (d *accountDetail) HasCoach() bool { return d.Coach != nil }

// CardCount totals the inventory, counting stacks.
func (d *accountDetail) CardCount() int {
	n := 0
	for _, c := range append(append([]domain.CoachCard{}, d.Equipped...), d.Bag...) {
		n += int(c.Quantity)
	}
	return n
}

// loadDetail aggregates everything hanging off an account.
//
// A missing coach is not an error: an account that has registered on the site
// but never logged into the game has none, and that page must still render.
func (s *Server) loadDetail(accountID uint) (*accountDetail, error) {
	acc, err := s.store.Accounts.FindByID(accountID)
	if err != nil {
		return nil, err
	}

	d := &accountDetail{Account: acc}
	if acc.CoachID == nil {
		return d, nil
	}

	coach, err := s.store.Coaches.Get(*acc.CoachID)
	if err != nil {
		if errors.Is(err, store.ErrNotFound) {
			// The account points at a coach that no longer exists. Render the
			// account rather than 500 — an admin looking at a broken row is
			// precisely who needs to see it.
			s.log.Warn("web: account references a missing coach",
				"account", acc.Name, "coachID", *acc.CoachID)
			return d, nil
		}
		return nil, err
	}
	d.Coach = coach

	if rank, err := s.store.Coaches.LadderRank(coach.ID); err == nil {
		d.Rank = rank
	}

	for _, c := range coach.Inventory {
		if c.Pos == 0 {
			d.Bag = append(d.Bag, c)
		} else {
			d.Equipped = append(d.Equipped, c)
		}
	}
	sort.Slice(d.Equipped, func(i, j int) bool { return d.Equipped[i].Pos < d.Equipped[j].Pos })
	sort.Slice(d.Bag, func(i, j int) bool { return d.Bag[i].TemplateID < d.Bag[j].TemplateID })

	for _, f := range coach.Friends {
		e := socialEntry{Notify: f.Notify}
		if f.Friend != nil {
			e.Name = f.Friend.Name
			e.Online = s.isOnline(f.Friend.Name)
		}
		d.Friends = append(d.Friends, e)
	}
	for _, ig := range coach.Ignored {
		e := socialEntry{}
		if ig.Ignored != nil {
			e.Name = ig.Ignored.Name
		}
		d.Ignored = append(d.Ignored, e)
	}

	d.Wallet = coach.Wallet
	d.Stats = coach.Stats
	sort.Slice(d.Stats, func(i, j int) bool { return d.Stats[i].StatID < d.Stats[j].StatID })

	if fighters, err := s.store.Fighters.ListByCoach(coach.ID); err == nil {
		d.Fighters = fighters
	} else {
		s.log.Error("web: list fighters failed", "err", err)
	}
	if teams, err := s.store.Teams.ListByCoach(coach.ID); err == nil {
		d.Teams = teams
	} else {
		s.log.Error("web: list teams failed", "err", err)
	}
	if n, err := s.store.Mail.InboxCount(coach.ID); err == nil {
		d.MailCount = n
	}

	return d, nil
}

// isOnline reports whether a coach is currently in the world. The portal has
// no direct access to the registry, so this is answered from the persisted
// connected flag, which startup resets and login/logout maintain.
func (s *Server) isOnline(coachName string) bool {
	var n int64
	err := s.store.DB().Table("accounts").
		Joins("JOIN coaches ON coaches.id = accounts.coach_id").
		Where("coaches.name = ? AND accounts.connected = ?", coachName, true).
		Count(&n).Error
	return err == nil && n > 0
}
