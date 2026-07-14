package world

import (
	"net"
	"testing"

	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/netio"
)

// newTestSession creates a netio.Session backed by an in-memory net.Pipe,
// with no goroutines pumping it -- sufficient for tests that only care
// about registry bookkeeping, not actual byte transmission.
func newTestSession(t *testing.T) *netio.Session {
	t.Helper()
	client, server := net.Pipe()
	t.Cleanup(func() {
		_ = client.Close()
		_ = server.Close()
	})
	return netio.NewSession(server)
}

func newTestOnlineCoach(t *testing.T, id uint, name string) *OnlineCoach {
	t.Helper()
	return &OnlineCoach{
		Coach:   &domain.Coach{ID: id, Name: name},
		Session: newTestSession(t),
	}
}

func TestRegistryInFightFilteringExcludesFightingCoaches(t *testing.T) {
	r := NewRegistry()
	alice := newTestOnlineCoach(t, 1, "Alice")
	bob := newTestOnlineCoach(t, 2, "Bob")
	carol := newTestOnlineCoach(t, 3, "Carol")
	for _, oc := range []*OnlineCoach{alice, bob, carol} {
		if !r.Add(oc) {
			t.Fatalf("Add(%d) failed", oc.ID())
		}
	}

	// Everyone starts on the overworld.
	if got := len(r.SnapshotWorld()); got != 3 {
		t.Fatalf("SnapshotWorld() = %d, want 3", got)
	}

	// Bob enters a fight.
	r.SetInFight(2, true)
	if !bob.InFight() {
		t.Fatal("Bob should be marked in-fight")
	}

	world := r.SnapshotWorld()
	if len(world) != 2 {
		t.Fatalf("SnapshotWorld() = %d, want 2 (Bob excluded)", len(world))
	}
	for _, oc := range world {
		if oc.ID() == 2 {
			t.Fatal("SnapshotWorld() must not include the in-fight coach")
		}
	}

	// SnapshotWorldWithout also excludes the fighting coach and the given id.
	if got := len(r.SnapshotWorldWithout(1)); got != 1 { // only Carol
		t.Fatalf("SnapshotWorldWithout(1) = %d, want 1 (only Carol)", got)
	}
	// The views variant likewise excludes Bob.
	views := r.SnapshotWorldViewsWithout(1)
	for _, v := range views {
		if v.ID == 2 {
			t.Fatal("SnapshotWorldViewsWithout must exclude the in-fight coach")
		}
	}
	if len(views) != 1 {
		t.Fatalf("SnapshotWorldViewsWithout(1) = %d, want 1", len(views))
	}

	// Bob returns to the world.
	r.SetInFight(2, false)
	if got := len(r.SnapshotWorld()); got != 3 {
		t.Fatalf("SnapshotWorld() after return = %d, want 3", got)
	}
}

func TestRegistryAddGetRemove(t *testing.T) {
	r := NewRegistry()
	oc := newTestOnlineCoach(t, 1, "Alice")

	if !r.Add(oc) {
		t.Fatal("Add should succeed for a new coach ID")
	}
	if r.Len() != 1 {
		t.Errorf("Len() = %d, want 1", r.Len())
	}

	got, ok := r.Get(1)
	if !ok || got.ID() != 1 {
		t.Errorf("Get(1) = %+v, %v", got, ok)
	}

	r.Remove(1)
	if r.Len() != 0 {
		t.Errorf("Len() after Remove = %d, want 0", r.Len())
	}
	if _, ok := r.Get(1); ok {
		t.Error("Get(1) after Remove should return ok=false")
	}
}

func TestRegistryAddDuplicateRejected(t *testing.T) {
	r := NewRegistry()
	oc1 := newTestOnlineCoach(t, 1, "Alice")
	oc2 := newTestOnlineCoach(t, 1, "AliceDupe")

	if !r.Add(oc1) {
		t.Fatal("first Add should succeed")
	}
	if r.Add(oc2) {
		t.Error("second Add with same coach ID should fail")
	}
	if r.Len() != 1 {
		t.Errorf("Len() = %d, want 1 (duplicate must not be added)", r.Len())
	}
}

