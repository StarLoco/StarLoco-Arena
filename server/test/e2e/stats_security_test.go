package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestClientCannotWriteServerBookkeepingStats drives the real attack frame.
//
// The server records "PvE challenge N already cleared" as stat id 2000+N in the
// SAME coach_stats table opcode 22003 lets the client write, and UpsertStat
// OVERWRITES rather than maxes. So: clear the flag, re-run the challenge, collect
// its reward cards again, repeat. Unbounded card creation with no race required.
//
// The id space was documented as un-collidable because 2000 sits above the
// client's MaxCriterionID (1007) - but that only kept those ids out of the
// server's OUTPUT, never out of its input.
func TestClientCannotWriteServerBookkeepingStats(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "statguard", "StatGuard")

	// 2000 + challengeID 1 - the server's own namespace.
	const bookkeepingStat = 2001
	payload := testclient.NewW().U16(bookkeepingStat).U8(0).U16(7).Bytes()
	_ = c.Send(2, opStatisticUpdate, payload)
	c.DrainReceived(300 * time.Millisecond)

	coach, err := st.Coaches.Get(uint(coachID))
	if err != nil {
		t.Fatalf("reload coach: %v", err)
	}
	for _, s := range coach.Stats {
		if s.StatID == bookkeepingStat {
			t.Fatalf("client wrote server bookkeeping stat %d (value %d) - "+
				"challenge reward flags are clearable, so reward cards can be "+
				"farmed without limit", s.StatID, s.Value)
		}
	}

	// And a LEGITIMATE criterion must still be writable, or this guard has broken
	// achievement progress instead of securing it.
	const realCriterion = 213
	_ = c.Send(2, opStatisticUpdate, testclient.NewW().U16(realCriterion).U8(0).U16(3).Bytes())
	c.DrainReceived(300 * time.Millisecond)

	coach, err = st.Coaches.Get(uint(coachID))
	if err != nil {
		t.Fatalf("reload coach: %v", err)
	}
	found := false
	for _, s := range coach.Stats {
		if s.StatID == realCriterion {
			found = true
		}
	}
	if !found {
		t.Errorf("criterion %d was rejected too - the guard is too broad and has "+
			"broken legitimate achievement reporting", realCriterion)
	}
}
