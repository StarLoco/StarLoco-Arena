package sba

import (
	"bytes"
	"compress/zlib"
	"math"
	"testing"
)

// bitWriter mirrors the client's OutputBitStream semantics (MSB-first bits, LE
// integers, align-flushes-partial-byte) so tests can craft byte-exact inputs.
type bitWriter struct {
	buf   []byte
	cur   byte
	nbits int
}

func (w *bitWriter) writeBit(b int) {
	if b != 0 {
		w.cur |= 1 << (7 - w.nbits)
	}
	w.nbits++
	if w.nbits == 8 {
		w.buf = append(w.buf, w.cur)
		w.cur, w.nbits = 0, 0
	}
}

func (w *bitWriter) writeBits(v uint64, n int) {
	for i := n - 1; i >= 0; i-- {
		w.writeBit(int((v >> uint(i)) & 1))
	}
}

func (w *bitWriter) align() {
	if w.nbits > 0 {
		w.buf = append(w.buf, w.cur)
		w.cur, w.nbits = 0, 0
	}
}

func (w *bitWriter) writeUI8(v uint8)   { w.align(); w.buf = append(w.buf, v) }
func (w *bitWriter) writeUI16(v uint16) { w.align(); w.buf = append(w.buf, byte(v), byte(v>>8)) }
func (w *bitWriter) writeUI32(v uint32) {
	w.align()
	w.buf = append(w.buf, byte(v), byte(v>>8), byte(v>>16), byte(v>>24))
}
func (w *bitWriter) writeFPBits(f float64, n int) {
	iv := int64(math.Round(f * 65536.0))
	w.writeBits(uint64(iv)&((1<<uint(n))-1), n)
}
func (w *bitWriter) writeBytes(b []byte) { w.align(); w.buf = append(w.buf, b...) }
func (w *bitWriter) bytes() []byte       { w.align(); return w.buf }

// --- bit reader primitives ---

func TestBitReader_Primitives(t *testing.T) {
	w := &bitWriter{}
	w.writeUI16(0x1234)
	w.writeUI32(0xDEADBEEF)
	r := newBitReader(w.bytes())
	if v := r.readUI16(); v != 0x1234 {
		t.Errorf("readUI16 = %#x, want 0x1234 (little-endian)", v)
	}
	if v := r.readUI32(); v != 0xDEADBEEF {
		t.Errorf("readUI32 = %#x, want 0xDEADBEEF", v)
	}
}

func TestBitReader_Bits(t *testing.T) {
	w := &bitWriter{}
	w.writeBits(0b101, 3)     // unsigned 5
	w.writeBits(tc(-3, 4), 4) // signed -3 in 4 bits
	r := newBitReader(w.bytes())
	if v := r.readUnsignedBits(3); v != 5 {
		t.Errorf("readUnsignedBits = %d, want 5", v)
	}
	if v := r.readSignedBits(4); v != -3 {
		t.Errorf("readSignedBits = %d, want -3", v)
	}
}

func TestBitReader_AlignAbandonsPartialByte(t *testing.T) {
	// A single bit, then a UI8: the reader must abandon the partial byte and
	// read the next byte fresh (fillBitBuffer + align semantics).
	w := &bitWriter{}
	w.writeBit(1)
	w.writeUI8(0x42)
	r := newBitReader(w.bytes())
	if !r.readBooleanBit() {
		t.Fatal("first bit should be 1")
	}
	if v := r.readUI8(); v != 0x42 {
		t.Errorf("readUI8 after partial bit = %#x, want 0x42", v)
	}
}

func TestBitReader_Float16(t *testing.T) {
	// 1.0 in IEEE half is 0x3C00.
	w := &bitWriter{}
	w.writeUI16(0x3C00)
	r := newBitReader(w.bytes())
	if v := r.readFloat16(); v != 1.0 {
		t.Errorf("readFloat16(0x3C00) = %v, want 1.0", v)
	}
}

