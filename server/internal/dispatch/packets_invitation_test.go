package dispatch

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

// TestBuildFightInvitation verifies FIGHT_INVITATION (4300) serializes in
// the exact field order the client's FightInvitationMessage.decode expects
// (int64 invitationId, byte inviter, byte fightTypeId, int32 bet, byte
// teamCount, then per team: byte teamId, int64 leaderId, byte mateCount,
// then per mate: int64 coachId, byte nameLen, name bytes).
func TestBuildFightInvitation(t *testing.T) {
	frame := buildFightInvitation(42, false, 4, 100, 1, 7,
		[]invitationTeamMate{{CoachID: 7, Name: "Bob"}})
	if frame.Opcode != protocol.SendFightInvitation {
		t.Fatalf("opcode = %v, want SendFightInvitation", frame.Opcode)
	}

	r := protocol.NewReader(frame.Payload)
	if got := r.Int64(); got != 42 {
		t.Errorf("invitationId = %d, want 42", got)
	}
	if got := r.Byte(); got != 0 {
		t.Errorf("inviter = %d, want 0", got)
	}
	if got := r.Byte(); got != 4 {
		t.Errorf("fightTypeId = %d, want 4", got)
	}
	if got := r.Int32(); got != 100 {
		t.Errorf("bet = %d, want 100", got)
	}
	if got := r.Byte(); got != 1 {
		t.Errorf("teamCount = %d, want 1", got)
	}
	if got := r.Byte(); got != 1 {
		t.Errorf("teamId = %d, want 1", got)
	}
	if got := r.Int64(); got != 7 {
		t.Errorf("leaderId = %d, want 7", got)
	}
	if got := r.Byte(); got != 1 {
		t.Errorf("mateCount = %d, want 1", got)
	}
	if got := r.Int64(); got != 7 {
		t.Errorf("mate coachId = %d, want 7", got)
	}
	if got := r.String(); got != "Bob" {
		t.Errorf("mate name = %q, want %q", got, "Bob")
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}
	if r.Remaining() != 0 {
		t.Errorf("payload has %d trailing bytes, want 0", r.Remaining())
	}
}

// TestBuildFightInvitationInviterFlag verifies the inviter flag serializes
// as 1 when true (drives the client's outgoing-vs-incoming message-box
// styling).
func TestBuildFightInvitationInviterFlag(t *testing.T) {
	frame := buildFightInvitation(1, true, 1, 0, 1, 5,
		[]invitationTeamMate{{CoachID: 5, Name: "A"}})
	r := protocol.NewReader(frame.Payload)
	r.Int64() // invitationId
	if got := r.Byte(); got != 1 {
		t.Errorf("inviter flag = %d, want 1", got)
	}
}

// TestBuildFightInvitationAccepted verifies FIGHT_INVITATION_ACCEPTED
// (4302) carries both the invitationId and the separate fightId, in that
// order (FightInvitationAcceptedMessage.decode).
func TestBuildFightInvitationAccepted(t *testing.T) {
	frame := buildFightInvitationAccepted(42, 900)
	if frame.Opcode != protocol.SendFightInvitationAccepted {
		t.Fatalf("opcode = %v, want SendFightInvitationAccepted", frame.Opcode)
	}
	r := protocol.NewReader(frame.Payload)
	if got := r.Int64(); got != 42 {
		t.Errorf("invitationId = %d, want 42", got)
	}
	if got := r.Int64(); got != 900 {
		t.Errorf("fightId = %d, want 900", got)
	}
	if r.Remaining() != 0 {
		t.Errorf("payload has %d trailing bytes, want 0", r.Remaining())
	}
}

// TestBuildFightInvitationRejected verifies FIGHT_INVITATION_REJECTED
// (4304) carries just the invitationId.
func TestBuildFightInvitationRejected(t *testing.T) {
	frame := buildFightInvitationRejected(42)
	if frame.Opcode != protocol.SendFightInvitationRejected {
		t.Fatalf("opcode = %v, want SendFightInvitationRejected", frame.Opcode)
	}
	r := protocol.NewReader(frame.Payload)
	if got := r.Int64(); got != 42 {
		t.Errorf("invitationId = %d, want 42", got)
	}
	if r.Remaining() != 0 {
		t.Errorf("payload has %d trailing bytes, want 0", r.Remaining())
	}
}

// TestBuildFightInvitationError verifies FIGHT_INVITATION_ERROR (4309)
// carries the single error-code byte the client reads at rawDatas[0].
func TestBuildFightInvitationError(t *testing.T) {
	frame := buildFightInvitationError(InvitationErrTargetNotFound)
	if frame.Opcode != protocol.SendFightInvitationError {
		t.Fatalf("opcode = %v, want SendFightInvitationError", frame.Opcode)
	}
	if len(frame.Payload) != 1 || frame.Payload[0] != 30 {
		t.Errorf("payload = %v, want [30]", frame.Payload)
	}
}
