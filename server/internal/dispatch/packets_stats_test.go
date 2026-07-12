package dispatch

import (
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/protocol"
)

// TestBuildPlayerStatisticsReportWireFormat verifies the 2400 packet
// serializes a coach's real statistics in the exact entry order/types the
// client's PlayerStatisticsReport expects (see docs/opcodes/03-coach-world.md):
// a size prefix, then {modelId, reportId, entryCount=7} and 7 entries.
func TestBuildPlayerStatisticsReportWireFormat(t *testing.T) {
	coach := &domain.Coach{
		TotalPlayTimeSecs: 7200,
		TimeInFightSecs:   1234,
		StatFights:        42,
		StatWins:          30,
		StatLosses:        12,
		Strength:          1500,
		ConsecutiveWins:   5,
	}

	frame := buildPlayerStatisticsReport(coach)
	if frame.Opcode != protocol.SendPlayerStatisticsReport {
		t.Fatalf("opcode = %v, want SendPlayerStatisticsReport", frame.Opcode)
	}

	r := protocol.NewReader(frame.Payload)
	size := r.Uint16()
	if int(size) != len(frame.Payload)-2 {
		t.Errorf("size prefix = %d, want %d (payload-2)", size, len(frame.Payload)-2)
	}
	if modelID := r.Uint16(); modelID != 1 {
		t.Errorf("modelId = %d, want 1", modelID)
	}
	if reportID := r.Int64(); reportID != 1 {
		t.Errorf("reportId = %d, want 1", reportID)
	}
	entryCount := r.Uint16()
	if entryCount != 7 {
		t.Fatalf("entryCount = %d, want 7", entryCount)
	}

	type want struct {
		id   uint16
		kind byte
		long int64
		i32  int32
	}
	wants := []want{
		{1, 2, 7200, 0}, // total play time
		{2, 2, 1234, 0}, // total fight time
		{3, 1, 0, 42},   // fights
		{4, 1, 0, 30},   // wins
		{5, 1, 0, 12},   // losses
		{6, 1, 0, 1500}, // strength
		{7, 1, 0, 5},    // consecutive wins
	}
	for i, w := range wants {
		id := r.Uint16()
		kind := r.Byte()
		if id != w.id || kind != w.kind {
			t.Errorf("entry %d header = {id=%d kind=%d}, want {id=%d kind=%d}", i, id, kind, w.id, w.kind)
		}
		if kind == 2 {
			if v := r.Int64(); v != w.long {
				t.Errorf("entry %d long = %d, want %d", i, v, w.long)
			}
		} else {
			if v := r.Int32(); v != w.i32 {
				t.Errorf("entry %d int = %d, want %d", i, v, w.i32)
			}
		}
	}

	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
	if r.Remaining() != 0 {
		t.Errorf("Remaining() = %d, want 0 (no trailing bytes)", r.Remaining())
	}
}

// TestWriteLaddersStrength covers both the ranked and unranked coach cases
// of the COACH_INFORMATION / ACTOR_SPAWN ladders-strength block.
func TestWriteLaddersStrength(t *testing.T) {
	// Ranked coach: count=1, ladderId=1, short strength.
	w := protocol.NewWriter(8)
	writeLaddersStrength(w, 1750)
	r := protocol.NewReader(w.Bytes())
	if count := r.Byte(); count != 1 {
		t.Fatalf("ranked count = %d, want 1", count)
	}
	if id := r.Byte(); id != 1 {
		t.Errorf("ladderId = %d, want 1", id)
	}
	if s := r.Int16(); s != 1750 {
		t.Errorf("strength = %d, want 1750", s)
	}
	if r.Remaining() != 0 {
		t.Errorf("ranked Remaining() = %d, want 0", r.Remaining())
	}

	// Unranked coach (strength 0): empty block (count=0 only).
	w = protocol.NewWriter(4)
	writeLaddersStrength(w, 0)
	if b := w.Bytes(); len(b) != 1 || b[0] != 0 {
		t.Errorf("unranked block = %v, want [0]", b)
	}
}
