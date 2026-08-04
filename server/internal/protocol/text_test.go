package protocol

import "testing"

// TestWireTextIsWindows1252 pins the encoding the client actually uses. The
// client reads with `new String(bytes)` and writes with `String.getBytes()`,
// both of which take the JVM platform default — verified as windows-1252 against
// the live client (Java 1.6, so never UTF-8).
func TestWireTextIsWindows1252(t *testing.T) {
	cases := []struct {
		s    string
		want []byte
		why  string
	}{
		{"Defi", []byte{'D', 'e', 'f', 'i'}, "pure ASCII is unchanged"},
		{"Défi", []byte{'D', 0xE9, 'f', 'i'}, "é is ONE byte 0xE9, not the two UTF-8 bytes C3 A9"},
		{"Démon", []byte{'D', 0xE9, 'm', 'o', 'n'}, "the challenge opponent name"},
		{"Maître d'élevage", []byte{'M', 'a', 0xEE, 't', 'r', 'e', ' ', 'd', '\'', 0xE9, 'l', 'e', 'v', 'a', 'g', 'e'}, "î and é"},
		{"", nil, "empty stays empty"},
		{"€", []byte{0x80}, "the euro sign is what makes this cp1252 and not latin-1"},
	}
	for _, c := range cases {
		got := EncodeText(c.s)
		if len(got) != len(c.want) {
			t.Errorf("EncodeText(%q) = % X (%d bytes), want % X (%d) — %s",
				c.s, got, len(got), c.want, len(c.want), c.why)
			continue
		}
		for i := range c.want {
			if got[i] != c.want[i] {
				t.Errorf("EncodeText(%q) = % X, want % X — %s", c.s, got, c.want, c.why)
				break
			}
		}
	}
}

// TestWireTextRoundTrip: anything we can send we must be able to read back, and
// vice versa — the client both reads and writes with the same charset, so a
// player's accented name has to survive the round trip.
func TestWireTextRoundTrip(t *testing.T) {
	for _, s := range []string{
		"", "Loov", "Défi", "Démon", "Maître d'élevage", "Éphémère",
		"Ça va où ?", "Ångström", "naïve façade", "€uro", "L'Iop à l'œil",
	} {
		if got := DecodeText(EncodeText(s)); got != s {
			t.Errorf("round trip %q -> %q", s, got)
		}
	}
}

// TestWireTextLengthPrefixCountsBYTES guards the trap this change could
// introduce: the length prefix must count ENCODED bytes, not runes and not UTF-8
// bytes. A mismatch desyncs the whole frame after the string.
func TestWireTextLengthPrefixCountsBytes(t *testing.T) {
	const name = "Défi" // 4 runes, 5 bytes in UTF-8, 4 bytes in cp1252
	w := NewWriter().StringU8(name).U8(0xAB)
	b := w.Bytes()
	if b[0] != 4 {
		t.Errorf("length prefix = %d, want 4 (encoded bytes, not the 5 UTF-8 ones)", b[0])
	}
	if b[len(b)-1] != 0xAB {
		t.Error("the sentinel after the string was displaced — the frame is desynced")
	}

	r := NewReader(b)
	got, err := r.StringU8()
	if err != nil {
		t.Fatalf("read back: %v", err)
	}
	if got != name {
		t.Errorf("read back %q, want %q", got, name)
	}
	tail, err := r.U8()
	if err != nil || tail != 0xAB {
		t.Errorf("sentinel = %#x (%v), want 0xAB — the reader consumed the wrong length", tail, err)
	}
}

// TestWireTextU16AndU32Prefixes covers the wider prefixes the same way. There is
// no Reader.StringU16 (nothing inbound uses that width), so the u16 case is read
// back manually to prove the prefix counts ENCODED bytes.
func TestWireTextU16AndU32Prefixes(t *testing.T) {
	const name = "Démon" // 5 runes, 6 UTF-8 bytes, 5 cp1252 bytes

	w := NewWriter().StringU16(name)
	b := w.Bytes()
	if b[0] != 0 || b[1] != 5 {
		t.Errorf("u16 length prefix = %d, want 5 encoded bytes", int(b[0])<<8|int(b[1]))
	}
	r := NewReader(b)
	n, _ := r.U16()
	if got, err := r.String(int(n)); err != nil || got != name {
		t.Errorf("u16 round trip = %q (%v), want %q", got, err, name)
	}

	w = NewWriter().StringU32(name).U8(0xCD)
	r = NewReader(w.Bytes())
	if got, err := r.StringU32(); err != nil || got != name {
		t.Errorf("StringU32 round trip = %q (%v), want %q", got, err, name)
	}
	if tail, err := r.U8(); err != nil || tail != 0xCD {
		t.Errorf("sentinel = %#x (%v), want 0xCD", tail, err)
	}
}

// TestWireTextUnmappableRune: a rune cp1252 cannot express becomes '?', which is
// what Java's own encoder does — the client must never receive a byte sequence
// it would decode into a different length than we counted.
func TestWireTextUnmappableRune(t *testing.T) {
	got := EncodeText("Hé日本")
	want := []byte{'H', 0xE9, '?', '?'}
	if len(got) != len(want) {
		t.Fatalf("EncodeText = % X (%d bytes), want % X (%d)", got, len(got), want, len(want))
	}
	for i := range want {
		if got[i] != want[i] {
			t.Fatalf("EncodeText = % X, want % X", got, want)
		}
	}
	// And it must still round-trip to a readable string of the SAME byte length,
	// so the length prefix stays honest.
	if s := DecodeText(got); s != "Hé??" {
		t.Errorf("DecodeText = %q, want %q", s, "Hé??")
	}
}

// TestWireTextDecodeNeverFails: every byte value has a cp1252 reading, so a
// garbled name degrades to nonsense rather than an error or a panic.
func TestWireTextDecodeNeverFails(t *testing.T) {
	all := make([]byte, 256)
	for i := range all {
		all[i] = byte(i)
	}
	s := DecodeText(all)
	if s == "" {
		t.Error("decoding all 256 byte values produced nothing")
	}
	// Re-encoding is lossy only for the five bytes cp1252 leaves undefined
	// (0x81 0x8D 0x8F 0x90 0x9D); everything else must survive.
	back := EncodeText(s)
	var diff int
	for i := 0; i < 256 && i < len(back); i++ {
		if back[i] != all[i] {
			diff++
		}
	}
	if diff > 5 {
		t.Errorf("%d of 256 byte values failed to round trip, want at most 5 (the undefined cp1252 slots)", diff)
	}
}
