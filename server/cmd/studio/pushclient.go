package main

import (
	"bytes"
	"crypto/sha1"
	"encoding/hex"
	"fmt"
	"os"
	"path"
	"path/filepath"

	"github.com/dofusarena/go-server/cmd/studio/encode"
	"github.com/dofusarena/go-server/internal/gamedata/parser"
)

// This file implements "Push to client": re-encoding the edited local
// data/*.dat files and repacking them into the compiled client's data.jar
// (backup + atomic), so the running client reflects the studio's edits. Each
// file is gated on a byte-exact parse->encode round-trip of the CURRENT local
// file (so we never push a file our encoder can't faithfully reproduce), and
// the status endpoint shows exactly which files differ from the client before
// anything is written.

// dataFileSpec ties a local data/<name> to its client jar entry and a
// round-trip re-encoder used as the write-safety gate.
type dataFileSpec struct {
	name     string // e.g. "spells.dat"
	jarEntry string // e.g. "data/spells.dat"
	// reencode parses raw and returns (reEncoded, error). Returning a
	// non-nil error means the file can't be safely round-tripped.
	reencode func(raw []byte) ([]byte, error)
}

// pushableFiles is the set of data files the studio can push to the client.
var pushableFiles = []dataFileSpec{
	{"spells.dat", "data/spells.dat", func(raw []byte) ([]byte, error) {
		f, err := parser.ParseSpellsFile(raw)
		if err != nil {
			return nil, err
		}
		return encode.EncodeSpellsFile(f), nil
	}},
	{"cards.dat", "data/cards.dat", func(raw []byte) ([]byte, error) {
		f, err := parser.ParseCardsFile(raw)
		if err != nil {
			return nil, err
		}
		return encode.EncodeCardsFile(f), nil
	}},
	{"events.dat", "data/events.dat", func(raw []byte) ([]byte, error) {
		f, err := parser.ParseEventsFile(raw)
		if err != nil {
			return nil, err
		}
		return encode.EncodeEventsFile(f), nil
	}},
	{"summoning.dat", "data/summoning.dat", func(raw []byte) ([]byte, error) {
		rows, err := parser.ParseSummoningFile(raw)
		if err != nil {
			return nil, err
		}
		return encode.EncodeSummoningFile(rows), nil
	}},
	{"staticEffects.dat", "data/staticEffects.dat", func(raw []byte) ([]byte, error) {
		f, err := parser.ParseStaticEffectsFile(raw)
		if err != nil {
			return nil, err
		}
		return encode.EncodeStaticEffectsFile(f), nil
	}},
}

// PushFileStatus is one data file's push-readiness: whether it exists locally,
// whether it round-trips, and whether it differs from the client jar's copy.
type PushFileStatus struct {
	Name       string `json:"name"`
	JarEntry   string `json:"jarEntry"`
	LocalOK    bool   `json:"localOk"`    // local file present + parses + round-trips
	InClient   bool   `json:"inClient"`   // the client jar has this entry
	Differs    bool   `json:"differs"`    // local (re-encoded) != client entry
	LocalHash  string `json:"localHash"`  // short sha1 of the re-encoded local bytes
	ClientHash string `json:"clientHash"` // short sha1 of the client entry
	Error      string `json:"error"`
}

// PushStatus is the full pre-flight report for the push panel.
type PushStatus struct {
	ClientJar string           `json:"clientJar"`
	Files     []PushFileStatus `json:"files"`
	Error     string           `json:"error"`
}

