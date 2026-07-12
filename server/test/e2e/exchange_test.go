package e2e

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

func TestE2E_ItemExchangeFullFlow(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	aliceCoachID := cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	bobCoachID := cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	// Give Alice a card to offer, directly via the service layer (there's
	// no wire opcode for "grant card" in this protocol -- items are
	// presumably granted by other game systems out of scope here).
	ctx := context.Background()
	card, err := a.Deps.Coach.AddCard(ctx, uint(aliceCoachID), 42, 5, 0)
	if err != nil {
		t.Fatalf("AddCard: %v", err)
	}

	// ITEM_EXCHANGE_INVITATION_REQUEST: long(targetCoachId)
	cAlice.send(5, protocol.RecvItemExchangeInvitationRequest, putInt64(bobCoachID))
	confirmAlice := cAlice.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	rA := newPayloadReader(confirmAlice)
	if rA.byte_() != 0 { // pending
		t.Error("Alice's initial confirmation should be 'pending' (0)")
	}

	inviteBob := cBob.expectOpcode(protocol.SendItemExchangeInvitationRequest)
	rB := newPayloadReader(inviteBob)
	exchangeID := rB.int64()
	fromID := rB.int64()
	fromName := rB.string_()
	if fromID != aliceCoachID || fromName != "Alice" {
		t.Errorf("invite from=%d (%q), want %d (Alice)", fromID, fromName, aliceCoachID)
	}

	// ITEM_EXCHANGE_INVITATION_ANSWER: long(exchangeId) byte(0=yes)
	answerPayload := append(putInt64(exchangeID), 0)
	cBob.send(5, protocol.RecvItemExchangeInvitationAnswer, answerPayload)

	confirmBob := cBob.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	rcB := newPayloadReader(confirmBob)
	if rcB.byte_() != 3 { // accepted
		t.Error("Bob's confirmation should be 'accepted' (3)")
	}
	confirmAlice2 := cAlice.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	rcA := newPayloadReader(confirmAlice2)
	if rcA.byte_() != 3 {
		t.Error("Alice's second confirmation should be 'accepted' (3)")
	}

	// ITEM_EXCHANGE_ADD_CARD: long(exchangeId) long(cardId) short(quantity)
	addPayload := append(putInt64(exchangeID), putInt64(int64(card.ID))...)
	addPayload = append(addPayload, putInt16(3)...)
	cAlice.send(5, protocol.RecvItemExchangeAddCard, addPayload)

	addedAlice := cAlice.expectOpcode(protocol.SendItemExchangeCardAdded)
	addedBob := cBob.expectOpcode(protocol.SendItemExchangeCardAdded)
	raA := newPayloadReader(addedAlice)
	raA.int64() // exchangeId
	if raA.byte_() != 0 {
		t.Error("card-added side byte should be 0 for the 'from' side")
	}
	raB := newPayloadReader(addedBob)
	raB.int64()
	if raB.byte_() != 0 {
		t.Error("Bob should also see side=0 (from Alice)")
	}

	// ITEM_EXCHANGE_SET_READY from both sides.
	cAlice.send(5, protocol.RecvItemExchangeSetReady, putInt64(exchangeID))
	cAlice.expectOpcode(protocol.SendItemExchangeUserReady)
	cBob.expectOpcode(protocol.SendItemExchangeUserReady)

	cBob.send(5, protocol.RecvItemExchangeSetReady, putInt64(exchangeID))
	cAlice.expectOpcode(protocol.SendItemExchangeUserReady)
	cBob.expectOpcode(protocol.SendItemExchangeUserReady)

	// Once both ready, the trade completes: Alice should see her card
	// removed from inventory, Bob should see it added.
	aliceUpdate := cAlice.expectOpcode(protocol.SendCoachInventoryUpdateMessage)
	bobUpdate := cBob.expectOpcode(protocol.SendCoachInventoryUpdateMessage)

	if len(aliceUpdate) == 0 {
		t.Error("Alice should receive a non-empty inventory update (card removed)")
	}
	if len(bobUpdate) == 0 {
		t.Error("Bob should receive a non-empty inventory update (card added)")
	}

}

