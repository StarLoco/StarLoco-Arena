package game

import (
	"regexp"
	"strings"
	"unicode"
	"unicode/utf8"
)

// Name validation for every player-authored DISPLAY NAME (coach, fighter, team,
// guild, guild rank).
//
// Threat model: the client is hostile. None of the client's own input widgets
// exist as far as this server is concerned - the retail client restricts its name
// fields with `restrict="[.*&[^<>]]"`, but a modified client simply does not, so
// every rule the UI appears to enforce has to be re-enforced here.
//
// Four distinct problems, all confirmed present before this file:
//
//  1. EMPTY NAMES. CoachRepo.Create applied only strings.TrimSpace, so a
//     whitespace-only name collapsed to "" and was accepted. sendGuildMembers
//     already had to skip blank member names, which is the symptom of exactly
//     this leaking through.
//
//  2. MARKUP INJECTION. The client's chat renderer parses <b>, <c>, <text
//     color=...> and <image pixmap=...> in message bodies AND in sender names,
//     with no escaping (B-104). A name is echoed to other players in far more
//     places than chat, so stripping angle brackets at the source is the only
//     reliable fix. teamUpName already did this for 2v2 names and cited B-104;
//     the coach and fighter paths did not.
//
//  3. CONTROL CHARACTERS AND INVISIBLES. cp1252 (the wire charset) maps C0
//     controls 1:1 and includes U+00AD SOFT HYPHEN, which is not
//     unicode.IsSpace and so survived TrimSpace. Both let an attacker register a
//     name that RENDERS identically to a target's - "Ad<U+00AD>min" is a
//     distinct DB row that can display as "Admin". Homoglyph impersonation of a
//     moderator is the concrete risk.
//
//  4. BYTE-BOUNDARY TRUNCATION. sanitizeFighterName cut at 16 BYTES, which
//     splits a multi-byte rune and persists invalid UTF-8. Its siblings
//     (sanitizeChatText, Writer.StringU8UTF8) all walk back to a rune boundary.
//
// The maxima are ours, not the client's: nothing on the wire enforces them (the
// u8 length prefix allows 255 bytes), so they exist to keep names renderable and
// to bound storage.
const (
	maxCoachNameLen     = 24
	maxTeamNameLen      = 32
	maxGuildNameLen     = 32
	maxGuildRankNameLen = 24
)

// sanitizeDisplayName strips markup, removes characters that are invisible or
// unrenderable, collapses internal whitespace runs, trims, and truncates on a
// RUNE boundary. It reports false when nothing usable is left, so callers can
// reject rather than silently store a blank.
//
// It deliberately does not attempt full Unicode confusable folding - that needs a
// table this project does not ship. It removes the invisibles that cp1252 can
// actually transmit, which is the reachable half of the problem.
func sanitizeDisplayName(name string, maxBytes int) (string, bool) {
	// Drop markup delimiters and anything invisible or non-printable. Zero-width
	// and RTL-override runes are included even though EncodeText would turn them
	// into '?', because the DB row would still be a distinct name.
	name = strings.Map(func(r rune) rune {
		switch {
		case r == '<' || r == '>':
			return -1
		case r == '\u00ad': // SOFT HYPHEN: not IsSpace, often renders as nothing
			return -1
		case r == '\u200b' || r == '\u200c' || r == '\u200d' || r == '\ufeff':
			return -1 // zero-width space / non-joiner / joiner / BOM
		case r >= '\u202a' && r <= '\u202e':
			return -1 // bidi embedding + RTL override
		case r == utf8.RuneError:
			return -1 // already-invalid input
		case unicode.IsControl(r):
			return -1 // C0/C1, including newlines
		case !unicode.IsPrint(r) && !unicode.IsSpace(r):
			return -1
		}
		return r
	}, name)

	// Collapse internal whitespace runs to single spaces so "A        B" cannot
	// masquerade as "A B" (and so a name cannot be padded to look centred).
	name = strings.Join(strings.Fields(name), " ")

	if len(name) > maxBytes {
		cut := maxBytes
		for cut > 0 && !utf8.RuneStart(name[cut]) {
			cut--
		}
		name = strings.TrimRight(name[:cut], " ")
	}
	if name == "" {
		return "", false
	}
	return name, true
}

