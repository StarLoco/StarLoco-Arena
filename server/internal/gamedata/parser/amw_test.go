package parser

import "testing"

// writeCellElement appends one AMWCellElement wire record: elementId,
// state, groupId, paramCount, then that many (type byte + payload) pairs.
func (w *aleaWriter) writeCellElement(elementID int32, state byte, groupID int32, params [][]byte, paramTypes []byte) *aleaWriter {
	w.int32(elementID)
	w.byte_(state)
	w.int32(groupID)
	w.byte_(byte(len(params)))
	for i, p := range params {
		w.byte_(paramTypes[i])
		w.buf = append(w.buf, p...)
	}
	return w
}

func TestParseAMWFile_SingleCellNoElements(t *testing.T) {
	w := &aleaWriter{}
	w.int32(0) // coordX
	w.int32(0) // coordY
	w.byte_(1) // size=1 -> 1 cell
	w.byte_(0) // levelCount=0 for the only cell

	got, err := ParseAMWFile(w.buf)
	if err != nil {
		t.Fatalf("ParseAMWFile: %v", err)
	}
	if got.Size != 1 || len(got.Cells) != 1 {
		t.Fatalf("got %+v", got)
	}
	if got.Cells[0].X != 0 || got.Cells[0].Y != 0 {
		t.Errorf("Cells[0] coords = (%d,%d), want (0,0)", got.Cells[0].X, got.Cells[0].Y)
	}
}

func TestParseAMWFile_CoordOffsetAppliesToWorldCoords(t *testing.T) {
	w := &aleaWriter{}
	w.int32(-1) // coordX
	w.int32(2)  // coordY
	w.byte_(2)  // size=2 -> 4 cells, row-major: (0,0)(1,0)(0,1)(1,1) local
	for i := 0; i < 4; i++ {
		w.byte_(0) // no levels
	}

	got, err := ParseAMWFile(w.buf)
	if err != nil {
		t.Fatalf("ParseAMWFile: %v", err)
	}
	// world base = coordX*size=-2, coordY*size=4
	want := [][2]int32{{-2, 4}, {-1, 4}, {-2, 5}, {-1, 5}}
	for i, w2 := range want {
		if got.Cells[i].X != w2[0] || got.Cells[i].Y != w2[1] {
			t.Errorf("Cells[%d] = (%d,%d), want (%d,%d)", i, got.Cells[i].X, got.Cells[i].Y, w2[0], w2[1])
		}
	}
}

func TestParseAMWFile_ElementWithParam(t *testing.T) {
	w := &aleaWriter{}
	w.int32(0)
	w.int32(0)
	w.byte_(1)                                               // size=1
	w.byte_(1)                                               // levelCount=1
	w.byte_(1)                                               // elemCount=1
	w.writeCellElement(1001, 0, 0, [][]byte{{1}}, []byte{3}) // FightStartCoachPointElement, team-side flag=1 (type=3, 1 byte)

	got, err := ParseAMWFile(w.buf)
	if err != nil {
		t.Fatalf("ParseAMWFile: %v", err)
	}
	el := got.Cells[0].Levels[0].Elements[0]
	if el.ElementID != 1001 {
		t.Errorf("ElementID = %d, want 1001", el.ElementID)
	}
	b, ok := el.ParamAsByte(0)
	if !ok || b != 1 {
		t.Errorf("ParamAsByte(0) = (%d, %v), want (1, true)", b, ok)
	}
}

func TestParseAMWFile_MultipleParamsWithDifferentTypes(t *testing.T) {
	w := &aleaWriter{}
	w.int32(0)
	w.int32(0)
	w.byte_(1)
	w.byte_(1)
	w.byte_(1)
	// param0: type=1 (1 byte) value=0xAB; param1: type=6 (int32, 4 bytes) value via LE bytes
	w.writeCellElement(42, 0, 0, [][]byte{{0xAB}, {0x01, 0x02, 0x03, 0x04}}, []byte{1, 6})

	got, err := ParseAMWFile(w.buf)
	if err != nil {
		t.Fatalf("ParseAMWFile: %v", err)
	}
	el := got.Cells[0].Levels[0].Elements[0]
	p0 := el.Param(0)
	if len(p0) != 1 || p0[0] != 0xAB {
		t.Errorf("Param(0) = %v, want [0xAB]", p0)
	}
	p1 := el.Param(1)
	if len(p1) != 4 || p1[0] != 0x01 || p1[3] != 0x04 {
		t.Errorf("Param(1) = %v, want [0x01 0x02 0x03 0x04]", p1)
	}
}

func TestParseAMWFile_MultipleLevelsPerCell(t *testing.T) {
	w := &aleaWriter{}
	w.int32(0)
	w.int32(0)
	w.byte_(1)
	w.byte_(2) // levelCount=2
	w.byte_(1) // level0: 1 element
	w.writeCellElement(1, 0, 0, nil, nil)
	w.byte_(1) // level1: 1 element
	w.writeCellElement(2, 0, 0, nil, nil)

	got, err := ParseAMWFile(w.buf)
	if err != nil {
		t.Fatalf("ParseAMWFile: %v", err)
	}
	cell := got.Cells[0]
	if len(cell.Levels) != 2 {
		t.Fatalf("Levels len = %d, want 2", len(cell.Levels))
	}
	if cell.Levels[0].Elements[0].ElementID != 1 || cell.Levels[1].Elements[0].ElementID != 2 {
		t.Errorf("level elements = %+v", cell.Levels)
	}
}

func TestParseAMWFile_TrailingBytesIsError(t *testing.T) {
	w := &aleaWriter{}
	w.int32(0)
	w.int32(0)
	w.byte_(1)
	w.byte_(0)
	w.byte_(0xFF) // trailing junk byte after a fully-consumed 1-cell chunk

	_, err := ParseAMWFile(w.buf)
	if err == nil {
		t.Fatal("expected error for trailing bytes, got nil")
	}
}

func TestParseAMWFile_TruncatedReturnsError(t *testing.T) {
	w := &aleaWriter{}
	w.int32(0)
	w.int32(0)
	w.byte_(2) // size=2 -> claims 4 cells
	w.byte_(0) // only 1 cell's worth of data provided

	_, err := ParseAMWFile(w.buf)
	if err == nil {
		t.Fatal("expected error for truncated .amw, got nil")
	}
}

func TestAMWMapChunk_CellAt(t *testing.T) {
	w := &aleaWriter{}
	w.int32(1) // coordX
	w.int32(0) // coordY
	w.byte_(2) // size=2
	for i := 0; i < 4; i++ {
		w.byte_(0)
	}
	chunk, err := ParseAMWFile(w.buf)
	if err != nil {
		t.Fatalf("ParseAMWFile: %v", err)
	}
	// base = coordX*size = 2
	cell, ok := chunk.CellAt(2, 0)
	if !ok {
		t.Fatal("CellAt(2,0) not found")
	}
	if cell.X != 2 || cell.Y != 0 {
		t.Errorf("cell = %+v", cell)
	}
	if _, ok := chunk.CellAt(999, 999); ok {
		t.Error("CellAt(999,999) should be out of range")
	}
}
