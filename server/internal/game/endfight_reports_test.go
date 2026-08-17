package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// The per-fighter post-fight reports in END_FIGHT are keyed by an id the client
// resolves against its OWN roster:
//
//	adY.atu().dz(id).a(report)      // y_0.run — no nil check
//
// The roster is filled from the fighter list, which sends the raw database
// fighter id. Sending a fight WIRE id there — or another coach's fighter —
// resolves to nothing, throws, and aborts the end-of-fight action before it
// opens the result dialog. That is B-096: no result screen appeared for any
// fight, of any kind.

// endFightReportIDs decodes the report block out of an 8300 frame and returns
// the ids it is keyed by.
func endFightReportIDs(t *testing.T, frame []byte) []int64 {
	t.Helper()
	r := protocol.NewReader(frame[4:])

	skip := func(what string, err error) {
		t.Helper()
		if err != nil {
			t.Fatalf("%s: %v", what, err)
		}
	}
	_, err := r.I32()
	skip("action uid", err)
	_, err = r.I32()
	skip("action header second word", err)
	_, err = r.U8()
	skip("flee flag", err)

	// Two [i32 n]{[i64][i32]} strength maps.
	for _, what := range []string{"winner strengths", "loser strengths"} {
		n, err := r.I32()
		skip(what, err)
		for i := int32(0); i < n; i++ {
			_, err = r.I64()
			skip(what, err)
			_, err = r.I32()
			skip(what, err)
		}
	}
	// Two [u8 n]{[i64][i16][i16 len][bytes]} result lists.
	for _, what := range []string{"winners", "losers"} {
		n, err := r.U8()
		skip(what, err)
		for i := uint8(0); i < n; i++ {
			_, err = r.I64()
			skip(what, err)
			_, err = r.U16()
			skip(what, err)
			blobLen, err := r.U16()
			skip(what, err)
			_, err = r.String(int(blobLen))
			skip(what, err)
		}
	}
	// Two card blobs.
	for _, what := range []string{"won cards", "lost cards"} {
		n, err := r.U16()
		skip(what, err)
		_, err = r.String(int(n))
		skip(what, err)
	}

	// The report block.
	n, err := r.U8()
	skip("report count", err)
	ids := make([]int64, 0, n)
	for i := uint8(0); i < n; i++ {
		id, err := r.I64()
		skip("report id", err)
		blobLen, err := r.U16()
		skip("report blob length", err)
		_, err = r.String(int(blobLen))
		skip("report blob", err)
		ids = append(ids, id)
	}
	return ids
}

func TestEndFightReportsUseRosterIDsNotWireIDs(t *testing.T) {
	const rosterID int64 = 3
	reports := []endFightReport{{FighterID: rosterID, CoachID: 1, Blob: make([]byte, 40)}}

	frame, err := buildEndFightFull(1, nil, nil, reports, 0, 0, 0, nil)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	ids := endFightReportIDs(t, frame)

	if len(ids) != 1 {
		t.Fatalf("got %d reports, want 1", len(ids))
	}
	if ids[0] != rosterID {
		t.Errorf("report id = %d, want %d", ids[0], rosterID)
	}
	// The specific failure mode: anything in the fight wire-id space cannot be
	// found in the client's roster.
	if ids[0] >= FighterWireIDBase {
		t.Errorf("report id %d is a fight wire id; the client looks these up in its "+
			"roster with adY.atu().dz() and dereferences the result without a nil "+
			"check, so this aborts the whole result dialog", ids[0])
	}
}

// A coach must only be sent reports for fighters in its own roster: the client
// cannot resolve somebody else's, and the failure is the same.
func TestEndFightReportsAreScopedToTheirOwner(t *testing.T) {
	reports := []endFightReport{
		{FighterID: 1, CoachID: 10, Blob: make([]byte, 40)},
		{FighterID: 2, CoachID: 10, Blob: make([]byte, 40)},
		{FighterID: 9, CoachID: 20, Blob: make([]byte, 40)},
	}

	mine := reportsFor(reports, 10)
	if len(mine) != 2 {
		t.Fatalf("coach 10 got %d reports, want 2", len(mine))
	}
	for _, rep := range mine {
		if rep.CoachID != 10 {
			t.Errorf("coach 10 was sent a report for coach %d's fighter %d",
				rep.CoachID, rep.FighterID)
		}
	}

	// Spectators have no roster to resolve against and must get none.
	if got := reportsFor(reports, 0); got != nil {
		t.Errorf("a spectator was sent %d reports; it has no roster to resolve them", len(got))
	}
	if got := reportsFor(nil, 10); got != nil {
		t.Errorf("no reports in, %d out", len(got))
	}
}