func TestRegistryGetByNameCaseInsensitive(t *testing.T) {
	r := NewRegistry()
	oc := newTestOnlineCoach(t, 1, "Alice")
	r.Add(oc)

	if _, ok := r.GetByName("alice"); !ok {
		t.Error("GetByName should be case-insensitive")
	}
	if _, ok := r.GetByName("ALICE"); !ok {
		t.Error("GetByName should be case-insensitive")
	}
	if _, ok := r.GetByName("Bob"); ok {
		t.Error("GetByName should not find a non-existent name")
	}
}

func TestRegistryIsOnline(t *testing.T) {
	r := NewRegistry()
	if r.IsOnline(1) {
		t.Error("IsOnline should be false before Add")
	}
	r.Add(newTestOnlineCoach(t, 1, "Alice"))
	if !r.IsOnline(1) {
		t.Error("IsOnline should be true after Add")
	}
}

func TestRegistrySnapshotWithout(t *testing.T) {
	r := NewRegistry()
	r.Add(newTestOnlineCoach(t, 1, "Alice"))
	r.Add(newTestOnlineCoach(t, 2, "Bob"))
	r.Add(newTestOnlineCoach(t, 3, "Carol"))

	others := r.SnapshotWithout(2)
	if len(others) != 2 {
		t.Fatalf("SnapshotWithout(2) len = %d, want 2", len(others))
	}
	for _, oc := range others {
		if oc.ID() == 2 {
			t.Error("SnapshotWithout(2) should not include coach 2")
		}
	}
}

func TestRegistryViewOfCopiesFieldsAndFiltersEquipped(t *testing.T) {
	r := NewRegistry()
	oc := &OnlineCoach{
		Coach: &domain.Coach{
			ID: 1, Name: "Alice",
			Skin: 3, Hair: 4, Sex: 1,
			PosX: 10, PosY: 20, PosZ: -4,
			Strength: 1500, StatFights: 7, StatWins: 5, StatLosses: 2,
			ConsecutiveWins: 3, TimeInFightSecs: 900, TotalPlayTimeSecs: 7200,
			Inventory: []domain.CoachCard{
				{ID: 100, TemplateID: 40, Pos: 1}, // equipped
				{ID: 101, TemplateID: 41, Pos: 0}, // in bag -> excluded
				{ID: 102, TemplateID: 42, Pos: 3}, // equipped
			},
		},
		Session: newTestSession(t),
	}
	r.Add(oc)

	v, ok := r.ViewOf(1)
	if !ok {
		t.Fatal("ViewOf(1) should find the coach")
	}
	if v.ID != 1 || v.Name != "Alice" || v.Skin != 3 || v.Hair != 4 || v.Sex != 1 {
		t.Errorf("view identity/look wrong: %+v", v)
	}
	if v.PosX != 10 || v.PosY != 20 || v.PosZ != -4 {
		t.Errorf("view position wrong: %+v", v)
	}
	if v.Strength != 1500 || v.StatFights != 7 || v.StatWins != 5 || v.StatLosses != 2 ||
		v.ConsecutiveWins != 3 || v.TimeInFightSecs != 900 || v.TotalPlayTimeSecs != 7200 {
		t.Errorf("view stats wrong: %+v", v)
	}
	if len(v.Equipped) != 2 {
		t.Fatalf("view Equipped len = %d, want 2 (only Pos!=0 cards)", len(v.Equipped))
	}

	// The Equipped slice must be a COPY: mutating it must not touch the
	// live coach's inventory.
	v.Equipped[0].TemplateID = 999
	if oc.Coach.Inventory[0].TemplateID == 999 {
		t.Error("view Equipped must be a copy, not alias the live inventory slice")
	}

	if _, ok := r.ViewOf(999); ok {
		t.Error("ViewOf of an unknown coach should return ok=false")
	}
}

func TestRegistrySnapshotViewsWithout(t *testing.T) {
	r := NewRegistry()
	r.Add(newTestOnlineCoach(t, 1, "Alice"))
	r.Add(newTestOnlineCoach(t, 2, "Bob"))
	r.Add(newTestOnlineCoach(t, 3, "Carol"))

	views := r.SnapshotViewsWithout(2)
	if len(views) != 2 {
		t.Fatalf("SnapshotViewsWithout(2) len = %d, want 2", len(views))
	}
	for _, v := range views {
		if v.ID == 2 {
			t.Error("SnapshotViewsWithout(2) should not include coach 2")
		}
	}
}

