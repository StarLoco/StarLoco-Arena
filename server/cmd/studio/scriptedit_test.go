package main

import (
	"strings"
	"testing"
)

// TestListAndGetSpellScript_RealClient confirms the studio can enumerate the
// Lua scripts shipped in data.jar and read one back. Skips when the real client
// isn't present (same convention as every other real-data test).
func TestListAndGetSpellScript_RealClient(t *testing.T) {
	a := newAppWithClient(t)
	ids, err := a.ListScriptIDs()
	if err != nil {
		t.Fatalf("ListScriptIDs: %v", err)
	}
	if len(ids) == 0 {
		t.Skip("no Lua scripts in data.jar")
	}
	// ids must be sorted ascending and correspond to scripts/<id>.lua.
	for i := 1; i < len(ids); i++ {
		if ids[i] <= ids[i-1] {
			t.Fatalf("ListScriptIDs not sorted/unique at %d: %v", i, ids[:i+1])
		}
	}
	sc, err := a.GetSpellScript(ids[0])
	if err != nil {
		t.Fatalf("GetSpellScript(%d): %v", ids[0], err)
	}
	if !sc.Exists {
		t.Fatalf("script %d listed but GetSpellScript says missing", ids[0])
	}
	if sc.Entry != scriptEntryPath(ids[0]) {
		t.Errorf("entry = %q, want %q", sc.Entry, scriptEntryPath(ids[0]))
	}
	if strings.TrimSpace(sc.Source) == "" {
		t.Errorf("script %d source is empty", ids[0])
	}
}

// TestGetSpellScript_MissingIsNotError confirms an unknown script id resolves
// to Exists=false without erroring, so the UI can offer a graceful "no script"
// state instead of a hard failure.
func TestGetSpellScript_MissingIsNotError(t *testing.T) {
	a := newAppWithClient(t)
	sc, err := a.GetSpellScript(999999)
	if err != nil {
		t.Fatalf("GetSpellScript(999999): unexpected error %v", err)
	}
	if sc.Exists {
		t.Fatalf("did not expect script 999999 to exist")
	}
}
