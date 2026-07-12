package dispatch

import "github.com/dofusarena/go-server/internal/protocol"

// Fight-invitation error codes sent in FIGHT_INVITATION_ERROR (4309),
// matching the client's NetFightInvitationFrame case-4309 handling
// (client/.../network/protocol/frame/NetFightInvitationFrame.java): 30 =
// target not found (offline/unknown), 31 = target busy (already in a
// pending invitation or duel), 32 = you're busy, 33 = target is yourself.
const (
	InvitationErrTargetNotFound   byte = 30
	InvitationErrTargetBusy       byte = 31
	InvitationErrYoureBusy        byte = 32
	InvitationErrTargetIsYourself byte = 33
)

// invitationTeamMate is one coach entry inside a FIGHT_INVITATION team
// blob: the coach's id and display name.
type invitationTeamMate struct {
	CoachID uint
	Name    string
}

// buildFightInvitation serializes FIGHT_INVITATION (opcode 4300), matching
// the client decode order in FightInvitationMessage.java:38-68:
//
//	int64 invitationId
//	byte  inviter        (1 = the recipient is the one who issued the invite)
//	byte  fightTypeId
//	int32 bet
//	byte  opponentTeamCount
//	  per team:
//	    byte  teamId
//	    int64 leaderId
//	    byte  teamMateCount
//	      per mate:
//	        int64 coachId
//	        byte  nameLength
//	        byte[nameLength] name
//
// For the 1v1 right-click challenge this is always a single opposing team
// with a single mate (the coach on the OTHER side from the recipient): the
// inviter's copy carries the target's team, the target's copy carries the
// inviter's team. `inviter` only drives the client's accept/reject
// message-box styling (FightInvitationManager.addInvitation), not routing.
func buildFightInvitation(invitationID int64, inviter bool, fightType byte, bet int32, teamID byte, leaderID uint, mates []invitationTeamMate) protocol.OutboundFrame {
	w := protocol.NewWriter(32)
	w.PutInt64(invitationID)
	if inviter {
		w.PutByte(1)
	} else {
		w.PutByte(0)
	}
	w.PutByte(fightType)
	w.PutInt32(bet)

	w.PutByte(1) // opponent team count (1v1 challenge: single opposing team)
	w.PutByte(teamID)
	w.PutInt64(int64(leaderID))
	w.PutByte(byte(len(mates)))
	for _, mate := range mates {
		w.PutInt64(int64(mate.CoachID))
		w.PutString(mate.Name) // 1-byte length-prefixed, matches client's byte nameLength
	}

	return protocol.OutboundFrame{Opcode: protocol.SendFightInvitation, Payload: w.Bytes()}
}

// buildFightInvitationAccepted serializes FIGHT_INVITATION_ACCEPTED (4302),
// see FightInvitationAcceptedMessage.java:28-33: int64 invitationId, int64
// fightId. Sent to BOTH coaches so each client swaps to the fight-creation
// / team-selection frame seeded with fightId (which then drives the shared
// SET_READY_FOR_FIGHT -> CREATE_FIGHT flow).
func buildFightInvitationAccepted(invitationID, fightID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(16)
	w.PutInt64(invitationID).PutInt64(fightID)
	return protocol.OutboundFrame{Opcode: protocol.SendFightInvitationAccepted, Payload: w.Bytes()}
}

// buildFightInvitationRejected serializes FIGHT_INVITATION_REJECTED (4304),
// see FightInvitationRejectedMessage.java:27-31: int64 invitationId. Sent
// to the inviter so their client removes the pending-invitation box.
func buildFightInvitationRejected(invitationID int64) protocol.OutboundFrame {
	w := protocol.NewWriter(8)
	w.PutInt64(invitationID)
	return protocol.OutboundFrame{Opcode: protocol.SendFightInvitationRejected, Payload: w.Bytes()}
}

// buildFightInvitationError serializes FIGHT_INVITATION_ERROR (4309), see
// FightInvitationErrorMessage.java:24-27: a single byte errorCode.
func buildFightInvitationError(code byte) protocol.OutboundFrame {
	w := protocol.NewWriter(1)
	w.PutByte(code)
	return protocol.OutboundFrame{Opcode: protocol.SendFightInvitationError, Payload: w.Bytes()}
}
