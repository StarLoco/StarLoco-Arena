package web

import (
	"fmt"
	"regexp"
	"strings"
	"testing"
)

// The portal's sense of depth comes from big blurred colour blobs deliberately
// positioned PAST the edges of their container — `right:-160px` and the like.
// That only works if the container clips them. It does not, and the blob
// widens the document, every page grows a horizontal scrollbar.
//
// That shipped once: `.section` was the one decoration host without
// `overflow: hidden`, and five pages put a blob at a negative `right` inside
// one. These tests make the invariant checkable instead of remembered.

// blobRe finds a decorative blob and captures its inline style.
var blobRe = regexp.MustCompile(`(?s)<div class="blob[^"]*"\s+style="([^"]*)"`)

// openTagRe finds any opening tag carrying a class attribute.
var openTagRe = regexp.MustCompile(`<(section|div|footer|main|header)[^>]*class="([^"]*)"`)

// anyTagRe finds every opening or closing tag of the container elements we
// track, so enclosingClasses can keep a real stack rather than guessing.
var anyTagRe = regexp.MustCompile(`<(/?)(section|div|footer|main|header)([^>]*)>`)

// classOfRe pulls the class attribute out of a tag's attribute text.
var classOfRe = regexp.MustCompile(`class="([^"]*)"`)

// enclosingClasses returns the classes of the elements that genuinely CONTAIN
// the byte at pos, outermost first.
//
// The obvious shortcut — "the last opening tag before this point" — is wrong,
// because a decorative sibling that opens and closes before the blob
// (`<div class="hero-scrim"></div>`) looks exactly like a parent. That is a
// sibling, not an ancestor, so the check has to track open and close tags and
// pop, which is what this does.
//
// Templates put conditional markup inside {{if}}, so the tags in the file are
// not guaranteed to balance; an unmatched close is ignored rather than
// panicking, which at worst makes the check more conservative.
func enclosingClasses(text string, pos int) []string {
	var stack []string
	for _, m := range anyTagRe.FindAllStringSubmatchIndex(text, -1) {
		if m[0] >= pos {
			break
		}
		closing := text[m[2]:m[3]] == "/"
		attrs := text[m[6]:m[7]]
		if closing {
			if len(stack) > 0 {
				stack = stack[:len(stack)-1]
			}
			continue
		}
		// A tag that closes itself on the same line encloses nothing.
		if strings.HasSuffix(strings.TrimSpace(attrs), "/") {
			continue
		}
		var class string
		if c := classOfRe.FindStringSubmatch(attrs); c != nil {
			class = c[1]
		}
		stack = append(stack, class)
	}
	return stack
}

// clippingClasses are the container classes app.css declares `overflow: hidden`
// on. TestClippingClassesReallyClip keeps this list honest against the CSS, so
// the two cannot drift apart.
var clippingClasses = map[string]bool{
	"hero":          true,
	"section":       true,
	"section-tight": true,
	"panel":         true,
	"site-footer":   true,
}

// TestEveryBlobIsInsideAClippingContainer walks the real templates and checks
// that each blob's nearest enclosing container is one that clips.
func TestEveryBlobIsInsideAClippingContainer(t *testing.T) {
	pages, err := templatesFS.ReadDir("templates")
	if err != nil {
		t.Fatalf("read templates dir: %v", err)
	}

	found := 0
	for _, page := range pages {
		src, err := templatesFS.ReadFile("templates/" + page.Name())
		if err != nil {
			t.Fatalf("read %s: %v", page.Name(), err)
		}
		text := string(src)

		for _, m := range blobRe.FindAllStringSubmatchIndex(text, -1) {
			found++
			style := text[m[2]:m[3]]

			// Any real ancestor that clips is enough: the blob cannot escape a
			// clipping box even if the elements nested inside it do not clip.
			ancestors := enclosingClasses(text, m[0])
			clips := false
			for _, host := range ancestors {
				for _, c := range strings.Fields(host) {
					if clippingClasses[c] {
						clips = true
						break
					}
				}
			}
			if !clips {
				t.Errorf("%s: a blob (%s) sits in %v, none of which clips — "+
					"it will hang past the edge and give the page a horizontal scrollbar",
					page.Name(), style, ancestors)
			}
		}
	}

	// A guard on the guard: if the blobs are ever renamed or removed, this test
	// must not keep silently passing over nothing.
	if found == 0 {
		t.Fatal("no blobs found in any template — this test is no longer checking anything")
	}
	t.Logf("checked %d decorative blobs", found)
}

