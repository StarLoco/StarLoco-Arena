package main

import (
	"encoding/json"
	"testing"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// The JSON bulk workflow's core promise: export a .dat to JSON and import it
// back UNCHANGED must reproduce the file byte-for-byte. This exercises the full
// path the App uses -- parse -> json.MarshalIndent -> json.Unmarshal(generic)
// -> remarshal(struct) -> encode -- so it also catches float32 precision or
// number-formatting drift introduced by round-tripping through `any`.

// jsonRoundTrip marshals value to JSON, decodes it generically (as the App's
// ImportRecordsJSON does), remarshals into a fresh struct of type T, and
// returns it for re-encoding.
func jsonRoundTrip[T any](t *testing.T, value any) T {
	t.Helper()
	doc, err := json.MarshalIndent(value, "", "  ")
	if err != nil {
		t.Fatalf("marshal: %v", err)
	}
	var generic any
	if err := json.Unmarshal(doc, &generic); err != nil {
		t.Fatalf("unmarshal generic: %v", err)
	}
	var out T
	if err := remarshal(generic, &out); err != nil {
		t.Fatalf("remarshal: %v", err)
	}
	return out
}

func TestJSONRoundTrip_Spells(t *testing.T) {
	raw := realData(t, "spells.dat")
	f, err := parser.ParseSpellsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	out := jsonRoundTrip[parser.SpellsFile](t, f)
	if got := encode.EncodeSpellsFile(out); !bytesEqual(raw, got) {
		t.Errorf("spells.dat JSON round-trip changed bytes (orig=%d got=%d)", len(raw), len(got))
	}
}

func TestJSONRoundTrip_Cards(t *testing.T) {
	raw := realData(t, "cards.dat")
	f, err := parser.ParseCardsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	out := jsonRoundTrip[parser.CardsFile](t, f)
	if got := encode.EncodeCardsFile(out); !bytesEqual(raw, got) {
		t.Errorf("cards.dat JSON round-trip changed bytes (orig=%d got=%d)", len(raw), len(got))
	}
}

func TestJSONRoundTrip_Events(t *testing.T) {
	raw := realData(t, "events.dat")
	f, err := parser.ParseEventsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	out := jsonRoundTrip[parser.EventsFile](t, f)
	if got := encode.EncodeEventsFile(out); !bytesEqual(raw, got) {
		t.Errorf("events.dat JSON round-trip changed bytes (orig=%d got=%d)", len(raw), len(got))
	}
}

func TestJSONRoundTrip_Summonings(t *testing.T) {
	raw := realData(t, "summoning.dat")
	rows, err := parser.ParseSummoningFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	out := jsonRoundTrip[[]parser.SummoningRaw](t, rows)
	if got := encode.EncodeSummoningFile(out); !bytesEqual(raw, got) {
		t.Errorf("summoning.dat JSON round-trip changed bytes (orig=%d got=%d)", len(raw), len(got))
	}
}

func TestJSONRoundTrip_StaticEffects(t *testing.T) {
	raw := realData(t, "staticEffects.dat")
	f, err := parser.ParseStaticEffectsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	out := jsonRoundTrip[parser.StaticEffectsFile](t, f)
	if got := encode.EncodeStaticEffectsFile(out); !bytesEqual(raw, got) {
		t.Errorf("staticEffects.dat JSON round-trip changed bytes (orig=%d got=%d)", len(raw), len(got))
	}
}
