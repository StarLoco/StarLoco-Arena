package e2e

import (
	"testing"
	"time"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/protocol"
)

func TestE2E_MatchmakingFindsOpponentAndCreatesFight(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	bobFighter := createFighter(t, cBob, "BobFighter")

	// OPPONENT_SEARCH_REQUEST: byte(type) int(bet)
	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)

	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	// Both should receive OPPONENT_FOUND with the same duel/fight ID.
	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	foundBob := cBob.expectOpcode(protocol.SendOpponentFound)

	rA := newPayloadReader(foundAlice)
	duelIDAlice := rA.int64()
	rB := newPayloadReader(foundBob)
	duelIDBob := rB.int64()
	if duelIDAlice != duelIDBob {
		t.Fatalf("duel IDs differ: alice=%d bob=%d", duelIDAlice, duelIDBob)
	}

	// SET_READY_FOR_FIGHT: long(duelId) byte(count) [long fighterId]*
	readyPayload := append(putInt64(duelIDAlice), 1)
	readyPayload = append(readyPayload, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyPayload)
	readyResultA := cAlice.expectOpcode(protocol.SendReadyForFight)
	if readyResultA[0] != 0 {
		t.Fatalf("Alice READY_FOR_FIGHT error code = %d", readyResultA[0])
	}

	readyPayloadB := append(putInt64(duelIDBob), 1)
	readyPayloadB = append(readyPayloadB, putInt64(bobFighter)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyPayloadB)
	readyResultB := cBob.expectOpcode(protocol.SendReadyForFight)
	if readyResultB[0] != 0 {
		t.Fatalf("Bob READY_FOR_FIGHT error code = %d", readyResultB[0])
	}

	// Once both are ready, CREATE_FIGHT should be broadcast to both.
	createFightAlice := cAlice.expectOpcode(protocol.SendCreateFight)
	createFightBob := cBob.expectOpcode(protocol.SendCreateFight)

	if createFightAlice[0] != 0 {
		t.Errorf("CREATE_FIGHT error code (Alice) = %d, want 0", createFightAlice[0])
	}
	if createFightBob[0] != 0 {
		t.Errorf("CREATE_FIGHT error code (Bob) = %d, want 0", createFightBob[0])
	}
}

func TestE2E_OpponentSearchCancel(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	c := dialTestClient(t, addr)
	c.mustLogin("alice", "pw", "Alice")

	searchPayload := append([]byte{1}, putInt32(0)...)
	c.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	c.expectOpcode(protocol.SendOpponentSearchInProgress)

	c.send(2, protocol.RecvOpponentSearchCancel, nil)
	c.expectOpcode(protocol.SendOpponentSearchCancelResult)
}

// TestE2E_OpponentSearchErrorOnInvalidRequest exercises the Go-only
// OPPONENT_SEARCH_ERROR (2302) signal, which replaces the previously-silent
// request-validation drops in handleOpponentSearchRequest. See
// docs/opcodes/04-matchmaking-invitation.md.
func TestE2E_OpponentSearchErrorOnInvalidRequest(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	c := dialTestClient(t, addr)
	c.mustLogin("alice", "pw", "Alice")

	// Invalid fightType (only 1 is a legitimate matchmaking type). A valid
	// bet is supplied so this isolates the fightType check specifically.
	badTypePayload := append([]byte{7}, putInt32(0)...)
	c.send(2, protocol.RecvOpponentSearchRequest, badTypePayload)
	got := c.expectOpcode(protocol.SendOpponentSearchError)
	if len(got) != 1 {
		t.Fatalf("OPPONENT_SEARCH_ERROR payload len = %d, want 1", len(got))
	}
	if got[0] != byte(protocol.OpponentSearchErrInvalidParams) {
		t.Fatalf("error code = %d, want %d (InvalidParams)",
			got[0], protocol.OpponentSearchErrInvalidParams)
	}

	// Negative bet is likewise rejected as invalid params.
	negBetPayload := append([]byte{1}, putInt32(-1)...)
	c.send(2, protocol.RecvOpponentSearchRequest, negBetPayload)
	gotNeg := c.expectOpcode(protocol.SendOpponentSearchError)
	if gotNeg[0] != byte(protocol.OpponentSearchErrInvalidParams) {
		t.Fatalf("neg-bet error code = %d, want %d (InvalidParams)",
			gotNeg[0], protocol.OpponentSearchErrInvalidParams)
	}

	// Truncated payload (missing the 4-byte bet) is a bad request.
	c.send(2, protocol.RecvOpponentSearchRequest, []byte{1})
	gotBad := c.expectOpcode(protocol.SendOpponentSearchError)
	if gotBad[0] != byte(protocol.OpponentSearchErrBadRequest) {
		t.Fatalf("truncated error code = %d, want %d (BadRequest)",
			gotBad[0], protocol.OpponentSearchErrBadRequest)
	}
}

