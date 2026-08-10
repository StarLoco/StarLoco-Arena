package protocol

import (
	"strings"
	"testing"
)

// TestStringU8TruncatesAtTheSignedByteLimit: several 2.70 decoders read this
// length prefix as a SIGNED byte, so a 128-byte payload would present -128 and
// crash the client's reader. Enforcing it in the writer makes every call site
// safe by construction — chat echoes an attacker-supplied channel NAME through
// StringU8, so an unenforced limit was a remote client-crash vector.
func TestStringU8TruncatesAtTheSignedByteLimit(t *testing.T) {
	for _, n := range []int{0, 1, 126, 127, 128, 255, 1000} {
		got := NewWriter().StringU8(strings.Repeat("a", n)).Bytes()
		want := n
		if want > MaxStringU8 {
			want = MaxStringU8
		}
		if len(got) != want+1 {
			t.Errorf("input %d bytes: frame = %d bytes, want %d", n, len(got), want+1)
			continue
		}
		if int(got[0]) != want {
			t.Errorf("input %d bytes: length prefix = %d, want %d", n, got[0], want)
		}
		if got[0] > MaxStringU8 {
			t.Errorf("input %d bytes: prefix %d reads as %d when signed",
				n, got[0], int8(got[0]))
		}
	}
}

// TestStringU8CountsEncodedBytes: the prefix counts WIRE bytes, so the limit has
// to be applied after the cp1252 encoding, not to the Go string's UTF-8 length.
func TestStringU8CountsEncodedBytes(t *testing.T) {
	// "é" is 2 bytes in UTF-8 but 1 byte in cp1252.
	got := NewWriter().StringU8(strings.Repeat("é", 100)).Bytes()
	if int(got[0]) != 100 || len(got) != 101 {
		t.Errorf("100 accented chars -> prefix %d / %d bytes, want 100 / 101 (cp1252 is single-byte)",
			got[0], len(got))
	}
	// 200 of them still fit under the cap only after encoding; they must clamp
	// to exactly 127 encoded bytes.
	got = NewWriter().StringU8(strings.Repeat("é", 200)).Bytes()
	if int(got[0]) != MaxStringU8 || len(got) != MaxStringU8+1 {
		t.Errorf("200 accented chars -> prefix %d / %d bytes, want %d / %d",
			got[0], len(got), MaxStringU8, MaxStringU8+1)
	}
}
