package gamedata

import (
	"encoding/binary"
	"sort"
)

// achievements.go decodes the "exploits" tables: achievements (type 800, client
// class ru_1 -> runtime aau_1), their categories (801, fw_0 -> ajk_1) and
// subcategories (802, wr_0 -> li_2).
//
// The important property of this system is that completion is entirely GENERIC.
// An achievement is done when every statistic condition is met AND every listed
// card is in the coach's tome; there is no per-achievement logic anywhere in the
// client, and there is no reward to grant:
//
//   - Points ("PE") are cosmetic. The client sums them for a header total and to
//     pick a row icon/style tier (aea_1), and nothing else reads them.
//   - The record's one remaining i32 (ru_1 field bJg, aau_1.adY) is parsed, copied
//     into the runtime object, and then never consumed by ANY client code. If a
//     reward id was ever meant to live there, 2.70 does not act on it — so it is
//     decoded here for completeness and deliberately given no behaviour.
//
// Unlocking therefore does exactly two things: it announces itself (S2C 22000),
// and it acts as a KEY for other content — zone triggers (oq), challenge gating
// (afz_0) and the island Zaap dialog all test "does this coach have achievement
// N" rather than granting anything.

// Achievement record types.
const (
	TypeAchievement            = 800
	TypeAchievementCategory    = 801
	TypeAchievementSubcategory = 802
)

// AchievementCondition is one statistic threshold: the coach's counter for StatID
// must be >= Threshold. StatID indexes the client's or_0 enum, which documents
// every id in French (e.g. 221 = "nombre de fois ou le coach a discute avec un
// breedmaster").
type AchievementCondition struct {
	StatID    int16
	Threshold int16
}

// Achievement is a decoded type-800 record.
//
// Wire layout (big-endian), field-for-field from ru_1.a, and verified by
// decoding all 332 shipped records with byte-exact consumption (no short read,
// no overrun):
//
//	i16 id, i16 previous, i16 superseding, i16 category, i16 subcategory,
//	i16 points, i32 scriptID,
//	u8 condCount,  condCount × {i16 statId, i16 threshold},
//	u8 cardCount,  cardCount × i32 cardTemplateId,
//	u8 autoDescription, i32 unused, u8 hidden
type Achievement struct {
	ID int16
	// Previous is the achievement that must already be done for this one to be
	// listed, and Superseding is the one that HIDES it once earned. Together they
	// form the tier chains the UI renders as a row of key icons (aea_1). 0 = none.
	//
	// These gate VISIBILITY only (qy_2's list filter), never completion: the
	// client evaluates aau_1.a() purely from the conditions below, so a chain's
	// later tier can legitimately complete before its earlier one.
	Previous    int16
	Superseding int16
	// Category / Subcategory index the type-801 / type-802 tables (the tab strip
	// and the left-hand list).
	Category    int16
	Subcategory int16
	// Points is the cosmetic "PE" value. See the file comment.
	Points int16
	// ScriptID is a scenario script fired client-side on unlock (zN, via
	// anr_0.aXN()). It is narrative, not a reward. 0 = none.
	ScriptID int32
	// Conditions are the statistic thresholds. ALL must hold.
	Conditions []AchievementCondition
	// Cards are card template ids that must ALL be in the coach's tome.
	Cards []int32
	// AutoDescription tells the client to build the description text from the
	// condition list instead of using the canned i18n string. Display-only.
	AutoDescription bool
	// Unused is ru_1 field bJg. Decoded so the record round-trips; it has NO
	// consumer anywhere in the client. Do not build behaviour on it.
	Unused int32
	// Hidden keeps the achievement out of the UI list, and out of the unlock
	// toast (zN checks isHidden before announcing).
	Hidden bool
}

// Done reports whether a coach satisfies this achievement.
//
// stat returns the coach's counter for a statistic id (0 if absent, matching the
// client's aGz.cp), and hasCard reports tome membership. This mirrors aau_1.a
// exactly: every condition >= its threshold, and every listed card present.
//
// Note the >= : the client uses `cp(id) < threshold -> false`, so a counter that
// overshoots still completes.
func (a *Achievement) Done(stat func(int16) int32, hasCard func(int32) bool) bool {
	if a == nil {
		return false
	}
	for _, c := range a.Conditions {
		if stat(c.StatID) < int32(c.Threshold) {
			return false
		}
	}
	for _, id := range a.Cards {
		if !hasCard(id) {
			return false
		}
	}
	return true
}

// Progress is the completion percentage the client shows on each row (aea_1):
// each condition contributes min(value*100/threshold, 100), each required card
// contributes 0 or 100, and the result is their mean. Reproduced here so the
// server can log/expose the same number the player sees.
func (a *Achievement) Progress(stat func(int16) int32, hasCard func(int32) bool) int {
	if a == nil {
		return 0
	}
	n, sum := 0, 0
	for _, c := range a.Conditions {
		n++
		if c.Threshold <= 0 {
			sum += 100
			continue
		}
		p := int(stat(c.StatID)) * 100 / int(c.Threshold)
		if p > 100 {
			p = 100
		}
		if p < 0 {
			p = 0
		}
		sum += p
	}
	for _, id := range a.Cards {
		n++
		if hasCard(id) {
			sum += 100
		}
	}
	if n == 0 {
		return 100
	}
	return sum / n
}

// AchievementCategory is a decoded type-801 record. The record carries ONLY the
// id — every label comes from i18n tables 43/44, keyed by it.
type AchievementCategory struct {
	ID int16
	// Subcategories are the type-802 ids attached to this category, ascending.
	Subcategories []int16
}

