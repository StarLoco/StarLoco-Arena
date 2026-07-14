// Command botswarm connects a large swarm of simulated coaches ("bots") to
// a LIVE, already-running DofusArena server over TCP and drives them through
// the full breadth of player behavior -- walking the overworld, chatting,
// matchmaking + fighting each other to a real KO with a tactical AI, and
// trading cards -- so that:
//
//   - connecting a real retail client to the same server shows a populated,
//     living world (bots visibly moving, chatting, fighting, trading), and
//   - the run doubles as an END-TO-END-at-scale test: every behavior's
//     success/failure/latency is recorded and a report (console + JSON/CSV)
//     shows exactly what broke under load.
//
// Unlike cmd/loadtest (which boots its OWN in-process server for throughput
// benchmarking), botswarm attaches to YOUR running server. You typically:
//
//  1. start the server:      go run ./cmd/server --config configs/config.dev.yaml
//  2. run the swarm:         go run ./cmd/botswarm --config configs/config.dev.yaml \
//     --connect 127.0.0.1:5555 --bots 500 --data-dir data
//  3. connect your client to the same server and watch the bots.
//
// Bots are provisioned directly in the server's database (accounts, coaches
// with 2 random card sets [1 equipped], and procedurally generated fighters)
// -- see seed.go. SQLite WAL mode allows this concurrently with the running
// server. Seeding is idempotent, so re-running reuses existing bot accounts.
package main

import (
	"context"
	"flag"
	"fmt"
	"math/rand"
	"os"
	"os/signal"
	"sync"
	"syscall"
	"time"

	"github.com/dofusarena/go-server/internal/botai"
	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/gamedata"
)

