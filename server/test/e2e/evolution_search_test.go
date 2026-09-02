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
	t.Parallel()
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
	t.Parallel()
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
	t.Parallel()
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
	t.Parallel()
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

// --- the CLASSIC twin (23101/23102/23103/23104/23106) ---
//
// Same handshake, same client frame shape (vu_1 instead of wp_0), and it had the
// same gap: 23103 was served but none of the replies were, so the player got no
// "Recherche en cours" overlay — and since the Cancel button lives inside that
// overlay, no way to leave the queue.

const (
	opClassicSearchCancel       = 23101
	opClassicSearchCancelResult = 23102
	opClassicReadyForFight      = 23103
	opClassicSearchResult       = 23104
	opClassicFightStarting      = 23106
)

// TestClassicReadyAcceptsAndCancels: the classic "Combattre" must accept the
// search (23104) so the overlay opens, and must answer its Cancel (23101 →
// 23102) so the player can get out of the queue again.
func TestClassicReadyAcceptsAndCancels(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, coachID := dialLogin(t, addr, "cls_a", "ClsA")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	// teamId -1 = "no preset selected", which the client really does send; it
	// must be tolerated (the roster falls back), not refused.
	_ = c.Send(2, opClassicReadyForFight, testclient.NewW().I64(coachID).U16(0xFFFF).Bytes())
	f, _, err := c.WaitFor(opClassicSearchResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no ClassicSearchResult(23104): no overlay, and no way to "+
			"cancel out of the queue: %v", err)
	}
	if ok := testclient.NewR(f.Payload).U16(); ok != 0xFFFF {
		t.Errorf("echoed teamId = %d, want 65535 (-1 passed through)", ok)
	}

	_ = c.Send(2, opClassicSearchCancel, testclient.NewW().I64(coachID).U16(0xFFFF).Bytes())
	cf, _, err := c.WaitFor(opClassicSearchCancelResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no ClassicSearchCancelResult(23102): %v", err)
	}
	if ok := testclient.NewR(cf.Payload).U8(); ok != 1 {
		t.Errorf("cancel accepted = %d, want 1", ok)
	}
}

// TestClassicReadyPairsAndStartsFight: two coaches ready up, pair, and both get
// 23106 before CREATE_FIGHT — the same ordering requirement as the evolution
// twin, asserted the same way (from the frames that preceded 8000).
func TestClassicReadyPairsAndStartsFight(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, aID := dialLogin(t, addr, "cls_b", "ClsB")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "cls_c", "ClsC")
	reachWorld(t, b)
	a.DrainReceived(200 * time.Millisecond)
	b.DrainReceived(200 * time.Millisecond)

	_ = a.Send(2, opClassicReadyForFight, testclient.NewW().I64(aID).U16(0xFFFF).Bytes())
	_ = b.Send(2, opClassicReadyForFight, testclient.NewW().I64(bID).U16(0xFFFF).Bytes())

	for name, cl := range map[string]*testclient.Client{"A": a, "B": b} {
		_, before, err := cl.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout)
		if err != nil {
			t.Fatalf("%s: no CREATE_FIGHT from a paired classic ready-up: %v", name, err)
		}
		var sawStarting bool
		for _, f := range before {
			if f.Opcode == opClassicFightStarting {
				sawStarting = true
			}
		}
		if !sawStarting {
			t.Errorf("%s: ClassicFightStarting(23106) did not arrive before "+
				"CREATE_FIGHT(8000) — the fight runs under the overlay", name)
		}
	}
}

