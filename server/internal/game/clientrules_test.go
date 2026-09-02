package game

import (
	"log/slog"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestSetBonusIgnoresCardsAboveTheCoachLevel covers a rule that is BOTH a
// security gap and a fidelity bug.
//
// The client's bonus aggregator (sj_1.java:346-366) skips a card whose
// RequiredLevel exceeds the coach's evolution level, dropping it from its own
// bonuses AND its set's threshold count. But the client only WARNS on equip
// (agn_0.java:32-35 equips anyway), so an honest level-1 player could equip
// end-game cards and the server counted them - unlocking set thresholds that feed
// resurrection chance, XP, morale, fatigue, reputation and wound/death chance,
// while the client refused to display any of it.
func TestSetBonusIgnoresCardsAboveTheCoachLevel(t *testing.T) {
	cards := gamedata.NewCards(
		&gamedata.CoachCard{ID: 1, CardSet: 7, RequiredLevel: 1},
		&gamedata.CoachCard{ID: 2, CardSet: 7, RequiredLevel: 40}, // far above level 1
	)

	// Standing 0 -> level 1 (StandingToLevel clamps to a minimum of 1).
	coach := &domain.Coach{ID: 1, Standing: 0, Inventory: []domain.CoachCard{
		{TemplateID: 1, Pos: 1, Quantity: 1},
		{TemplateID: 2, Pos: 2, Quantity: 1},
	}}
	s := &Session{Coach: coach, deps: &Deps{Cards: cards, Log: slog.Default()}}

	counts := s.equippedCountsPerSet()
	if got := counts[7]; got != 1 {
		t.Errorf("set 7 counted %d cards, want 1: the level-40 card must not count "+
			"for a level-1 coach", got)
	}

	// Raise the coach well past level 40 and the second card starts counting.
	// level = sqrt(standing/10), so standing 20000 -> level 44.
	coach.Standing = 20000
	if got := StandingToLevel(coach.Standing); got < 40 {
		t.Fatalf("fixture broken: standing 20000 gives level %d, need >= 40", got)
	}
	if got := s.equippedCountsPerSet()[7]; got != 2 {
		t.Errorf("set 7 counted %d cards at a high level, want 2 - the filter is "+
			"too aggressive", got)
	}
}

// TestFighterStateCapacities pins every destination cap against the client's own
// numbers. Only the graveyard was enforced; the TITULAR cap mattered most because
// titularRoster feeds evolution and PvE challenge fights and its 6-limit came
// only from a caller's argument, not from the stored line-up.
func TestFighterStateCapacities(t *testing.T) {
	cases := []struct {
		state uint8
		want  int
		why   string
	}{
		{domain.FighterStateTitular, 6, "hu_2.java:596 `n8 >= 6`"},
		{domain.FighterStateBench, 7, "hu_2.java:611 `n8 >= 7`"},
		{domain.FighterStateGraveyard, 5, "hu_2.java:626 `n8 >= 5`"},
		{domain.FighterStateLegendary, 6, "hu_2.java:655 `n8 >= 6`"},
		{domain.FighterStateLegBench, 9, "hu_2.java:640 `n8 >= 9`"},
	}
	for _, tc := range cases {
		var got int
		switch tc.state {
		case domain.FighterStateTitular, domain.FighterStateLegendary:
			got = maxTeamMembers
		case domain.FighterStateBench:
			got = domain.BenchCapacity
		case domain.FighterStateGraveyard:
			got = domain.GraveyardCapacity
		case domain.FighterStateLegBench:
			got = legendaryBenchCapacity
		}
		if got != tc.want {
			t.Errorf("state %d capacity = %d, want %d (%s)", tc.state, got, tc.want, tc.why)
		}
	}
}

// TestSpellEffectiveCooldownUsesTheDeferredLock covers the unmodelled brake.
//
// The client has TWO recast brakes; the server modelled one. Measured against
// shipped data: 25 spells carry CooldownUnlockDelay > 0 and exactly two rely on
// it alone - 476 has NO other limit at all (Cooldown 0, CastMaxPerTurn 0,
// CastMaxPerTarget 0) at 2 AP and range 1-2, so with 6 AP it was castable three
// times in a turn where retail allows one.
func TestSpellEffectiveCooldownUsesTheDeferredLock(t *testing.T) {
	cases := []struct {
		name             string
		cooldown, unlock uint8
		want             uint8
	}{
		{"neither set", 0, 0, 0},
		{"only the deferred lock (the 476 case)", 0, 1, 1},
		{"only the plain cooldown", 3, 0, 3},
		{"both, cooldown tighter", 5, 2, 5},
		{"both, lock tighter", 2, 5, 5},
	}
	for _, tc := range cases {
		t.Run(tc.name, func(t *testing.T) {
			sp := &gamedata.Spell{Cooldown: tc.cooldown, CooldownUnlockDelay: tc.unlock}
			if got := sp.EffectiveCooldown(); got != tc.want {
				t.Errorf("EffectiveCooldown = %d, want %d", got, tc.want)
			}
		})
	}
	if (*gamedata.Spell)(nil).EffectiveCooldown() != 0 {
		t.Error("nil spell should yield 0")
	}
}
