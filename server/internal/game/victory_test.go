package game

import (
	"fmt"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestVictoryReachTurnBoundary pins the port of `ajm_0`:
//
//	return fight.ZB().JI() > this.JI[0];
//
// Strictly greater, on the table-turn counter. The boundary is the whole
// mechanic: with param 20 the fight must NOT end on round 20 (the twentieth
// round still has to be played) and MUST end on round 21.
func TestVictoryReachTurnBoundary(t *testing.T) {
	cond := gamedata.VictoryCondition{Type: gamedata.VictoryReachTurn, Params: []int32{20}}
	f := &Fight{}
	for _, tc := range []struct {
		round int32
		want  bool
	}{
		{1, false}, {19, false}, {20, false}, {21, true}, {22, true},
	} {
		f.tableTurn = tc.round
		if got := f.victoryConditionMet(cond); got != tc.want {
			t.Errorf("round %d: met = %v, want %v", tc.round, got, tc.want)
		}
	}
}

// TestVictoryUnknownSubtypesAreInert guards the deliberate decision to evaluate
// only subtype 4. The other three subclasses are real code in the client but no
// shipped record uses them, so they must never fire — an accidental "true" would
// end fights at random.
func TestVictoryUnknownSubtypesAreInert(t *testing.T) {
	f := &Fight{tableTurn: 99}
	for _, ty := range []int16{
		gamedata.VictoryHoldPosition,
		gamedata.VictoryPointsTotal,
		gamedata.VictoryKillBreed,
		gamedata.VictoryNoCondition,
		31337, // not in qk_1 at all
	} {
		cond := gamedata.VictoryCondition{Type: ty, Params: []int32{1, 1}}
		if f.victoryConditionMet(cond) {
			t.Errorf("subtype %d fired; unimplemented subtypes must be inert", ty)
		}
	}
}

// TestVictoryReachTurnNeedsAParam: a malformed condition must not fire. The
// client would throw ArrayIndexOutOfBounds here; we must simply not win.
func TestVictoryReachTurnNeedsAParam(t *testing.T) {
	f := &Fight{tableTurn: 500}
	cond := gamedata.VictoryCondition{Type: gamedata.VictoryReachTurn}
	if f.victoryConditionMet(cond) {
		t.Error("a parameterless reach-turn condition fired")
	}
}

// winnerStr renders decidedWinner as its VALUE - printing the *uint8 itself
// shows a pointer address, which is useless in a failure message.
func winnerStr(f *Fight) string {
	if f.decidedWinner == nil {
		return "none"
	}
	return fmt.Sprintf("team %d", *f.decidedWinner)
}

// victoryFight builds a minimal two-team fight in the action phase, with both
// sides alive, carrying one reach-turn condition for `winner`.
func victoryFight(t *testing.T, turns int32, winner uint8) (*Fight, *FightFighter, *FightFighter) {
	t.Helper()
	fa := &FightFighter{WireID: 1, TeamID: 0, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	fb := &FightFighter{WireID: 2, TeamID: 1, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{fa}},
		{ID: 1, Fighters: []*FightFighter{fb}},
	}}
	f.Timeline = []*FightFighter{fa, fb}
	f.deps = &Deps{Log: testLogger(), Fights: NewFightManager()}
	f.Rules = defaultFightRules()
	f.Rules.Victory = []gamedata.VictoryCondition{{
		Type: gamedata.VictoryReachTurn, Params: []int32{turns},
		IsNecessary: true, AffectedTeam: winner,
	}}
	f.phase.Store(int32(PhaseAction))
	return f, fa, fb
}

// TestVictoryConditionEndsFightWithBothTeamsAlive is the point of the whole
// mechanic: a fight can now end WITHOUT anyone being eliminated. Before this,
// checkFightEnd returned early whenever two teams still had living fighters, so
// a met condition could never have ended anything.
func TestVictoryConditionEndsFightWithBothTeamsAlive(t *testing.T) {
	f, fa, fb := victoryFight(t, 20, 0)

	f.tableTurn = 20
	f.deps.checkVictoryConditions(f)
	if f.Phase() == PhaseEnded {
		t.Fatal("fight ended on round 20; the condition is strictly-greater, so round 20 must still be played")
	}

	f.tableTurn = 21
	f.deps.checkVictoryConditions(f)
	if f.Phase() != PhaseEnded {
		t.Fatal("fight did not end on round 21 with the condition met")
	}
	if f.decidedWinner == nil || *f.decidedWinner != 0 {
		t.Errorf("decidedWinner = %s, want team 0", winnerStr(f))
	}
	// Nobody may be killed to make the result work: evolution deaths are driven
	// by HP, so downing the loser here would permanently kill its fighters.
	if fa.HP <= 0 || fb.HP <= 0 {
		t.Errorf("a victory-condition win killed fighters (A=%d B=%d); it must not", fa.HP, fb.HP)
	}
}

