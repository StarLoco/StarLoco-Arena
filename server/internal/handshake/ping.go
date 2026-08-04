package handshake

import (
	"fmt"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// Ping is the latency / clock-sync handshake, opcode 107.
//
// Right after auth the client sends a 107 request on each channel (the frame's
// arch byte equals the ping's flag) and blocks on the login/black screen until
// the server answers. The client stamps System.nanoTime() on receipt to measure
// round-trip time.
//
//	Client -> Server (asg_0, 13 bytes): [i8 flag][i32 key][i64 clientNanos]
//	Server -> Client (abj_0, 29 bytes): [i8 flag][i32 key][i64 t1][i64 t2][i64 t3]
//
// The client only reads flag, key and the three longs; it does not validate the
// time values against anything, so echoing the request's flag/key with server
// timestamps is sufficient to satisfy the sync.
type Ping struct {
	Flag        uint8
	Key         int32
	ClientNanos int64
}

// DecodePing parses an opcode-107 client request (asg_0).
func DecodePing(payload []byte) (*Ping, error) {
	r := protocol.NewReader(payload)
	flag, err := r.U8()
	if err != nil {
		return nil, fmt.Errorf("ping flag: %w", err)
	}
	key, err := r.I32()
	if err != nil {
		return nil, fmt.Errorf("ping key: %w", err)
	}
	clientNanos, err := r.I64()
	if err != nil {
		return nil, fmt.Errorf("ping clientNanos: %w", err)
	}
	return &Ping{Flag: flag, Key: key, ClientNanos: clientNanos}, nil
}

// PingReplyOpcode is the server->client ping acknowledgement (abj_0). It is a
// DISTINCT opcode from the 107 request: the client's keepalive dispatcher
// (pl_2 case 108) only increments its "server reply" counter (nW.sL()) when it
// receives a 108. Replying with 107 (the request opcode) is silently ignored by
// the keepalive, so every 60s the client logs "Too high ping detected: Server
// reply number is low, 0 != 2" and resets the connection health. The reply must
// be 108.
const PingReplyOpcode = 108

// EncodePingReply builds the opcode-108 server reply (abj_0, 29-byte body):
// [i8 flag][i32 key][i64 t1][i64 t2][i64 t3]. t1/t2/t3 are server timestamps
// used only for RTT display; the client credits the reply via nW.sL().
func EncodePingReply(p *Ping, t1, t2, t3 int64) ([]byte, error) {
	payload := protocol.NewWriter().
		U8(p.Flag).
		I32(p.Key).
		I64(t1).
		I64(t2).
		I64(t3).
		Bytes()
	return protocol.EncodeS2C(PingReplyOpcode, payload)
}
