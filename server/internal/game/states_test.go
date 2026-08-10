package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

func TestClassifyState(t *testing.T) {
	cases := []struct {
		action int32
		want   fighterState
		ok     bool
	}{
		// The four displacement immunities are DISTINCT (client avx_0 properties
		// dex/dev/deA/deB) — only 65 stops the fighter walking.
		{65, stateRooted, true},
		{96, statePetrified, true},
		{94, stateStabilized, true},
		{127, stateAnchored, true},
		{128, stateIntransposable, true},
		{57, stateInvisible, true},
		{95, stateImmune, true}, {124, stateImmune, true},
		{1, 0, false}, {69, 0, false},
	}
	for _, c := range cases {
		got, ok := classifyState(c.action)
		if ok != c.ok || (ok && got != c.want) {
			t.Errorf("classifyState(%d) = (%d,%v), want (%d,%v)", c.action, got, ok, c.want, c.ok)
		}
	}
}

func stateTestFight() (*Fight, *FightFighter, *FightFighter) {
	caster := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 7, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	enemy := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 9, Y: 15}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{caster}},
		{ID: 1, Fighters: []*FightFighter{enemy}},
	}}
	f.setPhase(PhaseAction)
	return f, caster, enemy
}

func TestApplyStateAndEnforcement(t *testing.T) {
	// Immunity (124) blocks damage; a heal still lands.
	f, caster, enemy := stateTestFight()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 124, Duration: []int32{2, 0}}, enemy.Pos)
	if !enemy.hasState(stateImmune) {
		t.Fatal("enemy not immune after action 124")
	}
	f.applyDamageEffect(caster, gamedata.Effect{ActionID: 1, Params: []float32{20}}, enemy.Pos, false)
	if enemy.HP != 70 {
		t.Errorf("immune fighter took damage: HP=%d, want 70", enemy.HP)
	}

	// Root (65) blocks movement + zeroes MP.
	f, caster, enemy = stateTestFight()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 65, Duration: []int32{1, 0}}, enemy.Pos)
	if !enemy.hasState(stateRooted) || enemy.MP != 0 {
		t.Errorf("root: state=%v mp=%d, want rooted + mp 0", enemy.hasState(stateRooted), enemy.MP)
	}
	enemy.MP = 3 // even with MP restored, a rooted fighter can't move
	if f.validateFightMove(enemy, []Pos{{X: 8, Y: 15}}) {
		t.Error("validateFightMove allowed a rooted fighter to move")
	}

	// Stabilise (94) blocks push/pull — and ONLY push/pull.
	f, caster, enemy = stateTestFight()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 94, Duration: []int32{1, 0}}, enemy.Pos)
	before := enemy.Pos
	f.applyPushPull(caster, gamedata.Effect{ActionID: 37, Params: []float32{3}}, enemy.Pos, true)
	if enemy.Pos != before {
		t.Errorf("stabilised fighter was pushed: %v -> %v", before, enemy.Pos)
	}
	if enemy.MP != 3 {
		t.Errorf("stabilise must not touch MP: %d want 3", enemy.MP)
	}
	// It does NOT make the fighter intransposable: a swap still works.
	f.applySwap(caster, gamedata.Effect{ActionID: 64}, enemy.Pos)
	if enemy.Pos == before {
		t.Error("stabilise (94) wrongly blocked a swap; only 128 should")
	}

	// Anchored (127 "S'enraciner") blocks CARRY only — the fighter still walks.
	f, caster, enemy = stateTestFight()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 127, Duration: []int32{1, 0}}, enemy.Pos)
	if !enemy.hasState(stateAnchored) {
		t.Fatal("action 127 did not anchor")
	}
	if enemy.MP != 3 {
		t.Errorf("127 must not zero MP (it is not root): %d want 3", enemy.MP)
	}
	if !f.validateFightMove(enemy, []Pos{{X: 8, Y: 15}}) {
		t.Error("an anchored fighter must still be able to walk")
	}
	f.applyCarry(caster, gamedata.Effect{ActionID: 58}, enemy.Pos)
	if caster.CarriedFighter != nil || enemy.CarriedByFighter != nil {
		t.Error("an anchored fighter was carried")
	}

	// Intransposable (128) blocks SWAP only — push still works.
	f, caster, enemy = stateTestFight()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 128, Duration: []int32{1, 0}}, enemy.Pos)
	before = enemy.Pos
	f.applySwap(caster, gamedata.Effect{ActionID: 64}, enemy.Pos)
	if enemy.Pos != before {
		t.Errorf("intransposable fighter was swapped: %v -> %v", before, enemy.Pos)
	}
	f.applyPushPull(caster, gamedata.Effect{ActionID: 37, Params: []float32{1}}, enemy.Pos, true)
	if enemy.Pos == before {
		t.Error("128 wrongly blocked a push; only 94 should")
	}

	// Invisibility (57) hides a fighter from the AI's target search.
	f, caster, enemy = stateTestFight()
	f.resolveEffect(caster, gamedata.Effect{ActionID: 57, Duration: []int32{2, 0}}, enemy.Pos)
	if got := f.nearestOpponent(caster); got != nil {
		t.Errorf("nearestOpponent saw an invisible enemy: %v", got)
	}
}

