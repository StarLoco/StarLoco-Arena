package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// FORCED DISPLACEMENT ARMS TRAPS.
//
// checkEffectAreasMove used to have exactly ONE caller — the walk path — so
// push, pull, teleport, swap and throw all repositioned fighters straight past
// every trap on the board. Shoving someone onto a glyph is a core tactic, and
// its absence also made traps trivially avoidable.
//
// The client settles it. `he_1.a(fromX,fromY,fromZ, toX,toY,toZ, fighter)`
// partitions the live areas by whether they contain the FROM cell and the TO
// cell, then fires 10001 on the ones newly entered, 10008 on the ones the
// fighter stayed inside, and 10002 on the ones it left. It is a pure
// position-change notification — it does not care how the fighter got there —
// and eight distinct effect classes call it, including `go` (teleport) and
// `aox_1` (swap, which calls it once per swapped fighter). Our
// checkEffectAreasMove already implements the 10001 half of that partition
// exactly: `contains(arrival) && !contains(start)`.

// pushTrapFight is trapTestFight with the caster/enemy placed so the enemy can
// be shoved along the y axis onto a trap.
func trapAt(f *Fight, caster *FightFighter, cell Pos) {
	f.resolveEffect(caster, gamedata.Effect{ActionID: 66, Params: []float32{1}}, cell)
}

func TestTeleportArmsTrap(t *testing.T) {
	f, caster, _ := trapTestFight(walkOnTrap())
	trapAt(f, caster, Pos{X: 9, Y: 15})

	// The caster teleports onto its own trap (action 39 moves the CASTER).
	f.resolveEffect(caster, gamedata.Effect{ActionID: 39}, Pos{X: 9, Y: 15})

	if caster.Pos.X != 9 || caster.Pos.Y != 15 {
		t.Fatalf("teleport did not move the caster: %v", caster.Pos)
	}
	if caster.HP != 55 {
		t.Errorf("caster HP = %d, want 55 (teleported onto a 20-damage trap)", caster.HP)
	}
}

func TestSwapArmsTrapForBothFighters(t *testing.T) {
	f, caster, enemy := trapTestFight(walkOnTrap(), &gamedata.StaticEffect{
		ID: 2, Type: "TRAP", AreaShape: 1, MaxExec: 63,
		AppTriggers: []int32{trapTriggerWalkOn},
		Effects:     []gamedata.Effect{{ActionID: 1, Params: []float32{20}}},
	})
	// A trap on EACH fighter's destination: the swap moves both, so both must
	// be checked — the client notifies once per swapped fighter.
	caster.Pos = Pos{X: 7, Y: 15}
	enemy.Pos = Pos{X: 9, Y: 15}
	trapAt(f, caster, Pos{X: 7, Y: 15}) // caster's cell -> enemy lands here
	trapAt(f, caster, Pos{X: 9, Y: 15}) // enemy's cell  -> caster lands here

	f.resolveEffect(caster, gamedata.Effect{ActionID: 64}, Pos{X: 9, Y: 15})

	if caster.Pos.X != 9 || enemy.Pos.X != 7 {
		t.Fatalf("swap did not exchange cells: caster=%v enemy=%v", caster.Pos, enemy.Pos)
	}
	if caster.HP != 55 {
		t.Errorf("caster HP = %d, want 55 (swapped onto a trap)", caster.HP)
	}
	if enemy.HP != 50 {
		t.Errorf("enemy HP = %d, want 50 (swapped onto a trap)", enemy.HP)
	}
}

func TestPushArmsTrap(t *testing.T) {
	f, caster, enemy := trapTestFight(walkOnTrap())
	caster.Pos = Pos{X: 7, Y: 15}
	enemy.Pos = Pos{X: 8, Y: 15}
	trapAt(f, caster, Pos{X: 9, Y: 15}) // one cell beyond the enemy

	// Push (37) shoves the enemy along the caster->target axis, onto the trap.
	f.resolveEffect(caster, gamedata.Effect{ActionID: 37, Params: []float32{1}}, enemy.Pos)

	if enemy.Pos.X != 9 {
		t.Fatalf("push did not move the enemy: %v", enemy.Pos)
	}
	if enemy.HP != 50 {
		t.Errorf("enemy HP = %d, want 50 (pushed onto a 20-damage trap)", enemy.HP)
	}
}

