package combat

import (
	"math/rand"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
	"github.com/dofusarena/go-server/internal/protocol"
	"github.com/rs/zerolog"
)

// threeFighterFightWithSummon builds a fight where team A has two coach
// fighters (a1 the summoner + a2 a surviving teammate) and team B has one
// (b). A summon `s` (child of a1) is registered and inserted into the
// timeline. Having a2 alive means killing a1 does NOT end the fight, so the
// test can observe the summon-death cascade in isolation.
func threeFighterFightWithSummon(t *testing.T) (f *Fight, bc *fakeBroadcaster, a1, a2, b, s *Fighter) {
	t.Helper()
	a1 = NewFighterFromBreed(1, 1, BreedIop, "A1", 0, 0)
	a1.CoachID = 100
	a2 = NewFighterFromBreed(2, 1, BreedIop, "A2", 0, 0)
	a2.CoachID = 100
	b = NewFighterFromBreed(3, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200

	teamA := &Team{ID: 1, Name: "team1", Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a1, a2}}}}
	teamB := &Team{ID: 2, Name: "team2", Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	bc = newFakeBroadcaster()
	f = NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, bc, nil, zerolog.Nop())

	// Create a summon of a1 on team A and wire it into the fight.
	s = NewFighterFromBreed(f.nextSummonID(), 1, BreedIop, "Summon", 0, 0)
	s.Father = a1
	f.registerFighter(s, 100)
	f.Timeline.InsertAfter(a1, s)
	return f, bc, a1, a2, b, s
}

// --- #2: summons disappear immediately when their creator is killed ---

func TestKillFighter_KillsSummonsOfDeadCreator(t *testing.T) {
	f, _, a1, a2, b, s := threeFighterFightWithSummon(t)

	if s.IsDead {
		t.Fatal("setup invalid: summon already dead")
	}

	f.killFighter(a1, -1)

	if !a1.IsDead {
		t.Fatal("summoner should be dead after killFighter")
	}
	if !s.IsDead {
		t.Error("a summon must die when its creator is killed (wiki: summons disappear immediately)")
	}
	// The fight continues (a2 still alive on team A, b on team B).
	if f.CurrentPhase() == PhaseEnded {
		t.Error("fight should not end: team A still has a living fighter (a2)")
	}
	if a2.IsDead || b.IsDead {
		t.Error("only the summoner and its summon should have died")
	}
	// The dead summon must be gone from the turn order.
	for _, fr := range f.Timeline.Order() {
		if fr == s {
			t.Error("dead summon should have been removed from the timeline")
		}
	}
}

func TestKillFighter_CascadesThroughSummonOfSummon(t *testing.T) {
	f, _, a1, _, _, s := threeFighterFightWithSummon(t)

	// A summon-of-a-summon: grandchild whose Father is the summon s.
	gc := NewFighterFromBreed(f.nextSummonID(), 1, BreedIop, "GrandSummon", 0, 0)
	gc.Father = s
	f.registerFighter(gc, 100)
	f.Timeline.InsertAfter(s, gc)

	f.killFighter(a1, -1)

	if !s.IsDead || !gc.IsDead {
		t.Errorf("both the summon and its summon must die with the creator: s.dead=%v gc.dead=%v", s.IsDead, gc.IsDead)
	}
}

func TestKillFighter_LeavesUnrelatedSummonsAlone(t *testing.T) {
	f, _, a1, a2, _, s := threeFighterFightWithSummon(t)

	// A second summon owned by a2 (the survivor) must NOT die when a1 dies.
	other := NewFighterFromBreed(f.nextSummonID(), 1, BreedIop, "OtherSummon", 0, 0)
	other.Father = a2
	f.registerFighter(other, 100)
	f.Timeline.InsertAfter(a2, other)

	f.killFighter(a1, -1)

	if !s.IsDead {
		t.Error("a1's own summon should have died")
	}
	if other.IsDead {
		t.Error("a2's summon must survive: only the DEAD creator's summons disappear")
	}
}

// --- #4b: cards do not work on summons (fighter-card 8107 targeting) ---

