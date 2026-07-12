package main

import (
	"fmt"
	"os"
	"path/filepath"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file extends the Phase-5 editable-data surface beyond spells: coach
// cards, fighter cards, summonings and static-effect areas. Every save follows
// the exact same guarded pipeline as SaveSpells: reparse the on-disk file ->
// verify the encoder reproduces it byte-for-byte (refuse otherwise) -> apply
// the UI's edits by ID -> re-encode -> back up + atomic write -> reload store.
// Only the scalar fields the UI exposes are written; effects and every other
// field are preserved from the freshly-reparsed file, so unrelated data can't
// be corrupted.

// --- Summonings ---

// SummoningEdit is the editable subset of a summoning template.
type SummoningEdit struct {
	ID      int32 `json:"id"`
	HP      int32 `json:"hp"`
	AP      int32 `json:"ap"`
	MP      int32 `json:"mp"`
	Gfx     int32 `json:"gfx"`
	SpellID int32 `json:"spellId"`
}

// SaveSummonings re-encodes summoning.dat with the given edits applied by ID.
func (a *App) SaveSummonings(edits []SummoningEdit) (ExportResult, error) {
	target, orig, err := a.dataFile("summoning.dat")
	if err != nil {
		return ExportResult{}, err
	}
	rows, err := parser.ParseSummoningFile(orig)
	if err != nil {
		return ExportResult{}, fmt.Errorf("parse current summoning.dat: %w", err)
	}
	if err := verifyRoundTrip(orig, encode.EncodeSummoningFile(rows)); err != nil {
		return ExportResult{}, fmt.Errorf("refusing to export: %w", err)
	}
	idx := map[int32]int{}
	for i := range rows {
		idx[rows[i].ID] = i
	}
	for _, e := range edits {
		i, ok := idx[e.ID]
		if !ok {
			return ExportResult{}, fmt.Errorf("edit for unknown summoning id %d", e.ID)
		}
		rows[i].HP, rows[i].AP, rows[i].MP = e.HP, e.AP, e.MP
		rows[i].Gfx, rows[i].SpellID = e.Gfx, e.SpellID
	}
	return a.writeDatAndReload(target, encode.EncodeSummoningFile(rows))
}

// --- Coach cards ---

// CoachCardEdit is the editable subset of a coach card.
type CoachCardEdit struct {
	ID    int32 `json:"id"`
	Type  int32 `json:"type"`
	Value int32 `json:"value"`
	Set   int32 `json:"set"`
}

// SaveCoachCards re-encodes cards.dat's coach-card section with the given edits.
func (a *App) SaveCoachCards(edits []CoachCardEdit) (ExportResult, error) {
	target, orig, cf, err := a.parseCards()
	if err != nil {
		return ExportResult{}, err
	}
	_ = orig
	idx := map[int32]int{}
	for i := range cf.CoachCards {
		idx[cf.CoachCards[i].ID] = i
	}
	for _, e := range edits {
		i, ok := idx[e.ID]
		if !ok {
			return ExportResult{}, fmt.Errorf("edit for unknown coach card id %d", e.ID)
		}
		cf.CoachCards[i].Type = e.Type
		cf.CoachCards[i].Value = e.Value
		cf.CoachCards[i].Set = e.Set
	}
	return a.writeDatAndReload(target, encode.EncodeCardsFile(cf))
}

// --- Fighter cards ---

// FighterCardEdit is the editable subset of a fighter card (scalar fields
// only; effects are preserved).
type FighterCardEdit struct {
	ID       int32 `json:"id"`
	Type     byte  `json:"type"`
	Value    int32 `json:"value"`
	ScriptID int32 `json:"scriptId"`
	SubType  int32 `json:"subType"`
}

// SaveFighterCards re-encodes cards.dat's fighter-card section with the edits.
func (a *App) SaveFighterCards(edits []FighterCardEdit) (ExportResult, error) {
	target, orig, cf, err := a.parseCards()
	if err != nil {
		return ExportResult{}, err
	}
	_ = orig
	idx := map[int32]int{}
	for i := range cf.FighterCards {
		idx[cf.FighterCards[i].ID] = i
	}
	for _, e := range edits {
		i, ok := idx[e.ID]
		if !ok {
			return ExportResult{}, fmt.Errorf("edit for unknown fighter card id %d", e.ID)
		}
		cf.FighterCards[i].Type = e.Type
		cf.FighterCards[i].Value = e.Value
		cf.FighterCards[i].ScriptID = e.ScriptID
		cf.FighterCards[i].SubType = e.SubType
	}
	return a.writeDatAndReload(target, encode.EncodeCardsFile(cf))
}

// --- Static effects ---

// StaticEffectEdit is the editable subset of a static-effect area (scalar
// fields; effects/triggers preserved).
type StaticEffectEdit struct {
	ID                int32 `json:"id"`
	ScriptID          int32 `json:"scriptId"`
	AreaShapeID       int16 `json:"areaShapeId"`
	MaxExecutionCount int16 `json:"maxExecutionCount"`
	TargetsToShow     int32 `json:"targetsToShow"`
}

// SaveStaticEffects re-encodes staticEffects.dat with the given edits.
func (a *App) SaveStaticEffects(edits []StaticEffectEdit) (ExportResult, error) {
	target, orig, err := a.dataFile("staticEffects.dat")
	if err != nil {
		return ExportResult{}, err
	}
	sf, err := parser.ParseStaticEffectsFile(orig)
	if err != nil {
		return ExportResult{}, fmt.Errorf("parse current staticEffects.dat: %w", err)
	}
	if err := verifyRoundTrip(orig, encode.EncodeStaticEffectsFile(sf)); err != nil {
		return ExportResult{}, fmt.Errorf("refusing to export: %w", err)
	}
	idx := map[int32]int{}
	for i := range sf.Areas {
		idx[sf.Areas[i].ID] = i
	}
	for _, e := range edits {
		i, ok := idx[e.ID]
		if !ok {
			return ExportResult{}, fmt.Errorf("edit for unknown static effect id %d", e.ID)
		}
		sf.Areas[i].ScriptID = e.ScriptID
		sf.Areas[i].AreaShapeID = e.AreaShapeID
		sf.Areas[i].MaxExecutionCount = e.MaxExecutionCount
		sf.Areas[i].TargetsToShow = e.TargetsToShow
	}
	return a.writeDatAndReload(target, encode.EncodeStaticEffectsFile(sf))
}

// --- shared helpers ---

// dataFile resolves a data/<name> path and reads its current bytes.
func (a *App) dataFile(name string) (target string, orig []byte, err error) {
	if !a.paths.DataDirValid {
		return "", nil, fmt.Errorf("no valid data directory selected")
	}
	target = filepath.Join(a.paths.DataDir, name)
	orig, err = os.ReadFile(target)
	if err != nil {
		return "", nil, fmt.Errorf("read %s: %w", target, err)
	}
	return target, orig, nil
}

// parseCards reads + reparses cards.dat and gates on a byte-exact round-trip.
func (a *App) parseCards() (target string, orig []byte, cf parser.CardsFile, err error) {
	target, orig, err = a.dataFile("cards.dat")
	if err != nil {
		return "", nil, parser.CardsFile{}, err
	}
	cf, err = parser.ParseCardsFile(orig)
	if err != nil {
		return "", nil, parser.CardsFile{}, fmt.Errorf("parse current cards.dat: %w", err)
	}
	if err := verifyRoundTrip(orig, encode.EncodeCardsFile(cf)); err != nil {
		return "", nil, parser.CardsFile{}, fmt.Errorf("refusing to export: %w", err)
	}
	return target, orig, cf, nil
}

// writeDatAndReload exports bytes to target (backup + atomic) and reloads the
// gamedata store so subsequent Get* calls reflect the change.
func (a *App) writeDatAndReload(target string, out []byte) (ExportResult, error) {
	res, err := exportBytes(target, out)
	if err != nil {
		return res, err
	}
	a.store = a.newStoreForData()
	return res, nil
}
