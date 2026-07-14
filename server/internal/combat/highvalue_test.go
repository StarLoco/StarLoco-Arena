package combat

import (
	"math/rand"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file covers the high-value combat-fidelity additions:
//   - HV2: crit selects a spell/card's isCritical effect subset
//   - HV4: tackle evasion scales with the per-side EvasionBonus
//   - HV5: a plain Summon uses its SummoningTemplate HP/AP/MP
//   - HV1: the reactive trigger-bus defers triggered effects and fires them
//          on the matching in-fight event
// See docs/08-java-parity-roadmap.md §8.23 and FEATURES-STATUS.md §9.

// --- HV2: critical effect selection -----------------------------------

func TestSelectEffectsForCrit_CritPicksCriticalSubset(t *testing.T) {
	normal := gamedata.EffectDef{ID: 1, ActionID: 10, IsCritical: false}
	critical := gamedata.EffectDef{ID: 2, ActionID: 10, IsCritical: true}
	effects := []gamedata.EffectDef{normal, critical}

	onCrit := selectEffectsForCrit(effects, true)
	if len(onCrit) != 1 || onCrit[0].ID != 2 {
		t.Fatalf("crit selection = %+v, want just the critical effect (id 2)", onCrit)
	}
	onNormal := selectEffectsForCrit(effects, false)
	if len(onNormal) != 1 || onNormal[0].ID != 1 {
		t.Fatalf("normal selection = %+v, want just the normal effect (id 1)", onNormal)
	}
}

func TestSelectEffectsForCrit_NoCriticalEffectsFallsBackToNormal(t *testing.T) {
	// A spell that can't be critical (no isCritical effect) runs its normal
	// effects even on a lucky crit roll -- never a no-op.
	effects := []gamedata.EffectDef{
		{ID: 1, IsCritical: false},
		{ID: 2, IsCritical: false},
	}
	onCrit := selectEffectsForCrit(effects, true)
	if len(onCrit) != 2 {
		t.Fatalf("crit selection with no critical effects = %d effects, want fallback to both normal effects", len(onCrit))
	}
}

func TestSelectEffectsForCrit_OnlyCriticalEffectsRunOnNormalHit(t *testing.T) {
	// A container whose every effect is critical-flagged still runs them on
	// a normal hit rather than doing nothing.
	effects := []gamedata.EffectDef{
		{ID: 1, IsCritical: true},
	}
	onNormal := selectEffectsForCrit(effects, false)
	if len(onNormal) != 1 || onNormal[0].ID != 1 {
		t.Fatalf("normal selection with only-critical effects = %+v, want fallback to the critical effect", onNormal)
	}
}

// --- HV4: tackle evasion scaling --------------------------------------

func TestEvasionChanceAgainst_DefaultIsFlatBase(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = f
	if got := evasionChanceAgainst(a, b); got != tackleBaseEvasionPercent {
		t.Fatalf("default evasion chance = %d, want flat base %d", got, tackleBaseEvasionPercent)
	}
}

func TestEvasionChanceAgainst_EvaderBonusRaisesTacklerGripLowers(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = f
	a.EvasionBonus = 20 // easier to evade
	b.EvasionBonus = 5  // stronger grip
	// 67 + 20 - 5 = 82
	if got := evasionChanceAgainst(a, b); got != 82 {
		t.Fatalf("evasion chance with +20 evader / +5 tackler = %d, want 82", got)
	}
}

func TestEvasionChanceAgainst_ClampedTo0And100(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	_ = f
	a.EvasionBonus = 1000
	if got := evasionChanceAgainst(a, b); got != 100 {
		t.Fatalf("evasion chance = %d, want clamped to 100", got)
	}
	a.EvasionBonus = 0
	b.EvasionBonus = 1000
	if got := evasionChanceAgainst(a, b); got != 0 {
		t.Fatalf("evasion chance = %d, want clamped to 0", got)
	}
}

func TestAttemptEvadeTackle_GuaranteedEvadeWithHighBonus(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0} // adjacent opponent
	a.EvasionBonus = 1000           // 100% evade
	if !f.attemptEvadeTackle(a) {
		t.Fatalf("expected guaranteed evade with 100%% chance")
	}
}

func TestAttemptEvadeTackle_GuaranteedTackleWithZeroChance(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.EvasionBonus = 1000 // drives a's chance to 0
	if f.attemptEvadeTackle(a) {
		t.Fatalf("expected guaranteed tackle with 0%% evade chance")
	}
}

