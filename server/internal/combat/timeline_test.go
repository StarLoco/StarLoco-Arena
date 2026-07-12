package combat

import "testing"

func mkFighter(id int64, init int32) *Fighter {
	f := NewFighter(id, 1, BreedIop)
	f.Characteristics[Init].Value = init
	f.Characteristics[Init].Max = init
	f.Characteristics[HP].Value = 50
	f.Characteristics[HP].Max = 50
	return f
}

func TestBuildTurnOrder_DescendingInit(t *testing.T) {
	a := mkFighter(1, 10)
	b := mkFighter(2, 50)
	c := mkFighter(3, 30)

	order := BuildTurnOrder([]*Fighter{a, b, c})
	if len(order) != 3 || order[0] != b || order[1] != c || order[2] != a {
		t.Fatalf("order = %v, want [b, c, a]", order)
	}
}

func TestBuildTurnOrder_StableTies(t *testing.T) {
	a := mkFighter(1, 10)
	b := mkFighter(2, 10)
	c := mkFighter(3, 10)

	order := BuildTurnOrder([]*Fighter{a, b, c})
	if order[0] != a || order[1] != b || order[2] != c {
		t.Fatalf("tie order = %v, want insertion order [a,b,c]", order)
	}
}

func TestTimeline_ValidateGate_TurnDoesNotAutoAdvance(t *testing.T) {
	a := mkFighter(1, 20)
	b := mkFighter(2, 10)
	tl := NewTimeline([]*Fighter{a, b})

	f, isNew := tl.StartNextTurn()
	if f != a || !isNew {
		t.Fatalf("first StartNextTurn = (%v, %v), want (a, true)", f, isNew)
	}
	if tl.CurrentFighter() != a {
		t.Fatalf("CurrentFighter = %v, want a", tl.CurrentFighter())
	}

	// The timeline must NOT auto-advance: CurrentFighter stays "a" no
	// matter how many times we query, until EndCurrentTurn is explicitly
	// called (this is the validate-gate behavior from
	// docs/opcodes/08-fight-combat-engine.md §1.4).
	if tl.CurrentFighter() != a {
		t.Fatalf("timeline auto-advanced without EndCurrentTurn")
	}

	tl.EndCurrentTurn()
	if tl.CurrentFighter() != nil {
		t.Fatalf("CurrentFighter after EndCurrentTurn = %v, want nil", tl.CurrentFighter())
	}

	f2, isNew2 := tl.StartNextTurn()
	if f2 != b || isNew2 {
		t.Fatalf("second StartNextTurn = (%v, %v), want (b, false)", f2, isNew2)
	}
}

func TestTimeline_WrapsIntoNewTableTurn(t *testing.T) {
	a := mkFighter(1, 20)
	b := mkFighter(2, 10)
	tl := NewTimeline([]*Fighter{a, b})

	tl.StartNextTurn() // a, table turn 1
	tl.EndCurrentTurn()
	tl.StartNextTurn() // b, still table turn 1
	tl.EndCurrentTurn()

	f, isNew := tl.StartNextTurn() // wraps back to a
	if f != a || !isNew {
		t.Fatalf("wrap StartNextTurn = (%v, %v), want (a, true)", f, isNew)
	}
	if tl.TableTurn() != 2 {
		t.Fatalf("TableTurn = %d, want 2", tl.TableTurn())
	}
}

func TestTimeline_SkipsDeadFighters(t *testing.T) {
	a := mkFighter(1, 30)
	b := mkFighter(2, 20)
	c := mkFighter(3, 10)
	b.IsDead = true
	tl := NewTimeline([]*Fighter{a, b, c})

	f, _ := tl.StartNextTurn()
	if f != a {
		t.Fatalf("first turn = %v, want a", f)
	}
	tl.EndCurrentTurn()

	f2, _ := tl.StartNextTurn()
	if f2 != c {
		t.Fatalf("second turn = %v, want c (b is dead, should be skipped)", f2)
	}
}

func TestTimeline_AllDeadReturnsNil(t *testing.T) {
	a := mkFighter(1, 30)
	a.IsDead = true
	tl := NewTimeline([]*Fighter{a})

	f, isNew := tl.StartNextTurn()
	if f != nil || isNew {
		t.Fatalf("StartNextTurn with all dead = (%v, %v), want (nil, false)", f, isNew)
	}
}

func TestTimeline_InsertAfter_SingleSummon(t *testing.T) {
	a := mkFighter(1, 30)
	b := mkFighter(2, 10)
	tl := NewTimeline([]*Fighter{a, b})

	summon := mkFighter(3, 5)
	tl.InsertAfter(a, summon)

	order := tl.Order()
	if len(order) != 3 || order[0] != a || order[1] != summon || order[2] != b {
		t.Fatalf("order after single summon insert = %v, want [a, summon, b]", order)
	}
}

func TestTimeline_InsertAfter_MultipleSummonsQueueTogether(t *testing.T) {
	a := mkFighter(1, 30)
	b := mkFighter(2, 10)
	tl := NewTimeline([]*Fighter{a, b})

	summon1 := mkFighter(3, 5)
	summon2 := mkFighter(4, 5)
	tl.InsertAfter(a, summon1)
	tl.InsertAfter(a, summon2)

	order := tl.Order()
	// Both summons should queue up together after "a", in insertion order,
	// not have summon2 land between "a" and summon1.
	if len(order) != 4 || order[0] != a || order[1] != summon1 || order[2] != summon2 || order[3] != b {
		t.Fatalf("order after 2 summons = %v, want [a, summon1, summon2, b]", order)
	}
}

