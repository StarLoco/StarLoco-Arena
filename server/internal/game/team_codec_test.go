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
	values := map[uint]int16{10: 500, 11: 600, 12: 700}

	blob := encodeTeamPreset(team, values)
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
}