// TestE2E_CreateFightIncludesRealFighterLoadout verifies the fix for a
// reported bug: fighters previously showed up in-fight with NO spells or
// equipment at all, even though the player had equipped them via
// UPDATE_FIGHTER_INVENTORY_REQUEST, because buildCreateFight
// (internal/dispatch/packets_fight.go) always hardcoded both blob lengths
// to 0. This drives a real duel through CREATE_FIGHT after equipping a
// real spell/equipment loadout, and confirms the fighter's CREATE_FIGHT
// entry now actually carries that loadout on the wire.
func TestE2E_CreateFightIncludesRealFighterLoadout(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	bobFighter := createFighter(t, cBob, "BobFighter")

	// Equip Alice's fighter with a real spell + equipment loadout (same
	// real IDs used by TestE2E_FighterInventoryUpdateRoundTrip).
	sBlob := spellBlob(realSpellIDs...)
	objBlob := inventoryBlob(realObjectIDs...)
	invPayload := putInt64(aliceFighter)
	invPayload = append(invPayload, putInt16(int16(len(sBlob)))...)
	invPayload = append(invPayload, sBlob...)
	invPayload = append(invPayload, putInt16(int16(len(objBlob)))...)
	invPayload = append(invPayload, objBlob...)
	cAlice.send(3, protocol.RecvFighterUpdateInventoryRequest, invPayload)
	cAlice.expectOpcode(protocol.SendFighterUpdatedInformationInventory)

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	readyPayloadA := append(putInt64(duelID), 1)
	readyPayloadA = append(readyPayloadA, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyPayloadA)
	cAlice.expectOpcode(protocol.SendReadyForFight)

	readyPayloadB := append(putInt64(duelID), 1)
	readyPayloadB = append(readyPayloadB, putInt64(bobFighter)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyPayloadB)
	cBob.expectOpcode(protocol.SendReadyForFight)

	createFightAlice := cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	// Parse CREATE_FIGHT: errorCode, coachCardsBlobLen, fightTypeId, bet,
	// teamCount, then per team: teamId, teamName, coachCount, then per
	// coach: id, name, skin, hair, sex, equipBlobLen(+blob), fighterCount,
	// then per fighter: id, breed, name, sex, skin, spellsBlobLen(+blob),
	// equipmentBlobLen(+blob).
	r := newPayloadReader(createFightAlice)
	r.byte_() // error code
	r.int16() // coach cards blob length (always 0)
	r.int32() // fight type id
	r.int32() // bet
	teamCount := r.byte_()
	if teamCount != 2 {
		t.Fatalf("teamCount = %d, want 2", teamCount)
	}

	var aliceSpells, aliceObjects []int32
	foundAliceFighter := false
	for tIdx := byte(0); tIdx < teamCount; tIdx++ {
		r.byte_()   // team id
		r.string_() // team name
		coachCount := r.byte_()
		for c := byte(0); c < coachCount; c++ {
			r.int64()   // coach id
			r.string_() // coach name
			r.byte_()   // skin
			r.byte_()   // hair
			r.byte_()   // sex
			equipLen := int(uint16(r.int16()))
			r.skip(equipLen)

			fighterCount := r.byte_()
			for f := byte(0); f < fighterCount; f++ {
				fID := r.int64()
				r.byte_() // breed
				nameLen := int(r.byte_())
				r.skip(nameLen)
				r.byte_() // sex
				r.byte_() // skin

				spellsLen := int(uint16(r.int16()))
				spellsBlob := r.buf[r.pos : r.pos+spellsLen]
				r.skip(spellsLen)

				objLen := int(uint16(r.int16()))
				objBlobBack := r.buf[r.pos : r.pos+objLen]
				r.skip(objLen)

				// CREATE_FIGHT carries fighter ids offset by
				// FighterWireIDBase (so fighter ids never collide with
				// coach ids -- see §8.18); aliceFighter is the raw DB id.
				if fID == combat.FighterWireIDBase+aliceFighter {
					foundAliceFighter = true
					aliceSpells = parseSpellBlob(spellsBlob)
					aliceObjects = parseInventoryBlob(objBlobBack)
				}
			}
		}
		r.int16() // statistics report length
		betCardCount := r.byte_()
		r.skip(int(betCardCount) * 4)
	}

	if !foundAliceFighter {
		t.Fatal("Alice's fighter entry not found in CREATE_FIGHT payload")
	}
	assertIDs(t, "CREATE_FIGHT spells", aliceSpells, realSpellIDs)
	assertIDs(t, "CREATE_FIGHT objects", aliceObjects, realObjectIDs)
}

