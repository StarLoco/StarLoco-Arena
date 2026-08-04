package game

import (
	"encoding/binary"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestDemonLadderStubIsExactlyWellFormed: the client's 27511 decoder (awj) reads
// its trailing per-message i64 unconditionally and has NO length guard, so a short
// payload is silently dropped inside the frame decoder and the demon-ladder dialog
// never opens. The empty form must be exactly 20 bytes, and the status flag must
// be 1 or the client leaves its rows alone.
func TestDemonLadderStubIsExactlyWellFormed(t *testing.T) {
	frame, err := buildDemonLadder(7)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if got := binary.BigEndian.Uint16(frame[2:4]); got != protocol.OpDemonLadder {
		t.Errorf("opcode = %d, want %d", got, protocol.OpDemonLadder)
	}
	p := frame[4:]
	// [i16 demonId][i16 statusFlag][i32 startRank][i32 count][i64 affiliation]
	if len(p) != 2+2+4+4+8 {
		t.Fatalf("payload = %d bytes, want 20 (the client reads all of them)", len(p))
	}
	if got := binary.BigEndian.Uint16(p[0:2]); got != 7 {
		t.Errorf("demonId = %d, want 7 (echoed back)", got)
	}
	if got := binary.BigEndian.Uint16(p[2:4]); got != 1 {
		t.Errorf("statusFlag = %d, want 1 (required for the client to fill its rows)", got)
	}
	if got := binary.BigEndian.Uint32(p[8:12]); got != 0 {
		t.Errorf("row count = %d, want 0", got)
	}
}
