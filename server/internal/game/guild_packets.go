package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Guild S2C builders.
//
// Every string here is UTF-8 (StringU8UTF8), because the whole guild family goes
// through the client's `aey_0`, which names the charset explicitly instead of
// taking the platform default the coach/fighter name codecs use.

// buildGuildResult builds GUILD_RESULT (504): [u8 type][i32 resultCode].
//
// This is the client's only feedback channel for a guild operation, and the code
// selects the message it shows AND the state it clears: 400/401/402 all drop the
// local membership (`lh_1.java:44-90`), so sending the wrong one on a successful
// create would silently log the player out of the clan it just made.
func buildGuildResult(guildType uint8, code int32) ([]byte, error) {
	w := protocol.NewWriter().U8(guildType).I32(code)
	return protocol.EncodeS2C(protocol.OpGuildResult, w.Bytes())
}

// buildGuildRecord builds GUILD_INFO (510): [u16 len][record], where the record
// is what `KI.b` reads:
//
//	i64 guildId, str:u8 name, i16, i16, i32, i32, i16 demonId, i32,
//	u8 rankCount, rankCount x { i16 recLen, [i16 level][i32 rights][str:u8 name] }
//
// The four unused scalars are read and discarded by the client; they are written
// as zero rather than skipped because the reader is positional.
func buildGuildRecord(g *domain.Guild, ranks []domain.GuildRank) ([]byte, error) {
	rec := protocol.NewWriter().
		I64(int64(g.ID)).
		StringU8UTF8(g.Name).
		U16(0).
		U16(0).
		I32(0).
		I32(0).
		U16(uint16(g.DemonID)).
		I32(0).
		U8(uint8(len(ranks)))
	for _, rk := range ranks {
		inner := protocol.NewWriter().
			U16(uint16(rk.Level)).
			I32(rk.Rights).
			StringU8UTF8(rk.Name).
			Bytes()
		rec.U16(uint16(len(inner))).Raw(inner)
	}
	body := rec.Bytes()
	w := protocol.NewWriter().U16(uint16(len(body))).Raw(body)
	return protocol.EncodeS2C(protocol.OpGuildRecord, w.Bytes())
}

// guildMemberPart builds `ca_0` part 0 (`uy_2`), a member row:
// i64 memberId, i32 rights, i16 rankLevel, str rankName, str coachName, u8 connected.
func guildMemberPart(coachID int64, rights int32, rankLevel int16, rankName, coachName string, connected bool) []byte {
	w := protocol.NewWriter().
		I64(coachID).
		I32(rights).
		U16(uint16(rankLevel)).
		StringU8UTF8(rankName).
		StringU8UTF8(coachName)
	if connected {
		w.U8(1)
	} else {
		w.U8(0)
	}
	return w.Bytes()
}

// guildTagPart builds `ca_0` part 1 (`ut_2`), another player's clan tag:
// str guildName, i64 playerId, i16, i16, i32, i32, i16 demonId.
//
// The i64 is the PLAYER id, not the guild id - `lh_1.java:153` reads it back with
// `Ke()` to find the actor to label.
func guildTagPart(guildName string, playerID int64, demonID int16) []byte {
	return protocol.NewWriter().
		StringU8UTF8(guildName).
		I64(playerID).
		U16(0).
		U16(0).
		I32(0).
		I32(0).
		U16(uint16(demonID)).
		Bytes()
}

// buildGuildMemberList builds the `kf_1` container used by 512, 552 and 554:
// [i32 count] count x { [i32 len][ca_0 part-table blob] }.
//
// partIdx selects which `ca_0` part each entry carries, and it must match the
// opcode: 512 -> 0 (member rows), 552 -> 2 (my own membership), 554 -> 1 (tags).
// The client keys entirely off the part index inside the blob, so a mismatched
// pair decodes into the wrong fields rather than failing.
func buildGuildMemberList(opcode uint16, partIdx uint8, payloads [][]byte) ([]byte, error) {
	w := protocol.NewWriter().I32(int32(len(payloads)))
	for _, p := range payloads {
		blob := protocol.PartTable(protocol.Part{Idx: partIdx, Data: p})
		w.I32(int32(len(blob))).Raw(blob)
	}
	return protocol.EncodeS2C(opcode, w.Bytes())
}

// buildGuildInvitation builds GUILD_INVITATION (502):
// [u8 type][u8 len][inviterName][u8 len][guildName].
//
// The client answers with 503 echoing both names, which is the only key it gives
// back - so the pending invitation has to be findable by (target, inviterName,
// guildName) server-side.
func buildGuildInvitation(guildType uint8, inviterName, guildName string) ([]byte, error) {
	w := protocol.NewWriter().
		U8(guildType).
		StringU8UTF8(inviterName).
		StringU8UTF8(guildName)
	return protocol.EncodeS2C(protocol.OpGuildInvitation, w.Bytes())
}

// buildGuildMemberGone builds 556: [i64 playerId] - that player left or was
// removed. On itself the client clears its own membership.
func buildGuildMemberGone(playerID int64) ([]byte, error) {
	w := protocol.NewWriter().I64(playerID)
	return protocol.EncodeS2C(protocol.OpGuildMemberGone, w.Bytes())
}

// buildGuildCreatedFeed builds 558: [u8 len][coachName][u8 len][guildName],
// rendered as the `infos.guildCreated` line.
func buildGuildCreatedFeed(coachName, guildName string) ([]byte, error) {
	w := protocol.NewWriter().
		StringU8UTF8(coachName).
		StringU8UTF8(guildName)
	return protocol.EncodeS2C(protocol.OpGuildCreatedFeed, w.Bytes())
}

// buildGuildMemberFeed builds 560: [u8 len][coachName][u8 removed] - the
// "X joined / X was thrown out" clan feed line.
func buildGuildMemberFeed(coachName string, removed bool) ([]byte, error) {
	w := protocol.NewWriter().StringU8UTF8(coachName)
	if removed {
		w.U8(1)
	} else {
		w.U8(0)
	}
	return protocol.EncodeS2C(protocol.OpGuildMemberFeed, w.Bytes())
}
