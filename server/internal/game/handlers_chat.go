package game

import (
	"strings"
	"time"
	"unicode/utf8"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerChatHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpUserVicinityContentMessage, handleVicinityMessage)
	r.Register(protocol.OpUserPrivateContentMessage, handlePrivateMessageRecv)
	r.Register(protocol.OpUserChannelContentMessage, handleChannelMessage)
	r.Register(protocol.OpUserTradeContentMessage, handleTradeMessage)
	r.Register(protocol.OpUserGroupContentMessage, handleGroupMessage)
	r.Register(protocol.OpUserClanContentMessage, handleClanMessage)
}

// handlePrivateMessageRecv routes a whisper (3155) to the named target, sending
// them PrivateContent (3154). If the target is offline, replies UserNotFound.
func handlePrivateMessageRecv(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	target, err := r.StringU8()
	if err != nil {
		return err
	}
	msg, err := r.StringU8()
	if err != nil {
		return err
	}
	online := s.deps.World.GetByName(target)
	if online == nil {
		return s.sendUserNotFound(target)
	}
	// THE IGNORE LIST MUST BE ENFORCED HERE, and this is the one pipe where the
	// server is the only line of defence. The client filters General, Trade, Clan
	// and Group by sender name, but `om_0` case 3154 has no check at all — and
	// receiving a whisper additionally force-maximises the chat panel and force-
	// opens chatDialog. Relaying one from an ignored sender therefore lets that
	// sender pop the recipient's UI at will.
	//
	// Answering with UserNotFound rather than silence is deliberate: it is what the
	// sender already sees for an offline target, so it reveals nothing about having
	// been ignored.
	if ignoresCoach(online.Session.Coach, s.Coach.ID) {
		s.log.Debug("whisper to a coach who ignores the sender — dropped",
			"from", s.Coach.Name, "to", target)
		return s.sendUserNotFound(target)
	}
	frame, err := buildPrivateMessage(s.Coach.Name, int64(s.Coach.ID), msg)
	if err != nil {
		return err
	}
	// Deliver ONLY to the target. The sender is not echoed: the client shows the
	// sent whisper locally, so a server echo would duplicate it (matches the
	// reference server, which sends the private line only to the target).
	return online.Session.Send(frame)
}

// handleVicinityMessage receives a chat line (3153) and broadcasts it to all
// overworld coaches as VicinityContent (3152). Lines starting with '/' are
// treated as admin/GM commands and not broadcast.
func handleVicinityMessage(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	msgLen, err := r.U16()
	if err != nil {
		return err
	}
	msg, err := r.String(int(msgLen))
	if err != nil {
		return err
	}
	msg = strings.TrimSpace(msg)
	if msg == "" {
		return nil
	}

	if strings.HasPrefix(msg, "/") {
		return handleGMCommand(s, msg)
	}

	frame, err := buildVicinityMessage(s.Coach.Name, int64(s.Coach.ID), msg)
	if err != nil {
		return err
	}
	// Deliver ONLY to other coaches within the AoI radius, minus anyone who has
	// the sender ignored. The sender must NOT be echoed: the client already
	// displays its own outgoing line locally, so a server echo would show the
	// sender's message twice.
	n := deliverChat(frame, s.Coach.ID, s.deps.World.SessionsNear(s.Coach.PosX, s.Coach.PosY, s.Coach.ID))
	s.log.Debug("vicinity chat", "from", s.Coach.Name, "to", n, "msg", msg)
	return nil
}

