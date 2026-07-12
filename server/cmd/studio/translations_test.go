package main

import (
	"strings"
	"testing"
)

// TestRewriteProperties_NoEditPreservesEverything asserts that rewriting with
// no edits yields a byte-identical file (comments, order, blank lines kept).
func TestRewriteProperties_NoEditPreservesEverything(t *testing.T) {
	src := "# header comment\n" +
		"content.3.141=High-Energy Shot\n" +
		"\n" +
		"! bang comment\n" +
		"content.4.141=Deals fire damage\n" +
		"unrelated.key=keep me\n"
	got := string(rewriteProperties([]byte(src), map[string]string{}))
	if got != src {
		t.Errorf("no-edit rewrite changed the file:\n--- want ---\n%q\n--- got ---\n%q", src, got)
	}
}

// TestRewriteProperties_EditsValueOnly changes one value and confirms only that
// line changes, order/comments preserved.
func TestRewriteProperties_EditsValueOnly(t *testing.T) {
	src := "content.3.1=Old Name\ncontent.4.1=A description\n# note\n"
	edits := map[string]string{"content.3.1": "New Name"}
	got := string(rewriteProperties([]byte(src), edits))
	want := "content.3.1=New Name\ncontent.4.1=A description\n# note\n"
	if got != want {
		t.Errorf("edit rewrite:\n want %q\n  got %q", want, got)
	}
}

// TestRewriteProperties_AppendsNewKey adds a key not present in the file.
func TestRewriteProperties_AppendsNewKey(t *testing.T) {
	src := "content.3.1=Existing\n"
	edits := map[string]string{"content.3.2": "Brand New"}
	got := string(rewriteProperties([]byte(src), edits))
	if !strings.Contains(got, "content.3.1=Existing") || !strings.Contains(got, "content.3.2=Brand New") {
		t.Errorf("append new key failed: %q", got)
	}
}

// TestEncodePropertyValue_RoundTrip confirms a value with accents/newlines
// survives encode -> parse unchanged.
func TestEncodePropertyValue_RoundTrip(t *testing.T) {
	cases := []string{
		"Simple",
		"Feu \u00e9l\u00e9mentaire", // accented (French)
		"Line one\nline two",        // newline
		"Tab\there",                 // tab
		"Back\\slash",               // literal backslash
		" leading space",            // leading space
	}
	for _, in := range cases {
		enc := encodePropertyValue(in)
		// Build a minimal properties doc and parse it back.
		props := parseProperties([]byte("content.3.99=" + enc + "\n"))
		got := props["3.99"]
		if got != in {
			t.Errorf("round-trip %q: encoded=%q decoded=%q", in, enc, got)
		}
	}
}

// TestRewriteProperties_RealFileNoEdit rewrites the REAL texts_en.properties
// with no edits and confirms every content.* key still parses to the same
// value -- a strong guard that the escape encoder doesn't mangle the shipped
// accented/placeholder strings. Skips when the client isn't present.
func TestRewriteProperties_RealFileNoEdit(t *testing.T) {
	a := newAppWithClient(t)
	before, err := a.loadLangProps("en")
	if err != nil {
		t.Skipf("no en properties: %v", err)
	}
	r, err := a.openNamedJar("i18n.jar")
	if err != nil {
		t.Fatalf("open i18n.jar: %v", err)
	}
	f := findEntry(r, "i18n/texts_en.properties")
	if f == nil {
		t.Skip("texts_en.properties not found")
	}
	raw, err := readZipEntry(f, 16<<20)
	if err != nil {
		t.Fatalf("read: %v", err)
	}
	rewritten := rewriteProperties(raw, map[string]string{})
	after := parseProperties(rewritten)
	if len(after) != len(before) {
		t.Errorf("content key count changed: before=%d after=%d", len(before), len(after))
	}
	for k, v := range before {
		if after[k] != v {
			t.Errorf("key %s changed: %q -> %q", k, v, after[k])
		}
	}
}

// TestRewriteProperties_HandlesContinuation preserves a backslash-continued
// multi-line value when it is NOT edited.
func TestRewriteProperties_HandlesContinuation(t *testing.T) {
	src := "content.4.1=first part \\\n  second part\ncontent.3.1=Name\n"
	got := string(rewriteProperties([]byte(src), map[string]string{"content.3.1": "New"}))
	if !strings.Contains(got, "first part \\") || !strings.Contains(got, "second part") {
		t.Errorf("continuation not preserved: %q", got)
	}
	if !strings.Contains(got, "content.3.1=New") {
		t.Errorf("edit after continuation not applied: %q", got)
	}
}