// TestE2E_PresentationReadySkipsPresentationClock verifies the fix for the
// user's request: when both coaches click "Prêt" during the PRESENTATION
// phase (the client re-sends TEAM_MATE_SET_READY_FOR_PLACEMENT / opcode
// 8011 for this), the server ends presentation immediately instead of
// waiting out the full presentation clock. Uses a deliberately LONG
// presentation clock so a pass proves the skip worked, not the timer.
func TestE2E_PresentationReadySkipsPresentationClock(t *testing.T) {
	a, addr := startTestServerConfigured(t, func(cfg *config.Config) {
		cfg.Combat.PresentationClock = 30 * time.Second // must be skipped, not waited out
	})
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	bobFighter := createFighter(t, cBob, "BobFighter")

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	readyPayloadA := append(putInt64(duelID), 1)
	readyPayloadA = append(readyPayloadA, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyPayloadA)
	cAlice.expectOpcode(protocol.SendReadyForFight)
	readyPayloadB := append(putInt64(duelID), 1)
	readyPayloadB = append(readyPayloadB, putInt64(bobFighter)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyPayloadB)
	cBob.expectOpcode(protocol.SendReadyForFight)

	cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	// After CREATE_FIGHT the server IMMEDIATELY teleports + spawns + starts
	// presentation (§8.22 -- no separate teleport gate; CREATE_FIGHT is
	// what makes the client show the presentation VS panel).
	cAlice.expectOpcode(protocol.SendEnterWorldInstance)
	cAlice.expectOpcode(protocol.SendActorAppear)
	cAlice.expectOpcode(protocol.SendStartPresentation)
	cBob.expectOpcode(protocol.SendEnterWorldInstance)
	cBob.expectOpcode(protocol.SendActorAppear)
	cBob.expectOpcode(protocol.SendStartPresentation)

	// 8011 = the presentation VS panel's "Prêt" vote. The client hides its
	// VS dialog only when it receives an 8012 whose coachId equals ITS OWN
	// fighting coach id (NetFightPresentationFrame case 8012), so the ack
	// must carry the sending coach's REAL id. Alice votes first -> Alice
	// must receive an 8012 carrying her own coach id.
	cAlice.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)
	ack := cAlice.expectOpcode(protocol.SendTeamMateSetReadyForPlacementMessage)
	if gotCoach := newPayloadReader(ack).int64(); gotCoach != 1 && gotCoach != 2 {
		t.Errorf("presentation-ready 8012 ack coachId = %d, want a real coach id (1 or 2), not an offset/garbage value", gotCoach)
	}

	cBob.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)

	// Once both voted, presentation must END immediately (the 30s clock is
	// skipped) -> END_PRESENTATION then START_PLACEMENT.
	cAlice.drainUntil(protocol.SendEndPresentation, 6)
	cAlice.expectOpcode(protocol.SendStartPlacement)
	cBob.drainUntil(protocol.SendEndPresentation, 6)
	cBob.expectOpcode(protocol.SendStartPlacement)
}

// TestE2E_SetReadyForFightTimeoutCancelsDuelWhenIncomplete verifies the
// fix for the reported bug: previously, if one coach never sent
// SET_READY_FOR_FIGHT after being matched (OPPONENT_FOUND), the duel
// stalled forever with no server-side counterpart to the client's own
// cosmetic ready-countdown popup. Per project decision (see
// docs/08-java-parity-roadmap.md's write-up on this fix), since there's no
// fighter roster to fall back on for a coach who never selected anything,
// the duel is instead canceled for both sides once the timeout fires.
func TestE2E_SetReadyForFightTimeoutCancelsDuelWhenIncomplete(t *testing.T) {
	a, addr := startTestServerConfigured(t, func(cfg *config.Config) {
		cfg.Combat.MatchReadyClock = 300 * time.Millisecond
	})
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	createFighter(t, cBob, "BobFighter")

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	// Only Alice sends SET_READY_FOR_FIGHT; Bob never does.
	readyPayload := append(putInt64(duelID), 1)
	readyPayload = append(readyPayload, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyPayload)
	cAlice.expectOpcode(protocol.SendReadyForFight)

	// The timeout should fire and cancel the duel for both sides, since
	// Bob never submitted a fighter selection.
	canceledAlice := cAlice.expectOpcode(protocol.SendFightCreationCanceledMessage)
	canceledBob := cBob.expectOpcode(protocol.SendFightCreationCanceledMessage)

	const cancelReasonNoSelectedFighter = 42 // dispatch.CancelReasonNoSelectedFighter
	if reason := canceledAlice[8]; reason != cancelReasonNoSelectedFighter {
		t.Errorf("Alice's cancel reason = %d, want CancelReasonNoSelectedFighter (%d)", reason, cancelReasonNoSelectedFighter)
	}
	if reason := canceledBob[8]; reason != cancelReasonNoSelectedFighter {
		t.Errorf("Bob's cancel reason = %d, want CancelReasonNoSelectedFighter (%d)", reason, cancelReasonNoSelectedFighter)
	}
}

