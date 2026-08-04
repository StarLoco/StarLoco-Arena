package main

import (
	"archive/zip"
	"fmt"
	"path"
	"path/filepath"
	"testing"
)

// arena5Topo is the server's hand-decoded world-5 topology (internal/game/
// arena.go), row-major idx=y*18+x, 32767 = void. Our tplg decoder must reproduce
// it exactly from tplg/5.jar!/0_0 (a type-2 tile).
var arena5Topo = [18 * 18]int16{
	32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767,
	32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 4, 6, 4, 32767, 10, 32767, 32767, 32767, 32767,
	32767, 32767, 32767, 32767, 32767, 32767, 3, 3, 3, 3, 3, 0, 0, 0, 0, 32767, 32767, 32767,
	32767, 32767, 32767, 32767, 32767, 6, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 32767, 32767,
	32767, 32767, 32767, 32767, 14, 6, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 1, 32767,
	32767, 32767, 32767, 6, 6, 6, 3, 3, 3, 3, 32767, 0, 0, 0, 0, 0, -3, 32767,
	32767, 32767, 16, 6, 32767, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, -3,
	32767, 32767, 6, 32767, 32767, 32767, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, -3, -3,
	32767, 32767, 32767, 32767, 32767, 8, 0, 0, 32767, 32767, 32767, 32767, 0, 0, 0, 32767, -3, -3,
	32767, 32767, 32767, 32767, 32767, 10, 0, 0, 0, 32767, 32767, 32767, 0, 0, 32767, 32767, -3, 32767,
	32767, 32767, 32767, 32767, 32767, 8, 32767, 0, 0, 0, 0, 9, 0, 0, 0, 32767, -3, 32767,
	32767, 32767, 32767, 32767, 13, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, 32767,
	32767, 32767, 32767, 32767, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, 32767,
	32767, 32767, 32767, 32767, 12, 3, 0, 8, 0, 32767, 0, 0, 0, 0, 0, 0, 5, 32767,
	32767, 32767, 32767, 32767, 3, 3, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 32767, 32767,
	32767, 32767, 32767, 32767, 32767, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32767, 32767, 32767,
	32767, 32767, 32767, 32767, 32767, 32767, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32767, 32767, 32767,
	32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 32767, 10, 32767, 32767, 32767, 32767, 32767,
}

// readZipEntryByName returns the bytes of a named entry (base name) in a zip.
func readZipEntryByName(t *testing.T, zpath, base string) []byte {
	t.Helper()
	r, err := zip.OpenReader(zpath)
	if err != nil {
		t.Fatalf("open %s: %v", zpath, err)
	}
	defer r.Close()
	for _, f := range r.File {
		if path.Base(f.Name) == base {
			b, rerr := readAll(f)
			if rerr != nil {
				t.Fatalf("read %s!%s: %v", zpath, base, rerr)
			}
			return b
		}
	}
	t.Fatalf("entry %q not found in %s", base, zpath)
	return nil
}

func mapsTestDir(t *testing.T) string {
	t.Helper()
	dir := filepath.Join("..", "..", "..", "client", "compiled", "game", "contents", "maps")
	if !isDir(dir) {
		t.Skipf("v2.70 client maps not found at %s; skipping", dir)
	}
	return dir
}

func TestDecodeTile_World5MatchesArena(t *testing.T) {
	dir := mapsTestDir(t)
	data := readZipEntryByName(t, filepath.Join(dir, "tplg", "5.jar"), "0_0")

	cells, err := decodeTile(data)
	if err != nil {
		t.Fatalf("decodeTile: %v", err)
	}
	// Build an 18x18 grid from the decoded (walkable) cells; default void.
	var got [18 * 18]int16
	for i := range got {
		got[i] = 32767
	}
	for _, c := range cells {
		if c.X < 0 || c.X >= 18 || c.Y < 0 || c.Y >= 18 {
			t.Fatalf("cell out of tile bounds: %+v", c)
		}
		got[c.Y*18+c.X] = c.Alt
	}
	mismatches := 0
	for i := 0; i < 18*18; i++ {
		if got[i] != arena5Topo[i] {
			if mismatches < 10 {
				t.Errorf("cell (x=%d,y=%d): got %d want %d", i%18, i/18, got[i], arena5Topo[i])
			}
			mismatches++
		}
	}
	if mismatches > 0 {
		t.Fatalf("%d/%d cells mismatched", mismatches, 18*18)
	}
}

