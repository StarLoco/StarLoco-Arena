package gamedata

import (
	"os"
	"path/filepath"
	"sort"
	"testing"
)

// TestLoadTournamentsReal pins the type-1000/1001 decode. The record ends with
// five variable-length prize maps, so a field-order slip earlier in the record
// would almost certainly desync the tail and fail to parse — but it could also
// "succeed" with nonsense, which is what the plausibility assertions are for.
func TestLoadTournamentsReal(t *testing.T) {
	dir := filepath.Join("..", "..", "data-dist")
	if _, err := os.Stat(filepath.Join(dir, "data.bdat")); err != nil {
		t.Skip("no data-dist; skipping")
	}
	st, err := Open(dir)
	if err != nil {
		t.Fatal(err)
	}
	tr, err := st.LoadTournaments()
	if err != nil {
		t.Fatal(err)
	}
	if tr.Len() == 0 {
		t.Fatal("no tournament definitions decoded")
	}

	ids := make([]int16, 0, tr.Len())
	for id := range tr.All() {
		ids = append(ids, id)
	}
	sort.Slice(ids, func(i, j int) bool { return ids[i] < ids[j] })

	cards, err := st.LoadCards()
	if err != nil {
		t.Fatal(err)
	}
	for _, id := range ids {
		d := tr.Get(id)
		prizes := 0
		for _, p := range d.Prizes {
			prizes += len(p)
		}
		t.Logf("tournament %d: teamType=%d inscriptionCard=%d rewardCard=%d rules=%d prizeEntries=%d flag=%v u=[%d %d %d %d %d]",
			d.ID, d.TeamType, d.InscriptionCard, d.RewardCard, len(d.Rules), prizes, d.Flag,
			d.Unknown1, d.Unknown2, d.Unknown3, d.Unknown4, d.Unknown5)

		// The team type must be one the client understands, or it would fall through
		// every branch of its tab selection.
		if d.TeamType > TournamentTeamLegendary {
			t.Errorf("tournament %d: teamType %d is outside aql_0 (0-4)", d.ID, d.TeamType)
		}
		// A card reference must resolve, or the client would render an empty slot
		// where the entry ticket / reward should be.
		if d.InscriptionCard != 0 && cards.Get(d.InscriptionCard) == nil {
			t.Errorf("tournament %d: inscriptionCard %d is not a real card", d.ID, d.InscriptionCard)
		}
		if d.RewardCard != 0 && cards.Get(d.RewardCard) == nil {
			t.Errorf("tournament %d: rewardCard %d is not a real card", d.ID, d.RewardCard)
		}
	}
	t.Logf("levels (type 1001): %v", tr.Levels())
}
