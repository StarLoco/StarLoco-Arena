package protocol

// text.go — the wire TEXT ENCODING.
//
// Every string on this protocol is **windows-1252**, in both directions, and the
// reason is that the client never names a charset:
//
//	read:   this.setName(new String(byArray));      // aez_0.V, gn_0, sw_1, ta_0…
//	write:  byte[] byArray = this.bY.getBytes();    // acS, aey_0, afy_2…
//
// Both forms use the JVM's *platform default* charset. The shipped client runs on
// the bundled **Java 1.6**, whose default is the OS code page — never UTF-8 (that
// only became the Java default in 18). Confirmed at runtime against the live
// client via the control agent:
//
//	Charset.defaultCharset().name()  ->  windows-1252
//
// So a server that writes UTF-8 (as this one did) mangles every accented
// character it sends — "Défi" arrives as "DÃ©fi" — and mis-reads every accented
// name the player types, which is worse because that corruption gets persisted.
//
// windows-1252 is a superset of ISO-8859-1: identical for every French accent,
// differing only in 0x80–0x9F (typographic quotes, dashes, €). Encoding as
// latin-1 would be *nearly* right; matching cp1252 exactly costs nothing because
// x/text already ships the table.
//
// A rune with no cp1252 representation is written as '?', which is exactly what
// Java's own encoder does for an unmappable character — so the client sees the
// same thing a retail client would.

import (
	"strings"

	"golang.org/x/text/encoding/charmap"
)

// wireCharset is the client's platform default charset (see the file comment).
// Named rather than inlined so the one decision behind every string on the wire
// is visible and greppable.
var wireCharset = charmap.Windows1252

// EncodeText converts a Go (UTF-8) string into wire bytes.
func EncodeText(s string) []byte {
	if s == "" {
		return nil
	}
	if isASCII(s) {
		return []byte(s) // the common case: identical in both encodings
	}
	enc := wireCharset.NewEncoder()
	out, err := enc.Bytes([]byte(s))
	if err == nil {
		return out
	}
	// The string contains runes cp1252 cannot represent. Fall back to a
	// rune-by-rune pass that substitutes '?' for those, mirroring Java's
	// CharsetEncoder default, instead of dropping the string entirely.
	var b strings.Builder
	b.Grow(len(s))
	for _, r := range s {
		if _, ok := wireCharset.EncodeRune(r); ok {
			b.WriteRune(r)
		} else {
			b.WriteByte('?')
		}
	}
	out, err = enc.Bytes([]byte(b.String()))
	if err != nil {
		return []byte(s) // give up rather than lose the message
	}
	return out
}

// DecodeText converts wire bytes into a Go (UTF-8) string.
//
// It cannot fail: every one of the 256 byte values has a cp1252 meaning (the few
// undefined ones decode to U+FFFD), so a malformed name degrades to visible
// nonsense rather than an error, which matches how the client behaves.
func DecodeText(b []byte) string {
	if len(b) == 0 {
		return ""
	}
	if isASCIIBytes(b) {
		return string(b)
	}
	out, err := wireCharset.NewDecoder().Bytes(b)
	if err != nil {
		return string(b)
	}
	return string(out)
}

func isASCII(s string) bool {
	for i := 0; i < len(s); i++ {
		if s[i] >= 0x80 {
			return false
		}
	}
	return true
}

func isASCIIBytes(b []byte) bool {
	for _, c := range b {
		if c >= 0x80 {
			return false
		}
	}
	return true
}
