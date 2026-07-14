package main

import (
	"context"
	"fmt"
	"math/rand"
	"time"

	"github.com/dofusarena/go-server/internal/botai"
	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/protocol"
)

// bot.go is one simulated coach's whole life: connect + login, then a
// weighted-random behavior loop (walk / chat / fight / exchange / idle)
// until the run ends. Every behavior records success/failure + latency into
// the shared metrics so the swarm doubles as an E2E-at-scale test.

// runBot logs the bot in and drives its behavior loop until ctx is done.
func (s *swarm) runBot(ctx context.Context, id *botIdentity, rng *rand.Rand) {
	c, err := botclient.Dial(s.cfg.Addr, s.cfg.DialTimeout, 0, 10*time.Second)
	if err != nil {
		s.metrics.record("login", 0, err, "dial: "+classify(err))
		return
	}
	defer c.Disconnect()

	loginStart := time.Now()
	sess, err := c.Login(id.Login, id.Password, id.CoachName, botclient.CoachLook{})
	if err != nil {
		s.metrics.record("login", time.Since(loginStart), err, "login: "+classify(err))
		return
	}
	s.metrics.record("login", time.Since(loginStart), nil, "")
	if id.CoachID == 0 {
		id.CoachID = uint(sess.CoachID)
	}
	id.InventoryCardUIDs = sess.InventoryCardUIDs

	s.metrics.addOnline(1)
	defer s.metrics.addOnline(-1)

	// A dedicated background drainer is NOT needed: the botclient read pump
	// already keeps the socket drained, and every behavior consumes frames
	// while active. Between behaviors we do a short idle that also drains
	// any accumulated broadcast noise so the channel never backs up.
	s.behaviorLoop(ctx, c, id, rng)
}

// behaviorLoop repeatedly picks a weighted-random behavior until ctx ends.
func (s *swarm) behaviorLoop(ctx context.Context, c *botclient.Client, id *botIdentity, rng *rand.Rand) {
	for {
		select {
		case <-ctx.Done():
			return
		default:
		}

		switch s.pickBehavior(rng) {
		case behWalk:
			s.doWalk(c, id, rng)
		case behChat:
			s.doChat(c, rng)
		case behFight:
			s.doFight(ctx, c, id, rng)
		case behExchange:
			s.doExchange(ctx, c, id, rng)
		default:
			s.doIdle(ctx, c, rng)
		}
	}
}

type behavior int

const (
	behIdle behavior = iota
	behWalk
	behChat
	behFight
	behExchange
)

// pickBehavior chooses a behavior by the configured relative weights.
func (s *swarm) pickBehavior(rng *rand.Rand) behavior {
	total := s.cfg.WalkRate + s.cfg.ChatRate + s.cfg.FightRate + s.cfg.ExchangeRate + s.cfg.IdleRate
	if total <= 0 {
		return behIdle
	}
	n := rng.Intn(total)
	if n < s.cfg.WalkRate {
		return behWalk
	}
	n -= s.cfg.WalkRate
	if n < s.cfg.ChatRate {
		return behChat
	}
	n -= s.cfg.ChatRate
	if n < s.cfg.FightRate {
		return behFight
	}
	n -= s.cfg.FightRate
	if n < s.cfg.ExchangeRate {
		return behExchange
	}
	return behIdle
}

// --- Walk ---

// doWalk sends a short random walk on the overworld. The server broadcasts
// the move to every coach, so a human watching sees the bot wander.
func (s *swarm) doWalk(c *botclient.Client, id *botIdentity, rng *rand.Rand) {
	start := time.Now()
	// A small random path near an arbitrary origin. The overworld has no
	// strict walkability enforcement for these coordinates (the server
	// stores the last cell), so a few random steps produce visible motion.
	steps := 1 + rng.Intn(3)
	path := make([]botclient.Cell, 0, steps)
	x, y := int32(rng.Intn(15)), int32(rng.Intn(15))
	for i := 0; i < steps; i++ {
		x += int32(rng.Intn(3) - 1)
		y += int32(rng.Intn(3) - 1)
		path = append(path, botclient.Cell{X: x, Y: y, Z: 0})
	}
	err := c.Walk(path...)
	s.metrics.record("walk", time.Since(start), err, walkErr(err))
	// Small pause so successive walks look natural and don't spam.
	time.Sleep(time.Duration(300+rng.Intn(700)) * time.Millisecond)
}

