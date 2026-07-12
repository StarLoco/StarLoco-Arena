package e2e

import (
	"encoding/json"
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/protocol"
)

// startFullFight drives two connected+logged-in clients through
// matchmaking -> SET_READY_FOR_FIGHT -> CREATE_FIGHT ->
// TEAM_MATE_SET_READY_FOR_PLACEMENT -> START_PRESENTATION, then drains
// both clients' clocked phase transitions (END_PRESENTATION,
// START_PLACEMENT, END_PLACEMENT, START_OBSERVATION, END_OBSERVATION,
// START_ACTION, NEW_TABLE_TURN_BEGIN, FIGHTER_TURN_BEGIN) all the way to
// the first fighter's turn in the ACTION phase. Returns both clients,
// their fighter IDs (aliceFighterID goes first, since fighter creation
// order determines the DB-assigned ID and this test doesn't set explicit
// INIT values -- ties are broken by insertion order per
// docs/opcodes/08-fight-combat-engine.md §1.4, and both fighters share the
// same breed's base INIT), and each fighter's real starting position (as
// broadcast via ACTOR_APPEAR) keyed by fighter ID.
func startFullFight(t *testing.T) (a *app.App, cAlice, cBob *testClient, aliceFighterID, bobFighterID int64, positions map[int64][3]int32, currentFighterID int64) {
	t.Helper()
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice = dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob = dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighterID = createFighter(t, cAlice, "AliceFighter")
	bobFighterID = createFighter(t, cBob, "BobFighter")

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	readyPayloadA := append(putInt64(duelID), 1)
	readyPayloadA = append(readyPayloadA, putInt64(aliceFighterID)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyPayloadA)
	cAlice.expectOpcode(protocol.SendReadyForFight)

	readyPayloadB := append(putInt64(duelID), 1)
	readyPayloadB = append(readyPayloadB, putInt64(bobFighterID)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyPayloadB)
	cBob.expectOpcode(protocol.SendReadyForFight)

	cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	// After CREATE_FIGHT the server IMMEDIATELY teleports + spawns +
	// starts presentation (§8.22 -- there is no separate pre-fight
	// teleport gate). Teleport (ENTER_WORLD_INSTANCE), then ACTOR_APPEAR
	// (spawns both coaches + every fighter), then START_PRESENTATION.
	// Parse ACTOR_APPEAR to capture each fighter's real starting position.
	cAlice.expectOpcode(protocol.SendEnterWorldInstance)
	actorAppear := cAlice.expectOpcode(protocol.SendActorAppear)
	cAlice.expectOpcode(protocol.SendStartPresentation)
	cBob.expectOpcode(protocol.SendEnterWorldInstance)
	cBob.expectOpcode(protocol.SendActorAppear)
	cBob.expectOpcode(protocol.SendStartPresentation)

	positions = parseActorAppearPositions(actorAppear)

	// TEAM_MATE_SET_READY_FOR_PLACEMENT (8011) = the presentation "Prêt"
	// vote. Both coaches vote -> presentation ends immediately (each vote
	// is acked with 8012, then END_PRESENTATION once both arrive). Skip
	// the drained 8012 acks via drainUntil.
	cAlice.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)
	cBob.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)

	// From here, phase transitions after placement are clock-driven (test
	// server uses 2s clocks). Drain down to the first FIGHTER_TURN_BEGIN.
	cAlice.drainUntil(protocol.SendEndPresentation, 6)
	cAlice.expectOpcode(protocol.SendStartPlacement)
	cAlice.expectOpcode(protocol.SendEndPlacement)
	cAlice.expectOpcode(protocol.SendStartObservation)
	cAlice.expectOpcode(protocol.SendEndObservation)
	cAlice.expectOpcode(protocol.SendStartAction)
	cAlice.expectOpcode(protocol.SendNewTableTurnBegin)
	// The round's event card broadcasts its RUNNING_EFFECT_ACTION effects
	// (applied to all fighters) between NEW_TABLE_TURN_BEGIN and the first
	// FIGHTER_TURN_BEGIN, so drain past them rather than expecting the turn
	// begin immediately. See events.go.
	turnBeginAlice := cAlice.drainUntil(protocol.SendFighterTurnBegin, 12)

	cBob.drainUntil(protocol.SendEndPresentation, 6)
	cBob.expectOpcode(protocol.SendStartPlacement)
	cBob.expectOpcode(protocol.SendEndPlacement)
	cBob.expectOpcode(protocol.SendStartObservation)
	cBob.expectOpcode(protocol.SendEndObservation)
	cBob.expectOpcode(protocol.SendStartAction)
	cBob.expectOpcode(protocol.SendNewTableTurnBegin)
	cBob.drainUntil(protocol.SendFighterTurnBegin, 12) // drain past event-card effects

	// FIGHTER_TURN_BEGIN payload: 8-byte fight-action header (uniqueId,
	// triggeringActionUniqueId) + int64 fighterID -- see
	// internal/combat/packets.go's buildFighterTurnBegin. Parse out whose
	// turn it actually is: turn order (INIT-based, ties broken by
	// insertion order) does NOT necessarily put Alice's fighter first,
	// since Duel.CoachAID/CoachBID assignment depends on matchmaking
	// queue order, not which client is "Alice" -- callers needing to act
	// on the current fighter (e.g. movement tests) must use this, not
	// assume aliceFighterID.
	r := newPayloadReader(turnBeginAlice)
	r.skip(8)
	currentFighterID = r.int64()

	// aliceFighterID/bobFighterID are the DB ids returned by createFighter,
	// but every fighter id on the WIRE (ACTOR_APPEAR positions,
	// FIGHTER_TURN_BEGIN's currentFighterID, move/cast requests) carries
	// the FighterWireIDBase offset so fighter ids never collide with coach
	// ids (see §8.18). Return the WIRE ids so callers correlate directly
	// against currentFighterID and the positions map.
	aliceFighterID += combat.FighterWireIDBase
	bobFighterID += combat.FighterWireIDBase

	return a, cAlice, cBob, aliceFighterID, bobFighterID, positions, currentFighterID
}

