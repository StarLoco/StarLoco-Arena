package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// The evolution opponent search (the EVOLUTION tab's "Combattre").
//
//	C2S 23003 {i64 coachId, i16 99}
//	S2C 23004 {i16 99, i8 1}     opens the client's "Searching…" overlay
//	S2C 23006 {}                 closes it — MUST arrive before CREATE_FIGHT
//	S2C 8000  CREATE_FIGHT
//
// The ordering is the part worth testing over a real socket: the client closes
// that overlay on 23006 and on nothing else, so a fight that starts without it
// runs underneath a "Searching…" veil.
const (
	opEvoSearchCancel       = 23001
	opEvoSearchCancelResult = 23002
	opEvoSearchRequest      = 23003
	opEvoSearchResult       = 23004
	opEvoFightStarting      = 23006
	opEvoSearchError        = 23008

	evoPreset = 99
)

func evoSearchPayload(coachID int64) []byte {
	return testclient.NewW().I64(coachID).U16(evoPreset).Bytes()
}

// giveTitulars creates n titular fighters for a coach, so the evolution search
// has a team to field.
func giveTitulars(t *testing.T, st interface {
	Create(*domain.Fighter) error
}, coachID int64, names ...string) {
	t.Helper()
	for _, n := range names {
		f := &domain.Fighter{CoachID: uint(coachID), BreedID: 1, Name: n,
			State: domain.FighterStateTitular}
		if err := st.Create(f); err != nil {
			t.Fatalf("create fighter %s: %v", n, err)
		}
	}
}

// TestEvolutionSearchAcceptsAndWaits: a lone searcher is ACCEPTED (23004 with
// accepted=1, which is what opens the overlay) and then simply waits. Sending
// accepted=0 instead would leave the player on a bare screen — the client pops
// its team panels either way but only opens the overlay on true.
func TestEvolutionSearchAcceptsAndWaits(t *testing.T) {
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "evo_a", "EvoA")
	reachWorld(t, c)
	giveTitulars(t, st.Fighters, coachID, "A1", "A2")
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, opEvoSearchRequest, evoSearchPayload(coachID))

	f, _, err := c.WaitFor(opEvoSearchResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no EvolutionSearchResult(23004): the client waits on a silent "+
			"screen forever: %v", err)
	}
	r := testclient.NewR(f.Payload)
	if preset := r.U16(); preset != evoPreset {
		t.Errorf("echoed preset = %d, want %d", preset, evoPreset)
	}
	if ok := r.U8(); ok != 1 {
		t.Errorf("accepted = %d, want 1 (0 opens no overlay and shows nothing)", ok)
	}
}

// TestEvolutionSearchCancel: the overlay's Cancel button (23001) must be
// answered with 23002, because that reply is what actually closes the overlay
// and unregisters the client's search frame.
func TestEvolutionSearchCancel(t *testing.T) {
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "evo_b", "EvoB")
	reachWorld(t, c)
	giveTitulars(t, st.Fighters, coachID, "B1", "B2")
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, opEvoSearchRequest, evoSearchPayload(coachID))
	if _, _, err := c.WaitFor(opEvoSearchResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no 23004: %v", err)
	}

	_ = c.Send(2, opEvoSearchCancel, evoSearchPayload(coachID))
	f, _, err := c.WaitFor(opEvoSearchCancelResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no EvolutionSearchCancelResult(23002): the overlay never "+
			"closes: %v", err)
	}
	if ok := testclient.NewR(f.Payload).U8(); ok != 1 {
		t.Errorf("cancel accepted = %d, want 1", ok)
	}
}

// TestEvolutionSearchWithoutATeamIsRefused: an empty titular line-up gets a
// visible error (23008), not silence. The retail client guards this itself, so
// reaching the server means something is wrong and the player still deserves to
// be told.
func TestEvolutionSearchWithoutATeamIsRefused(t *testing.T) {
	addr := testServer(t)
	c, coachID := dialLogin(t, addr, "evo_c", "EvoC")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, opEvoSearchRequest, evoSearchPayload(coachID))
	f, _, err := c.WaitFor(opEvoSearchError, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no EvolutionSearchError(23008) for an empty team: %v", err)
	}
	if code := testclient.NewR(f.Payload).U8(); code != 2 {
		t.Errorf("error code = %d, want 2 (matchfinder.badTeam)", code)
	}
}

