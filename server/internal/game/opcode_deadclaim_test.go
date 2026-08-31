package game

import (
	"os"
	"path/filepath"
	"regexp"
	"strings"
	"testing"
)

// TestDeadClaimsHoldAgainstTheClient checks every inventory row that claims an
// opcode is unsendable/dead against the decompiled client itself.
//
// Why this exists: three separate claims in OPCODE-INVENTORY.md turned out to be
// wrong in a single session, and all three were "absence" arguments - the sink is
// never installed, the console is dead, the handler is not registered - made by
// reasoning about startup paths instead of listing what is actually there. The
// one argument that held up every time was the constructor search: a message
// class the client never constructs cannot be sent by the client, full stop.
//
// That argument is mechanical, so it should not live in prose where it can rot.
// If someone later writes "no constructor anywhere" about a class the client does
// construct, this fails and names it.
//
// Skips when the decompiled tree is absent, per the repo's real-data test rule.
func TestDeadClaimsHoldAgainstTheClient(t *testing.T) {
	coreDir := filepath.Join("..", "..", "..", "client", "decompiled", "core")
	entries, err := os.ReadDir(coreDir)
	if err != nil {
		t.Skipf("decompiled client not available (%v)", err)
	}
	javaFiles := map[string]bool{}
	for _, e := range entries {
		if strings.HasSuffix(e.Name(), ".java") {
			javaFiles[strings.TrimSuffix(e.Name(), ".java")] = true
		}
	}
	if len(javaFiles) == 0 {
		t.Skip("decompiled client directory is empty")
	}

	invBytes, err := os.ReadFile(opcodeInventoryPath)
	if err != nil {
		t.Fatalf("read inventory: %v", err)
	}

	// Markers that assert "the client cannot send this".
	deadMarkers := []string{
		"constructed nowhere",
		"constructed NOWHERE",
		"Constructed nowhere",
		"no constructor",
		"No constructor",
		"no `new",
		"No `new",
	}
	backticked := regexp.MustCompile("`([A-Za-z][A-Za-z0-9_]*)`")

	// Collect claimed-dead class names, remembering the line for the message.
	type claim struct {
		class string
		line  int
		text  string
	}
	var claims []claim
	for i, line := range strings.Split(string(invBytes), "\n") {
		hasMarker := false
		for _, m := range deadMarkers {
			if strings.Contains(line, m) {
				hasMarker = true
				break
			}
		}
		if !hasMarker {
			continue
		}
		for _, m := range backticked.FindAllStringSubmatch(line, -1) {
			if javaFiles[m[1]] {
				claims = append(claims, claim{class: m[1], line: i + 1, text: strings.TrimSpace(line)})
			}
		}
	}

	// Anti-vacuous: if the parse finds nothing, the test proves nothing. The
	// tournament admin family is the reason this test exists, so it must be here.
	if len(claims) == 0 {
		t.Fatal("parsed 0 dead-claims from OPCODE-INVENTORY.md - the markers or the " +
			"row format must have changed, and this test is now vacuous")
	}
	found := map[string]bool{}
	for _, c := range claims {
		found[c.class] = true
	}
	if !found["ayQ"] {
		t.Errorf("expected the tournament admin class ayQ among the parsed dead-claims "+
			"(got %d claims); this test was written for exactly that family, so not "+
			"seeing it means the parse is drifting", len(claims))
	}

	// Verify each claim: no `new <Class>(` anywhere in the client. Read every file
	// ONCE and test all claims against it - the naive nesting is ~70k file reads
	// and took a minute.
	ctors := make(map[string]*regexp.Regexp, len(claims))
	sitesByClass := map[string][]string{}
	for _, c := range claims {
		if ctors[c.class] == nil {
			ctors[c.class] = regexp.MustCompile(`\bnew\s+` + regexp.QuoteMeta(c.class) + `\s*\(`)
		}
	}
	for name := range javaFiles {
		b, err := os.ReadFile(filepath.Join(coreDir, name+".java"))
		if err != nil {
			continue
		}
		for class, re := range ctors {
			if name == class {
				continue // a class's own file may name its constructor
			}
			if len(sitesByClass[class]) < 3 && re.Match(b) {
				sitesByClass[class] = append(sitesByClass[class], name+".java")
			}
		}
	}
	for _, c := range claims {
		sites := sitesByClass[c.class]
		if len(sites) > 0 {
			t.Errorf("OPCODE-INVENTORY.md:%d claims %s is never constructed, but the "+
				"client constructs it in %v.\n  row: %s\n  A class the client CAN construct "+
				"is a class the client can send - the claim is stale.",
				c.line, c.class, sites, truncateRow(c.text))
		}
	}
	t.Logf("verified %d dead-claim(s) against %d client classes", len(claims), len(javaFiles))
}

