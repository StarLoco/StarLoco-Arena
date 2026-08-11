package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestResolveEffect verifies each single-target spell effect the resolver
// handles: flat damage, heal (capped), AP/MP loss (clamped), and AP/MP steal
// (target loses, caster gains). Broadcasts are no-ops here (no sessions).
func TestResolveEffect(t *testing.T) {
	newFight := func() (*Fight, *FightFighter, *FightFighter) {
		caster := &FightFighter{WireID: 1, Pos: Pos{X: 5, Y: 5}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
		victim := &FightFighter{WireID: 2, Pos: Pos{X: 6, Y: 5}, HP: 50, MaxHP: 80, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
		f := &Fight{Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{caster}},
			{ID: 1, Fighters: []*FightFighter{victim}},
		}}
		return f, caster, victim
	}
	ef := func(action int32, v float32) gamedata.Effect {
		return gamedata.Effect{ActionID: action, Params: []float32{v}}
	}

	// Flat neutral damage (action 1).
	f, caster, victim := newFight()
	f.resolveEffect(caster, ef(1, 20), victim.Pos)
	if victim.HP != 30 {
		t.Errorf("damage: HP=%d want 30", victim.HP)
	}

	// "Par sort" fire damage (action 131) also resolves as flat damage.
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(131, 25), victim.Pos)
	if victim.HP != 25 {
		t.Errorf("par-sort damage: HP=%d want 25", victim.HP)
	}

	// Heal (action 69) capped at MaxHP (50 -> 80, not 150).
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(69, 100), victim.Pos)
	if victim.HP != 80 {
		t.Errorf("heal: HP=%d want 80 (capped at MaxHP)", victim.HP)
	}

	// AP loss (16): target only.
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(16, 2), victim.Pos)
	if victim.AP != 4 || caster.AP != 6 {
		t.Errorf("AP loss: victim=%d caster=%d want 4/6", victim.AP, caster.AP)
	}

	// MP loss (20).
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(20, 1), victim.Pos)
	if victim.MP != 2 {
		t.Errorf("MP loss: MP=%d want 2", victim.MP)
	}

	// AP steal (85): victim -2, caster +2.
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(85, 2), victim.Pos)
	if victim.AP != 4 || caster.AP != 8 {
		t.Errorf("AP steal: victim=%d caster=%d want 4/8", victim.AP, caster.AP)
	}

	// MP steal (103): victim -1, caster +1.
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(103, 1), victim.Pos)
	if victim.MP != 2 || caster.MP != 4 {
		t.Errorf("MP steal: victim=%d caster=%d want 2/4", victim.MP, caster.MP)
	}

	// Over-drain clamps at 0, never negative.
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(16, 10), victim.Pos)
	if victim.AP != 0 {
		t.Errorf("AP over-drain: AP=%d want 0", victim.AP)
	}

	// Lethal damage sets HP to exactly 0 (death handled by broadcast).
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(1, 100), victim.Pos)
	if victim.HP != 0 {
		t.Errorf("lethal: HP=%d want 0", victim.HP)
	}

	// No fighter on the target cell → silent no-op (must not panic).
	f, caster, _ = newFight()
	f.resolveEffect(caster, ef(1, 20), Pos{X: 99, Y: 99})
	f.resolveEffect(caster, ef(69, 20), Pos{X: 99, Y: 99})
}

