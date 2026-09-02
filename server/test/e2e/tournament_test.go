package e2e

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

// readStr32 reads an [i32 len][utf8] string (the tournament list/tree string form).
func readStr32(r *testclient.R) string {
	n := int(r.I32())
	if n <= 0 {
		return ""
	}
	return string(r.RawN(n))
}

// tournamentStatus walks a 28602 payload and returns the coachStatus byte reported
// for tid (and whether tid was found).
func tournamentStatus(payload []byte, tid int64) (int8, bool) {
	r := testclient.NewR(payload)
	count := r.I32()
	for i := int32(0); i < count; i++ {
		id := r.I64()
		r.U8() // openedSearch
		status := int8(r.U8())
		r.U16()      // defID
		r.U8()       // registrationOpen
		r.I32()      // fightParamCount
		readStr32(r) // name
		readStr32(r) // desc
		readStr32(r) // organizer
		r.U8()       // kind
		if id == tid {
			return status, true
		}
	}
	return 0, false
}

// TestTournamentListShowsRegisterableTournaments: the totem's list request
// (28601 -> 28602) returns the standing tournaments, each not-registered (-128) and
// open for registration, and the payload is consumed exactly.
func TestTournamentListShowsRegisterableTournaments(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "trn1", "Joiner")
	reachWorld(t, c)

	_ = c.Send(2, testclient.OpTournamentListReq, nil)
	f, _, err := c.WaitFor(testclient.OpTournamentList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 28602: %v", err)
	}
	r := testclient.NewR(f.Payload)
	count := r.I32()
	if count < 1 {
		t.Fatalf("tournament count = %d, want >= 1", count)
	}
	for i := int32(0); i < count; i++ {
		if tid := r.I64(); tid == 0 {
			t.Errorf("row %d has a zero id", i)
		}
		r.U8() // openedSearch
		if status := int8(r.U8()); status != -128 {
			t.Errorf("row %d status = %d, want -128 (not registered)", i, status)
		}
		r.U16() // defID
		if regOpen := r.U8(); regOpen != 1 {
			t.Errorf("row %d registrationOpen = %d, want 1", i, regOpen)
		}
		if fp := r.I32(); fp != 0 {
			t.Errorf("row %d fightParamCount = %d, want 0", i, fp)
		}
		if name := readStr32(r); name == "" {
			t.Errorf("row %d has an empty name", i)
		}
		readStr32(r) // desc
		readStr32(r) // organizer
		r.U8()       // kind
	}
	if rem := r.Remaining(); rem != 0 {
		t.Errorf("28602 has %d trailing bytes", rem)
	}
}

// TestTournamentCalendarShowsEvents: the calendar request (17002 -> 17003) returns
// the standing tournaments as typeId=4 events, each with >=1 registration-period
// pair (the client reads element 0 of that list unguarded), consumed exactly.
func TestTournamentCalendarShowsEvents(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "trn2", "Cal")
	reachWorld(t, c)

	_ = c.Send(3, testclient.OpTournamentCalReq, nil)
	f, _, err := c.WaitFor(testclient.OpTournamentCalendar, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 17003: %v", err)
	}
	r := testclient.NewR(f.Payload)
	count := int(r.U16())
	if count < 1 {
		t.Fatalf("event count = %d, want >= 1", count)
	}
	for i := 0; i < count; i++ {
		if typeID := r.I32(); typeID != 4 {
			t.Errorf("event %d typeId = %d, want 4 (tournament prototype)", i, typeID)
		}
		r.I64() // eventId
		r.I64() // startDate
		r.I64() // endDate
		r.I64() // recurrence
		r.I32() // labelIndex
		r.I64() // extraDate
		r.I64() // tournamentId
		r.Str8()
		dl := int(r.U16())
		r.RawN(dl) // desc (i16-prefixed)
		r.Str8()   // short
		for j, sched := 0, int(r.U8()); j < sched; j++ {
			r.I64()
			r.I64()
		}
		reg := int(r.U8())
		if reg < 1 {
			t.Errorf("event %d has %d registration-period pairs, want >= 1", i, reg)
		}
		for j := 0; j < reg; j++ {
			r.I64()
			r.I64()
		}
	}
	if rem := r.Remaining(); rem != 0 {
		t.Errorf("17003 has %d trailing bytes", rem)
	}
}

// TestTournamentRegister: registering (4607) for a listed tournament is accepted
// (28608 err 0), and the re-fetched list reports that tournament as registered.
func TestTournamentRegister(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "trn3", "Reg")
	reachWorld(t, c)

	// List, take the first tournament id.
	_ = c.Send(2, testclient.OpTournamentListReq, nil)
	f, _, err := c.WaitFor(testclient.OpTournamentList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 28602: %v", err)
	}
	lr := testclient.NewR(f.Payload)
	if lr.I32() < 1 {
		t.Fatal("empty tournament list")
	}
	tid := lr.I64()

	// Register: [i64 tid][i64 coachId][i16 preset=-1][i32 card=0]. 0xFFFF is i16 -1.
	body := testclient.NewW().I64(tid).I64(0).U16(0xFFFF).I32(0).Bytes()
	_ = c.Send(3, testclient.OpTournamentRegister, body)
	rf, _, err := c.WaitFor(testclient.OpTournamentRegisterReply, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 28608: %v", err)
	}
	rr := testclient.NewR(rf.Payload)
	if got := rr.I64(); got != tid {
		t.Errorf("reply tid = %d, want %d", got, tid)
	}
	if code := rr.U8(); code != 0 {
		t.Fatalf("register errorCode = %d, want 0 (accepted)", code)
	}
	if rr.Remaining() != 0 {
		t.Errorf("28608 has %d trailing bytes", rr.Remaining())
	}

	// Re-list: the registered tournament now reports a non -128 status.
	_ = c.Send(2, testclient.OpTournamentListReq, nil)
	f2, _, err := c.WaitFor(testclient.OpTournamentList, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 28602 (2nd): %v", err)
	}
	status, found := tournamentStatus(f2.Payload, tid)
	if !found {
		t.Fatalf("tournament %d missing from re-fetched list", tid)
	}
	if status == -128 {
		t.Errorf("tournament %d still reports not-registered after 4607", tid)
	}
}

// TestTournamentTreeEmpty: the bracket request (28649) returns a well-formed empty
// tree (28650) — the client shows "tree unavailable" and the stream stays aligned.
func TestTournamentTreeEmpty(t *testing.T) {
	t.Parallel()
	addr := testServer(t)
	c, _ := dialLogin(t, addr, "trn4", "Tree")
	reachWorld(t, c)

	body := testclient.NewW().I64(2600001).I32(0).I32(0).Bytes() // tid, round 0, empty name
	_ = c.Send(2, testclient.OpTournamentTreeReq, body)
	f, _, err := c.WaitFor(testclient.OpTournamentTree, testclient.DefaultTimeout)
	if err != nil {
		t.Fatalf("no 28650: %v", err)
	}
	r := testclient.NewR(f.Payload)
	if sz := r.I32(); sz != 0 {
		t.Errorf("treeSize = %d, want 0", sz)
	}
	if n := r.I32(); n != 0 {
		t.Errorf("node count = %d, want 0", n)
	}
	r.I32() // bib
	if rem := r.Remaining(); rem != 0 {
		t.Errorf("28650 has %d trailing bytes", rem)
	}
}