// --- HV5: summon stat template ----------------------------------------

// findSummonOf returns the fighter in the timeline whose Father is father,
// or nil. A summon is inserted after its father, not necessarily last.
func findSummonOf(f *Fight, father *Fighter) *Fighter {
	for _, fr := range f.Timeline.Order() {
		if fr.Father == father {
			return fr
		}
	}
	return nil
}

func TestApplySummon_UsesSummoningTemplateStats(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	f.data = &gamedata.Store{
		Summonings: gamedata.NewRepository(func() (map[int32]gamedata.SummoningTemplate, error) {
			return map[int32]gamedata.SummoningTemplate{
				42: {ID: 42, HP: 30, AP: 7, MP: 4},
			}, nil
		}),
	}
	a.Position = Point3{X: 5, Y: 5}
	target := Point3{X: 6, Y: 5} // free adjacent cell

	// EffectSummon (actionID 67) with params[0]=42 -> template 42.
	eff := gamedata.EffectDef{ActionID: 67, Params: []float32{42}}
	before := len(f.Timeline.Order())
	f.applySummon(a, target, eff, -1)

	if got := len(f.Timeline.Order()); got != before+1 {
		t.Fatalf("timeline size after summon = %d, want %d", got, before+1)
	}
	summon := findSummonOf(f, a)
	if summon == nil {
		t.Fatalf("no summon with father == caster found in timeline")
	}
	if got := summon.Characteristic(HP); got != 30 {
		t.Errorf("summon HP = %d, want 30 from template", got)
	}
	if got := summon.Characteristic(AP); got != 7 {
		t.Errorf("summon AP = %d, want 7 from template", got)
	}
	if got := summon.Characteristic(MP); got != 4 {
		t.Errorf("summon MP = %d, want 4 from template", got)
	}
}

// TestSummonMirror_UsesDialTemplateStats verifies Xelor's Dial (action 97,
// SummonMirror) spawns from its SummoningTemplate (params[0]=defId) rather
// than cloning the caster: the decompiled SummonMirror.execute passes
// m_value=params[0] to summonMirror() as the definition id. So the Dial gets
// its own HP50/AP4/MP1 + Mirwar spell, and (unlike Sram's Double) is NOT a
// caster stat-clone.
func TestSummonMirror_UsesDialTemplateStats(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	f.data = &gamedata.Store{
		Summonings: gamedata.NewRepository(func() (map[int32]gamedata.SummoningTemplate, error) {
			return map[int32]gamedata.SummoningTemplate{
				7: {ID: 7, HP: 50, AP: 4, MP: 1, SpellID: 203}, // the Dial
			}, nil
		}),
	}
	a.Position = Point3{X: 5, Y: 5}
	// Give the caster distinctly different stats to prove no cloning.
	a.Characteristics[HP].Max, a.Characteristics[AP].Max, a.Characteristics[MP].Max = 999, 9, 9

	eff := gamedata.EffectDef{ActionID: 97, Params: []float32{7}} // SummonMirror -> template 7
	f.applySummon(a, Point3{X: 6, Y: 5}, eff, -1)

	dial := findSummonOf(f, a)
	if dial == nil {
		t.Fatalf("no dial summon found")
	}
	if got := dial.Characteristic(HP); got != 50 {
		t.Errorf("dial HP = %d, want 50 from template (not caster clone)", got)
	}
	if got := dial.Characteristic(AP); got != 4 {
		t.Errorf("dial AP = %d, want 4 from template", got)
	}
	if got := dial.Characteristic(MP); got != 1 {
		t.Errorf("dial MP = %d, want 1 from template", got)
	}
	if dial.SummonSpellID != 203 {
		t.Errorf("dial SummonSpellID = %d, want 203 (Mirwar) so it auto-casts its reflect buff", dial.SummonSpellID)
	}
}

func TestApplySummon_FallsBackToBreedWhenTemplateMissing(t *testing.T) {
	f, a, _ := newTestFightForEffects(t)
	f.data = &gamedata.Store{
		Summonings: gamedata.NewRepository(func() (map[int32]gamedata.SummoningTemplate, error) {
			return map[int32]gamedata.SummoningTemplate{}, nil
		}),
	}
	a.Position = Point3{X: 5, Y: 5}
	eff := gamedata.EffectDef{ActionID: 67, Params: []float32{999}} // no such template
	f.applySummon(a, Point3{X: 6, Y: 5}, eff, -1)

	summon := findSummonOf(f, a)
	if summon == nil {
		t.Fatalf("no summon with father == caster found")
	}
	breedStats, _ := GetBreedStats(a.Breed)
	if got := summon.Characteristic(HP); got != breedStats.BaseHP {
		t.Errorf("summon HP with missing template = %d, want breed fallback %d", got, breedStats.BaseHP)
	}
}

