package parser

import "testing"

// aleaWriter is a tiny test-only builder for the Alea binary convention
// (little-endian, distinct from datWriter's big-endian .dat convention --
// see reader_test.go).
type aleaWriter struct {
	buf []byte
}

func (w *aleaWriter) byte_(v byte) *aleaWriter {
	w.buf = append(w.buf, v)
	return w
}
func (w *aleaWriter) bool_(v bool) *aleaWriter {
	if v {
		return w.byte_(1)
	}
	return w.byte_(0)
}
func (w *aleaWriter) int16(v int16) *aleaWriter {
	w.buf = append(w.buf, byte(v), byte(v>>8))
	return w
}
func (w *aleaWriter) int32(v int32) *aleaWriter {
	w.buf = append(w.buf, byte(v), byte(v>>8), byte(v>>16), byte(v>>24))
	return w
}

// writeSpatialData appends one 21-byte SpatialDataElementProperties
// record (all flags false, weight 0, height h, slope 0) except walkable,
// which is parameterized -- the common case needed by amw_test.go's
// walkability tests.
func (w *aleaWriter) writeSpatialData(height int8, walkable bool) *aleaWriter {
	w.int16(0)     // weight
	w.bool_(false) // uniqueWeightInLevel
	w.bool_(false) // piled
	w.bool_(false) // mobileOnTop
	w.byte_(byte(height))
	w.bool_(false) // virtualHeight
	w.byte_(0)     // slope
	w.bool_(false) // los1
	w.bool_(false) // los3
	w.bool_(false) // los5
	w.bool_(false) // los7
	w.bool_(false) // losTop
	w.bool_(false) // losBottom
	w.bool_(false) // move1
	w.bool_(false) // move3
	w.bool_(false) // move5
	w.bool_(false) // move7
	w.bool_(false) // moveTop
	w.bool_(false) // moveBottom
	w.bool_(walkable)
	return w
}

// writeGraphical appends one 30-byte GraphicalElementProperties record
// (21-byte spatial data + gfxId/originX/originY/flip).
func (w *aleaWriter) writeGraphical(height int8, walkable bool, gfxID int32) *aleaWriter {
	w.writeSpatialData(height, walkable)
	w.int32(gfxID)
	w.int16(0) // originX
	w.int16(0) // originY
	w.bool_(false)
	return w
}

func TestParseElementsFile_GraphicalElement(t *testing.T) {
	w := &aleaWriter{}
	w.int32(42) // id
	w.int16(int16(ElementKindGraphical))
	w.byte_(1) // 1 state
	w.byte_(0) // state=0
	w.writeGraphical(5, true, 999)

	got, err := ParseElementsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseElementsFile: %v", err)
	}
	el, ok := got.Elements[42]
	if !ok {
		t.Fatal("element 42 not found")
	}
	if el.Kind != ElementKindGraphical {
		t.Errorf("Kind = %v, want Graphical", el.Kind)
	}
	props, ok := el.StateProperties(0)
	if !ok {
		t.Fatal("state 0 not found")
	}
	if !props.Walkable {
		t.Error("Walkable = false, want true")
	}
	if props.HeightRaw != 5 {
		t.Errorf("HeightRaw = %d, want 5", props.HeightRaw)
	}
	if props.GfxID != 999 {
		t.Errorf("GfxID = %d, want 999", props.GfxID)
	}
}

func TestParseElementsFile_BonusElementHasGraphicalPayload(t *testing.T) {
	w := &aleaWriter{}
	w.int32(1002)
	w.int16(int16(ElementKindBonus))
	w.byte_(1)
	w.byte_(0)
	w.writeGraphical(0, true, 5)

	got, err := ParseElementsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseElementsFile: %v", err)
	}
	el, ok := got.Elements[1002]
	if !ok {
		t.Fatal("element 1002 not found")
	}
	props, ok := el.StateProperties(0)
	if !ok || !props.Walkable {
		t.Errorf("BonusElement state properties = %+v, ok=%v -- BonusElement extends GraphicalElement and must carry real properties", props, ok)
	}
}

func TestParseElementsFile_CustomFightStartPointHasNoPayload(t *testing.T) {
	w := &aleaWriter{}
	w.int32(5000)
	w.int16(int16(ElementKindFightStartPoint))
	w.byte_(1) // 1 state
	w.byte_(0) // state=0, zero extra bytes (BasicElement's empty readStateProperties)
	// Immediately followed by a second unrelated record to confirm no
	// bytes were skipped.
	w.int32(6000)
	w.int16(int16(ElementKindFightStartCoachPoint))
	w.byte_(0) // 0 states

	got, err := ParseElementsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseElementsFile: %v", err)
	}
	if len(got.Elements) != 2 {
		t.Fatalf("Elements len = %d, want 2", len(got.Elements))
	}
	if _, ok := got.Elements[5000]; !ok {
		t.Error("element 5000 not found")
	}
	if _, ok := got.Elements[6000]; !ok {
		t.Error("element 6000 not found")
	}
}

func TestParseElementsFile_MultipleStates(t *testing.T) {
	w := &aleaWriter{}
	w.int32(7)
	w.int16(int16(ElementKindGraphical))
	w.byte_(2) // 2 states (e.g. open/closed door)
	w.byte_(0)
	w.writeGraphical(0, true, 1) // state 0: walkable
	w.byte_(1)
	w.writeGraphical(3, false, 2) // state 1: not walkable, height 3

	got, err := ParseElementsFile(w.buf)
	if err != nil {
		t.Fatalf("ParseElementsFile: %v", err)
	}
	el := got.Elements[7]
	p0, _ := el.StateProperties(0)
	p1, _ := el.StateProperties(1)
	if !p0.Walkable || p1.Walkable {
		t.Errorf("state0.Walkable=%v (want true) state1.Walkable=%v (want false)", p0.Walkable, p1.Walkable)
	}
	if p1.HeightRaw != 3 {
		t.Errorf("state1.HeightRaw = %d, want 3", p1.HeightRaw)
	}
}

func TestParseElementsFile_TruncatedReturnsError(t *testing.T) {
	w := &aleaWriter{}
	w.int32(1)
	w.int16(int16(ElementKindGraphical))
	w.byte_(1)
	w.byte_(0)
	// Missing the 30-byte Graphical payload entirely.

	_, err := ParseElementsFile(w.buf)
	if err == nil {
		t.Fatal("expected error for truncated elements.ade, got nil")
	}
}

func TestPeekAleaHeader(t *testing.T) {
	data := []byte{77, 1, 0xAA, 0xBB}
	hdr, rest, err := PeekAleaHeader(data)
	if err != nil {
		t.Fatalf("PeekAleaHeader: %v", err)
	}
	if hdr.TypeCode != 77 || hdr.Version != 1 {
		t.Errorf("header = %+v, want {77 1}", hdr)
	}
	if len(rest) != 2 || rest[0] != 0xAA || rest[1] != 0xBB {
		t.Errorf("rest = %v, want [0xAA 0xBB]", rest)
	}
}

func TestPeekAleaHeader_TooShort(t *testing.T) {
	_, _, err := PeekAleaHeader([]byte{1})
	if err == nil {
		t.Fatal("expected error for 1-byte input")
	}
}
