package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// testBoards builds a two-board catalogue: breed 3 has a root at (5,7) and a
// neighbour at (6,7); breed 9 has a root elsewhere so a mixed-up lookup shows up
// as the WRONG board rather than as no board.
func testBoards() *gamedata.SphereBoards {
	return gamedata.NewSphereBoards(
		[]*gamedata.SphereBoard{
			{ID: 21, Season: 1, Breed: 3, RootX: 5, RootY: 7},
			{ID: 23, Season: 1, Breed: 9, RootX: 2, RootY: 2},
		},
		[]*gamedata.Sphere{
			{ID: 100, BoardID: 21, X: 5, Y: 7},
			{ID: 101, BoardID: 21, X: 6, Y: 7},
			{ID: 200, BoardID: 23, X: 2, Y: 2},
		},
	)
}

func TestSphereCursorStartsANewFighterOnItsBoardRoot(t *testing.T) {
	boards := testBoards()
	f := &domain.Fighter{BreedID: 3}

	board, x, y := SphereCursor(f, boards)
	if board != 21 {
		t.Errorf("board = %d, want 21 (breed 3's board)", board)
	}
	if x != 5 || y != 7 {
		t.Errorf("cursor = (%d,%d), want the root (5,7)", x, y)
	}
}

func TestSphereCursorKeepsAStoredPosition(t *testing.T) {
	boards := testBoards()
	f := &domain.Fighter{BreedID: 3, SphereX: 6, SphereY: 7}

	board, x, y := SphereCursor(f, boards)
	if board != 21 || x != 6 || y != 7 {
		t.Errorf("got board %d cursor (%d,%d), want 21 (6,7) - a stored cursor must "+
			"survive, or every fighter is walked back to the root on each login",
			board, x, y)
	}
}

// A stored cell that names no node is what a board data revision would leave
// behind. The client selects X(cursor) when the Kanodo opens, so a dangling cell
// selects null; put the fighter back on the root instead.
func TestSphereCursorRepairsADanglingPosition(t *testing.T) {
	boards := testBoards()
	f := &domain.Fighter{BreedID: 3, SphereX: 99, SphereY: 99}

	board, x, y := SphereCursor(f, boards)
	if board != 21 || x != 5 || y != 7 {
		t.Errorf("got board %d cursor (%d,%d), want the root 21 (5,7)", board, x, y)
	}
}

// The client does akp_1.aW(id) with no nil check and dereferences the result, so
// a board id naming nothing is an NPE the moment the Kanodo opens. A breed with
// no board must be sent 0.
func TestSphereCursorSendsNoBoardForABreedThatHasNone(t *testing.T) {
	boards := testBoards()
	for _, f := range []*domain.Fighter{
		{BreedID: 7},                         // no board defined
		{BreedID: 7, SphereX: 5, SphereY: 7}, // stored cursor must not leak a board
		{BreedID: 127},                       // the sentinel breed is not playable
	} {
		board, x, y := SphereCursor(f, boards)
		if board != 0 || x != 0 || y != 0 {
			t.Errorf("breed %d: got board %d cursor (%d,%d), want all zero - the client "+
				"NPEs on a board id it cannot resolve", f.BreedID, board, x, y)
		}
	}
}

func TestSphereCursorWithoutDataIsZero(t *testing.T) {
	board, x, y := SphereCursor(&domain.Fighter{BreedID: 3}, nil)
	if board != 0 || x != 0 || y != 0 {
		t.Errorf("got board %d cursor (%d,%d), want zeroes when no data is loaded", board, x, y)
	}
}

// TestEvolutionTailCarriesTheBoardAndOwnedNodes reads the bytes back out of the
// blob, because the tail is positional: an extra or missing field here does not
// error, it silently shifts everything after it and the client's own parser
// downgrades the fighter to a non-evolution one, making it vanish from the roster.
func TestEvolutionTailCarriesTheBoardAndOwnedNodes(t *testing.T) {
	boards := testBoards()
	f := &domain.Fighter{
		ID: 1, BreedID: 3, Name: "F", Evolution: true, State: 1,
		XP: 40, TotalXP: 90, SphereX: 6, SphereY: 7,
		Spheres: []domain.FighterSphere{{SphereID: 100}, {SphereID: 101}},
	}
	blob := encodeFighterBlob(f, boards)

	// Walk to the evolution tail: skip the classic header and the two blobs.
	p := 1 + 2 + 1                   // type, budget, breed
	p += 1 + len(f.Name)             // name
	p += 1 + 1 + 1 + 1 + 1           // sex, colour marker, hair, skin, eye
	spellLen := int(beU16(blob[p:])) // spell blob
	p += 2 + spellLen
	cardLen := int(beU16(blob[p:])) // card blob
	p += 2 + cardLen

	if got := be32(blob[p:]); got != 21 {
		t.Fatalf("sphereBoardId = %d, want 21", got)
	}
	if got := be32(blob[p+4:]); got != 40 {
		t.Errorf("xp = %d, want 40", got)
	}
	if got := be32(blob[p+8:]); got != 90 {
		t.Errorf("totalXp = %d, want 90", got)
	}
	q := p + 12 + 3 // past tiredness, morale, state
	if x, y := beU16(blob[q:]), beU16(blob[q+2:]); x != 6 || y != 7 {
		t.Errorf("cursor = (%d,%d), want (6,7)", x, y)
	}
	n := beU16(blob[q+4:])
	if n != 2 {
		t.Fatalf("sphere count = %d, want 2", n)
	}
	if a, b := be32(blob[q+6:]), be32(blob[q+10:]); a != 100 || b != 101 {
		t.Errorf("owned = [%d %d], want [100 101]", a, b)
	}
}

func beU16(b []byte) uint16 { return uint16(b[0])<<8 | uint16(b[1]) }
