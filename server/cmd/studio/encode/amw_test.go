package encode

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// TestRoundTrip_AMW_AllRealMaps parses every real .amw chunk under
// data/maps/**/ and asserts EncodeAMWFile(parse(f)) == f byte-for-byte. This
// is the safety foundation for the map editor / jar repack (Phase 7): we only
// ever write back a chunk whose encoder faithfully reproduces the original.
func TestRoundTrip_AMW_AllRealMaps(t *testing.T) {
	mapsDir := filepath.Join(realDataDir(t), "maps")
	entries, err := os.ReadDir(mapsDir)
	if err != nil {
		t.Skipf("no maps dir: %v", err)
	}

	checked := 0
	for _, dirent := range entries {
		if !dirent.IsDir() {
			continue
		}
		mapDir := filepath.Join(mapsDir, dirent.Name())
		files, err := os.ReadDir(mapDir)
		if err != nil {
			continue
		}
		for _, fe := range files {
			if fe.IsDir() || filepath.Ext(fe.Name()) != ".amw" {
				continue
			}
			p := filepath.Join(mapDir, fe.Name())
			raw, err := os.ReadFile(p)
			if err != nil {
				t.Fatalf("read %s: %v", p, err)
			}
			header, body, err := parser.PeekAleaHeader(raw)
			if err != nil {
				t.Fatalf("header %s: %v", p, err)
			}
			chunk, err := parser.ParseAMWFile(body)
			if err != nil {
				t.Fatalf("parse %s: %v", p, err)
			}
			got := EncodeAMWFile(header, chunk)
			assertByteExact(t, filepath.Join(dirent.Name(), fe.Name()), raw, got)
			checked++
		}
	}
	if checked == 0 {
		t.Skip("no .amw files found")
	}
	t.Logf("byte-exact round-trip verified for %d real .amw chunks", checked)
}
