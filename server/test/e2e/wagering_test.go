package e2e

import (
	"context"
	"testing"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/protocol"
)

// TestE2E_BetFightTransfersStakedCardToWinner drives a full BET fight
// (bet=1) between two coaches who each own one unlocked coach card, has the
// loser forfeit, and asserts the winner ended up owning the loser's staked
// card (the authoritative server-side card transfer that END_FIGHT only
// displays). Also confirms CREATE_FIGHT announced a stake for each coach.
func TestE2E_BetFightTransfersStakedCardToWinner(t *testing.T) {
	a, addr := startTestServer(t)
	seedAccount(t, a, "alice", "pw")
	seedAccount(t, a, "bob", "pw")

	cAlice := dialTestClient(t, addr)
	aliceCoachID := cAlice.mustLogin("alice", "pw", "Alice")
	cBob := dialTestClient(t, addr)
	bobCoachID := cBob.mustLogin("bob", "pw", "Bob")
	cAlice.drainUntil(protocol.SendActorSpawn, 5)

	// Seed one distinct unlocked coach card per coach (the stake).
	ctx := context.Background()
	if _, err := a.Deps.Coach.AddCard(ctx, uint(aliceCoachID), 1001, 1, 0); err != nil {
		t.Fatalf("seed Alice card: %v", err)
	}
	if _, err := a.Deps.Coach.AddCard(ctx, uint(bobCoachID), 2002, 1, 0); err != nil {
		t.Fatalf("seed Bob card: %v", err)
	}

	aliceFighter := createFighter(t, cAlice, "AliceFighter")
	bobFighter := createFighter(t, cBob, "BobFighter")

	// Matchmake WITH a bet (bet=1).
	searchPayload := append([]byte{1}, putInt32(1)...)
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

	createFightAlice := cAlice.expectOpcode(protocol.SendCreateFight)
	cBob.expectOpcode(protocol.SendCreateFight)

	// Both coaches must have a stake announced in CREATE_FIGHT.
	stakeTemplates := parseCreateFightBetTemplates(t, createFightAlice)
	if len(stakeTemplates) != 2 {
		t.Fatalf("CREATE_FIGHT announced %d staked cards, want 2 (one per coach): %v", len(stakeTemplates), stakeTemplates)
	}
	if !stakeTemplates[1001] || !stakeTemplates[2002] {
		t.Errorf("staked templates = %v, want both 1001 and 2002", stakeTemplates)
	}

	// Drive through presentation to the fight, then Alice forfeits (Alice
	// loses, Bob wins -> Bob should gain Alice's card 1001).
	cAlice.expectOpcode(protocol.SendEnterWorldInstance)
	cAlice.expectOpcode(protocol.SendActorAppear)
	cAlice.expectOpcode(protocol.SendStartPresentation)
	cBob.expectOpcode(protocol.SendEnterWorldInstance)
	cBob.expectOpcode(protocol.SendActorAppear)
	cBob.expectOpcode(protocol.SendStartPresentation)

	cAlice.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)
	cBob.send(3, protocol.RecvTeamMateSetReadyForPlacement, nil)

	// Alice forfeits (flee path) -> Alice loses, Bob wins. Both sides get
	// END_FIGHT; drain any intervening presentation/placement frames.
	cAlice.send(3, protocol.RecvGiveUpFightRequest, nil)
	cAlice.drainUntil(protocol.SendEndFight, 12)
	cBob.drainUntil(protocol.SendEndFight, 12)

	// Ack END_FIGHT so the fight fully tears down and the transfer is
	// committed (the transfer happens in the end hook, synchronously before
	// END_FIGHT is sent, so it's already durable here, but ack for cleanliness).
	cAlice.send(3, protocol.RecvEndFightDone, nil)
	cBob.send(3, protocol.RecvEndFightDone, nil)

	// Assert the transfer: Bob (winner) now owns template 1001 (Alice's
	// stake); Alice (loser) no longer owns it.
	assertOwnsTemplate(t, a, uint(bobCoachID), 1001, true, "winner Bob should have gained Alice's staked card 1001")
	assertOwnsTemplate(t, a, uint(aliceCoachID), 1001, false, "loser Alice should have lost her staked card 1001")
	// Bob keeps his own stake (he won, so his 2002 was not transferred).
	assertOwnsTemplate(t, a, uint(bobCoachID), 2002, true, "winner Bob should keep his own card 2002")
}

// parseCreateFightBetTemplates parses a CREATE_FIGHT payload and returns the
// set of staked card template ids across both teams' bet-card lists.
func parseCreateFightBetTemplates(t *testing.T, payload []byte) map[int32]bool {
	t.Helper()
	r := newPayloadReader(payload)
	r.byte_() // error code
	r.int16() // coach cards blob length
	r.int32() // fight type
	r.int32() // bet
	teamCount := r.byte_()
	out := map[int32]bool{}
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
			equipLen := r.int16()
			r.skip(int(equipLen))
			fighterCount := r.byte_()
			for f := byte(0); f < fighterCount; f++ {
				r.int64()   // fighter id
				r.byte_()   // breed
				r.string_() // name
				r.byte_()   // sex
				r.byte_()   // skin
				spellsLen := r.int16()
				r.skip(int(spellsLen))
				objLen := r.int16()
				r.skip(int(objLen))
			}
			statsLen := r.int16()
			r.skip(int(statsLen))
			betCount := r.byte_()
			for b := byte(0); b < betCount; b++ {
				out[r.int32()] = true
			}
		}
	}
	return out
}

// assertOwnsTemplate checks whether coach owns an unequipped card of the
// given template id, matching wantOwned.
func assertOwnsTemplate(t *testing.T, a *app.App, coachID uint, templateID int32, wantOwned bool, msg string) {
	t.Helper()
	var count int64
	err := a.Deps.Coach.DB().WithContext(context.Background()).
		Model(&domain.CoachCard{}).
		Where("coach_id = ? AND template_id = ?", coachID, templateID).
		Count(&count).Error
	if err != nil {
		t.Fatalf("count cards: %v", err)
	}
	owns := count > 0
	if owns != wantOwned {
		t.Errorf("%s (owns=%v, want %v)", msg, owns, wantOwned)
	}
}
