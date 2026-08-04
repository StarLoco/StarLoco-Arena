package game

import (
	"path/filepath"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// evoDeathHarness builds a real store with a coach and two persisted titular
// fighters, plus a Deps ready to run checkFightEnd.
func evoDeathHarness(t *testing.T) (*Deps, uint, *domain.Fighter, *domain.Fighter, *store.Store) {
	t.Helper()
	st, err := store.Open(filepath.Join(t.TempDir(), "evodeath.db"))
	if err != nil {
		t.Fatalf("open store: %v", err)
	}
	t.Cleanup(func() { _ = st.Close() })

	acc, _ := st.Accounts.CreateAccount("evoA", "pw", false)
	coach, _ := st.Coaches.Create(acc.ID, "Evo", 0, 0, 0)
	downed := &domain.Fighter{CoachID: coach.ID, BreedID: 1, Name: "Downed", State: domain.FighterStateTitular, Budget: 400}
	survivor := &domain.Fighter{CoachID: coach.ID, BreedID: 1, Name: "Survivor", State: domain.FighterStateTitular, Budget: 400}
	for _, fr := range []*domain.Fighter{downed, survivor} {
		if err := st.Fighters.Create(fr); err != nil {
			t.Fatalf("create fighter: %v", err)
		}
	}
	d := &Deps{Store: st, Fights: NewFightManager(), World: NewRegistry(150), Log: testLogger()}
	return d, coach.ID, downed, survivor, st
}

// buildEvoFight assembles a finished fight: the real coach's team (one fighter at
// 0 HP = downed, one alive) beats a synthetic sparring team. evolution toggles
// the lethal mode.
func buildEvoFight(d *Deps, coachID uint, downed, survivor *domain.Fighter, evolution bool) *Fight {
	teamA := &FightTeam{
		ID: 0, Coach: &domain.Coach{ID: coachID, Name: "Evo"},
		Fighters: []*FightFighter{
			{WireID: FighterWireIDBase + int64(downed.ID)*16, CoachID: coachID, TeamID: 0, Fighter: downed, MaxHP: 100, HP: 0},
			{WireID: FighterWireIDBase + int64(survivor.ID)*16 + 1, CoachID: coachID, TeamID: 0, Fighter: survivor, MaxHP: 100, HP: 80},
		},
	}
	teamB := buildSparringTeam(1, Pos{})
	for _, ff := range teamB.Fighters {
		ff.HP = 0 // synthetic opponent wiped -> team A wins
	}
	f := &Fight{
		Evolution: evolution, Practice: true,
		Teams:        [2]*FightTeam{teamA, teamB},
		deps:         d,
		readyPresent: map[uint]bool{}, readyObserve: map[uint]bool{}, readyAction: map[uint]bool{},
	}
	f.setPhase(PhaseAction)
	d.Fights.Create(f)
	return f
}

// TestEvolutionFightPersistsDeaths: in an evolution fight, a fighter that fell to
// 0 HP is persisted as dead(2) — even on the WINNING side — while a survivor is
// untouched. This is what fills the graveyard from real play.
func TestEvolutionFightPersistsDeaths(t *testing.T) {
	d, coachID, downed, survivor, st := evoDeathHarness(t)
	f := buildEvoFight(d, coachID, downed, survivor, true)

	d.checkFightEnd(f)

	if got := fighterStateOf(t, st, downed.ID); got != domain.FighterStateDead {
		t.Errorf("downed fighter state = %d, want dead(2)", got)
	}
	if got := fighterStateOf(t, st, survivor.ID); got != domain.FighterStateTitular {
		t.Errorf("survivor state = %d, want titular(0) (only downed fighters die)", got)
	}
}

// TestNonEvolutionFightKeepsFightersAlive: an ordinary (non-evolution) fight must
// NOT persist deaths, however badly a fighter was beaten — the /FSTATE-only
// behaviour for every ranked/practice/PvE fight.
func TestNonEvolutionFightKeepsFightersAlive(t *testing.T) {
	d, coachID, downed, survivor, st := evoDeathHarness(t)
	f := buildEvoFight(d, coachID, downed, survivor, false) // evolution OFF

	d.checkFightEnd(f)

	if got := fighterStateOf(t, st, downed.ID); got != domain.FighterStateTitular {
		t.Errorf("downed fighter state = %d in a NON-evolution fight, want titular(0)", got)
	}
}

// TestEvolutionDeathSkipsSyntheticFighters: the synthetic opponent (sparring
// dummy / challenge demon) has no DB row, so SetState must never be attempted for
// it (a 0-id write would corrupt the table). Guard: the losing synthetic team is
// all at 0 HP, yet no persistence error occurs and the real roster is the only
// thing touched.
func TestEvolutionDeathSkipsSyntheticFighters(t *testing.T) {
	d, coachID, downed, survivor, st := evoDeathHarness(t)
	f := buildEvoFight(d, coachID, downed, survivor, true)

	// Sanity: the synthetic team's fighters have no real DB id.
	for _, ff := range f.Teams[1].Fighters {
		if ff.Fighter != nil && ff.Fighter.ID != 0 {
			t.Fatalf("sparring fighter unexpectedly has a DB id %d", ff.Fighter.ID)
		}
	}
	d.checkFightEnd(f)

	// No fighter with id 0 should have been written; the real downed one is dead.
	if got := fighterStateOf(t, st, downed.ID); got != domain.FighterStateDead {
		t.Errorf("downed fighter state = %d, want dead(2)", got)
	}
	// And the coach still owns exactly its two fighters (nothing phantom created).
	fighters, _ := st.Fighters.ListByCoach(coachID)
	if len(fighters) != 2 {
		t.Errorf("coach fighter count = %d, want 2 (no phantom rows)", len(fighters))
	}
}
