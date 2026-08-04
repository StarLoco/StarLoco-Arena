package e2e

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestWorldElementsSpawnedOnEntry: entering the overworld must push
// INTERACTIVE_ELEMENT_SPAWN (200) listing that island's interactive elements.
//
// This is not optional decoration: the client CLEARS its element manager on every
// ENTER_INSTANCE (4600), so without this frame the island has no Zaap, no Card
// Master and no Fusion altar — nothing is clickable. It also must not be driven
// from the client's 4517 ack, which stops arriving once criterion 229 is set.
func TestWorldElementsSpawnedOnEntry(t *testing.T) {
	_, addr := testServerWithStore(t)

	// WaitFor immediately after dialLogin: this is a server PUSH, and reachWorld's
	// drain would swallow it.
	a, _ := dialLogin(t, addr, "elem_a", "ElemA")
	f, _, err := a.WaitFor(testclient.OpInteractiveElementSpawn, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no InteractiveElementSpawn(200) on world entry: %v", err)
	}

	// Layout: [i16 count]{[i64 instanceId][i16 payloadLen][payload]}.
	r := testclient.NewR(f.Payload)
	count := int(r.U16())
	if count == 0 {
		t.Fatal("element spawn carried 0 elements")
	}
	got := make(map[int64]bool, count)
	for i := 0; i < count; i++ {
		id := r.I64()
		blobLen := int(r.U16())
		if blobLen == 0 {
			t.Errorf("element %d: empty payload blob", id)
		}
		if r.Remaining() < blobLen {
			t.Fatalf("element %d: payload truncated (want %d, have %d)", id, blobLen, r.Remaining())
		}
		_ = r.RawN(blobLen)
		got[id] = true
	}
	if r.Remaining() != 0 {
		t.Errorf("%d trailing bytes after %d elements", r.Remaining(), count)
	}

	// The login island is world 25 (Venivici): its Zaap, both Card Masters and its
	// Fusion altar must all be present.
	for _, want := range []struct {
		id   int64
		name string
	}{
		{37, "Zaap"},
		{9, "Card Master (north)"},
		{10, "Card Master (south)"},
		{176, "Fusion altar"},
	} {
		if !got[want.id] {
			t.Errorf("world 25 spawn is missing %s (instanceId %d); got %v", want.name, want.id, got)
		}
	}
}
