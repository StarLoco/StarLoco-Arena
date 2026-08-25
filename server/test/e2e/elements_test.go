package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// Interactive elements are STREAMED per env chunk, not bulk-sent per world.
//
// The client cannot materialise an element just because we name it: opcode 200
// carries only [instanceId][payload], and the element's TYPE is resolved through a
// registry the CLIENT fills from its own env data, per chunk, registering a
// chunk's definitions as it streams in and dropping them when it unloads. An
// element whose chunk is not loaded is rejected with
// "Aucune définition trouvée pour l'instance d'élement interactif <id>".
//
// The previous version of this test asserted the OPPOSITE — that world 25's spawn
// frame carries the Zaap, both Card Masters and the Fusion altar. It passed for
// months while four of those five were being thrown away by the client, because it
// only ever checked what the server put on the wire. Measured radius: Chebyshev
// chunk distance <= 2, chunks are 18 cells. See BUGS.md B-108.

// readElementSpawn decodes a 200 frame: [i16 count]{[i64 id][i16 len][payload]}.
func readElementSpawn(t *testing.T, payload []byte) map[int64]bool {
	t.Helper()
	r := testclient.NewR(payload)
	count := int(r.U16())
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
	return got
}

// TestWorldElementsSpawnedOnEntry: entering the overworld must push
// INTERACTIVE_ELEMENT_SPAWN (200) for the elements the client can actually
// resolve from the spawn cell — and must NOT include the ones it cannot, since
// those are dropped for good (nothing re-sends them).
//
// World 25 (Venivici) is the login island. The coach spawns on its Zaap (37) at
// (40,-20); its other five elements are 54-98 cells away, i.e. 3-6 chunks, all
// out of range.
func TestWorldElementsSpawnedOnEntry(t *testing.T) {
	_, addr := testServerWithStore(t)

	// WaitFor immediately after dialLogin: this is a server PUSH, and reachWorld's
	// drain would swallow it.
	a, _ := dialLogin(t, addr, "elem_a", "ElemA")
	f, _, err := a.WaitFor(testclient.OpInteractiveElementSpawn, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no InteractiveElementSpawn(200) on world entry: %v", err)
	}
	got := readElementSpawn(t, f.Payload)

	// The Zaap is on the spawn cell, so it must be there.
	if !got[37] {
		t.Errorf("world 25 spawn is missing the Zaap (37); got %v", got)
	}
	// The far ones must NOT be: sending them is what the client rejects.
	for _, bad := range []struct {
		id   int64
		name string
	}{
		{9, "Card Master (north, 54 cells)"},
		{10, "Card Master (south, 98 cells)"},
		{21, "Mailbox (56 cells)"},
		{103, "Graveyard (65 cells)"},
		{176, "Fusion altar (85 cells)"},
	} {
		if got[bad.id] {
			t.Errorf("world 25 spawn includes %s: the client cannot resolve it from "+
				"the spawn cell and drops it permanently", bad.name)
		}
	}
}

// TestWorldElementsStreamInOnApproach is the other half, and the one that proves
// the streaming actually works rather than just withholding elements: walking
// toward a far element must spawn it.
//
// Uses the GM teleport, which re-enters the world at a chosen cell — the same
// path a Zaap arrival takes. Standing on the graveyard's own cell (4,45) must
// bring the graveyard in and drop the now-distant Zaap.
func TestWorldElementsStreamInOnApproach(t *testing.T) {
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "elem_b", "ElemB")
	reachWorld(t, a)
	a.DrainReceived(300 * time.Millisecond)

	// /WORLD 25 4 45 — onto the graveyard's cell.
	// GM commands ride on vicinity chat (3153, [u16 len]message).
	_ = a.Send(3, 3153, testclient.NewW().StrU16("/WORLD 25 4 45").Bytes())

	f, _, err := a.WaitFor(testclient.OpInteractiveElementSpawn, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no element spawn after moving to the graveyard: %v", err)
	}
	got := readElementSpawn(t, f.Payload)
	if !got[103] {
		t.Errorf("the graveyard (103) did not stream in when the coach stood on its "+
			"own cell; got %v", got)
	}
	// The mailbox is 1 chunk away from there, so it comes along too.
	if !got[21] {
		t.Errorf("the mailbox (21) should be in range from (4,45); got %v", got)
	}
}

// TestWorldElementsResentOnSameWorldReentry: the client clears its element manager
// on EVERY ENTER_INSTANCE, so a re-entry into the world it is ALREADY on must
// re-send the elements. Gating the reset on a world change passes every other test
// here and silently empties the island after any same-world teleport — which is
// what a GM /TP does, and what a Zaap landing on the current island does.
func TestWorldElementsResentOnSameWorldReentry(t *testing.T) {
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "elem_c", "ElemC")
	reachWorld(t, a)
	a.DrainReceived(300 * time.Millisecond)

	// Re-enter the SAME world at the same spot.
	_ = a.Send(3, 3153, testclient.NewW().StrU16("/WORLD 25 40 -20").Bytes())

	f, _, err := a.WaitFor(testclient.OpInteractiveElementSpawn, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("re-entering the same world sent no element spawn: the client has "+
			"already dropped them, so the island is now empty: %v", err)
	}
	if got := readElementSpawn(t, f.Payload); !got[37] {
		t.Errorf("re-entry spawn is missing the Zaap (37); got %v", got)
	}
}

// TestRosterSurvivesAWorldChange: the client throws away the fighter roster and
// the saved team presets on EVERY ENTER_INSTANCE (4600), exactly as it does its
// element manager, and never asks for them again.
//
// Without the server re-sending them, the team panel is empty after any Zaap
// trip or GM teleport - six "Recruter" slots and a budget of 0 - until the
// player relogs, which looks precisely like the team was deleted. Found by
// driving the retail client: the roster was present after login and gone after
// a single /WORLD.
func TestRosterSurvivesAWorldChange(t *testing.T) {
	_, addr := testServerWithStore(t)
	a, _ := dialLogin(t, addr, "roster_w", "RosterW")
	reachWorld(t, a)
	a.DrainReceived(300 * time.Millisecond)

	// GM commands ride on vicinity chat (3153, [u16 len]message).
	_ = a.Send(3, 3153, testclient.NewW().StrU16("/WORLD 25 4 45").Bytes())

	if _, _, err := a.WaitFor(testclient.OpFighterList, testclient.DefaultTimeout); err != nil {
		t.Errorf("no fighter roster (6006) after a world change: the team panel "+
			"stays empty until relog: %v", err)
	}
	if _, _, err := a.WaitFor(testclient.OpTeamPresetList, testclient.DefaultTimeout); err != nil {
		t.Errorf("no team preset list after a world change: the saved teams stay "+
			"missing until relog: %v", err)
	}
}
