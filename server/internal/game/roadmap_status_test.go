package game

import (
	"os"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
	"testing"
)

// ROADMAP.md is the cold-start entry point, and its per-item status markers had
// silently drifted away from the prose underneath them: item 31 read "core loop
// DONE and live-verified" while still marked in-progress, and item 33 carried no
// marker or explanation at all despite being closed by evidence months earlier.
//
// Both misled me while planning, and I then repeated one of them to the user as
// "the largest remaining gap". A stale marker on the file people read FIRST is
// worse than no marker, so the cheap invariants are checked here.
//
// This deliberately does not try to judge prose. It catches the two failures that
// actually happened: an item with no status at all, and an item whose marker
// contradicts its own opening sentence.

var roadmapItemRE = regexp.MustCompile(`^(\d+)\.\s+(.*)$`)

// roadmapBacklog returns the numbered backlog items, which start at the "Tier 0"
// heading - earlier numbered lists in the document are prose, not backlog.
func roadmapBacklog(t *testing.T) []string {
	t.Helper()
	path := filepath.Join("..", "..", "..", "ROADMAP.md")
	raw, err := os.ReadFile(path)
	if err != nil {
		t.Skipf("ROADMAP.md not readable: %v", err)
	}
	lines := strings.Split(string(raw), "\n")
	start := -1
	for i, ln := range lines {
		if strings.HasPrefix(ln, "### Tier 0") {
			start = i
			break
		}
	}
	if start < 0 {
		t.Fatal(`ROADMAP.md has no "### Tier 0" heading; the backlog anchor moved ` +
			`and this test is no longer reading the right list`)
	}
	var items []string
	for _, ln := range lines[start:] {
		if m := roadmapItemRE.FindStringSubmatch(strings.TrimRight(ln, "\r")); m != nil {
			items = append(items, strings.TrimRight(ln, "\r"))
		}
	}
	if len(items) < 30 {
		t.Fatalf("found only %d backlog items; the parse is wrong", len(items))
	}
	return items
}

// TestRoadmapItemsAreNumberedContiguously catches an item being duplicated or
// dropped by an edit.
func TestRoadmapItemsAreNumberedContiguously(t *testing.T) {
	items := roadmapBacklog(t)
	for i, ln := range items {
		m := roadmapItemRE.FindStringSubmatch(ln)
		n, err := strconv.Atoi(m[1])
		if err != nil {
			t.Fatalf("item %q has a non-numeric index", ln)
		}
		if n != i+1 {
			t.Fatalf("backlog item %d is numbered %d: %q", i+1, n, truncateItem(ln))
		}
	}
}

// TestEveryRoadmapItemHasAStatus is the one that would have caught item 33, which
// sat as a bare title with no marker and no explanation long after it had been
// established as dead code - exactly the shape a future reader picks up and
// starts building.
func TestEveryRoadmapItemHasAStatus(t *testing.T) {
	items := roadmapBacklog(t)
	for _, ln := range items {
		body := roadmapItemRE.FindStringSubmatch(ln)[2]
		if !hasRoadmapStatus(body) {
			t.Errorf("no status marker: %q\n  every item needs [x] (done/closed), "+
				"~~struck~~ (done), U+1F7E1 (in progress) or U+26D4 (blocked) - a "+
				"bare item reads as open work", truncateItem(ln))
		}
	}
}

// TestNoRoadmapItemContradictsItsOwnMarker is the one that would have caught item
// 31, marked in-progress while opening with "core loop DONE and live-verified".
func TestNoRoadmapItemContradictsItsOwnMarker(t *testing.T) {
	items := roadmapBacklog(t)
	for _, ln := range items {
		body := roadmapItemRE.FindStringSubmatch(ln)[2]
		if !strings.HasPrefix(body, "\U0001F7E1") {
			continue // only the in-progress marker can contradict itself this way
		}
		for _, claim := range []string{"DONE", "CLOSED"} {
			if strings.Contains(body, claim) {
				t.Errorf("marked in progress but says %q on its first line: %q\n"+
					"  either the marker or the sentence is stale", claim,
					truncateItem(ln))
			}
		}
	}
}

func hasRoadmapStatus(body string) bool {
	for _, marker := range []string{"[x]", "~~", "\U0001F7E1", "\u26D4"} {
		if strings.HasPrefix(body, marker) {
			return true
		}
	}
	return false
}

func truncateItem(s string) string {
	const max = 90
	if len(s) <= max {
		return s
	}
	return s[:max] + "..."
}
