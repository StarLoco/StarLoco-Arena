package game

import "testing"

// setTurnToTeam points the timeline at the first fighter of the given side.
func setTurnToTeam(f *Fight, teamID uint8) {
	for i, ff := range f.Timeline {
		if ff.TeamID == teamID {
			f.turnIndex = i
			return
		}
	}
}

// TestDisconnectGracePassesTurnAndForfeits: a real coach dropping mid-fight does
// NOT end the fight immediately — its team is flagged absent + detached
// (reconnect-ready) and, if it's that coach's turn, it is passed at once — then
// the reconnect-grace/return forfeit hands the opponent a proper victory + stats.
func TestDisconnectGracePassesTurnAndForfeits(t *testing.T) {
	f := buildTestFight()
	f.setPhase(PhaseAction)
	setTurnToTeam(f, 0) // it is the leaver's (team A) turn
	d := &Deps{Fights: NewFightManager(), World: NewRegistry(150), Log: testLogger()}
	d.Fights.Create(f)
	coachA := f.Teams[0].Coach.ID

	d.coachLeftFightOnActor(f, coachA)

	if f.Phase() == PhaseEnded {
		t.Fatal("fight must stay alive during the reconnect grace, not end")
	}
	if !f.Teams[0].Absent || f.Teams[0].Session != nil {
		t.Fatalf("team A should be absent+detached: absent=%v session!=nil=%v",
			f.Teams[0].Absent, f.Teams[0].Session != nil)
	}
	if !f.teamAbsent(f.Teams[0].Fighters[0]) {
		t.Error("teamAbsent should report true for the absent team's fighter")
	}
	// The leaver's in-progress turn was passed immediately (no waiting out its clock).
	if cur := f.currentFighter(); cur == nil || cur.CoachID == coachA {
		t.Errorf("leaver's turn should have been passed to the opponent, current=%v", cur)
	}

	// Grace expiry (identical to the returning-coach path) forfeits A -> B wins.
	d.forfeitCoach(f, coachA)
	if f.Phase() != PhaseEnded {
		t.Fatal("forfeit should end the fight")
	}
	if f.Teams[1].Coach.StatWins != 1 {
		t.Errorf("opponent StatWins = %d, want 1", f.Teams[1].Coach.StatWins)
	}
	if f.Teams[0].Coach.StatLosses != 1 {
		t.Errorf("leaver StatLosses = %d, want 1", f.Teams[0].Coach.StatLosses)
	}
}

// TestDisconnectBothLeaveTearsDown: if BOTH coaches drop there is no one left to
// play or win, so the fight is torn down (no victory declared).
func TestDisconnectBothLeaveTearsDown(t *testing.T) {
	f := buildTestFight()
	f.setPhase(PhaseAction)
	setTurnToTeam(f, 1) // opponent's turn, so the first leave doesn't pass a turn
	d := &Deps{Fights: NewFightManager(), World: NewRegistry(150), Log: testLogger()}
	d.Fights.Create(f)

	d.coachLeftFightOnActor(f, f.Teams[0].Coach.ID)
	if f.Phase() == PhaseEnded {
		t.Fatal("a single leaver should not end the fight")
	}
	d.coachLeftFightOnActor(f, f.Teams[1].Coach.ID)
	if f.Phase() != PhaseEnded {
		t.Error("both leavers should tear the fight down")
	}
	f.stopGrace() // cancel the grace timer armed by the first leaver
}

// TestDisconnectPracticeTearsDown: a practice ("Tester") fight has no real
// opponent to award, so dropping out tears it down rather than forfeiting.
func TestDisconnectPracticeTearsDown(t *testing.T) {
	f := buildTestFight()
	f.Practice = true
	f.setPhase(PhaseAction)
	d := &Deps{Fights: NewFightManager(), World: NewRegistry(150), Log: testLogger()}
	d.Fights.Create(f)

	d.coachLeftFightOnActor(f, f.Teams[0].Coach.ID)
	if f.Phase() != PhaseEnded {
		t.Error("a practice fight should tear down on disconnect")
	}
}
