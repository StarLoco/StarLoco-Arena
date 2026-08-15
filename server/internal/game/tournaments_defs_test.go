package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// TestStandingTournamentsMatchRealDefinitions validates the hand-built standing
// tournament table against the real type-1000 definitions, now that those decode.
//
// Two assumptions were written into the seed line-up as comments and
// could not be checked before:
//
//   - "defIDs are real ... client definitions" — the client looks the definition
//     up by this id (`LS.Yf().gG(...)`) and a miss leaves it with a null
//     definition on a tournament it is already showing;
//   - "referenceCardId MUST be 0" — a definition with an inscription card makes
//     the client demand that card to register (`aug.registerTournament` counts it
//     in the inventory), which we neither grant nor honour.
//
// Both are now data, not prose. This test also reports the team type each defID
// really carries, since the client picks which tournament UI to open from it
// (`agz_1` branches on `aub.aHh()` against `aql_0`).
func TestStandingTournamentsMatchRealDefinitions(t *testing.T) {
	st := openRealGameData(t)
	defs, err := st.LoadTournaments()
	if err != nil {
		t.Fatalf("LoadTournaments: %v", err)
	}
	if defs.Len() == 0 {
		t.Fatal("no tournament definitions decoded")
	}

	for _, s := range store.DefaultTournaments() {
		d := defs.Get(int16(s.DefID))
		if d == nil {
			t.Errorf("standing tournament %q uses defID %d, which is not in the real "+
				"type-1000 table — the client would resolve a null definition", s.Name, s.DefID)
			continue
		}
		if d.InscriptionCard != 0 {
			t.Errorf("standing tournament %q uses defID %d, whose inscription card is %d; "+
				"the table requires a no-card definition because we never grant or check "+
				"the entry ticket", s.Name, s.DefID, d.InscriptionCard)
		}
		if d.TeamType > gamedata.TournamentTeamLegendary {
			t.Errorf("defID %d has team type %d, outside aql_0", s.DefID, d.TeamType)
		}
		t.Logf("%-24q defID=%2d teamType=%d rules=%d", s.Name, s.DefID, d.TeamType, len(d.Rules))
	}
}
