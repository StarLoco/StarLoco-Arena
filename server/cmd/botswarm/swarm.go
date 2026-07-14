package main

import (
	"context"
	"sync"
	"sync/atomic"
	"time"

	"github.com/dofusarena/go-server/internal/botai"
)

// swarm.go holds shared swarm state: config, metrics, the game-data-derived
// spell book the fight AI consults, and the pairing brokers that match two
// bots together for a fight or a card exchange (both need two willing
// participants that agree on a rendezvous key).

// swarmConfig is the resolved runtime configuration for a run.
type swarmConfig struct {
	Addr        string
	Password    string
	LoginPrefix string
	NumBots     int
	Ramp        time.Duration // total time to spread bot logins over
	Duration    time.Duration // how long bots stay active (0 = until interrupted)
	Fighters    int           // fighters generated per bot

	// behavior weights (relative)
	WalkRate     int
	ChatRate     int
	FightRate    int
	ExchangeRate int
	IdleRate     int

	// fight AI
	SmartAI     bool
	ActionPause time.Duration

	DialTimeout  time.Duration
	FrameTimeout time.Duration
}

// swarm bundles everything a bot goroutine shares.
type swarm struct {
	cfg     swarmConfig
	metrics *metrics
	book    botai.SpellBook

	seeder *seeder

	// betCounter hands out unique matchmaking bet values so a fight pair
	// is matched with EACH OTHER, not with an unrelated concurrent pair
	// (the server pairs identical (Type, Bet) tuples). Each pairing claims
	// two consecutive... actually the SAME bet for both members.
	betCounter atomic.Int64

	fightBroker    *pairBroker
	exchangeBroker *pairBroker

	// chatLines are canned phrases bots say.
	chatLines []string
}

// pairMatch is the result handed to two bots that were paired: a shared
// rendezvous key (used as the matchmaking bet, or the exchange nonce) and
// the role (initiator vs. acceptor) so they take complementary actions.
type pairMatch struct {
	Key         int64
	Initiator   bool
	PartnerName string
	PartnerID   int64
}

// pairRequest is a bot waiting to be paired.
type pairRequest struct {
	name   string
	coach  int64
	result chan pairMatch
}

// pairBroker matches two waiting bots. The first waiter parks; the second
// completes the pair and both receive complementary pairMatch values.
type pairBroker struct {
	mu      sync.Mutex
	waiting *pairRequest
	nextKey func() int64
}

func newPairBroker(nextKey func() int64) *pairBroker {
	return &pairBroker{nextKey: nextKey}
}

// request enrolls a bot for pairing and blocks (up to ctx) until matched or
// canceled. Returns false if the context ended first.
func (b *pairBroker) request(ctx context.Context, name string, coach int64) (pairMatch, bool) {
	req := &pairRequest{name: name, coach: coach, result: make(chan pairMatch, 1)}

	b.mu.Lock()
	if b.waiting == nil {
		// Park as the waiter.
		b.waiting = req
		b.mu.Unlock()
		select {
		case m := <-req.result:
			return m, true
		case <-ctx.Done():
			// Best-effort de-park so a stale waiter isn't matched later.
			b.mu.Lock()
			if b.waiting == req {
				b.waiting = nil
			}
			b.mu.Unlock()
			return pairMatch{}, false
		}
	}

	// We are the second party: complete the pair.
	partner := b.waiting
	b.waiting = nil
	key := b.nextKey()
	b.mu.Unlock()

	// Deliver complementary matches. The parked partner is the initiator.
	partner.result <- pairMatch{Key: key, Initiator: true, PartnerName: name, PartnerID: coach}
	return pairMatch{Key: key, Initiator: false, PartnerName: partner.name, PartnerID: partner.coach}, true
}

// nextBet returns a fresh, unique, non-zero matchmaking bet value. Non-zero
// so the server's bet-fight path (which requires a stakeable card, which
// seeded bots have) is exercised and pairing is unambiguous.
func (s *swarm) nextBet() int64 {
	return s.betCounter.Add(1)
}

// defaultChatLines are innocuous phrases (no leading '/', which would be
// treated as a GM command and swallowed).
var defaultChatLines = []string{
	"hello there",
	"anyone up for a fight?",
	"gg wp",
	"nice moves",
	"trading cards, whisper me",
	"lag is real today",
	"who wants to duel",
	"brb one sec",
	"that summon was huge",
	"good luck have fun",
}