// GetPushStatus reports, for every pushable data file, whether the edited local
// copy differs from the client's current data.jar entry (so the UI can show a
// review list before pushing). No files are written.
func (a *App) GetPushStatus() PushStatus {
	if !a.paths.DataDirValid {
		return PushStatus{Error: "no valid data directory selected"}
	}
	dir, err := a.contentsDir()
	if err != nil {
		return PushStatus{Error: err.Error()}
	}
	jarPath := filepath.Join(dir, "data.jar")

	clientEntries := map[string][]byte{}
	r, err := a.openNamedJar("data.jar")
	if err == nil {
		for _, spec := range pushableFiles {
			if f := findEntry(r, spec.jarEntry); f != nil {
				if raw, err := readZipEntry(f, 32<<20); err == nil {
					clientEntries[spec.jarEntry] = raw
				}
			}
		}
	}

	out := PushStatus{ClientJar: jarPath}
	for _, spec := range pushableFiles {
		st := PushFileStatus{Name: spec.name, JarEntry: spec.jarEntry}
		local, err := os.ReadFile(filepath.Join(a.paths.DataDir, spec.name))
		if err != nil {
			st.Error = "not found locally"
			out.Files = append(out.Files, st)
			continue
		}
		reenc, err := spec.reencode(local)
		if err != nil {
			st.Error = "parse/encode failed: " + err.Error()
			out.Files = append(out.Files, st)
			continue
		}
		// Safety: the re-encoded local file MUST match the on-disk local file.
		if !bytes.Equal(local, reenc) {
			st.Error = "encoder not faithful for this file (won't push)"
			out.Files = append(out.Files, st)
			continue
		}
		st.LocalOK = true
		st.LocalHash = shortHash(reenc)
		if clientRaw, ok := clientEntries[spec.jarEntry]; ok {
			st.InClient = true
			st.ClientHash = shortHash(clientRaw)
			st.Differs = !bytes.Equal(reenc, clientRaw)
		} else {
			st.Differs = true // not present in client -> pushing adds it
		}
		out.Files = append(out.Files, st)
	}
	return out
}

// PushDataToClient re-encodes the named data files (or all changed ones if
// names is empty) and repacks them into the client data.jar in one atomic,
// backed-up operation. Only files that round-trip are written; anything that
// can't be faithfully re-encoded is skipped and reported, never forced.
func (a *App) PushDataToClient(names []string) (RepackResult, error) {
	if !a.paths.DataDirValid {
		return RepackResult{}, fmt.Errorf("no valid data directory selected")
	}
	dir, err := a.contentsDir()
	if err != nil {
		return RepackResult{}, err
	}
	jarPath := filepath.Join(dir, "data.jar")

	want := map[string]bool{}
	for _, n := range names {
		want[n] = true
	}

	var replacements []RepackReplacement
	for _, spec := range pushableFiles {
		if len(want) > 0 && !want[spec.name] {
			continue
		}
		local, err := os.ReadFile(filepath.Join(a.paths.DataDir, spec.name))
		if err != nil {
			continue // not present locally -> skip
		}
		reenc, err := spec.reencode(local)
		if err != nil {
			return RepackResult{}, fmt.Errorf("refusing to push %s: %w", spec.name, err)
		}
		if !bytes.Equal(local, reenc) {
			return RepackResult{}, fmt.Errorf("refusing to push %s: encoder not faithful for this file", spec.name)
		}
		replacements = append(replacements, RepackReplacement{
			EntryPath: path.Clean(spec.jarEntry),
			Data:      reenc,
		})
	}
	if len(replacements) == 0 {
		return RepackResult{}, fmt.Errorf("no pushable data files selected/available")
	}

	// Release any cached data.jar handle before the in-place repack (Windows).
	a.jars.invalidate(jarPath)

	return repackJar(jarPath, replacements)
}

// shortHash returns the first 7 hex chars of the sha1 of b, for display.
func shortHash(b []byte) string {
	sum := sha1.Sum(b)
	return hex.EncodeToString(sum[:])[:7]
}

// PushDiff summarizes what pushing one data file would change in the client:
// per-kind record-count deltas (client entry -> local edited copy) plus a size
// delta and a note. Read-only. Client=the value currently in the client jar,
// Local=the value that would be pushed.
type PushDiff struct {
	Name        string        `json:"name"`
	InClient    bool          `json:"inClient"`    // the client jar has this entry today
	Identical   bool          `json:"identical"`   // local re-encoded == client entry (push is a no-op)
	ClientBytes int64         `json:"clientBytes"` // -1 when not present in client
	LocalBytes  int64         `json:"localBytes"`
	Parsed      bool          `json:"parsed"`
	Deltas      []RecordDelta `json:"deltas"` // RecordDelta.Current=client, .Backup=local
	Note        string        `json:"note"`
}

