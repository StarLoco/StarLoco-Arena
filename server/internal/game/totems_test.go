package game

import (
	"encoding/binary"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// TestDemonLadderStubIsExactlyWellFormed: the client's 27511 decoder (awj) reads
// its trailing per-message i64 unconditionally and has NO length guard, so a short
// payload is silently dropped inside the frame decoder and the demon-ladder dialog
// never opens. The empty form must be exactly 20 bytes, and the status flag must
// be 1 or the client leaves its rows alone.
func TestDemonLadderStubIsExactlyWellFormed(t *testing.T) {
	frame, err := buildDemonLadder(7, nil, 0)
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

// TestDemonLadderCarriesItsClans: the "strongest servant of each demon" rule is
// only visible to a player through this window, so the rows have to be real - and
// ordered, since the top row is the clan that holds the island.
func TestDemonLadderCarriesItsClans(t *testing.T) {
	rows := []store.DemonReputationRow{
		{GuildID: 1, Name: "Les Bouftous", Points: 900},
		{GuildID: 2, Name: "Les Pious", Points: 400},
	}
	frame, err := buildDemonLadder(3, rows, 3)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	p := frame[4:]
	if got := binary.BigEndian.Uint32(p[8:12]); got != 2 {
		t.Fatalf("row count = %d, want 2", got)
	}
	// First row: [i32 len][name][i64][i64][i64]
	off := 12
	n := int(binary.BigEndian.Uint32(p[off : off+4]))
	off += 4
	if string(p[off:off+n]) != "Les Bouftous" {
		t.Errorf("first clan = %q, want Les Bouftous (the leader must come first)", string(p[off:off+n]))
	}
	off += n
	if got := binary.BigEndian.Uint64(p[off : off+8]); got != 900 {
		t.Errorf("quarterly reputation = %d, want 900", got)
	}
	// The trailing per-message i64 is the viewer's own affiliation.
	if got := binary.BigEndian.Uint64(p[len(p)-8:]); got != 3 {
		t.Errorf("viewer affiliation = %d, want 3", got)
	}
}
