package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// Bits 512 / 1024 test the target's breed against the ZERO breed (`xq.axE`)
// rather than against a numbered slot:
//
//	aLc: (0x200 & c) != 0 && (!(t instanceof gn_0) || t.NY().lV() != xq.axE.lV()) -> reject
//	     (0x400 & c) != 0 && (!(t instanceof gn_0) || t.NY().lV() == xq.axE.lV()) -> reject
//
// They were missing until the np_1 type-12 work needed them: the three shipped
// fight-start effects carry target mask 1024, and an unimplemented bit is
// silently permissive, so the buff would have landed on summons too.
func TestTargetConditionBreedZeroBits(t *testing.T) {
	real := &FightFighter{WireID: 1, TeamID: 0, Fighter: &domain.Fighter{BreedID: 8}}
	summon := &FightFighter{WireID: 2, TeamID: 0, Father: real}

	for _, tc := range []struct {
		name         string
		cond         int64
		target       *FightFighter
		wantAccepted bool
	}{
		{"1024 accepts a real breed", condBreedIsNotZero, real, true},
		{"1024 rejects a summon", condBreedIsNotZero, summon, false},
		{"512 rejects a real breed", condBreedIsZero, real, false},
		{"512 accepts a summon", condBreedIsZero, summon, true},
		// Both bits in one condition is unsatisfiable, exactly as two positive
		// breed bits are: the client ANDs within a condition.
		{"512|1024 accepts nobody (real)", condBreedIsZero | condBreedIsNotZero, real, false},
		{"512|1024 accepts nobody (summon)", condBreedIsZero | condBreedIsNotZero, summon, false},
	} {
		if got := targetConditionPasses(real, tc.target, tc.cond); got != tc.wantAccepted {
			t.Errorf("%s: passes = %v, want %v", tc.name, got, tc.wantAccepted)
		}
	}
}

// TestFightStartMaskExcludesSummons is the mask in the shape the shipped data
// actually uses it: one condition, one bit, evaluated through the public entry
// point. This is what stops challenges 29/30/31 from buffing summons.
func TestFightStartMaskExcludesSummons(t *testing.T) {
	real := &FightFighter{WireID: 1, TeamID: 0, Fighter: &domain.Fighter{BreedID: 3}}
	summon := &FightFighter{WireID: 2, TeamID: 1, Father: real}
	mask := []int64{condBreedIsNotZero}

	if !effectTargetAllowed(real, real, mask) {
		t.Error("the +40 dodge start effect skipped a real fighter")
	}
	if effectTargetAllowed(summon, summon, mask) {
		t.Error("the +40 dodge start effect landed on a summon; mask 1024 must exclude breed 0")
	}
}

// TestUnsetBreedZeroBitsAreIgnored: a condition that sets neither bit must not
// start filtering on breed. Guards against the two checks being written without
// their bit tests.
func TestUnsetBreedZeroBitsAreIgnored(t *testing.T) {
	real := &FightFighter{WireID: 1, TeamID: 0, Fighter: &domain.Fighter{BreedID: 8}}
	summon := &FightFighter{WireID: 2, TeamID: 0, Father: real}
	for _, target := range []*FightFighter{real, summon} {
		if !targetConditionPasses(real, target, 0) {
			t.Error("an empty condition rejected a target")
		}
	}
}
