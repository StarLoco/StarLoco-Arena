package game

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// Coach.TimeInFightSecs and Coach.TotalPlaySecs were declared, written to the
// wire (the 2400 statistics panel's dL/dM entries), persisted by CoachRepo.Save
// and read by a test — but incremented nowhere, so both showed 0 forever. The
// web portal's account page surfaces the same two values, which made the gap
// visible twice over.

func TestCreditFightTimeAddsToEveryParticipant(t *testing.T) {
	d := &Deps{Log: testLogger()}
	a := &domain.Coach{ID: 1, Name: "A"}
	b := &domain.Coach{ID: 2, Name: "B"}
	f := &Fight{
		Teams:     [2]*FightTeam{{ID: 0, Members: []*FightMember{{Coach: a}}}, {ID: 1, Members: []*FightMember{{Coach: b}}}},
		startedAt: time.Now().Add(-90 * time.Second),
	}

	d.creditFightTime(f)

	for _, c := range []*domain.Coach{a, b} {
		if c.TimeInFightSecs < 89 || c.TimeInFightSecs > 92 {
			t.Errorf("%s TimeInFightSecs = %d, want ~90", c.Name, c.TimeInFightSecs)
		}
	}
}

// A fight can end three ways — a declared winner, a forfeit, or a teardown with
// no winner — and each calls creditFightTime. Crediting twice would inflate
// every player's lifetime total on every single fight.
func TestCreditFightTimeIsIdempotent(t *testing.T) {
	d := &Deps{Log: testLogger()}
	c := &domain.Coach{ID: 1, Name: "A"}
	f := &Fight{
		Teams:     [2]*FightTeam{{ID: 0, Members: []*FightMember{{Coach: c}}}, nil},
		startedAt: time.Now().Add(-60 * time.Second),
	}

	d.creditFightTime(f)
	first := c.TimeInFightSecs
	d.creditFightTime(f)
	d.creditFightTime(f)

	if c.TimeInFightSecs != first {
		t.Errorf("crediting three times gave %d, want %d — the CAS guard is not holding",
			c.TimeInFightSecs, first)
	}
	if first == 0 {
		t.Fatal("nothing was credited at all")
	}
}

// Practice fights are excluded from wins, losses and ladder movement because
// those are competitive records. Time spent is not a competitive record: an
// hour of sparring is still an hour played.
func TestCreditFightTimeCountsPracticeFights(t *testing.T) {
	d := &Deps{Log: testLogger()}
	c := &domain.Coach{ID: 1, Name: "A"}
	f := &Fight{
		Practice:  true,
		Teams:     [2]*FightTeam{{ID: 0, Members: []*FightMember{{Coach: c}}}, nil},
		startedAt: time.Now().Add(-30 * time.Second),
	}

	d.creditFightTime(f)

	if c.TimeInFightSecs == 0 {
		t.Error("a practice fight credited no time")
	}
}

// A fight shorter than a second must not be credited at all rather than
// rounding up — otherwise a burst of instant test/forfeit fights would inflate
// the counter.
func TestCreditFightTimeIgnoresSubSecondFights(t *testing.T) {
	d := &Deps{Log: testLogger()}
	c := &domain.Coach{ID: 1, Name: "A"}
	f := &Fight{
		Teams:     [2]*FightTeam{{ID: 0, Members: []*FightMember{{Coach: c}}}, nil},
		startedAt: time.Now(),
	}

	d.creditFightTime(f)

	if c.TimeInFightSecs != 0 {
		t.Errorf("a sub-second fight credited %d seconds", c.TimeInFightSecs)
	}
}

// A Fight built without going through FightManager.Create has no start time.
// Crediting from a zero timestamp would add ~57 years to the coach's total.
func TestCreditFightTimeIgnoresZeroStart(t *testing.T) {
	d := &Deps{Log: testLogger()}
	c := &domain.Coach{ID: 1, Name: "A"}
	f := &Fight{Teams: [2]*FightTeam{{ID: 0, Members: []*FightMember{{Coach: c}}}, nil}} // startedAt zero

	d.creditFightTime(f)

	if c.TimeInFightSecs != 0 {
		t.Errorf("a fight with no start time credited %d seconds", c.TimeInFightSecs)
	}
}

// FightManager.Create is the one chokepoint every fight passes through, so it
// is where the clock starts.
func TestFightManagerStampsStartTime(t *testing.T) {
	m := NewFightManager()
	f := &Fight{}
	m.Create(f)

	if f.startedAt.IsZero() {
		t.Fatal("Create did not stamp startedAt — every fight would credit zero time")
	}
	if time.Since(f.startedAt) > time.Minute {
		t.Errorf("startedAt = %v, expected roughly now", f.startedAt)
	}
}

// ---------------------------------------------------------------------------
// Play time
// ---------------------------------------------------------------------------

func TestCreditPlayTimeAccumulates(t *testing.T) {
	c := &domain.Coach{ID: 1, Name: "A"}
	s := &Session{Coach: c, playSince: time.Now().Add(-120 * time.Second)}

	s.creditPlayTime()

	if c.TotalPlaySecs < 119 || c.TotalPlaySecs > 122 {
		t.Errorf("TotalPlaySecs = %d, want ~120", c.TotalPlaySecs)
	}
}

// onClose can run more than once (kick then socket teardown), and the session
// is also credited before the replaced-session early return. Double crediting
// would hand a reconnecting player free hours.
func TestCreditPlayTimeIsIdempotent(t *testing.T) {
	c := &domain.Coach{ID: 1, Name: "A"}
	s := &Session{Coach: c, playSince: time.Now().Add(-60 * time.Second)}

	s.creditPlayTime()
	first := c.TotalPlaySecs
	s.creditPlayTime()

	if c.TotalPlaySecs != first {
		t.Errorf("crediting twice gave %d, want %d", c.TotalPlaySecs, first)
	}
	if first == 0 {
		t.Fatal("nothing was credited at all")
	}
}

// A session that never got as far as a coach (auth failed, or it dropped at the
// character-select screen) has nothing to credit.
func TestCreditPlayTimeWithoutCoachIsSafe(t *testing.T) {
	s := &Session{playSince: time.Now().Add(-60 * time.Second)}
	s.creditPlayTime() // must not panic
}
