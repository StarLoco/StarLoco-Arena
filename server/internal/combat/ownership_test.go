package combat

import (
	"testing"
)

// These tests cover the fix for a reported bug where a coach could act on
// the OPPONENT's fighters -- e.g. send MOVE_TO_FREE_PLACEMENT for the other
// team's fighter id and the server would apply it. Every player-driven
// fighter command now runs through resolveOwnedFighter, which rejects the
// action unless the requesting coach owns the target fighter. See §8.18 of
// docs/08-java-parity-roadmap.md.
//
// In twoTeamFight: fighter a (id 1) is owned by coach 100, fighter b
// (id 2) by coach 200.

func TestResolveOwnedFighter_RejectsForeignFighter(t *testing.T) {
	f, a, b := newTestFightForEffects(t)

	// Coach 100 owns fighter a, not fighter b.
	if _, ok := f.resolveOwnedFighter(100, b.ID); ok {
		t.Error("coach 100 must NOT be able to resolve opponent fighter b (owned by coach 200)")
	}
	if got, ok := f.resolveOwnedFighter(100, a.ID); !ok || got != a {
		t.Error("coach 100 must be able to resolve its own fighter a")
	}
	// Coach 200 owns b, not a.
	if _, ok := f.resolveOwnedFighter(200, a.ID); ok {
		t.Error("coach 200 must NOT be able to resolve opponent fighter a (owned by coach 100)")
	}
	if got, ok := f.resolveOwnedFighter(200, b.ID); !ok || got != b {
		t.Error("coach 200 must be able to resolve its own fighter b")
	}
}

func TestResolveOwnedFighter_ZeroCoachIDBypassesCheck(t *testing.T) {
	f, a, b := newTestFightForEffects(t)

	// requesterCoachID 0 is the trusted-internal-caller sentinel: it
	// bypasses the ownership check so tests / server-driven paths still
	// work (no real coach ever has id 0).
	if got, ok := f.resolveOwnedFighter(0, a.ID); !ok || got != a {
		t.Error("requesterCoachID 0 should resolve any existing fighter (a)")
	}
	if got, ok := f.resolveOwnedFighter(0, b.ID); !ok || got != b {
		t.Error("requesterCoachID 0 should resolve any existing fighter (b)")
	}
}

func TestResolveOwnedFighter_UnknownFighter(t *testing.T) {
	f, _, _ := newTestFightForEffects(t)
	if _, ok := f.resolveOwnedFighter(100, 99999); ok {
		t.Error("resolveOwnedFighter must reject an unknown fighter id")
	}
	if _, ok := f.resolveOwnedFighter(0, 99999); ok {
		t.Error("resolveOwnedFighter must reject an unknown fighter id even for coach 0")
	}
}

// TestHandleMoveToFreePlacement_RejectsForeignFighter is the direct
// regression guard for the exact reported bug: during PLACEMENT, coach 100
// must not be able to move coach 200's fighter (id 2).
func TestHandleMoveToFreePlacement_RejectsForeignFighter(t *testing.T) {
	f, a, b := newTestFightForEffects(t)
	f.setPhase(PhasePlacement)

	origBPos := Point3{X: 5, Y: 5, Z: 0}
	b.Position = origBPos

	// Coach 100 (owner of a) tries to move b (owned by coach 200).
	f.handleMoveToFreePlacement(cmdMoveToFreePlacement{
		RequesterCoachID: 100,
		FighterID:        b.ID,
		Pos:              Point3{X: 8, Y: 8, Z: 0},
	})
	if b.Position != origBPos {
		t.Errorf("coach 100 moved opponent fighter b to %+v -- must have been rejected (b should stay at %+v)", b.Position, origBPos)
	}

	// Coach 100 moving its OWN fighter a is allowed.
	newAPos := Point3{X: 3, Y: 3, Z: 0}
	f.handleMoveToFreePlacement(cmdMoveToFreePlacement{
		RequesterCoachID: 100,
		FighterID:        a.ID,
		Pos:              newAPos,
	})
	if a.Position != newAPos {
		t.Errorf("coach 100 could not move its own fighter a: got %+v, want %+v", a.Position, newAPos)
	}
}
