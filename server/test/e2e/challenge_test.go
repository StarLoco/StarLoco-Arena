package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// Direct-challenge opcodes.
const (
	opChallengeInvite     = 26301 // C2S: [i64 targetCoachId][i8 evo]
	opChallengeInvitation = 26300 // S2C: [i64 handle][i8 outgoing][i8 evo][i8 nNames]{[i32 len][name]}
	opChallengeAccept     = 26305 // C2S: [i64 handle][i8 evo]
	opChallengeAccepted   = 26302 // S2C: [i64 handle][i8 evo]
	opChallengeDecline    = 26307 // C2S: [i64 handle]
	opChallengeCancelled  = 26304 // S2C: [i64 handle]
	opFightReadyConfirm   = 26303 // C2S: [i64 coachId][i16 teamId]
)

// TestDirectChallenge drives the full training-challenge handshake over the wire:
// A challenges B (26301) → B and A get the INVITATION (26300, with the right
// incoming/outgoing flag + name) → B accepts (26305) → both get ACCEPTED (26302)
// → both confirm a team (26303) → both receive CREATE_FIGHT(8000).
func TestDirectChallenge(t *testing.T) {
	t.Parallel()
	if raceEnabled {
		t.Skip("timing-sensitive full-fight E2E; flaky under -race's slowdown " +
			"(the challenge logic itself is unit-tested race-clean in internal/game)")
	}
	addr := testServer(t)
	a, aid := dialLogin(t, addr, "chalA", "Challenger")
	reachWorld(t, a)
	b, bid := dialLogin(t, addr, "chalB", "Target")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// A challenges B.
	_ = a.Send(2, opChallengeInvite, testclient.NewW().I64(bid).U8(0).Bytes())

	// B gets the incoming invitation (handle = challenger id, outgoing=0, name="Challenger").
	f, _, err := b.WaitFor(opChallengeInvitation, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("target no INVITATION(26300): %v", err)
	}
	rd := testclient.NewR(f.Payload)
	if handle := rd.I64(); handle != aid {
		t.Errorf("invitation handle = %d, want challenger id %d", handle, aid)
	}
	if out := rd.U8(); out != 0 {
		t.Errorf("target's invitation outgoing flag = %d, want 0 (incoming)", out)
	}

	// A gets the outgoing "waiting" echo (outgoing=1).
	f, _, err = a.WaitFor(opChallengeInvitation, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("challenger no outgoing INVITATION echo: %v", err)
	}
	rd = testclient.NewR(f.Payload)
	_ = rd.I64()
	if out := rd.U8(); out != 1 {
		t.Errorf("challenger's invitation outgoing flag = %d, want 1 (outgoing)", out)
	}

	// B accepts (handle = challenger id).
	_ = b.Send(2, opChallengeAccept, testclient.NewW().I64(aid).U8(0).Bytes())

	// Both get ACCEPTED and open the team panel.
	if _, _, err := a.WaitFor(opChallengeAccepted, testclient.DefaultTimeout); err != nil {
		t.Fatalf("challenger no ACCEPTED(26302): %v", err)
	}
	if _, _, err := b.WaitFor(opChallengeAccepted, testclient.DefaultTimeout); err != nil {
		t.Fatalf("target no ACCEPTED(26302): %v", err)
	}

	// Both confirm a team (26303 [i64 coachId][i16 teamId]).
	_ = a.Send(2, opFightReadyConfirm, testclient.NewW().I64(aid).U16(0).Bytes())
	_ = b.Send(2, opFightReadyConfirm, testclient.NewW().I64(bid).U16(0).Bytes())

	// Once both confirmed, the fight starts: both receive CREATE_FIGHT(8000).
	if _, _, err := a.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("challenger no CREATE_FIGHT after both confirmed: %v", err)
	}
	if _, _, err := b.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("target no CREATE_FIGHT after both confirmed: %v", err)
	}
}

// TestDirectChallengeDecline asserts that declining a challenge (26307) tells the
// challenger it was cancelled (26304) and no fight starts.
func TestDirectChallengeDecline(t *testing.T) {
	t.Parallel()
	if raceEnabled {
		t.Skip("timing-sensitive E2E under -race's slowdown")
	}
	addr := testServer(t)
	a, aid := dialLogin(t, addr, "decA", "DeclChallenger")
	reachWorld(t, a)
	b, bid := dialLogin(t, addr, "decB", "DeclTarget")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	_ = a.Send(2, opChallengeInvite, testclient.NewW().I64(bid).U8(0).Bytes())
	if _, _, err := b.WaitFor(opChallengeInvitation, testclient.DefaultTimeout); err != nil {
		t.Fatalf("target no INVITATION: %v", err)
	}

	// B declines → A gets CANCELLED(26304).
	_ = b.Send(2, opChallengeDecline, testclient.NewW().I64(aid).Bytes())
	if _, _, err := a.WaitFor(opChallengeCancelled, testclient.DefaultTimeout); err != nil {
		t.Fatalf("challenger no CANCELLED(26304) after decline: %v", err)
	}
	// And no fight was created for the challenger.
	if f, _, err := a.WaitFor(testclient.OpCreateFight, 400*time.Millisecond); err == nil {
		t.Fatalf("a declined challenge must not start a fight; got op=%d", f.Opcode)
	}
}
