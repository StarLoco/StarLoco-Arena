package main

import (
	"sort"

	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file exposes the read-only game-data repositories (Phase 1) to the
// frontend. Every method reuses the existing, byte-exact parsers via the
// gamedata.Store built in app.go -- there is no format logic here, only
// loading, sorting, and shaping into JSON the UI can render as tables.

// DataCounts is a quick summary of how many records each repository holds,
// used to populate the nav badges and the overview page. A per-repo error
// string (empty when fine) lets the UI show a load failure inline instead
// of failing the whole panel.
type DataCounts struct {
	Spells        int    `json:"spells"`
	CoachCards    int    `json:"coachCards"`
	FighterCards  int    `json:"fighterCards"`
	Summonings    int    `json:"summonings"`
	StaticEffects int    `json:"staticEffects"`
	Events        int    `json:"events"`
	Error         string `json:"error"`
}

// GetDataCounts loads every repository and returns their record counts. A
// missing/invalid data dir yields an Error string rather than a hard fail.
func (a *App) GetDataCounts() DataCounts {
	store, err := a.requireStore()
	if err != nil {
		return DataCounts{Error: err.Error()}
	}
	c := DataCounts{
		Spells:        store.Spells.Len(),
		CoachCards:    store.CoachCards.Len(),
		FighterCards:  store.FighterCards.Len(),
		Summonings:    store.Summonings.Len(),
		StaticEffects: store.StaticEffectAreas.Len(),
		Events:        store.Events.Len(),
	}
	if e := firstRepoErr(store); e != "" {
		c.Error = e
	}
	return c
}

// GetSpells returns every spell template, sorted by ID.
func (a *App) GetSpells() ([]gamedata.SpellTemplate, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	out := store.Spells.All()
	if err := store.Spells.Err(); err != nil {
		return nil, err
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// GetCoachCards returns every coach-card template, sorted by ID.
func (a *App) GetCoachCards() ([]gamedata.CoachCardTemplate, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	out := store.CoachCards.All()
	if err := store.CoachCards.Err(); err != nil {
		return nil, err
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// GetFighterCards returns every fighter-card template, sorted by ID.
func (a *App) GetFighterCards() ([]gamedata.FighterCardTemplate, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	out := store.FighterCards.All()
	if err := store.FighterCards.Err(); err != nil {
		return nil, err
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// GetSummonings returns every summoning template, sorted by ID.
func (a *App) GetSummonings() ([]gamedata.SummoningTemplate, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	out := store.Summonings.All()
	if err := store.Summonings.Err(); err != nil {
		return nil, err
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// GetStaticEffects returns every static-effect-area template, sorted by ID.
func (a *App) GetStaticEffects() ([]gamedata.StaticEffectAreaTemplate, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	out := store.StaticEffectAreas.All()
	if err := store.StaticEffectAreas.Err(); err != nil {
		return nil, err
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// GetEvents returns every event template, sorted by ID.
func (a *App) GetEvents() ([]gamedata.EventTemplate, error) {
	store, err := a.requireStore()
	if err != nil {
		return nil, err
	}
	out := store.Events.All()
	if err := store.Events.Err(); err != nil {
		return nil, err
	}
	sort.Slice(out, func(i, j int) bool { return out[i].ID < out[j].ID })
	return out, nil
}

// firstRepoErr returns the first non-nil repository load error as a string
// (empty if all loaded fine), so DataCounts can surface a load problem.
func firstRepoErr(store *gamedata.Store) string {
	for _, e := range []error{
		store.Spells.Err(),
		store.CoachCards.Err(),
		store.FighterCards.Err(),
		store.Summonings.Err(),
		store.StaticEffectAreas.Err(),
		store.Events.Err(),
	} {
		if e != nil {
			return e.Error()
		}
	}
	return ""
}