// TestVictoryConditionCanNameTheOtherTeam: the winner comes from the condition's
// affected_team, not from a hardcoded side. Every shipped condition says 0, so
// without this the field could be ignored entirely and nothing would notice.
func TestVictoryConditionCanNameTheOtherTeam(t *testing.T) {
	f, _, _ := victoryFight(t, 5, 1)
	f.tableTurn = 6
	f.deps.checkVictoryConditions(f)
	if f.decidedWinner == nil || *f.decidedWinner != 1 {
		t.Fatalf("decidedWinner = %s, want team 1", winnerStr(f))
	}
}

// TestVictoryConditionIgnoresUnknownTeam: a condition naming a side this fight
// does not have must be skipped, not crash and not hand the win to team 0.
func TestVictoryConditionIgnoresUnknownTeam(t *testing.T) {
	f, _, _ := victoryFight(t, 5, 7)
	f.tableTurn = 99
	f.deps.checkVictoryConditions(f)
	if f.decidedWinner != nil {
		t.Errorf("decidedWinner = %s, want none for a team that does not exist", winnerStr(f))
	}
	if f.Phase() == PhaseEnded {
		t.Error("fight ended on a condition naming a non-existent team")
	}
}

// TestNoVictoryConditionLeavesFightsAlone: the overwhelming majority of fights
// carry no condition at all and must be completely unaffected.
func TestNoVictoryConditionLeavesFightsAlone(t *testing.T) {
	f, _, _ := victoryFight(t, 5, 0)
	f.Rules.Victory = nil
	f.tableTurn = 500
	f.deps.checkVictoryConditions(f)
	if f.decidedWinner != nil || f.Phase() == PhaseEnded {
		t.Error("a fight with no victory condition was ended by the victory check")
	}
}

// TestShippedTimeChallengesResolveToSurvivalRules is the real-data canary: it
// asserts the nine shipped victory conditions all decode to the ONE shape this
// server implements. If a future field-order slip changes any of it, this fails
// loudly rather than silently disabling the mechanic.
func TestShippedTimeChallengesResolveToSurvivalRules(t *testing.T) {
	gd := openRealGameData(t)
	defs, err := gd.LoadChallenges()
	if err != nil {
		t.Fatalf("LoadChallenges: %v", err)
	}

	withVictory := 0
	for id, ch := range defs.All() {
		rules := rulesFromParameters(ch.Bonuses)
		if len(rules.Victory) == 0 {
			continue
		}
		withVictory += len(rules.Victory)
		for _, c := range rules.Victory {
			if c.Type != gamedata.VictoryReachTurn {
				t.Errorf("challenge %d: victory subtype %d; only 4 (reach turn) is shipped and implemented", id, c.Type)
				continue
			}
			if len(c.Params) != 1 || (c.Params[0] != 20 && c.Params[0] != 30) {
				t.Errorf("challenge %d: reach-turn params %v, want exactly one of 20 or 30", id, c.Params)
			}
			// All nine agree on these, which is why the server can ignore them.
			if !c.IsNecessary || c.VictoryPoints != 0 || c.AffectedTeam != 0 {
				t.Errorf("challenge %d: necessary=%v points=%d team=%d; every shipped condition is (true, 0, 0)",
					id, c.IsNecessary, c.VictoryPoints, c.AffectedTeam)
			}
		}
	}
	if withVictory != 9 {
		t.Errorf("%d victory conditions across the challenge table, want 9", withVictory)
	}
}

// TestShippedFightStartEffectsResolve is the same canary for np_1 type 12.
// Challenges 29/30/31 each grant +40 dodge for the whole fight to real fighters
// only (target mask 1024 = "breed is not 0").
func TestShippedFightStartEffectsResolve(t *testing.T) {
	gd := openRealGameData(t)
	defs, err := gd.LoadChallenges()
	if err != nil {
		t.Fatalf("LoadChallenges: %v", err)
	}

	got := map[int32]int{}
	for id, ch := range defs.All() {
		rules := rulesFromParameters(ch.Bonuses)
		for _, ef := range rules.StartEffects {
			got[id]++
			if ef.ActionID != 122 {
				t.Errorf("challenge %d: start effect action %d, want 122 (dodge gain)", id, ef.ActionID)
			}
			if len(ef.Params) != 1 || ef.Params[0] != 40 {
				t.Errorf("challenge %d: start effect params %v, want [40]", id, ef.Params)
			}
			if len(ef.Targets) != 1 || ef.Targets[0] != condBreedIsNotZero {
				t.Errorf("challenge %d: start effect targets %v, want [%d]", id, ef.Targets, condBreedIsNotZero)
			}
		}
	}
	for _, id := range []int32{29, 30, 31} {
		if got[id] != 1 {
			t.Errorf("challenge %d carries %d fight-start effects, want 1", id, got[id])
		}
	}
	if len(got) != 3 {
		t.Errorf("%d challenges carry fight-start effects, want 3", len(got))
	}
}
