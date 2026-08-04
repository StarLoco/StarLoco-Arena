package e2e

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// readLadderStr reads a ladder string ([i32 len][utf8]).
func readLadderStr(r *testclient.R) string {
	n := int(r.I32())
	if n <= 0 {
		return ""
	}
	return string(r.RawN(n))
}

// TestLadder1v1ShowsRankedCoach: a coach with a rating appears on the 1v1 board
// with its stats and its own rank. This is the board's whole purpose, and proves
// the window contract (end = start + rows) end-to-end against the wire.
func TestLadder1v1ShowsRankedCoach(t *testing.T) {
	st, addr := testServerWithStore(t)
	c, id := dialLogin(t, addr, "lad1", "Ranker")
	reachWorld(t, c)

	// Give the coach a rating (fights are exercised elsewhere; here we only need a
	// ranked row to exist). The ladder queries read from the DB.
	coach, err := st.Coaches.Get(uint(id))
	if err != nil {
		t.Fatalf("get coach: %v", err)
	}
	coach.Strength = 1500
	coach.StatWins = 10
	coach.StatLosses = 3
	coach.ConsecutiveWins = 4
	if err := st.Coaches.Save(coach); err != nil {
		t.Fatalf("save coach: %v", err)
	}

	_ = c.Send(2, testclient.OpLadderRequest, testclient.NewW().I32(0).Bytes())
	f, _, err := c.WaitFor(testclient.OpLadderResponse, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 27501: %v", err)
	}
	r := testclient.NewR(f.Payload)
	total := r.I32()
	start := r.I32()
	end := r.I32()
	myRank := r.I32()
	if total < 1 {
		t.Fatalf("total = %d, want >= 1", total)
	}
	if end != start+total && end != start+20 { // window = min(total, pageSize)
		// end must equal start + (# rows that follow); we assert that by consuming.
	}
	if myRank != 1 {
		t.Errorf("myRank = %d, want 1 (only ranked coach)", myRank)
	}

	var found bool
	for j := int32(0); j < end-start; j++ {
		name := readLadderStr(r)
		_ = readLadderStr(r) // guild
		rating := int32(int16(r.U16()))
		r.I32()
		r.I32()
		streak := r.I32()
		r.I32()
		wins := r.I32()
		losses := r.I32()
		if name == "Ranker" {
			found = true
			if rating != 1500 || streak != 4 || wins != 10 || losses != 3 {
				t.Errorf("row = (rating %d, streak %d, w %d, l %d), want (1500,4,10,3)",
					rating, streak, wins, losses)
			}
		}
	}
	if !found {
		t.Error("the ranked coach did not appear on the 1v1 board")
	}
	if search := r.U8(); search != 1 {
		t.Errorf("trailing search flag = %d, want 1", search)
	}
	if rem := r.Remaining(); rem != 0 {
		t.Errorf("payload has %d trailing bytes — window/row contract is off", rem)
	}
}

