package e2e

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

func TestE2E_AddFriendAndPrivateMessage(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")

	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	// Bob's login triggers an ACTOR_SPAWN broadcast to Alice too; drain it
	// so it doesn't interfere with subsequent expectOpcode calls.
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	// ADD_FRIEND_MESSAGE
	cAlice.send(4, protocol.RecvAddFriendMessage, pstring("Bob"))
	result := cAlice.expectOpcode(protocol.SendFriendAddedMessage)
	r := newPayloadReader(result)
	name := r.string_()
	if name != "Bob" {
		t.Errorf("FRIEND_ADDED_MESSAGE name = %q, want %q", name, "Bob")
	}

	// PRIVATE_MESSAGE: Alice -> Bob
	msgPayload := append(pstring("Bob"), pstring("hello there")...)
	cAlice.send(4, protocol.RecvPrivateMessage, msgPayload)

	received := cBob.expectOpcode(protocol.SendPrivateMessage)
	rr := newPayloadReader(received)
	senderName := rr.string_()
	rr.int64() // sender coach id
	message := rr.string_()
	if senderName != "Alice" || message != "hello there" {
		t.Errorf("PRIVATE_MESSAGE sender=%q message=%q", senderName, message)
	}
}

func TestE2E_PrivateMessageToUnknownUserFails(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	c := dialTestClient(t, addr)
	c.mustLogin("alice", "pw", "Alice")

	msgPayload := append(pstring("Nobody"), pstring("hi")...)
	c.send(4, protocol.RecvPrivateMessage, msgPayload)

	result := c.expectOpcode(protocol.SendUserNotFound)
	r := newPayloadReader(result)
	if r.string_() != "Nobody" {
		t.Error("USER_NOT_FOUND should echo back the target name")
	}
}

func TestE2E_VicinityMessageBroadcast(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	// VICINITY_MESSAGE uses a 2-byte length prefix (unlike most chat
	// opcodes), see docs/02-protocol.md.
	msg := "hello everyone"
	payload := putInt16(int16(len(msg)))
	payload = append(payload, msg...)
	cAlice.send(4, protocol.RecvVicinityMessage, payload)

	// Bob (a different connection) should receive it; Alice (the sender)
	// should not receive her own message back.
	received := cBob.expectOpcode(protocol.SendVicinityMessage)
	r := newPayloadReader(received)
	senderName := r.string_()
	r.int64()
	message := r.string_()
	if senderName != "Alice" || message != msg {
		t.Errorf("VICINITY_MESSAGE sender=%q message=%q", senderName, message)
	}
}

func TestE2E_AddIgnoreAndRemove(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	cAlice.send(4, protocol.RecvAddIgnoreMessage, pstring("Bob"))
	result := cAlice.expectOpcode(protocol.SendIgnoreAddedMessage)
	r := newPayloadReader(result)
	if r.string_() != "Bob" {
		t.Error("IGNORE_ADDED_MESSAGE should echo back the target name")
	}

	cAlice.send(4, protocol.RecvRemoveIgnoreMessage, pstring("Bob"))
	result = cAlice.expectOpcode(protocol.SendIgnoreRemovedMessage)
	r = newPayloadReader(result)
	if r.string_() != "Bob" {
		t.Error("IGNORE_REMOVED_MESSAGE should echo back the target name")
	}
}