// TestE2E_ItemExchangeAnswerUsesClientBuggyExchangeId reproduces the real
// official client's behavior: ItemExchanger.java/CardTrade.java initialize
// the ItemExchanger's internal ID from the *requester's coachID*
// (`super(userRequesting.getId())` in CardTrade's constructor), NOT from
// the real exchangeId the server generated and sent in
// ITEM_EXCHANGE_INVITATION_REQUEST_MESSAGE/INVITATION_CONFIRMATION_MESSAGE.
// So every subsequent client->server exchange message (answer, add/remove
// card, set ready, cancel) actually carries the requester's coachID in the
// "exchangeId" field, not the real exchange ID. The legacy Java server
// accommodates this by having World.getCoachExchangeById ignore its
// exchangeId parameter and look up by coach instead -- this test ensures
// the Go server does the same (regression test for a bug where the
// handlers used Exchanges.Get(exchangeID) directly, causing the lookup to
// always fail and silently drop every answer/add/remove/ready/cancel).
func TestE2E_ItemExchangeAnswerUsesClientBuggyExchangeId(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	aliceCoachID := cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	bobCoachID := cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	cAlice.send(5, protocol.RecvItemExchangeInvitationRequest, putInt64(bobCoachID))
	cAlice.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	cBob.expectOpcode(protocol.SendItemExchangeInvitationRequest)
	// Deliberately ignore the real exchangeId from the invite frame and
	// instead send Alice's coachID, exactly like the real client does.
	answerPayload := append(putInt64(aliceCoachID), 0)
	cBob.send(5, protocol.RecvItemExchangeInvitationAnswer, answerPayload)

	confirmBob := cBob.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	rcB := newPayloadReader(confirmBob)
	if rcB.byte_() != 3 { // accepted
		t.Error("Bob's confirmation should be 'accepted' (3) even with the client's buggy exchangeId")
	}
	confirmAlice2 := cAlice.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	rcA := newPayloadReader(confirmAlice2)
	if rcA.byte_() != 3 {
		t.Error("Alice should still receive her 'accepted' confirmation")
	}

	// Now cancel using the same buggy coachID-as-exchangeId pattern, and
	// confirm both sides still get ITEM_EXCHANGE_END (i.e. the exchange
	// was found and torn down, rather than silently getting stuck).
	cAlice.send(5, protocol.RecvItemExchangeCancel, putInt64(aliceCoachID))
	cAlice.expectOpcode(protocol.SendItemExchangeEnd)
	cBob.expectOpcode(protocol.SendItemExchangeEnd)
}

// TestE2E_ItemExchangeRejectsConcurrentExchange is the security regression
// guard for the card-duplication vector: a coach already engaged in one
// exchange must be refused a second, concurrent exchange (otherwise the
// same card could be staged in both and committed twice, duplicating it).
func TestE2E_ItemExchangeRejectsConcurrentExchange(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")
	seedAccount(t, a, "carol", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	bobCoachID := cBob.mustLogin("bob", "pw", "Bob")
	cCarol := dialTestClient(t, addr)
	carolCoachID := cCarol.mustLogin("carol", "pw", "Carol")
	// Drain all the login/presence broadcasts Alice receives for Bob and
	// Carol coming online before starting the exchange, so they don't
	// interleave with the exchange confirmations below.
	cAlice.drainUntil(protocol.SendActorSpawn, 10)

	// Alice opens an exchange with Bob (pending).
	cAlice.send(5, protocol.RecvItemExchangeInvitationRequest, putInt64(bobCoachID))
	first := cAlice.drainUntil(protocol.SendItemExchangeInvitationConfirmation, 10)
	if first[0] != 0 { // pending
		t.Fatalf("first exchange confirmation = %d, want 0 (pending)", first[0])
	}
	cBob.drainUntil(protocol.SendItemExchangeInvitationRequest, 10)

	// Alice tries to open a SECOND exchange with Carol while the first is
	// still active -- must be refused (2), not pending.
	cAlice.send(5, protocol.RecvItemExchangeInvitationRequest, putInt64(carolCoachID))
	second := cAlice.drainUntil(protocol.SendItemExchangeInvitationConfirmation, 10)
	if second[0] != 2 { // refused
		t.Errorf("second concurrent exchange confirmation = %d, want 2 (refused)", second[0])
	}
}

func TestE2E_ItemExchangeCancel(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	bobCoachID := cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)
	_ = a

	cAlice.send(5, protocol.RecvItemExchangeInvitationRequest, putInt64(bobCoachID))
	cAlice.expectOpcode(protocol.SendItemExchangeInvitationConfirmation)
	invite := cBob.expectOpcode(protocol.SendItemExchangeInvitationRequest)
	r := newPayloadReader(invite)
	exchangeID := r.int64()

	cAlice.send(5, protocol.RecvItemExchangeCancel, putInt64(exchangeID))
	endAlice := cAlice.expectOpcode(protocol.SendItemExchangeEnd)
	endBob := cBob.expectOpcode(protocol.SendItemExchangeEnd)

	if endAlice[0] != 1 || endBob[0] != 1 {
		t.Error("ITEM_EXCHANGE_END result byte should be 1 for both sides")
	}
}
