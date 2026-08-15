package e2e

import (
	"testing"
	"time"
)

// TestPlayTimeIsPersistedOnDisconnect drives the real thing: log in over a
// socket, stay a moment, disconnect, and check the counter reached the
// database. The unit tests cover the arithmetic; this covers the wiring —
// that completeLogin stamps the clock and onClose banks it through
// CoachRepo.Save, which is exactly the part that was missing.
func TestPlayTimeIsPersistedOnDisconnect(t *testing.T) {
	st, addr := testServerWithStore(t)

	c, coachID := dialLogin(t, addr, "timer", "Timekeeper")

	// Long enough to round to a non-zero number of seconds.
	time.Sleep(1200 * time.Millisecond)
	_ = c.Close()

	// onClose runs on the server's own goroutine; give it a moment to land.
	var secs int64
	deadline := time.Now().Add(5 * time.Second)
	for time.Now().Before(deadline) {
		coach, err := st.Coaches.Get(uint(coachID))
		if err == nil && coach.TotalPlaySecs > 0 {
			secs = coach.TotalPlaySecs
			break
		}
		time.Sleep(50 * time.Millisecond)
	}

	if secs == 0 {
		t.Fatal("TotalPlaySecs is still 0 after a session that lasted over a second — " +
			"the statistics panel would show nothing no matter how long anyone played")
	}
	if secs > 60 {
		t.Errorf("TotalPlaySecs = %d after a ~1s session; the clock is not measuring what it should", secs)
	}
}
