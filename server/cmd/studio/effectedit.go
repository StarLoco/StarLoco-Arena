package main

import (
	"fmt"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file adds full EFFECT editing: add / remove / reorder / edit the effect
// records attached to a spell (or card / event). Effects live in a shared
// []EffectRaw section keyed by ParentID; editing a parent means replacing every
// effect row with that ParentID (in order) with the UI's new set, preserving
// all other parents' effects untouched. The same guarded pipeline is used:
// reparse -> byte-exact round-trip gate -> splice new effects -> re-encode ->
// backup + atomic write -> reload store.

// EffectEditDTO is one effect row as edited in the UI. Slice fields default to
// nil (encoded as an empty length-prefixed list, matching the format).
type EffectEditDTO struct {
	ID                     int32     `json:"id"`
	Reserved               int16     `json:"reserved"`
	ActionID               int32     `json:"actionId"`
	IsCritical             bool      `json:"isCritical"`
	Duration               []int32   `json:"duration"`
	Params                 []float32 `json:"params"`
	AreaShape              int16     `json:"areaShape"`
	AreaSize               []int32   `json:"areaSize"`
	Targets                []int32   `json:"targets"`
	TriggersAfter          []int32   `json:"triggersAfter"`
	TriggersBefore         []int32   `json:"triggersBefore"`
	AffectedByLocalisation bool      `json:"affectedByLocalisation"`
}

// toEffectRaw converts a UI DTO to the parser record, stamping the parent link.
func (e EffectEditDTO) toEffectRaw(parentType string, parentID int32) parser.EffectRaw {
	return parser.EffectRaw{
		ID:                     e.ID,
		ParentType:             parentType,
		ParentID:               parentID,
		Reserved:               e.Reserved,
		Duration:               e.Duration,
		ActionID:               e.ActionID,
		IsCritical:             e.IsCritical,
		Params:                 e.Params,
		AreaShape:              e.AreaShape,
		AreaSize:               e.AreaSize,
		Targets:                e.Targets,
		TriggersAfter:          e.TriggersAfter,
		TriggersBefore:         e.TriggersBefore,
		AffectedByLocalisation: e.AffectedByLocalisation,
	}
}

// SaveSpellEffects replaces the effects attached to one spell (by parent id)
// with the given set and re-encodes spells.dat. Every other spell's fields and
// effects are preserved from the freshly-reparsed on-disk file.
func (a *App) SaveSpellEffects(spellID int32, effects []EffectEditDTO) (ExportResult, error) {
	target, orig, err := a.dataFile("spells.dat")
	if err != nil {
		return ExportResult{}, err
	}
	f, err := parser.ParseSpellsFile(orig)
	if err != nil {
		return ExportResult{}, fmt.Errorf("parse current spells.dat: %w", err)
	}
	if err := verifyRoundTrip(orig, encode.EncodeSpellsFile(f)); err != nil {
		return ExportResult{}, fmt.Errorf("refusing to export: %w", err)
	}
	if _, ok := spellByID(f, spellID); !ok {
		return ExportResult{}, fmt.Errorf("unknown spell id %d", spellID)
	}
	// Determine the ParentType used by this spell's existing effects (preserve
	// the exact string, incl. any trailing padding the format uses). Fall back
	// to the first effect's type, then to "SPELL".
	parentType := effectParentType(f.Effects, spellID, "SPELL")

	f.Effects = spliceEffects(f.Effects, spellID, parentType, effects)
	return a.writeDatAndReload(target, encode.EncodeSpellsFile(f))
}

// spellByID returns the spell with id, if present.
func spellByID(f parser.SpellsFile, id int32) (parser.SpellRaw, bool) {
	for _, s := range f.Spells {
		if s.ID == id {
			return s, true
		}
	}
	return parser.SpellRaw{}, false
}

// effectParentType returns the ParentType string of the first effect belonging
// to parentID, or fallback if the parent currently has no effects.
func effectParentType(effects []parser.EffectRaw, parentID int32, fallback string) string {
	for _, e := range effects {
		if e.ParentID == parentID {
			return e.ParentType
		}
	}
	return fallback
}

// spliceEffects returns a new effect slice with every row for parentID removed
// and the new set inserted AT THE POSITION of the parent's first existing
// effect (so file ordering stays natural). If the parent had no effects, the
// new set is appended. Other parents' effects are untouched and keep order.
func spliceEffects(all []parser.EffectRaw, parentID int32, parentType string, edited []EffectEditDTO) []parser.EffectRaw {
	out := make([]parser.EffectRaw, 0, len(all)+len(edited))
	inserted := false
	insert := func() {
		for _, e := range edited {
			out = append(out, e.toEffectRaw(parentType, parentID))
		}
		inserted = true
	}
	for _, e := range all {
		if e.ParentID == parentID {
			if !inserted {
				insert() // drop-in at the first occurrence
			}
			continue // skip the old rows for this parent
		}
		out = append(out, e)
	}
	if !inserted {
		insert() // parent had no prior effects -> append
	}
	return out
}