// TestE2E_SetReadyForFightTimeoutIsCanceledByNormalCompletion verifies
// that when both coaches DO send SET_READY_FOR_FIGHT before the timeout
// fires, the timeout is properly canceled and does NOT fire a spurious
// second CREATE_FIGHT/cancel afterwards.
func TestE2E_SetReadyForFightTimeoutIsCanceledByNormalCompletion(t *testing.T) {
	a, addr := startTestServerConfigured(t, func(cfg *config.Config) {
		cfg.Combat.MatchReadyClock = 300 * time.Millisecond
	})
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	bobFighter := createFighter(t, cBob, "BobFighter")

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	readyPayloadA := append(putInt64(duelID), 1)
	readyPayloadA = append(readyPayloadA, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyPayloadA)
	cAlice.expectOpcode(protocol.SendReadyForFight)

	readyPayloadB := append(putInt64(duelID), 1)
	readyPayloadB = append(readyPayloadB, putInt64(bobFighter)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyPayloadB)
	cBob.expectOpcode(protocol.SendReadyForFight)

	cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	// After CREATE_FIGHT the server immediately teleports + spawns + starts
	// presentation (§8.22). Drain that expected sequence...
	cAlice.expectOpcode(protocol.SendEnterWorldInstance)
	cAlice.expectOpcode(protocol.SendActorAppear)
	cAlice.expectOpcode(protocol.SendStartPresentation)

	// ...then confirm the SET_READY_FOR_FIGHT forced-progress timer does
	// NOT fire a spurious extra frame (it was canceled when both readied).
	// The next thing would only be presentation-clock-driven phase changes,
	// which are far beyond this short window (test presentation clock 2s;
	// we check a 600ms window for any UNEXPECTED early frame).
	_ = cAlice.conn.SetReadDeadline(time.Now().Add(600 * time.Millisecond))
	if op, _, err := cAlice.recvFrame(); err == nil {
		t.Errorf("unexpected extra frame (opcode %d) arrived shortly after presentation start -- the SET_READY_FOR_FIGHT timer should have been canceled", op)
	}
	_ = cAlice.conn.SetReadDeadline(time.Now().Add(10 * time.Second))
}

// TestE2E_PresentationReadyTimeoutForcesEnd verifies the fallback: after
// CREATE_FIGHT the server immediately teleports + starts presentation
// (§8.22); if only ONE coach clicks "Prêt" (sends 8011) and the other
// never does, the presentation-ready forced-progress timer fires and
// force-ends presentation (-> END_PRESENTATION + START_PLACEMENT) rather
// than the fight stalling. (The combat.Fight's own presentation clock is
// a second, independent fallback; this timer just makes it prompt.)
func TestE2E_PresentationReadyTimeoutForcesEnd(t *testing.T) {
	a, addr := startTestServerConfigured(t, func(cfg *config.Config) {
		cfg.Combat.PlacementReadyClock = 300 * time.Millisecond
		cfg.Combat.PresentationClock = 30 * time.Second // must be beaten by the ready-timeout
	})
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	bobFighter := createFighter(t, cBob, "BobFighter")

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	readyPayloadA := append(putInt64(duelID), 1)
	readyPayloadA = append(readyPayloadA, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyPayloadA)
	cAlice.expectOpcode(protocol.SendReadyForFight)

	readyPayloadB := append(putInt64(duelID), 1)
	readyPayloadB = append(readyPayloadB, putInt64(bobFighter)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyPayloadB)
	cBob.expectOpcode(protocol.SendReadyForFight)

	cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	// Presentation starts immediately after CREATE_FIGHT.
	cAlice.expectOpcode(protocol.SendEnterWorldInstance)
	cAlice.expectOpcode(protocol.SendActorAppear)
	cAlice.expectOpcode(protocol.SendStartPresentation)
	cBob.expectOpcode(protocol.SendEnterWorldInstance)
	cBob.expectOpcode(protocol.SendActorAppear)
	cBob.expectOpcode(protocol.SendStartPresentation)

	// Only Alice clicks "Prêt"; Bob never does. The presentation-ready
	// timeout (300ms) must force presentation to END despite the 30s
	// presentation clock.
	cAlice.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)

	cAlice.drainUntil(protocol.SendEndPresentation, 6)
	cAlice.expectOpcode(protocol.SendStartPlacement)
	cBob.drainUntil(protocol.SendEndPresentation, 6)
	cBob.expectOpcode(protocol.SendStartPlacement)
}