// handleChannelMessage receives a channel chat line (3151) and delivers it to
// the channel audience as ChannelContent (3140). C2S layout (verified vs client
// acS): [u8 channelLen][channel][u8 msgLen][message].
//
// Channel scoping: we currently model a single global audience (all online,
// non-in-fight coaches), preserving the channel key so the client routes the
// line to the right tab. Guild/team-scoped channels layer on once those
// subsystems exist. Lines starting with '/' are treated as GM commands.
func handleChannelMessage(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	channel, err := r.StringU8()
	if err != nil {
		return err
	}
	msg, err := r.StringU8()
	if err != nil {
		return err
	}
	msg = strings.TrimSpace(msg)
	if msg == "" {
		return nil
	}
	if strings.HasPrefix(msg, "/") {
		return handleGMCommand(s, msg)
	}
	// The client's 3140 decoder reads each field length as a single signed
	// byte, so clamp to 127 to avoid a wrapped length prefix corrupting the
	// frame.
	if len(msg) > 127 {
		msg = msg[:127]
	}

	frame, err := buildChannelMessage(channel, s.Coach.Name, msg)
	if err != nil {
		return err
	}
	// Fan out to every OTHER online coach. The sender is not echoed: the client
	// displays its own outgoing line locally, so echoing would duplicate it.
	for _, other := range s.deps.World.SessionsWithout(s.Coach.ID) {
		_ = other.Send(frame)
	}
	s.log.Debug("channel chat", "from", s.Coach.Name, "channel", channel, "msg", msg)
	return nil
}

// buildChannelMessage builds ChannelContent (3140):
// [u8 channelLen][channel][u8 senderLen][sender][u8 msgLen][message].
func buildChannelMessage(channel, sender, msg string) ([]byte, error) {
	w := protocol.NewWriter().
		StringU8(channel).
		StringU8(sender).
		StringU8(msg)
	return protocol.EncodeS2C(protocol.OpChannelContentMessage, w.Bytes())
}

// handleTradeMessage receives a Trade chat line (3159, the client's "/t" pipe)
// and delivers it to every other online coach as TradeContent (3168).
//
// Trade is deliberately GLOBAL — that is what the pipe is for, and unlike the
// other two scoped pipes it needs no subsystem that does not exist yet:
//
//   - /c Clan (3199) is guild-scoped, and the client self-gates on having a guild
//     (`GuildContentCommand` does nothing when the coach has no `ca_0`), so it
//     cannot even be exercised until guilds exist. Confirmed live: typing /c
//     produces no packet at all.
//   - /p Group (3161) targets the ally coach on your side of a live fight, read
//     out of CREATE_FIGHT's coach list. In a 1v1-only server there is no ally, so
//     its target id is 0 or stale.
//
// Until this existed the server logged "unhandled opcode 3159" and dropped the
// line, while the client rendered the player's own message locally — so it looked
// sent and simply never arrived (B-103).
func handleTradeMessage(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	msgLen, err := r.U16()
	if err != nil {
		return err
	}
	msg, err := r.String(int(msgLen))
	if err != nil {
		return err
	}
	msg = strings.TrimSpace(msg)
	if msg == "" {
		return nil
	}
	// A leading '/' is a GM command here too: the pipe is just which tab the text
	// was typed into, and a player who selects Trade and types /WORLD means it.
	if strings.HasPrefix(msg, "/") {
		return handleGMCommand(s, msg)
	}

	// Trade is the one pipe the client itself throttles (30 s). Mirror it, plus
	// the global anti-repeat, so a modified client cannot spam what a stock one
	// physically cannot. A legitimate client never reaches either check.
	now := time.Now()
	if !s.chat.allowTrade(now) {
		s.log.Debug("trade chat throttled", "coach", s.Coach.Name)
		return nil
	}
	if !s.chat.allowRepeat(msg, now) {
		s.log.Debug("trade chat repeat suppressed", "coach", s.Coach.Name)
		return nil
	}

	frame, err := buildTradeMessage(s.Coach.Name, int64(s.Coach.ID), msg)
	if err != nil {
		return err
	}
	// Every other online overworld coach. SessionsWithout also drops coaches who
	// are in a fight, which is the same audience the other broadcast paths use —
	// consistency matters more here than a judgement call about whether a fighting
	// player wants to read trade spam. The sender is not echoed: the client
	// displays its own outgoing line locally, so an echo would duplicate it.
	n := deliverChat(frame, s.Coach.ID, s.deps.World.SessionsWithout(s.Coach.ID))
	s.log.Debug("trade chat", "from", s.Coach.Name, "to", n, "msg", msg)
	return nil
}

