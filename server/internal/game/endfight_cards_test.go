package game

import (
	"testing"

	"github.com/StarLoco/arena-2.70/internal/protocol"
)

// TestEndFightWonCardsBlob pins the card-blob framing and, above all, its ORDER.
//
// The client reads two blobs and the FIRST is the cards WON:
//
//	YP.c(blob, bl2) files into `bl2 ? by : bz`; the first blob is read with
//	bl2 = false (-> bz), and ajo_1 publishes bz as "fight.wonCards" and by as
//	"fight.lostCards".
//
// Our source used to comment these the other way round, so a player would have
// been shown their winnings in the "Cartes perdues" column.
func TestEndFightWonCardsBlob(t *testing.T) {
	won := []int32{0x11223344, 0x55667788}
	frame, err := buildEndFightFull(1, nil, nil, nil, 0, 0, 0, won)
	if err != nil {
		t.Fatal(err)
	}
	// Walk to the card blobs: skip the S2C header, then the fields before them.
	r := protocol.NewReader(frame[4:]) // [u16 len][u16 opcode]
	skipEndFightPrefix(t, r)

	firstLen, err := r.U16()
	if err != nil {
		t.Fatalf("first blob length: %v", err)
	}
	if firstLen == 0 {
		t.Fatal("the FIRST blob is empty — won cards must go first, not second")
	}
	blob, err := r.Bytes(int(firstLen))
	if err != nil {
		t.Fatal(err)
	}
	br := protocol.NewReader(blob)
	groups, _ := br.U8()
	if groups != 1 {
		t.Errorf("group count = %d, want 1", groups)
	}
	n, _ := br.U8()
	if int(n) != len(won) {
		t.Fatalf("card count = %d, want %d", n, len(won))
	}
	for i, want := range won {
		got, err := br.I32()
		if err != nil || got != want {
			t.Errorf("card %d = %#x (%v), want %#x", i, got, err, want)
		}
	}

	// The SECOND blob (lost cards) must be empty — staking is not implemented,
	// and a zero LENGTH is what makes the client skip it entirely.
	secondLen, err := r.U16()
	if err != nil {
		t.Fatalf("second blob length: %v", err)
	}
	if secondLen != 0 {
		t.Errorf("lost-cards blob = %d bytes, want 0", secondLen)
	}
}

// TestEndFightNoCardsWritesZeroLengths guards the empty case: an empty list must
// write a zero length, NOT a blob containing an empty group.
func TestEndFightNoCardsWritesZeroLengths(t *testing.T) {
	frame, err := buildEndFight(1, nil, nil)
	if err != nil {
		t.Fatal(err)
	}
	r := protocol.NewReader(frame[4:])
	skipEndFightPrefix(t, r)
	for i, name := range []string{"won", "lost"} {
		n, err := r.U16()
		if err != nil {
			t.Fatalf("%s blob length: %v", name, err)
		}
		if n != 0 {
			t.Errorf("blob %d (%s) = %d bytes, want 0", i, name, n)
		}
	}
}

// skipEndFightPrefix advances past everything in END_FIGHT before the card
// blobs: the action header, the flee flag, the two strength maps and the two
// per-coach result lists.
func skipEndFightPrefix(t *testing.T, r *protocol.Reader) {
	t.Helper()
	if _, err := r.I32(); err != nil { // action uid
		t.Fatal(err)
	}
	if _, err := r.I32(); err != nil { // writeActionHeader's trailing -1
		t.Fatal(err)
	}
	if _, err := r.U8(); err != nil { // flee
		t.Fatal(err)
	}
	for i := 0; i < 2; i++ { // winner/loser strength maps
		n, err := r.I32()
		if err != nil {
			t.Fatal(err)
		}
		for j := int32(0); j < n; j++ {
			_, _ = r.I64()
			_, _ = r.I32()
		}
	}
	for i := 0; i < 2; i++ { // winner/loser result lists
		n, err := r.U8()
		if err != nil {
			t.Fatal(err)
		}
		for j := uint8(0); j < n; j++ {
			_, _ = r.I64()
			_, _ = r.U16()
			reportLen, _ := r.U16()
			if reportLen > 0 {
				_, _ = r.Bytes(int(reportLen))
			}
		}
	}
}
