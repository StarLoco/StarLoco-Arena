package dispatch

import (
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/protocol"
)

// serializePlayerStatisticsReport serializes the PLAYER_STATISTICS_REPORT
// body from a coach's real, persisted statistics. The entry IDs and types
// match the client's PlayerStatisticsReport.updateFromReport mapping
// (com/ankamagames/dofusarena/common/game/statistics/
// PlayerStatisticsReport.java): 1=total play time (long, s), 2=total fight
// time (long, s), 3=total fights, 4=wins, 5=losses, 6=strength (also drives
// the window's Level/Rank fallback), 7=consecutive wins.
//
// NOTE: the legacy Java version declares nbEntries=7 but then writes 8
// entry blocks (an off-by-one between the declared count and the actual
// payload). This port writes exactly 7 entries to match the declared
// count, dropping the 8th ("consecutive losses") block the UI never shows,
// since a mismatched count risks a client-side parse desync more than a
// missing cosmetic stat does. This function writes only the report body
// (model id, report id, entries) -- callers frame it (with a size prefix
// for the standalone packet, or embedded in END_FIGHT's per-player blob).
func serializePlayerStatisticsReport(w *protocol.Writer, coach *domain.Coach) {
	w.PutUint16(1) // model id
	w.PutInt64(1)  // report id
	w.PutUint16(7) // entry count

	longEntry := func(id uint16, v int64) { w.PutUint16(id).PutByte(2).PutInt64(v) }
	intEntry := func(id uint16, v int32) { w.PutUint16(id).PutByte(1).PutInt32(v) }

	longEntry(1, coach.TotalPlayTimeSecs) // total time spent (s)
	longEntry(2, coach.TimeInFightSecs)   // total time spent in fight (s)
	intEntry(3, coach.StatFights)         // total fights
	intEntry(4, coach.StatWins)           // total wins
	intEntry(5, coach.StatLosses)         // total losses
	intEntry(6, coach.Strength)           // strength
	intEntry(7, coach.ConsecutiveWins)    // consecutive wins
}

// buildPlayerStatisticsReport frames a full PLAYER_STATISTICS_REPORT packet
// (opcode 2400) for one coach: a leading uint16 payload-size (size-2)
// prefix followed by the serialized report body.
func buildPlayerStatisticsReport(coach *domain.Coach) protocol.OutboundFrame {
	body := protocol.NewWriter(78)
	serializePlayerStatisticsReport(body, coach)

	w := protocol.NewWriter(2 + body.Len())
	w.PutUint16(uint16(body.Len()))
	w.PutBytes(body.Bytes())

	return protocol.OutboundFrame{Opcode: protocol.SendPlayerStatisticsReport, Payload: w.Bytes()}
}
