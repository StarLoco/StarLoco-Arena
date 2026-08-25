package game

import (
	"path/filepath"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/store"
)

// The prize a tournament advertises lives in the CLIENT's own data - a type-1000
// aub record, field aHi(), bound to the GUI field "tournamentRewards" - and is
// never sent over the wire. So the client shows the prize whether or not the
// server pays it, and only the server can close that gap.
//
// Of the 22 shipped definitions only 11 (card 26) and 18 (card 544) name a
// prize. Both are free to enter, so both pass the web console's "no entry
// ticket" filter and can be picked by an operator.

// tournamentPrizeDeps builds a Deps with a real store, the real card table and
// the real type-1000 definitions, plus a persisted tournament row on defID and
// the coach that will win it.
func tournamentPrizeDeps(t *testing.T, defID uint16) (*Deps, uint, int64) {
	t.Helper()
	st, err := store.Open(filepath.Join(t.TempDir(), "tournprize.db"))
	if err != nil {
		t.Fatalf("open store: %v", err)
	}
	t.Cleanup(func() { _ = st.Close() })

	gd := openRealGameData(t)
	cards, err := gd.LoadCards()
	if err != nil {
		t.Fatalf("LoadCards: %v", err)
	}
	defs, err := gd.LoadTournaments()
	if err != nil {
		t.Fatalf("LoadTournaments: %v", err)
	}

	acc, _ := st.Accounts.CreateAccount("tournwin", "pw", false)
	coach, _ := st.Coaches.Create(acc.ID, "TournWinner", 0, 0, 0)

	tr := &domain.Tournament{
		DefID: defID, Name: "Prize Cup", Short: "PC", Enabled: true,
	}
	if err := st.Tournaments.Create(tr); err != nil {
		t.Fatalf("create tournament: %v", err)
	}

	d := &Deps{
		Store: st, Cards: cards, TournamentDefs: defs,
		Fights: NewFightManager(), World: NewRegistry(150), Log: testLogger(),
	}
	return d, coach.ID, tr.WireID()
}

// TestTournamentPrizeIsGranted: winning a tournament built on a definition that
// names a prize puts that exact card in the winner's inventory.
func TestTournamentPrizeIsGranted(t *testing.T) {
	// Definition 11 advertises card 26.
	d, coachID, tid := tournamentPrizeDeps(t, 11)
	const prizeCard = 26

	before := ownedQty(t, d, coachID, prizeCard)
	d.awardTournamentPrize(tid, coachID, nil)
	after := ownedQty(t, d, coachID, prizeCard)

	if after != before+1 {
		t.Errorf("card %d quantity %d -> %d, want +1: the client advertises this "+
			"prize, so the server must pay it", prizeCard, before, after)
	}
}

// TestTournamentPrizeIsTheDefinitionsCard guards against paying a fixed or
// wrong card: definition 18 pays 544, not 26.
func TestTournamentPrizeIsTheDefinitionsCard(t *testing.T) {
	d, coachID, tid := tournamentPrizeDeps(t, 18)

	before26 := ownedQty(t, d, coachID, 26)
	before544 := ownedQty(t, d, coachID, 544)
	d.awardTournamentPrize(tid, coachID, nil)

	if got := ownedQty(t, d, coachID, 544); got != before544+1 {
		t.Errorf("card 544 quantity %d -> %d, want +1", before544, got)
	}
	if got := ownedQty(t, d, coachID, 26); got != before26 {
		t.Errorf("card 26 quantity %d -> %d: definition 18 pays 544, so the "+
			"prize is being read from the wrong definition", before26, got)
	}
}

// TestTournamentWithNoPrizePaysNothing: 20 of the 22 definitions advertise no
// prize, so the common case must grant nothing at all rather than defaulting to
// some card.
func TestTournamentWithNoPrizePaysNothing(t *testing.T) {
	// Definition 1 has no reward card - it is also one of the three the server
	// actually offers today.
	d, coachID, tid := tournamentPrizeDeps(t, 1)

	c, err := d.Store.Coaches.Get(coachID)
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	before := len(c.Inventory)
	d.awardTournamentPrize(tid, coachID, nil)
	c, err = d.Store.Coaches.Get(coachID)
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	if len(c.Inventory) != before {
		t.Errorf("inventory grew from %d to %d rows for a tournament that "+
			"advertises no prize", before, len(c.Inventory))
	}

	// Again with no card catalogue. Deps.Cards is nil on a server started
	// without data files, and then the "advertises no prize" check is the ONLY
	// thing standing between a 0 reward and a grant of card 0 - the
	// unknown-card guard that would otherwise catch it needs the catalogue it
	// no longer has.
	d.Cards = nil
	d.awardTournamentPrize(tid, coachID, nil)
	c, err = d.Store.Coaches.Get(coachID)
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	if len(c.Inventory) != before {
		t.Errorf("inventory grew from %d to %d rows with no card catalogue: "+
			"a zero reward card was granted", before, len(c.Inventory))
	}
}

