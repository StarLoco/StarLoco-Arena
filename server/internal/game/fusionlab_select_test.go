package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// TestFusionLabPickedByPosition covers which altar a fusion runs on. It matters:
// the six in-world altars are six different TIERS of the type-1100 table (ids
// 2-7, power 1/10/20/30/5/15, slot counts 2/3/4/5/2/3), so the altar decides how
// many cards may be fed in. The client resolves it the same way — `xx_2`, the
// fusion-altar element, parses its own descriptor as the lab-definition id and
// looks it up with `CN.by(id)`; our element table carries that as
// `worldElement.arg`.
func TestFusionLabPickedByPosition(t *testing.T) {
	labs := gamedata.NewFusionLabs(
		&gamedata.FusionLab{ID: 2, Power: 1, Quality: 1, Slots: 2},
		&gamedata.FusionLab{ID: 5, Power: 30, Quality: 15, Slots: 5},
		&gamedata.FusionLab{ID: 7, Power: 15, Quality: 5, Slots: 3},
	)

	// Find two real fusion altars with DIFFERENT lab ids to stand next to.
	var a, b *worldElement
	for w := range worldElements {
		for i := range worldElements[w] {
			e := &worldElements[w][i]
			if e.kind != kindFusionLab {
				continue
			}
			if labs.Get(int64(e.arg)) == nil {
				continue
			}
			switch {
			case a == nil:
				a = e
			case b == nil && e.arg != a.arg:
				b = e
			}
		}
	}
	if a == nil || b == nil {
		t.Skip("need two fusion altars with distinct lab ids in the element table")
	}

	mk := func(e *worldElement) *Session {
		s := &Session{
			Coach: &domain.Coach{PosX: e.cellX, PosY: e.cellY},
			deps:  &Deps{FusionLabs: labs},
		}
		// Stand in the world that actually holds this element.
		for w := range worldElements {
			for i := range worldElements[w] {
				if &worldElements[w][i] == e {
					s.currentWorld = w
				}
			}
		}
		return s
	}

	if got := mk(a).fusionLab(); got == nil || got.ID != int64(a.arg) {
		t.Errorf("standing at altar arg=%d selected %v, want lab %d", a.arg, got, a.arg)
	}
	if got := mk(b).fusionLab(); got == nil || got.ID != int64(b.arg) {
		t.Errorf("standing at altar arg=%d selected %v, want lab %d", b.arg, got, b.arg)
	}
	// The two must genuinely differ, or this test proves nothing.
	if a.arg == b.arg {
		t.Fatal("picked two altars with the same lab id; the test cannot distinguish them")
	}

	// No labs loaded -> nil, never a panic.
	s := &Session{Coach: &domain.Coach{}, deps: &Deps{}}
	if got := s.fusionLab(); got != nil {
		t.Errorf("fusionLab with no table = %v, want nil", got)
	}
}
