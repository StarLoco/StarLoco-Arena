package game

import (
	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// spheres.go resolves a fighter's Sphere Board ("Kanodo") position for the wire.
//
// The board GRAPH is client-side data: `vj_2`/`dq_1` load types 900/901 out of
// the client's own files into `akp_1`, so the server never sends it. What the
// server owns is where the fighter STANDS and what it has BOUGHT, which ride in
// the evolution tail of the fighter blob.
//
// Getting the board id wrong is not a cosmetic error. The client does
// `Ei ei = (Ei)akp_1.aVO().aW(fighter.NH()); ei.fi(...)` with no nil check
// (afb_1.l), so an id naming no board is an NPE the moment the Kanodo is opened.
// A fighter with no board must therefore be sent 0, not a guess.

// SphereCursor resolves the board id and the 1-BASED cursor cell to send for a
// fighter.
//
// The cursor is stored, not derived, because it MOVES: buying a node walks the
// cursor onto it (`fi(aLe())` in the client's own purchase path) and a portal
// node jumps it elsewhere entirely. A fighter that has never been placed - or one
// whose stored cell no longer names a node, which is what a board data revision
// would cause - is put back on the board's root rather than left pointing at
// nothing, since the client selects `X(cursor)` on open and would otherwise
// select null.
func SphereCursor(f *domain.Fighter, boards *gamedata.SphereBoards) (boardID int32, x, y int16) {
	if f == nil || boards == nil {
		return 0, 0, 0
	}
	board := boards.BoardForBreed(f.BreedID)
	if board == nil {
		return 0, 0, 0
	}
	if f.SphereX != 0 && f.SphereY != 0 && boards.At(board.ID, f.SphereX, f.SphereY) != nil {
		return board.ID, f.SphereX, f.SphereY
	}
	root := boards.Root(board.ID)
	if root == nil {
		return board.ID, 0, 0
	}
	return board.ID, root.X, root.Y
}
