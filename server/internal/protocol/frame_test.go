package protocol

import (
	"bufio"
	"bytes"
	"testing"
)

func TestInboundFrameRoundTrip(t *testing.T) {
	// Manually construct a frame matching docs/02-protocol.md §2.2:
	// [uint16 totalSize][uint8 archTarget][uint16 opcode][payload].
	payload := []byte("hello world")
	totalSize := uint16(InboundHeaderSize + len(payload))

	var buf bytes.Buffer
	buf.WriteByte(byte(totalSize >> 8))
	buf.WriteByte(byte(totalSize))
	buf.WriteByte(2) // architecture target
	buf.WriteByte(0)
	buf.WriteByte(42) // opcode 42
	buf.Write(payload)

	frame, err := ReadInboundFrame(bufio.NewReader(&buf))
	if err != nil {
		t.Fatalf("ReadInboundFrame: %v", err)
	}
	if frame.ArchitectureTarget != 2 {
		t.Errorf("ArchitectureTarget = %d, want 2", frame.ArchitectureTarget)
	}
	if frame.Opcode != 42 {
		t.Errorf("Opcode = %d, want 42", frame.Opcode)
	}
	if string(frame.Payload) != "hello world" {
		t.Errorf("Payload = %q, want %q", frame.Payload, "hello world")
	}
}

func TestOutboundFrameRoundTrip(t *testing.T) {
	var buf bytes.Buffer
	w := bufio.NewWriter(&buf)

	payload := []byte("response data")
	err := WriteOutboundFrame(w, OutboundFrame{Opcode: 1024, Payload: payload})
	if err != nil {
		t.Fatalf("WriteOutboundFrame: %v", err)
	}

	written := buf.Bytes()
	wantSize := OutboundHeaderSize + len(payload)
	gotSize := int(written[0])<<8 | int(written[1])
	if gotSize != wantSize {
		t.Errorf("totalSize = %d, want %d", gotSize, wantSize)
	}
	gotOpcode := int(written[2])<<8 | int(written[3])
	if gotOpcode != 1024 {
		t.Errorf("opcode = %d, want 1024", gotOpcode)
	}
	if string(written[4:]) != "response data" {
		t.Errorf("payload = %q, want %q", written[4:], "response data")
	}
}

func TestReadInboundFrameRejectsOversizedFrame(t *testing.T) {
	if MaxFrameSize > 0xFFFF {
		t.Skip("MaxFrameSize >= uint16 max, cannot construct an oversized totalSize to test rejection")
	}
	// A malicious/corrupt totalSize declaring more than MaxFrameSize must
	// be rejected immediately, before attempting to read (and allocate)
	// any payload bytes -- otherwise a bad totalSize could be used to
	// force large allocations. Since MaxFrameSize == 0xFFFF here would
	// leave no headroom, assert the boundary using the largest
	// representable uint16 value and skip if that happens to be exactly
	// MaxFrameSize (no invalid value exists to test in that edge case).
	if MaxFrameSize == 0xFFFF {
		t.Skip("MaxFrameSize equals uint16 max, no invalid larger value exists to test")
	}
	var buf bytes.Buffer
	oversized := uint32(MaxFrameSize) + 1
	buf.WriteByte(byte(oversized >> 8))
	buf.WriteByte(byte(oversized))
	buf.WriteByte(0)
	buf.WriteByte(0)
	buf.WriteByte(0)

	_, err := ReadInboundFrame(bufio.NewReader(&buf))
	if err == nil {
		t.Fatal("expected ErrFrameTooLarge for totalSize > MaxFrameSize, got nil")
	}
}

func TestReadInboundFrameRejectsHeaderSizeSmallerThanHeader(t *testing.T) {
	var buf bytes.Buffer
	// totalSize = 3, smaller than InboundHeaderSize (5) -- must be
	// rejected rather than underflowing the payload length calculation.
	buf.WriteByte(0)
	buf.WriteByte(3)
	buf.WriteByte(0)
	buf.WriteByte(0)
	buf.WriteByte(0)

	_, err := ReadInboundFrame(bufio.NewReader(&buf))
	if err == nil {
		t.Fatal("expected error for totalSize smaller than header size, got nil")
	}
}

func TestReadInboundFrameEmptyPayload(t *testing.T) {
	var buf bytes.Buffer
	buf.WriteByte(0)
	buf.WriteByte(InboundHeaderSize)
	buf.WriteByte(0)
	buf.WriteByte(0)
	buf.WriteByte(7) // VERSION opcode

	frame, err := ReadInboundFrame(bufio.NewReader(&buf))
	if err != nil {
		t.Fatalf("ReadInboundFrame: %v", err)
	}
	if len(frame.Payload) != 0 {
		t.Errorf("Payload len = %d, want 0", len(frame.Payload))
	}
	if frame.Opcode != 7 {
		t.Errorf("Opcode = %d, want 7", frame.Opcode)
	}
}
