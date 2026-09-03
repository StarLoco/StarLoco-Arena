package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestTargetConditionPassesAppliesTheStateBank covers the WIRING, not the helper.
//
// A mutation that stopped targetConditionPasses from consulting
// stateConditionRejects survived my first tests, because they called the helper
// directly. Testing the predicate is not testing the caller - the same mistake
// this work has now made five times.
func TestTargetConditionPassesAppliesTheStateBank(t *testing.T) {
	caster := &FightFighter{HP: 50, MaxHP: 100, TeamID: 0, WireID: 1}
	target := &FightFighter{HP: 100, MaxHP: 100, TeamID: 1, WireID: 2}

	// condIsEnemy alone: the target qualifies.
	if !targetConditionPasses(caster, target, condIsEnemy) {
		t.Fatal("fixture broken: an enemy should satisfy condIsEnemy")
	}
	// Add the "already at full HP" reject bit and it must stop qualifying.
	if targetConditionPasses(caster, target, condIsEnemy|condTargetAtFullHP) {
		t.Error("a full-HP target passed a condition carrying the full-HP reject " +
			"bit: the state bank is not wired into targetConditionPasses")
	}
	// Drop it below full and it qualifies again, proving the bit - not something
	// else - was doing the work.
	target.HP = 99
	if !targetConditionPasses(caster, target, condIsEnemy|condTargetAtFullHP) {
		t.Error("a damaged target was rejected by the full-HP bit")
	}
}

// TestThrowRefusesDestroyedCell and TestCloseCombatRefusesDestroyedCell cover the
// effect-level cell checks. Sudden death REMOVES cells and the client has no
// concept of that, so these are the server's rules alone.
func TestThrowRefusesDestroyedCell(t *testing.T) {
	f, caster, victim := summonTestFight()
	caster.Pos = Pos{X: 7, Y: 15}
	victim.Pos = Pos{X: 7, Y: 16}

	// Set up a carry so applyThrow has something to throw.
	caster.CarriedFighter = victim
	victim.CarriedByFighter = caster
	before := victim.Pos

	dest := Pos{X: 8, Y: 15}
	f.destroyedCells = map[[2]int32]bool{{dest.X, dest.Y}: true}
	f.applyThrow(caster, gamedata.Effect{ActionID: 70}, dest)

	if victim.Pos != before {
		t.Errorf("carried fighter was thrown onto a destroyed cell: %v -> %v",
			before, victim.Pos)
	}
	if caster.CarriedFighter == nil {
		t.Error("the carry was released even though the throw was refused")
	}

	// A LIVE cell must still work, or the guard is refusing everything.
	f.destroyedCells = nil
	f.applyThrow(caster, gamedata.Effect{ActionID: 70}, dest)
	if victim.Pos == before {
		t.Error("a throw onto a valid cell was refused; the test proves nothing")
	}
}
