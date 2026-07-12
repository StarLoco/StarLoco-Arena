package parser

import "testing"

// writeEffect appends one Effect record in the shared wire layout (see
// effect.go's ReadEffect) to w, for use by cards/spells/events file tests.
func (w *datWriter) writeEffect(e EffectRaw) *datWriter {
	w.int32(e.ID)
	w.string_(e.ParentType)
	w.int32(e.ParentID)
	w.int16(0) // unused/discarded field
	w.int32Slice(e.Duration)
	w.int32(e.ActionID)
	w.bool_(e.IsCritical)
	w.float32Slice(e.Params)
	w.int16(e.AreaShape)
	w.int32Slice(e.AreaSize)
	w.int32Slice(e.Targets)
	w.int32Slice(e.TriggersAfter)
	w.int32Slice(e.TriggersBefore)
	w.bool_(e.AffectedByLocalisation)
	return w
}

func TestParseCardsFile(t *testing.T) {
	w := &datWriter{}

	// Section 1: 2 coach cards.
	w.int32(2)
	w.int32(100).int32(1).int32(50).int32(0) // id, type, value, set
	w.int32(101).int32(2).int32(75).int32(1)

	// Section 2: 1 fighter card.
	w.int32(1)
	w.int32(200)   // id
	w.byte_(1)     // type
	w.byte_(0)     // unused
	w.bool_(false) // unused
	w.int32(0)     // unused
	w.int32(0)     // unused
	w.bool_(false) // unused
	w.bool_(false) // unused
	w.int32(999)   // value
	w.bool_(false) // unused
	w.bool_(false) // unused
	w.int32(5)     // scriptId
	w.int32(3)     // subType

	// Section 3: 1 effect attached to the fighter card above.
	w.int32(1)
	w.writeEffect(EffectRaw{
		ID:                     1,
		ParentType:             "FIGHTER_CARD",
		ParentID:               200,
		Duration:               []int32{1, 2},
		ActionID:               42,
		IsCritical:             true,
		Params:                 []float32{1.5, 2.5},
		AreaShape:              2,
		AreaSize:               []int32{3},
		Targets:                []int32{1},
		TriggersAfter:          nil,
		TriggersBefore:         nil,
		AffectedByLocalisation: true,
	})

	got, err := ParseCardsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseCardsFile: %v", err)
	}

	if len(got.CoachCards) != 2 {
		t.Fatalf("CoachCards len = %d, want 2", len(got.CoachCards))
	}
	if got.CoachCards[0] != (CoachCardRaw{ID: 100, Type: 1, Value: 50, Set: 0}) {
		t.Errorf("CoachCards[0] = %+v", got.CoachCards[0])
	}
	if got.CoachCards[1] != (CoachCardRaw{ID: 101, Type: 2, Value: 75, Set: 1}) {
		t.Errorf("CoachCards[1] = %+v", got.CoachCards[1])
	}

	if len(got.FighterCards) != 1 {
		t.Fatalf("FighterCards len = %d, want 1", len(got.FighterCards))
	}
	fc := got.FighterCards[0]
	if fc.ID != 200 || fc.Type != 1 || fc.Value != 999 || fc.ScriptID != 5 || fc.SubType != 3 {
		t.Errorf("FighterCards[0] = %+v", fc)
	}

	if len(got.Effects) != 1 {
		t.Fatalf("Effects len = %d, want 1", len(got.Effects))
	}
	eff := got.Effects[0]
	if eff.ID != 1 || eff.ParentType != "FIGHTER_CARD" || eff.ParentID != 200 || eff.ActionID != 42 || !eff.IsCritical {
		t.Errorf("Effects[0] = %+v", eff)
	}
	if len(eff.Params) != 2 || eff.Params[0] != 1.5 || eff.Params[1] != 2.5 {
		t.Errorf("Effects[0].Params = %v", eff.Params)
	}
}

func TestParseCardsFileEmpty(t *testing.T) {
	w := &datWriter{}
	w.int32(0) // 0 coach cards
	w.int32(0) // 0 fighter cards
	w.int32(0) // 0 effects

	got, err := ParseCardsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseCardsFile: %v", err)
	}
	if len(got.CoachCards) != 0 || len(got.FighterCards) != 0 || len(got.Effects) != 0 {
		t.Errorf("expected all-empty result, got %+v", got)
	}
}

