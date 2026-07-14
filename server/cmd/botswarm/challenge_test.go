package main

import (
	"testing"

	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/protocol"
)

// fightInvitationPayload builds a FIGHT_INVITATION (4300) body the way the
// server does (packets_invitation.go buildFightInvitation): the only field
// the bot reads is the leading int64 invitationId.
func fightInvitationPayload(invitationID int64, inviterCoachID int64, name string) []byte {
	w := protocol.NewWriter(32)
	w.PutInt64(invitationID)
	w.PutByte(0) // inviter flag (0 = we're the target)
	w.PutByte(1) // fight type
	w.PutInt32(0)
	w.PutByte(1)                 // opponent team count
	w.PutByte(1)                 // team id
	w.PutInt64(inviterCoachID)   // leader id
	w.PutByte(1)                 // team mate count
	w.PutInt64(inviterCoachID)   // mate coach id
	w.PutString(name)            // mate name (1-byte length prefix)
	return w.Bytes()
}

func TestPollChallenge_ExtractsInvitationID(t *testing.T) {
	c := botclient.NewFrameTestClient(botclient.Frame{
		Opcode:  protocol.SendFightInvitation,
		Payload: fightInvitationPayload(4242, 7, "Alice"),
	})
	invID, ok := pollChallenge(c)
	if !ok {
		t.Fatal("expected a challenge to be detected")
	}
	if invID != 4242 {
		t.Fatalf("invitation id = %d, want 4242", invID)
	}
}

func TestPollChallenge_SkipsNoiseThenFindsInvitation(t *testing.T) {
	// Interleave broadcast noise before the invitation; pollChallenge must
	// discard the noise and still find the invitation.
	c := botclient.NewFrameTestClient(
		botclient.Frame{Opcode: protocol.SendActorMovement, Payload: []byte{1, 2, 3}},
		botclient.Frame{Opcode: protocol.SendVicinityMessage, Payload: []byte{4, 5}},
		botclient.Frame{Opcode: protocol.SendFightInvitation, Payload: fightInvitationPayload(99, 3, "Bob")},
	)
	invID, ok := pollChallenge(c)
	if !ok || invID != 99 {
		t.Fatalf("pollChallenge = (%d,%v), want (99,true)", invID, ok)
	}
}

func TestPollChallenge_NoneWhenEmpty(t *testing.T) {
	c := botclient.NewFrameTestClient()
	if _, ok := pollChallenge(c); ok {
		t.Fatal("expected no challenge on an empty channel")
	}
}
