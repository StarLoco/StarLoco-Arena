package game

import (
	"path/filepath"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// fightCreationDeps builds a Deps backed by a temp store with the managers the
// fight-creation handlers need.
func fightCreationDeps(t *testing.T) (*Deps, *store.Store) {
	t.Helper()
	st, err := store.Open(filepath.Join(t.TempDir(), "fc.db"))
	if err != nil {
		t.Fatalf("open store: %v", err)
	}
	t.Cleanup(func() { _ = st.Close() })
	d := &Deps{
		Store:      st,
		World:      NewRegistry(150),
		Matchmaker: NewMatchmaker(),
		Challenges: NewChallengeManager(),
		Fights:     NewFightManager(),
		Sessions:   NewSessionRegistry(),
		Log:        testLogger(),
	}
	return d, st
}

func fcSession(d *Deps, coach *domain.Coach) *Session {
	return &Session{
		log:   testLogger(),
		deps:  d,
		out:   make(chan []byte, writeQueueSize),
		quit:  make(chan struct{}),
		Coach: coach,
	}
}

// coachWithTeam creates a coach with two fighters and a saved 1v1 preset holding
// them, returning the coach and the preset id.
func coachWithTeam(t *testing.T, st *store.Store, login string) (*domain.Coach, uint) {
	t.Helper()
	acc, err := st.Accounts.CreateAccount(login, "pw", false)
	if err != nil {
		t.Fatalf("create account: %v", err)
	}
	coach, err := st.Coaches.Create(acc.ID, login+"C", 0, 0, 0)
	if err != nil {
		t.Fatalf("create coach: %v", err)
	}
	f1 := &domain.Fighter{CoachID: coach.ID, BreedID: 1, Name: "F1", Budget: 400}
	f2 := &domain.Fighter{CoachID: coach.ID, BreedID: 8, Name: "F2", Budget: 400}
	for _, f := range []*domain.Fighter{f1, f2} {
		if err := st.Fighters.Create(f); err != nil {
			t.Fatalf("create fighter: %v", err)
		}
	}
	team := &domain.Team{CoachID: coach.ID, Name: "T", Type: -6, GameMode: 1}
	if err := st.Teams.Upsert(team); err != nil {
		t.Fatalf("upsert team: %v", err)
	}
	for _, f := range []*domain.Fighter{f1, f2} {
		if err := st.Teams.AddMember(team.ID, f.ID); err != nil {
			t.Fatalf("add member: %v", err)
		}
	}
	return coach, team.ID
}

// stopTestFight tears a running fight actor down deterministically: it first
// drains the start-up event (so the presentation clock is armed) then ends the
// fight and waits for the goroutine to exit — no leaked timer/goroutine.
func stopTestFight(d *Deps, f *Fight) {
	if f == nil {
		return
	}
	barrier := make(chan struct{})
	if f.Post(func(*Fight) { close(barrier) }) {
		<-barrier
	}
	d.endFight(f)
	f.waitStopped()
}

// TestTeamTestLaunchesPracticeFight: pressing "Tester" (26330) starts an unranked
// fight of the caller's team against the synthetic sparring opponent — with no
// second coach — and never persists stats for the dummy side.
func TestTeamTestLaunchesPracticeFight(t *testing.T) {
	d, st := fightCreationDeps(t)
	coach, teamID := coachWithTeam(t, st, "tester")
	s := fcSession(d, coach)

	payload := protocol.NewWriter().I32(12).U16(uint16(teamID)).Bytes()
	if err := handleTeamTest(s, &protocol.C2SFrame{Payload: payload}); err != nil {
		t.Fatalf("handleTeamTest: %v", err)
	}

	f := d.Fights.ByCoach(coach.ID)
	if f == nil {
		t.Fatal("no fight created for the coach")
	}
	defer stopTestFight(d, f)

	if !f.Practice {
		t.Error("Tester fight must be flagged Practice (unranked)")
	}
	// Team A (side 0) is the real coach with both roster fighters.
	teamA := f.Teams[0]
	if teamA == nil || teamA.Coach == nil || teamA.Coach.ID != coach.ID {
		t.Fatalf("team A is not the caller: %+v", teamA)
	}
	if len(teamA.Fighters) != 2 {
		t.Errorf("team A fighters = %d, want 2 (roster)", len(teamA.Fighters))
	}
	// Team B (side 1) is the session-less sparring opponent.
	teamB := f.Teams[1]
	if teamB == nil || teamB.Session != nil {
		t.Fatalf("team B should be the session-less sparring team: %+v", teamB)
	}
	if teamB.Coach == nil || teamB.Coach.ID != sparringCoachID {
		t.Errorf("team B coach = %+v, want sparring id %d", teamB.Coach, sparringCoachID)
	}
	if len(teamB.Fighters) != 1 {
		t.Errorf("sparring fighters = %d, want 1", len(teamB.Fighters))
	}
}

// TestTeamTestIgnoresForeignTeam: a team id the caller does not own must not leak
// that team's fighters; the fight falls back to the caller's own roster.
func TestTeamTestIgnoresForeignTeam(t *testing.T) {
	d, st := fightCreationDeps(t)
	owner, foreignTeam := coachWithTeam(t, st, "owner")
	_ = owner
	attacker, _ := coachWithTeam(t, st, "attacker")
	s := fcSession(d, attacker)

	payload := protocol.NewWriter().I32(12).U16(uint16(foreignTeam)).Bytes()
	if err := handleTeamTest(s, &protocol.C2SFrame{Payload: payload}); err != nil {
		t.Fatalf("handleTeamTest: %v", err)
	}
	f := d.Fights.ByCoach(attacker.ID)
	if f == nil {
		t.Fatal("no fight created")
	}
	defer stopTestFight(d, f)

	// Every fighter on the attacker's side must belong to the attacker.
	for _, ff := range f.Teams[0].Fighters {
		if ff.CoachID != attacker.ID {
			t.Fatalf("IDOR: foreign fighter %d (coach %d) on attacker's team", ff.WireID, ff.CoachID)
		}
	}
}

// TestClassicReadyPairsTwoCoaches: two coaches pressing "Combattre" (23103) are
// paired into a single (ranked) fight; the first waits, the second triggers it,
// and the matchmaker's pending state is cleared afterwards.
func TestClassicReadyPairsTwoCoaches(t *testing.T) {
	d, st := fightCreationDeps(t)
	coachA, teamA := coachWithTeam(t, st, "alpha")
	coachB, teamB := coachWithTeam(t, st, "bravo")
	sA := fcSession(d, coachA)
	sB := fcSession(d, coachB)

	ready := func(s *Session, coach *domain.Coach, teamID uint) {
		t.Helper()
		p := protocol.NewWriter().I64(int64(coach.ID)).U16(uint16(teamID)).Bytes()
		if err := handleClassicReadyForFight(s, &protocol.C2SFrame{Payload: p}); err != nil {
			t.Fatalf("combattre %s: %v", coach.Name, err)
		}
	}

	// First coach: queued, no fight yet.
	ready(sA, coachA, teamA)
	if f := d.Fights.ByCoach(coachA.ID); f != nil {
		t.Fatal("first Combattre should wait, not start a fight")
	}

	// Second coach: pairs and launches one shared fight for both.
	ready(sB, coachB, teamB)
	fA := d.Fights.ByCoach(coachA.ID)
	fB := d.Fights.ByCoach(coachB.ID)
	if fA == nil || fB == nil {
		t.Fatalf("both coaches should be in a fight (A=%v B=%v)", fA, fB)
	}
	if fA != fB {
		t.Fatal("the two coaches must share the same fight")
	}
	defer stopTestFight(d, fA)

	if fA.Practice {
		t.Error("Combattre fight must be ranked, not Practice")
	}
	// Ready-room bypasses the accept handshake: no pending match must linger.
	if pm := d.Matchmaker.Pending(coachA.ID); pm != nil {
		t.Error("pending match not discarded after pairing")
	}
}