// TestTournamentPrizeOnlyAtTheWinnerSlot: the prize is for winning the
// tournament, not for winning a round.
//
// This needs a FULL 16-entrant draw. The bracket has a fixed 16-slot first
// round and no byes, so a coach whose sibling slot is empty simply stops
// advancing - with four entrants the winner stalls at slot 4 and slot 1 is
// never reached. That is a real limitation of the bracket (see BUGS B-122), not
// of the prize, so it is pinned separately below and kept out of this test.
func TestTournamentPrizeOnlyAtTheWinnerSlot(t *testing.T) {
	d, coachID, tid := tournamentPrizeDeps(t, 11)
	const prizeCard = 26

	// 16 entrants, the winner first: seeding is by coach id, so coachID takes
	// slot 16 and its opponents are the next ids up.
	m := NewTournamentManager()
	entrants := make([]uint, 0, bracketEntrants)
	for i := 0; i < bracketEntrants; i++ {
		entrants = append(entrants, coachID+uint(i))
	}
	for _, c := range entrants {
		m.Register(c, tid)
	}
	d.Tournaments = m

	before := ownedQty(t, d, coachID, prizeCard)
	rounds := 0
	pay := func(winner, loser uint) {
		if slot := m.RecordMatchResult(tid, winner, loser); slot == bracketWinnerSlot {
			d.awardTournamentPrize(tid, winner, nil)
		}
	}

	// Walk the draw: each round, every occupied sibling pair plays and the LOWER
	// coach id wins, so coachID wins the whole thing.
	for {
		slots := m.BracketSlots(tid)
		played := false
		for slot := int32(bracketSlots) - 1; slot >= 2; slot-- {
			a, okA := slots[slot]
			b, okB := slots[slot^1]
			if !okA || !okB || slot&1 == 1 {
				continue
			}
			if _, done := m.BracketSlots(tid)[slot/2]; done {
				continue
			}
			if a < b {
				pay(a, b)
			} else {
				pay(b, a)
			}
			played = true
		}
		if !played {
			break
		}
		rounds++
		if rounds > 10 {
			t.Fatal("bracket did not settle")
		}
		// The prize must not land before the final.
		if _, decided := m.BracketSlots(tid)[bracketWinnerSlot]; !decided {
			if got := ownedQty(t, d, coachID, prizeCard); got != before {
				t.Fatalf("card %d paid after round %d, before the final: the "+
					"prize is for winning the tournament, not a round",
					prizeCard, rounds)
			}
		}
	}

	if got := m.BracketSlots(tid)[bracketWinnerSlot]; got != coachID {
		t.Fatalf("winner slot = coach %d, want %d", got, coachID)
	}
	if got := ownedQty(t, d, coachID, prizeCard); got != before+1 {
		t.Errorf("card %d quantity %d -> %d, want exactly +1 for the tournament "+
			"win", prizeCard, before, got)
	}
}

// TestBracketStallsWithoutAFullDraw pins the limitation the prize test works
// around: the first round is a fixed 16 slots and there are no byes, so a coach
// whose sibling slot is empty never advances and an under-filled tournament can
// never be won. Recorded as B-122.
func TestBracketStallsWithoutAFullDraw(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	if got := m.RecordMatchResult(7, 1, 2); got != 8 {
		t.Fatalf("advanced to %d, want 8", got)
	}
	if got := m.RecordMatchResult(7, 3, 4); got != 9 {
		t.Fatalf("advanced to %d, want 9", got)
	}
	if got := m.RecordMatchResult(7, 1, 3); got != 4 {
		t.Fatalf("advanced to %d, want 4", got)
	}
	// Slot 4's sibling is slot 5, which nobody reached: coach 1 has won every
	// match available to it and is still not the tournament winner.
	if _, decided := m.BracketSlots(7)[bracketWinnerSlot]; decided {
		t.Error("a 4-entrant draw reached the winner slot - byes are implemented, " +
			"so B-122 and the prize test's 16-entrant workaround can go")
	}
}
