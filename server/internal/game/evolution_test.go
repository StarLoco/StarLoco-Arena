package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestFighterBlobTypeFollowsState: a titular fighter is a classic type-1 blob; any
// other state makes it an evolution (type-2) fighter, which is what files it into
// the client's evolution roster — and hence the graveyard.
func TestFighterBlobTypeFollowsState(t *testing.T) {
	for _, tc := range []struct {
		state    uint8
		wantType byte
		name     string
	}{
		{domain.FighterStateTitular, 1, "titular"},
		{domain.FighterStateBench, 2, "bench"},
		{domain.FighterStateDead, 2, "dead"},
		{domain.FighterStateGraveyard, 2, "graveyard"},
		{domain.FighterStateLegendary, 2, "legendary"},
	} {
		f := &domain.Fighter{BreedID: 1, Name: "X", State: tc.state}
		blob := encodeFighterBlob(f)
		if blob[0] != tc.wantType {
			t.Errorf("%s: blob type = %d, want %d", tc.name, blob[0], tc.wantType)
		}
	}
}

// TestEvolutionTailCarriesState decodes the type-2 tail and checks the state byte
// (the field the graveyard filters on) plus the xp/tiredness/morale around it.
func TestEvolutionTailCarriesState(t *testing.T) {
	f := &domain.Fighter{
		BreedID: 1, Name: "Ghost", State: domain.FighterStateGraveyard,
		XP: 1234, TotalXP: 5678, Tiredness: 7, Morale: 9,
	}
	blob := encodeFighterBlob(f)

	// Walk the type-1 body to find where the tail starts:
	// [u8 type][u16 budget][u8 breed][u8 nameLen][name][u8 sex][u8 ey]
	// [u8][u8][u8 colors][u16 spellLen][spells][u16 cardLen][cards]
	p := 1 + 2 + 1
	nameLen := int(blob[p])
	p += 1 + nameLen
	p += 1 + 1 + 3 // sex, ey, three colours
	spellLen := int(blob[p])<<8 | int(blob[p+1])
	p += 2 + spellLen
	cardLen := int(blob[p])<<8 | int(blob[p+1])
	p += 2 + cardLen

	tail := blob[p:]
	// [i32 sphereBoardId][i32 xp][i32 totalXp][u8 tiredness][u8 morale][u8 state]...
	if len(tail) < 15 {
		t.Fatalf("evolution tail too short: %d bytes", len(tail))
	}
	be32 := func(b []byte) int32 {
		return int32(b[0])<<24 | int32(b[1])<<16 | int32(b[2])<<8 | int32(b[3])
	}
	if got := be32(tail[4:8]); got != 1234 {
		t.Errorf("xp = %d, want 1234", got)
	}
	if got := be32(tail[8:12]); got != 5678 {
		t.Errorf("totalXp = %d, want 5678", got)
	}
	if tail[12] != 7 {
		t.Errorf("tiredness = %d, want 7", tail[12])
	}
	if tail[13] != 9 {
		t.Errorf("morale = %d, want 9", tail[13])
	}
	if tail[14] != domain.FighterStateGraveyard {
		t.Errorf("state = %d, want %d (graveyard)", tail[14], domain.FighterStateGraveyard)
	}
	// Every trailing count must be present or the client silently downgrades the
	// fighter to type 1 and it disappears from the evolution roster.
	// sphereX(2) + sphereY(2) + spheres(2) + conditions(1) + passives(2) + sets(2)
	if len(tail) != 15+11 {
		t.Errorf("tail = %d bytes, want %d (all trailing counts present)", len(tail), 15+11)
	}
}

// TestFighterStateTransitions covers the state machine the client applies
// optimistically and the server must reproduce.
func TestFighterStateTransitions(t *testing.T) {
	for _, tc := range []struct {
		from, want uint8
		ok         bool
		name       string
	}{
		{domain.FighterStateTitular, domain.FighterStateBench, true, "titular -> bench"},
		{domain.FighterStateBench, domain.FighterStateTitular, true, "bench -> titular"},
		{domain.FighterStateDead, domain.FighterStateGraveyard, true, "dead -> graveyard"},
		{domain.FighterStateLegendary, domain.FighterStateLegBench, true, "legendary toggle"},
		{domain.FighterStateLegBench, domain.FighterStateLegendary, true, "legendary bench toggle"},
		{domain.FighterStateGraveyard, domain.FighterStateGraveyard, false, "graveyard is a dead end"},
	} {
		got, ok := nextFighterState(tc.from)
		if ok != tc.ok || got != tc.want {
			t.Errorf("%s: next(%d) = (%d,%v), want (%d,%v)",
				tc.name, tc.from, got, ok, tc.want, tc.ok)
		}
	}
}

// TestResurrectionTargets: only dead/interred fighters can be resurrected, and
// each returns to the right slot.
func TestResurrectionTargets(t *testing.T) {
	for _, tc := range []struct {
		from, want uint8
		ok         bool
	}{
		{domain.FighterStateGraveyard, domain.FighterStateBench, true},
		{domain.FighterStateDead, domain.FighterStateTitular, true},
		{domain.FighterStateTitular, domain.FighterStateTitular, false},
		{domain.FighterStateBench, domain.FighterStateBench, false},
	} {
		got, ok := resurrectedState(tc.from)
		if ok != tc.ok || got != tc.want {
			t.Errorf("resurrect(%d) = (%d,%v), want (%d,%v)",
				tc.from, got, ok, tc.want, tc.ok)
		}
	}
}