func main() {
	var (
		configPath  = flag.String("config", "", "path to the server config YAML (used to open the SAME database the running server uses, for seeding bot accounts)")
		connect     = flag.String("connect", "127.0.0.1:5555", "host:port of the running server's game socket")
		dataDir     = flag.String("data-dir", "data", "path to the gamedata directory (spells.dat, cards.dat, ...), for the fighter generator + fight AI")
		numBots     = flag.Int("bots", 100, "number of concurrent bots")
		ramp        = flag.Duration("ramp", 30*time.Second, "spread bot logins over this duration (avoids a thundering-herd connect)")
		duration    = flag.Duration("duration", 5*time.Minute, "how long the swarm runs before shutting down (0 = until Ctrl+C)")
		fighters    = flag.Int("fighters", 1, "fighters generated per bot")
		password    = flag.String("password", "botpw", "shared password for all bot accounts")
		loginPrefix = flag.String("login-prefix", "swarmbot", "login name prefix; bot N is <prefix>NNNNN")

		walkRate     = flag.Int("walk-rate", 40, "relative weight of the walk behavior")
		chatRate     = flag.Int("chat-rate", 20, "relative weight of the chat behavior")
		fightRate    = flag.Int("fight-rate", 25, "relative weight of the fight behavior")
		exchangeRate = flag.Int("exchange-rate", 10, "relative weight of the card-exchange behavior")
		idleRate     = flag.Int("idle-rate", 5, "relative weight of idling")

		smartAI     = flag.Bool("ai", false, "use the smart tactical fight AI (default: the cheap 'dumb' melee AI)")
		actionPause = flag.Duration("action-pause", 400*time.Millisecond, "pause between a bot's own fight actions so a human watching sees the fight play out (0 = as fast as possible)")

		reportJSON = flag.String("report", "", "write the final report as JSON to this path")
		reportCSV  = flag.String("csv", "", "write the per-behavior table as CSV to this path")
		seed       = flag.Int64("seed", 0, "RNG seed (0 = random)")

		dialTimeout  = flag.Duration("dial-timeout", 15*time.Second, "TCP dial timeout per bot")
		frameTimeout = flag.Duration("frame-timeout", 40*time.Second, "per-frame receive timeout during a fight")
	)
	flag.Parse()

	if *configPath == "" {
		fmt.Fprintln(os.Stderr, "botswarm: --config is required (to seed bot accounts into the running server's database)")
		os.Exit(1)
	}
	if *numBots <= 0 {
		fmt.Fprintln(os.Stderr, "botswarm: --bots must be > 0")
		os.Exit(1)
	}

	if *seed == 0 {
		*seed = time.Now().UnixNano()
	}
	rootRNG := rand.New(rand.NewSource(*seed))

	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintln(os.Stderr, "botswarm: load config:", err)
		os.Exit(1)
	}

	// Load game data (for the generator + fight AI's spell book).
	fmt.Println("botswarm: loading game data from", *dataDir)
	store := gamedata.NewStore(*dataDir)
	idx := buildDataIndex(store)
	if len(idx.spellsByBreed) == 0 {
		fmt.Fprintln(os.Stderr, "botswarm: WARNING: no breed spells loaded -- fighters will have empty loadouts and the smart AI will fall back to melee. Check --data-dir.")
	}
	book := buildSpellBook(store, idx)

	// Open the DB and seed bots.
	sd, err := newSeeder(cfg, idx)
	if err != nil {
		fmt.Fprintln(os.Stderr, "botswarm:", err)
		os.Exit(1)
	}
	defer sd.Close()

	scfg := swarmConfig{
		Addr:         *connect,
		Password:     *password,
		LoginPrefix:  *loginPrefix,
		NumBots:      *numBots,
		Ramp:         *ramp,
		Duration:     *duration,
		Fighters:     *fighters,
		WalkRate:     *walkRate,
		ChatRate:     *chatRate,
		FightRate:    *fightRate,
		ExchangeRate: *exchangeRate,
		IdleRate:     *idleRate,
		SmartAI:      *smartAI,
		ActionPause:  *actionPause,
		DialTimeout:  *dialTimeout,
		FrameTimeout: *frameTimeout,
	}

	m := newMetrics()
	sw := &swarm{
		cfg:       scfg,
		metrics:   m,
		book:      book,
		seeder:    sd,
		chatLines: defaultChatLines,
	}
	sw.fightBroker = newPairBroker(sw.nextBet)
	sw.exchangeBroker = newPairBroker(sw.nextBet)

	aiName := "dumb"
	if scfg.SmartAI {
		aiName = "smart"
	}
	fmt.Printf("botswarm: seeding %d bots...\n", *numBots)
	ctx := context.Background()
	identities := make([]*botIdentity, 0, *numBots)
	for i := 0; i < *numBots; i++ {
		botRNG := rand.New(rand.NewSource(rootRNG.Int63()))
		id, err := sd.seedBot(ctx, *loginPrefix, *password, i, *fighters, botRNG)
		if err != nil {
			fmt.Fprintf(os.Stderr, "botswarm: seed bot %d: %v\n", i, err)
			os.Exit(1)
		}
		identities = append(identities, id)
	}
	fmt.Printf("botswarm: seeded. connecting to %s (ai=%s)\n", *connect, aiName)

	runCtx, cancel := context.WithCancel(context.Background())

	// Ctrl+C / SIGTERM triggers a graceful stop.
	sigs := make(chan os.Signal, 1)
	signal.Notify(sigs, os.Interrupt, syscall.SIGTERM)
	go func() {
		<-sigs
		fmt.Println("\nbotswarm: shutdown signal received, stopping bots...")
		cancel()
	}()
	// Duration cap.
	if scfg.Duration > 0 {
		go func() {
			select {
			case <-time.After(scfg.Duration):
				fmt.Println("\nbotswarm: duration elapsed, stopping bots...")
				cancel()
			case <-runCtx.Done():
			}
		}()
	}

	// Live status ticker.
	tickerDone := make(chan struct{})
	go func() {
		defer close(tickerDone)
		t := time.NewTicker(2 * time.Second)
		defer t.Stop()
		for {
			select {
			case <-runCtx.Done():
				return
			case <-t.C:
				fmt.Println(m.snapshotLine())
			}
		}
	}()

	// Ramp-up: spread bot goroutine starts over the ramp window.
	var wg sync.WaitGroup
	interval := time.Duration(0)
	if *numBots > 1 && scfg.Ramp > 0 {
		interval = scfg.Ramp / time.Duration(*numBots)
	}
	for i, id := range identities {
		select {
		case <-runCtx.Done():
		default:
		}
		wg.Add(1)
		botRNG := rand.New(rand.NewSource(rootRNG.Int63()))
		go func(id *botIdentity, rng *rand.Rand) {
			defer wg.Done()
			sw.runBot(runCtx, id, rng)
		}(id, botRNG)
		if interval > 0 && i < len(identities)-1 {
			select {
			case <-time.After(interval):
			case <-runCtx.Done():
			}
		}
	}

	wg.Wait()
	cancel()
	<-tickerDone

	// Final report.
	fr := m.build()
	fr.printConsole()
	if *reportJSON != "" {
		if err := fr.writeJSON(*reportJSON); err != nil {
			fmt.Fprintln(os.Stderr, "botswarm: write JSON report:", err)
		} else {
			fmt.Println("botswarm: wrote JSON report to", *reportJSON)
		}
	}
	if *reportCSV != "" {
		if err := fr.writeCSV(*reportCSV); err != nil {
			fmt.Fprintln(os.Stderr, "botswarm: write CSV report:", err)
		} else {
			fmt.Println("botswarm: wrote CSV report to", *reportCSV)
		}
	}
}

