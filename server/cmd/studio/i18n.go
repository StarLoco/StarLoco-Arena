package main

import (
	"bufio"
	"bytes"
	"fmt"
	"strconv"
	"strings"
	"sync"
	"unicode/utf8"
)

// This file resolves the human-readable names/descriptions the client stores
// in i18n.jar/i18n/texts_<lang>.properties, so the studio can show "Real Name
// (id)" instead of bare numeric ids everywhere an id maps to a name.
//
// Key scheme (confirmed empirically against the real file by cross-checking
// counts + known ids): keys are "content.<category>.<id>=Value", where each
// data type occupies a fixed category number, and names/descriptions live in
// adjacent categories:
//
//	category  name / desc     data type          (real count)
//	1 / 2     fighter cards   FighterCardTemplate (127)
//	3 / 4     spells          SpellTemplate       (142)
//	5 / 6     breeds          the 12 Dofus classes (12)
//	7         effect labels   EffectDef templates  (91)
//	10        summons         SummoningTemplate    (10)
//	14 / 15   events/bonuses  EventTemplate        (68 labels)
//	23 / 24   coach cards     CoachCardTemplate    (181)
//
// texts_en.properties / texts_fr.properties both exist; the active language
// is an App setting (see config.go / SetLanguage).
const (
	catFighterCardName = 1
	catFighterCardDesc = 2
	catSpellName       = 3
	catSpellDesc       = 4
	catBreedName       = 5
	catBreedDesc       = 6
	catEffectName      = 7
	catSummonName      = 10
	catEventName       = 14
	catEventDesc       = 15
	catCoachCardName   = 23
	catCoachCardDesc   = 24
)

// i18nStore holds one language's parsed content.* table, keyed
// "<category>.<id>" -> value. Built lazily from the client jar and cached.
type i18nStore struct {
	mu      sync.RWMutex
	lang    string
	entries map[string]string
	loaded  bool
	loadErr error
}

// key builds the lookup key for a (category,id) pair.
func i18nKey(category int, id int32) string {
	return strconv.Itoa(category) + "." + strconv.FormatInt(int64(id), 10)
}

// lookup returns the value for (category,id), or "" if absent/blank.
func (s *i18nStore) lookup(category int, id int32) string {
	s.mu.RLock()
	defer s.mu.RUnlock()
	return s.entries[i18nKey(category, id)]
}

// ensureLoaded parses texts_<lang>.properties from i18n.jar on first use.
func (a *App) ensureI18n() *i18nStore {
	a.i18nMu.Lock()
	defer a.i18nMu.Unlock()

	lang := a.lang
	if lang == "" {
		lang = "en"
	}
	if a.i18n != nil && a.i18n.lang == lang {
		return a.i18n
	}

	store := &i18nStore{lang: lang, entries: map[string]string{}}
	a.i18n = store

	// The i18n jar is a client asset; if there's no valid client dir we just
	// return an empty (name-less) store rather than erroring the whole UI.
	r, err := a.openNamedJar("i18n.jar")
	if err != nil {
		store.loadErr = err
		store.loaded = true
		return store
	}
	entryPath := "i18n/texts_" + lang + ".properties"
	f := findEntry(r, entryPath)
	if f == nil {
		store.loadErr = fmt.Errorf("i18n: %s not found in i18n.jar", entryPath)
		store.loaded = true
		return store
	}
	raw, err := readZipEntry(f, 8<<20)
	if err != nil {
		store.loadErr = err
		store.loaded = true
		return store
	}
	store.entries = parseProperties(raw)
	store.loaded = true
	return store
}

// name resolves a (category,id) to its localized value, or "" if none.
func (a *App) name(category int, id int32) string {
	return a.ensureI18n().lookup(category, id)
}

// nameWithID formats "Name (id)" when a name exists, else just the id -- a
// convenience the DTO builders use so the frontend can render either.
func (a *App) nameWithID(category int, id int32) string {
	if n := a.name(category, id); n != "" {
		return fmt.Sprintf("%s (%d)", n, id)
	}
	return strconv.FormatInt(int64(id), 10)
}