// DiffPushFile compares the local edited data/<name> (what would be pushed)
// against the client's current data.jar entry, and returns a per-kind
// record-count delta so the deploy panel can show WHAT changes, not just that
// the hashes differ. Never writes.
func (a *App) DiffPushFile(name string) (PushDiff, error) {
	if !a.paths.DataDirValid {
		return PushDiff{}, fmt.Errorf("no valid data directory selected")
	}
	var spec *dataFileSpec
	for i := range pushableFiles {
		if pushableFiles[i].name == name {
			spec = &pushableFiles[i]
			break
		}
	}
	if spec == nil {
		return PushDiff{}, fmt.Errorf("%q is not a pushable data file", name)
	}

	local, err := os.ReadFile(filepath.Join(a.paths.DataDir, spec.name))
	if err != nil {
		return PushDiff{}, fmt.Errorf("read local %s: %w", spec.name, err)
	}
	// Use the re-encoded local bytes (the exact bytes push would write).
	reenc, err := spec.reencode(local)
	if err != nil {
		return PushDiff{}, fmt.Errorf("parse/encode local %s: %w", spec.name, err)
	}

	out := PushDiff{Name: spec.name, LocalBytes: int64(len(reenc)), ClientBytes: -1}

	var clientRaw []byte
	if r, e := a.openNamedJar("data.jar"); e == nil {
		if f := findEntry(r, spec.jarEntry); f != nil {
			if raw, re := readZipEntry(f, 32<<20); re == nil {
				clientRaw = raw
				out.InClient = true
				out.ClientBytes = int64(len(raw))
			}
		}
	}
	if out.InClient {
		out.Identical = bytes.Equal(reenc, clientRaw)
	}

	// Per-kind counts: current = client entry, backup(=new) = local re-encoded.
	if deltas, ok := recordCountDeltas(spec.name, clientRaw, reenc); ok {
		out.Parsed = true
		out.Deltas = deltas
	}
	out.Note = summarizePushDiff(out)
	return out, nil
}

// DiffAllPushFiles returns a PushDiff for every pushable data file that DIFFERS
// from the client (identical or locally-missing files are omitted), so the UI
// can show a single consolidated "review everything you're about to push"
// summary. Read-only; each file is gated by DiffPushFile's own round-trip.
func (a *App) DiffAllPushFiles() ([]PushDiff, error) {
	if !a.paths.DataDirValid {
		return nil, fmt.Errorf("no valid data directory selected")
	}
	var out []PushDiff
	for _, spec := range pushableFiles {
		// Skip files that don't exist locally (nothing to push).
		if _, err := os.Stat(filepath.Join(a.paths.DataDir, spec.name)); err != nil {
			continue
		}
		d, err := a.DiffPushFile(spec.name)
		if err != nil {
			// Surface the problem as a diff entry rather than aborting the whole
			// review (e.g. a file whose encoder isn't faithful).
			out = append(out, PushDiff{Name: spec.name, Note: "cannot diff: " + err.Error()})
			continue
		}
		if d.Identical {
			continue // no change -> not part of the review
		}
		out = append(out, d)
	}
	return out, nil
}

// summarizePushDiff builds a one-line human summary for a PushDiff.
func summarizePushDiff(d PushDiff) string {
	if !d.InClient {
		return "Not in the client yet \u2014 pushing adds this file."
	}
	if d.Identical {
		return "Identical to the client \u2014 pushing changes nothing."
	}
	var parts []string
	if d.Parsed {
		for _, r := range d.Deltas {
			if r.Current != r.Backup {
				parts = append(parts, fmt.Sprintf("%s %d\u2192%d (%+d)", r.Kind, r.Current, r.Backup, r.Backup-r.Current))
			}
		}
	}
	sizeDelta := d.LocalBytes - d.ClientBytes
	sizeTxt := "same size"
	if sizeDelta != 0 {
		sizeTxt = fmt.Sprintf("%+d bytes", sizeDelta)
	}
	if len(parts) == 0 {
		return fmt.Sprintf("Content differs (%s) but record counts are unchanged.", sizeTxt)
	}
	return fmt.Sprintf("Pushing would set: %s \u00B7 %s.", joinComma(parts), sizeTxt)
}

// joinComma joins parts with ", " (avoids importing strings just for this).
func joinComma(parts []string) string {
	out := ""
	for i, p := range parts {
		if i > 0 {
			out += ", "
		}
		out += p
	}
	return out
}
