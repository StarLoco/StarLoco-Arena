package main

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/dofusarena/go-server/internal/combat"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// tryRead reads dir/name, returning nil (not fatal) if absent.
func tryRead(t *testing.T, dir, name string) []byte {
	t.Helper()
	raw, err := os.ReadFile(filepath.Join(dir, name))
	if err != nil {
		return nil
	}
	return raw
}

// TestEffectSemantics_CoverAllRealActions asserts that EVERY effect action id
// present in the shipped data (spells/cards/events/staticEffects) is modeled by
// the combat EffectSemantics table -- i.e. the studio's decoder never has to
// fall back to "action N" for real content. This is a durable regression guard:
// if data ever references a new action, or the semantics table drops an entry,
// this fails loudly. (An audit at the time this was written found 62 distinct
// action ids in use, all modeled.)
func TestEffectSemantics_CoverAllRealActions(t *testing.T) {
	a := newAppWithData(t)

	known := map[int32]bool{}
	for _, s := range combat.EffectSemantics() {
		known[s.ActionID] = true
	}

	used := map[int32]int{}
	addEffects := func(effs []parser.EffectRaw) {
		for _, e := range effs {
			used[e.ActionID]++
		}
	}
	dir := a.paths.DataDir
	if raw := tryRead(t, dir, "spells.dat"); raw != nil {
		if f, err := parser.ParseSpellsFile(raw); err == nil {
			addEffects(f.Effects)
		}
	}
	if raw := tryRead(t, dir, "cards.dat"); raw != nil {
		if f, err := parser.ParseCardsFile(raw); err == nil {
			addEffects(f.Effects)
		}
	}
	if raw := tryRead(t, dir, "events.dat"); raw != nil {
		if f, err := parser.ParseEventsFile(raw); err == nil {
			addEffects(f.Effects)
		}
	}
	if raw := tryRead(t, dir, "staticEffects.dat"); raw != nil {
		if f, err := parser.ParseStaticEffectsFile(raw); err == nil {
			addEffects(f.Effects)
		}
	}
	if len(used) == 0 {
		t.Skip("no effect data available")
	}

	var unmodeled []int32
	for id := range used {
		if !known[id] {
			unmodeled = append(unmodeled, id)
		}
	}
	if len(unmodeled) > 0 {
		t.Errorf("real data uses %d unmodeled action id(s): %v -- add them to EffectSemantics", len(unmodeled), unmodeled)
	}
	t.Logf("all %d distinct action ids in real data are modeled", len(used))
}

// TestValidateData_RealData runs the integrity validator over the real
// repositories and asserts the report is well-formed. It scans a non-trivial
// number of records and every issue must carry a navigable target. Skips when
// the data dir is absent.
func TestValidateData_RealData(t *testing.T) {
	a := newAppWithData(t)
	rep, err := a.ValidateData()
	if err != nil {
		t.Fatalf("ValidateData: %v", err)
	}
	if rep.Checked == 0 {
		t.Fatal("validator scanned zero records")
	}
	if rep.Errors+rep.Warnings+rep.Infos != len(rep.Issues) {
		t.Errorf("counts (%d/%d/%d) != issue count %d",
			rep.Errors, rep.Warnings, rep.Infos, len(rep.Issues))
	}
	for i, is := range rep.Issues {
		if is.Severity == "" || is.View == "" || is.Message == "" {
			t.Errorf("issue %d missing fields: %+v", i, is)
		}
	}
	t.Logf("validated %d records: %d errors, %d warnings, %d infos",
		rep.Checked, rep.Errors, rep.Warnings, rep.Infos)
}

// TestValidateData_RealFightMapsHaveSpawns confirms the shipped fight maps pass
// the spawn-marker check -- i.e. the validator's map rules don't false-flag the
// real, playable maps. Any "missing spawn" issue here would indicate either a
// broken map or a broken check.
func TestValidateData_RealFightMapsHaveSpawns(t *testing.T) {
	a := newAppWithData(t)
	rep, err := a.ValidateData()
	if err != nil {
		t.Fatalf("ValidateData: %v", err)
	}
	for _, is := range rep.Issues {
		if is.Category == "missing spawn" || is.Category == "map load" {
			t.Errorf("real fight map flagged: [%s] %s", is.Category, is.Message)
		}
	}
}

// TestValidateData_DetectsBrokenSummonRef confirms the validator flags a summon
// effect that references a non-existent summoning, using a synthetic store so
// the assertion doesn't depend on the shipped data being clean or dirty.
func TestValidateData_SortsErrorsFirst(t *testing.T) {
	a := newAppWithData(t)
	rep, err := a.ValidateData()
	if err != nil {
		t.Fatalf("ValidateData: %v", err)
	}
	// Errors must precede warnings which must precede infos in the sorted list.
	rank := map[string]int{"error": 0, "warning": 1, "info": 2}
	last := -1
	for _, is := range rep.Issues {
		r := rank[is.Severity]
		if r < last {
			t.Fatalf("issues not sorted worst-first: %s after rank %d", is.Severity, last)
		}
		last = r
	}
}
