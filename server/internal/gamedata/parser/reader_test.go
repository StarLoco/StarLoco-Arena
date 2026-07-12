package parser

import (
	"encoding/binary"
	"math"
	"testing"
)

// datWriter is a tiny test-only builder for the .dat file format's
// primitives (distinct from internal/protocol.Writer -- 4-byte string
// length prefix instead of 1-byte, see reader.go's doc comment).
type datWriter struct {
	buf []byte
}

func (w *datWriter) bool_(v bool) *datWriter {
	if v {
		return w.byte_(1)
	}
	return w.byte_(0)
}
func (w *datWriter) byte_(v byte) *datWriter {
	w.buf = append(w.buf, v)
	return w
}
func (w *datWriter) int16(v int16) *datWriter {
	var b [2]byte
	binary.BigEndian.PutUint16(b[:], uint16(v))
	w.buf = append(w.buf, b[:]...)
	return w
}
func (w *datWriter) int32(v int32) *datWriter {
	var b [4]byte
	binary.BigEndian.PutUint32(b[:], uint32(v))
	w.buf = append(w.buf, b[:]...)
	return w
}
func (w *datWriter) float32_(v float32) *datWriter {
	return w.int32(int32(math.Float32bits(v)))
}
func (w *datWriter) string_(s string) *datWriter {
	w.int32(int32(len(s)))
	w.buf = append(w.buf, s...)
	return w
}
func (w *datWriter) int32Slice(vals []int32) *datWriter {
	w.int32(int32(len(vals)))
	for _, v := range vals {
		w.int32(v)
	}
	return w
}
func (w *datWriter) float32Slice(vals []float32) *datWriter {
	w.int32(int32(len(vals)))
	for _, v := range vals {
		w.float32_(v)
	}
	return w
}

func TestReaderPrimitivesRoundTrip(t *testing.T) {
	w := &datWriter{}
	w.bool_(true).byte_(0x7F).int16(-1000).int32(-100000).float32_(2.5).string_("café")

	r := NewReader(w.buf)
	if got := r.Bool(); got != true {
		t.Errorf("Bool() = %v, want true", got)
	}
	if got := r.Byte(); got != 0x7F {
		t.Errorf("Byte() = %#x, want 0x7F", got)
	}
	if got := r.Int16(); got != -1000 {
		t.Errorf("Int16() = %d, want -1000", got)
	}
	if got := r.Int32(); got != -100000 {
		t.Errorf("Int32() = %d, want -100000", got)
	}
	if got := r.Float32(); got != 2.5 {
		t.Errorf("Float32() = %v, want 2.5", got)
	}
	if got := r.String(); got != "café" {
		t.Errorf("String() = %q, want %q", got, "café")
	}
	if err := r.Err(); err != nil {
		t.Fatalf("unexpected error: %v", err)
	}
}

func TestReaderSlicesRoundTrip(t *testing.T) {
	w := &datWriter{}
	w.int32Slice([]int32{1, -2, 3, 0})
	w.float32Slice([]float32{1.5, -2.25})

	r := NewReader(w.buf)
	gotInts := r.Int32Slice()
	wantInts := []int32{1, -2, 3, 0}
	if len(gotInts) != len(wantInts) {
		t.Fatalf("Int32Slice len = %d, want %d", len(gotInts), len(wantInts))
	}
	for i := range wantInts {
		if gotInts[i] != wantInts[i] {
			t.Errorf("Int32Slice[%d] = %d, want %d", i, gotInts[i], wantInts[i])
		}
	}

	gotFloats := r.Float32Slice()
	wantFloats := []float32{1.5, -2.25}
	if len(gotFloats) != len(wantFloats) {
		t.Fatalf("Float32Slice len = %d, want %d", len(gotFloats), len(wantFloats))
	}
	for i := range wantFloats {
		if gotFloats[i] != wantFloats[i] {
			t.Errorf("Float32Slice[%d] = %v, want %v", i, gotFloats[i], wantFloats[i])
		}
	}
}

func TestReaderEmptySlice(t *testing.T) {
	w := &datWriter{}
	w.int32Slice(nil)

	r := NewReader(w.buf)
	got := r.Int32Slice()
	if len(got) != 0 {
		t.Errorf("Int32Slice() on empty = %v, want empty", got)
	}
}

func TestReaderShortReadIsSticky(t *testing.T) {
	r := NewReader([]byte{0, 0}) // too short for even an Int32
	_ = r.Int32()
	if r.Err() == nil {
		t.Fatal("expected error on short read")
	}
	// Once failed, further reads must not panic and must return zero
	// values.
	if got := r.String(); got != "" {
		t.Errorf("String() after error = %q, want empty", got)
	}
	if got := r.Int32Slice(); got != nil {
		t.Errorf("Int32Slice() after error = %v, want nil", got)
	}
}
