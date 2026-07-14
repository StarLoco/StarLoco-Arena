// Command loadtest is a dedicated load-testing harness for the combat
// engine, implementing docs/08-java-parity-roadmap.md §8.11 item 10 /
// §8.12 Phase O: a standalone tool distinct from
// test/e2e.TestE2E_ConcurrentFightsLoad, which only proves correctness
// (no deadlocks/crashes under -race for a fixed small N) rather than
// measuring throughput/latency or supporting profiling.
//
// loadtest boots a real, fully-wired server in-process (the exact same
// composition root as cmd/server, via internal/app) with its admin/pprof
// HTTP endpoint enabled, then drives -fights concurrent fights to
// completion (login -> matchmaking -> fight setup -> presentation ->
// forfeit -> END_FIGHT -> ack), reporting per-phase latency percentiles
// and total throughput. Because the admin HTTP server is real and
// reachable, a real CPU/memory profile can be captured DURING a loadtest
// run with the standard `go tool pprof` workflow, e.g. in a second
// terminal while loadtest is running:
//
//	go tool pprof http://127.0.0.1:9091/debug/pprof/profile?seconds=10
//	go tool pprof http://127.0.0.1:9091/debug/pprof/heap
//
// The raw wire client, login handshake, and per-opcode payload helpers this
// tool needs are provided by internal/botclient (shared with cmd/botswarm),
// so this file only contains the in-process server boot, the fight
// orchestration, and the latency/throughput report.
//
// Usage:
//
//	go run ./cmd/loadtest -fights 50 -concurrency 10
//	go run ./cmd/loadtest -fights 200 -concurrency 50 -admin-addr 127.0.0.1:9091
package main

import (
	"context"
	"flag"
	"fmt"
	"os"
	"sort"
	"sync"
	"sync/atomic"
	"time"

	"github.com/dofusarena/go-server/internal/app"
	"github.com/dofusarena/go-server/internal/botclient"
	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/dofusarena/go-server/internal/service"

	"github.com/rs/zerolog"
)

// stakeCardTemplateID is a real coach-card template id (from the loaded
// gamedata) granted to every seeded coach so that bet fights (Bet != 0) can
// select a stake and reach CREATE_FIGHT. Chosen once at startup. This fixes
// a latent bug in the original loadtest: it paired coaches with a unique
// non-zero bet per fight for matchmaking determinism, but never gave those
// coaches a stakeable card, so every fight was canceled with
// "cantHoldTheBet" before CREATE_FIGHT.
var stakeCardTemplateID int32

