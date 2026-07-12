package gamedata

import (
	"os"
	"path/filepath"
	"sort"
	"strconv"
	"testing"
)

// TEMP: dump one breed's spells. Set env BREED to the breed id.
func TestDumpBreedSpells(t *testing.T) {
	dir := filepath.Join("..", "..", "data")
	if _, err := os.Stat(filepath.Join(dir, "spells.dat")); err != nil {
		t.Skipf("no spells.dat: %v", err)
	}
	breed, _ := strconv.Atoi(os.Getenv("BREED"))
	if breed == 0 {
		t.Skip("set BREED env")
	}
	store := NewStore(dir)
	var list []SpellTemplate
	for _, s := range store.Spells.All() {
		if int(s.BreedID) == breed {
			list = append(list, s)
		}
	}
	sort.Slice(list, func(i, j int) bool { return list[i].ID < list[j].ID })
	for _, s := range list {
		t.Logf("=== spell %d ap=%d range=%d-%d onlyLine=%v needFree=%v LOS=%v maxPerPlayer=%d maxPerTurn=%d minInterval=%d crit=%q scriptID=%d ===",
			s.ID, s.ActionPointsCost, s.RangeMin, s.RangeMax, s.CastOnlyLine, s.NeedFreeCell, s.CastTestLineOfSight,
			s.CastFrequencyMaxPerPlayer, s.CastFrequencyMaxPerTurn, s.CastFrequencyMinInterval, s.Criterion, s.ScriptID)
		for _, e := range s.Effects {
			t.Logf("    eff id=%d action=%d crit=%v params=%v dur=%v area(shape=%d size=%v) targets=%v",
				e.ID, e.ActionID, e.IsCritical, e.Params, e.Duration, e.AreaShape, e.AreaSize, e.Targets)
		}
	}
	// Summon templates (dump once, on breed 6).
	if breed == 6 {
		for _, tm := range store.Summonings.All() {
			t.Logf("=== summon template %d: HP=%d AP=%d MP=%d spellID=%d ===", tm.ID, tm.HP, tm.AP, tm.MP, tm.SpellID)
		}
	}
}
