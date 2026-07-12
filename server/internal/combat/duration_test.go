package combat

import (
	"math/rand"
	"testing"

	"github.com/rs/zerolog"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file implements docs/08-java-parity-roadmap.md §8.12 Phase J's
// required tests: a DoT tick applies damage on 2+ subsequent turns, and a
// timed buff reverts exactly at its documented expiry turn (not
// before/after).

func TestEffectCharacPoison_TicksAgainOnNextTableTurn(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	def := runningEffectDef{Kind: EffectCharacPoison, Elem: ElementPhysical}
	// Duration=[2 table-turns]: first tick fires immediately (below), then
	// should re-tick on the next table-turn boundary before expiring.
	eff := gamedata.EffectDef{Params: []float32{10}, Duration: []int32{2}}
	f.applyRunningEffect(a, b, def, eff, -1)

	if got := b.Characteristic(HP); got != 90 {
		t.Fatalf("HP after first (immediate) poison tick = %d, want 90", got)
	}
	if len(b.ActiveEffects) != 1 {
		t.Fatalf("ActiveEffects len = %d, want 1 (poison tracked for future ticks)", len(b.ActiveEffects))
	}

	// Simulate the next table-turn boundary.
	f.tickActiveEffects(-1)
	if got := b.Characteristic(HP); got != 80 {
		t.Errorf("HP after second poison tick (table-turn boundary) = %d, want 80", got)
	}

	// Third boundary: RemainingTableTurns was 2 at creation, decremented
	// to 1 after the first tickActiveEffects call, decremented to 0 on
	// this one -- so it should tick ONE more time then stop tracking.
	f.tickActiveEffects(-1)
	if got := b.Characteristic(HP); got != 70 {
		t.Errorf("HP after third poison tick = %d, want 70", got)
	}
	if len(b.ActiveEffects) != 0 {
		t.Errorf("ActiveEffects len after duration exhausted = %d, want 0 (poison stopped ticking)", len(b.ActiveEffects))
	}

	// A fourth boundary must NOT tick again (duration exhausted).
	f.tickActiveEffects(-1)
	if got := b.Characteristic(HP); got != 70 {
		t.Errorf("HP after a 4th boundary (duration already exhausted) = %d, want unchanged 70", got)
	}
}

func TestEffectCharacPoison_InfiniteDurationNeverStopsTicking(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	b.Characteristics[HP].Value = 1000
	b.Characteristics[HP].Max = 1000

	def := runningEffectDef{Kind: EffectCharacPoison, Elem: ElementPhysical}
	// Duration[0]=63 means infinite per TurnBasedTimeInterval.isInfinite().
	eff := gamedata.EffectDef{Params: []float32{5}, Duration: []int32{63}}
	f.applyRunningEffect(a, b, def, eff, -1)

	for i := 0; i < 10; i++ {
		f.tickActiveEffects(-1)
	}
	if len(b.ActiveEffects) != 1 {
		t.Errorf("ActiveEffects len after 10 boundaries with infinite duration = %d, want 1 (still tracked)", len(b.ActiveEffects))
	}
	// 1 immediate tick + 10 boundary ticks = 11 ticks of 5 damage = 55.
	if got := b.Characteristic(HP); got != 1000-55 {
		t.Errorf("HP after 11 total poison ticks = %d, want %d", got, 1000-55)
	}
}

func TestEffectCharacPoison_ZeroDurationDoesNotTrack(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 50

	def := runningEffectDef{Kind: EffectCharacPoison, Elem: ElementPhysical}
	// No Duration at all -- must behave exactly like the pre-Phase-J
	// one-shot-only case (no ActiveEffect tracked).
	eff := gamedata.EffectDef{Params: []float32{10}}
	f.applyRunningEffect(a, b, def, eff, -1)

	if got := b.Characteristic(HP); got != 40 {
		t.Fatalf("HP after one-shot poison tick = %d, want 40", got)
	}
	if len(b.ActiveEffects) != 0 {
		t.Errorf("ActiveEffects len with no Duration = %d, want 0 (must not track when no real duration given)", len(b.ActiveEffects))
	}
}

func TestEffectCharacBuff_RevertsExactlyAtExpiryTableTurn(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 6

	def := runningEffectDef{Kind: EffectCharacBuff, Charc: AP}
	eff := gamedata.EffectDef{Params: []float32{2}, Duration: []int32{2}}
	f.applyRunningEffect(a, a, def, eff, -1)

	if a.Characteristics[AP].Max != 8 {
		t.Fatalf("AP.Max right after buff = %d, want 8", a.Characteristics[AP].Max)
	}
	if len(a.ActiveEffects) != 1 {
		t.Fatalf("ActiveEffects len = %d, want 1", len(a.ActiveEffects))
	}

	// Boundary 1: RemainingTableTurns 2 -> 1, not yet expired.
	f.tickActiveEffects(-1)
	if a.Characteristics[AP].Max != 8 {
		t.Errorf("AP.Max after boundary 1 (not yet expired) = %d, want still 8", a.Characteristics[AP].Max)
	}
	if len(a.ActiveEffects) != 1 {
		t.Errorf("ActiveEffects len after boundary 1 = %d, want 1 (not yet expired)", len(a.ActiveEffects))
	}

	// Boundary 2: RemainingTableTurns 1 -> 0, reverts NOW, not before/after.
	f.tickActiveEffects(-1)
	if a.Characteristics[AP].Max != 6 {
		t.Errorf("AP.Max after boundary 2 (expiry) = %d, want reverted to 6", a.Characteristics[AP].Max)
	}
	if len(a.ActiveEffects) != 0 {
		t.Errorf("ActiveEffects len after expiry = %d, want 0", len(a.ActiveEffects))
	}
}

func TestEffectCharacBuff_RevertDoesNotDoubleChargeSpentResource(t *testing.T) {
	// Mirrors CharacBuff.unapply()'s documented contract (see
	// specialcells.go's grantTurnCellBuff comment): reverting a buff must
	// only lower Max, never separately subtract from Value -- a fighter
	// who has since spent the bonus resource shouldn't be double-charged.
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 6

	def := runningEffectDef{Kind: EffectCharacBuff, Charc: AP}
	eff := gamedata.EffectDef{Params: []float32{2}, Duration: []int32{1}}
	f.applyRunningEffect(a, a, def, eff, -1)
	// AP.Max=8, AP.Value=8.

	// Fighter spends all 8 AP down to 0 before the buff expires.
	a.AddCharacteristic(AP, -8)
	if a.Characteristic(AP) != 0 {
		t.Fatalf("AP.Value after spending = %d, want 0", a.Characteristic(AP))
	}

	f.tickActiveEffects(-1) // expires (RemainingTableTurns 1 -> 0)

	if a.Characteristics[AP].Max != 6 {
		t.Errorf("AP.Max after expiry = %d, want 6", a.Characteristics[AP].Max)
	}
	if got := a.Characteristic(AP); got != 0 {
		t.Errorf("AP.Value after expiry = %d, want still 0 (must not go negative from double-charging)", got)
	}
}

func TestEffectCharacDebuff_RevertsExactlyAtExpiryTableTurn(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	a.Characteristics[AP].Max = 6
	a.Characteristics[AP].Value = 6

	def := runningEffectDef{Kind: EffectCharacDebuff, Charc: AP}
	eff := gamedata.EffectDef{Params: []float32{2}, Duration: []int32{1}}
	f.applyRunningEffect(b, a, def, eff, -1)

	if a.Characteristics[AP].Max != 4 {
		t.Fatalf("AP.Max right after debuff = %d, want 4", a.Characteristics[AP].Max)
	}

	f.tickActiveEffects(-1) // expires (RemainingTableTurns 1 -> 0)
	if a.Characteristics[AP].Max != 6 {
		t.Errorf("AP.Max after debuff expiry = %d, want reverted to 6", a.Characteristics[AP].Max)
	}
}

// TestEffectCharacGain_TimedResistRevertsAtExpiry is the regression for
// Feca's Immunity/Truce and the timed resist armors: a CharacGain (current-
// value resist gain) with a finite Duration must REVERT at expiry, not last
// the whole fight. Immunity = +100 ResInPercent for 1 round.
func TestEffectCharacGain_TimedResistRevertsAtExpiry(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	def := runningEffectDef{Kind: EffectCharacGain, Charc: ResInPercent}
	eff := gamedata.EffectDef{Params: []float32{100}, Duration: []int32{1}} // +100 resist, 1 table-turn
	f.applyRunningEffect(a, a, def, eff, -1)

	if got := a.Characteristic(ResInPercent); got != 100 {
		t.Fatalf("ResInPercent right after Immunity = %d, want 100", got)
	}
	if len(a.ActiveEffects) != 1 {
		t.Fatalf("ActiveEffects len = %d, want 1 (timed gain must be tracked)", len(a.ActiveEffects))
	}

	f.tickActiveEffects(-1) // RemainingTableTurns 1 -> 0 -> revert
	if got := a.Characteristic(ResInPercent); got != 0 {
		t.Errorf("ResInPercent after Immunity expiry = %d, want reverted to 0 (immunity must not last the whole fight)", got)
	}
	if len(a.ActiveEffects) != 0 {
		t.Errorf("ActiveEffects len after expiry = %d, want 0", len(a.ActiveEffects))
	}
}

// TestEffectCharacLoss_TimedWeaknessRevertsAtExpiry is the regression for
// Feca's Weakness (-damage of all elements for 1 round): a CharacLoss with a
// finite Duration must revert at expiry rather than persist for the fight.
func TestEffectCharacLoss_TimedWeaknessRevertsAtExpiry(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	b.Characteristics[DmgInPercent].Value = 0

	def := runningEffectDef{Kind: EffectCharacLoss, Charc: DmgInPercent}
	eff := gamedata.EffectDef{Params: []float32{20}, Duration: []int32{1}} // -20 dmg%, 1 table-turn
	f.applyRunningEffect(a, b, def, eff, -1)

	if got := b.Characteristic(DmgInPercent); got != -20 {
		t.Fatalf("DmgInPercent right after Weakness = %d, want -20", got)
	}

	f.tickActiveEffects(-1) // expires -> revert
	if got := b.Characteristic(DmgInPercent); got != 0 {
		t.Errorf("DmgInPercent after Weakness expiry = %d, want reverted to 0", got)
	}
}

// TestEffectCharacGain_NoDurationNeverReverts confirms a CharacGain WITHOUT a
// duration (e.g. the permanent "Game" armors, or a one-shot resource gain) is
// NOT tracked and stays for the fight -- preserving prior behavior.
func TestEffectCharacGain_NoDurationNeverReverts(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})

	def := runningEffectDef{Kind: EffectCharacGain, Charc: ResFirePercent}
	eff := gamedata.EffectDef{Params: []float32{25}} // no Duration -> permanent
	f.applyRunningEffect(a, a, def, eff, -1)

	if len(a.ActiveEffects) != 0 {
		t.Errorf("a durationless CharacGain must not be tracked, got %d active effects", len(a.ActiveEffects))
	}
	f.tickActiveEffects(-1)
	if got := a.Characteristic(ResFirePercent); got != 25 {
		t.Errorf("permanent resist gain = %d after tick, want still 25", got)
	}
}

