package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestSelfTradeIsRefused covers the trade-lockout primitive: Start(s, s) passed
// both busy checks, then wrote byCoach twice leaving ex.A == ex.B. sideOf always
// returned 0, so ready[1] could never be set - the exchange could never complete
// while the coach stayed permanently "busy".
func TestSelfTradeIsRefused(t *testing.T) {
	m := NewExchangeManager()
	s := sessionWithCoach(1, "Solo")

	if ex := m.Start(s, s); ex != nil {
		t.Fatal("a coach was allowed to open an exchange with itself")
	}
	// And crucially it must NOT be marked busy by the failed attempt.
	other := sessionWithCoach(2, "Partner")
	if ex := m.Start(s, other); ex == nil {
		t.Error("the refused self-trade left the coach locked out of trading")
	}
}

// TestExchangeRejectsCoachlessSessions guards the same entry point against the
// ghost-coach class: 27529 nils Session.Coach while the socket stays up.
func TestExchangeRejectsCoachlessSessions(t *testing.T) {
	m := NewExchangeManager()
	live := sessionWithCoach(1, "Live")
	ghost := &Session{}

	if ex := m.Start(ghost, live); ex != nil {
		t.Error("an exchange was opened by a coachless session")
	}
	if ex := m.Start(live, ghost); ex != nil {
		t.Error("an exchange was opened against a coachless session")
	}
}

var _ = domain.Coach{}