// Achievements is the decoded achievement catalogue.
type Achievements struct {
	byID       map[int16]*Achievement
	ids        []int16
	categories map[int16]*AchievementCategory
	catIDs     []int16
}

// decodeAchievement parses one type-800 record. It returns nil if the buffer is
// too short at any point, so a truncated record is skipped rather than producing
// a half-filled definition that would silently mis-evaluate.
func decodeAchievement(b []byte) *Achievement {
	p := 0
	need := func(n int) bool { return p+n <= len(b) }
	i16 := func() int16 { v := int16(binary.BigEndian.Uint16(b[p:])); p += 2; return v }
	i32 := func() int32 { v := int32(binary.BigEndian.Uint32(b[p:])); p += 4; return v }

	if !need(16) {
		return nil
	}
	a := &Achievement{}
	a.ID, a.Previous, a.Superseding = i16(), i16(), i16()
	a.Category, a.Subcategory, a.Points = i16(), i16(), i16()
	a.ScriptID = i32()

	if !need(1) {
		return nil
	}
	n := int(b[p])
	p++
	if !need(n * 4) {
		return nil
	}
	for i := 0; i < n; i++ {
		a.Conditions = append(a.Conditions, AchievementCondition{StatID: i16(), Threshold: i16()})
	}

	if !need(1) {
		return nil
	}
	n = int(b[p])
	p++
	if !need(n * 4) {
		return nil
	}
	for i := 0; i < n; i++ {
		a.Cards = append(a.Cards, i32())
	}

	if !need(1) {
		return nil
	}
	a.AutoDescription = b[p] == 1
	p++
	if !need(4) {
		return nil
	}
	a.Unused = i32()
	if !need(1) {
		return nil
	}
	a.Hidden = b[p] == 1
	return a
}

// LoadAchievements reads types 800, 801 and 802 and links them together.
func (s *Store) LoadAchievements() (*Achievements, error) {
	out := &Achievements{
		byID:       make(map[int16]*Achievement),
		categories: make(map[int16]*AchievementCategory),
	}
	for _, e := range s.EntriesOf(TypeAchievement) {
		rec, err := s.ReadRecord(e.Position)
		if err != nil {
			return nil, err
		}
		if a := decodeAchievement(rec.Data); a != nil {
			out.byID[a.ID] = a
		}
	}
	// Type 801: a bare i16 id.
	for _, e := range s.EntriesOf(TypeAchievementCategory) {
		rec, err := s.ReadRecord(e.Position)
		if err != nil {
			return nil, err
		}
		if len(rec.Data) < 2 {
			continue
		}
		id := int16(binary.BigEndian.Uint16(rec.Data))
		out.categories[id] = &AchievementCategory{ID: id}
	}
	// Type 802: [i16 parentCategory][i16 subcategoryId].
	for _, e := range s.EntriesOf(TypeAchievementSubcategory) {
		rec, err := s.ReadRecord(e.Position)
		if err != nil {
			return nil, err
		}
		if len(rec.Data) < 4 {
			continue
		}
		parent := int16(binary.BigEndian.Uint16(rec.Data))
		sub := int16(binary.BigEndian.Uint16(rec.Data[2:]))
		cat, ok := out.categories[parent]
		if !ok {
			cat = &AchievementCategory{ID: parent}
			out.categories[parent] = cat
		}
		cat.Subcategories = append(cat.Subcategories, sub)
	}
	out.reindex()
	return out, nil
}

// NewAchievements builds a catalogue from explicit definitions (tests/tooling).
func NewAchievements(defs ...*Achievement) *Achievements {
	out := &Achievements{
		byID:       make(map[int16]*Achievement, len(defs)),
		categories: make(map[int16]*AchievementCategory),
	}
	for _, d := range defs {
		if d != nil {
			out.byID[d.ID] = d
		}
	}
	out.reindex()
	return out
}

func (a *Achievements) reindex() {
	a.ids = make([]int16, 0, len(a.byID))
	for id := range a.byID {
		a.ids = append(a.ids, id)
	}
	sort.Slice(a.ids, func(i, j int) bool { return a.ids[i] < a.ids[j] })

	a.catIDs = make([]int16, 0, len(a.categories))
	for id, c := range a.categories {
		a.catIDs = append(a.catIDs, id)
		sort.Slice(c.Subcategories, func(i, j int) bool { return c.Subcategories[i] < c.Subcategories[j] })
	}
	sort.Slice(a.catIDs, func(i, j int) bool { return a.catIDs[i] < a.catIDs[j] })
}

// Get returns an achievement by id, or nil.
func (a *Achievements) Get(id int16) *Achievement {
	if a == nil {
		return nil
	}
	return a.byID[id]
}

// IDs returns every achievement id, ascending. Iterating this (rather than the
// map) keeps unlock ORDER deterministic, which matters because unlocks are
// announced one frame each.
func (a *Achievements) IDs() []int16 {
	if a == nil {
		return nil
	}
	return a.ids
}

// Len is the number of decoded achievements.
func (a *Achievements) Len() int {
	if a == nil {
		return 0
	}
	return len(a.byID)
}

// Category returns a category by id, or nil.
func (a *Achievements) Category(id int16) *AchievementCategory {
	if a == nil {
		return nil
	}
	return a.categories[id]
}

// CategoryIDs returns every category id, ascending.
func (a *Achievements) CategoryIDs() []int16 {
	if a == nil {
		return nil
	}
	return a.catIDs
}
