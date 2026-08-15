package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// CREATE_FIGHT's header is five fields that all look interchangeable at a
// glance, and the kind was written into the wrong one for a long time (B-095).
// aat_2.ac reads them in this order:
//
//	[i32] -> mv_1.cAq   read back as aKl()   <- the FIGHT KIND
//	[i64] -> adu_0.cmF  read back as asy()   <- the CHALLENGE ID
//	[i8]  -> mv_1.byp   read back as ZC()    <- no reader anywhere
//	[i64] -> mv_1.byv                        <- turn-display budget, ms
//	[i32] -> axw.aW                          <- fight instance id
//
// These tests decode the header exactly as the client does and assert each
// value lands in the slot the client reads it from.

// createFightHeader pulls the five header fields out of a built 8000 frame.
type createFightHeader struct {
	kind        int32
	challengeID int64
	unreadByte  uint8
	turnClockMS int64
	instanceID  int32
}

func decodeCreateFightHeader(t *testing.T, frame []byte) createFightHeader {
	t.Helper()
	// Skip the 4-byte S2C envelope, then the error byte and the coach-deck blob.
	r := protocol.NewReader(frame[4:])
	if _, err := r.U8(); err != nil { // error byte
		t.Fatalf("error byte: %v", err)
	}
	blobLen, err := r.U16()
	if err != nil {
		t.Fatalf("deck blob length: %v", err)
	}
	if _, err := r.String(int(blobLen)); err != nil {
		t.Fatalf("deck blob: %v", err)
	}

	var h createFightHeader
	if h.kind, err = r.I32(); err != nil {
		t.Fatalf("kind: %v", err)
	}
	if h.challengeID, err = r.I64(); err != nil {
		t.Fatalf("challenge id: %v", err)
	}
	if h.unreadByte, err = r.U8(); err != nil {
		t.Fatalf("unread byte: %v", err)
	}
	if h.turnClockMS, err = r.I64(); err != nil {
		t.Fatalf("turn clock: %v", err)
	}
	if h.instanceID, err = r.I32(); err != nil {
		t.Fatalf("instance id: %v", err)
	}
	return h
}

func TestCreateFightKindLandsInTheSlotTheClientReads(t *testing.T) {
	for _, tc := range []struct {
		name        string
		setup       func(*Fight)
		wantKind    int32
		wantChallID int64
	}{
		{
			name:     "ordinary fight",
			setup:    func(f *Fight) {},
			wantKind: fightKindNormal,
		},
		{
			name:        "challenge fight",
			setup:       func(f *Fight) { f.ChallengeID = 35 },
			wantKind:    fightKindChallenge,
			wantChallID: 35,
		},
		{
			name:     "evolution fight",
			setup:    func(f *Fight) { f.Evolution = true },
			wantKind: fightKindEvolution,
		},
		{
			// Evolution wins, and the challenge id is suppressed with it so the
			// client cannot try to open both result panels.
			name:     "evolution beats challenge",
			setup:    func(f *Fight) { f.Evolution = true; f.ChallengeID = 35 },
			wantKind: fightKindEvolution,
		},
	} {
		t.Run(tc.name, func(t *testing.T) {
			f := buildTestFight()
			tc.setup(f)
			frame, err := buildCreateFight(f, nil, false)
			if err != nil {
				t.Fatalf("build: %v", err)
			}
			h := decodeCreateFightHeader(t, frame)

			if h.kind != tc.wantKind {
				t.Errorf("aKl() slot = %d, want %d — the client decides which result "+
					"dialog to open from this field", h.kind, tc.wantKind)
			}
			if h.challengeID != tc.wantChallID {
				t.Errorf("asy() slot = %d, want %d — ahy_1.axg().dC() resolves the "+
					"challenge with this", h.challengeID, tc.wantChallID)
			}
		})
	}
}

// The i8 is genuinely unread by the client (ZC() has no call site). Writing a
// meaningful value there is what caused B-095, so it is pinned at zero to stop
// anyone "helpfully" putting the kind back into it.
func TestCreateFightLeavesTheUnreadByteZero(t *testing.T) {
	f := buildTestFight()
	f.ChallengeID = 35
	frame, err := buildCreateFight(f, nil, false)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	if got := decodeCreateFightHeader(t, frame).unreadByte; got != 0 {
		t.Errorf("the unread i8 = %d, want 0; the fight kind belongs in the i32", got)
	}
}

// The client floors the turn budget at 31s (Math.max(31000, byv)), so the exact
// value rarely shows — but sending zero would misreport a ruleset that
// lengthens turns, and sending the challenge id there (the old behaviour) was
// simply wrong.
func TestCreateFightSendsTheTurnClock(t *testing.T) {
	f := buildTestFight()
	frame, err := buildCreateFight(f, nil, false)
	if err != nil {
		t.Fatalf("build: %v", err)
	}
	h := decodeCreateFightHeader(t, frame)

	want := f.turnClockFor().Milliseconds()
	if h.turnClockMS != want {
		t.Errorf("turn clock slot = %d ms, want %d", h.turnClockMS, want)
	}
	if h.turnClockMS <= 100 {
		t.Errorf("turn clock slot = %d ms, which is an id-sized value, not a duration",
			h.turnClockMS)
	}
}
