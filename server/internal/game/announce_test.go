package game

import "testing"

// TestBlankAnnouncementIsNotBroadcast: 2070 does not merely print - `om_0`
// force-maximises the chat panel and force-opens chatDialog - so an empty
// announcement would take over every player's screen to show them nothing.
//
// Unreachable through /ANNOUNCE itself (strings.Fields drops whitespace, so the
// usage message fires first), which is why it needs a direct test: the guard
// protects every future caller.
//
// The registry MUST contain a session, or this passes for the wrong reason -
// with nobody online the function returns 0 whether the guard exists or not,
// which is how the first version of this test fooled me.
func TestBlankAnnouncementIsNotBroadcast(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	s := tmSession(d, 1, "Listener")
	d.World.Add(&Online{Coach: s.Coach, Session: s})

	if n := d.broadcastServerMessage("a real notice"); n != 1 {
		t.Fatalf("a real announcement reached %d session(s), want 1 - the fixture "+
			"has nobody online, so the blank cases below would prove nothing", n)
	}
	for _, msg := range []string{"", "   ", "\t\n"} {
		if n := d.broadcastServerMessage(msg); n != 0 {
			t.Errorf("broadcastServerMessage(%q) sent to %d session(s), want 0", msg, n)
		}
	}
}
