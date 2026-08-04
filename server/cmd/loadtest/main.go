// Command loadtest spins up N concurrent bot clients against a running server
// to measure how many simultaneous coaches one instance sustains, reporting
// connection success, latency and (if the server is in-process) resource use.
//
//	# terminal 1: run the server
//	go run ./cmd/server --config configs/config.sqlite.yaml
//	# terminal 2: hammer it with 5000 bots
//	go run ./cmd/loadtest --addr 127.0.0.1:5555 --clients 5000
package main

import (
	"flag"
	"fmt"
	"os"
	"sync"
	"sync/atomic"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

func main() {
	addr := flag.String("addr", "127.0.0.1:5555", "server address")
	n := flag.Int("clients", 1000, "number of concurrent bot clients")
	ramp := flag.Duration("ramp", 5*time.Second, "time to ramp up all clients")
	hold := flag.Duration("hold", 20*time.Second, "how long to hold the connections after ramp")
	chatEvery := flag.Duration("chat", 3*time.Second, "each bot sends a vicinity chat this often (0=off)")
	flag.Parse()

	var (
		connected  atomic.Int64
		failed     atomic.Int64
		inWorld    atomic.Int64
		chatsSent  atomic.Int64
		loginNanos atomic.Int64 // sum of login latencies
	)

	stop := make(chan struct{})
	var wg sync.WaitGroup
	gap := *ramp / time.Duration(max(*n, 1))

	fmt.Printf("ramping %d clients over %s to %s...\n", *n, *ramp, *addr)
	start := time.Now()

	for i := 0; i < *n; i++ {
		time.Sleep(gap)
		wg.Add(1)
		go func(id int) {
			defer wg.Done()
			c, err := testclient.Dial(*addr)
			if err != nil {
				failed.Add(1)
				return
			}
			defer c.Close()

			t0 := time.Now()
			login := fmt.Sprintf("bot%05d", id)
			if err := c.Login(login, "pw"); err != nil {
				failed.Add(1)
				return
			}
			if _, err := c.CreateCoach(fmt.Sprintf("Bot%05d", id)); err != nil {
				failed.Add(1)
				return
			}
			loginNanos.Add(int64(time.Since(t0)))
			connected.Add(1)

			// Reach the world (drain the post-login burst).
			if _, _, err := c.WaitFor(testclient.OpEnterInstance, 5*time.Second); err == nil {
				inWorld.Add(1)
			}
			c.DrainReceived(100 * time.Millisecond)

			// Spread bots across a large map so AoI scoping is exercised (chat
			// only reaches nearby bots, not all of them). Grid of ~mapSpread.
			mx := int32((id % 100) * 40)
			my := int32((id / 100) * 40)
			mv := testclient.NewW().I32(mx).I32(my).U16(0).Bytes()
			_ = c.Send(3, 4501, mv) // overworld move request
			c.DrainReceived(30 * time.Millisecond)

			// Hold + periodic activity (chat broadcast is the fan-out stressor).
			ticker := time.NewTicker(maxDur(*chatEvery, time.Second))
			defer ticker.Stop()
			for {
				select {
				case <-stop:
					return
				case <-ticker.C:
					if *chatEvery > 0 {
						msg := "hello from " + login
						p := testclient.NewW().U16(uint16(len(msg))).Raw([]byte(msg)).Bytes()
						if c.Send(3, 3153, p) == nil {
							chatsSent.Add(1)
						}
					}
					// Drain incoming (broadcasts from other bots) so our read
					// buffer doesn't fill and cause the server to kick us.
					c.DrainReceived(20 * time.Millisecond)
				}
			}
		}(i)
	}

	rampDone := time.Now()
	fmt.Printf("ramp complete in %s; holding for %s...\n", rampDone.Sub(start).Round(time.Millisecond), *hold)

	// Progress ticker during hold.
	holdDeadline := time.Now().Add(*hold)
	for time.Now().Before(holdDeadline) {
		time.Sleep(2 * time.Second)
		fmt.Printf("  connected=%d inWorld=%d failed=%d chats=%d\n",
			connected.Load(), inWorld.Load(), failed.Load(), chatsSent.Load())
	}

	close(stop)
	wg.Wait()

	c := connected.Load()
	avgLogin := time.Duration(0)
	if c > 0 {
		avgLogin = time.Duration(loginNanos.Load() / c)
	}
	fmt.Println("\n=== load test result ===")
	fmt.Printf("target clients : %d\n", *n)
	fmt.Printf("connected      : %d\n", c)
	fmt.Printf("reached world  : %d\n", inWorld.Load())
	fmt.Printf("failed         : %d\n", failed.Load())
	fmt.Printf("chats sent     : %d\n", chatsSent.Load())
	fmt.Printf("avg login time : %s\n", avgLogin.Round(time.Millisecond))

	if failed.Load() > int64(*n)/20 { // >5% failures
		os.Exit(1)
	}
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func maxDur(a, b time.Duration) time.Duration {
	if a > b {
		return a
	}
	return b
}
