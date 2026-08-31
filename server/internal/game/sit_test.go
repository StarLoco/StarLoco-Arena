package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestSitBroadcastsToViewersAndSelf: there is no client-side sit, so a coach can
// only ever appear seated because the server said so. /sit must therefore reach
// both the sitter and everyone who has it spawned.
func TestSitBroadcastsToViewersAndSelf(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	self := tmSession(d, 1, "Sitter")
	near := tmSession(d, 2, "Watcher")
	d.World.Add(&Online{Coach: self.Coach, Session: self})
	d.World.Add(&Online{Coach: near.Coach, Session: near})
	d.World.EnterAoI(self.Coach.ID)
	d.World.EnterAoI(near.Coach.ID)
	if len(d.World.ViewersOf(self.Coach.ID)) == 0 {
		t.Fatal("fixture: nobody has the sitter spawned, so this test could not " +
			"tell a working broadcast from a missing one")
	}

	if err := self.setSitting(true); err != nil {
		t.Fatalf("setSitting: %v", err)
	}
	if !d.World.IsSitting(self.Coach.ID) {
		t.Error("the coach is not marked sitting")
	}
	// Assert WHICH list the id lands in, not merely that a frame arrived.
	// Swapping the two lists still sends a well-formed 4601 - it just makes the
	// client stand the coach up instead of seating it.
	for _, tc := range []struct {
		name string
		sess *Session
	}{{"watcher", near}, {"sitter", self}} {
		p := drainPayload(t, tc.sess, protocol.OpSitStand)
		if p == nil {
			t.Errorf("%s got no 4601", tc.name)
			continue
		}
		sit, stand := decodeSitStand(t, p)
		if len(sit) != 1 || sit[0] != int64(self.Coach.ID) {
			t.Errorf("%s: sitting list = %v, want [%d] - /sit must put the coach in "+
				"the SITTING list", tc.name, sit, self.Coach.ID)
		}
		if len(stand) != 0 {
			t.Errorf("%s: standing list = %v, want empty", tc.name, stand)
		}
	}
}

// TestSitIsIdempotent: sitting twice must not emit a second 4601. The client
// would re-trigger AnimAssis-Debut and the coach would visibly re-seat itself.
func TestSitIsIdempotent(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	s := tmSession(d, 1, "Sitter")
	d.World.Add(&Online{Coach: s.Coach, Session: s})

	_ = s.setSitting(true)
	_ = drainPayload(t, s, protocol.OpSitStand) // consume the first
	_ = s.setSitting(true)
	if p := drainPayload(t, s, protocol.OpSitStand); p != nil {
		t.Error("sitting while already seated emitted a second 4601")
	}
}

// TestSitStandWireShape pins the layout: [i16 n]{i64 sitting}[i16 n]{i64 standing}.
// Standing must land in the SECOND list - swapping them makes a standing coach
// sit down, which is the kind of inversion that looks fine until you see it.
func TestSitStandWireShape(t *testing.T) {
	frame, err := buildSitStand(nil, []int64{42})
	if err != nil {
		t.Fatalf("buildSitStand: %v", err)
	}
	r := protocol.NewReader(frame[4:])
	nSit, _ := r.U16()
	if nSit != 0 {
		t.Fatalf("sitting list has %d entries, want 0 - a stand must not sit anyone", nSit)
	}
	nStand, _ := r.U16()
	if nStand != 1 {
		t.Fatalf("standing list has %d entries, want 1", nStand)
	}
	id, _ := r.I64()
	if id != 42 {
		t.Errorf("standing id = %d, want 42", id)
	}
}

// TestPlayerCommandVerbAllowList: /sit and /stand must work for a NON-admin, and
// nothing else may slip past the admin gate through this door.
func TestPlayerCommandVerbAllowList(t *testing.T) {
	for _, tc := range []struct{ line, want string }{
		{"/sit", "SIT"},
		{"/STAND", "STAND"},
		{"  /Sit  ", "SIT"},
		{"/announce hello", ""},
		{"/tp 1 2", ""},
		{"/", ""},
		{"hello", ""},
	} {
		if got := playerCommandVerb(tc.line); got != tc.want {
			t.Errorf("playerCommandVerb(%q) = %q, want %q", tc.line, got, tc.want)
		}
	}
}

// decodeSitStand splits a 4601 payload into its two id lists.
func decodeSitStand(t *testing.T, payload []byte) (sitting, standing []int64) {
	t.Helper()
	r := protocol.NewReader(payload)
	n, err := r.U16()
	if err != nil {
		t.Fatalf("sitting count: %v", err)
	}
	for i := 0; i < int(n); i++ {
		id, err := r.I64()
		if err != nil {
			t.Fatalf("sitting id %d: %v", i, err)
		}
		sitting = append(sitting, id)
	}
	m, err := r.U16()
	if err != nil {
		t.Fatalf("standing count: %v", err)
	}
	for i := 0; i < int(m); i++ {
		id, err := r.I64()
		if err != nil {
			t.Fatalf("standing id %d: %v", i, err)
		}
		standing = append(standing, id)
	}
	return sitting, standing
}

// TestStandPutsCoachInTheStandingList is the mirror of the assertion above: a
// /stand must use the SECOND list. Together they pin the inversion from both
// sides, which one test alone cannot do.
func TestStandPutsCoachInTheStandingList(t *testing.T) {
	d := tmDeps()
	d.Log = testLogger()
	s := tmSession(d, 1, "Stander")
	d.World.Add(&Online{Coach: s.Coach, Session: s})
	_ = s.setSitting(true)
	_ = drainPayload(t, s, protocol.OpSitStand)

	if err := s.setSitting(false); err != nil {
		t.Fatalf("setSitting(false): %v", err)
	}
	p := drainPayload(t, s, protocol.OpSitStand)
	if p == nil {
		t.Fatal("no 4601 for /stand")
	}
	sit, stand := decodeSitStand(t, p)
	if len(stand) != 1 || stand[0] != int64(s.Coach.ID) {
		t.Errorf("standing list = %v, want [%d]", stand, s.Coach.ID)
	}
	if len(sit) != 0 {
		t.Errorf("sitting list = %v, want empty - a /stand must not seat anyone", sit)
	}
}
