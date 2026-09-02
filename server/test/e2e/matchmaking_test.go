package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// searchRequest builds an OpponentSearchRequest(2301): [i16 mode][i16 sub][i32 N].
func searchRequest() []byte {
	return testclient.NewW().U16(1).U16(0).I32(0).Bytes()
}

// TestSearchInProgressThenCancel: a lone searcher is told the search is live
// (2304), then cancelling (2303) yields a success CancelResult(2306, 0).
func TestSearchInProgressThenCancel(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "mm_a", "MmA")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	// Queue with no opponent -> SearchInProgress(2304).
	_ = a.Send(2, testclient.OpSearch, searchRequest())
	if _, _, err := a.WaitFor(testclient.OpSearchInProgress, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no SearchInProgress(2304): %v", err)
	}

	// Cancel -> CancelResult(2306) with result 0 (something was cancelled).
	_ = a.Send(2, testclient.OpSearchCancel, nil)
	f, _, err := a.WaitFor(testclient.OpSearchCancelResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no CancelResult(2306): %v", err)
	}
	if res := testclient.NewR(f.Payload).U8(); res != 0 {
		t.Fatalf("cancel result = %d, want 0 (cancelled)", res)
	}
}

// TestCancelWithoutSearch: cancelling when nothing is queued replies with the
// idempotent "nothing" result (2306, 1).
func TestCancelWithoutSearch(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "mm_b", "MmB")
	reachWorld(t, a)
	a.DrainReceived(150 * time.Millisecond)

	_ = a.Send(2, testclient.OpSearchCancel, nil)
	f, _, err := a.WaitFor(testclient.OpSearchCancelResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no CancelResult(2306): %v", err)
	}
	if res := testclient.NewR(f.Payload).U8(); res != 1 {
		t.Fatalf("cancel result = %d, want 1 (nothing queued)", res)
	}
}

// TestCancelReleasesQueueSlot: after A cancels, a later searcher B is NOT
// matched with A (A truly left the queue) — B just gets its own in-progress.
func TestCancelReleasesQueueSlot(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, _ := dialLogin(t, addr, "mm_c", "MmC")
	reachWorld(t, a)
	b, _ := dialLogin(t, addr, "mm_d", "MmD")
	reachWorld(t, b)
	a.DrainReceived(150 * time.Millisecond)
	b.DrainReceived(150 * time.Millisecond)

	// A queues, then cancels.
	_ = a.Send(2, testclient.OpSearch, searchRequest())
	if _, _, err := a.WaitFor(testclient.OpSearchInProgress, testclient.DefaultTimeout); err != nil {
		t.Fatalf("A no SearchInProgress: %v", err)
	}
	_ = a.Send(2, testclient.OpSearchCancel, nil)
	if _, _, err := a.WaitFor(testclient.OpSearchCancelResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("A no CancelResult: %v", err)
	}

	// B queues -> should get in-progress, NOT a MatchFound (A already left).
	_ = b.Send(2, testclient.OpSearch, searchRequest())
	f, seen, err := b.WaitFor2(testclient.OpSearchInProgress, testclient.OpMatchFound, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("B got neither in-progress nor match: %v", err)
	}
	if f.Opcode == testclient.OpMatchFound {
		t.Fatal("B was matched with A after A cancelled (queue slot not released)")
	}
	_ = seen

	// A must NOT receive a MatchFound either.
	for _, fr := range a.DrainReceived(200 * time.Millisecond) {
		if fr.Opcode == testclient.OpMatchFound {
			t.Fatal("A received MatchFound after cancelling")
		}
	}
}