// TestResolveEffectHPVariants covers leech (damage + caster heal), %HP damage,
// instant death, and AP/MP gain.
func TestResolveEffectHPVariants(t *testing.T) {
	ef := func(action int32, v float32) gamedata.Effect {
		return gamedata.Effect{ActionID: action, Params: []float32{v}}
	}
	newFight := func() (*Fight, *FightFighter, *FightFighter) {
		caster := &FightFighter{WireID: 1, Pos: Pos{X: 5, Y: 5}, HP: 40, MaxHP: 70, AP: 4, MaxAP: 6, MP: 2, MaxMP: 3}
		victim := &FightFighter{WireID: 2, Pos: Pos{X: 6, Y: 5}, HP: 50, MaxHP: 80, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
		f := &Fight{Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{caster}},
			{ID: 1, Fighters: []*FightFighter{victim}},
		}}
		return f, caster, victim
	}

	// Leech (action 8): victim loses 20, caster heals 20 (40 -> 60).
	f, caster, victim := newFight()
	f.resolveEffect(caster, ef(8, 20), victim.Pos)
	if victim.HP != 30 || caster.HP != 60 {
		t.Errorf("leech: victim=%d caster=%d want 30/60", victim.HP, caster.HP)
	}

	// Leech capped at target's HP: victim has 10, leech 25 → victim 0, caster +10.
	f, caster, victim = newFight()
	victim.HP = 10
	caster.HP = 40
	f.resolveEffect(caster, ef(8, 25), victim.Pos)
	if victim.HP != 0 || caster.HP != 50 {
		t.Errorf("leech cap: victim=%d caster=%d want 0/50", victim.HP, caster.HP)
	}

	// %HP (125): 25% of MaxHP(80) = 20 damage.
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(125, 25), victim.Pos)
	if victim.HP != 30 {
		t.Errorf("%%HP: HP=%d want 30", victim.HP)
	}

	// Instant death (63): HP -> 0 regardless of value.
	f, caster, victim = newFight()
	f.resolveEffect(caster, ef(63, 0), victim.Pos)
	if victim.HP != 0 {
		t.Errorf("instant death: HP=%d want 0", victim.HP)
	}

	// AP gain (15) on self (target cell = caster): 4 -> 6 (capped at MaxAP).
	f, caster, _ = newFight()
	f.resolveEffect(caster, ef(15, 5), caster.Pos)
	if caster.AP != 6 {
		t.Errorf("AP gain: AP=%d want 6 (capped)", caster.AP)
	}

	// MP gain (19) on self: 2 -> 3 (capped at MaxMP).
	f, caster, _ = newFight()
	f.resolveEffect(caster, ef(19, 5), caster.Pos)
	if caster.MP != 3 {
		t.Errorf("MP gain: MP=%d want 3 (capped)", caster.MP)
	}
}

