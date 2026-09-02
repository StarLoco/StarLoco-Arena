package game

import (
	"strings"
	"testing"
	"unicode/utf8"
)

// TestValidateCoachNameRejectsHostileInput is the SECURITY regression test for
// coach naming. Before this, CoachRepo.Create applied only strings.TrimSpace.
func TestValidateCoachNameRejectsHostileInput(t *testing.T) {
	reject := []struct{ name, why string }{
		{"", "empty"},
		{"   ", "whitespace only - TrimSpace collapsed this to \"\" and stored it"},
		{"\t\n", "control whitespace only"},
		{"A", "too short to be distinguishable"},
		{"<b>Admin</b>", "markup: the client renderer parses tags in NAMES unescaped (B-104)"},
		{"<image pixmap=x>", "markup: image injection"},
		{"Ad\u00admin", "U+00AD soft hyphen: not IsSpace, renders invisibly -> reads as 'Admin'"},
		{"Ad\u200bmin", "zero-width space: same impersonation trick"},
		{"Ad\u202emin", "RTL override: reorders the displayed name"},
		{"Admin\x00", "NUL"},
		{"Ad\nmin", "newline in a display name"},
		{"Ad\x07min", "C0 control (BEL)"},
		{"123", "all digits: no letter at all"},
		{"---", "punctuation only"},
		{strings.Repeat("A", 21), "over the client's own 20-character cap"},
		{"Admin  Two", "double internal space: pads to mimic another name"},
	}
	for _, tc := range reject {
		got, ok := validateCoachName(tc.name)
		if ok {
			t.Errorf("validateCoachName(%q) accepted %q; must reject (%s)", tc.name, got, tc.why)
		}
	}

	accept := []string{
		"Alice", "Bob", "Chrono", "Combatant1", "Jean-Luc",
		"O'Brien", "Zoe Two", "Ren\u00e9e", // accented letters must survive
	}
	for _, name := range accept {
		if _, ok := validateCoachName(name); !ok {
			t.Errorf("validateCoachName(%q) rejected a legitimate name", name)
		}
	}
}

// TestValidateCoachNameTrimsWhatItReturns pins that the caller stores exactly the
// validated string - not the raw input, which would re-open padding tricks.
func TestValidateCoachNameTrimsWhatItReturns(t *testing.T) {
	// A padded-but-valid name is ACCEPTED and stored trimmed. That is correct,
	// not a uniqueness bypass: the trimmed form is what the caller passes to the
	// LOWER(name) uniqueness check, so " Admin" and "Admin" collide as they must.
	got, ok := validateCoachName("  Alice  ")
	if !ok {
		t.Fatal("padded but otherwise valid name should be accepted after trimming")
	}
	if got != "Alice" {
		t.Errorf("returned %q, want %q - the caller must persist the trimmed form", got, "Alice")
	}
}

// TestSanitizeFighterNameIsRuneSafe covers the byte-boundary truncation bug: the
// old code cut at 16 BYTES, splitting a multi-byte rune and persisting invalid
// UTF-8 to the database.
func TestSanitizeFighterNameIsRuneSafe(t *testing.T) {
	// 16 two-byte runes: a byte cut at 16 lands mid-rune.
	long := strings.Repeat("\u00e9", 16)
	got := sanitizeFighterName(long)
	if !utf8.ValidString(got) {
		t.Errorf("sanitizeFighterName produced invalid UTF-8: %q", got)
	}
	if len(got) > maxFighterNameLen {
		t.Errorf("name %q is %d bytes, over the %d cap", got, len(got), maxFighterNameLen)
	}

	if got := sanitizeFighterName("<b>Pwn</b>"); strings.ContainsAny(got, "<>") {
		t.Errorf("fighter name %q still carries markup", got)
	}
	if got := sanitizeFighterName("   "); got != "Noob" {
		t.Errorf("blank fighter name = %q, want the client's own %q fallback", got, "Noob")
	}
	if got := sanitizeFighterName("Iop\u0000"); strings.ContainsRune(got, 0) {
		t.Errorf("fighter name %q still carries a NUL", got)
	}
}

// TestClientCoachRuleIsStricterThanOurs documents the deliberate divergence: we
// accept a superset. If this ever fails, the two rules have drifted in a way
// nobody decided - being STRICTER than the client would reject real players.
func TestClientCoachRuleIsStricterThanOurs(t *testing.T) {
	for _, name := range []string{"Alice", "Jean-Luc", "Chrono"} {
		if !clientCoachNameRE.MatchString(name) {
			t.Errorf("%q should satisfy the client's own rule", name)
		}
		if _, ok := validateCoachName(name); !ok {
			t.Errorf("%q satisfies the CLIENT rule but our server rejects it - "+
				"the server must never be stricter than the client", name)
		}
	}
	// And the documented deviation really is a deviation.
	if clientCoachNameRE.MatchString("Combatant1") {
		t.Error("client rule unexpectedly admits digits; the comment explaining " +
			"our superset is now wrong")
	}
}