func TestThrowArmsTrap(t *testing.T) {
	f, caster, enemy := trapTestFight(walkOnTrap())
	caster.Pos = Pos{X: 7, Y: 15}
	enemy.Pos = Pos{X: 8, Y: 15}
	trapAt(f, caster, Pos{X: 9, Y: 15})

	// Carry (58) then throw (59) onto the trap cell.
	f.resolveEffect(caster, gamedata.Effect{ActionID: 58}, enemy.Pos)
	if caster.CarriedFighter != enemy {
		t.Fatal("carry did not link the fighters")
	}
	f.resolveEffect(caster, gamedata.Effect{ActionID: 59}, Pos{X: 9, Y: 15})

	if enemy.Pos.X != 9 || enemy.Pos.Y != 15 {
		t.Fatalf("throw did not land the fighter on the target cell: %v", enemy.Pos)
	}
	if enemy.HP != 50 {
		t.Errorf("enemy HP = %d, want 50 (thrown onto a 20-damage trap)", enemy.HP)
	}
}

// TestDisplacementWithinOneTrapDoesNotRefire guards the half of the client's
// partition that is easy to get wrong: a fighter shoved from one cell to another
// INSIDE the same area has not "entered" it. The client fires trigger 10008
// (stayed inside) for that case, not 10001 — and 10008 is not implemented here,
// so the correct behaviour is that nothing fires at all rather than the walk-on
// effect firing twice.
func TestDisplacementWithinOneTrapDoesNotRefire(t *testing.T) {
	big := &gamedata.StaticEffect{
		ID: 1, Type: "TRAP", AreaShape: 2, AreaSize: []int32{3}, MaxExec: 63,
		AppTriggers: []int32{trapTriggerWalkOn},
		Effects:     []gamedata.Effect{{ActionID: 1, Params: []float32{20}}},
	}
	f, caster, enemy := trapTestFight(big)
	caster.Pos = Pos{X: 7, Y: 15}
	trapAt(f, caster, Pos{X: 9, Y: 15}) // radius-3 circle centred at (9,15)

	// The enemy starts INSIDE the footprint and is pushed one cell further in.
	enemy.Pos = Pos{X: 8, Y: 15}
	before := enemy.HP
	f.checkEffectAreasMove(Pos{X: 8, Y: 15}, Pos{X: 9, Y: 15}, enemy)
	if enemy.HP != before {
		t.Errorf("area re-fired for a move INSIDE it: HP %d -> %d", before, enemy.HP)
	}
}

// TestLethalTrapOnPushEndsTheFight walks the whole consequence chain: a shove
// arms a trap, the trap kills the last enemy, and the fight ends.
//
// This is the case that could not happen before — no trap test had ever reached
// endFight, because only a voluntary walk could spring a trap and the walker was
// never the last enemy in these fixtures. It immediately exposed that endFight
// dereferences deps.Log unguarded (a nil logger panicked the fight actor).
func TestLethalTrapOnPushEndsTheFight(t *testing.T) {
	f, caster, enemy := trapTestFight(walkOnTrap())
	caster.Pos = Pos{X: 7, Y: 15}
	enemy.Pos = Pos{X: 8, Y: 15}
	enemy.HP = 15 // less than the trap's 20
	trapAt(f, caster, Pos{X: 9, Y: 15})

	f.resolveEffect(caster, gamedata.Effect{ActionID: 37, Params: []float32{1}}, enemy.Pos)

	if enemy.HP > 0 {
		t.Fatalf("enemy survived a 20-damage trap on %d HP", enemy.HP)
	}
	if f.Phase() != PhaseEnded {
		t.Errorf("phase = %v, want Ended: the last enemy died in the trap", f.Phase())
	}
}