// --- Chat ---

func (s *swarm) doChat(c *botclient.Client, rng *rand.Rand) {
	start := time.Now()
	line := s.chatLines[rng.Intn(len(s.chatLines))]
	err := c.SayVicinity(line)
	s.metrics.record("chat", time.Since(start), err, walkErr(err))
	time.Sleep(time.Duration(500+rng.Intn(1500)) * time.Millisecond)
}

// --- Idle ---

func (s *swarm) doIdle(ctx context.Context, c *botclient.Client, rng *rand.Rand) {
	// Drain and discard any accumulated broadcast frames while idling, so
	// the read-pump channel never backs up during a quiet stretch.
	dur := time.Duration(500+rng.Intn(2000)) * time.Millisecond
	deadline := time.Now().Add(dur)
	for time.Now().Before(deadline) {
		select {
		case <-ctx.Done():
			return
		default:
		}
		if _, err := c.Recv(200 * time.Millisecond); err != nil {
			if err == botclient.ErrClosed {
				return
			}
			// timeout: nothing to drain right now, keep idling.
		}
	}
}

// --- Fight ---

// doFight pairs with another fight-seeking bot, runs matchmaking so the two
// match each other, then drives a real fight to a KO via the AI.
func (s *swarm) doFight(ctx context.Context, c *botclient.Client, id *botIdentity, rng *rand.Rand) {
	if len(id.FighterIDs) == 0 {
		// No fighter to field (e.g. a reused/partial account); skip.
		return
	}
	// Bounded wait for a partner so a lone fight-seeker doesn't block
	// forever.
	pairCtx, cancel := context.WithTimeout(ctx, 20*time.Second)
	defer cancel()
	match, ok := s.fightBroker.request(pairCtx, id.CoachName, int64(id.CoachID))
	if !ok {
		// No partner in time; not a failure, just no fight this round.
		return
	}

	// Discard any leftover frames from a previous behavior so the
	// matchmaking handshake's Expects don't consume stale frames.
	c.Drain()
	start := time.Now()
	err := s.playMatchmakedFight(c, id, match, rng)
	s.metrics.record("fight", time.Since(start), err, fightErr(err))
}

// playMatchmakedFight runs the full matchmaking + fight-setup + AI-driven
// fight for one bot. Both paired bots call this with the SAME match.Key
// (used as the matchmaking bet) so the server pairs them together.
func (s *swarm) playMatchmakedFight(c *botclient.Client, id *botIdentity, match pairMatch, rng *rand.Rand) error {
	bet := int32(match.Key)
	fighterID := combatWireID(id.FighterIDs[0])

	if err := c.SearchOpponent(protocol.FightTypeMatchmakingDefy, bet); err != nil {
		return fmt.Errorf("search: %w", err)
	}
	if _, err := c.Expect(protocol.SendOpponentSearchInProgress, 15*time.Second); err != nil {
		return fmt.Errorf("search-progress: %w", err)
	}
	found, err := c.Expect(protocol.SendOpponentFound, 20*time.Second)
	if err != nil {
		return fmt.Errorf("opponent-found: %w", err)
	}
	duelID := protocol.NewReader(found).Int64()

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

// --- Exchange ---

// doExchange pairs with another exchange-seeking bot and runs the card-trade
// flow. The initiator sends the invitation; the acceptor answers, both add a
// card and set ready to complete the trade.
func (s *swarm) doExchange(ctx context.Context, c *botclient.Client, id *botIdentity, rng *rand.Rand) {
	pairCtx, cancel := context.WithTimeout(ctx, 15*time.Second)
	defer cancel()
	match, ok := s.exchangeBroker.request(pairCtx, id.CoachName, int64(id.CoachID))
	if !ok {
		return
	}
	c.Drain()
	start := time.Now()
	err := s.playExchange(c, id, match)
	s.metrics.record("exchange", time.Since(start), err, exchangeErr(err))
}

// combatWireID converts a DB fighter id to its on-wire id.
func combatWireID(dbID int64) int64 {
	const fighterWireIDBase = 1_000_000_000
	return fighterWireIDBase + dbID
}
