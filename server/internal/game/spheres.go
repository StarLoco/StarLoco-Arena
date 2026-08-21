package game

import (
	"sort"

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

// --- What a bought sphere actually does -------------------------------------
//
// The client applies a purchase to its own copy of the fighter the moment it is
// made (`ee_2.a`): it moves the cursor, debits the experience, adds the node to
// the owned list and then, for a NEW node only, appends the node's spell and its
// equipment pool and runs every one of its effect rows onto the fighter.
//
// It does NOT re-derive any of that on the next login. `ee_2` fills its three
// lists straight from the blob - `aRD = NE()` (owned nodes), `aRE = NI()` (spells)
// and `aRF = NJ()` (equipment pools) - and resolves aRE through the spell table
// and aRF through the equipment-pool table. So those two lists are the server's to
// send, and the "passive"/"passiveSet" slots the evolution tail already reserved
// are exactly where they go. Sent empty, as they were, a Spell sphere bought in
// one session silently stopped existing in the next.
//
// The effects are a different matter: nothing sends a fighter's characteristics on
// the wire, because the server is authoritative for the fight. They are re-derived
// here at fight time, the same way equipped cards and wounds already are.

// ownedSpheres resolves the fighter's bought node ids to their definitions,
// skipping any the data no longer knows.
func ownedSpheres(f *domain.Fighter, boards *gamedata.SphereBoards) []*gamedata.Sphere {
	if f == nil || boards == nil {
		return nil
	}
	out := make([]*gamedata.Sphere, 0, len(f.Spheres))
	for _, owned := range f.Spheres {
		if sp := boards.Sphere(owned.SphereID); sp != nil {
			out = append(out, sp)
		}
	}
	return out
}

// sphereFightEffects returns every effect row the fighter's bought nodes carry.
//
// These are `Ht` rows - the same structure spells, cards and wounds use - so they
// feed the existing passive-effect accumulator rather than needing a mechanism of
// their own. A malus node's rows are in here too: the board deliberately sells
// penalties ("un sacrifice qu'un combattant doit faire"), and dropping them would
// hand the player the upside of a bargain without its cost.
func sphereFightEffects(f *domain.Fighter, boards *gamedata.SphereBoards) []gamedata.Effect {
	var out []gamedata.Effect
	for _, sp := range ownedSpheres(f, boards) {
		out = append(out, sp.Effects...)
	}
	return out
}

// sphereSpellIDs returns the spells the fighter's bought nodes unlock, in
// ascending order and without duplicates.
func sphereSpellIDs(f *domain.Fighter, boards *gamedata.SphereBoards) []int32 {
	seen := map[int32]bool{}
	var out []int32
	for _, sp := range ownedSpheres(f, boards) {
		if sp.SpellID != 0 && !seen[sp.SpellID] {
			seen[sp.SpellID] = true
			out = append(out, sp.SpellID)
		}
	}
	sort.Slice(out, func(i, j int) bool { return out[i] < out[j] })
	return out
}

// sphereEquipmentPools returns the equipment pools (record type 251) the bought
// nodes unlock, ascending and deduplicated.
func sphereEquipmentPools(f *domain.Fighter, boards *gamedata.SphereBoards) []int32 {
	seen := map[int32]bool{}
	var out []int32
	for _, sp := range ownedSpheres(f, boards) {
		if sp.EquipmentPoolID != 0 && !seen[sp.EquipmentPoolID] {
			seen[sp.EquipmentPoolID] = true
			out = append(out, sp.EquipmentPoolID)
		}
	}
	sort.Slice(out, func(i, j int) bool { return out[i] < out[j] })
	return out
}

// fighterWithSphereSpells returns the fighter to FIGHT with: the same record, plus
// the spells its bought nodes unlock.
//
// A copy, because the spell list it returns must not be written back to the
// persisted fighter - a sphere spell is derived from the board every time, and
// baking it into fighter_spells would make it survive even if the node were
// somehow lost, and duplicate on every load. The copy is shallow: everything the
// fight reads besides Spells is shared deliberately.
func fighterWithSphereSpells(f *domain.Fighter, boards *gamedata.SphereBoards) *domain.Fighter {
	extra := sphereSpellIDs(f, boards)
	if f == nil || len(extra) == 0 {
		return f
	}
	known := make(map[int32]bool, len(f.Spells))
	for _, sp := range f.Spells {
		known[sp.SpellID] = true
	}
	out := *f
	out.Spells = make([]domain.FighterSpell, len(f.Spells), len(f.Spells)+len(extra))
	copy(out.Spells, f.Spells)
	for _, id := range extra {
		if known[id] {
			continue
		}
		out.Spells = append(out.Spells, domain.FighterSpell{FighterID: f.ID, SpellID: id})
	}
	return &out
}