// TestClippingClassesReallyClip asserts app.css actually declares
// `overflow: hidden` for every class the test above trusts to clip.
func TestClippingClassesReallyClip(t *testing.T) {
	css, err := staticFS.ReadFile("static/app.css")
	if err != nil {
		t.Fatalf("read app.css: %v", err)
	}
	text := string(css)

	for class := range clippingClasses {
		if !declaresOverflowHidden(text, class) {
			t.Errorf(".%s is trusted to clip decoration but app.css never gives it "+
				"overflow:hidden", class)
		}
	}
}

// declaresOverflowHidden reports whether any rule whose selector list mentions
// .class sets overflow:hidden. Deliberately simple — the stylesheet is
// hand-written, one file, and not minified.
func declaresOverflowHidden(css, class string) bool {
	needle := "." + class
	for _, block := range strings.Split(css, "}") {
		open := strings.Index(block, "{")
		if open < 0 {
			continue
		}
		selector, body := block[:open], block[open+1:]
		if !mentionsClass(selector, needle) {
			continue
		}
		if strings.Contains(strings.ReplaceAll(body, " ", ""), "overflow:hidden") {
			return true
		}
	}
	return false
}

// mentionsClass avoids matching ".section" inside ".section-tight".
func mentionsClass(selector, needle string) bool {
	idx := 0
	for {
		i := strings.Index(selector[idx:], needle)
		if i < 0 {
			return false
		}
		i += idx
		end := i + len(needle)
		if end >= len(selector) || !isClassChar(selector[end]) {
			return true
		}
		idx = end
	}
}

func isClassChar(b byte) bool {
	return b == '-' || b == '_' ||
		(b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z') || (b >= '0' && b <= '9')
}

// TestFeatureItemsAreGridChildren pins the fix for the misaligned 01/02/03/04
// list. Wrapping them in two column <div>s let each column stack on its own, so
// the second row drifted apart by whatever the first row's text lengths
// differed by. Only direct grid children share row heights.
func TestFeatureItemsAreGridChildren(t *testing.T) {
	src, err := templatesFS.ReadFile("templates/index.html")
	if err != nil {
		t.Fatalf("read index.html: %v", err)
	}
	text := string(src)

	start := strings.Index(text, `class="grid grid-2 feature-grid"`)
	if start < 0 {
		t.Fatal(`the feature grid is gone (expected class="grid grid-2 feature-grid")`)
	}
	// Take the markup from the grid to the end of the enclosing section.
	end := strings.Index(text[start:], "</section>")
	if end < 0 {
		t.Fatal("no closing </section> after the feature grid")
	}
	grid := text[start : start+end]

	if n := strings.Count(grid, `class="feature"`); n != 4 {
		t.Errorf("found %d feature items, want 4", n)
	}
	for _, num := range []string{"01", "02", "03", "04"} {
		if !strings.Contains(grid, fmt.Sprintf(`<div class="feature-num">%s</div>`, num)) {
			t.Errorf("feature %s is missing", num)
		}
	}

	// The real assertion: the grid's FIRST child element must be a feature.
	// Any wrapper in between re-introduces the independent-column bug.
	openEnd := strings.Index(grid, ">")
	if openEnd < 0 {
		t.Fatal("malformed feature-grid opening tag")
	}
	rest := grid[openEnd+1:]
	next := strings.Index(rest, "<")
	if next < 0 {
		t.Fatal("the feature grid has no children")
	}
	tagEnd := strings.Index(rest[next:], ">")
	if tagEnd < 0 {
		t.Fatal("malformed child tag in the feature grid")
	}
	firstChild := rest[next : next+tagEnd+1]
	if !strings.Contains(firstChild, `class="feature"`) {
		t.Errorf("the feature grid's first child is %q, not a feature item — "+
			"a wrapper makes each column stack independently and the second row "+
			"stops lining up", strings.TrimSpace(firstChild))
	}
}