// TestResolveEffectPositioning covers teleport, swap, push and pull on the real
// practice arena (so walkability/altitude are exercised).
func TestResolveEffectPositioning(t *testing.T) {
	ef := func(action int32, v float32) gamedata.Effect {
		p := []float32{v}
		if action == 39 || action == 64 { // teleport/swap are param-less
			p = nil
		}
		return gamedata.Effect{ActionID: action, Params: p}
	}
	// Row y=15 of the practice arena: x=5..14 are walkable floor at altitude 0.
	newFight := func(cx, cy, vx, vy int32) (*Fight, *FightFighter, *FightFighter) {
		caster := &FightFighter{WireID: 1, Pos: Pos{X: cx, Y: cy}, HP: 70, MaxHP: 70, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
		victim := &FightFighter{WireID: 2, Pos: Pos{X: vx, Y: vy}, HP: 60, MaxHP: 60, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
		f := &Fight{Teams: [2]*FightTeam{
			{ID: 0, Fighters: []*FightFighter{caster}},
			{ID: 1, Fighters: []*FightFighter{victim}},
		}}
		return f, caster, victim
	}

	// Teleport (39): caster moves to the targeted (empty, walkable) cell.
	f, caster, _ := newFight(7, 15, 8, 15)
	dest := Pos{X: 12, Y: 15}
	f.resolveEffect(caster, ef(39, 0), dest)
	if caster.Pos.X != 12 || caster.Pos.Y != 15 {
		t.Errorf("teleport: caster at (%d,%d) want (12,15)", caster.Pos.X, caster.Pos.Y)
	}
	// Teleport onto void is rejected (caster stays).
	f, caster, _ = newFight(7, 15, 8, 15)
	f.resolveEffect(caster, ef(39, 0), Pos{X: 0, Y: 0})
	if caster.Pos.X != 7 {
		t.Errorf("teleport to void moved caster to (%d,%d)", caster.Pos.X, caster.Pos.Y)
	}

	// Swap (64): caster (7,15) <-> victim (9,15).
	f, caster, victim := newFight(7, 15, 9, 15)
	f.resolveEffect(caster, ef(64, 0), victim.Pos)
	if caster.Pos.X != 9 || victim.Pos.X != 7 {
		t.Errorf("swap: caster.x=%d victim.x=%d want 9/7", caster.Pos.X, victim.Pos.X)
	}

	// Push (37) distance 2: caster (7,15) pushes victim (8,15) → (10,15), clear.
	f, caster, victim = newFight(7, 15, 8, 15)
	f.resolveEffect(caster, ef(37, 2), victim.Pos)
	if victim.Pos.X != 10 || victim.HP != 60 {
		t.Errorf("push clear: victim at (%d,%d) hp=%d want (10,15)/60", victim.Pos.X, victim.Pos.Y, victim.HP)
	}

	// Push into the wall: victim at (13,15), push 3 east; x=15 is void, so it
	// moves (13->14) one cell then takes collision damage for the 2 blocked cells
	// (2*6=12).
	f, caster, victim = newFight(7, 15, 13, 15)
	f.resolveEffect(caster, ef(37, 3), victim.Pos)
	if victim.Pos.X != 14 {
		t.Errorf("push wall: victim.x=%d want 14", victim.Pos.X)
	}
	if victim.HP != 48 {
		t.Errorf("push wall collision: hp=%d want 48 (60-12)", victim.HP)
	}

	// Pull (38) distance 3: caster (7,15) pulls victim (11,15) toward it, stopping
	// adjacent to the caster at (8,15) (no collision damage on a caster stop).
	f, caster, victim = newFight(7, 15, 11, 15)
	f.resolveEffect(caster, ef(38, 3), victim.Pos)
	if victim.Pos.X != 8 || victim.HP != 60 {
		t.Errorf("pull: victim at (%d,%d) hp=%d want (8,15)/60", victim.Pos.X, victim.Pos.Y, victim.HP)
	}
}

// TestBuffLifecycle covers a finite resource buff (AP boost applied then reverted
// on expiry), an infinite buff (applied permanently, untracked), and a pure-stat
// buff (tracked but no resource change).
func TestBuffLifecycle(t *testing.T) {
	mk := func() (*Fight, *FightFighter) {
		ff := &FightFighter{WireID: 1, Pos: Pos{X: 5, Y: 5}, HP: 50, MaxHP: 50, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
		f := &Fight{Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{ff}}}}
		return f, ff
	}
	buff := func(action int32, v float32, dur int32) gamedata.Effect {
		return gamedata.Effect{ActionID: action, Params: []float32{v}, Duration: []int32{dur, 0}}
	}

	// Finite AP boost (13) +2 for 2 turns: MaxAP 6->8, AP 6->8.
	f, ff := mk()
	f.resolveEffect(ff, buff(13, 2, 2), ff.Pos)
	if ff.MaxAP != 8 || ff.AP != 8 || len(ff.Buffs) != 1 {
		t.Fatalf("AP boost apply: MaxAP=%d AP=%d buffs=%d want 8/8/1", ff.MaxAP, ff.AP, len(ff.Buffs))
	}
	f.tickBuffs() // turnsLeft 2->1, still active
	if ff.MaxAP != 8 || len(ff.Buffs) != 1 {
		t.Fatalf("AP boost after 1 tick: MaxAP=%d buffs=%d want 8/1", ff.MaxAP, len(ff.Buffs))
	}
	f.tickBuffs() // turnsLeft 1->0, expires and reverts
	if ff.MaxAP != 6 || ff.AP != 6 || len(ff.Buffs) != 0 {
		t.Fatalf("AP boost expiry: MaxAP=%d AP=%d buffs=%d want 6/6/0", ff.MaxAP, ff.AP, len(ff.Buffs))
	}

	// Infinite HP boost (11, dur 63) +25: MaxHP/HP +25, now TRACKED and flagged
	// infinite (so action 149 can strip it) but never aged/reverted by tickBuffs.
	f, ff = mk()
	f.resolveEffect(ff, buff(11, 25, 63), ff.Pos)
	if ff.MaxHP != 75 || ff.HP != 75 || len(ff.Buffs) != 1 || !ff.Buffs[0].infinite {
		t.Fatalf("infinite HP boost: MaxHP=%d HP=%d buffs=%d want 75/75/1(infinite)", ff.MaxHP, ff.HP, len(ff.Buffs))
	}
	f.tickBuffs()
	if ff.MaxHP != 75 || len(ff.Buffs) != 1 {
		t.Errorf("infinite HP boost after tick: MaxHP=%d buffs=%d want 75/1 (permanent)", ff.MaxHP, len(ff.Buffs))
	}

	// Elemental stat buff (82 all-damage%, dur 1): now MECHANICAL — applied to the
	// fighter's combat stats and reverted exactly on expiry.
	f, ff = mk()
	f.resolveEffect(ff, buff(82, 15, 1), ff.Pos)
	if ff.Stats.dmgPctAll != 15 || len(ff.Buffs) != 1 || !ff.Buffs[0].statBuff || ff.Buffs[0].delta != 15 {
		t.Fatalf("elemental buff: dmgPctAll=%d buffs=%d want dmgPctAll15 / 1 elemental buff", ff.Stats.dmgPctAll, len(ff.Buffs))
	}
	f.tickBuffs()
	if ff.Stats.dmgPctAll != 0 || len(ff.Buffs) != 0 {
		t.Errorf("elemental buff expiry: dmgPctAll=%d buffs=%d want 0/0", ff.Stats.dmgPctAll, len(ff.Buffs))
	}

	// Render-only stat buff (155 damage/resistance bluff, dur 1): tracked so the
	// count matches the client, but no modelled stat change (delta 0, not
	// elemental). NB action 120 is NO LONGER render-only — it is the block %
	// (Lr.brd) that drives tackle, so it now moves a real characteristic.
	f, ff = mk()
	f.resolveEffect(ff, buff(155, 15, 1), ff.Pos)
	if len(ff.Buffs) != 1 || ff.Buffs[0].delta != 0 || ff.Buffs[0].statBuff {
		t.Fatalf("render-only buff: buffs=%d (want 1 with delta 0, non-statBuff)", len(ff.Buffs))
	}
	f.tickBuffs()
	if len(ff.Buffs) != 0 {
		t.Errorf("render-only buff expiry: buffs=%d want 0", len(ff.Buffs))
	}
}

// TestBuffsStackAndExpireIndependently locks in the stacking semantics against a
// future "optimisation" into merge/refresh/cap. The retail client STACKS: every
// running effect it receives is filed in the per-fighter registry alf_1 under a
// key from xb_2.ahT(), a monotonic counter, so two casts can never collide and
// the registry's duplicate guard never fires. Its only removal helpers are bulk
// by-source (dispel/death/fight-end); there is no same-effect eviction anywhere.
// So a blind append is the wire-correct behaviour, and repeat-casting is bounded
// by cast frequency (cooldown / max-per-turn / max-per-target), not by merging.
//
// Two casts of DIFFERENT magnitude and DIFFERENT duration, so the test also
// fails if a revert ever credits the wrong instance's delta.
func TestBuffsStackAndExpireIndependently(t *testing.T) {
	ff := &FightFighter{WireID: 1, Pos: Pos{X: 5, Y: 5}, HP: 50, MaxHP: 50, AP: 6, MaxAP: 6, MP: 3, MaxMP: 3}
	f := &Fight{Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{ff}}}}
	buff := func(action int32, v float32, dur int32) gamedata.Effect {
		return gamedata.Effect{ActionID: action, Params: []float32{v}, Duration: []int32{dur, 0}}
	}

	// +2 AP for 3 turns, then +3 AP for 1 turn: both live, both counted.
	f.resolveEffect(ff, buff(13, 2, 3), ff.Pos)
	f.resolveEffect(ff, buff(13, 3, 1), ff.Pos)
	if len(ff.Buffs) != 2 || ff.MaxAP != 11 || ff.AP != 11 {
		t.Fatalf("two casts: buffs=%d MaxAP=%d AP=%d want 2/11/11 (stacked, not merged)",
			len(ff.Buffs), ff.MaxAP, ff.AP)
	}

	// Tick 1: only the 1-turn +3 expires, and it must give back exactly 3.
	f.tickBuffs()
	if len(ff.Buffs) != 1 || ff.MaxAP != 8 || ff.AP != 8 {
		t.Fatalf("after tick 1: buffs=%d MaxAP=%d AP=%d want 1/8/8 (only the +3 reverted)",
			len(ff.Buffs), ff.MaxAP, ff.AP)
	}

	// Ticks 2-3: the 3-turn +2 ages out on its own schedule, back to the base.
	f.tickBuffs()
	if len(ff.Buffs) != 1 || ff.MaxAP != 8 {
		t.Fatalf("after tick 2: buffs=%d MaxAP=%d want 1/8 (+2 still running)", len(ff.Buffs), ff.MaxAP)
	}
	f.tickBuffs()
	if len(ff.Buffs) != 0 || ff.MaxAP != 6 || ff.AP != 6 {
		t.Fatalf("after tick 3: buffs=%d MaxAP=%d AP=%d want 0/6/6 (fully unwound)",
			len(ff.Buffs), ff.MaxAP, ff.AP)
	}

	// The mechanical stat path stacks too: two damage-% buffs sum, and each
	// reverts its own rolled value.
	ff2 := &FightFighter{WireID: 2, Pos: Pos{X: 6, Y: 6}, HP: 50, MaxHP: 50, AP: 6, MaxAP: 6}
	f2 := &Fight{Teams: [2]*FightTeam{{ID: 0, Fighters: []*FightFighter{ff2}}}}
	f2.resolveEffect(ff2, buff(82, 15, 2), ff2.Pos)
	f2.resolveEffect(ff2, buff(82, 10, 1), ff2.Pos)
	if len(ff2.Buffs) != 2 || ff2.Stats.dmgPctAll != 25 {
		t.Fatalf("two stat buffs: buffs=%d dmgPctAll=%d want 2/25", len(ff2.Buffs), ff2.Stats.dmgPctAll)
	}
	f2.tickBuffs()
	if len(ff2.Buffs) != 1 || ff2.Stats.dmgPctAll != 15 {
		t.Fatalf("stat buff tick 1: buffs=%d dmgPctAll=%d want 1/15 (only the +10 reverted)",
			len(ff2.Buffs), ff2.Stats.dmgPctAll)
	}
	f2.tickBuffs()
	if len(ff2.Buffs) != 0 || ff2.Stats.dmgPctAll != 0 {
		t.Fatalf("stat buff tick 2: buffs=%d dmgPctAll=%d want 0/0", len(ff2.Buffs), ff2.Stats.dmgPctAll)
	}
}

func TestCardinalStep(t *testing.T) {
	cases := []struct{ dx, dy, wx, wy int32 }{
		{2, 0, 1, 0}, {-3, 0, -1, 0}, {0, 2, 0, 1}, {0, -4, 0, -1},
		{3, 1, 1, 0}, {1, 3, 0, 1}, {-2, -1, -1, 0}, {0, 0, 0, 0},
	}
	for _, c := range cases {
		gx, gy := cardinalStep(c.dx, c.dy)
		if gx != c.wx || gy != c.wy {
			t.Errorf("cardinalStep(%d,%d) = (%d,%d), want (%d,%d)", c.dx, c.dy, gx, gy, c.wx, c.wy)
		}
	}
}
