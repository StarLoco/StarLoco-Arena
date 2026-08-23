package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// 28630 is the message that makes the whole Tournois tab reachable: it creates
// the client's tournament NOTIFICATION (zN builds a td_0), and clicking that is
// the only thing that sets the selected tournament (agz_1 -> vk_1.ad(tid)).
// Without it "Combattre" refuses with error.noTournamentSelected forever.
func TestSearchPeriodWireIsExact(t *testing.T) {
	for _, tc := range []struct {
		tid  int64
		open bool
		want byte
	}{
		{7, true, 1},
		{9, false, 0},
	} {
		frame, err := buildTournamentSearchPeriod(tc.tid, tc.open)
		if err != nil {
			t.Fatalf("build: %v", err)
		}
		r := protocol.NewReader(frame[4:]) // skip [u16 len][u16 opcode]
		tid, err := r.I64()
		if err != nil {
			t.Fatalf("tid: %v", err)
		}
		open, err := r.U8()
		if err != nil {
			t.Fatalf("open flag: %v", err)
		}
		if tid != tc.tid || open != tc.want {
			t.Errorf("28630(%d,%v) = tid %d flag %d, want tid %d flag %d",
				tc.tid, tc.open, tid, open, tc.tid, tc.want)
		}
		if r.Remaining() != 0 {
			t.Errorf("%d trailing bytes - dg_0 reads exactly 9", r.Remaining())
		}
	}
}

// TestSearchPeriodOpcode: it must be 28630 and not the neighbouring 28644/28646,
// which build a DIFFERENT notification (a countdown, not a selectable tournament).
func TestSearchPeriodOpcode(t *testing.T) {
	frame, err := buildTournamentSearchPeriod(1, true)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if got := uint16(frame[2])<<8 | uint16(frame[3]); got != protocol.OpTournamentSearchPeriod {
		t.Errorf("opcode = %d, want %d", got, protocol.OpTournamentSearchPeriod)
	}
	if protocol.OpTournamentSearchPeriod != 28630 {
		t.Errorf("OpTournamentSearchPeriod = %d, want 28630",
			protocol.OpTournamentSearchPeriod)
	}
}
