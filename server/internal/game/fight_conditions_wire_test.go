package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// The CREATE_FIGHT fighter blob ends with two id lists the client reads as
// [i16 count][i32 x count] and [i16 count][i16 x count] (gn_0.b). The second is
// the persistent fighter conditions (type 902), which the client keys into
// gn_0.uk and draws on the portrait — so a reconnecting player or a spectator,
// both of whom rebuild the fight from CREATE_FIGHT, see wounds again.
//
// The FIRST list is deliberately left empty: the client resolves those ids
// through akp_1, the SPHERE BOARD registry, and re-applies the matching node's
// effects. It is not a buff-icon channel.

// blobConditions parses the two trailing id lists plus the three deltas from a
// fighter blob, returning the condition ids.
func blobConditions(t *testing.T, blob []byte) []int16 {
	t.Helper()
	// Walk from the end: 3 x i32 deltas (12 bytes) precede nothing else, so the
	// condition list ends 12 bytes from the tail. Scanning forward from the
	// front would mean re-implementing the whole blob layout here.
	if len(blob) < 12 {
		t.Fatalf("blob too short: %d bytes", len(blob))
	}
	body := blob[:len(blob)-12]
	if len(body) < 2 {
		t.Fatal("no condition list")
	}
	// The list is [i16 count][i16 x count] at the very end of body. Locate it by
	// finding the count that exactly consumes the tail, rather than
	// re-implementing the whole blob layout to walk forward to it.
	for count := 0; count <= 8; count++ {
		need := 2 + 2*count
		if len(body) < need {
			break
		}
		hdr := body[len(body)-need:]
		got := int(hdr[0])<<8 | int(hdr[1])
		if got != count {
			continue
		}
		out := make([]int16, count)
		for i := 0; i < count; i++ {
			out[i] = int16(uint16(hdr[2+2*i])<<8 | uint16(hdr[3+2*i]))
		}
		return out
	}
	t.Fatal("could not locate the condition list at the end of the blob")
	return nil
}

func TestFighterBlobCarriesConditions(t *testing.T) {
	ff := &FightFighter{
		WireID: 1, MaxHP: 70, HP: 70, MaxAP: 6, AP: 6, MaxMP: 3, MP: 3,
		Fighter: &domain.Fighter{
			Name: "Wounded", BreedID: 8,
			Conditions: []domain.FighterCondition{
				{ConditionID: 11, Remaining: 3},
				{ConditionID: 42, Remaining: -1},
			},
		},
	}
	w := protocol.NewWriter()
	writeCombatFighterBlob(w, ff)

	got := blobConditions(t, w.Bytes())
	if len(got) != 2 || got[0] != 11 || got[1] != 42 {
		t.Errorf("condition ids on the wire = %v, want [11 42]", got)
	}
}

func TestFighterBlobWithNoConditionsIsStillWellFormed(t *testing.T) {
	ff := &FightFighter{
		WireID: 1, MaxHP: 70, HP: 70, MaxAP: 6, AP: 6, MaxMP: 3, MP: 3,
		Fighter: &domain.Fighter{Name: "Healthy", BreedID: 8},
	}
	w := protocol.NewWriter()
	writeCombatFighterBlob(w, ff)
	if got := blobConditions(t, w.Bytes()); len(got) != 0 {
		t.Errorf("healthy fighter sent %v conditions, want none", got)
	}
}
