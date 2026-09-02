package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// combatTestSpellID is the spell the combat e2e fighters are created with. The
// harness has no spell table, so the id resolves to nothing and the cast takes
// castSpellByFighter's unknown-spell fallback (default AP cost, neutral HP
// loss) - which is exactly what these tests assert on. It must still be a spell
// the fighter OWNS.
const combatTestSpellID int32 = 1

// startFightForCombat runs two clients through matchmaking + all phase gates so
// they're both in the action phase, returning both clients.
func startFightForCombat(t *testing.T, addr string) (a, b *testclient.Client) {
	t.Helper()
	a, b = matchIntoFight(t, addr, nil)
	readyGate(a, b, testclient.OpReadyForPlacement)
	readyGate(a, b, testclient.OpReadyForObservation)
	_ = a.Send(3, testclient.OpReadyForAction, nil)
	_ = b.Send(3, testclient.OpReadyForAction, nil)
	return a, b
}

// readyGate sends one phase-ready signal from both clients and drains.
//
// Generous drains: the fight actor processes ready signals asynchronously, and
// under -race everything runs ~10x slower.
func readyGate(a, b *testclient.Client, op uint16) {
	_ = a.Send(3, op, nil)
	_ = b.Send(3, op, nil)
	a.DrainReceived(250 * time.Millisecond)
	b.DrainReceived(250 * time.Millisecond)
}

// matchIntoFight logs two clients in, optionally runs `prepare` on each before
// queueing (to create fighters), and takes them through matchmaking to
// CREATE_FIGHT — stopping BEFORE any phase gate, so a caller can act during
// presentation or placement.
func matchIntoFight(t *testing.T, addr string, prepare func(c *testclient.Client, coachID int64)) (a, b *testclient.Client) {
	t.Helper()
	var aID, bID int64
	a, aID = dialLogin(t, addr, "c1", "Combatant1")
	reachWorld(t, a)
	b, bID = dialLogin(t, addr, "c2", "Combatant2")
	reachWorld(t, b)
	if prepare != nil {
		prepare(a, aID)
		prepare(b, bID)
		a.DrainReceived(150 * time.Millisecond)
		b.DrainReceived(150 * time.Millisecond)
	}

	search := testclient.NewW().U16(1).U16(0).I32(0).Bytes()
	_ = a.Send(2, testclient.OpSearch, search)
	_ = b.Send(2, testclient.OpSearch, search)

	fa, _, err := a.WaitFor(testclient.OpMatchFound, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no match: %v", err)
	}
	_, _, _ = b.WaitFor(testclient.OpMatchFound, testclient.DefaultTimeout)
	matchID := testclient.NewR(fa.Payload).I64()

	acc := testclient.NewW().I64(matchID).I64(0).U16(1).U16(1).I32(0).U8(1).Bytes()
	_ = a.Send(2, testclient.OpMatchAccept, acc)
	_ = b.Send(2, testclient.OpMatchAccept, acc)

	_, _, _ = a.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout)
	_, _, _ = b.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)
	return a, b
}

