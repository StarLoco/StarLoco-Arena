package game

import "time"

// The fight actor model: each Fight runs a single goroutine that serially
// processes every event affecting fight state (ready signals, moves, casts,
// end-turn, clock firings, teardown). Handlers and timers never touch fight
// state directly — they enqueue an event via Post. This removes ALL locks on
// fight state and eliminates data races by construction, which is essential for
// running thousands of concurrent fights efficiently (no lock contention).

// fightEvent is a unit of work processed on the fight's own goroutine. It
// returns nothing; it mutates fight state and broadcasts as needed.
type fightEvent func(f *Fight)

// mailboxSize bounds a fight's pending-event queue. A 1v1 fight generates few
// events; a small buffer avoids blocking the sender in normal play.
const mailboxSize = 64

// startActor launches the fight's processing goroutine. Called once at Create.
func (f *Fight) startActor() {
	f.mailbox = make(chan fightEvent, mailboxSize)
	f.done = make(chan struct{})
	f.stopped = make(chan struct{})
	go f.run()
}

// run is the single goroutine owning all fight state.
func (f *Fight) run() {
	if f.stopped != nil {
		defer close(f.stopped)
	}
	for {
		select {
		case ev := <-f.mailbox:
			ev(f)
		case <-f.done:
			return
		}
	}
}

// waitStopped blocks until the run goroutine has fully exited (test helper).
func (f *Fight) waitStopped() {
	if f.stopped != nil {
		<-f.stopped
	}
}

// Post enqueues an event to run on the fight goroutine. Non-blocking: if the
// mailbox is full (pathological), the event is dropped rather than stalling the
// caller (a session or timer goroutine). Returns false if the fight has stopped.
func (f *Fight) Post(ev fightEvent) bool {
	select {
	case <-f.done:
		return false
	default:
	}
	select {
	case f.mailbox <- ev:
		return true
	case <-f.done:
		return false
	default:
		// Mailbox full: drop. For a 1v1 this should never happen; if it does,
		// dropping a redundant ready/move is safer than blocking the caller.
		return false
	}
}

// stopActor signals the run goroutine to exit (idempotent, nil-safe for fights
// created without an actor, e.g. in unit tests).
func (f *Fight) stopActor() {
	if f.done == nil {
		return
	}
	f.stopOnce.Do(func() { close(f.done) })
}

// postAfter schedules ev to be posted to the fight goroutine after d, unless
// the fight ends first. Replaces the raw timer: the callback runs ON the fight
// goroutine, so it's race-free. The generation guard drops a stale timer.
func (f *Fight) postAfter(d time.Duration, gen uint64, ev fightEvent) *time.Timer {
	return time.AfterFunc(d, func() {
		f.Post(func(f *Fight) {
			if gen != f.clockGen || f.Phase() == PhaseEnded {
				return // stale timer
			}
			ev(f)
		})
	})
}

// armGrace schedules ev to run on the fight goroutine after d as the reconnect
// grace timer — INDEPENDENT of the phase/turn clock (its own generation), so
// arming a turn clock never cancels it. Must be called from inside the actor.
func (f *Fight) armGrace(d time.Duration, ev fightEvent) {
	f.stopGrace()
	f.graceGen++
	gen := f.graceGen
	f.graceTimer = time.AfterFunc(d, func() {
		f.Post(func(f *Fight) {
			if gen != f.graceGen || f.Phase() == PhaseEnded {
				return // stale (reconnected / already ended)
			}
			ev(f)
		})
	})
}

// stopGrace cancels a pending reconnect grace timer. Called from inside the actor
// (on reconnect or when the fight ends).
func (f *Fight) stopGrace() {
	f.graceGen++
	if f.graceTimer != nil {
		f.graceTimer.Stop()
		f.graceTimer = nil
	}
}
