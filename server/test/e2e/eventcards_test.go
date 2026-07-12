package e2e

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

// TestE2E_NewTableTurnCarriesEventCard drives a real fight to the first
// NEW_TABLE_TURN_BEGIN and asserts the server drew a real event card (a
// non-zero eventId on the wire), confirming the per-round event-card system
// is wired end-to-end against the real events.dat. Previously the server
// always sent eventId 0.
func TestE2E_NewTableTurnCarriesEventCard(t *testing.T) {
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

	searchPayload := append([]byte{1}, putInt32(0)...)
	cAlice.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cAlice.expectOpcode(protocol.SendOpponentSearchInProgress)
	cBob.send(2, protocol.RecvOpponentSearchRequest, searchPayload)
	cBob.expectOpcode(protocol.SendOpponentSearchInProgress)

	foundAlice := cAlice.expectOpcode(protocol.SendOpponentFound)
	cBob.expectOpcode(protocol.SendOpponentFound)
	duelID := newPayloadReader(foundAlice).int64()

	readyA := append(putInt64(duelID), 1)
	readyA = append(readyA, putInt64(aliceFighter)...)
	cAlice.send(2, protocol.RecvSetReadyForFight, readyA)
	cAlice.expectOpcode(protocol.SendReadyForFight)
	readyB := append(putInt64(duelID), 1)
	readyB = append(readyB, putInt64(bobFighter)...)
	cBob.send(2, protocol.RecvSetReadyForFight, readyB)
	cBob.expectOpcode(protocol.SendReadyForFight)

	cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	cAlice.expectOpcode(protocol.SendEnterWorldInstance)
	cAlice.expectOpcode(protocol.SendActorAppear)
	cAlice.expectOpcode(protocol.SendStartPresentation)
	cBob.expectOpcode(protocol.SendEnterWorldInstance)
	cBob.expectOpcode(protocol.SendActorAppear)
	cBob.expectOpcode(protocol.SendStartPresentation)

	cAlice.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)
	cBob.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)

	// Drive to the first NEW_TABLE_TURN_BEGIN (round 1) and read its eventId.
	// Between placement-ready and the table turn come END_PRESENTATION,
	// START/END_PLACEMENT, START/END_OBSERVATION, START_ACTION -- give a
	// generous frame budget.
	newTableTurn := cAlice.drainUntil(protocol.SendNewTableTurnBegin, 20)

	// Payload: 8-byte fight-action header + byte numTurns + int32 eventId.
	r := newPayloadReader(newTableTurn)
	r.int32() // uniqueId
	r.int32() // triggeringActionUniqueId
	numTurns := r.byte_()
	eventID := r.int32()

	if numTurns != 1 {
		t.Errorf("first table-turn numTurns = %d, want 1", numTurns)
	}
	if eventID == 0 {
		t.Errorf("NEW_TABLE_TURN_BEGIN eventId = 0, want a real drawn event card (>0) from events.dat")
	}
}
