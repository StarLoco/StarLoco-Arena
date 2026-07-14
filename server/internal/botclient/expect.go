package botclient

import (
	"fmt"
	"time"

	"github.com/dofusarena/go-server/internal/protocol"
)

// DefaultRecvTimeout bounds how long Expect/DrainUntil wait for each frame.
// Generous enough to absorb server-side phase clocks and scheduling jitter
// under heavy swarm load, short enough that a genuinely stuck bot is
// reported rather than hanging the run forever.
const DefaultRecvTimeout = 30 * time.Second

// maxBroadcastSkip bounds how many interleaved presence/world broadcast
// frames Expect/DrainUntil will tolerate skipping before giving up. At
// swarm scale (hundreds-to-thousands of concurrent logins) every login/
// logout/move/chat fans out to EVERY online coach, so a single connection
// can accumulate a large burst of unrelated broadcast noise while waiting
// for its own next meaningful frame -- this is expected server behavior at
// scale, not a bug. Matches the tolerance already chosen empirically in
// cmd/loadtest.
const maxBroadcastSkip = 8192

// isBroadcastNoise reports whether op is an unsolicited world/presence
// broadcast that Expect/DrainUntil may safely skip while hunting for a
// specific reply. These arrive interleaved with a bot's own responses
// because the server has no per-player area-of-interest filtering.
func isBroadcastNoise(op protocol.SendOpcode) bool {
	switch op {
	case protocol.SendActorSpawn,
		protocol.SendActorDespawn,
		protocol.SendActorMovement,
		protocol.SendActorTeleport,
		protocol.SendVicinityMessage,
		protocol.SendPrivateMessage,
		protocol.SendFriendAddedMessage,
		protocol.SendFriendRemovedMessage,
		protocol.SendIgnoreAddedMessage,
		protocol.SendIgnoreRemovedMessage:
		return true
	default:
		return false
	}
}

// Expect reads frames until one with opcode want arrives, skipping
// broadcast noise, and returns its payload. It fails if a non-noise,
// unexpected opcode arrives (that indicates a protocol desync worth
// reporting), if the connection closes, or if too many noise frames are
// skipped. timeout <= 0 uses DefaultRecvTimeout.
func (c *Client) Expect(want protocol.SendOpcode, timeout time.Duration) ([]byte, error) {
	if timeout <= 0 {
		timeout = DefaultRecvTimeout
	}
	for i := 0; i < maxBroadcastSkip; i++ {
		f, err := c.Recv(timeout)
		if err != nil {
			return nil, fmt.Errorf("expect(%s): %w", want.Name(), err)
		}
		if f.Opcode == want {
			return f.Payload, nil
		}
		if !isBroadcastNoise(f.Opcode) {
			return nil, fmt.Errorf("expect(%s): got %s instead",
				want.Name(), f.Opcode.Name())
		}
	}
	return nil, fmt.Errorf("expect(%s): too many broadcast frames skipped", want.Name())
}

// DrainUntil reads and discards frames until one with opcode want arrives.
// Unlike Expect it tolerates ANY interleaved opcode (not just broadcast
// noise), which is needed for phases that emit several distinct frames in
// an order the caller does not need to assert. max bounds how many
// non-matching frames it will consume. timeout <= 0 uses DefaultRecvTimeout.
func (c *Client) DrainUntil(want protocol.SendOpcode, max int, timeout time.Duration) ([]byte, error) {
	if timeout <= 0 {
		timeout = DefaultRecvTimeout
	}
	for i := 0; i < max; i++ {
		f, err := c.Recv(timeout)
		if err != nil {
			return nil, fmt.Errorf("drainUntil(%s): %w", want.Name(), err)
		}
		if f.Opcode == want {
			return f.Payload, nil
		}
	}
	return nil, fmt.Errorf("drainUntil(%s): opcode not seen within %d frames", want.Name(), max)
}