func TestParseCardsFileTruncatedReturnsError(t *testing.T) {
	w := &datWriter{}
	w.int32(1) // claims 1 coach card
	w.int32(100).int32(1)
	// missing value+set fields -- truncated

	_, err := ParseCardsFile(w.buf)
	if err == nil {
		t.Fatal("expected error for truncated cards.dat, got nil")
	}
}

func TestParseSpellsFile(t *testing.T) {
	w := &datWriter{}
	// Fields written in the EXACT wire order the reference SpellLoader
	// reads them (SpellLoader.java:74-90): testFreeCell comes AFTER
	// aiTargetId, NOT between onlyLine and rangeMin.
	w.int32(1)     // 1 spell
	w.int32(500)   // id
	w.byte_(4)     // AP cost
	w.byte_(1)     // max per player
	w.byte_(1)     // max per turn
	w.byte_(0)     // min interval
	w.bool_(true)  // testLOS
	w.bool_(false) // onlyLine
	w.byte_(1)     // rangeMin
	w.byte_(6)     // rangeMax
	w.int32(0)     // price/value
	w.int32(0)     // aiTargetId
	w.bool_(true)  // testFreeCell (AFTER aiTargetId)
	w.int32(0)     // scriptId
	w.int32(1)     // breedId
	w.string_("")  // criterion
	w.bool_(false) // useAutoDescription

	w.int32(1) // 1 effect
	w.writeEffect(EffectRaw{ID: 10, ParentType: "SPELL", ParentID: 500, ActionID: 7})

	got, err := ParseSpellsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseSpellsFile: %v", err)
	}
	if len(got.Spells) != 1 {
		t.Fatalf("Spells len = %d, want 1", len(got.Spells))
	}
	sp := got.Spells[0]
	if sp.ID != 500 || sp.ActionPointsCost != 4 || sp.RangeMin != 1 || sp.RangeMax != 6 || sp.BreedID != 1 {
		t.Errorf("Spells[0] = %+v", sp)
	}
	if !sp.CastTestLineOfSight {
		t.Error("CastTestLineOfSight should be true")
	}
	if !sp.NeedFreeCell {
		t.Error("NeedFreeCell should be true (testFreeCell byte after aiTargetId)")
	}

	if len(got.Effects) != 1 || got.Effects[0].ParentType != "SPELL" || got.Effects[0].ParentID != 500 {
		t.Errorf("Effects = %+v", got.Effects)
	}
}

func TestParseEventsFile(t *testing.T) {
	w := &datWriter{}
	w.int32(2) // 2 events
	w.int32(1).bool_(true)
	w.int32(2).bool_(false)

	w.int32(1) // 1 effect
	w.writeEffect(EffectRaw{ID: 99, ParentType: "EVENT", ParentID: 1, ActionID: 3})

	got, err := ParseEventsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseEventsFile: %v", err)
	}
	if len(got.Events) != 2 {
		t.Fatalf("Events len = %d, want 2", len(got.Events))
	}
	if got.Events[0] != (EventRaw{ID: 1, UseAutoDescription: true}) {
		t.Errorf("Events[0] = %+v", got.Events[0])
	}
	if got.Events[1] != (EventRaw{ID: 2, UseAutoDescription: false}) {
		t.Errorf("Events[1] = %+v", got.Events[1])
	}
	if len(got.Effects) != 1 || got.Effects[0].ParentID != 1 {
		t.Errorf("Effects = %+v", got.Effects)
	}
}

func TestParseSummoningFile(t *testing.T) {
	w := &datWriter{}
	w.int32(2)
	w.int32(1).int32(100).int32(6).int32(3).int32(50).int32(500)
	w.int32(2).int32(50).int32(3).int32(2).int32(51).int32(501)

	got, err := ParseSummoningFile(w.buf)
	if err != nil {
		t.Fatalf("ParseSummoningFile: %v", err)
	}
	if len(got) != 2 {
		t.Fatalf("len = %d, want 2", len(got))
	}
	want0 := SummoningRaw{ID: 1, HP: 100, AP: 6, MP: 3, Gfx: 50, SpellID: 500}
	if got[0] != want0 {
		t.Errorf("got[0] = %+v, want %+v", got[0], want0)
	}
}

func TestParseSummoningFileEmpty(t *testing.T) {
	w := &datWriter{}
	w.int32(0)
	got, err := ParseSummoningFile(w.buf)
	if err != nil {
		t.Fatalf("ParseSummoningFile: %v", err)
	}
	if len(got) != 0 {
		t.Errorf("expected empty result, got %v", got)
	}
}