// --- HV1: reactive trigger-bus ----------------------------------------

func TestEffectMustBeDeferred(t *testing.T) {
	if effectMustBeDeferred(gamedata.EffectDef{}) {
		t.Errorf("plain effect should not be deferred")
	}
	if !effectMustBeDeferred(gamedata.EffectDef{TriggersAfter: []int32{2}}) {
		t.Errorf("effect with TriggersAfter should be deferred")
	}
	if !effectMustBeDeferred(gamedata.EffectDef{TriggersBefore: []int32{2}}) {
		t.Errorf("effect with TriggersBefore should be deferred")
	}
}

func TestReactiveEffect_DeferredThenFiresOnAttack(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	a.Position = Point3{X: 0, Y: 0}
	b.Position = Point3{X: 1, Y: 0}
	b.Characteristics[HP].Value = 100
	b.Characteristics[HP].Max = 100
	a.Characteristics[HP].Value = 100
	a.Characteristics[HP].Max = 100

	// Arm a reactive HP-GAIN (actionID 69) on b that fires when b is
	// attacked: a "regen-when-struck" that heals b by 10 each time it takes
	// a hit. HP_GAIN targets the carrier itself, so no opponent-check is
	// needed. First injure b so the heal has room to show.
	b.Characteristics[HP].Value = 50
	reactive := gamedata.EffectDef{
		ID: 1, ActionID: 69, Params: []float32{10}, // actionID 69 -> HP gain
		TriggersAfter: []int32{trigOnAttacked},
		Duration:      []int32{3, 0},
	}
	def, ok := LookupRunningEffect(69)
	if !ok {
		t.Fatalf("actionID 69 not resolvable")
	}
	// Deferring should NOT heal b immediately.
	f.deferReactiveEffect(b, b, def, reactive)
	if got := b.Characteristic(HP); got != 50 {
		t.Fatalf("HP after deferring reactive effect = %d, want unchanged 50 (stored, not executed)", got)
	}
	if len(b.ReactiveEffects) != 1 {
		t.Fatalf("reactive effect not stored on carrier")
	}

	// Now a attacks b for 5 -> the on-attacked reactive HP_GAIN(10) fires,
	// so net HP change is -5 (hit) +10 (regen) = +5, ending at 55.
	f.applyDamageFromEffect(a, b, 5, gamedata.EffectDef{ActionID: 4, Params: []float32{5}}, -1)
	if got := b.Characteristic(HP); got != 55 {
		t.Errorf("HP after 5 hit + 10 regen reactive = %d, want 55", got)
	}
}

func TestReactiveEffect_ExpiresAfterDuration(t *testing.T) {
	f, _, b := newTestFightForEffects(t)
	reactive := gamedata.EffectDef{
		ID: 1, ActionID: 4, Params: []float32{10},
		TriggersAfter: []int32{trigOnAttacked},
		Duration:      []int32{2, 0},
	}
	def, _ := LookupRunningEffect(4)
	f.deferReactiveEffect(nil, b, def, reactive)
	if len(b.ReactiveEffects) != 1 {
		t.Fatalf("reactive effect not stored")
	}
	// Two table-turn ticks should age it out (Remaining 2 -> 1 -> 0).
	f.tickReactiveEffects(b, -1)
	if len(b.ReactiveEffects) != 1 {
		t.Fatalf("reactive effect expired too early after 1 tick")
	}
	f.tickReactiveEffects(b, -1)
	if len(b.ReactiveEffects) != 0 {
		t.Fatalf("reactive effect did not expire after 2 ticks, remaining=%d", len(b.ReactiveEffects))
	}
}

func TestReactiveEffect_InfiniteDurationNeverExpires(t *testing.T) {
	f, _, b := newTestFightForEffects(t)
	reactive := gamedata.EffectDef{
		ID: 1, ActionID: 4, Params: []float32{10},
		TriggersAfter: []int32{trigOnAttacked},
		Duration:      []int32{63, 0}, // infinite
	}
	def, _ := LookupRunningEffect(4)
	f.deferReactiveEffect(nil, b, def, reactive)
	for i := 0; i < 5; i++ {
		f.tickReactiveEffects(b, -1)
	}
	if len(b.ReactiveEffects) != 1 {
		t.Fatalf("infinite-duration reactive effect expired, want it to persist")
	}
}