// TestCombatSpellDamage verifies a full damaging cast against the enemy:
// SPELL_CAST(8110) -> AP debit(8120 id 91) -> HP-loss on the ENEMY fighter
// (8120 id 1 = neutral HP-loss fallback for the unknown test spell) -> flush(8200).
func TestCombatSpellDamage(t *testing.T) {
	t.Parallel()
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(server logic itself is race-clean -- internal/game passes -race)")
	}
	addr := testServer(t)
	// Both coaches field a real fighter that KNOWS combatTestSpellID. Casting
	// works through the unknown-spell fallback (this harness builds Deps with a
	// nil spell table), but the caster must still own the spell — a forged 8109
	// for an id the fighter never equipped is refused, which is the whole point
	// of the ownership check. Before this the fight ran on the synthesized
	// "Champion" placeholder, which owns nothing.
	a, b := matchIntoFight(t, addr, func(c *testclient.Client, _ int64) {
		blob := buildFighterBlob("Caster", 8, combatTestSpellID)
		req := testclient.NewW().U8(0).U16(0).U16(uint16(len(blob))).Raw(blob).Bytes()
		_ = c.Send(3, testclient.OpFighterCreate, req)
		_, _, _ = c.WaitFor(testclient.OpFighterCreateResult, testclient.DefaultTimeout)
	})
	readyGate(a, b, testclient.OpReadyForPlacement)
	readyGate(a, b, testclient.OpReadyForObservation)
	_ = a.Send(3, testclient.OpReadyForAction, nil)
	_ = b.Send(3, testclient.OpReadyForAction, nil)

	// Drive B in the background: drain it (so its write queue never fills and
	// back-pressures the fight actor) AND end its turns immediately.
	//
	// Ending B's turns matters more than it looks. Previously B only drained, so
	// B's turns ended solely by the server's turn CLOCK expiring. That forced the
	// clock to be short (6s) — otherwise A's WaitForTurn(15s) would time out
	// waiting through B — and a short clock is exactly what made this test flaky:
	// on a loaded machine A's own turn could expire before A's cast reached the
	// fight goroutine, and the server (correctly) refuses a cast from a fighter
	// whose turn is over, silently. That produced the rotating "no SPELL_CAST"
	// failure. With B ending its turns promptly the rotation is fast regardless of
	// the clock, so the clock can be generous (see harness_test.go).
	stopB := make(chan struct{})
	go func() {
		for {
			select {
			case <-stopB:
				return
			default:
			}
			f, err := b.Recv(200 * time.Millisecond)
			if err != nil {
				continue
			}
			if f.Opcode == testclient.OpFighterTurnBegin {
				// End every turn we are shown. Ending a turn we do not own is a
				// silent no-op server-side, so this needs no side assumption.
				_ = b.EndTurn(testclient.ParseFighterTurnBegin(f))
			}
		}
	}()
	defer close(stopB)

	// Cast on A's turn and collect the resulting frames. Under CPU load the
	// action phase / turn ordering is timing-sensitive, so we retry across up to
	// a few of A's turns until the damaging cast lands (the server only accepts
	// a cast from the current-turn fighter).
	var sawCast, sawAPUse, sawDamage bool
	var dmgTarget, dmgCaster int64
	var dmgValue int32

	// pendingTurn carries a FIGHTER_TURN_BEGIN that the collect loop below read
	// while draining a cast group. Without it that frame is swallowed and the next
	// WaitForTurn blocks for a whole extra turn — the actual cause of this test's
	// rotating timeouts under load.
	var pendingTurn int64
	for attempt := 0; attempt < 4 && !sawDamage; attempt++ {
		caster := pendingTurn
		pendingTurn = 0
		if caster == 0 {
			var err error
			caster, err = a.WaitForTurn(15 * time.Second)
			if err != nil {
				t.Fatalf("no turn (attempt %d): %v", attempt, err)
			}
		}
		// Cast on EVERY turn we are handed, and aim at whichever side the caster
		// is NOT on.
		//
		// Do not assume "client A == side 0". Side 0 is simply whoever reached the
		// matchmaker queue first (`buildFightTeam(pm.a, 0)`), and this test fires
		// both searches back-to-back without a happens-before, so on a loaded
		// machine B can win the race and A becomes side 1. The old code assumed
		// A was side 0, so when the sides inverted it cast with B's fighter (the
		// server dropped it silently: `caster.CoachID != cid`) and skipped its
		// own — producing the rotating "no SPELL_CAST" failure. A cast for a
		// fighter we do not own is a harmless no-op, so casting on every turn is
		// both simpler and correct whichever side we drew.
		target := enemyStartCell(caster)
		if err := a.CastSpell(caster, combatTestSpellID, target.x, target.y, target.z); err != nil {
			t.Fatalf("cast: %v", err)
		}
		// Collect until the flush barrier (8200) ends the cast group, or the
		// next turn begins.
		deadline := time.Now().Add(4 * time.Second)
	collect:
		for time.Now().Before(deadline) {
			f, err := a.Recv(time.Until(deadline))
			if err != nil {
				break
			}
			switch f.Opcode {
			case testclient.OpSpellCast:
				sawCast = true
			case testclient.OpRunningEffect:
				eff := testclient.ParseRunningEffect(f)
				switch eff.EffectID {
				case 91:
					sawAPUse = true
				case 1: // neutral HP-loss fallback (unknown spell 0)
					sawDamage = true
					dmgTarget = eff.TargetID
					dmgValue = eff.Value
					dmgCaster = caster
				}
			case testclient.OpFighterTurnBegin:
				// The turn moved on while we were draining. Keep the frame for the
				// next attempt instead of dropping it (the loop's stated contract:
				// "collect until the flush barrier OR the next turn begins").
				pendingTurn = testclient.ParseFighterTurnBegin(f)
				break collect
			case 8200: // ActionSequenceExecute = end of the cast group
				break collect
			}
		}
		// End A's turn so the timeline advances back to A next round.
		_ = a.EndTurn(caster)
	}

	if !sawCast {
		t.Error("no SPELL_CAST (8110)")
	}
	if !sawAPUse {
		t.Error("no AP-use RUNNING_EFFECT (id 91)")
	}
	if !sawDamage {
		t.Fatal("no HP-loss RUNNING_EFFECT (id 9) -- damage not applied")
	}
	if dmgValue <= 0 {
		t.Errorf("damage value = %d, want > 0", dmgValue)
	}
	// The damage must land on the ENEMY side — expressed relative to the caster,
	// so it holds whichever side we drew (see the casting loop above).
	if fighterSide(dmgTarget) == fighterSide(dmgCaster) {
		t.Errorf("damage hit the caster's own side: caster %d (side %d), target %d (side %d)",
			dmgCaster, fighterSide(dmgCaster), dmgTarget, fighterSide(dmgTarget))
	}
}

