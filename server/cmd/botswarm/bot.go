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
	// The seeder set id.posX/posY from the coach's stored DB position (the
	// same position the server broadcasts as the "from" cell of the first
	// move), so walk tracking is already in sync. Fall back to the default
	// spawn (1,1) only if it wasn't populated.
	if id.posX == 0 && id.posY == 0 {
		id.posX, id.posY = 1, 1
	}

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

		// Always check first whether a real player has challenged this bot
		// (right-click -> fight). If so, accept and fight them now.
		if s.cfg.AcceptChallenges {
			if invID, ok := pollChallenge(c); ok {
				s.handleChallenge(c, id, invID, rng)
				continue
			}
		}

		// With IdleChance probability, the bot does NOTHING this cycle (a
		// short quiet wait) instead of acting. This keeps the world from
		// being a constant storm of actions -- real players pause a lot --
		// and throttles the broadcast volume that was overwhelming clients.
		if s.cfg.IdleChance > 0 && rng.Float64() < s.cfg.IdleChance {
			s.doIdle(ctx, c, id, rng)
			continue
		}

		switch s.pickBehavior(rng) {
		case behWalk:
			s.doWalk(c, id, rng)
		case behChat:
			s.doChat(c, id, rng)
		case behFight:
			s.doFight(ctx, c, id, rng)
		case behExchange:
			s.doExchange(ctx, c, id, rng)
		default:
			s.doIdle(ctx, c, id, rng)
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

// walkRegionLo/Hi bound the overworld region bots roam in. It's large enough
// that ~hundreds of bots spread out across the map instead of piling onto
// the spawn tile.
const walkRegionLo, walkRegionHi = 0, 28

// cellWalkSecurity is a fixed margin added per cell on top of the client's
// per-cell animation time, so the bot always finishes waiting a little AFTER
// the client has finished animating the step (never before, which would let
// the next move overlap the current animation and look like a jump).
const cellWalkSecurity = 120 * time.Millisecond

// doWalk moves the bot one short leg toward its roaming waypoint. It picks a
// new random waypoint when it arrives (or has none), so bots continuously
// travel ACROSS the map and disperse instead of milling around the spawn
// tile (a plain symmetric random walk stays centred on its origin, which is
// why the bots were stacking). The leg is a single contiguous path of
// strictly-adjacent cells; the client animates a smooth walk over it.
//
// Timing, per the intended model:
//
//	moveTime = cells * (cellWalkTime + security)   // wait out the animation
//	then also sleep stepDuration                    // idle pause between actions
func (s *swarm) doWalk(c *botclient.Client, id *botIdentity, rng *rand.Rand) {
	// Ensure a waypoint exists / refresh it on arrival.
	if !id.hasWaypoint || (id.posX == id.wpX && id.posY == id.wpY) {
		id.wpX = int32(walkRegionLo + rng.Intn(walkRegionHi-walkRegionLo+1))
		id.wpY = int32(walkRegionLo + rng.Intn(walkRegionHi-walkRegionLo+1))
		id.hasWaypoint = true
	}

	path := buildWalkLeg(id.posX, id.posY, id.wpX, id.wpY, rng)
	if len(path) == 0 {
		return
	}

	start := time.Now()
	err := c.Walk(path...)
	s.metrics.record("walk", time.Since(start), err, walkErr(err))
	if err != nil {
		return
	}
	last := path[len(path)-1]
	id.posX, id.posY = last.X, last.Y

	// Wait out the client's animation of this leg: cells * (per-cell walk
	// time + security margin). Only after that does the coach visually
	// stand still, so any next action fires from a settled position.
	cellWalk := s.cfg.CellWalkTime
	if cellWalk <= 0 {
		cellWalk = 350 * time.Millisecond
	}
	moveTime := time.Duration(len(path)) * (cellWalk + cellWalkSecurity)
	s.waitWatching(c, id, rng, moveTime)

	// Then an additional idle pause between actions.
	if d := s.cfg.StepDuration; d > 0 {
		jitter := time.Duration(rng.Int63n(int64(d/2) + 1))
		s.waitWatching(c, id, rng, d+jitter)
	}
}

// buildWalkLeg returns a contiguous path from (fromX,fromY) that steps
// TOWARD (toX,toY), of a short random length. EVERY consecutive cell --
// including the server-prepended "from" cell -> the first returned cell --
// differs by exactly ONE cell in exactly ONE axis, so the client
// (ActorMovementMessage -> PathMobile.setPath) animates a real step-by-step
// walk rather than sliding/teleporting between non-adjacent cells.
func buildWalkLeg(fromX, fromY, toX, toY int32, rng *rand.Rand) []botclient.Cell {
	nCells := 2 + rng.Intn(5) // a 2..6-cell leg
	path := make([]botclient.Cell, 0, nCells)
	x, y := fromX, fromY
	for i := 0; i < nCells; i++ {
		if x == toX && y == toY {
			break // arrived at the waypoint
		}
		// Choose an axis to advance. Bias toward the axis with the larger
		// remaining gap so we head to the waypoint, with occasional random
		// wandering so paths aren't dead-straight.
		dx, dy := toX-x, toY-y
		moveX := abs32i(dx) >= abs32i(dy)
		if rng.Intn(5) == 0 { // 20% random axis flip for organic motion
			moveX = !moveX
		}
		if moveX && dx != 0 {
			x += sign32(dx)
		} else if dy != 0 {
			y += sign32(dy)
		} else if dx != 0 {
			x += sign32(dx)
		} else {
			break
		}
		x = clampRegion(x)
		y = clampRegion(y)
		path = append(path, botclient.Cell{X: x, Y: y, Z: 0})
	}
	return path
}

func abs32i(v int32) int32 {
	if v < 0 {
		return -v
	}
	return v
}

func sign32(v int32) int32 {
	if v > 0 {
		return 1
	}
	if v < 0 {
		return -1
	}
	return 0
}

func clampRegion(v int32) int32 {
	if v < walkRegionLo {
		return walkRegionLo
	}
	if v > walkRegionHi {
		return walkRegionHi
	}
	return v
}

// --- Chat ---

func (s *swarm) doChat(c *botclient.Client, id *botIdentity, rng *rand.Rand) {
	start := time.Now()
	line := s.chatLines[rng.Intn(len(s.chatLines))]
	err := c.SayVicinity(line)
	s.metrics.record("chat", time.Since(start), err, walkErr(err))
	s.waitWatching(c, id, rng, time.Duration(500+rng.Intn(1500))*time.Millisecond)
}

// --- Idle ---

func (s *swarm) doIdle(ctx context.Context, c *botclient.Client, id *botIdentity, rng *rand.Rand) {
	// Idle for a short spell while staying responsive to a player challenge
	// and keeping the socket drained (so the read-pump channel never backs
	// up during a quiet stretch). waitWatching polls for challenges and, in
	// doing so, discards accumulated broadcast noise.
	dur := time.Duration(500+rng.Intn(2000)) * time.Millisecond
	select {
	case <-ctx.Done():
		return
	default:
	}
	s.waitWatching(c, id, rng, dur)
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
	// SET_READY_FOR_FIGHT selects fighters by their RAW DB id (the dispatch
	// handler loads them from the DB scoped to the coach); the wire-offset
	// id is only used for in-fight action frames. Passing the wire id here
	// makes the server field an EMPTY team (fighter not found), which is why
	// bots showed up with a coach but no fighter.
	fighterID := id.FighterIDs[0]

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
