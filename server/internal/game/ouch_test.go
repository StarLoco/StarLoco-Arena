package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestOuchOnlyForFightersThatLostHP: the "ouch !" bubble (4902) names exactly one
// fighter, so it can only mean the one that took the hit - never the attacker.
//
// Server policy: a fighter says ouch when it LOSES HP to a CRITICAL cast. Both
// halves are load-bearing, and each has its own case below.
func TestOuchOnlyForFightersThatLostHP(t *testing.T) {
	f, victim, bystander := ouchFight()

	before := f.hpSnapshot()
	if _, ok := before[victim.WireID]; !ok {
		t.Fatal("fixture: the victim is missing from the snapshot, so this test " +
			"could not tell a working filter from a broken one")
	}

	// Only the victim loses HP.
	victim.HP -= 5

	got := ouchTargets(t, f, before)
	if len(got) != 1 || got[0] != victim.WireID {
		t.Errorf("ouch went to %v, want exactly the damaged fighter [%d] - the "+
			"bystander at full HP must not say ouch", got, victim.WireID)
	}
	_ = bystander
}

// TestOuchNotSentForZeroDamageCrit: a critical cast that deals no damage - a
// debuff, a fully absorbed hit - must produce no bubble. Without this, "crit"
// alone would trigger it and fighters would yelp at buffs.
func TestOuchNotSentForZeroDamageCrit(t *testing.T) {
	f := buildTestFight()
	before := f.hpSnapshot()
	if got := ouchTargets(t, f, before); len(got) != 0 {
		t.Errorf("a crit that changed no HP produced ouch for %v, want none", got)
	}
}

// TestOuchNotSentForHealing guards the comparison direction: a critical HEAL
// raises HP, and a fighter must not say ouch for being healed.
func TestOuchNotSentForHealing(t *testing.T) {
	f := buildTestFight()
	before := f.hpSnapshot()
	f.allFighters()[0].HP += 5
	if got := ouchTargets(t, f, before); len(got) != 0 {
		t.Errorf("a critical heal produced ouch for %v, want none - the HP "+
			"comparison must be strictly 'lost', not 'changed'", got)
	}
}

// ouchTargets runs the broadcast and returns the fighter ids named by the 4902
// frames it produced.
func ouchTargets(t *testing.T, f *Fight, before map[int64]int32) []int64 {
	t.Helper()
	drain := captureFight(t, f)
	f.broadcastOuchForDamaged(before)

	var out []int64
	for _, frame := range drain() {
		if len(frame) < 4 || uint16(frame[2])<<8|uint16(frame[3]) != protocol.OpFighterOuch {
			continue
		}
		r := protocol.NewReader(frame[4:])
		_, _ = r.I32()
		_, _ = r.I32()
		id, err := r.I64()
		if err != nil {
			t.Fatalf("decode 4902: %v", err)
		}
		out = append(out, id)
	}
	return out
}

// ouchFight builds a minimal two-fighter fight: one that will take damage and a
// bystander at full HP, so "only the damaged fighter" is actually testable.
func ouchFight() (*Fight, *FightFighter, *FightFighter) {
	victim := &FightFighter{WireID: 1, TeamID: 0, Pos: Pos{X: 5, Y: 15}, HP: 70, MaxHP: 70}
	bystander := &FightFighter{WireID: 2, TeamID: 1, Pos: Pos{X: 6, Y: 15}, HP: 60, MaxHP: 60}
	f := &Fight{Teams: [2]*FightTeam{
		{ID: 0, Fighters: []*FightFighter{victim}},
		{ID: 1, Fighters: []*FightFighter{bystander}},
	}}
	return f, victim, bystander
}
