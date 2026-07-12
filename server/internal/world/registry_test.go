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
