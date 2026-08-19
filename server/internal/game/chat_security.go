package game

import (
	"strings"
	"time"
	"unicode/utf8"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// Chat safety, mirroring the retail client's own constraints.
//
// The client applies several limits before it sends anything, so a stock client
// never trips the ones below. That is exactly why the server needs them: every
// one of these is trivially removed from a modified client, and several of the
// client's own protections turn out to be missing, one-sided or bypassable.
//
// What the client does, and what we therefore do:
//
//   - TRADE COOLDOWN: `TradeContentCommand.czB = 30000` ms, compared strictly
//     (`<`), on a singleton that is never reset — so it survives relogin. We
//     enforce the same 30 s per coach.
//   - ANTI-REPEAT: `jd_0` keeps the last 10 distinct input lines for 5 s each and
//     rejects a repeat, but only for lines of 6+ characters, and it hashes the RAW
//     input line — so a trailing space, or switching pipe prefix, defeats it. We
//     key on the trimmed body instead, which closes that hole.
//   - `<` AND `>`: the input widget's `restrict="[.*&[^<>]]"` strips them. This is
//     the only thing standing between players and the chat renderer, which parses
//     `<b>`, `<c>`, `<text color=...>` and `<image pixmap=...>` from the message
//     body AND the sender name with no escaping at all (`rw_2.bJ`). A modified
//     client could otherwise inject markup into everyone else's chat window, so we
//     strip them on relay.
//   - IGNORE LIST: the client filters General, Trade, Clan and Group by sender
//     name — but NOT private messages (`om_0` case 3154 has no check), and a
//     private message additionally force-maximises and force-opens the chat
//     window. An ignored player can therefore pop a victim's UI at will. There is
//     no client-side backstop, so the server must filter, and private is the case
//     that actually matters.
const (
	// tradeCooldown mirrors TradeContentCommand.czB.
	tradeCooldown = 30 * time.Second
	// chatRepeatWindow / chatRepeatMinLen / chatRepeatRing mirror jd_0's
	// bjq / bjl / bjn.
	chatRepeatWindow = 5 * time.Second
	chatRepeatMinLen = 6
	chatRepeatRing   = 10

	// maxChatBody is the largest message body the u16-prefixed pipes (3152 / 3168
	// / 3170 / 3198) can carry. The client reads it with a SIGNED getShort, so
	// anything past 32767 arrives negative and the message is dropped with a
	// NegativeArraySizeException. The stock client can only produce ~4 KB
	// (maxChars=1024, UTF-8), so this only bites on a modified one.
	maxChatBody = 32767
	// maxChatName bounds the sender name, which every chat reader takes with a u8
	// length (`& 0xFF`, so 255 is safe — unlike the channel family's signed reads).
	maxChatName = 255
)

// chatGate is a session's chat rate state. It is touched only by the session's
// own goroutine (the read loop dispatches handlers inline), so it needs no lock —
// the same reasoning as Session.spectating.
type chatGate struct {
	lastTrade time.Time
	recent    [chatRepeatRing]string
	recentAt  [chatRepeatRing]time.Time
	next      int
}

// allowTrade reports whether a Trade line may be sent now, stamping the clock
// when it may. Matches the client's strict comparison: the gap must EXCEED the
// cooldown.
func (g *chatGate) allowTrade(now time.Time) bool {
	if !g.lastTrade.IsZero() && now.Sub(g.lastTrade) <= tradeCooldown {
		return false
	}
	g.lastTrade = now
	return true
}

// allowRepeat reports whether a message is not a too-recent duplicate.
//
// Deliberately stricter than the client in one respect: it keys on the trimmed
// body rather than the raw input line, so appending a space or switching pipe no
// longer slips the same text past the window.
func (g *chatGate) allowRepeat(msg string, now time.Time) bool {
	if utf8.RuneCountInString(msg) < chatRepeatMinLen {
		return true // the client exempts short lines; so do we
	}
	key := strings.ToLower(msg)
	for i := range g.recent {
		if g.recent[i] != key {
			continue
		}
		if now.Before(g.recentAt[i]) {
			return false // still inside its window
		}
		g.recentAt[i] = now.Add(chatRepeatWindow) // expired: allow and restart
		return true
	}
	g.recent[g.next] = key
	g.recentAt[g.next] = now.Add(chatRepeatWindow)
	g.next = (g.next + 1) % chatRepeatRing
	return true
}

// sanitizeChatText makes a string safe to hand to the client's chat renderer.
//
// The renderer treats the message body and the sender name as MARKUP and does no
// escaping, so a `<` from an untrusted source is a live tag. The client's own
// input filter drops `<` and `>` outright rather than escaping them, and this
// does the same so relayed text looks the way the sender's client would have
// shown it. Also caps the body at what the wire can carry, cutting on a rune
// boundary so a truncated message cannot end mid-character (which the client
// would render as mojibake).
func sanitizeChatText(s string, maxBytes int) string {
	if strings.ContainsAny(s, "<>") {
		s = strings.Map(func(r rune) rune {
			if r == '<' || r == '>' {
				return -1
			}
			return r
		}, s)
	}
	if len(s) > maxBytes {
		cut := maxBytes
		for cut > 0 && !utf8.RuneStart(s[cut]) {
			cut--
		}
		s = s[:cut]
	}
	return s
}

// ignoresCoach reports whether c has senderID on its ignore list.
//
// Reads the in-memory edge list, which socialEdit keeps in step with the database
// precisely so this is cheap enough to run per recipient on a broadcast.
func ignoresCoach(c *domain.Coach, senderID uint) bool {
	if c == nil {
		return false
	}
	for _, ig := range c.Ignored {
		if ig.IgnoredID == senderID {
			return true
		}
	}
	return false
}

// deliverChat sends a prepared frame to each recipient that has not ignored the
// sender. Returns how many actually received it.
func deliverChat(frame []byte, senderID uint, to []*Session) int {
	n := 0
	for _, other := range to {
		if other == nil || ignoresCoach(other.Coach, senderID) {
			continue
		}
		if err := other.Send(frame); err == nil {
			n++
		}
	}
	return n
}

// fightAllies returns the sessions of coaches on the SAME fight team as coachID,
// excluding the coach itself. Empty when the coach is not fighting, and empty in
// every 1v1 fight — the current Fight model carries one coach per team, so allies
// only appear once 2v2 lands (roadmap item 30). Resolving the audience here, from
// server state, is what keeps /p from becoming a client-addressable DM channel.
func (d *Deps) fightAllies(coachID uint) []*Session {
	if d == nil || d.Fights == nil {
		return nil
	}
	f := d.Fights.ByCoach(coachID)
	if f == nil {
		return nil
	}
	var out []*Session
	for _, t := range f.Teams {
		if t == nil || t.Coach == nil || t.Coach.ID != coachID {
			continue
		}
		// This is the sender's team. Everyone else on it is an ally.
		for _, ff := range t.Fighters {
			if ff == nil || ff.CoachID == coachID || ff.CoachID == 0 {
				continue
			}
			if sess := d.sessionForCoach(ff.CoachID); sess != nil {
				out = appendSessionOnce(out, sess)
			}
		}
	}
	return out
}

// guildSessions returns the online sessions of every member of guildID except
// excludeCoach. Guilds are not modelled yet (roadmap item 31), so this is always
// empty; it exists so the clan-chat path is complete and validated rather than
// half-written, and so the one place that needs a membership lookup is obvious.
func (d *Deps) guildSessions(guildID int64, excludeCoach uint) []*Session {
	return nil
}

// coachGuildID returns the coach's guild, if any. Always (0, false) until guild
// membership exists — see guildSessions.
func coachGuildID(c *domain.Coach) (int64, bool) {
	return 0, false
}

// appendSessionOnce keeps the ally list free of duplicates (several fighters can
// belong to the same coach).
func appendSessionOnce(list []*Session, s *Session) []*Session {
	for _, e := range list {
		if e == s {
			return list
		}
	}
	return append(list, s)
}

// sessionForCoach finds the online session of a coach. SessionRegistry is keyed
// by ACCOUNT, so this scans; the caller is a chat message to a handful of allies,
// not a hot path.
func (d *Deps) sessionForCoach(coachID uint) *Session {
	if d == nil || d.Sessions == nil {
		return nil
	}
	var found *Session
	d.Sessions.Each(func(s *Session) {
		if found == nil && s.Coach != nil && s.Coach.ID == coachID {
			found = s
		}
	})
	return found
}