func truncateRow(s string) string {
	if len(s) > 120 {
		return s[:120] + "..."
	}
	return s
}

// TestNoOpConsumerClaimsHoldAgainstTheClient is the companion to
// TestDeadClaimsHoldAgainstTheClient, covering the OTHER absence argument in the
// inventory: "the client's consumer for this opcode does nothing".
//
// That claim is what justifies not sending ~10 S2C frames. It is exactly as
// rottable as the constructor claims were, and it is checkable the same way: find
// `case <opcode>:` in the named consumer class and look at the body. A body that
// invokes a method is doing something, whatever the row says.
//
// Deliberately conservative - it only inspects rows that name BOTH an opcode and
// a consumer class, and it only rejects bodies containing method invocations.
// Casts, local assignments, break and return are all fine; those are precisely
// what a no-op consumer looks like (`bl2 = false; break;`, or cast-and-return).
func TestNoOpConsumerClaimsHoldAgainstTheClient(t *testing.T) {
	coreDir := filepath.Join("..", "..", "..", "client", "decompiled", "core")
	if _, err := os.Stat(coreDir); err != nil {
		t.Skipf("decompiled client not available (%v)", err)
	}
	invBytes, err := os.ReadFile(opcodeInventoryPath)
	if err != nil {
		t.Fatalf("read inventory: %v", err)
	}

	// Rows shaped: | <op> | S2C | - | ... `<consumer>` ... no-op ...
	rowRe := regexp.MustCompile(`^\|\s*(\d{3,5})\s*\|`)
	classRe := regexp.MustCompile("`([A-Za-z][A-Za-z0-9_]*)`")
	callRe := regexp.MustCompile(`\.\w+\s*\(|\bnew\s+\w+\s*\(`)

	checked := 0
	for i, line := range strings.Split(string(invBytes), "\n") {
		m := rowRe.FindStringSubmatch(line)
		if m == nil {
			continue
		}
		// Both tables in this file assert the claim, in different words: the main
		// opcode table says "no-op", the consumer-analysis table says "no side
		// effect" / "body is a cast and a return".
		low := strings.ToLower(line)
		isNoOp := false
		for _, marker := range []string{"no-op", "no side effect", "body is a cast"} {
			if strings.Contains(low, marker) {
				isNoOp = true
				break
			}
		}
		if !isNoOp {
			continue
		}
		opcode := m[1]
		// A row names both the message class and the consumer (e.g. 28622 names
		// `uw_2` for the wire and `ds_2` for the handler). Take whichever one
		// actually contains the case - that IS the consumer, by definition.
		var consumer, body string
		var named []string
		for _, c := range classRe.FindAllStringSubmatch(line, -1) {
			src, err := os.ReadFile(filepath.Join(coreDir, c[1]+".java"))
			if err != nil {
				continue
			}
			named = append(named, c[1])
			if b, ok := caseBody(string(src), opcode); ok {
				consumer, body = c[1], b
				break
			}
		}
		if len(named) == 0 {
			continue // row names no client class; nothing mechanical to check
		}
		if consumer == "" {
			t.Errorf("OPCODE-INVENTORY.md:%d claims %s has a no-op consumer, but none of "+
				"the classes it names %v contains a `case %s:` - the row points at the "+
				"wrong class, so the claim rests on nothing checkable",
				i+1, opcode, named, opcode)
			continue
		}
		checked++
		if calls := callRe.FindAllString(body, -1); len(calls) > 0 {
			t.Errorf("OPCODE-INVENTORY.md:%d claims %s is a no-op in %s, but its case body "+
				"invokes %v.\n  body: %s\n  A consumer that calls something is doing "+
				"something - re-read it before trusting the row.",
				i+1, opcode, consumer, calls, strings.TrimSpace(body))
		}
	}
	if checked == 0 {
		t.Fatal("checked 0 no-op claims - the row format or wording must have drifted, " +
			"and this test is now vacuous")
	}
	t.Logf("verified %d no-op consumer claim(s)", checked)
}

// caseBody returns the source between `case <opcode>:` and the statement that
// ends it (break/return/the next case), for the first match.
func caseBody(src, opcode string) (string, bool) {
	idx := strings.Index(src, "case "+opcode+":")
	if idx < 0 {
		return "", false
	}
	rest := src[idx+len("case "+opcode+":"):]
	end := len(rest)
	for _, term := range []string{"break;", "case ", "return "} {
		if j := strings.Index(rest, term); j >= 0 && j < end {
			end = j
		}
	}
	return rest[:end], true
}