// parseActorAppearPositions decodes an ACTOR_APPEAR payload (byte count +
// repeated {long id, int worldX, int worldY, short altitude, byte
// direction}) into a map of entity ID -> [x,y,z], ignoring direction.
func parseActorAppearPositions(payload []byte) map[int64][3]int32 {
	r := newPayloadReader(payload)
	count := int(r.byte_())
	out := make(map[int64][3]int32, count)
	for i := 0; i < count; i++ {
		id := r.int64()
		x := r.int32()
		y := r.int32()
		z := r.int16()
		r.skip(1) // direction byte, unused here
		out[id] = [3]int32{x, y, int32(z)}
	}
	return out
}

func TestE2E_FightLifecycleReachesActionPhase(t *testing.T) {
	a, _, _, _, _, _, _ := startFullFight(t)

	// Verify the admin/stats endpoint reports the real active fight
	// (docs/08-java-parity-roadmap.md Phase B: Manager.Count() must
	// reflect real fights instead of always 0).
	adminAddr := a.AdminAddr()
	resp := httpGetWithRetry(t, "http://"+adminAddr+"/stats")
	defer resp.Body.Close()
	var stats struct {
		ActiveFights int `json:"active_fights"`
	}
	if err := json.NewDecoder(resp.Body).Decode(&stats); err != nil {
		t.Fatalf("decode /stats body: %v", err)
	}
	if stats.ActiveFights != 1 {
		t.Errorf("/stats active_fights = %d, want 1", stats.ActiveFights)
	}
}

// TestE2E_CloseCombatRequestDoesNotCrashConnection sends a
// CLOSE_COMBAT_REQUEST (8111) against a non-adjacent target (this test
// suite's simplified free-placement scheme doesn't guarantee adjacency --
// see resolveCoachStartSpots/buildCombatTeam in handlers_fight.go) and
// confirms the server silently rejects it (no crash, no hang, no
// spurious broadcast) rather
// than erroring the connection -- the full damage-dealing path itself is
// covered by the stronger, position-controlled unit test
// TestFight_CloseCombatDealsDamageAndEndsFight in internal/combat.
func TestE2E_CloseCombatRequestDoesNotCrashConnection(t *testing.T) {
	_, cAlice, cBob, aliceFighterID, _, _, _ := startFullFight(t)

	// CLOSE_COMBAT_REQUEST (8111): long fighterId; int32 x,y; short z.
	payload := putInt64(aliceFighterID)
	payload = append(payload, putInt32(0)...)
	payload = append(payload, putInt32(0)...)
	payload = append(payload, putInt16(0)...)
	cAlice.send(3, protocol.RecvCloseCombatRequest, payload)

	// Prove the connection is still healthy by successfully forfeiting
	// afterward and getting a clean END_FIGHT on both sides.
	cAlice.send(3, protocol.RecvGiveUpFightRequest, nil)
	// The fight-end stats hook pushes a PLAYER_STATISTICS_REPORT (2400) to
	// each coach just before END_FIGHT; drain past it.
	cAlice.drainUntil(protocol.SendEndFight, 4)
	cBob.drainUntil(protocol.SendEndFight, 4)
}

