package main

import (
	"encoding/csv"
	"encoding/json"
	"fmt"
	"os"
	"sort"
	"strconv"
	"sync"
	"time"
)

// report.go aggregates per-behavior success/failure/latency across the whole
// swarm and renders a live console ticker plus a final console summary and
// optional JSON/CSV files. It is the "what failed under load" record the
// swarm is designed to produce (an E2E-at-scale harness).

// behaviorStat accumulates counters + latencies for one behavior (login,
// walk, chat, fight, exchange, ...).
type behaviorStat struct {
	attempted int64
	succeeded int64
	failed    int64
	latencies []time.Duration
}

// metrics is the concurrent-safe swarm-wide aggregator.
type metrics struct {
	mu    sync.Mutex
	stats map[string]*behaviorStat
	// failures dedups distinct failure messages -> count + a sample.
	failures map[string]*failureAgg
	// live gauges
	botsOnline int64
	startTime  time.Time
}

type failureAgg struct {
	Behavior string
	Message  string
	Count    int64
}

func newMetrics() *metrics {
	return &metrics{
		stats:     make(map[string]*behaviorStat),
		failures:  make(map[string]*failureAgg),
		startTime: time.Now(),
	}
}

func (m *metrics) stat(behavior string) *behaviorStat {
	s, ok := m.stats[behavior]
	if !ok {
		s = &behaviorStat{}
		m.stats[behavior] = s
	}
	return s
}

// record logs one behavior attempt outcome. On failure, msg is the deduped
// error text (keep it stable/low-cardinality: wrap dynamic ids out).
func (m *metrics) record(behavior string, latency time.Duration, err error, msg string) {
	m.mu.Lock()
	defer m.mu.Unlock()
	s := m.stat(behavior)
	s.attempted++
	if err == nil {
		s.succeeded++
		s.latencies = append(s.latencies, latency)
		return
	}
	s.failed++
	key := behavior + ": " + msg
	agg, ok := m.failures[key]
	if !ok {
		agg = &failureAgg{Behavior: behavior, Message: msg}
		m.failures[key] = agg
	}
	agg.Count++
}

func (m *metrics) addOnline(delta int64) {
	m.mu.Lock()
	m.botsOnline += delta
	m.mu.Unlock()
}

// snapshotLine renders a compact one-line live status for the console
// ticker.
func (m *metrics) snapshotLine() string {
	m.mu.Lock()
	defer m.mu.Unlock()
	elapsed := time.Since(m.startTime).Round(time.Second)
	line := fmt.Sprintf("[%s] online=%d", elapsed, m.botsOnline)
	// Deterministic behavior order for a stable ticker.
	for _, b := range behaviorOrder(m.stats) {
		s := m.stats[b]
		line += fmt.Sprintf(" | %s ok=%d fail=%d", b, s.succeeded, s.failed)
	}
	return line
}

