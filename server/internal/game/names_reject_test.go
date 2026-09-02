package game

import (
	"strings"
	"testing"
)

// hostileRunes are the characters a display name must never carry to another
// client: markup delimiters (the renderer parses them unescaped, B-104), control
// characters, and invisibles usable for impersonation.
const hostileRunes = "<>\n\r\x00\u00ad\u200b\u202e"

// TestEmptyFighterAndTeamNamesAreRejected pins the rule: a name that is empty -
// or becomes empty once hostile characters are removed - is REFUSED, never
// silently renamed. sanitizeFighterName used to return "Noob" for empty input,
// which accepted the input and merely disguised it.
func TestEmptyFighterAndTeamNamesAreRejected(t *testing.T) {
	blank := []struct{ name, why string }{
		{"", "empty"},
		{"   ", "spaces only"},
		{"\t\n", "control whitespace only"},
		{"\u00ad\u200b", "invisible characters only"},
		{"<>", "markup delimiters only"},
		{"123", "digits only - no letter to distinguish it"},
		{"A", "below the 2-rune minimum"},
	}
	for _, tc := range blank {
		if got, ok := validateFighterName(tc.name); ok {
			t.Errorf("validateFighterName(%q) accepted %q; want rejection (%s)", tc.name, got, tc.why)
		}
	}
	for _, tc := range blank[:5] { // teams allow digit-only and 1-rune names
		if got, ok := validateTeamName(tc.name); ok {
			t.Errorf("validateTeamName(%q) accepted %q; want rejection (%s)", tc.name, got, tc.why)
		}
	}
}

// TestHostileFighterNamesAreNeutralised records the deliberate model: hostile
// input is STRIPPED, not refused, and refused only if nothing usable survives.
//
// My first version of this test asserted rejection and was wrong. Neutralising is
// the better behaviour, and "Ad\u00admin" shows why: it collapses to "Admin", so
// the impersonation attempt lands on the real name and is then subject to
// whatever uniqueness applies - rather than being stored as a separate row that
// renders identically. What matters is that nothing hostile reaches another
// client, which is what this asserts.
func TestHostileFighterNamesAreNeutralised(t *testing.T) {
	hostile := []string{
		"<image pixmap=evil>",
		"<b>Pwn</b>",
		"Io\x00p",
		"Io\np",
		"Ad\u00admin",
		"Ad\u200bmin",
		"Ad\u202emin",
	}
	for _, name := range hostile {
		got, ok := validateFighterName(name)
		if !ok {
			continue // refused outright is also acceptable
		}
		if strings.ContainsAny(got, hostileRunes) {
			t.Errorf("validateFighterName(%q) = %q, which still carries hostile characters", name, got)
		}
	}

	// The impersonation case specifically must normalise onto the plain name.
	if got, _ := validateFighterName("Ad\u00admin"); got != "Admin" {
		t.Errorf("soft-hyphen name normalised to %q, want %q", got, "Admin")
	}
}

// TestLegitimateFighterNamesSurvive guards against over-rejection.
func TestLegitimateFighterNamesSurvive(t *testing.T) {
	for _, name := range []string{"Iop", "Cra Two", "Jean-Luc", "O'Brien", "Ren\u00e9e", "Tank1"} {
		got, ok := validateFighterName(name)
		if !ok {
			t.Errorf("validateFighterName(%q) rejected a legitimate name", name)
			continue
		}
		if got != name {
			t.Errorf("validateFighterName(%q) altered it to %q", name, got)
		}
	}
}

// TestOverlongFighterNameIsTruncatedNotRejected records the split: overlong is
// CLIPPED, empty is REFUSED. Truncation loses nothing a player cares about;
// renaming an empty field to "Noob" hid an attack.
func TestOverlongFighterNameIsTruncatedNotRejected(t *testing.T) {
	got, ok := validateFighterName(strings.Repeat("Z", 40))
	if !ok {
		t.Fatal("an overlong but otherwise valid name should be truncated, not refused")
	}
	if len(got) > maxFighterNameLen {
		t.Errorf("name %q is %d bytes, over the %d cap", got, len(got), maxFighterNameLen)
	}
}

// TestGuildNamesAreValidated covers the guild side: no maximum, no control filter
// and no markup strip existed, while the name is broadcast to every online
// session on creation.
func TestGuildNamesAreValidated(t *testing.T) {
	for _, name := range []string{"<b>Elite</b>", "Eli\u00adte", "Eli\nte"} {
		clean, ok := sanitizeDisplayName(name, maxGuildNameLen)
		if ok && strings.ContainsAny(clean, hostileRunes) {
			t.Errorf("guild name %q sanitised to %q, which still carries hostile characters", name, clean)
		}
	}
	for _, name := range []string{"", "   ", "<>"} {
		if _, ok := sanitizeDisplayName(name, maxGuildNameLen); ok {
			t.Errorf("guild name %q should not survive sanitisation", name)
		}
	}
	if clean, ok := sanitizeDisplayName(strings.Repeat("G", 100), maxGuildNameLen); !ok || len(clean) > maxGuildNameLen {
		t.Errorf("overlong guild name not capped: ok=%v len=%d", ok, len(clean))
	}
}
