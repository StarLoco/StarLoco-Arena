package protocol

import (
	"bytes"
	"testing"
)

// TestReadC2S decodes a hand-built client-to-server frame:
// [u16 total][u8 arch][u16 opcode][payload].
func TestReadC2S(t *testing.T) {
	// opcode 7, arch 0, payload = {0x02, 0x00, 0x46, 0x02, '2','7'}
	payload := []byte{0x02, 0x00, 0x46, 0x02, '2', '7'}
	total := 5 + len(payload)
	frame := []byte{byte(total >> 8), byte(total), 0x00, 0x00, 0x07}
	frame = append(frame, payload...)

	f, err := ReadC2S(bytes.NewReader(frame))
	if err != nil {
		t.Fatalf("ReadC2S: %v", err)
	}
	if f.Opcode != OpClientVersion {
		t.Errorf("opcode = %d, want %d", f.Opcode, OpClientVersion)
	}
	if f.Arch != ArchBasics {
		t.Errorf("arch = %d, want %d", f.Arch, ArchBasics)
	}
	if !bytes.Equal(f.Payload, payload) {
		t.Errorf("payload = %v, want %v", f.Payload, payload)
	}
}

// TestEncodeS2C builds a server-to-client frame and checks the 4-byte header.
func TestEncodeS2C(t *testing.T) {
	frame, err := EncodeS2C(OpClientAuthResult, []byte{0x00})
	if err != nil {
		t.Fatalf("EncodeS2C: %v", err)
	}
	want := []byte{0x00, 0x05, 0x04, 0x00, 0x00} // total=5, opcode=1024, code=0
	if !bytes.Equal(frame, want) {
		t.Errorf("frame = %v, want %v", frame, want)
	}
}

// TestReadShortFrame rejects a frame whose declared length is below the header.
func TestReadShortFrame(t *testing.T) {
	frame := []byte{0x00, 0x03, 0x00, 0x00, 0x07} // total=3 (< 5)
	if _, err := ReadC2S(bytes.NewReader(frame)); err == nil {
		t.Fatal("expected ErrShortFrame, got nil")
	}
}
