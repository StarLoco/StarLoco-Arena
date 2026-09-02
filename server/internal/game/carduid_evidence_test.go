package game

import (
	"os"
	"path/filepath"
	"regexp"
	"strings"
	"testing"
)

// TestCardUIDsAreClientLocal pins the four client facts that make ROADMAP item 14
// (5203 destructive/lock inventory ops) impossible rather than merely unbuilt.
//
// This matters because "just implement the destructive ops" looks like a small
// task, and the reason it is not is spread across four classes. The conclusion
// has already been re-litigated twice from memory. Prose rots; this does not.
//
// If any assertion here fails, item 14 deserves a fresh look - the wire changed
// or the original reading was wrong. Do not "fix" the test to make it pass.
//
// Skips when the decompiled tree is absent, per the repo's real-data test rule.
func TestCardUIDsAreClientLocal(t *testing.T) {
	coreDir := filepath.Join("..", "..", "..", "client", "decompiled", "core")
	if _, err := os.Stat(coreDir); err != nil {
		t.Skipf("decompiled client not available (%v)", err)
	}

	read := func(class string) string {
		b, err := os.ReadFile(filepath.Join(coreDir, class+".java"))
		if err != nil {
			t.Fatalf("cannot read %s.java, which item 14's conclusion rests on: %v", class, err)
		}
		return string(b)
	}

	// FACT 1: the card deserializer assigns its uid from a client-local counter,
	// unconditionally. A server-supplied uid would be discarded even if the wire
	// carried one.
	eb1 := read("eb_1")
	deser := regexp.MustCompile(`(?s)public boolean b\(ByteBuffer.*?\n    \}`).FindString(eb1)
	if deser == "" {
		t.Fatal("eb_1.b(ByteBuffer) not found - the card deserializer moved; re-verify item 14")
	}
	if !strings.Contains(deser, "uq_1.ahR()") {
		t.Error("eb_1.b(ByteBuffer) no longer assigns its uid from uq_1.ahR().\n" +
			"  Item 14 was closed BECAUSE the uid is client-local. Re-open it.")
	}
	// The assignment must not be inside a conditional: "unconditionally" is the
	// whole point, since a guarded assignment would leave room for a server uid.
	for _, guard := range []string{"if (", "else"} {
		if idx := strings.Index(deser, "uq_1.ahR()"); idx >= 0 {
			if strings.Contains(deser[idx:], guard) {
				t.Errorf("text after the uq_1.ahR() assignment contains %q; "+
					"the assignment may no longer be unconditional", guard)
			}
		}
	}
	// And it must NOT read a long: a per-card identity from the server would be
	// an i64, so getLong() appearing here would mean the premise changed.
	if strings.Contains(deser, "getLong()") {
		t.Error("eb_1.b(ByteBuffer) now reads a long - a server-assigned card identity\n" +
			"  may exist after all. Item 14's blocker must be re-verified.")
	}

	// FACT 2: uq_1.ahR() really is a local monotonic counter, not a server echo.
	uq1 := read("uq_1")
	ahR := regexp.MustCompile(`(?s)public static long ahR\(\).*?\n        \}`).FindString(uq1)
	if ahR == "" {
		t.Fatal("uq_1.ahR() not found - re-verify item 14")
	}
	if !strings.Contains(ahR, "++") {
		t.Errorf("uq_1.ahR() no longer increments a local counter; body was:\n%s", ahR)
	}

	// FACT 3: 5203 carries ONLY those uids - no template id to reconcile against,
	// and no action discriminator, so it cannot mean "lock" as once assumed.
	fh0 := read("fh_0")
	if !strings.Contains(fh0, "return 5203") {
		t.Fatal("fh_0 is no longer opcode 5203 - re-verify item 14")
	}
	enc := regexp.MustCompile(`(?s)public byte\[\] encode\(\).*?\n    \}`).FindString(fh0)
	if enc == "" {
		t.Fatal("fh_0.encode() not found - re-verify item 14")
	}
	if !strings.Contains(enc, "putLong") {
		t.Error("fh_0.encode() no longer writes the uids item 14 reasoned about")
	}
	if strings.Contains(enc, "putInt") {
		t.Error("fh_0.encode() now writes an int alongside the uids. If that is a\n" +
			"  reference-card id, the uids become resolvable and item 14 is UNBLOCKED.")
	}

	t.Log("item 14 blocker intact: card uids are client-local (eb_1 + uq_1) and " +
		"5203 (fh_0) carries only those uids, with no template id and no action discriminator")
}
