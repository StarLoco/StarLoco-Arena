package game

import (
	"math/rand"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// eventTestFight builds a two-fighter fight whose Deps carry a synthetic event
// table, with a seeded RNG so deck draws are reproducible.
func eventTestFight(defs ...*gamedata.Event) (*Fight, *FightFighter, *FightFighter) {
	a := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	b := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 9, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	f := &Fight{
		Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{a}},
			{ID: 1, Fighters: []*FightFighter{b}},
		},
		deps: &Deps{Events: gamedata.NewEvents(defs...)},
		rng:  rand.New(rand.NewSource(1)),
	}
	f.setPhase(PhaseAction)
	return f, a, b
}

// deckCards builds n trivial event cards with ids 1..n.
func deckCards(n int32) []*gamedata.Event {
	out := make([]*gamedata.Event, 0, n)
	for id := int32(1); id <= n; id++ {
		out = append(out, &gamedata.Event{ID: id})
	}
	return out
}

// TestRoundEventInertWithoutData: no event table -> eventId 0 and no effects,
// i.e. exactly the behaviour before the mechanic existed.
func TestRoundEventInertWithoutData(t *testing.T) {
	f, _, _ := eventTestFight()
	if got := f.drawRoundEvent(); got != noRoundEvent {
		t.Errorf("drawRoundEvent with no data = %d, want 0", got)
	}
	f.applyRoundEvent(0) // must not panic
	f.applyRoundEvent(999)
}

// TestFirstRoundIsAlwaysTheOpeningCard: the opening round always draws card 14,
// whatever the shuffle produced, for every seed.
func TestFirstRoundIsAlwaysTheOpeningCard(t *testing.T) {
	for seed := int64(0); seed < 50; seed++ {
		f, _, _ := eventTestFight(deckCards(roundEventDeckMaxID)...)
		f.rng = rand.New(rand.NewSource(seed))
		if got := f.drawRoundEvent(); got != firstRoundEventID {
			t.Fatalf("seed %d: first draw = %d, want %d", seed, got, firstRoundEventID)
		}
	}
}

// TestOpeningCardNotDealtTwiceInACycle: forcing card 14 to the top must not put
// it back in the pile — it should not reappear until the deck is reshuffled.
func TestOpeningCardNotDealtTwiceInACycle(t *testing.T) {
	f, _, _ := eventTestFight(deckCards(roundEventDeckMaxID)...)
	seen := map[int32]int{}
	for i := int32(0); i < roundEventDeckMaxID; i++ {
		seen[f.drawRoundEvent()]++
	}
	if seen[firstRoundEventID] != 1 {
		t.Errorf("opening card dealt %d times in one cycle, want 1", seen[firstRoundEventID])
	}
	if len(seen) != int(roundEventDeckMaxID) {
		t.Errorf("cycle dealt %d distinct cards, want %d (every card once)",
			len(seen), roundEventDeckMaxID)
	}
}

// TestDeckReshufflesAndRepeats: past one full cycle the deck reshuffles, so
// every card is seen exactly twice over two cycles.
func TestDeckReshufflesAndRepeats(t *testing.T) {
	f, _, _ := eventTestFight(deckCards(roundEventDeckMaxID)...)
	seen := map[int32]int{}
	for i := int32(0); i < roundEventDeckMaxID*2; i++ {
		seen[f.drawRoundEvent()]++
	}
	for id := int32(1); id <= roundEventDeckMaxID; id++ {
		if seen[id] != 2 {
			t.Errorf("card %d dealt %d times over two cycles, want 2", id, seen[id])
		}
	}
}

// TestDeckExcludesNonBaseCards: the PvE/creature/demon cards (43+) are loaded
// but must never be drawn as a round card.
func TestDeckExcludesNonBaseCards(t *testing.T) {
	defs := append(deckCards(roundEventDeckMaxID),
		&gamedata.Event{ID: 45}, &gamedata.Event{ID: 55}, &gamedata.Event{ID: 63})
	f, _, _ := eventTestFight(defs...)
	for i := 0; i < 200; i++ {
		if id := f.drawRoundEvent(); id > roundEventDeckMaxID {
			t.Fatalf("drew non-base card %d", id)
		}
	}
}

// TestDeckDeterministicUnderSeed: two fights with the same seed deal the same
// cards, so a fight can be replayed.
func TestDeckDeterministicUnderSeed(t *testing.T) {
	draw := func() []int32 {
		f, _, _ := eventTestFight(deckCards(roundEventDeckMaxID)...)
		f.rng = rand.New(rand.NewSource(42))
		out := make([]int32, 0, 10)
		for i := 0; i < 10; i++ {
			out = append(out, f.drawRoundEvent())
		}
		return out
	}
	a, b := draw(), draw()
	for i := range a {
		if a[i] != b[i] {
			t.Fatalf("seeded draws diverged at %d: %v vs %v", i, a, b)
		}
	}
}

