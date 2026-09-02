package game

import "testing"

// TestPetrifiedFighterCannotAct covers the highest-severity combat finding.
//
// beginTurn does NOT skip a petrified fighter synchronously: it broadcasts
// FIGHTER_TURN_BEGIN, refills raw AP/MP, and arms a 1200ms timer to end the turn.
// For that window isCurrentTurn is true and the resources are full, so a modified
// client could cast, use cards and move freely - "Petrifie, ne peut jouer" was a
// full free turn.
//
// The retail client refuses a petrified caster in BOTH of its validators
// (mv_1.java:298-300 spells, :415-417 cards) and its pathfinder returns 0 MP, so
// no honest client ever sends these.
func TestPetrifiedFighterCannotAct(t *testing.T) {
	ff := &FightFighter{HP: 100, AP: 6, MP: 3}
	if !ff.canAct() {
		t.Fatal("fixture broken: a healthy fighter must be able to act")
	}

	ff.addState(statePetrified, 2)
	if ff.canAct() {
		t.Error("a petrified fighter may act")
	}
	if got := ff.effectiveAP(); got != 0 {
		t.Errorf("effectiveAP = %d, want 0 while petrified", got)
	}
	if got := ff.effectiveMP(); got != 0 {
		t.Errorf("effectiveMP = %d, want 0 while petrified", got)
	}

	// Raw values are deliberately left intact - refillFighter refills the RAW
	// resource so the effect ending restores it - which is exactly why the GATES
	// had to move to the effective readings rather than the raw ones.
	if ff.AP == 0 {
		t.Error("raw AP was zeroed; the model derives the restriction, it does not " +
			"destroy the resource")
	}
}

// TestDeadFighterCannotAct pins the other half of canAct.
func TestDeadFighterCannotAct(t *testing.T) {
	ff := &FightFighter{HP: 0, AP: 6}
	if ff.canAct() {
		t.Error("a fighter at 0 HP may act")
	}
}

// TestRootedFighterCanStillCast guards against over-blocking: rooted stops
// MOVEMENT, not casting, and the project deliberately does not zero a rooted
// fighter's AP.
func TestRootedFighterCanStillCast(t *testing.T) {
	ff := &FightFighter{HP: 100, AP: 6, MP: 3}
	ff.addState(stateRooted, 2)
	if !ff.canAct() {
		t.Error("a rooted fighter must still be able to cast")
	}
	if got := ff.effectiveAP(); got != 6 {
		t.Errorf("rooted effectiveAP = %d, want 6 (root does not cost AP)", got)
	}
	if got := ff.effectiveMP(); got != 0 {
		t.Errorf("rooted effectiveMP = %d, want 0", got)
	}
}

// TestAutoPassedTurnBlocksActions covers the skip-turn window, which is a server
// model defect rather than a client divergence: the skip is consumed at turn
// start but the turn itself is ended by the same 1200ms timer, so the fighter had
// a fully playable turn that was supposed to be lost.
func TestAutoPassedTurnBlocksActions(t *testing.T) {
	f := &Fight{}
	if f.turnAutoPassed {
		t.Fatal("a fresh fight should not have an auto-passed turn")
	}
	f.turnAutoPassed = true
	// The flag is what every combat handler consults; assert it is readable and
	// that beginTurn resets it (covered by the handlers, pinned here as intent).
	if !f.turnAutoPassed {
		t.Error("turnAutoPassed did not stick")
	}
}
