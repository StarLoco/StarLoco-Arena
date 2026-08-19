package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// The achievement tab. Opening it client-side does NOT open the dialog: yh.java
// only registers handler A and sends 22001, and it is A's 22002 case that pops
// "achievementDialog". So an unanswered 22001 makes the achievements button inert
// — which is how it behaved before handleStatisticRequest existed.
const (
	opStatisticRequest = 22001 // C2S anp_0: empty
	opStatisticData    = 22002 // S2C ls_0: [i32 byteLen]{[i16 id][i16 value]}
	opStatisticUpdate  = 22003 // C2S nq: [i16 id][i8 flag][i16 value]

	// criterionZaapUnlock (or_0.YW, 219) is seeded for every coach. It doubles as
	// the canary for the wholesale-replace hazard below.
	criterionZaapUnlock = 219
)

// readCriteria decodes a 22002 payload into a map, asserting the i32 length
// prefix agrees exactly with the pairs that follow. The client trusts that
// length (ls_0.a loops `while (n*4 < byteLen)` with no bounds check), so a
// mismatch would run it off the end of the buffer.
func readCriteria(t *testing.T, payload []byte) map[uint16]uint16 {
	t.Helper()
	r := testclient.NewR(payload)
	byteLen := r.I32()
	if byteLen < 0 || int(byteLen)%4 != 0 {
		t.Fatalf("22002 byteLen = %d, want a non-negative multiple of 4", byteLen)
	}
	if got, want := len(payload)-4, int(byteLen); got != want {
		t.Fatalf("22002 byteLen = %d but %d bytes of pairs follow: the client would "+
			"read past the end of the frame", want, got)
	}
	out := make(map[uint16]uint16, byteLen/4)
	for i := 0; i < int(byteLen)/4; i++ {
		out[r.U16()] = r.U16()
	}
	return out
}

// TestAchievementTabOpens is the one that matters: the tab is opened BY the
// reply, so this asserts the server answers 22001 at all. A unit test of the
// encoder cannot see a missing handler registration.
func TestAchievementTabOpens(t *testing.T) {
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "achv_open", "AchvOpen")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, opStatisticRequest, nil)

	f, _, err := c.WaitFor(opStatisticData, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no StatisticData(22002) for the client's 22001: the achievements "+
			"button is inert again (the reply is what opens the dialog): %v", err)
	}
	crit := readCriteria(t, f.Payload)
	if crit[criterionZaapUnlock] != 1 {
		t.Errorf("criterion %d = %d, want 1 (got %v)", criterionZaapUnlock,
			crit[criterionZaapUnlock], crit)
	}
}

// TestAchievementTabShowsEarnedCriteria: a criterion reported since login must
// appear in the snapshot, not just after a relog.
func TestAchievementTabShowsEarnedCriteria(t *testing.T) {
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "achv_earn", "AchvEarn")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	// 221 = or_0.YY, "nombre de fois ou le coach a discute avec un breedmaster".
	const statID = 221
	_ = c.Send(2, opStatisticUpdate,
		testclient.NewW().U16(statID).U8(1).U16(1).Bytes())
	time.Sleep(200 * time.Millisecond) // 22003 has no reply to synchronise on

	_ = c.Send(2, opStatisticRequest, nil)
	f, _, err := c.WaitFor(opStatisticData, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no StatisticData(22002): %v", err)
	}
	crit := readCriteria(t, f.Payload)
	if crit[statID] != 1 {
		t.Errorf("criterion %d = %d, want 1: the tab is not reflecting criteria "+
			"earned since login (got %v)", statID, crit[statID], crit)
	}
}

// TestAchievementSnapshotIsCompleteNotADelta guards the wholesale-replace hazard.
// The client's handler A does Ln().b(ls_0.qI()) and aez_0.b REPLACES the criteria
// map rather than merging, so anything omitted from 22002 is ERASED from the
// running client. Here: earning a new criterion must not cost the coach the Zaap
// criterion it logged in with, which would silently re-lock the island Zaap.
func TestAchievementSnapshotIsCompleteNotADelta(t *testing.T) {
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "achv_full", "AchvFull")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	const statID = 221
	_ = c.Send(2, opStatisticUpdate,
		testclient.NewW().U16(statID).U8(1).U16(1).Bytes())
	time.Sleep(200 * time.Millisecond)

	_ = c.Send(2, opStatisticRequest, nil)
	f, _, err := c.WaitFor(opStatisticData, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no StatisticData(22002): %v", err)
	}
	crit := readCriteria(t, f.Payload)
	if crit[criterionZaapUnlock] != 1 {
		t.Errorf("criterion %d missing from the snapshot (got %v): the client "+
			"replaces its map wholesale, so this re-locks the island Zaap",
			criterionZaapUnlock, crit)
	}
	if crit[statID] != 1 {
		t.Errorf("criterion %d = %d, want 1", statID, crit[statID])
	}
}