// parseProperties parses a Java .properties byte buffer into a flat map,
// restricted to the "content.<cat>.<id>=value" keys we care about (stored
// under the "<cat>.<id>" sub-key). It handles the subset of the .properties
// spec these files actually use plus the common escapes defensively:
//   - '#'/'!' comment lines and blank lines are skipped
//   - key/value split on the first unescaped '=' or ':'
//   - trailing-backslash line continuations are joined
//   - \uXXXX, \n, \t, \r, \\, \=, \: escapes in values are decoded
func parseProperties(raw []byte) map[string]string {
	// Java .properties files are ISO-8859-1 (Latin-1): each byte 0x00-0xFF is
	// the Unicode code point of the same value. Reading them as UTF-8 mangles
	// accented characters (é 0xE9 -> replacement char). Transcode Latin-1 ->
	// UTF-8 up front so line scanning and value decoding see correct runes.
	// (Non-ASCII escapes still come through \uXXXX, handled in decode.)
	raw = latin1ToUTF8(raw)

	out := map[string]string{}
	sc := bufio.NewScanner(bytes.NewReader(raw))
	sc.Buffer(make([]byte, 64*1024), 4*1024*1024)

	var pending string
	haspending := false
	for sc.Scan() {
		line := sc.Text()
		if haspending {
			line = pending + strings.TrimLeft(line, " \t")
			haspending = false
			pending = ""
		}
		trimmed := strings.TrimLeft(line, " \t")
		if !haspending && (trimmed == "" || strings.HasPrefix(trimmed, "#") || strings.HasPrefix(trimmed, "!")) {
			continue
		}
		// line continuation: an odd number of trailing backslashes means join.
		if countTrailingBackslashes(line)%2 == 1 {
			pending = line[:len(line)-1]
			haspending = true
			continue
		}

		key, val, ok := splitProperty(line)
		if !ok {
			continue
		}
		if !strings.HasPrefix(key, "content.") {
			continue
		}
		sub := strings.TrimPrefix(key, "content.")
		// keep only "<cat>.<id>" numeric keys
		if !isCatIDKey(sub) {
			continue
		}
		out[sub] = decodePropertyValue(val)
	}
	return out
}

// latin1ToUTF8 transcodes an ISO-8859-1 byte buffer to UTF-8. Each input byte
// is a Unicode code point 0x00-0xFF, so bytes < 0x80 pass through and 0x80-0xFF
// become their 2-byte UTF-8 encoding. If the input is already valid ASCII this
// is a no-op copy.
func latin1ToUTF8(raw []byte) []byte {
	// Fast path: pure ASCII needs no change.
	ascii := true
	for _, b := range raw {
		if b >= 0x80 {
			ascii = false
			break
		}
	}
	if ascii {
		return raw
	}
	out := make([]byte, 0, len(raw)+len(raw)/8)
	for _, b := range raw {
		out = utf8.AppendRune(out, rune(b))
	}
	return out
}

func countTrailingBackslashes(s string) int {
	n := 0
	for i := len(s) - 1; i >= 0 && s[i] == '\\'; i-- {
		n++
	}
	return n
}

// splitProperty splits on the first unescaped '=' or ':' separator.
func splitProperty(line string) (key, val string, ok bool) {
	for i := 0; i < len(line); i++ {
		c := line[i]
		if c == '\\' {
			i++ // skip escaped char
			continue
		}
		if c == '=' || c == ':' {
			key = strings.TrimSpace(line[:i])
			val = strings.TrimLeft(line[i+1:], " \t")
			return key, val, key != ""
		}
	}
	return "", "", false
}

// isCatIDKey reports whether sub is "<digits>.<digits>".
func isCatIDKey(sub string) bool {
	dot := strings.IndexByte(sub, '.')
	if dot <= 0 || dot == len(sub)-1 {
		return false
	}
	return allDigits(sub[:dot]) && allDigits(sub[dot+1:])
}

func allDigits(s string) bool {
	if s == "" {
		return false
	}
	for i := 0; i < len(s); i++ {
		if s[i] < '0' || s[i] > '9' {
			return false
		}
	}
	return true
}

// decodePropertyValue applies .properties escape decoding to a value.
func decodePropertyValue(v string) string {
	if !strings.ContainsRune(v, '\\') {
		return v
	}
	var b strings.Builder
	for i := 0; i < len(v); i++ {
		c := v[i]
		if c != '\\' || i+1 >= len(v) {
			b.WriteByte(c)
			continue
		}
		i++
		switch v[i] {
		case 'n':
			b.WriteByte('\n')
		case 't':
			b.WriteByte('\t')
		case 'r':
			b.WriteByte('\r')
		case 'f':
			b.WriteByte('\f')
		case 'u':
			if i+4 < len(v) {
				if cp, err := strconv.ParseUint(v[i+1:i+5], 16, 32); err == nil {
					b.WriteRune(rune(cp))
					i += 4
					continue
				}
			}
			b.WriteByte('u')
		default:
			b.WriteByte(v[i])
		}
	}
	return b.String()
}
