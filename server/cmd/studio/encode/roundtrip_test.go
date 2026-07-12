package encode

import (
	"bytes"
	"os"
	"path/filepath"
	"testing"

	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// realDataDir locates the repo's data/ dir relative to this test package
// (server/cmd/studio/encode -> ../../../data), skipping if absent.
func realDataDir(t *testing.T) string {
	t.Helper()
	wd, err := os.Getwd()
	if err != nil {
		t.Fatalf("getwd: %v", err)
	}
	dir := filepath.Join(wd, "..", "..", "..", "data")
	if _, err := os.Stat(filepath.Join(dir, "spells.dat")); err != nil {
		t.Skipf("real data dir not found at %s; skipping", dir)
	}
	return dir
}

func readReal(t *testing.T, name string) []byte {
	t.Helper()
	raw, err := os.ReadFile(filepath.Join(realDataDir(t), name))
	if err != nil {
		t.Fatalf("read %s: %v", name, err)
	}
	return raw
}

// assertByteExact reports the first differing offset (with context) if the
// re-encoded bytes don't match the original -- far more useful than a bare
// "not equal".
func assertByteExact(t *testing.T, name string, orig, got []byte) {
	t.Helper()
	if bytes.Equal(orig, got) {
		return
	}
	if len(orig) != len(got) {
		t.Errorf("%s: length mismatch: orig=%d got=%d", name, len(orig), len(got))
	}
	n := len(orig)
	if len(got) < n {
		n = len(got)
	}
	for i := 0; i < n; i++ {
		if orig[i] != got[i] {
			lo := i - 8
			if lo < 0 {
				lo = 0
			}
			hi := i + 8
			if hi > n {
				hi = n
			}
			t.Errorf("%s: first byte diff at offset %d: orig=0x%02x got=0x%02x\n  orig[%d:%d]=% x\n  got [%d:%d]=% x",
				name, i, orig[i], got[i], lo, hi, orig[lo:hi], lo, hi, got[lo:hi])
			return
		}
	}
}

func TestRoundTrip_Spells(t *testing.T) {
	raw := readReal(t, "spells.dat")
	f, err := parser.ParseSpellsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	assertByteExact(t, "spells.dat", raw, EncodeSpellsFile(f))
}

func TestRoundTrip_Events(t *testing.T) {
	raw := readReal(t, "events.dat")
	f, err := parser.ParseEventsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	assertByteExact(t, "events.dat", raw, EncodeEventsFile(f))
}

func TestRoundTrip_Summoning(t *testing.T) {
	raw := readReal(t, "summoning.dat")
	rows, err := parser.ParseSummoningFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	assertByteExact(t, "summoning.dat", raw, EncodeSummoningFile(rows))
}

func TestRoundTrip_StaticEffects(t *testing.T) {
	raw := readReal(t, "staticEffects.dat")
	f, err := parser.ParseStaticEffectsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	assertByteExact(t, "staticEffects.dat", raw, EncodeStaticEffectsFile(f))
}

func TestRoundTrip_Cards(t *testing.T) {
	raw := readReal(t, "cards.dat")
	f, err := parser.ParseCardsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	assertByteExact(t, "cards.dat", raw, EncodeCardsFile(f))
}