func TestPetrifiedSkipAndTickStates(t *testing.T) {
	f, _, enemy := stateTestFight()
	enemy.addState(statePetrified, 1)
	if !enemy.hasState(statePetrified) {
		t.Fatal("not petrified")
	}
	// Tick: a 1-turn state expires; a longer one decrements; an infinite persists.
	enemy.addState(stateStabilized, 3)
	enemy.addState(stateImmune, infiniteStateTurns)
	f.tickStates()
	if enemy.hasState(statePetrified) {
		t.Error("1-turn petrify should have expired after one tick")
	}
	if enemy.States[stateStabilized] != 2 {
		t.Errorf("stabilise turns = %d, want 2 after one tick", enemy.States[stateStabilized])
	}
	if enemy.States[stateImmune] != infiniteStateTurns {
		t.Errorf("infinite immune should not decrement: %d", enemy.States[stateImmune])
	}
}

func TestSkipTurnState(t *testing.T) {
	for _, id := range []int32{56, 111} {
		if s, ok := classifyState(id); !ok || s != stateSkipTurn {
			t.Errorf("classifyState(%d) = (%d,%v), want (skipTurn,true)", id, s, ok)
		}
	}
	f, _, enemy := stateTestFight()
	enemy.addState(stateSkipTurn, 2)
	// Skip-turn is consumed per skipped turn, NOT aged per round.
	f.tickStates()
	if enemy.States[stateSkipTurn] != 2 {
		t.Errorf("tickStates aged skip-turn: %d want 2", enemy.States[stateSkipTurn])
	}
	enemy.consumeSkipTurn()
	if enemy.States[stateSkipTurn] != 1 {
		t.Errorf("consumeSkipTurn: %d want 1", enemy.States[stateSkipTurn])
	}
	enemy.consumeSkipTurn()
	if enemy.hasState(stateSkipTurn) {
		t.Error("skip-turn should clear after consuming both")
	}
}

