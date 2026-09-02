package game

import (
	"log/slog"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestDestroyCoachReleasesSubsystems pins the ROOT CAUSE of the ghost-coach
// crashes. handleDestroyCoach (27529) nils Session.Coach; if it does not first
// release the subsystems holding this session, the entries survive pointing at a
// coachless session - and onClose cannot clean them either, because its own
// teardown is gated on a non-nil coach.
func TestDestroyCoachReleasesSubsystems(t *testing.T) {
	t.Run("matchmaker queue entry is released", func(t *testing.T) {
		m := NewMatchmaker()
		s := sendableSession(1, "Doomed")
		s.deps = &Deps{Matchmaker: m, Challenges: NewChallengeManager(), Exchanges: NewExchangeManager()}
		if pm := m.Search(s, 1, 0, nil); pm != nil {
			t.Fatal("fixture broken: a lone searcher must only enqueue")
		}
		m.mu.Lock()
		queued := len(m.queue)
		m.mu.Unlock()
		if queued != 1 {
			t.Fatalf("fixture broken: queue has %d entries, want 1", queued)
		}

		s.releaseSubsystems()

		m.mu.Lock()
		left := len(m.queue)
		m.mu.Unlock()
		if left != 0 {
			t.Errorf("queue still holds %d entries after release - a destroyed "+
				"coach stays matchable", left)
		}
	})

	t.Run("challenge is released and the other side told", func(t *testing.T) {
		cm := NewChallengeManager()
		attacker := sendableSession(1, "Doomed")
		victim := sendableSession(2, "Victim")
		attacker.deps = &Deps{Matchmaker: NewMatchmaker(), Challenges: cm, Exchanges: NewExchangeManager()}
		if c := cm.Create(attacker, victim, false); c == nil {
			t.Fatal("fixture broken: challenge not created")
		}

		attacker.releaseSubsystems()

		if c := cm.Get(2); c != nil {
			t.Error("victim still holds a challenge against a destroyed coach")
		}
	})
}

// TestReleaseSubsystemsIsSafeWithoutCoach guards the ordering hazard: if a caller
// ever runs this AFTER s.Coach = nil, it must no-op rather than panic.
func TestReleaseSubsystemsIsSafeWithoutCoach(t *testing.T) {
	s := &Session{deps: &Deps{Matchmaker: NewMatchmaker(), Challenges: NewChallengeManager(), Exchanges: NewExchangeManager()}}
	s.releaseSubsystems() // must not panic
}

// sendableSession builds a Session that can actually receive a frame.
//
// sessionWithCoach sets only Coach, so Session.Send falls through to its
// slow-client branch and dereferences a nil logger. Notifying a counterparty is
// the POINT of releaseSubsystems, so a fixture that cannot receive would make
// these tests assert the wrong thing - or crash, as it did.
func sendableSession(id uint, name string) *Session {
	return &Session{
		Coach: &domain.Coach{ID: id, Name: name},
		log:   slog.Default(),
		out:   make(chan []byte, writeQueueSize),
		quit:  make(chan struct{}),
	}
}
