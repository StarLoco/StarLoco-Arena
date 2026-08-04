package handshake

import (
	"fmt"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// PathStep is one node of a movement path: world X/Y and altitude.
type PathStep struct {
	X int32
	Y int32
	Z int16
}

// stepBytes is the wire size of one path step: i32 x + i32 y + i16 z.
const stepBytes = 10

// DecodeCoachMovementRequest parses an opcode-4501 payload
// (CoachActorMovementRequest). It is a bare sequence of path steps with no
// count prefix — the step count is derived from the payload length.
//
//	Payload: n × [i32 x][i32 y][i16 z]
func DecodeCoachMovementRequest(payload []byte) ([]PathStep, error) {
	if len(payload)%stepBytes != 0 {
		return nil, fmt.Errorf("movement: payload %d not a multiple of %d",
			len(payload), stepBytes)
	}
	r := protocol.NewReader(payload)
	steps := make([]PathStep, 0, len(payload)/stepBytes)
	for r.Remaining() > 0 {
		x, err := r.I32()
		if err != nil {
			return nil, err
		}
		y, err := r.I32()
		if err != nil {
			return nil, err
		}
		z, err := r.U16()
		if err != nil {
			return nil, err
		}
		steps = append(steps, PathStep{X: x, Y: y, Z: int16(z)})
	}
	return steps, nil
}

// EncodeActorMovement builds an opcode-4500 S2C frame (ActorMovement): the
// actor id followed by the path. The client animates the actor along it.
//
//	Payload: [i64 actorId] then n × [i32 x][i32 y][i16 z]
func EncodeActorMovement(actorID int64, steps []PathStep) ([]byte, error) {
	w := protocol.NewWriter().I64(actorID)
	for _, s := range steps {
		w.I32(s.X).I32(s.Y).U16(uint16(s.Z))
	}
	return protocol.EncodeS2C(protocol.OpActorMovement, w.Bytes())
}