// --- Trigger emission for the niche charac-op triggers (52/54/56/64) ---
//
// These verify that the characteristic-mutation effects EMIT the trigger
// their real-data listeners (spell-rebound / poison) wait for, so those
// listeners actually fire (previously stored-but-inert). Each arms a
// reactive self-HP-gain keyed to the trigger, applies the mutation, and
// checks the reactive fired (HP went up).

func armRegenOn(f *Fight, carrier *Fighter, trigger int32) {
	def, _ := LookupRunningEffect(69) // HP_GAIN
	f.deferReactiveEffect(carrier, carrier, def, gamedata.EffectDef{
		ID: 1, ActionID: 69, Params: []float32{10},
		TriggersAfter: []int32{trigger},
		Duration:      []int32{3, 0},
	})
}

func TestEmit_CritRateDebuffFiresTrigger54(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 100
	b.Characteristics[CriticalRate].Value = 20
	b.Characteristics[CriticalRate].Max = 20
	armRegenOn(f, b, trigOnCritRateDebuff)

	// Debuff b's CRITICAL_RATE -> emits trigger 54 -> regen fires (+10).
	f.applyRunningEffect(a, b, runningEffectDef{Kind: EffectCharacDebuff, Charc: CriticalRate}, effectDefWithParams(5), -1)

	if got := b.Characteristic(HP); got != 60 {
		t.Errorf("HP after crit-rate debuff firing trig54 regen = %d, want 60 (+10)", got)
	}
}

func TestEmit_DmgDebuffFiresTrigger64(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 100
	b.Characteristics[Dmg].Value = 10
	b.Characteristics[Dmg].Max = 10
	armRegenOn(f, b, trigOnDmgDebuff)

	f.applyRunningEffect(a, b, runningEffectDef{Kind: EffectCharacDebuff, Charc: Dmg}, effectDefWithParams(5), -1)

	if got := b.Characteristic(HP); got != 60 {
		t.Errorf("HP after dmg debuff firing trig64 regen = %d, want 60 (+10)", got)
	}
}

func TestEmit_CritRateLossFiresTrigger52(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 100
	b.Characteristics[CriticalRate].Value = 20
	b.Characteristics[CriticalRate].Max = 20
	armRegenOn(f, b, trigOnAPLoss) // 52

	f.applyRunningEffect(a, b, runningEffectDef{Kind: EffectCharacLoss, Charc: CriticalRate}, effectDefWithParams(5), -1)

	if got := b.Characteristic(HP); got != 60 {
		t.Errorf("HP after crit-rate loss firing trig52 regen = %d, want 60 (+10)", got)
	}
}

func TestEmit_NoTriggerForUnlistenedCharac(t *testing.T) {
	// A debuff of a characteristic with NO emission mapping (e.g. HEAL) must
	// NOT fire the trigger-52-armed reactive.
	f, a, b := newTestFightForEffects(t)
	f.rng = rand.New(rand.NewSource(1))
	b.Characteristics[HP].Value = 50
	b.Characteristics[HP].Max = 100
	b.Characteristics[Heal].Value = 10
	b.Characteristics[Heal].Max = 10
	armRegenOn(f, b, trigOnDmgDebuff)

	// Debuff HEAL -> no (kind,charac) mapping -> no trigger -> no regen.
	f.applyRunningEffect(a, b, runningEffectDef{Kind: EffectCharacDebuff, Charc: Heal}, effectDefWithParams(5), -1)

	if got := b.Characteristic(HP); got != 50 {
		t.Errorf("HP after HEAL debuff = %d, want unchanged 50 (no trigger emitted for unlistened charac)", got)
	}
}

// --- HV3: esquive PA/PM (documented flat-resist model, verified) ------

func TestEsquiveAPMP_IsFlatResistNotRoll(t *testing.T) {
	// HV3 decision: this game has NO probabilistic AP/MP-loss dodge; it uses
	// the reference's deterministic ResAPLoss/ResMPLoss percent-resist. This
	// test pins that contract: the SAME resist always yields the SAME loss,
	// with no RNG variance across runs.
	for seed := int64(0); seed < 5; seed++ {
		f, a, b := newTestFightForEffects(t)
		f.rng = rand.New(rand.NewSource(seed))
		b.Characteristics[AP].Value = 6
		b.Characteristics[AP].Max = 6
		b.Characteristics[ResAPLoss].Value = 50 // 50% flat resist

		def := runningEffectDef{Kind: EffectCharacLoss, Charc: AP}
		f.applyRunningEffect(a, b, def, effectDefWithParams(4), -1)
		// 4 requested, 50% resisted -> exactly 2 lost, every seed.
		if got := b.Characteristic(AP); got != 4 {
			t.Fatalf("seed %d: AP after loss(4) with 50%% ResAPLoss = %d, want deterministic 4", seed, got)
		}
	}
}

