package game

import (
	"encoding/binary"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// noCardDefIDs are the retail 2.70 tournament definition ids (data.bdat type-1000
// aub records) whose referenceCardId == 0 — i.e. a coach can register without
// owning a card. A 28602 row MUST use one of the 22 real definition ids or the
// client NPEs dereferencing it; sticking to the no-card subset also lets
// registration complete without a card check. See BUGS.md B-044.
var noCardDefIDs = map[uint16]bool{
	1: true, 4: true, 5: true, 6: true, 7: true, 8: true, 9: true, 10: true,
	11: true, 12: true, 14: true, 15: true, 16: true, 17: true, 18: true,
	20: true, 21: true, 22: true, 23: true, 24: true,
}

// TestStandingTournamentsAreCrashSafe locks the invariants that keep the retail
// client from throwing: every standing tournament must reference a real no-card
// definition id, ids must be unique, and the name must fit the qr_0 i8 length
// prefix (a name > 127 bytes presents a negative length and crashes the decoder).
func TestStandingTournamentsAreCrashSafe(t *testing.T) {
	if len(standingTournamentTable) == 0 {
		t.Fatal("no standing tournaments defined")
	}
	seen := map[int64]bool{}
	for _, tr := range standingTournamentTable {
		if !noCardDefIDs[tr.defID] {
			t.Errorf("tournament %q defID %d is not a real no-card definition -> client would NPE", tr.name, tr.defID)
		}
		if tr.id == 0 {
			t.Errorf("tournament %q has a zero id", tr.name)
		}
		if seen[tr.id] {
			t.Errorf("duplicate tournament id %d", tr.id)
		}
		seen[tr.id] = true
		if len(tr.name) > 127 {
			t.Errorf("name %q is %d bytes; exceeds the qr_0 i8 length prefix (127)", tr.name, len(tr.name))
		}
		if len(tr.short) > 127 {
			t.Errorf("short %q is %d bytes; exceeds the qr_0 i8 length prefix (127)", tr.short, len(tr.short))
		}
	}
}

// TestTournamentCalendarWireIsExact re-decodes 17003 exactly the way the client's
// awa_0/qr_0 decoders do and asserts nothing is left over: a byte too many or too
// few silently drops the frame in the client and the calendar never opens. It also
// enforces the two crash traps: typeId must be 4 (the tournament content prototype)
// and each event must carry at least one registration-period pair (qr_0 reads
// element 0 of that list unguarded).
func TestTournamentCalendarWireIsExact(t *testing.T) {
	frame, err := buildTournamentCalendar()
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if got := binary.BigEndian.Uint16(frame[2:4]); got != protocol.OpTournamentCalendar {
		t.Fatalf("opcode = %d, want %d", got, protocol.OpTournamentCalendar)
	}
	r := protocol.NewReader(frame[4:])
	count, err := r.U16()
	if err != nil {
		t.Fatalf("count: %v", err)
	}
	if int(count) != len(standingTournamentTable) {
		t.Fatalf("event count = %d, want %d", count, len(standingTournamentTable))
	}
	for i := 0; i < int(count); i++ {
		typeID, _ := r.I32()
		if typeID != tournamentContentTypeID {
			t.Errorf("event %d typeId = %d, want %d (qr_0 prototype)", i, typeID, tournamentContentTypeID)
		}
		// iz_0 base + th_2 extra date.
		r.I64() // eventId
		r.I64() // startDate
		r.I64() // endDate
		r.I64() // recurrence
		r.I32() // labelIndex
		r.I64() // extraDate
		// qr_0 fields.
		r.I64() // tournamentId
		name, _ := r.StringU8()
		descLen, _ := r.U16()
		r.String(int(descLen))
		r.StringU8() // short
		sched, _ := r.U8()
		for j := 0; j < int(sched); j++ {
			r.I64()
			r.I64()
		}
		reg, _ := r.U8()
		if reg < 1 {
			t.Errorf("event %d has %d registration-period pairs; must be >=1 (client reads element 0 unguarded)", i, reg)
		}
		for j := 0; j < int(reg); j++ {
			r.I64()
			r.I64()
		}
		if name == "" {
			t.Errorf("event %d has an empty name", i)
		}
	}
	if r.Remaining() != 0 {
		t.Errorf("calendar has %d trailing bytes; the client reads exactly the declared entries", r.Remaining())
	}
}

// TestTournamentListWireIsExact re-decodes 28602 field-by-field and asserts an exact
// consume plus the values that make a row registerable: not-registered status, an
// open registration flag, a real definition id and no special fight params.
func TestTournamentListWireIsExact(t *testing.T) {
	frame, err := buildTournamentList(999, nil) // no manager -> everyone not registered
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if got := binary.BigEndian.Uint16(frame[2:4]); got != protocol.OpTournamentList {
		t.Fatalf("opcode = %d, want %d", got, protocol.OpTournamentList)
	}
	r := protocol.NewReader(frame[4:])
	count, err := r.I32()
	if err != nil {
		t.Fatalf("count: %v", err)
	}
	if int(count) != len(standingTournamentTable) {
		t.Fatalf("row count = %d, want %d", count, len(standingTournamentTable))
	}
	for i := 0; i < int(count); i++ {
		want := standingTournamentTable[i]
		tid, _ := r.I64()
		search, _ := r.U8()
		status, _ := r.U8()
		defID, _ := r.U16()
		regOpen, _ := r.U8()
		fp, _ := r.I32()
		name, _ := r.StringU32()
		r.StringU32() // desc
		r.StringU32() // organizer
		kind, _ := r.U8()

		if tid != want.id {
			t.Errorf("row %d id = %d, want %d", i, tid, want.id)
		}
		if search != 0 {
			t.Errorf("row %d openedSearch = %d, want 0", i, search)
		}
		if int8(status) != tournamentCoachNotRegistered {
			t.Errorf("row %d status = %d, want %d (not registered)", i, int8(status), tournamentCoachNotRegistered)
		}
		if defID != want.defID {
			t.Errorf("row %d defID = %d, want %d", i, defID, want.defID)
		}
		if regOpen != 1 {
			t.Errorf("row %d registrationOpen = %d, want 1", i, regOpen)
		}
		if fp != 0 {
			t.Errorf("row %d fightParamCount = %d, want 0", i, fp)
		}
		if name != want.name {
			t.Errorf("row %d name = %q, want %q", i, name, want.name)
		}
		if kind != want.kind {
			t.Errorf("row %d kind = %d, want %d", i, kind, want.kind)
		}
	}
	if r.Remaining() != 0 {
		t.Errorf("list has %d trailing bytes", r.Remaining())
	}
}

// TestTournamentManagerRegister covers the in-memory registration tracker:
// Register is idempotent and IsRegistered reflects it.
func TestTournamentManagerRegister(t *testing.T) {
	tm := NewTournamentManager()
	const coach = uint(42)
	tid := standingTournamentTable[0].id

	if tm.IsRegistered(coach, tid) {
		t.Error("fresh manager reports coach registered")
	}
	if !tm.Register(coach, tid) {
		t.Error("first Register should return true")
	}
	if tm.Register(coach, tid) {
		t.Error("second Register should return false (idempotent)")
	}
	if !tm.IsRegistered(coach, tid) {
		t.Error("IsRegistered false after Register")
	}
	if tm.IsRegistered(coach+1, tid) {
		t.Error("registration leaked to another coach")
	}
}

// TestTournamentListReflectsRegistration proves that after a coach registers, the
// re-built list marks exactly that tournament as registered (status "first round")
// and leaves the others not-registered — so a re-opened window is consistent.
func TestTournamentListReflectsRegistration(t *testing.T) {
	tm := NewTournamentManager()
	const coach = uint(7)
	target := standingTournamentTable[1].id
	tm.Register(coach, target)

	frame, err := buildTournamentList(coach, tm)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	r := protocol.NewReader(frame[4:])
	count, _ := r.I32()
	for i := 0; i < int(count); i++ {
		tid, _ := r.I64()
		r.U8() // search
		status, _ := r.U8()
		r.U16() // defID
		r.U8()  // regOpen
		r.I32() // fightParamCount
		r.StringU32()
		r.StringU32()
		r.StringU32()
		r.U8() // kind
		want := tournamentCoachNotRegistered
		if tid == target {
			want = tournamentCoachFirstRound
		}
		if int8(status) != want {
			t.Errorf("tid %d status = %d, want %d", tid, int8(status), want)
		}
	}
	if r.Remaining() != 0 {
		t.Errorf("list has %d trailing bytes", r.Remaining())
	}
}