// handleGroupMessage receives a Group chat line (3161, the client's "/p") and
// delivers it to the sender's fight allies as GroupContent (3170).
//
// C2S layout is [i64 targetCoachId][u16 len][message], and THE TARGET ID IS NOT
// TRUSTED. It is client-supplied, and taking it at face value would turn /p into
// an unfilterable direct-message channel: a modified client could put any coach
// id there and reach a player who has it on their ignore list, or who is not in
// the fight at all. The audience is resolved from the sender's own fight instead.
//
// On a 1v1 server that audience is always empty, and the id always arrives as 0 —
// the client only sets it from CREATE_FIGHT's coach list when a same-team coach
// other than itself exists, and it never clears it, so it stays 0 forever and the
// client still sends /p from the overworld. Handling it correctly now means 2v2
// (roadmap item 30) gets working group chat for free, and means the opcode stops
// being an unhandled hole in the meantime.
func handleGroupMessage(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	if _, err := r.I64(); err != nil { // advisory target id — deliberately ignored
		return err
	}
	msgLen, err := r.U16()
	if err != nil {
		return err
	}
	msg, err := r.String(int(msgLen))
	if err != nil {
		return err
	}
	msg = strings.TrimSpace(msg)
	if msg == "" {
		return nil
	}
	if strings.HasPrefix(msg, "/") {
		return handleGMCommand(s, msg)
	}
	if !s.chat.allowRepeat(msg, time.Now()) {
		return nil
	}

	allies := s.deps.fightAllies(s.Coach.ID)
	if len(allies) == 0 {
		// No teammate to talk to. Silent, exactly as the client's own /p is when
		// it has no target: this is the normal case in 1v1, not an error.
		s.log.Debug("group chat with no allies", "coach", s.Coach.Name)
		return nil
	}
	frame, err := buildGroupMessage(s.Coach.Name, int64(s.Coach.ID), msg)
	if err != nil {
		return err
	}
	n := deliverChat(frame, s.Coach.ID, allies)
	s.log.Debug("group chat", "from", s.Coach.Name, "to", n, "msg", msg)
	return nil
}

// handleClanMessage receives a Clan chat line (3199, the client's "/c") and
// delivers it to the sender's guild as ClanContent (3198).
//
// C2S layout is [u16 len][message][i64 guildId] — note the id comes AFTER the
// body, unlike every other chat message. As with /p, THE ID IS CLIENT-SUPPLIED
// and is re-validated against the sender's own guild rather than trusted;
// otherwise a modified client could broadcast into any guild it named.
//
// No coach has a guild yet (guilds are roadmap item 31), so this currently always
// resolves to nobody. That is not a stub: a stock client cannot even send this
// message without a guild — `GuildContentCommand` self-gates on the coach having
// a `ca_0`, which is only populated from the 0x20 blob of the coach record in
// 2052, which we send empty. Confirmed live: /c emits no packet at all. Handling
// it means the wire side is done and validated when guild membership lands.
func handleClanMessage(s *Session, f *protocol.C2SFrame) error {
	if s.Coach == nil {
		return nil
	}
	r := protocol.NewReader(f.Payload)
	msgLen, err := r.U16()
	if err != nil {
		return err
	}
	msg, err := r.String(int(msgLen))
	if err != nil {
		return err
	}
	claimedGuild, err := r.I64()
	if err != nil {
		return err
	}
	msg = strings.TrimSpace(msg)
	if msg == "" {
		return nil
	}
	if strings.HasPrefix(msg, "/") {
		return handleGMCommand(s, msg)
	}

	guildID, ok := coachGuildID(s.Coach)
	if !ok {
		s.log.Debug("clan chat from a coach with no guild",
			"coach", s.Coach.Name, "claimed", claimedGuild)
		return nil
	}
	if claimedGuild != guildID {
		s.log.Warn("clan chat for a guild the sender is not in — dropped",
			"coach", s.Coach.Name, "claimed", claimedGuild, "actual", guildID)
		return nil
	}
	if !s.chat.allowRepeat(msg, time.Now()) {
		return nil
	}
	frame, err := buildClanMessage(s.Coach.Name, int64(s.Coach.ID), msg)
	if err != nil {
		return err
	}
	n := deliverChat(frame, s.Coach.ID, s.deps.guildSessions(guildID, s.Coach.ID))
	s.log.Debug("clan chat", "from", s.Coach.Name, "to", n, "msg", msg)
	return nil
}