func TestBitReader_String(t *testing.T) {
	r := newBitReader([]byte{'h', 'i', 0x00, 0xFF})
	if s := r.readString(); s != "hi" {
		t.Errorf("readString = %q, want \"hi\"", s)
	}
}

// --- records ---

func TestReadMatrix_RoundTrip(t *testing.T) {
	w := &bitWriter{}
	w.writeBit(1) // hasScale
	w.writeBits(20, 5)
	w.writeFPBits(0.5, 20)
	w.writeFPBits(2.0, 20)
	w.writeBit(0) // hasRotateSkew
	w.writeBit(1) // hasTranslate
	w.writeBits(24, 5)
	w.writeFPBits(-10.0, 24)
	w.writeFPBits(15.0, 24)
	w.align()

	r := newBitReader(w.bytes())
	m := readMatrix(r)
	if !m.HasScale || !approx(m.ScaleX, 0.5) || !approx(m.ScaleY, 2.0) {
		t.Errorf("scale = (%v,%v), want (0.5,2.0)", m.ScaleX, m.ScaleY)
	}
	if m.HasRotate {
		t.Error("hasRotate should be false")
	}
	if !m.HasTranslate || !approx(m.TranslateX, -10.0) || !approx(m.TranslateY, 15.0) {
		t.Errorf("translate = (%v,%v), want (-10,15)", m.TranslateX, m.TranslateY)
	}
}

func TestReadColorTransform_FlagValueOrderingAsymmetry(t *testing.T) {
	// The source reads FLAGS add-then-mult, but VALUE blocks mult-then-add.
	// Set only mult terms: hasAdd=0, hasMult=1.
	w := &bitWriter{}
	w.writeBit(0) // hasAddTerms (read first)
	w.writeBit(1) // hasMultTerms
	w.writeBits(10, 4)
	w.writeBits(tc(128, 10), 10) // redMult
	w.writeBits(tc(256, 10), 10) // greenMult
	w.writeBits(tc(-5, 10), 10)  // blueMult
	w.writeBits(tc(256, 10), 10) // alphaMult
	w.align()

	r := newBitReader(w.bytes())
	c := readColorTransform(r)
	if c.HasAdd {
		t.Error("hasAdd should be false")
	}
	if !c.HasMult {
		t.Fatal("hasMult should be true")
	}
	if c.RedMult != 128 || c.GreenMult != 256 || c.BlueMult != -5 || c.AlphaMult != 256 {
		t.Errorf("mult = (%d,%d,%d,%d), want (128,256,-5,256)", c.RedMult, c.GreenMult, c.BlueMult, c.AlphaMult)
	}
}

func TestDecodeDisplayOp_PlaceWithMatrixAndColor(t *testing.T) {
	w := &bitWriter{}
	w.writeUI16(42) // identifier
	w.writeUI16(3)  // depth
	w.writeBit(1)   // hasMatrix
	// matrix: translate only
	w.writeBit(0) // hasScale
	w.writeBit(0) // hasRotate
	w.writeBit(1) // hasTranslate
	w.writeBits(24, 5)
	w.writeFPBits(5.0, 24)
	w.writeFPBits(7.0, 24)
	w.align()
	w.writeBit(0) // hasColorTransform
	w.align()

	op := decodeDisplayOp(TagPlaceObject, w.bytes())
	if op.Op != "place" || op.CharID != 42 || op.Depth != 3 {
		t.Fatalf("op = %+v, want place char=42 depth=3", op)
	}
	if op.Matrix == nil || !approx(op.Matrix.TranslateX, 5.0) || !approx(op.Matrix.TranslateY, 7.0) {
		t.Errorf("matrix translate wrong: %+v", op.Matrix)
	}
	if op.Color != nil {
		t.Error("color should be absent")
	}
}

