package main

import (
	"fmt"
	"math/rand"
	"time"

	"github.com/dofusarena/go-server/internal/botai"
	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/protocol"
)

// challenge.go lets a REAL player fight a bot: bots watch for an incoming
// FIGHT_INVITATION (the "right-click a coach -> challenge" flow, opcode 4300)
// and auto-accept it, then drive their side of the fight with the same AI
// used for bot-vs-bot fights. So a human walks up to any bot, right-clicks ->
// challenge, and gets a real opponent.
//
// Detection is poll-based: the bot scans its incoming frames for a
// FIGHT_INVITATION between/while doing other things (see waitWatching). This
// is simpler and safe because a bot only ever RECEIVES an invitation from a
// player -- bots challenge each other through the matchmaking broker, never
// via invitations -- so treating any inbound FIGHT_INVITATION as "a player
// wants to fight me" is unambiguous.

// pollChallenge does a non-blocking scan of buffered frames for a
// FIGHT_INVITATION. Non-invitation frames it encounters are broadcast noise
// and are discarded. Returns the invitation id and true if one was found.
func pollChallenge(c *botclient.Client) (int64, bool) {
	for {
		f, ok := c.TryRecv()
		if !ok {
			return 0, false
		}
		if f.Opcode == protocol.SendFightInvitation {
			// FIGHT_INVITATION payload begins with int64 invitationId
			// (packets_invitation.go buildFightInvitation).
			return protocol.NewReader(f.Payload).Int64(), true
		}
		// else: overworld/broadcast noise -- keep scanning.
	}
}

// handleChallenge accepts a player's invitation and drives the resulting
// fight. If the bot has no fighter to field it rejects instead. Records a
// "challenge" behavior outcome.
func (s *swarm) handleChallenge(c *botclient.Client, id *botIdentity, invitationID int64, rng *rand.Rand) {
	if len(id.FighterIDs) == 0 {
		_ = c.RejectFightInvitation(invitationID)
		return
	}
	start := time.Now()
	err := s.playChallengeFight(c, id, invitationID, rng)
	s.metrics.record("challenge", time.Since(start), err, fightErr(err))
}

// playChallengeFight runs the accept -> setup -> AI-driven fight flow for an
// invitation from a real player. Mirrors playMatchmakedFight but replaces the
// matchmaking search with accepting the invitation.
func (s *swarm) playChallengeFight(c *botclient.Client, id *botIdentity, invitationID int64, rng *rand.Rand) error {
	c.Drain() // clear stale noise before the handshake Expects

	if err := c.AcceptFightInvitation(invitationID); err != nil {
		return fmt.Errorf("accept: %w", err)
	}
	// FIGHT_INVITATION_ACCEPTED (4302): int64 invitationId, int64 fightId
	// (== duelId). Sent to both parties once the server creates the duel.
	acc, err := c.Expect(protocol.SendFightInvitationAccepted, 15*time.Second)
	if err != nil {
		return fmt.Errorf("accepted: %w", err)
	}
	r := protocol.NewReader(acc)
	_ = r.Int64() // invitationId
	duelID := r.Int64()

	// Raw DB fighter id -- SET_READY_FOR_FIGHT loads fighters from the DB by
	// this id (NOT the wire-offset id, which is only for in-fight frames).
	fighterID := id.FighterIDs[0]
	if err := c.SetReadyForFight(duelID, fighterID); err != nil {
		return fmt.Errorf("set-ready: %w", err)
	}
	if _, err := c.Expect(protocol.SendReadyForFight, 15*time.Second); err != nil {
		return fmt.Errorf("ready-ack: %w", err)
	}
	createPayload, err := c.Expect(protocol.SendCreateFight, 20*time.Second)
	if err != nil {
		return fmt.Errorf("create-fight: %w", err)
	}

	var ai botai.AI = botai.Dumb{}
	if s.cfg.SmartAI {
		ai = botai.Smart{}
	}
	driver := &botai.Driver{
		Client:       c,
		CoachID:      int64(id.CoachID),
		Book:         s.book,
		AI:           ai,
		RNG:          rng,
		FrameTimeout: s.cfg.FrameTimeout,
		ActionPause:  s.cfg.ActionPause,
	}
	if _, err := driver.RunFight(createPayload); err != nil {
		return fmt.Errorf("drive: %w", err)
	}
	return nil
}

// waitWatching sleeps for d, but in small slices, checking for an incoming
// player challenge between slices so the bot responds within ~a slice even
// while "resting" between actions. If a challenge arrives it is handled
// immediately (which itself blocks for the fight's duration). Returns after
// at least d has elapsed (excluding any time spent in a fight).
func (s *swarm) waitWatching(c *botclient.Client, id *botIdentity, rng *rand.Rand, d time.Duration) {
	if !s.cfg.AcceptChallenges {
		time.Sleep(d)
		return
	}
	const slice = 250 * time.Millisecond
	deadline := time.Now().Add(d)
	for time.Now().Before(deadline) {
		if invID, ok := pollChallenge(c); ok {
			s.handleChallenge(c, id, invID, rng)
			continue
		}
		remaining := time.Until(deadline)
		if remaining > slice {
			remaining = slice
		}
		if remaining > 0 {
			time.Sleep(remaining)
		}
	}
}