// TestRoundEventAffectsEveryFighter: a card's effect lands on BOTH teams — that
// is the whole point of a round card.
func TestRoundEventAffectsEveryFighter(t *testing.T) {
	// Action 13 = AP boost, one table turn, no target restriction.
	card := &gamedata.Event{ID: 12, Effects: []gamedata.Effect{
		{ActionID: 13, AreaShape: 32767, Params: []float32{2}, Duration: []int32{1, 0}},
	}}
	f, a, b := eventTestFight(card)
	f.applyRoundEvent(12)
	if a.MaxAP != 8 || b.MaxAP != 8 {
		t.Errorf("round card did not buff both teams: maxAP %d / %d, want 8 / 8", a.MaxAP, b.MaxAP)
	}
}

// TestRoundEventHonoursTargetConditions: a breed-god card buffs only its breed.
// This is what keeps "Dieu Iop" from buffing the whole arena.
func TestRoundEventHonoursTargetConditions(t *testing.T) {
	const iopBreedBit int64 = 1 << (8 - 1) << 16
	card := &gamedata.Event{ID: 1, Effects: []gamedata.Effect{
		{ActionID: 13, AreaShape: 32767, Params: []float32{2},
			Duration: []int32{1, 0}, Targets: []int64{iopBreedBit}},
	}}
	f, a, b := eventTestFight(card)
	a.Fighter = &domain.Fighter{Name: "Iop", BreedID: 8}
	b.Fighter = &domain.Fighter{Name: "Feca", BreedID: 1}
	f.applyRoundEvent(1)
	if a.MaxAP != 8 {
		t.Errorf("the Iop was not buffed by the Iop card: maxAP %d, want 8", a.MaxAP)
	}
	if b.MaxAP != 6 {
		t.Errorf("the Feca was buffed by the Iop card: maxAP %d, want 6", b.MaxAP)
	}
}

// TestRoundEventNegativeBreedCondition reproduces the real "Dieu Enutrof" card
// (event 8): one effect buffs Enutrofs (breed bit 18 = "is breed 3"), the other
// buffs everyone ELSE (bit 34 = "is NOT breed 3"). Without the negative breed
// bank the second effect would land on the Enutrof too, double-buffing it.
func TestRoundEventNegativeBreedCondition(t *testing.T) {
	const isEnutrof int64 = 1 << (3 - 1) << 16 // 262144
	const isNotEnutrof int64 = 1 << (3 - 1) << 32
	card := &gamedata.Event{ID: 8, Effects: []gamedata.Effect{
		{ActionID: 13, AreaShape: 32767, Params: []float32{2},
			Duration: []int32{1, 0}, Targets: []int64{isEnutrof}},
		{ActionID: 13, AreaShape: 32767, Params: []float32{1},
			Duration: []int32{1, 0}, Targets: []int64{isNotEnutrof}},
	}}
	f, enutrof, other := eventTestFight(card)
	enutrof.Fighter = &domain.Fighter{Name: "Enutrof", BreedID: 3}
	other.Fighter = &domain.Fighter{Name: "Iop", BreedID: 8}
	f.applyRoundEvent(8)

	if enutrof.MaxAP != 8 {
		t.Errorf("Enutrof maxAP = %d, want 8 (+2 only, not both effects)", enutrof.MaxAP)
	}
	if other.MaxAP != 7 {
		t.Errorf("non-Enutrof maxAP = %d, want 7 (+1 only)", other.MaxAP)
	}
}

// TestOpeningCardDoesNotFreezeTheArena is the regression guard for B-053: the
// round-1 card (94 + 127 + 128) must leave everyone able to WALK — it only
// blocks being displaced by someone else.
func TestOpeningCardDoesNotFreezeTheArena(t *testing.T) {
	card := &gamedata.Event{ID: firstRoundEventID, Effects: []gamedata.Effect{
		{ActionID: 94, AreaShape: 32767, Duration: []int32{1, 0}},
		{ActionID: 127, AreaShape: 32767, Duration: []int32{1, 0}},
		{ActionID: 128, AreaShape: 32767, Duration: []int32{1, 0}},
	}}
	f, a, b := eventTestFight(card)
	f.applyRoundEvent(firstRoundEventID)

	for _, ff := range []*FightFighter{a, b} {
		if ff.MP != 3 {
			t.Errorf("opening card zeroed MP (%d): it must not root anyone", ff.MP)
		}
		if ff.hasState(stateRooted) {
			t.Error("opening card rooted a fighter")
		}
		if !ff.hasState(stateStabilized) || !ff.hasState(stateAnchored) || !ff.hasState(stateIntransposable) {
			t.Errorf("opening card did not grant all three displacement immunities: %s", ff.stateSummary())
		}
	}
	if !f.validateFightMove(a, []Pos{{X: 8, Y: 15}}) {
		t.Error("a fighter could not walk on the opening round")
	}
}
