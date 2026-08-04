package handshake

import (
	"fmt"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// StatisticUpdate is a decoded opcode-22003 payload (the client's `nq`
// message). The client sends it to report progress on an achievement/statistic
// counter — for example when a coach is created or chats with a breedmaster.
// StatID indexes the client's statistic enum (see client `or_0`).
type StatisticUpdate struct {
	StatID int16 // statistic/achievement id (client `or_0` enum ordinal value)
	Flag   bool  // client-side flag (observed as true); semantics unconfirmed
	Value  int16 // counter value/delta the client reports
}

// statisticUpdateLen is the fixed wire size of a 22003 payload:
// i16 statId + i8 flag + i16 value.
const statisticUpdateLen = 5

// DecodeStatisticUpdate parses an opcode-22003 payload.
//
//	Payload: [i16 statId][i8 flag][i16 value]
func DecodeStatisticUpdate(payload []byte) (StatisticUpdate, error) {
	if len(payload) != statisticUpdateLen {
		return StatisticUpdate{}, fmt.Errorf("statistic update: payload %d bytes, want %d",
			len(payload), statisticUpdateLen)
	}
	r := protocol.NewReader(payload)
	statID, err := r.U16()
	if err != nil {
		return StatisticUpdate{}, err
	}
	flag, err := r.U8()
	if err != nil {
		return StatisticUpdate{}, err
	}
	value, err := r.U16()
	if err != nil {
		return StatisticUpdate{}, err
	}
	return StatisticUpdate{
		StatID: int16(statID),
		Flag:   flag != 0,
		Value:  int16(value),
	}, nil
}