// TestE2E_FighterCanMoveFromRealPlacementCell verifies the fix for a
// reported bug: fighters were previously placed at
// (coachAnchor.X, coachAnchor.Y+i, coachAnchor.Z) during team assembly --
// stacked on/adjacent to the coach's own pedestal -- which could put a
// fighter on a cell with no walkable path out at all, permanently
// blocking movement once combat started ("it is blocked"). This test
// drives a real fight to the ACTION phase, takes the current fighter's
// REAL starting position (from ACTOR_APPEAR, i.e. the actual fix under
// test, not a hardcoded assumption), requests a one-step move to an
// adjacent cell, and confirms the server actually broadcasts
// FIGHTER_MOVE (4524) -- i.e. the move was NOT silently rejected by
// FindPath/IsWalkable, proving the fighter's start cell is real,
// walkable, path-reachable map data. See
// docs/08-java-parity-roadmap.md's write-up on this fix.
func TestE2E_FighterCanMoveFromRealPlacementCell(t *testing.T) {
	_, cAlice, cBob, aliceFighterID, bobFighterID, positions, currentFighterID := startFullFight(t)

	// Only the fighter whose turn it currently is may move -- resolve
	// which client owns currentFighterID (turn order is INIT-based, ties
	// broken by insertion order, but does NOT necessarily correspond to
	// which client is "Alice" -- see startFullFight's doc comment).
	// currentFighterID is the WIRE id (DB id + FighterWireIDBase, offset so
	// fighter ids never collide with coach ids -- see §8.18); the
	// aliceFighterID/bobFighterID returned here are also wire ids (parsed
	// from the wire), so they compare directly.
	moverID := currentFighterID
	var moverClient *testClient
	switch currentFighterID {
	case aliceFighterID:
		moverClient = cAlice
	case bobFighterID:
		moverClient = cBob
	default:
		t.Fatalf("currentFighterID %d matches neither aliceFighterID %d nor bobFighterID %d", currentFighterID, aliceFighterID, bobFighterID)
	}
	pos, ok := positions[moverID]
	if !ok {
		t.Fatalf("current fighter %d's position not found in ACTOR_APPEAR positions=%v", moverID, positions)
	}

	// Try each of the 4 legal fight-movement offsets in turn -- the
	// single-axis grid deltas of SOUTH_EAST(+1,0)/SOUTH_WEST(0,+1)/
	// NORTH_WEST(-1,0)/NORTH_EAST(0,-1), matching Point3.Step and
	// fightMoveDirections in internal/combat/pathfind.go. (These look
	// diagonal on the isometric screen but change only one grid axis;
	// two-axis moves are forbidden -- the client resolves them to a
	// cardinal facing with no sprite art, rendering the fighter invisible.)
	// until one actually produces a FIGHTER_MOVE broadcast -- a real
	// placement cell isn't guaranteed to have a walkable neighbor in
	// EVERY direction, but per the real-data test
	// (TestMapStore_RealFightStartCellsDistinctFromCoachAnchor) every
	// FightStartCells() cell does have at least one walkable direction
	// out, so this must succeed at least once. Before this fix, a
	// fighter stuck in an unreachable/off-map pocket would silently
	// reject movement in EVERY direction (FindPath returning nil for
	// all of them), which is exactly what this loop would catch.
	offsets := [][2]int32{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}
	moved := false
	var moveFrame []byte
	for _, off := range offsets {
		destX, destY, destZ := pos[0]+off[0], pos[1]+off[1], int16(pos[2])
		payload := putInt64(moverID)
		payload = append(payload, putInt32(destX)...)
		payload = append(payload, putInt32(destY)...)
		payload = append(payload, putInt16(destZ)...)
		moverClient.send(3, protocol.RecvFighterActorMovementRequest, payload)

		if f, ok := moverClient.tryExpectFrame(protocol.SendFighterMove, 300*time.Millisecond); ok {
			moved = true
			moveFrame = f
			break
		}
	}
	if !moved {
		t.Fatalf("fighter at %v could not move in any of the 4 cardinal directions -- placement cell is an unreachable pocket (the bug this test guards against)", pos)
	}

	// The FIGHTER_MOVE path must START at the fighter's current cell (so the
	// client animates from the origin rather than teleporting a cell) --
	// §8.21. Decode: 8-byte header, int64 fighterId, then (int32 x,y; int16
	// z) cells.
	mr := newPayloadReader(moveFrame)
	mr.skip(8)
	mr.int64() // fighter id
	firstX, firstY := mr.int32(), mr.int32()
	mr.int16()
	if firstX != pos[0] || firstY != pos[1] {
		t.Errorf("FIGHTER_MOVE path[0] = (%d,%d), want the fighter's START cell (%d,%d)", firstX, firstY, pos[0], pos[1])
	}

	// A RUNNING_EFFECT_ACTION (8120, MP_USE) must follow the move to debit
	// the client's PM counter -- without it the counter never decreases and
	// the fighter can move without limit (a reported bug, §8.20). Its
	// payload: 8-byte header, byte mustExecNow, byte triggered, int32
	// runningEffectId (=92 MP_USE), then the effect body. We only assert
	// the opcode arrives and carries runningEffectId 92.
	mpUse := moverClient.expectOpcode(protocol.SendRunningEffectAction)
	r := newPayloadReader(mpUse)
	r.skip(8) // fight-action header
	r.byte_() // mustBeExecutedNow
	r.byte_() // triggered
	if got := r.int32(); got != 92 {
		t.Errorf("post-move RUNNING_EFFECT_ACTION runningEffectId = %d, want 92 (MP_USE)", got)
	}
}

