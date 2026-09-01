package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestPlayerStatisticsWireShape pins the layout recovered from the client:
//
//	[i16 blobLen][i16 modelId][i64 ownerId][i16 count]
//	  count x { [i16 statId][i8 type][value] }
//
// The stat ids are exact, from the UNOBFUSCATED PlayerStatisticsReport getters
// (dJ() reads V((short)4), so "fights won" IS 4). The modelId is verified
// against a live client: `arq_0.aa` refuses an unknown model by name, and 0, 2
// and 3 were all refused while 1 was accepted silently.
func TestPlayerStatisticsWireShape(t *testing.T) {
	c := &domain.Coach{
		ID: 7, StatFights: 12, StatWins: 7, StatLosses: 5,
		ConsecutiveWins: 3, ConsecutiveLosses: 0,
	}
	frame, err := buildPlayerStatistics(c)
	if err != nil {
		t.Fatalf("buildPlayerStatistics: %v", err)
	}
	r := protocol.NewReader(frame[4:])

	blobLen, err := r.U16()
	if err != nil {
		t.Fatalf("blobLen: %v", err)
	}
	// Compared against the LITERAL 1, not against statsReportModelID: asserting a
	// constant equals itself cannot fail, and this is the one field where being
	// wrong costs the entire report rather than one number.
	//
	// 1 is not a guess. Injected into a live retail client, modelId 0, 2 and 3 were
	// each refused by name ("le modele n'est pas reconnu : modelId=N") while 1 was
	// accepted silently. If the client content ever changes, that experiment is the
	// way to re-derive it - not this test.
	modelID, _ := r.U16()
	if modelID != 1 {
		t.Errorf("modelId = %d, want 1 (verified against a live client) - the client "+
			"REFUSES an unrecognised model and drops the whole report", modelID)
	}
	owner, _ := r.I64()
	if owner != 7 {
		t.Errorf("ownerId = %d, want the coach id 7", owner)
	}
	count, _ := r.U16()

	got := map[int16]int32{}
	for i := 0; i < int(count); i++ {
		id, err := r.U16()
		if err != nil {
			t.Fatalf("entry %d id: %v", i, err)
		}
		typ, _ := r.U8()
		if typ != statTypeInt32 {
			t.Fatalf("entry %d type = %d; this test only writes i32 entries", i, typ)
		}
		v, _ := r.I32()
		got[int16(id)] = v
	}

	// blobLen must describe the bytes that follow it, or the client reads the
	// wrong slice and the model parses garbage.
	if int(blobLen) != len(frame[4:])-2 {
		t.Errorf("blobLen = %d but %d bytes follow it", blobLen, len(frame[4:])-2)
	}

	for _, tc := range []struct {
		id   int16
		want int32
		name string
	}{
		{statTotalFights, 12, "total fights"},
		{statTotalFightsWon, 7, "fights won"},
		{statTotalFightsLost, 5, "fights lost"},
		{statConsecutiveWins, 3, "consecutive wins"},
		{statConsecutiveLoses, 0, "consecutive losses"},
	} {
		v, ok := got[tc.id]
		if !ok {
			t.Errorf("%s (id %d) missing from the report", tc.name, tc.id)
			continue
		}
		if v != tc.want {
			t.Errorf("%s (id %d) = %d, want %d", tc.name, tc.id, v, tc.want)
		}
	}
}

// TestPlayerStatisticsOmitsUntrackedStats: the map is deliberately SPARSE. Play
// time and total fight time are not measured by this server, and sending 0 for
// them is indistinguishable from "you have played zero seconds" - so they are
// left out and the client reads its own default.
func TestPlayerStatisticsOmitsUntrackedStats(t *testing.T) {
	frame, err := buildPlayerStatistics(&domain.Coach{ID: 1})
	if err != nil {
		t.Fatalf("buildPlayerStatistics: %v", err)
	}
	r := protocol.NewReader(frame[4:])
	_, _ = r.U16() // blobLen
	_, _ = r.U16() // modelId
	_, _ = r.I64() // owner
	count, _ := r.U16()
	for i := 0; i < int(count); i++ {
		id, _ := r.U16()
		_, _ = r.U8()
		_, _ = r.I32()
		switch int16(id) {
		case statTotalPlayTime, statTotalFightsTime, statUnknown6:
			t.Errorf("stat id %d is in the report, but this server does not track it - "+
				"sending 0 would read as a real zero on the client", id)
		}
	}
}