func TestRegistryUpdatePosition(t *testing.T) {
	r := NewRegistry()
	oc := newTestOnlineCoach(t, 1, "Alice")
	r.Add(oc)

	r.UpdatePosition(1, 42, 43, 7)
	if oc.Coach.PosX != 42 || oc.Coach.PosY != 43 || oc.Coach.PosZ != 7 {
		t.Errorf("UpdatePosition did not update the live coach: %+v", oc.Coach)
	}
	v, _ := r.ViewOf(1)
	if v.PosX != 42 || v.PosY != 43 || v.PosZ != 7 {
		t.Errorf("view after UpdatePosition = %+v, want (42,43,7)", v)
	}
	// Offline coach is a no-op, not a panic.
	r.UpdatePosition(999, 1, 1, 1)
}

func TestRegistryUpdateStats(t *testing.T) {
	r := NewRegistry()
	oc := newTestOnlineCoach(t, 1, "Alice")
	r.Add(oc)

	r.UpdateStats(1, &domain.Coach{
		Strength: 2000, StatFights: 10, StatWins: 6, StatLosses: 4,
		ConsecutiveWins: 0, TimeInFightSecs: 1234, TotalPlayTimeSecs: 5678,
	})
	v, _ := r.ViewOf(1)
	if v.Strength != 2000 || v.StatFights != 10 || v.StatWins != 6 || v.StatLosses != 4 ||
		v.ConsecutiveWins != 0 || v.TimeInFightSecs != 1234 || v.TotalPlayTimeSecs != 5678 {
		t.Errorf("stats after UpdateStats = %+v", v)
	}
	r.UpdateStats(999, &domain.Coach{Strength: 1}) // offline: no-op, no panic
}

func TestRegistryUpdateInventory(t *testing.T) {
	r := NewRegistry()
	oc := newTestOnlineCoach(t, 1, "Alice")
	r.Add(oc)

	r.UpdateInventory(1, []domain.CoachCard{
		{ID: 1, TemplateID: 50, Pos: 2}, // equipped
		{ID: 2, TemplateID: 51, Pos: 0}, // bag
	})
	v, _ := r.ViewOf(1)
	if len(v.Equipped) != 1 || v.Equipped[0].TemplateID != 50 {
		t.Errorf("view Equipped after UpdateInventory = %+v, want one card template 50", v.Equipped)
	}
	r.UpdateInventory(999, nil) // offline: no-op, no panic
}

// TestRegistryConcurrentViewAndUpdate is the unit-level regression test for
// the cross-goroutine coach-field data race: it hammers the write path
// (UpdatePosition/UpdateStats/UpdateInventory) and the read path
// (ViewOf/SnapshotViews) on the same coach from many goroutines at once.
// Under `go test -race` this fails loudly if any coach field is accessed
// without going through the registry lock. Before the fix, broadcast
// serializers read the shared *domain.Coach fields directly (no lock),
// racing concurrent fight-end/movement writes; the value-copy views +
// lock-guarded mutators close that.
func TestRegistryConcurrentViewAndUpdate(t *testing.T) {
	r := NewRegistry()
	r.Add(newTestOnlineCoach(t, 1, "Alice"))
	const n = 200
	done := make(chan struct{})

	for i := 0; i < n; i++ {
		go func(k int) {
			defer func() { done <- struct{}{} }()
			switch k % 5 {
			case 0:
				r.UpdatePosition(1, int32(k), int32(k), int16(k))
			case 1:
				r.UpdateStats(1, &domain.Coach{Strength: int32(1000 + k)})
			case 2:
				r.UpdateInventory(1, []domain.CoachCard{{ID: uint(k), TemplateID: int32(k), Pos: 1}})
			case 3:
				_, _ = r.ViewOf(1)
			case 4:
				_ = r.SnapshotViews()
			}
		}(i)
	}
	for i := 0; i < n; i++ {
		<-done
	}
}

func TestRegistryConcurrentAddRemove(t *testing.T) {
	r := NewRegistry()
	const n = 100
	done := make(chan struct{})

	for i := 0; i < n; i++ {
		go func(id uint) {
			defer func() { done <- struct{}{} }()
			oc := newTestOnlineCoach(t, id, "Coach")
			r.Add(oc)
			r.Get(id)
			r.Snapshot()
			r.Remove(id)
		}(uint(i + 1))
	}
	for i := 0; i < n; i++ {
		<-done
	}

	if r.Len() != 0 {
		t.Errorf("Len() after concurrent add/remove = %d, want 0", r.Len())
	}
}
