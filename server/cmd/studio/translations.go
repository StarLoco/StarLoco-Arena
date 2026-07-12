package main

import (
	"fmt"
	"path/filepath"
	"sort"
	"strconv"
	"strings"
)

// This file exposes the client's i18n strings for EDITING. It reads every
// content.<cat>.<id> key from texts_<lang>.properties (in i18n.jar), lets the
// UI change any value (and add new ones), and writes the edited .properties
// back into i18n.jar via the safe repack pipeline (backup + atomic swap).
//
// The write path preserves the original file's LINE ORDER and every
// non-content line (comments, blanks, unrelated keys) verbatim -- only the
// values of edited content keys change, and brand-new keys are appended. Java
// .properties escaping is reapplied to values on write so the client reads them
// back identically.

// TransCategory describes what a content category holds, for grouping in the UI.
type TransCategory struct {
	Cat    int    `json:"cat"`
	Label  string `json:"label"` // e.g. "Spell name"
	Kind   string `json:"kind"`  // machine kind for filtering: spell/card/...
	IsDesc bool   `json:"isDesc"`
}

// transCategories maps each editable content category to a human label.
var transCategories = []TransCategory{
	{catSpellName, "Spell name", "spell", false},
	{catSpellDesc, "Spell description", "spell", true},
	{catFighterCardName, "Fighter card name", "fighterCard", false},
	{catFighterCardDesc, "Fighter card description", "fighterCard", true},
	{catCoachCardName, "Coach card name", "coachCard", false},
	{catCoachCardDesc, "Coach card description", "coachCard", true},
	{catBreedName, "Class name", "breed", false},
	{catBreedDesc, "Class description", "breed", true},
	{catSummonName, "Summon name", "summon", false},
	{catEventName, "Event name", "event", false},
	{catEventDesc, "Event description", "event", true},
	{catEffectName, "Effect label", "effect", false},
}

// TransRow is one editable translation entry.
type TransRow struct {
	Cat     int    `json:"cat"`
	ID      int32  `json:"id"`
	Label   string `json:"label"` // category label
	Kind    string `json:"kind"`  // spell/card/... (for icons + filters)
	IsDesc  bool   `json:"isDesc"`
	Value   string `json:"value"`   // current-language value
	Alt     string `json:"alt"`     // the OTHER language's value (side-by-side ref)
	AltLang string `json:"altLang"` // code of the alt column
}

// TranslationSet is the full payload for the translations editor.
type TranslationSet struct {
	Lang    string     `json:"lang"`
	AltLang string     `json:"altLang"`
	Rows    []TransRow `json:"rows"`
	Error   string     `json:"error"`
}

// GetTranslations returns every content.* translation row for the active
// language, with the other supported language shown alongside as a reference.
func (a *App) GetTranslations() TranslationSet {
	lang := a.lang
	if lang == "" {
		lang = "en"
	}
	altLang := "fr"
	if lang == "fr" {
		altLang = "en"
	}

	cur, err := a.loadLangProps(lang)
	if err != nil {
		return TranslationSet{Lang: lang, AltLang: altLang, Error: err.Error()}
	}
	alt, _ := a.loadLangProps(altLang) // best-effort reference column

	catMeta := map[int]TransCategory{}
	for _, c := range transCategories {
		catMeta[c.Cat] = c
	}

	// Collect the union of ids that appear in either language for our categories.
	type ck struct {
		cat int
		id  int32
	}
	seen := map[ck]bool{}
	collect := func(m map[string]string) {
		for key := range m {
			cat, id, ok := parseCatID(key)
			if !ok {
				continue
			}
			if _, editable := catMeta[cat]; !editable {
				continue
			}
			seen[ck{cat, id}] = true
		}
	}
	collect(cur)
	if alt != nil {
		collect(alt)
	}

	rows := make([]TransRow, 0, len(seen))
	for k := range seen {
		meta := catMeta[k.cat]
		rows = append(rows, TransRow{
			Cat:     k.cat,
			ID:      k.id,
			Label:   meta.Label,
			Kind:    meta.Kind,
			IsDesc:  meta.IsDesc,
			Value:   cur[i18nKey(k.cat, k.id)],
			Alt:     valueOf(alt, k.cat, k.id),
			AltLang: altLang,
		})
	}
	sort.Slice(rows, func(i, j int) bool {
		if rows[i].Cat != rows[j].Cat {
			return rows[i].Cat < rows[j].Cat
		}
		return rows[i].ID < rows[j].ID
	})

	return TranslationSet{Lang: lang, AltLang: altLang, Rows: rows}
}

func valueOf(m map[string]string, cat int, id int32) string {
	if m == nil {
		return ""
	}
	return m[i18nKey(cat, id)]
}

// TransEdit is one value the UI changed (or a new key it added).
type TransEdit struct {
	Cat   int    `json:"cat"`
	ID    int32  `json:"id"`
	Value string `json:"value"`
}

