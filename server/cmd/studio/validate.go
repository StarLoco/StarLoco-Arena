package main

import (
	"fmt"
	"sort"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/gamedata"
)

// This file adds a DATA-INTEGRITY validator: a single pass over every record
// repository that flags broken cross-references and suspicious values, turning
// the studio into a lint tool. It leans on the same sources of truth the rest
// of the studio uses -- the combat EffectSemantics table for which actionIDs
// are modeled, and the client jar's script index for which Lua scripts exist --
// so the checks can never drift from real behavior.
//
// Every issue carries a target {view, query} so the UI can deep-link straight
// to the offending record via the existing navigate bus.

// ValidationIssue is one finding. Severity is "error" (a broken reference that
// would misbehave at runtime), "warning" (suspicious but tolerated), or "info".
type ValidationIssue struct {
	Severity string `json:"severity"`
	Category string `json:"category"` // short group label, e.g. "missing summon"
	Message  string `json:"message"`  // human description
	View     string `json:"view"`     // nav view to jump to
	Query    string `json:"query"`    // search query to focus the record
	RecordID int32  `json:"recordId"` // the offending record's id
}

// ValidationReport is the full result plus quick totals for the UI header.
type ValidationReport struct {
	Issues   []ValidationIssue `json:"issues"`
	Errors   int               `json:"errors"`
	Warnings int               `json:"warnings"`
	Infos    int               `json:"infos"`
	Checked  int               `json:"checked"` // total records scanned
}

