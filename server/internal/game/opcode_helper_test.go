package game

import (
	"regexp"
	"strings"
	"testing"
)

// TestVariableOpcodeHelpersAreInventoried closes a documented blind spot in
// TestOpcodeInventoryMarksEveryEmittedFrame.
//
// That test scans for a LITERAL opcode next to EncodeS2C. A helper that takes the
// opcode as a parameter - `sendChatError(opcode uint16)` and friends - is
// therefore invisible to it, so every frame sent through one is inventoried only
// by hand. That is exactly the maintenance-by-memory that left 10 stale rows in
// the file before.
//
// The helpers are DISCOVERED, not listed: any method whose signature takes an
// `opcode uint16` is picked up automatically, so adding a new one cannot quietly
// reopen the hole.
func TestVariableOpcodeHelpersAreInventoried(t *testing.T) {
	src := packageSourceText(t)

	// 1. Find helpers of the shape `func (s *Session) name(opcode uint16, ...)`
	//    that actually SEND: the body must reach EncodeS2C.
	//
	//    Without that second condition this also matches `Router.Register(opcode
	//    uint16, ...)`, which takes an opcode to register a HANDLER, not to emit a
	//    frame - and then every registered C2S opcode gets demanded as "emitted".
	//    Taking an opcode is not the same as sending one.
	helperRe := regexp.MustCompile(`func \([^)]*\) (\w+)\(opcode uint16`)
	var helpers []string
	for _, m := range helperRe.FindAllStringSubmatchIndex(src, -1) {
		name := src[m[2]:m[3]]
		if bodyReachesEncode(src, m[0]) {
			helpers = append(helpers, name)
		}
	}
	if len(helpers) == 0 {
		t.Fatal("found no variable-opcode helpers - either they were renamed or the " +
			"signature pattern changed, and this test is now vacuous")
	}

	// 2. Collect the protocol constants passed to them at every call site.
	constNames := map[string]bool{}
	for _, h := range helpers {
		callRe := regexp.MustCompile(regexp.QuoteMeta(h) + `\(\s*(protocol\.Op\w+)`)
		for _, m := range callRe.FindAllStringSubmatch(src, -1) {
			constNames[strings.TrimPrefix(m[1], "protocol.")] = true
		}
	}
	if len(constNames) == 0 {
		t.Fatalf("helpers %v are never called with a protocol.Op constant; if they are "+
			"now called with a computed opcode this test cannot see it, which is worth "+
			"knowing", helpers)
	}

	// 3. Resolve each constant to its numeric opcode.
	byValue := protocolConstantsByValue(t)
	valueOf := map[string]int64{}
	for v, names := range byValue {
		for _, n := range names {
			valueOf[n] = v
		}
	}

	// 4. Every one of them must be marked EMITTED in the inventory.
	emitted := inventoryRowsByStatus(t, "E")
	checked := 0
	for name := range constNames {
		v, ok := valueOf[name]
		if !ok {
			t.Errorf("%s is passed to a send helper but is not defined in opcodes.go", name)
			continue
		}
		checked++
		if !emitted[v] {
			t.Errorf("opcode %d (%s) is sent via a variable-opcode helper but is NOT "+
				"marked E in OPCODE-INVENTORY.md.\n  The literal-scan invariant cannot "+
				"see this call, so the row was being maintained by hand - which is how "+
				"rows go stale.", v, name)
		}
	}

	// Anti-vacuous: the chat-error family is the reason this exists, so seeing it
	// is the signal that discovery and resolution both still work.
	if !constNames["OpChatErrMalformedCommand"] {
		t.Errorf("expected OpChatErrMalformedCommand among the helper-sent opcodes "+
			"(found %d: %v); not seeing it means the scan is drifting", len(constNames), keysOf(constNames))
	}
	t.Logf("verified %d opcode(s) sent through %d variable-opcode helper(s): %v",
		checked, len(helpers), helpers)
}

func keysOf(m map[string]bool) []string {
	out := make([]string, 0, len(m))
	for k := range m {
		out = append(out, k)
	}
	return out
}

// bodyReachesEncode reports whether the function starting at `from` mentions
// EncodeS2C before the next top-level closing brace. Crude but sufficient: a
// send helper encodes a frame, a registration helper does not.
func bodyReachesEncode(src string, from int) bool {
	end := strings.Index(src[from:], "\n}")
	if end < 0 {
		end = len(src) - from
	}
	return strings.Contains(src[from:from+end], "EncodeS2C")
}
