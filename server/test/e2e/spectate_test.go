package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// Spectator opcodes.
const (
	opSpectateQuery = 2260  // C2S: [i64 coachId] — is this coach spectatable?
	opSpectateReply = 2261  // S2C: [i8 spectatable]
	opSpectateJoin  = 26331 // C2S: [i64 coachId] — join as a spectator
)

// TestSpectateFight drives two coaches into a matchmade fight, then has a THIRD
// client watch it as a spectator over the wire: it queries whether Combatant1 is
// spectatable (2260→2261=1), joins (26331), receives the fight snapshot
// (CREATE_FIGHT 8000 + FIGHTER_TURN_BEGIN 8104), and — when a coach gives up —
// receives END_FIGHT(8300) like the players. It also checks that a coach who is
// NOT in a fight reports not-spectatable (2261=0).
func TestSpectateFight(t *testing.T) {
	t.Parallel()
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(the spectator logic itself is unit-tested race-clean in internal/game)")
	}
	st, addr := testServerWithStore(t)
	a, b := startFightForCombat(t, addr) // Combatant1 (A) vs Combatant2 (B), action phase
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	target, err := st.Coaches.GetByName("Combatant1")
	if err != nil || target == nil {
		t.Fatalf("lookup Combatant1: %v", err)
	}

	// A third client logs in to watch.
	c, specID := dialLogin(t, addr, "spec", "Spectator")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	// Query: a fighting coach is spectatable (2261 = 1).
	_ = c.Send(2, opSpectateQuery, testclient.NewW().I64(int64(target.ID)).Bytes())
	f, _, err := c.WaitFor(opSpectateReply, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no SPECTATE_REPLY(2261): %v", err)
	}
	if got := testclient.NewR(f.Payload).U8(); got != 1 {
		t.Fatalf("Combatant1 should be spectatable, got %d", got)
	}

	// Query: a coach NOT in a fight (the spectator itself) is not spectatable.
	_ = c.Send(2, opSpectateQuery, testclient.NewW().I64(specID).Bytes())
	f, _, err = c.WaitFor(opSpectateReply, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no SPECTATE_REPLY(2261) for non-fighter: %v", err)
	}
	if got := testclient.NewR(f.Payload).U8(); got != 0 {
		t.Fatalf("a non-fighting coach must not be spectatable, got %d", got)
	}

	// Join as a spectator: expect the fight snapshot (CREATE_FIGHT then the current
	// turn), proving the spectator is driven into the live fight.
	_ = c.Send(2, opSpectateJoin, testclient.NewW().I64(int64(target.ID)).Bytes())
	if _, _, err := c.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("spectator no CREATE_FIGHT(8000) snapshot: %v", err)
	}
	if _, _, err := c.WaitFor(testclient.OpFighterTurnBegin, testclient.DefaultTimeout); err != nil {
		t.Fatalf("spectator no FIGHTER_TURN_BEGIN(8104) in the snapshot: %v", err)
	}

	// When a player gives up, the spectator sees END_FIGHT(8300) too.
	_ = a.Send(3, testclient.OpGiveUp, nil)
	if _, _, err := c.WaitFor(testclient.OpEndFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("spectator should receive END_FIGHT(8300) when the fight ends: %v", err)
	}
}
