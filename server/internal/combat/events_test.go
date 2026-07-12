package combat

import (
	"math/rand"
	"testing"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// Tests for the per-round EVENT CARD system (events.go): deck draw
// determinism, no-repeat-until-exhausted cycling, effect application to all
// fighters, and the inert (no-data) fallback.

func eventStore(events map[int32]gamedata.EventTemplate) *gamedata.Store {
	return &gamedata.Store{
		Events: gamedata.NewRepository(func() (map[int32]gamedata.EventTemplate, error) {
			return events, nil
		}),
	}
}

// drawEvent is a test helper mirroring the old combined select+apply so the
// draw-order/inert tests read clearly.
func (f *Fight) drawEvent(triggeringActionID int32) int32 {
	id := f.selectEventID()
	f.applyDrawnEvent(id, triggeringActionID)
	return id
}

func TestDrawEvent_InertWithoutData(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	f.data = nil
	if id := f.drawEvent(-1); id != 0 {
		t.Errorf("drawEvent with no store = %d, want 0 (inert)", id)
	}
	f.data = eventStore(map[int32]gamedata.EventTemplate{}) // empty repo
	if id := f.drawEvent(-1); id != 0 {
		t.Errorf("drawEvent with empty event data = %d, want 0 (inert)", id)
	}
}

func TestDrawEvent_DealsEveryEventOnceBeforeRepeat(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	f.data = eventStore(map[int32]gamedata.EventTemplate{
		10: {ID: 10}, 20: {ID: 20}, 30: {ID: 30},
	})

	// Draw 3 -> should be a permutation of {10,20,30}, each once.
	seen := map[int32]int{}
	for i := 0; i < 3; i++ {
		seen[f.drawEvent(-1)]++
	}
	for _, id := range []int32{10, 20, 30} {
		if seen[id] != 1 {
			t.Errorf("event %d drawn %d times in first cycle, want exactly 1 (deck=%v)", id, seen[id], seen)
		}
	}

	// The 4th draw starts a fresh reshuffled cycle -> a valid event again.
	fourth := f.drawEvent(-1)
	if fourth != 10 && fourth != 20 && fourth != 30 {
		t.Errorf("4th draw = %d, want one of {10,20,30} (deck reshuffled)", fourth)
	}
}

func TestDrawEvent_DeterministicUnderSeed(t *testing.T) {
	draw := func(seed int64) []int32 {
		f, _, _ := newTestFightForEffects(t)
		f.rng = rand.New(rand.NewSource(seed))
		f.data = eventStore(map[int32]gamedata.EventTemplate{
			1: {ID: 1}, 2: {ID: 2}, 3: {ID: 3}, 4: {ID: 4}, 5: {ID: 5},
		})
		var got []int32
		for i := 0; i < 5; i++ {
			got = append(got, f.drawEvent(-1))
		}
		return got
	}
	a := draw(42)
	b := draw(42)
	for i := range a {
		if a[i] != b[i] {
			t.Fatalf("same seed produced different draw order: %v vs %v", a, b)
		}
	}
}

func TestApplyEventEffects_HitsAllLivingFighters(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	a.Characteristics[HP].Value = 100
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// An event that heals everyone +10 (actionID 69 = HP_GAIN). First injure
	// both so the heal is visible.
	a.Characteristics[HP].Value = 50
	b.Characteristics[HP].Value = 50
	event := gamedata.EventTemplate{
		ID: 27,
		Effects: []gamedata.EffectDef{
			{ID: 1, ActionID: 69, Params: []float32{10}, AreaShape: int16(AreaEmpty)},
		},
	}
	f.applyEventEffects(event, -1)

	if got := a.Characteristic(HP); got != 60 {
		t.Errorf("fighter A HP after event heal(10) = %d, want 60", got)
	}
	if got := b.Characteristic(HP); got != 60 {
		t.Errorf("fighter B HP after event heal(10) = %d, want 60", got)
	}
}

func TestApplyEventEffects_DamageEventHurtsEveryone(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	a.Characteristics[HP].Value = 100
	a.Characteristics[HP].Max = 100
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	// actionID 4 = HP loss (elemental). 10 damage to everyone; nil caster
	// (an event has no attacker) must not panic (AreOpponents nil-guard).
	event := gamedata.EventTemplate{
		ID: 5,
		Effects: []gamedata.EffectDef{
			{ID: 1, ActionID: 4, Params: []float32{10}, AreaShape: int16(AreaEmpty)},
		},
	}
	f.applyEventEffects(event, -1)

	if a.Characteristic(HP) >= 100 || b.Characteristic(HP) >= 100 {
		t.Errorf("event damage did not hit both fighters: A=%d B=%d", a.Characteristic(HP), b.Characteristic(HP))
	}
}

// TestApplyEventEffects_SkipsSummons verifies an event/bonus card does NOT
// affect a summon (Father != nil) -- only real coach fighters (wiki: "Cards
// do not work on summons").
func TestApplyEventEffects_SkipsSummons(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	a.Characteristics[HP].Value, a.Characteristics[HP].Max = 100, 100
	b.Characteristics[HP].Value, b.Characteristics[HP].Max = 100, 100
	// b is a summons of a.
	b.Father = a

	// actionID 4 = HP loss (elemental) 10 to everyone.
	event := gamedata.EventTemplate{
		ID: 5,
		Effects: []gamedata.EffectDef{
			{ID: 1, ActionID: 4, Params: []float32{10}, AreaShape: int16(AreaEmpty)},
		},
	}
	f.applyEventEffects(event, -1)

	if a.Characteristic(HP) >= 100 {
		t.Errorf("event should have hit the real fighter A, HP=%d", a.Characteristic(HP))
	}
	if b.Characteristic(HP) != 100 {
		t.Errorf("event must NOT affect the summon B, HP=%d want unchanged 100", b.Characteristic(HP))
	}
}

func TestBuildNewTableTurnBegin_CarriesEventID(t *testing.T) {
	frame := buildNewTableTurnBegin(1, 3, 27)
	// Payload: 8-byte header + byte numTurns + int32 eventId.
	p := frame.Payload
	if len(p) != 13 {
		t.Fatalf("payload len = %d, want 13", len(p))
	}
	if p[8] != 3 {
		t.Errorf("numTurns = %d, want 3", p[8])
	}
	eventID := int32(p[9])<<24 | int32(p[10])<<16 | int32(p[11])<<8 | int32(p[12])
	if eventID != 27 {
		t.Errorf("wire eventId = %d, want 27", eventID)
	}
}

// TestEvent_APBuffIsUsableThisRound is the regression for "the event bonus
// AP/MP shows at turn start but can't be used". A round-scoped (+1 AP)
// CharacBuff event must persist for the whole round it's drawn on -- the
// fighter's AP.Max must actually be raised (so the server lets them spend the
// extra AP), and must only revert on the NEXT table turn. The bug was that
// startNextTurn applied the event and then ticked durations on the SAME
// boundary, instantly reverting the fresh buff.
func TestEvent_APBuffIsUsableThisRound(t *testing.T) {
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100 // a goes first
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 10
	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}

	// A single event: +1 AP to everyone this round (action 13 = AP CharacBuff),
	// duration [1,0] (one table-turn).
	events := map[int32]gamedata.EventTemplate{
		12: {ID: 12, Effects: []gamedata.EffectDef{
			{ID: 1, ActionID: 13, Params: []float32{1}, Duration: []int32{1, 0}, AreaShape: int16(AreaEmpty)},
		}},
	}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, newFakeBroadcaster(), eventStore(events), zerolog.Nop())
	f.setPhase(PhaseAction)

	baseAP := a.Characteristics[AP].Max

	// First turn of the fight -> new table turn -> event drawn + applied.
	f.startNextTurn()
	defer f.cancelTurnClock()

	if got := a.Characteristics[AP].Max; got != baseAP+1 {
		t.Fatalf("AP.Max after event on turn 1 = %d, want %d (the +1 AP event buff must persist and be spendable this round)", got, baseAP+1)
	}
	if got := a.Characteristic(AP); got != baseAP+1 {
		t.Errorf("AP value after event on turn 1 = %d, want %d (turn-start refill must reach the buffed max)", got, baseAP+1)
	}

	// Advance to b's turn, then wrap back to a NEW table turn: last round's
	// event buff must now EXPIRE (AP.Max back to base), and this round draws
	// its own fresh +1 (only event in the deck), so AP.Max is base+1 again --
	// i.e. the buff lasts exactly one round, never stacks permanently.
	f.cancelTurnClock()
	f.askForFighterEndTurn(a.ID) // -> b's turn (same table turn)
	f.cancelTurnClock()
	f.askForFighterEndTurn(b.ID) // -> wrap: new table turn, a again
	f.cancelTurnClock()

	if got := a.Characteristics[AP].Max; got != baseAP+1 {
		t.Errorf("AP.Max on round 2 = %d, want %d (old buff expired, new event buff applied -- not stacked, not lost)", got, baseAP+1)
	}
}