func TestE2E_FightForfeitEndsFight(t *testing.T) {
	_, cAlice, cBob, _, _, _, _ := startFullFight(t)

	// Alice forfeits (GIVE_UP_FIGHT_REQUEST, 8151, empty payload).
	cAlice.send(3, protocol.RecvGiveUpFightRequest, nil)

	// The fight-end stats hook pushes a PLAYER_STATISTICS_REPORT (2400) to
	// each coach just before END_FIGHT; drain past it.
	endFightAlice := cAlice.drainUntil(protocol.SendEndFight, 4)
	endFightBob := cBob.drainUntil(protocol.SendEndFight, 4)

	rA := newPayloadReader(endFightAlice)
	rA.skip(8) // fight-action header
	if flee := rA.byte_(); flee != 1 {
		t.Errorf("Alice END_FIGHT flee = %d, want 1", flee)
	}

	rB := newPayloadReader(endFightBob)
	rB.skip(8)
	if flee := rB.byte_(); flee != 1 {
		t.Errorf("Bob END_FIGHT flee = %d, want 1", flee)
	}

	// Both ack EndFightDoneMessage (4321, empty payload). Once BOTH have
	// acked, the server must send each client back to the overworld
	// (ENTER_WORLD_INSTANCE, map 0) -- otherwise the client dismisses the
	// results popup but stays stuck on the fight map (regression: post-fight
	// return-to-world). Map field is int16 at payload offset 8 (after two
	// float32 world coords).
	cAlice.send(3, protocol.RecvEndFightDone, nil)
	cBob.send(3, protocol.RecvEndFightDone, nil)

	assertReturnedToWorld := func(c *testClient, who string) {
		payload := c.drainUntil(protocol.SendEnterWorldInstance, 8)
		r := newPayloadReader(payload)
		r.skip(4) // worldX float32
		r.skip(4) // worldY float32
		r.skip(2) // worldZ int16
		if mapID := r.int16(); mapID != 0 {
			t.Errorf("%s post-fight ENTER_WORLD_INSTANCE map = %d, want 0 (overworld)", who, mapID)
		}
	}
	assertReturnedToWorld(cAlice, "Alice")
	assertReturnedToWorld(cBob, "Bob")
}
