package game

import (
	"os"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
	"testing"
)

// TestNoUnimplementedRowIsActuallyWired closes a hole that made the inventory
// wrong in the direction nobody checks.
//
// TestOpcodeInventoryMarksEveryEmittedFrame scans EncodeS2C call sites for a
// LITERAL opcode. Anything sent indirectly is invisible to it:
//
//   - sendChatError(opcode uint16)      - the opcode is a parameter
//   - searchFamily{result: protocol.X}  - the opcode is a struct field
//
// Both are real. The 23002/23004/23006/23008 and 23102/23104/23106/23108 search
// handshakes have been fully implemented for a long time and were still marked
// "-", which inflated the unimplemented count and put finished work at the top of
// a "where to start" list.
//
// This checks the cheap, reliable signal instead: if a protocol constant for an
// opcode is REFERENCED anywhere in the non-test source of this package, that row
// cannot honestly be "not implemented". It does not prove the frame is sent - it
// proves the claim needs re-checking by a human, which is the right failure mode
// for a document people plan from.
func TestNoUnimplementedRowIsActuallyWired(t *testing.T) {
	rows := inventoryRowsByStatus(t, "-", "I")
	if len(rows) == 0 {
		t.Fatal("parsed no unimplemented rows; the inventory format changed")
	}
	consts := protocolConstantsByValue(t)
	used := packageSourceText(t)

	var wired []string
	for op := range rows {
		for _, name := range consts[op] {
			// Word-boundary match: a plain Contains reports OpCoachEquipmentUpdate
			// as used because OpCoachEquipmentUpdateRequest contains it. Substring
			// matching on identifiers is how the first version of this test
			// invented a twelfth stale row that did not exist.
			if regexp.MustCompile(`protocol\.` + regexp.QuoteMeta(name) + `\b`).MatchString(used) {
				wired = append(wired, strconv.Itoa(int(op))+" ("+name+")")
				break
			}
		}
	}
	if len(wired) > 0 {
		t.Errorf("%d opcode(s) are marked unimplemented but their constant is used "+
			"in internal/game: %s\n  Either the row is stale, or the constant is "+
			"referenced without being sent - both need a human decision, which is "+
			"why this fails rather than guessing.", len(wired), strings.Join(wired, ", "))
	}
}

var inventoryRowRE = regexp.MustCompile(`^\|\s*(\d{3,5})\s*\|\s*(C2S|S2C)\s*\|\s*([HEI\-])\s*\|`)

func inventoryRowsByStatus(t *testing.T, statuses ...string) map[int64]bool {
	t.Helper()
	raw, err := os.ReadFile(filepath.Join("..", "..", "docs", "OPCODE-INVENTORY.md"))
	if err != nil {
		t.Skipf("inventory not readable: %v", err)
	}
	want := map[string]bool{}
	for _, s := range statuses {
		want[s] = true
	}
	out := map[int64]bool{}
	for _, ln := range strings.Split(string(raw), "\n") {
		m := inventoryRowRE.FindStringSubmatch(strings.TrimRight(ln, "\r"))
		if m == nil || !want[m[3]] {
			continue
		}
		if v, err := strconv.ParseInt(m[1], 10, 64); err == nil {
			out[v] = true
		}
	}
	return out
}

var constRE = regexp.MustCompile(`(?m)^\s*(Op\w+)\s*=\s*(\d{3,5})\b`)

func protocolConstantsByValue(t *testing.T) map[int64][]string {
	t.Helper()
	raw, err := os.ReadFile(filepath.Join("..", "protocol", "opcodes.go"))
	if err != nil {
		t.Skipf("opcodes.go not readable: %v", err)
	}
	out := map[int64][]string{}
	for _, m := range constRE.FindAllStringSubmatch(string(raw), -1) {
		if v, err := strconv.ParseInt(m[2], 10, 64); err == nil {
			out[v] = append(out[v], m[1])
		}
	}
	return out
}

// packageSourceText concatenates this package's non-test sources.
func packageSourceText(t *testing.T) string {
	t.Helper()
	entries, err := os.ReadDir(".")
	if err != nil {
		t.Fatalf("read package dir: %v", err)
	}
	var b strings.Builder
	for _, e := range entries {
		n := e.Name()
		if !strings.HasSuffix(n, ".go") || strings.HasSuffix(n, "_test.go") {
			continue
		}
		raw, err := os.ReadFile(n)
		if err != nil {
			continue
		}
		b.Write(raw)
	}
	return b.String()
}
