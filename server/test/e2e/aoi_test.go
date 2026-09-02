package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const (
	opUserVicinity = 3153 // C2S chat
	opVicinity     = 3152 // S2C chat broadcast
	opMoveReq      = 4501 // C2S overworld move request
	opActorSpawn   = 4096 // S2C actor spawn
	opActorDespawn = 4098 // S2C actor despawn
)

// receivedOpWithin reports whether a frame with the given opcode arrives within d.
func receivedOpWithin(c *testclient.Client, opcode uint16, d time.Duration) bool {
	deadline := time.Now().Add(d)
	for time.Now().Before(deadline) {
		f, err := c.Recv(time.Until(deadline))
		if err != nil {
			return false
		}
		if f.Opcode == opcode {
			return true
		}
	}
	return false
}

// moveTo teleports a coach in the overworld by sending a single-step move
// request (movement is unvalidated). The step is [i32 x][i32 y][i16 z].
func moveTo(c *testclient.Client, x, y int32) {
	p := testclient.NewW().I32(x).I32(y).U16(0).Bytes()
	_ = c.Send(3, opMoveReq, p)
}

func sendChat(c *testclient.Client, msg string) {
	p := testclient.NewW().U16(uint16(len(msg))).Raw([]byte(msg)).Bytes()
	_ = c.Send(3, opUserVicinity, p)
}

// receivedVicinityWithin reports whether a vicinity-chat (3152) frame arrives
// within d.
func receivedVicinityWithin(c *testclient.Client, d time.Duration) bool {
	deadline := time.Now().Add(d)
	for time.Now().Before(deadline) {
		f, err := c.Recv(time.Until(deadline))
		if err != nil {
			return false
		}
		if f.Opcode == opVicinity {
			return true
		}
	}
	return false
}

// TestAoIChatScoping: a coach far outside the AoI radius does NOT receive a
// nearby coach's vicinity chat, while a coach close by DOES. Proves overworld
// chat is area-scoped, not a global broadcast.
func TestAoIChatScoping(t *testing.T) {
	t.Parallel()
	addr := testServer(t) // harness uses AoI radius 150

	// Three coaches, all enter at spawn (1,1).
	near, _ := dialLogin(t, addr, "near", "Near")
	reachWorld(t, near)
	far, _ := dialLogin(t, addr, "far", "Far")
	reachWorld(t, far)
	speaker, _ := dialLogin(t, addr, "speaker", "Speaker")
	reachWorld(t, speaker)

	// Move "far" 400 cells away (well beyond the 150 radius). "near" stays at
	// spawn with the speaker.
	moveTo(far, 400, 400)
	time.Sleep(150 * time.Millisecond)

	// Drain any spawn/move traffic so the only thing left to receive is chat.
	near.DrainReceived(150 * time.Millisecond)
	far.DrainReceived(150 * time.Millisecond)

	// Speaker (at spawn) says something.
	sendChat(speaker, "hello neighbours")

	if !receivedVicinityWithin(near, 1500*time.Millisecond) {
		t.Error("NEAR coach should have received the vicinity chat")
	}
	if receivedVicinityWithin(far, 800*time.Millisecond) {
		t.Error("FAR coach should NOT have received the vicinity chat (out of AoI)")
	}
}

// TestDynamicAoISpawnOnApproach: a coach that walks from out-of-range into
// another coach's AoI receives an ACTOR_SPAWN (4096) for them (dynamic
// visibility as actors cross the boundary).
func TestDynamicAoISpawnOnApproach(t *testing.T) {
	t.Parallel()
	addr := testServer(t) // AoI radius 75

	// "resident" stays at spawn (1,1). "walker" moves far away first.
	resident, _ := dialLogin(t, addr, "res", "Resident")
	reachWorld(t, resident)
	walker, _ := dialLogin(t, addr, "walk", "Walker")
	reachWorld(t, walker)

	// Walk out of range (500,500), well beyond radius 75.
	moveTo(walker, 500, 500)
	time.Sleep(150 * time.Millisecond)
	resident.DrainReceived(200 * time.Millisecond)
	walker.DrainReceived(200 * time.Millisecond)

	// Now walk back adjacent to the resident (2,2 — within 75 of 1,1).
	moveTo(walker, 2, 2)

	// The walker should be spawned the resident (entered its AoI), and the
	// resident should be spawned the walker.
	if !receivedOpWithin(walker, opActorSpawn, 1500*time.Millisecond) {
		t.Error("walker should receive ACTOR_SPAWN of the resident on approach")
	}
	if !receivedOpWithin(resident, opActorSpawn, 1500*time.Millisecond) {
		t.Error("resident should receive ACTOR_SPAWN of the walker on approach")
	}
}

// TestDynamicAoIDespawnOnLeave: two adjacent coaches; one walks far away and the
// other receives an ACTOR_DESPAWN (4098) as it leaves the AoI.
func TestDynamicAoIDespawnOnLeave(t *testing.T) {
	t.Parallel()
	addr := testServer(t)

	a, _ := dialLogin(t, addr, "adja", "Adja")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "adjb", "Adjb")
	reachWorld(t, b) // both at spawn (1,1), mutually visible

	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	// B walks far out of range.
	moveTo(b, 600, 600)

	if !receivedOpWithin(a, opActorDespawn, 1500*time.Millisecond) {
		t.Error("A should receive ACTOR_DESPAWN of B when B leaves the AoI")
	}
}