// SaveTranslations applies the edits to texts_<lang>.properties inside
// i18n.jar and repacks the jar (backup + atomic). Only the given language file
// is touched; the other language is untouched. Returns the repack result.
func (a *App) SaveTranslations(lang string, edits []TransEdit) (RepackResult, error) {
	if lang == "" {
		lang = a.lang
	}
	if lang == "" {
		lang = "en"
	}
	dir, err := a.contentsDir()
	if err != nil {
		return RepackResult{}, err
	}
	jarPath := filepath.Join(dir, "i18n.jar")
	entryPath := "i18n/texts_" + lang + ".properties"

	// Read the raw properties file so we can preserve unrelated lines exactly.
	r, err := a.openNamedJar("i18n.jar")
	if err != nil {
		return RepackResult{}, err
	}
	f := findEntry(r, entryPath)
	if f == nil {
		return RepackResult{}, fmt.Errorf("i18n: %s not found in i18n.jar", entryPath)
	}
	raw, err := readZipEntry(f, 16<<20)
	if err != nil {
		return RepackResult{}, err
	}

	editMap := map[string]string{}
	for _, e := range edits {
		editMap["content."+i18nKey(e.Cat, e.ID)] = e.Value
	}

	newRaw := rewriteProperties(raw, editMap)

	// Release the cached i18n.jar handle BEFORE repack: on Windows a live
	// open handle blocks renaming the temp jar over the original.
	a.jars.invalidate(jarPath)

	res, err := repackJar(jarPath, []RepackReplacement{{EntryPath: entryPath, Data: newRaw}})
	if err != nil {
		return res, err
	}
	// Drop the cached i18n name table so name lookups reflect the edit.
	a.i18nMu.Lock()
	a.i18n = nil
	a.i18nMu.Unlock()
	return res, nil
}

// loadLangProps parses one language's content.* table (not cached; used by the
// editor which must see edits reflected). Returns a map keyed "<cat>.<id>".
func (a *App) loadLangProps(lang string) (map[string]string, error) {
	r, err := a.openNamedJar("i18n.jar")
	if err != nil {
		return nil, err
	}
	entryPath := "i18n/texts_" + lang + ".properties"
	f := findEntry(r, entryPath)
	if f == nil {
		return nil, fmt.Errorf("i18n: %s not found", entryPath)
	}
	raw, err := readZipEntry(f, 16<<20)
	if err != nil {
		return nil, err
	}
	return parseProperties(raw), nil
}

// parseCatID splits a "<cat>.<id>" sub-key (the part after "content.") into ints.
func parseCatID(subOrFull string) (cat int, id int32, ok bool) {
	sub := strings.TrimPrefix(subOrFull, "content.")
	dot := strings.IndexByte(sub, '.')
	if dot <= 0 || dot == len(sub)-1 {
		return 0, 0, false
	}
	c, err1 := strconv.Atoi(sub[:dot])
	i, err2 := strconv.ParseInt(sub[dot+1:], 10, 32)
	if err1 != nil || err2 != nil {
		return 0, 0, false
	}
	return c, int32(i), true
}

// rewriteProperties rewrites a .properties byte buffer, replacing the VALUE of
// any key present in edits, preserving every other line (comments, blanks,
// unrelated keys, and original order) verbatim. Keys in edits that don't yet
// exist are appended at the end. Values are re-escaped for .properties.
func rewriteProperties(raw []byte, edits map[string]string) []byte {
	// Detect the original newline style so we don't churn CRLF<->LF.
	nl := "\n"
	if strings.Contains(string(raw), "\r\n") {
		nl = "\r\n"
	}
	lines := strings.Split(strings.ReplaceAll(string(raw), "\r\n", "\n"), "\n")

	applied := map[string]bool{}
	var out []string
	i := 0
	for i < len(lines) {
		line := lines[i]
		trimmed := strings.TrimLeft(line, " \t")
		// Skip/keep comments and blanks verbatim.
		if trimmed == "" || strings.HasPrefix(trimmed, "#") || strings.HasPrefix(trimmed, "!") {
			out = append(out, line)
			i++
			continue
		}
		// Join logical line across backslash-continuations to find its key.
		joined := line
		j := i
		for countTrailingBackslashes(joined)%2 == 1 {
			j++
			if j >= len(lines) {
				break
			}
			joined = joined[:len(joined)-1] + strings.TrimLeft(lines[j], " \t")
		}
		key, _, ok := splitProperty(joined)
		if ok {
			if newVal, found := edits[key]; found {
				out = append(out, key+"="+encodePropertyValue(newVal))
				applied[key] = true
				i = j + 1
				continue
			}
		}
		// Unedited: emit the original physical lines [i..j] untouched.
		for k := i; k <= j && k < len(lines); k++ {
			out = append(out, lines[k])
		}
		i = j + 1
	}

	// Append brand-new keys not seen in the file.
	var newKeys []string
	for k := range edits {
		if !applied[k] {
			newKeys = append(newKeys, k)
		}
	}
	sort.Strings(newKeys)
	for _, k := range newKeys {
		out = append(out, k+"="+encodePropertyValue(edits[k]))
	}

	return []byte(strings.Join(out, nl))
}

// encodePropertyValue re-applies the minimal Java .properties value escaping the
// client's reader expects: newlines/tabs as \n/\t, and non-ASCII as \uXXXX
// (the source files are ASCII with unicode-escaped accents). A literal
// backslash is doubled. Leading spaces are escaped so they're preserved.
func encodePropertyValue(v string) string {
	var b strings.Builder
	for idx, r := range v {
		switch r {
		case '\\':
			b.WriteString("\\\\")
		case '\n':
			b.WriteString("\\n")
		case '\r':
			b.WriteString("\\r")
		case '\t':
			b.WriteString("\\t")
		case ' ':
			if idx == 0 {
				b.WriteString("\\ ")
			} else {
				b.WriteByte(' ')
			}
		default:
			if r < 0x20 || r > 0x7e {
				// escape control + non-ASCII as \uXXXX (matches the shipped files)
				if r > 0xffff {
					// encode as surrogate pair
					r1, r2 := utf16Pair(r)
					fmt.Fprintf(&b, "\\u%04x\\u%04x", r1, r2)
				} else {
					fmt.Fprintf(&b, "\\u%04x", r)
				}
			} else {
				b.WriteRune(r)
			}
		}
	}
	return b.String()
}

func utf16Pair(r rune) (uint16, uint16) {
	r -= 0x10000
	return uint16(0xd800 + (r >> 10)), uint16(0xdc00 + (r & 0x3ff))
}
