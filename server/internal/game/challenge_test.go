package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

func TestChallengeManager(t *testing.T) {
	mk := func(id uint, name string) *Session {
		return &Session{Coach: &domain.Coach{ID: id, Name: name}}
	}
	m := NewChallengeManager()
	a, b, cc := mk(1, "A"), mk(2, "B"), mk(3, "C")

	// Create A→B; the handle is the challenger id and both coaches map to it.
	c := m.Create(a, b, false)
	if c == nil || c.id != 1 {
		t.Fatalf("Create: got %v", c)
	}
	if m.Get(1) != c || m.Get(2) != c {
		t.Fatal("both coaches should map to the challenge")
	}
	// Either coach being busy refuses a new challenge.
	if m.Create(a, cc, false) != nil {
		t.Error("challenger already busy — should refuse")
	}
	if m.Create(cc, b, false) != nil {
		t.Error("target already busy — should refuse")
	}

	// The challenger can't accept its own invite; only the target can.
	if m.Accept(1) != nil {
		t.Error("challenger must not accept its own invite")
	}
	if m.Accept(2) != c || !c.accepted {
		t.Fatal("target accept should mark accepted")
	}
	if m.Accept(2) != nil {
		t.Error("double accept should return nil")
	}

	// Team confirms: one side alone doesn't start; both does, and clears the entry.
	if _, both := m.ConfirmTeam(1, 5); both {
		t.Error("one confirm must not start the fight")
	}
	got, both := m.ConfirmTeam(2, 7)
	if got != c || !both {
		t.Fatal("both confirms should report ready")
	}
	if c.teamChallenger != 5 || c.teamTarget != 7 {
		t.Errorf("team ids = %d/%d, want 5/7", c.teamChallenger, c.teamTarget)
	}
	if m.Get(1) != nil || m.Get(2) != nil {
		t.Error("challenge should be cleared once both confirm")
	}

	// Remove returns the challenge and unmaps both coaches.
	c = m.Create(a, b, false)
	if m.Remove(1) != c {
		t.Fatal("Remove should return the challenge")
	}
	if m.Get(1) != nil || m.Get(2) != nil {
		t.Error("Remove should unmap both coaches")
	}

	// other() + evolution flag.
	c = m.Create(a, b, true)
	if c.other(1) != b || c.other(2) != a {
		t.Error("other() should return the far side")
	}
	if !c.evolution {
		t.Error("evolution flag not carried")
	}
	// A confirm before acceptance is a no-op.
	if got, both := m.ConfirmTeam(1, 1); got != nil || both {
		t.Error("confirm before acceptance must be a no-op")
	}
}