// TestClassicReadyTwiceDoesNotQueueTwice: clicking "Combattre" twice must not
// leave two entries for the same coach in the queue. Guard: after A readies
// twice, ONE opponent B pairs with it and the queue is then empty — so a third
// coach C finds nobody and waits.
func TestClassicReadyTwiceDoesNotQueueTwice(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	a, aID := dialLogin(t, addr, "cls_d", "ClsD")
	reachWorld(t, a)
	b, bID := dialLogin(t, addr, "cls_e", "ClsE")
	reachWorld(t, b)
	cc, cID := dialLogin(t, addr, "cls_f", "ClsF")
	reachWorld(t, cc)
	for _, cl := range []*testclient.Client{a, b, cc} {
		cl.DrainReceived(200 * time.Millisecond)
	}

	ready := func(cl *testclient.Client, id int64) {
		_ = cl.Send(2, opClassicReadyForFight, testclient.NewW().I64(id).U16(0xFFFF).Bytes())
	}
	ready(a, aID)
	ready(a, aID) // double click
	if _, _, err := a.WaitFor(opClassicSearchResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("A: no 23104: %v", err)
	}
	ready(b, bID)
	if _, _, err := b.WaitFor(testclient.OpCreateFight, testclient.DefaultTimeout); err != nil {
		t.Fatalf("B did not pair with A: %v", err)
	}

	// A's duplicate entry, if it existed, would still be sitting in the queue and
	// would wrongly pair with C.
	ready(cc, cID)
	if _, _, err := cc.WaitFor(testclient.OpCreateFight, 2*time.Second); err == nil {
		t.Error("C paired with a STALE duplicate queue entry for A — clicking " +
			"Combattre twice enqueued the same coach twice")
	}
}

// --- the TOURNAMENT third of the pattern (28609/28610/28611/28612/28616) ---

const (
	opTournamentSearchCancel       = 28609
	opTournamentSearchCancelResult = 28610
	opTournamentSearchRequest      = 28611
	opTournamentSearchError        = 28616
)

// tournamentSearchPayload: [i64 tournamentId][i64 coachId][i16 preset].
func tournamentSearchPayload(tid, coachID int64, preset uint16) []byte {
	return testclient.NewW().I64(tid).I64(coachID).U16(preset).Bytes()
}

// TestTournamentSearchIsRefusedVisibly: there is no bracket/match layer yet, so
// the tournament "Combattre" must FAIL VISIBLY rather than go silent. Silence is
// the actual bug here — both client senders pop the team panel themselves, so an
// unanswered 28611 leaves the player on a bare screen with nothing to click.
//
// The reply must also be TWO bytes: kw_1 reads a code and a sub-code, unlike the
// classic/evolution error which is one byte. A short frame is a decode failure.
func TestTournamentSearchIsRefusedVisibly(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, coachID := dialLogin(t, addr, "tsr_a", "TsrA")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, opTournamentSearchRequest, tournamentSearchPayload(1, coachID, 1))
	f, _, err := c.WaitFor(opTournamentSearchError, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no TournamentSearchError(28616): the tournament Combattre goes "+
			"silent and the player is left on a bare screen: %v", err)
	}
	if n := len(f.Payload); n != 2 {
		t.Fatalf("28616 payload = %d bytes, want 2 ([i8 code][i8 subCode] — kw_1 "+
			"reads both)", n)
	}
	r := testclient.NewR(f.Payload)
	if code := r.U8(); code != 1 {
		t.Errorf("error code = %d, want 1 (impossibleToStartOpponentsSearch)", code)
	}
}

// TestTournamentSearchFromLegendsTabIsAlsoAnswered: the Légendes tab sends the
// same 28611 with the legend pseudo-preset 9999, so it must not fall through to
// silence either.
func TestTournamentSearchFromLegendsTabIsAlsoAnswered(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, coachID := dialLogin(t, addr, "tsr_b", "TsrB")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, opTournamentSearchRequest, tournamentSearchPayload(0, coachID, 9999))
	if _, _, err := c.WaitFor(opTournamentSearchError, testclient.DefaultTimeout); err != nil {
		t.Fatalf("Légendes (preset 9999) got no answer to 28611: %v", err)
	}
}

