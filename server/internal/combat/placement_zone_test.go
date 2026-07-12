package combat

import "testing"

// Tests for the free-placement ZONE restriction in handleMoveToFreePlacement
// / isInPlacementZone: a fighter may only be repositioned during PLACEMENT
// onto one of its own team's FightStartPointElement cells (Map.FightStartCells
// keyed by TeamID-1). See phases.go.

// --- isInPlacementZone permissive fallbacks (no map data) ---------------

func TestIsInPlacementZone_PermissiveWithoutMapData(t *testing.T) {
	f, a, _ := newTestFightForEffects(t) // no map data attached
	if !f.isInPlacementZone(a, Point3{X: 999, Y: 999}) {
		t.Error("without map data, any cell must be permitted (permissive fallback)")
	}
}

// --- Real-map zone enforcement -----------------------------------------

func TestHandleMoveToFreePlacement_RejectsCellOutsideZone(t *testing.T) {
	f, m := realMapFight(t) // fightMapID 2, real FightStartCells
	// realMapFight builds on twoTeamFight: fighter a = TeamID 1 (side 0).
	a := f.Timeline.Order()[0]
	if a.TeamID != 1 {
		// Find the TeamID-1 fighter explicitly if order differs.
		for _, fr := range f.Timeline.Order() {
			if fr.TeamID == 1 {
				a = fr
				break
			}
		}
	}
	f.setPhase(PhasePlacement)

	zone := m.FightStartCells()[a.TeamID-1]
	if len(zone) == 0 {
		t.Skip("map 2 has no start cells for team side 0")
	}

	// A legal cell: the team's own start cell. Move there -> accepted.
	legal := zone[0]
	legalZ, _ := m.StandingAltitudeAt(legal[0], legal[1])
	f.handleMoveToFreePlacement(cmdMoveToFreePlacement{
		RequesterCoachID: a.CoachID,
		FighterID:        a.ID,
		Pos:              Point3{X: legal[0], Y: legal[1], Z: legalZ},
	})
	if a.Position.X != legal[0] || a.Position.Y != legal[1] {
		t.Fatalf("legal placement onto own start cell %v was rejected (fighter at %+v)", legal, a.Position)
	}

	// An illegal cell: a walkable cell that is NOT in the team's zone.
	// Find one by scanning the map for a walkable cell not in either team's
	// start set and not currently occupied.
	inZone := map[[2]int32]bool{}
	for _, cs := range m.FightStartCells() {
		for _, c := range cs {
			inZone[c] = true
		}
	}
	var illegal [2]int32
	found := false
	for _, cell := range m.Cells() {
		if inZone[[2]int32{cell.X, cell.Y}] {
			continue
		}
		z, ok := m.StandingAltitudeAt(cell.X, cell.Y)
		if !ok {
			continue
		}
		pos := Point3{X: cell.X, Y: cell.Y, Z: z}
		if f.IsOccupied(pos, a) {
			continue
		}
		illegal = [2]int32{cell.X, cell.Y}
		found = true
		break
	}
	if !found {
		t.Skip("could not find a walkable non-zone cell on map 2")
	}

	before := a.Position
	illegalZ, _ := m.StandingAltitudeAt(illegal[0], illegal[1])
	f.handleMoveToFreePlacement(cmdMoveToFreePlacement{
		RequesterCoachID: a.CoachID,
		FighterID:        a.ID,
		Pos:              Point3{X: illegal[0], Y: illegal[1], Z: illegalZ},
	})
	if a.Position != before {
		t.Errorf("placement onto out-of-zone walkable cell %v was accepted (fighter moved to %+v), must be rejected", illegal, a.Position)
	}
}

func TestHandleMoveToFreePlacement_AcceptsAllOwnZoneCells(t *testing.T) {
	f, m := realMapFight(t)
	var a *Fighter
	for _, fr := range f.Timeline.Order() {
		if fr.TeamID == 1 {
			a = fr
			break
		}
	}
	if a == nil {
		t.Skip("no team-1 fighter")
	}
	f.setPhase(PhasePlacement)

	zone := m.FightStartCells()[a.TeamID-1]
	if len(zone) == 0 {
		t.Skip("no start cells for team side 0")
	}
	// Every cell in the team's own zone must be an accepted placement (as
	// long as it isn't occupied by the other fighter).
	for _, cell := range zone {
		pos := Point3{X: cell[0], Y: cell[1]}
		if z, ok := m.StandingAltitudeAt(cell[0], cell[1]); ok {
			pos.Z = z
		}
		if f.IsOccupied(pos, a) {
			continue
		}
		if !f.isInPlacementZone(a, pos) {
			t.Errorf("own-zone cell %v reported as illegal placement", cell)
		}
	}
}
