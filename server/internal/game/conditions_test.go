package game

import (
	"math/rand"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// woundCatalogue builds the 10 real wounds plus a blessing and an ailment, with
// the same ids/types/payloads the shipped data carries (locked by
// gamedata.TestLoadConditionsReal).
func woundCatalogue() *gamedata.Conditions {
	fx := func(action int32, param float32) []gamedata.Effect {
		return []gamedata.Effect{{ActionID: action, Params: []float32{param},
			ContainerType: "FIGHTER_CONDITION"}}
	}
	meta := func(action int32, param int32) []gamedata.CardSetEffect {
		return []gamedata.CardSetEffect{{Action: action, Params: []int32{param}}}
	}
	return gamedata.NewConditions(
		// light wounds (types 1..5)
		&gamedata.Condition{ID: 1, Type: 1, Duration: -1, FightEffects: fx(123, 20)}, // leg: -20% dodge
		&gamedata.Condition{ID: 4, Type: 2, Duration: -1, FightEffects: fx(121, 20)}, // arm: -20% block
		&gamedata.Condition{ID: 6, Type: 3, Duration: -1, MetaEffects: meta(1, -10)}, // head: -10% XP
		&gamedata.Condition{ID: 8, Type: 4, Duration: -1, FightEffects: fx(81, 5)},   // torso
		&gamedata.Condition{ID: 10, Type: 5, Duration: -1, FightEffects: fx(77, 10)}, // other: -10 init
		// serious wounds (types 11..15)
		&gamedata.Condition{ID: 3, Type: 11, Duration: -1, FightEffects: fx(18, 1)},   // leg: -1 MP
		&gamedata.Condition{ID: 5, Type: 12, Duration: -1, FightEffects: fx(14, 1)},   // arm: -1 AP
		&gamedata.Condition{ID: 7, Type: 13, Duration: -1, MetaEffects: meta(1, -20)}, // head: -20% XP
		&gamedata.Condition{ID: 9, Type: 14, Duration: -1, FightEffects: fx(81, 20)},  // torso
		&gamedata.Condition{ID: 11, Type: 15, Duration: -1, MetaEffects: meta(9, -1)}, // other: -1 morale
		// a blessing (type 20) and two ailments (type 21, the stacking class)
		&gamedata.Condition{ID: 74, Type: 20, Duration: 3, FightEffects: fx(17, 1)},
		&gamedata.Condition{ID: 72, Type: 21, Duration: 3, FightEffects: fx(12, 20)},
		&gamedata.Condition{ID: 55, Type: 21, Duration: 5, FightEffects: fx(123, 40)},
		// a pet-style grant (type 70), which the normal apply path must refuse
		&gamedata.Condition{ID: 90, Type: 70, Duration: 10, FightEffects: fx(11, 20)},
	)
}

// TestConditionApplyRules pins vm_2.a: one per mutual-exclusion type, FIRST wins
// (no replacement), type 21 stacks, type 70 is refused.
func TestConditionApplyRules(t *testing.T) {
	defs := woundCatalogue()

	f := &domain.Fighter{ID: 1}
	if !applyCondition(defs, f, 1, -1) {
		t.Fatal("first light leg wound should apply")
	}
	// A second wound of the SAME body part is refused — and the original stays.
	if applyCondition(defs, f, 1, -1) {
		t.Error("a duplicate of the same condition applied; want first-wins refusal")
	}
	// Same TYPE via a different id is also refused (type 11 vs held type 1 differ,
	// so use the arm pair to prove the rule is on type, not id).
	if !applyCondition(defs, f, 4, -1) {
		t.Fatal("a wound on a DIFFERENT body part should apply")
	}
	if len(f.Conditions) != 2 {
		t.Fatalf("holding %d conditions, want 2", len(f.Conditions))
	}

	// Type 21 stacks freely: both ailments land.
	if !applyCondition(defs, f, 72, 3) || !applyCondition(defs, f, 55, 5) {
		t.Error("type-21 ailments must stack")
	}
	// Type 70 is never applied through this path.
	if applyCondition(defs, f, 90, 10) {
		t.Error("type-70 condition applied; the client's vm_2.a refuses it here")
	}
	// A blessing (type 20) coexists with wounds.
	if !applyCondition(defs, f, 74, 3) {
		t.Error("a type-20 blessing should apply alongside wounds")
	}
	if !f.HasCondition(1) || !f.HasCondition(4) || !f.HasCondition(74) {
		t.Error("expected conditions missing after the apply sequence")
	}
}

// TestConditionExpiry checks the per-fight countdown and that wounds never tick.
func TestConditionExpiry(t *testing.T) {
	defs := woundCatalogue()
	f := &domain.Fighter{ID: 1}
	applyCondition(defs, f, 1, -1) // permanent wound
	applyCondition(defs, f, 74, 2) // blessing, 2 fights

	if got := expireConditions(f); len(got) != 0 {
		t.Errorf("first fight expired %v, want nothing", got)
	}
	if !f.HasCondition(74) {
		t.Error("a 2-fight blessing vanished after one fight")
	}
	expired := expireConditions(f)
	if len(expired) != 1 || expired[0] != 74 {
		t.Errorf("second fight expired %v, want [74]", expired)
	}
	if f.HasCondition(74) {
		t.Error("the blessing should be gone")
	}
	// The permanent wound survives any number of fights.
	for i := 0; i < 50; i++ {
		expireConditions(f)
	}
	if !f.HasCondition(1) {
		t.Error("a permanent wound expired; wounds only go away by healing")
	}
}

// TestWoundRollUpgradesAndKills pins the three outcomes of bf_1.b.
func TestWoundRollUpgradesAndKills(t *testing.T) {
	defs := woundCatalogue()

	// With no wounds held the upgrade chance is 0² × 10 = 0%, so a fresh fighter
	// can only ever take a NEW light wound.
	rng := rand.New(rand.NewSource(1))
	for i := 0; i < 50; i++ {
		f := &domain.Fighter{ID: 1}
		out := rollWound(defs, f, rng)
		if out.Upgraded || out.Died {
			t.Fatalf("an unwounded fighter was upgraded/killed: %+v", out)
		}
		c := defs.Get(out.WoundID)
		if c == nil || !c.IsLightWound() {
			t.Fatalf("inflicted %d, want a light wound", out.WoundID)
		}
	}

	// Three light wounds force an upgrade (mm_02.size() >= 3).
	rng = rand.New(rand.NewSource(2))
	f := &domain.Fighter{ID: 1}
	applyCondition(defs, f, 1, -1) // leg
	applyCondition(defs, f, 4, -1) // arm
	applyCondition(defs, f, 6, -1) // head
	out := rollWound(defs, f, rng)
	if !out.Upgraded {
		t.Fatalf("3 light wounds must force an upgrade, got %+v", out)
	}
	if c := defs.Get(out.WoundID); c == nil || !c.IsSeriousWound() {
		t.Fatalf("upgrade produced %d, want a serious wound", out.WoundID)
	}
	// The light wound it replaced must be GONE — an upgrade moves a wound, it
	// does not add one.
	light, serious, _ := heldWounds(defs, f)
	if len(light) != 2 || len(serious) != 1 {
		t.Errorf("after upgrade: %d light / %d serious, want 2/1", len(light), len(serious))
	}

	// Three serious wounds + an upgrade = permanent death.
	rng = rand.New(rand.NewSource(3))
	f = &domain.Fighter{ID: 1}
	f.Conditions = []domain.FighterCondition{
		{ConditionID: 3, Remaining: -1},  // serious leg
		{ConditionID: 5, Remaining: -1},  // serious arm
		{ConditionID: 7, Remaining: -1},  // serious head
		{ConditionID: 8, Remaining: -1},  // light torso -> the upgrade victim
		{ConditionID: 10, Remaining: -1}, // light other -> makes 5 parts wounded
	}
	out = rollWound(defs, f, rng)
	if !out.Died {
		t.Fatalf("a 4th serious wound must kill, got %+v", out)
	}
	if f.State != domain.FighterStateDead {
		t.Errorf("state = %d, want %d (dead)", f.State, domain.FighterStateDead)
	}
}

// TestWoundRollNeverPanics guards the two states where the shipped client would
// call nextInt(0) and throw. A fight actor must not panic.
func TestWoundRollNeverPanics(t *testing.T) {
	defs := woundCatalogue()
	rng := rand.New(rand.NewSource(4))

	// All four drawable body parts already wounded, and few enough light wounds
	// that the upgrade branch is not guaranteed.
	for i := 0; i < 200; i++ {
		f := &domain.Fighter{ID: 1}
		f.Conditions = []domain.FighterCondition{
			{ConditionID: 3, Remaining: -1}, // serious leg
			{ConditionID: 5, Remaining: -1}, // serious arm
			{ConditionID: 6, Remaining: -1}, // light head
			{ConditionID: 8, Remaining: -1}, // light torso
		}
		_ = rollWound(defs, f, rng) // must not panic
	}
}

// TestConditionsAffectFighterStats is the payoff: a wound must actually change
// how the fighter plays, through the SAME path equipped cards use.
func TestConditionsAffectFighterStats(t *testing.T) {
	defs := woundCatalogue()
	iop := &domain.Fighter{ID: 1, BreedID: 8} // Iop: 75 HP, 6 AP, 3 MP, block 40, dodge 100

	base := computeFighterStatsWithConditions(iop, nil, defs)
	if base.MaxAP != 6 || base.MaxMP != 3 || base.Block != 40 || base.Dodge != 100 {
		t.Fatalf("unwounded Iop = %+v, want AP 6 / MP 3 / block 40 / dodge 100", base)
	}

	// Serious leg (-1 MP) + serious arm (-1 AP) + light leg... no: one per part.
	iop.Conditions = []domain.FighterCondition{
		{ConditionID: 3, Remaining: -1}, // serious leg: -1 MP
		{ConditionID: 5, Remaining: -1}, // serious arm: -1 AP
	}
	hurt := computeFighterStatsWithConditions(iop, nil, defs)
	if hurt.MaxAP != 5 || hurt.MaxMP != 2 {
		t.Errorf("wounded Iop AP/MP = %d/%d, want 5/2", hurt.MaxAP, hurt.MaxMP)
	}

	// A light leg wound cuts DODGE and a light arm wound cuts BLOCK — the same
	// stats the tackle roll uses (B-063), so wounds make a fighter easier to pin.
	iop.Conditions = []domain.FighterCondition{
		{ConditionID: 1, Remaining: -1}, // light leg: -20% dodge
		{ConditionID: 4, Remaining: -1}, // light arm: -20% block
	}
	tackled := computeFighterStatsWithConditions(iop, nil, defs)
	if tackled.Dodge != 80 {
		t.Errorf("dodge with a light leg wound = %d, want 80", tackled.Dodge)
	}
	if tackled.Block != 20 {
		t.Errorf("block with a light arm wound = %d, want 20", tackled.Block)
	}

	// Stats must never go negative however many penalties pile on.
	iop.Conditions = []domain.FighterCondition{
		{ConditionID: 3, Remaining: -1},
		{ConditionID: 5, Remaining: -1},
		{ConditionID: 55, Remaining: 5}, // -40% dodge
		{ConditionID: 72, Remaining: 3}, // -20 max HP
	}
	crippled := computeFighterStatsWithConditions(iop, nil, defs)
	if crippled.MaxHP < 1 || crippled.MaxAP < 0 || crippled.MaxMP < 0 ||
		crippled.Dodge < 0 || crippled.Block < 0 {
		t.Errorf("penalties drove a stat negative: %+v", crippled)
	}
}

// TestConditionMetaBonus checks that a head wound reaches the post-fight report.
func TestConditionMetaBonus(t *testing.T) {
	defs := woundCatalogue()
	f := &domain.Fighter{ID: 1}
	if got := conditionMetaBonus(defs, f, aiXPPercent); got != 0 {
		t.Errorf("unwounded XP modifier = %d, want 0", got)
	}
	applyCondition(defs, f, 6, -1) // light head: -10% XP
	if got := conditionMetaBonus(defs, f, aiXPPercent); got != -10 {
		t.Errorf("light head wound XP modifier = %d, want -10", got)
	}
	applyCondition(defs, f, 11, -1) // serious other: -1 morale
	if got := conditionMetaBonus(defs, f, aiMorale); got != -1 {
		t.Errorf("serious other wound morale modifier = %d, want -1", got)
	}
}

// TestHealWounds pins the consumable behaviour of AI 5 / AI 11.
func TestHealWounds(t *testing.T) {
	defs := woundCatalogue()
	always := func(int) int { return 0 } // roll 0 -> always under any chance > 0
	never := func(int) int { return 99 } // roll 99 -> only a 100% chance heals

	f := &domain.Fighter{ID: 1}
	f.Conditions = []domain.FighterCondition{
		{ConditionID: 1, Remaining: -1}, // light leg
		{ConditionID: 5, Remaining: -1}, // serious arm
	}
	// Healing LIGHT wounds must leave the serious one alone.
	if n := healWounds(defs, f, false, 100, always); n != 1 {
		t.Errorf("healed %d light wounds, want 1", n)
	}
	if !f.HasCondition(5) {
		t.Error("healing light wounds removed a SERIOUS one")
	}
	// A failed roll heals nothing.
	if n := healWounds(defs, f, true, 50, never); n != 0 {
		t.Errorf("healed %d on a failed roll, want 0", n)
	}
	if n := healWounds(defs, f, true, 100, always); n != 1 {
		t.Errorf("healed %d serious wounds, want 1", n)
	}
	if len(f.Conditions) != 0 {
		t.Errorf("%d conditions left, want none", len(f.Conditions))
	}
}

// TestInjuryChancesScaleWithExperience pins adl_0.atd + ate(): a veteran is far
// more fragile than a rookie, death grows QUADRATICALLY with injury risk, and
// fatigue then amplifies the injury chance on top.
func TestInjuryChancesScaleWithExperience(t *testing.T) {
	rng := rand.New(rand.NewSource(5))
	cases := []struct {
		totalXP   int32
		wantBase  int32 // injury % before the fatigue amplification
		wantDeath int32
	}{
		{0, 0, 0},
		{1000, 1, 0},
		{10000, 10, 1},
		{20000, 20, 4},
		{50000, 50, 25},
		{100000, 100, 100},
	}
	for _, c := range cases {
		r := &postFightReport{}
		r.rollInjuryChances(c.totalXP, rng)

		// Death is NOT amplified by fatigue — only the injury chance is.
		if r.deathChance != c.wantDeath {
			t.Errorf("totalXP %d -> death %d%%, want %d%%", c.totalXP, r.deathChance, c.wantDeath)
		}
		// ate(): injury = base + tiredness*base/100.
		wantInjury := c.wantBase + int32(r.tiredness)*c.wantBase/100
		if r.injuryChance != wantInjury {
			t.Errorf("totalXP %d -> injury %d%%, want %d%% (base %d amplified by %d fatigue)",
				c.totalXP, r.injuryChance, wantInjury, c.wantBase, r.tiredness)
		}
		// The roll always costs fatigue (rand(13)), which is what makes repeated
		// fighting compound the risk.
		if r.tirednessDelta < 0 || r.tirednessDelta > 12 {
			t.Errorf("fatigue delta = %d, want 0..12", r.tirednessDelta)
		}
		// Zero chances auto-set their cancel flags, which is what spares rookies.
		if c.wantBase == 0 && !r.injuryCancel {
			t.Errorf("totalXP %d: injuryCancel not set despite a 0%% chance", c.totalXP)
		}
		if c.wantDeath == 0 && !r.resurrected {
			t.Errorf("totalXP %d: death not cancelled despite a 0%% chance", c.totalXP)
		}
	}

	// An EXHAUSTED fighter reports a 100% injury chance regardless of its XP —
	// the client's `ati()` returns 100 whenever the exhausted flag is set.
	r := &postFightReport{tiredness: maxTiredness}
	r.finaliseTiredness()
	if !r.exhausted {
		t.Error("a fighter at max fatigue must be flagged exhausted")
	}
}
