package main

import (
	"context"
	"sync/atomic"
	"testing"
	"time"
)

func TestPairBroker_MatchesTwoWaiters(t *testing.T) {
	var keys atomic.Int64
	b := newPairBroker(func() int64 { return keys.Add(1) })

	type res struct {
		m  pairMatch
		ok bool
	}
	ch := make(chan res, 2)
	go func() {
		m, ok := b.request(context.Background(), "alice", 1)
		ch <- res{m, ok}
	}()
	// Give alice time to park as the waiter.
	time.Sleep(20 * time.Millisecond)
	go func() {
		m, ok := b.request(context.Background(), "bob", 2)
		ch <- res{m, ok}
	}()

	r1 := <-ch
	r2 := <-ch
	if !r1.ok || !r2.ok {
		t.Fatalf("both requests should succeed: %+v %+v", r1, r2)
	}
	if r1.m.Key != r2.m.Key {
		t.Fatalf("paired bots must share a key: %d vs %d", r1.m.Key, r2.m.Key)
	}
	// Exactly one initiator.
	if r1.m.Initiator == r2.m.Initiator {
		t.Fatalf("exactly one bot must be the initiator, got %v/%v", r1.m.Initiator, r2.m.Initiator)
	}
	// Each learns the other's identity.
	if r1.m.PartnerName == "" || r2.m.PartnerName == "" {
		t.Fatal("partner names should be populated")
	}
}

func TestPairBroker_CancelUnparksWaiter(t *testing.T) {
	b := newPairBroker(func() int64 { return 1 })
	ctx, cancel := context.WithCancel(context.Background())

	done := make(chan bool, 1)
	go func() {
		_, ok := b.request(ctx, "lonely", 1)
		done <- ok
	}()
	time.Sleep(20 * time.Millisecond)
	cancel()

	select {
	case ok := <-done:
		if ok {
			t.Fatal("canceled request should return ok=false")
		}
	case <-time.After(time.Second):
		t.Fatal("canceled request did not return")
	}

	// A subsequent single requester must NOT be matched against the stale
	// (canceled) waiter -- it should park, not complete.
	parked := make(chan bool, 1)
	ctx2, cancel2 := context.WithTimeout(context.Background(), 50*time.Millisecond)
	defer cancel2()
	go func() {
		_, ok := b.request(ctx2, "next", 2)
		parked <- ok
	}()
	if ok := <-parked; ok {
		t.Fatal("next requester should not match a canceled waiter")
	}
}

func TestMetrics_RecordsAndDedupsFailures(t *testing.T) {
	m := newMetrics()
	m.record("walk", 5*time.Millisecond, nil, "")
	m.record("walk", 7*time.Millisecond, nil, "")
	err := context.DeadlineExceeded
	m.record("fight", 0, err, "drive: timeout")
	m.record("fight", 0, err, "drive: timeout")
	m.record("fight", 0, err, "create-fight: canceled")

	fr := m.build()

	var walk, fight *behaviorReport
	for i := range fr.Behaviors {
		switch fr.Behaviors[i].Behavior {
		case "walk":
			walk = &fr.Behaviors[i]
		case "fight":
			fight = &fr.Behaviors[i]
		}
	}
	if walk == nil || walk.Succeeded != 2 || walk.Failed != 0 {
		t.Fatalf("walk stats wrong: %+v", walk)
	}
	if fight == nil || fight.Failed != 3 {
		t.Fatalf("fight failed count wrong: %+v", fight)
	}
	// The two identical "drive: timeout" failures dedup to one entry
	// with count=2; the distinct "create-fight" one is separate.
	var timeoutCount int64
	distinct := 0
	for _, f := range fr.Failures {
		if f.Behavior == "fight" {
			distinct++
			if f.Message == "drive: timeout" {
				timeoutCount = f.Count
			}
		}
	}
	if distinct != 2 {
		t.Fatalf("expected 2 distinct fight failures, got %d", distinct)
	}
	if timeoutCount != 2 {
		t.Fatalf("expected drive:timeout count=2, got %d", timeoutCount)
	}
}
