package protocol

import (
	"bufio"
	"encoding/binary"
	"errors"
	"fmt"
	"io"
)

// Wire framing constants, see docs/02-protocol.md §2.2.
const (
	// InboundHeaderSize is the number of bytes in a client->server frame
	// header: uint16 totalSize + uint8 architectureTarget + uint16 opcode.
	InboundHeaderSize = 5
	// OutboundHeaderSize is the number of bytes in a server->client frame
	// header: uint16 totalSize + uint16 opcode.
	OutboundHeaderSize = 4

	// MaxFrameSize is a defensive upper bound on a single frame's total
	// size, guarding against a malicious/corrupt totalSize field causing an
	// unbounded allocation. The legacy client never sends frames anywhere
	// near this size; CREATE_FIGHT (the largest known packet) tops out at a
	// few KB even with large rosters.
	MaxFrameSize = 64 * 1024
)

// ErrFrameTooLarge is returned when a declared frame size exceeds
// MaxFrameSize.
var ErrFrameTooLarge = errors.New("protocol: frame exceeds maximum allowed size")

// InboundFrame is a decoded client->server message before payload parsing.
type InboundFrame struct {
	// ArchitectureTarget is the legacy multi-server routing byte (0-4).
	// This server is a single-process monolith and does not use it for
	// routing; it is preserved here purely for protocol fidelity/logging.
	ArchitectureTarget byte
	Opcode             RecvOpcode
	Payload            []byte
}

// OutboundFrame is a server->client message ready to be written to the
// wire.
type OutboundFrame struct {
	Opcode  SendOpcode
	Payload []byte
}

// ReadInboundFrame reads exactly one framed client->server message from r.
// It returns io.EOF (or a wrapped io.ErrUnexpectedEOF) when the connection
// closes cleanly between frames.
func ReadInboundFrame(r *bufio.Reader) (InboundFrame, error) {
	header := make([]byte, InboundHeaderSize)
	if _, err := io.ReadFull(r, header); err != nil {
		return InboundFrame{}, err
	}

	totalSize := binary.BigEndian.Uint16(header[0:2])
	if int(totalSize) < InboundHeaderSize {
		return InboundFrame{}, fmt.Errorf("protocol: inbound totalSize %d smaller than header size %d", totalSize, InboundHeaderSize)
	}
	if int(totalSize) > MaxFrameSize {
		return InboundFrame{}, ErrFrameTooLarge
	}

	archTarget := header[2]
	opcode := RecvOpcode(binary.BigEndian.Uint16(header[3:5]))

	payloadLen := int(totalSize) - InboundHeaderSize
	payload := make([]byte, payloadLen)
	if payloadLen > 0 {
		if _, err := io.ReadFull(r, payload); err != nil {
			return InboundFrame{}, fmt.Errorf("protocol: read inbound payload: %w", err)
		}
	}

	return InboundFrame{
		ArchitectureTarget: archTarget,
		Opcode:             opcode,
		Payload:            payload,
	}, nil
}

// WriteOutboundFrame writes one framed server->client message to w.
func WriteOutboundFrame(w *bufio.Writer, f OutboundFrame) error {
	totalSize := OutboundHeaderSize + len(f.Payload)
	if totalSize > MaxFrameSize {
		return ErrFrameTooLarge
	}

	header := make([]byte, OutboundHeaderSize)
	binary.BigEndian.PutUint16(header[0:2], uint16(totalSize))
	binary.BigEndian.PutUint16(header[2:4], uint16(f.Opcode))

	if _, err := w.Write(header); err != nil {
		return fmt.Errorf("protocol: write outbound header: %w", err)
	}
	if len(f.Payload) > 0 {
		if _, err := w.Write(f.Payload); err != nil {
			return fmt.Errorf("protocol: write outbound payload: %w", err)
		}
	}
	return w.Flush()
}