// sanitizeNameWithFallback is for the paths that must always yield SOMETHING
// (fighters and teams are auto-named by the client, and rejecting them would
// break a legitimate flow). The fallback matches what the client itself uses.
func sanitizeNameWithFallback(name string, maxBytes int, fallback string) string {
	if clean, ok := sanitizeDisplayName(name, maxBytes); ok {
		return clean
	}
	return fallback
}

// coachNameRE mirrors the retail client's OWN coach-name rule, taken from
// aBC.validateCoachCreationForm:
//
//	string.length() <= 20 && aet_0.dDM.matcher(string).matches() && avQ.jR(string)
//	aet_0.dDM = Pattern.compile("([\\p{L}]|[\\p{L}][-]){2,}\\p{L}", 64)
//
// i.e. Unicode LETTERS and hyphens only, at least 3 characters, ending in a
// letter. Go's regexp is RE2 and supports \p{L}, so the pattern transfers
// verbatim; the client's flag 64 is UNICODE_CASE, which is irrelevant here as
// the pattern has no case-sensitive literals.
//
// Enforcing the client's own rule rather than inventing one is deliberate. It
// makes the server agree with the UI a legitimate player sees, and it closes the
// whole invisible-character class for free: soft hyphen, zero-width spaces, bidi
// overrides, C0 controls, '<' and '>' are none of them Unicode letters, so they
// cannot appear in a coach name at all.
//
// The client's third check (avQ.jR, a forbidden-word pattern) is NOT replicated:
// its word list lives in client resources we do not ship, and a wrong guess would
// reject legitimate names. Profanity is a moderation concern, not a security one.
// The client's exact rule, kept for reference and for any operator who wants
// strict retail parity. NOTE this admits NO DIGITS.
var clientCoachNameRE = regexp.MustCompile(`^([\p{L}]|[\p{L}][-]){2,}\p{L}$`)

// coachNameRE is what the SERVER enforces: a deliberate SUPERSET of the client's
// rule - Unicode letters, digits, hyphen, apostrophe and single internal spaces,
// starting and ending alphanumeric.
//
// Why not mirror the client exactly? Two reasons, and the distinction matters:
//
//   - Being STRICTER than the client would reject names a legitimate retail
//     client can produce, breaking real players. Being LOOSER only risks
//     accepting a name retail would not have created, which is a cosmetic
//     concern, not a security one.
//   - Everything the client's rule buys us SECURITY-wise is already bought here.
//     The attacks were empty names, markup injection and invisible-character
//     impersonation; digits enable none of them. Rejecting "Combatant1" would be
//     authenticity theatre.
//
// An operator wanting retail parity can swap in clientCoachNameRE above.
var coachNameRE = regexp.MustCompile(`^[\p{L}\p{N}][\p{L}\p{N}'-]*( [\p{L}\p{N}'-]+)*$`)

// maxCoachNameRunes is the client's own cap (aBC: string.length() <= 20). Java's
// String.length() counts UTF-16 code units; for the BMP letters this pattern
// admits, that equals the rune count.
const maxCoachNameRunes = 20

// minCoachNameRunes matches the client's own minimum: its pattern
// ([\p{L}]|[\p{L}][-]){2,}\p{L} cannot match fewer than 3 characters. A 1- or
// 2-character name is a real confusion vector ("I" vs "l" vs "1"), so this is one
// place where agreeing with the client is also the safer choice.
const minCoachNameRunes = 3

// validateCoachName reports whether a client-supplied coach name is acceptable.
//
// SECURITY: before this, CoachRepo.Create applied only strings.TrimSpace, so an
// EMPTY name was accepted (TrimSpace(" ") == "", uniqueness count 0, NOT NULL
// satisfied by the empty string). Control characters, markup and U+00AD were all
// storable too, the last two giving a name that renders identically to another
// player's - impersonating a moderator is the concrete risk.
//
// Returns the trimmed name so the caller stores exactly what was validated.
func validateCoachName(name string) (string, bool) {
	name = strings.TrimSpace(name)
	if name == "" {
		return "", false
	}
	n := utf8.RuneCountInString(name)
	if n < minCoachNameRunes || n > maxCoachNameRunes {
		return "", false
	}
	if !coachNameRE.MatchString(name) {
		return "", false
	}
	// At least one letter: blocks all-digit and punctuation-only names, which are
	// trivially confusable with each other and with ids.
	if !strings.ContainsFunc(name, unicode.IsLetter) {
		return "", false
	}
	return name, true
}
