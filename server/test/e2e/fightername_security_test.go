package e2e

import (
	"testing"
	"time"

	"github.com/StarLoco/arena-2.70/internal/testclient"
)

const (
	opFighterCreate       = 6001
	opFighterCreateResult = 6000
)

// fighterBlob builds an et_2 fighter blob with the given name.
// Layout read from decodeFighterBlob (fighter_codec.go), not guessed:
//
//	[u8 type][u16 budget][u8 breed][Str8 name][u8 sex][u8 ey]
//	(ey<0 -> [u8 hair][u8 skin][u8 eye]) [u16 spellLen]... [u16 cardLen]...
func fighterBlob(name string) []byte {
	return testclient.NewW().
		U8(1).  // type 1 = classic
		U16(0). // budget (recomputed server-side)
		U8(1).  // breed
		Str8(name).
		U8(0).   // sex
		U8(255). // ey = -1 -> colours follow
		U8(1).U8(2).U8(1).
		U16(0). // no spells
		U16(0). // no cards
		Bytes()
}

// TestEmptyFighterNameIsRefusedOverTheWire drives opcode 6001 directly, the way a
// modified client would. The retail client refuses this in its own form
// (error.fighterCreation.invalidName) and never sends it.
//
// Testing validateFighterName alone would not prove the handler consults it -
// that exact gap let an unguarded coach-name handler pass its unit tests.
func TestEmptyFighterNameIsRefusedOverTheWire(t *testing.T) {
	t.Parallel()
	st, addr := testServerWithStore(t)
	c, coachID := dialLogin(t, addr, "emptyfighter", "EmptyF")
	c.DrainReceived(200 * time.Millisecond)

	before, err := st.Fighters.ListByCoach(uint(coachID))
	if err != nil {
		t.Fatalf("list fighters: %v", err)
	}

	for _, name := range []string{"", "   ", "<b></b>", "\u00ad"} {
		blob := fighterBlob(name)
		payload := testclient.NewW().
			U8(0).
			U16(0).
			U16(uint16(len(blob))).
			Raw(blob).
			Bytes()
		_ = c.Send(2, opFighterCreate, payload)
		c.DrainReceived(250 * time.Millisecond)
	}

	after, err := st.Fighters.ListByCoach(uint(coachID))
	if err != nil {
		t.Fatalf("list fighters: %v", err)
	}
	if len(after) != len(before) {
		var names []string
		for _, f := range after {
			names = append(names, f.Name)
		}
		t.Errorf("fighter count %d -> %d: an unusable name was accepted (names: %v)",
			len(before), len(after), names)
	}

	// A legitimate name must still work, or the guard is too broad.
	blob := fighterBlob("Goodname")
	payload := testclient.NewW().U8(0).U16(0).U16(uint16(len(blob))).Raw(blob).Bytes()
	_ = c.Send(2, opFighterCreate, payload)
	c.DrainReceived(400 * time.Millisecond)

	final, err := st.Fighters.ListByCoach(uint(coachID))
	if err != nil {
		t.Fatalf("list fighters: %v", err)
	}
	if len(final) != len(before)+1 {
		t.Errorf("a legitimate fighter name was refused: count %d -> %d", len(before), len(final))
	}
}