// fighterSide extracts the team side (0 or 1) encoded in a fighter's wire id.
// Wire id = base + fighterID*16 + side*8 + index.
func fighterSide(wireID int64) int {
	const base = int64(1) << 40
	if ((wireID - base) % 16) >= 8 {
		return 1
	}
	return 0
}

// enemyStartCell returns an arena start cell of the side OPPOSITE to the given
// fighter — practiceArena's team0[0] (7,15,0) / team1[0] (6,2,3) from
// internal/game/arena.go. Enemies stand on their start cells until they move,
// so this reliably aims at a live opponent on the first round.
func enemyStartCell(casterWireID int64) cell {
	if fighterSide(casterWireID) == 0 {
		return cell{6, 2, 3} // team1[0]
	}
	return cell{7, 15, 0} // team0[0]
}

// cell is an arena coordinate used by the e2e fight helpers.
type cell struct {
	x, y int32
	z    int16
}

// TestCombatVictoryByForfeit: after entering the action phase, one coach gives
// up and BOTH clients receive END_FIGHT(8300).
func TestCombatVictoryByForfeit(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, b := startFightForCombat(t, addr)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	_ = a.Send(3, testclient.OpGiveUp, nil)

	if _, _, err := a.WaitFor(testclient.OpEndFight, 3*time.Second); err != nil {
		t.Fatalf("A no END_FIGHT: %v", err)
	}
	if _, _, err := b.WaitFor(testclient.OpEndFight, 3*time.Second); err != nil {
		t.Fatalf("B no END_FIGHT: %v", err)
	}
}

// oneStepFromStart returns a cell one step from the given fighter's OWN arena
// start cell: side 0 starts at (7,15,0) and steps north to (7,14,0); side 1
// starts at (6,2,3) and steps south to (6,3,3) (both are walkable team cells in
// practiceArena). Used to exercise a 1-MP move whichever side we drew.
func oneStepFromStart(wireID int64) cell {
	if fighterSide(wireID) == 0 {
		return cell{7, 14, 0}
	}
	return cell{6, 3, 3}
}