func main() {
	fights := flag.Int("fights", 50, "total number of independent fights to run")
	concurrency := flag.Int("concurrency", 10, "max number of fights running simultaneously")
	adminAddr := flag.String("admin-addr", "127.0.0.1:9091", "bind address for the server's admin/pprof HTTP endpoint (empty to disable)")
	dataDir := flag.String("data-dir", "../../data", "path to the gamedata directory (cards.dat, spells.dat, etc.), relative to this binary's working directory")
	verbose := flag.Bool("v", false, "log server-side warnings/errors instead of running silently")
	flag.Parse()

	if *fights <= 0 || *concurrency <= 0 {
		fmt.Fprintln(os.Stderr, "loadtest: -fights and -concurrency must both be > 0")
		os.Exit(1)
	}

	logLevel := zerolog.Disabled
	if *verbose {
		logLevel = zerolog.WarnLevel
	}
	logger := zerolog.New(os.Stderr).Level(logLevel).With().Timestamp().Logger()

	cfg := config.Default()
	cfg.Server.ListenAddr = "127.0.0.1:0"
	cfg.Server.AdminAddr = *adminAddr
	cfg.Database.Driver = "sqlite"
	cfg.Database.DSN = "file:loadtest?mode=memory&cache=shared&_pragma=foreign_keys(1)"
	cfg.Database.MaxOpenConns = 1
	cfg.GameData.Dir = *dataDir
	// Fast clocks: the point of this tool is measuring the
	// login/matchmaking/fight-setup/action pipeline's own overhead, not
	// waiting out production-length presentation/placement/observation
	// timers -- every simulated fight forfeits immediately once
	// PhaseAction-adjacent setup has completed, mirroring
	// test/e2e/load_test.go's existing (correctness-only) approach.
	cfg.Combat.PresentationClock = 200 * time.Millisecond
	cfg.Combat.PlacementClock = 200 * time.Millisecond
	cfg.Combat.ObservationClock = 200 * time.Millisecond
	cfg.Combat.TurnClock = 30 * time.Second

	a, err := app.New(cfg, logger, app.Options{})
	if err != nil {
		fmt.Fprintln(os.Stderr, "loadtest: app.New:", err)
		os.Exit(1)
	}
	if err := a.Listen(); err != nil {
		fmt.Fprintln(os.Stderr, "loadtest: Listen:", err)
		os.Exit(1)
	}

	serveDone := make(chan struct{})
	go func() {
		defer close(serveDone)
		_ = a.Serve(context.Background())
	}()
	// Give the accept loop a moment to actually start (Listen already
	// bound the socket synchronously, so this is generous, not required).
	time.Sleep(50 * time.Millisecond)

	// Pick a real coach-card template to stake in bet fights.
	if cards := a.Deps.Data.CoachCards.All(); len(cards) > 0 {
		stakeCardTemplateID = cards[0].TemplateID()
	} else {
		fmt.Fprintln(os.Stderr, "loadtest: WARNING: no coach-card templates loaded; bet fights will be canceled (cantHoldTheBet)")
	}

	addr := a.Addr()
	fmt.Printf("loadtest: server ready at %s", addr)
	if a.AdminAddr() != "" {
		fmt.Printf(" (admin/pprof at http://%s/debug/pprof/)", a.AdminAddr())
	}
	fmt.Println()
	fmt.Printf("loadtest: running %d fights at concurrency %d...\n", *fights, *concurrency)

	report := runLoadTest(a, addr, *fights, *concurrency)
	report.Print()

	_ = a.Close()
	<-serveDone
}

// --- results collection ---

type fightResult struct {
	idx      int
	err      error
	duration time.Duration
}

type report struct {
	total     int
	successes int
	failures  []string
	durations []time.Duration
	wallClock time.Duration
}

func (r *report) Print() {
	fmt.Println()
	fmt.Println("=== loadtest report ===")
	fmt.Printf("total fights:      %d\n", r.total)
	fmt.Printf("successful:        %d\n", r.successes)
	fmt.Printf("failed:            %d\n", len(r.failures))
	fmt.Printf("wall-clock time:   %s\n", r.wallClock.Round(time.Millisecond))
	if r.successes > 0 {
		fmt.Printf("throughput:        %.1f fights/sec\n", float64(r.successes)/r.wallClock.Seconds())
		sorted := append([]time.Duration(nil), r.durations...)
		sort.Slice(sorted, func(i, j int) bool { return sorted[i] < sorted[j] })
		fmt.Printf("per-fight latency: p50=%s p90=%s p99=%s max=%s\n",
			percentile(sorted, 0.50).Round(time.Millisecond),
			percentile(sorted, 0.90).Round(time.Millisecond),
			percentile(sorted, 0.99).Round(time.Millisecond),
			sorted[len(sorted)-1].Round(time.Millisecond),
		)
	}
	if len(r.failures) > 0 {
		fmt.Println()
		fmt.Println("--- failures (first 10) ---")
		max := len(r.failures)
		if max > 10 {
			max = 10
		}
		for _, f := range r.failures[:max] {
			fmt.Println("  " + f)
		}
	}
}

func percentile(sorted []time.Duration, p float64) time.Duration {
	if len(sorted) == 0 {
		return 0
	}
	idx := int(p * float64(len(sorted)))
	if idx >= len(sorted) {
		idx = len(sorted) - 1
	}
	return sorted[idx]
}

