package game

import (
	"testing"
)

// Advancing a result up the bracket. The tree is a binary heap, so the winner of
// slots 2i / 2i+1 takes slot i - and only a genuine SIBLING pair may advance.
// ReadyUp now offers a coach only its sibling, so a non-sibling pair should not
// reach here through the queue; it is still refused, because a GM can end a
// fight directly and seating a coach in a slot it never played for is worse than
// declining to advance it.

func tmWithEntrants(tid int64, coaches ...uint) *TournamentManager {
	m := NewTournamentManager()
	for _, c := range coaches {
		m.Register(c, tid)
	}
	return m
}

func TestBracketAdvancesTheWinnerOfASiblingPair(t *testing.T) {
	// Coaches 1..4 seed into slots 16,17,18,19 (sorted by id).
	m := tmWithEntrants(7, 1, 2, 3, 4)
	slots := m.BracketSlots(7)
	if slots[16] != 1 || slots[17] != 2 || slots[18] != 3 || slots[19] != 4 {
		t.Fatalf("seeding = %v, want 16..19 -> coaches 1..4", slots)
	}

	// 1 (slot 16) beats 2 (slot 17): siblings, parent 8.
	if got := m.RecordMatchResult(7, 1, 2); got != 8 {
		t.Fatalf("advanced to slot %d, want 8 (parent of 16/17)", got)
	}
	if got := m.BracketSlots(7)[8]; got != 1 {
		t.Errorf("slot 8 = coach %d, want 1", got)
	}
	// The first round is untouched: the bracket shows the whole history.
	if got := m.BracketSlots(7)[16]; got != 1 {
		t.Errorf("slot 16 = coach %d, want 1 still seated in its first round", got)
	}

	// 3 beats 4 -> parent 9. Then 1 (now at 8) beats 3 (now at 9) -> parent 4,
	// and because the whole 5-subtree was never seeded the byes carry it from
	// there to the root: with four entrants that last win IS the tournament.
	if got := m.RecordMatchResult(7, 3, 4); got != 9 {
		t.Fatalf("advanced to slot %d, want 9", got)
	}
	if got := m.RecordMatchResult(7, 1, 3); got != bracketWinnerSlot {
		t.Fatalf("advanced to slot %d, want %d - the winner must advance from "+
			"the round it actually reached, then take its byes", got,
			bracketWinnerSlot)
	}
	if got := m.BracketSlots(7)[4]; got != 1 {
		t.Errorf("slot 4 = coach %d, want 1 still recorded on the way up", got)
	}
}

func TestBracketRefusesNonSiblings(t *testing.T) {
	m := tmWithEntrants(7, 1, 2, 3, 4)
	// 1 (slot 16) vs 3 (slot 18): different halves, parents 8 and 9.
	if got := m.RecordMatchResult(7, 1, 3); got != 0 {
		t.Errorf("advanced a non-sibling pair to slot %d - coach 1 would occupy a "+
			"slot it never played for", got)
	}
	if len(m.BracketSlots(7)) != 4 {
		t.Errorf("bracket changed: %v", m.BracketSlots(7))
	}
}

func TestBracketRefusesUnknownOrSelfMatch(t *testing.T) {
	m := tmWithEntrants(7, 1, 2)
	if got := m.RecordMatchResult(7, 1, 99); got != 0 {
		t.Errorf("advanced against a coach that is not an entrant (slot %d)", got)
	}
	if got := m.RecordMatchResult(7, 1, 1); got != 0 {
		t.Errorf("advanced a coach against itself (slot %d)", got)
	}
}

// TestBracketIsPerTournament: a result in one tournament must not move another's
// bracket, even with the same coaches.
func TestBracketIsPerTournament(t *testing.T) {
	m := NewTournamentManager()
	for _, c := range []uint{1, 2} {
		m.Register(c, 7)
		m.Register(c, 8)
	}
	m.RecordMatchResult(7, 1, 2)
	if _, ok := m.BracketSlots(8)[8]; ok {
		t.Error("a result in tournament 7 advanced tournament 8's bracket")
	}
	if got := m.BracketSlots(7)[8]; got != 1 {
		t.Errorf("tournament 7 slot 8 = %d, want 1", got)
	}
}