func TestTickActiveEffects_SkipsDeadFighters(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	def := runningEffectDef{Kind: EffectCharacPoison, Elem: ElementPhysical}
	eff := gamedata.EffectDef{Params: []float32{10}, Duration: []int32{5}}
	f.applyRunningEffect(a, b, def, eff, -1)

	b.IsDead = true
	hpBeforeTick := b.Characteristic(HP)
	f.tickActiveEffects(-1)
	if got := b.Characteristic(HP); got != hpBeforeTick {
		t.Errorf("HP changed on a dead fighter's poison tick = %d, want unchanged %d", got, hpBeforeTick)
	}
}

func TestClearActiveEffectsOnDeath_RemovesAllTrackedEffects(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	// HP set high enough that the poison's own immediate first tick
	// doesn't itself kill b -- this test wants to observe killFighter
	// being called explicitly afterward, on a fighter that's still alive
	// with an active poison tracked.
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	def := runningEffectDef{Kind: EffectCharacPoison, Elem: ElementPhysical}
	eff := gamedata.EffectDef{Params: []float32{10}, Duration: []int32{5}}
	f.applyRunningEffect(a, b, def, eff, -1)

	if len(b.ActiveEffects) == 0 {
		t.Fatalf("expected poison to be tracked before death")
	}
	if b.IsDead {
		t.Fatalf("test setup invalid: b already dead before killFighter call")
	}

	f.killFighter(b, -1)
	if len(b.ActiveEffects) != 0 {
		t.Errorf("ActiveEffects after death = %d, want 0 (cleared)", len(b.ActiveEffects))
	}
}