// TestEvolutionSearchPairsAndStartsFight is the one that matters: two coaches
// search, pair, and BOTH must receive 23006 BEFORE CREATE_FIGHT(8000).
//
// The order is asserted from WaitFor's `seen` — the frames that arrived ahead of
// the one it matched. Waiting for 23006 and then for 8000 would NOT prove
// anything: WaitFor skips over frames it does not want, so it succeeds whatever
// the order. Asking for 8000 and then looking for 23006 among the frames that
// preceded it is the assertion that actually holds.
func TestEvolutionSearchPairsAndStartsFight(t *testing.T) {
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "evo_d", "EvoD")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "evo_e", "EvoE")
	reachWorld(t, b)
	giveTitulars(t, st.Fighters, aID, "D1", "D2")
	giveTitulars(t, st.Fighters, bID, "E1", "E2")
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(2, opEvoSearchRequest, evoSearchPayload(aID))
	if _, _, err := a.WaitFor(opEvoSearchResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("A: no 23004: %v", err)
	}
	_ = b.Send(2, opEvoSearchRequest, evoSearchPayload(bID))

	for _, c := range []struct {
		name string
		cl   *testclient.Client
	}{{"A", a}, {"B", b}} {
		_, before, err := c.cl.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout)
		if err != nil {
			t.Fatalf("%s: no CREATE_FIGHT(8000) from a paired evolution search: %v",
				c.name, err)
		}
		var sawStarting bool
		for _, f := range before {
			if f.Opcode == opEvoFightStarting {
				sawStarting = true
			}
		}
		if !sawStarting {
			t.Errorf("%s: EvolutionFightStarting(23006) did not arrive BEFORE "+
				"CREATE_FIGHT(8000) — the fight runs underneath the \"Searching…\" "+
				"overlay, which closes on 23006 and on nothing else", c.name)
		}
	}
}

// TestEvolutionFightFeedsProgression: the fight a paired search produces must be
// a real EVOLUTION fight, not a practice one — that is the whole point of the
// mode. Proven from the outside by its effect: an evolution fight banks XP onto
// the fighters, a practice fight does not.
func TestEvolutionFightFeedsProgression(t *testing.T) {
	st, addr := testServerWithStore(t)
	a, aID := dialLogin(t, addr, "evo_f", "EvoF")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "evo_g", "EvoG")
	reachWorld(t, b)
	giveTitulars(t, st.Fighters, aID, "F1", "F2")
	giveTitulars(t, st.Fighters, bID, "G1", "G2")
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(2, opEvoSearchRequest, evoSearchPayload(aID))
	_ = b.Send(2, opEvoSearchRequest, evoSearchPayload(bID))
	for _, c := range []*testclient.Client{a, b} {
		if _, _, err := c.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
			t.Fatalf("no CREATE_FIGHT: %v", err)
		}
	}

	// End it: one side gives up, both get END_FIGHT.
	_ = a.Send(3, testclient.OpGiveUp, nil)
	for _, c := range []*testclient.Client{a, b} {
		if _, _, err := c.WaitFor(testclient.OpEndFight, 8*time.Second); err != nil {
			t.Fatalf("no END_FIGHT: %v", err)
		}
	}

	// The winner's fighters must have banked XP — only a progression fight does
	// that, so this fails if the fight was created as practice.
	fighters, err := st.Fighters.ListByCoach(uint(bID))
	if err != nil {
		t.Fatalf("list fighters: %v", err)
	}
	var withXP int
	for _, f := range fighters {
		if f.TotalXP > 0 {
			withXP++
		}
	}
	if withXP == 0 {
		t.Error("no fighter banked XP — the paired search produced a fight that " +
			"does not feed progression (practice?), so evolution mode is inert")
	}
}
