package game

import (
	"strings"
	"unicode/utf8"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

func registerChatHandlers(r *Router, d *Deps) {
	r.Register(protocol.OpUserVicinityContentMessage, handleVicinityMessage)
	r.Register(protocol.OpUserPrivateContentMessage, handlePrivateMessageRecv)
	r.Register(protocol.OpUserChannelContentMessage, handleChannelMessage)
	r.Register(protocol.OpUserTradeContentMessage, handleTradeMessage)
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
	// Deliver ONLY to other coaches within the AoI radius. The sender must NOT
	// be echoed: the client already displays its own outgoing line locally, so
	// a server echo would show the sender's message twice.
	for _, other := range s.deps.World.SessionsNear(s.Coach.PosX, s.Coach.PosY, s.Coach.ID) {
		_ = other.Send(frame)
	}
	s.log.Debug("vicinity chat", "from", s.Coach.Name, "msg", msg)
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

	frame, err := buildTradeMessage(s.Coach.Name, int64(s.Coach.ID), msg)
	if err != nil {
		return err
	}
	// Every other online overworld coach. SessionsWithout also drops coaches who
	// are in a fight, which is the same audience the other broadcast paths use —
	// consistency matters more here than a judgement call about whether a fighting
	// player wants to read trade spam. The sender is not echoed: the client
	// displays its own outgoing line locally, so an echo would duplicate it.
	for _, other := range s.deps.World.SessionsWithout(s.Coach.ID) {
		_ = other.Send(frame)
	}
	s.log.Debug("trade chat", "from", s.Coach.Name, "msg", msg)
	return nil
}

// buildTradeMessage builds TradeContent (3168). The client's `ayy` is a
// field-for-field copy of the vicinity reader `ck_0`, so this is
// buildVicinityMessage with a different opcode.
func buildTradeMessage(name string, id int64, msg string) ([]byte, error) {
	w := protocol.NewWriter().
		StringU8(name).
		I64(id).
		StringU16(msg)
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
		StringU8(name).
		I64(id).
		StringU16(msg)
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
		StringU8(name).
		I64(id).
		StringU8(msg)
	return protocol.EncodeS2C(protocol.OpPrivateContentMessage, w.Bytes())
}
