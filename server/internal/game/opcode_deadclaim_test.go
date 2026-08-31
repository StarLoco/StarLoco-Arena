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
