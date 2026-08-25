package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// The bracket's slot ranges are hard-coded in the client (`ah_1.getFieldValue`),
// so they are the one thing a test here can meaningfully pin:
//
//	1 winner | 2-3 finale | 4-7 semi | 8-15 quarter | 16-31 first round
//
// Put an entrant anywhere else and it renders in the wrong round, or nowhere.

func decodeTree(t *testing.T, frame []byte) (page int32, slots map[int32]string, trailing int32) {
	t.Helper()
	r := protocol.NewReader(frame[4:]) // skip [u16 len][u16 opcode]
	p, err := r.I32()
	if err != nil {
		t.Fatalf("page: %v", err)
	}
	n, err := r.I32()
	if err != nil {
		t.Fatalf("count: %v", err)
	}
	slots = make(map[int32]string, n)
	for i := int32(0); i < n; i++ {
		slot, err := r.I32()
		if err != nil {
			t.Fatalf("slot %d: %v", i, err)
		}
		l, err := r.I32()
		if err != nil {
			t.Fatalf("len %d: %v", i, err)
		}
		raw, err := r.Bytes(int(l))
		if err != nil {
			t.Fatalf("name %d: %v", i, err)
		}
		slots[slot] = string(raw)
	}
	tr, err := r.I32()
	if err != nil {
		t.Fatalf("trailing i32 missing - IL.a reads it unconditionally: %v", err)
	}
	if r.Remaining() != 0 {
		t.Errorf("%d bytes left over", r.Remaining())
	}
	return p, slots, tr
}

func TestBracketSeedsTheFirstRound(t *testing.T) {
	names := []string{"Alpha", "Bravo", "Charlie"}
	frame, err := encodeTournamentTree(0, buildBracket(names))
	if err != nil {
		t.Fatalf("encode: %v", err)
	}
	_, slots, _ := decodeTree(t, frame)

	for i, want := range names {
		slot := int32(bracketFirstRoundSlot + i)
		if got := slots[slot]; got != want {
			t.Errorf("slot %d = %q, want %q (first round is 16..31)", slot, got, want)
		}
	}
	// Nothing above the first round: no match has been decided, and inventing a
	// winner would render results that do not exist.
	for slot := int32(bracketWinnerSlot); slot < bracketFirstRoundSlot; slot++ {
		if name, ok := slots[slot]; ok {
			t.Errorf("slot %d holds %q - upper rounds must stay empty until matches "+
				"are played", slot, name)
		}
	}
}

// TestBracketClampsToWhatTheClientCanDraw: the first round has exactly 16 slots.
// A 17th entrant has nowhere to go, and writing past slot 31 would land in no
// round at all (the client only reads 16..31).
func TestBracketClampsToWhatTheClientCanDraw(t *testing.T) {
	names := make([]string, 20)
	for i := range names {
		names[i] = string(rune('A' + i))
	}
	b := buildBracket(names)
	if len(b) != bracketEntrants {
		t.Errorf("bracket holds %d entrants, want %d", len(b), bracketEntrants)
	}
	for slot := range b {
		if slot < bracketFirstRoundSlot || slot >= bracketSlots {
			t.Errorf("entrant at slot %d, outside the first round 16..31", slot)
		}
	}
}

// TestTournamentTreeEchoesThePage: the client stores the returned page
// (`ah_1.eE`) and sends it back +/-1 when the player uses the tree's paging
// buttons (20069). Returning a different one makes those buttons jump.
func TestTournamentTreeEchoesThePage(t *testing.T) {
	for _, page := range []int32{0, 1, 7} {
		frame, err := encodeTournamentTree(page, buildBracket([]string{"X"}))
		if err != nil {
			t.Fatalf("encode: %v", err)
		}
		got, _, _ := decodeTree(t, frame)
		if got != page {
			t.Errorf("page echoed as %d, want %d", got, page)
		}
	}
}

// TestTournamentTreeNamesAreUTF8: this message decodes names with
// `new String(bytes, "UTF-8")`, unlike the windows-1252 the rest of the protocol
// uses (B-068). An accented name must survive as UTF-8 bytes.
func TestTournamentTreeNamesAreUTF8(t *testing.T) {
	const name = "Krîkétôr"
	frame, err := encodeTournamentTree(0, buildBracket([]string{name}))
	if err != nil {
		t.Fatalf("encode: %v", err)
	}
	_, slots, _ := decodeTree(t, frame)
	if got := slots[bracketFirstRoundSlot]; got != name {
		t.Errorf("name = %q, want %q - the bracket is UTF-8, not cp1252", got, name)
	}
}

// TestEmptyTournamentTreeStillWellFormed: with no entrants the client shows
// "tournamentTreeNotAvailable" and closes cleanly - but only if the trailing i32
// is present, because IL.a reads it unconditionally.
func TestEmptyTournamentTreeStillWellFormed(t *testing.T) {
	frame, err := encodeTournamentTree(0, buildBracket(nil))
	if err != nil {
		t.Fatalf("encode: %v", err)
	}
	page, slots, trailing := decodeTree(t, frame)
	if page != 0 || len(slots) != 0 || trailing != 0 {
		t.Errorf("empty tree = page %d, %d slots, trailing %d", page, len(slots), trailing)
	}
}

// TestFirstRoundRange pins the subtree helper directly.
//
// Behaviour tests cannot catch an off-by-one here: seeding fills the first round
// contiguously from slot 16, so a subtree contains an entrant exactly when its
// FIRST leaf does, and a range that drops its last leaf still gives the right
// answer everywhere. That masking disappears the moment seeding gains a gap, so
// the contract is asserted on its own.
func TestFirstRoundRange(t *testing.T) {
	cases := []struct{ slot, lo, hi int32 }{
		{1, 16, 31}, // the root covers the whole draw
		{2, 16, 23}, // the top half
		{3, 24, 31}, // the bottom half
		{4, 16, 19},
		{5, 20, 23},
		{9, 18, 19},
		{16, 16, 16}, // a first-round slot covers only itself
		{31, 31, 31},
	}
	for _, c := range cases {
		lo, hi := firstRoundRange(c.slot)
		if lo != c.lo || hi != c.hi {
			t.Errorf("firstRoundRange(%d) = (%d,%d), want (%d,%d)",
				c.slot, lo, hi, c.lo, c.hi)
		}
	}
	// Every slot must cover a power-of-two span and stay inside the first round.
	for slot := int32(1); slot < bracketSlots; slot++ {
		lo, hi := firstRoundRange(slot)
		if lo < bracketFirstRoundSlot || hi >= bracketSlots || lo > hi {
			t.Fatalf("firstRoundRange(%d) = (%d,%d) is outside the first round",
				slot, lo, hi)
		}
		if n := hi - lo + 1; n&(n-1) != 0 {
			t.Errorf("firstRoundRange(%d) spans %d leaves, not a power of two",
				slot, n)
		}
	}
}
