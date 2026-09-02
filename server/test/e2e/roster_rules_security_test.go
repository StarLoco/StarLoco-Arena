package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const opOpponentSearch = 2301

// TestOversizedRosterViaMatchmakingIsRefused drives opcode 2301, the entry that
// B-148 did NOT close.
//
// handleOpponentSearch takes a raw client id list capped only at 64 and hands it
// straight to the matchmaker, which stores it verbatim; buildFightTeamFor then
// fielded all of them. Past i=16 the derived WireID (base + fighterID*16 +
// side*8 + i) collides with another fighter's space and corrupts targeting, HP
// and turn order - reachable against any honest player who pressed "Combattre".
func TestOversizedRosterViaMatchmakingIsRefused(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "bigroster", "BigRoster")
	c.DrainReceived(200 * time.Millisecond)

	var ids []int64
	for i := 0; i < 20; i++ {
		f := &domain.Fighter{CoachID: uint(coachID), BreedID: uint8(i%12 + 1),
			Name: "F" + string(rune('a'+i)), Budget: 100}
		if err := st.Fighters.Create(f); err != nil {
			t.Fatalf("seed fighter %d: %v", i, err)
		}
		ids = append(ids, int64(f.ID))
	}

	// 2301: [i16 mode][i16 subMode][i32 N][i64 x N, reversed]
	w := testclient.NewW().U16(1).U16(0).I32(int32(len(ids)))
	for i := len(ids) - 1; i >= 0; i-- {
		w = w.I64(ids[i])
	}
	_ = c.Send(3, opOpponentSearch, w.Bytes())
	c.DrainReceived(300 * time.Millisecond)

	// A second coach searches; if a fight forms, the attacker's side must not
	// contain 20 fighters. The cleanest observable is that the oversized side is
	// refused outright, so no fight starts for the attacker.
	d, _ := dialLogin(t, addr, "bigroster2", "BigRosterTwo")
	f2 := &domain.Fighter{CoachID: uint(2), BreedID: 1, Name: "Solo", Budget: 100}
	_ = st.Fighters.Create(f2)
	_ = d.Send(3, opOpponentSearch, testclient.NewW().U16(1).U16(0).I32(0).Bytes())
	d.DrainReceived(600 * time.Millisecond)

	// The decisive assertion: no fight exists with a 20-fighter side. We assert
	// via the store-independent fact that the attacker never received a fight
	// start (CREATE_FIGHT). If the roster had been accepted, it would have.
	if gotFrameWithin(c, 4200, 600*time.Millisecond) {
		t.Error("a fight started with an oversized roster (20 fighters)")
	}
}

// gotFrameWithin reports whether opcode op arrives within d.
func gotFrameWithin(c *testclient.Client, op uint16, d time.Duration) bool {
	deadline := time.Now().Add(d)
	for time.Now().Before(deadline) {
		f, err := c.Recv(time.Until(deadline))
		if err != nil {
			return false
		}
		if f.Opcode == op {
			return true
		}
	}
	return false
}

// TestRosterEditIsRefusedWhileQueued covers the TOCTOU bait-and-switch: the
// matchmaker snapshots fighter IDS, but buildFightTeamFor re-reads their STATS at
// fight start - so re-equipping while queued swaps a cheap legal roster for an
// expensive one after pairing, defeating both the budget rule and rating-band
// matchmaking. Retail refused this with code 69.
func TestRosterEditIsRefusedWhileQueued(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "toctou", "Toctou")
	c.DrainReceived(200 * time.Millisecond)

	f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1, Name: "Cheap", Budget: 100}
	if err := st.Fighters.Create(f); err != nil {
		t.Fatalf("seed fighter: %v", err)
	}

	// Queue.
	_ = c.Send(3, opOpponentSearch, testclient.NewW().U16(1).U16(0).I32(1).I64(int64(f.ID)).Bytes())
	c.DrainReceived(300 * time.Millisecond)

	// Now try to re-equip while queued: 6011 with an empty loadout is enough to
	// prove the gate, since a refusal means no write happened.
	before, _ := st.Fighters.Get(f.ID)
	beforeBudget := before.Budget

	payload := testclient.NewW().
		I64(int64(f.ID)).U16(0).U16(0).U16(0).Bytes()
	_ = c.Send(3, opFighterInventoryUpdate, payload)
	c.DrainReceived(400 * time.Millisecond)

	after, err := st.Fighters.Get(f.ID)
	if err != nil {
		t.Fatalf("reload fighter: %v", err)
	}
	if after.Budget != beforeBudget {
		t.Errorf("loadout was rewritten while queued (budget %d -> %d): a queued "+
			"roster can be swapped for an expensive one after pairing",
			beforeBudget, after.Budget)
	}
}