// runLoadTest drives n fights at the given concurrency limit against a's
// listener, returning aggregated latency/throughput stats.
func runLoadTest(a *app.App, addr string, n, concurrency int) *report {
	start := time.Now()
	sem := make(chan struct{}, concurrency)
	results := make(chan fightResult, n)
	var wg sync.WaitGroup

	var counter atomic.Int64
	for i := 0; i < n; i++ {
		wg.Add(1)
		sem <- struct{}{}
		go func(idx int) {
			defer wg.Done()
			defer func() { <-sem }()
			fightStart := time.Now()
			err := runOneLoadTestFight(a, addr, int(counter.Add(1)))
			results <- fightResult{idx: idx, err: err, duration: time.Since(fightStart)}
		}(i)
	}

	go func() {
		wg.Wait()
		close(results)
	}()

	rep := &report{total: n}
	for res := range results {
		if res.err != nil {
			rep.failures = append(rep.failures, fmt.Sprintf("fight %d: %v", res.idx, res.err))
			continue
		}
		rep.successes++
		rep.durations = append(rep.durations, res.duration)
	}
	rep.wallClock = time.Since(start)
	return rep
}

// seedAccountRaw inserts a bare account row directly (no coach/cards), the
// minimum for a login that then creates its coach over the wire.
func seedAccountRaw(a *app.App, login, password string) error {
	hash, err := service.HashPassword(password)
	if err != nil {
		return err
	}
	return a.DB.Exec("INSERT INTO accounts (name, password_hash, connected, is_admin) VALUES (?, ?, false, false)", login, hash).Error
}

// createFighter drives RecvFighterCreateRequest (6001) for an empty-loadout
// fighter (breed 1, no spells/cards) -- legal and fightable per the e2e
// suite -- and returns the new fighter's DB id from FIGHTER_CREATE_RESULT.
func createFighter(c *botclient.Client, name string) (int64, error) {
	w := protocol.NewWriter(0)
	w.PutInt16(0)     // legacy leading short (ignored)
	w.PutByte(1)      // client-version byte (unused)
	w.PutInt16(100)   // client budget (ignored, recomputed server-side)
	w.PutByte(1)      // breed
	w.PutString(name) // name
	w.PutByte(0)      // sex
	w.PutByte(0)      // skin
	w.PutUint16(0)    // spell blob length (empty)
	w.PutUint16(0)    // object blob length (empty)
	if err := c.Send(3, protocol.RecvFighterCreateRequest, w.Bytes()); err != nil {
		return 0, err
	}
	result, err := c.Expect(protocol.SendFighterCreateResult, 0)
	if err != nil {
		return 0, err
	}
	r := protocol.NewReader(result)
	errCode := r.Byte()
	fighterID := r.Int64()
	if errCode != 0 {
		return 0, fmt.Errorf("fighter create error code = %d", errCode)
	}
	return fighterID, nil
}