// buildGroupMessage builds GroupContent (3170) — the vicinity shape again.
func buildGroupMessage(name string, id int64, msg string) ([]byte, error) {
	w := protocol.NewWriter().
		StringU8(sanitizeChatText(name, maxChatName)).
		I64(id).
		StringU16(sanitizeChatText(msg, maxChatBody))
	return protocol.EncodeS2C(protocol.OpGroupContentMessage, w.Bytes())
}

// buildClanMessage builds ClanContent (3198) — the vicinity shape again.
func buildClanMessage(name string, id int64, msg string) ([]byte, error) {
	w := protocol.NewWriter().
		StringU8(sanitizeChatText(name, maxChatName)).
		I64(id).
		StringU16(sanitizeChatText(msg, maxChatBody))
	return protocol.EncodeS2C(protocol.OpClanContentMessage, w.Bytes())
}

// buildTradeMessage builds TradeContent (3168). The client's `ayy` is a
// field-for-field copy of the vicinity reader `ck_0`, so this is
// buildVicinityMessage with a different opcode.
func buildTradeMessage(name string, id int64, msg string) ([]byte, error) {
	w := protocol.NewWriter().
		StringU8(sanitizeChatText(name, maxChatName)).
		I64(id).
		StringU16(sanitizeChatText(msg, maxChatBody))
	return protocol.EncodeS2C(protocol.OpTradeContentMessage, w.Bytes())
}

// buildVicinityMessage builds VicinityContent (3152):
// [u8 nameLen][name][i64 senderId][u16 msgLen][message].
func buildVicinityMessage(name string, id int64, msg string) ([]byte, error) {
	// StringU16 rather than a hand-rolled length+Raw: the helper encodes the text
	// to the wire charset (cp1252) and counts the ENCODED bytes. Writing raw UTF-8
	// here made every accented chat message arrive mangled and, worse, made the
	// u16 length disagree with the payload for any non-ASCII character.
	w := protocol.NewWriter().
		StringU8(sanitizeChatText(name, maxChatName)).
		I64(id).
		StringU16(sanitizeChatText(msg, maxChatBody))
	return protocol.EncodeS2C(protocol.OpVicinityContentMessage, w.Bytes())
}

// buildPrivateMessage builds PrivateContent (3154):
// [u8 nameLen][name][i64 senderId][u8 msgLen][message].
//
// NOTE the body length is a single BYTE here, unlike the otherwise-identical
// vicinity message (3152), which uses a short. The client's ais_2 reads it as
// `byteBuffer.get() & 0xFF`; we used to write a u16, so for any message under 256
// bytes the client read the high byte — zero — and rendered an EMPTY line. Every
// whisper and every GM reply showed as "de Server :" with no text (B-051).
//
// The byte prefix caps a message at 255 bytes, so an over-long one is truncated on
// a rune boundary rather than being cut mid-character (which would render as
// mojibake, since the client decodes the body as UTF-8).
func buildPrivateMessage(name string, id int64, msg string) ([]byte, error) {
	const maxPrivateMsg = 255
	if len(msg) > maxPrivateMsg {
		cut := maxPrivateMsg
		for cut > 0 && !utf8.RuneStart(msg[cut]) {
			cut--
		}
		msg = msg[:cut]
	}
	w := protocol.NewWriter().
		StringU8(sanitizeChatText(name, maxChatName)).
		I64(id).
		StringU8(sanitizeChatText(msg, maxPrivateMsg))
	return protocol.EncodeS2C(protocol.OpPrivateContentMessage, w.Bytes())
}