func behaviorOrder(stats map[string]*behaviorStat) []string {
	out := make([]string, 0, len(stats))
	for b := range stats {
		out = append(out, b)
	}
	sort.Strings(out)
	return out
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

// --- final report structures (also the JSON schema) ---

type behaviorReport struct {
	Behavior  string `json:"behavior"`
	Attempted int64  `json:"attempted"`
	Succeeded int64  `json:"succeeded"`
	Failed    int64  `json:"failed"`
	P50ms     int64  `json:"p50_ms"`
	P90ms     int64  `json:"p90_ms"`
	P99ms     int64  `json:"p99_ms"`
	MaxMs     int64  `json:"max_ms"`
}

type failureReport struct {
	Behavior string `json:"behavior"`
	Message  string `json:"message"`
	Count    int64  `json:"count"`
}

type finalReport struct {
	WallClockSec float64          `json:"wall_clock_sec"`
	Behaviors    []behaviorReport `json:"behaviors"`
	Failures     []failureReport  `json:"failures"`
}

func (m *metrics) build() finalReport {
	m.mu.Lock()
	defer m.mu.Unlock()

	fr := finalReport{WallClockSec: time.Since(m.startTime).Seconds()}
	for _, b := range behaviorOrder(m.stats) {
		s := m.stats[b]
		sorted := append([]time.Duration(nil), s.latencies...)
		sort.Slice(sorted, func(i, j int) bool { return sorted[i] < sorted[j] })
		var maxMs int64
		if len(sorted) > 0 {
			maxMs = sorted[len(sorted)-1].Milliseconds()
		}
		fr.Behaviors = append(fr.Behaviors, behaviorReport{
			Behavior:  b,
			Attempted: s.attempted,
			Succeeded: s.succeeded,
			Failed:    s.failed,
			P50ms:     percentile(sorted, 0.50).Milliseconds(),
			P90ms:     percentile(sorted, 0.90).Milliseconds(),
			P99ms:     percentile(sorted, 0.99).Milliseconds(),
			MaxMs:     maxMs,
		})
	}
	for _, agg := range m.failures {
		fr.Failures = append(fr.Failures, failureReport{Behavior: agg.Behavior, Message: agg.Message, Count: agg.Count})
	}
	sort.Slice(fr.Failures, func(i, j int) bool { return fr.Failures[i].Count > fr.Failures[j].Count })
	return fr
}

// printConsole renders the final human-readable report.
func (fr finalReport) printConsole() {
	fmt.Println()
	fmt.Println("=== botswarm report ===")
	fmt.Printf("wall-clock: %.1fs\n", fr.WallClockSec)
	fmt.Println()
	fmt.Printf("%-12s %9s %9s %9s   %7s %7s %7s %7s\n", "behavior", "attempt", "ok", "fail", "p50ms", "p90ms", "p99ms", "maxms")
	for _, b := range fr.Behaviors {
		fmt.Printf("%-12s %9d %9d %9d   %7d %7d %7d %7d\n",
			b.Behavior, b.Attempted, b.Succeeded, b.Failed, b.P50ms, b.P90ms, b.P99ms, b.MaxMs)
	}
	if len(fr.Failures) > 0 {
		fmt.Println()
		fmt.Println("--- distinct failures (by count) ---")
		max := len(fr.Failures)
		if max > 25 {
			max = 25
		}
		for _, f := range fr.Failures[:max] {
			fmt.Printf("  %5d  [%s] %s\n", f.Count, f.Behavior, f.Message)
		}
		if len(fr.Failures) > max {
			fmt.Printf("  ... and %d more distinct failures (see JSON report)\n", len(fr.Failures)-max)
		}
	}
}

// writeJSON writes the report as pretty JSON.
func (fr finalReport) writeJSON(path string) error {
	b, err := json.MarshalIndent(fr, "", "  ")
	if err != nil {
		return err
	}
	return os.WriteFile(path, b, 0o644)
}

// writeCSV writes the per-behavior table as CSV (failures go to JSON only,
// since their free-text messages don't tabulate cleanly).
func (fr finalReport) writeCSV(path string) error {
	f, err := os.Create(path)
	if err != nil {
		return err
	}
	defer f.Close()
	w := csv.NewWriter(f)
	defer w.Flush()
	_ = w.Write([]string{"behavior", "attempted", "succeeded", "failed", "p50_ms", "p90_ms", "p99_ms", "max_ms"})
	for _, b := range fr.Behaviors {
		_ = w.Write([]string{
			b.Behavior,
			strconv.FormatInt(b.Attempted, 10),
			strconv.FormatInt(b.Succeeded, 10),
			strconv.FormatInt(b.Failed, 10),
			strconv.FormatInt(b.P50ms, 10),
			strconv.FormatInt(b.P90ms, 10),
			strconv.FormatInt(b.P99ms, 10),
			strconv.FormatInt(b.MaxMs, 10),
		})
	}
	return w.Error()
}