// runOneLoadTestFight drives one complete fight (login -> matchmaking ->
// fight setup -> presentation -> forfeit -> END_FIGHT -> ack) for a
// uniquely-numbered pair of accounts, using internal/botclient for all wire
// interaction. Mirrors test/e2e/load_test.go's runOneFightToForfeit.
func runOneLoadTestFight(a *app.App, addr string, idx int) (err error) {
	defer func() {
		if r := recover(); r != nil {
			err = fmt.Errorf("panic: %v", r)
		}
	}()

	loginA := fmt.Sprintf("loadalice%d", idx)
	loginB := fmt.Sprintf("loadbob%d", idx)
	if err := seedAccountRaw(a, loginA, "pw"); err != nil {
		return fmt.Errorf("seed alice: %w", err)
	}
	if err := seedAccountRaw(a, loginB, "pw"); err != nil {
		return fmt.Errorf("seed bob: %w", err)
	}

	cAlice, err := botclient.Dial(addr, 10*time.Second, 30*time.Second, 10*time.Second)
	if err != nil {
		return fmt.Errorf("dial alice: %w", err)
	}
	defer cAlice.Close()
	cBob, err := botclient.Dial(addr, 10*time.Second, 30*time.Second, 10*time.Second)
	if err != nil {
		return fmt.Errorf("dial bob: %w", err)
	}
	defer cBob.Close()

	sessA, err := cAlice.Login(loginA, "pw", "LoadAlice"+fmt.Sprint(idx), botclient.CoachLook{})
	if err != nil {
		return fmt.Errorf("alice login: %w", err)
	}
	sessB, err := cBob.Login(loginB, "pw", "LoadBob"+fmt.Sprint(idx), botclient.CoachLook{})
	if err != nil {
		return fmt.Errorf("bob login: %w", err)
	}

	// Grant each freshly-created coach one unlocked, unequipped card so a
	// bet fight can select a stake (see stakeCardTemplateID).
	if stakeCardTemplateID != 0 {
		ctx := context.Background()
		if _, err := a.Deps.Coach.AddCard(ctx, uint(sessA.CoachID), stakeCardTemplateID, 1, 0); err != nil {
			return fmt.Errorf("grant alice stake card: %w", err)
		}
		if _, err := a.Deps.Coach.AddCard(ctx, uint(sessB.CoachID), stakeCardTemplateID, 1, 0); err != nil {
			return fmt.Errorf("grant bob stake card: %w", err)
		}
	}

	aliceFighterID, err := createFighter(cAlice, "AF"+fmt.Sprint(idx))
	if err != nil {
		return fmt.Errorf("alice create fighter: %w", err)
	}
	bobFighterID, err := createFighter(cBob, "BF"+fmt.Sprint(idx))
	if err != nil {
		return fmt.Errorf("bob create fighter: %w", err)
	}

	// Use a unique "bet" value per fight index so this fight's Alice/Bob
	// are deterministically paired with EACH OTHER rather than
	// cross-matching with a different concurrently-running fight's pair
	// (the matchmaker pairs identical (Type, Bet) tuples; see
	// internal/world/matchmaking.go).
	bet := int32(idx)
	if err := cAlice.SearchOpponent(protocol.FightTypeMatchmakingDefy, bet); err != nil {
		return err
	}
	if _, err := cAlice.Expect(protocol.SendOpponentSearchInProgress, 0); err != nil {
		return err
	}
	if err := cBob.SearchOpponent(protocol.FightTypeMatchmakingDefy, bet); err != nil {
		return err
	}
	if _, err := cBob.Expect(protocol.SendOpponentSearchInProgress, 0); err != nil {
		return err
	}

	foundAlice, err := cAlice.Expect(protocol.SendOpponentFound, 0)
	if err != nil {
		return err
	}
	if _, err := cBob.Expect(protocol.SendOpponentFound, 0); err != nil {
		return err
	}
	duelID := protocol.NewReader(foundAlice).Int64()

	if err := cAlice.SetReadyForFight(duelID, aliceFighterID); err != nil {
		return err
	}
	if _, err := cAlice.Expect(protocol.SendReadyForFight, 0); err != nil {
		return err
	}
	if err := cBob.SetReadyForFight(duelID, bobFighterID); err != nil {
		return err
	}
	if _, err := cBob.Expect(protocol.SendReadyForFight, 0); err != nil {
		return err
	}

	if _, err := cAlice.Expect(protocol.SendCreateFight, 0); err != nil {
		return err
	}
	if _, err := cBob.Expect(protocol.SendCreateFight, 0); err != nil {
		return err
	}

	if err := cAlice.ReadyForPlacement(); err != nil {
		return err
	}
	if err := cBob.ReadyForPlacement(); err != nil {
		return err
	}

	if _, err := cAlice.DrainUntil(protocol.SendStartPresentation, 32, 0); err != nil {
		return err
	}
	if _, err := cBob.DrainUntil(protocol.SendStartPresentation, 32, 0); err != nil {
		return err
	}

	if err := cAlice.GiveUp(); err != nil {
		return err
	}
	if _, err := cAlice.DrainUntil(protocol.SendEndFight, 32, 0); err != nil {
		return err
	}
	if _, err := cBob.DrainUntil(protocol.SendEndFight, 32, 0); err != nil {
		return err
	}

	if err := cAlice.EndFightDone(); err != nil {
		return err
	}
	if err := cBob.EndFightDone(); err != nil {
		return err
	}

	return nil
}