// ValidateData runs every integrity check over the loaded repositories and the
// client script index, returning a sorted report (errors first). It needs the
// data dir; the script check degrades gracefully if the client jar is absent.
func (a *App) ValidateData() (ValidationReport, error) {
	store, err := a.requireStore()
	if err != nil {
		return ValidationReport{}, err
	}

	// Known action IDs (modeled by the engine) + the summon/set_area kinds, from
	// the same semantic table the decoder uses.
	known := map[int32]combat.EffectSemantic{}
	for _, s := range combat.EffectSemantics() {
		known[s.ActionID] = s
	}
	isSummonKind := func(k string) bool {
		return k == "summon" || k == "summon_double" || k == "summon_mirror"
	}

	// Script index (best-effort: empty when the client jar isn't available, in
	// which case we skip the missing-script check rather than false-flag).
	scriptSet := map[int32]bool{}
	haveScripts := false
	if ids, e := a.ListScriptIDs(); e == nil {
		haveScripts = true
		for _, id := range ids {
			scriptSet[id] = true
		}
	}

	// Build reference sets.
	spells := store.Spells.All()
	summonings := store.Summonings.All()
	statics := store.StaticEffectAreas.All()
	fighters := store.FighterCards.All()
	events := store.Events.All()

	spellIDs := idSet(spells, func(s gamedata.SpellTemplate) int32 { return s.ID })
	summonIDs := idSet(summonings, func(s gamedata.SummoningTemplate) int32 { return s.ID })
	staticIDs := idSet(statics, func(s gamedata.StaticEffectAreaTemplate) int32 { return s.ID })

	var issues []ValidationIssue
	add := func(sev, cat, msg, view, query string, id int32) {
		issues = append(issues, ValidationIssue{
			Severity: sev, Category: cat, Message: msg, View: view, Query: query, RecordID: id,
		})
	}

	// checkEffects validates one record's effect list for unknown actions and
	// broken summon/area references.
	checkEffects := func(effects []gamedata.EffectDef, view, query string, ownerID int32, ownerLabel string) {
		for _, e := range effects {
			sem, ok := known[e.ActionID]
			if !ok {
				add("warning", "unknown action",
					fmt.Sprintf("%s references unmodeled actionID %d (renders as raw action)", ownerLabel, e.ActionID),
					view, query, ownerID)
				continue
			}
			if isSummonKind(sem.Kind) && len(e.Params) > 0 {
				sid := int32(e.Params[0])
				if !summonIDs[sid] {
					add("error", "missing summon",
						fmt.Sprintf("%s summons unknown summoning #%d", ownerLabel, sid),
						view, query, ownerID)
				}
			}
			if sem.Kind == "set_area" && len(e.Params) > 0 {
				aid := int32(e.Params[0])
				if !staticIDs[aid] {
					add("error", "missing area",
						fmt.Sprintf("%s places unknown static-effect area #%d", ownerLabel, aid),
						view, query, ownerID)
				}
			}
		}
	}

	checked := 0

	// Spells: script existence, range sanity, effect refs.
	for _, s := range spells {
		checked++
		label := fmt.Sprintf("Spell #%d", s.ID)
		q := fmt.Sprintf("%d", s.ID)
		if haveScripts && s.ScriptID > 0 && !scriptSet[s.ScriptID] {
			add("warning", "missing script",
				fmt.Sprintf("%s references missing scripts/%d.lua", label, s.ScriptID),
				"spells", q, s.ID)
		}
		if s.RangeMax < s.RangeMin {
			add("warning", "bad range",
				fmt.Sprintf("%s has RangeMax(%d) < RangeMin(%d)", label, s.RangeMax, s.RangeMin),
				"spells", q, s.ID)
		}
		checkEffects(s.Effects, "spells", q, s.ID, label)
	}

	// Fighter cards: script existence, effect refs.
	for _, c := range fighters {
		checked++
		label := fmt.Sprintf("Fighter card #%d", c.ID)
		q := fmt.Sprintf("%d", c.ID)
		if haveScripts && c.ScriptID > 0 && !scriptSet[c.ScriptID] {
			add("warning", "missing script",
				fmt.Sprintf("%s references missing scripts/%d.lua", label, c.ScriptID),
				"cards", q, c.ID)
		}
		checkEffects(c.Effects, "cards", q, c.ID, label)
	}

	// Static-effect areas: script existence, effect refs.
	for _, a2 := range statics {
		checked++
		label := fmt.Sprintf("Static effect #%d", a2.ID)
		q := fmt.Sprintf("%d", a2.ID)
		if haveScripts && a2.ScriptID > 0 && !scriptSet[a2.ScriptID] {
			add("warning", "missing script",
				fmt.Sprintf("%s references missing scripts/%d.lua", label, a2.ScriptID),
				"staticEffects", q, a2.ID)
		}
		checkEffects(a2.Effects, "staticEffects", q, a2.ID, label)
	}

	// Events: effect refs.
	for _, ev := range events {
		checked++
		label := fmt.Sprintf("Event #%d", ev.ID)
		q := fmt.Sprintf("%d", ev.ID)
		checkEffects(ev.Effects, "events", q, ev.ID, label)
	}

	// Summonings: the spell they cast should exist.
	for _, sm := range summonings {
		checked++
		if sm.SpellID > 0 && !spellIDs[sm.SpellID] {
			add("warning", "missing spell",
				fmt.Sprintf("Summoning #%d casts unknown spell #%d", sm.ID, sm.SpellID),
				"summonings", fmt.Sprintf("%d", sm.ID), sm.ID)
		}
	}

	// Map integrity: fight maps need spawn markers for both teams and at least
	// one walkable cell, or they can't host a playable match.
	checked += a.checkMaps(store, &issues)

	// Duplicate IDs within each repository would be a real corruption.
	checkDupes(spells, func(s gamedata.SpellTemplate) int32 { return s.ID }, "Spell", "spells", &issues)
	checkDupes(summonings, func(s gamedata.SummoningTemplate) int32 { return s.ID }, "Summoning", "summonings", &issues)
	checkDupes(statics, func(s gamedata.StaticEffectAreaTemplate) int32 { return s.ID }, "Static effect", "staticEffects", &issues)
	checkDupes(fighters, func(s gamedata.FighterCardTemplate) int32 { return s.ID }, "Fighter card", "cards", &issues)
	checkDupes(events, func(s gamedata.EventTemplate) int32 { return s.ID }, "Event", "events", &issues)

	// Sort: errors first, then by category, then by record id -- stable, so the
	// UI list reads worst-first.
	sevRank := map[string]int{"error": 0, "warning": 1, "info": 2}
	sort.SliceStable(issues, func(i, j int) bool {
		if sevRank[issues[i].Severity] != sevRank[issues[j].Severity] {
			return sevRank[issues[i].Severity] < sevRank[issues[j].Severity]
		}
		if issues[i].Category != issues[j].Category {
			return issues[i].Category < issues[j].Category
		}
		return issues[i].RecordID < issues[j].RecordID
	})

	// Never return a nil slice: it marshals to JSON null and breaks the
	// frontend's rep.issues.length on a clean run. An empty (but non-nil)
	// slice marshals to [].
	if issues == nil {
		issues = []ValidationIssue{}
	}
	rep := ValidationReport{Issues: issues, Checked: checked}
	for _, is := range issues {
		switch is.Severity {
		case "error":
			rep.Errors++
		case "warning":
			rep.Warnings++
		default:
			rep.Infos++
		}
	}
	return rep, nil
}