// buildSpellBook derives the fight AI's SpellBook (id -> AP/range/damage/
// summon) from the game-data store, for every playable-breed spell the
// generator might assign. Damage is a rough offensive weight summed from the
// spell's damage-type effects so the AI can rank spells; non-damaging spells
// get weight 0.
func buildSpellBook(store *gamedata.Store, idx *dataIndex) botai.SpellBook {
	book := make(botai.SpellBook)
	for _, sp := range store.Spells.All() {
		info := botai.SpellInfo{
			ID:       sp.ID,
			APCost:   int32(sp.ActionPointsCost),
			RangeMin: int32(sp.RangeMin),
			RangeMax: int32(sp.RangeMax),
		}
		for _, eff := range sp.Effects {
			if eff.ActionID == summonActionID {
				info.Summon = true
			}
			info.Damage += damageWeight(eff)
		}
		book[sp.ID] = info
	}
	return book
}

// damageWeight returns a rough offensive weight for an effect. Damage
// effects (the several HP-loss action ids) contribute their average roll
// magnitude; everything else contributes 0. This is a heuristic for spell
// ranking, not a damage simulation.
func damageWeight(eff gamedata.EffectDef) int32 {
	if !isDamageAction(eff.ActionID) {
		return 0
	}
	// Params[0]/[1] are typically the min/max roll for damage effects.
	var w int32
	if len(eff.Params) >= 2 {
		w = int32((eff.Params[0] + eff.Params[1]) / 2)
	} else if len(eff.Params) == 1 {
		w = int32(eff.Params[0])
	}
	if w < 0 {
		w = -w
	}
	return w
}

// isDamageAction reports whether an effect ActionID is a direct HP-loss/
// leech/poison/death effect, per internal/combat/effects_registry.go:
//
//	1-5   HP loss   (physical/fire/earth/water/wind)
//	6-10  HP leech  (physical/fire/earth/water/wind)
//	12    HP debuff (max-HP reduction)
//	61    poison
//	63    death
//
// Kept as a small allowlist so buffs/heals/AP-MP-loss/debuffs are NOT ranked
// as damage by the fight AI's spell selection.
func isDamageAction(actionID int32) bool {
	switch actionID {
	case 1, 2, 3, 4, 5,
		6, 7, 8, 9, 10,
		12, 61, 63:
		return true
	default:
		return false
	}
}