// TestFight_PoisonTicksAcrossRealTableTurnBoundary is a fuller integration
// test using the real startNextTurn/Timeline wrap machinery (not directly
// calling tickActiveEffects), confirming the hook point in turns.go fires
// at the right moment during a real turn cycle.
func TestFight_PoisonTicksAcrossRealTableTurnBoundary(t *testing.T) {
	bc := newFakeBroadcaster()
	a := NewFighterFromBreed(1, 1, BreedIop, "A", 0, 0)
	a.CoachID = 100
	a.Characteristics[Init].Value = 100
	b := NewFighterFromBreed(2, 2, BreedFeca, "B", 0, 0)
	b.CoachID = 200
	b.Characteristics[Init].Value = 50
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100

	teamA := &Team{ID: 1, Mates: []*TeamMate{{CoachID: 100, Fighters: []*Fighter{a}}}}
	teamB := &Team{ID: 2, Mates: []*TeamMate{{CoachID: 200, Fighters: []*Fighter{b}}}}
	f := NewFight(1, 1, testClocks(), []*Team{teamA, teamB}, bc, nil, zerolog.Nop())
	f.rng = rand.New(rand.NewSource(1))
	f.Timeline = NewTimeline([]*Fighter{a, b})

	def := runningEffectDef{Kind: EffectCharacPoison, Elem: ElementPhysical}
	eff := gamedata.EffectDef{Params: []float32{10}, Duration: []int32{5}}
	f.applyRunningEffect(a, b, def, eff, -1)
	hpAfterFirstTick := b.Characteristic(HP)

	// a's turn (index 0, higher init) -> wraps to a new table-turn.
	f.startNextTurn()
	if f.Timeline.CurrentFighter() != a {
		t.Fatalf("expected a's turn first, got %v", f.Timeline.CurrentFighter())
	}
	// The very first StartNextTurn call always reports isNewTableTurn=true
	// (see Timeline.StartNextTurn's doc comment), so poison should have
	// ticked once already by now.
	if got := b.Characteristic(HP); got != hpAfterFirstTick-10 {
		t.Errorf("HP after first real table-turn boundary = %d, want %d", got, hpAfterFirstTick-10)
	}

	f.Timeline.EndCurrentTurn()
	f.startNextTurn() // b's turn, same table-turn -- no additional tick.
	if got := b.Characteristic(HP); got != hpAfterFirstTick-10 {
		t.Errorf("HP after advancing within the same table-turn = %d, want unchanged %d", got, hpAfterFirstTick-10)
	}

	f.Timeline.EndCurrentTurn()
	f.startNextTurn() // wraps back to a -- new table-turn -> another tick.
	if got := b.Characteristic(HP); got != hpAfterFirstTick-20 {
		t.Errorf("HP after second table-turn wrap = %d, want %d", got, hpAfterFirstTick-20)
	}
}