func TestDispel(t *testing.T) {
	f, caster, enemy := stateTestFight()
	// Stack a resource buff (AP), an elemental buff (fire resist) and a state.
	f.resolveEffect(caster, gamedata.Effect{ActionID: 13, Params: []float32{2}, Duration: []int32{3, 0}}, enemy.Pos)  // AP boost
	f.resolveEffect(caster, gamedata.Effect{ActionID: 21, Params: []float32{20}, Duration: []int32{3, 0}}, enemy.Pos) // fire resist
	f.resolveEffect(caster, gamedata.Effect{ActionID: 124, Duration: []int32{3, 0}}, enemy.Pos)                       // immunity state
	if len(enemy.Buffs) != 2 || enemy.Stats.resFlat[elemFire] != 20 || !enemy.hasState(stateImmune) || enemy.MaxAP != 8 {
		t.Fatalf("setup: buffs=%d fireRes=%d immune=%v maxAP=%d", len(enemy.Buffs), enemy.Stats.resFlat[elemFire], enemy.hasState(stateImmune), enemy.MaxAP)
	}

	// Désenvoûtement (62): reverts every buff and clears every state.
	f.resolveEffect(caster, gamedata.Effect{ActionID: 62}, enemy.Pos)
	if len(enemy.Buffs) != 0 || enemy.Stats.resFlat[elemFire] != 0 || enemy.hasState(stateImmune) || enemy.MaxAP != 6 {
		t.Errorf("dispel: buffs=%d fireRes=%d immune=%v maxAP=%d want 0/0/false/6",
			len(enemy.Buffs), enemy.Stats.resFlat[elemFire], enemy.hasState(stateImmune), enemy.MaxAP)
	}
}

// TestDispelKeepsInnateSummonProperties is the bug: dispel cleared the whole
// state map, so one cast permanently stripped a summon of what it IS. Of the 53
// shipped creatures 22 are rooted, 21 anchored, 18 stabilised and 15
// intransposable, all applied at spawn as INFINITE states — so a dispel used to
// make a stationary summon mobile, or a carry-proof one carryable, for the rest
// of the fight.
//
// The buff half of dispel always kept permanent entries; the state half now
// agrees, as does tickStates, which never ages a state at >= infiniteStateTurns.
func TestDispelKeepsInnateSummonProperties(t *testing.T) {
	f, caster, enemy := stateTestFight()

	// What a summon looks like after applySummonInnateProperties.
	applySummonInnateProperties(enemy, &gamedata.Summoning{
		Rooted: true, CannotBeCarried: true, Stabilised: true, Intransposable: true,
	})
	// Plus a genuine, finite enchantment that SHOULD be stripped.
	f.resolveEffect(caster, gamedata.Effect{ActionID: 124, Duration: []int32{3, 0}}, enemy.Pos) // immunity
	if !enemy.hasState(stateImmune) {
		t.Fatal("setup: the finite state was not applied")
	}

	f.resolveEffect(caster, gamedata.Effect{ActionID: 62}, enemy.Pos)

	if enemy.hasState(stateImmune) {
		t.Error("dispel left the finite state (immunity) in place")
	}
	for _, s := range []struct {
		name  string
		state fighterState
	}{
		{"rooted", stateRooted},
		{"anchored", stateAnchored},
		{"stabilized", stateStabilized},
		{"intransposable", stateIntransposable},
	} {
		if !enemy.hasState(s.state) {
			t.Errorf("dispel stripped the summon's innate %s property", s.name)
		}
	}
}

// TestDispelKeepsAnInfiniteStateOnARealFighter: the rule is about PERMANENCE,
// not about being a summon. A Masqueraider's mask (173/174/175) is an infinite
// state on an ordinary fighter and survives for the same reason its infinite
// buff half already did.
func TestDispelKeepsAnInfiniteStateOnARealFighter(t *testing.T) {
	f, caster, enemy := stateTestFight()
	// Duration 63 = infinite (the same marker the shipped condition rows use).
	f.resolveEffect(caster, gamedata.Effect{ActionID: 173, Duration: []int32{63, 0}}, enemy.Pos)
	if !enemy.hasState(stateMaskClass) {
		t.Fatal("setup: the infinite mask state was not applied")
	}

	f.resolveEffect(caster, gamedata.Effect{ActionID: 62}, enemy.Pos)

	if !enemy.hasState(stateMaskClass) {
		t.Error("dispel stripped an infinite (permanent) state from a real fighter")
	}
}