// cardStore builds a *gamedata.Store whose FighterCards repo serves one card
// (id) with a single HP-loss use-effect, enough to exercise handleCardUse.
func cardStore(cardID int32) *gamedata.Store {
	return &gamedata.Store{
		FighterCards: gamedata.NewRepository(func() (map[int32]gamedata.FighterCardTemplate, error) {
			return map[int32]gamedata.FighterCardTemplate{
				cardID: {
					ID: cardID,
					// ActionID 3 = HP-loss (Earth), AreaPoint so it resolves the
					// single fighter standing on the target cell -- so the
					// control test can observe a real HP change.
					Effects: []gamedata.EffectDef{{ID: 1, ActionID: 3, Params: []float32{10}, AreaShape: int16(AreaPoint)}},
				},
			}, nil
		}),
	}
}

// enemySummonOf makes and wires a summon owned by `owner` (on owner's team),
// used so the card's enemy-targeting HP-loss WOULD hit it absent the guard.
func enemySummonOf(f *Fight, owner *Fighter) *Fighter {
	s := NewFighterFromBreed(f.nextSummonID(), owner.TeamID, BreedFeca, "EnemySummon", 0, 0)
	s.Father = owner
	f.registerFighter(s, owner.CoachID)
	return s
}

// makeCurrent rebuilds the timeline over `all` and advances to `who`'s turn.
func makeCurrent(f *Fight, who *Fighter, all ...*Fighter) {
	f.Timeline = NewTimeline(all)
	f.Timeline.StartNextTurn()
	for f.Timeline.CurrentFighter() != who {
		f.Timeline.StartNextTurn()
	}
}

func TestHandleCardUse_RejectedWhenTargetIsSummon(t *testing.T) {
	f, bc, a1, _, b, _ := threeFighterFightWithSummon(t)
	f.data = cardStore(700)
	f.rng = rand.New(rand.NewSource(1)) // deterministic: no fumble/crit
	f.setPhase(PhaseAction)

	// An ENEMY summon (child of the opponent b): the card's enemy-targeting
	// HP-loss WOULD hit it if the guard weren't there, so this test truly
	// exercises the summon-target rejection (not merely a same-team no-op).
	es := enemySummonOf(f, b)
	makeCurrent(f, a1, a1, b, es)
	a1.ObjectIDs = append(a1.ObjectIDs, 700)

	// Place the enemy summon adjacent so range/LOS wouldn't be the reason
	// for rejection -- the summon-target guard must be.
	a1.Position = Point3{X: 0, Y: 0}
	es.Position = Point3{X: 1, Y: 0}
	es.Characteristics[HP].Value, es.Characteristics[HP].Max = 50, 50

	f.handleCardUse(cmdCardUse{RequesterCoachID: 100, FighterID: a1.ID, CardID: 700, Target: es.Position})

	// The guard returns BEFORE broadcasting FIGHTER_CARD_USE, so no card-use
	// frame is sent when the target is a summon.
	if _, ok := bc.lastFrame(100, protocol.SendFighterCardUse); ok {
		t.Error("card aimed at a summon must be rejected (no FIGHTER_CARD_USE broadcast)")
	}
	if got := es.Characteristic(HP); got != 50 {
		t.Errorf("card must not affect a summon target, summon HP=%d want unchanged 50", got)
	}
}

// TestCardSummonGuard_OnlyTripsForSummons verifies the summon-target guard
// used by handleCardUse (fighterAt(target).Father != nil) trips for a summon
// but NOT for a real fighter or an empty cell -- so a card is never
// over-blocked. Asserting the guard condition directly avoids coupling to
// card range/AP/element validation (which is exercised elsewhere).
func TestCardSummonGuard_OnlyTripsForSummons(t *testing.T) {
	f, _, a1, _, b, s := threeFighterFightWithSummon(t)
	a1.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	s.Position = Point3{X: 2, Y: 0}

	guardTrips := func(target Point3) bool {
		tf := f.fighterAt(target, nil)
		return tf != nil && tf.Father != nil
	}

	if !guardTrips(s.Position) {
		t.Error("guard must trip on a summon's cell (card must not affect summons)")
	}
	if guardTrips(b.Position) {
		t.Error("guard must NOT trip on a real fighter's cell (card must still work on players)")
	}
	if guardTrips(Point3{X: 9, Y: 9}) {
		t.Error("guard must NOT trip on an empty cell")
	}
}
