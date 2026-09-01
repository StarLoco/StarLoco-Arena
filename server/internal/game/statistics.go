package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Player statistics report (2401).
//
// The payload is NOT Java-serialized, despite the decompiled type name: it is a
// plain typed key-value map that the client's report MODEL parses.
//
//	[i16 blobLen]
//	[i16 modelId][i64 ownerId][i16 count]
//	  count x { [i16 statId][i8 type][value] }
//
// type tags come from `dr_2`: 1 = i32 (4B), 2 = i64 (8B), 3 = float32 (4B).
//
// The stat ids are exact, not guessed - they come from the one UNOBFUSCATED
// class in the client, `PlayerStatisticsReport`, whose getters read them
// literally (e.g. `dJ() { return this.V((short)4); }`).
const (
	statTotalPlayTime    int16 = 1 // long, seconds (the UI multiplies by 1000)
	statTotalFightsTime  int16 = 2 // long, seconds
	statTotalFights      int16 = 3 // int
	statTotalFightsWon   int16 = 4 // int
	statTotalFightsLost  int16 = 5 // int
	statUnknown6         int16 = 6 // int, `dN()` - no bound UI property found
	statConsecutiveWins  int16 = 7 // int
	statConsecutiveLoses int16 = 8 // int
)

// Value type tags (client `dr_2`).
const (
	statTypeInt32   uint8 = 1
	statTypeInt64   uint8 = 2
	statTypeFloat32 uint8 = 3
)

// statsReportModelID is the report model the client must already know: the
// models are loaded from client content by id, and `arq_0.aa` REFUSES a payload
// whose model it cannot find, logging "le modele n'est pas reconnu : modelId=N".
// That refusal is what makes this value verifiable against a live client rather
// than guessed.
const statsReportModelID int16 = 1

// buildPlayerStatistics builds 2401 for a coach.
//
// The map is written SPARSELY on purpose: only stats the server actually tracks
// are included. Absent ids read back as zero on the client, which is honest -
// total play time and total fight time are not measured here, and sending 0 for
// them would be indistinguishable from "you have played for zero seconds".
func buildPlayerStatistics(c *domain.Coach) ([]byte, error) {
	type entry struct {
		id    int16
		typ   uint8
		asI32 int32
	}
	entries := []entry{
		{statTotalFights, statTypeInt32, c.StatFights},
		{statTotalFightsWon, statTypeInt32, c.StatWins},
		{statTotalFightsLost, statTypeInt32, c.StatLosses},
		{statConsecutiveWins, statTypeInt32, c.ConsecutiveWins},
		{statConsecutiveLoses, statTypeInt32, c.ConsecutiveLosses},
	}

	blob := protocol.NewWriter()
	blob.U16(uint16(statsReportModelID))
	blob.I64(int64(c.ID))
	blob.U16(uint16(len(entries)))
	for _, e := range entries {
		blob.U16(uint16(e.id))
		blob.U8(e.typ)
		blob.I32(e.asI32)
	}
	b := blob.Bytes()

	w := protocol.NewWriter()
	w.U16(uint16(len(b)))
	w.Raw(b)
	return protocol.EncodeS2C(protocol.OpPlayerStatistics, w.Bytes())
}

// sendPlayerStatistics pushes the coach's statistics report.
func (s *Session) sendPlayerStatistics() error {
	if s.Coach == nil {
		return nil
	}
	frame, err := buildPlayerStatistics(s.Coach)
	if err != nil {
		return err
	}
	return s.Send(frame)
}
