package game

import (
	"strings"
	"testing"
)

// TestChannelMessageIsSanitized is the SECURITY regression for the one chat pipe
// that sanitized nothing. It fans out to EVERY online coach, and the client's
// renderer parses markup in the message body AND the sender name unescaped
// (B-104), so this was the widest injection surface of the chat family.
func TestChannelMessageIsSanitized(t *testing.T) {
	hostile := "<image pixmap=evil><b>pwn</b>"

	frame, err := buildChannelMessage(hostile, hostile, hostile)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if strings.ContainsAny(string(frame), "<>") {
		t.Error("3140 frame still carries markup delimiters; all three fields " +
			"(channel key, sender name, body) must be sanitized")
	}
}

// TestChannelFieldsExactlyConsumeThePayload pins the real corruption mode.
//
// My first attempt at this test checked for a NEGATIVE length prefix, on the
// theory that a >127 field wraps when read as a signed byte. That premise was
// wrong: byte(len(s)) wraps modulo 256, so a 300-byte field yields prefix 44 -
// positive, but describing far fewer bytes than follow. The observable damage is
// therefore a DESYNC between the declared lengths and the payload, and the
// client reads the next field out of the middle of this one.
//
// Verified by mutation: with both the sanitizer cap and Writer.StringU8's clamp
// disabled, this test fires. The earlier version did not fire even then, which
// is how I found it was inert.
func TestChannelFieldsExactlyConsumeThePayload(t *testing.T) {
	long := strings.Repeat("A", 300)

	frame, err := buildChannelMessage(long, long, long)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	payload := s2cPayload(t, frame)

	pos := 0
	for i := 0; i < 3; i++ {
		if pos >= len(payload) {
			t.Fatalf("payload exhausted while starting field %d: the declared "+
				"lengths do not describe this payload", i)
		}
		n := int(payload[pos])
		pos += 1 + n
		if pos > len(payload) {
			t.Fatalf("field %d claims %d bytes but only %d remain - length prefix "+
				"and payload disagree", i, n, len(payload)-pos+n)
		}
	}
	if pos != len(payload) {
		t.Errorf("three fields consumed %d of %d payload bytes; %d left over - "+
			"the client would read the next frame from inside this one",
			pos, len(payload), len(payload)-pos)
	}
}

// s2cPayload extracts the payload of an encoded S2C frame.
// Format (frame.go): [u16 totalLen][u16 opcode][payload], totalLen counting the
// 4-byte header. Read rather than assumed - my first version guessed a 5-byte
// header and a self-excluding length, and failed on both counts.
func s2cPayload(t *testing.T, frame []byte) []byte {
	t.Helper()
	if len(frame) < 4 {
		t.Fatalf("frame too short: %d bytes", len(frame))
	}
	total := int(frame[0])<<8 | int(frame[1])
	if total != len(frame) {
		t.Fatalf("frame declares %d bytes but is %d long", total, len(frame))
	}
	return frame[4:]
}

// TestChannelMessageKeepsLegitimateText guards against over-sanitizing: a normal
// line, including accents, must survive intact.
func TestChannelMessageKeepsLegitimateText(t *testing.T) {
	frame, err := buildChannelMessage("trade", "Ren\u00e9e", "salut tout le monde")
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if !strings.Contains(string(frame), "salut tout le monde") {
		t.Error("legitimate chat text was mangled by sanitization")
	}
}