// --- Sacrieur's Sacrifice: reactive damage-redirect -------------------

// armSacrifice arms a Sacrifice buff on `ally` bound to `sacrier`: a reactive
// ExchangePosition (action 64) on the before-HP-loss trigger, per spell 135.
func armSacrifice(f *Fight, sacrier, ally *Fighter) {
	f.deferReactiveEffect(sacrier, ally,
		runningEffectDef{Kind: EffectExchangePosition},
		gamedata.EffectDef{ActionID: 64, TriggersBefore: []int32{trigOnAttacked}, Duration: []int32{4, 0}})
}

func TestSacrifice_RedirectsHitAndSwapsPositions(t *testing.T) {
	f, sac, enemy := newTestFightForEffects(t)
	ally := NewFighterFromBreed(9, sac.TeamID, BreedFeca, "Ally", 0, 0)
	sac.Position = Point3{X: 1, Y: 1}
	ally.Position = Point3{X: 5, Y: 5}
	enemy.Position = Point3{X: 5, Y: 6}
	for _, fr := range []*Fighter{sac, ally, enemy} {
		fr.Characteristics[HP].Value, fr.Characteristics[HP].Max = 100, 100
	}
	armSacrifice(f, sac, ally)

	// Enemy hits the protected ally for 30 -> redirected to the Sacrieur,
	// positions swapped, ally unharmed.
	f.applyDamageFromEffect(enemy, ally, 30, gamedata.EffectDef{ActionID: 2, Params: []float32{30}}, -1)

	if got := ally.Characteristic(HP); got != 100 {
		t.Errorf("protected ally HP = %d, want unchanged 100 (hit redirected)", got)
	}
	if got := sac.Characteristic(HP); got != 70 {
		t.Errorf("Sacrieur HP = %d, want 70 (took the 30 hit)", got)
	}
	if sac.Position != (Point3{X: 5, Y: 5}) || ally.Position != (Point3{X: 1, Y: 1}) {
		t.Errorf("positions not swapped: sacrier=%v ally=%v", sac.Position, ally.Position)
	}

	// The buff persists (not consumed): a second hit also redirects.
	f.applyDamageFromEffect(enemy, ally, 10, gamedata.EffectDef{ActionID: 2, Params: []float32{10}}, -1)
	if got := ally.Characteristic(HP); got != 100 {
		t.Errorf("ally HP after 2nd hit = %d, want 100 (Sacrifice persists for its duration)", got)
	}
	if got := sac.Characteristic(HP); got != 60 {
		t.Errorf("Sacrieur HP after 2nd hit = %d, want 60", got)
	}
}

// TestSacrifice_OneHopDoesNotChain verifies the wiki's "only one swap will
// take place": B is protected by A, and A is protected by C. Hitting B
// redirects to A (one hop); A takes the damage even though A itself carries a
// Sacrifice buff -- C is NOT hit.
func TestSacrifice_OneHopDoesNotChain(t *testing.T) {
	f, a, enemy := newTestFightForEffects(t)
	b := NewFighterFromBreed(9, a.TeamID, BreedFeca, "B", 0, 0)
	c := NewFighterFromBreed(8, a.TeamID, BreedIop, "C", 0, 0)
	a.Position = Point3{X: 1, Y: 1}
	b.Position = Point3{X: 5, Y: 5}
	c.Position = Point3{X: 2, Y: 2}
	enemy.Position = Point3{X: 5, Y: 6}
	for _, fr := range []*Fighter{a, b, c, enemy} {
		fr.Characteristics[HP].Value, fr.Characteristics[HP].Max = 100, 100
	}
	armSacrifice(f, a, b) // B protected by A
	armSacrifice(f, c, a) // A protected by C

	f.applyDamageFromEffect(enemy, b, 30, gamedata.EffectDef{ActionID: 2, Params: []float32{30}}, -1)

	if got := b.Characteristic(HP); got != 100 {
		t.Errorf("B HP = %d, want 100 (hit redirected off B)", got)
	}
	if got := a.Characteristic(HP); got != 70 {
		t.Errorf("A HP = %d, want 70 (one hop -- A takes it despite its own Sacrifice buff)", got)
	}
	if got := c.Characteristic(HP); got != 100 {
		t.Errorf("C HP = %d, want unchanged 100 (only ONE swap, no chain)", got)
	}
}