// TestTournamentSearchCancelIsAnswered: the cancel reply is what closes the
// overlay and unregisters ds_2, so it goes out even though nothing was queued.
func TestTournamentSearchCancelIsAnswered(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, coachID := dialLogin(t, addr, "tsr_c", "TsrC")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(2, opTournamentSearchCancel, tournamentSearchPayload(1, coachID, 1))
	f, _, err := c.WaitFor(opTournamentSearchCancelResult, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no TournamentSearchCancelResult(28610): %v", err)
	}
	if ok := testclient.NewR(f.Payload).U8(); ok != 1 {
		t.Errorf("cancel accepted = %d, want 1", ok)
	}
}

// TestEvolutionFightFeedsProgression: the fight a paired search produces must be
// a real EVOLUTION fight, not a practice one — that is the whole point of the
// mode. Proven from the outside by its effect: an evolution fight banks XP onto
// the fighters, a practice fight does not.
func TestEvolutionFightFeedsProgression(t *testing.T) {
	t.Parallel()
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

// --- unopposed tournament entrant (28648) ---

const (
	opTournamentRegister      = 4607
	opTournamentRegisterReply = 28608
	opTournamentSearchResult  = 28612
	opTournamentSearchEnded   = 28648
)

// TestUnopposedTournamentEntrantIsDeclaredWinner: a coach that readies up with
// nobody left to play must be TOLD, not left queued.
//
// The client's waiting overlay (tournamentsSearchStatusDialog) is dismissed by
// exactly one server message - 28648 on its winner branch - so an entrant whose
// half of the draw is empty would otherwise sit in that dialog for the rest of
// the session waiting for an opponent who cannot exist.
//
// A sole entrant is the extreme case: the byes carry it from slot 16 to the
// root, so it has won the tournament before playing anything.
func TestUnopposedTournamentEntrantIsDeclaredWinner(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	tr := &domain.Tournament{
		DefID: 1, Name: "Lonely Cup", Short: "LC",
		Enabled: true, RegistrationOpen: true,
	}
	_ = tr
	if err := st.Tournaments.Create(tr); err != nil {
		t.Fatalf("create tournament: %v", err)
	}
	tid := tr.WireID()

	c, coachID := dialLogin(t, addr, "solo_t", "SoloT")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	// Register through the real 4607 path.
	_ = c.Send(3, opTournamentRegister, testclient.NewW().
		I64(tid).I64(coachID).U16(0xFFFF).I32(0).Bytes())
	f, _, err := c.WaitFor(opTournamentRegisterReply, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no registration reply: %v", err)
	}
	r := testclient.NewR(f.Payload)
	_ = r.I64()
	if code := r.U8(); code != 0 {
		t.Fatalf("registration refused with code %d", code)
	}

	// Close registration. Until it closes, an empty half of the draw only means
	// nobody has entered YET, so the server correctly keeps the coach waiting;
	// once closed, no opponent can ever arrive.
	if err := st.DB().Table("tournaments").Where("id = ?", tr.ID).
		Update("registration_open", false).Error; err != nil {
		t.Fatalf("close registration: %v", err)
	}

	// Ready up. 28612 accepts and opens the overlay; 28648 must then close it.
	_ = c.Send(2, opTournamentSearchRequest, tournamentSearchPayload(tid, coachID, 1))
	if _, _, err := c.WaitFor(opTournamentSearchResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no 28612 accept: %v", err)
	}
	f, _, err = c.WaitFor(opTournamentSearchEnded, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no TournamentSearchEnded(28648): an unopposed entrant is left "+
			"in the waiting dialog forever: %v", err)
	}
	if n := len(f.Payload); n != 9 {
		t.Fatalf("28648 payload = %d bytes, want 9 ([i64 tid][i8 forfeit])", n)
	}
	r = testclient.NewR(f.Payload)
	if got := r.I64(); got != tid {
		t.Errorf("28648 tid = %d, want %d", got, tid)
	}
	if forfeit := r.U8(); forfeit != 0 {
		t.Errorf("28648 forfeit = %d, want 0: the unopposed coach is the WINNER "+
			"by forfeit, not the one forfeiting", forfeit)
	}
}

