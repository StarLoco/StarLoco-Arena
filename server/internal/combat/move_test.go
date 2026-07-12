package combat

import (
	"testing"

	"github.com/dofusarena/go-server/internal/protocol"
)

// TestFighterMoveWirePathIncludesStartCell verifies the fix for a reported
// "movement teleports / looks weird / sometimes doesn't move" bug: the
// FIGHTER_MOVE (4524) broadcast must include the fighter's CURRENT cell as
// the first path entry, because the client's move animator treats path[0]
// as the origin and interpolates from it. The A* path itself excludes the
// start cell (used for MP cost / trap checks), so handleFighterMove
// prepends it for the wire. See §8.21 of docs/08-java-parity-roadmap.md.
func TestFighterMoveWirePathIncludesStartCell(t *testing.T) {
	bc := newFakeBroadcaster()
	f, a, b := twoTeamFight(t, bc)
	_ = b
	// Drive to the action phase deterministically without the clocks: set
	// the phase directly and pin A as the current fighter.
	f.setPhase(PhaseAction)
	f.Timeline = NewTimeline([]*Fighter{a, b})
	f.Timeline.StartNextTurn() // A (higher INIT) becomes current

	a.Position = Point3{X: 5, Y: 5, Z: 0}
	a.Characteristics[MP].Value = 6
	a.Characteristics[MP].Max = 6

	// Move A one cell in the SOUTH_EAST direction, whose client grid vector
	// is single-axis (+1, 0) -- a legal fight move (Point3.Step /
	// fightMoveDirections). Two-axis moves are forbidden: the client would
	// resolve them to a cardinal facing and render the fighter invisible.
	// handleFighterMove re-derives the path via A* (no real map data
	// attached -> the stub walkability accepts any cell), so the resolved A*
	// path is [dest]; the wire path must be [start, dest].
	dest := Point3{X: 6, Y: 5, Z: 0}
	f.handleFighterMove(cmdFighterMove{
		RequesterCoachID: a.CoachID,
		FighterID:        a.ID,
		Path:             []Point3{dest},
	})

	frame, ok := bc.lastFrame(a.CoachID, protocol.SendFighterMove)
	if !ok {
		t.Fatal("no FIGHTER_MOVE broadcast produced")
	}

	// Decode: 8-byte header, int64 fighterId, then repeated (int32 x,y;
	// int16 z).
	r := protocol.NewReader(frame.Payload)
	r.Int32() // uniqueId
	r.Int32() // triggeringId
	gotFighter := r.Int64()
	if gotFighter != a.ID {
		t.Errorf("FIGHTER_MOVE fighterId = %d, want %d", gotFighter, a.ID)
	}

	var cells []Point3
	for r.Remaining() >= 10 {
		cells = append(cells, Point3{X: r.Int32(), Y: r.Int32(), Z: r.Int16()})
	}
	if r.Err() != nil {
		t.Fatalf("unexpected read error: %v", r.Err())
	}

	if len(cells) < 2 {
		t.Fatalf("FIGHTER_MOVE path has %d cells, want >= 2 (start + dest)", len(cells))
	}
	if cells[0].X != 5 || cells[0].Y != 5 {
		t.Errorf("FIGHTER_MOVE path[0] = (%d,%d), want the START cell (5,5) so the client animates from the origin", cells[0].X, cells[0].Y)
	}
	last := cells[len(cells)-1]
	if last.X != 6 || last.Y != 5 {
		t.Errorf("FIGHTER_MOVE path[last] = (%d,%d), want the destination (6,5)", last.X, last.Y)
	}

	// MP must have been debited by the A* path cost (1 cell), NOT counting
	// the prepended start cell.
	if got := a.Characteristic(MP); got != 5 {
		t.Errorf("MP after 1-cell move = %d, want 5 (start cell must not cost MP)", got)
	}
}
