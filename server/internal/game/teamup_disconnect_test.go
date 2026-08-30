package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestPartnerDisconnectNotifiesTheOtherHalf: every other pairing in onClose
// notifies its counterparty - matchmaking, challenges, exchanges - but the 2v2
// team-up did not. A partner dropping during team formation left the other
// player sitting in the fighter picker waiting for someone who was gone.
//
// 6029 is one byte and `dx_2` shows "error.teamManagement.coachDisconnected"
// regardless of its value: the opcode IS the message.
func TestPartnerDisconnectNotifiesTheOtherHalf(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	d.TeamUps = newTeamUps()
	a := tmSession(d, 1, "Leaver")
	b := tmSession(d, 2, "Stayer")
	d.World.Add(&Online{Coach: a.Coach, Session: a})
	d.World.Add(&Online{Coach: b.Coach, Session: b})
	d.TeamUps.bind(&teamUpPair{Name: "Duo", InviterID: 1, InvitedID: 2})

	if got := d.TeamUps.Partner(2); got != 1 {
		t.Fatalf("fixture: coach 2's partner = %d, want 1 - the pair is not bound, "+
			"so this test could not detect the notification either way", got)
	}

	d.releaseTeamUpAndNotify(a.Coach.ID)

	got := drain(t, b)
	found := false
	for _, op := range got {
		if op == protocol.OpTeamUpCoachGone {
			found = true
		}
	}
	if !found {
		t.Errorf("the surviving partner got %v, want %d (6029) among them",
			got, protocol.OpTeamUpCoachGone)
	}
	if p := d.TeamUps.Partner(2); p != 0 {
		t.Errorf("coach 2 is still paired with %d after its partner disconnected", p)
	}
}
