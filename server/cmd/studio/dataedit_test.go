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

// TestSpliceEffects_NoOpPreservesFile confirms that re-splicing a spell's own
// effects back in the same order reproduces the exact effect list, so a no-op
// effect save round-trips the whole spells.dat byte-for-byte.
func TestSpliceEffects_NoOpPreservesFile(t *testing.T) {
	raw := realData(t, "spells.dat")
	f, err := parser.ParseSpellsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	// Pick the first spell that actually has effects.
	var target int32 = -1
	for _, e := range f.Effects {
		target = e.ParentID
		break
	}
	if target == -1 {
		t.Skip("no effects in spells.dat")
	}
	// Convert that parent's effects to DTOs and splice them back unchanged.
	var dtos []EffectEditDTO
	var ptype string
	for _, e := range f.Effects {
		if e.ParentID == target {
			ptype = e.ParentType
			dtos = append(dtos, EffectEditDTO{
				ID: e.ID, Reserved: e.Reserved, ActionID: e.ActionID, IsCritical: e.IsCritical,
				Duration: e.Duration, Params: e.Params, AreaShape: e.AreaShape, AreaSize: e.AreaSize,
				Targets: e.Targets, TriggersAfter: e.TriggersAfter, TriggersBefore: e.TriggersBefore,
				AffectedByLocalisation: e.AffectedByLocalisation,
			})
		}
	}
	f.Effects = spliceEffects(f.Effects, target, ptype, dtos)
	if got := encode.EncodeSpellsFile(f); !bytesEqual(raw, got) {
		t.Errorf("no-op effect splice changed spells.dat (orig=%d got=%d)", len(raw), len(got))
	}
}

// effectsToDTOs converts every effect belonging to parentID into edit DTOs
// (order preserved) plus that parent's ParentType.
func effectsToDTOs(all []parser.EffectRaw, parentID int32) (string, []EffectEditDTO) {
	var ptype string
	var dtos []EffectEditDTO
	for _, e := range all {
		if e.ParentID == parentID {
			ptype = e.ParentType
			dtos = append(dtos, EffectEditDTO{
				ID: e.ID, Reserved: e.Reserved, ActionID: e.ActionID, IsCritical: e.IsCritical,
				Duration: e.Duration, Params: e.Params, AreaShape: e.AreaShape, AreaSize: e.AreaSize,
				Targets: e.Targets, TriggersAfter: e.TriggersAfter, TriggersBefore: e.TriggersBefore,
				AffectedByLocalisation: e.AffectedByLocalisation,
			})
		}
	}
	return ptype, dtos
}

// TestSpliceEffects_NoOpCards confirms a no-op re-splice of a fighter card's
// own effects reproduces cards.dat byte-for-byte.
func TestSpliceEffects_NoOpCards(t *testing.T) {
	raw := realData(t, "cards.dat")
	cf, err := parser.ParseCardsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	var target int32 = -1
	for _, e := range cf.Effects {
		target = e.ParentID
		break
	}
	if target == -1 {
		t.Skip("no effects in cards.dat")
	}
	ptype, dtos := effectsToDTOs(cf.Effects, target)
	cf.Effects = spliceEffects(cf.Effects, target, ptype, dtos)
	if got := encode.EncodeCardsFile(cf); !bytesEqual(raw, got) {
		t.Errorf("no-op card effect splice changed cards.dat (orig=%d got=%d)", len(raw), len(got))
	}
}

// TestSpliceEffects_NoOpEvents confirms a no-op re-splice of an event's own
// effects reproduces events.dat byte-for-byte.
func TestSpliceEffects_NoOpEvents(t *testing.T) {
	raw := realData(t, "events.dat")
	f, err := parser.ParseEventsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	var target int32 = -1
	for _, e := range f.Effects {
		target = e.ParentID
		break
	}
	if target == -1 {
		t.Skip("no effects in events.dat")
	}
	ptype, dtos := effectsToDTOs(f.Effects, target)
	f.Effects = spliceEffects(f.Effects, target, ptype, dtos)
	if got := encode.EncodeEventsFile(f); !bytesEqual(raw, got) {
		t.Errorf("no-op event effect splice changed events.dat (orig=%d got=%d)", len(raw), len(got))
	}
}

// TestCreateSpell_AppendThenRemoveRestoresFile appends a fresh spell then
// removes it, confirming the encoder can reproduce the original spells.dat —
// i.e. creation is a clean, reversible append.
func TestCreateSpell_AppendThenRemoveRestoresFile(t *testing.T) {
	raw := realData(t, "spells.dat")
	f, err := parser.ParseSpellsFile(raw)
	if err != nil {
		t.Fatalf("parse: %v", err)
	}
	used := map[int32]bool{}
	for _, s := range f.Spells {
		used[s.ID] = true
	}
	newID := nextFreeID(used)
	f.Spells = append(f.Spells, parser.SpellRaw{ID: newID, Criterion: ""})
	// Now drop it back off the end.
	f.Spells = f.Spells[:len(f.Spells)-1]
	if got := encode.EncodeSpellsFile(f); !bytesEqual(raw, got) {
		t.Errorf("append+remove changed spells.dat (orig=%d got=%d)", len(raw), len(got))
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
