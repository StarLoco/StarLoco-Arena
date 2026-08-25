package game

import (
	"encoding/binary"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// decodeFinale mirrors the CLIENT's Yq.a: both arrays are read BACKWARDS, so the
// first element on the wire lands at the highest index. Decoding the way the
// client does is the point - a decoder that read forwards would agree with a
// wrong encoder and prove nothing.
func decodeFinale(t *testing.T, frame []byte) (status uint8, tid int64, ids []int64, names []string, tournament string) {
	t.Helper()
	if op := binary.BigEndian.Uint16(frame[2:4]); op != protocol.OpTournamentFinale {
		t.Fatalf("opcode = %d, want %d", op, protocol.OpTournamentFinale)
	}
	r := protocol.NewReader(frame[4:]) // skip [u16 len][u16 opcode]
	var err error
	status, err = r.U8()
	if err != nil {
		t.Fatalf("status: %v", err)
	}
	tid, err = r.I64()
	if err != nil {
		t.Fatalf("tid: %v", err)
	}
	if status == finaleStatusRemove {
		return status, tid, nil, nil, ""
	}
	n, err := r.I32()
	if err != nil {
		t.Fatalf("id count: %v", err)
	}
	ids = make([]int64, n)
	for i := int(n) - 1; i >= 0; i-- {
		v, err := r.I64()
		if err != nil {
			t.Fatalf("id: %v", err)
		}
		ids[i] = v
	}
	n, err = r.I32()
	if err != nil {
		t.Fatalf("name count: %v", err)
	}
	names = make([]string, n)
	for i := int(n) - 1; i >= 0; i-- {
		s, err := r.StringU32()
		if err != nil {
			t.Fatalf("name: %v", err)
		}
		names[i] = s
	}
	tournament, err = r.StringU32()
	if err != nil {
		t.Fatalf("tournament name: %v", err)
	}
	return status, tid, ids, names, tournament
}

// TestFinaleAnnouncementOrdersFinalistsForTheClient is the whole reason this
// encoder is not a one-liner.
//
// `zN` renders `names[0] VS names[1]`, and `Yq.a` fills both arrays back to
// front. Writing them in natural order produces a completely valid frame that
// announces the finalists the wrong way round - a bug no length or type check
// would ever catch.
func TestFinaleAnnouncementOrdersFinalistsForTheClient(t *testing.T) {
	frame, err := buildTournamentFinaleAdd(2600002,
		[]int64{11, 22}, []string{"Chrono", "ExBot"}, "Tournoi des Champions")
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	status, tid, ids, names, tournament := decodeFinale(t, frame)

	if status != finaleStatusAdd {
		t.Errorf("status = %d, want %d (add)", status, finaleStatusAdd)
	}
	if tid != 2600002 {
		t.Errorf("tid = %d, want 2600002", tid)
	}
	if len(names) != 2 || names[0] != "Chrono" || names[1] != "ExBot" {
		t.Errorf("names = %v, want [Chrono ExBot] as the CLIENT reads them - the "+
			"finalists are announced in the wrong order", names)
	}
	if len(ids) != 2 || ids[0] != 11 || ids[1] != 22 {
		t.Errorf("ids = %v, want [11 22] as the client reads them", ids)
	}
	if tournament != "Tournoi des Champions" {
		t.Errorf("tournament = %q", tournament)
	}
}

// TestFinaleRemoveCarriesOnlyTheID: the REMOVE form stops after the id, because
// that is all `Yq.a` reads for status 2. Writing the arrays anyway would leave
// trailing bytes in the frame.
func TestFinaleRemoveCarriesOnlyTheID(t *testing.T) {
	frame, err := buildTournamentFinaleRemove(2600002)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if n := len(frame) - 4; n != 9 {
		t.Errorf("remove payload = %d bytes, want 9 ([i8 status][i64 id])", n)
	}
	status, tid, _, _, _ := decodeFinale(t, frame)
	if status != finaleStatusRemove {
		t.Errorf("status = %d, want %d (remove)", status, finaleStatusRemove)
	}
	if tid != 2600002 {
		t.Errorf("tid = %d, want 2600002", tid)
	}
}

// TestOnlyTheLastFixtureIsAFinal: the announcement must not fire on every match.
// With four entrants the semi-finals are between slots 16/17 and 18/19, whose
// parents are 8 and 9 - not the root.
func TestOnlyTheLastFixtureIsAFinal(t *testing.T) {
	d := &Deps{Tournaments: tmWithEntrants(7, 1, 2, 3, 4), Log: testLogger()}

	if d.isFinalFixture(7, 1, 2) {
		t.Error("a first-round fixture (slots 16/17 -> 8) was treated as the final")
	}
	// Play both semi-finals: 1 and 3 reach slots 8 and 9, whose parent IS the root
	// once the byes above are taken into account.
	d.Tournaments.RecordMatchResult(7, 1, 2)
	d.Tournaments.RecordMatchResult(7, 3, 4)
	if !d.isFinalFixture(7, 1, 3) {
		t.Error("the last fixture of the draw was not recognised as the final")
	}
	// Two coaches who are not siblings are not a fixture at all.
	if d.isFinalFixture(7, 1, 1) {
		t.Error("a coach was treated as its own final opponent")
	}
}
