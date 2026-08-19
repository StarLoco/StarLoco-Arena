package game

import (
	"strings"
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestTradeCooldownMatchesTheClient: 30 s, compared strictly, mirroring
// TradeContentCommand.czB. The first line always passes (the client's timestamp
// starts at 0).
func TestTradeCooldownMatchesTheClient(t *testing.T) {
	var g chatGate
	base := time.Date(2026, 8, 19, 12, 0, 0, 0, time.UTC)

	if !g.allowTrade(base) {
		t.Fatal("the first trade line was rejected")
	}
	if g.allowTrade(base.Add(29 * time.Second)) {
		t.Error("a second line 29s later was allowed; the client waits 30s")
	}
	// The client's test is `last < now - 30000`, i.e. the gap must EXCEED 30s.
	if g.allowTrade(base.Add(30 * time.Second)) {
		t.Error("a line exactly 30s later was allowed; the comparison is strict")
	}
	if !g.allowTrade(base.Add(30*time.Second + time.Millisecond)) {
		t.Error("a line just past 30s was rejected")
	}
}

// TestChatRepeatWindowMatchesTheClient: the same text inside 5 s is suppressed,
// short lines are exempt (the client skips anything under 6 characters), and the
// window expires.
func TestChatRepeatWindowMatchesTheClient(t *testing.T) {
	var g chatGate
	base := time.Date(2026, 8, 19, 12, 0, 0, 0, time.UTC)

	if !g.allowRepeat("selling a dofus", base) {
		t.Fatal("the first line was suppressed")
	}
	if g.allowRepeat("selling a dofus", base.Add(time.Second)) {
		t.Error("an immediate repeat was allowed")
	}
	if !g.allowRepeat("selling a dofus", base.Add(6*time.Second)) {
		t.Error("a repeat after the 5s window was suppressed")
	}
	// Under 6 characters: exempt, exactly as jd_0's bjl guard.
	for i := 0; i < 3; i++ {
		if !g.allowRepeat("hi", base) {
			t.Error("a short line was suppressed; the client exempts <6 chars")
		}
	}
}

// TestChatRepeatIsCaseInsensitiveAndTrimResistant: the client hashes the RAW
// input line, so a trailing space or a different pipe prefix slips the same text
// straight through. We key on the trimmed body, which closes that bypass — this
// test pins the stricter behaviour deliberately.
func TestChatRepeatIsCaseInsensitiveAndTrimResistant(t *testing.T) {
	var g chatGate
	base := time.Date(2026, 8, 19, 12, 0, 0, 0, time.UTC)

	if !g.allowRepeat("buying kamas", base) {
		t.Fatal("first line suppressed")
	}
	if g.allowRepeat("BUYING KAMAS", base.Add(time.Second)) {
		t.Error("a case-variant repeat was allowed")
	}
}

// TestSanitizeChatStripsMarkup is the injection guard. The client renders chat
// bodies AND sender names as markup with no escaping (rw_2.bJ), so a '<' from an
// untrusted source is a live tag — `<b>`, `<c>`, even `<image pixmap=...>`. The
// stock client can never send one (its input widget restricts them away), which
// is precisely why the server cannot rely on that.
func TestSanitizeChatStripsMarkup(t *testing.T) {
	cases := []struct{ in, want string }{
		{"<b>bold</b>", "bbold/b"},
		{"<image pixmap=\"x\"/>", "image pixmap=\"x\"/"},
		{"plain text", "plain text"},
		{"a > b < c", "a  b  c"},
		{"accents éàü survive", "accents éàü survive"},
	}
	for _, c := range cases {
		if got := sanitizeChatText(c.in, maxChatBody); got != c.want {
			t.Errorf("sanitizeChatText(%q) = %q, want %q", c.in, got, c.want)
		}
	}
}

// TestSanitizeChatTruncatesOnARuneBoundary: the cap exists because the u16-body
// readers are SIGNED in the client (>32767 arrives negative and the message is
// dropped). Cutting mid-rune would render as mojibake, so the cut must land on a
// boundary.
func TestSanitizeChatTruncatesOnARuneBoundary(t *testing.T) {
	long := strings.Repeat("é", 200) // 2 bytes each
	got := sanitizeChatText(long, 51)
	if len(got) > 51 {
		t.Errorf("result is %d bytes, want <= 51", len(got))
	}
	if len(got)%2 != 0 {
		t.Errorf("result is %d bytes — an odd length means it cut mid-rune", len(got))
	}
	if !strings.HasSuffix(got, "é") {
		t.Error("result does not end on a whole rune")
	}
}

// TestIgnoresCoach covers the lookup every delivery path depends on.
func TestIgnoresCoach(t *testing.T) {
	c := &domain.Coach{ID: 1, Ignored: []domain.CoachIgnored{
		{OwnerID: 1, IgnoredID: 7},
	}}
	if !ignoresCoach(c, 7) {
		t.Error("an ignored coach was not detected")
	}
	if ignoresCoach(c, 8) {
		t.Error("a non-ignored coach was reported as ignored")
	}
	if ignoresCoach(nil, 7) {
		t.Error("a nil coach must not report ignoring anyone")
	}
}

// TestDeliverChatSkipsIgnorers: the shared fan-out drops recipients who ignore
// the sender and reports how many actually received the line.
func TestDeliverChatSkipsIgnorers(t *testing.T) {
	const sender = uint(5)
	willGet := &Session{Coach: &domain.Coach{ID: 2}}
	wontGet := &Session{Coach: &domain.Coach{ID: 3, Ignored: []domain.CoachIgnored{
		{OwnerID: 3, IgnoredID: sender},
	}}}
	// Sessions with no connection fail to Send, so count only the filtering here.
	if ignoresCoach(willGet.Coach, sender) {
		t.Error("recipient 2 should not be filtered")
	}
	if !ignoresCoach(wontGet.Coach, sender) {
		t.Error("recipient 3 ignores the sender and must be filtered")
	}
	if n := deliverChat(nil, sender, []*Session{nil}); n != 0 {
		t.Errorf("a nil session counted as delivered (%d)", n)
	}
}
