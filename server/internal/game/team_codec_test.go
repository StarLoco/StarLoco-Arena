package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestTeamPresetRoundTrip encodes a team preset and decodes it back.
func TestTeamPresetRoundTrip(t *testing.T) {
	team := &domain.Team{
		ID:       3,
		Name:     "Alpha",
		Type:     0,
		GameMode: 1,
		Members: []domain.TeamFighter{
			{FighterID: 10}, {FighterID: 11}, {FighterID: 12},
		},
	}
	// fighterId -> owning coach. The second i64 of each entry is the OWNER, which
	// is how sw_1.cF() attributes a fighter to a coach.
	owners := map[uint]uint{10: 7, 11: 7, 12: 8}

	blob := encodeTeamPreset(team, owners)
	got, err := decodeTeamPreset(blob)
	if err != nil {
		t.Fatalf("decode: %v", err)
	}
	if got.Name != "Alpha" {
		t.Errorf("name = %q, want Alpha", got.Name)
	}
	if got.TeamID != 3 || got.GameMode != 1 {
		t.Errorf("teamId=%d gameMode=%d, want 3,1", got.TeamID, got.GameMode)
	}
	if len(got.FighterIDs) != 3 || got.FighterIDs[0] != 10 || got.FighterIDs[2] != 12 {
		t.Errorf("fighters = %v, want [10 11 12]", got.FighterIDs)
	}
	// The owner must survive the round trip: it is what sw_1.cF() matches against
	// each coach id, and a wrong value makes a 2v2 unlaunchable.
	if len(got.FighterOwners) != 3 || got.FighterOwners[0] != 7 || got.FighterOwners[2] != 8 {
		t.Errorf("owners = %v, want [7 7 8]", got.FighterOwners)
	}
}
