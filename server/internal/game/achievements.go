package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// achievements.go evaluates the "exploits" and announces unlocks.
//
// There is no per-achievement code and there never needs to be: an achievement is
// done when every statistic condition is met and every listed card is in the
// coach's tome (gamedata.Achievement.Done, mirroring the client's aau_1.a). So
// this file is a loop, and everything interesting lives in the DATA plus the
// question of WHEN to re-run it.
//
// Nothing about completion is stored — it is recomputed from the criteria each
// time, exactly as the client does. The database only remembers which unlocks
// have already been ANNOUNCED, so the 22000 toast fires once per coach.
//
// What an unlock does NOT do is grant anything. 2.70 has no achievement reward:
// points are cosmetic, and the record's one spare i32 has no consumer anywhere in
// the client. Unlocks matter because other content uses them as KEYS (zone
// triggers, challenge gating, the island Zaap dialog).

// evaluateAchievements recomputes this coach's achievements and announces any
// that are newly complete.
//
// Safe to call often: it is a pure read plus, in the common case, no writes at
// all. Callers do not need to know whether anything changed.
func (s *Session) evaluateAchievements() {
	if s.Coach == nil || s.deps.Achievements == nil {
		return
	}
	coach, err := s.deps.Store.Coaches.Get(s.Coach.ID)
	if err != nil || coach == nil {
		return
	}
	// Fold whatever the coach currently holds into the tome, then evaluate against
	// the tome rather than the inventory. The client's set never shrinks, so a
	// card that has been owned keeps counting after it is sold or fused away.
	tome, err := s.deps.Store.Coaches.SyncTome(s.Coach.ID, ownedTemplates(coach))
	if err != nil {
		s.log.Warn("sync tome", "coach", s.Coach.Name, "err", err)
		tome = nil
	}
	stat, hasCard := coachAchievementLookups(coach, tome)

	// Deterministic order: IDs() is sorted, so a coach completing several at once
	// is always announced in the same sequence.
	var done []int16
	for _, id := range s.deps.Achievements.IDs() {
		if a := s.deps.Achievements.Get(id); a.Done(stat, hasCard) {
			done = append(done, id)
		}
	}
	if len(done) == 0 {
		return
	}
	fresh, err := s.deps.Store.Coaches.RecordAchievements(s.Coach.ID, done)
	if err != nil {
		s.log.Warn("record achievements", "coach", s.Coach.Name, "err", err)
		return
	}
	for _, id := range fresh {
		a := s.deps.Achievements.Get(id)
		// A hidden achievement is a no-op client-side: zN gates its ENTIRE 22000
		// body on !isHidden(), so the frame would be decoded and discarded. It is
		// still recorded above, because hidden achievements are exactly the ones
		// used as content keys.
		if a == nil || a.Hidden {
			continue
		}
		s.sendAchievementUnlocked(id)
		s.log.Debug("achievement unlocked", "coach", s.Coach.Name,
			"achievement", id, "points", a.Points)
	}
}

// ownedTemplates lists the distinct card templates the coach currently holds,
// equipped or not. This is the INPUT to the tome, never the tome itself.
func ownedTemplates(c *domain.Coach) []int32 {
	seen := make(map[int32]bool, len(c.Inventory))
	out := make([]int32, 0, len(c.Inventory))
	for _, card := range c.Inventory {
		if card.TemplateID == 0 || seen[card.TemplateID] {
			continue
		}
		seen[card.TemplateID] = true
		out = append(out, card.TemplateID)
	}
	return out
}

// coachAchievementLookups builds the two accessors the evaluator needs: the
// criteria counters, and tome membership.
func coachAchievementLookups(c *domain.Coach, tome map[int32]bool) (func(int16) int32, func(int32) bool) {
	stats := make(map[int16]int32, len(c.Stats))
	for _, st := range c.Stats {
		stats[st.StatID] = st.Value
	}
	// Absent keys read as 0, matching the client's aGz.cp.
	return func(id int16) int32 { return stats[id] }, func(id int32) bool { return tome[id] }
}

// sendAchievementUnlocked sends S2C 22000 (client ade_0): [i16 achievementId].
//
// Safe to push unsolicited: its handler zN is registered permanently at login
// (by_2), and all it does is raise a toast. This is unlike its sibling 22002,
// which must only ever be sent as a reply — see protocol.OpStatisticData.
func (s *Session) sendAchievementUnlocked(id int16) {
	w := protocol.NewWriter().U16(uint16(id))
	frame, err := protocol.EncodeS2C(protocol.OpAchievementUnlocked, w.Bytes())
	if err != nil {
		return
	}
	_ = s.Send(frame)
}
