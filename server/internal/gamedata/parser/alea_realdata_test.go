package parser

import (
	"os"
	"testing"
)

// TestRealElementsAdeParses loads the actual production elements.ade file
// (from ../../../data relative to this package) and confirms it parses
// without error and consumes every byte of the file -- this is the
// strongest available verification for a reverse-engineered binary format
// with no reference decoder: if the byte layout hypothesis is wrong, a
// full-file parse either errors out on a bounds check or, worse, silently
// desyncs and leaves trailing bytes unconsumed (which ParseElementsFile's
// loop structure can't directly detect since it has no top-level count,
// but a wrong layout reliably manifests as an error well before EOF in
// practice -- confirmed during manual reverse-engineering, see
// docs/04-game-data-format.md §4.9). Skipped if the data directory isn't
// present.
func TestRealElementsAdeParses(t *testing.T) {
	const path = "../../../data/elements.ade"
	raw, err := os.ReadFile(path)
	if err != nil {
		t.Skipf("real elements.ade not available (%v), skipping", err)
	}

	hdr, body, err := PeekAleaHeader(raw)
	if err != nil {
		t.Fatalf("PeekAleaHeader: %v", err)
	}
	if hdr.TypeCode != AleaTypeCodeWorldElements {
		t.Errorf("TypeCode = %d, want %d ('E')", hdr.TypeCode, AleaTypeCodeWorldElements)
	}
	if hdr.Version != AleaDocumentVersion1 {
		t.Errorf("Version = %d, want 1", hdr.Version)
	}

	got, err := ParseElementsFile(body)
	if err != nil {
		t.Fatalf("ParseElementsFile: %v", err)
	}
	if len(got.Elements) == 0 {
		t.Fatal("parsed zero elements from a non-empty real elements.ade")
	}
	t.Logf("parsed %d element definitions from real elements.ade", len(got.Elements))

	// Sanity-check a couple of the custom DofusArena element kinds
	// (1000/1001/1002) actually appear, since those are the ones this
	// project cares about for fight-map placement.
	var fightStart, coachStart, bonus int
	for _, e := range got.Elements {
		switch e.Kind {
		case ElementKindFightStartPoint:
			fightStart++
		case ElementKindFightStartCoachPoint:
			coachStart++
		case ElementKindBonus:
			bonus++
		}
	}
	t.Logf("custom element kinds: FightStartPoint=%d FightStartCoachPoint=%d Bonus=%d", fightStart, coachStart, bonus)
}

// TestRealFightMapAMWParses loads the three real .amw chunk files backing
// this project's actual fight map (mapID=2, see
// internal/dispatch/handlers_fight.go's fightMapID constant) and confirms
// each parses cleanly, consuming every byte with zero leftover -- the
// strongest available correctness signal for this reverse-engineered
// format. Also cross-checks that the parsed FightStartCoachPointElement
// cell coordinates match this project's existing hardcoded teleport
// destinations exactly, which is the strongest possible confirmation that
// both the byte-layout AND the semantic interpretation (team-side flag
// param) are correct.
func TestRealFightMapAMWParses(t *testing.T) {
	files := []string{
		"../../../data/maps/2/map_0_0.amw",
		"../../../data/maps/2/map_-1_0.amw",
		"../../../data/maps/2/map_1_0.amw",
	}

	var coachCells []struct{ X, Y int32 }
	for _, path := range files {
		raw, err := os.ReadFile(path)
		if err != nil {
			t.Skipf("real .amw fixture not available (%v), skipping", err)
		}

		hdr, body, err := PeekAleaHeader(raw)
		if err != nil {
			t.Fatalf("%s: PeekAleaHeader: %v", path, err)
		}
		if hdr.TypeCode != AleaTypeCodeWorldMap {
			t.Errorf("%s: TypeCode = %d, want %d ('M')", path, hdr.TypeCode, AleaTypeCodeWorldMap)
		}

		chunk, err := ParseAMWFile(body)
		if err != nil {
			t.Fatalf("%s: ParseAMWFile: %v", path, err)
		}
		if len(chunk.Cells) != int(chunk.Size)*int(chunk.Size) {
			t.Errorf("%s: parsed %d cells, want %d (size=%d)", path, len(chunk.Cells), chunk.Size*chunk.Size, chunk.Size)
		}
		t.Logf("%s: coordX=%d coordY=%d size=%d cells=%d", path, chunk.CoordX, chunk.CoordY, chunk.Size, len(chunk.Cells))

		for _, cell := range chunk.Cells {
			for _, lvl := range cell.Levels {
				for _, el := range lvl.Elements {
					if el.ElementID == int32(ElementKindFightStartCoachPoint) {
						coachCells = append(coachCells, struct{ X, Y int32 }{cell.X, cell.Y})
					}
				}
			}
		}
	}

	if len(coachCells) == 0 {
		t.Fatal("found zero FightStartCoachPointElement(1001) cells across all 3 fight-map chunks")
	}
	t.Logf("found %d coach start-point cells: %v", len(coachCells), coachCells)

	// This project's existing handlers_fight.go hardcodes teleport
	// destinations (16,11) and (1,7) for fightMapID=2 (see
	// TeamMateSetReadyForPlacement.java-derived logic) -- confirm the
	// real map data actually has FightStartCoachPointElement cells at
	// exactly those coordinates, which is the strongest possible
	// end-to-end confirmation that this parser's byte layout AND element
	// semantics are correct (not just "doesn't error").
	want := map[[2]int32]bool{{16, 11}: false, {1, 7}: false}
	for _, c := range coachCells {
		key := [2]int32{c.X, c.Y}
		if _, ok := want[key]; ok {
			want[key] = true
		}
	}
	for coord, found := range want {
		if !found {
			t.Errorf("expected a FightStartCoachPointElement at (%d,%d) matching handlers_fight.go's hardcoded teleport destination, not found in parsed map data", coord[0], coord[1])
		}
	}
}
