package game

import (
	"math/rand"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestPostFightReportWireLayout pins the 40-byte `adl_0.cd()` layout. The client
// reads it positionally with no length markers, so a single shifted field
// silently corrupts every value after it on the debrief panel.
func TestPostFightReportWireLayout(t *testing.T) {
	r := &postFightReport{
		injuryRolled: true, won: true, injuryChance: 0x11223344,
		injuryCancel: true, exhausted: true, woundID: 0x55667788,
		deathChance: 0x2A2B2C2D, resurrected: true, dead: true,
		tiredness: 40, tirednessDelta: 7, morale: 60, moraleDelta: -3,
		totalXP: 0x01020304, baseXP: 0x05060708, moraleBonus: 60,
		goodRest: true, xpBeforeGear: 0x090A0B0C, xpFinal: 0x0D0E0F10,
	}
	b := r.encode()
	if len(b) != postFightBytes {
		t.Fatalf("report = %d bytes, want %d (adl_0.nj())", len(b), postFightBytes)
	}
	want := []byte{
		1,                      // cng injuryRolled
		1,                      // cnh won
		0x11, 0x22, 0x33, 0x44, // cni injuryChance
		1,                      // cnj injuryCancel
		1,                      // cnk exhausted
		0x55, 0x66, 0x77, 0x88, // cnl woundID
		0x2A, 0x2B, 0x2C, 0x2D, // cnm deathChance
		1,                      // cnn resurrected
		1,                      // baT dead
		40,                     // aRx tiredness
		7,                      // cno tirednessDelta
		60,                     // aRy morale
		0xFD,                   // cnp moraleDelta (-3 as a signed byte)
		0x01, 0x02, 0x03, 0x04, // cnq totalXP
		0x05, 0x06, 0x07, 0x08, // cnr baseXP
		60,                     // cns moraleBonus
		1,                      // cnt goodRest
		0x09, 0x0A, 0x0B, 0x0C, // cnu xpBeforeGear
		0x0D, 0x0E, 0x0F, 0x10, // cnv xpFinal
	}
	for i := range want {
		if b[i] != want[i] {
			t.Errorf("byte %d = 0x%02X, want 0x%02X", i, b[i], want[i])
		}
	}
}

// TestPostFightXPFormula pins adl_0.a(baseXp, hours, morale).
func TestPostFightXPFormula(t *testing.T) {
	cases := []struct {
		base                  int32
		hours                 int64
		morale                int8
		wantBefore, wantFinal int32
		wantRest              bool
		why                   string
	}{
		{100, 0, 0, 100, 100, false, "no morale, no rest = the raw base"},
		{100, 0, 50, 150, 150, false, "morale IS the bonus percentage"},
		{100, 0, 100, 200, 200, false, "max morale doubles the gain"},
		{100, 13, 0, 150, 150, true, "rested over 12 h = +50%"},
		{100, 12, 0, 100, 100, false, "exactly 12 h does NOT qualify (strict >)"},
		{100, 13, 100, 300, 300, true, "both bonuses multiply, not add: 2.0 * 1.5"},
		{0, 13, 100, 0, 0, true, "nothing from nothing"},
	}
	for _, c := range cases {
		r := &postFightReport{}
		r.applyXP(c.base, c.hours, c.morale)
		if r.xpBeforeGear != c.wantBefore || r.xpFinal != c.wantFinal {
			t.Errorf("base %d, %dh, morale %d = %d/%d, want %d/%d (%s)",
				c.base, c.hours, c.morale, r.xpBeforeGear, r.xpFinal,
				c.wantBefore, c.wantFinal, c.why)
		}
		if r.goodRest != c.wantRest {
			t.Errorf("base %d, %dh: goodRest = %v, want %v (%s)", c.base, c.hours, r.goodRest, c.wantRest, c.why)
		}
		if r.moraleBonus != c.morale {
			t.Errorf("moraleBonus = %d, want the morale value %d", r.moraleBonus, c.morale)
		}
	}
	// addXP is a no-op on a zero result, exactly as the client guards it.
	r := &postFightReport{}
	r.applyXP(0, 0, 0)
	r.addXP(500)
	if r.xpFinal != 0 {
		t.Errorf("addXP onto a zero result = %d, want 0 (adl_0.ft guards on cnv != 0)", r.xpFinal)
	}
}

// TestTirednessRecovery pins et_2.a(fatigue, hours):
// newFatigue = (sqrt(fatigue) - sqrt(hours-1))^2, floored at 0.
func TestTirednessRecovery(t *testing.T) {
	cases := []struct {
		fatigue uint8
		hours   int64
		want    uint8
		why     string
	}{
		{100, 0, 100, "no elapsed time changes nothing"},
		{100, 1, 100, "the first hour is free (hours-1 = 0)"},
		{25, 26, 0, "25 fatigue clears in 26 h"},
		{100, 101, 0, "100 fatigue clears in 101 h"},
		{100, 1000, 0, "recovery never goes negative"},
		{0, 50, 0, "already rested stays rested"},
	}
	for _, c := range cases {
		if got := recoverTiredness(c.fatigue, c.hours); got != c.want {
			t.Errorf("recover(%d, %dh) = %d, want %d (%s)", c.fatigue, c.hours, got, c.want, c.why)
		}
	}
	// Recovery must be monotonic: more rest is never worse.
	prev := uint8(255)
	for h := int64(0); h <= 120; h++ {
		got := recoverTiredness(100, h)
		if got > prev {
			t.Fatalf("recovery went UP at %dh: %d after %d", h, got, prev)
		}
		prev = got
	}
}

// TestMoraleDriftConverges pins the damping in adl_0.dg: a win moves morale by
// (100-morale)/50 and a loss by morale/50, so morale converges on the extremes
// instead of slamming into them.
func TestMoraleDriftConverges(t *testing.T) {
	rng := rand.New(rand.NewSource(1))

	// Winning at already-max morale can gain nothing (the (100-morale) term is 0).
	r := &postFightReport{won: true}
	r.applyMoraleDrift(100, true, rng)
	if r.morale != 100 || r.moraleDelta != 0 {
		t.Errorf("win at morale 100 = %d (delta %d), want 100 (delta 0)", r.morale, r.moraleDelta)
	}
	// Losing at 0 morale cannot go below 0.
	r = &postFightReport{won: false}
	r.applyMoraleDrift(0, false, rng)
	if r.morale != 0 {
		t.Errorf("loss at morale 0 = %d, want 0", r.morale)
	}
	// A win from the floor should generally rise, and never leave 0..100.
	rises := 0
	for i := 0; i < 200; i++ {
		r = &postFightReport{won: true}
		r.applyMoraleDrift(10, true, rng)
		if r.morale < 0 || r.morale > 100 {
			t.Fatalf("morale out of range: %d", r.morale)
		}
		if r.morale > 10 {
			rises++
		}
	}
	if rises < 100 {
		t.Errorf("wins from low morale rose only %d/200 times, expected most", rises)
	}
}

// TestFighterLevel pins nr_0.cs + its PP thresholds, and the names the client
// shows for each level.
func TestFighterLevel(t *testing.T) {
	cases := []struct {
		xp   int32
		want int16
		name string
	}{
		{0, 1, "Larve"}, {860, 1, "Larve (boundary is inclusive)"},
		{861, 2, "Tofu"}, {4000, 2, "Tofu"},
		{4001, 3, "Prespic"}, {10000, 3, "Prespic"},
		{10001, 4, "Bouftou"}, {20000, 4, "Bouftou"},
		{20001, 5, "Craqueleur"}, {40000, 5, "Craqueleur"},
		{40001, 6, "Démon"}, {999999, 6, "Démon (capped)"},
	}
	for _, c := range cases {
		if got := FighterLevel(c.xp); got != c.want {
			t.Errorf("FighterLevel(%d) = %d, want %d (%s)", c.xp, got, c.want, c.name)
		}
	}
}

// TestStandingLevelRoundTrip pins aet_0.nJ / nr_0.ct against each other.
func TestStandingLevelRoundTrip(t *testing.T) {
	if got := StandingToLevel(0); got != 1 {
		t.Errorf("StandingToLevel(0) = %d, want 1 (floor)", got)
	}
	if got := StandingForLevel(1); got != 0 {
		t.Errorf("StandingForLevel(1) = %d, want 0", got)
	}
	for lvl := int32(2); lvl <= 50; lvl++ {
		need := StandingForLevel(lvl)
		if got := StandingToLevel(need); got != lvl {
			t.Errorf("standing %d (for level %d) reports level %d", need, lvl, got)
		}
		// One short of the threshold must still be the previous level.
		if got := StandingToLevel(need - 1); got != lvl-1 {
			t.Errorf("standing %d (one below level %d) reports %d, want %d", need-1, lvl, got, lvl-1)
		}
	}
	if got := StandingToLevel(1 << 30); got != 50 {
		t.Errorf("StandingToLevel(huge) = %d, want the 50 cap", got)
	}
}

// TestPostFightBankRespectsClientGuards checks the persistence guards.
func TestPostFightBankRespectsClientGuards(t *testing.T) {
	// Spendable XP at the cap refuses further gains (et_2.ft).
	f := &domain.Fighter{XP: maxSpendableXP, TotalXP: 90000}
	r := &postFightReport{xpFinal: 500, morale: 50, tiredness: 20}
	r.bank(f, 1000)
	if f.XP != maxSpendableXP || f.TotalXP != 90000 {
		t.Errorf("XP at the cap grew to %d/%d, want it refused", f.XP, f.TotalXP)
	}
	// Below the cap it banks into BOTH counters.
	f = &domain.Fighter{XP: 10, TotalXP: 10}
	r = &postFightReport{xpFinal: 500, morale: 50, tiredness: 20}
	r.bank(f, 1000)
	if f.XP != 510 || f.TotalXP != 510 {
		t.Errorf("banked %d/%d, want 510/510", f.XP, f.TotalXP)
	}
	if r.totalXP != 510 {
		t.Errorf("report totalXP = %d, want the post-bank 510", r.totalXP)
	}
	if f.LastFightAt != 1000 {
		t.Errorf("LastFightAt = %d, want 1000", f.LastFightAt)
	}
	// Out-of-range morale/fatigue are clamped, not wrapped.
	f = &domain.Fighter{}
	r = &postFightReport{morale: -40, tiredness: 120, xpFinal: 1}
	r.bank(f, 1)
	if f.Morale != 0 || f.Tiredness != 100 {
		t.Errorf("clamped to %d morale / %d fatigue, want 0/100", f.Morale, f.Tiredness)
	}
}

// TestHoursSince covers the "never fought" case, which must read as rested.
func TestHoursSince(t *testing.T) {
	if got := hoursSince(0, 999999); got <= goodRestHours {
		t.Errorf("a fighter that never fought reports %dh, want > %d (rested)", got, goodRestHours)
	}
	if got := hoursSince(1000, 1000+7200); got != 2 {
		t.Errorf("hoursSince = %d, want 2", got)
	}
	if got := hoursSince(5000, 1000); got != 0 {
		t.Errorf("a clock going backwards gave %d, want 0", got)
	}
}