// TestLoneEntrantWaitsWhileRegistrationIsOpen is the other half of the rule.
//
// Byes are derived from the current entrant list, so an empty half of the draw
// while registration is OPEN means only "nobody has entered there yet". Handing
// the tournament - and its prize - to whoever pressed Combattre first would be
// both wrong and unrecoverable.
func TestLoneEntrantWaitsWhileRegistrationIsOpen(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	tr := &domain.Tournament{
		DefID: 1, Name: "Open Cup", Short: "OC",
		Enabled: true, RegistrationOpen: true,
	}
	if err := st.Tournaments.Create(tr); err != nil {
		t.Fatalf("create tournament: %v", err)
	}
	tid := tr.WireID()

	c, coachID := dialLogin(t, addr, "open_t", "OpenT")
	reachWorld(t, c)
	c.DrainReceived(200 * time.Millisecond)

	_ = c.Send(3, opTournamentRegister, testclient.NewW().
		I64(tid).I64(coachID).U16(0xFFFF).I32(0).Bytes())
	if _, _, err := c.WaitFor(opTournamentRegisterReply, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no registration reply: %v", err)
	}

	_ = c.Send(2, opTournamentSearchRequest, tournamentSearchPayload(tid, coachID, 1))
	if _, _, err := c.WaitFor(opTournamentSearchResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no 28612 accept: %v", err)
	}
	if f, _, err := c.WaitFor(opTournamentSearchEnded, 700*time.Millisecond); err == nil {
		t.Fatalf("got 28648 (payload %d bytes) while registration was still open: "+
			"the coach was handed the tournament before its opponents could enter",
			len(f.Payload))
	}
}

// TestClosedTournamentWithAnOpponentStillWaits: registration being closed is not
// on its own a licence to declare a winner - there must actually be nobody in
// the other half of the draw.
//
// Two entrants seed at slots 16 and 17, so the first to ready up has a real
// opponent and must wait for it, not be handed the tournament.
func TestClosedTournamentWithAnOpponentStillWaits(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	tr := &domain.Tournament{
		DefID: 1, Name: "Duo Cup", Short: "DC",
		Enabled: true, RegistrationOpen: true,
	}
	if err := st.Tournaments.Create(tr); err != nil {
		t.Fatalf("create tournament: %v", err)
	}
	tid := tr.WireID()

	register := func(login, name string) (*testclient.Client, int64) {
		c, coachID := dialLogin(t, addr, login, name)
		reachWorld(t, c)
		c.DrainReceived(200 * time.Millisecond)
		_ = c.Send(3, opTournamentRegister, testclient.NewW().
			I64(tid).I64(coachID).U16(0xFFFF).I32(0).Bytes())
		if _, _, err := c.WaitFor(opTournamentRegisterReply, testclient.DefaultTimeout); err != nil {
			t.Fatalf("%s: no registration reply: %v", name, err)
		}
		return c, coachID
	}
	a, aID := register("dc_a", "DcA")
	register("dc_b", "DcB")

	if err := st.DB().Table("tournaments").Where("id = ?", tr.ID).
		Update("registration_open", false).Error; err != nil {
		t.Fatalf("close registration: %v", err)
	}

	_ = a.Send(2, opTournamentSearchRequest, tournamentSearchPayload(tid, aID, 1))
	if _, _, err := a.WaitFor(opTournamentSearchResult, testclient.DefaultTimeout); err != nil {
		t.Fatalf("no 28612 accept: %v", err)
	}
	if _, _, err := a.WaitFor(opTournamentSearchEnded, 700*time.Millisecond); err == nil {
		t.Fatal("got 28648: a coach with a real opponent in the draw was declared " +
			"winner by forfeit instead of waiting for it")
	}
}
