package main

import (
	"encoding/json"
	"fmt"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file adds a power-user BULK JSON workflow: export any editable .dat
// record type to a full-fidelity JSON document, edit it in any external tool,
// and re-import it. Unlike the per-field editors (which touch only exposed
// scalars), JSON import replaces the ENTIRE parsed structure -- every field,
// including the engine-ignored "reserved" bytes the parser preserves -- so a
// no-op export->import round-trips the .dat byte-for-byte.
//
// Safety: import re-encodes the JSON, and the SAME guarded pipeline runs first
// on the CURRENT on-disk file (reparse -> byte-exact round-trip gate) so we
// only trust the encoder when it faithfully reproduces the live file. The
// import bytes are then written via the standard backup + atomic pipeline and
// the store is reloaded.

// jsonDatKinds is the set of record kinds the JSON bulk workflow supports, each
// mapping to its .dat file. cards.dat/staticEffects.dat/etc. are whole-file
// documents (records + their effects), so the JSON captures everything.
var jsonDatKinds = map[string]string{
	"spells":        "spells.dat",
	"cards":         "cards.dat",
	"events":        "events.dat",
	"summonings":    "summoning.dat",
	"staticEffects": "staticEffects.dat",
}

// parseAndGate reads the named .dat, parses it into the kind's structure, and
// verifies the encoder reproduces the current bytes exactly. Returns the raw
// parsed value (as any) and the re-encode function bound to it.
func (a *App) parseAndGate(kind string) (target string, value any, reencode func(any) ([]byte, error), err error) {
	name, ok := jsonDatKinds[kind]
	if !ok {
		return "", nil, nil, fmt.Errorf("unknown JSON record kind %q", kind)
	}
	target, orig, err := a.dataFile(name)
	if err != nil {
		return "", nil, nil, err
	}

	switch kind {
	case "spells":
		f, e := parser.ParseSpellsFile(orig)
		if e != nil {
			return "", nil, nil, fmt.Errorf("parse %s: %w", name, e)
		}
		if e := verifyRoundTrip(orig, encode.EncodeSpellsFile(f)); e != nil {
			return "", nil, nil, fmt.Errorf("refusing: %w", e)
		}
		return target, f, func(v any) ([]byte, error) {
			var out parser.SpellsFile
			if e := remarshal(v, &out); e != nil {
				return nil, e
			}
			return encode.EncodeSpellsFile(out), nil
		}, nil
	case "cards":
		f, e := parser.ParseCardsFile(orig)
		if e != nil {
			return "", nil, nil, fmt.Errorf("parse %s: %w", name, e)
		}
		if e := verifyRoundTrip(orig, encode.EncodeCardsFile(f)); e != nil {
			return "", nil, nil, fmt.Errorf("refusing: %w", e)
		}
		return target, f, func(v any) ([]byte, error) {
			var out parser.CardsFile
			if e := remarshal(v, &out); e != nil {
				return nil, e
			}
			return encode.EncodeCardsFile(out), nil
		}, nil
	case "events":
		f, e := parser.ParseEventsFile(orig)
		if e != nil {
			return "", nil, nil, fmt.Errorf("parse %s: %w", name, e)
		}
		if e := verifyRoundTrip(orig, encode.EncodeEventsFile(f)); e != nil {
			return "", nil, nil, fmt.Errorf("refusing: %w", e)
		}
		return target, f, func(v any) ([]byte, error) {
			var out parser.EventsFile
			if e := remarshal(v, &out); e != nil {
				return nil, e
			}
			return encode.EncodeEventsFile(out), nil
		}, nil
	case "summonings":
		rows, e := parser.ParseSummoningFile(orig)
		if e != nil {
			return "", nil, nil, fmt.Errorf("parse %s: %w", name, e)
		}
		if e := verifyRoundTrip(orig, encode.EncodeSummoningFile(rows)); e != nil {
			return "", nil, nil, fmt.Errorf("refusing: %w", e)
		}
		return target, rows, func(v any) ([]byte, error) {
			var out []parser.SummoningRaw
			if e := remarshal(v, &out); e != nil {
				return nil, e
			}
			return encode.EncodeSummoningFile(out), nil
		}, nil
	case "staticEffects":
		f, e := parser.ParseStaticEffectsFile(orig)
		if e != nil {
			return "", nil, nil, fmt.Errorf("parse %s: %w", name, e)
		}
		if e := verifyRoundTrip(orig, encode.EncodeStaticEffectsFile(f)); e != nil {
			return "", nil, nil, fmt.Errorf("refusing: %w", e)
		}
		return target, f, func(v any) ([]byte, error) {
			var out parser.StaticEffectsFile
			if e := remarshal(v, &out); e != nil {
				return nil, e
			}
			return encode.EncodeStaticEffectsFile(out), nil
		}, nil
	}
	return "", nil, nil, fmt.Errorf("unhandled kind %q", kind)
}

// remarshal converts a decoded JSON value (or a parsed struct) into a concrete
// target struct by round-tripping through JSON. This lets the generic reencode
// closures accept the UI's parsed JSON without knowing its static type.
func remarshal(v any, target any) error {
	b, err := json.Marshal(v)
	if err != nil {
		return fmt.Errorf("re-marshal: %w", err)
	}
	if err := json.Unmarshal(b, target); err != nil {
		return fmt.Errorf("decode into target: %w", err)
	}
	return nil
}

// ExportRecordsJSON returns a pretty-printed JSON document of the full parsed
// contents of the kind's .dat file. It gates on the byte-exact round-trip so a
// kind whose encoder can't reproduce the live file is reported rather than
// silently exporting un-reimportable data.
func (a *App) ExportRecordsJSON(kind string) (string, error) {
	_, value, _, err := a.parseAndGate(kind)
	if err != nil {
		return "", err
	}
	b, err := json.MarshalIndent(value, "", "  ")
	if err != nil {
		return "", fmt.Errorf("marshal %s json: %w", kind, err)
	}
	return string(b), nil
}

// ImportRecordsJSON replaces the kind's .dat with the given JSON document. The
// JSON must decode into the kind's full structure; the result is re-encoded and
// written via backup + atomic write, then the store is reloaded. A no-op
// (export then import unchanged) reproduces the file byte-for-byte.
func (a *App) ImportRecordsJSON(kind, jsonDoc string) (ExportResult, error) {
	target, _, reencode, err := a.parseAndGate(kind)
	if err != nil {
		return ExportResult{}, err
	}
	// Decode the incoming JSON generically, then let the kind's reencode closure
	// project it onto the concrete struct and produce bytes.
	var generic any
	if err := json.Unmarshal([]byte(jsonDoc), &generic); err != nil {
		return ExportResult{}, fmt.Errorf("invalid JSON: %w", err)
	}
	out, err := reencode(generic)
	if err != nil {
		return ExportResult{}, fmt.Errorf("encode imported %s: %w", kind, err)
	}
	return a.writeDatAndReload(target, out)
}