func TestParseFmd_World5MatchesArena(t *testing.T) {
	dir := mapsTestDir(t)
	fd, err := parseFightJar(filepath.Join(dir, "fight", "5.jar"))
	if err != nil {
		t.Fatalf("parseFightJar: %v", err)
	}
	if fd == nil {
		t.Fatal("world 5 should be an arena (has .fmd)")
	}

	// Expected start cells from arena.go (compared as sets; order differs).
	wantTeam0 := cellSet([]fmdCell{
		{7, 15, 0}, {9, 15, 0}, {12, 15, 0}, {13, 15, 0}, {6, 16, 0}, {8, 16, 0}, {12, 16, 0}, {13, 16, 0},
	})
	wantTeam1 := cellSet([]fmdCell{
		{6, 2, 3}, {9, 2, 3}, {10, 2, 3}, {13, 2, 0}, {6, 3, 3}, {9, 3, 3}, {10, 3, 3}, {13, 3, 0},
	})
	if !setsEqual(cellSet(fd.Team0), wantTeam0) {
		t.Errorf("team0 = %v, want %v", fd.Team0, wantTeam0)
	}
	if !setsEqual(cellSet(fd.Team1), wantTeam1) {
		t.Errorf("team1 = %v, want %v", fd.Team1, wantTeam1)
	}
	// Coach pedestals: slot 0 = side 0, slot 1 = side 1.
	if len(fd.Coach) < 2 {
		t.Fatalf("expected >=2 coach slots, got %d", len(fd.Coach))
	}
	if c := fd.Coach[0]; c.X != 5 || c.Y != 9 || c.Z != 10 {
		t.Errorf("coach[0] = %+v, want {5 9 10}", c)
	}
	if c := fd.Coach[1]; c.X != 10 || c.Y != 1 || c.Z != 6 {
		t.Errorf("coach[1] = %+v, want {10 1 6}", c)
	}
}

func cellSet(cells []fmdCell) map[string]bool {
	m := make(map[string]bool, len(cells))
	for _, c := range cells {
		m[fmt.Sprintf("%d,%d,%d", c.X, c.Y, c.Z)] = true
	}
	return m
}

func setsEqual(a, b map[string]bool) bool {
	if len(a) != len(b) {
		return false
	}
	for k := range a {
		if !b[k] {
			return false
		}
	}
	return true
}

func TestApp_ListMaps_RealClient(t *testing.T) {
	dir := realClientDir(t)
	a := NewApp()
	a.SetClientDir(dir)
	maps, err := a.ListMaps()
	if err != nil {
		t.Fatalf("ListMaps: %v", err)
	}
	if len(maps) == 0 {
		t.Fatal("expected some worlds")
	}
	var world5 *MapInfo
	for i := range maps {
		if maps[i].ID == 5 {
			world5 = &maps[i]
		}
	}
	if world5 == nil {
		t.Fatal("world 5 missing from ListMaps")
	}
	if !world5.IsArena {
		t.Error("world 5 should be flagged as an arena")
	}

	dto, err := a.GetMap(5)
	if err != nil {
		t.Fatalf("GetMap(5): %v", err)
	}
	if len(dto.Cells) == 0 || len(dto.Team0) == 0 || len(dto.Team1) == 0 {
		t.Errorf("GetMap(5): cells=%d team0=%d team1=%d", len(dto.Cells), len(dto.Team0), len(dto.Team1))
	}
	if dto.CellWidth != 86 || dto.CellHeight != 43 || dto.ElevationUnit != 10 {
		t.Errorf("projection constants wrong: %+v", dto)
	}
}
