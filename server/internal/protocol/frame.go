// Package protocol implements the DofusArena 2.70 wire framing.
//
// The client uses two frame shapes on the same big-endian TCP stream:
//
//	Client -> Server (built by acb_2.addClientHeader):
//	    [u16 totalLen = 5 + len(payload)] [u8 archTarget] [u16 opcode] [payload]
//
//	Server -> Client (parsed by fp_0.g):
//	    [u16 totalLen = 4 + len(payload)] [u16 opcode] [payload]
//	    (no archTarget byte inbound; the client reads only opcode + payload)
//
// All integers are big-endian (network byte order). Game login is NOT
// encrypted in 2.70 (the RSA machinery belongs to a separate admin channel the
// game client never uses), so payloads are read/written verbatim.
package protocol

import (
	"encoding/binary"
	"errors"
	"fmt"
	"io"
)

// archTarget (a.k.a. flag) byte on C2S frames selects the logical back-end the
// proxy routes to. Observed values:
const (
	ArchBasics = 0 // connection / version handshake (opcode 7)
	ArchAuth   = 1 // authentication (opcode 1025) — plaintext in 2.70
	ArchWorld  = 2 // world server (e.g. coach creation 2049)
	ArchCoach  = 3 // most in-game coach messages
)

// MaxFrameLen bounds a single frame's declared length. The length prefix is a
// u16, so frames can never exceed 65535 bytes including the header.
const MaxFrameLen = 0xFFFF

// ErrShortFrame is returned when a frame's declared length is smaller than its
// own header (a malformed or hostile client).
var ErrShortFrame = errors.New("protocol: frame length shorter than header")

// C2SFrame is a decoded client-to-server frame.
type C2SFrame struct {
	Arch    byte   // architecture target / flag byte
	Opcode  uint16 // message opcode
	Payload []byte // message body (header stripped)
}

// ReadC2S reads a single client-to-server frame from r.
//
// Wire layout: [u16 totalLen][u8 arch][u16 opcode][payload].
// totalLen counts the whole frame including the 5-byte header, so the payload
// length is totalLen-5.
func ReadC2S(r io.Reader) (*C2SFrame, error) {
	var header [5]byte
	if _, err := io.ReadFull(r, header[:]); err != nil {
		return nil, err
	}
	total := binary.BigEndian.Uint16(header[0:2])
	arch := header[2]
	opcode := binary.BigEndian.Uint16(header[3:5])

	if total < 5 {
		return nil, fmt.Errorf("%w: totalLen=%d", ErrShortFrame, total)
	}
	payloadLen := int(total) - 5

	payload := make([]byte, payloadLen)
	if _, err := io.ReadFull(r, payload); err != nil {
		return nil, err
	}
	return &C2SFrame{Arch: arch, Opcode: opcode, Payload: payload}, nil
}

// EncodeS2C builds a server-to-client frame: [u16 totalLen][u16 opcode][payload].
// totalLen counts the 4-byte header plus the payload.
func EncodeS2C(opcode uint16, payload []byte) ([]byte, error) {
	total := 4 + len(payload)
	if total > MaxFrameLen {
		return nil, fmt.Errorf("protocol: S2C frame too large: %d bytes", total)
	}
	buf := make([]byte, total)
	binary.BigEndian.PutUint16(buf[0:2], uint16(total))
	binary.BigEndian.PutUint16(buf[2:4], opcode)
	copy(buf[4:], payload)
	return buf, nil
}