// checkMaps validates every fight map: it must define spawn markers for both
// team sides (side 1 = A, side 0 = B) and have at least one walkable cell,
// otherwise it can't host a playable match. Non-fight world maps (e.g. the
// overworld) are skipped -- they have no match-start requirement. Returns the
// number of maps scanned.
func (a *App) checkMaps(store *gamedata.Store, out *[]ValidationIssue) int {
	fightIDs, err := store.Maps.FightMapIDs()
	if err != nil {
		return 0
	}
	scanned := 0
	for _, id := range fightIDs {
		m, err := store.Maps.Get(id)
		if err != nil {
			*out = append(*out, ValidationIssue{
				Severity: "error", Category: "map load",
				Message: fmt.Sprintf("Fight map %d failed to load: %v", id, err),
				View:    "maps", Query: fmt.Sprintf("%d", id), RecordID: int32(id),
			})
			continue
		}
		scanned++
		q := fmt.Sprintf("%d", id)

		fight := m.FightStartCells()
		if len(fight[1]) == 0 {
			*out = append(*out, ValidationIssue{
				Severity: "error", Category: "missing spawn",
				Message: fmt.Sprintf("Fight map %d has no Team A start markers", id),
				View:    "maps", Query: q, RecordID: int32(id),
			})
		}
		if len(fight[0]) == 0 {
			*out = append(*out, ValidationIssue{
				Severity: "error", Category: "missing spawn",
				Message: fmt.Sprintf("Fight map %d has no Team B start markers", id),
				View:    "maps", Query: q, RecordID: int32(id),
			})
		}

		// At least one walkable cell -- otherwise nobody can stand/move.
		walkable := 0
		for _, c := range m.Cells() {
			if alt, ok := m.StandingAltitudeAt(c.X, c.Y); ok && m.IsWalkable(c.X, c.Y, alt) {
				walkable++
				break
			}
		}
		if walkable == 0 {
			*out = append(*out, ValidationIssue{
				Severity: "warning", Category: "unplayable map",
				Message: fmt.Sprintf("Fight map %d has no walkable cells", id),
				View:    "maps", Query: q, RecordID: int32(id),
			})
		}
	}
	return scanned
}

// idSet builds a presence set of ids from a slice via an id accessor.
func idSet[T any](items []T, id func(T) int32) map[int32]bool {
	m := make(map[int32]bool, len(items))
	for _, it := range items {
		m[id(it)] = true
	}
	return m
}

// checkDupes appends an error issue for any id that appears more than once.
func checkDupes[T any](items []T, id func(T) int32, label, view string, out *[]ValidationIssue) {
	seen := map[int32]int{}
	for _, it := range items {
		seen[id(it)]++
	}
	// Deterministic order.
	ids := make([]int32, 0, len(seen))
	for k := range seen {
		ids = append(ids, k)
	}
	sort.Slice(ids, func(i, j int) bool { return ids[i] < ids[j] })
	for _, k := range ids {
		if seen[k] > 1 {
			*out = append(*out, ValidationIssue{
				Severity: "error", Category: "duplicate id",
				Message: fmt.Sprintf("%s id %d appears %d times", label, k, seen[k]),
				View:    view, Query: fmt.Sprintf("%d", k), RecordID: k,
			})
		}
	}
}