// TestLadderAllBoardsRenderCleanly: every board tab answers without desyncing the
// stream — the guild and 2v2 boards come back well-formed empty, and the seasonal
// board is deliberately silent (its tab stays blank without breaking the others).
func TestLadderAllBoardsRenderCleanly(t *testing.T) {
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "lad2", "Boards")
	reachWorld(t, c)

	// Guild board: [i16 board][i32 start] -> 27503 with board==1, zero rows.
	_ = c.Send(2, testclient.OpGuildLadderRequest, testclient.NewW().U16(1).I32(0).Bytes())
	g, _, err := c.WaitFor(testclient.OpGuildLadder, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 27503: %v", err)
	}
	gr := testclient.NewR(g.Payload)
	if board := gr.U16(); board != 1 {
		t.Errorf("guild board = %d, want 1 (else the client shows nothing)", board)
	}
	gr.I32() // start
	if n := gr.I32(); n != 0 {
		t.Errorf("guild rows = %d, want 0", n)
	}
	if gr.Remaining() != 0 {
		t.Errorf("guild payload has %d trailing bytes", gr.Remaining())
	}

	// 2v2 board: [i32 start] -> 27505 well-formed empty (trailing flag is i32).
	_ = c.Send(2, testclient.OpLadder2v2Request, testclient.NewW().I32(0).Bytes())
	tv, _, err := c.WaitFor(testclient.OpLadder2v2Response, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 27505: %v", err)
	}
	tr := testclient.NewR(tv.Payload)
	if total := tr.I32(); total != 0 {
		t.Errorf("2v2 total = %d, want 0", total)
	}
	tr.I32() // start
	tr.I32() // end
	if icons := tr.I32(); icons != 0 {
		t.Errorf("2v2 icon count = %d, want 0", icons)
	}
	if flag := tr.I32(); flag != 1 {
		t.Errorf("2v2 trailing flag = %d, want 1", flag)
	}
	if tr.Remaining() != 0 {
		t.Errorf("2v2 payload has %d trailing bytes", tr.Remaining())
	}

	// Coach reputation board: [i32 start] -> 27509, well-formed empty.
	_ = c.Send(2, testclient.OpCoachReputationRequest, testclient.NewW().I32(0).Bytes())
	cr, _, err := c.WaitFor(testclient.OpCoachReputation, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 27509: %v", err)
	}
	rr := testclient.NewR(cr.Payload)
	if total := rr.I32(); total != 0 {
		t.Errorf("coach-reputation total = %d, want 0", total)
	}
	rr.I32() // start
	rr.I32() // end
	rr.I32() // localIdx
	rr.U8()  // search flag
	if rr.Remaining() != 0 {
		t.Errorf("coach-reputation payload has %d trailing bytes", rr.Remaining())
	}

	// Démon tab: [i16 flag][i32 start] -> 27513 listing the first 12 demons.
	_ = c.Send(2, testclient.OpDemonListRequest, testclient.NewW().U16(1).I32(0).Bytes())
	dl, _, err := c.WaitFor(testclient.OpDemonList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 27513: %v", err)
	}
	dr := testclient.NewR(dl.Payload)
	if flag := dr.U16(); flag != 1 {
		t.Errorf("demon-list status flag = %d, want 1", flag)
	}
	dr.I32() // start
	if count := dr.I32(); count != 12 {
		t.Errorf("demon-list count = %d, want 12 (first page of 24)", count)
	}
	if first := dr.U16(); first != 1 {
		t.Errorf("first demonId = %d, want 1", first)
	}

	// Tournoi board: [i32 mStart][i32 tStart][i32 yStart][i8 m][i8 t][i16 y] ->
	// 27507 carrying THREE empty windows (month/trimester/year). This tab used to
	// get no reply at all and rendered blank (B-046).
	tq := testclient.NewW().I32(0).I32(0).I32(0).U8(3).U8(1).U16(2026).Bytes()
	_ = c.Send(2, testclient.OpTournamentLadderReq, tq)
	tl, _, err := c.WaitFor(testclient.OpTournamentLadder, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 27507: %v", err)
	}
	tlr := testclient.NewR(tl.Payload)
	if m := tlr.U8(); m != 3 {
		t.Errorf("month = %d, want 3 (echoed)", m)
	}
	if tr2 := tlr.U8(); tr2 != 1 {
		t.Errorf("trimester = %d, want 1 (echoed)", tr2)
	}
	if y := tlr.U16(); y != 2026 {
		t.Errorf("year = %d, want 2026 (echoed)", y)
	}
	tlr.I32() // my points, month
	tlr.I32() // my points, trimester
	tlr.I32() // my points, year
	for blk := 0; blk < 3; blk++ {
		if total := tlr.I32(); total != 0 {
			t.Errorf("block %d total = %d, want 0 (client clears/indexes a pre-sized list)", blk, total)
		}
		start := tlr.I32()
		end := tlr.I32()
		if end != start {
			t.Errorf("block %d window = [%d,%d), want end==start (zero rows)", blk, start, end)
		}
		tlr.I32() // my rank
		tlr.U8()  // search button
	}
	if rem := tlr.Remaining(); rem != 0 {
		t.Errorf("27507 has %d trailing bytes", rem)
	}

	// Ligue Pro board: [i32 start][i32 leagueId][i32 pageSize] -> 27515 empty.
	// This opcode previously had no handler at all (B-046). The league id round-
	// trips through the client, so an id it cannot name would title the tab
	// "!content.58.N!"; only 1 and 3 exist. Check BOTH paths.
	readProLeague := func(t *testing.T, reqLeague int32) int32 {
		t.Helper()
		pq := testclient.NewW().I32(0).I32(reqLeague).I32(20).Bytes()
		_ = c.Send(2, testclient.OpProLeagueLadderReq, pq)
		pl, _, err := c.WaitFor(testclient.OpProLeagueLadder, testclient.DefaultTimeout)
		if err != nil {
			t.Fatalf("no 27515 for league %d: %v", reqLeague, err)
		}
		plr := testclient.NewR(pl.Payload)
		if total := plr.I32(); total != 0 {
			t.Errorf("pro-league total = %d, want 0 (it bounds the client's clear loop)", total)
		}
		pstart, pend := plr.I32(), plr.I32()
		if pend != pstart {
			t.Errorf("pro-league window = [%d,%d), want end==start", pstart, pend)
		}
		plr.I32() // my rank
		got := plr.I32()
		plr.U8() // search button
		if rem := plr.Remaining(); rem != 0 {
			t.Errorf("27515 has %d trailing bytes", rem)
		}
		return got
	}
	// 0 = the client's initial value (it has no league table): must be normalised
	// to a nameable league, else the tab is titled "!content.58.0!".
	if got := readProLeague(t, 0); got != 1 {
		t.Errorf("league id for request 0 = %d, want 1 (normalised to a nameable league)", got)
	}
	// A league the client CAN name must be preserved, not overridden.
	if got := readProLeague(t, 3); got != 3 {
		t.Errorf("league id for request 3 = %d, want 3 (valid selection preserved)", got)
	}
}
