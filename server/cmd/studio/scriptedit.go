package main

import (
	"fmt"
	"sort"
)

// This file turns the studio into a real spell-scripting IDE: it links a spell's
// ScriptID to its Lua source at scripts/<ScriptID>.lua inside the client's
// data.jar, lets the UI read that source, and writes edits back through the same
// order-preserving repackJar path (backup + atomic swap) used for every other
// jar asset. Fighter cards and static-effect areas also carry a ScriptID, so the
// same read/save pair serves them too.

// scriptEntryPath returns the data.jar entry path for a given script id.
func scriptEntryPath(scriptID int32) string {
	return fmt.Sprintf("scripts/%d.lua", scriptID)
}

// SpellScript is the resolved Lua source for one script id.
type SpellScript struct {
	ScriptID int32  `json:"scriptId"`
	Entry    string `json:"entry"`
	Exists   bool   `json:"exists"`
	Source   string `json:"source"`
	Bytes    int64  `json:"bytes"`
}

// GetSpellScript resolves a script id to its Lua source in data.jar. A missing
// script is NOT an error (Exists=false) so the UI can offer to view/link it
// without a hard failure -- some spells reference script ids that have no file.
func (a *App) GetSpellScript(scriptID int32) (SpellScript, error) {
	entry := scriptEntryPath(scriptID)
	out := SpellScript{ScriptID: scriptID, Entry: entry}
	r, err := a.openNamedJar("data.jar")
	if err != nil {
		return out, err
	}
	f := findEntry(r, entry)
	if f == nil {
		return out, nil // Exists stays false
	}
	data, err := readZipEntry(f, 1<<20) // scripts are tiny; 1 MiB ceiling is ample
	if err != nil {
		return out, fmt.Errorf("read %s: %w", entry, err)
	}
	out.Exists = true
	out.Source = string(data)
	out.Bytes = int64(f.UncompressedSize64)
	return out, nil
}

// SaveSpellScript overwrites scripts/<scriptID>.lua in data.jar with new source
// (backup + atomic). The script must already exist; creating brand-new script
// files is intentionally out of scope for this in-place editor.
func (a *App) SaveSpellScript(scriptID int32, source string) (RepackResult, error) {
	if scriptID <= 0 {
		return RepackResult{}, fmt.Errorf("invalid script id %d", scriptID)
	}
	return a.SaveJarText("data.jar", scriptEntryPath(scriptID), source)
}

// ListScriptIDs returns the sorted set of script ids that have a Lua file in
// data.jar, so the UI can badge which spells/cards actually have a script.
func (a *App) ListScriptIDs() ([]int32, error) {
	r, err := a.openNamedJar("data.jar")
	if err != nil {
		return nil, err
	}
	var ids []int32
	for _, f := range r.File {
		var id int32
		if _, scanErr := fmt.Sscanf(f.Name, "scripts/%d.lua", &id); scanErr == nil && id > 0 {
			// Guard against false positives like "scripts/12.lua.bak".
			if f.Name == scriptEntryPath(id) {
				ids = append(ids, id)
			}
		}
	}
	sort.Slice(ids, func(i, j int) bool { return ids[i] < ids[j] })
	return ids, nil
}
