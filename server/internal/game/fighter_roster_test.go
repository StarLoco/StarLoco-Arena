package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestFighterCreateHonoursBlobType pins the roster the client asked for.
//
// The et_2 `type` byte (1 classic / 2 evolution) used to be decoded into
// FighterBlob.Type and then never read, so `buildFighter` left State at 0 and
// IsEvolution() answered false: a fighter recruited on the Évolution tab came
// straight back as a CLASSIC one, and the evolution roster could never fill.
func TestFighterCreateHonoursBlobType(t *testing.T) {
	s := &Session{log: testLogger(), deps: &Deps{Log: testLogger()}}
	for _, c := range []struct {
		typ  uint8
		want bool
		what string
	}{
		{fighterBlobTypeClassic, false, "Élite tab -> classic fighter"},
		{fighterBlobTypeEvolution, true, "Évolution tab -> evolution fighter"},
	} {
		f := s.buildFighter(1, &FighterBlob{Type: c.typ, BreedID: 8, Name: "Probe"})
		if f.Evolution != c.want {
			t.Errorf("blob type %d -> Evolution=%v, want %v (%s)", c.typ, f.Evolution, c.want, c.what)
		}
		if f.IsEvolution() != c.want {
			t.Errorf("blob type %d -> IsEvolution()=%v, want %v (%s)", c.typ, f.IsEvolution(), c.want, c.what)
		}
		// A new fighter always starts in the LINE-UP; the roster it belongs to is
		// the Evolution flag's job, not the state's.
		if f.State != domain.FighterStateTitular {
			t.Errorf("blob type %d -> state %d, want %d (line-up)", c.typ, f.State, domain.FighterStateTitular)
		}
	}
}

// TestIsEvolutionBackCompat: rows written before the Evolution column existed
// must keep working — anything not in the line-up could only have got there
// through evolution play.
func TestIsEvolutionBackCompat(t *testing.T) {
	for _, st := range []uint8{
		domain.FighterStateBench, domain.FighterStateDead,
		domain.FighterStateGraveyard, domain.FighterStateLegendary,
	} {
		f := &domain.Fighter{State: st} // Evolution flag unset, as legacy rows are
		if !f.IsEvolution() {
			t.Errorf("legacy fighter in state %d is not treated as evolution", st)
		}
	}
	if (&domain.Fighter{State: domain.FighterStateTitular}).IsEvolution() {
		t.Error("a plain titular fighter with no flag must NOT be an evolution fighter")
	}
}

// TestEvolutionFighterCarriesTail: the flag must reach the wire, because the
// type-2 tail is what files the fighter into the client's evolution roster.
func TestEvolutionFighterCarriesTail(t *testing.T) {
	classic := encodeFighterBlob(&domain.Fighter{ID: 1, BreedID: 8, Name: "A"}, nil)
	evo := encodeFighterBlob(&domain.Fighter{ID: 2, BreedID: 8, Name: "A", Evolution: true}, nil)
	if classic[0] != fighterBlobTypeClassic {
		t.Errorf("classic blob type = %d, want %d", classic[0], fighterBlobTypeClassic)
	}
	if evo[0] != fighterBlobTypeEvolution {
		t.Errorf("evolution blob type = %d, want %d", evo[0], fighterBlobTypeEvolution)
	}
	if len(evo) <= len(classic) {
		t.Errorf("evolution blob (%d B) is not longer than the classic one (%d B) — the tail is missing",
			len(evo), len(classic))
	}
}
