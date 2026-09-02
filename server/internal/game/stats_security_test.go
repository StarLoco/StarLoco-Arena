package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/handshake"
)

// TestChallengeBookkeepingIsNotClientWritable pins the boundary between the
// client's criterion id space and the server's own.
//
// The server stores "challenge N already cleared" as stat id 2000+N in the SAME
// table opcode 22003 lets the client write. That namespacing was documented as
// making collision impossible - it does not: it only keeps those ids out of
// buildCriteriaBlob's OUTPUT. Writing them was wide open, and clearing the flag
// re-armed a PvE challenge's reward cards for unbounded farming.
func TestChallengeBookkeepingIsNotClientWritable(t *testing.T) {
	// Every challenge bookkeeping id must fall OUTSIDE the writable range, for a
	// generous span of challenge ids.
	for id := int32(0); id < 500; id++ {
		stat := challengeDoneStat(id)
		if stat > 0 && uint16(stat) <= handshake.MaxCriterionID {
			t.Fatalf("challengeDoneStat(%d) = %d, which is inside the "+
				"client-writable range (<= %d) - the client could clear it",
				id, stat, handshake.MaxCriterionID)
		}
	}
}

// TestStatisticUpdateRangeMatchesTheClientEnum documents the accepted window. If
// MaxCriterionID ever rises above statChallengeDoneBase the two spaces overlap
// and the farming bug returns silently, so that is asserted rather than assumed.
func TestStatisticUpdateRangeMatchesTheClientEnum(t *testing.T) {
	if uint16(statChallengeDoneBase) <= handshake.MaxCriterionID {
		t.Errorf("statChallengeDoneBase (%d) is inside the client-writable range "+
			"(<= %d): the server's bookkeeping namespace has been swallowed",
			statChallengeDoneBase, handshake.MaxCriterionID)
	}
}
