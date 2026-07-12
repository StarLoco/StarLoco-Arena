package main

// This file exposes resolved i18n names to the frontend as compact lookup
// maps, so every view that shows an id can render "Name (id)" without the
// backend having to reshape each data DTO. The frontend fetches these once
// (per language) and does the id->name join client-side.

// NamesBundle is a set of id->name maps for each named data type, plus the
// active language. Maps use string keys because Wails/JSON object keys are
// strings; the frontend parses them as needed.
type NamesBundle struct {
	Language     string            `json:"language"`
	Available    bool              `json:"available"` // false when no i18n jar/client dir
	Spells       map[string]string `json:"spells"`
	Breeds       map[string]string `json:"breeds"`
	FighterCards map[string]string `json:"fighterCards"`
	CoachCards   map[string]string `json:"coachCards"`
	Events       map[string]string `json:"events"`
	Effects      map[string]string `json:"effects"`
	Summons      map[string]string `json:"summons"`
}

// GetNames returns every resolved name map for the active language. It reads
// straight from the parsed i18n table (already keyed "<cat>.<id>"), filtering
// each category into its own id->name map. Blank values are omitted so the
// frontend cleanly falls back to the bare id.
func (a *App) GetNames() NamesBundle {
	store := a.ensureI18n()
	nb := NamesBundle{
		Language:     store.lang,
		Available:    store.loadErr == nil && len(store.entries) > 0,
		Spells:       map[string]string{},
		Breeds:       map[string]string{},
		FighterCards: map[string]string{},
		CoachCards:   map[string]string{},
		Events:       map[string]string{},
		Effects:      map[string]string{},
		Summons:      map[string]string{},
	}

	dest := map[int]map[string]string{
		catSpellName:       nb.Spells,
		catBreedName:       nb.Breeds,
		catFighterCardName: nb.FighterCards,
		catCoachCardName:   nb.CoachCards,
		catEventName:       nb.Events,
		catEffectName:      nb.Effects,
		catSummonName:      nb.Summons,
	}

	store.mu.RLock()
	defer store.mu.RUnlock()
	for key, val := range store.entries {
		if val == "" {
			continue
		}
		cat, id, ok := splitCatID(key)
		if !ok {
			continue
		}
		if m, want := dest[cat]; want {
			m[id] = val
		}
	}
	return nb
}

// splitCatID splits a "<cat>.<id>" key into its integer category and the id
// string (kept as a string for direct use as a JSON map key).
func splitCatID(key string) (cat int, id string, ok bool) {
	for i := 0; i < len(key); i++ {
		if key[i] == '.' {
			if !allDigits(key[:i]) || !allDigits(key[i+1:]) {
				return 0, "", false
			}
			c := 0
			for _, ch := range key[:i] {
				c = c*10 + int(ch-'0')
			}
			return c, key[i+1:], true
		}
	}
	return 0, "", false
}
