package world

import (
	"testing"
	"time"
)

func TestDuelManagerCreateAndGet(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 100)

	got, ok := m.Get(d.ID)
	if !ok || got != d {
		t.Fatalf("Get(%d) = %+v, %v", d.ID, got, ok)
	}
}

func TestDuelManagerCreateAllocatesUniqueIDs(t *testing.T) {
	m := NewDuelManager()
	d1 := m.Create(1, 2, 1, 0)
	d2 := m.Create(3, 4, 1, 0)
	if d1.ID == d2.ID {
		t.Errorf("expected unique duel IDs, got %d twice", d1.ID)
	}
}

func TestDuelOpponentOf(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if got := d.OpponentOf(1); got != 2 {
		t.Errorf("OpponentOf(1) = %d, want 2", got)
	}
	if got := d.OpponentOf(2); got != 1 {
		t.Errorf("OpponentOf(2) = %d, want 1", got)
	}
}

func TestDuelInvolvesCoach(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if !d.InvolvesCoach(1) || !d.InvolvesCoach(2) {
		t.Error("InvolvesCoach should be true for both participants")
	}
	if d.InvolvesCoach(3) {
		t.Error("InvolvesCoach should be false for a non-participant")
	}
}

func TestDuelSetSelectionBecomesReadyOnlyWhenBothSubmit(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if bothReady := d.SetSelection(1, []uint{10, 11}); bothReady {
		t.Error("should not be ready after only one side selects")
	}
	if bothReady := d.SetSelection(2, []uint{20}); !bothReady {
		t.Error("should be ready after both sides select")
	}

	a, b, ok := d.Selections()
	if !ok {
		t.Fatal("Selections() ok = false, want true")
	}
	if len(a.FighterIDs) != 2 || len(b.FighterIDs) != 1 {
		t.Errorf("Selections = %+v, %+v", a, b)
	}
}

func TestDuelMarkPreparedOnlyFirstTimeTrue(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if !d.MarkPrepared() {
		t.Error("first MarkPrepared() should return true")
	}
	if d.MarkPrepared() {
		t.Error("second MarkPrepared() should return false (guards double CREATE_FIGHT send)")
	}
}

func TestDuelSetPlacementReadyRequiresBothCoaches(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if bothReady := d.SetPlacementReady(1); bothReady {
		t.Error("should not be ready after only one side is ready")
	}
	if bothReady := d.SetPlacementReady(2); !bothReady {
		t.Error("should be ready after both sides are ready")
	}
}

func TestDuelManagerGetByCoach(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	got, ok := m.GetByCoach(1)
	if !ok || got.ID != d.ID {
		t.Errorf("GetByCoach(1) = %+v, %v", got, ok)
	}
	got, ok = m.GetByCoach(2)
	if !ok || got.ID != d.ID {
		t.Errorf("GetByCoach(2) = %+v, %v", got, ok)
	}
	if _, ok := m.GetByCoach(999); ok {
		t.Error("GetByCoach for uninvolved coach should return false")
	}
}

func TestDuelManagerRemove(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)
	m.Remove(d.ID)

	if _, ok := m.Get(d.ID); ok {
		t.Error("Get should fail after Remove")
	}
}

func TestDuelSelectedCoachesTracksSubmittedSelections(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if got := d.SelectedCoaches(); len(got) != 0 {
		t.Errorf("SelectedCoaches() before any selection = %v, want empty", got)
	}
	d.SetSelection(1, []uint{10})
	got := d.SelectedCoaches()
	if len(got) != 1 || !got[1] {
		t.Errorf("SelectedCoaches() after coach 1 selects = %v, want {1: true}", got)
	}
	d.SetSelection(2, []uint{20})
	got = d.SelectedCoaches()
	if len(got) != 2 || !got[1] || !got[2] {
		t.Errorf("SelectedCoaches() after both select = %v, want {1: true, 2: true}", got)
	}
}

func TestDuelPlacementReadyCoachesTracksAcks(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if got := d.PlacementReadyCoaches(); len(got) != 0 {
		t.Errorf("PlacementReadyCoaches() before any ack = %v, want empty", got)
	}
	d.SetPlacementReady(1)
	got := d.PlacementReadyCoaches()
	if len(got) != 1 || !got[1] {
		t.Errorf("PlacementReadyCoaches() after coach 1 acks = %v, want {1: true}", got)
	}
}

func TestDuelArmReadyTimerFiresAfterDelay(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	fired := make(chan struct{})
	d.ArmReadyTimer(20*time.Millisecond, func() { close(fired) })

	select {
	case <-fired:
	case <-time.After(2 * time.Second):
		t.Fatal("ArmReadyTimer's callback never fired")
	}
}

func TestDuelCancelReadyTimerPreventsFiring(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	fired := make(chan struct{}, 1)
	d.ArmReadyTimer(20*time.Millisecond, func() { fired <- struct{}{} })
	d.CancelReadyTimer()

	select {
	case <-fired:
		t.Fatal("callback fired despite CancelReadyTimer")
	case <-time.After(100 * time.Millisecond):
		// Expected: no fire.
	}
}

func TestDuelArmReadyTimerReplacesPreviousTimer(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	firstFired := make(chan struct{}, 1)
	d.ArmReadyTimer(20*time.Millisecond, func() { firstFired <- struct{}{} })

	secondFired := make(chan struct{})
	d.ArmReadyTimer(40*time.Millisecond, func() { close(secondFired) })

	select {
	case <-secondFired:
	case <-time.After(2 * time.Second):
		t.Fatal("second ArmReadyTimer's callback never fired")
	}
	select {
	case <-firstFired:
		t.Error("first (superseded) timer's callback fired, want it stopped")
	default:
	}
}

func TestDuelManagerRemoveCancelsReadyTimer(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	fired := make(chan struct{}, 1)
	d.ArmReadyTimer(20*time.Millisecond, func() { fired <- struct{}{} })
	m.Remove(d.ID)

	select {
	case <-fired:
		t.Error("callback fired despite duel being removed")
	case <-time.After(100 * time.Millisecond):
		// Expected: no fire.
	}
}

func TestDuelMarkPresentationStartedOnlyFirstTimeTrue(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if !d.MarkPresentationStarted() {
		t.Error("first MarkPresentationStarted() should return true")
	}
	if d.MarkPresentationStarted() {
		t.Error("second MarkPresentationStarted() should return false")
	}
}

func TestDuelIsPreparedReflectsMarkPrepared(t *testing.T) {
	m := NewDuelManager()
	d := m.Create(1, 2, 1, 0)

	if d.IsPrepared() {
		t.Error("IsPrepared() should be false before MarkPrepared")
	}
	d.MarkPrepared()
	if !d.IsPrepared() {
		t.Error("IsPrepared() should be true after MarkPrepared")
	}
}
