package handshake

import (
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// GuildMembership is what the coach blob needs to describe the coach's own clan.
// It is the server-side shape of the client's `ca_0` part 2 (`uu_2`).
type GuildMembership struct {
	GuildID   int64
	GuildName string
	// Rights is the rank's bitmask (aen_1): bit0 leader/all, bit1 invite,
	// bit2 remove, bit3 promote, bit4 demote.
	Rights    int32
	RankLevel int16
	RankName  string
	// DemonID is the Demon des Heures the clan is affiliated to (0 = none). The
	// client appends it to the displayed clan name and gates the clan island on
	// it.
	DemonID int16
}

// buildGuildBlob builds the coach blob's 0x20 section: [u16 len][part table].
//
// The container is the same part table as the running-effect blob (`aJj.ad`);
// `aez_0.W` reads the length, then hands the bytes to `ca_0.ad`. Only part 2
// (`uu_2`) is meaningful for "my own membership" - part 0 is a member row and
// part 1 another player's tag, both sent on their own opcodes.
//
// Field order is `uu_2.f` read for read:
//
//	i64 guildId, i32 rights, i16 rankLevel, str rankName,
//	i16 (unused), str guildName, i16 (unused), i32 (unused), i32 (unused),
//	i16 demonId
//
// Strings are UTF-8 (`aey_0.V`), not the cp1252 the coach name uses - see
// StringU8UTF8.
//
// A nil membership writes a zero length, which is exactly what the server sent
// unconditionally before guilds existed, so a coach with no clan is byte-identical
// to the old behaviour.
func buildGuildBlob(g *GuildMembership) []byte {
	if g == nil {
		return protocol.NewWriter().U16(0).Bytes()
	}
	part2 := protocol.NewWriter().
		I64(g.GuildID).
		I32(g.Rights).
		U16(uint16(g.RankLevel)).
		StringU8UTF8(g.RankName).
		U16(0).
		StringU8UTF8(g.GuildName).
		U16(0).
		I32(0).
		I32(0).
		U16(uint16(g.DemonID)).
		Bytes()
	blob := protocol.PartTable(protocol.Part{Idx: 2, Data: part2})
	return protocol.NewWriter().U16(uint16(len(blob))).Raw(blob).Bytes()
}
