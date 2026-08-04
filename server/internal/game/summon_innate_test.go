package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestSummonInnatePropertiesApplied is the regression guard for B-060: a creature
// whose type-300 template says it is immovable must actually be immovable. Before
// this, every summon in the game could be pushed, swapped or carried.
func TestSummonInnatePropertiesApplied(t *testing.T) {
	wall := &gamedata.Summoning{
		ID: 6, HP: 40, AP: 0, MP: 0,
		CannotBeCarried: true, Intransposable: true, Stabilised: true, Rooted: true,
	}
	summon := &FightFighter{WireID: 9, TeamID: 0, Pos: Pos{X: 8, Y: 15},
		HP: 40, MaxHP: 40, MP: 3, MaxMP: 3}
	applySummonInnateProperties(summon, wall)

	if !summon.hasState(stateAnchored) || !summon.hasState(stateIntransposable) ||
		!summon.hasState(stateStabilized) || !summon.hasState(stateRooted) {
		t.Fatalf("wall summon did not get its innate properties: %s", summon.stateSummary())
	}
	if summon.MP != 0 || summon.MaxMP != 0 {
		t.Errorf("a rooted summon kept MP %d/%d, want 0/0", summon.MP, summon.MaxMP)
	}

	// And they must survive a round tick — they are innate, not a 1-turn buff.
	f, caster, _ := stateTestFight()
	f.Teams[0].Fighters = append(f.Teams[0].Fighters, summon)
	f.tickStates()
	if !summon.hasState(stateStabilized) {
		t.Error("an innate property expired after one round")
	}

	// Enforcement: it resists push and carry.
	before := summon.Pos
	f.applyPushPull(caster, gamedata.Effect{ActionID: 37, Params: []float32{3}}, summon.Pos, true)
	if summon.Pos != before {
		t.Errorf("an innately stabilised summon was pushed: %v -> %v", before, summon.Pos)
	}
	f.applyCarry(caster, gamedata.Effect{ActionID: 58}, summon.Pos)
	if caster.CarriedFighter != nil {
		t.Error("an innately anchored summon was carried")
	}

	// A creature with no flags keeps its mobility.
	plain := &FightFighter{WireID: 10, TeamID: 0, Pos: Pos{X: 9, Y: 14}, HP: 20, MaxHP: 20, MP: 3, MaxMP: 3}
	applySummonInnateProperties(plain, &gamedata.Summoning{ID: 1, HP: 20, MP: 3})
	if len(plain.States) != 0 || plain.MP != 3 {
		t.Errorf("a flagless summon was restricted: %s mp=%d", plain.stateSummary(), plain.MP)
	}
}
