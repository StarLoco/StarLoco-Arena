package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestTeleportWireShape pins 4510: [i64 actorId][i32 x][i32 y][i16 z], exactly
// 18 bytes. The client validates the length and drops a short frame silently, so
// a size regression would be invisible at runtime.
func TestTeleportWireShape(t *testing.T) {
	frame, err := buildActorTeleport(7, 40, -20, 8)
	if err != nil {
		t.Fatalf("buildActorTeleport: %v", err)
	}
	payload := frame[4:]
	if len(payload) != 18 {
		t.Fatalf("payload is %d bytes, want exactly 18 - the client length-checks "+
			"this frame and silently drops anything shorter", len(payload))
	}
	r := protocol.NewReader(payload)
	id, _ := r.I64()
	x, _ := r.I32()
	y, _ := r.I32()
	z, _ := r.U16()
	if id != 7 || x != 40 || y != -20 || int16(z) != 8 {
		t.Errorf("decoded (%d,%d,%d,%d), want (7,40,-20,8)", id, x, y, int16(z))
	}
}

// TestTeleportNotifiesViewersAndSelf: the coach itself must be included - the
// client recentres its own camera from this frame, so omitting it teleports the
// actor out from under a stationary camera.
func TestTeleportNotifiesViewersAndSelf(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	self := tmSession(d, 1, "Jumper")
	watcher := tmSession(d, 2, "Watcher")
	d.World.Add(&Online{Coach: self.Coach, Session: self})
	d.World.Add(&Online{Coach: watcher.Coach, Session: watcher})
	d.World.EnterAoI(self.Coach.ID)
	d.World.EnterAoI(watcher.Coach.ID)
	if len(d.World.ViewersOf(self.Coach.ID)) == 0 {
		t.Fatal("fixture: nobody can see the jumper, so this test could not tell a " +
			"working broadcast from a missing one")
	}

	if !self.teleportWithinWorld(11, 22, 3) {
		t.Fatal("teleportWithinWorld returned false")
	}
	for _, tc := range []struct {
		name string
		sess *Session
	}{{"watcher", watcher}, {"self", self}} {
		if p := drainPayload(t, tc.sess, protocol.OpActorTeleports); p == nil {
			t.Errorf("%s received no 4510", tc.name)
		}
	}
	if self.Coach.PosX != 11 || self.Coach.PosY != 22 {
		t.Errorf("coach at (%d,%d), want (11,22)", self.Coach.PosX, self.Coach.PosY)
	}
}

// TestTeleportStandsTheCoachUp: a teleport is a discontinuity. A coach dragged
// out from under a sitting animation would arrive still seated.
func TestTeleportStandsTheCoachUp(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	s := tmSession(d, 1, "Sitter")
	d.World.Add(&Online{Coach: s.Coach, Session: s})
	_ = s.setSitting(true)
	if !d.World.IsSitting(s.Coach.ID) {
		t.Fatal("fixture: the coach is not sitting, so this test proves nothing")
	}

	if !s.teleportWithinWorld(5, 5, 0) {
		t.Fatal("teleportWithinWorld returned false")
	}
	if d.World.IsSitting(s.Coach.ID) {
		t.Error("the coach is still sitting after a teleport")
	}
}

// TestTeleportSpawnsBeforeTelling: a session that only just gained sight of the
// coach must receive ActorSpawn BEFORE the 4510 naming it. The retail handler
// resolves the actor id and dereferences the result with no null check, so the
// reverse order is a NullPointerException in someone else's client (B-136).
//
// Order, not mere presence: both frames arrive either way.
func TestTeleportSpawnsBeforeTelling(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	self := tmSession(d, 1, "Jumper")
	stranger := tmSession(d, 2, "Stranger")
	d.World.Add(&Online{Coach: self.Coach, Session: self})
	d.World.Add(&Online{Coach: stranger.Coach, Session: stranger})

	// The stranger has NOT spawned the jumper: this teleport is what brings them
	// into each other's view, which is the case the ordering protects.
	if len(d.World.ViewersOf(self.Coach.ID)) != 0 {
		t.Fatal("fixture: the stranger already sees the jumper, so no spawn would " +
			"be generated and this test would prove nothing")
	}

	if !self.teleportWithinWorld(self.Coach.PosX, self.Coach.PosY, self.Coach.PosZ) {
		t.Fatal("teleportWithinWorld returned false")
	}

	ops := drain(t, stranger)
	spawnAt, tpAt := -1, -1
	for i, op := range ops {
		switch op {
		case protocol.OpActorSpawn:
			if spawnAt < 0 {
				spawnAt = i
			}
		case protocol.OpActorTeleports:
			if tpAt < 0 {
				tpAt = i
			}
		}
	}
	if spawnAt < 0 {
		t.Fatalf("the stranger never received ActorSpawn for the jumper (got %v)", ops)
	}
	if tpAt >= 0 && tpAt < spawnAt {
		t.Errorf("4510 (index %d) arrived BEFORE ActorSpawn (index %d); the retail "+
			"handler NPEs on an actor it has not spawned", tpAt, spawnAt)
	}
}
