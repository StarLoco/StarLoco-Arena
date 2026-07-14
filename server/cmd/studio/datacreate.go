package main

import (
	"fmt"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file adds NEW-RECORD CREATION: appending brand-new spells, fighter
// cards, coach cards and summonings to their .dat files. New records start
// with sensible scalar defaults and no effects; the same guarded pipeline as
// every other save is used (reparse -> byte-exact round-trip gate -> append ->
// re-encode -> backup + atomic write -> reload store). Callers can then use the
// per-parent effect editors to attach effects.
//
// A new record's ID is validated to be non-zero and not already present, so
// creation can never silently collide with an existing record.

// NewRecordResult reports the created record's id alongside the export result.
type NewRecordResult struct {
	ExportResult
	NewID int32 `json:"newId"`
}

// nextFreeID returns the smallest positive id not present in used.
func nextFreeID(used map[int32]bool) int32 {
	id := int32(1)
	for used[id] {
		id++
	}
	return id
}

// SuggestSpellID returns a free spell id (for the UI to pre-fill a create form).
func (a *App) SuggestSpellID() (int32, error) {
	_, orig, err := a.dataFile("spells.dat")
	if err != nil {
		return 0, err
	}
	f, err := parser.ParseSpellsFile(orig)
	if err != nil {
		return 0, fmt.Errorf("parse current spells.dat: %w", err)
	}
	used := map[int32]bool{}
	for _, s := range f.Spells {
		used[s.ID] = true
	}
	return nextFreeID(used), nil
}

// SpellCreate is the scalar payload for a brand-new spell.
type SpellCreate struct {
	ID                        int32  `json:"id"`
	ActionPointsCost          int    `json:"actionPointsCost"`
	CastFrequencyMaxPerPlayer int    `json:"castFrequencyMaxPerPlayer"`
	CastFrequencyMaxPerTurn   int    `json:"castFrequencyMaxPerTurn"`
	CastFrequencyMinInterval  int    `json:"castFrequencyMinInterval"`
	CastTestLineOfSight       bool   `json:"castTestLineOfSight"`
	CastOnlyLine              bool   `json:"castOnlyLine"`
	RangeMin                  int    `json:"rangeMin"`
	RangeMax                  int    `json:"rangeMax"`
	Price                     int32  `json:"price"`
	AiTargetID                int32  `json:"aiTargetId"`
	NeedFreeCell              bool   `json:"needFreeCell"`
	ScriptID                  int32  `json:"scriptId"`
	BreedID                   int32  `json:"breedId"`
	Criterion                 string `json:"criterion"`
	UseAutoDescription        bool   `json:"useAutoDescription"`
}

// CreateSpell appends a new spell (with no effects) to spells.dat.
func (a *App) CreateSpell(c SpellCreate) (NewRecordResult, error) {
	target, orig, err := a.dataFile("spells.dat")
	if err != nil {
		return NewRecordResult{}, err
	}
	f, err := parser.ParseSpellsFile(orig)
	if err != nil {
		return NewRecordResult{}, fmt.Errorf("parse current spells.dat: %w", err)
	}
	if err := verifyRoundTrip(orig, encode.EncodeSpellsFile(f)); err != nil {
		return NewRecordResult{}, fmt.Errorf("refusing to export: %w", err)
	}
	if c.ID <= 0 {
		return NewRecordResult{}, fmt.Errorf("new spell id must be positive, got %d", c.ID)
	}
	for _, s := range f.Spells {
		if s.ID == c.ID {
			return NewRecordResult{}, fmt.Errorf("spell id %d already exists", c.ID)
		}
	}
	f.Spells = append(f.Spells, parser.SpellRaw{
		ID:                        c.ID,
		ActionPointsCost:          byte(c.ActionPointsCost),
		CastFrequencyMaxPerPlayer: byte(c.CastFrequencyMaxPerPlayer),
		CastFrequencyMaxPerTurn:   byte(c.CastFrequencyMaxPerTurn),
		CastFrequencyMinInterval:  byte(c.CastFrequencyMinInterval),
		CastTestLineOfSight:       c.CastTestLineOfSight,
		CastOnlyLine:              c.CastOnlyLine,
		RangeMin:                  byte(c.RangeMin),
		RangeMax:                  byte(c.RangeMax),
		Price:                     c.Price,
		AiTargetID:                c.AiTargetID,
		NeedFreeCell:              c.NeedFreeCell,
		ScriptID:                  c.ScriptID,
		BreedID:                   c.BreedID,
		Criterion:                 c.Criterion,
		UseAutoDescription:        c.UseAutoDescription,
	})
	res, err := a.writeDatAndReload(target, encode.EncodeSpellsFile(f))
	return NewRecordResult{ExportResult: res, NewID: c.ID}, err
}

// SummoningCreate is the payload for a brand-new summoning template.
type SummoningCreate struct {
	ID      int32 `json:"id"`
	HP      int32 `json:"hp"`
	AP      int32 `json:"ap"`
	MP      int32 `json:"mp"`
	Gfx     int32 `json:"gfx"`
	SpellID int32 `json:"spellId"`
}

// SuggestSummoningID returns a free summoning id.
func (a *App) SuggestSummoningID() (int32, error) {
	_, orig, err := a.dataFile("summoning.dat")
	if err != nil {
		return 0, err
	}
	rows, err := parser.ParseSummoningFile(orig)
	if err != nil {
		return 0, fmt.Errorf("parse current summoning.dat: %w", err)
	}
	used := map[int32]bool{}
	for _, s := range rows {
		used[s.ID] = true
	}
	return nextFreeID(used), nil
}

// CreateSummoning appends a new summoning template to summoning.dat.
func (a *App) CreateSummoning(c SummoningCreate) (NewRecordResult, error) {
	target, orig, err := a.dataFile("summoning.dat")
	if err != nil {
		return NewRecordResult{}, err
	}
	rows, err := parser.ParseSummoningFile(orig)
	if err != nil {
		return NewRecordResult{}, fmt.Errorf("parse current summoning.dat: %w", err)
	}
	if err := verifyRoundTrip(orig, encode.EncodeSummoningFile(rows)); err != nil {
		return NewRecordResult{}, fmt.Errorf("refusing to export: %w", err)
	}
	if c.ID <= 0 {
		return NewRecordResult{}, fmt.Errorf("new summoning id must be positive, got %d", c.ID)
	}
	for _, s := range rows {
		if s.ID == c.ID {
			return NewRecordResult{}, fmt.Errorf("summoning id %d already exists", c.ID)
		}
	}
	rows = append(rows, parser.SummoningRaw{
		ID:      c.ID,
		HP:      c.HP,
		AP:      c.AP,
		MP:      c.MP,
		Gfx:     c.Gfx,
		SpellID: c.SpellID,
	})
	res, err := a.writeDatAndReload(target, encode.EncodeSummoningFile(rows))
	return NewRecordResult{ExportResult: res, NewID: c.ID}, err
}

// CoachCardCreate is the payload for a brand-new coach card.
type CoachCardCreate struct {
	ID    int32 `json:"id"`
	Type  int32 `json:"type"`
	Value int32 `json:"value"`
	Set   int32 `json:"set"`
}

// FighterCardCreate is the payload for a brand-new fighter card.
type FighterCardCreate struct {
	ID       int32 `json:"id"`
	Type     int   `json:"type"`
	Value    int32 `json:"value"`
	ScriptID int32 `json:"scriptId"`
	SubType  int32 `json:"subType"`
}

// SuggestCardIDs returns free ids for coach and fighter cards.
func (a *App) SuggestCardIDs() (map[string]int32, error) {
	_, _, cf, err := a.parseCards()
	if err != nil {
		return nil, err
	}
	coachUsed := map[int32]bool{}
	for _, c := range cf.CoachCards {
		coachUsed[c.ID] = true
	}
	fighterUsed := map[int32]bool{}
	for _, c := range cf.FighterCards {
		fighterUsed[c.ID] = true
	}
	return map[string]int32{
		"coach":   nextFreeID(coachUsed),
		"fighter": nextFreeID(fighterUsed),
	}, nil
}

// CreateCoachCard appends a new coach card to cards.dat.
func (a *App) CreateCoachCard(c CoachCardCreate) (NewRecordResult, error) {
	target, orig, cf, err := a.parseCards()
	if err != nil {
		return NewRecordResult{}, err
	}
	_ = orig
	if c.ID <= 0 {
		return NewRecordResult{}, fmt.Errorf("new coach card id must be positive, got %d", c.ID)
	}
	for _, x := range cf.CoachCards {
		if x.ID == c.ID {
			return NewRecordResult{}, fmt.Errorf("coach card id %d already exists", c.ID)
		}
	}
	cf.CoachCards = append(cf.CoachCards, parser.CoachCardRaw{
		ID:    c.ID,
		Type:  c.Type,
		Value: c.Value,
		Set:   c.Set,
	})
	res, err := a.writeDatAndReload(target, encode.EncodeCardsFile(cf))
	return NewRecordResult{ExportResult: res, NewID: c.ID}, err
}

// CreateFighterCard appends a new fighter card (no effects) to cards.dat.
func (a *App) CreateFighterCard(c FighterCardCreate) (NewRecordResult, error) {
	target, orig, cf, err := a.parseCards()
	if err != nil {
		return NewRecordResult{}, err
	}
	_ = orig
	if c.ID <= 0 {
		return NewRecordResult{}, fmt.Errorf("new fighter card id must be positive, got %d", c.ID)
	}
	for _, x := range cf.FighterCards {
		if x.ID == c.ID {
			return NewRecordResult{}, fmt.Errorf("fighter card id %d already exists", c.ID)
		}
	}
	// Reserved* fields default to zero — the round-trip gate on the next save
	// still holds because these are engine-ignored leftovers and every existing
	// card stored them as 0/1; a fresh card writing zeros is valid.
	cf.FighterCards = append(cf.FighterCards, parser.FighterCardRaw{
		ID:       c.ID,
		Type:     byte(c.Type),
		Value:    c.Value,
		ScriptID: c.ScriptID,
		SubType:  c.SubType,
	})
	res, err := a.writeDatAndReload(target, encode.EncodeCardsFile(cf))
	return NewRecordResult{ExportResult: res, NewID: c.ID}, err
}
