package parser

import (
	"os"
	"strings"
	"testing"
)

// TestRealStaticEffectsFileParses loads the actual production
// staticEffects.dat file and confirms it parses without error and
// consumes every byte of the file -- see staticeffects.go's doc comment
// for why this format was previously (incorrectly) believed
// unrecoverable, and why full-file byte-exact consumption is the
// strongest available verification signal here.
func TestRealStaticEffectsFileParses(t *testing.T) {
	const path = "../../../data/staticEffects.dat"
	raw, err := os.ReadFile(path)
	if err != nil {
		t.Skipf("real staticEffects.dat not available (%v), skipping", err)
	}

	got, err := ParseStaticEffectsFile(raw)
	if err != nil {
		t.Fatalf("ParseStaticEffectsFile: %v", err)
	}

	if len(got.Areas) != 10 {
		t.Errorf("Areas len = %d, want 10 (confirmed via manual reverse-engineering)", len(got.Areas))
	}
	if len(got.Effects) != 16 {
		t.Errorf("Effects len = %d, want 16", len(got.Effects))
	}

	var trapCount, specialCount int
	for _, a := range got.Areas {
		switch a.EffectAreaType {
		case "TRAP":
			trapCount++
		case "SPECIAL":
			specialCount++
		default:
			t.Errorf("unexpected EffectAreaType %q for area id=%d", a.EffectAreaType, a.ID)
		}
	}
	if trapCount != 2 {
		t.Errorf("trapCount = %d, want 2", trapCount)
	}
	if specialCount != 8 {
		t.Errorf("specialCount = %d, want 8", specialCount)
	}

	for _, e := range got.Effects {
		// The real data pads ParentType with trailing spaces to a fixed
		// 18-char width (e.g. "AREA              ") -- a real authoring
		// quirk in the original .dat file (confirmed: the 4-byte string
		// length prefix genuinely says 18, not a parsing bug), tolerated
		// the same way store.go's existing hasPrefix-based grouping
		// already does for spells/cards/events.
		if !strings.HasPrefix(e.ParentType, "AREA") {
			t.Errorf("effect id=%d ParentType = %q, want prefix \"AREA\"", e.ID, e.ParentType)
		}
	}
}
