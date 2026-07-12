package main

import (
	"os"
	"testing"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// These tests assert the encoders reproduce each real .dat file byte-for-byte,
// which is the safety gate every Save* method relies on (it refuses to write if
// the round-trip fails). If any of these break, the corresponding editor must
// not ship until the encoder is fixed.

func realData(t *testing.T, name string) []byte {
	t.Helper()
	p := "../../data/" + name
	raw, err := os.ReadFile(p)
	if err != nil {
		t.Skipf("real %s not available: %v", name, err)
	}
	return raw
}

func TestEncodeRoundTrip_Summoning(t *testing.T) {
	raw := realData(t, "summoning.dat")
	rows, err := parser.ParseSummoningFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	if got := encode.EncodeSummoningFile(rows); !bytesEqual(raw, got) {
		t.Errorf("summoning.dat round-trip mismatch (len orig=%d got=%d)", len(raw), len(got))
	}
}

func TestEncodeRoundTrip_Cards(t *testing.T) {
	raw := realData(t, "cards.dat")
	cf, err := parser.ParseCardsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	if got := encode.EncodeCardsFile(cf); !bytesEqual(raw, got) {
		t.Errorf("cards.dat round-trip mismatch (len orig=%d got=%d)", len(raw), len(got))
	}
}

func TestEncodeRoundTrip_StaticEffects(t *testing.T) {
	raw := realData(t, "staticEffects.dat")
	sf, err := parser.ParseStaticEffectsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	if got := encode.EncodeStaticEffectsFile(sf); !bytesEqual(raw, got) {
		t.Errorf("staticEffects.dat round-trip mismatch (len orig=%d got=%d)", len(raw), len(got))
	}
}

func bytesEqual(a, b []byte) bool {
	if len(a) != len(b) {
		return false
	}
	for i := range a {
		if a[i] != b[i] {
			return false
		}
	}
	return true
}