func TestDecodeMovieClip_NestedWalkAndFrameCount(t *testing.T) {
	// Body: def header (id=9, no linkage) + loopCount, then Place, ShowFrame,
	// ShowFrame, End.
	w := &bitWriter{}
	w.writeUI16(9) // identifier
	w.writeBit(0)  // hasLinkage
	w.writeUI8(2)  // loopCount

	place := &bitWriter{}
	place.writeUI16(1) // char
	place.writeUI16(0) // depth
	place.writeBit(0)  // no matrix
	place.writeBit(0)  // no color
	appendTag(w, TagPlaceObject, place.bytes())
	appendTag(w, TagShowFrame, ui16(11))
	appendTag(w, TagShowFrame, ui16(22))
	appendTag(w, TagEnd, nil)

	sym, err := decodeDefineMovieClip(w.bytes(), 3)
	if err != nil {
		t.Fatalf("decode: %v", err)
	}
	if sym.ID != 9 || sym.LoopCount != 2 {
		t.Errorf("id/loop = %d/%d, want 9/2", sym.ID, sym.LoopCount)
	}
	if sym.FrameCount != 2 {
		t.Errorf("frameCount = %d, want 2", sym.FrameCount)
	}
	if len(sym.Ops) != 3 {
		t.Errorf("ops = %d, want 3 (place + 2 showframe)", len(sym.Ops))
	}
}

func TestDecodeDefineBitmap_V3RGBA(t *testing.T) {
	// Build a 2x1 v3 bitmap: red then green, straight alpha (not premultiplied).
	inner := &bitWriter{}
	inner.writeUI8(1) // AlphaBitmapData version
	inner.writeBit(0) // alphaPremultiplied = false
	inner.writeUI16(2)
	inner.writeUI16(1)
	rgba := []byte{255, 0, 0, 255, 0, 255, 0, 255}
	inner.writeUI32(uint32(len(rgba)))
	inner.writeBytes(rgba)

	var zbuf bytes.Buffer
	zw := zlib.NewWriter(&zbuf)
	zw.Write(inner.bytes())
	zw.Close()

	body := &bitWriter{}
	body.writeUI16(7)      // identifier
	body.writeBit(0)       // hasLinkage
	body.writeUI16(0x3C00) // invertScaling = float16 1.0
	body.writeSI32(3, 4)   // hotPoint (x=3,y=4)
	body.writeUI8(100)     // quality
	body.writeUI16(uint16(zbuf.Len()))
	body.writeBytes(zbuf.Bytes())

	sym, err := decodeDefineBitmap(body.bytes(), 3)
	if err != nil {
		t.Fatalf("decode: %v", err)
	}
	if sym.ID != 7 || sym.HotX != 3 || sym.HotY != 4 {
		t.Errorf("id/hot = %d/(%d,%d), want 7/(3,4)", sym.ID, sym.HotX, sym.HotY)
	}
	if sym.Bitmap == nil || sym.Bitmap.Width != 2 || sym.Bitmap.Height != 1 {
		t.Fatalf("bitmap dims wrong: %+v", sym.Bitmap)
	}
	if !bytes.Equal(sym.Bitmap.RGBA, rgba) {
		t.Errorf("RGBA = %v, want %v", sym.Bitmap.RGBA, rgba)
	}
}

// helpers

func (w *bitWriter) writeSI32(x, y int32) {
	w.writeUI32(uint32(x))
	w.writeUI32(uint32(y))
}

func appendTag(w *bitWriter, code int, body []byte) {
	w.align()
	th := uint16(code<<6) | uint16(len(body))
	if len(body) >= 0x3f {
		th = uint16(code<<6) | 0x3f
		w.writeUI16(th)
		w.writeUI32(uint32(len(body)))
	} else {
		w.writeUI16(th)
	}
	if len(body) > 0 {
		w.writeBytes(body)
	}
}

func ui16(v uint16) []byte { return []byte{byte(v), byte(v >> 8)} }

func approx(a, b float64) bool { return math.Abs(a-b) < 1e-3 }

// tc returns v as an n-bit two's-complement value (runtime, so negative
// literals don't overflow uint64 at compile time).
func tc(v int64, n int) uint64 { return uint64(v) & ((1 << uint(n)) - 1) }