// --- Caster death removes the buffs/reactive effects it placed ---------

func TestCasterDeath_RemovesAllyBuff(t *testing.T) {
	f, caster, enemy := newTestFightForEffects(t)
	ally := NewFighterFromBreed(9, caster.TeamID, BreedFeca, "Ally", 0, 0)
	f.registerFighter(ally, caster.CoachID) // so removeEffectsCastBy sees it
	_ = enemy

	// Stimulating Word: a permanent +2 AP buff on the ally, cast by `caster`.
	ally.Characteristics[AP].Max = 6
	ally.Characteristics[AP].AddMax(2)
	ally.Characteristics[AP].ToMax()
	ally.trackActiveEffect(ActiveEffect{Kind: ActiveEffectCharacBuff, Caster: caster, Charc: AP, Delta: 2, Infinite: true})

	// Caster dies -> the ally's caster-linked buff must be reverted+removed.
	f.killFighter(caster, -1)

	if got := ally.Characteristics[AP].Max; got != 6 {
		t.Errorf("ally AP.Max after caster death = %d, want 6 (Stimulating Word lost)", got)
	}
	if len(ally.ActiveEffects) != 0 {
		t.Errorf("ally still has %d active effects after caster death, want 0", len(ally.ActiveEffects))
	}
}

func TestCasterDeath_RemovesSacrificeBuff(t *testing.T) {
	f, sac, enemy := newTestFightForEffects(t)
	ally := NewFighterFromBreed(9, sac.TeamID, BreedFeca, "Ally", 0, 0)
	f.registerFighter(ally, sac.CoachID)
	for _, fr := range []*Fighter{sac, ally, enemy} {
		fr.Characteristics[HP].Value, fr.Characteristics[HP].Max = 100, 100
	}
	armSacrifice(f, sac, ally)
	if len(ally.ReactiveEffects) != 1 {
		t.Fatalf("setup: ally should carry the Sacrifice reactive effect")
	}

	// The Sacrieur dies -> the Sacrifice buff on the ally is removed.
	f.killFighter(sac, -1)
	if len(ally.ReactiveEffects) != 0 {
		t.Errorf("ally still carries the Sacrifice buff after its Sacrieur died, want it removed")
	}

	// And a subsequent hit on the ally is NOT redirected (no dead sacrier).
	f.applyDamageFromEffect(enemy, ally, 20, gamedata.EffectDef{ActionID: 2, Params: []float32{20}}, -1)
	if got := ally.Characteristic(HP); got != 80 {
		t.Errorf("ally HP after hit (no more Sacrifice) = %d, want 80", got)
	}
}

// TestCasterDeath_KeepsUnrelatedBuffs verifies only the DEAD caster's buffs
// are removed -- a buff another (living) caster placed survives.
func TestCasterDeath_KeepsUnrelatedBuffs(t *testing.T) {
	f, caster, other := newTestFightForEffects(t)
	ally := NewFighterFromBreed(9, caster.TeamID, BreedFeca, "Ally", 0, 0)
	f.registerFighter(ally, caster.CoachID)

	ally.Characteristics[AP].Max = 6
	ally.Characteristics[AP].AddMax(3) // +1 from caster, +2 from other
	ally.trackActiveEffect(ActiveEffect{Kind: ActiveEffectCharacBuff, Caster: caster, Charc: AP, Delta: 1, Infinite: true})
	ally.trackActiveEffect(ActiveEffect{Kind: ActiveEffectCharacBuff, Caster: other, Charc: AP, Delta: 2, Infinite: true})

	f.killFighter(caster, -1)

	// Only caster's +1 is reverted; other's +2 remains -> Max 6+2 = 8.
	if got := ally.Characteristics[AP].Max; got != 8 {
		t.Errorf("ally AP.Max = %d, want 8 (only dead caster's +1 removed, other's +2 kept)", got)
	}
	if len(ally.ActiveEffects) != 1 {
		t.Errorf("ally active effects = %d, want 1 (the surviving caster's buff)", len(ally.ActiveEffects))
	}
}
