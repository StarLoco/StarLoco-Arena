package world

import "testing"

func TestInvitationManagerCreateAndGet(t *testing.T) {
	m := NewInvitationManager()
	inv := m.Create(1, 2, 4, 100)

	got, ok := m.Get(inv.ID)
	if !ok || got != inv {
		t.Fatalf("Get(%d) = %+v, %v", inv.ID, got, ok)
	}
	if inv.InviterID != 1 || inv.TargetID != 2 || inv.Type != 4 || inv.Bet != 100 {
		t.Fatalf("unexpected invitation fields: %+v", inv)
	}
}

func TestInvitationManagerAllocatesUniqueIDs(t *testing.T) {
	m := NewInvitationManager()
	a := m.Create(1, 2, 1, 0)
	b := m.Create(3, 4, 1, 0)
	if a.ID == b.ID {
		t.Errorf("expected unique invitation IDs, got %d twice", a.ID)
	}
}

func TestInvitationInvolvesCoach(t *testing.T) {
	inv := &Invitation{ID: 1, InviterID: 5, TargetID: 9}
	if !inv.InvolvesCoach(5) || !inv.InvolvesCoach(9) {
		t.Errorf("InvolvesCoach should be true for both parties")
	}
	if inv.InvolvesCoach(7) {
		t.Errorf("InvolvesCoach should be false for a third coach")
	}
}

func TestInvitationManagerGetByCoach(t *testing.T) {
	m := NewInvitationManager()
	inv := m.Create(10, 20, 1, 0)

	if got, ok := m.GetByCoach(10); !ok || got != inv {
		t.Errorf("GetByCoach(inviter) = %+v, %v", got, ok)
	}
	if got, ok := m.GetByCoach(20); !ok || got != inv {
		t.Errorf("GetByCoach(target) = %+v, %v", got, ok)
	}
	if _, ok := m.GetByCoach(30); ok {
		t.Errorf("GetByCoach(uninvolved) should be false")
	}
}

func TestInvitationManagerRemove(t *testing.T) {
	m := NewInvitationManager()
	inv := m.Create(1, 2, 1, 0)
	m.Remove(inv.ID)
	if _, ok := m.Get(inv.ID); ok {
		t.Errorf("invitation should be gone after Remove")
	}
	// Removing an unknown id is a no-op (must not panic).
	m.Remove(999)
}

func TestInvitationManagerRemoveByCoach(t *testing.T) {
	m := NewInvitationManager()
	inv := m.Create(1, 2, 1, 0)

	removed := m.RemoveByCoach(2)
	if len(removed) != 1 || removed[0] != inv {
		t.Fatalf("RemoveByCoach = %+v, want [%+v]", removed, inv)
	}
	if _, ok := m.Get(inv.ID); ok {
		t.Errorf("invitation should be gone after RemoveByCoach")
	}
	if got := m.RemoveByCoach(2); got != nil {
		t.Errorf("RemoveByCoach on empty = %+v, want nil", got)
	}
}
