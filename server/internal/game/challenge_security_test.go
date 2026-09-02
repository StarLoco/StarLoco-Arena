package game

import "testing"

// TestChallengeSurvivesNilCoach is a SECURITY regression test.
//
// Attack: invite a victim to a duel (26301), then destroy your own coach (27529).
// handleDestroyCoach nils Session.Coach and leaves the challenge in place, and
// onClose's challenge cleanup is gated on a non-nil coach so disconnecting does
// not clear it either. The victim's accept/decline - or ANY third party's logout
// that touches the same challenge - then dereferenced a nil coach.
//
// With no recover() in the accept loop that panic ended the whole process.
func TestChallengeSurvivesNilCoach(t *testing.T) {
	steps := []struct {
		name string
		// preAccept makes the victim accept first, so subtests that only run
		// AFTER acceptance actually reach the code under test instead of
		// returning early. Without this the ConfirmTeam subtest passed
		// vacuously - a mutation that restored the unguarded deref survived it.
		preAccept bool
		run       func(m *ChallengeManager, victimID uint)
	}{
		{"Accept", false, func(m *ChallengeManager, id uint) { m.Accept(id) }},
		{"Remove", false, func(m *ChallengeManager, id uint) { m.Remove(id) }},
		{"Get", false, func(m *ChallengeManager, id uint) { m.Get(id) }},
		{"ConfirmTeam", true, func(m *ChallengeManager, id uint) { m.ConfirmTeam(id, 1) }},
		{"RemoveAfterAccept", true, func(m *ChallengeManager, id uint) { m.Remove(id) }},
		{"OtherAfterAccept", true, func(m *ChallengeManager, id uint) {
			if c := m.Get(id); c != nil {
				c.other(id)
			}
		}},
	}
	for _, st := range steps {
		t.Run(st.name, func(t *testing.T) {
			m := NewChallengeManager()
			attacker := sessionWithCoach(1, "Attacker")
			victim := sessionWithCoach(2, "Victim")
			if c := m.Create(attacker, victim, false); c == nil {
				t.Fatal("challenge should be created")
			}
			if st.preAccept {
				if c := m.Accept(2); c == nil {
					t.Fatal("fixture broken: the victim must be able to accept, " +
						"otherwise post-acceptance paths are never reached")
				}
			}
			attacker.Coach = nil // 27529
			st.run(m, 2)         // must not panic
		})
	}
}

// TestChallengeCreateRejectsCoachlessSession pins the entry guard: a session with
// no coach cannot open a challenge at all, so the manager never stores one.
func TestChallengeCreateRejectsCoachlessSession(t *testing.T) {
	m := NewChallengeManager()
	coachless := &Session{}
	if c := m.Create(coachless, sessionWithCoach(2, "V"), false); c != nil {
		t.Error("a coachless session must not be able to create a challenge")
	}
	if c := m.Create(sessionWithCoach(1, "A"), coachless, false); c != nil {
		t.Error("a coachless target must not be challengeable")
	}
}

// TestChallengeOtherSurvivesNilCoach covers challenge.other, reached from onClose.
func TestChallengeOtherSurvivesNilCoach(t *testing.T) {
	a := sessionWithCoach(1, "A")
	b := sessionWithCoach(2, "B")
	c := &challenge{challenger: a, target: b}
	a.Coach = nil
	_ = c.other(2)
	_ = c.other(1)
}

// TestChallengeSurvivesNilTargetCoach covers the mirror case: the INVITED side
// destroys its coach. Both sides can send 27529, so guarding only the challenger
// leaves the identical crash reachable from the other direction - a mutation
// restoring the unguarded c.target deref survived until this test existed.
func TestChallengeSurvivesNilTargetCoach(t *testing.T) {
	steps := []struct {
		name string
		run  func(m *ChallengeManager, challengerID uint)
	}{
		{"Accept", func(m *ChallengeManager, id uint) { m.Accept(2) }},
		{"Remove", func(m *ChallengeManager, id uint) { m.Remove(id) }},
		{"ConfirmTeam", func(m *ChallengeManager, id uint) { m.ConfirmTeam(id, 1) }},
		{"Other", func(m *ChallengeManager, id uint) {
			if c := m.Get(id); c != nil {
				c.other(id)
			}
		}},
	}
	for _, st := range steps {
		t.Run(st.name, func(t *testing.T) {
			m := NewChallengeManager()
			challenger := sessionWithCoach(1, "Challenger")
			target := sessionWithCoach(2, "Target")
			if c := m.Create(challenger, target, false); c == nil {
				t.Fatal("challenge should be created")
			}
			target.Coach = nil // the INVITED side sends 27529
			st.run(m, 1)       // must not panic
		})
	}
}
