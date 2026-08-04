package e2e

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// TestInvalidClientVersionRejected: a client that announces the wrong protocol
// version gets InvalidClientVersion(8) carrying the server's expected version,
// instead of being silently allowed to proceed to auth. (The accept path is
// covered by every other e2e test, which log in with version 70.)
func TestInvalidClientVersionRejected(t *testing.T) {
	addr := testServer(t)
	c, err := testclient.Dial(addr)
	if err != nil {
		t.Fatalf("dial: %v", err)
	}
	defer func() { _ = c.Close() }()

	// Announce version 69 (the client sends 70 for 2.70).
	ver := testclient.NewW().U8(0x02).U16(69).Str8("bad-build").Bytes()
	if err := c.Send(0, testclient.OpClientVersion, ver); err != nil {
		t.Fatalf("send version: %v", err)
	}

	f, _, err := c.WaitFor(testclient.OpInvalidClientVersion, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no InvalidClientVersion(8): %v", err)
	}
	// Payload is the server's EXPECTED version [u8 major][u16 minor] = 2.70.
	r := testclient.NewR(f.Payload)
	if maj := r.U8(); maj != 2 {
		t.Errorf("expected-version major = %d, want 2", maj)
	}
	if min := r.U16(); min != 70 {
		t.Errorf("expected-version minor = %d, want 70", min)
	}
}