func TestTimeline_RemoveFighter_MidRound(t *testing.T) {
	a := mkFighter(1, 30)
	b := mkFighter(2, 20)
	c := mkFighter(3, 10)
	tl := NewTimeline([]*Fighter{a, b, c})

	tl.StartNextTurn() // a
	tl.EndCurrentTurn()

	// b dies before their turn starts.
	b.IsDead = true
	tl.RemoveFighter(b)

	f, _ := tl.StartNextTurn()
	if f != c {
		t.Fatalf("after removing b mid-round, next turn = %v, want c", f)
	}
}

// TestTimeline_SummonByLastFighter_PlaysSameRound reproduces the reported
// "summoning double freezes the UI for a full round" bug: the fighter who
// summons is the LAST in the turn order, so the turn cursor has already
// wrapped to the front. The summon must still act THIS round (right after
// its father) and its insertion must NOT trigger a spurious
// NEW_TABLE_TURN_BEGIN. Order [a,b,c]; c (last) summons s.
func TestTimeline_SummonByLastFighter_PlaysSameRound(t *testing.T) {
	a := mkFighter(1, 30)
	b := mkFighter(2, 20)
	c := mkFighter(3, 10)
	tl := NewTimeline([]*Fighter{a, b, c})

	// Round 1: a, b, c.
	if f, isNew := tl.StartNextTurn(); f != a || !isNew {
		t.Fatalf("turn 1 = (%v,%v), want (a,true)", f, isNew)
	}
	tl.EndCurrentTurn()
	if f, isNew := tl.StartNextTurn(); f != b || isNew {
		t.Fatalf("turn 2 = (%v,%v), want (b,false)", f, isNew)
	}
	tl.EndCurrentTurn()
	if f, isNew := tl.StartNextTurn(); f != c || isNew {
		t.Fatalf("turn 3 = (%v,%v), want (c,false)", f, isNew)
	}

	// c (the last fighter, mid-turn) summons s.
	s := mkFighter(4, 5)
	tl.InsertAfter(c, s)
	if order := tl.Order(); len(order) != 4 || order[3] != s {
		t.Fatalf("order after summon = %v, want [...,s]", order)
	}

	tl.EndCurrentTurn()

	// The summon MUST play next, in the SAME round -- no new table turn.
	if f, isNew := tl.StartNextTurn(); f != s {
		t.Fatalf("post-summon turn = %v, want s (summon plays same round)", f)
	} else if isNew {
		t.Fatalf("post-summon turn wrongly reported a NEW table turn (the bug)")
	}
	if tt := tl.TableTurn(); tt != 1 {
		t.Fatalf("TableTurn after summon = %d, want 1 (still round 1)", tt)
	}

	// Only AFTER the summon does the round actually wrap to a new one.
	tl.EndCurrentTurn()
	if f, isNew := tl.StartNextTurn(); f != a || !isNew {
		t.Fatalf("wrap turn = (%v,%v), want (a,true)", f, isNew)
	}
	if tt := tl.TableTurn(); tt != 2 {
		t.Fatalf("TableTurn after wrap = %d, want 2", tt)
	}
}

// TestTimeline_SummonByFirstFighter_PlaysSameRound covers the mid-round
// case: the summoner acts early, so the summon is spliced in ahead of the
// still-pending fighters and plays right after its father, without a new
// table turn. Order [a,b,c]; a (first) summons s -> [a,s,b,c].
func TestTimeline_SummonByFirstFighter_PlaysSameRound(t *testing.T) {
	a := mkFighter(1, 30)
	b := mkFighter(2, 20)
	c := mkFighter(3, 10)
	tl := NewTimeline([]*Fighter{a, b, c})

	if f, isNew := tl.StartNextTurn(); f != a || !isNew {
		t.Fatalf("turn 1 = (%v,%v), want (a,true)", f, isNew)
	}

	s := mkFighter(4, 5)
	tl.InsertAfter(a, s)
	if order := tl.Order(); len(order) != 4 || order[1] != s {
		t.Fatalf("order after summon = %v, want [a,s,b,c]", order)
	}

	tl.EndCurrentTurn()

	// Expected remainder of round 1: s, then b, then c -- none new-round.
	for i, want := range []*Fighter{s, b, c} {
		f, isNew := tl.StartNextTurn()
		if f != want || isNew {
			t.Fatalf("turn %d = (%v,%v), want (%v,false)", i+2, f, isNew, want)
		}
		tl.EndCurrentTurn()
	}
	if tt := tl.TableTurn(); tt != 1 {
		t.Fatalf("TableTurn at end of round = %d, want 1", tt)
	}

	// Now the round wraps to a fresh table turn.
	if f, isNew := tl.StartNextTurn(); f != a || !isNew {
		t.Fatalf("wrap turn = (%v,%v), want (a,true)", f, isNew)
	}
	if tt := tl.TableTurn(); tt != 2 {
		t.Fatalf("TableTurn after wrap = %d, want 2", tt)
	}
}
